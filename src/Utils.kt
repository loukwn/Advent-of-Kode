import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun gcd(a: Long, b: Long): Long {
    var reducedB = b
    var newA = a
    while (reducedB > 0) {
        val temp = reducedB
        reducedB = a % reducedB
        newA = temp
    }

    return newA
}

fun lcm(a: Long, b: Long): Long {
    return a * (b / gcd(a, b))
}

fun lcm(nums: List<Int>): Long {
    return nums.fold(nums[0].toLong()) { acc, l -> lcm(acc, l.toLong()) }
}
