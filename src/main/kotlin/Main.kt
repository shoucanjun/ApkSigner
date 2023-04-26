import model.KeyBean
import model.KeyStoreRepo
import model.SeonConst
import tools.ApkHelper
import sign.Signer
import tools.JsonUtil
import java.io.File

fun main() {
    start()
}

val keystoreMap by lazy {
    KeyStoreRepo.fetchKeyMap()
}

fun start() {
    printLine()
    "Welcome to signApk!".println()
    //select method
    selectMethod()
    "Finish ALL!".println()
    "Wanna continue? Y/N".println()
    printLine()
    if (readln().uppercase() == "Y") {
        start()
    }
}

fun selectMethod() {
    "Please select serve~".println()
    "1.Clean history".println()
    "2.To sign apk".println()
    when (readln()) {
        "1" -> {
            File(SeonConst.outputFile).listFiles().forEach {
                it.delete()
            }
            File(SeonConst.inputFile).listFiles().forEach {
                it.delete()
            }
        }

        "2" -> {
            start2Sign()
        }
    }
}

fun start2Sign() {
    // 获取key
    val signInfoResult = fetchSigner()
    signInfoResult.onFailure {
        it.message?.println() ?: run {
            it.printStackTrace()
            println("unknown error！")
        }
        start()
        return
    }
    //拿到选择的key
    val selectKey = signInfoResult.getOrNull()!!
    "You have selected :${selectKey.keyFile}".println()
    //寻找需要签名的apk
    ApkHelper.findInputApks().apply {
        if (isNullOrEmpty()) {
            println("Not found any apk need to be signed!")
            return@apply
        }
        forEach {
            "${it.name} -> gonna be signed>>>>>".println()
            Signer.toSign(it.path, ApkHelper.makeOutputPath(it.path).path, selectKey)
            "${it.name} Done!!!"
        }
    }
}


/**
 * 获取选择的key
 */
private fun fetchSigner(): Result<KeyBean> {
    "Please enter a number to select a key:".println()
    keystoreMap.forEach { (index, bean) ->
        "$index.${bean.keyFile}".println()
    }
    return runCatching {
        val index = readln()
        if (keystoreMap.containsKey(index)) {
            keystoreMap[index]!!
        } else {
            throw Exception("Illegal input!")
        }
    }
}
