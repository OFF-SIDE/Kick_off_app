package com.test.kick_off_app.ui.location

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.kick_off_app.data.Constants
import com.test.kick_off_app.data.LocationEnum
import com.test.kick_off_app.data.getLocation
import com.test.kick_off_app.repository.Repository

class LocationViewModel: ViewModel() {
    private val repository = Repository()

    // 현재 선택된 지역 저장
    //private val _locations = MutableLiveData<>

    private val _locations = MutableLiveData<Array<Boolean>>()
    val locations: LiveData<Array<Boolean>>
        get() = _locations

    private val _selectCount = MutableLiveData<Int>()
    val selectCount: LiveData<Int>
        get() = _selectCount

    init {
        // viewmodel이 생성될 때 livedate 초기화
        val initialArray = Array(Constants.LOCATION_SIZE) { false }
        _locations.value = initialArray
        _selectCount.value = 0
    }

    fun toggleLocation(context: Context,  position: Int) {
        val currentLocations = _locations.value ?: return
        if (position in currentLocations.indices) {
            if (currentLocations[position]==true){
                // 활성된 버튼
                currentLocations[position]=false
                val currentValue = _selectCount.value ?: 0
                _selectCount.value = currentValue - 1
            }
            else {
                // 비활성 버튼
                val currentValue = _selectCount.value ?: 0
                if(currentValue < 2){
                    currentLocations[position]=true
                    _selectCount.value = currentValue + 1
                }
                else{
                    // 이미 버튼 2개가 선택됨
                    Toast.makeText(context, "최대 2개까지 선택 가능합니다.", Toast.LENGTH_SHORT).show()
                }
            }
            _locations.value = currentLocations
        }
    }

    fun firstLocation(): String {
        var location: String = ""
        for (index in _locations.value!!.indices) {
            if (_locations.value!![index]) {
                location = getLocation(LocationEnum.values()[index])
                break
            }
        }
        return location
    }

    fun secondLocation(): String{
        var location: String = ""
        var firstFlag = true
        _locations.value!!.forEachIndexed { index, b ->
            if(b){
                if(firstFlag){
                    firstFlag = false
                }
                else{
                    location = getLocation(LocationEnum.values()[index])
                }
            }
        }
        return location
    }

    fun getLocationText(): String{
        var text = ""
        var firstFrag = true
        _locations.value!!.forEachIndexed { index, item ->
            if(item){
                if(firstFrag){
                    text = getLocation(LocationEnum.values()[index])
                    firstFrag = false
                }
                else{
                    text = text+"/"+getLocation(LocationEnum.values()[index])
                }
            }
        }
        if (text == ""){
            text = "지역을 선택하세요!"
        }

        return text
    }

    fun getLocations(): String{
        // _locations의 값이 null이 아닌지 확인하고, null이면 빈 문자열을 반환
        val locations = _locations.value ?: return ""

        // 선택된 지역들의 이름을 리스트에 추가
        val selectedLocations = mutableListOf<String>()
        for (index in locations.indices) {
            if (locations[index]) {
                selectedLocations.add(getLocation(LocationEnum.values()[index]))
            }
        }

        // 리스트를 콤마(,)로 연결하여 문자열로 반환
        return selectedLocations.joinToString(separator = ",")
    }
}