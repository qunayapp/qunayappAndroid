package com.pe.mascotapp.interfaces

import android.net.Uri

interface OnEditTextChanged {
    fun onTextChanged(txt:String,step:Int,input:String)
    fun onImageChange(step:Int,img:Uri)
}