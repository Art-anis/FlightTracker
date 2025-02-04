package com.nerazim.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flight_search_history")
data class FlightSearchHistory(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "departure") val departure: String,
    @ColumnInfo(name = "arrival") val arrival: String
)
