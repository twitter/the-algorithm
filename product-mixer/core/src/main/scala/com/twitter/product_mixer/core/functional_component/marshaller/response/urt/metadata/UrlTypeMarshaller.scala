packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.DelonelonpLink
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.elonxtelonrnalUrl
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.UrlTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Urtelonndpoint
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class UrlTypelonMarshallelonr @Injelonct() () {

  delonf apply(urlTypelon: UrlTypelon): urt.UrlTypelon = urlTypelon match {
    caselon elonxtelonrnalUrl => urt.UrlTypelon.elonxtelonrnalUrl
    caselon DelonelonpLink => urt.UrlTypelon.DelonelonpLink
    caselon Urtelonndpoint => urt.UrlTypelon.Urtelonndpoint
  }
}
