package `in`.yepkart.vendor.models

import java.time.LocalDate

data class JobItem(val job: String, val service: String, val subCategory: String, val category: String, val customerName: String, val customerAddress: String, val customerEmail: String, val customerMobile: String, val serviceDate: LocalDate, val serviceTime: String)