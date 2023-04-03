packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.covelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.CovelonrContelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.FullCovelonrContelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.HalfCovelonrContelonnt
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class CovelonrContelonntMarshallelonr @Injelonct() (
  fullCovelonrContelonntMarshallelonr: FullCovelonrContelonntMarshallelonr,
  halfCovelonrContelonntMarshallelonr: HalfCovelonrContelonntMarshallelonr) {

  delonf apply(covelonrContelonnt: CovelonrContelonnt): urt.Covelonr = covelonrContelonnt match {
    caselon fullCovelonr: FullCovelonrContelonnt => fullCovelonrContelonntMarshallelonr(fullCovelonr)
    caselon halfCovelonr: HalfCovelonrContelonnt => halfCovelonrContelonntMarshallelonr(halfCovelonr)
  }
}
