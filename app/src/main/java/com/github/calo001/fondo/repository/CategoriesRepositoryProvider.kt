package com.github.calo001.fondo.repository

import com.github.calo001.fondo.R
import com.github.calo001.fondo.model.Category

object CategoriesRepositoryProvider {
    fun provideCategoriesRepository(): List<Category> {
        return listOf(
            Category(R.drawable.nature, R.string.nature, R.drawable.back_nature),
            Category(R.drawable.animal, R.string.animal, R.drawable.back_animal),
            Category(
                R.drawable.fodd_drink,
                R.string.food_drink,
                R.drawable.back_food_drink
            ),
            Category(R.drawable.space, R.string.space, R.drawable.back_space),
            Category(R.drawable.sport, R.string.sport, R.drawable.back_sport),
            Category(
                R.drawable.business_work,
                R.string.business_work,
                R.drawable.back_business_work
            ),
            Category(R.drawable.fashion, R.string.fashion, R.drawable.back_fashion),
            Category(
                R.drawable.art_culture,
                R.string.art_culture,
                R.drawable.back_arts_culture
            ),
            Category(
                R.drawable.architecture,
                R.string.architecture,
                R.drawable.back_architecture
            ),
            Category(
                R.drawable.texture_pattern,
                R.string.texture_pattern,
                R.drawable.back_texture_pattern
            ),
            Category(
                R.drawable.technology,
                R.string.technology,
                R.drawable.back_technology
            ),
            Category(R.drawable.flatlay, R.string.flatlay, R.drawable.back_flatlay)
        )
    }
}