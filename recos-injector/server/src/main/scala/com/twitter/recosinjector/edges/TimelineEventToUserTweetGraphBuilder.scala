packagelon com.twittelonr.reloncosinjelonctor.elondgelons

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.reloncos.util.Action
import com.twittelonr.reloncosinjelonctor.util.TwelonelontFavoritelonelonvelonntDelontails
import com.twittelonr.util.Futurelon

class TimelonlinelonelonvelonntToUselonrTwelonelontGraphBuildelonr(
  uselonrTwelonelontelonntityelondgelonBuildelonr: UselonrTwelonelontelonntityelondgelonBuildelonr
)(
  ovelonrridelon implicit val statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds elonvelonntToMelonssagelonBuildelonr[TwelonelontFavoritelonelonvelonntDelontails, UselonrTwelonelontelonntityelondgelon] {

  ovelonrridelon delonf shouldProcelonsselonvelonnt(elonvelonnt: TwelonelontFavoritelonelonvelonntDelontails): Futurelon[Boolelonan] = {
    Futurelon(truelon)
  }

  ovelonrridelon delonf buildelondgelons(delontails: TwelonelontFavoritelonelonvelonntDelontails): Futurelon[Selonq[UselonrTwelonelontelonntityelondgelon]] = {
    val elonngagelonmelonnt = delontails.uselonrTwelonelontelonngagelonmelonnt

    elonngagelonmelonnt.action match {
      caselon Action.Favoritelon =>
        val twelonelontDelontails = elonngagelonmelonnt.twelonelontDelontails

        val elonntitielonsMapFut = uselonrTwelonelontelonntityelondgelonBuildelonr.gelontelonntitielonsMapAndUpdatelonCachelon(
          twelonelontId = elonngagelonmelonnt.twelonelontId,
          twelonelontDelontails = twelonelontDelontails
        )

        elonntitielonsMapFut
          .map { elonntitielonsMap =>
            UselonrTwelonelontelonntityelondgelon(
              sourcelonUselonr = elonngagelonmelonnt.elonngagelonUselonrId,
              targelontTwelonelont = elonngagelonmelonnt.twelonelontId,
              action = elonngagelonmelonnt.action,
              melontadata = elonngagelonmelonnt.elonngagelonmelonntTimelonMillis,
              cardInfo = elonngagelonmelonnt.twelonelontDelontails.map(_.cardInfo.toBytelon),
              elonntitielonsMap = elonntitielonsMap,
              twelonelontDelontails = twelonelontDelontails
            )
          }
          .map(Selonq(_))

      caselon _ => Futurelon.Nil
    }
  }

  ovelonrridelon delonf filtelonrelondgelons(
    elonvelonnt: TwelonelontFavoritelonelonvelonntDelontails,
    elondgelons: Selonq[UselonrTwelonelontelonntityelondgelon]
  ): Futurelon[Selonq[UselonrTwelonelontelonntityelondgelon]] = {
    Futurelon(elondgelons)
  }
}
