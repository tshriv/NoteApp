package com.inventory.noteapp

import android.app.ActivityManager
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.inventory.noteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //val viewmodel :ViewModel by
    lateinit var navController: NavController
    lateinit var navHostfrag: NavHostFragment
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        navController = findNavController(R.id.fragmentContainerView)

        navHostfrag =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        //  NavigationUI.setupActionBarWithNavController(this,navController)
        // setupActionBarWithNavController(navController)

    }

    /* override fun onNavigateUp(): Boolean {
      /*   if (findNavController(R.id.fragmentContainerView).currentDestination?.label == "HomeFragment") {
             //
                 Log.d("ifstatementcalled","if statement called")
             this.finishAffinity()
         } */
         Log.d("onnavigationup","onnavigationup called")
         return navController.navigateUp() || super.onNavigateUp()
     }*/

    override fun onSupportNavigateUp(): Boolean {
        Log.d("navigateUp", "onsupport navigation up called")
        onBackPressed()
        return super.onSupportNavigateUp()

    }

    override fun onBackPressed() {
     /*   Log.d("current fragment tag", navHostfrag.childFragmentManager.fragments[0].tag.toString())
        Log.d("fragment_visible", navHostfrag.childFragmentManager.fragments[0].toString())
        Log.d("findfragbyid",navHostfrag.childFragmentManager.findFragmentById(navHostfrag.childFragmentManager.fragments[0].id).toString())
       // Log.d("activityactive",navHostfrag.childFragmentManager.getFragment())
        //(applicationContext as MainActivity).isA
        Log.d("containHomeFrag", navHostfrag.childFragmentManager.fragments[0].toString().contains("HomeFragment").toString())

        if(navHostfrag.childFragmentManager.fragments[0].toString().contains("HomeFragment")){
            Log.d("isHomeFrag","${navHostfrag.childFragmentManager.fragments[0]} is Homefragment")
            this.finishAffinity()
        }
        else
            Log.d("isHomeFrag","${navHostfrag.childFragmentManager.fragments[0]} is not HomeFragment")  */

       /* if (navHostfrag.childFragmentManager.fragments[0].toString().contains("HomeFragment") == true) {
            Log.d("within if condition", "within if condition")
            this.finishAffinity()
        } */
        super.onBackPressed()
    }
}