package ru.sber.oop

data class User(val name: String, val age: Long) {
    lateinit var city: String
}

fun main() {
    val user1 = User("Alex", 13)

    //TODO: user2 = ...
    val user2 = user1.copy(name = "Alice")

    user1.city = "Omsk"
    //TODO: user3 = ...
    val user3 = user1.copy().apply { city = "Tomsk"}

    println(user1.equals(user3)) //ответ true

    //Измененый класс User
//  data class User(val name: String, val age: Long, val city: String){
//  }
//

}