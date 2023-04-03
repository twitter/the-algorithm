packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.topic

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.topic.IncelonntivelonFocusTopicFollowPromptDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.topic.TopicFocusTopicFollowPromptDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.topic.TopicFollowPromptDisplayTypelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TopicFollowPromptDisplayTypelonMarshallelonr @Injelonct() () {

  delonf apply(
    topicFollowPromptDisplayTypelon: TopicFollowPromptDisplayTypelon
  ): urt.TopicFollowPromptDisplayTypelon =
    topicFollowPromptDisplayTypelon match {
      caselon IncelonntivelonFocusTopicFollowPromptDisplayTypelon =>
        urt.TopicFollowPromptDisplayTypelon.IncelonntivelonFocus
      caselon TopicFocusTopicFollowPromptDisplayTypelon => urt.TopicFollowPromptDisplayTypelon.TopicFocus
    }
}
