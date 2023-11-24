package com.sport
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sport.databinding.ActivityDasboardBinding

class DasboardActivity : AppCompatActivity() {
    private lateinit var bindashboard: ActivityDasboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindashboard = ActivityDasboardBinding.inflate(layoutInflater)
        setContentView(bindashboard.root)

        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val isAdminFromSharedPreferences = sharedPreferences.getBoolean("isAdmin", false)

        val isAdminFromIntent = intent.getBooleanExtra("isAdmin", false)
        val isAdmin = isAdminFromIntent || isAdminFromSharedPreferences
        if (isAdmin) {
        } else {
        }

        if (isAdmin) {
            bindashboard.menuaddbola.visibility = View.VISIBLE
            bindashboard.menuaddraket.visibility = View.VISIBLE
        } else {
            bindashboard.menuaddbola.visibility = View.GONE
            bindashboard.menuaddraket.visibility = View.GONE
        }
        setClickListeners()

        val listIklan = ArrayList<IklanModel>()
        listIklan.add(IklanModel(R.drawable.ball))
        listIklan.add(IklanModel(R.drawable.ball2))
        listIklan.add(IklanModel(R.drawable.minton))

        val adapter =ImageSliderAdapter(listIklan)
        bindashboard.apply {
            carouselrecyclerview.adapter = adapter
            carouselrecyclerview.set3DItem(true)
            carouselrecyclerview.setAlpha(true)
            carouselrecyclerview.setInfinite(true)
        }
        bindashboard.BTNLogout.setOnClickListener {
            logout()
        }
    }

    private fun setClickListeners() {
        bindashboard.menubolasepak.setOnClickListener { navigateToActivity(SoccerActivity::class.java) }
        bindashboard.menubolavoli.setOnClickListener { navigateToActivity(VolyActivity::class.java) }
        bindashboard.menubolabasket.setOnClickListener { navigateToActivity(BasketActivity::class.java) }
        bindashboard.menuraket.setOnClickListener { navigateToActivity(RaketActivity::class.java) }
        bindashboard.menuaddbola.setOnClickListener { navigateToActivity(AddBolaActivity::class.java) }
        bindashboard.menuaddraket.setOnClickListener { navigateToActivity(AddRaketActivity::class.java) }
        bindashboard.toProfile.setOnClickListener { navigateToActivity(ProfileActivity::class.java) }
    }

    private fun navigateToActivity(destinationActivity: Class<out AppCompatActivity>) {
        startActivity(Intent(this, destinationActivity))
    }
    private fun logout() {
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
        startActivity(Intent(this, LoginActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
        finish()
    }
}