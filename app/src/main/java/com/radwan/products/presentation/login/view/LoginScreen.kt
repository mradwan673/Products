package com.radwan.products.presentation.login.view

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.radwan.products.R
import com.radwan.products.presentation.common.navigation.Routes
import com.radwan.products.presentation.login.event.LoginEvent
import com.radwan.products.presentation.login.state.LoginState
import com.radwan.products.presentation.login.viewModel.LoginViewModel
import com.radwan.products.util.collectAsEffect
import com.radwan.products.util.toast

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    //region collect flows
    viewModel.navigateToProducts.collectAsEffect {
        navController.popBackStack()
        navController.navigate(Routes.ProductsScreen)
    }

    viewModel.error.collectAsEffect {
          context.toast(it)
    }
    //endregion

    LoginContent(
        context = context,
        state = viewModel.state,
        onEvent = { viewModel.onEvent(it) }
    )



}



@Composable
private fun LoginContent(
    context: Context,
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,) {


    Box( modifier = Modifier.fillMaxSize()
        .background(Color.White)
        .padding(16.dp)
        ) {

        val scrollState = rememberScrollState()



        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Text(
                modifier = Modifier.fillMaxWidth()
                .padding(top = 180.dp, bottom = 40.dp, start = 8.dp, end = 8.dp)
                .fillMaxWidth()
                ,text = stringResource(id = R.string.login_screen),
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue,
                    textAlign = TextAlign.Center,
                    )
                )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = state.userName,
                onValueChange = { onEvent(LoginEvent.OnUsernameChanged(it)) },
                label = { Text(stringResource(id = R.string.user_name)) },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.Transparent,
                    backgroundColor = colorResource(R.color.blue_100)
                )
                )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = state.password,
                onValueChange = { onEvent(LoginEvent.OnPasswordChanged(it)) },
                label = { Text(stringResource(id = R.string.password)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                maxLines = 1,
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.Transparent,
                    backgroundColor = colorResource(R.color.blue_100)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth()
                .padding(vertical = 16.dp)
                ,
                contentPadding = PaddingValues(16.dp),
                enabled = state.isLoginBtnEnabled,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue,contentColor = if (state.isLoginBtnEnabled) Color.White else Color.Gray),
                onClick = { onEvent(LoginEvent.OnLoginClicked) })
            {
                Text(text = stringResource(id = R.string.sign_in))
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }




    }
}
@Preview
@Composable
fun LoginPreview(modifier: Modifier = Modifier) {
    LoginContent(
        context = LocalContext.current,
        state = LoginState(),
        onEvent = {}
    )
}