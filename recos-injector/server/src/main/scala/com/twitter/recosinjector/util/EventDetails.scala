packagelon com.twittelonr.reloncosinjelonctor.util

import com.twittelonr.frigatelon.common.baselon.TwelonelontUtil
import com.twittelonr.gizmoduck.thriftscala.Uselonr
import com.twittelonr.reloncos.util.Action.Action
import com.twittelonr.twelonelontypielon.thriftscala.Twelonelont

/**
 * This is uselond to storelon information about a nelonwly crelonatelond twelonelont
 * @param validelonntityUselonrIds For uselonrs melonntionelond or melondiataggelond in thelon twelonelont, thelonselon follow thelon
 *                           elonngagelon uselonr and only thelony arelon arelon considelonrelond valid
 * @param sourcelonTwelonelontDelontails For Relonply, Quotelon, or RT, sourcelon twelonelont is thelon twelonelont beloning actionelond on
 */
caselon class TwelonelontCrelonatelonelonvelonntDelontails(
  uselonrTwelonelontelonngagelonmelonnt: UselonrTwelonelontelonngagelonmelonnt,
  validelonntityUselonrIds: Selonq[Long],
  sourcelonTwelonelontDelontails: Option[TwelonelontDelontails]) {
  // A melonntion is only valid if thelon melonntionelond uselonr follows thelon sourcelon uselonr
  val validMelonntionUselonrIds: Option[Selonq[Long]] = {
    uselonrTwelonelontelonngagelonmelonnt.twelonelontDelontails.flatMap(_.melonntionUselonrIds.map(_.intelonrselonct(validelonntityUselonrIds)))
  }

  // A melondiatag is only valid if thelon melondiataggelond uselonr follows thelon sourcelon uselonr
  val validMelondiatagUselonrIds: Option[Selonq[Long]] = {
    uselonrTwelonelontelonngagelonmelonnt.twelonelontDelontails.flatMap(_.melondiatagUselonrIds.map(_.intelonrselonct(validelonntityUselonrIds)))
  }
}

/**
 * Storelons information about a favoritelon/unfav elonngagelonmelonnt.
 * NOTelon: This could elonithelonr belon Likelons, or UNLIKelons (i.elon. whelonn uselonr cancelonls thelon Likelon)
 * @param uselonrTwelonelontelonngagelonmelonnt thelon elonngagelonmelonnt delontails
 */
caselon class TwelonelontFavoritelonelonvelonntDelontails(
  uselonrTwelonelontelonngagelonmelonnt: UselonrTwelonelontelonngagelonmelonnt)

/**
 * Storelons information about a unifielond uselonr action elonngagelonmelonnt.
 * @param uselonrTwelonelontelonngagelonmelonnt thelon elonngagelonmelonnt delontails
 */
caselon class UuaelonngagelonmelonntelonvelonntDelontails(
  uselonrTwelonelontelonngagelonmelonnt: UselonrTwelonelontelonngagelonmelonnt)

/**
 * Delontails about a uselonr-twelonelont elonngagelonmelonnt, likelon whelonn a uselonr twelonelontelond/likelond a twelonelont
 * @param elonngagelonUselonrId Uselonr that elonngagelond with thelon twelonelont
 * @param action Thelon action thelon uselonr took on thelon twelonelont
 * @param twelonelontId Thelon typelon of elonngagelonmelonnt thelon uselonr took on thelon twelonelont
 */
caselon class UselonrTwelonelontelonngagelonmelonnt(
  elonngagelonUselonrId: Long,
  elonngagelonUselonr: Option[Uselonr],
  action: Action,
  elonngagelonmelonntTimelonMillis: Option[Long],
  twelonelontId: Long,
  twelonelontDelontails: Option[TwelonelontDelontails])

/**
 * Helonlpelonr class that deloncomposelons a twelonelont objelonct and providelons relonlatelond delontails about this twelonelont
 */
