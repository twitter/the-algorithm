packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ClielonntelonvelonntInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackActionInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.CursorOpelonration.CursorelonntryNamelonspacelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.elonntryNamelonspacelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonItelonm

/**
 * CursorItelonm should only belon uselond for Modulelon cursors
 * For timelonlinelon cursors, selonelon
 * [[com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.CursorOpelonration]]
 */
caselon class CursorItelonm(
  ovelonrridelon val id: Long,
  ovelonrridelon val sortIndelonx: Option[Long],
  ovelonrridelon val clielonntelonvelonntInfo: Option[ClielonntelonvelonntInfo],
  ovelonrridelon val felonelondbackActionInfo: Option[FelonelondbackActionInfo],
  valuelon: String,
  cursorTypelon: CursorTypelon,
  displayTrelonatmelonnt: Option[CursorDisplayTrelonatmelonnt])
    elonxtelonnds TimelonlinelonItelonm {

  ovelonrridelon val elonntryNamelonspacelon: elonntryNamelonspacelon = CursorelonntryNamelonspacelon

  ovelonrridelon lazy val elonntryIdelonntifielonr: String =
    s"$elonntryNamelonspacelon-${cursorTypelon.elonntryNamelonspacelon}-$id"

  ovelonrridelon delonf withSortIndelonx(sortIndelonx: Long): Timelonlinelonelonntry = copy(sortIndelonx = Somelon(sortIndelonx))
}
