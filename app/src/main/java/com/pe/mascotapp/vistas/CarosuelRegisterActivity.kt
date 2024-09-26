package com.pe.mascotapp.vistas

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.viewpager.widget.ViewPager
import com.google.android.material.button.MaterialButton
import com.pe.mascotapp.R
import com.pe.mascotapp.utils.Utils
import com.pe.mascotapp.vistas.fragments.CarosuelFragmentRegisterState

import android.view.View.OnTouchListener
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.textfield.TextInputLayout
import com.pe.mascotapp.interfaces.OnEditTextChanged
import com.pe.mascotapp.modelos.Usuario
import androidx.viewpager.widget.PagerAdapter
import com.pe.mascotapp.interfaces.PrincipalPresentador
import java.io.ByteArrayOutputStream
import android.view.WindowManager
import com.pe.mascotapp.utils.Constantes
import android.graphics.Bitmap.CompressFormat
import android.content.ContextWrapper
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.pe.mascotapp.vistas.entities.PetEntity
import com.pe.mascotapp.vistas.entities.PetWithBreedsEntity
import com.pe.mascotapp.vistas.fragments.stepRegister.StepOne
import com.pe.mascotapp.vistas.fragments.stepRegister.StepThree
import com.pe.mascotapp.vistas.fragments.stepRegister.StepTwo
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception


class CarosuelRegisterActivity : AppCompatActivity(), OnEditTextChanged, RegisterPets {
    override var listPets: List<PetWithBreedsEntity> = listOf(
        PetWithBreedsEntity(
            PetEntity(),
            listOf()
        )
    )

    var viewPStep: ViewPager2? = null
    var btnVolver: TextView? = null
    var btnSiguiente: MaterialButton? = null
    var bottomButtons: LinearLayout? = null
    var usuario: Usuario? = null
    var edtNombreOne = ""
    var edtFechaOne = ""
    var radioGroupOne = ""
    var imgOne = ""

    var edtNombreTwo = ""
    var edtRazaTwo = ""
    var radioGroupTwo = ""
    var imgTwo = ""
    var imgTwoSelected: Uri? = null

    var edtPesoThree = ""
    var radioGroupThree = ""
    var edtFechaThree = ""

    var edtEmail = ""
    var pass = ""
    var pass2 = ""
    var numCel = ""

