packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.topic

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.topic.BasicTopicDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.topic.NoIconTopicDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.topic.PillTopicDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.topic.PillWithoutActionIconDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.topic.TopicDisplayTypelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TopicDisplayTypelonMarshallelonr @Injelonct() () {

  delonf apply(topicDisplayTypelon: TopicDisplayTypelon): urt.TopicDisplayTypelon = topicDisplayTypelon match {
    caselon BasicTopicDisplayTypelon => urt.TopicDisplayTypelon.Basic
    caselon PillTopicDisplayTypelon => urt.TopicDisplayTypelon.Pill
    caselon NoIconTopicDisplayTypelon => urt.TopicDisplayTypelon.NoIcon
    caselon PillWithoutActionIconDisplayTypelon => urt.TopicDisplayTypelon.PillWithoutActionIcon
  }
}
