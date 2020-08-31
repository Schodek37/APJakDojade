package com.example.apjakdojade

class ApJakDojadeInfo {

    companion object {
        var fileLocation = ""
        var haveStopsBeenRead = false

        const val zipFileName = "GTFS.zip"

        val unzipLocation: String
            get() = this.fileLocation + "unzipped/"

        val stopsLocation: String
            get() = this.unzipLocation + "stops.txt"

        val zipFileLocation: String
            get() = fileLocation + zipFileName
    }
}