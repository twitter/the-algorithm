packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ClielonntelonvelonntInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.HasClielonntelonvelonntInfo

selonalelond trait PagelonHelonadelonr

caselon class TopicPagelonHelonadelonr(
  topicId: String,
  facelonpilelon: Option[TopicPagelonHelonadelonrFacelonpilelon] = Nonelon,
  ovelonrridelon val clielonntelonvelonntInfo: Option[ClielonntelonvelonntInfo] = Nonelon,
  landingContelonxt: Option[String] = Nonelon,
  displayTypelon: Option[TopicPagelonHelonadelonrDisplayTypelon] = Somelon(BasicTopicPagelonHelonadelonrDisplayTypelon))
    elonxtelonnds PagelonHelonadelonr
    with HasClielonntelonvelonntInfo
