package com.example.dateappproject
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.dateappproject.databinding.ActivityDateAppMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DateAppMainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var binding: ActivityDateAppMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityDateAppMainBinding.inflate(layoutInflater)
     setContentView(binding.root)

        setSupportActionBar(binding.appBarDateAppMain.toolbar)

        binding.appBarDateAppMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_date_app_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        appBarConfiguration = AppBarConfiguration(
            setOf
                (
            R.id.nav_User, R.id.nav_gallery, R.id.nav_slideshow
        ), drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        update(navView)
    }

    private fun update(navView: NavigationView) {
        val view : View = navView.getHeaderView(0)
        val tvName : TextView = view.findViewById(R.id.name_user)
        val tvemail : TextView = view.findViewById(R.id.email_user)
        val image : ImageView = view.findViewById(R.id.user_profile_picture)
        val user = Firebase.auth.currentUser
        tvName.text = user?.displayName
        tvemail.text = user?.email
        val pictureURL = user?.photoUrl.toString()
        if(pictureURL.isNotEmpty()){
            Glide.with(this)
                .load(pictureURL)
                .load(tvName)
                .load(tvemail)
                .circleCrop()
                .into(image)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.date_app_main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_date_app_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}