import kotlin.math.PI

fun main(args: Array<String>) {
    println("######################")
    println("Main function executed")
    println("Good ${if (args[0].toInt() < 12) "morning" else "night"}, Kotlin")

    val spices = listOf("curry", "pepper", "cayenne", "ginger", "red curry", "green curry", "red pepper")
    println(spices.filter { it.contains("curry") }.sortedByDescending { it.length })

    println(spices.filter { it.startsWith("c") && it.endsWith("e") })

    println(spices.filterIndexed { index, _ -> index < 3 }.filter { it.startsWith("c") })

    println(spices.take(3).filter { it.startsWith("c") })


    var waterFilter = { dirty: Int -> dirty / 2 }
    var waterFilter2: (Int) -> Int = { dirty -> dirty / 2 }

    val spice = Spice("curry")
    println(spice)

    println("############### AQUARIUM ####################")
//    val aq = Aquarium(1, 1, 2)
//    println(aq)
//    val aq2 = Aquarium()
//    println(aq2)
    val aq3 = Aquarium(9)
    println(aq3)

    val fish1 = Fish(false, 18)
    println("Is the fish1 friendly? ${fish1.friendly}. It needs volumen ${fish1.size}")

    val fish2 = Fish()
    println("Is the fish2 friendly? ${fish2.friendly}. It needs volumen ${fish2.size}")

    val fish3 = makeDefaultFish()
    println("Is the fish3 friendly? ${fish3.friendly}. It needs volumen ${fish3.size}")


    println("############### MORE SPICES ####################")
    val spices1 = listOf(
            Spice("curry", "mild"),
            Spice("pepper", "medium"),
            Spice("cayenne", "spicy"),
            Spice("ginger", "mild"),
            Spice("red curry", "medium"),
            Spice("green curry", "mild"),
            Spice("hot pepper", "extremely spicy"),
            makeSalt()
    )

    println("Spices that are spice or less")
    println(spices1.filter { it.heat <= 7 }.toList())


    println("############### AQUARIUMFISH ####################")
    val shark1 = Shark()
    val pleco = Plecostomus()
    println("Shark ${shark1.color}\nPleco ${pleco.color} ")

    shark1.eat()
    pleco.eat()

    test()

}

fun test() {
    var mood = "I am sad"

    run {
        val mood = "I am happy"
        println(mood) // I am happy
    }
    println(mood)  // I am sad
}

fun feedFish(fish: FishAction) {
    fish.eat()
}
fun makeSalt() = Spice("salt")

fun makeDefaultFish() = Fish(true, 50)

main(arrayOf("13"))

class Spice(var name: String, var spiceness: String = "mild") {
    var heat: Int = 0
        get() {
            return when (spiceness) {
                "mild" -> 1
                "medium" -> 3
                "spicy" -> 5
                "very spicy" -> 7
                "extremely spicy" -> 10
                else -> 0
            }
        }

    init {
        println("Spice $name ($spiceness)")
    }

    override fun toString(): String {
        return "$name ($heat)"
    }
}

open class Aquarium(var length: Int = 20, var width: Int = 40, var height: Int = 15) {

    open var volume: Int
        get() = length * height * width / 1000
        set(value) {
            height = (value * 1000) / length * width
        }

    open var water = volume * 0.9

    init {
        println("Aquarium Executed init block 1. Length: $length")
    }

    constructor(numberOfFish: Int) : this() {
        val water = numberOfFish * 2000 //cm3
        val tank = water + water * 0.1
        height = (tank / (length * width)).toInt()
        println("Aquarium Executed secondary constructor")
    }

    init {
        println("Aquarium Executed init block 2. Water: $water")
    }

    override fun toString(): String {
        return "Aquarium ($length x $height X $width) -> Volume: $volume m3 -> Water: $water m3"
    }
}

class TowerTank() : Aquarium() {
    override var volume = (length * height * width / 1000 * PI).toInt()

    override var water = volume * 0.8
}

class Fish(val friendly: Boolean, volumeNeeded: Int) {
    val size: Int

    constructor() : this(true, 10) {
        println("Fish Secundary constructor executed")
    }

    init {
        size = if (friendly) {
            volumeNeeded
        } else {
            volumeNeeded * 2
        }
        //size = if (friendly) volumeNeeded else volumeNeeded * 2
    }
}

open class Book(var title: String, var author: String) {
    private var currentPage: Int = 0

    open fun readPage() = currentPage++
}

class eBook(title: String, author: String, var format: String = "text") : Book(title, author) {
    private var currentWord: Int = 0
    val theTitle = "The title is $title".also (::println)

    override fun readPage(): Int = currentWord.plus(250)
}

abstract class AquariumFish {
    abstract val color: String
}

class Shark : AquariumFish(), FishAction {
    override val color: String = "Gray"

    override fun eat() {

    }
}

class Plecostomus : AquariumFish(), FishAction {
    override val color: String = "Gold"

    override fun eat() {

    }
}

interface FishAction {
    fun eat()
}