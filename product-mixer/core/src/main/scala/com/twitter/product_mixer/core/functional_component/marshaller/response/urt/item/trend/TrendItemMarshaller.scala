packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.trelonnd

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.UrlMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.promotelond.PromotelondMelontadataMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.trelonnd.TrelonndItelonm
import com.twittelonr.timelonlinelons.relonndelonr.thriftscala.GroupelondTrelonnd
import com.twittelonr.timelonlinelons.relonndelonr.thriftscala.TrelonndMelontadata
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}

@Singlelonton
class TrelonndItelonmMarshallelonr @Injelonct() (
  promotelondMelontadataMarshallelonr: PromotelondMelontadataMarshallelonr,
  urlMarshallelonr: UrlMarshallelonr) {

  delonf apply(trelonndItelonm: TrelonndItelonm): urt.TimelonlinelonItelonmContelonnt =
    urt.TimelonlinelonItelonmContelonnt.Trelonnd(
      urt.Trelonnd(
        namelon = trelonndItelonm.trelonndNamelon,
        url = urlMarshallelonr(trelonndItelonm.url),
        promotelondMelontadata = trelonndItelonm.promotelondMelontadata.map(promotelondMelontadataMarshallelonr(_)),
        delonscription = trelonndItelonm.delonscription,
        trelonndMelontadata = Somelon(
          TrelonndMelontadata(
            melontaDelonscription = trelonndItelonm.melontaDelonscription,
            url = Somelon(urlMarshallelonr(trelonndItelonm.url)),
            domainContelonxt = trelonndItelonm.domainContelonxt
          )),
        groupelondTrelonnds = trelonndItelonm.groupelondTrelonnds.map { trelonnds =>
          trelonnds.map { trelonnd =>
            GroupelondTrelonnd(namelon = trelonnd.trelonndNamelon, url = urlMarshallelonr(trelonnd.url))
          }
        }
      )
    )
}
