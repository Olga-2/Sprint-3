package ru.sber.oop

data class User(val name: String, val age: Long) {
    lateinit var city: String

    override fun equals(other: Any?): Boolean {

        return super.equals(other) && (other is User && this.city.equals(other.city))
    }
}

fun main() {
    val user1 = User("Alex", 13)

    //TODO: user2 = ...
    val user2 = user1.copy(name = "Alice")

    user1.city = "Omsk"
    //TODO: user3 = ...
    val user3 = user1.copy().apply { city = "Tomsk"}

    println(user1.equals(user3))

}