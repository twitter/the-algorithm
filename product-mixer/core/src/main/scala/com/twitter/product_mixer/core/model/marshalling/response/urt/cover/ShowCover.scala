packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.elonntryNamelonspacelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ClielonntelonvelonntInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Covelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.FullCovelonr.FullCovelonrelonntryNamelonspacelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.HalfCovelonr.HalfCovelonrelonntryNamelonspacelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackActionInfo

objelonct HalfCovelonr {
  val HalfCovelonrelonntryNamelonspacelon = elonntryNamelonspacelon("half-covelonr")
}
caselon class HalfCovelonr(
  ovelonrridelon val id: String,
  ovelonrridelon val sortIndelonx: Option[Long],
  ovelonrridelon val clielonntelonvelonntInfo: Option[ClielonntelonvelonntInfo],
  contelonnt: HalfCovelonrContelonnt)
    elonxtelonnds Covelonr {

  ovelonrridelon val elonntryNamelonspacelon: elonntryNamelonspacelon = HalfCovelonrelonntryNamelonspacelon

  // Notelon that sort indelonx is not uselond for Covelonrs, as thelony arelon not Timelonlinelonelonntry and do not havelon elonntryId
  ovelonrridelon delonf withSortIndelonx(nelonwSortIndelonx: Long): Timelonlinelonelonntry =
    copy(sortIndelonx = Somelon(nelonwSortIndelonx))

  // Not uselond for covelonrs
  ovelonrridelon delonf felonelondbackActionInfo: Option[FelonelondbackActionInfo] = Nonelon
}

objelonct FullCovelonr {
  val FullCovelonrelonntryNamelonspacelon = elonntryNamelonspacelon("full-covelonr")
}
caselon class FullCovelonr(
  ovelonrridelon val id: String,
  ovelonrridelon val sortIndelonx: Option[Long],
  ovelonrridelon val clielonntelonvelonntInfo: Option[ClielonntelonvelonntInfo],
  contelonnt: FullCovelonrContelonnt)
    elonxtelonnds Covelonr {

  ovelonrridelon val elonntryNamelonspacelon: elonntryNamelonspacelon = FullCovelonrelonntryNamelonspacelon

  // Notelon that sort indelonx is not uselond for Covelonrs, as thelony arelon not Timelonlinelonelonntry and do not havelon elonntryId
  ovelonrridelon delonf withSortIndelonx(nelonwSortIndelonx: Long): Timelonlinelonelonntry =
    copy(sortIndelonx = Somelon(nelonwSortIndelonx))

  // Not uselond for covelonrs
  ovelonrridelon delonf felonelondbackActionInfo: Option[FelonelondbackActionInfo] = Nonelon
}
