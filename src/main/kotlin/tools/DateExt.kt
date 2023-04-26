package tools

import java.text.SimpleDateFormat
import java.util.*

/**
 * Creator: seon
 * data: 4/8/2023 10:29 AM
 * Description:
 */

/**
 * 扩展 Date 类，返回指定格式的日期字符串
 */

object DateExt {
    const val FORMAT_HOUR_FORMAT = "HH:mm" //16:08
    const val FORMAT_MINUTE_SECOND = "mm:ss" //16:08
    const val FORMAT_HOUR_FORMAT_SECOND = "HH:mm:ss" //16:08:00
    const val FORMAT_YEAR_FORMAT_HOUR_MINUTE = "yyyy-MM-dd HH:mm" //2015-12-31 16:08
    const val FORMAT_YEAR_FORMAT_HOUR_SECOND = "yyyy-MM-dd HH:mm:ss" //2015-12-31 16:08:00
    const val FORMAT = "yyyy-MM-dd"
    const val FORMAT_GO = "yyyy-MM-ddTHH:mm:ss"//2023-03-18T17:58:43+08:00
    const val FORMAT_CN = "yyyy年MM月dd日"
    const val FORMAT_MD_CN = "M月d日"
}

//2023-03-26T23:47:40+08:00
fun String.convertGoDate2Normal() = replace("T", " ").substringBefore("+")

fun Date.formatTime(pattern: String): String {
    val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return dateFormat.format(this)
}

/**
 * 扩展 String 类，将日期字符串转换为 Date 对象
 */
fun String.toDate(pattern: String): Date? {
    val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    try {
        return dateFormat.parse(this)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

/**
 * 扩展 Long 类型时间戳，返回指定格式的日期字符串
 */
fun Long.formatTime(pattern: String): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar.time.formatTime(pattern)
}