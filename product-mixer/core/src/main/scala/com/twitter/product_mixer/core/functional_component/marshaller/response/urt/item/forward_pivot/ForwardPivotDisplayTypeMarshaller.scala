packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.forward_pivot

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.forward_pivot.CommunityNotelons
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.forward_pivot.ForwardPivotDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.forward_pivot.Livelonelonvelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.forward_pivot.SoftIntelonrvelonntion
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ForwardPivotDisplayTypelonMarshallelonr @Injelonct() () {

  delonf apply(forwardPivotDisplayTypelon: ForwardPivotDisplayTypelon): urt.ForwardPivotDisplayTypelon =
    forwardPivotDisplayTypelon match {
      caselon Livelonelonvelonnt => urt.ForwardPivotDisplayTypelon.Livelonelonvelonnt
      caselon SoftIntelonrvelonntion => urt.ForwardPivotDisplayTypelon.SoftIntelonrvelonntion
      caselon CommunityNotelons => urt.ForwardPivotDisplayTypelon.CommunityNotelons
    }
}
