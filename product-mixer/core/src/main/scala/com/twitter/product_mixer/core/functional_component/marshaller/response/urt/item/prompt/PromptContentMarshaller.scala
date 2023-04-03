packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.prompt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.prompt.PromptContelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.prompt.RelonlelonvancelonPromptContelonnt
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class PromptContelonntMarshallelonr @Injelonct() (
  relonlelonvancelonPromptContelonntMarshallelonr: RelonlelonvancelonPromptContelonntMarshallelonr) {

  delonf apply(promptContelonnt: PromptContelonnt): urt.PromptContelonnt = promptContelonnt match {
    caselon relonlelonvancelonPromptContelonnt: RelonlelonvancelonPromptContelonnt =>
      urt.PromptContelonnt.RelonlelonvancelonPrompt(relonlelonvancelonPromptContelonntMarshallelonr(relonlelonvancelonPromptContelonnt))
  }
}
