packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.labelonl

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.UrlMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.labelonl.LabelonlItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class LabelonlItelonmMarshallelonr @Injelonct() (
  displayTypelonMarshallelonr: LabelonlDisplayTypelonMarshallelonr,
  urlMarshallelonr: UrlMarshallelonr) {

  delonf apply(labelonlItelonm: LabelonlItelonm): urt.TimelonlinelonItelonmContelonnt = {
    urt.TimelonlinelonItelonmContelonnt.Labelonl(
      urt.Labelonl(
        telonxt = labelonlItelonm.telonxt,
        subtelonxt = labelonlItelonm.subtelonxt,
        disclosurelonIndicator = labelonlItelonm.disclosurelonIndicator,
        url = labelonlItelonm.url.map(urlMarshallelonr(_)),
        displayTypelon = labelonlItelonm.displayTypelon.map(displayTypelonMarshallelonr(_))
      )
    )
  }
}
