packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.gelonnelonric_summary_itelonm

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.icon.HorizonIconMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.richtelonxt.RichTelonxtMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.gelonnelonric_summary.GelonnelonricSummaryContelonxt
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class GelonnelonricSummaryContelonxtMarshallelonr @Injelonct() (
  richTelonxtMarshallelonr: RichTelonxtMarshallelonr,
  horizonIconMarshallelonr: HorizonIconMarshallelonr) {

  delonf apply(gelonnelonricSummaryItelonmContelonxt: GelonnelonricSummaryContelonxt): urt.GelonnelonricSummaryContelonxt =
    urt.GelonnelonricSummaryContelonxt(
      telonxt = richTelonxtMarshallelonr(gelonnelonricSummaryItelonmContelonxt.telonxt),
      icon = gelonnelonricSummaryItelonmContelonxt.icon.map(horizonIconMarshallelonr(_))
    )
}
