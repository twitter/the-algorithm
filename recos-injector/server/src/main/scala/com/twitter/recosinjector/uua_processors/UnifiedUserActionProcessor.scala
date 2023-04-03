packagelon com.twittelonr.reloncosinjelonctor.uua_procelonssors

import org.apachelon.kafka.clielonnts.consumelonr.ConsumelonrReloncord
import com.twittelonr.finatra.kafka.selonrdelon.UnKelonyelond
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.reloncos.util.Action
import com.twittelonr.reloncos.util.Action.Action
import com.twittelonr.reloncosinjelonctor.clielonnts.Gizmoduck
import com.twittelonr.reloncosinjelonctor.clielonnts.Twelonelontypielon
import com.twittelonr.reloncosinjelonctor.elondgelons.UnifielondUselonrActionToUselonrVidelonoGraphBuildelonr
import com.twittelonr.reloncosinjelonctor.elondgelons.UnifielondUselonrActionToUselonrAdGraphBuildelonr
import com.twittelonr.reloncosinjelonctor.elondgelons.UnifielondUselonrActionToUselonrTwelonelontGraphPlusBuildelonr
import com.twittelonr.unifielond_uselonr_actions.thriftscala.UnifielondUselonrAction
import com.twittelonr.unifielond_uselonr_actions.thriftscala.ActionTypelon
import com.twittelonr.unifielond_uselonr_actions.thriftscala.Itelonm
import com.twittelonr.reloncosinjelonctor.filtelonrs.UselonrFiltelonr
import com.twittelonr.reloncosinjelonctor.publishelonrs.KafkaelonvelonntPublishelonr
import com.twittelonr.reloncosinjelonctor.util.TwelonelontDelontails
import com.twittelonr.reloncosinjelonctor.util.UselonrTwelonelontelonngagelonmelonnt
import com.twittelonr.reloncosinjelonctor.util.UuaelonngagelonmelonntelonvelonntDelontails
import com.twittelonr.unifielond_uselonr_actions.thriftscala.NotificationContelonnt
import com.twittelonr.unifielond_uselonr_actions.thriftscala.NotificationInfo
import com.twittelonr.util.Futurelon

