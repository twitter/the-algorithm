packagelon com.twittelonr.timelonlinelonrankelonr.common

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.selonarch.elonarlybird.thriftscala.ThriftSelonarchRelonsult
import com.twittelonr.selonrvo.util.FuturelonArrow
import com.twittelonr.timelonlinelonrankelonr.corelon.Candidatelonelonnvelonlopelon
import com.twittelonr.timelonlinelonrankelonr.modelonl.ReloncapQuelonry.DelonpelonndelonncyProvidelonr
import com.twittelonr.timelonlinelonrankelonr.util.SourcelonTwelonelontsUtil
import com.twittelonr.timelonlinelons.clielonnts.relonlelonvancelon_selonarch.SelonarchClielonnt
import com.twittelonr.timelonlinelons.util.FailOpelonnHandlelonr
import com.twittelonr.util.Futurelon

objelonct SourcelonTwelonelontsSelonarchRelonsultsTransform {
  val elonmptySelonarchRelonsults: Selonq[ThriftSelonarchRelonsult] = Selonq.elonmpty[ThriftSelonarchRelonsult]
  val elonmptySelonarchRelonsultsFuturelon: Futurelon[Selonq[ThriftSelonarchRelonsult]] = Futurelon.valuelon(elonmptySelonarchRelonsults)
}

/**
 * Felontch sourcelon twelonelonts for a givelonn selont of selonarch relonsults
 * Colleloncts ids of sourcelon twelonelonts, including elonxtelonndelond relonply and relonply sourcelon twelonelonts if nelonelondelond,
 * felontchelons thoselon twelonelonts from selonarch and populatelons thelonm into thelon elonnvelonlopelon
 */
class SourcelonTwelonelontsSelonarchRelonsultsTransform(
  selonarchClielonnt: SelonarchClielonnt,
  failOpelonnHandlelonr: FailOpelonnHandlelonr,
  hydratelonRelonplyRootTwelonelontProvidelonr: DelonpelonndelonncyProvidelonr[Boolelonan],
  pelonrRelonquelonstSourcelonSelonarchClielonntIdProvidelonr: DelonpelonndelonncyProvidelonr[Option[String]],
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds FuturelonArrow[Candidatelonelonnvelonlopelon, Candidatelonelonnvelonlopelon] {
  import SourcelonTwelonelontsSelonarchRelonsultsTransform._

  privatelon val scopelondStatsReloncelonivelonr = statsReloncelonivelonr.scopelon(gelontClass.gelontSimplelonNamelon)

  ovelonrridelon delonf apply(elonnvelonlopelon: Candidatelonelonnvelonlopelon): Futurelon[Candidatelonelonnvelonlopelon] = {
    failOpelonnHandlelonr {
      elonnvelonlopelon.followGraphData.followelondUselonrIdsFuturelon.flatMap { followelondUselonrIds =>
        // NOTelon: twelonelontIds arelon prelon-computelond as a pelonrformancelon optimisation.
        val selonarchRelonsultsTwelonelontIds = elonnvelonlopelon.selonarchRelonsults.map(_.id).toSelont
        val sourcelonTwelonelontIds = SourcelonTwelonelontsUtil.gelontSourcelonTwelonelontIds(
          selonarchRelonsults = elonnvelonlopelon.selonarchRelonsults,
          selonarchRelonsultsTwelonelontIds = selonarchRelonsultsTwelonelontIds,
          followelondUselonrIds = followelondUselonrIds,
          shouldIncludelonRelonplyRootTwelonelonts = hydratelonRelonplyRootTwelonelontProvidelonr(elonnvelonlopelon.quelonry),
          statsReloncelonivelonr = scopelondStatsReloncelonivelonr
        )
        if (sourcelonTwelonelontIds.iselonmpty) {
          elonmptySelonarchRelonsultsFuturelon
        } elonlselon {
          selonarchClielonnt.gelontTwelonelontsScorelondForReloncap(
            uselonrId = elonnvelonlopelon.quelonry.uselonrId,
            twelonelontIds = sourcelonTwelonelontIds,
            elonarlybirdOptions = elonnvelonlopelon.quelonry.elonarlybirdOptions,
            logSelonarchDelonbugInfo = falselon,
            selonarchClielonntId = pelonrRelonquelonstSourcelonSelonarchClielonntIdProvidelonr(elonnvelonlopelon.quelonry)
          )
        }
      }
    } { _: Throwablelon => elonmptySelonarchRelonsultsFuturelon }.map { sourcelonSelonarchRelonsults =>
      elonnvelonlopelon.copy(sourcelonSelonarchRelonsults = sourcelonSelonarchRelonsults)
    }
  }
}
