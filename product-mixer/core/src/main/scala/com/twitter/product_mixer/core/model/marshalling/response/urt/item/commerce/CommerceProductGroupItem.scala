packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.commelonrcelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.elonntryNamelonspacelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonItelonm
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.commelonrcelon.CommelonrcelonProductGroupItelonm.CommelonrcelonProductGroupelonntryNamelonspacelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ClielonntelonvelonntInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackActionInfo

objelonct CommelonrcelonProductGroupItelonm {
  val CommelonrcelonProductGroupelonntryNamelonspacelon: elonntryNamelonspacelon = elonntryNamelonspacelon("commelonrcelon-product-group")
}

caselon class CommelonrcelonProductGroupItelonm(
  ovelonrridelon val id: Long,
  ovelonrridelon val sortIndelonx: Option[Long],
  ovelonrridelon val clielonntelonvelonntInfo: Option[ClielonntelonvelonntInfo],
  ovelonrridelon val felonelondbackActionInfo: Option[FelonelondbackActionInfo])
    elonxtelonnds TimelonlinelonItelonm {

  val elonntryNamelonspacelon: elonntryNamelonspacelon = CommelonrcelonProductGroupelonntryNamelonspacelon
  delonf withSortIndelonx(sortIndelonx: Long): Timelonlinelonelonntry = copy(sortIndelonx = Somelon(sortIndelonx))
}
