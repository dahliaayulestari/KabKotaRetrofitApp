package dahlia.com.example.kabkotaretrofitapp.response

import com.google.gson.annotations.SerializedName
import dahlia.com.example.kabkotaretrofitapp.model.KabKotaItem

data class KabKotaResponse(

	@field:SerializedName("data")
	val data: List<KabKotaItem>,

	@field:SerializedName("message")
	val message: String
)


