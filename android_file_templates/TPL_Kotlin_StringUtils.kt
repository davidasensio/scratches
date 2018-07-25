package ${PACKAGE_NAME}

@Suppress("unused")
object StringUtils {

    fun isEmpty(string: String?): Boolean {
        return string == null || string.trim { it <= ' ' } == ""
    }

    fun isNull(string: String?): Boolean {
        return string == null || string.trim { it <= ' ' } == "null"
    }

    fun containsHttp(string: String): Boolean {
        return !isEmpty(string) && (string.contains("http:") || string.contains("https:"))
    }
}
