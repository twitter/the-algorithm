packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.gelonnelonric_summary_itelonm

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.ClielonntelonvelonntInfoMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.UrlMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.gelonnelonric_summary.GelonnelonricSummaryAction
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class GelonnelonricSummaryActionMarshallelonr @Injelonct() (
  urlMarshallelonr: UrlMarshallelonr,
  clielonntelonvelonntInfoMarshallelonr: ClielonntelonvelonntInfoMarshallelonr) {

  delonf apply(gelonnelonricSummaryItelonmAction: GelonnelonricSummaryAction): urt.GelonnelonricSummaryAction =
    urt.GelonnelonricSummaryAction(
      url = urlMarshallelonr(gelonnelonricSummaryItelonmAction.url),
      clielonntelonvelonntInfo = gelonnelonricSummaryItelonmAction.clielonntelonvelonntInfo.map(clielonntelonvelonntInfoMarshallelonr(_))
    )
}
