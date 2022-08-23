package com.example.revistasdownload

import android.Manifest
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.revistasdownload.adapter.revistaAdapter
import com.example.revistasdownload.clases.Articulos
import com.example.revistasdownload.clases.HttpsTrustManager
import com.example.revistasdownload.clases.Permisos

open class MainActivity : AppCompatActivity() {
    lateinit var adminPermisos: Permisos
    var downloadid: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adminPermisos = Permisos(this@MainActivity)
        val permisosSolicitados = ArrayList<String?>()
        permisosSolicitados.add(Manifest.permission.CAMERA)
        permisosSolicitados.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        permisosSolicitados.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        permisosSolicitados.add(Manifest.permission.WRITE_CALENDAR)
        val permisosAprobados:ArrayList<String?>   = adminPermisos.getPermisosAprobados(permisosSolicitados)
        val listPermisosNOAprob:ArrayList<String?> = adminPermisos.getPermisosNoAprobados(permisosSolicitados)
        adminPermisos.getPermission(listPermisosNOAprob)
        initRecycler()

    }
    private fun initRecycler(){
        val recyclerView=findViewById<RecyclerView>(R.id.idrecycler)
        recyclerView.layoutManager= LinearLayoutManager(this)
        //var txt_json=findViewById<TextView>(R.id.json)
        val cola = Volley.newRequestQueue(this)
        val lstArticulos = ArrayList<Articulos>()
        var url="https://revistas.uteq.edu.ec/ws/pubs.php?i_id=1"
        HttpsTrustManager.allowAllSSL()
        var request=JsonArrayRequest(Request.Method.GET,url,null,{
            respuesta->try{
            for (i in 0 until respuesta.length()){
                var item = respuesta.getJSONObject(i)
                lstArticulos.add(Articulos(item))
            }
            recyclerView.adapter= revistaAdapter(lstArticulos)





        }catch (error:Exception){
            Toast.makeText(this,
                "Error al cargar datos del volumen",
                Toast.LENGTH_SHORT).show()
        }
        }, {
            Toast.makeText(this,
                "Error al cargar datos del volumen",
                Toast.LENGTH_SHORT).show()
        })
        cola.add(request)

    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var resp:String  = adminPermisos.onRequestPermissionsResult(requestCode, permissions as Array<String>, grantResults)
        Toast.makeText(this.applicationContext, resp, Toast.LENGTH_LONG).show()
    }
    fun MostrarDescargas(view: View?) {
        val intent = Intent()
        intent.action = DownloadManager.ACTION_VIEW_DOWNLOADS
        startActivity(intent)
    }
    fun BajarDoc(view: View?) {

        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val request =  DownloadManager.Request(Uri.parse("https://www.uteq.edu.ec/doc/investigacion/lineas_inv.pdf"))
            .setDescription("Download PDF")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            .setTitle("Download Pdf")
            .setAllowedOverMetered(true)
            .setVisibleInDownloadsUi(true)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalFilesDir(this.applicationContext, Environment.DIRECTORY_DOWNLOADS,"downloadfile.pdf")



        try {
            downloadid = manager.enqueue(request)
            registerReceiver(MyBroadcastReceiver(downloadid), IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        } catch (e: Exception) {
            Toast.makeText(this.applicationContext, "Error: " + e.message, Toast.LENGTH_LONG).show()
        }
    }
}
class MyBroadcastReceiver(var downloadid: Long) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        if (id == downloadid)
            Toast.makeText(context,  "Download Done!!",Toast.LENGTH_LONG).show()
    }
}