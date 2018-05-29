fun sayHi() {
    println("Hello kotlin")
}

for (x in 0..11) {
    println("Number " + x)
}


listOf(1, 2, 3, 4, 5, 6).filter({
    it > 3
})

fun testScopes() {
    var mood = "I am sad"

    println(mood)

    //
    val book = Book("Book1", "Me")
    mywith(book) {
        author = "Anonymous"
        repeat(15, { readPage() })
        println(book)
    }

    val book2 = Book("Book2", "Other")

    book2.runrunWithScope {
        println(title)
        mood
    }.reversed().also(::println)
}

testScopes()

fun <T, R> T.runrun(block: T.() -> R): R {
    return block()
}

fun <T, R> T.runrunWithScope(block: T.() -> R): R {
    return block(this)
}

fun <T, R> mywith(receiver: T, block: T.() -> R): R {
    return receiver.block()

}

open class Book(var title: String, var author: String) {
    private var currentPage: Int = 0

    open fun readPage() = currentPage++

    override fun toString(): String =
            "Book $title by $author (CurrentPage is $currentPage)"

}

class eBook(title: String, author: String, var format: String = "text") : Book(title, author) {
    private var currentWord: Int = 0
    val theTitle = "The title is $title".also(::println)

    override fun readPage(): Int = currentWord.plus(250)
}



