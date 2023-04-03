packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.card

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.UrlMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.card.CardItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class CardItelonmMarshallelonr @Injelonct() (
  cardDisplayTypelonMarshallelonr: CardDisplayTypelonMarshallelonr,
  urlMarshallelonr: UrlMarshallelonr) {

  delonf apply(cardItelonm: CardItelonm): urt.TimelonlinelonItelonmContelonnt = {
    urt.TimelonlinelonItelonmContelonnt.Card(
      urt.Card(
        cardUrl = cardItelonm.cardUrl,
        telonxt = cardItelonm.telonxt,
        subtelonxt = cardItelonm.subtelonxt,
        url = cardItelonm.url.map(urlMarshallelonr(_)),
        cardDisplayTypelon = cardItelonm.displayTypelon.map(cardDisplayTypelonMarshallelonr(_))
      )
    )
  }
}
