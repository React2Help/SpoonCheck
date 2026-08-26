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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.react2help.spooncheck.theme.SpoonCheckCharcoal
import dev.react2help.spooncheck.theme.SpoonCheckMint
import dev.react2help.spooncheck.theme.SpoonCheckNavy
import dev.react2help.spooncheck.theme.SpoonCheckTeal

@Preview
@Composable
fun WelcomeScreen(
    selectedUserType: UserType = UserType.Patient,
    onUserTypeChange: (UserType) -> Unit = {},
    onLoginClick: () -> Unit = {},
    onCreateAccountClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Box(contentAlignment = Alignment.TopStart, modifier = modifier.fillMaxSize()) {
        SpoonCheckBackground()

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(24.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.weight(1f),
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                SpoonCheckTitle(italic = true)

                SpoonCheckLogo()

                Text(
                    text = "Are you a Provider or a Patient?",
                    style = MaterialTheme.typography.titleMedium,
                    color = SpoonCheckNavy,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    UserTypeToggleButton(
                        label = "Provider",
                        selected = selectedUserType == UserType.Provider,
                        onClick = { onUserTypeChange(UserType.Provider) },
                    )
                    UserTypeToggleButton(
                        label = "Patient",
                        selected = selectedUserType == UserType.Patient,
                        onClick = { onUserTypeChange(UserType.Patient) },
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Button(
                    onClick = onLoginClick,
                    shape = RoundedCornerShape(12.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = SpoonCheckCharcoal,
                            contentColor = Color.White,
                        ),
                    modifier = Modifier.weight(1f).height(48.dp),
                ) {
                    Text("Login")
                }

                Button(
                    onClick = onCreateAccountClick,
                    shape = RoundedCornerShape(12.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = SpoonCheckMint,
                            contentColor = SpoonCheckNavy,
                        ),
                    modifier = Modifier.weight(1f).height(48.dp),
                ) {
                    Text("Create Account")
                }
            }
        }
    }
}

@Composable
private fun UserTypeToggleButton(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (selected) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(12.dp),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = SpoonCheckMint,
                    contentColor = SpoonCheckNavy
                ),
            modifier = modifier.size(109.dp, 56.dp),
        ) {
            Text(label, fontWeight = FontWeight.Medium)
        }
    } else {
        OutlinedButton(
            onClick = onClick,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = SpoonCheckTeal),
            modifier = modifier.size(109.dp, 56.dp),
        ) {
            Text(label)
        }
    }
}
