package com.tw ter.graph.batch.job.t epcred

/**
 *  lper class to calculate reputat on, borro d from repo reputat ons
 */
object Reputat on {

  /**
   * convert pagerank to t epcred bet en 0 and 100,
   * take from repo reputat ons, ut l/Ut ls.scala
   */
  def scaledReputat on(raw: Double): Byte = {
     f (raw == 0 || (raw < 1.0e-20)) {
      0
    } else {
      // convert log(pagerank) to a number bet en 0 and 100
      // t  two para ters are from a l near f  by convert ng
      // max pagerank -> 95
      // m n pagerank -> 15
      val e: Double = 130d + 5.21 * scala.math.log(raw) // log to t  base e
      val pos = scala.math.r nt(e)
      val v =  f (pos > 100) 100.0 else  f (pos < 0) 0.0 else pos
      v.toByte
    }
  }

  // t se constants are take from repo reputat ons, conf g/product on.conf
  pr vate val threshAbsNumFr endsReps = 2500
  pr vate val constantD v s onFactorGt_threshFr endsToFollo rsRat oReps = 3.0
  pr vate val threshFr endsToFollo rsRat oUMass = 0.6
  pr vate val maxD vFactorReps = 50

  /**
   * reduce pagerank of users w h low follo rs but h gh follow ngs
   */
  def adjustReputat onsPostCalculat on(mass: Double, numFollo rs:  nt, numFollow ngs:  nt) = {
     f (numFollow ngs > threshAbsNumFr endsReps) {
      val fr endsToFollo rsRat o = (1.0 + numFollow ngs) / (1.0 + numFollo rs)
      val d vFactor =
        scala.math.exp(
          constantD v s onFactorGt_threshFr endsToFollo rsRat oReps *
            (fr endsToFollo rsRat o - threshFr endsToFollo rsRat oUMass) *
            scala.math.log(scala.math.log(numFollow ngs))
        )
      mass / ((d vFactor m n maxD vFactorReps) max 1.0)
    } else {
      mass
    }
  }
}
