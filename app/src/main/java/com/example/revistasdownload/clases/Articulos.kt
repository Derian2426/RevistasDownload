package com.example.revistasdownload.clases

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class Articulos (a: JSONObject)
{
    var section: String
    var publication_id: String
    var title:String
    var doi:String
    var abstract:String

    var date_published:String
    var submission_id:String
    var section_id:String
    var seq:String

    lateinit var galley_id : String
    lateinit var label :String
    lateinit var file_id:String
    lateinit var UrlViewGalley:String
    companion object {
        @Throws(JSONException::class)
        fun JsonObjectsBuild(datos: JSONArray): ArrayList<Articulos> {
            val usuarios: ArrayList<Articulos> = ArrayList<Articulos>()
            var i = 0
            while (i < datos.length()) {
                usuarios.add(Articulos(datos.getJSONObject(i)))
                i++
            }
            return usuarios
        }

    }
    init {
        section=a.getString("section").toString()
        publication_id=a.getString("publication_id").toString()
        title=a.getString("title").toString()
        doi=a.getString("doi").toString()
        abstract=a.getString("abstract").toString()
        date_published=a.getString("date_published").toString()
        submission_id=a.getString("submission_id").toString()
        section_id=a.getString("section_id").toString()
        seq=a.getString("seq").toString()

        var galera=a.getJSONArray("galeys")
        for (i in 0 until galera.length()) {
            galley_id=galera.getJSONObject(i).getString("galley_id")
            label=galera.getJSONObject(i).getString("label")
            file_id=galera.getJSONObject(i).getString("label")
            UrlViewGalley=galera.getJSONObject(i).getString("UrlViewGalley")

        }

    }
}