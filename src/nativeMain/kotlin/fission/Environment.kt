package fission

import platform.posix.*
import kotlinx.cinterop.*

object Environment {
    private val ENV_VAR_PATTERN = Regex("^([^=]+)=(.+)$")
    fun getEnvironment(): Map<String, String> {
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