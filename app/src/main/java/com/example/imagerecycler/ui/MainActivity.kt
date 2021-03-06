package com.example.imagerecycler.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagerecycler.BuildConfig
import com.example.imagerecycler.R
import com.example.imagerecycler.adapter.GroupAdapter
import com.example.imagerecycler.databinding.ActivityMainBinding
import com.example.imagerecycler.model.PictureModel
import com.example.imagerecycler.util.PictureUtil
import com.example.imagerecycler.viewmodel.PictureViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    private val TAG = "janghee"

    private lateinit var binding: ActivityMainBinding
    private lateinit var pictureViewModel: PictureViewModel
    var uri: Uri? = null

    private val pictureUtil by lazy { PictureUtil() }
    private val groupAdapter by lazy { GroupAdapter(this) }
    private val REQUEST_PERMISSIONS = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    private val PICK_IMAGE = 123
    private val CAMERA = 124


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        pictureViewModel = ViewModelProvider(this).get(PictureViewModel::class.java)
        requestPermission()

        setupRecyclerView()
        readDatabase()

        binding.button.setOnClickListener {

        }

    }

    fun takePicture() {
        val capturedFile = File(filesDir, "captured.jpg")
        try {
            if (capturedFile.exists()) {
                capturedFile.delete()
            }
            capturedFile.createNewFile()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        uri = if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, capturedFile)
        } else {
            Uri.fromFile(capturedFile)
        }

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intent, CAMERA)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = groupAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }


    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, REQUEST_PERMISSIONS, PICK_IMAGE)
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
        if (requestCode == PICK_IMAGE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "true", Toast.LENGTH_SHORT).show()
            getAllPhotos()
        } else {
            Toast.makeText(this, "false", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getAllPhotos() {
        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null, null, null, null
        )

        val pictureList = ArrayList<PictureModel>()
        var index = 0
        if (cursor != null) {
            while (cursor.moveToNext()) {
                // ?????? ?????? ????????????
                val uri =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                val file = File(uri)
                val formatter = SimpleDateFormat("yyyy.MM.dd")
                val date: String = formatter.format(file.lastModified())

                Log.d(TAG, "?????? ??????: " + date)
                pictureList.add(PictureModel(id = index, date = date, path = uri))
                index++
            }
            cursor.close()
        }

        Log.d(TAG, "?????? ????????? ${pictureList.size}")
        val pictureGroupModelList = pictureUtil.setDataTitle(pictureList)
        // TODO: ????????? ?????? ??????
        pictureViewModel.setPictureGroupData(pictureGroupModelList)
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            pictureViewModel.pictureGroupLiveData.observe(this@MainActivity, { dataList ->
                if (dataList.isNotEmpty()) {
                    groupAdapter.setGroup(dataList)
                    for (picture in dataList) {
                        pictureViewModel.setPictureData(picture.pictureList)
                    }
                }
            })
        }
    }

}