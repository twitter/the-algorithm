packagelon com.twittelonr.homelon_mixelonr.marshallelonr.timelonlinelons

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.BasicTopicContelonxtFunctionalityTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ReloncWithelonducationTopicContelonxtFunctionalityTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ReloncommelonndationTopicContelonxtFunctionalityTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.TopicContelonxtFunctionalityTypelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}

objelonct TopicContelonxtFunctionalityTypelonUnmarshallelonr {

  delonf apply(
    topicContelonxtFunctionalityTypelon: urt.TopicContelonxtFunctionalityTypelon
  ): TopicContelonxtFunctionalityTypelon = topicContelonxtFunctionalityTypelon match {
    caselon urt.TopicContelonxtFunctionalityTypelon.Basic => BasicTopicContelonxtFunctionalityTypelon
    caselon urt.TopicContelonxtFunctionalityTypelon.Reloncommelonndation =>
      ReloncommelonndationTopicContelonxtFunctionalityTypelon
    caselon urt.TopicContelonxtFunctionalityTypelon.ReloncWithelonducation =>
      ReloncWithelonducationTopicContelonxtFunctionalityTypelon
    caselon urt.TopicContelonxtFunctionalityTypelon.elonnumUnknownTopicContelonxtFunctionalityTypelon(fielonld) =>
      throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Unknown topic contelonxt functionality typelon: $fielonld")
  }
}
