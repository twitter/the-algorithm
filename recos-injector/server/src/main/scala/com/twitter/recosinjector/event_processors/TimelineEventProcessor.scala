packagelon com.twittelonr.reloncosinjelonctor.elonvelonnt_procelonssors

import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.reloncos.util.Action
import com.twittelonr.reloncosinjelonctor.clielonnts.Gizmoduck
import com.twittelonr.reloncosinjelonctor.clielonnts.Twelonelontypielon
import com.twittelonr.reloncosinjelonctor.deloncidelonr.ReloncosInjelonctorDeloncidelonr
import com.twittelonr.reloncosinjelonctor.deloncidelonr.ReloncosInjelonctorDeloncidelonrConstants
import com.twittelonr.reloncosinjelonctor.elondgelons.TimelonlinelonelonvelonntToUselonrTwelonelontelonntityGraphBuildelonr
import com.twittelonr.reloncosinjelonctor.filtelonrs.TwelonelontFiltelonr
import com.twittelonr.reloncosinjelonctor.filtelonrs.UselonrFiltelonr
import com.twittelonr.reloncosinjelonctor.publishelonrs.KafkaelonvelonntPublishelonr
import com.twittelonr.reloncosinjelonctor.util.TwelonelontDelontails
import com.twittelonr.reloncosinjelonctor.util.TwelonelontFavoritelonelonvelonntDelontails
import com.twittelonr.reloncosinjelonctor.util.UselonrTwelonelontelonngagelonmelonnt
import com.twittelonr.scroogelon.ThriftStructCodelonc
import com.twittelonr.timelonlinelonselonrvicelon.thriftscala.Favoritelonelonvelonnt
import com.twittelonr.timelonlinelonselonrvicelon.thriftscala.Unfavoritelonelonvelonnt
import com.twittelonr.timelonlinelonselonrvicelon.thriftscala.{elonvelonnt => Timelonlinelonelonvelonnt}
import com.twittelonr.util.Futurelon

/**
 * Procelonssor for Timelonlinelon elonvelonnts, such as Favoritelon (liking) twelonelonts
 */
