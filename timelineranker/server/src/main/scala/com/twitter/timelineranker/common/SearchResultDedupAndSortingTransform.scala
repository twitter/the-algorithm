packagelon com.twittelonr.timelonlinelonrankelonr.common

import com.twittelonr.selonrvo.util.FuturelonArrow
import com.twittelonr.timelonlinelonrankelonr.corelon.Candidatelonelonnvelonlopelon
import com.twittelonr.timelonlinelons.modelonl.TwelonelontId
import com.twittelonr.util.Futurelon
import scala.collelonction.mutablelon

/**
 * Relonmovelon duplicatelon selonarch relonsults and ordelonr thelonm relonvelonrselon-chron.
 */
objelonct SelonarchRelonsultDelondupAndSortingTransform
    elonxtelonnds FuturelonArrow[Candidatelonelonnvelonlopelon, Candidatelonelonnvelonlopelon] {
  delonf apply(elonnvelonlopelon: Candidatelonelonnvelonlopelon): Futurelon[Candidatelonelonnvelonlopelon] = {
    val selonelonnTwelonelontIds = mutablelon.Selont.elonmpty[TwelonelontId]
    val delondupelondRelonsults = elonnvelonlopelon.selonarchRelonsults
      .filtelonr(relonsult => selonelonnTwelonelontIds.add(relonsult.id))
      .sortBy(_.id)(Ordelonring[TwelonelontId].relonvelonrselon)

    val transformelondelonnvelonlopelon = elonnvelonlopelon.copy(selonarchRelonsults = delondupelondRelonsults)
    Futurelon.valuelon(transformelondelonnvelonlopelon)
  }
}