    //var tabLayout: TabLayout? = null
    //var vista = 0
    private lateinit var stepFmList: List<Fragment>
    private lateinit var presentador: PrincipalPresentador.VistaStart
    private var isFromMainPets: Boolean = false
    var indexEdit:Int? = null
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carosuel_register)

        //usuario = intent!!.getSerializableExtra("OBJECT_USUARIO") as Usuario
        presentador = PrincipalPresentador.VistaStart(this)

        //Utils.dump(usuario!!.email)

        viewPStep = findViewById<ViewPager2>(R.id.viewPStep)
        //tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        btnVolver = findViewById<TextView>(R.id.btnVolver)
        btnSiguiente = findViewById<MaterialButton>(R.id.btnSiguiente)
        bottomButtons = findViewById<LinearLayout>(R.id.bottomButtons)
        isFromMainPets = intent.extras?.getBoolean("isFromMainPets", false) ?: false
        stepFmList = if (isFromMainPets) listOf(
            StepTwo.newInstance("Paso 1"),
            StepThree.newInstance("Paso 2")
        ) else listOf(
            StepOne.newInstance("Paso 1"),
            StepTwo.newInstance("Paso 2"),
            StepThree.newInstance("Paso 3")
        )
        viewPStep!!.adapter = CarosuelFragmentRegisterState(stepFmList, this)
        viewPStep!!.isUserInputEnabled = false
        //tabLayout!!.setupWithViewPager(viewPStep, true);

        btnVolver!!.setOnClickListener {
            onBackStep()
        }

        btnSiguiente!!.setOnClickListener {
            nextStep()
        }

        viewPStep!!.setOnTouchListener(OnTouchListener { arg0, arg1 -> true })

        viewPStep!!.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                Utils.dump("posicion por scrolear onPageSelected " + position)

                if (position == viewPStep!!.adapter?.itemCount?.minus(1)) {

                    btnSiguiente!!.setOnClickListener {
                        /*val str1 = "INSERT INTO usuario(name, birthdate, email,pass, numPhone, img, sex ) VALUES\n"+
                                "('"+ edtNombreOne +"','"+ edtFechaOne +"','"+ usuario!!.email +"','"+ usuario!!.pass +"','"+ usuario!!.numPhone +"','"+ imgOne +"','"+ radioGroupOne+"')"
                        val idUsuario = presentador.insertar(str1)

                        val str2 = "INSERT INTO mascota(name, tipo, raza,weight, birthdate, img, sex) VALUES\n"+
                                "('"+ edtNombreTwo +"','"+ radioGroupTwo +"','"+ edtRazaTwo +"','"+ edtPesoThree +"','"+ edtFechaThree +"','"+ imgTwo +"','"+ radioGroupThree+"')"
                        val idMascota = presentador.insertar(str2)

                        val str3 = "INSERT INTO mascotaUsuario(idUsuario, idMascota) VALUES\n"+
                                "("+ idUsuario +","+ idMascota +")"
                        val idMascotaUsuario = presentador.insertar(str3)

                        val preferences = getSharedPreferences(Constantes.SHARED_PREF, Context.MODE_PRIVATE)
                        with (preferences.edit()) {
                            putBoolean(Constantes.SHARED_PREF_SUCCESS, true)
                            putString(Constantes.SHARED_PREF_MESSAGE, "logeado")
                            putInt(Constantes.SHARED_ID_USUARIO, idUsuario)
                            commit()
                        }*/

                        //showDialog()

                        finishStep()
                    }
                    btnVolver!!.setOnClickListener {
                        backStep()
                    }
                } else {
                    btnSiguiente!!.setOnClickListener {
                        nextStep()
                    }
                    btnVolver!!.setOnClickListener {
                        backStep()
                    }
                }

            }

        })

    }


    fun nextStep() {
        viewPStep!!.setCurrentItem(getItem(+1), true)
/*        if (viewPStep!!.currentItem != 0) {
            bottomButtons!!.visibility = View.GONE
            return
        }
        bottomButtons!!.visibility = View.VISIBLE*/
    }

    private fun backStep() {
        viewPStep!!.setCurrentItem(getItem(-1), true)
/*        if (viewPStep!!.currentItem != 0) {
            bottomButtons!!.visibility = View.GONE
            return
        }
        bottomButtons!!.visibility = View.VISIBLE*/
    }

    private fun finishStep() {
        if (isFromMainPets) onBackStep()
        else finishActivity()
    }

    private fun onBackStep() {
        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
        /*        val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)*/
    }

    private fun finishActivity() {

        val intent = Intent(this, LoadingActivity::class.java)
        startActivity(intent)

        /*when(vista){
            0 -> {
                val intent = Intent(this, HomeStartActivity::class.java)
                startActivity(intent)
            }
            1 -> {
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }*/


    }
    fun addPet(){
        indexEdit = null
        onBackPressed()
    }

    fun editPet(index:Int){
        indexEdit = index
        onBackPressed()
    }

    private fun getItem(i: Int): Int {
        Utils.dump("posicion" + viewPStep?.currentItem)
        if (viewPStep?.currentItem == 2) {
            btnSiguiente!!.setOnClickListener {
                finishStep()
            }
        } else if (viewPStep?.currentItem == 0) {

            btnVolver!!.setOnClickListener {
                onBackStep()
            }
            btnSiguiente!!.setOnClickListener {
                nextStep()
            }
        } else {
            btnSiguiente!!.setOnClickListener {
                nextStep()
            }
            btnVolver!!.setOnClickListener {
                backStep()
            }
        }
        return viewPStep!!.currentItem + i
    }

    override fun onTextChanged(txt: String, step: Int, input: String) {
        Utils.dump("Llegó " + txt)

        when (step) {
            1 -> {
                if (input.equals("edtNombre")) {
                    edtNombreOne = txt
                }
                if (input.equals("edtFecha")) {
                    edtFechaOne = txt
                }

                if (input.equals("radioGroup")) {
                    radioGroupOne = txt
                }
            }

            2 -> {
                if (input.equals("edtNombre")) {
                    edtNombreTwo = txt
                }
                if (input.equals("autoRaza")) {
                    edtRazaTwo = txt
                }

                if (input.equals("radioGroup")) {
                    radioGroupTwo = txt
                }
            }

            3 -> {
                if (input.equals("edtFecha")) {
                    edtFechaThree = txt
                }
                if (input.equals("edtPeso")) {
                    edtPesoThree = txt
                }

                if (input.equals("radioGroup")) {
                    radioGroupThree = txt
                }


            }
        }

    }

    override fun onImageChange(step: Int, img: Uri) {

        when (step) {
            1 -> {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, img)
                imgOne = saveToInternalStorage(bitmap)

                /*val out = ByteArrayOutputStream()

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                val buffer: ByteArray = out.toByteArray()
                Utils.dump(buffer.toString())
                imgOne = buffer*/

            }

            2 -> {
                imgTwoSelected = img
                viewPStep!!.adapter!!.notifyDataSetChanged()
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, img)

                imgTwo = saveToInternalStorage(bitmap)
                /*val out = ByteArrayOutputStream()

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                val buffer: ByteArray = out.toByteArray()
                imgTwo = buffer*/
            }
        }
    }

    private fun saveToInternalStorage(bitmapImage: Bitmap): String {
        val cw = ContextWrapper(applicationContext)
        val currentTimestamp = System.currentTimeMillis()
        // path to /data/data/yourapp/app_data/imageDir
        val directory: File = cw.getDir("imageMascotaAppDir", MODE_PRIVATE)
        // Create imageDir
        val mypath = File(directory, "image_user_" + currentTimestamp + ".jpg")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (fos != null) {
                    fos.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return directory.absolutePath + "/" + "image_user_" + currentTimestamp + ".jpg"
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_custom_modal_register_gracias)
        val btn = dialog.findViewById(R.id.btnSiguiente_gracias) as Button
        btn.setOnClickListener {
            dialog.dismiss()
            finishStep()
        }
        val lp = WindowManager.LayoutParams()
        val window = dialog.window
        lp.copyFrom(window!!.attributes)
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = lp

        dialog.show()

    }
    fun updateUsuario(updatedUsuario: Usuario) {
        usuario = updatedUsuario
    }

    override fun onBackPressed() {
        if (viewPStep!!.currentItem == 0) {
            super.onBackPressed()
        }
        else backStep()
    }

}

interface RegisterPets {
    var listPets: List<PetWithBreedsEntity>
}