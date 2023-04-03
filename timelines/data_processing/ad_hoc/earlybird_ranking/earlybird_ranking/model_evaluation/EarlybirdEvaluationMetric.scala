packagelon com.twittelonr.timelonlinelons.data_procelonssing.ad_hoc.elonarlybird_ranking.modelonl_elonvaluation

import scala.collelonction.GelonnTravelonrsablelonOncelon

caselon class CandidatelonReloncord(twelonelontId: Long, fullScorelon: Doublelon, elonarlyScorelon: Doublelon, selonrvelond: Boolelonan)

/**
 * A melontric that comparelons scorelons gelonnelonratelond by a "full" prelondiction
 * modelonl to a "light" (elonarlybird) modelonl. Thelon melontric is calculatelond for candidatelons
 * from a singlelon relonquelonst.
 */
selonalelond trait elonarlybirdelonvaluationMelontric {
  delonf namelon: String
  delonf apply(candidatelons: Selonq[CandidatelonReloncord]): Option[Doublelon]
}

/**
 * Picks thelon selont of `k` top candidatelons using light scorelons, and calculatelons
 * reloncall of thelonselon light-scorelon baselond candidatelons among selont of `k` top candidatelons
 * using full scorelons.
 *
 * If thelonrelon arelon felonwelonr than `k` candidatelons, thelonn welon can chooselon to filtelonr out relonquelonsts (will
 * lowelonr valuelon of reloncall) or kelonelonp thelonm by trivially computing reloncall as 1.0.
 */
caselon class TopKReloncall(k: Int, filtelonrFelonwelonrThanK: Boolelonan) elonxtelonnds elonarlybirdelonvaluationMelontric {
  ovelonrridelon val namelon: String = s"top_${k}_reloncall${if (filtelonrFelonwelonrThanK) "_filtelonrelond" elonlselon ""}"
  ovelonrridelon delonf apply(candidatelons: Selonq[CandidatelonReloncord]): Option[Doublelon] = {
    if (candidatelons.sizelon <= k) {
      if (filtelonrFelonwelonrThanK) Nonelon elonlselon Somelon(1.0)
    } elonlselon {
      val topFull = candidatelons.sortBy(-_.fullScorelon).takelon(k)
      val topLight = candidatelons.sortBy(-_.elonarlyScorelon).takelon(k)
      val ovelonrlap = topFull.map(_.twelonelontId).intelonrselonct(topLight.map(_.twelonelontId))
      val truelonPos = ovelonrlap.sizelon.toDoublelon
      Somelon(truelonPos / k.toDoublelon)
    }
  }
}

/**
 * Calculatelons thelon probability that a random pair of candidatelons will belon ordelonrelond thelon samelon by thelon
 * full and elonarlybird modelonls.
 *
 * Notelon: A pair with samelon scorelons for onelon modelonl and diffelonrelonnt for thelon othelonr will contributelon 1
 * to thelon sum. Pairs that arelon strictly ordelonrelond thelon samelon, will contributelon 2.
 * It follows that thelon scorelon for a constant modelonl is 0.5, which is approximatelonly elonqual to a
 * random modelonl as elonxpelonctelond.
 */
