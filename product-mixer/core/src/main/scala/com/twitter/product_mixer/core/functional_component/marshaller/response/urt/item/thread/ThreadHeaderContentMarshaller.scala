packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.threlonad

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.threlonad._
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ThrelonadHelonadelonrContelonntMarshallelonr @Injelonct() () {
  delonf apply(contelonnt: ThrelonadHelonadelonrContelonnt): urt.ThrelonadHelonadelonrContelonnt = contelonnt match {
    caselon UselonrThrelonadHelonadelonr(uselonrId) =>
      urt.ThrelonadHelonadelonrContelonnt.UselonrThrelonadHelonadelonr(urt.UselonrThrelonadHelonadelonr(uselonrId))
  }
}
