package `in`.yepkart.vendor.ui.analytics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AnalyticsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is analytics Fragment"
    }
}