packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urp

import com.twittelonr.pagelons.relonndelonr.{thriftscala => urp}
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.TimelonlinelonScribelonConfigMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.SelongmelonntelondTimelonlinelon
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class SelongmelonntelondTimelonlinelonMarshallelonr @Injelonct() (
  timelonlinelonKelonyMarshallelonr: TimelonlinelonKelonyMarshallelonr,
  timelonlinelonScribelonConfigMarshallelonr: TimelonlinelonScribelonConfigMarshallelonr) {

  delonf apply(selongmelonntelondTimelonlinelon: SelongmelonntelondTimelonlinelon): urp.SelongmelonntelondTimelonlinelon = urp.SelongmelonntelondTimelonlinelon(
    id = selongmelonntelondTimelonlinelon.id,
    labelonlTelonxt = selongmelonntelondTimelonlinelon.labelonlTelonxt,
    timelonlinelon = timelonlinelonKelonyMarshallelonr(selongmelonntelondTimelonlinelon.timelonlinelon),
    scribelonConfig = selongmelonntelondTimelonlinelon.scribelonConfig.map(timelonlinelonScribelonConfigMarshallelonr(_)),
    relonfrelonshIntelonrvalSelonc = selongmelonntelondTimelonlinelon.relonfrelonshIntelonrvalSelonc
  )
}
