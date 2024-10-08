package com.test.kick_off_app.functions

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.test.kick_off_app.data.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.OutputStream
import java.net.URL
import java.io.InputStream
import java.io.BufferedInputStream
import java.io.File
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
    private var listener: SharedPreferences.OnSharedPreferenceChangeListener? = null

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

    fun registerPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        this.listener = listener
        prefs.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterPreferenceChangeListener() {
        listener?.let {
            prefs.unregisterOnSharedPreferenceChangeListener(it)
            listener = null
        }
    }

    fun putAccessToken(accessToken: String) {
        prefs.edit().putString("access_token", accessToken).apply()
    }

    fun getAccessToken(): String? {
        return prefs.getString("access_token", null)
    }

    fun putUserInfo(userInfo: UserInfo){
        prefs.edit().apply{
            putLong("id",userInfo.id!!)
            putString("name",userInfo.name)
            putString("nickname",userInfo.nickname)
            putString("location",userInfo.location)
            putString("category",userInfo.category)
            apply()
        }
    }

    fun getUserInfo(): UserInfo? {
        val id = prefs.getLong("id", -1)
        val name = prefs.getString("name", null)
        val nickname = prefs.getString("nickname", null)
        val location = prefs.getString("location", null)
        val category = prefs.getString("category", null)

        return if (id != -1L && name != null && nickname != null && location != null && category != null) {
            UserInfo(id, name, nickname, location, category)
        } else {
            null
        }
    }

    fun getCategory(): String?{
        return prefs.getString("current_category", null)
    }

    fun setCategory(category: String){
        prefs.edit()
            .putString("current_category", category)
            .apply()
    }

    fun getProfileImage(): Uri?{
        val uriString = prefs.getString("profile_image", null)
        return if(uriString != null) Uri.parse(uriString) else null

    }

    fun setProfileImage(profileImage: Uri){
        prefs.edit()
            .putString("profile_image", profileImage.toString())
            .apply()
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

fun getRequestBodyFromUri(context: Context, uri: Uri): RequestBody?{
    val path = getRealPathFromURI(context, uri)
    if(path.isEmpty()){
        return null
    }
    val file = File(path)
    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
    //val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

    return requestFile
}


// URI에서 실제 파일 경로를 얻는 함수
fun getRealPathFromURI(context: Context, uri: Uri): String {

    var path = ""
    val cursor = context?.contentResolver?.query(uri, null, null, null, null)
    /*
    if (cursor != null) {
        cursor.moveToFirst()
        val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        path = cursor.getString(index)
        cursor.close()
    }
     */
    cursor?.use {
        if (it.moveToFirst()) {
            val index = it.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            if (index != -1) {
                path = it.getString(index)
            }
        }
    }
    return path
}