import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.model.dish.Dish

@Composable
fun MenuItemCard(
    dish: Dish,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onUpdateClick: (Int) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        color = Color.LightGray,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img2),
                    contentDescription = "Meal image",
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Tên: ${dish.name}",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
                    )
                    Text(
                        text = "Giá: ${dish.price} VND",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
                    )
                }
            }

            Column(

            ) {
                IconButton(
                    onClick = {
                        dish.id?.let { onUpdateClick(it) }
                    },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Delete Meal",
                        tint = Color.Black
                    )
                }

                Spacer(Modifier.height(10.dp))
                IconButton(
                    onClick = {
                        onDeleteClick()
                    },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Meal",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}
