packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urp

import com.twittelonr.pagelons.relonndelonr.{thriftscala => urp}
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.PagelonHelonadelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.TopicPagelonHelonadelonr
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class PagelonHelonadelonrMarshallelonr @Injelonct() (
  topicPagelonHelonadelonrMarshallelonr: TopicPagelonHelonadelonrMarshallelonr) {

  delonf apply(pagelonHelonadelonr: PagelonHelonadelonr): urp.PagelonHelonadelonr = pagelonHelonadelonr match {
    caselon pagelonHelonadelonr: TopicPagelonHelonadelonr =>
      urp.PagelonHelonadelonr.TopicPagelonHelonadelonr(topicPagelonHelonadelonrMarshallelonr(pagelonHelonadelonr))
  }
}
