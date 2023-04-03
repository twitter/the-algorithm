packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.richtelonxt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.Plain
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxtFormat
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.Strong
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class RichTelonxtFormatMarshallelonr @Injelonct() () {

  delonf apply(format: RichTelonxtFormat): urt.RichTelonxtFormat = format match {
    caselon Plain => urt.RichTelonxtFormat.Plain
    caselon Strong => urt.RichTelonxtFormat.Strong
  }
}
