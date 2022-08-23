package com.example.revistasdownload.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.revistasdownload.R
import com.example.revistasdownload.clases.Articulos

class revistaAdapter(private val listaRevista:List<Articulos>): RecyclerView.Adapter<revistaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): revistaViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        return revistaViewHolder(layoutInflater.inflate(R.layout.iten_articulo,parent,false))
    }

    override fun onBindViewHolder(holder: revistaViewHolder, position: Int) {
        val item=listaRevista[position]
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return listaRevista.size
    }

}