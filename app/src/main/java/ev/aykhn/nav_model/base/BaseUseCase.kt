package ev.aykhn.nav_model.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class BaseUseCase<in Params, out Result> {

    protected abstract suspend fun execute(params: Params): Result

    suspend fun start(params: Params, context: CoroutineContext = Dispatchers.IO): Result {
        return withContext(context) {
            execute(params)
        }
    }

}