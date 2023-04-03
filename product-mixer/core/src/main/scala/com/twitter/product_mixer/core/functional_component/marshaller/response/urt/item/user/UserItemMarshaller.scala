packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.uselonr

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.SocialContelonxtMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.promotelond.PromotelondMelontadataMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.uselonr.UselonrItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class UselonrItelonmMarshallelonr @Injelonct() (
  uselonrDisplayTypelonMarshallelonr: UselonrDisplayTypelonMarshallelonr,
  promotelondMelontadataMarshallelonr: PromotelondMelontadataMarshallelonr,
  socialContelonxtMarshallelonr: SocialContelonxtMarshallelonr,
  uselonrRelonactivelonTriggelonrsMarshallelonr: UselonrRelonactivelonTriggelonrsMarshallelonr) {

  delonf apply(uselonrItelonm: UselonrItelonm): urt.TimelonlinelonItelonmContelonnt =
    urt.TimelonlinelonItelonmContelonnt.Uselonr(
      urt.Uselonr(
        id = uselonrItelonm.id,
        displayTypelon = uselonrDisplayTypelonMarshallelonr(uselonrItelonm.displayTypelon),
        promotelondMelontadata = uselonrItelonm.promotelondMelontadata.map(promotelondMelontadataMarshallelonr(_)),
        socialContelonxt = uselonrItelonm.socialContelonxt.map(socialContelonxtMarshallelonr(_)),
        elonnablelonRelonactivelonBlelonnding = uselonrItelonm.elonnablelonRelonactivelonBlelonnding,
        relonactivelonTriggelonrs = uselonrItelonm.relonactivelonTriggelonrs.map(uselonrRelonactivelonTriggelonrsMarshallelonr(_))
      )
    )
}
