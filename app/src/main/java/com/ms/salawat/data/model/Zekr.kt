package com.ms.salawat.data.model

data class  AzkarCategory(
    val title : String,
    val content : List<Zekr>,
)

data class Zekr (
    val zekr : String,
    val repeat : Int,
    val bless : String,
)



data class AzkarAll(
    val category: String,
    val count: String,
    val description: String,
    val reference: String,
    val dhikr: String,
)