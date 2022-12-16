package com.example.dateappproject
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageButton
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
import com.example.dateappproject.Auth.MainActivity
import com.example.dateappproject.databinding.ActivityDateAppMainBinding
import com.example.dateappproject.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class DateAppMainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var binding: ActivityDateAppMainBinding

    private val firestore : FirebaseFirestore = FirebaseFirestore.getInstance()


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

        //Navbar (para la parte deslizante)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.getHeaderView(0).findViewById<ImageButton>(R.id.ib_logout).setOnClickListener{
            FirebaseAuth.getInstance().signOut();
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        navView.getHeaderView(0).findViewById<ImageView>(R.id.user_profile_picture).setOnClickListener{
            val intent = Intent(this, ProfileShowUp::class.java)
            startActivity(intent)
            finish()
        }

        update(navView)
    }


    //Funciones para mostrar info del propio perfil
    private fun update(navView: NavigationView) {
        val view : View = navView.getHeaderView(0)
        val tvName : TextView = view.findViewById(R.id.name_user)
        val tvemail : TextView = view.findViewById(R.id.email_user)
        val image : ImageView = view.findViewById(R.id.user_profile_picture)
        val user = Firebase.auth.currentUser
        firestore.collection("Users")
            .document(user!!.uid)
            .get().addOnSuccessListener { documentSnapshot ->
                val User = documentSnapshot.toObject<Users>()

                tvName.text = User!!.name
                tvemail.text = User!!.email

                val pictureURL = User!!.profileMainPicture
                if (pictureURL!!.isNotEmpty()) {
                    Glide.with(this)
                        .load(User.profileMainPicture)
                        .load(tvName)
                        //.load(pictureURL)
                        //.load(tvName)
                        .load(tvemail)
                        .circleCrop()
                        .into(image)
                } else {
                    Glide.with(this)
                        .load(R.drawable.ic_person)
                        .load("name")
                        .load(tvemail)
                        .circleCrop()
                        .into(image)
                }
            }



        view.setOnClickListener {
            val intent = Intent(this , UpdateProfile::class.java)
            startActivity(intent)
            finish()
        }

    }

//Barra de abajo
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