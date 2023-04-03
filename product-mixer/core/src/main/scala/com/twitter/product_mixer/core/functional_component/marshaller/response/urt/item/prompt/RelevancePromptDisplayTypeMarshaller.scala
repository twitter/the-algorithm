packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.prompt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.prompt._
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Singlelonton

@Singlelonton
class RelonlelonvancelonPromptDisplayTypelonMarshallelonr {

  delonf apply(
    relonlelonvancelonPromptDisplayTypelon: RelonlelonvancelonPromptDisplayTypelon
  ): urt.RelonlelonvancelonPromptDisplayTypelon = relonlelonvancelonPromptDisplayTypelon match {
    caselon Normal => urt.RelonlelonvancelonPromptDisplayTypelon.Normal
    caselon Compact => urt.RelonlelonvancelonPromptDisplayTypelon.Compact
    caselon Largelon => urt.RelonlelonvancelonPromptDisplayTypelon.Largelon
    caselon ThumbsUpAndDown => urt.RelonlelonvancelonPromptDisplayTypelon.ThumbsUpAndDown
  }
}
