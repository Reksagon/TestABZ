package com.korniienko.testtask.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.korniienko.testtask.R
import com.korniienko.testtask.api.User
import com.korniienko.testtask.screens.UsersScreen

@Composable
fun UserItem(user: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            painter = rememberImagePainter(data = user.photo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .clip(CircleShape)


        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = user.name,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.regular)),
                color = colorResource(id = R.color.black)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = user.position,
                color = Color(0x99000000),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.regular))
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = user.email,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.regular)),
                color = colorResource(id = R.color.black)
            )
            Text(
                text = user.phone,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.regular)),
                color = colorResource(id = R.color.black)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Color.LightGray, thickness = 1.dp) // Роздільник внизу

        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserItemPreview() {
    UserItem(User(1, "Malcolm Bailey", "jany_murazik51@hotmail.com", "+38 (098) 278 76 24", "Frontend developer", "https://frontend-test-assignment-api.abz.agency/images/users/5fa2a65977df010.jpeg"))
}