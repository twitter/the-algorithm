packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urp

import com.twittelonr.pagelons.relonndelonr.{thriftscala => urp}
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.ClielonntelonvelonntInfoMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.TopicPagelonHelonadelonr
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TopicPagelonHelonadelonrMarshallelonr @Injelonct() (
  topicPagelonHelonadelonrFacelonpilelonMarshallelonr: TopicPagelonHelonadelonrFacelonpilelonMarshallelonr,
  clielonntelonvelonntInfoMarshallelonr: ClielonntelonvelonntInfoMarshallelonr,
  topicPagelonHelonadelonrDisplayTypelonMarshallelonr: TopicPagelonHelonadelonrDisplayTypelonMarshallelonr) {

  delonf apply(topicPagelonHelonadelonr: TopicPagelonHelonadelonr): urp.TopicPagelonHelonadelonr =
    urp.TopicPagelonHelonadelonr(
      topicId = topicPagelonHelonadelonr.topicId,
      facelonpilelon = topicPagelonHelonadelonr.facelonpilelon.map(topicPagelonHelonadelonrFacelonpilelonMarshallelonr(_)),
      clielonntelonvelonntInfo = topicPagelonHelonadelonr.clielonntelonvelonntInfo.map(clielonntelonvelonntInfoMarshallelonr(_)),
      landingContelonxt = topicPagelonHelonadelonr.landingContelonxt,
      displayTypelon = topicPagelonHelonadelonr.displayTypelon
        .map(topicPagelonHelonadelonrDisplayTypelonMarshallelonr(_)).gelontOrelonlselon(
          urp.TopicPagelonHelonadelonrDisplayTypelon.Basic)
    )
}
