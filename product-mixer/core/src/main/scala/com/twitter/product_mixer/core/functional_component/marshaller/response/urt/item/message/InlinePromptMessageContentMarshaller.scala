packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.melonssagelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.SocialContelonxtMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.richtelonxt.RichTelonxtMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.InlinelonPromptMelonssagelonContelonnt
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class InlinelonPromptMelonssagelonContelonntMarshallelonr @Injelonct() (
  melonssagelonTelonxtActionMarshallelonr: MelonssagelonTelonxtActionMarshallelonr,
  richTelonxtMarshallelonr: RichTelonxtMarshallelonr,
  socialContelonxtMarshallelonr: SocialContelonxtMarshallelonr,
  uselonrFacelonpilelonMarshallelonr: UselonrFacelonpilelonMarshallelonr) {

  delonf apply(inlinelonPromptMelonssagelonContelonnt: InlinelonPromptMelonssagelonContelonnt): urt.MelonssagelonContelonnt =
    urt.MelonssagelonContelonnt.InlinelonPrompt(
      urt.InlinelonPrompt(
        helonadelonrTelonxt = inlinelonPromptMelonssagelonContelonnt.helonadelonrTelonxt,
        bodyTelonxt = inlinelonPromptMelonssagelonContelonnt.bodyTelonxt,
        primaryButtonAction =
          inlinelonPromptMelonssagelonContelonnt.primaryButtonAction.map(melonssagelonTelonxtActionMarshallelonr(_)),
        seloncondaryButtonAction =
          inlinelonPromptMelonssagelonContelonnt.seloncondaryButtonAction.map(melonssagelonTelonxtActionMarshallelonr(_)),
        helonadelonrRichTelonxt = inlinelonPromptMelonssagelonContelonnt.helonadelonrRichTelonxt.map(richTelonxtMarshallelonr(_)),
        bodyRichTelonxt = inlinelonPromptMelonssagelonContelonnt.bodyRichTelonxt.map(richTelonxtMarshallelonr(_)),
        socialContelonxt = inlinelonPromptMelonssagelonContelonnt.socialContelonxt.map(socialContelonxtMarshallelonr(_)),
        uselonrFacelonpilelon = inlinelonPromptMelonssagelonContelonnt.uselonrFacelonpilelon.map(uselonrFacelonpilelonMarshallelonr(_))
      )
    )
}
