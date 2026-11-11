package com.kotlin.basic.appberitatrpl3d.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.basic.appberitatrpl3d.R
import com.kotlin.basic.appberitatrpl3d.config.ApiClient
import com.kotlin.basic.appberitatrpl3d.models.ArticleModel
import com.kotlin.basic.appberitatrpl3d.models.ResponModel
import com.kotlin.basic.appberitatrpl3d.pages.article.ArticleActivity
import com.kotlin.basic.appberitatrpl3d.pages.article.EditArticlectivity
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleAdapter(
    val listArticle: MutableList<ArticleModel.Article>
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
        //  "http://10.20.30.181:8080/api-berita-trpl3d/" +
        //  "uploads/Mahasiswa-Baru-di-PKKMB-2026-01.jpeg"
        Picasso.get().load(ApiClient.BASE_URL + "/api-berita-trpl3d/" + itemArticle.cover)
            .into(holder.ivArticle)
        holder.tvJudul.text = itemArticle.judul
        holder.tvTanggal.text = itemArticle.tanggal_publish_
        holder.rbArticle.rating =
            itemArticle.rating_.toString().toFloat()
        holder.tvPenulis.text = itemArticle.fullname
        holder.btnEdit.setOnClickListener {
            holder.itemView.context.startActivity(
                Intent(
                    holder.itemView.context,
                    EditArticlectivity::class.java
                )
                    .putExtra("id", itemArticle.id)
                    .putExtra("user_id", itemArticle.user_id)
                    .putExtra("judul", itemArticle.judul)
                    .putExtra("isi", itemArticle.isi)
                    .putExtra("cover", itemArticle.cover)
                    .putExtra("rating", itemArticle.rating_)
            )
        }
        holder.btnDelete.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context).apply {
                setTitle("Konfirmasi")
                setMessage("Apakah anda ingin melanjutkan?")
                setIcon(R.drawable.ic_delete)
                setPositiveButton("Ya") { _, _ ->
                    holder.btnDelete.isEnabled = false
                    ApiClient.apiService.deleteArticle(itemArticle.id).enqueue(object : Callback<ResponModel?> {
                        override fun onResponse(
                            call: Call<ResponModel?>,
                            response: Response<ResponModel?>
                        ) {
                            if (response.isSuccessful) {
                                val respon = response.body()!!
                                val success = respon.success
                                val message = respon.message

                                if (success) {
//                                   removeItem
                                    val position = holder.bindingAdapterPosition

                                    if (position != RecyclerView.NO_POSITION) {
                                        removeItem(position)
                                    }
                                }

                                Toast.makeText(
                                    holder.itemView.context,
                                    message,
                                    Toast.LENGTH_SHORT
                                ).show()
//                                holder.btnDelete.isEnabled = true
                            }
                        }

                        override fun onFailure(call: Call<ResponModel?>, t: Throwable) {
                            Toast.makeText(
                                holder.itemView.context,
                                "Gagal Terhubung ke API",
                                Toast.LENGTH_SHORT
                            ).show()
                            holder.btnDelete.isEnabled = true
                        }
                    })


                }
                setNegativeButton("Tidak") { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
            }.show()
        }

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

    fun removeItem(position: Int) {
        if (position in listArticle.indices) {
            listArticle.removeAt(position)
            notifyItemRemoved(position) // Notify the position of the removed item
            notifyItemRangeChanged(
                position,
                listArticle.size - position
            ) // Optional: Adjust for index shifts
        }

    }
}