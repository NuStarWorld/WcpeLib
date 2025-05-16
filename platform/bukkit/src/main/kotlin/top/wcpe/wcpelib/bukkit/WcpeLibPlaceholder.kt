package top.wcpe.wcpelib.bukkit

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.OfflinePlayer

/**
 * 由 WCPE 在 2024/11/8 12:58 创建
 * <p>
 * Created by WCPE on 2024/11/8 12:58
 * <p>
 * <p>
 * GitHub  : <a href="https://github.com/wcpe">wcpe 's GitHub</a>
 * <p>
 * QQ      : 1837019522
 * @author : WCPE
 */
class WcpeLibPlaceholder : PlaceholderExpansion() {
    override fun getAuthor(): String {
        return "WCPE"
    }

    override fun getIdentifier(): String {
        return "WcpeLib"
    }

    override fun getVersion(): String {
        return "1.0.1"
    }

    //%WcpeLib_server_name%
    //%WcpeLib_display_name%
    //%WcpeLib_firstLoginTime_{playerName}% 首次登录时间
    //%WcpeLib_lastLoginTime_{playerName}% 最后登录时间
    override fun onRequest(p: OfflinePlayer, identifier: String): String {
        when (identifier) {
            "server_name" -> return WcpeLib.getServerName()
            "display_name" -> return WcpeLib.getDisplayName()
        }
        val splitParams = identifier.split("_")
        when (splitParams[0]) {
            "firstLoginTime" -> {
                val playerData = WcpeLib.dataManager.getPlayerDataByName(splitParams[1])
                return playerData?.firstLoginTime.toString()
            }
            "lastLoginTime" -> {
                val playerData = WcpeLib.dataManager.getPlayerDataByName(splitParams[1])
                return playerData?.lastLoginTime.toString()
            }
        }
        return ""

    }
}