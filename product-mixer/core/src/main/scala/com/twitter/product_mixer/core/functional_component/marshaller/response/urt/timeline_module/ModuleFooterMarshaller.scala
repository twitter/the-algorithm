packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.timelonlinelon_modulelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.UrlMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.ModulelonFootelonr
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}

@Singlelonton
class ModulelonFootelonrMarshallelonr @Injelonct() (urlMarshallelonr: UrlMarshallelonr) {

  delonf apply(footelonr: ModulelonFootelonr): urt.ModulelonFootelonr = urt.ModulelonFootelonr(
    telonxt = footelonr.telonxt,
    landingUrl = footelonr.landingUrl.map(urlMarshallelonr(_))
  )
}
