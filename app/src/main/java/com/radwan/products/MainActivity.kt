package com.radwan.products

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.radwan.products.domain.model.ProductListing
import com.radwan.products.presentation.common.navigation.Routes
import com.radwan.products.presentation.login.view.LoginScreen
import com.radwan.products.presentation.productDetails.view.ProductDetailsScreen
import com.radwan.products.presentation.products.view.ProductsScreen
import com.radwan.products.ui.theme.ProductsTheme
import com.radwan.products.util.CustomNavType
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.typeOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductsTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.LoginScreen) {

                    composable<Routes.LoginScreen> {
                        LoginScreen(navController)
                    }

                    composable<Routes.ProductsScreen> {
                        ProductsScreen(navController)
                    }

                    composable<Routes.ProductsDetailsScreen>(
                        typeMap = mapOf(
                            typeOf<ProductListing>() to CustomNavType(
                                ProductListing::class.java,
                                ProductListing.serializer()
                            )
                        )
                    ) {
                        ProductDetailsScreen(navController)
                    }


                }

            }
        }
    }
}