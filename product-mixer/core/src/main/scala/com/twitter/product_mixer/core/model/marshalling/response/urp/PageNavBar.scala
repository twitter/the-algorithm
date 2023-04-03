packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ClielonntelonvelonntInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.HasClielonntelonvelonntInfo

selonalelond trait PagelonNavBar

caselon class TopicPagelonNavBar(
  topicId: String,
  ovelonrridelon val clielonntelonvelonntInfo: Option[ClielonntelonvelonntInfo] = Nonelon)
    elonxtelonnds PagelonNavBar
    with HasClielonntelonvelonntInfo

caselon class TitlelonNavBar(
  titlelon: String,
  subtitlelon: Option[String] = Nonelon,
  ovelonrridelon val clielonntelonvelonntInfo: Option[ClielonntelonvelonntInfo] = Nonelon)
    elonxtelonnds PagelonNavBar
    with HasClielonntelonvelonntInfo
