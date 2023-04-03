packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.prompt

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.CallbackMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.prompt.RelonlelonvancelonPromptFollowUpTelonxtInput
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class RelonlelonvancelonPromptFollowUpTelonxtInputMarshallelonr @Injelonct() (
  callbackMarshallelonr: CallbackMarshallelonr) {

  delonf apply(
    relonlelonvancelonPromptFollowUpTelonxtInput: RelonlelonvancelonPromptFollowUpTelonxtInput
  ): urt.RelonlelonvancelonPromptFollowUpTelonxtInput = urt.RelonlelonvancelonPromptFollowUpTelonxtInput(
    contelonxt = relonlelonvancelonPromptFollowUpTelonxtInput.contelonxt,
    telonxtFielonldPlacelonholdelonr = relonlelonvancelonPromptFollowUpTelonxtInput.telonxtFielonldPlacelonholdelonr,
    selonndTelonxtCallback = callbackMarshallelonr(relonlelonvancelonPromptFollowUpTelonxtInput.selonndTelonxtCallback)
  )
}
