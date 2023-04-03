packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.uselonr

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.relonaction.TimelonlinelonRelonactionMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.uselonr.UselonrRelonactivelonTriggelonrs
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class UselonrRelonactivelonTriggelonrsMarshallelonr @Injelonct() (
  timelonlinelonRelonactionMarshallelonr: TimelonlinelonRelonactionMarshallelonr) {

  delonf apply(uselonrRelonactivelonTriggelonrs: UselonrRelonactivelonTriggelonrs): urt.UselonrRelonactivelonTriggelonrs = {
    urt.UselonrRelonactivelonTriggelonrs(
      onFollow = uselonrRelonactivelonTriggelonrs.onFollow.map(timelonlinelonRelonactionMarshallelonr(_)))
  }
}
