package com.github.calo001.fondo.repository

import com.github.calo001.fondo.R
import com.github.calo001.fondo.model.Category

object CategoriesRepositoryProvider {
    fun provideCategoriesRepository(): List<Category> {
        return listOf(
            Category(
                R.drawable.nature,
                R.string.nature,
                "Nature",
                R.drawable.back_nature
            ),
            Category(
                R.drawable.animal,
                R.string.animal,
                "Animal",
                R.drawable.back_animal
            ),
            Category(
                R.drawable.fodd_drink,
                R.string.food_drink,
                "Food & drink",
                R.drawable.back_food_drink
            ),
            Category(
                R.drawable.space,
                R.string.space,
                "Space",
                R.drawable.back_space
            ),
            Category(
                R.drawable.sport,
                R.string.sport,
                "Sport",
                R.drawable.back_sport
            ),
            Category(
                R.drawable.business_work,
                R.string.business_work,
                "Business & work",
                R.drawable.back_business_work
            ),
            Category(
                R.drawable.fashion,
                R.string.fashion,
                "Fashion",
                R.drawable.back_fashion),
            Category(
                R.drawable.art_culture,
                R.string.art_culture,
                "Art & culture",
                R.drawable.back_arts_culture
            ),
            Category(
                R.drawable.architecture,
                R.string.architecture,
                "Architecture",
                R.drawable.back_architecture
            ),
            Category(
                R.drawable.texture_pattern,
                R.string.texture_pattern,
                "Texture & pattern",
                R.drawable.back_texture_pattern
            ),
            Category(
                R.drawable.technology,
                R.string.technology,
                "Technology",
                R.drawable.back_technology
            ),
            Category(
                R.drawable.flatlay,
                R.string.flatlay,
                "Flatlay",
                R.drawable.back_flatlay
            )
        )
    }
}