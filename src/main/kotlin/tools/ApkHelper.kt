package tools

import model.ApkInfoBean
import model.SeonConst
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

/**
 * Creator: seon
 * data: 4/24/2023 11:07 PM
 * Description:
 */
object ApkHelper {
    fun findInputApks() = File(SeonConst.inputFile).listFiles().filter {
        it.isFile && it.name.endsWith(".apk")
    }

    fun makeOutputPath(originalApkPath: String) :File{
        val apkInfoBean = fetchApkInfo(originalApkPath)
        return File(SeonConst.outputFile, "${File(originalApkPath).nameWithoutExtension}_v${apkInfoBean.versionName}(${apkInfoBean.versionCode})_${System.currentTimeMillis().formatTime(DateExt.FORMAT)}.apk")
    }

    fun fetchApkInfo(apkPath: String): ApkInfoBean {
        val processBuilder = ProcessBuilder("aapt.exe", "dump", "badging", apkPath).redirectErrorStream(true)
        val process = processBuilder.start()
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        var line: String?
        var applicationLabel: String? = ""
        var versionName: String? = ""
        var versionCode: String? = ""
        while (reader.readLine().also { line = it } != null) {
            if (line!!.startsWith("package")) {
                val versionNameRegex = "versionName='([^']+)'".toRegex()
                val versionCodeRegex = "versionCode='(\\d+)'".toRegex()
                versionName = versionNameRegex.find(line!!)?.groupValues?.getOrNull(1)
                versionCode = versionCodeRegex.find(line!!)?.groupValues?.getOrNull(1)
            } else if (line!!.startsWith("application-label:")) {
                applicationLabel = line!!.substringAfter(":").trim().removeSurrounding("'")
            }
        }
        return ApkInfoBean(applicationLabel!!, versionName!!, versionCode!!)
    }

}