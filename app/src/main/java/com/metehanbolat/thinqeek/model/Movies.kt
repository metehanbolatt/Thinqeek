package com.metehanbolat.thinqeek.model

data class Movies(
    var director: String,
    var name: String,
    var year: Int,
    var comment: String,
    var rate: Double,
    var downloadUrl: String,
    var date: String
)