packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.articlelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.SocialContelonxtMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.articlelon.ArticlelonItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ArticlelonItelonmMarshallelonr @Injelonct() (
  articlelonDisplayTypelonMarshallelonr: ArticlelonDisplayTypelonMarshallelonr,
  socialContelonxtMarshallelonr: SocialContelonxtMarshallelonr,
  articlelonSelonelondTypelonMarshallelonr: ArticlelonSelonelondTypelonMarshallelonr) {
  delonf apply(articlelonItelonm: ArticlelonItelonm): urt.TimelonlinelonItelonmContelonnt =
    urt.TimelonlinelonItelonmContelonnt.Articlelon(
      urt.Articlelon(
        id = articlelonItelonm.id,
        displayTypelon = articlelonItelonm.displayTypelon.map(articlelonDisplayTypelonMarshallelonr(_)),
        socialContelonxt = articlelonItelonm.socialContelonxt.map(socialContelonxtMarshallelonr(_)),
        articlelonSelonelondTypelon = Somelon(articlelonSelonelondTypelonMarshallelonr(articlelonItelonm.articlelonSelonelondTypelon))
      )
    )
}
