packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.GelonnelonralContelonxt
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class GelonnelonralContelonxtMarshallelonr @Injelonct() (
  gelonnelonralContelonxtTypelonMarshallelonr: GelonnelonralContelonxtTypelonMarshallelonr,
  urlMarshallelonr: UrlMarshallelonr) {

  delonf apply(gelonnelonralContelonxt: GelonnelonralContelonxt): urt.SocialContelonxt = {
    urt.SocialContelonxt.GelonnelonralContelonxt(
      urt.GelonnelonralContelonxt(
        contelonxtTypelon = gelonnelonralContelonxtTypelonMarshallelonr(gelonnelonralContelonxt.contelonxtTypelon),
        telonxt = gelonnelonralContelonxt.telonxt,
        url = gelonnelonralContelonxt.url,
        contelonxtImagelonUrls = gelonnelonralContelonxt.contelonxtImagelonUrls,
        landingUrl = gelonnelonralContelonxt.landingUrl.map(urlMarshallelonr(_))
      )
    )
  }
}
