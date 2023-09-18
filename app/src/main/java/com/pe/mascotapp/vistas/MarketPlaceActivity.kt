package com.pe.mascotapp.vistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.R
import com.pe.mascotapp.modelos.Categorias
import com.pe.mascotapp.modelos.Market
import com.pe.mascotapp.modelos.Oferta
import com.pe.mascotapp.vistas.adapters.HomeAdapter
import com.pe.mascotapp.vistas.adapters.MarketAdapter
import com.pe.mascotapp.vistas.adapters.MarketOfertaAdapter

class MarketPlaceActivity : AppCompatActivity() {

    var rcvMarketOffer:RecyclerView ?= null
    var marketOfertaAdapter:MarketOfertaAdapter ?= null
    var ofertasArray:ArrayList<Oferta> = ArrayList()
    var rcvMarket:RecyclerView ?= null
    var marketAdapter:MarketAdapter ?= null
    var marketArray:ArrayList<Market> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market_place)

        rcvMarketOffer = findViewById<View>(R.id.rcvMarketOffer) as RecyclerView
        rcvMarket = findViewById<View>(R.id.rcvMarket) as RecyclerView

        obtenerData()
        startRCV()
    }

    fun obtenerData(){
        val oferta1 = Oferta()
        oferta1.id = 0
        oferta1.titulo = "Comida y Snacks"
        oferta1.descripcion = "Comida y variados"
        oferta1.img = "snack_comida"
        ofertasArray.add(oferta1)

        val oferta2 = Oferta()
        oferta2.id = 1
        oferta2.titulo = "Paseos y entrenamiento"
        oferta2.descripcion = "Paseos"
        oferta2.img = "paseo_perros"
        ofertasArray.add(oferta2)


        val oferta3 = Oferta()
        oferta3.id = 2
        oferta3.titulo = "Juguetes, ropa y accesorios"
        oferta3.descripcion = "juguetes y variados"
        oferta3.img = "juguetes_perros"
        ofertasArray.add(oferta3)

        val oferta4 = Oferta()
        oferta4.id = 3
        oferta4.titulo = "Veterinaria"
        oferta4.descripcion = "Veterinaria"
        oferta4.img = "veterinaria_img"
        ofertasArray.add(oferta4)


        val market1 = Market()
        market1.id = 0
        market1.titulo = "Comida"
        market1.categoria = "Alimento"
        market1.descripcion = "Comida del día"
        market1.img = "snack_comida"
        marketArray.add(market1)

        val market2 = Market()
        market2.id = 1
        market2.titulo = "Juguetes"
        market2.categoria = "Juguetes"
        market2.descripcion = "Juguetes más populares"
        market2.img = "paseo_perros"
        marketArray.add(market2)


        val market3 = Market()
        market3.id = 2
        market3.titulo = "Accesorios"
        market3.categoria = "Accesorios"
        market3.descripcion = "Accesorios más usados"
        market3.img = "juguetes_perros"
        marketArray.add(market3)

        val market4 = Market()
        market4.id = 3
        market4.titulo = "Medicina"
        market4.categoria = "Medicina"
        market4.descripcion = "Nuestros aliados"
        market4.img = "veterinaria_img"
        marketArray.add(market4)
    }

    fun startRCV(){
        rcvMarketOffer?.setLayoutManager(LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false))
        marketOfertaAdapter = MarketOfertaAdapter(ofertasArray){Oferta->

        }
        rcvMarketOffer?.setAdapter(marketOfertaAdapter)
        rcvMarketOffer?.setItemAnimator(DefaultItemAnimator())

        val mLayoutManager = GridLayoutManager(this,2)
        rcvMarket?.setLayoutManager(mLayoutManager)
        marketAdapter = MarketAdapter(marketArray){ market ->
            val intent = Intent(this, MarketPlaceCategoryActivity::class.java)
            intent.putExtra("OBJECT_MARKET",market)
            startActivity(intent)
        }
        rcvMarket?.setAdapter(marketAdapter)
        rcvMarket?.setItemAnimator(DefaultItemAnimator())
    }
}