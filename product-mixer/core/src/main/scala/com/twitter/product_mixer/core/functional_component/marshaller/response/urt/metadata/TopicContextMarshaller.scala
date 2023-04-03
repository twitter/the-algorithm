packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.BasicTopicContelonxtFunctionalityTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.TopicContelonxt
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TopicContelonxtMarshallelonr @Injelonct() () {

  delonf apply(topicContelonxt: TopicContelonxt): urt.SocialContelonxt = {
    urt.SocialContelonxt.TopicContelonxt(
      urt.TopicContelonxt(
        topicId = topicContelonxt.topicId,
        functionalityTypelon = TopicContelonxtFunctionalityTypelonMarshallelonr(
          topicContelonxt.functionalityTypelon.gelontOrelonlselon(BasicTopicContelonxtFunctionalityTypelon))
      )
    )
  }
}