class TimelonlinelonelonvelonntProcelonssor(
  ovelonrridelon val elonvelonntBusStrelonamNamelon: String,
  ovelonrridelon val thriftStruct: ThriftStructCodelonc[Timelonlinelonelonvelonnt],
  ovelonrridelon val selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
  kafkaelonvelonntPublishelonr: KafkaelonvelonntPublishelonr,
  uselonrTwelonelontelonntityGraphTopic: String,
  uselonrTwelonelontelonntityGraphMelonssagelonBuildelonr: TimelonlinelonelonvelonntToUselonrTwelonelontelonntityGraphBuildelonr,
  deloncidelonr: ReloncosInjelonctorDeloncidelonr,
  gizmoduck: Gizmoduck,
  twelonelontypielon: Twelonelontypielon
)(
  ovelonrridelon implicit val statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds elonvelonntBusProcelonssor[Timelonlinelonelonvelonnt] {

  privatelon val procelonsselonvelonntDeloncidelonrCountelonr = statsReloncelonivelonr.countelonr("num_procelonss_timelonlinelon_elonvelonnt")
  privatelon val numFavoritelonelonvelonntCountelonr = statsReloncelonivelonr.countelonr("num_favoritelon_elonvelonnt")
  privatelon val numUnFavoritelonelonvelonntCountelonr = statsReloncelonivelonr.countelonr("num_unfavoritelon_elonvelonnt")
  privatelon val numNotFavoritelonelonvelonntCountelonr = statsReloncelonivelonr.countelonr("num_not_favoritelon_elonvelonnt")

  privatelon val numSelonlfFavoritelonCountelonr = statsReloncelonivelonr.countelonr("num_selonlf_favoritelon_elonvelonnt")
  privatelon val numNullCastTwelonelontCountelonr = statsReloncelonivelonr.countelonr("num_null_cast_twelonelont")
  privatelon val numTwelonelontFailSafelontyLelonvelonlCountelonr = statsReloncelonivelonr.countelonr("num_fail_twelonelontypielon_safelonty")
  privatelon val numFavoritelonUselonrUnsafelonCountelonr = statsReloncelonivelonr.countelonr("num_favoritelon_uselonr_unsafelon")
  privatelon val elonngagelonUselonrFiltelonr = nelonw UselonrFiltelonr(gizmoduck)(statsReloncelonivelonr.scopelon("elonngagelon_uselonr"))
  privatelon val twelonelontFiltelonr = nelonw TwelonelontFiltelonr(twelonelontypielon)

  privatelon val numProcelonssFavoritelon = statsReloncelonivelonr.countelonr("num_procelonss_favoritelon")
  privatelon val numNoProcelonssFavoritelon = statsReloncelonivelonr.countelonr("num_no_procelonss_favoritelon")

  privatelon delonf gelontFavoritelonelonvelonntDelontails(
    favoritelonelonvelonnt: Favoritelonelonvelonnt
  ): TwelonelontFavoritelonelonvelonntDelontails = {

    val elonngagelonmelonnt = UselonrTwelonelontelonngagelonmelonnt(
      elonngagelonUselonrId = favoritelonelonvelonnt.uselonrId,
      elonngagelonUselonr = favoritelonelonvelonnt.uselonr,
      action = Action.Favoritelon,
      elonngagelonmelonntTimelonMillis = Somelon(favoritelonelonvelonnt.elonvelonntTimelonMs),
      twelonelontId = favoritelonelonvelonnt.twelonelontId, // thelon twelonelont, or sourcelon twelonelont if targelont twelonelont is a relontwelonelont
      twelonelontDelontails = favoritelonelonvelonnt.twelonelont.map(TwelonelontDelontails) // twelonelont always elonxists
    )
    TwelonelontFavoritelonelonvelonntDelontails(uselonrTwelonelontelonngagelonmelonnt = elonngagelonmelonnt)
  }

  privatelon delonf gelontUnfavoritelonelonvelonntDelontails(
    unfavoritelonelonvelonnt: Unfavoritelonelonvelonnt
  ): TwelonelontFavoritelonelonvelonntDelontails = {
    val elonngagelonmelonnt = UselonrTwelonelontelonngagelonmelonnt(
      elonngagelonUselonrId = unfavoritelonelonvelonnt.uselonrId,
      elonngagelonUselonr = unfavoritelonelonvelonnt.uselonr,
      action = Action.Unfavoritelon,
      elonngagelonmelonntTimelonMillis = Somelon(unfavoritelonelonvelonnt.elonvelonntTimelonMs),
      twelonelontId = unfavoritelonelonvelonnt.twelonelontId, // thelon twelonelont, or sourcelon twelonelont if targelont twelonelont is a relontwelonelont
      twelonelontDelontails = unfavoritelonelonvelonnt.twelonelont.map(TwelonelontDelontails) // twelonelont always elonxists
    )
    TwelonelontFavoritelonelonvelonntDelontails(uselonrTwelonelontelonngagelonmelonnt = elonngagelonmelonnt)
  }

  privatelon delonf shouldProcelonssFavoritelonelonvelonnt(elonvelonnt: TwelonelontFavoritelonelonvelonntDelontails): Futurelon[Boolelonan] = {
    val elonngagelonmelonnt = elonvelonnt.uselonrTwelonelontelonngagelonmelonnt
    val elonngagelonUselonrId = elonngagelonmelonnt.elonngagelonUselonrId
    val twelonelontId = elonngagelonmelonnt.twelonelontId
    val authorIdOpt = elonngagelonmelonnt.twelonelontDelontails.flatMap(_.authorId)

    val isSelonlfFavoritelon = authorIdOpt.contains(elonngagelonUselonrId)
    val isNullCastTwelonelont = elonngagelonmelonnt.twelonelontDelontails.forall(_.isNullCastTwelonelont)
    val iselonngagelonUselonrSafelonFut = elonngagelonUselonrFiltelonr.filtelonrByUselonrId(elonngagelonUselonrId)
    val isTwelonelontPassSafelontyFut = twelonelontFiltelonr.filtelonrForTwelonelontypielonSafelontyLelonvelonl(twelonelontId)

    Futurelon.join(iselonngagelonUselonrSafelonFut, isTwelonelontPassSafelontyFut).map {
      caselon (iselonngagelonUselonrSafelon, isTwelonelontPassSafelonty) =>
        if (isSelonlfFavoritelon) numSelonlfFavoritelonCountelonr.incr()
        if (isNullCastTwelonelont) numNullCastTwelonelontCountelonr.incr()
        if (!iselonngagelonUselonrSafelon) numFavoritelonUselonrUnsafelonCountelonr.incr()
        if (!isTwelonelontPassSafelonty) numTwelonelontFailSafelontyLelonvelonlCountelonr.incr()

        !isSelonlfFavoritelon && !isNullCastTwelonelont && iselonngagelonUselonrSafelon && isTwelonelontPassSafelonty
    }
  }

  privatelon delonf procelonssFavoritelonelonvelonnt(favoritelonelonvelonnt: Favoritelonelonvelonnt): Futurelon[Unit] = {
    val elonvelonntDelontails = gelontFavoritelonelonvelonntDelontails(favoritelonelonvelonnt)
    shouldProcelonssFavoritelonelonvelonnt(elonvelonntDelontails).map {
      caselon truelon =>
        numProcelonssFavoritelon.incr()
        // Convelonrt thelon elonvelonnt for UselonrTwelonelontelonntityGraph
        uselonrTwelonelontelonntityGraphMelonssagelonBuildelonr.procelonsselonvelonnt(elonvelonntDelontails).map { elondgelons =>
          elondgelons.forelonach { elondgelon =>
            kafkaelonvelonntPublishelonr.publish(elondgelon.convelonrtToReloncosHoselonMelonssagelon, uselonrTwelonelontelonntityGraphTopic)
          }
        }
      caselon falselon =>
        numNoProcelonssFavoritelon.incr()
    }
  }

  privatelon delonf procelonssUnFavoritelonelonvelonnt(unFavoritelonelonvelonnt: Unfavoritelonelonvelonnt): Futurelon[Unit] = {
    if (deloncidelonr.isAvailablelon(ReloncosInjelonctorDeloncidelonrConstants.elonnablelonUnfavoritelonelondgelon)) {
      val elonvelonntDelontails = gelontUnfavoritelonelonvelonntDelontails(unFavoritelonelonvelonnt)
      // Convelonrt thelon elonvelonnt for UselonrTwelonelontelonntityGraph
      uselonrTwelonelontelonntityGraphMelonssagelonBuildelonr.procelonsselonvelonnt(elonvelonntDelontails).map { elondgelons =>
        elondgelons.forelonach { elondgelon =>
          kafkaelonvelonntPublishelonr.publish(elondgelon.convelonrtToReloncosHoselonMelonssagelon, uselonrTwelonelontelonntityGraphTopic)
        }
      }
    } elonlselon {
      Futurelon.Unit
    }
  }

  ovelonrridelon delonf procelonsselonvelonnt(elonvelonnt: Timelonlinelonelonvelonnt): Futurelon[Unit] = {
    procelonsselonvelonntDeloncidelonrCountelonr.incr()
    elonvelonnt match {
      caselon Timelonlinelonelonvelonnt.Favoritelon(favoritelonelonvelonnt: Favoritelonelonvelonnt) =>
        numFavoritelonelonvelonntCountelonr.incr()
        procelonssFavoritelonelonvelonnt(favoritelonelonvelonnt)
      caselon Timelonlinelonelonvelonnt.Unfavoritelon(unFavoritelonelonvelonnt: Unfavoritelonelonvelonnt) =>
        numUnFavoritelonelonvelonntCountelonr.incr()
        procelonssUnFavoritelonelonvelonnt(unFavoritelonelonvelonnt)
      caselon _ =>
        numNotFavoritelonelonvelonntCountelonr.incr()
        Futurelon.Unit
    }
  }
}
