package com.tw ter.servo.cac 

 mport com.google.common.base.Charsets
 mport com.tw ter.ut l.Try

/**
 * Fast  mple ntat on of deal ng w h  mcac d counters.
 *
 *  mcac   s funkytown for  ncr and decr. Bas cally,   store a number,
 * as a STR NG, and t n  ncr and decr that. T  abstracts over that deta l.
 *
 * T   mple ntat on was qu e a b  faster than t  s mple  mple ntat on
 * of `new Str ng(bytes, Charsets.US_ASC  ).toLong()`
 * and `Long.toStr ng(value).getBytes()`
 *
 * Thread-safe.
 */
object CounterSer al zer extends Ser al zer[Long] {
  pr vate[t ] val M nus = '-'.toByte
  // T  lo r bound
  pr vate[t ] val Zero = '0'.toByte
  // T  upper bound
  pr vate[t ] val N ne = '9'.toByte

  // Max length for   byte arrays that'll f  all pos  ve longs
  pr vate[t ] val MaxByteArrayLength = 19

  overr de def to(long: Long): Try[Array[Byte]] = Try {
    // NOTE: code based on Long.toStr ng(value), but   avo ds creat ng t 
    //  nter d ate Str ng object and t  charset encod ng  n Str ng.getBytes
    // T  was about 12% faster than call ng Long.toStr ng(long).getBytes
     f (long == Long.M nValue) {
      "-9223372036854775808".getBytes(Charsets.US_ASC  )
    } else {
      val s ze =  f (long < 0) str ngS ze(-long) + 1 else str ngS ze(long)
      val bytes = new Array[Byte](s ze)

      var  sNegat ve = false
      var endAt = 0
      var currentLong =  f (long < 0) {
         sNegat ve = true
        endAt = 1
        -long
      } else {
        long
      }

      // Note: look at t   mple ntat on  n Long.getChars(long,  nt, char[])
      // T y can do 2 d g s at a t   for t , so   could speed t  up
      // See: D v s on by  nvar ant  ntegers us ng Mult pl cat on
      // http://gmpl b.org/~tege/d vcnst-pld 94.pdf

      // start ng at t  least s gn f cant d g  and work ng   way up...
      var pos = s ze - 1
      do {
        val byte = currentLong % 10
        bytes(pos) = (Zero + byte).toByte
        currentLong /= 10
        pos -= 1
      } wh le (currentLong != 0)

       f ( sNegat ve) {
        assert(pos == 0, "For value " + long + ", pos " + pos)
        bytes(0) = M nus
      }

      bytes
    }
  }

  overr de def from(bytes: Array[Byte]): Try[Long] = Try {
    // T   mple ntat on was about 4x faster than t  s mple:
    //    new Str ng(bytes, Charsets.US_ASC  ).toLong

     f (bytes.length < 1)
      throw new NumberFormatExcept on("Empty byte arrays are unsupported")

    val  sNegat ve = bytes(0) == M nus
     f ( sNegat ve && bytes.length == 1)
      throw new NumberFormatExcept on(bytes.mkStr ng(","))

    //   count  n negat ve numbers so   don't have problems at Long.MaxValue
    var total = 0L
    val endAt = bytes.length
    var   =  f ( sNegat ve) 1 else 0
    wh le (  < endAt) {
      val b = bytes( )
       f (b < Zero || b > N ne)
        throw new NumberFormatExcept on(bytes.mkStr ng(","))

      val  nt = b - Zero
      total = (total * 10L) -  nt

        += 1
    }

     f ( sNegat ve) total else -total
  }

  /**
   * @param long must be non-negat ve
   */
  pr vate[t ] def str ngS ze(long: Long):  nt = {
    var p = 10
    var   = 1
    wh le (  < MaxByteArrayLength) {
       f (long < p) return  
      p *= 10
        += 1
    }
    MaxByteArrayLength
  }

}
