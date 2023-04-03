packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.topic

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ClielonntelonvelonntInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackActionInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.elonntryNamelonspacelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonItelonm

objelonct TopicItelonm {
  val TopicelonntryNamelonspacelon = elonntryNamelonspacelon("topic")
}

caselon class TopicItelonm(
  ovelonrridelon val id: Long,
  ovelonrridelon val sortIndelonx: Option[Long],
  ovelonrridelon val clielonntelonvelonntInfo: Option[ClielonntelonvelonntInfo],
  ovelonrridelon val felonelondbackActionInfo: Option[FelonelondbackActionInfo],
  topicFunctionalityTypelon: Option[TopicFunctionalityTypelon],
  topicDisplayTypelon: Option[TopicDisplayTypelon])
    elonxtelonnds TimelonlinelonItelonm {
  ovelonrridelon val elonntryNamelonspacelon: elonntryNamelonspacelon = TopicItelonm.TopicelonntryNamelonspacelon

  ovelonrridelon delonf withSortIndelonx(sortIndelonx: Long): Timelonlinelonelonntry = copy(sortIndelonx = Somelon(sortIndelonx))
}
