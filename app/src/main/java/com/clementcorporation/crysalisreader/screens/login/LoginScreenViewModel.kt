package com.clementcorporation.crysalisreader.screens.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clementcorporation.crysalisreader.model.ReaderUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginScreenViewModel: ViewModel(){
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun createUserWithEmailAndPassword(context: Context, email: String, password: String, home: () -> Unit = {}) {
        if (_loading.value == false) {
            _loading.value = true
            try {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener{task ->
                        if (task.isSuccessful) {
                            try {
                                val displayName = task.result?.user?.email?.split("@")?.first()
                                createUser(displayName)
                                home()
                            } catch(e: Exception) {
                                Toast.makeText(context, "Cannot Create User", Toast.LENGTH_SHORT).show()
                            }

                        } else {
                            Log.d("Create User: ", task.result.toString())
                        }
                        _loading.value = false
                    }
            } catch (e: Exception) {
                Toast.makeText(context, "Cannot Create User", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    task -> Log.d("Sign In: ", "SUCCESS")
                    home()
                }
            } catch (e: Exception) {
                e.localizedMessage?.let {
                    Log.d("Sign In: ", it)
                }
            }
        }
    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val readerUser = ReaderUser(
            id = null,
            userId = userId.toString(),
            displayName = displayName!!,
            avatarUrl = "",
            quote = "YOLO",
            profession = "Android Dev"
        ).toMap()
        FirebaseFirestore.getInstance().collection("users").add(readerUser)
    }
}