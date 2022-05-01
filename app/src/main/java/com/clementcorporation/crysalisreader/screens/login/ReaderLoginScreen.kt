package com.clementcorporation.crysalisreader.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.clementcorporation.crysalisreader.components.EmailInput
import com.clementcorporation.crysalisreader.components.PasswordInput
import com.clementcorporation.crysalisreader.components.ReaderLogo

@Composable
fun ReaderLoginScreen(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize().padding(8.dp), color = Color.White) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ReaderLogo()
            UserForm()
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm() {
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
    Surface(modifier = modifier, shape = RoundedCornerShape(16.dp), color = Color.LightGray.copy(alpha = 0.2f)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            EmailInput(emailState = email, enabled = true, onAction = KeyboardActions {
                passwordFocusRequest.requestFocus()
            })
            PasswordInput(passwordState = password, enabled = true, onAction = KeyboardActions {
                passwordFocusRequest.freeFocus()
            })
        }
    }
}