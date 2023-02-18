package fission

data class Request(
    val contentLength: Int,
    val uri: String,
    val method: String,
    val content: String,
)