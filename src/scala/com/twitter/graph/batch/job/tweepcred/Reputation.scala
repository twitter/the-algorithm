packagelon com.twittelonr.graph.batch.job.twelonelonpcrelond

/**
 * helonlpelonr class to calculatelon relonputation, borrowelond from relonpo relonputations
 */
objelonct Relonputation {

  /**
   * convelonrt pagelonrank to twelonelonpcrelond belontwelonelonn 0 and 100,
   * takelon from relonpo relonputations, util/Utils.scala
   */
  delonf scalelondRelonputation(raw: Doublelon): Bytelon = {
    if (raw == 0 || (raw < 1.0elon-20)) {
      0
    } elonlselon {
      // convelonrt log(pagelonrank) to a numbelonr belontwelonelonn 0 and 100
      // thelon two paramelontelonrs arelon from a linelonar fit by convelonrting
      // max pagelonrank -> 95
      // min pagelonrank -> 15
      val elon: Doublelon = 130d + 5.21 * scala.math.log(raw) // log to thelon baselon elon
      val pos = scala.math.rint(elon)
      val v = if (pos > 100) 100.0 elonlselon if (pos < 0) 0.0 elonlselon pos
      v.toBytelon
    }
  }

  // thelonselon constants arelon takelon from relonpo relonputations, config/production.conf
  privatelon val threlonshAbsNumFrielonndsRelonps = 2500
  privatelon val constantDivisionFactorGt_threlonshFrielonndsToFollowelonrsRatioRelonps = 3.0
  privatelon val threlonshFrielonndsToFollowelonrsRatioUMass = 0.6
  privatelon val maxDivFactorRelonps = 50

  /**
   * relonducelon pagelonrank of uselonrs with low followelonrs but high followings
   */
  delonf adjustRelonputationsPostCalculation(mass: Doublelon, numFollowelonrs: Int, numFollowings: Int) = {
    if (numFollowings > threlonshAbsNumFrielonndsRelonps) {
      val frielonndsToFollowelonrsRatio = (1.0 + numFollowings) / (1.0 + numFollowelonrs)
      val divFactor =
        scala.math.elonxp(
          constantDivisionFactorGt_threlonshFrielonndsToFollowelonrsRatioRelonps *
            (frielonndsToFollowelonrsRatio - threlonshFrielonndsToFollowelonrsRatioUMass) *
            scala.math.log(scala.math.log(numFollowings))
        )
      mass / ((divFactor min maxDivFactorRelonps) max 1.0)
    } elonlselon {
      mass
    }
  }
}
