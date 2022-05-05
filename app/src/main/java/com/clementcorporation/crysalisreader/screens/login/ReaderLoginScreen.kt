package com.clementcorporation.crysalisreader.screens.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.clementcorporation.crysalisreader.R
import com.clementcorporation.crysalisreader.components.EmailInput
import com.clementcorporation.crysalisreader.components.PasswordInput
import com.clementcorporation.crysalisreader.components.ReaderLogo
import com.clementcorporation.crysalisreader.components.SubmitButton
import com.clementcorporation.crysalisreader.navigation.ReaderScreens

@Composable
fun ReaderLoginScreen(navController: NavController, viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val showLoginForm = rememberSaveable {
        mutableStateOf(true)
    }
    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp), color = Color.White) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ReaderLogo()
            if (showLoginForm.value) {
                UserForm {email, password ->
                    viewModel.signInWithEmailAndPassword(email, password) {
                        navController.navigate(ReaderScreens.HomeScreen.name)
                    }
                }
            } else {
                UserForm(loading = false, isCreateAccount = true) { email, password ->
                    viewModel.createUserWithEmailAndPassword(navController.context, email, password) {
                        navController.navigate(ReaderScreens.HomeScreen.name)
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                val prefaceText = if (showLoginForm.value) "New User?" else "Already Signed Up?"
                val linkedText = if (showLoginForm.value) "Sign Up" else "Login"
                Text(text = prefaceText, color = Color.Gray)
                Text(
                    text = linkedText,
                    modifier = Modifier
                        .clickable {
                            showLoginForm.value = !showLoginForm.value
                        }
                        .padding(start = 5.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = {email, password ->}
) {
    val email = rememberSaveable {
        mutableStateOf("")
    }
    val password = rememberSaveable {
        mutableStateOf("")
    }
    val passwordVisibility = rememberSaveable {
        mutableStateOf(false)
    }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val modifier = Modifier
        .padding(8.dp)
        .height(250.dp)
        .verticalScroll(rememberScrollState())
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = Color.LightGray.copy(alpha = 0.2f)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (isCreateAccount) {
                Text(
                    text = stringResource(id = R.string.create_account),
                    modifier = Modifier.padding(6.dp),
                    fontSize = 12.sp
                )
            }
            EmailInput(
                emailState = email,
                enabled = !loading,
//                onAction = KeyboardActions {
//                    passwordFocusRequest.requestFocus()
//                }
            )
            PasswordInput(
                modifier = Modifier.focusRequester(passwordFocusRequest),
                passwordState = password,
                passwordVisibility = passwordVisibility,
                enabled = !loading,
                onAction = KeyboardActions {
                    if (!valid) return@KeyboardActions else {
                        onDone(email.value.trim(), password.value.trim())
                    }
                }
            )
            SubmitButton(
                textId = if (isCreateAccount) "Create Account" else "Submit",
                loading = loading,
                validInputs = valid
            ) {
                onDone(email.value.trim(), email.value.trim())
                keyboardController?.hide()
            }
        }
    }
}