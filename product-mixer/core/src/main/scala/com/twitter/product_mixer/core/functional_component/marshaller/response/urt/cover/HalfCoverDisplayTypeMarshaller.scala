packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.covelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.CelonntelonrCovelonrHalfCovelonrDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.CovelonrHalfCovelonrDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.HalfCovelonrDisplayTypelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class HalfCovelonrDisplayTypelonMarshallelonr @Injelonct() () {

  delonf apply(halfCovelonrDisplayTypelon: HalfCovelonrDisplayTypelon): urt.HalfCovelonrDisplayTypelon =
    halfCovelonrDisplayTypelon match {
      caselon CelonntelonrCovelonrHalfCovelonrDisplayTypelon => urt.HalfCovelonrDisplayTypelon.CelonntelonrCovelonr
      caselon CovelonrHalfCovelonrDisplayTypelon => urt.HalfCovelonrDisplayTypelon.Covelonr
    }
}
