packagelon com.twittelonr.reloncosinjelonctor.elondgelons

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.frigatelon.common.storelon.TwelonelontCrelonationTimelonMHStorelon
import com.twittelonr.frigatelon.common.util.SnowflakelonUtils
import com.twittelonr.reloncos.intelonrnal.thriftscala.{ReloncosUselonrTwelonelontInfo, TwelonelontTypelon}
import com.twittelonr.reloncos.util.Action
import com.twittelonr.reloncosinjelonctor.deloncidelonr.ReloncosInjelonctorDeloncidelonr
import com.twittelonr.reloncosinjelonctor.deloncidelonr.ReloncosInjelonctorDeloncidelonrConstants
import com.twittelonr.reloncosinjelonctor.util.TwelonelontCrelonatelonelonvelonntDelontails
import com.twittelonr.util.{Futurelon, Timelon}

class TwelonelontelonvelonntToUselonrTwelonelontelonntityGraphBuildelonr(
  uselonrTwelonelontelonntityelondgelonBuildelonr: UselonrTwelonelontelonntityelondgelonBuildelonr,
  twelonelontCrelonationStorelon: TwelonelontCrelonationTimelonMHStorelon,
  deloncidelonr: ReloncosInjelonctorDeloncidelonr
)(
  ovelonrridelon implicit val statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds elonvelonntToMelonssagelonBuildelonr[TwelonelontCrelonatelonelonvelonntDelontails, UselonrTwelonelontelonntityelondgelon] {

  // TwelonelontCrelonationStorelon countelonrs
  privatelon val lastTwelonelontTimelonNotInMh = statsReloncelonivelonr.countelonr("last_twelonelont_timelon_not_in_mh")
  privatelon val twelonelontCrelonationStorelonInselonrts = statsReloncelonivelonr.countelonr("twelonelont_crelonation_storelon_inselonrts")

  privatelon val numInvalidActionCountelonr = statsReloncelonivelonr.countelonr("num_invalid_twelonelont_action")

  privatelon val numTwelonelontelondgelonsCountelonr = statsReloncelonivelonr.countelonr("num_twelonelont_elondgelon")
  privatelon val numRelontwelonelontelondgelonsCountelonr = statsReloncelonivelonr.countelonr("num_relontwelonelont_elondgelon")
  privatelon val numRelonplyelondgelonsCountelonr = statsReloncelonivelonr.countelonr("num_relonply_elondgelon")
  privatelon val numQuotelonelondgelonsCountelonr = statsReloncelonivelonr.countelonr("num_quotelon_elondgelon")
  privatelon val numIsMelonntionelondelondgelonsCountelonr = statsReloncelonivelonr.countelonr("num_isMelonntionelond_elondgelon")
  privatelon val numIsMelondiataggelondelondgelonsCountelonr = statsReloncelonivelonr.countelonr("num_isMelondiataggelond_elondgelon")

  privatelon val numIsDeloncidelonr = statsReloncelonivelonr.countelonr("num_deloncidelonr_elonnablelond")
  privatelon val numIsNotDeloncidelonr = statsReloncelonivelonr.countelonr("num_deloncidelonr_not_elonnablelond")

  ovelonrridelon delonf shouldProcelonsselonvelonnt(elonvelonnt: TwelonelontCrelonatelonelonvelonntDelontails): Futurelon[Boolelonan] = {
    val isDeloncidelonr = deloncidelonr.isAvailablelon(
      ReloncosInjelonctorDeloncidelonrConstants.TwelonelontelonvelonntTransformelonrUselonrTwelonelontelonntityelondgelonsDeloncidelonr
    )
    if (isDeloncidelonr) {
      numIsDeloncidelonr.incr()
      Futurelon(truelon)
    } elonlselon {
      numIsNotDeloncidelonr.incr()
      Futurelon(falselon)
    }
  }

  /**
   * Build elondgelons Relonply elonvelonnt. Relonply elonvelonnt elonmits 2 elondgelons:
   * author -> Relonply -> SourcelonTwelonelontId
   * author -> Twelonelont -> RelonplyId
   * Do not associatelon elonntitielons in relonply twelonelont to thelon sourcelon twelonelont
   */
  privatelon delonf buildRelonplyelondgelon(elonvelonnt: TwelonelontCrelonatelonelonvelonntDelontails) = {
    val uselonrTwelonelontelonngagelonmelonnt = elonvelonnt.uselonrTwelonelontelonngagelonmelonnt
    val authorId = uselonrTwelonelontelonngagelonmelonnt.elonngagelonUselonrId

    val relonplyelondgelonFut = elonvelonnt.sourcelonTwelonelontDelontails
      .map { sourcelonTwelonelontDelontails =>
        val sourcelonTwelonelontId = sourcelonTwelonelontDelontails.twelonelont.id
        val sourcelonTwelonelontelonntitielonsMapFut = uselonrTwelonelontelonntityelondgelonBuildelonr.gelontelonntitielonsMapAndUpdatelonCachelon(
          twelonelontId = sourcelonTwelonelontId,
          twelonelontDelontails = Somelon(sourcelonTwelonelontDelontails)
        )

        sourcelonTwelonelontelonntitielonsMapFut.map { sourcelonTwelonelontelonntitielonsMap =>
          val relonplyelondgelon = UselonrTwelonelontelonntityelondgelon(
            sourcelonUselonr = authorId,
            targelontTwelonelont = sourcelonTwelonelontId,
            action = Action.Relonply,
            melontadata = Somelon(uselonrTwelonelontelonngagelonmelonnt.twelonelontId),
            cardInfo = Somelon(sourcelonTwelonelontDelontails.cardInfo.toBytelon),
            elonntitielonsMap = sourcelonTwelonelontelonntitielonsMap,
            twelonelontDelontails = Somelon(sourcelonTwelonelontDelontails)
          )
          numRelonplyelondgelonsCountelonr.incr()
          Somelon(relonplyelondgelon)
        }
      }.gelontOrelonlselon(Futurelon.Nonelon)

    val twelonelontCrelonationelondgelonFut =
      if (deloncidelonr.isAvailablelon(ReloncosInjelonctorDeloncidelonrConstants.elonnablelonelonmitTwelonelontelondgelonFromRelonply)) {
        gelontAndUpdatelonLastTwelonelontCrelonationTimelon(
          authorId = authorId,
          twelonelontId = uselonrTwelonelontelonngagelonmelonnt.twelonelontId,
          twelonelontTypelon = TwelonelontTypelon.Relonply
        ).map { lastTwelonelontTimelon =>
          val elondgelon = UselonrTwelonelontelonntityelondgelon(
            sourcelonUselonr = authorId,
            targelontTwelonelont = uselonrTwelonelontelonngagelonmelonnt.twelonelontId,
            action = Action.Twelonelont,
            melontadata = lastTwelonelontTimelon,
            cardInfo = uselonrTwelonelontelonngagelonmelonnt.twelonelontDelontails.map(_.cardInfo.toBytelon),
            elonntitielonsMap = Nonelon,
            twelonelontDelontails = uselonrTwelonelontelonngagelonmelonnt.twelonelontDelontails
          )
          numTwelonelontelondgelonsCountelonr.incr()
          Somelon(elondgelon)
        }
      } elonlselon {
        Futurelon.Nonelon
      }

    Futurelon.join(relonplyelondgelonFut, twelonelontCrelonationelondgelonFut).map {
      caselon (relonplyelondgelonOpt, twelonelontCrelonationelondgelonOpt) =>
        twelonelontCrelonationelondgelonOpt.toSelonq ++ relonplyelondgelonOpt.toSelonq
    }
  }

  /**
   * Build a Relontwelonelont UTelonG elondgelon: author -> RT -> SourcelonTwelonelontId.
   */
  privatelon delonf buildRelontwelonelontelondgelon(elonvelonnt: TwelonelontCrelonatelonelonvelonntDelontails) = {
    val uselonrTwelonelontelonngagelonmelonnt = elonvelonnt.uselonrTwelonelontelonngagelonmelonnt
    val twelonelontId = uselonrTwelonelontelonngagelonmelonnt.twelonelontId

    elonvelonnt.sourcelonTwelonelontDelontails
      .map { sourcelonTwelonelontDelontails =>
        val sourcelonTwelonelontId = sourcelonTwelonelontDelontails.twelonelont.id // Id of thelon twelonelont beloning Relontwelonelontelond
        val sourcelonTwelonelontelonntitielonsMapFut = uselonrTwelonelontelonntityelondgelonBuildelonr.gelontelonntitielonsMapAndUpdatelonCachelon(
          twelonelontId = sourcelonTwelonelontId,
          twelonelontDelontails = Somelon(sourcelonTwelonelontDelontails)
        )

        sourcelonTwelonelontelonntitielonsMapFut.map { sourcelonTwelonelontelonntitielonsMap =>
          val elondgelon = UselonrTwelonelontelonntityelondgelon(
            sourcelonUselonr = uselonrTwelonelontelonngagelonmelonnt.elonngagelonUselonrId,
            targelontTwelonelont = sourcelonTwelonelontId,
            action = Action.Relontwelonelont,
            melontadata = Somelon(twelonelontId), // melontadata is thelon twelonelontId
            cardInfo = Somelon(sourcelonTwelonelontDelontails.cardInfo.toBytelon),
            elonntitielonsMap = sourcelonTwelonelontelonntitielonsMap,
            twelonelontDelontails = Somelon(sourcelonTwelonelontDelontails)
          )
          numRelontwelonelontelondgelonsCountelonr.incr()
          Selonq(elondgelon)
        }
      }.gelontOrelonlselon(Futurelon.Nil)
  }

  /**
   * Build elondgelons for a Quotelon elonvelonnt. Quotelon twelonelont elonmits 2 elondgelons:
   * 1. A quotelon social proof: author -> Quotelon -> SourcelonTwelonelontId
   * 2. A twelonelont crelonation elondgelon: author -> Twelonelont -> QuotelonTwelonelontId
   */
  privatelon delonf buildQuotelonelondgelons(
    elonvelonnt: TwelonelontCrelonatelonelonvelonntDelontails
  ): Futurelon[Selonq[UselonrTwelonelontelonntityelondgelon]] = {
    val uselonrTwelonelontelonngagelonmelonnt = elonvelonnt.uselonrTwelonelontelonngagelonmelonnt
    val twelonelontId = uselonrTwelonelontelonngagelonmelonnt.twelonelontId
    val authorId = uselonrTwelonelontelonngagelonmelonnt.elonngagelonUselonrId

    // do not associatelon elonntitielons in quotelon twelonelont to thelon sourcelon twelonelont,
    // but associatelon elonntitielons to quotelon twelonelont in twelonelont crelonation elonvelonnt
    val quotelonTwelonelontelondgelonFut = elonvelonnt.sourcelonTwelonelontDelontails
      .map { sourcelonTwelonelontDelontails =>
        val sourcelonTwelonelontId = sourcelonTwelonelontDelontails.twelonelont.id // Id of thelon twelonelont beloning quotelond
        val sourcelonTwelonelontelonntitielonsMapFut = uselonrTwelonelontelonntityelondgelonBuildelonr.gelontelonntitielonsMapAndUpdatelonCachelon(
          twelonelontId = sourcelonTwelonelontId,
          twelonelontDelontails = elonvelonnt.sourcelonTwelonelontDelontails
        )

        sourcelonTwelonelontelonntitielonsMapFut.map { sourcelonTwelonelontelonntitielonsMap =>
          val elondgelon = UselonrTwelonelontelonntityelondgelon(
            sourcelonUselonr = authorId,
            targelontTwelonelont = sourcelonTwelonelontId,
            action = Action.Quotelon,
            melontadata = Somelon(twelonelontId), // melontadata is twelonelontId
            cardInfo = Somelon(sourcelonTwelonelontDelontails.cardInfo.toBytelon), // cardInfo of thelon sourcelon twelonelont
            elonntitielonsMap = sourcelonTwelonelontelonntitielonsMap,
            twelonelontDelontails = Somelon(sourcelonTwelonelontDelontails)
          )
          numQuotelonelondgelonsCountelonr.incr()
          Selonq(elondgelon)
        }
      }.gelontOrelonlselon(Futurelon.Nil)

    val twelonelontCrelonationelondgelonFut = gelontAndUpdatelonLastTwelonelontCrelonationTimelon(
      authorId = authorId,
      twelonelontId = twelonelontId,
      twelonelontTypelon = TwelonelontTypelon.Quotelon
    ).map { lastTwelonelontTimelon =>
      val melontadata = lastTwelonelontTimelon
      val cardInfo = uselonrTwelonelontelonngagelonmelonnt.twelonelontDelontails.map(_.cardInfo.toBytelon)
      val elondgelon = UselonrTwelonelontelonntityelondgelon(
        sourcelonUselonr = authorId,
        targelontTwelonelont = twelonelontId,
        action = Action.Twelonelont,
        melontadata = melontadata,
        cardInfo = cardInfo,
        elonntitielonsMap = Nonelon,
        twelonelontDelontails = uselonrTwelonelontelonngagelonmelonnt.twelonelontDelontails
      )
      numTwelonelontelondgelonsCountelonr.incr()
      Selonq(elondgelon)
    }

    Futurelon.join(quotelonTwelonelontelondgelonFut, twelonelontCrelonationelondgelonFut).map {
      caselon (quotelonelondgelon, crelonationelondgelon) =>
        quotelonelondgelon ++ crelonationelondgelon
    }
  }

  /**
   * Build elondgelons for a Twelonelont elonvelonnt. A Twelonelont elonmits 3 tyelons elondgelons:
   * 1. A twelonelont crelonation elondgelon: author -> Twelonelont -> TwelonelontId
   * 2. IsMelonntionelond elondgelons: melonntionelondUselonrId -> IsMelonntionelond -> TwelonelontId
   * 3. IsMelondiataggelond elondgelons: melondiataggelondUselonrId -> IsMelondiataggelond -> TwelonelontId
   */
  privatelon delonf buildTwelonelontelondgelons(elonvelonnt: TwelonelontCrelonatelonelonvelonntDelontails): Futurelon[Selonq[UselonrTwelonelontelonntityelondgelon]] = {
    val uselonrTwelonelontelonngagelonmelonnt = elonvelonnt.uselonrTwelonelontelonngagelonmelonnt
    val twelonelontDelontails = uselonrTwelonelontelonngagelonmelonnt.twelonelontDelontails
    val twelonelontId = uselonrTwelonelontelonngagelonmelonnt.twelonelontId
    val authorId = uselonrTwelonelontelonngagelonmelonnt.elonngagelonUselonrId

    val cardInfo = twelonelontDelontails.map(_.cardInfo.toBytelon)

    val elonntitielonsMapFut = uselonrTwelonelontelonntityelondgelonBuildelonr.gelontelonntitielonsMapAndUpdatelonCachelon(
      twelonelontId = twelonelontId,
      twelonelontDelontails = twelonelontDelontails
    )

    val lastTwelonelontTimelonFut = gelontAndUpdatelonLastTwelonelontCrelonationTimelon(
      authorId = authorId,
      twelonelontId = twelonelontId,
      twelonelontTypelon = TwelonelontTypelon.Twelonelont
    )

    Futurelon.join(elonntitielonsMapFut, lastTwelonelontTimelonFut).map {
      caselon (elonntitielonsMap, lastTwelonelontTimelon) =>
        val twelonelontCrelonationelondgelon = UselonrTwelonelontelonntityelondgelon(
          sourcelonUselonr = authorId,
          targelontTwelonelont = twelonelontId,
          action = Action.Twelonelont,
          melontadata = lastTwelonelontTimelon,
          cardInfo = cardInfo,
          elonntitielonsMap = elonntitielonsMap,
          twelonelontDelontails = uselonrTwelonelontelonngagelonmelonnt.twelonelontDelontails
        )
        numTwelonelontelondgelonsCountelonr.incr()

        val isMelonntionelondelondgelons = elonvelonnt.validMelonntionUselonrIds
          .map(_.map { melonntionelondUselonrId =>
            UselonrTwelonelontelonntityelondgelon(
              sourcelonUselonr = melonntionelondUselonrId,
              targelontTwelonelont = twelonelontId,
              action = Action.IsMelonntionelond,
              melontadata = Somelon(twelonelontId),
              cardInfo = cardInfo,
              elonntitielonsMap = elonntitielonsMap,
              twelonelontDelontails = uselonrTwelonelontelonngagelonmelonnt.twelonelontDelontails
            )
          }).gelontOrelonlselon(Nil)
        numIsMelonntionelondelondgelonsCountelonr.incr(isMelonntionelondelondgelons.sizelon)

        val isMelondiataggelondelondgelons = elonvelonnt.validMelondiatagUselonrIds
          .map(_.map { melondiataggelondUselonrId =>
            UselonrTwelonelontelonntityelondgelon(
              sourcelonUselonr = melondiataggelondUselonrId,
              targelontTwelonelont = twelonelontId,
              action = Action.IsMelondiaTaggelond,
              melontadata = Somelon(twelonelontId),
              cardInfo = cardInfo,
              elonntitielonsMap = elonntitielonsMap,
              twelonelontDelontails = uselonrTwelonelontelonngagelonmelonnt.twelonelontDelontails
            )
          }).gelontOrelonlselon(Nil)
        numIsMelondiataggelondelondgelonsCountelonr.incr(isMelondiataggelondelondgelons.sizelon)

        Selonq(twelonelontCrelonationelondgelon) ++ isMelonntionelondelondgelons ++ isMelondiataggelondelondgelons
    }
  }

  /**
   * For a givelonn uselonr, relonad thelon uselonr's last timelon twelonelontelond from thelon MH storelon, and
   * writelon thelon nelonw twelonelont timelon into thelon MH storelon belonforelon relonturning.
   * Notelon this function is async, so thelon MH writelon opelonrations will continuelon to elonxeloncutelon on its own.
   * This might crelonatelon a relonad/writelon racelon condition, but it's elonxpelonctelond.
   */
  privatelon delonf gelontAndUpdatelonLastTwelonelontCrelonationTimelon(
    authorId: Long,
    twelonelontId: Long,
    twelonelontTypelon: TwelonelontTypelon
  ): Futurelon[Option[Long]] = {
    val nelonwTwelonelontInfo = ReloncosUselonrTwelonelontInfo(
      authorId,
      twelonelontId,
      twelonelontTypelon,
      SnowflakelonUtils.twelonelontCrelonationTimelon(twelonelontId).map(_.inMillis).gelontOrelonlselon(Timelon.now.inMillis)
    )

    twelonelontCrelonationStorelon
      .gelont(authorId)
      .map(_.map { prelonviousTwelonelontInfoSelonq =>
        val lastTwelonelontTimelon = prelonviousTwelonelontInfoSelonq
          .filtelonr(info => info.twelonelontTypelon == TwelonelontTypelon.Twelonelont || info.twelonelontTypelon == TwelonelontTypelon.Quotelon)
          .map(_.twelonelontTimelonstamp)
          .sortBy(-_)
          .helonadOption // Felontch thelon latelonst timelon uselonr Twelonelontelond or Quotelond
          .gelontOrelonlselon(
            Timelon.Bottom.inMillis
          ) // Last twelonelont timelon nelonvelonr reloncordelond in MH, delonfault to oldelonst point in timelon

        if (lastTwelonelontTimelon == Timelon.Bottom.inMillis) lastTwelonelontTimelonNotInMh.incr()
        lastTwelonelontTimelon
      })
      .elonnsurelon {
        twelonelontCrelonationStorelon
          .put(authorId, nelonwTwelonelontInfo)
          .onSuccelonss(_ => twelonelontCrelonationStorelonInselonrts.incr())
          .onFailurelon { elon =>
            statsReloncelonivelonr.countelonr("writelon_failelond_with_elonx:" + elon.gelontClass.gelontNamelon).incr()
          }
      }
  }

  ovelonrridelon delonf buildelondgelons(elonvelonnt: TwelonelontCrelonatelonelonvelonntDelontails): Futurelon[Selonq[UselonrTwelonelontelonntityelondgelon]] = {
    val uselonrTwelonelontelonngagelonmelonnt = elonvelonnt.uselonrTwelonelontelonngagelonmelonnt
    uselonrTwelonelontelonngagelonmelonnt.action match {
      caselon Action.Relonply =>
        buildRelonplyelondgelon(elonvelonnt)
      caselon Action.Relontwelonelont =>
        buildRelontwelonelontelondgelon(elonvelonnt)
      caselon Action.Twelonelont =>
        buildTwelonelontelondgelons(elonvelonnt)
      caselon Action.Quotelon =>
        buildQuotelonelondgelons(elonvelonnt)
      caselon _ =>
        numInvalidActionCountelonr.incr()
        Futurelon.Nil
    }

  }

  ovelonrridelon delonf filtelonrelondgelons(
    elonvelonnt: TwelonelontCrelonatelonelonvelonntDelontails,
    elondgelons: Selonq[UselonrTwelonelontelonntityelondgelon]
  ): Futurelon[Selonq[UselonrTwelonelontelonntityelondgelon]] = {
    Futurelon(elondgelons) // No filtelonring for now. Add morelon if nelonelondelond
  }
}
