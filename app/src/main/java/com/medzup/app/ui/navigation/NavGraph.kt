package com.medzup.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.medzup.app.ui.screens.add_medicine.AddMedicineScreen
import com.medzup.app.ui.screens.camera.CameraScreen
import com.medzup.app.ui.screens.details.MedicineDetailsScreen
import com.medzup.app.ui.screens.home.HomeScreen

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object AddMedicine : Screen("add_medicine_screen")
    object MedicineDetails : Screen("medicine_details_screen/{medicineId}") {
        fun createRoute(medicineId: Long) = "medicine_details_screen/$medicineId"
    }
    object CameraScan : Screen("camera_scan_screen/{scanMode}") {
        fun createRoute(scanMode: String) = "camera_scan_screen/$scanMode"
    }
}

@Composable
fun MainNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.AddMedicine.route) {
            AddMedicineScreen(navController = navController)
        }
        composable(
            route = Screen.MedicineDetails.route,
            arguments = listOf(navArgument("medicineId") { type = NavType.LongType })
        ) {
            MedicineDetailsScreen(navController = navController)
        }
        composable(
            route = Screen.CameraScan.route,
            arguments = listOf(navArgument("scanMode") { type = NavType.StringType })
        ) {
            CameraScreen(navController = navController)
        }
    }
} 