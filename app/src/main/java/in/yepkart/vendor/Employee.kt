package `in`.yepkart.vendor

class Employee {

    private lateinit var name: String
    private lateinit var mobile: String
    private lateinit var email: String
    private lateinit var pan: String
    private lateinit var address: String
    private var salary: Float = 0.0f
    private var status: Boolean = false

    constructor() {

    }

    constructor(name: String, mobile: String, email: String, pan: String, address: String, salary: Float, status: Boolean) {
        this.name = name
        this.mobile = mobile
        this.email = email
        this.pan = pan
        this.address = address
        this.salary = salary
        this.status = status
    }

    fun getName() : String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getMobile() : String {
        return mobile
    }

    fun setMobile(mobile: String) {
        this.mobile = mobile
    }

    fun getEmail() : String {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getPan() : String {
        return pan
    }

    fun setPan(pan: String) {
        this.pan = pan
    }

    fun getAddress() : String {
        return address
    }

    fun setAddress(address: String) {
        this.address = address
    }

    fun getSalary() : Float {
        return salary
    }

    fun setSalary(salary: Float) {
        this.salary = salary
    }

    fun getStatus() : Boolean {
        return status
    }

    fun setStatus(status: Boolean) {
        this.status = status
    }

}