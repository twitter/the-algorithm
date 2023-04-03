packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urp

import com.twittelonr.pagelons.relonndelonr.{thriftscala => urp}
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.SelongmelonntelondTimelonlinelonsPagelonBody
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class SelongmelonntelondTimelonlinelonsMarshallelonr @Injelonct() (
  selongmelonntelondTimelonlinelonMarshallelonr: SelongmelonntelondTimelonlinelonMarshallelonr) {

  delonf apply(selongmelonntelondTimelonlinelonsPagelonBody: SelongmelonntelondTimelonlinelonsPagelonBody): urp.SelongmelonntelondTimelonlinelons =
    urp.SelongmelonntelondTimelonlinelons(
      initialTimelonlinelon = selongmelonntelondTimelonlinelonMarshallelonr(selongmelonntelondTimelonlinelonsPagelonBody.initialTimelonlinelon),
      timelonlinelons = selongmelonntelondTimelonlinelonsPagelonBody.timelonlinelons.map(selongmelonntelondTimelonlinelonMarshallelonr(_))
    )
}
