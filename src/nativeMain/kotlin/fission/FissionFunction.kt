package fission

object FissionFunction {
    fun init(function: Function) {
        val environment = Environment.getEnvironment()
        val contentLength = environment["CONTENT_LENGTH"]?.toIntOrNull() ?: 0
        val requestUri = environment["REQUEST_URI"]!!
        val requestMethod = environment["REQUEST_METHOD"]!!
        val content = readlnOrNull() ?: ""
        val response =
            function.onCall(Request(contentLength, requestUri, requestMethod, content), getHeaders(environment))
        print(response)
    }

    private fun getHeaders(environment: Map<String, String>): List<Header> = environment
        .filterKeys { it.startsWith("HTTP_") }
        .map { Header(it.key.drop(5), it.value) }

}

interface Function {
    fun onCall(request: Request, headers: List<Header>): String
}