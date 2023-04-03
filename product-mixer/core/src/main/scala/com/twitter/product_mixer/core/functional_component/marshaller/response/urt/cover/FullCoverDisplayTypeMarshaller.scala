packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.covelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.CovelonrFullCovelonrDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.FullCovelonrDisplayTypelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class FullCovelonrDisplayTypelonMarshallelonr @Injelonct() () {

  delonf apply(halfCovelonrDisplayTypelon: FullCovelonrDisplayTypelon): urt.FullCovelonrDisplayTypelon =
    halfCovelonrDisplayTypelon match {
      caselon CovelonrFullCovelonrDisplayTypelon => urt.FullCovelonrDisplayTypelon.Covelonr
    }
}
