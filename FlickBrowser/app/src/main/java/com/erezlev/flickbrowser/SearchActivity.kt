package com.erezlev.flickbrowser

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.preference.PreferenceManager

private const val TAG = "SearchActivity"

class SearchActivity : BaseActivity() {
    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: starts")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        activateToolbar(true)
        Log.d(TAG, "onCreate: ends")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, "onCreateOptionsMenu: starts")
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchManger = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        val searchableInfo = searchManger.getSearchableInfo(componentName)
        searchView?.setSearchableInfo(searchableInfo)
//        Log.d(TAG, "onCreateOptionsMenu: $componentName")
//        Log.d(TAG, "onCreateOptionsMenu: hint is ${searchView?.queryHint}")
//        Log.d(TAG, "onCreateOptionsMenu: $searchableInfo")

        searchView?.isIconified = false

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "onQueryTextSubmit: called")

                val sharePref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                sharePref.edit().putString(FLICKR_QUERY, query).apply()
                searchView?.clearFocus()

                finish()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        searchView?.setOnCloseListener {
            finish()
            false
        }
        Log.d(TAG, "onCreateOptionsMenu: returning")
        return true
    }
}
