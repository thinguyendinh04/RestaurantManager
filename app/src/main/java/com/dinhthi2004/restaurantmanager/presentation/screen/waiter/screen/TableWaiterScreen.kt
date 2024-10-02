import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun TableWaiterScreen(navController: NavHostController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Bàn đang sử dung", "Bàn trống", "Bàn đặt")
    Column(modifier = Modifier.fillMaxSize()) {
        androidx.compose.material3.TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.Gray
        ) {
            tabs.forEachIndexed { index, title ->
                androidx.compose.material3.Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        androidx.compose.material3.Text(
                            text = title,
                            color = if (selectedTabIndex == index) Color.Green else Color.White
                        )
                    },
                    modifier = Modifier.padding(bottom = if (selectedTabIndex == index) 2.dp else 0.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTabIndex) {
            0 -> InUseTables()
            1 -> EmptyTables()
            2 -> ReservedTables()
        }
    }
}

@Composable
fun InUseTables() {
    // Add logic to display in-use tables
    Text(text = "Danh sách bàn đang sử dụng")
}

@Composable
fun EmptyTables() {
    // Add logic to display empty tables
    Text(text = "Danh sách bàn trống")
}

@Composable
fun ReservedTables() {
    // Add logic to display reserved tables
    Text(text = "Danh sách bàn đặt")
}

