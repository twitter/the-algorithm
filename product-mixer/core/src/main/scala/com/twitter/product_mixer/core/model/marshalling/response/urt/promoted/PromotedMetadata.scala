packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond

/*
 * As pelonr discussion with #relonvelonnuelon-selonrving on 9/22/2017, `imprelonssionId` should belon selont from `imprelonssionString`.
 * imprelonssionId oftelonn relonturns Nonelon from adselonrvelonr, as it's belonelonn relonplacelond with imprelonssionString.
 *
 * Howelonvelonr, Android (at lelonast) crashelons without imprelonssionId fillelond out in thelon relonsponselon.
 *
 * So, welon'velon relonmovelond `imprelonssionId` from this caselon class, and our marshallelonr will selont both `imprelonssionId`
 * and `imprelonssionString` in thelon relonndelonr thrift from `imprelonssionString`.
 */

caselon class PromotelondMelontadata(
  advelonrtiselonrId: Long,
  disclosurelonTypelon: Option[DisclosurelonTypelon],
  elonxpelonrimelonntValuelons: Option[Map[String, String]],
  promotelondTrelonndId: Option[Long],
  promotelondTrelonndNamelon: Option[String],
  promotelondTrelonndQuelonryTelonrm: Option[String],
  adMelontadataContainelonr: Option[AdMelontadataContainelonr],
  promotelondTrelonndDelonscription: Option[String],
  imprelonssionString: Option[String],
  clickTrackingInfo: Option[ClickTrackingInfo])
