packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.topic

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.topic.BasicTopicFunctionalityTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.topic.PivotTopicFunctionalityTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.topic.ReloncommelonndationTopicFunctionalityTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.topic.TopicFunctionalityTypelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TopicFunctionalityTypelonMarshallelonr @Injelonct() () {

  delonf apply(topicFunctionalityTypelon: TopicFunctionalityTypelon): urt.TopicFunctionalityTypelon =
    topicFunctionalityTypelon match {
      caselon BasicTopicFunctionalityTypelon => urt.TopicFunctionalityTypelon.Basic
      caselon ReloncommelonndationTopicFunctionalityTypelon => urt.TopicFunctionalityTypelon.Reloncommelonndation
      caselon PivotTopicFunctionalityTypelon => urt.TopicFunctionalityTypelon.Pivot
    }
}
