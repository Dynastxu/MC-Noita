package dynastxu.noita.utils

import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import kotlin.math.max
import kotlin.math.min

object MathHelper {
    fun nextRandomIntBetweenInclusive(a: Int, b: Int): Int {
        val min = min(a, b)
        val max = max(a, b)
        if (min == max) {
            return min
        }
        val random = RandomSource.create()
        return random.nextIntBetweenInclusive(min, max)
    }

    fun nextRandomFloat(a: Float, b: Float, level: Level? = null): Float {
        val min = min(a, b)
        val max = max(a, b)
        if (min == max) {
            return min
        }
        val random = level?.random ?: RandomSource.create()
        return min + random.nextFloat() * (max - min)
    }

    fun nextRandomDouble(a: Double, b: Double, level: Level? = null): Double {
        val min = min(a, b)
        val max = max(a, b)
        if (min == max) {
            return min
        }
        val random = level?.random ?: RandomSource.create()
        return min + random.nextDouble() * (max - min)
    }
}
