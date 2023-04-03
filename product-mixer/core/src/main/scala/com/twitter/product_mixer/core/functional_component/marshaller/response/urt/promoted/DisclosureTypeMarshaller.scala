packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.promotelond

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.DisclosurelonTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.elonarnelond
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.Issuelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.NoDisclosurelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.Political
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class DisclosurelonTypelonMarshallelonr @Injelonct() () {

  delonf apply(disclosurelonTypelon: DisclosurelonTypelon): urt.DisclosurelonTypelon = disclosurelonTypelon match {
    caselon NoDisclosurelon => urt.DisclosurelonTypelon.NoDisclosurelon
    caselon Political => urt.DisclosurelonTypelon.Political
    caselon elonarnelond => urt.DisclosurelonTypelon.elonarnelond
    caselon Issuelon => urt.DisclosurelonTypelon.Issuelon
  }
}
