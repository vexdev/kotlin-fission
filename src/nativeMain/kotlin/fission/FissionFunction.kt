package fission

import platform.posix.*
import kotlinx.cinterop.*

object FissionFunction {
    private val ENV_VAR_PATTERN = Regex("^([^=]+)=(.+)$")

    fun init(function: Function) {
        val environment = getEnvironment()
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

    private fun getEnvironment(): Map<String, String> {
        if (__environ == null) {
            throw IllegalArgumentException("Missing environment")
        }
        val envlist = mutableListOf<String>()
        var index = 0
        var variable = __environ?.get(index)
        while (variable != null) {
            val sb = StringBuilder()
            var iteridx = 0
            var iter = variable[iteridx]
            while (iter >= 32) {
                sb.append(iter.toInt().toChar())
                iter = variable[++iteridx]
            }
            val stringVariable = sb.toString()
            if (stringVariable.matches(ENV_VAR_PATTERN)) envlist.add(sb.toString())
            variable = __environ?.get(++index)
        }

        val result = mutableMapOf<String, String>()
        for (env in envlist) {
            val groups = ENV_VAR_PATTERN.matchEntire(env)!!.groupValues
            result[groups[1]] = groups[2]
        }
        return result
    }
}

interface Function {
    fun onCall(request: Request, headers: List<Header>): String
}