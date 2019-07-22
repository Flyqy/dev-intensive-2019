package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.pow

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

fun Date.humanizeDiff(date: Date = Date()): String {
    val time = (date.time - this.time)
    val absTime = abs(time)
    val second = absTime / SECOND
    val minutes = second / 60
    val hours = minutes / 60
    val days = hours / 24
    val how = time < 0

    return when {
        days > 360 -> {
            if (how) "более чем через год"
            else "более года назад"
        }
        hours > 26 && days <= 360 -> getHow(TimeUnits.DAY.plural(days.toInt()), how)
        hours in 23..26 -> getHow("день", how)
        minutes > 75 && hours <= 22 -> getHow(TimeUnits.HOUR.plural(hours.toInt()), how)
        minutes in 46..75 -> getHow("час", how)
        second > 75 && minutes <= 45 -> getHow(TimeUnits.MINUTE.plural(minutes.toInt()), how)
        second in 46..75 -> getHow("минуту", how)
        second in 2..45 -> getHow("несколько секунд", how)
        second in 0..1 -> "только что"
        else -> ""
    }
}


private fun getHow(string: String, how: Boolean) = if (how) {
    "через $string"
} else {
    "$string назад"
}

private fun getNumberFormat(number: Int, units: TimeUnits): String {

    return when (units) {
        TimeUnits.MINUTE -> {
            when (number) {
                0, in 5..19 -> "минут"
                1 -> "минуту"
                in 2..4 -> "минуты"
                else -> getNumberFormat(reduceNumber(number), units)
            }
        }
        TimeUnits.HOUR -> {
            when (number) {
                0, in 5..19 -> "часов"
                1 -> "час"
                in 2..4 -> "часа"
                else -> getNumberFormat(reduceNumber(number), units)
            }
        }
        TimeUnits.DAY -> {
            when (number) {
                0, in 5..19 -> "дней"
                1 -> "день"
                in 2..4 -> "дня"
                else -> getNumberFormat(reduceNumber(number), units)
            }
        }
        else -> throw IllegalArgumentException()
    }
}


private fun reduceNumber(number: Int): Int {
    return number % (10.0.pow((number.toString().length) - 1).toInt())
}

enum class TimeUnits {
    SECOND {
        override fun plural(value: Int): String {
            if (value == 11) return "$value секунд"
            return when (value % 10) {
                1 -> "$value секунду"
                2, 3, 4 -> "$value секунды"
                else -> "$value секунд"
            }
        }
    },
    MINUTE {
        override fun plural(value: Int): String {
            if (value == 11) return "$value минут"
            return when (value % 10) {
                1 -> "$value минуту"
                2, 3, 4 -> "$value минуты"
                else -> "$value минут"
            }
        }
    },
    HOUR {
        override fun plural(value: Int): String {
            if (value == 11) return "$value часов"
            return when (value % 10) {
                1 -> "$value час"
                2, 3, 4 -> "$value часа"
                else -> "$value часов"
            }
        }
    },
    DAY {
        override fun plural(value: Int): String {
            if (value == 11) return "$value дней"
            return when (value % 10) {
                1 -> "$value день"
                2, 3, 4 -> "$value дня"
                else -> "$value дней"
            }
        }
    };

    abstract fun plural(value: Int): String
}