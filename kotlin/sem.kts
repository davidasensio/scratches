import java.util.*

class Sem(var greenTime: Int = 5,var amberTime: Int = 2,var redTime: Int = 5) {

    enum class Color { Green, Amber, Red }

    private var timer: java.util.Timer? = null

    private var currentState = Color.Green
    private var currentTime = 0

    init {
        currentTime = greenTime
        currentState = Color.Green
    }

    private fun getNextState(): Color {
        return when (currentState) {
            Color.Green -> Color.Amber
            Color.Amber -> Color.Red
            Color.Red -> Color.Green
        }
    }

    private fun getNextTime(): Int {
        return when (currentState) {
            Color.Green -> amberTime
            Color.Amber -> redTime
            Color.Red -> greenTime
        }
    }

    fun setNextState() {
        currentTime = getNextTime()
        currentState = getNextState()
        onStateChange()
        Sem.log("Changing to $currentState during $currentTime secs")
    }

    fun onStateChange(state: Sem.Color = currentState) {
        Sem.log("State changed to $state")
    }

    private fun startTimeCounter(time: Long = currentTime.toLong()) {
        if (timer == null) {
            timer = Timer()
        }

        timer?.schedule(object : java.util.TimerTask() {
            override fun run() {
                setNextState()
                startTimeCounter()
            }
        }, time * TIME_FACTOR_SECONDS)
    }

    fun startAuto() {
        Sem.log("Start")
        onStateChange()
        startTimeCounter()
    }

    fun stop() {
        Sem.log("Stop")
        timer?.cancel()
        timer = null
    }

    companion object {
        const val TIME_FACTOR_SECONDS = 1000
        fun log(msg: String) {
            println(msg)
        }
    }
}

//Sem Usage
val sem = Sem()

sem.startAuto()

//Stop
Timer().schedule(object : java.util.TimerTask() {
    override fun run() {
        sem.stop()
    }
}, 21000)