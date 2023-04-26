package sign

import model.KeyBean
import println

/**
 * Creator: seon
 * data: 4/24/2023 9:53 AM
 * Description:
 */
object Signer {
    fun toSign(
        inputApk: String,
        outputApk: String,
        key: KeyBean,
    ) {
        runCatching {
            val apkPath = inputApk
            val outputPath = outputApk
            val keystorePath = key.keyFile
            val keystorePassword = key.keyStorePwd
            val keyAlias = key.keyAlias
            ////////////////////
            val apksignerCommand = mutableListOf<String>()
            apksignerCommand.add("apksigner.bat")
            apksignerCommand.add("sign")
            apksignerCommand.add("--out")
            apksignerCommand.add(outputPath)
            apksignerCommand.add("--ks")
            apksignerCommand.add(keystorePath)
            apksignerCommand.add("--ks-pass")
            apksignerCommand.add("pass:$keystorePassword")
            apksignerCommand.add("--key-pass")
            apksignerCommand.add("pass:$keystorePassword")
            apksignerCommand.add("--ks-key-alias")
            apksignerCommand.add(keyAlias)
            apksignerCommand.add("--v2-signing-enabled")
            apksignerCommand.add("true")
            apksignerCommand.add("--v3-signing-enabled")
            apksignerCommand.add("false")
            apksignerCommand.add(apkPath)

            val processBuilder = ProcessBuilder(apksignerCommand)
            processBuilder.redirectErrorStream(true)
            val process = processBuilder.start()

            // 读取命令行输出流并打印
            val inputStream = process.inputStream
            val bufferedReader = inputStream.bufferedReader()
            var line: String? = bufferedReader.readLine()
            while (line != null) {
                println(line)
                line = bufferedReader.readLine()
            }

            val exitCode = process.waitFor()
            if (exitCode != 0) {
                throw RuntimeException("Failed to sign APK with apksigner: $exitCode")
            }
        }.onFailure {
            "报错${it.println()}".println()
            it.printStackTrace()
        }
    }

}
