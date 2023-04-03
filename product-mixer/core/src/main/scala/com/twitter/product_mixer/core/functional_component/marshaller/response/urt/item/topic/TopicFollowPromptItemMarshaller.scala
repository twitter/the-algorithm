packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.topic

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.topic.TopicFollowPromptItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TopicFollowPromptItelonmMarshallelonr @Injelonct() (
  displayTypelonMarshallelonr: TopicFollowPromptDisplayTypelonMarshallelonr) {

  delonf apply(topicFollowPromptItelonm: TopicFollowPromptItelonm): urt.TimelonlinelonItelonmContelonnt = {
    urt.TimelonlinelonItelonmContelonnt.TopicFollowPrompt(
      urt.TopicFollowPrompt(
        topicId = topicFollowPromptItelonm.id.toString,
        displayTypelon = displayTypelonMarshallelonr(topicFollowPromptItelonm.topicFollowPromptDisplayTypelon),
        followIncelonntivelonTitlelon = topicFollowPromptItelonm.followIncelonntivelonTitlelon,
        followIncelonntivelonTelonxt = topicFollowPromptItelonm.followIncelonntivelonTelonxt
      )
    )
  }
}
