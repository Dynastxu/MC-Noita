package dynastxu.noita.utils

import kotlin.math.roundToInt

object UnitConversion {
    const val BASE_TIME: Int = 3
    const val BASE_DISTANCE: Double = 160.0
    const val BASE_GRAVITY: Double = 250.0 / 0.03

    /**
     * @see NoitaPx
     */
    fun px(value: Int): NoitaPx {
        return NoitaPx(value)
    }

    /**
     * @see NoitaFrame
     */
    fun f(value: Int): NoitaFrame {
        return NoitaFrame(value)
    }

    /**
     * @see NoitaGravity
     */
    fun g(value: Int): NoitaGravity {
        return NoitaGravity(value)
    }

    /**
     * Noita 单位
     * @param value 像素
     */
    data class NoitaPx(val value: Int) {
        fun toMcDistance(): Double {
            return value / BASE_DISTANCE
        }
    }

    /**
     * Noita 单位
     * @param value 帧
     */
    data class NoitaFrame(val value: Int) {
        fun toTick(): Int {
            return (value.toFloat() / BASE_TIME).roundToInt()
        }
    }

    /**
     * Noita 单位
     * @param value 重力
     */
    @JvmRecord
    data class NoitaGravity(val value: Int) {
        fun toMcG(): Double {
            return value / BASE_GRAVITY
        }
    }

    /**
     * Noita 单位
     * @param value 摩擦系数
     */
    data class NoitaFriction(val value: Float) {
        /**
         * 下一刻速度
         * @param px 当前速度（像素每帧）
         * @return 速度
         */
        fun nextTickSpeed(px: NoitaPx): Double {
            return nextTickSpeed(px.toMcDistance())
        }

        /**
         * 下一刻速度
         * @param v 当前速度
         * @return 速度
         */
        fun nextTickSpeed(v: Double): Double {
            return v * (1 - value * 0.05)
        }
    }

    data class NoitaMass(val value: Float) {
        fun toMcMass(): Float {
            return value
        }
    }
}
