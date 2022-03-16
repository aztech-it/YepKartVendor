package `in`.yepkart.vendor.models

import `in`.yepkart.vendor.enums.TransactionType

data class TransactionItem(val remarks: String, val timeStamp: String, val amount: String, val type: TransactionType)