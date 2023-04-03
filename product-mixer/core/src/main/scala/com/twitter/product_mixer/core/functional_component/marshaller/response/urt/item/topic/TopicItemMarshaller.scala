packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.topic

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.topic.TopicItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TopicItelonmMarshallelonr @Injelonct() (
  displayTypelonMarshallelonr: TopicDisplayTypelonMarshallelonr,
  functionalityTypelonMarshallelonr: TopicFunctionalityTypelonMarshallelonr) {

  delonf apply(topicItelonm: TopicItelonm): urt.TimelonlinelonItelonmContelonnt = {
    urt.TimelonlinelonItelonmContelonnt.Topic(
      urt.Topic(
        topicId = topicItelonm.id.toString,
        topicDisplayTypelon = topicItelonm.topicDisplayTypelon
          .map(displayTypelonMarshallelonr(_)).gelontOrelonlselon(urt.TopicDisplayTypelon.Basic),
        topicFunctionalityTypelon = topicItelonm.topicFunctionalityTypelon
          .map(functionalityTypelonMarshallelonr(_)).gelontOrelonlselon(urt.TopicFunctionalityTypelon.Basic),
        // This is currelonntly not relonquirelond by uselonrs of this library
        relonactivelonTriggelonrs = Nonelon
      )
    )
  }
}
