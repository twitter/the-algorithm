packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.promotelond

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.Amplify
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.DynamicPrelonrollTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.LivelonTvelonvelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.Markelontplacelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class DynamicPrelonrollTypelonMarshallelonr @Injelonct() () {

  delonf apply(dynamicPrelonrollTypelon: DynamicPrelonrollTypelon): urt.DynamicPrelonrollTypelon =
    dynamicPrelonrollTypelon match {
      caselon Amplify => urt.DynamicPrelonrollTypelon.Amplify
      caselon Markelontplacelon => urt.DynamicPrelonrollTypelon.Markelontplacelon
      caselon LivelonTvelonvelonnt => urt.DynamicPrelonrollTypelon.LivelonTvelonvelonnt
    }
}
