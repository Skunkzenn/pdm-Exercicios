import kotlin.math.sqrt
import kotlin.random.Random

class CalculadoraBrain {
    enum class Operation(val op: String) {
        SUM("+"),
        SUB("-"),
        MUL("*"),
        DIV("/"),
        SQRT("√"),
        SIGNAL("±"),
        PERCENT("%"),
        RAND("\uD83D\uDE02");
        companion object {
            fun getOp(value: String): Operation {
                return entries.find { it.op == value } ?: RAND
                }
            }
    }

    var operation: Operation? = null
    var operand: Double = 0.0

    fun doOperation(value: Double) : Double {
        val result = when (operation) {
            Operation.SUM -> operand + value
            Operation.SUB -> operand - value
            Operation.MUL -> operand * value
            Operation.DIV -> operand / value
            Operation.SQRT -> sqrt(operand)
            Operation.SIGNAL -> -operand
            Operation.PERCENT -> operand / 100
            Operation.RAND -> Math.random()
            null -> value
        }
        return result
    }
}