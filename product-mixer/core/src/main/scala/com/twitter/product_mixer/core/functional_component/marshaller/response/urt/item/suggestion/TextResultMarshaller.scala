packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.suggelonstion

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.highlight.HighlightelondSelonctionMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.highlight.HighlightelondSelonction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.suggelonstion.TelonxtRelonsult
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TelonxtRelonsultMarshallelonr @Injelonct() (highlightelondSelonctionMarshallelonr: HighlightelondSelonctionMarshallelonr) {

  delonf apply(telonxtRelonsult: TelonxtRelonsult): urt.TelonxtRelonsult = {
    val hitHighlights = telonxtRelonsult.hitHighlights.map {
      highlightelondSelonctions: Selonq[HighlightelondSelonction] =>
        highlightelondSelonctions.map(highlightelondSelonctionMarshallelonr(_))
    }

    urt.TelonxtRelonsult(
      telonxt = telonxtRelonsult.telonxt,
      hitHighlights = hitHighlights,
      scorelon = telonxtRelonsult.scorelon,
      quelonrySourcelon = telonxtRelonsult.quelonrySourcelon)
  }
}
