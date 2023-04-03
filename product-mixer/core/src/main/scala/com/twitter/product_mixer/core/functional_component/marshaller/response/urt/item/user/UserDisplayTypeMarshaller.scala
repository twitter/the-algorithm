packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.uselonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.uselonr.PelonndingFollowUselonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.uselonr.Uselonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.uselonr.UselonrDelontailelond
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.uselonr.UselonrDisplayTypelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class UselonrDisplayTypelonMarshallelonr @Injelonct() () {

  delonf apply(uselonrDisplayTypelon: UselonrDisplayTypelon): urt.UselonrDisplayTypelon =
    uselonrDisplayTypelon match {
      caselon Uselonr => urt.UselonrDisplayTypelon.Uselonr
      caselon UselonrDelontailelond => urt.UselonrDisplayTypelon.UselonrDelontailelond
      caselon PelonndingFollowUselonr => urt.UselonrDisplayTypelon.PelonndingFollowUselonr
    }
}
