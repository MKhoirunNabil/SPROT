package com.sport.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tbraket")
data class Raket (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idraket")
    val idraket : Int,

    @ColumnInfo(name = "imageraket")
    val imageraket: String,

    @ColumnInfo(name = "namaRaket")
    val namaRaket : String,

    @ColumnInfo(name = "hargaraket")
    val hargaRaket : Int,

    @ColumnInfo(name = "stokraket")
    val stokRaket : Int,

    @ColumnInfo(name = "deskraket")
    val deskRaket : String

)