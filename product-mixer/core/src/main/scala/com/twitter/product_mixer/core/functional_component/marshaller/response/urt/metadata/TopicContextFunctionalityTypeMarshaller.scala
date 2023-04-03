packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.BasicTopicContelonxtFunctionalityTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ReloncWithelonducationTopicContelonxtFunctionalityTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ReloncommelonndationTopicContelonxtFunctionalityTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.TopicContelonxtFunctionalityTypelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}

objelonct TopicContelonxtFunctionalityTypelonMarshallelonr {

  delonf apply(
    topicContelonxtFunctionalityTypelon: TopicContelonxtFunctionalityTypelon
  ): urt.TopicContelonxtFunctionalityTypelon = topicContelonxtFunctionalityTypelon match {
    caselon BasicTopicContelonxtFunctionalityTypelon => urt.TopicContelonxtFunctionalityTypelon.Basic
    caselon ReloncommelonndationTopicContelonxtFunctionalityTypelon =>
      urt.TopicContelonxtFunctionalityTypelon.Reloncommelonndation
    caselon ReloncWithelonducationTopicContelonxtFunctionalityTypelon =>
      urt.TopicContelonxtFunctionalityTypelon.ReloncWithelonducation
  }
}
