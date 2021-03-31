package com.example.imagerecycler.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.example.imagerecycler.R
import com.example.imagerecycler.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val REQUEST_PERMISSIONS = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val IMAGE_PICK = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.button.setOnClickListener {
            // 권한 체크
            if (checkPermission()) {
                getStorage()
            } else {
                requestPermission()
            }
        }

    }

    fun getStorage() {
        val intent: Intent = Intent(Intent.ACTION_PICK)
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE)
        startActivityForResult(intent, IMAGE_PICK);
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, REQUEST_PERMISSIONS, IMAGE_PICK)
    }

    private fun checkPermission(): Boolean {
        val readStoragePermission = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return readStoragePermission == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "true", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "false", Toast.LENGTH_SHORT).show()
        }
    }
}