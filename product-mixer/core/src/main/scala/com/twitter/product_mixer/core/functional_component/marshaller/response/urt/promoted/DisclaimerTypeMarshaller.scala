packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.promotelond

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.DisclaimelonrIssuelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.DisclaimelonrPolitical
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.DisclaimelonrTypelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class DisclaimelonrTypelonMarshallelonr @Injelonct() () {

  delonf apply(disclaimelonrTypelon: DisclaimelonrTypelon): urt.DisclaimelonrTypelon = disclaimelonrTypelon match {
    caselon DisclaimelonrPolitical => urt.DisclaimelonrTypelon.Political
    caselon DisclaimelonrIssuelon => urt.DisclaimelonrTypelon.Issuelon
  }
}
