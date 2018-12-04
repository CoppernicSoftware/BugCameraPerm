package fr.coppernic.template.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.method.ScrollingMovementMethod
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.EmptyPermissionRequestErrorListener
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener
import fr.coppernic.app.askey.bug.camera.R
import fr.coppernic.template.App
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

private const val MY_PERMISSIONS_REQUEST_CAMERA = 0

class HomeActivity : AppCompatActivity() {

    private val permissionListener = object : BasePermissionListener() {
        @SuppressLint("SetTextI18n")
        override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
            Timber.i("onPermissionGranted")
            log("${permissionGrantedResponse.permissionName} granted")
        }

        @SuppressLint("SetTextI18n")
        override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {
            Timber.i("onPermissionDenied")
            log("${permissionDeniedResponse.permissionName} denied")
        }
    }

    private val errorListener = EmptyPermissionRequestErrorListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.appComponents.inject(this)

        tv.movementMethod = ScrollingMovementMethod()
        btnDexter.setOnClickListener { askingForPermissionDexter() }
        btnAndroid.setOnClickListener { askingForPermissionAndroid() }
    }

    override fun onStart() {
        super.onStart()

        checkPermission()
    }

    private fun askingForPermissionDexter() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(permissionListener)
                .withErrorListener(errorListener)
                .onSameThread()
                .force()
                .check()
    }

    private fun askingForPermissionAndroid() {
        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CAMERA),
                    MY_PERMISSIONS_REQUEST_CAMERA)

            // MY_PERMISSIONS_REQUEST_CAMERA is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }

    private fun checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            log("Camera permission is not granted => KO, it shall be granted because this is a system app")
        } else {
            log("Camera permission is already granted => OK")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun log(s: String){
        tv.text =  "${tv.text}\n$s"
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CAMERA -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    log("Camera permission is granted => OK")
                } else {
                    log("Camera permission is denied => :-(")
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
}
