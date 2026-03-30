package be.opsteven.composeretrofitposttest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.opsteven.composeretrofitposttest.data.ProductenUIState
import be.opsteven.composeretrofitposttest.data.ResponseState
import be.opsteven.composeretrofitposttest.data.User
import be.opsteven.composeretrofitposttest.network.MyApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    // State, simple as Strings in ResponseState
    private val _responseState = MutableStateFlow(ResponseState(
        loginResponse = "",
        productenResponse = "",
        output = "Klik op 1 van de knoppen om te testen")
    )
    val responseState: StateFlow<ResponseState> = _responseState.asStateFlow()

    private val _productenUIState = MutableStateFlow(ProductenUIState())
    val productenUIState = _productenUIState.asStateFlow()


    /**
     * Haal de producten uit de lijst van de API
     * Opgelet : aangezien deze API verspreid is, is het mogelijk dat andere studenten de
     * producten verwijderd hebben. In dat geval voeg je best nieuwe producten toe als je met
     * hetzelfde endpoint werkt.
     */
    fun getProducten() {
        viewModelScope.launch {
            _productenUIState.update {
                it.copy(
                    data = listOf() // niet nodig, maar doen we om iets visueel te testen bij het laden
                )
            }
            try {
                val productenResponse = MyApi.retroFitService.getProducten()

                if(productenResponse.status in 200..299 && !productenResponse.data.isEmpty()) {
                    // zet de nieuwe Producten in _productenUIState
                    _productenUIState.update {
                        it.copy(
                            data = productenResponse.data
                        )
                    }

                    // de output aanpassen (voor het bovenste deel)
                    _responseState.update { currentState ->
                        currentState.copy(
                            productenResponse = productenResponse.data.size.toString(),
                            output = productenResponse.toString()
                        )
                    }
                }
            } catch(e: Exception) {
                Log.e("getProducten", e.toString())
            }


        }
    }

    fun login() {
         viewModelScope.launch {
             // voeg hier je eigen user toe
             val myUser = User("test", "test")
             val loginResult = MyApi.retroFitService.login(myUser)

             _responseState.update { currentState ->
                 currentState.copy(
                     productenResponse = loginResult.toString(),
                     output = loginResult.toString()
                 )
             }
        }
     }

}