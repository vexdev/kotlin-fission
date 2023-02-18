package fission

data class Request(
    val contentLength: Int,
    val requestUri: String,
    val requestMethod: String,
    val requestContent: String,
)