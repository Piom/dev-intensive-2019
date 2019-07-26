package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR


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
    }
    this.time = time
    return this
}

enum class TimeUnits {
    SECOND, MINUTE, HOUR, DAY
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
    fun getTimeAddition(value: Long, caseOne: String, caseTwo: String, caseFive: String): String {

        return when {
            ((value % 10) == 1L) && ((value % 100) != 11L) -> caseOne
            ((value % 10) >= 2L) && ((value % 10) <= 4L) && ((value % 100) < 10L || (value % 100) >= 20L) -> caseTwo
            else -> caseFive
        }

    }

    return when {
        diff.days > 336 -> "более ${diff.days / 336} ${getTimeAddition(diff.days / 336, "год", "года", "лет")} назад"
        diff.days > 28 -> "${diff.days / 28} ${getTimeAddition(diff.days / 28, "месяц", "месяца", "месяцев")} назад"
        diff.days > 6 -> "${diff.days / 6} ${getTimeAddition(diff.days / 6, "неделю", "недели", "недель")} назад"
        diff.days > 0 -> "${diff.days} ${getTimeAddition(diff.days, "день", "дня", "дней")} назад"
        diff.hours > 0 -> "${diff.hours} ${getTimeAddition(diff.hours, "час", "часа", "часов")} назад"
        diff.minutes > 0 -> "${diff.minutes} ${getTimeAddition(diff.minutes, "минуту", "минуты", "минут")} назад"
        diff.seconds > 0 -> "${diff.seconds} ${getTimeAddition(diff.seconds, "секунду", "секунды", "секунд")} назад"

        diff.days < -336 -> "более чем через ${diff.days / 336} ${getTimeAddition(diff.days / 336, "год", "год", "год")}"
        diff.days < -28 -> "через ${diff.days / 28} ${getTimeAddition(diff.days / 28, "месяц", "месяца", "месяцев")} назад"
        diff.days < -6 -> "через ${diff.days / 6} ${getTimeAddition(diff.days / 6, "неделю", "недели", "недель")} назад"
        diff.days < 0 -> "через ${diff.days} ${getTimeAddition(diff.days, "день", "дня", "дней")} назад"
        diff.hours < 0 -> "через ${diff.hours} ${getTimeAddition(diff.hours, "час", "часа", "часов")} назад"
        diff.minutes < 0 -> "через ${diff.minutes} ${getTimeAddition(diff.minutes, "минуту", "минуты", "минут")} назад"
        diff.seconds < 0 -> " через ${diff.seconds} ${getTimeAddition(diff.seconds, "секунду", "секунды", "секунд")} назад"

        else -> "только что"
    }

}
