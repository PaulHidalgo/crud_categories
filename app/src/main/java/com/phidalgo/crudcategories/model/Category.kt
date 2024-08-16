package com.phidalgo.crudcategories.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int,
    var name: String,
    val pokemonList: MutableList<Pokemon>
): Parcelable
