package `in`.yepkart.vendor.models

import java.time.LocalDateTime

data class JobItem(
        val orderId: String,
        val orderAmount: Float,
        val orderDate: LocalDateTime,
        val orderStatus: String,
        val paymentMode: String,
        val paymentStatus: String,
        val serviceDate: LocalDateTime,
        val serviceCategory: String,
        val serviceSubcategory: String,
        val serviceName: String,
        val serviceJob: String,
        val customerId: String,
        val customerName: String,
        val customerMobile: String,
        val customerEmail: String,
        val customerAddress: String
    )