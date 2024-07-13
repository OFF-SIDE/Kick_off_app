package com.test.kick_off_app.functions

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.kakao.sdk.user.model.User
import com.test.kick_off_app.data.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStream
import java.net.URL
import java.io.InputStream
import java.io.BufferedInputStream
import java.io.FileOutputStream

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

suspend fun downloadImage(context: Context, url: String, filename: String) {
    withContext(Dispatchers.IO) {
        val connection = URL(url).openConnection()
        connection.connect()
        val input: InputStream = BufferedInputStream(URL(url).openStream(), 8192)
        val output: OutputStream = FileOutputStream(context.getFilesDir().toString() + "/" + filename)
        val data = ByteArray(1024)
        var count: Int
        while (input.read(data).also { count = it } != -1) {
            output.write(data, 0, count)
        }
        output.flush()
        output.close()
        input.close()
    }
}

class SharedPrefManager private constructor(context: Context) {
    private val prefs: SharedPreferences

    init {
        val masterKeyAlias = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        prefs = EncryptedSharedPreferences.create(
            context,
            "MyEncryptedPrefs",
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun putAccessToken(accessToken: String) {
        prefs.edit().putString("access_token", accessToken).apply()
    }

    fun getAccessToken(): String? {
        return prefs.getString("access_token", null)
    }

    fun putUserInfo(userInfo: UserInfo){
        with(prefs.edit()){
            putInt("id",userInfo.id!!)
            putString("name",userInfo.name)
            putString("nickname",userInfo.nickname)
            putString("location",userInfo.location)
            putString("category",userInfo.category)
            apply()
        }
    }

    fun getUserInfo(): UserInfo? {
        val id = prefs.getInt("id", -1)
        val name = prefs.getString("name", null)
        val nickname = prefs.getString("nickname", null)
        val location = prefs.getString("location", null)
        val category = prefs.getString("category", null)

        return if (id != -1 && name != null && nickname != null && location != null && category != null) {
            UserInfo(id, name, nickname, location, category)
        } else {
            null
        }
    }

    companion object {
        private lateinit var instance: SharedPrefManager
        fun init(context: Context){
            instance = SharedPrefManager(context)
        }
        fun getInstance(): SharedPrefManager {
            return instance
        }
    }
}