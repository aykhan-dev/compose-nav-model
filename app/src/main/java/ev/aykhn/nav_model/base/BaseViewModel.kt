package ev.aykhn.nav_model.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.CoroutineContext

/*
    BaseViewModel.kt has been built for all basic view model functionalities;
    All state, effect hoisting and event processing handled by this base class;
 */

abstract class BaseViewModel<State, Event> : ViewModel() {

    private val mutex = Mutex()
    private val exceptionHandler = CoroutineExceptionHandler(::onError)

    abstract fun provideInitialState(): State

    private val _state = MutableStateFlow(provideInitialState())
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effect = Channel<BaseEffect>(Channel.BUFFERED)
    val effect: Flow<BaseEffect> = _effect.receiveAsFlow()

    //optional override
    open fun onEvent(event: Event) {}

    open fun onError(context: CoroutineContext, throwable: Throwable) {

    }

    protected fun emitState(state: State) {
        launchOnMain {
            mutex.withLock {
                _state.emit(state)
            }
        }
    }

    protected fun emitEffect(effect: BaseEffect) {
        launchOnMain {
            _effect.send(effect)
        }
    }

    protected fun <P, R, U : BaseUseCase<P, R>> executeUseCase(
        useCase: U,
        param: P,
        onComplete: ((R) -> Unit)? = null,
    ) {
        launchOnMain {
            val result = useCase.start(param)
            onComplete?.invoke(result)
        }
    }

    protected fun launchOnMain(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(exceptionHandler, block = block)
    }

    protected fun launchOnIO(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(exceptionHandler + Dispatchers.IO, block = block)
    }

    protected fun launchOnDefault(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(exceptionHandler + Dispatchers.Default, block = block)
    }

    protected fun <T> Flow<T>.launchFlow(scope: CoroutineScope = viewModelScope): Job =
        this.catch {
            exceptionHandler.handleException(currentCoroutineContext(), it)
        }.launchIn(scope)

}