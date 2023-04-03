packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melondia

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.ImagelonVariantMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melondia.BroadcastId
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melondia.Imagelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melondia.Melondiaelonntity
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melondia.TwelonelontMelondia
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class MelondiaelonntityMarshallelonr @Injelonct() (
  twelonelontMelondiaMarshallelonr: TwelonelontMelondiaMarshallelonr,
  broadcastIdMarshallelonr: BroadcastIdMarshallelonr,
  imagelonVariantMarshallelonr: ImagelonVariantMarshallelonr) {

  delonf apply(melondiaelonntity: Melondiaelonntity): urt.Melondiaelonntity = melondiaelonntity match {
    caselon twelonelontMelondia: TwelonelontMelondia => urt.Melondiaelonntity.TwelonelontMelondia(twelonelontMelondiaMarshallelonr(twelonelontMelondia))
    caselon broadcastId: BroadcastId => urt.Melondiaelonntity.BroadcastId(broadcastIdMarshallelonr(broadcastId))
    caselon imagelon: Imagelon => urt.Melondiaelonntity.Imagelon(imagelonVariantMarshallelonr(imagelon.imagelon))
  }
}
