package com.radwan.products.presentation.productDetails.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.radwan.products.R
import com.radwan.products.domain.model.ProductListing
import com.radwan.products.presentation.productDetails.event.ProductDetailsEvent
import com.radwan.products.presentation.productDetails.state.ProductDetailsState
import com.radwan.products.presentation.productDetails.viewModel.ProductDetailsViewModel
import com.radwan.products.util.collectAsEffect
import com.radwan.products.util.toast
import java.net.URLDecoder


@Composable
fun ProductDetailsScreen(
    navController: NavHostController,
    viewModel: ProductDetailsViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    //region collect flows

    viewModel.error.collectAsEffect {
        context.toast(it)
    }

    viewModel.popToProducts.collectAsEffect {
        navController.popBackStack()
    }
    //endregion

    ProductDetailsContent(
        state = viewModel.state,
        onEvent = { viewModel.onEvent(it) }
    )
}


@Composable
private fun ProductDetailsContent(
    state: ProductDetailsState,
    onEvent: (ProductDetailsEvent) -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 50.dp, horizontal = 16.dp)
    ) {

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize().verticalScroll(scrollState),
        ) {

            IconButton(
                onClick = { onEvent(ProductDetailsEvent.OnBackButtonClicked) }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = stringResource(id = R.string.back),
                    tint = Color.Black
                )
            }


            AsyncImage(
                model = URLDecoder.decode(state.productInfo.thumbnail, "UTF-8") ,
                contentDescription = stringResource(id = R.string.product_image),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .size(200.dp)
                    .clip(CircleShape).background(Color.LightGray)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                text = state.productInfo.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue,
                    textAlign = TextAlign.Center,
                )
            )
            Spacer(modifier = Modifier.width(22.dp))
            Text(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                text = stringResource(
                    R.string.description_with_value,
                    state.productInfo.description
                ),
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Blue,
                    textAlign = TextAlign.Start,
                )
            )
            Text(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                text = stringResource(R.string.brand_with_value, state.productInfo.brand),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Blue,
                    textAlign = TextAlign.Start,
                )
            )
            Text(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                text = stringResource(R.string.category_with_value, state.productInfo.category),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Blue,
                    textAlign = TextAlign.Start,
                )
            )

            Text(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                text = stringResource(R.string.price_with_value, state.productInfo.price),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Blue,
                    textAlign = TextAlign.Start,
                )
            )

            Text(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                text = stringResource(R.string.rating_with_value, state.productInfo.rating),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Blue,
                    textAlign = TextAlign.Start,
                )
            )

        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

}

@Preview
@Composable
fun ProductDetailsPreview(modifier: Modifier = Modifier) {
    ProductDetailsContent(
        state = ProductDetailsState(
            ProductListing(
                title = "Essence Mascara Lash Princess",
                description = "The Essence Mascara Lash Princess is a popular mascara known for its volumizing and lengthening effects. Achieve dramatic lashes with this long-lasting and cruelty-free formula.",
                category = "beauty",
                price = 19.99,
                rating = 3.28,
                brand = "Glamour Beauty"
            )
        ),
        onEvent = {}

    )
}