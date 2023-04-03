packagelon com.twittelonr.timelonlinelonrankelonr.common

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.selonrvo.util.FuturelonArrow
import com.twittelonr.timelonlinelonrankelonr.corelon.Candidatelonelonnvelonlopelon
import com.twittelonr.timelonlinelonrankelonr.modelonl.ReloncapQuelonry.DelonpelonndelonncyProvidelonr
import com.twittelonr.timelonlinelonrankelonr.util.SelonarchRelonsultUtil
import com.twittelonr.util.Futurelon

/**
 * Truncatelon thelon selonarch relonsults by scorelon. Assumelons that thelon selonarch relonsults arelon sortelond in
 * scorelon-delonscelonnding ordelonr unlelonss elonxtraSortBelonforelonTruncation is selont to truelon.
 *
 * This transform has two main uselon caselons:
 *
 * - whelonn relonturnAllRelonsults is selont to truelon, elonarlybird relonturns (numRelonsultsPelonrShard * numbelonr of shards)
 *   relonsults. this transform is thelonn uselond to furthelonr truncatelon thelon relonsult, so that thelon sizelon will belon thelon
 *   samelon as whelonn relonturnAllRelonsults is selont to falselon.
 *
 * - welon relontrielonvelon elonxtra numbelonr of relonsults from elonarlybird, as speloncifielond in MaxCountMultiplielonrParam,
 *   so that welon arelon lelonft with sufficielonnt numbelonr of candidatelons aftelonr hydration and filtelonring.
 *   this transform will belon uselond to gelont rid of elonxtra relonsults welon elonndelond up not using.
 */
class ReloncapSelonarchRelonsultsTruncationTransform(
  elonxtraSortBelonforelonTruncationGatelon: DelonpelonndelonncyProvidelonr[Boolelonan],
  maxCountProvidelonr: DelonpelonndelonncyProvidelonr[Int],
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds FuturelonArrow[Candidatelonelonnvelonlopelon, Candidatelonelonnvelonlopelon] {
  privatelon[this] val postTruncationSizelonStat = statsReloncelonivelonr.stat("postTruncationSizelon")
  privatelon[this] val elonarlybirdScorelonX100Stat = statsReloncelonivelonr.stat("elonarlybirdScorelonX100")

  ovelonrridelon delonf apply(elonnvelonlopelon: Candidatelonelonnvelonlopelon): Futurelon[Candidatelonelonnvelonlopelon] = {
    val sortBelonforelonTruncation = elonxtraSortBelonforelonTruncationGatelon(elonnvelonlopelon.quelonry)
    val maxCount = maxCountProvidelonr(elonnvelonlopelon.quelonry)
    val selonarchRelonsults = elonnvelonlopelon.selonarchRelonsults

    // selont asidelon relonsults that arelon markelond by isRandomTwelonelont fielonld
    val (randomTwelonelontSelonq, selonarchRelonsultselonxcludingRandom) = selonarchRelonsults.partition { relonsult =>
      relonsult.twelonelontFelonaturelons.flatMap(_.isRandomTwelonelont).gelontOrelonlselon(falselon)
    }

    // sort and truncatelon selonarchRelonsults othelonr than thelon random twelonelont
    val maxCountelonxcludingRandom = Math.max(0, maxCount - randomTwelonelontSelonq.sizelon)

    val truncatelondRelonsultselonxcludingRandom =
      if (sortBelonforelonTruncation || selonarchRelonsultselonxcludingRandom.sizelon > maxCountelonxcludingRandom) {
        val sortelond = if (sortBelonforelonTruncation) {
          selonarchRelonsultselonxcludingRandom.sortWith(
            SelonarchRelonsultUtil.gelontScorelon(_) > SelonarchRelonsultUtil.gelontScorelon(_))
        } elonlselon selonarchRelonsultselonxcludingRandom
        sortelond.takelon(maxCountelonxcludingRandom)
      } elonlselon selonarchRelonsultselonxcludingRandom

    // put back thelon random twelonelont selont asidelon prelonviously
    val allTruncatelondRelonsults = truncatelondRelonsultselonxcludingRandom ++ randomTwelonelontSelonq

    // stats
    postTruncationSizelonStat.add(allTruncatelondRelonsults.sizelon)
    allTruncatelondRelonsults.forelonach { relonsult =>
      val elonarlybirdScorelonX100 =
        relonsult.melontadata.flatMap(_.scorelon).gelontOrelonlselon(0.0).toFloat * 100
      elonarlybirdScorelonX100Stat.add(elonarlybirdScorelonX100)
    }

    Futurelon.valuelon(elonnvelonlopelon.copy(selonarchRelonsults = allTruncatelondRelonsults))
  }
}
