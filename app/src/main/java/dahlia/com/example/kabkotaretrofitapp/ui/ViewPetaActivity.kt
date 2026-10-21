package dahlia.com.example.kabkotaretrofitapp.ui

import android.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import dahlia.com.example.kabkotaretrofitapp.databinding.ActivityViewPetaBinding
import dahlia.com.example.kabkotaretrofitapp.helpers.Config
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

class ViewPetaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewPetaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPetaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val urlPeta = intent.getStringExtra(Config.EXTRA_URL_PETA)
        val requestBuilder = GlideToVectorYou
            .init()
            .with(this)
            .requestBuilder
        requestBuilder
            .load(urlPeta)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_menu_gallery)
                    .error(R.drawable.ic_lock_lock)
                    .fitCenter()
            )
            .into(binding.imgItemPhoto)
    }
}