package com.test.kick_off_app.data

import com.google.gson.annotations.SerializedName

data class Response<T>(
    @SerializedName("status"  ) var status  : String?         = null,
    @SerializedName("code"    ) var code    : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : T?              = null
)

data class Stadium(
    @SerializedName("id"           ) var id           : Int?    = null,
    @SerializedName("location"     ) var location     : String? = null,
    @SerializedName("contactPhone" ) var contactPhone : String? = null,
    @SerializedName("name"         ) var name         : String? = null,
    @SerializedName("comment"      ) var comment      : String? = null,
    @SerializedName("category"     ) var category     : String? = null,
    @SerializedName("image"        ) var image        : String? = null,
    @SerializedName("price"        ) var price        : String? = null,
    @SerializedName("totalRating"  ) var totalRating  : Int?    = null,
    @SerializedName("ratingPeople" ) var ratingPeople : Int?    = null,
    @SerializedName("visitor"      ) var visitor      : Int?    = null,
    @SerializedName("x"            ) var x            : Double? = null,
    @SerializedName("y"            ) var y            : Double? = null,
    @SerializedName("openAt"       ) var openAt       : String? = null,
    @SerializedName("closeAt"      ) var closeAt      : String? = null
)

data class Reservation(
    @SerializedName("externalId" ) var externalId : String?  = null,
    @SerializedName("stadiumId"  ) var stadiumId  : Int?     = null,
    @SerializedName("subName"    ) var subName    : String?  = null,
    @SerializedName("link"       ) var link       : String?  = null,
    @SerializedName("openDay"    ) var openDay    : String?  = null,
    @SerializedName("closeDay"   ) var closeDay   : String?  = null,
    @SerializedName("status"     ) var status     : Boolean? = null
)


