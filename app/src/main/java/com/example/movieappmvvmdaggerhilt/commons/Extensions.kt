package com.example.movieappmvvmdaggerhilt.commons

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

fun Activity.toast(message: String, duration:Int = Toast.LENGTH_SHORT) = Toast.makeText(this,message,duration).show()
fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(requireContext(),message, duration).show()
fun loge(message:String,tag:String = "ERROR") = Log.e(tag,message)
fun linear(context: Context) = LinearLayoutManager(context)
fun gridLayout(context: Context) = GridLayoutManager(context,3, LinearLayoutManager.VERTICAL,false)



fun ViewGroup.inflate(layout:Int) = LayoutInflater.from(context).inflate(layout,this,false)!!
fun Activity.preference(preferenceName:String) = this.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
fun Boolean.isSame(a:Int, b:Int) = a==b

fun SharedPreferences.Editor.putDouble(key: String, double: Double) =
    putLong(key, java.lang.Double.doubleToRawLongBits(double))

fun SharedPreferences.getDouble(key: String, default: Double) =
    java.lang.Double.longBitsToDouble(getLong(key, java.lang.Double.doubleToRawLongBits(default)))


//Despues del login
inline fun <reified T: Activity> Activity.goToHomeScreen(noinline init: Intent.() -> Unit = {}){
    val intent = Intent(this,T::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    intent.init()
    startActivity(intent)
    finish()
}

//Despues del login
inline fun <reified T: Activity> Fragment.goToHomeScreen(noinline init: Intent.() -> Unit = {}){
    val intent = Intent(requireActivity(),T::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    intent.init()
    startActivity(intent)
    requireActivity().finish()
}

//StartActivityFromFragment
inline fun <reified T: Activity> Fragment.goToActivityWResultX(code:Int, init: Intent.() -> Unit = {}){
    val intent = Intent(requireContext(),T::class.java)
    intent.init()
    startActivityForResult(intent,code)
}


//StartActivity
inline fun <reified T: Activity> Activity.goToActivity(noinline init: Intent.() -> Unit = {}){
    val intent = Intent(this,T::class.java)
    intent.init()
    startActivity(intent)
}
//Set activity for results
fun Activity.goToActivityWResult(action:String, code:Int, init: Intent.() -> Unit = {}){
    val intent = Intent(action)
    intent.init()
    startActivityForResult(intent,code)
}

inline fun <reified T: Activity> Activity.goToActivityWResultX(code:Int, init: Intent.() -> Unit = {}){
    val intent = Intent(this,T::class.java)
    intent.init()
    startActivityForResult(intent,code)
}


//Change Fragment
inline fun FragmentManager.doTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(frameId: Int, fragment: Fragment){
    supportFragmentManager.doTransaction { add(frameId, fragment) }
}


fun AppCompatActivity.replaceFragment(frameId: Int, fragment: Fragment) {
    supportFragmentManager.doTransaction{replace(frameId, fragment)}
}

fun AppCompatActivity.removeFragment(fragment: Fragment) {
    supportFragmentManager.doTransaction{remove(fragment)}
}

fun ContextWrapper.getRealURL(contentURI: Uri): String? {
    val result: String?
    val cursor: Cursor? = contentResolver.query(contentURI, null, null, null, null)
    if (cursor == null) { // Source is Dropbox or other similar local file path
        result = contentURI.path
    } else {
        cursor.moveToFirst()
        val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        result = cursor.getString(idx)
        cursor.close()
    }
    return result
}

fun AppCompatActivity.getNavController(id:Int): NavController = (supportFragmentManager.findFragmentById(id) as NavHostFragment).navController

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}