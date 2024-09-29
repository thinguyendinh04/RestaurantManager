package com.dinhthi2004.restaurantmanager.presentation.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.TableBar
import androidx.compose.material.icons.rounded.Assessment
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.People
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import  androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.HomeEmployee
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.HomeIngredients
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.HomeManager
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.HomeOrder
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.HomeRevenue
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.HomeSetting
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.HomeTable
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.Setting
import com.dinhthi2004.restaurantmanager.uilts.Route
import kotlinx.coroutines.launch

@Composable
fun BottomNavigation(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        MyBottomAppBar(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomAppBar(navController: NavHostController) {

    val navigationController = rememberNavController()
    val coroutineScope= rememberCoroutineScope()
    val drawerState= rememberDrawerState(initialValue = DrawerValue.Closed)
    val context= LocalContext.current.applicationContext
    val selected = remember {
        mutableStateOf(Icons.Default.Home)
    }
    val selectedItem = remember { mutableStateOf(Route.HomeManager.screen) }
    ModalNavigationDrawer(
        drawerState=drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Box(modifier = Modifier.background(Color.White).fillMaxWidth().height(100.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.man),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth(0.25f)
                                .clip(CircleShape)

                        )


                        Column(
                            modifier = Modifier
                                .padding(start = 7.dp)
                                .offset(y = (-3).dp)
                        ) {
                            Text(
                                text = "Nguyễn Văn A",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Manager",
                                fontSize = 20.sp,
                            )
                        }
                    }
                }
                    Divider()
                    NavigationDrawerItem(label = { Text(text = "Trang chủ",  color = if (selectedItem.value == Route.HomeManager.screen) Color(0xff2ACCCF) else Color(0xff565e6c))},
                        icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "", tint = if (selectedItem.value == Route.HomeManager.screen) Color(0xff2ACCCF) else Color(0xff565e6c)) },
                        selected = false,
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            selectedItem.value = Route.HomeManager.screen
                            navigationController.navigate(Route.HomeManager.screen) {
                                popUpTo(0)
                            }
                        })
                    NavigationDrawerItem(label = { Text(
                            text = "Quản lí Bàn Ăn",
                        color = if (selectedItem.value == Route.HomeTable.screen) Color(0xff2ACCCF) else Color(0xff565e6c))

                    },

                        icon = { Icon(imageVector = Icons.Default.TableBar, contentDescription = "",
                            tint = if (selectedItem.value == Route.HomeTable.screen) Color(0xff2ACCCF) else Color(0xff565e6c)) },
                        selected = false,
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            selectedItem.value = Route.HomeTable.screen
                            navigationController.navigate(Route.HomeTable.screen) {
                                popUpTo(0)
                            }
                        })
                    NavigationDrawerItem(label = {
                        Text(
                            text = "Quản lí đơn hàng",
                            color = if (selectedItem.value == Route.HomeOrder.screen) Color(0xff2ACCCF) else Color(0xff565e6c))

                    },

                        icon = { Icon(imageVector = Icons.Default.ShoppingBag, contentDescription = "",
                            tint = if (selectedItem.value == Route.HomeOrder.screen) Color(0xff2ACCCF) else Color(0xff565e6c)) },
                        selected = false,
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            selectedItem.value = Route.HomeOrder.screen
                            navigationController.navigate(Route.HomeOrder.screen) {
                                popUpTo(0)
                            }
                        })
                    NavigationDrawerItem(label = {
                        Text(
                            text = "Quản lí nguyên liệu",
                            color = if (selectedItem.value == Route.HomeIngredients.screen) Color(0xff2ACCCF) else Color(0xff565e6c))

                    },
                        icon = { Icon(imageVector = Icons.Default.Restaurant, contentDescription = "",
                            tint = if (selectedItem.value == Route.HomeIngredients.screen) Color(0xff2ACCCF) else Color(0xff565e6c)) },
                        selected = false,
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            selectedItem.value = Route.HomeIngredients.screen
                            navigationController.navigate(Route.HomeIngredients.screen) {
                                popUpTo(0)
                            }
                        })
                    NavigationDrawerItem(label = { Text(text = "Cài đặt", color = if (selectedItem.value == Route.HomeSetting.screen) Color(0xff2ACCCF) else Color(0xff565e6c)) },

                        icon = { Icon(imageVector = Icons.Default.Settings, contentDescription = "",tint = if (selectedItem.value == Route.HomeSetting.screen) Color(0xff2ACCCF) else Color(0xff565e6c)) },
                        selected = false,
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            selectedItem.value = Route.HomeSetting.screen
                            navigationController.navigate(Route.HomeSetting.screen) {
                                popUpTo(0)
                            }
                        })
                    NavigationDrawerItem(label = { Text(text = "Log Out",
                        color = if (selectedItem.value == Route.HomeManager.screen) Color.Red else Color(0xff565e6c)) },

                        icon = { Icon(imageVector = Icons.Default.Logout, contentDescription = "",
                            tint = if (selectedItem.value == Route.HomeManager.screen) Color.Red else Color(0xff565e6c)) },
                        selected = false,
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            selectedItem.value = Route.HomeManager.screen
                            navigationController.navigate(Route.HomeManager.screen) {
                                popUpTo(0)
                            }
                        })


                }


        }
    ) {




        Scaffold(
            topBar = {
                val coroutineScope= rememberCoroutineScope()
                Column(Modifier.fillMaxWidth()) {
                    TopAppBar(
                        title = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.img),
                                    contentDescription = "",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .fillMaxWidth(0.15f)
                                        .clip(CircleShape)
                                        .border(1.dp, Color.Black, CircleShape)
                                )


                                Column(
                                    modifier = Modifier
                                        .padding(start = 7.dp)
                                        .offset(y = (-3).dp)
                                ) {
                                    Text(
                                        text = "Nguyễn Văn A",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Chào mừng bạn đến với nhà hàng",
                                        fontSize = 12.sp,
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                IconButton(
                                    onClick = {
                                   coroutineScope.launch {
                                       drawerState.open()
                                   }

                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(bottom = 16.dp)
                                ) {
                                    Icon(
                                        Icons.Rounded.Menu,
                                        contentDescription = "",
                                        modifier = Modifier.size(24.dp),
                                        tint = if (selected.value == Icons.Rounded.Menu) Color(
                                            0xff2ACCCF
                                        ) else Color.Black

                                    )
                                }

                            }


                        },

                        )
                    if ((selected.value != Icons.Default.Home)) {
                        Divider(thickness = 2.dp, color = Color(0xffBCC1CA))
                    }


                }

            },


            bottomBar = {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Divider(thickness = 2.dp, color = Color(0xffBCC1CA))

                    BottomAppBar(
                        containerColor = Color.White
                    ) {
                        IconButton(
                            onClick = {
                                selected.value = Icons.Default.Home
                                navigationController.navigate(Route.HomeManager.screen) {
                                    popUpTo(0)
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 16.dp)
                        )
                        {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally

                            ) {
                                Icon(
                                    Icons.Default.Home,
                                    contentDescription = "",
                                    modifier = Modifier.size(20.dp),
                                    tint = if (selected.value == Icons.Default.Home) Color(
                                        0xff2ACCCF
                                    ) else Color(0xff565e6c)
                                )
                                Text(
                                    text = "Trang chủ",
                                    color = if (selected.value == Icons.Default.Home) Color(
                                        0xff2ACCCF
                                    ) else Color(0xff565e6c)
                                )
                            }


                        }

                        IconButton(
                            onClick = {
                                selected.value = Icons.Rounded.People
                                navigationController.navigate(Route.HomeEmployee.screen) {
                                    popUpTo(0)
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 16.dp)
                        )
                        {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally

                            ) {
                                Icon(
                                    Icons.Rounded.People,
                                    contentDescription = "",
                                    modifier = Modifier.size(20.dp),
                                    tint = if (selected.value == Icons.Rounded.People) Color(
                                        0xff2ACCCF
                                    ) else Color(0xff565e6c)

                                )
                                Text(
                                    text = "QLNV",
                                    color = if (selected.value == Icons.Rounded.People) Color(
                                        0xff2ACCCF
                                    ) else Color(0xff565e6c)
                                )
                            }

                        }


                        IconButton(
                            onClick = {
                                selected.value = Icons.Rounded.Assessment
                                navigationController.navigate(Route.HomeRevenue.screen) {
                                    popUpTo(0)
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 16.dp)
                        )
                        {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally

                            ) {
                                Icon(
                                    Icons.Rounded.Assessment,
                                    contentDescription = "",
                                    modifier = Modifier.size(20.dp),
                                    tint = if (selected.value == Icons.Rounded.Assessment) Color(
                                        0xff2ACCCF
                                    ) else Color(0xff565e6c)

                                )
                                Text(
                                    text = "Thống kê",
                                    color = if (selected.value == Icons.Rounded.Assessment) Color(
                                        0xff2ACCCF
                                    ) else Color(0xff565e6c)
                                )
                            }

                        }



                        IconButton(
                            onClick = {
                                selected.value = Icons.Default.Settings
                                navigationController.navigate(Route.HomeSetting.screen) {
                                    popUpTo(0)
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 16.dp)
                        )
                        {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally

                            ) {
                                Icon(
                                    Icons.Default.Settings,
                                    contentDescription = "",
                                    modifier = Modifier.size(20.dp),
                                    tint = if (selected.value == Icons.Default.Settings) Color(
                                        0xff2ACCCF
                                    ) else Color(0xff565e6c)
                                )
                                Text(
                                    text = "Cài đặt",
                                    color = if (selected.value == Icons.Default.Settings) Color(
                                        0xff2ACCCF
                                    ) else Color(0xff565e6c)
                                )
                            }

                        }


                    }
                }

            }
        )

        { paddingValues ->


            NavHost(
                navController = navigationController,
                startDestination = Route.HomeManager.screen,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Route.HomeManager.screen) {
                    HomeManager(navigationController)
                }
                composable(Route.HomeEmployee.screen) {
                    HomeEmployee(navigationController)
                }
                composable(Route.HomeTable.screen) {
                    HomeTable(navigationController)
                }
                composable(Route.HomeOrder.screen) {
                    HomeOrder(navigationController)
                }
                composable(Route.HomeIngredients.screen) {
                    HomeIngredients(navigationController)
                }
                composable(Route.HomeRevenue.screen) {
                    HomeRevenue(navigationController)
                }
                composable(Route.HomeSetting.screen) {
                    HomeSetting(navigationController)
                }
                composable(Route.Setting.screen) {
                    Setting(navigationController)
                }
            }
        }
    }
}