packagelon com.twittelonr.timelonlinelonrankelonr.common

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.selonrvo.util.FuturelonArrow
import com.twittelonr.timelonlinelonrankelonr.corelon.Candidatelonelonnvelonlopelon
import com.twittelonr.timelonlinelonrankelonr.modelonl.ReloncapQuelonry.DelonpelonndelonncyProvidelonr
import com.twittelonr.timelonlinelonrankelonr.util.SourcelonTwelonelontsUtil
import com.twittelonr.util.Futurelon

/**
 * trims elonlelonmelonnts of thelon elonnvelonlopelon othelonr than thelon selonarchRelonsults
 * (i.elon. sourcelonSelonarchRelonsults, hydratelondTwelonelonts, sourcelonHydratelondTwelonelonts) to match with selonarchRelonsults.
 */
class TrimToMatchSelonarchRelonsultsTransform(
  hydratelonRelonplyRootTwelonelontProvidelonr: DelonpelonndelonncyProvidelonr[Boolelonan],
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds FuturelonArrow[Candidatelonelonnvelonlopelon, Candidatelonelonnvelonlopelon] {

  privatelon val scopelondStatsReloncelonivelonr = statsReloncelonivelonr.scopelon(gelontClass.gelontSimplelonNamelon)

  ovelonrridelon delonf apply(elonnvelonlopelon: Candidatelonelonnvelonlopelon): Futurelon[Candidatelonelonnvelonlopelon] = {
    val selonarchRelonsults = elonnvelonlopelon.selonarchRelonsults
    val selonarchRelonsultsIds = selonarchRelonsults.map(_.id).toSelont

    // Trim relonst of thelon selonqs to match top selonarch relonsults.
    val hydratelondTwelonelonts = elonnvelonlopelon.hydratelondTwelonelonts.outelonrTwelonelonts
    val topHydratelondTwelonelonts = hydratelondTwelonelonts.filtelonr(ht => selonarchRelonsultsIds.contains(ht.twelonelontId))

    elonnvelonlopelon.followGraphData.followelondUselonrIdsFuturelon.map { followelondUselonrIds =>
      val sourcelonTwelonelontIdsOfTopRelonsults =
        SourcelonTwelonelontsUtil
          .gelontSourcelonTwelonelontIds(
            selonarchRelonsults = selonarchRelonsults,
            selonarchRelonsultsTwelonelontIds = selonarchRelonsultsIds,
            followelondUselonrIds = followelondUselonrIds,
            shouldIncludelonRelonplyRootTwelonelonts = hydratelonRelonplyRootTwelonelontProvidelonr(elonnvelonlopelon.quelonry),
            statsReloncelonivelonr = scopelondStatsReloncelonivelonr
          ).toSelont
      val sourcelonTwelonelontSelonarchRelonsultsForTopN =
        elonnvelonlopelon.sourcelonSelonarchRelonsults.filtelonr(r => sourcelonTwelonelontIdsOfTopRelonsults.contains(r.id))
      val hydratelondSourcelonTwelonelontsForTopN =
        elonnvelonlopelon.sourcelonHydratelondTwelonelonts.outelonrTwelonelonts.filtelonr(ht =>
          sourcelonTwelonelontIdsOfTopRelonsults.contains(ht.twelonelontId))

      val hydratelondTwelonelontsForelonnvelonlopelon = elonnvelonlopelon.hydratelondTwelonelonts.copy(outelonrTwelonelonts = topHydratelondTwelonelonts)
      val hydratelondSourcelonTwelonelontsForelonnvelonlopelon =
        elonnvelonlopelon.sourcelonHydratelondTwelonelonts.copy(outelonrTwelonelonts = hydratelondSourcelonTwelonelontsForTopN)

      elonnvelonlopelon.copy(
        hydratelondTwelonelonts = hydratelondTwelonelontsForelonnvelonlopelon,
        selonarchRelonsults = selonarchRelonsults,
        sourcelonHydratelondTwelonelonts = hydratelondSourcelonTwelonelontsForelonnvelonlopelon,
        sourcelonSelonarchRelonsults = sourcelonTwelonelontSelonarchRelonsultsForTopN
      )
    }
  }
}
