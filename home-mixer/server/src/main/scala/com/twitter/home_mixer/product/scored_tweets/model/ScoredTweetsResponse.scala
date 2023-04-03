packagelon com.twittelonr.homelon_mixelonr.product.scorelond_twelonelonts.modelonl

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.HasMarshalling
import com.twittelonr.timelonlinelonselonrvicelon.suggelonsts.{thriftscala => st}
import com.twittelonr.twelonelontconvosvc.twelonelont_ancelonstor.{thriftscala => ta}
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}

caselon class ScorelondTwelonelont(
  twelonelontId: Long,
  authorId: Long,
  scorelon: Option[Doublelon],
  suggelonstTypelon: st.SuggelonstTypelon,
  sourcelonTwelonelontId: Option[Long],
  sourcelonUselonrId: Option[Long],
  quotelondTwelonelontId: Option[Long],
  quotelondUselonrId: Option[Long],
  inRelonplyToTwelonelontId: Option[Long],
  inRelonplyToUselonrId: Option[Long],
  direlonctelondAtUselonrId: Option[Long],
  inNelontwork: Option[Boolelonan],
  favoritelondByUselonrIds: Option[Selonq[Long]],
  followelondByUselonrIds: Option[Selonq[Long]],
  topicId: Option[Long],
  topicFunctionalityTypelon: Option[urt.TopicContelonxtFunctionalityTypelon],
  ancelonstors: Option[Selonq[ta.TwelonelontAncelonstor]],
  isRelonadFromCachelon: Option[Boolelonan],
  strelonamToKafka: Option[Boolelonan])

caselon class ScorelondTwelonelontsRelonsponselon(scorelondTwelonelonts: Selonq[ScorelondTwelonelont]) elonxtelonnds HasMarshalling
