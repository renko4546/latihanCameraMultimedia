package padila1ilham.com.latihancameramultimedia

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        img_pick_btn.setOnClickListener {
            var i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            startActivityForResult(i, 123)

        }

        button.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkCallingOrSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED
                ) {
                    val permissions =
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                    requestPermissions(permissions, PERMESSION_CODE);
                } else {
                    pickImageFromGallery();
                }
            } else {
                pickImageFromGallery();
            }
        }

    }
    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,IMAGE_PICK_CODE)
    }

    companion object{
        private  val IMAGE_PICK_CODE = 1000;
        private  val PERMESSION_CODE = 1001;
    }


        override fun onRequestPermissionsResult(requestCode : Int, permissions: Array<out String>, grantResults:IntArray)
        {
            when(requestCode){
                PERMESSION_CODE -> {
                    if (grantResults.size >0 && grantResults [0]
                    == PackageManager.PERMISSION_GRANTED){
                        pickImageFromGallery()
                    }
                    else{
                        Toast.makeText(this,"Permissiondenied",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if(requestCode==123){
                var bmp =data?.extras?.get("data") as? Bitmap
                imageView.setImageBitmap(bmp)
            }
            if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
                imageView.setImageURI(data?.data)
            }
        }
}
