package top.wcpe.wcpelib.bukkit.entity

import java.time.LocalDate
import java.util.UUID

/**
 * @author NuStar
 * @since 2025/12/7 17:13
 */
data class PlayerOnlineData(
    var id: Long? = null,
    var playerUuid: UUID,
    var statDate: LocalDate,
    var onlineMinutes: Int = 0,
    var loginCount: Int = 0,
) {
    companion object {
        @JvmStatic
        fun create(playerUuid: UUID): PlayerOnlineData {
            return PlayerOnlineData(null, playerUuid, LocalDate.now(), 0)
        }

        @JvmStatic
        fun or(playerUuid: UUID, playerOnlineData: PlayerOnlineData?): PlayerOnlineData {
            return playerOnlineData ?: create(playerUuid)
        }
    }
}