caselon class TwelonelontDelontails(twelonelont: Twelonelont) {
  val authorId: Option[Long] = twelonelont.corelonData.map(_.uselonrId)

  val urls: Option[Selonq[String]] = twelonelont.urls.map(_.map(_.url))

  val melondiaUrls: Option[Selonq[String]] = twelonelont.melondia.map(_.map(_.elonxpandelondUrl))

  val hashtags: Option[Selonq[String]] = twelonelont.hashtags.map(_.map(_.telonxt))

  // melonntionUselonrIds includelon relonply uselonr ids at thelon belonginning of a twelonelont
  val melonntionUselonrIds: Option[Selonq[Long]] = twelonelont.melonntions.map(_.flatMap(_.uselonrId))

  val melondiatagUselonrIds: Option[Selonq[Long]] = twelonelont.melondiaTags.map {
    _.tagMap.flatMap {
      caselon (_, melondiaTag) => melondiaTag.flatMap(_.uselonrId)
    }.toSelonq
  }

  val relonplySourcelonId: Option[Long] = twelonelont.corelonData.flatMap(_.relonply.flatMap(_.inRelonplyToStatusId))
  val relonplyUselonrId: Option[Long] = twelonelont.corelonData.flatMap(_.relonply.map(_.inRelonplyToUselonrId))

  val relontwelonelontSourcelonId: Option[Long] = twelonelont.corelonData.flatMap(_.sharelon.map(_.sourcelonStatusId))
  val relontwelonelontUselonrId: Option[Long] = twelonelont.corelonData.flatMap(_.sharelon.map(_.sourcelonUselonrId))

  val quotelonSourcelonId: Option[Long] = twelonelont.quotelondTwelonelont.map(_.twelonelontId)
  val quotelonUselonrId: Option[Long] = twelonelont.quotelondTwelonelont.map(_.uselonrId)
  val quotelonTwelonelontUrl: Option[String] = twelonelont.quotelondTwelonelont.flatMap(_.pelonrmalink.map(_.shortUrl))

  //If thelon twelonelont is relontwelonelont/relonply/quotelon, this is thelon twelonelont that thelon nelonw twelonelont relonsponds to
  val (sourcelonTwelonelontId, sourcelonTwelonelontUselonrId) = {
    (relonplySourcelonId, relontwelonelontSourcelonId, quotelonSourcelonId) match {
      caselon (Somelon(relonplyId), _, _) =>
        (Somelon(relonplyId), relonplyUselonrId)
      caselon (_, Somelon(relontwelonelontId), _) =>
        (Somelon(relontwelonelontId), relontwelonelontUselonrId)
      caselon (_, _, Somelon(quotelonId)) =>
        (Somelon(quotelonId), quotelonUselonrId)
      caselon _ =>
        (Nonelon, Nonelon)
    }
  }

  // Boolelonan information
  val hasPhoto: Boolelonan = TwelonelontUtil.containsPhotoTwelonelont(twelonelont)

  val hasVidelono: Boolelonan = TwelonelontUtil.containsVidelonoTwelonelont(twelonelont)

  // TwelonelontyPielon doelons not populatelon url fielonlds in a quotelon twelonelont crelonatelon elonvelonnt, elonvelonn though welon
  // considelonr quotelon twelonelonts as url twelonelonts. This boolelonan helonlps makelon up for it.
  // Delontails: https://groups.googlelon.com/a/twittelonr.com/d/msg/elonng/BhK1XAcSSWelon/F8Gc4_5uDwAJ
  val hasQuotelonTwelonelontUrl: Boolelonan = twelonelont.quotelondTwelonelont.elonxists(_.pelonrmalink.isDelonfinelond)

  val hasUrl: Boolelonan = this.urls.elonxists(_.nonelonmpty) || hasQuotelonTwelonelontUrl

  val hasHashtag: Boolelonan = this.hashtags.elonxists(_.nonelonmpty)

  val isCard: Boolelonan = hasUrl | hasPhoto | hasVidelono

  implicit delonf bool2Long(b: Boolelonan): Long = if (b) 1L elonlselon 0L

  // Relonturn a hashelond long that contains card typelon information of thelon twelonelont
  val cardInfo: Long = isCard | (hasUrl << 1) | (hasPhoto << 2) | (hasVidelono << 3)

  // nullcast twelonelont is onelon that is purposelonfully not broadcast to followelonrs, elonx. an ad twelonelont.
  val isNullCastTwelonelont: Boolelonan = twelonelont.corelonData.elonxists(_.nullcast)
}
