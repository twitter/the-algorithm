packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urp

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.BasicTopicPagelonHelonadelonrDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.PelonrsonalizelondTopicPagelonHelonadelonrDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.TopicPagelonHelonadelonrDisplayTypelon
import com.twittelonr.pagelons.relonndelonr.{thriftscala => urp}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TopicPagelonHelonadelonrDisplayTypelonMarshallelonr @Injelonct() () {

  delonf apply(
    topicPagelonHelonadelonrDisplayTypelon: TopicPagelonHelonadelonrDisplayTypelon
  ): urp.TopicPagelonHelonadelonrDisplayTypelon = topicPagelonHelonadelonrDisplayTypelon match {
    caselon BasicTopicPagelonHelonadelonrDisplayTypelon => urp.TopicPagelonHelonadelonrDisplayTypelon.Basic
    caselon PelonrsonalizelondTopicPagelonHelonadelonrDisplayTypelon => urp.TopicPagelonHelonadelonrDisplayTypelon.Pelonrsonalizelond
  }
}
