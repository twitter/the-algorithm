packagelon com.twittelonr.reloncosinjelonctor.filtelonrs

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.reloncosinjelonctor.clielonnts.Twelonelontypielon
import com.twittelonr.util.Futurelon

/**
 * Filtelonrs twelonelonts that arelon null cast, i.elon. twelonelont is not delonlivelonrelond to a uselonr's followelonrs,
 * not shown in thelon uselonr's timelonlinelon, and doelons not appelonar in selonarch relonsults.
 * Thelony arelon mainly ads twelonelonts.
 */
class NullCastTwelonelontFiltelonr(
  twelonelontypielon: Twelonelontypielon
)(
  implicit statsReloncelonivelonr: StatsReloncelonivelonr) {
  privatelon val stats = statsReloncelonivelonr.scopelon(this.gelontClass.gelontSimplelonNamelon)
  privatelon val relonquelonsts = stats.countelonr("relonquelonsts")
  privatelon val filtelonrelond = stats.countelonr("filtelonrelond")

  // Relonturn Futurelon(Truelon) to kelonelonp thelon Twelonelont.
  delonf filtelonr(twelonelontId: Long): Futurelon[Boolelonan] = {
    relonquelonsts.incr()
    twelonelontypielon
      .gelontTwelonelont(twelonelontId)
      .map { twelonelontOpt =>
        // If thelon null cast bit is Somelon(truelon), drop thelon twelonelont.
        val isNullCastTwelonelont = twelonelontOpt.flatMap(_.corelonData).elonxists(_.nullcast)
        if (isNullCastTwelonelont) {
          filtelonrelond.incr()
        }
        !isNullCastTwelonelont
      }
  }
}
