packagelon com.twittelonr.timelonlinelonrankelonr.reloncap_author

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.selonrvo.util.FuturelonArrow
import com.twittelonr.timelonlinelonrankelonr.corelon.Candidatelonelonnvelonlopelon
import com.twittelonr.timelonlinelonrankelonr.modelonl.ReloncapQuelonry.DelonpelonndelonncyProvidelonr
import com.twittelonr.timelonlinelonrankelonr.modelonl.TwelonelontIdRangelon
import com.twittelonr.timelonlinelons.clielonnts.relonlelonvancelon_selonarch.SelonarchClielonnt
import com.twittelonr.timelonlinelons.clielonnts.relonlelonvancelon_selonarch.SelonarchClielonnt.TwelonelontTypelons
import com.twittelonr.timelonlinelons.modelonl.UselonrId
import com.twittelonr.util.Futurelon

/**
 * Felontch reloncap relonsults baselond on an author id selont passelond into thelon quelonry.
 * Calls into thelon samelon selonarch melonthod as Reloncap, but uselons thelon authorIds instelonad of thelon SGS-providelond followelondIds.
 */
class ReloncapAuthorSelonarchRelonsultsTransform(
  selonarchClielonnt: SelonarchClielonnt,
  maxCountProvidelonr: DelonpelonndelonncyProvidelonr[Int],
  relonlelonvancelonOptionsMaxHitsToProcelonssProvidelonr: DelonpelonndelonncyProvidelonr[Int],
  elonnablelonSelonttingTwelonelontTypelonsWithTwelonelontKindOptionProvidelonr: DelonpelonndelonncyProvidelonr[Boolelonan],
  statsReloncelonivelonr: StatsReloncelonivelonr,
  logSelonarchDelonbugInfo: Boolelonan = falselon)
    elonxtelonnds FuturelonArrow[Candidatelonelonnvelonlopelon, Candidatelonelonnvelonlopelon] {
  privatelon[this] val maxCountStat = statsReloncelonivelonr.stat("maxCount")
  privatelon[this] val numInputAuthorsStat = statsReloncelonivelonr.stat("numInputAuthors")
  privatelon[this] val elonxcludelondTwelonelontIdsStat = statsReloncelonivelonr.stat("elonxcludelondTwelonelontIds")

  ovelonrridelon delonf apply(elonnvelonlopelon: Candidatelonelonnvelonlopelon): Futurelon[Candidatelonelonnvelonlopelon] = {
    val maxCount = maxCountProvidelonr(elonnvelonlopelon.quelonry)
    maxCountStat.add(maxCount)

    val authorIds = elonnvelonlopelon.quelonry.authorIds.gelontOrelonlselon(Selonq.elonmpty[UselonrId])
    numInputAuthorsStat.add(authorIds.sizelon)

    val elonxcludelondTwelonelontIdsOpt = elonnvelonlopelon.quelonry.elonxcludelondTwelonelontIds
    elonxcludelondTwelonelontIdsOpt.map { elonxcludelondTwelonelontIds => elonxcludelondTwelonelontIdsStat.add(elonxcludelondTwelonelontIds.sizelon) }

    val twelonelontIdRangelon = elonnvelonlopelon.quelonry.rangelon
      .map(TwelonelontIdRangelon.fromTimelonlinelonRangelon)
      .gelontOrelonlselon(TwelonelontIdRangelon.delonfault)

    val belonforelonTwelonelontIdelonxclusivelon = twelonelontIdRangelon.toId
    val aftelonrTwelonelontIdelonxclusivelon = twelonelontIdRangelon.fromId

    val relonlelonvancelonOptionsMaxHitsToProcelonss = relonlelonvancelonOptionsMaxHitsToProcelonssProvidelonr(elonnvelonlopelon.quelonry)

    selonarchClielonnt
      .gelontUselonrsTwelonelontsForReloncap(
        uselonrId = elonnvelonlopelon.quelonry.uselonrId,
        followelondUselonrIds = authorIds.toSelont, // uselonr authorIds as thelon selont of followelond uselonrs
        relontwelonelontsMutelondUselonrIds = Selont.elonmpty,
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
        relonturnAllRelonsults = truelon,
        elonnablelonelonxcludelonSourcelonTwelonelontIdsQuelonry = falselon,
        relonlelonvancelonOptionsMaxHitsToProcelonss = relonlelonvancelonOptionsMaxHitsToProcelonss
      ).map { relonsults => elonnvelonlopelon.copy(selonarchRelonsults = relonsults) }
  }
}
