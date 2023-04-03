packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.prompt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.prompt._
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class RelonlelonvancelonPromptFollowUpFelonelondbackTypelonMarshallelonr @Injelonct() (
  relonlelonvancelonPromptFollowUpTelonxtInputMarshallelonr: RelonlelonvancelonPromptFollowUpTelonxtInputMarshallelonr) {

  delonf apply(
    relonlelonvancelonPromptFollowUpFelonelondbackTypelon: RelonlelonvancelonPromptFollowUpFelonelondbackTypelon
  ): urt.RelonlelonvancelonPromptFollowUpFelonelondbackTypelon = relonlelonvancelonPromptFollowUpFelonelondbackTypelon match {
    caselon relonlelonvancelonPromptFollowUpTelonxtInput: RelonlelonvancelonPromptFollowUpTelonxtInput =>
      urt.RelonlelonvancelonPromptFollowUpFelonelondbackTypelon.FollowUpTelonxtInput(
        relonlelonvancelonPromptFollowUpTelonxtInputMarshallelonr(relonlelonvancelonPromptFollowUpTelonxtInput))
  }
}
