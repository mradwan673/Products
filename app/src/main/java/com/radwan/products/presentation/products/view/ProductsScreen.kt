package com.radwan.products.presentation.products.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.radwan.products.R
import com.radwan.products.data.mapper.toProductListing
import com.radwan.products.presentation.common.navigation.Routes
import com.radwan.products.presentation.companies.viewModel.ProductsViewModel
import com.radwan.products.presentation.products.event.ProductsEvent
import com.radwan.products.presentation.products.state.ProductsState
import com.radwan.products.util.collectAsEffect
import com.radwan.products.util.toast


@Composable
fun ProductsScreen(
    navController: NavHostController,
    viewModel: ProductsViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    //region collect flows
    viewModel.navigateToProductDetails.collectAsEffect {
        navController.navigate(Routes.ProductsDetailsScreen(it.toProductListing()))
    }

    viewModel.error.collectAsEffect {
        context.toast(it)
    }
    //endregion

    ProductsContent(
        state = viewModel.state,
        onEvent = { viewModel.onEvent(it) }
    )

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ProductsContent(
    state: ProductsState,
    onEvent: (ProductsEvent) -> Unit,
) {

    val refreshState =
        rememberPullRefreshState(
            refreshing = state.isRefreshing,
            onRefresh = { onEvent(ProductsEvent.Refresh) }
        )

    Box(
        modifier = Modifier
            .pullRefresh(refreshState)
            .fillMaxSize()
            .padding(vertical = 50.dp, horizontal = 16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Text(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal =  16.dp, vertical = 8.dp)
                    .fillMaxWidth()
                ,text = stringResource(id = R.string.welcome),
                style = TextStyle(
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue,
                    textAlign = TextAlign.Start,
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.products.size) { i ->
                    productListItem(product = state.products[i],
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onEvent(ProductsEvent.OnItemClicked(i))
                            })
                    if (i < state.products.size) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

        }

        PullRefreshIndicator(
            refreshing = state.isRefreshing,
            state = refreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

}

@Preview
@Composable
fun ProductsPreview(modifier: Modifier = Modifier) {
    ProductsContent(
        state = ProductsState(),
        onEvent = {}
    )
}