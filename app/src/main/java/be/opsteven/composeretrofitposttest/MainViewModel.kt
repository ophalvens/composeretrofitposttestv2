package be.opsteven.composeretrofitposttest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.opsteven.composeretrofitposttest.data.Product
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

    private val _producten = MutableStateFlow<MutableList<Product>>(mutableListOf())
    val producten = _producten.asStateFlow()




    fun getProducten(){
        _getProducts()
    }

    /**
     * Haal de producten uit de lijst van de API
     * Opgelet : aangezien deze API verspreid is, is het mogelijk dat andere studenten de
     * producten verwijderd hebben. In dat geval voeg je best nieuwe producten toe als je met
     * hetzelfde endpoint werkt.
     */
    private fun _getProducts() {
        viewModelScope.launch {
            val productenResponse = MyApi.retroFitService.getProducten()

            // verwijder alle vorige Producten en zet de nieuwe in _producten
            _producten.value.clear()
            _producten.value.addAll(productenResponse.data)

            _responseState.update { currentState ->
                currentState.copy(
                    productenResponse = productenResponse.data.size.toString(),
                    output = productenResponse.toString()
                )
            }



        }
    }

    fun login() {
        _login()
    }


    private fun _login() {
         viewModelScope.launch {
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