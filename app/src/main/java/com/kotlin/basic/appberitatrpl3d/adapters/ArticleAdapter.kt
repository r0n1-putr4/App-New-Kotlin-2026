package com.kotlin.basic.appberitatrpl3d.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.basic.appberitatrpl3d.R
import com.kotlin.basic.appberitatrpl3d.config.ApiClient
import com.kotlin.basic.appberitatrpl3d.models.ArticleModel
import com.squareup.picasso.Picasso

class ArticleAdapter(
    val listArticle: List<ArticleModel.Article>
) : RecyclerView.Adapter<ArticleAdapter.ViewArticle>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleAdapter.ViewArticle {
        return ViewArticle(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.article_layout,
                    parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: ArticleAdapter.ViewArticle, position: Int) {
        val itemArticle = listArticle[position]
                                    "http://10.20.30.181:8080/api-berita-trpl3d/" +
                                            "uploads/Mahasiswa-Baru-di-PKKMB-2026-01.jpeg"
        Picasso.get().load(ApiClient.BASE_URL+"/api-berita-trpl3d/"+itemArticle.cover)
            .into(holder.ivArticle)
        holder.tvJudul.text = itemArticle.judul
        holder.tvTanggal.text = itemArticle.tanggal_publish_
        holder.rbArticle.rating =
            itemArticle.rating_.toString().toFloat()
        holder.tvPenulis.text = itemArticle.fullname
    }

    override fun getItemCount(): Int = listArticle.size

    class ViewArticle(view: View) : RecyclerView.ViewHolder(view) {

        val ivArticle = view.findViewById<ImageView>(R.id.ivArticle)
        val tvJudul = view.findViewById<TextView>(R.id.tvJudul)
        val tvTanggal = view.findViewById<TextView>(R.id.tvTanggal)
        val rbArticle = view.findViewById<RatingBar>(R.id.rbArticle)
        val tvPenulis = view.findViewById<TextView>(R.id.tvPenulis)
        val btnEdit = view.findViewById<Button>(R.id.btnEdit)
        val btnDelete = view.findViewById<Button>(R.id.btnDelete)


    }
}