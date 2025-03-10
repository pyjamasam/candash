package app.candash.cluster

import android.content.SharedPreferences
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DashRepository @ExperimentalCoroutinesApi
@Inject constructor(private val pandaService: PandaServiceImpl, val mockPandaService: MockPandaService, val sharedPreferences: SharedPreferences) {

    fun useMockService() : Boolean {
        return sharedPreferences.getBoolean(Constants.useMockServerPrefKey, false)
    }

    fun getPandaService() : PandaService {
        return if (useMockService()) mockPandaService else pandaService
    }

    suspend fun startRequests(signalNamesToRequest: List<String>) {
        getPandaService().startRequests(signalNamesToRequest)
    }

    fun isRunning() : Boolean {
        return getPandaService().isRunning()
    }

    @ExperimentalCoroutinesApi
    suspend fun shutdown() {
        getPandaService().shutdown()
    }

    @ExperimentalCoroutinesApi
    fun carState() : Flow<CarState> {
        return getPandaService().carState();
    }


}