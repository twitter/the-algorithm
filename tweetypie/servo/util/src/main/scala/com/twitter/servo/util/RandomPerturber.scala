package com.twitter.servo.util

import com.twitter.util.Duration
import scala.util.Random

/**
 * A class for generating bounded random fluctuations around a given Duration.
 */
class RandomPerturber(percentage: Float, rnd: Random = new Random) extends (Duration => Duration) {
  assert(percentage > 0 && percentage < 1, "percentage must be > 0 and < 1")

  override def apply(dur: Duration): Duration = {
    val ns = dur.inNanoseconds
    Duration.fromNanoseconds((ns + ((2 * rnd.nextFloat - 1) * percentage * ns)).toLong)
  }
}
