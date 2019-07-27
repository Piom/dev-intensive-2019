package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR
const val YEAR = 336 * DAY


fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time
    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
        TimeUnits.YEAR -> value * YEAR
    }
    this.time = time
    return this
}

enum class TimeUnits {
    SECOND, MINUTE, HOUR, DAY, YEAR;

    fun plural(value: Long, withValueYS: Boolean = true): String {
        return plural(value.toInt(), withValueYS)
    }

    fun plural(value: Int, withValueYS: Boolean = true): String {
        val additional = when (this) {
            SECOND -> {
                getTimeAddition(value.toLong(), "секунду", "секунды", "секунд")
            }
            MINUTE -> {
                getTimeAddition(value.toLong(), "минуту", "минуты", "минут")
            }
            HOUR -> {
                getTimeAddition(value.toLong(), "час", "часа", "часов")
            }
            DAY -> {
                getTimeAddition(value.toLong(), "день", "дня", "дней")
            }
            YEAR -> {
                getTimeAddition(value.toLong(), "год", "года", "лет")
            }
        }
        return if (withValueYS) "${value.absoluteValue} $additional" else additional
    }

    fun getTimeAddition(value: Long, caseOne: String, caseTwo: String, caseFive: String): String {
        val absolute = value.absoluteValue
        return when {
            ((absolute % 10) == 1L) && ((absolute % 100) != 11L) -> caseOne
            ((absolute % 10) >= 2L) && ((absolute % 10) <= 4L) && ((absolute % 100) < 10L || (absolute % 100) >= 20L) -> caseTwo
            else -> caseFive
        }

    }
}

class TimeDiff(
        val milSec: Long
) {
    val seconds = milSec / SECOND
    val minutes = milSec / MINUTE
    val hours = milSec / HOUR
    val days = milSec / DAY
}


fun Date.humanizeDiff(date: Date = Date()): String? {
    val diff = TimeDiff(date.time - this.time)

    return when {
        diff.days > 336 -> "более ${TimeUnits.YEAR.plural(2, false)} назад"
        diff.days > 0 -> "${TimeUnits.DAY.plural(diff.days)} назад"
        diff.hours > 0 -> "${TimeUnits.HOUR.plural(diff.hours)} назад"
        diff.minutes > 0 -> "${TimeUnits.MINUTE.plural(diff.minutes)} назад"
        diff.seconds > 0 -> "несколько ${TimeUnits.SECOND.plural(5, false)} назад"

        diff.days < -336 -> "более чем через ${TimeUnits.YEAR.plural(1, false)}"
        diff.days < 0 -> "через ${TimeUnits.DAY.plural(diff.days)}"
        diff.hours < 0 -> "через ${TimeUnits.HOUR.plural(diff.hours)}"
        diff.minutes < 0 -> "через ${TimeUnits.MINUTE.plural(diff.minutes)}"
        diff.seconds < 0 -> "через несколько ${TimeUnits.SECOND.plural(5, false)}"

        else -> "только что"
    }

}
