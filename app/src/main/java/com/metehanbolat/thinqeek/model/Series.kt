package com.metehanbolat.thinqeek.model

data class Series(
    var director: String,
    var name: String,
    var year: Int,
    var rate: Double,
    var season: Int,
    var time: String,
    var stars: String,
    var subject: String,
    var type: String,
    var downloadUrl: String,
    var comment: String,
    var date: String,
    var author: String
)