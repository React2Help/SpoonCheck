package dev.react2help.spooncheck.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.react2help.spooncheck.theme.SpoonCheckFieldBackground
import dev.react2help.spooncheck.theme.SpoonCheckNavy
import dev.react2help.spooncheck.theme.SpoonCheckTeal
import org.jetbrains.compose.resources.painterResource
import spooncheck.shared.generated.resources.Res
import spooncheck.shared.generated.resources.logo
import spooncheck.shared.generated.resources.welcome_screen_background

@Composable
fun SpoonCheckBackground(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(Res.drawable.welcome_screen_background),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier.fillMaxSize(),
    )
}

@Composable
fun SpoonCheckLogo(
    modifier: Modifier = Modifier,
    sizeDp: Int = 183,
) {
    Image(
        painter = painterResource(Res.drawable.logo),
        contentDescription = "SpoonCheck logo",
        contentScale = ContentScale.Crop,
        modifier = modifier.size(sizeDp.dp).clip(CircleShape),
    )
}

@Composable
fun SpoonCheckTitle(
    text: String = "Welcome To SpoonCheck",
    italic: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontStyle = if (italic) FontStyle.Italic else FontStyle.Normal,
        fontWeight = if (italic) FontWeight.Normal else FontWeight.Medium,
        color = Color.Black,
    )
}

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        singleLine = true,
        visualTransformation =
            if (isPassword) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors =
            TextFieldDefaults.colors(
                focusedContainerColor = SpoonCheckFieldBackground,
                unfocusedContainerColor = SpoonCheckFieldBackground,
                disabledContainerColor = SpoonCheckFieldBackground,
                focusedIndicatorColor = SpoonCheckNavy,
                unfocusedIndicatorColor = SpoonCheckNavy,
                cursorColor = SpoonCheckTeal,
            ),
        modifier = modifier.fillMaxWidth(),
    )
}
