package io.codyffly.fondo.ui.main

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import io.codyffly.fondo.R
import io.codyffly.fondo.ui.main.fragment.category.CategoriesFragment
import io.codyffly.fondo.ui.main.fragment.photo.PhotosFragment
import io.codyffly.fondo.ui.main.fragment.search.SearchFragment
import io.codyffly.fondo.util.makeStatusBarTransparent
import io.codyffly.fondo.util.setMarginTop
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), PhotosFragment.OnFragmentInteractionListener{
    val mainFragment: PhotosFragment = PhotosFragment.newInstance()
    val categoriesFragment: CategoriesFragment = CategoriesFragment.newInstance("p", "p")
    val searchFragment: SearchFragment = SearchFragment.newInstance()
    lateinit var activeFragment: androidx.fragment.app.Fragment

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun setupFragments() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.mainFragment, mainFragment)
            .add(R.id.mainFragment, searchFragment).hide(searchFragment)
            .add(R.id.mainFragment, categoriesFragment).hide(categoriesFragment)
            .commit()
        activeFragment = mainFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        makeStatusBarTransparent()
        ViewCompat.setOnApplyWindowInsetsListener(mainContainer) { _, insets ->
            //mainCardView.setMarginTop(insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }

        //mainAppBarLayout.setMarginTop(40)
        setSupportActionBar(toolbar)
        setupToolbar(getString(R.string.toolbar_title_today))
        setupFragments()
        setupButtonNavigation()
    }

    private fun setupButtonNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navbar_home -> {
                        supportFragmentManager
                            .beginTransaction()
                            .hide(activeFragment)
                            .show(mainFragment)
                            .commit()
                        activeFragment = mainFragment
                    }
                R.id.navbar_categories -> {
                        supportFragmentManager
                            .beginTransaction()
                            .hide(activeFragment)
                            .show(categoriesFragment)
                            .commit()
                        activeFragment = categoriesFragment
                    }
            }
            true
        }
    }

    fun setupToolbar(title: String) {
        supportActionBar?.title = null
    }

    fun toogleDayNight() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            /*R.id.action_search -> {
            /    supportFragmentManager
                    .beginTransaction()
                    .hide(activeFragment)
                    .show(searchFragment)
                    .commit()
                activeFragment = searchFragment
            }*/
            else -> super.onOptionsItemSelected(item)
        }

        return true
    }
}
