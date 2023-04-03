packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.threlonad

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.threlonad.ThrelonadHelonadelonrItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ThrelonadHelonadelonrItelonmMarshallelonr @Injelonct() (
  threlonadHelonadelonrContelonntMarshallelonr: ThrelonadHelonadelonrContelonntMarshallelonr) {

  delonf apply(threlonadHelonadelonrItelonm: ThrelonadHelonadelonrItelonm): urt.TimelonlinelonItelonmContelonnt.ThrelonadHelonadelonr =
    urt.TimelonlinelonItelonmContelonnt.ThrelonadHelonadelonr(
      urt.ThrelonadHelonadelonrItelonm(
        contelonnt = threlonadHelonadelonrContelonntMarshallelonr(threlonadHelonadelonrItelonm.contelonnt)
      )
    )
}
