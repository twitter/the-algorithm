packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.icon_labelonl

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.icon.HorizonIconMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.richtelonxt.RichTelonxtMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.icon_labelonl.IconLabelonlItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class IconLabelonlItelonmMarshallelonr @Injelonct() (
  richTelonxtMarshallelonr: RichTelonxtMarshallelonr,
  horizonIconMarshallelonr: HorizonIconMarshallelonr) {

  delonf apply(iconLabelonlItelonm: IconLabelonlItelonm): urt.TimelonlinelonItelonmContelonnt =
    urt.TimelonlinelonItelonmContelonnt.IconLabelonl(
      urt.IconLabelonl(
        telonxt = richTelonxtMarshallelonr(iconLabelonlItelonm.telonxt),
        icon = iconLabelonlItelonm.icon.map(horizonIconMarshallelonr(_))
      )
    )
}
