package com.example.amunstore.data.model.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class Product(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id") var id: Long? = null,

    @ColumnInfo(name = "title")
    @SerializedName("title") var title: String? = null,
    @SerializedName("body_html") var bodyHtml: String? = null,

    @ColumnInfo(name = "vendor")
    @SerializedName("vendor") var vendor: String? = null,

    @ColumnInfo(name = "product_type")
    @SerializedName("product_type") var productType: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("handle") var handle: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("published_at") var publishedAt: String? = null,
    @SerializedName("template_suffix") var templateSuffix: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("published_scope") var publishedScope: String? = null,
    @SerializedName("tags") var tags: String? = null,
    @Ignore
    @SerializedName("admin_graphql_api_id") var adminGraphqlApiId: String? = null,
    @Ignore
    @SerializedName("variants") var variants: ArrayList<Variants> = arrayListOf(),
    @Ignore
    @SerializedName("options") var options: ArrayList<Options> = arrayListOf(),
    @Ignore
    @SerializedName("images") var images: ArrayList<Images> = arrayListOf(),
    @Ignore
    @SerializedName("image") var image: Image? = Image(),

    @ColumnInfo(name = "imageSrc")
    var imageSrc: String? = "",

    @ColumnInfo(name = "isFavourite")
    var isFavourite: Boolean = false
)