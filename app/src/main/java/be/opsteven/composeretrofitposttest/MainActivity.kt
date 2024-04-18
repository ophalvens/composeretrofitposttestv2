package be.opsteven.composeretrofitposttest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.opsteven.composeretrofitposttest.ui.theme.ComposeRetrofitPostTestTheme

import androidx.lifecycle.viewmodel.compose.viewModel
import be.opsteven.composeretrofitposttest.data.Product

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeRetrofitPostTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TestPostApp()
                }
            }
        }
    }
}

@Composable
fun ButtonsRow(
    onLoginClicked : ()-> Unit,
    onGetProductenClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    ) {
        Button(
            onClick = onLoginClicked,
            elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp, pressedElevation = 2.dp),
            modifier = Modifier.weight(1f)
        ){
            Text(stringResource(R.string.test_login))
        }

        Spacer( modifier = Modifier.weight(0.2f) )

        Button(
            onClick = onGetProductenClicked,
            elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp, pressedElevation = 2.dp),
            modifier = Modifier.weight(1f)
        ){
            Text(stringResource(R.string.get_producten))
        }
    }
}

@Composable
fun OutputArea(response: String, modifier: Modifier = Modifier) {

    Text(
        text = response,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    )
}

@Composable
fun TestPostApp(
    viewModel: MainViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val responseState by viewModel.responseState.collectAsState()
    val producten by viewModel.producten.collectAsState()

    Column(){
        OutputArea(
            response = responseState.output,
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        )

        ProductCardList(producten = producten, paddingValues = PaddingValues(all = 8.dp), modifier = Modifier.weight(5f))

        ButtonsRow(
            onLoginClicked = { viewModel.login() },
            onGetProductenClicked = { viewModel.getProducten() } ,
            Modifier.weight(1f)
        )
    }
}

@Composable
fun ProductCardList(producten: List<Product> ,paddingValues: PaddingValues, modifier: Modifier = Modifier){
    LazyColumn(
        contentPadding = paddingValues,
        modifier = Modifier.fillMaxHeight(0.5f)

    ) {
        items(producten) {
            ProductCard(
                product = it,
                modifier = Modifier.padding(8.dp)
            )
        }

    }

}

@Preview
@Composable
fun ProductCardListPreview() {
    ComposeRetrofitPostTestTheme {
        ProductCardList(
            paddingValues = PaddingValues(),
            producten = listOf(
                Product(id=1, categorieId = 1, naam = "Appel", prijs = 2.5f, categorie = "Fruit"),
                Product(id=2, categorieId = 2, naam = "Kwamkwammer", prijs = 3.5f, categorie = "Groenten"),
        ))
    }
}



@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier
){
    Row(modifier = Modifier.fillMaxWidth()){
        Text(text = product.categorie, modifier = Modifier
            .weight(2f)
            .height(24.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(start=8.dp, end = 8.dp)
        )
        Text(text = product.naam, modifier = Modifier
            .weight(4f)
            .height(24.dp)
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(start=8.dp, end = 8.dp)
        )
        Text(text = product.prijs.toString(), modifier = Modifier
            .weight(1f)
            .height(24.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(start=8.dp, end = 8.dp)
        )
    }
}




@Preview
@Composable
fun ProductCardPreview() {
    ComposeRetrofitPostTestTheme {
        ProductCard(Product(id=1, categorieId = 1, naam = "Appel", prijs = 2.5f, categorie = "Fruit"))
    }
}




@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AppPreview() {
    ComposeRetrofitPostTestTheme {
        TestPostApp()
    }
}