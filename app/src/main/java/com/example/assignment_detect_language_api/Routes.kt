package com.example.assignment_detect_language_api

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object ResponseItem : Routes("response_item")
    object History : Routes("history")
    object HistorySingle : Routes("history_single")
}