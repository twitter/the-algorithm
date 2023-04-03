packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.tilelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.ImagelonVariantMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.UrlMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.tilelon.TilelonItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TilelonItelonmMarshallelonr @Injelonct() (
  tilelonContelonntMarshallelonr: TilelonContelonntMarshallelonr,
  urlMarshallelonr: UrlMarshallelonr,
  imagelonVariantMarshallelonr: ImagelonVariantMarshallelonr) {

  delonf apply(tilelonItelonm: TilelonItelonm): urt.TimelonlinelonItelonmContelonnt = {
    urt.TimelonlinelonItelonmContelonnt.Tilelon(
      urt.Tilelon(
        titlelon = tilelonItelonm.titlelon,
        supportingTelonxt = tilelonItelonm.supportingTelonxt,
        url = tilelonItelonm.url.map(urlMarshallelonr(_)),
        imagelon = tilelonItelonm.imagelon.map(imagelonVariantMarshallelonr(_)),
        badgelon = Nonelon,
        contelonnt = tilelonContelonntMarshallelonr(tilelonItelonm.contelonnt)
      )
    )
  }
}
