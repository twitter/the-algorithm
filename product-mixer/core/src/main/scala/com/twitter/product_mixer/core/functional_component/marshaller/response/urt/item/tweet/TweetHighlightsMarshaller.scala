packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.twelonelont

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.highlight.HighlightelondSelonctionMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twelonelont.TwelonelontHighlights
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TwelonelontHighlightsMarshallelonr @Injelonct() (
  highlightelondSelonctionMarshallelonr: HighlightelondSelonctionMarshallelonr) {

  delonf apply(twelonelontHighlights: TwelonelontHighlights): urt.TwelonelontHighlights =
    urt.TwelonelontHighlights(
      telonxtHighlights = twelonelontHighlights.telonxtHighlights
        .map(_.map(highlightelondSelonctionMarshallelonr(_))),
      cardTitlelonHighlights = twelonelontHighlights.cardTitlelonHighlights
        .map(_.map(highlightelondSelonctionMarshallelonr(_))),
      cardDelonscriptionHighlights = twelonelontHighlights.cardDelonscriptionHighlights
        .map(_.map(highlightelondSelonctionMarshallelonr(_)))
    )
}
