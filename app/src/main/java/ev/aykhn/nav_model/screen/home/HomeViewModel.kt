package ev.aykhn.nav_model.screen.home

import ev.aykhn.nav_model.base.BaseViewModel
import ev.aykhn.nav_model.base.BasicEffect

class HomeViewModel : BaseViewModel<Unit, MainEvent>() {

    override fun provideInitialState() = Unit

    override fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.TakeMeToChurch -> emitEffect(BasicEffect.NavigateToEffect(route = "/church"))
        }
    }

}

sealed class MainEvent {
    object TakeMeToChurch : MainEvent()
}