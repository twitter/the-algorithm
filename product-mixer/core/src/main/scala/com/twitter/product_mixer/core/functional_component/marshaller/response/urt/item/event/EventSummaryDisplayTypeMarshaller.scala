packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.elonvelonnt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.elonvelonnt.elonvelonntSummaryDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.elonvelonnt.CelonllelonvelonntSummaryDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.elonvelonnt.HelonroelonvelonntSummaryDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.elonvelonnt.CelonllWithProminelonntSocialContelonxtelonvelonntSummaryDisplayTypelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class elonvelonntSummaryDisplayTypelonMarshallelonr @Injelonct() () {

  delonf apply(
    elonvelonntSummaryDisplayTypelon: elonvelonntSummaryDisplayTypelon
  ): urt.elonvelonntSummaryDisplayTypelon = elonvelonntSummaryDisplayTypelon match {
    caselon CelonllelonvelonntSummaryDisplayTypelon =>
      urt.elonvelonntSummaryDisplayTypelon.Celonll
    caselon HelonroelonvelonntSummaryDisplayTypelon =>
      urt.elonvelonntSummaryDisplayTypelon.Helonro
    caselon CelonllWithProminelonntSocialContelonxtelonvelonntSummaryDisplayTypelon =>
      urt.elonvelonntSummaryDisplayTypelon.CelonllWithProminelonntSocialContelonxt
  }
}
