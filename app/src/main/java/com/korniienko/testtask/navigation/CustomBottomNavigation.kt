package com.korniienko.testtask.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.korniienko.testtask.R

@Composable
fun CustomBottomNavigation(
    items: List<BottomNavItem>,
    selectedRoute: String,
    onItemSelected: (BottomNavItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8F8F8))
            .padding(vertical = 24.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val isSelected = item.route == selectedRoute
            Row(
                modifier = Modifier
                    .clickable { onItemSelected(item) }
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = item.iconResId),
                    contentDescription = item.label,
                    tint = if (isSelected) colorResource(id = R.color.blue) else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = item.label,
                    color = if (isSelected) colorResource(id = R.color.blue) else Color.Gray,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.regular))
                )
            }
        }
    }
}

data class BottomNavItem(
    val route: String,
    val iconResId: Int,
    val label: String
)

