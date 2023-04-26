package model

import println
import tools.JsonUtil
import java.io.File

/**
 * Creator: seon
 * data: 4/24/2023 10:21 PM
 * Description:
 */
object KeyStoreRepo {

    private const val fileName = SeonConst.keyJsonFile
    fun fetchKeyMap(): Map<String, KeyBean> {
        val jsonFile = File(fileName)
        if (!jsonFile.exists()) {
            throw Exception("$fileName not found!")
        }
        val json = jsonFile.readText()
        val list = JsonUtil.fromJson<List<KeyBean>>(json)

        return list.associateBy({ (list.indexOf(it) + 1).toString() }, { it })
    }
}