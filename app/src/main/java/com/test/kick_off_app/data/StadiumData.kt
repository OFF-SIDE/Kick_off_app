package com.test.kick_off_app.data

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body

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

data class StadiumInfoList(
    @SerializedName("externalId" ) var externalId : String?  = null,
    @SerializedName("stadiumId"  ) var stadiumId  : Int?     = null,
    @SerializedName("subName"    ) var subName    : String?  = null,
    @SerializedName("link"       ) var link       : String?  = null,
    @SerializedName("openDay"    ) var openDay    : String?  = null,
    @SerializedName("closeDay"   ) var closeDay   : String?  = null,
    @SerializedName("status"     ) var status     : Boolean? = null
)

data class StadiumDetail(
    val stadium: Stadium,
    val stadiumInfoList: List<StadiumInfoList>,
    val stadiumRateList: List<Any?>,
    val stadiumStar: Boolean,
)

data class SignupInfo(
    @SerializedName("oauthId"  ) var id       : Long?   = null,
    @SerializedName("name"     ) var name     : String? = null,
    @SerializedName("nickname" ) var nickname : String? = null,
    @SerializedName("location" ) var location : String? = null,
    @SerializedName("category" ) var category : String? = null
)

data class UserInfo(
    @SerializedName("id"       ) var id       : Long?   = null,
    @SerializedName("name"     ) var name     : String? = null,
    @SerializedName("nickname" ) var nickname : String? = null,
    @SerializedName("location" ) var location : String? = null,
    @SerializedName("category" ) var category : String? = null
)

data class FileData(
    var file: String? = null,
    var preSignedUrl: String? = null
)

data class FileUploadRequest(
    var fileType: FileTypeEnum,
    var fileName: String
)

data class Referee(
    @SerializedName("id"          ) var id          : Int?     = null,
    @SerializedName("userId"      ) var userId      : Int?     = null,
    @SerializedName("title"       ) var title       : String?  = null,
    @SerializedName("price"       ) var price       : Int?     = null,
    @SerializedName("stadiumId"   ) var stadiumId   : Int?     = null,
    @SerializedName("userStadium" ) var userStadium : String?  = null,
    @SerializedName("imgLink"     ) var imgLink     : String?  = null,
    @SerializedName("comment"     ) var comment     : String?  = null,
    @SerializedName("status"      ) var status      : String?  = null,
    @SerializedName("isHiring"    ) var isHiring    : Boolean? = null,
    @SerializedName("category"    ) var category    : String?  = null,
    @SerializedName("startDate"   ) var startDate   : String?  = null,
    @SerializedName("endDate"     ) var endDate     : String?  = null,
    @SerializedName("dateNego"    ) var dateNego    : Boolean? = null,
    @SerializedName("startTime"   ) var startTime   : Int?     = null,
    @SerializedName("endTime"     ) var endTime     : Int?     = null,
    @SerializedName("timeNego"    ) var timeNego    : Boolean? = null,
    @SerializedName("priceNego"   ) var priceNego   : Boolean? = null,
    @SerializedName("createdAt"   ) var createdAt   : String?  = null
)