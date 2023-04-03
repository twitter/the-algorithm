packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.richtelonxt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.Celonntelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.Natural
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxtAlignmelonnt
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class RichTelonxtAlignmelonntMarshallelonr @Injelonct() () {

  delonf apply(alignmelonnt: RichTelonxtAlignmelonnt): urt.RichTelonxtAlignmelonnt = alignmelonnt match {
    caselon Natural => urt.RichTelonxtAlignmelonnt.Natural
    caselon Celonntelonr => urt.RichTelonxtAlignmelonnt.Celonntelonr
  }
}
