package com.example.gpslocation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gpslocation.adapters.SimpleStringsAdapter

class MainActivity : AppCompatActivity(), LocationListener {
    val LOCATION_PERM_CODE = 2
    lateinit var providerStatusTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // запрашиваем разрешения на доступ к геопозиции
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            // переход в запрос разрешений
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERM_CODE)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
        val allProviders = locationManager.allProviders
        val providersRv = findViewById<RecyclerView>(R.id.providers_rv)
        val rvAdapter = SimpleStringsAdapter(allProviders.toTypedArray())
        providersRv.adapter = rvAdapter
        providersRv.layoutManager = LinearLayoutManager(this)
        providerStatusTv = findViewById(R.id.status_tv)
        //val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val prv = locationManager.getBestProvider(Criteria(), true)
        if (prv != null) {
            val location = locationManager.getLastKnownLocation(prv)
            if (location != null)
                displayCoord(location.latitude, location.longitude)
                Log.d("mytag", "location set")
        }

        }

    override fun onLocationChanged(loc: Location) {
        val lat = loc.latitude
        val lng = loc.longitude
        displayCoord(lat, lng)
        Log.d("my", "lat " + lat + " long " + lng)
    }

    fun displayCoord(latitude: Double, longtitude: Double) {
        findViewById<TextView>(R.id.lat).text = String.format("%.5f", latitude)
        findViewById<TextView>(R.id.lng).text = String.format("%.5f", longtitude)
    }

    override fun onProviderEnabled(provider: String) {
        providerStatusTv.text = "online"
    }

    override fun onProviderDisabled(provider: String) {
        providerStatusTv.text = "offline"
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d("permissions status", permissions.toString())
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    // TODO: обработать случай отключения GPS (геолокации) пользователем
    // onProviderDisabled + onProviderEnabled

    // TODO: обработать возврат в активность onRequestPermissionsResult
}