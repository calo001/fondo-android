package com.github.calo001.fondo.ui.main.fragment.category


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.github.calo001.fondo.R
import com.github.calo001.fondo.adapter.CategoryAdapter
import com.github.calo001.fondo.repository.CategoriesRepositoryProvider
import kotlinx.android.synthetic.main.fragment_categories.*

/**
 * A simple [Fragment] subclass.
 * Use the [CategoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CategoriesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvCategories.layoutManager = GridLayoutManager(activity, 2)
        rvCategories.adapter = CategoryAdapter(
            CategoriesRepositoryProvider.provideCategoriesRepository(),
            context
        )
    }

    companion object {
        const val TAG = "CategoriesFragment"

        @JvmStatic
        fun newInstance() =
            CategoriesFragment()
    }
}
