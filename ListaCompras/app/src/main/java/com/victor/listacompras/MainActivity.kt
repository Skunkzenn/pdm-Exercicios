package com.victor.listacompras

import LoginView
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.victor.listacompras.ui.theme.ListaComprasTheme

const val TAG = "myshoppinglist"

class MainActivity : ComponentActivity() {

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private val REQ_ONE_TAP = 2 // Código de requisição para o One Tap
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o oneTapClient e o signInRequest para o Google One Tap
        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

        setContent {
            val navController = rememberNavController()
            ListaComprasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = "login"
                    ) {
                        composable("login") {
                            LoginView(
                                onLoginSuccess = { isGoogleLogin ->
                                    if (isGoogleLogin) {
                                        navController.navigate("profile") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                    } else {
                                        navController.navigate("home") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                    }
                                },
                                startGoogleSignIn = {
                                    startGoogleOneTapSignIn()
                                }
                            )
                        }
                        composable("home") {
                            AddListTypesView()
                        }
                        composable("profile") {
                            ProfileView(userViewModel = userViewModel)
                        }
                    }
                }

                navControllerRef = navController
            }
        }
    }

    // Função para iniciar o Google One Tap Sign-In
    private fun startGoogleOneTapSignIn() {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                try {
                    startIntentSenderForResult(
                        result.pendingIntent.intentSender,
                        REQ_ONE_TAP,
                        null, 0, 0, 0
                    )
                } catch (e: IntentSender.SendIntentException) {
                    Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "One Tap Sign-In failed: ${e.localizedMessage}")
            }
    }

    private fun firebaseAuthWithGoogle(idToken: String, navController: NavController) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    Toast.makeText(this, "Login com Google realizado com sucesso", Toast.LENGTH_SHORT).show()

                    // Obtenha o usuário atual
                    val user = auth.currentUser
                    user?.let {
                        // Capture e armazene os dados do usuário no UserViewModel
                        userViewModel.setUserProfile(it.displayName, it.email, it.photoUrl)

                        // Armazene essas informações em um ViewModel ou passe para a página de perfil
                        navController.navigate("profile") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Falha no login com Google: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }


    // Tratar o resultado do Google One Tap Sign-In
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_ONE_TAP) {
            try {
                val credential = oneTapClient.getSignInCredentialFromIntent(data)
                val idToken = credential.googleIdToken
                if (idToken != null) {
                    Log.d(TAG, "Got ID token.")
                    // Chama a função para autenticar com Firebase e redirecionar para "home"
                    navControllerRef?.let { firebaseAuthWithGoogle(idToken, it) }
                } else {
                    Log.d(TAG, "No ID token!")
                }
            } catch (e: ApiException) {
                Log.e(TAG, "Google sign-in failed: ${e.localizedMessage}")
            }
        }
    }

    companion object {
        private var navControllerRef: NavController? = null
    }
}
