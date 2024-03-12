package com.pe.mascotapp.vistas

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.pe.mascotapp.R
import com.pe.mascotapp.interfaces.PrincipalPresentador
import com.pe.mascotapp.modelos.Categorias
import com.pe.mascotapp.modelos.PromocionBanner
import com.pe.mascotapp.notifications.WorkManagerScheduler
import com.pe.mascotapp.utils.Constantes
import com.pe.mascotapp.utils.Utils
import com.pe.mascotapp.vistas.adapters.HomeAdapter
import com.pe.mascotapp.vistas.adapters.HomeListServiceAdapter
import com.pe.mascotapp.vistas.adapters.HomeServiceAdapter
import com.pe.mascotapp.vistas.fragments.home.HomeFragment
import com.pe.mascotapp.vistas.fragments.home.ReminderFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileInputStream

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    var menuHome: BottomNavigationView? = null
    var toolbar: Toolbar?= null
    var drawer_layout:DrawerLayout ?= null
    var rcvHome: RecyclerView?= null
    var rcvHomeService:RecyclerView ?= null
    var homeAdapterType:HomeAdapter ?= null
    var homeListServiceAdapterType: HomeListServiceAdapter?= null
    var homeServiceAdapterType:HomeServiceAdapter ?= null
    var categoriasArray: ArrayList<Categorias> = ArrayList()
    var promocionBanner:PromocionBanner = PromocionBanner()
    //var imgUser:ImageView ?= null
    var imgBanner:ImageView ?= null
    var navigationView: NavigationView? = null

    private val onNavigationItemSelectedListener =
        NavigationView.OnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            Utils.dump("item: " + item.itemId)
            when (item.itemId) {

                R.id.nav_home -> {

                    selectedFragment = HomeFragment.newInstance()
                    //title = R.string.menu_camera;
                    supportFragmentManager
                        .beginTransaction() //.setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.fragmentContainer, selectedFragment)
                        .commit()

                    //setTitle(getString(title));
                    drawer_layout!!.closeDrawer(GravityCompat.START)

                }

                /*R.id.nav_sobre_mimp -> {

                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gob.pe/mimp"))
                    startActivity(browserIntent)

                }

                R.id.nav_programa_nacional_aurora -> {

                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gob.pe/aurora"))
                    startActivity(browserIntent)

                }*/

                /*R.id.nav_perfil -> {
                    selectedFragment = AccountFragment.newInstance()
                    //title = R.string.menu_camera;
                    supportFragmentManager
                        .beginTransaction() //.setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.fragmentContainer, selectedFragment)
                        .commit()

                    //setTitle(getString(title));
                    drawer_layout!!.closeDrawer(GravityCompat.START)
                }*/
                /*R.id.nav_notification -> {
                    selectedFragment = ReminderFragment.newInstance()
                    //title = R.string.menu_gallery;
                    supportFragmentManager
                        .beginTransaction() //.setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.fragmentContainer, selectedFragment)
                        .commit()

                    //setTitle(getString(title));
                    drawer_layout!!.closeDrawer(GravityCompat.START)
                }*/

                /*R.id.nav_prev_violencia -> {

                    val intent = Intent(this, PrevencionViolenciaActivity::class.java)
                    startActivity(intent)

                }*/
                /*R.id.nav_wp -> {
                }*/
                else -> throw IllegalArgumentException("menu option not implemented!!")
            }
            true
        }



    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            /*val menuItem1 = menuHome!!.menu.findItem(R.id.nav_home)
            val newIcon1 = menuItem1.icon
            val menuItem2 = menuHome!!.menu.findItem(R.id.nav_config)
            val newIcon2 = menuItem2.icon
            //val menuItem4 = menuHome!!.menu.findItem(R.id.nav_toximetro)
            //val newIcon4 = menuItem4.icon
            //Utils.dump("item: " + item.itemId)*/
            when (item.itemId) {
                R.id.nav_home -> {
                    selectedFragment = HomeFragment.newInstance()
                    //title = R.string.menu_camera;
                    supportFragmentManager
                        .beginTransaction() //.setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.fragmentContainer, selectedFragment)
                        .commit()

                    //setTitle(getString(title));
                    drawer_layout!!.closeDrawer(GravityCompat.START)

                }
                R.id.nav_history -> {

                }

                R.id.nav_calendar -> {

                }

                R.id.nav_pet -> {

                }
                R.id.nav_notification -> {
                    selectedFragment = ReminderFragment.newInstance()
                    //title = R.string.menu_gallery;
                    supportFragmentManager
                        .beginTransaction() //.setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.fragmentContainer, selectedFragment)
                        .commit()

                    //setTitle(getString(title));
                    drawer_layout!!.closeDrawer(GravityCompat.START)
                }
            }
            /*val transaction =
                supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, selectedFragment!!)
            transaction.commit()*/
            true
        }

    private lateinit var presentador: PrincipalPresentador.VistaStart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        menuHome = findViewById<BottomNavigationView>(R.id.menuHome)
        rcvHome = findViewById<RecyclerView>(R.id.rcvHome)
        rcvHomeService = findViewById<RecyclerView>(R.id.rcvHomeService)
        //imgUser = findViewById<ImageView>(R.id.imgUser)
        imgBanner = findViewById<ImageView>(R.id.imgBanner)
        presentador = PrincipalPresentador.VistaStart(this)

        checkBattery(this)
        //obtenerData()
        //startRCVHome()
        iniciarvista(savedInstanceState)
    }
    @SuppressLint("Range")
    fun obtenerData(){
        val preferences = getSharedPreferences(Constantes.SHARED_PREF, Context.MODE_PRIVATE)
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
        }

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
        rcvHome?.setLayoutManager(LinearLayoutManager(this))
        rcvHomeService?.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false))

        homeListServiceAdapterType = HomeListServiceAdapter(categoriasArray,promocionBanner){ categorias ->

            val intent = Intent(this, DetailServiceActivity::class.java)
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
        rcvHome?.setAdapter(homeListServiceAdapterType)
        rcvHome?.setItemAnimator(DefaultItemAnimator())

        rcvHomeService?.setAdapter(homeServiceAdapterType)
        rcvHomeService?.setItemAnimator(DefaultItemAnimator())
    }

    fun byteArrayToBitmap(data: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(data, 0, data.size)
    }

    fun iniciarvista(savedInstanceState: Bundle?){
        menuHome = findViewById<BottomNavigationView>(R.id.menuHome)
        navigationView = findViewById<NavigationView>(R.id.navigationView)
        drawer_layout = findViewById<DrawerLayout>(R.id.drawer_layout)
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        menuHome!!.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        startMenu(savedInstanceState)

        val navViewBackground = navigationView?.background as MaterialShapeDrawable
        navViewBackground.shapeAppearanceModel = navViewBackground.shapeAppearanceModel
            .toBuilder()
            .setBottomRightCorner(CornerFamily.ROUNDED, 528f)
            .build()

        navigationView!!.setNavigationItemSelectedListener(onNavigationItemSelectedListener)

        startMenu(savedInstanceState)

        val toggle =
            ActionBarDrawerToggle(
                this,
                drawer_layout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )

        drawer_layout!!.addDrawerListener(toggle)
        toggle.syncState()


    }

    fun startMenu(savedInstanceState: Bundle?) {

        if (savedInstanceState == null) {
            val transaction =
                supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, HomeFragment.newInstance())
            transaction.commit()
            //toolTitle!!.text = "Hola, " + "Usuario"
            //menuHome!!.selectedItemId = R.id.nav_home
            //navigationView!!.setCheckedItem(R.id.nav_menu_principal)
        }

    }

    private fun isBatteryOptimized(context: Context): Boolean {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val name = context.packageName
        return !powerManager.isIgnoringBatteryOptimizations(name)
    }

    private fun checkBattery(context: Context) {
        if (isBatteryOptimized(context)) {
            val name = context.resources.getString(R.string.app_name)
            Toast.makeText(context, "Battery optimization -> All apps -> $name -> Don't optimize", Toast.LENGTH_LONG).show()

            val intent = Intent(ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
            context.startActivity(intent)
            return
        }
        WorkManagerScheduler.scheduleWorker(application.applicationContext)
    }

}