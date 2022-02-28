package com.example.perludilindungi.models

import android.location.Location
import android.util.Log

data class FaskesResponse(
    val success: Boolean,
    val message: String,
    val count_total: Int,
    val data: ArrayList<DataFaskesResponse>
) {

    fun get5Nearest(myLon: Double, myLat: Double): ArrayList<DataFaskesResponse> {
        var arrData = ArrayList<DataFaskesResponse>()
        val dataSize = this.data.size
        var counter = 0

        // faskes <= 5
        if (dataSize <= 5) {
            arrData = this.data
        }
        // faskes > 5
        else {
            // create hashmap between the distance and array index
            var hashMap: HashMap<Int, Float> = HashMap()

            // adding element
            for (i in 0..dataSize-1) {
                hashMap.put(i, distance(myLon, myLat,
                    this.data.get(i).longitude.toDouble(),
                    this.data.get(i).latitude.toDouble()))
//                Log.d("DALEM LOOP", distance(myLon, myLat,
//                    this.data.get(i).longitude.toDouble(),
//                    this.data.get(i).latitude.toDouble()).toString())
            }

            // sort hashmap
//            var sorted = hashMap.toSortedMap().values.toList()
            var sorted = hashMap.toList().sortedBy { (_, value) -> value}.toMap()
//            Log.d("JUMLAH DATA", dataSize.toString())
//            Log.d("JUMLAH HASHMAP", sorted.size.toString())
//            Log.d("HASH MAP", sorted.toString())

            // get 5 first
            for(item in sorted) {
                if (arrData.size >= 5) {
                    break
                }

                arrData.add(this.data.get(item.key))
//                Log.d("YANG MASUK", this.data.get(item.key).toString())
//                Log.d("DENGAN JARAk", item.value.toString())
            }
        }
        return arrData
    }

    fun distance(lon1: Double, lat1:Double, lon2: Double, lat2: Double): Float {
        val loc1: Location? = Location("")
        loc1!!.latitude = lat1
        loc1!!.longitude = lon1

        val loc2: Location? = Location("")
        loc2!!.latitude = lat2
        loc2!!.longitude = lon2

        val distance = loc1.distanceTo(loc2)
        return distance
    }
}
