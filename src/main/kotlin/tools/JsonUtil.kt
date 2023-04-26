package tools

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Creator: seon
 * data: 3/28/2023 9:45 PM
 * Description:
 */
object JsonUtil {
    @PublishedApi
    internal val gson = Gson()

    /**
     * 将 JSON 字符串转换成对象
     *
     * @param json JSON 字符串
     * @param clazz 要转换成的对象类型
     * @return 转换后的对象
     */
    fun <T> fromJson(json: String, clazz: Class<T>): T {
        return gson.fromJson(json, clazz)
    }

    fun <T> fromJson(json: String, typeOf: Type): T {
        return gson.fromJson(json, typeOf)
    }

    inline fun <reified T : Any> fromJson(json: String): T {
        return gson.fromJson(json, object : TypeToken<T>() {}.type)
    }

    /**
     * 将对象转换成 JSON 字符串
     *
     * @param obj 要转换的对象
     * @return 转换后的 JSON 字符串
     */
    fun toJson(obj: Any): String {
        return gson.toJson(obj)
    }


    fun jsonToMap(jsonStr: String): Map<*, *> {
        val type = object : TypeToken<Map<*, *>?>() {}.type
        return gson.fromJson(jsonStr, type)
    }

    fun beanToMap(bean: Any): Map<String, Any> {
        return kotlin.runCatching {
            val type = object : TypeToken<Map<String, Any>>() {}.type
            gson.fromJson<Map<String, Any>>(gson.toJsonTree(bean), type)
        }.getOrNull() ?: emptyMap()
    }

}