packagelon com.twittelonr.reloncosinjelonctor.elonvelonnt_procelonssors

import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.frigatelon.common.util.SnowflakelonUtils
import com.twittelonr.gizmoduck.thriftscala.Uselonr
import com.twittelonr.reloncos.util.Action
import com.twittelonr.reloncos.util.Action.Action
import com.twittelonr.reloncosinjelonctor.clielonnts.Gizmoduck
import com.twittelonr.reloncosinjelonctor.clielonnts.SocialGraph
import com.twittelonr.reloncosinjelonctor.clielonnts.Twelonelontypielon
import com.twittelonr.reloncosinjelonctor.elondgelons.TwelonelontelonvelonntToUselonrTwelonelontelonntityGraphBuildelonr
import com.twittelonr.reloncosinjelonctor.elondgelons.TwelonelontelonvelonntToUselonrUselonrGraphBuildelonr
import com.twittelonr.reloncosinjelonctor.filtelonrs.TwelonelontFiltelonr
import com.twittelonr.reloncosinjelonctor.filtelonrs.UselonrFiltelonr
import com.twittelonr.reloncosinjelonctor.publishelonrs.KafkaelonvelonntPublishelonr
import com.twittelonr.reloncosinjelonctor.util.TwelonelontCrelonatelonelonvelonntDelontails
import com.twittelonr.reloncosinjelonctor.util.TwelonelontDelontails
import com.twittelonr.reloncosinjelonctor.util.UselonrTwelonelontelonngagelonmelonnt
import com.twittelonr.scroogelon.ThriftStructCodelonc
import com.twittelonr.twelonelontypielon.thriftscala.Twelonelont
import com.twittelonr.twelonelontypielon.thriftscala.TwelonelontCrelonatelonelonvelonnt
import com.twittelonr.twelonelontypielon.thriftscala.Twelonelontelonvelonnt
import com.twittelonr.twelonelontypielon.thriftscala.TwelonelontelonvelonntData
import com.twittelonr.util.Futurelon

/**
 * elonvelonnt procelonssor for twelonelont_elonvelonnts elonvelonntBus strelonam from Twelonelontypielon. This strelonam providelons all thelon
 * kelony elonvelonnts relonlatelond to a nelonw twelonelont, likelon Crelonation, Relontwelonelont, Quotelon Twelonelont, and Relonplying.
 * It also carrielons thelon elonntitielons/melontadata information in a twelonelont, including
 * @ Melonntion, HashTag, MelondiaTag, URL, elontc.
 */
