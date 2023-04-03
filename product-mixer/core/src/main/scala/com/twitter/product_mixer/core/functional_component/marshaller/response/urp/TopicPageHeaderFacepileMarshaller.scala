packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urp

import com.twittelonr.pagelons.relonndelonr.{thriftscala => urp}
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.UrlMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.TopicPagelonHelonadelonrFacelonpilelon
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TopicPagelonHelonadelonrFacelonpilelonMarshallelonr @Injelonct() (
  urlMarshallelonr: UrlMarshallelonr) {

  delonf apply(topicPagelonHelonadelonrFacelonpilelon: TopicPagelonHelonadelonrFacelonpilelon): urp.TopicPagelonHelonadelonrFacelonpilelon =
    urp.TopicPagelonHelonadelonrFacelonpilelon(
      uselonrIds = topicPagelonHelonadelonrFacelonpilelon.uselonrIds,
      facelonpilelonUrl = topicPagelonHelonadelonrFacelonpilelon.facelonpilelonUrl.map(urlMarshallelonr(_))
    )
}
