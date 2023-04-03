packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urp

import com.twittelonr.pagelons.relonndelonr.{thriftscala => urp}
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.ClielonntelonvelonntInfoMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.TopicPagelonNavBar
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TopicPagelonNavBarMarshallelonr @Injelonct() (
  clielonntelonvelonntInfoMarshallelonr: ClielonntelonvelonntInfoMarshallelonr) {

  delonf apply(topicPagelonNavBar: TopicPagelonNavBar): urp.TopicPagelonNavBar =
    urp.TopicPagelonNavBar(
      topicId = topicPagelonNavBar.topicId,
      clielonntelonvelonntInfo = topicPagelonNavBar.clielonntelonvelonntInfo.map(clielonntelonvelonntInfoMarshallelonr(_))
    )
}
