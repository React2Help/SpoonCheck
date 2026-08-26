package dev.react2help.spooncheck.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.react2help.spooncheck.theme.SpoonCheckCharcoal

@Preview
@Composable
fun CreateAccountScreen(
    userType: UserType = UserType.Patient,
    onSubmit: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var verifyPassword by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    val canSubmit =
        email.isNotBlank() &&
            password.isNotBlank() &&
            verifyPassword.isNotBlank() &&
            firstName.isNotBlank() &&
            lastName.isNotBlank() &&
            age.isNotBlank() &&
            gender.isNotBlank() &&
            password == verifyPassword

    Box(modifier = modifier.fillMaxSize()) {
        SpoonCheckBackground()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier =
                Modifier.fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 32.dp),
        ) {
            Text(
                text = "Create An Account",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            AuthTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Email",
                keyboardType = KeyboardType.Email,
            )

            AuthTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Password",
                isPassword = true,
            )

            AuthTextField(
                value = verifyPassword,
                onValueChange = { verifyPassword = it },
                placeholder = "Verify Password",
                isPassword = true,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                AuthTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    placeholder = "First Name",
                    modifier = Modifier.weight(1f),
                )
                AuthTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    placeholder = "Last Name",
                    modifier = Modifier.weight(1f),
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                AuthTextField(
                    value = age,
                    onValueChange = { age = it },
                    placeholder = "Age",
                    keyboardType = KeyboardType.Number,
                    modifier = Modifier.weight(1f),
                )
                AuthTextField(
                    value = gender,
                    onValueChange = { gender = it },
                    placeholder = "Gender",
                    modifier = Modifier.weight(1f),
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onSubmit,
                enabled = canSubmit,
                shape = RoundedCornerShape(12.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = SpoonCheckCharcoal,
                        contentColor = androidx.compose.ui.graphics.Color.White,
                    ),
                modifier = Modifier.fillMaxWidth().height(48.dp),
            ) {
                Text("Submit")
            }
        }
    }
}
