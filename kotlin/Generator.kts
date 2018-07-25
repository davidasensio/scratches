import java.util.*

val letters = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N" )
fun generateDeliveryNotes() {
    for (i in 0 until 100) {
        val randomLetter = letters[Random().nextInt(letters.size)]
        val randomLetter2 = letters[Random().nextInt(letters.size)]
        val randomNumber = String.format("%04d", Random().nextInt(9999))
        val randomDay = String.format("%02d", Random().nextInt(29) + 1)
        val randomMonth = String.format("%02d", Random().nextInt(11) + 1)
        println("DeliveryNote(\"$i\", \"$randomLetter$randomLetter2$randomNumber\", \"P\", \"$randomDay/$randomMonth/18\"),")
    }
}

generateDeliveryNotes()
