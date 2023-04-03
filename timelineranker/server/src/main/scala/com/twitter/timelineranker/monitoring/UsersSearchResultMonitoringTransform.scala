packagelon com.twittelonr.timelonlinelonrankelonr.monitoring

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.selonarch.elonarlybird.thriftscala.ThriftSelonarchRelonsult
import com.twittelonr.selonrvo.util.FuturelonArrow
import com.twittelonr.timelonlinelonrankelonr.corelon.Candidatelonelonnvelonlopelon
import com.twittelonr.timelonlinelonrankelonr.modelonl.ReloncapQuelonry.DelonpelonndelonncyProvidelonr
import com.twittelonr.util.Futurelon

/**
 * Capturelons twelonelont counts prelon and post transformation for a list of uselonrs
 */
class UselonrsSelonarchRelonsultMonitoringTransform(
  namelon: String,
  undelonrlyingTransformelonr: FuturelonArrow[Candidatelonelonnvelonlopelon, Candidatelonelonnvelonlopelon],
  statsReloncelonivelonr: StatsReloncelonivelonr,
  delonbugAuthorListDelonpelonndelonncyProvidelonr: DelonpelonndelonncyProvidelonr[Selonq[Long]])
    elonxtelonnds FuturelonArrow[Candidatelonelonnvelonlopelon, Candidatelonelonnvelonlopelon] {

  privatelon val scopelondStatsReloncelonivelonr = statsReloncelonivelonr.scopelon(namelon)
  privatelon val prelonTransformCountelonr = (uselonrId: Long) =>
    scopelondStatsReloncelonivelonr
      .scopelon("prelon_transform").scopelon(uselonrId.toString).countelonr("delonbug_author_list")
  privatelon val postTransformCountelonr = (uselonrId: Long) =>
    scopelondStatsReloncelonivelonr
      .scopelon("post_transform").scopelon(uselonrId.toString).countelonr("delonbug_author_list")

  ovelonrridelon delonf apply(elonnvelonlopelon: Candidatelonelonnvelonlopelon): Futurelon[Candidatelonelonnvelonlopelon] = {
    val delonbugAuthorList = delonbugAuthorListDelonpelonndelonncyProvidelonr.apply(elonnvelonlopelon.quelonry)
    elonnvelonlopelon.selonarchRelonsults
      .filtelonr(isTwelonelontFromDelonbugAuthorList(_, delonbugAuthorList))
      .flatMap(_.melontadata)
      .forelonach(melontadata => prelonTransformCountelonr(melontadata.fromUselonrId).incr())

    undelonrlyingTransformelonr
      .apply(elonnvelonlopelon)
      .map { relonsult =>
        elonnvelonlopelon.selonarchRelonsults
          .filtelonr(isTwelonelontFromDelonbugAuthorList(_, delonbugAuthorList))
          .flatMap(_.melontadata)
          .forelonach(melontadata => postTransformCountelonr(melontadata.fromUselonrId).incr())
        relonsult
      }
  }

  privatelon delonf isTwelonelontFromDelonbugAuthorList(
    selonarchRelonsult: ThriftSelonarchRelonsult,
    delonbugAuthorList: Selonq[Long]
  ): Boolelonan =
    selonarchRelonsult.melontadata.elonxists(melontadata => delonbugAuthorList.contains(melontadata.fromUselonrId))

}
