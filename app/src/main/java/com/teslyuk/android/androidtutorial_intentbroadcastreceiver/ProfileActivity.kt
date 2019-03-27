package com.teslyuk.android.androidtutorial_intentbroadcastreceiver

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.util.Log
import android.widget.Toast

import java.io.File

/**
 * Created by teslyuk.taras on 5/15/18.
 */

class ProfileActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private const val REQUEST_CODE_IMAGE_CAMERA = 1
        private const val REQUEST_CODE_IMAGE_GALLERY = 2
        private val TAG = ProfileActivity::class.java.simpleName
    }

    private lateinit var avatarIv: ImageView
    private lateinit var callIv: ImageView
    private lateinit var chatIv: ImageView
    private lateinit var shareIv: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initView()
        initListener()
    }

    private fun initView() {
        avatarIv = findViewById(R.id.user_profile_iv)
        callIv = findViewById(R.id.action_call_iv)
        chatIv = findViewById(R.id.action_chat_iv)
        shareIv = findViewById(R.id.action_share_iv)
    }

    private fun initListener() {
        avatarIv.setOnClickListener(this)
        callIv.setOnClickListener(this)
        chatIv.setOnClickListener(this)
        shareIv.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.user_profile_iv -> getNewAvatar()
            R.id.action_call_iv -> makeCall()
            R.id.action_chat_iv -> startChat()
            R.id.action_share_iv -> shareContact()
        }
    }

    private fun getNewAvatar() {
        val builder = AlertDialog.Builder(this)
                .setTitle("Change avatar")
                .setMessage("Please select option to change avatar.")
                .setIcon(R.mipmap.ic_launcher_round)
                .setPositiveButton("Camera") { dialogInterface, i -> getNewAvatarFromCamera() }
                .setNegativeButton("Gallery") { dialogInterface, i -> getNewAvatarFromGallery() }
        val dialog = builder.create()
        dialog.show()
    }

    private fun getNewAvatarFromCamera() {
        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val uri = Uri.parse("file:///sdcard/photo.jpg")
        takePicture.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(takePicture, REQUEST_CODE_IMAGE_CAMERA)
    }

    private fun getNewAvatarFromGallery() {
        val pickPhoto = Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickPhoto, REQUEST_CODE_IMAGE_GALLERY)//one can be replaced with any action code
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            Log.d(TAG, "RESULT_OK")
            val selectedImage: Uri?
            when (requestCode) {
                REQUEST_CODE_IMAGE_CAMERA -> {
                    Log.d(TAG, "REQUEST_CODE_IMAGE_CAMERA")
                    val file = File(Environment.getExternalStorageDirectory().path, "photo.jpg")
                    val uri = Uri.fromFile(file)
                    //                    selectedImage = data.getData();
                    avatarIv.setImageURI(uri)
                }
                REQUEST_CODE_IMAGE_GALLERY -> {
                    Log.d(TAG, "REQUEST_CODE_IMAGE_GALLERY")
                    selectedImage = data!!.data
                    avatarIv.setImageURI(selectedImage)
                }
            }
        } else {
            Log.d(TAG, "RESULT_NE OK")
        }
    }

    private fun makeCall() {
        val phone = "+380671234567"
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
        startActivity(intent)
    }

    private fun startChat() {
        try {
            val phone = "+380671234567"
            val message = "Hello"

            val sendIntent = Intent(Intent.ACTION_VIEW)
            sendIntent.putExtra("address", phone)
            sendIntent.putExtra("sms_body", message)
            sendIntent.type = "vnd.android-dir/mms-sms"
            startActivity(sendIntent)
        } catch (e: Exception) {
            Toast.makeText(this@ProfileActivity, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show()
        }

    }

    private fun shareContact() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }
}