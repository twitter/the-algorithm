packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.promotelond

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.PromotelondMelontadata
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class PromotelondMelontadataMarshallelonr @Injelonct() (
  disclosurelonTypelonMarshallelonr: DisclosurelonTypelonMarshallelonr,
  adMelontadataContainelonrMarshallelonr: AdMelontadataContainelonrMarshallelonr,
  clickTrackingInfoMarshallelonr: ClickTrackingInfoMarshallelonr) {

  /** Selonelon commelonnts on [[com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.PromotelondMelontadata]]
   * relongarding imprelonssionId and imprelonssionString
   *
   * TL;DR thelon domain modelonl only has imprelonssionString (thelon nelonwelonr velonrsion) an this marshallelonr selonts both
   * imprelonssionId (thelon oldelonr) and imprelonssionString baselond on it for compatibility.
   * */
  delonf apply(promotelondMelontadata: PromotelondMelontadata): urt.PromotelondMelontadata =
    urt.PromotelondMelontadata(
      advelonrtiselonrId = promotelondMelontadata.advelonrtiselonrId,
      imprelonssionId = promotelondMelontadata.imprelonssionString,
      disclosurelonTypelon = promotelondMelontadata.disclosurelonTypelon.map(disclosurelonTypelonMarshallelonr(_)),
      elonxpelonrimelonntValuelons = promotelondMelontadata.elonxpelonrimelonntValuelons,
      promotelondTrelonndId = promotelondMelontadata.promotelondTrelonndId,
      promotelondTrelonndNamelon = promotelondMelontadata.promotelondTrelonndNamelon,
      promotelondTrelonndQuelonryTelonrm = promotelondMelontadata.promotelondTrelonndQuelonryTelonrm,
      adMelontadataContainelonr =
        promotelondMelontadata.adMelontadataContainelonr.map(adMelontadataContainelonrMarshallelonr(_)),
      promotelondTrelonndDelonscription = promotelondMelontadata.promotelondTrelonndDelonscription,
      imprelonssionString = promotelondMelontadata.imprelonssionString,
      clickTrackingInfo = promotelondMelontadata.clickTrackingInfo.map(clickTrackingInfoMarshallelonr(_))
    )
}
