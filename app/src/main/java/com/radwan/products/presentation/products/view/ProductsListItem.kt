package com.radwan.products.presentation.products.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.radwan.products.R
import com.radwan.products.domain.model.ProductListing


@Composable
fun productListItem(product: ProductListing, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .border(1.dp, Color.Blue, shape = RoundedCornerShape(8.dp))
            .background(
                colorResource(R.color.blue_100),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(  12.dp)
            , verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = product.thumbnail,

            contentDescription = stringResource(id = R.string.product_image),
            modifier = Modifier
                .size(76.dp)
                .clip(CircleShape).background(Color.LightGray)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = product.title,
                color = Color.Black,
                fontSize = 22.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
            )

            Text(
                text = product.description,
                color = Color.Black,
                fontSize = 14.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Start,

            )
        }


    }
}


@Preview()
@Composable
fun productListItemPreview(modifier: Modifier = Modifier) {
    productListItem(product = ProductListing(title = "Essence Mascara Lash Princess", description = "The Essence Mascara Lash Princess is a popular mascara known for its volumizing and lengthening effects. Achieve dramatic lashes with this long-lasting and cruelty-free formula.", category = "https://cdn.dummyjson.com/products/images/beauty/Essence%20Mascara%20Lash%20Princess/thumbnail.png"))
}