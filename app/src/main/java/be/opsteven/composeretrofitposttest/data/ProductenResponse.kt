package be.opsteven.composeretrofitposttest.data
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductenResponse (
    val code: Long,
    val status: Long,
    val data: List<Product>
)

@Serializable
data class Product (
    @SerialName("PR_ID")
    val id: Long,

    @SerialName("PR_CT_ID")
    val categorieId: Long,

    @SerialName("PR_naam")
    val naam: String,

    @SerialName("PR_prijs")
    val prijs: Float,

    @SerialName("CT_OM")
    val categorie: String
)
