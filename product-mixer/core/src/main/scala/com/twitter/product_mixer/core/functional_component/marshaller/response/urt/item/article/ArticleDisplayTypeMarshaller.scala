packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.articlelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.articlelon.ArticlelonDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.articlelon.Delonfault
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ArticlelonDisplayTypelonMarshallelonr @Injelonct() () {
  delonf apply(articlelonDisplayTypelon: ArticlelonDisplayTypelon): urt.ArticlelonDisplayTypelon =
    articlelonDisplayTypelon match {
      caselon Delonfault => urt.ArticlelonDisplayTypelon.Delonfault
    }
}
