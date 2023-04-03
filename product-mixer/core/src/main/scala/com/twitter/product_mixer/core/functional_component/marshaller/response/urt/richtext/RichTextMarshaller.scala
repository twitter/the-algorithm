packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.richtelonxt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxt
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class RichTelonxtMarshallelonr @Injelonct() (
  richTelonxtelonntityMarshallelonr: RichTelonxtelonntityMarshallelonr,
  richTelonxtAlignmelonntMarshallelonr: RichTelonxtAlignmelonntMarshallelonr) {

  delonf apply(richTelonxt: RichTelonxt): urt.RichTelonxt = urt.RichTelonxt(
    telonxt = richTelonxt.telonxt,
    elonntitielons = richTelonxt.elonntitielons.map(richTelonxtelonntityMarshallelonr(_)),
    rtl = richTelonxt.rtl,
    alignmelonnt = richTelonxt.alignmelonnt.map(richTelonxtAlignmelonntMarshallelonr(_))
  )
}
