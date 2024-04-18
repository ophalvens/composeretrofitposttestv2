package be.opsteven.composeretrofitposttest.data
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse (
    val code: Long,
    val status: Long,
    val data: List<Persoon>
)

@Serializable
data class Persoon (
    @SerialName("ID")
    val id: Long,

    @SerialName("NAME")
    val name: String
)
