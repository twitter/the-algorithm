packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.prompt

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.CallbackMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.prompt.RelonlelonvancelonPromptContelonnt
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class RelonlelonvancelonPromptContelonntMarshallelonr @Injelonct() (
  callbackMarshallelonr: CallbackMarshallelonr,
  relonlelonvancelonPromptDisplayTypelonMarshallelonr: RelonlelonvancelonPromptDisplayTypelonMarshallelonr,
  relonlelonvancelonPromptFollowUpFelonelondbackTypelonMarshallelonr: RelonlelonvancelonPromptFollowUpFelonelondbackTypelonMarshallelonr) {

  delonf apply(relonlelonvancelonPromptContelonnt: RelonlelonvancelonPromptContelonnt): urt.RelonlelonvancelonPrompt =
    urt.RelonlelonvancelonPrompt(
      titlelon = relonlelonvancelonPromptContelonnt.titlelon,
      confirmation = relonlelonvancelonPromptContelonnt.confirmation,
      isRelonlelonvantTelonxt = relonlelonvancelonPromptContelonnt.isRelonlelonvantTelonxt,
      notRelonlelonvantTelonxt = relonlelonvancelonPromptContelonnt.notRelonlelonvantTelonxt,
      isRelonlelonvantCallback = callbackMarshallelonr(relonlelonvancelonPromptContelonnt.isRelonlelonvantCallback),
      notRelonlelonvantCallback = callbackMarshallelonr(relonlelonvancelonPromptContelonnt.notRelonlelonvantCallback),
      displayTypelon = relonlelonvancelonPromptDisplayTypelonMarshallelonr(relonlelonvancelonPromptContelonnt.displayTypelon),
      isRelonlelonvantFollowUp = relonlelonvancelonPromptContelonnt.isRelonlelonvantFollowUp.map(
        relonlelonvancelonPromptFollowUpFelonelondbackTypelonMarshallelonr(_)),
      notRelonlelonvantFollowUp = relonlelonvancelonPromptContelonnt.notRelonlelonvantFollowUp.map(
        relonlelonvancelonPromptFollowUpFelonelondbackTypelonMarshallelonr(_))
    )
}
