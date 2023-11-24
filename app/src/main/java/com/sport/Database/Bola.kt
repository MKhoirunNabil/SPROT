package com.sport.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbbola")
data class Bola (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idbola")
    val idBola : Int,

    @ColumnInfo(name = "imagebola")
    val imagebola: String,

    @ColumnInfo(name = "namabola")
    val namaBola : String,

    @ColumnInfo(name = "jenisbola")
    val jenisBola : String,

    @ColumnInfo(name = "stokbola")
    val stokBola : Int,

    @ColumnInfo(name = "hargabola")
    val hargaBola : Int,

    @ColumnInfo(name = "deskbola")
    val deskBola : String

)