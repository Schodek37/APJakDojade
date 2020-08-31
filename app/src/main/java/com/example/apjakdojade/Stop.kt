package com.example.apjakdojade

 class Stop(var stopID: String, var stopName: String, var stopLat: Double, var stopLon: Double, var stopURL: String) {

    companion object Stops {
        val stops = mutableListOf<Stop>()
        public fun getURL(elo : String): String{
            var found = ""
            for(item in stops){
                if(item.stopName==elo){
                    found = item.stopURL
                    break;
                }
            }
            return found
        }
    }


}