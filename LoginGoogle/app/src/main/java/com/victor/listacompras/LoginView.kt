import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.victor.listacompras.LoginViewModel
import com.victor.listacompras.R
import com.victor.listacompras.ui.theme.ListaComprasTheme

@Composable
fun LoginView(modifier: Modifier = Modifier,
              onLoginSuccess: (Boolean) -> Unit = {},
              startGoogleSignIn: () -> Unit // Adicione o parâmetro startGoogleSignIn
) {

    val viewModel by remember { mutableStateOf(LoginViewModel()) }
    val state = viewModel.state

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(value = state.value.username,
                onValueChange = viewModel::onUsernameChange,
                placeholder = {
                    Text(text = "email")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = state.value.password,
                onValueChange = viewModel::onPasswordChange,
                placeholder = {
                    Text(text = "password")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                // Ação para login normal
                onLoginSuccess(false) // Login normal, redireciona para `home`
            }) {
                Text(text = "Login")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão para login com Google (apenas ícone)
            IconButton(
                onClick = { startGoogleSignIn() },
                modifier = Modifier.size(48.dp) // Ajuste o tamanho do botão
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google), // Substitua com o recurso do ícone
                    contentDescription = "Google Icon",
                    modifier = Modifier.size(50.dp) // Tamanho do ícone
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = state.value.error ?: "")
            if (state.value.isLoading)
                CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    ListaComprasTheme {
        LoginView(
            onLoginSuccess = {}, // Função vazia para preview
            startGoogleSignIn = {} // Função vazia para preview
        )
    }
}