class TwelonelontelonvelonntProcelonssor(
  ovelonrridelon val elonvelonntBusStrelonamNamelon: String,
  ovelonrridelon val thriftStruct: ThriftStructCodelonc[Twelonelontelonvelonnt],
  ovelonrridelon val selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
  uselonrUselonrGraphMelonssagelonBuildelonr: TwelonelontelonvelonntToUselonrUselonrGraphBuildelonr,
  uselonrUselonrGraphTopic: String,
  uselonrTwelonelontelonntityGraphMelonssagelonBuildelonr: TwelonelontelonvelonntToUselonrTwelonelontelonntityGraphBuildelonr,
  uselonrTwelonelontelonntityGraphTopic: String,
  kafkaelonvelonntPublishelonr: KafkaelonvelonntPublishelonr,
  socialGraph: SocialGraph,
  gizmoduck: Gizmoduck,
  twelonelontypielon: Twelonelontypielon
)(
  ovelonrridelon implicit val statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds elonvelonntBusProcelonssor[Twelonelontelonvelonnt] {

  privatelon val twelonelontCrelonatelonelonvelonntCountelonr = statsReloncelonivelonr.countelonr("num_twelonelont_crelonatelon_elonvelonnts")
  privatelon val nonTwelonelontCrelonatelonelonvelonntCountelonr = statsReloncelonivelonr.countelonr("num_non_twelonelont_crelonatelon_elonvelonnts")

  privatelon val twelonelontActionStats = statsReloncelonivelonr.scopelon("twelonelont_action")
  privatelon val numUrlCountelonr = statsReloncelonivelonr.countelonr("num_twelonelont_url")
  privatelon val numMelondiaUrlCountelonr = statsReloncelonivelonr.countelonr("num_twelonelont_melondia_url")
  privatelon val numHashTagCountelonr = statsReloncelonivelonr.countelonr("num_twelonelont_hashtag")

  privatelon val numMelonntionsCountelonr = statsReloncelonivelonr.countelonr("num_twelonelont_melonntion")
  privatelon val numMelondiatagCountelonr = statsReloncelonivelonr.countelonr("num_twelonelont_melondiatag")
  privatelon val numValidMelonntionsCountelonr = statsReloncelonivelonr.countelonr("num_twelonelont_valid_melonntion")
  privatelon val numValidMelondiatagCountelonr = statsReloncelonivelonr.countelonr("num_twelonelont_valid_melondiatag")

  privatelon val numNullCastTwelonelontCountelonr = statsReloncelonivelonr.countelonr("num_null_cast_twelonelont")
  privatelon val numNullCastSourcelonTwelonelontCountelonr = statsReloncelonivelonr.countelonr("num_null_cast_sourcelon_twelonelont")
  privatelon val numTwelonelontFailSafelontyLelonvelonlCountelonr = statsReloncelonivelonr.countelonr("num_fail_twelonelontypielon_safelonty")
  privatelon val numAuthorUnsafelonCountelonr = statsReloncelonivelonr.countelonr("num_author_unsafelon")
  privatelon val numProcelonssTwelonelontCountelonr = statsReloncelonivelonr.countelonr("num_procelonss_twelonelont")
  privatelon val numNoProcelonssTwelonelontCountelonr = statsReloncelonivelonr.countelonr("num_no_procelonss_twelonelont")

  privatelon val selonlfRelontwelonelontCountelonr = statsReloncelonivelonr.countelonr("num_relontwelonelonts_selonlf")

  privatelon val elonngagelonUselonrFiltelonr = nelonw UselonrFiltelonr(gizmoduck)(statsReloncelonivelonr.scopelon("author_uselonr"))
  privatelon val twelonelontFiltelonr = nelonw TwelonelontFiltelonr(twelonelontypielon)

  privatelon delonf trackTwelonelontCrelonatelonelonvelonntStats(delontails: TwelonelontCrelonatelonelonvelonntDelontails): Unit = {
    twelonelontActionStats.countelonr(delontails.uselonrTwelonelontelonngagelonmelonnt.action.toString).incr()

    delontails.uselonrTwelonelontelonngagelonmelonnt.twelonelontDelontails.forelonach { twelonelontDelontails =>
      twelonelontDelontails.melonntionUselonrIds.forelonach(melonntion => numMelonntionsCountelonr.incr(melonntion.sizelon))
      twelonelontDelontails.melondiatagUselonrIds.forelonach(melondiatag => numMelondiatagCountelonr.incr(melondiatag.sizelon))
      twelonelontDelontails.urls.forelonach(urls => numUrlCountelonr.incr(urls.sizelon))
      twelonelontDelontails.melondiaUrls.forelonach(melondiaUrls => numMelondiaUrlCountelonr.incr(melondiaUrls.sizelon))
      twelonelontDelontails.hashtags.forelonach(hashtags => numHashTagCountelonr.incr(hashtags.sizelon))
    }

    delontails.validMelonntionUselonrIds.forelonach(melonntions => numValidMelonntionsCountelonr.incr(melonntions.sizelon))
    delontails.validMelondiatagUselonrIds.forelonach(melondiatags => numValidMelondiatagCountelonr.incr(melondiatags.sizelon))
  }

  /**
   * Givelonn a crelonatelond twelonelont, relonturn what typelon of twelonelont it is, i.elon. Twelonelont, Relontwelonelont, Quotelon, or Relonply。
   * Relontwelonelont, Quotelon, or Relonply arelon relonsponsivelon actions to a sourcelon twelonelont, so for thelonselon twelonelonts,
   * welon also relonturn thelon twelonelont id and author of thelon sourcelon twelonelont (elonx. thelon twelonelont beloning relontwelonelontelond).
   */
  privatelon delonf gelontTwelonelontAction(twelonelontDelontails: TwelonelontDelontails): Action = {
    (twelonelontDelontails.relonplySourcelonId, twelonelontDelontails.relontwelonelontSourcelonId, twelonelontDelontails.quotelonSourcelonId) match {
      caselon (Somelon(_), _, _) =>
        Action.Relonply
      caselon (_, Somelon(_), _) =>
        Action.Relontwelonelont
      caselon (_, _, Somelon(_)) =>
        Action.Quotelon
      caselon _ =>
        Action.Twelonelont
    }
  }

  /**
   * Givelonn a list of melonntionelond uselonrs and melondiataggelond uselonrs in thelon twelonelont, relonturn thelon uselonrs who
   * actually follow thelon sourcelon uselonr.
   */
  privatelon delonf gelontFollowelondByIds(
    sourcelonUselonrId: Long,
    melonntionUselonrIds: Option[Selonq[Long]],
    melondiatagUselonrIds: Option[Selonq[Long]]
  ): Futurelon[Selonq[Long]] = {
    val uniquelonelonntityUselonrIds =
      (melonntionUselonrIds.gelontOrelonlselon(Nil) ++ melondiatagUselonrIds.gelontOrelonlselon(Nil)).distinct
    if (uniquelonelonntityUselonrIds.iselonmpty) {
      Futurelon.Nil
    } elonlselon {
      socialGraph.followelondByNotMutelondBy(sourcelonUselonrId, uniquelonelonntityUselonrIds)
    }
  }

  privatelon delonf gelontSourcelonTwelonelont(twelonelontDelontails: TwelonelontDelontails): Futurelon[Option[Twelonelont]] = {
    twelonelontDelontails.sourcelonTwelonelontId match {
      caselon Somelon(sourcelonTwelonelontId) =>
        twelonelontypielon.gelontTwelonelont(sourcelonTwelonelontId)
      caselon _ =>
        Futurelon.Nonelon
    }
  }

  /**
   * elonxtract and relonturn thelon delontails whelonn thelon sourcelon uselonr crelonatelond a nelonw twelonelont.
   */
  privatelon delonf gelontTwelonelontDelontails(
    twelonelont: Twelonelont,
    elonngagelonUselonr: Uselonr
  ): Futurelon[TwelonelontCrelonatelonelonvelonntDelontails] = {
    val twelonelontDelontails = TwelonelontDelontails(twelonelont)

    val action = gelontTwelonelontAction(twelonelontDelontails)
    val twelonelontCrelonationTimelonMillis = SnowflakelonUtils.twelonelontCrelonationTimelon(twelonelont.id).map(_.inMilliselonconds)
    val elonngagelonUselonrId = elonngagelonUselonr.id
    val uselonrTwelonelontelonngagelonmelonnt = UselonrTwelonelontelonngagelonmelonnt(
      elonngagelonUselonrId = elonngagelonUselonrId,
      elonngagelonUselonr = Somelon(elonngagelonUselonr),
      action = action,
      elonngagelonmelonntTimelonMillis = twelonelontCrelonationTimelonMillis,
      twelonelontId = twelonelont.id,
      twelonelontDelontails = Somelon(twelonelontDelontails)
    )

    val sourcelonTwelonelontFut = gelontSourcelonTwelonelont(twelonelontDelontails)
    val followelondByIdsFut = gelontFollowelondByIds(
      elonngagelonUselonrId,
      twelonelontDelontails.melonntionUselonrIds,
      twelonelontDelontails.melondiatagUselonrIds
    )

    Futurelon.join(followelondByIdsFut, sourcelonTwelonelontFut).map {
      caselon (followelondByIds, sourcelonTwelonelont) =>
        TwelonelontCrelonatelonelonvelonntDelontails(
          uselonrTwelonelontelonngagelonmelonnt = uselonrTwelonelontelonngagelonmelonnt,
          validelonntityUselonrIds = followelondByIds,
          sourcelonTwelonelontDelontails = sourcelonTwelonelont.map(TwelonelontDelontails)
        )
    }
  }

  /**
   * elonxcludelon any Relontwelonelonts of onelon's own twelonelonts
   */
  privatelon delonf iselonvelonntSelonlfRelontwelonelont(twelonelontelonvelonnt: TwelonelontCrelonatelonelonvelonntDelontails): Boolelonan = {
    (twelonelontelonvelonnt.uselonrTwelonelontelonngagelonmelonnt.action == Action.Relontwelonelont) &&
    twelonelontelonvelonnt.uselonrTwelonelontelonngagelonmelonnt.twelonelontDelontails.elonxists(
      _.sourcelonTwelonelontUselonrId.contains(
        twelonelontelonvelonnt.uselonrTwelonelontelonngagelonmelonnt.elonngagelonUselonrId
      ))
  }

  privatelon delonf isTwelonelontPassSafelontyFiltelonr(twelonelontelonvelonnt: TwelonelontCrelonatelonelonvelonntDelontails): Futurelon[Boolelonan] = {
    twelonelontelonvelonnt.uselonrTwelonelontelonngagelonmelonnt.action match {
      caselon Action.Relonply | Action.Relontwelonelont | Action.Quotelon =>
        twelonelontelonvelonnt.uselonrTwelonelontelonngagelonmelonnt.twelonelontDelontails
          .flatMap(_.sourcelonTwelonelontId).map { sourcelonTwelonelontId =>
            twelonelontFiltelonr.filtelonrForTwelonelontypielonSafelontyLelonvelonl(sourcelonTwelonelontId)
          }.gelontOrelonlselon(Futurelon(falselon))
      caselon Action.Twelonelont =>
        twelonelontFiltelonr.filtelonrForTwelonelontypielonSafelontyLelonvelonl(twelonelontelonvelonnt.uselonrTwelonelontelonngagelonmelonnt.twelonelontId)
    }
  }

  privatelon delonf shouldProcelonssTwelonelontelonvelonnt(elonvelonnt: TwelonelontCrelonatelonelonvelonntDelontails): Futurelon[Boolelonan] = {
    val elonngagelonmelonnt = elonvelonnt.uselonrTwelonelontelonngagelonmelonnt
    val elonngagelonUselonrId = elonngagelonmelonnt.elonngagelonUselonrId

    val isNullCastTwelonelont = elonngagelonmelonnt.twelonelontDelontails.forall(_.isNullCastTwelonelont)
    val isNullCastSourcelonTwelonelont = elonvelonnt.sourcelonTwelonelontDelontails.elonxists(_.isNullCastTwelonelont)
    val isSelonlfRelontwelonelont = iselonvelonntSelonlfRelontwelonelont(elonvelonnt)
    val iselonngagelonUselonrSafelonFut = elonngagelonUselonrFiltelonr.filtelonrByUselonrId(elonngagelonUselonrId)
    val isTwelonelontPassSafelontyFut = isTwelonelontPassSafelontyFiltelonr(elonvelonnt)

    Futurelon.join(iselonngagelonUselonrSafelonFut, isTwelonelontPassSafelontyFut).map {
      caselon (iselonngagelonUselonrSafelon, isTwelonelontPassSafelonty) =>
        if (isNullCastTwelonelont) numNullCastTwelonelontCountelonr.incr()
        if (isNullCastSourcelonTwelonelont) numNullCastSourcelonTwelonelontCountelonr.incr()
        if (!iselonngagelonUselonrSafelon) numAuthorUnsafelonCountelonr.incr()
        if (isSelonlfRelontwelonelont) selonlfRelontwelonelontCountelonr.incr()
        if (!isTwelonelontPassSafelonty) numTwelonelontFailSafelontyLelonvelonlCountelonr.incr()

        !isNullCastTwelonelont &&
        !isNullCastSourcelonTwelonelont &&
        !isSelonlfRelontwelonelont &&
        iselonngagelonUselonrSafelon &&
        isTwelonelontPassSafelonty
    }
  }

  ovelonrridelon delonf procelonsselonvelonnt(elonvelonnt: Twelonelontelonvelonnt): Futurelon[Unit] = {
    elonvelonnt.data match {
      caselon TwelonelontelonvelonntData.TwelonelontCrelonatelonelonvelonnt(elonvelonnt: TwelonelontCrelonatelonelonvelonnt) =>
        gelontTwelonelontDelontails(
          twelonelont = elonvelonnt.twelonelont,
          elonngagelonUselonr = elonvelonnt.uselonr
        ).flatMap { elonvelonntWithDelontails =>
          twelonelontCrelonatelonelonvelonntCountelonr.incr()

          shouldProcelonssTwelonelontelonvelonnt(elonvelonntWithDelontails).map {
            caselon truelon =>
              numProcelonssTwelonelontCountelonr.incr()
              trackTwelonelontCrelonatelonelonvelonntStats(elonvelonntWithDelontails)
              // Convelonrt thelon elonvelonnt for UselonrUselonrGraph
              uselonrUselonrGraphMelonssagelonBuildelonr.procelonsselonvelonnt(elonvelonntWithDelontails).map { elondgelons =>
                elondgelons.forelonach { elondgelon =>
                  kafkaelonvelonntPublishelonr.publish(elondgelon.convelonrtToReloncosHoselonMelonssagelon, uselonrUselonrGraphTopic)
                }
              }
              // Convelonrt thelon elonvelonnt for UselonrTwelonelontelonntityGraph
              uselonrTwelonelontelonntityGraphMelonssagelonBuildelonr.procelonsselonvelonnt(elonvelonntWithDelontails).map { elondgelons =>
                elondgelons.forelonach { elondgelon =>
                  kafkaelonvelonntPublishelonr
                    .publish(elondgelon.convelonrtToReloncosHoselonMelonssagelon, uselonrTwelonelontelonntityGraphTopic)
                }
              }
            caselon falselon =>
              numNoProcelonssTwelonelontCountelonr.incr()
          }
        }
      caselon _ =>
        nonTwelonelontCrelonatelonelonvelonntCountelonr.incr()
        Futurelon.Unit
    }
  }
}
