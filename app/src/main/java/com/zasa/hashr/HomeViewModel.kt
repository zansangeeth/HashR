package com.zasa.hashr

import android.util.Log
import androidx.lifecycle.ViewModel
import java.security.MessageDigest

/**
 **@Project -> HashR
 **@Author -> Sangeeth on 6/27/2022
 */
class HomeViewModel : ViewModel() {

    fun getHash(plainText : String, algorithm : String) : String{
        val bytes = MessageDigest.getInstance(algorithm).digest(plainText.toByteArray())
        return toHex(bytes)
    }

    private fun toHex(byteArray: ByteArray) : String{
        Log.i("ViewModel", byteArray.joinToString("") { "%02x".format(it) })
        return byteArray.joinToString { "%02x".format(it) }
    }

}