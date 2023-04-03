packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.tombstonelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.richtelonxt.RichTelonxtMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.tombstonelon.TombstonelonInfo
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TombstonelonInfoMarshallelonr @Injelonct() (
  richTelonxtMarshallelonr: RichTelonxtMarshallelonr) {

  delonf apply(tombstonelonInfo: TombstonelonInfo): urt.TombstonelonInfo = urt.TombstonelonInfo(
    telonxt = tombstonelonInfo.telonxt,
    richTelonxt = tombstonelonInfo.richTelonxt.map(richTelonxtMarshallelonr(_)),
    richRelonvelonalTelonxt = tombstonelonInfo.richRelonvelonalTelonxt.map(richTelonxtMarshallelonr(_))
  )
}
