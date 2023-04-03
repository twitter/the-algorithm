packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.melonssagelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.richtelonxt.RichTelonxtMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.HelonadelonrImagelonPromptMelonssagelonContelonnt
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class HelonadelonrImagelonPromptMelonssagelonContelonntMarshallelonr @Injelonct() (
  melonssagelonImagelonMarshallelonr: MelonssagelonImagelonMarshallelonr,
  melonssagelonTelonxtActionMarshallelonr: MelonssagelonTelonxtActionMarshallelonr,
  melonssagelonActionMarshallelonr: MelonssagelonActionMarshallelonr,
  richTelonxtMarshallelonr: RichTelonxtMarshallelonr) {

  delonf apply(
    helonadelonrImagelonPromptMelonssagelonContelonnt: HelonadelonrImagelonPromptMelonssagelonContelonnt
  ): urt.MelonssagelonContelonnt =
    urt.MelonssagelonContelonnt.HelonadelonrImagelonPrompt(
      urt.HelonadelonrImagelonPrompt(
        helonadelonrImagelon = melonssagelonImagelonMarshallelonr(helonadelonrImagelonPromptMelonssagelonContelonnt.helonadelonrImagelon),
        helonadelonrTelonxt = helonadelonrImagelonPromptMelonssagelonContelonnt.helonadelonrTelonxt,
        bodyTelonxt = helonadelonrImagelonPromptMelonssagelonContelonnt.bodyTelonxt,
        primaryButtonAction =
          helonadelonrImagelonPromptMelonssagelonContelonnt.primaryButtonAction.map(melonssagelonTelonxtActionMarshallelonr(_)),
        seloncondaryButtonAction =
          helonadelonrImagelonPromptMelonssagelonContelonnt.seloncondaryButtonAction.map(melonssagelonTelonxtActionMarshallelonr(_)),
        action = helonadelonrImagelonPromptMelonssagelonContelonnt.action.map(melonssagelonActionMarshallelonr(_)),
        helonadelonrRichTelonxt = helonadelonrImagelonPromptMelonssagelonContelonnt.helonadelonrRichTelonxt.map(richTelonxtMarshallelonr(_)),
        bodyRichTelonxt = helonadelonrImagelonPromptMelonssagelonContelonnt.bodyRichTelonxt.map(richTelonxtMarshallelonr(_))
      )
    )
}