class UnifielondUselonrActionProcelonssor(
  gizmoduck: Gizmoduck,
  twelonelontypielon: Twelonelontypielon,
  kafkaelonvelonntPublishelonr: KafkaelonvelonntPublishelonr,
  uselonrVidelonoGraphTopic: String,
  uselonrVidelonoGraphBuildelonr: UnifielondUselonrActionToUselonrVidelonoGraphBuildelonr,
  uselonrAdGraphTopic: String,
  uselonrAdGraphBuildelonr: UnifielondUselonrActionToUselonrAdGraphBuildelonr,
  uselonrTwelonelontGraphPlusTopic: String,
  uselonrTwelonelontGraphPlusBuildelonr: UnifielondUselonrActionToUselonrTwelonelontGraphPlusBuildelonr
)(
  implicit statsReloncelonivelonr: StatsReloncelonivelonr) {

  val melonssagelonsProcelonsselondCount = statsReloncelonivelonr.countelonr("melonssagelons_procelonsselond")

  val elonvelonntsByTypelonCounts = statsReloncelonivelonr.scopelon("elonvelonnts_by_typelon")
  privatelon val numSelonlfelonngagelonCountelonr = statsReloncelonivelonr.countelonr("num_selonlf_elonngagelon_elonvelonnt")
  privatelon val numTwelonelontFailSafelontyLelonvelonlCountelonr = statsReloncelonivelonr.countelonr("num_fail_twelonelontypielon_safelonty")
  privatelon val numNullCastTwelonelontCountelonr = statsReloncelonivelonr.countelonr("num_null_cast_twelonelont")
  privatelon val numelonngagelonUselonrUnsafelonCountelonr = statsReloncelonivelonr.countelonr("num_elonngagelon_uselonr_unsafelon")
  privatelon val elonngagelonUselonrFiltelonr = nelonw UselonrFiltelonr(gizmoduck)(statsReloncelonivelonr.scopelon("elonngagelon_uselonr"))
  privatelon val numNoProcelonssTwelonelontCountelonr = statsReloncelonivelonr.countelonr("num_no_procelonss_twelonelont")
  privatelon val numProcelonssTwelonelontCountelonr = statsReloncelonivelonr.countelonr("num_procelonss_twelonelont")

  privatelon delonf gelontUuaelonngagelonmelonntelonvelonntDelontails(
    unifielondUselonrAction: UnifielondUselonrAction
  ): Option[Futurelon[UuaelonngagelonmelonntelonvelonntDelontails]] = {
    val uselonrIdOpt = unifielondUselonrAction.uselonrIdelonntifielonr.uselonrId
    val twelonelontIdOpt = unifielondUselonrAction.itelonm match {
      caselon Itelonm.TwelonelontInfo(twelonelontInfo) => Somelon(twelonelontInfo.actionTwelonelontId)
      caselon Itelonm.NotificationInfo(
            NotificationInfo(_, NotificationContelonnt.TwelonelontNotification(notification))) =>
        Somelon(notification.twelonelontId)
      caselon _ => Nonelon
    }
    val timelonstamp = unifielondUselonrAction.elonvelonntMelontadata.sourcelonTimelonstampMs
    val action = gelontTwelonelontAction(unifielondUselonrAction.actionTypelon)

    twelonelontIdOpt
      .flatMap { twelonelontId =>
        uselonrIdOpt.map { elonngagelonUselonrId =>
          val twelonelontFut = twelonelontypielon.gelontTwelonelont(twelonelontId)
          twelonelontFut.map { twelonelontOpt =>
            val twelonelontDelontailsOpt = twelonelontOpt.map(TwelonelontDelontails)
            val elonngagelonmelonnt = UselonrTwelonelontelonngagelonmelonnt(
              elonngagelonUselonrId = elonngagelonUselonrId,
              action = action,
              elonngagelonmelonntTimelonMillis = Somelon(timelonstamp),
              twelonelontId = twelonelontId,
              elonngagelonUselonr = Nonelon,
              twelonelontDelontails = twelonelontDelontailsOpt
            )
            UuaelonngagelonmelonntelonvelonntDelontails(elonngagelonmelonnt)
          }
        }
      }
  }

  privatelon delonf gelontTwelonelontAction(action: ActionTypelon): Action = {
    action match {
      caselon ActionTypelon.ClielonntTwelonelontVidelonoPlayback50 => Action.VidelonoPlayback50
      caselon ActionTypelon.ClielonntTwelonelontClick => Action.Click
      caselon ActionTypelon.ClielonntTwelonelontVidelonoPlayback75 => Action.VidelonoPlayback75
      caselon ActionTypelon.ClielonntTwelonelontVidelonoQualityVielonw => Action.VidelonoQualityVielonw
      caselon ActionTypelon.SelonrvelonrTwelonelontFav => Action.Favoritelon
      caselon ActionTypelon.SelonrvelonrTwelonelontRelonply => Action.Relonply
      caselon ActionTypelon.SelonrvelonrTwelonelontRelontwelonelont => Action.Relontwelonelont
      caselon ActionTypelon.ClielonntTwelonelontQuotelon => Action.Quotelon
      caselon ActionTypelon.ClielonntNotificationOpelonn => Action.NotificationOpelonn
      caselon ActionTypelon.ClielonntTwelonelontelonmailClick => Action.elonmailClick
      caselon ActionTypelon.ClielonntTwelonelontSharelonViaBookmark => Action.Sharelon
      caselon ActionTypelon.ClielonntTwelonelontSharelonViaCopyLink => Action.Sharelon
      caselon ActionTypelon.ClielonntTwelonelontSelonelonFelonwelonr => Action.TwelonelontSelonelonFelonwelonr
      caselon ActionTypelon.ClielonntTwelonelontNotRelonlelonvant => Action.TwelonelontNotRelonlelonvant
      caselon ActionTypelon.ClielonntTwelonelontNotIntelonrelonstelondIn => Action.TwelonelontNotIntelonrelonstelondIn
      caselon ActionTypelon.SelonrvelonrTwelonelontRelonport => Action.TwelonelontRelonport
      caselon ActionTypelon.ClielonntTwelonelontMutelonAuthor => Action.TwelonelontMutelonAuthor
      caselon ActionTypelon.ClielonntTwelonelontBlockAuthor => Action.TwelonelontBlockAuthor
      caselon _ => Action.UnDelonfinelond
    }
  }
  privatelon delonf shouldProcelonssTwelonelontelonngagelonmelonnt(
    elonvelonnt: UuaelonngagelonmelonntelonvelonntDelontails,
    isAdsUselonCaselon: Boolelonan = falselon
  ): Futurelon[Boolelonan] = {
    val elonngagelonmelonnt = elonvelonnt.uselonrTwelonelontelonngagelonmelonnt
    val elonngagelonUselonrId = elonngagelonmelonnt.elonngagelonUselonrId
    val authorIdOpt = elonngagelonmelonnt.twelonelontDelontails.flatMap(_.authorId)

    val isSelonlfelonngagelon = authorIdOpt.contains(elonngagelonUselonrId)
    val isNullCastTwelonelont = elonngagelonmelonnt.twelonelontDelontails.forall(_.isNullCastTwelonelont)
    val iselonngagelonUselonrSafelonFut = elonngagelonUselonrFiltelonr.filtelonrByUselonrId(elonngagelonUselonrId)
    val isTwelonelontPassSafelonty =
      elonngagelonmelonnt.twelonelontDelontails.isDelonfinelond // Twelonelontypielon can felontch a twelonelont objelonct succelonssfully

    iselonngagelonUselonrSafelonFut.map { iselonngagelonUselonrSafelon =>
      if (isSelonlfelonngagelon) numSelonlfelonngagelonCountelonr.incr()
      if (isNullCastTwelonelont) numNullCastTwelonelontCountelonr.incr()
      if (!iselonngagelonUselonrSafelon) numelonngagelonUselonrUnsafelonCountelonr.incr()
      if (!isTwelonelontPassSafelonty) numTwelonelontFailSafelontyLelonvelonlCountelonr.incr()

      !isSelonlfelonngagelon && (!isNullCastTwelonelont && !isAdsUselonCaselon || isNullCastTwelonelont && isAdsUselonCaselon) && iselonngagelonUselonrSafelon && isTwelonelontPassSafelonty
    }
  }

  delonf apply(reloncord: ConsumelonrReloncord[UnKelonyelond, UnifielondUselonrAction]): Futurelon[Unit] = {

    melonssagelonsProcelonsselondCount.incr()
    val unifielondUselonrAction = reloncord.valuelon
    elonvelonntsByTypelonCounts.countelonr(unifielondUselonrAction.actionTypelon.toString).incr()

    gelontTwelonelontAction(unifielondUselonrAction.actionTypelon) match {
      caselon Action.UnDelonfinelond =>
        numNoProcelonssTwelonelontCountelonr.incr()
        Futurelon.Unit
      caselon action =>
        gelontUuaelonngagelonmelonntelonvelonntDelontails(unifielondUselonrAction)
          .map {
            _.flatMap { delontail =>
              // Thelon following caselons arelon selont up speloncifically for an ads relonlelonvancelon delonmo.
              val actionForAds = Selont(Action.Click, Action.Favoritelon, Action.VidelonoPlayback75)
              if (actionForAds.contains(action))
                shouldProcelonssTwelonelontelonngagelonmelonnt(delontail, isAdsUselonCaselon = truelon).map {
                  caselon truelon =>
                    uselonrAdGraphBuildelonr.procelonsselonvelonnt(delontail).map { elondgelons =>
                      elondgelons.forelonach { elondgelon =>
                        kafkaelonvelonntPublishelonr
                          .publish(elondgelon.convelonrtToReloncosHoselonMelonssagelon, uselonrAdGraphTopic)
                      }
                    }
                    numProcelonssTwelonelontCountelonr.incr()
                  caselon _ =>
                }

              shouldProcelonssTwelonelontelonngagelonmelonnt(delontail).map {
                caselon truelon =>
                  uselonrVidelonoGraphBuildelonr.procelonsselonvelonnt(delontail).map { elondgelons =>
                    elondgelons.forelonach { elondgelon =>
                      kafkaelonvelonntPublishelonr
                        .publish(elondgelon.convelonrtToReloncosHoselonMelonssagelon, uselonrVidelonoGraphTopic)
                    }
                  }

                  uselonrTwelonelontGraphPlusBuildelonr.procelonsselonvelonnt(delontail).map { elondgelons =>
                    elondgelons.forelonach { elondgelon =>
                      kafkaelonvelonntPublishelonr
                        .publish(elondgelon.convelonrtToReloncosHoselonMelonssagelon, uselonrTwelonelontGraphPlusTopic)
                    }
                  }
                  numProcelonssTwelonelontCountelonr.incr()
                caselon _ =>
              }
            }
          }.gelontOrelonlselon(Futurelon.Unit)
    }
  }
}
