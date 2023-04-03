packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urp

import com.twittelonr.pagelons.relonndelonr.{thriftscala => urp}
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.PagelonNavBar
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.TopicPagelonNavBar
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.TitlelonNavBar
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class PagelonNavBarMarshallelonr @Injelonct() (
  topicPagelonNavBarMarshallelonr: TopicPagelonNavBarMarshallelonr,
  titlelonNavBarMarshallelonr: TitlelonNavBarMarshallelonr) {

  delonf apply(pagelonNavBar: PagelonNavBar): urp.PagelonNavBar = pagelonNavBar match {
    caselon pagelonNavBar: TopicPagelonNavBar =>
      urp.PagelonNavBar.TopicPagelonNavBar(topicPagelonNavBarMarshallelonr(pagelonNavBar))
    caselon pagelonNavBar: TitlelonNavBar =>
      urp.PagelonNavBar.TitlelonNavBar(titlelonNavBarMarshallelonr(pagelonNavBar))
  }
}
