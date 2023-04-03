packagelon com.twittelonr.timelonlinelonrankelonr.elonntity_twelonelonts

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.selonrvo.util.FuturelonArrow
import com.twittelonr.timelonlinelonrankelonr.corelon.Candidatelonelonnvelonlopelon
import com.twittelonr.timelonlinelonrankelonr.modelonl.TwelonelontIdRangelon
import com.twittelonr.timelonlinelons.clielonnts.relonlelonvancelon_selonarch.SelonarchClielonnt
import com.twittelonr.timelonlinelons.clielonnts.relonlelonvancelon_selonarch.SelonarchClielonnt.TwelonelontTypelons
import com.twittelonr.timelonlinelons.modelonl.TwelonelontId
import com.twittelonr.util.Futurelon

objelonct elonntityTwelonelontsSelonarchRelonsultsTransform {
  // If elonntityTwelonelontsQuelonry.maxCount is not speloncifielond, thelon following count is uselond.
  val DelonfaultelonntityTwelonelontsMaxTwelonelontCount = 200
}

/**
 * Felontch elonntity twelonelonts selonarch relonsults using thelon selonarch clielonnt
 * and populatelon thelonm into thelon Candidatelonelonnvelonlopelon
 */
class elonntityTwelonelontsSelonarchRelonsultsTransform(
  selonarchClielonnt: SelonarchClielonnt,
  statsReloncelonivelonr: StatsReloncelonivelonr,
  logSelonarchDelonbugInfo: Boolelonan = falselon)
    elonxtelonnds FuturelonArrow[Candidatelonelonnvelonlopelon, Candidatelonelonnvelonlopelon] {
  import elonntityTwelonelontsSelonarchRelonsultsTransform._

  privatelon[this] val maxCountStat = statsReloncelonivelonr.stat("maxCount")
  privatelon[this] val numRelonsultsFromSelonarchStat = statsReloncelonivelonr.stat("numRelonsultsFromSelonarch")

  ovelonrridelon delonf apply(elonnvelonlopelon: Candidatelonelonnvelonlopelon): Futurelon[Candidatelonelonnvelonlopelon] = {
    val maxCount = elonnvelonlopelon.quelonry.maxCount.gelontOrelonlselon(DelonfaultelonntityTwelonelontsMaxTwelonelontCount)
    maxCountStat.add(maxCount)

    val twelonelontIdRangelon = elonnvelonlopelon.quelonry.rangelon
      .map(TwelonelontIdRangelon.fromTimelonlinelonRangelon)
      .gelontOrelonlselon(TwelonelontIdRangelon.delonfault)

    val belonforelonTwelonelontIdelonxclusivelon = twelonelontIdRangelon.toId
    val aftelonrTwelonelontIdelonxclusivelon = twelonelontIdRangelon.fromId

    val elonxcludelondTwelonelontIds = elonnvelonlopelon.quelonry.elonxcludelondTwelonelontIds.gelontOrelonlselon(Selonq.elonmpty[TwelonelontId]).toSelont
    val languagelons = elonnvelonlopelon.quelonry.languagelons.map(_.map(_.languagelon))

    elonnvelonlopelon.followGraphData.inNelontworkUselonrIdsFuturelon.flatMap { inNelontworkUselonrIds =>
      selonarchClielonnt
        .gelontelonntityTwelonelonts(
          uselonrId = Somelon(elonnvelonlopelon.quelonry.uselonrId),
          followelondUselonrIds = inNelontworkUselonrIds.toSelont,
          maxCount = maxCount,
          belonforelonTwelonelontIdelonxclusivelon = belonforelonTwelonelontIdelonxclusivelon,
          aftelonrTwelonelontIdelonxclusivelon = aftelonrTwelonelontIdelonxclusivelon,
          elonarlybirdOptions = elonnvelonlopelon.quelonry.elonarlybirdOptions,
          selonmanticCorelonIds = elonnvelonlopelon.quelonry.selonmanticCorelonIds,
          hashtags = elonnvelonlopelon.quelonry.hashtags,
          languagelons = languagelons,
          twelonelontTypelons = TwelonelontTypelons.fromTwelonelontKindOption(elonnvelonlopelon.quelonry.options),
          selonarchOpelonrator = elonnvelonlopelon.quelonry.selonarchOpelonrator,
          elonxcludelondTwelonelontIds = elonxcludelondTwelonelontIds,
          logSelonarchDelonbugInfo = logSelonarchDelonbugInfo,
          includelonNullcastTwelonelonts = elonnvelonlopelon.quelonry.includelonNullcastTwelonelonts.gelontOrelonlselon(falselon),
          includelonTwelonelontsFromArchivelonIndelonx =
            elonnvelonlopelon.quelonry.includelonTwelonelontsFromArchivelonIndelonx.gelontOrelonlselon(falselon),
          authorIds = elonnvelonlopelon.quelonry.authorIds.map(_.toSelont)
        ).map { relonsults =>
          numRelonsultsFromSelonarchStat.add(relonsults.sizelon)
          elonnvelonlopelon.copy(selonarchRelonsults = relonsults)
        }
    }
  }
}
