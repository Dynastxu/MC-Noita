package dynastxu.noita.utils

import net.minecraft.util.RandomSource
import kotlin.math.max
import kotlin.math.min

object MathHelper {
    fun getRandomIntInRange(a: Int, b: Int): Int {
        return getRandomIntInRange(Pair(a, b))
    }

    fun getRandomIntInRange(range: Pair<Int, Int>): Int {
        val min = min(range.first, range.second)
        val max = max(range.first, range.second)
        if (min == max) {
            return min
        }
        return RandomSource.create().nextIntBetweenInclusive(min, max)
    }

    fun getRandomFloatInRange(range: Pair<Float, Float>): Float {
        val min = min(range.first, range.second)
        val max = max(range.first, range.second)
        if (min == max) {
            return min
        }
        val random = RandomSource.create()
        return min + random.nextFloat() * (max - min)
    }

    fun getRandomDoubleInRange(a: Double, b: Double): Double {
        return getRandomDoubleInRange(Pair(a, b))
    }

    fun getRandomDoubleInRange(range: Pair<Double, Double>): Double {
        val min = min(range.first, range.second)
        val max = max(range.first, range.second)
        if (min == max) {
            return min
        }
        val random = RandomSource.create()
        return min + random.nextDouble() * (max - min)
    }
}
