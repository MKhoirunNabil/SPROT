package com.sport.Database

import androidx.room.*
import com.sport.RaketActivity
import java.util.concurrent.Flow


@Dao
interface ALLDAO {
    //Bola
    @Insert
    fun TambahBola(vararg bola: Bola)
    @Update
    fun EditBola(vararg bola: Bola)
    @Delete
    fun HapusBola(vararg bola: Bola)
    @Query("SELECT * FROM tbbola WHERE idbola =:idbolaaa")
    fun getBolaById(idbolaaa: Int): List<Bola>

    //Bolaaaaaaaaaaa SEPAKKKK
    @Query("SELECT * FROM tbbola WHERE jenisbola = 'Sepak'")
    fun TampilSepak() : List<Bola>
    @Query("SELECT * FROM tbbola WHERE namabola LIKE :searchQuery OR hargabola LIKE :searchQuery")
    fun searchBola(searchQuery: String):Bola
    //Bolaaaaaaa BASKETTT
    @Query("SELECT * FROM tbbola WHERE jenisbola = 'Basket'")
    fun TampilBasket() : List<Bola>
    //Bolaaaaaaa Voliiii
    @Query("SELECT * FROM tbbola WHERE jenisbola = 'Voli'")
    fun TampilVoli() : List<Bola>


    //Raket
    @Query("SELECT * FROM tbraket")
    fun TampilRaket() : List<Raket>
    @Insert
    suspend fun TambahRaket(raket: Raket)
    @Update
    fun EditRaket(vararg raket: Raket)
    @Delete
    fun HapusRaket(vararg raket: Raket)
    @Query("SELECT * FROM tbraket WHERE idraket =:idrakettt")
    fun getRaketById(idrakettt: Int): List<Raket>



    //User
    @Query("SELECT * FROM USER WHERE email = :email")
    fun getUserByEmail(email: String): USER?

    @Insert
    fun insertUser(user: USER)

    @Query("SELECT * FROM USER WHERE id = :userId")
    suspend fun getUserById(userId: Int): USER?

}
