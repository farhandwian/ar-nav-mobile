package polinema.ac.id.pinar_app_astar.di

import polinema.ac.id.pinar_app_astar.data.repository.AuthRepositoryImpl
import polinema.ac.id.pinar_app_astar.data.repository.BuildingRepositoryImpl
import polinema.ac.id.pinar_app_astar.data.repository.PathNodeRepositoryImpl
import polinema.ac.id.pinar_app_astar.data.repository.ReportRepositoryImpl
import polinema.ac.id.pinar_app_astar.domain.repository.AuthRepository
import polinema.ac.id.pinar_app_astar.domain.repository.BuildingRepository
import polinema.ac.id.pinar_app_astar.domain.repository.PathNodeRepository
import polinema.ac.id.pinar_app_astar.domain.repository.ReportRepository
import polinema.ac.id.pinar_app_astar.presentation.detail.add.AddSceneViewModel
import polinema.ac.id.pinar_app_astar.presentation.home.HomeViewModel
import polinema.ac.id.pinar_app_astar.presentation.home.add.HomeFormViewModel
import polinema.ac.id.pinar_app_astar.presentation.maps.MapsViewModel
import polinema.ac.id.pinar_app_astar.presentation.scan.ScanViewModel
import polinema.ac.id.pinar_app_astar.presentation.scene.SceneViewModel
import polinema.ac.id.pinar_app_astar.presentation.sharedviewmodel.NavigationViewModel
import polinema.ac.id.pinar_app_astar.presentation.tracktest.TrackTestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    // Single instance of Repository
    single<AuthRepository> { AuthRepositoryImpl() }
    single<BuildingRepository> { BuildingRepositoryImpl() }
    single<PathNodeRepository> { PathNodeRepositoryImpl() }

    // Data for Testing
    single<ReportRepository> { ReportRepositoryImpl() }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { HomeFormViewModel(get()) }
    viewModel { MapsViewModel(get()) }
    viewModel { NavigationViewModel(get()) }
    viewModel { SceneViewModel() }
    viewModel { AddSceneViewModel(get(), get()) }
    viewModel { ScanViewModel() }
    viewModel { TrackTestViewModel(get()) }
}