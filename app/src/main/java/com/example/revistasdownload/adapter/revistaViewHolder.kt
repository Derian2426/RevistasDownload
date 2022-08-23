package com.example.revistasdownload.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.revistasdownload.R
import com.example.revistasdownload.clases.Articulos

class revistaViewHolder(view:View): RecyclerView.ViewHolder(view) {
    val title=view.findViewById<TextView>(R.id.title)
    val publisher=view.findViewById<TextView>(R.id.publisher)
    val date_published=view.findViewById<TextView>(R.id.date_published)
    val foto=view.findViewById<ImageView>(R.id.imagePDFDownload)
    fun render(articulos: Articulos){
        try {
            title.text=articulos.title
        publisher.text=articulos.date_published
        date_published.text=articulos.date_published



            foto.setOnClickListener {
                var link: Uri = Uri.parse(articulos.UrlViewGalley)
                var i: Intent = Intent(Intent.ACTION_VIEW, link)
                foto.context.startActivity(i)
            }
        }catch (e:Exception){
            Toast.makeText(foto.context,  e.toString(),Toast.LENGTH_LONG).show()
        }
    }

}