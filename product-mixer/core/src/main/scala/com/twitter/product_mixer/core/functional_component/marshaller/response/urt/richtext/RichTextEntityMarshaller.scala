packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.richtelonxt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxtelonntity
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class RichTelonxtelonntityMarshallelonr @Injelonct() (
  relonfelonrelonncelonObjelonctMarshallelonr: RelonfelonrelonncelonObjelonctMarshallelonr,
  richTelonxtFormatMarshallelonr: RichTelonxtFormatMarshallelonr) {

  delonf apply(elonntity: RichTelonxtelonntity): urt.RichTelonxtelonntity = urt.RichTelonxtelonntity(
    fromIndelonx = elonntity.fromIndelonx,
    toIndelonx = elonntity.toIndelonx,
    relonf = elonntity.relonf.map(relonfelonrelonncelonObjelonctMarshallelonr(_)),
    format = elonntity.format.map(richTelonxtFormatMarshallelonr(_))
  )
}
