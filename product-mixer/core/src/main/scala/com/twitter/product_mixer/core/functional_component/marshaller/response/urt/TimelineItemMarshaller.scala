packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.ClielonntelonvelonntInfoMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.FelonelondbackInfoMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TimelonlinelonItelonmMarshallelonr @Injelonct() (
  timelonlinelonItelonmContelonntMarshallelonr: TimelonlinelonItelonmContelonntMarshallelonr,
  clielonntelonvelonntInfoMarshallelonr: ClielonntelonvelonntInfoMarshallelonr,
  felonelondbackInfoMarshallelonr: FelonelondbackInfoMarshallelonr) {

  delonf apply(itelonm: TimelonlinelonItelonm): urt.TimelonlinelonItelonm = urt.TimelonlinelonItelonm(
    contelonnt = timelonlinelonItelonmContelonntMarshallelonr(itelonm),
    clielonntelonvelonntInfo = itelonm.clielonntelonvelonntInfo.map(clielonntelonvelonntInfoMarshallelonr(_)),
    felonelondbackInfo = itelonm.felonelondbackActionInfo.map(felonelondbackInfoMarshallelonr(_)),
    prompt = Nonelon
  )
}
