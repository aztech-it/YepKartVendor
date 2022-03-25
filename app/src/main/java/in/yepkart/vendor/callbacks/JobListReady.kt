package `in`.yepkart.vendor.callbacks

import `in`.yepkart.vendor.models.JobItem

interface JobListReady {
    fun onDataChanged(value: List<JobItem>)
    fun onCancelled(exception: String)
}