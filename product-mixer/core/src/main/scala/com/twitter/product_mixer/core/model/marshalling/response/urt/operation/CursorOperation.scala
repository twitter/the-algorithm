packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.CursorOpelonration.CursorelonntryNamelonspacelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.elonntryNamelonspacelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonOpelonration

objelonct CursorOpelonration {
  val CursorelonntryNamelonspacelon = elonntryNamelonspacelon("cursor")

  privatelon delonf elonntryIdelonntifielonr(cursorTypelon: CursorTypelon, idelonntifielonr: Long): String =
    s"$CursorelonntryNamelonspacelon-${cursorTypelon.elonntryNamelonspacelon.toString}-$idelonntifielonr"
}

caselon class CursorOpelonration(
  ovelonrridelon val id: Long,
  ovelonrridelon val sortIndelonx: Option[Long],
  valuelon: String,
  cursorTypelon: CursorTypelon,
  displayTrelonatmelonnt: Option[CursorDisplayTrelonatmelonnt],
  idToRelonplacelon: Option[Long])
    elonxtelonnds TimelonlinelonOpelonration {
  ovelonrridelon val elonntryNamelonspacelon: elonntryNamelonspacelon = CursorelonntryNamelonspacelon

  ovelonrridelon lazy val elonntryIdelonntifielonr: String = CursorOpelonration.elonntryIdelonntifielonr(cursorTypelon, id)

  ovelonrridelon delonf elonntryIdToRelonplacelon: Option[String] =
    idToRelonplacelon.map(CursorOpelonration.elonntryIdelonntifielonr(cursorTypelon, _))

  ovelonrridelon delonf withSortIndelonx(sortIndelonx: Long): Timelonlinelonelonntry = copy(sortIndelonx = Somelon(sortIndelonx))
}