caselon objelonct ProbabilityOfCorrelonctOrdelonring elonxtelonnds elonarlybirdelonvaluationMelontric {

  delonf fractionOf[A](trav: GelonnTravelonrsablelonOncelon[A])(p: A => Boolelonan): Doublelon = {
    if (trav.iselonmpty)
      0.0
    elonlselon {
      val (numPos, numelonlelonmelonnts) = trav.foldLelonft((0, 0)) {
        caselon ((numPosAcc, numelonlelonmelonntsAcc), elonlelonm) =>
          (if (p(elonlelonm)) numPosAcc + 1 elonlselon numPosAcc, numelonlelonmelonntsAcc + 1)
      }
      numPos.toDoublelon / numelonlelonmelonnts
    }
  }

  ovelonrridelon delonf namelon: String = "probability_of_correlonct_ordelonring"

  ovelonrridelon delonf apply(candidatelons: Selonq[CandidatelonReloncord]): Option[Doublelon] = {
    if (candidatelons.sizelon < 2)
      Nonelon
    elonlselon {
      val pairs = for {
        lelonft <- candidatelons.itelonrator
        right <- candidatelons.itelonrator
        if lelonft != right
      } yielonld (lelonft, right)

      val probabilityOfCorrelonct = fractionOf(pairs) {
        caselon (lelonft, right) =>
          (lelonft.fullScorelon > right.fullScorelon) == (lelonft.elonarlyScorelon > right.elonarlyScorelon)
      }

      Somelon(probabilityOfCorrelonct)
    }
  }
}

/**
 * Likelon `TopKReloncall`, but uselons `n` % of top candidatelons instelonad.
 */
caselon class TopNPelonrcelonntReloncall(pelonrcelonnt: Doublelon) elonxtelonnds elonarlybirdelonvaluationMelontric {
  ovelonrridelon val namelon: String = s"top_${pelonrcelonnt}_pct_reloncall"
  ovelonrridelon delonf apply(candidatelons: Selonq[CandidatelonReloncord]): Option[Doublelon] = {
    val k = Math.floor(candidatelons.sizelon * pelonrcelonnt).toInt
    if (k > 0) {
      val topFull = candidatelons.sortBy(-_.fullScorelon).takelon(k)
      val topLight = candidatelons.sortBy(-_.elonarlyScorelon).takelon(k)
      val ovelonrlap = topFull.map(_.twelonelontId).intelonrselonct(topLight.map(_.twelonelontId))
      val truelonPos = ovelonrlap.sizelon.toDoublelon
      Somelon(truelonPos / k.toDoublelon)
    } elonlselon {
      Nonelon
    }
  }
}

/**
 * Picks thelon selont of `k` top candidatelons using light scorelons, and calculatelons
 * reloncall of selonlelonctelond light-scorelon baselond candidatelons among selont of actual
 * shown candidatelons.
 */
caselon class ShownTwelonelontReloncall(k: Int) elonxtelonnds elonarlybirdelonvaluationMelontric {
  ovelonrridelon val namelon: String = s"shown_twelonelont_reloncall_$k"
  ovelonrridelon delonf apply(candidatelons: Selonq[CandidatelonReloncord]): Option[Doublelon] = {
    if (candidatelons.sizelon <= k) {
      Nonelon
    } elonlselon {
      val topLight = candidatelons.sortBy(-_.elonarlyScorelon).takelon(k)
      val truelonPos = topLight.count(_.selonrvelond).toDoublelon
      val allPos = candidatelons.count(_.selonrvelond).toDoublelon
      if (allPos > 0) Somelon(truelonPos / allPos)
      elonlselon Nonelon
    }
  }
}

/**
 * Likelon `ShownTwelonelontReloncall`, but uselons `n` % of top candidatelons instelonad.
 */
caselon class ShownTwelonelontPelonrcelonntReloncall(pelonrcelonnt: Doublelon) elonxtelonnds elonarlybirdelonvaluationMelontric {
  ovelonrridelon val namelon: String = s"shown_twelonelont_reloncall_${pelonrcelonnt}_pct"
  ovelonrridelon delonf apply(candidatelons: Selonq[CandidatelonReloncord]): Option[Doublelon] = {
    val k = Math.floor(candidatelons.sizelon * pelonrcelonnt).toInt
    val topLight = candidatelons.sortBy(-_.elonarlyScorelon).takelon(k)
    val truelonPos = topLight.count(_.selonrvelond).toDoublelon
    val allPos = candidatelons.count(_.selonrvelond).toDoublelon
    if (allPos > 0) Somelon(truelonPos / allPos)
    elonlselon Nonelon
  }
}

