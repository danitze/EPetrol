package com.example.epetrol

inline fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if(p1 != null && p2 != null) block(p1, p2) else null
}

fun formPriceText(price: Double): String = "$price UAH"

fun getPainterId(stationName: String): Int = R.drawable.ic_placeholder