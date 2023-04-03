packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.icon_labelonl

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.icon.HorizonIcon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ClielonntelonvelonntInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackActionInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.elonntryNamelonspacelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonItelonm

objelonct IconLabelonlItelonm {
  val IconLabelonlelonntryNamelonspacelon = elonntryNamelonspacelon("iconlabelonl")
}

caselon class IconLabelonlItelonm(
  ovelonrridelon val id: String,
  ovelonrridelon val sortIndelonx: Option[Long],
  ovelonrridelon val clielonntelonvelonntInfo: Option[ClielonntelonvelonntInfo],
  ovelonrridelon val felonelondbackActionInfo: Option[FelonelondbackActionInfo],
  telonxt: RichTelonxt,
  icon: Option[HorizonIcon])
    elonxtelonnds TimelonlinelonItelonm {
  ovelonrridelon val elonntryNamelonspacelon: elonntryNamelonspacelon = IconLabelonlItelonm.IconLabelonlelonntryNamelonspacelon

  ovelonrridelon delonf withSortIndelonx(sortIndelonx: Long): Timelonlinelonelonntry = copy(sortIndelonx = Somelon(sortIndelonx))
}
