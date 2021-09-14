package ru.sber.oop

import kotlin.random.Random

interface Fightable {
    val powerType: String
    var healthPoints: Int
    val damageRoll: Int
        get() = Random.nextInt(100)

    fun attack(opponent: Fightable): Int
}

//TODO: create class Player, Monster, Goblin here...

class Player(override val powerType: String, override var healthPoints: Int,
             val name : String, val isBlessed : Boolean) : Fightable {

    override fun attack(opponent: Fightable): Int {
        val damage : Int = if (this.isBlessed)  2 * this.damageRoll else  this.damageRoll
        opponent.healthPoints = opponent.healthPoints - damage
        return damage
    }
}

abstract class Monster(override val powerType: String, override var healthPoints: Int,
             open val name : String, open val description : String) : Fightable {

    override fun attack(opponent: Fightable): Int {
        opponent.healthPoints = opponent.healthPoints - this.damageRoll
        return this.damageRoll
    }
    fun getSalutation(){
        println("Hello, guys!")
    }

}

class Goblin(override val powerType: String, override var healthPoints: Int,
              override val name : String, override val description : String)
                : Monster(powerType, healthPoints, name, description) {
    override val damageRoll = super.damageRoll / 2

    override fun attack(opponent: Fightable): Int {
        opponent.healthPoints = opponent.healthPoints - damageRoll
        return healthPoints
    }
}
