package com.korniienko.testtask.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.korniienko.testtask.R

@Composable
fun RegisterStatusScreen(
    onRetry: () -> Unit,
    success: Boolean
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val imageRes = if (success) R.drawable.ic_register_success else R.drawable.ic_register_fail
            val message = if (success) "User successfully registered" else "That email is already registered"
            val buttonText = if (success) "Got it" else "Try again"

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                color = Color.Black,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.medium))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4E041))
            ) {
                Text(text = buttonText, color = Color.Black, fontFamily = FontFamily(Font(R.font.medium)))
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun RegisterStatusScreenPreview() {
    RegisterStatusScreen({ }, success = false)
}