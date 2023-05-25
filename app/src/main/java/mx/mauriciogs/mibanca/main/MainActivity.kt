package mx.mauriciogs.mibanca.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import mx.mauriciogs.mibanca.R
import mx.mauriciogs.mibanca.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var bottomNav: BottomNavigationView
    private val navHost : NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.navHostFrag) as NavHostFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        navController = navHost.navController
        bottomNav = findViewById(R.id.bottomNav)

        binding.bottomNav.setupWithNavController(navController)

    }

}