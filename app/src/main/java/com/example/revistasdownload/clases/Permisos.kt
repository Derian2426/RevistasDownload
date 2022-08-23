package com.example.revistasdownload.clases

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat


class Permisos(var context: Context) {
    fun getPermisosNoAprobados(listaPermisos: ArrayList<String?>): ArrayList<String?> {
        val list = ArrayList<String?>()
        for (permiso in listaPermisos) {
            if (Build.VERSION.SDK_INT >= 23)
                if (context.checkSelfPermission(permiso!!) != PackageManager.PERMISSION_GRANTED)
                    list.add(permiso)
        }
        return list
    }

    fun getPermisosAprobados(listaPermisos: ArrayList<String?>): ArrayList<String?> {
        val list = ArrayList<String?>()
        for (permiso in listaPermisos) {
            if (Build.VERSION.SDK_INT >= 23)
                if (context.checkSelfPermission(permiso!!) == PackageManager.PERMISSION_GRANTED)
                    list.add(permiso)
        }
        return list
    }

    fun getPermission(permisosSolicitados: ArrayList<String?>) {
        if(permisosSolicitados.size>0)
            if (Build.VERSION.SDK_INT >= 23)
                ActivityCompat.requestPermissions(context as Activity, permisosSolicitados.toArray(arrayOfNulls(permisosSolicitados.size)),1)
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,grantResults: IntArray):String  {
        var s = ""
        if (requestCode == 1) {
            for (i in permissions.indices) {
                s+= if(grantResults[i] == PackageManager.PERMISSION_GRANTED)"Permitido: " else "Denegado: "
                s+=" " + permissions[i] + "\n"
            }
        }
        return s
    }

}