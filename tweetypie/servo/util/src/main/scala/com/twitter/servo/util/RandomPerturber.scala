package com.tw ter.servo.ut l

 mport com.tw ter.ut l.Durat on
 mport scala.ut l.Random

/**
 * A class for generat ng bounded random fluctuat ons around a g ven Durat on.
 */
class RandomPerturber(percentage: Float, rnd: Random = new Random) extends (Durat on => Durat on) {
  assert(percentage > 0 && percentage < 1, "percentage must be > 0 and < 1")

  overr de def apply(dur: Durat on): Durat on = {
    val ns = dur. nNanoseconds
    Durat on.fromNanoseconds((ns + ((2 * rnd.nextFloat - 1) * percentage * ns)).toLong)
  }
}
