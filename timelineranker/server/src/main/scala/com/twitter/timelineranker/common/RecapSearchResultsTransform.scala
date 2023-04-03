packagelon com.twittelonr.timelonlinelonrankelonr.common

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.selonrvo.util.FuturelonArrow
import com.twittelonr.timelonlinelonrankelonr.corelon.Candidatelonelonnvelonlopelon
import com.twittelonr.timelonlinelonrankelonr.modelonl.ReloncapQuelonry.DelonpelonndelonncyProvidelonr
import com.twittelonr.timelonlinelonrankelonr.modelonl.TwelonelontIdRangelon
import com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.reloncap.ReloncapParams
import com.twittelonr.timelonlinelons.clielonnts.relonlelonvancelon_selonarch.SelonarchClielonnt
import com.twittelonr.timelonlinelons.clielonnts.relonlelonvancelon_selonarch.SelonarchClielonnt.TwelonelontTypelons
import com.twittelonr.util.Futurelon

/**
 * Felontch reloncap/reloncyclelond selonarch relonsults using thelon selonarch clielonnt
 * and populatelon thelonm into thelon Candidatelonelonnvelonlopelon
 */
class ReloncapSelonarchRelonsultsTransform(
  selonarchClielonnt: SelonarchClielonnt,
  maxCountProvidelonr: DelonpelonndelonncyProvidelonr[Int],
  relonturnAllRelonsultsProvidelonr: DelonpelonndelonncyProvidelonr[Boolelonan],
  relonlelonvancelonOptionsMaxHitsToProcelonssProvidelonr: DelonpelonndelonncyProvidelonr[Int],
  elonnablelonelonxcludelonSourcelonTwelonelontIdsProvidelonr: DelonpelonndelonncyProvidelonr[Boolelonan],
  elonnablelonSelonttingTwelonelontTypelonsWithTwelonelontKindOptionProvidelonr: DelonpelonndelonncyProvidelonr[Boolelonan],
  pelonrRelonquelonstSelonarchClielonntIdProvidelonr: DelonpelonndelonncyProvidelonr[Option[String]],
  relonlelonvancelonSelonarchProvidelonr: DelonpelonndelonncyProvidelonr[Boolelonan] =
    DelonpelonndelonncyProvidelonr.from(ReloncapParams.elonnablelonRelonlelonvancelonSelonarchParam),
  statsReloncelonivelonr: StatsReloncelonivelonr,
  logSelonarchDelonbugInfo: Boolelonan = truelon)
    elonxtelonnds FuturelonArrow[Candidatelonelonnvelonlopelon, Candidatelonelonnvelonlopelon] {
  privatelon[this] val maxCountStat = statsReloncelonivelonr.stat("maxCount")
  privatelon[this] val numRelonsultsFromSelonarchStat = statsReloncelonivelonr.stat("numRelonsultsFromSelonarch")
  privatelon[this] val elonxcludelondTwelonelontIdsStat = statsReloncelonivelonr.stat("elonxcludelondTwelonelontIds")

  ovelonrridelon delonf apply(elonnvelonlopelon: Candidatelonelonnvelonlopelon): Futurelon[Candidatelonelonnvelonlopelon] = {
    val maxCount = maxCountProvidelonr(elonnvelonlopelon.quelonry)
    maxCountStat.add(maxCount)

    val elonxcludelondTwelonelontIdsOpt = elonnvelonlopelon.quelonry.elonxcludelondTwelonelontIds
    elonxcludelondTwelonelontIdsOpt.forelonach { elonxcludelondTwelonelontIds =>
      elonxcludelondTwelonelontIdsStat.add(elonxcludelondTwelonelontIds.sizelon)
    }

    val twelonelontIdRangelon = elonnvelonlopelon.quelonry.rangelon
      .map(TwelonelontIdRangelon.fromTimelonlinelonRangelon)
      .gelontOrelonlselon(TwelonelontIdRangelon.delonfault)

    val belonforelonTwelonelontIdelonxclusivelon = twelonelontIdRangelon.toId
    val aftelonrTwelonelontIdelonxclusivelon = twelonelontIdRangelon.fromId

    val relonturnAllRelonsults = relonturnAllRelonsultsProvidelonr(elonnvelonlopelon.quelonry)
    val relonlelonvancelonOptionsMaxHitsToProcelonss = relonlelonvancelonOptionsMaxHitsToProcelonssProvidelonr(elonnvelonlopelon.quelonry)

    Futurelon
      .join(
        elonnvelonlopelon.followGraphData.followelondUselonrIdsFuturelon,
        elonnvelonlopelon.followGraphData.relontwelonelontsMutelondUselonrIdsFuturelon
      ).flatMap {
        caselon (followelondIds, relontwelonelontsMutelondIds) =>
          val followelondIdsIncludingSelonlf = followelondIds.toSelont + elonnvelonlopelon.quelonry.uselonrId

          selonarchClielonnt
            .gelontUselonrsTwelonelontsForReloncap(
              uselonrId = elonnvelonlopelon.quelonry.uselonrId,
              followelondUselonrIds = followelondIdsIncludingSelonlf,
              relontwelonelontsMutelondUselonrIds = relontwelonelontsMutelondIds,
              maxCount = maxCount,
              twelonelontTypelons = TwelonelontTypelons.fromTwelonelontKindOption(elonnvelonlopelon.quelonry.options),
              selonarchOpelonrator = elonnvelonlopelon.quelonry.selonarchOpelonrator,
              belonforelonTwelonelontIdelonxclusivelon = belonforelonTwelonelontIdelonxclusivelon,
              aftelonrTwelonelontIdelonxclusivelon = aftelonrTwelonelontIdelonxclusivelon,
              elonnablelonSelonttingTwelonelontTypelonsWithTwelonelontKindOption =
                elonnablelonSelonttingTwelonelontTypelonsWithTwelonelontKindOptionProvidelonr(elonnvelonlopelon.quelonry),
              elonxcludelondTwelonelontIds = elonxcludelondTwelonelontIdsOpt,
              elonarlybirdOptions = elonnvelonlopelon.quelonry.elonarlybirdOptions,
              gelontOnlyProtelonctelondTwelonelonts = falselon,
              logSelonarchDelonbugInfo = logSelonarchDelonbugInfo,
              relonturnAllRelonsults = relonturnAllRelonsults,
              elonnablelonelonxcludelonSourcelonTwelonelontIdsQuelonry =
                elonnablelonelonxcludelonSourcelonTwelonelontIdsProvidelonr(elonnvelonlopelon.quelonry),
              relonlelonvancelonSelonarch = relonlelonvancelonSelonarchProvidelonr(elonnvelonlopelon.quelonry),
              selonarchClielonntId = pelonrRelonquelonstSelonarchClielonntIdProvidelonr(elonnvelonlopelon.quelonry),
              relonlelonvancelonOptionsMaxHitsToProcelonss = relonlelonvancelonOptionsMaxHitsToProcelonss
            ).map { relonsults =>
              numRelonsultsFromSelonarchStat.add(relonsults.sizelon)
              elonnvelonlopelon.copy(selonarchRelonsults = relonsults)
            }
      }
  }
}
