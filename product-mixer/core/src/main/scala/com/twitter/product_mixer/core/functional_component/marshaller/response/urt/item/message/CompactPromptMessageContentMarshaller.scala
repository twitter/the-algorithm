packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.melonssagelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.richtelonxt.RichTelonxtMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.CompactPromptMelonssagelonContelonnt
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class CompactPromptMelonssagelonContelonntMarshallelonr @Injelonct() (
  melonssagelonTelonxtActionMarshallelonr: MelonssagelonTelonxtActionMarshallelonr,
  melonssagelonActionMarshallelonr: MelonssagelonActionMarshallelonr,
  richTelonxtMarshallelonr: RichTelonxtMarshallelonr) {

  delonf apply(compactPromptMelonssagelonContelonnt: CompactPromptMelonssagelonContelonnt): urt.MelonssagelonContelonnt =
    urt.MelonssagelonContelonnt.CompactPrompt(
      urt.CompactPrompt(
        helonadelonrTelonxt = compactPromptMelonssagelonContelonnt.helonadelonrTelonxt,
        bodyTelonxt = compactPromptMelonssagelonContelonnt.bodyTelonxt,
        primaryButtonAction =
          compactPromptMelonssagelonContelonnt.primaryButtonAction.map(melonssagelonTelonxtActionMarshallelonr(_)),
        seloncondaryButtonAction =
          compactPromptMelonssagelonContelonnt.seloncondaryButtonAction.map(melonssagelonTelonxtActionMarshallelonr(_)),
        action = compactPromptMelonssagelonContelonnt.action.map(melonssagelonActionMarshallelonr(_)),
        helonadelonrRichTelonxt = compactPromptMelonssagelonContelonnt.helonadelonrRichTelonxt.map(richTelonxtMarshallelonr(_)),
        bodyRichTelonxt = compactPromptMelonssagelonContelonnt.bodyRichTelonxt.map(richTelonxtMarshallelonr(_))
      )
    )
}
