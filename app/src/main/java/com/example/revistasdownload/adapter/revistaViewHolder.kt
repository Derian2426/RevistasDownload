package com.example.revistasdownload.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.revistasdownload.R
import com.example.revistasdownload.clases.Articulos

class revistaViewHolder(view:View): RecyclerView.ViewHolder(view) {
    val title=view.findViewById<TextView>(R.id.title)
    val publisher=view.findViewById<TextView>(R.id.publisher)
    val date_published=view.findViewById<TextView>(R.id.date_published)
    val foto=view.findViewById<ImageView>(R.id.imagePDFDownload)
    fun render(articulos: Articulos){
        title.text=articulos.title
        publisher.text=articulos.date_published
        date_published.text=articulos.date_published
    }

}