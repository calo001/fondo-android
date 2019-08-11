package io.codyffly.fondo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.codyffly.fondo.adapter.CategoryAdapter
import io.codyffly.fondo.repository.CategoriesRepositoryProvider
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        rvCategories.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        rvCategories.adapter = CategoryAdapter(CategoriesRepositoryProvider
            .provideCategoriesRepository(), this)
    }
}
