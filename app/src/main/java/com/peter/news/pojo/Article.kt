package com.peter.news.pojo

import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
 class Article(
    var id: String ,
    var urlToImage: String ,
    var title: String,
    var url: String,
    var author: String ,
    var description: String ,
    var publishedAt: String ,
    var content: String ,
) : Parcelable



