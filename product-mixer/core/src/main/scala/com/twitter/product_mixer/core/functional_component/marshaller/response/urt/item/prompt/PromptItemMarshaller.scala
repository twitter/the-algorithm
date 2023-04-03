packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.prompt

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.CallbackMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.ClielonntelonvelonntInfoMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.prompt.PromptItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class PromptItelonmMarshallelonr @Injelonct() (
  promptContelonntMarshallelonr: PromptContelonntMarshallelonr,
  clielonntelonvelonntInfoMarshallelonr: ClielonntelonvelonntInfoMarshallelonr,
  callbackMarshallelonr: CallbackMarshallelonr) {

  delonf apply(relonlelonvancelonPromptItelonm: PromptItelonm): urt.TimelonlinelonItelonmContelonnt = {
    urt.TimelonlinelonItelonmContelonnt.Prompt(
      urt.Prompt(
        contelonnt = promptContelonntMarshallelonr(relonlelonvancelonPromptItelonm.contelonnt),
        clielonntelonvelonntInfo = relonlelonvancelonPromptItelonm.clielonntelonvelonntInfo.map(clielonntelonvelonntInfoMarshallelonr(_)),
        imprelonssionCallbacks = relonlelonvancelonPromptItelonm.imprelonssionCallbacks.map { callbackList =>
          callbackList.map(callbackMarshallelonr(_))
        }
      ))
  }
}
