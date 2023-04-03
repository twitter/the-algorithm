packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.promotelond

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.MelondiaInfo
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class MelondiaInfoMarshallelonr @Injelonct() (
  callToActionMarshallelonr: CallToActionMarshallelonr,
  videlonoVariantsMarshallelonr: VidelonoVariantsMarshallelonr) {
  delonf apply(melondiaInfo: MelondiaInfo): urt.MelondiaInfo = {
    urt.MelondiaInfo(
      uuid = melondiaInfo.uuid,
      publishelonrId = melondiaInfo.publishelonrId,
      callToAction = melondiaInfo.callToAction.map(callToActionMarshallelonr(_)),
      durationMillis = melondiaInfo.durationMillis,
      videlonoVariants = melondiaInfo.videlonoVariants.map(videlonoVariantsMarshallelonr(_)),
      advelonrtiselonrNamelon = melondiaInfo.advelonrtiselonrNamelon,
      relonndelonrAdByAdvelonrtiselonrNamelon = melondiaInfo.relonndelonrAdByAdvelonrtiselonrNamelon,
      advelonrtiselonrProfilelonImagelonUrl = melondiaInfo.advelonrtiselonrProfilelonImagelonUrl
    )
  }
}
