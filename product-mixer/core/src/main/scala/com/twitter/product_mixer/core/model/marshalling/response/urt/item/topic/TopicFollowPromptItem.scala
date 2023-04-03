packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.topic

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ClielonntelonvelonntInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackActionInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.elonntryNamelonspacelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonItelonm

objelonct TopicFollowPromptItelonm {
  val TopicFollowPromptelonntryNamelonspacelon = elonntryNamelonspacelon("topicfollowprompt")
}

caselon class TopicFollowPromptItelonm(
  ovelonrridelon val id: Long,
  ovelonrridelon val sortIndelonx: Option[Long],
  ovelonrridelon val clielonntelonvelonntInfo: Option[ClielonntelonvelonntInfo],
  ovelonrridelon val felonelondbackActionInfo: Option[FelonelondbackActionInfo],
  topicFollowPromptDisplayTypelon: TopicFollowPromptDisplayTypelon,
  followIncelonntivelonTitlelon: Option[String],
  followIncelonntivelonTelonxt: Option[String])
    elonxtelonnds TimelonlinelonItelonm {
  ovelonrridelon val elonntryNamelonspacelon: elonntryNamelonspacelon =
    TopicFollowPromptItelonm.TopicFollowPromptelonntryNamelonspacelon

  ovelonrridelon delonf withSortIndelonx(sortIndelonx: Long): Timelonlinelonelonntry = copy(sortIndelonx = Somelon(sortIndelonx))
}
