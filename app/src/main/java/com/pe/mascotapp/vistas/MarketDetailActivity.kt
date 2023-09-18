package com.pe.mascotapp.vistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.R
import com.pe.mascotapp.modelos.Market
import com.pe.mascotapp.modelos.Oferta
import com.pe.mascotapp.modelos.Producto
import com.pe.mascotapp.vistas.adapters.MarketAdapter
import com.pe.mascotapp.vistas.adapters.MarketOfertaAdapter
import com.pe.mascotapp.vistas.adapters.ProductoAdapter

class MarketDetailActivity : AppCompatActivity() {

    var rcvMarketOffer: RecyclerView?= null
    var marketOfertaAdapter: MarketOfertaAdapter?= null
    var ofertasArray:ArrayList<Oferta> = ArrayList()
    var rcvMarket: RecyclerView?= null
    var productoAdapter: ProductoAdapter?= null
    var productoArray:ArrayList<Producto> = ArrayList()
    var txtTitleTiendas: TextView?= null
    var txtTileMarketPlace: TextView?= null

    var veterinaria: Market?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market_detail)
        veterinaria = intent.getSerializableExtra("OBJECT_MARKET") as Market

        rcvMarketOffer = findViewById<View>(R.id.rcvMarketOffer) as RecyclerView
        rcvMarket = findViewById<View>(R.id.rcvMarket) as RecyclerView
        txtTitleTiendas = findViewById<View>(R.id.txtTitleTiendas) as TextView
        txtTileMarketPlace = findViewById<View>(R.id.txtTileMarketPlace) as TextView

        txtTileMarketPlace!!.text = veterinaria!!.titulo
        txtTitleTiendas!!.text = veterinaria!!.descripcion

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


        val producto1 = Producto()
        producto1.id = 0
        producto1.titulo = "Comida junior"
        producto1.descripcion = "Alimento"
        producto1.img = "snack_comida"
        producto1.precio = "S/ 100"
        producto1.oferta = "S/ 70"
        producto1.unidad = "kg"
        producto1.cantidadUnidad = "1"
        producto1.marca = "Purina Dog Chow"
        productoArray.add(producto1)

        val producto2 = Producto()
        producto2.id = 1
        producto2.titulo = "Comida junior"
        producto2.descripcion = "Alimento"
        producto2.img = "snack_comida"
        producto2.precio = "S/ 100"
        producto2.oferta = "S/ 70"
        producto2.unidad = "kg"
        producto2.cantidadUnidad = "1"
        producto2.marca = "Purina Dog Chow"
        productoArray.add(producto2)


        val producto3 = Producto()
        producto3.id = 2
        producto3.titulo = "Comida junior"
        producto3.descripcion = "Alimento"
        producto3.img = "snack_comida"
        producto3.precio = "S/ 50"
        producto3.oferta = ""
        producto3.unidad = "kg"
        producto3.cantidadUnidad = "1"
        producto3.marca = "Purina Dog Chow"
        productoArray.add(producto3)

        val producto4 = Producto()
        producto4.id = 3
        producto4.titulo = "Comida junior"
        producto4.descripcion = "Alimento"
        producto4.img = "snack_comida"
        producto4.precio = "S/ 100"
        producto4.oferta = ""
        producto4.unidad = "kg"
        producto4.cantidadUnidad = "1"
        producto4.marca = "Purina Dog Chow"
        productoArray.add(producto4)
    }

    fun startRCV(){
        rcvMarketOffer?.setLayoutManager(
            LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false)
        )
        marketOfertaAdapter = MarketOfertaAdapter(ofertasArray){Oferta->


        }
        rcvMarketOffer?.setAdapter(marketOfertaAdapter)
        rcvMarketOffer?.setItemAnimator(DefaultItemAnimator())

        rcvMarket?.setLayoutManager(LinearLayoutManager(this))
        productoAdapter = ProductoAdapter(productoArray){ producto ->

        }
        rcvMarket?.setAdapter(productoAdapter)
        rcvMarket?.setItemAnimator(DefaultItemAnimator())
    }
}