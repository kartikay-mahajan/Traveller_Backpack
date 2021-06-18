package com.kartikaymahajan.travellerbackpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.kartikaymahajan.travellerbackpack.databinding.ActivityMainBinding

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(){
    lateinit var binding: ActivityMainBinding

    lateinit var viewPager2:ViewPager2

    lateinit var adapter:ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.secondaryDarkColor)

        viewPager2 = binding.flFragment
        viewPager2.isUserInputEnabled = false
//        setCurrentFragment(scanTextFragment)
        viewPager2.setCurrentItem(0,false)

        binding.bottomNav.onItemSelected = {
            when (it) {
                0 -> viewPager2.setCurrentItem(0,false)
                1 -> viewPager2.setCurrentItem(1,false)
                2 -> viewPager2.setCurrentItem(2,false)
            }
        }

        setupViewPager(viewPager2)
    }

    private fun setupViewPager(viewPager: ViewPager2) {
        adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        val scanTextFragment = ScanTextFragment()
        val translateTextFragment = TranslateTextFragment()
        val mapsFragment = MapsFragment()
        adapter.addFragment(scanTextFragment)
        adapter.addFragment(translateTextFragment)
        adapter.addFragment(mapsFragment)
        viewPager.adapter = adapter
    }
}

