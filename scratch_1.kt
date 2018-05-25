interface Expr

class Num(val value: Int) : Expr

class Sum(val left: Expr, val right: Expr)

fun main(args: Array<String>) {
    println("Hola")

    val n1 = Num(4)
    val sum1 = Sum(2, 1)

    println(eval(Sum(Sum(1,2)), 4))

}

fun eval(expr: Expr): Int {
    if (expr is Num)
        return expr.value
    if (expr is Sum)
        return eval(expr.left + expr.right)
    throw java.lang.IllegalArgumentException("Unknown expression")
}
