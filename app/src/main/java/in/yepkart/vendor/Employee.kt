package `in`.yepkart.vendor

class Employee( name: String, mobile: String, email: String, pan: String, address: String, salary: Float, status: Boolean) {

    private var name: String = name
    private var mobile: String = mobile
    private var email: String = email
    private var pan: String = pan
    private var address: String = address
    private var salary: Float = salary
    private var status: Boolean = status

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