package com.example.osmapi.core


class OSMConnection(private var apiUrl: String,
                    private var userAgent: String,
                    private var oauthToken: String? = null,
                    private var timeout: Int = DEFAULT_TIMEOUT) {

    fun setTimeout(timeout: Int){
        this.timeout = timeout
    }



    companion object{
        private  const val DEFAULT_TIMEOUT: Int = 45*1000
    }
}