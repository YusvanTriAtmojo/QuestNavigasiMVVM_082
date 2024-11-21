package com.example.application5

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.application5.model.DataJenisKelamin
import com.example.application5.ui.view.DetailMahasiswaView
import com.example.application5.ui.view.FormMahasiswaView
import com.example.application5.ui.viewmodel.MahasiswaViewModel

enum class Halaman{
    Formulir,
    Detail,
}

@Composable
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    viewModel: MahasiswaViewModel = viewModel(),
    navHost: NavHostController = rememberNavController()
){
    val stateUI by viewModel.uiState.collectAsState()
    NavHost(modifier = modifier.padding(8.dp),
        navController = navHost, startDestination = Halaman.Formulir.name) {
        composable(route = Halaman.Formulir.name) {
            val konteks = LocalContext.current
            FormMahasiswaView(
                listJK =  DataJenisKelamin.listJK.map {
                    isi -> konteks.resources.getString(isi)
                },
                onSubmitClicked = {
                    viewModel.saveDataMahasiswa(it)
                    navHost.navigate(Halaman.Detail.name)
                }
            )
        }
        composable(route = Halaman.Detail.name){
            DetailMahasiswaView(
                uiStateMahasiswa = stateUI,
                onClickButton = {
                    navHost.popBackStack()
                }
            )
        }
    }

}