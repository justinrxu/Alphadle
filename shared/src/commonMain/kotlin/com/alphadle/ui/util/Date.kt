package com.alphadle.ui.util

import kotlinx.datetime.LocalDate

internal fun LocalDate.stringDate(): String =
    "${month.name.capitalize()} $dayOfMonth, $year"