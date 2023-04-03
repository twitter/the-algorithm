packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.melonssagelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.CallbackMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.MelonssagelonPromptItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class MelonssagelonPromptItelonmMarshallelonr @Injelonct() (
  melonssagelonContelonntMarshallelonr: MelonssagelonContelonntMarshallelonr,
  callbackMarshallelonr: CallbackMarshallelonr) {

  delonf apply(melonssagelonPromptItelonm: MelonssagelonPromptItelonm): urt.TimelonlinelonItelonmContelonnt =
    urt.TimelonlinelonItelonmContelonnt.Melonssagelon(
      urt.MelonssagelonPrompt(
        contelonnt = melonssagelonContelonntMarshallelonr(melonssagelonPromptItelonm.contelonnt),
        imprelonssionCallbacks = melonssagelonPromptItelonm.imprelonssionCallbacks.map { callbackList =>
          callbackList.map(callbackMarshallelonr(_))
        }
      )
    )
}
