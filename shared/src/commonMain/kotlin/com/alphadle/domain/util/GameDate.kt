package com.alphadle.domain.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal val dailyResetTimeZone = TimeZone.of("America/Los_Angeles")

fun currentGameDate(): LocalDate {
    return Clock.System.now().toLocalDateTime(dailyResetTimeZone).date
}