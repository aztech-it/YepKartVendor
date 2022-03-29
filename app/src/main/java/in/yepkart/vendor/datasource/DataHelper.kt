package `in`.yepkart.vendor.datasource

import `in`.yepkart.vendor.models.JobItem
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

class DataHelper(mContext: Context) : SQLiteAssetHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION) {

    interface TABLES {
        companion object {
            const val JOBS = "JOBS"
        }
    }

    interface JOBS {
        companion object {
            const val ORDER_ID = "order_id"
            const val ORDER_AMOUNT = "order_amount"
            const val ORDER_DATE = "order_date"
            const val ORDER_STATUS = "order_status"
            const val PAYMENT_MODE = "payment_mode"
            const val PAYMENT_STATUS = "payment_status"
            const val SERVICE_DATE = "service_date"
            const val SERVICE_CATEGORY = "service_category"
            const val SERVICE_SUBCATEGORY = "service_subcategory"
            const val SERVICE_NAME = "service_name"
            const val SERVICE_JOB = "service_job"
            const val CUSTOMER_ID = "customer_id"
            const val CUSTOMER_NAME = "customer_name"
            const val CUSTOMER_MOBILE = "customer_mobile"
            const val CUSTOMER_EMAIL = "customer_email"
            const val CUSTOMER_ADDRESS = "customer_address"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getJobs(status: String): List<JobItem> {
        val db = readableDatabase
        val qb = SQLiteQueryBuilder()
        val selection = arrayOf(status)

        val jobList = ArrayList<JobItem>()

        qb.tables = TABLES.JOBS

        val c: Cursor = qb.query(db, null, JOBS.ORDER_STATUS + "=?", selection, null, null, null)
        c.moveToFirst()

        do {
            jobList.add(JobItem(
                c.getString(0),
                c.getFloat(1),
                LocalDateTime.parse(c.getString(2)),
                c.getString(3),
                c.getString(4),
                c.getString(5),
                LocalDateTime.parse(c.getString(6)),
                c.getString(7),
                c.getString(8),
                c.getString(9),
                c.getString(10),
                c.getString(11),
                c.getString(12),
                c.getString(13),
                c.getString(14),
                c.getString(15)
            ))
        } while (c.moveToNext())

        c.close()
        db.close()

        return jobList
    }

    fun setJobs(
        ORDER_ID: String,
        ORDER_AMOUNT: Float,
        ORDER_DATE: LocalDateTime,
        ORDER_STATUS: String,
        PAYMENT_MODE: String,
        PAYMENT_STATUS: String,
        SERVICE_DATE: LocalDateTime,
        SERVICE_CATEGORY: String,
        SERVICE_SUBCATEGORY: String,
        SERVICE_NAME: String,
        SERVICE_JOB: String,
        CUSTOMER_ID: String,
        CUSTOMER_NAME: String,
        CUSTOMER_MOBILE: String,
        CUSTOMER_EMAIL: String,
        CUSTOMER_ADDRESS: String
    )
    {

        val db = writableDatabase
        val values = ContentValues()

        values.put(JOBS.ORDER_ID, ORDER_ID)
        values.put(JOBS.ORDER_AMOUNT, ORDER_AMOUNT)
        values.put(JOBS.ORDER_DATE, ORDER_DATE.toString())
        values.put(JOBS.ORDER_STATUS, ORDER_STATUS)
        values.put(JOBS.PAYMENT_MODE, PAYMENT_MODE)
        values.put(JOBS.PAYMENT_STATUS, PAYMENT_STATUS)
        values.put(JOBS.SERVICE_DATE, SERVICE_DATE.toString())
        values.put(JOBS.SERVICE_CATEGORY, SERVICE_CATEGORY)
        values.put(JOBS.SERVICE_SUBCATEGORY, SERVICE_SUBCATEGORY)
        values.put(JOBS.SERVICE_NAME, SERVICE_NAME)
        values.put(JOBS.SERVICE_JOB, SERVICE_JOB)
        values.put(JOBS.CUSTOMER_ID, CUSTOMER_ID)
        values.put(JOBS.CUSTOMER_NAME, CUSTOMER_NAME)
        values.put(JOBS.CUSTOMER_MOBILE, CUSTOMER_MOBILE)
        values.put(JOBS.CUSTOMER_EMAIL, CUSTOMER_EMAIL)
        values.put(JOBS.CUSTOMER_ADDRESS, CUSTOMER_ADDRESS)

        db.insert(TABLES.JOBS, null, values)

        db.close()
    }

    fun clearJobs() {
        val db = readableDatabase

        db.delete(TABLES.JOBS, null, null)
    }

    companion object {
        private const val DATABASE_NAME = "cache.db"
        private const val DATABASE_VERSION = 3
    }
}