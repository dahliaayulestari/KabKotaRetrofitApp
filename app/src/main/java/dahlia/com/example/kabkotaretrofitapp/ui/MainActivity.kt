package dahlia.com.example.kabkotaretrofitapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import dahlia.com.example.kabkotaretrofitapp.adapter.KabKotaAdapter
import dahlia.com.example.kabkotaretrofitapp.databinding.ActivityMainBinding
import dahlia.com.example.kabkotaretrofitapp.helpers.Config
import dahlia.com.example.kabkotaretrofitapp.model.KabKotaItem
import dahlia.com.example.kabkotaretrofitapp.response.KabKotaResponse
import dahlia.com.example.kabkotaretrofitapp.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)

        binding.rvKabKota.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(
            this,
            layoutManager.orientation
        )

        binding.rvKabKota.addItemDecoration(itemDecoration)

        getKabKotaList()

        binding.swipeRefresh.setOnRefreshListener {
            getKabKotaList()
        }
    }

    private fun getKabKotaList() {

        showLoading(true)

        val client = ApiConfig.getApiService().getListUsers()

        client.enqueue(object : Callback<KabKotaResponse> {

            override fun onResponse(
                call: Call<KabKotaResponse>,
                response: Response<KabKotaResponse>
            ) {

                showLoading(false)

                if (response.isSuccessful) {

                    val responseBody = response.body()

                    if (responseBody != null) {
                        setKabKotaData(responseBody.data)
                    }

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(
                call: Call<KabKotaResponse>,
                t: Throwable
            ) {

                showLoading(false)

                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setKabKotaData(
        kabKota: List<KabKotaItem>
    ) {

        val adapter = KabKotaAdapter()

        adapter.submitList(kabKota)

        binding.rvKabKota.adapter = adapter

        adapter.setOnItemClickCallback(
            object : KabKotaAdapter.OnItemClickCallback {

                override fun onItemClicked(data: KabKotaItem) {

                    val json = Gson().toJson(data)

                    val moveWithObjectIntent = Intent(
                        this@MainActivity,
                        DetailKabKotaActivity::class.java
                    )

                    moveWithObjectIntent.putExtra(
                        Config.EXTRA_KAB_KOTA,
                        json
                    )

                    startActivity(moveWithObjectIntent)
                }
            }
        )
    }

    private fun showLoading(isLoading: Boolean) {

        // Tampilkan progress bar hanya jika sedang loading
        // dan tidak sedang swipe refresh
        if (!binding.swipeRefresh.isRefreshing) {

            binding.progressBar.visibility =
                if (isLoading) View.VISIBLE else View.GONE
        }

        // Matikan indikator swipe refresh
        // jika loading sudah selesai
        if (!isLoading) {
            binding.swipeRefresh.isRefreshing = false
        }
    }
}