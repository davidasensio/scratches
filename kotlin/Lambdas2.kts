import kotlin.system.measureTimeMillis

data class Country(val name: String, val population: Int, val area: Int) {
}

data class Book(val title: String, val author: String, val year: Int) {
    var ISBN = "012010202020101"

    fun info(): Pair<String, String> = title to author

    fun fullInfo(): Triple<String, String, Int> = Triple(title, author, year)
}

object MobyDickWhale {
    val author = "Herman Melville"
    private var page = 0

    fun jump() = page++
    fun restart() {
        page = 0
    }

    override fun toString(): String {
        return "Book MobyDick. Author: $author. Current page: $page"
    }
}

fun doThings() {
    val countries = listOf<Country>(
            Country("Spain", 46, 505990),
            Country("France", 66, 643801),
            Country("Germany", 82, 357021),
            Country("England", 53, 159000)
    )

    println(countries)

    val country1 = Country("USA", 325, 9_000_000)
    val country2 = country1.copy()
    val china = country2.copy("China", 1300)

    println(country1)
    println(country2)
    println(china)

    val (a, b, c) = china
    println(a)
    println(b)
    println(c)

    val m1 = MobyDickWhale
    val m2 = MobyDickWhale
    repeat(10, { m1.jump() })
    //val m2 = MobyDickWhale()
    repeat(50, { m2.jump() })
    println("M1 -> $m1")
    println("M2 -> $m2")
    //android.os.Handler().postDelayed({println("Executed")}, 2000)

    val book1 = Book("Title 1", "Author1", 2010)
    val book2 = Book("Title 2", "Author2", 2012)
    val book3 = Book("Title 3", "Author3", 2013)


    val book1Info = book1.fullInfo()
    val title = book1Info.first
    val author = book1Info.second
    val year = book1Info.third
    println("Here is your book $title written by $author in $year")


    val sequence = generateSequence(1) { it + 1 }
    val list = sequence.toList()

    println("List filter sum " + measureTimeMillis { list.filter { it % 3 == 0 } })
    println("End")
}

doThings()