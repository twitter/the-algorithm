packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.card

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.card._
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}

@Singlelonton
class CardDisplayTypelonMarshallelonr @Injelonct() () {

  delonf apply(cardDisplayTypelon: CardDisplayTypelon): urt.CardDisplayTypelon = cardDisplayTypelon match {
    caselon HelonroDisplayTypelon => urt.CardDisplayTypelon.Helonro
    caselon CelonllDisplayTypelon => urt.CardDisplayTypelon.Celonll
    caselon TwelonelontCardDisplayTypelon => urt.CardDisplayTypelon.TwelonelontCard
  }
}
