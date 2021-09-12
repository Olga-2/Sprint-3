package ru.sber.oop

open class Room(val name: String, val size: Int) {

    protected open val dangerLevel = 5

    fun description() = "Room: $name"

    open fun load() = monster.getSalutation()

    constructor(name : String) : this (name, 100){ }

    val monster : Monster = Goblin ("monster", 50, "Crazy", "Сумашедший гоблин")
}

//TODO: create class TownSquare here...
class TownSquare: Room("TownSquare", 1000) {

    override val dangerLevel = super.dangerLevel - 3

    override fun load() = print("TownSquare is here...")

}