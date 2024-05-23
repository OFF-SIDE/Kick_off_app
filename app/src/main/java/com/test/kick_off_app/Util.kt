package com.test.kick_off_app

import android.content.Context
import android.widget.Toast
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