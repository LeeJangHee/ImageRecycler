package com.example.imagerecycler.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagerecycler.R
import com.example.imagerecycler.adapter.MainAdapter
import com.example.imagerecycler.databinding.ActivityMainBinding
import com.example.imagerecycler.model.PictureModel
import com.example.imagerecycler.viewmodel.PictureViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = "janghee"

    private lateinit var binding: ActivityMainBinding
    private lateinit var pictureViewModel: PictureViewModel

    private val mainAdapter by lazy { MainAdapter(this) }
    private val REQUEST_PERMISSIONS = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val IMAGE_PICK = 123



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        pictureViewModel = ViewModelProvider(this).get(PictureViewModel::class.java)
        requestPermission()

        setupRecyclerView()
        readDatabase()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = mainAdapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 4)
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
        if (requestCode == IMAGE_PICK && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "true", Toast.LENGTH_SHORT).show()
            getAllPhotos()
        } else {
            Toast.makeText(this, "false", Toast.LENGTH_SHORT).show()
        }
    }

//    fun getStorage() {
//        val intent = Intent("android.intent.action.GET_CONTENT")
//        intent.type = "image/*"
//        startActivityForResult(intent, IMAGE_PICK)
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when (requestCode) {
//            IMAGE_PICK -> {
//                if (resultCode == Activity.RESULT_OK) {
//                    if (Build.VERSION.SDK_INT >= 19) {
//                        handleImage(data)
//                    }
//                }
//            }
//        }
//    }

//    @TargetApi(19)
//    fun handleImage(data: Intent?) {
//        var imagePath: String? = null
//        val uri = data?.data
//
//        if (uri != null) {
//            if (DocumentsContract.isDocumentUri(this, uri)) {
//                val docId = DocumentsContract.getDocumentId(uri)
//                if (uri.authority == "com.android.providers.media.documents") {
//                    val id = docId.split(":")[1]
//                    val selection = MediaStore.Images.Media._ID + "=" + id
//                    imagePath = getImagePath(
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                        selection
//                    )
//                } else if (uri.authority == "com.android.providers.downloads.documents") {
//                    val contentUri = ContentUris.withAppendedId(
//                        Uri.parse(
//                            "content://downloads/public_downloads"
//                        ), docId.toLong()
//                    )
//                    imagePath = getImagePath(contentUri, null)
//                }
//            } else if ("content".equals(uri.scheme, ignoreCase = true)) {
//                imagePath = getImagePath(uri, null)
//            } else if ("file".equals(uri.scheme, ignoreCase = true)) {
//                imagePath = uri.path
//            }
//
//            val bitmap = BitmapFactory.decodeFile(imagePath)
//            Log.d(TAG, "handleImage: " + bitmap.byteCount)
//        }
//    }

//    private fun getImagePath(uri: Uri, selection: String?): String? {
//        var path: String? = null
//        val cursor = contentResolver.query(uri, null, selection, null, null)
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
//            }
//            cursor.close()
//        }
//
//        return path
//    }

    private fun getAllPhotos() {
        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null, null, null, null
        )

        val pictureList = ArrayList<PictureModel>()
        var index = 0
        if (cursor != null) {
            while (cursor.moveToNext()) {
                // 사진 경로 가져오기
                val uri =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                val date =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN))
                pictureList.add(PictureModel(id = index, date = date, path = uri))
                index++
            }
            cursor.close()
        }
        // TODO: 데이터 변경 부분
        pictureViewModel.setPictureData(pictureList)
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            pictureViewModel.pictureLiveData.observe(this@MainActivity, { dataList ->
                if (dataList.isNotEmpty()) {
                    mainAdapter.setData(dataList)
                }
            })
        }
    }
}