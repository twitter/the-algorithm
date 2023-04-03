packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.promotelond

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.DcmUrlOvelonrridelonTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.UnknownUrlOvelonrridelonTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.UrlOvelonrridelonTypelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class UrlOvelonrridelonTypelonMarshallelonr @Injelonct() () {

  delonf apply(urlOvelonrridelonTypelon: UrlOvelonrridelonTypelon): urt.UrlOvelonrridelonTypelon = urlOvelonrridelonTypelon match {
    caselon UnknownUrlOvelonrridelonTypelon => urt.UrlOvelonrridelonTypelon.Unknown
    caselon DcmUrlOvelonrridelonTypelon => urt.UrlOvelonrridelonTypelon.Dcm
  }
}
