package com.korniienko.testtask.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.korniienko.testtask.screens.vm.RegisterViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.korniienko.testtask.R
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

val emailPatternRFC2822 = Regex(
    "^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$"
)

@Composable
fun SignUpScreen(viewModel: RegisterViewModel = viewModel(), navController: NavController,
                 onRegisterSuccess: () -> Unit,
                 onRegisterError: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var selectedImageName by remember { mutableStateOf("Upload your photo") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var base64Image by remember { mutableStateOf<String?>(null) }
    var showLoadingDialog by remember { mutableStateOf(false) }
    var registerStatus by remember { mutableStateOf<Int?>(null) }

    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(it)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            val fileSize = context.contentResolver.openFileDescriptor(it, "r")?.statSize ?: 0L
            val imageName = it.lastPathSegment ?: "selected_image.jpg"

            if (fileSize > 5 * 1024 * 1024) {
                errorMessage = "Image size must not exceed 5MB."
                base64Image = null
            } else if (bitmap.width < 70 || bitmap.height < 70) {
                errorMessage = "Resolution should be at least 70x70px."
                base64Image = null
            } else {
                // Якщо зображення підходить, перетворюємо його в Base64
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                val imageBytes = byteArrayOutputStream.toByteArray()
                base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT)

                selectedImageName = imageName
                errorMessage = null
            }

        }
    }

    val isNameValid = name.length in 2..60
    val isPhoneValid = phone.startsWith("+380") && phone.length >= 13
    val isPositionSelected = viewModel.selectedPosition != null
    val isEmailValid = emailPatternRFC2822.matches(email)
    val isPhotoSelected = base64Image != null
    val isFormValid = isNameValid && isEmailValid && isPhoneValid && isPositionSelected && isPhotoSelected

    if (showLoadingDialog) {
        LoadingDialog()
    }

    when (registerStatus) {
        201 -> {
            onRegisterSuccess()
        }
        409 -> {
            onRegisterError()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            // Name Field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Your name", fontFamily = FontFamily(Font(R.font.medium))) },
                isError = showError && !isNameValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(66.dp),
            )
            if (showError && !isNameValid) {
                Text("Required field", color = colorResource(id = R.color.red), fontSize = 12.sp, fontFamily = FontFamily(Font(R.font.regular)))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", fontFamily = FontFamily(Font(R.font.medium))) },
                isError = showError && !isEmailValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(66.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
            )
            if (showError && !isEmailValid) {
                Text("Invalid email format", color = colorResource(id = R.color.red), fontSize = 12.sp, fontFamily = FontFamily(Font(R.font.regular)))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Phone Field
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone", fontFamily = FontFamily(Font(R.font.medium))) },
                isError = showError && !isPhoneValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(66.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
            )
            if (showError && !isPhoneValid) {
                Text("Required field", color = colorResource(id = R.color.red), fontSize = 12.sp, fontFamily = FontFamily(Font(R.font.regular)))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text("+38 (XXX) XXX - XX - XX", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(start = 16.dp), fontFamily = FontFamily(Font(R.font.regular)))

            Spacer(modifier = Modifier.height(16.dp))

            // Position Selection
            Text("Select your position", fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.medium)))
            if (viewModel.positions.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            } else {
                Column {
                    viewModel.positions.forEach { position ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            RadioButton(
                                selected = viewModel.selectedPosition == position,
                                onClick = { viewModel.onPositionSelected(position) }
                            )
                            Text(text = position.name, fontSize = 16.sp)
                        }
                    }
                }
            }
            if (showError && !isPositionSelected) {
                Text("Position is required", color = colorResource(id = R.color.red), fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(66.dp)
                    .border(
                        width = 1.dp,
                        color = if (showError && base64Image == null) colorResource(id = R.color.red) else Color.Gray,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable { imagePickerLauncher.launch("image/*") }
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedImageName,
                        color = if (showError && base64Image == null) colorResource(id = R.color.red) else Color.Gray,
                        fontFamily = FontFamily(Font(R.font.medium))
                    )
                    Text(
                        text = "Upload",
                        color = colorResource(id = R.color.blue_secondary),
                        fontFamily = FontFamily(Font(R.font.medium))
                    )
                }
            }



            errorMessage?.let {
                Text(
                    text = it,
                    color = colorResource(id = R.color.red),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
        }

        Button(
            onClick = {
                showError = !isFormValid
                if (isFormValid) {
                    showLoadingDialog = true
                    viewModel.viewModelScope.launch {
                        val response  = viewModel.registerUser(
                            name = name,
                            email = email,
                            phone = phone,
                            positionId = viewModel.selectedPosition?.id ?: 0,
                            base64Image = base64Image!!)
                        showLoadingDialog = false
                        registerStatus = response?.code()
                        Log.e("register", response?.body().toString())
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4E041)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(text = "Sign up", color = Color.Black, fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.medium)))
        }

    }
}

@Composable
fun LoadingDialog() {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "Please wait") },
        text = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        },
        buttons = {}
    )
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(navController = rememberNavController(), onRegisterSuccess = {}, onRegisterError = {})
}