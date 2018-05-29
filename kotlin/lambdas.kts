data class Person(val name: String, val age: Int, val height: Int, val weight: Int = 70) {

    fun isAdult(): String {
        return if (age >= 18) "true" else "false"
    }
}

fun doThings() {
    val people = listOf<Person>(
            Person("David", 36, 182, 85),
            Person("Hugo", 44, 168, 105),
            Person("María", 24, 159, 60),
            Person("Elena", 34, 159, 72),
            Person("Belén", 29, 159, 65),
            Person("Marco", 14, 159, 52),
            Person("Rocío", 2, 90, 14),
            Person("Sergio", 28, 177, 65)
    )
    println("Finding the oldest person")

    val oldestPerson = findTheOldest(people)
    println("The oldest person is $oldestPerson")

    val youngestPerson = people.minBy { it.age }
    println("The youngest person is $youngestPerson")

    val youngestPerson2 = people.minBy(Person::age)
    println("The youngest person still is $youngestPerson2")

    val sum = { x: Int, y: Int -> x + y }
    println("Executing stored lambda sum: ${sum(4, 5)}")

    println("Executing inline lambda { println(hi!) }():")


    println("The oldest person is:")
    println(people.maxBy { p: Person -> p.age })

    println("The oldest person again is:")
    println(people.maxBy({ p -> p.age }))

    println("The youngest person  is:")
    println(people.minBy() { p -> p.age })

    println("The youngest person again is:")
    println(people.minBy { p -> p.age })

    val names = people.joinToString(separator = ", ") { p: Person -> p.name }
    println(names)

    println(people.sortedBy { p -> p.age })

    val getAge = { p: Person -> p.age }
    println("The smallest person is ${people.minBy(getAge)}")

    printWithPrefix(people, "Person")

    //print2Pow()


    run(::doPrintRandomNumber)
    println("${people[0].name} is adult -> " + run(people[0]::isAdult))
    println("${people[0].name} is adult -> " + run { people[0].isAdult() })


}

{ println("hi!") }()

fun doPrintRandomNumber() {
    println(java.util.Random().nextInt(10))
}

fun printWithPrefix(list: Collection<Person>, prefix: String) {
    var suffix = "*"
    list.forEach {
        println("$prefix: ${it.name} $suffix")
        suffix += suffix
    }
}

fun print2Pow() {
    var char = "*"
    for (iter in 1 until 20) {
        println("$iter: $char")
        char += char
    }
}

fun findTheOldest(people: List<Person>): Person? {
    var maxAge = 0
    var oldestPerson: Person? = null
    for (person in people) {
        maxAge = if (person.age > maxAge) {
            oldestPerson = person
            person.age
        } else maxAge
    }
    return oldestPerson
}

doThings()