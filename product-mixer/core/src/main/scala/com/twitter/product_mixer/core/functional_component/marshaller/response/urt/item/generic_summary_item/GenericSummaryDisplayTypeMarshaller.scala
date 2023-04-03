packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.gelonnelonric_summary_itelonm

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.gelonnelonric_summary.GelonnelonricSummaryItelonmDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.gelonnelonric_summary.HelonroDisplayTypelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class GelonnelonricSummaryDisplayTypelonMarshallelonr @Injelonct() () {

  delonf apply(
    gelonnelonricSummaryItelonmDisplayTypelon: GelonnelonricSummaryItelonmDisplayTypelon
  ): urt.GelonnelonricSummaryDisplayTypelon =
    gelonnelonricSummaryItelonmDisplayTypelon match {
      caselon HelonroDisplayTypelon => urt.GelonnelonricSummaryDisplayTypelon.Helonro
    }
}
