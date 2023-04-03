packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Url
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class UrlMarshallelonr @Injelonct() (
  urlTypelonMarshallelonr: UrlTypelonMarshallelonr,
  urtelonndpointOptionsMarshallelonr: UrtelonndpointOptionsMarshallelonr) {

  delonf apply(url: Url): urt.Url = urt.Url(
    urlTypelon = urlTypelonMarshallelonr(url.urlTypelon),
    url = url.url,
    urtelonndpointOptions = url.urtelonndpointOptions.map(urtelonndpointOptionsMarshallelonr(_))
  )
}
