package com.github.calo001.fondo.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.github.calo001.fondo.R
import com.github.calo001.fondo.dialog.SearchDialogFragment
import com.github.calo001.fondo.dialog.SearchDialogFragment.OnSearchListener
import com.github.calo001.fondo.model.Category
import com.github.calo001.fondo.ui.main.fragment.category.CategoriesFragment
import com.github.calo001.fondo.ui.main.fragment.category.CategoriesFragment.OnCategoryListener
import com.github.calo001.fondo.ui.main.fragment.photo.PhotosFragment
import com.github.calo001.fondo.ui.main.fragment.search.SearchFragment
import com.github.calo001.fondo.util.makeStatusBarTransparent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnSearchListener, OnCategoryListener {
    private val mainFragment: PhotosFragment =              PhotosFragment.newInstance()

    private val categoriesFragment: CategoriesFragment =    CategoriesFragment.newInstance()
    private val searchFragment: SearchFragment =            SearchFragment.newInstance()
    private lateinit var activeFragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupStatusbar()
        setSupportActionBar(toolbar)
        setupToolbar()
        setupFragments()
        setupButtonNavigation()
    }

    private fun setupStatusbar() {
        makeStatusBarTransparent("#CDF5F5F5")
        ViewCompat.setOnApplyWindowInsetsListener(mainContainer) { _, insets ->
            //mainCardView.setMarginTop(insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }
    }

    private fun setupToolbar() {
        supportActionBar?.title = null
        toolbar.setOnClickListener {
            val dialog = SearchDialogFragment()
            val ft =
                ViewCompat.getTransitionName(toolbar)?.let { transitionName ->
                    supportFragmentManager.beginTransaction()
                        .addSharedElement(toolbar, transitionName)
                }
            dialog.show(ft, SearchDialogFragment.TAG)
        }
    }

    private fun setupFragments() {
        with(supportFragmentManager) {
            val ft = beginTransaction()
            if (supportFragmentManager.findFragmentByTag(PhotosFragment.TAG) == null) {
                ft.add(R.id.mainFragment, mainFragment, PhotosFragment.TAG)
                activeFragment = mainFragment
            }

            if (supportFragmentManager.findFragmentByTag(PhotosFragment.TAG) == null) {
                ft.add(R.id.mainFragment, searchFragment, SearchFragment.TAG)
                ft.hide(searchFragment)
            }

            if(supportFragmentManager.findFragmentByTag(PhotosFragment.TAG) == null) {
                ft.add(R.id.mainFragment, categoriesFragment, CategoriesFragment.TAG)
                ft.hide(categoriesFragment)
            }
            ft.commit()
        }
    }

    private fun setupButtonNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navbar_home -> {
                    if(activeFragment == mainFragment) {
                        mainFragment.scrollToUp()
                    } else {
                        supportFragmentManager
                            .beginTransaction()
                            .hide(activeFragment)
                            .show(mainFragment)
                            .commit()
                        activeFragment = mainFragment
                    }
                }
                R.id.navbar_categories -> {
                    if (activeFragment == categoriesFragment) {
                        categoriesFragment.scrollToUp()
                    } else {
                        supportFragmentManager
                            .beginTransaction()
                            .hide(activeFragment)
                            .show(categoriesFragment)
                            .commit()
                        activeFragment = categoriesFragment
                    }
                }
            }
            true
        }
    }

    override fun onSearch(term: String) {
        supportFragmentManager
            .beginTransaction()
            .hide(activeFragment)
            .show(searchFragment)
            .commit()
        activeFragment = searchFragment
        searchFragment.newSearchQuery(term)
    }

    override fun onCategoryClick(category: Category) {
        supportFragmentManager
            .beginTransaction()
            .hide(activeFragment)
            .show(searchFragment)
            .commit()
        activeFragment = searchFragment
        searchFragment.newSearchQuery(category.query)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}