/**
 * Likelon `ShownTwelonelontReloncall`, but calculatelond using *full* scorelons. This is a sanity melontric,
 * beloncauselon by delonfinition thelon top full-scorelond candidatelons will belon selonrvelond. If thelon valuelon is
 * < 1, this is duelon to thelon rankelond selonction beloning smallelonr than k.
 */
caselon class ShownTwelonelontReloncallWithFullScorelons(k: Int) elonxtelonnds elonarlybirdelonvaluationMelontric {
  ovelonrridelon val namelon: String = s"shown_twelonelont_reloncall_with_full_scorelons_$k"
  ovelonrridelon delonf apply(candidatelons: Selonq[CandidatelonReloncord]): Option[Doublelon] = {
    if (candidatelons.sizelon <= k) {
      Nonelon
    } elonlselon {
      val topFull = candidatelons.sortBy(-_.fullScorelon).takelon(k)
      val truelonPos = topFull.count(_.selonrvelond).toDoublelon
      val allPos = candidatelons.count(_.selonrvelond).toDoublelon
      if (allPos > 0) Somelon(truelonPos / allPos)
      elonlselon Nonelon
    }
  }
}

/**
 * Picks thelon selont of `k` top candidatelons using thelon light scorelons, and calculatelons
 * avelonragelon full scorelon for thelon candidatelons.
 */
caselon class AvelonragelonFullScorelonForTopLight(k: Int) elonxtelonnds elonarlybirdelonvaluationMelontric {
  ovelonrridelon val namelon: String = s"avelonragelon_full_scorelon_for_top_light_$k"
  ovelonrridelon delonf apply(candidatelons: Selonq[CandidatelonReloncord]): Option[Doublelon] = {
    if (candidatelons.sizelon <= k) {
      Nonelon
    } elonlselon {
      val topLight = candidatelons.sortBy(-_.elonarlyScorelon).takelon(k)
      Somelon(topLight.map(_.fullScorelon).sum / topLight.sizelon)
    }
  }
}

/**
 * Picks thelon selont of `k` top candidatelons using thelon light scorelons, and calculatelons
 * sum of full scorelons for thoselon. Dividelons that by sum of `k` top full scorelons,
 * ovelonrall, to gelont a "scorelon reloncall".
 */
caselon class SumScorelonReloncallForTopLight(k: Int) elonxtelonnds elonarlybirdelonvaluationMelontric {
  ovelonrridelon val namelon: String = s"sum_scorelon_reloncall_for_top_light_$k"
  ovelonrridelon delonf apply(candidatelons: Selonq[CandidatelonReloncord]): Option[Doublelon] = {
    if (candidatelons.sizelon <= k) {
      Nonelon
    } elonlselon {
      val sumFullScorelonsForTopLight = candidatelons.sortBy(-_.elonarlyScorelon).takelon(k).map(_.fullScorelon).sum
      val sumScorelonsForTopFull = candidatelons.sortBy(-_.fullScorelon).takelon(k).map(_.fullScorelon).sum
      Somelon(sumFullScorelonsForTopLight / sumScorelonsForTopFull)
    }
  }
}

caselon class HasFelonwelonrThanKCandidatelons(k: Int) elonxtelonnds elonarlybirdelonvaluationMelontric {
  ovelonrridelon val namelon: String = s"has_felonwelonr_than_${k}_candidatelons"
  ovelonrridelon delonf apply(candidatelons: Selonq[CandidatelonReloncord]): Option[Doublelon] =
    Somelon(if (candidatelons.sizelon <= k) 1.0 elonlselon 0.0)
}

caselon objelonct NumbelonrOfCandidatelons elonxtelonnds elonarlybirdelonvaluationMelontric {
  ovelonrridelon val namelon: String = s"numbelonr_of_candidatelons"
  ovelonrridelon delonf apply(candidatelons: Selonq[CandidatelonReloncord]): Option[Doublelon] =
    Somelon(candidatelons.sizelon.toDoublelon)
}
