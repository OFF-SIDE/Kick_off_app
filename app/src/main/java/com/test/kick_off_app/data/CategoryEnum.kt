package com.test.kick_off_app.data

enum class CategoryEnum {
    FOOTBALL, VOLLEYBALL, BASKETBALL, TENNIS, BASEBALL, ETC
}

enum class LocationEnum {
    강남구, 강동구, 강북구, 강서구, 관악구, 광진구, 구로구, 금천구, 노원구, 도봉구, 동대문구, 동작구, 마포구, 서대문구, 서초구, 성동구, 성북구, 송파구, 양천구, 영등포구, 용산구, 은평구, 종로구, 중구, 중랑구
}

enum class StatusEnum {
    진행중, 예약중, 마감
}

fun getCategory(i: CategoryEnum): String{
    return i.name
}

fun getLocation(i: LocationEnum): String{
    return i.name
}

fun getStatus(i: StatusEnum) : String{
    return i.name
}