package com.pe.mascotapp.vistas.fragments.home

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.R
import com.pe.mascotapp.databinding.FragmentHomeBinding
import com.pe.mascotapp.modelos.Categorias
import com.pe.mascotapp.modelos.PromocionBanner
import com.pe.mascotapp.utils.Constantes
import com.pe.mascotapp.utils.Utils
import com.pe.mascotapp.vistas.DetailServiceActivity
import com.pe.mascotapp.vistas.adapters.HomeListServiceAdapter
import com.pe.mascotapp.vistas.adapters.HomeServiceAdapter
import java.io.File
import java.io.FileInputStream

class HomeFragment : Fragment() {


    var categoriasArray: ArrayList<Categorias> = ArrayList()
    var promocionBanner: PromocionBanner = PromocionBanner()

    var homeListServiceAdapterType: HomeListServiceAdapter?= null
    var homeServiceAdapterType:HomeServiceAdapter ?= null
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding =FragmentHomeBinding.inflate(inflater, container,false)
        obtenerData()
        startRCVHome()
        setUpListener()
        return binding.root
    }

    private val filterClickListener = View.OnClickListener {
        val intent = Intent(context, FilterActivity::class.java)
        startActivity(intent)
    }

    private fun setUpListener(){
        binding.ivFilter.setOnClickListener(filterClickListener)
        binding.tvFilter.setOnClickListener(filterClickListener)
    }

    fun obtenerData(){
        /*val preferences = getSharedPreferences(Constantes.SHARED_PREF, Context.MODE_PRIVATE)
        val id = preferences?.getInt(Constantes.SHARED_ID_USUARIO, 0)


        val str = "SELECT * FROM usuario where id=" +id
        val data = presentador.leer(str)
        val count = data.count
        Utils.dump("cantidad:" + count)
        if (data.moveToFirst()){
            do {
                val name = data.getString(data.getColumnIndex("name"))
                Utils.dump("name:" + name)
                val birthdate = data.getString(data.getColumnIndex("birthdate"))
                val email = data.getString(data.getColumnIndex("email"))
                val numPhone = data.getString(data.getColumnIndex("numPhone"))
                val img = data.getString(data.getColumnIndex("img"))
                Utils.dump(img.toString())

                /*val bm = BitmapFactory.decodeByteArray(img, 0, img.size)
                Utils.dump(bm.toString())*/
                val f = File(img)
                val b = BitmapFactory.decodeStream(FileInputStream(f))

                //!!.setImageBitmap(b)

            }while (data.moveToNext())
        }*/

        val categorias1 = Categorias()
        categorias1.id = 0
        categorias1.titulo = "Comida y Snacks"
        categorias1.descripcion = "Comida y variados"
        categorias1.img = "snack_comida"
        categoriasArray.add(categorias1)

        val categorias2 = Categorias()
        categorias2.id = 1
        categorias2.titulo = "Paseos y entrenamiento"
        categorias2.descripcion = "Paseos"
        categorias2.img = "paseo_perros"
        categoriasArray.add(categorias2)


        val categorias3 = Categorias()
        categorias3.id = 2
        categorias3.titulo = "Juguetes, ropa y accesorios"
        categorias3.descripcion = "juguetes y variados"
        categorias3.img = "juguetes_perros"
        categoriasArray.add(categorias3)

        val categorias4 = Categorias()
        categorias4.id = 3
        categorias4.titulo = "Veterinaria"
        categorias4.descripcion = "Veterinaria"
        categorias4.img = "veterinaria_img"
        categoriasArray.add(categorias4)

        val categorias5 = Categorias()
        categorias5.id = 4
        categorias5.titulo = "Juguetes, ropa y accesorios"
        categorias5.descripcion = "juguetes y variados"
        categorias5.img = "juguetes_perros"
        categoriasArray.add(categorias5)

        val categorias6 = Categorias()
        categorias6.id = 5
        categorias6.titulo = "Juguetes, ropa y accesorios"
        categorias6.descripcion = "juguetes y variados"
        categorias6.img = "juguetes_perros"
        categoriasArray.add(categorias6)

        val categorias7 = Categorias()
        categorias7.id = 6
        categorias7.titulo = "Juguetes, ropa y accesorios"
        categorias7.descripcion = "juguetes y variados"
        categorias7.img = "juguetes_perros"
        categoriasArray.add(categorias7)

        val categorias8 = Categorias()
        categorias8.id =7
        categorias8.titulo = "Juguetes, ropa y accesorios"
        categorias8.descripcion = "juguetes y variados"
        categorias8.img = "juguetes_perros"
        categoriasArray.add(categorias8)

        val categorias9 = Categorias()
        categorias9.id =8
        categorias9.titulo = "Cerrar sesión"
        categorias9.descripcion = "Cerrar sesión"
        categorias9.img = "ic_baseline_logout_24"
        categoriasArray.add(categorias9)

        promocionBanner.id = 0
        promocionBanner.titulo = "titulo"
        promocionBanner.descrpcion = "promo"
        promocionBanner.img = "juguetes_perros"

    }

    fun startRCVHome(){
        /*val mLayoutManager = GridLayoutManager(this,2)
        rcvHome?.setLayoutManager(mLayoutManager)
        rcvHomeService?.setLayoutManager(mLayoutManager)*/
        binding.rcvHome.setLayoutManager(LinearLayoutManager(context))
        binding.rcvHomeService.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false))

        homeListServiceAdapterType = HomeListServiceAdapter(categoriasArray,promocionBanner){ categorias ->

            val intent = Intent(context, DetailServiceActivity::class.java)
            startActivity(intent)
            /*when(categorias.id){
                0,2 -> {
                    val intent = Intent(this, MarketPlaceActivity::class.java)
                    startActivity(intent)
                }
                3 -> {
                    val intent = Intent(this, JourneyTipoOneActivity::class.java)
                    startActivity(intent)
                }
                8 -> {
                    val pref = applicationContext.getSharedPreferences(
                        Constantes.SHARED_PREF,
                        MODE_PRIVATE
                    )
                    pref.edit().clear().commit()

                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                else -> {
                    Toast.makeText(this,"Proximamente",Toast.LENGTH_LONG).show()
                }
            }*/

        }

        homeServiceAdapterType = HomeServiceAdapter(categoriasArray,promocionBanner){ categorias ->
            /*when(categorias.id){
                0,2 -> {
                    val intent = Intent(this, MarketPlaceActivity::class.java)
                    startActivity(intent)
                }
                3 -> {
                    val intent = Intent(this, JourneyTipoOneActivity::class.java)
                    startActivity(intent)
                }
                8 -> {
                    val pref = applicationContext.getSharedPreferences(
                        Constantes.SHARED_PREF,
                        Context.MODE_PRIVATE
                    )
                    pref.edit().clear().commit()

                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                else -> {
                    Toast.makeText(this,"Proximamente",Toast.LENGTH_LONG).show()
                }
            }*/



        }
        binding.rcvHome.setAdapter(homeListServiceAdapterType)
       binding.rcvHome.setItemAnimator(DefaultItemAnimator())

        binding.rcvHomeService.setAdapter(homeServiceAdapterType)
        binding.rcvHomeService.setItemAnimator(DefaultItemAnimator())
    }

    override fun onDestroy() {
        homeServiceAdapterType = null
        homeListServiceAdapterType = null
        super.onDestroy()
    }

    companion object {
        fun newInstance() : Fragment{
            val homeFragment = HomeFragment()
            return homeFragment
        }
    }
}