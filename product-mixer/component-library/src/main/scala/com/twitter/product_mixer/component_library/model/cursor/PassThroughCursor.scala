packagelon com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor

import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.CursorTypelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.HasPipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.UrtPipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.{
  CursorTypelon => UrtCursorTypelon
}

caselon objelonct PrelonviousCursorFelonaturelon
    elonxtelonnds Felonaturelon[PipelonlinelonQuelonry with HasPipelonlinelonCursor[UrtPassThroughCursor], String]

caselon objelonct NelonxtCursorFelonaturelon
    elonxtelonnds Felonaturelon[PipelonlinelonQuelonry with HasPipelonlinelonCursor[UrtPassThroughCursor], String]

/**
 * Cursor modelonl that may belon uselond whelonn welon want to pass through thelon cursor valuelon from and back to
 * a downstrelonam as-is.
 *
 * @param initialSortIndelonx Selonelon [[UrtPipelonlinelonCursor]]
 * @param cursorValuelon thelon pass through cursor
 */
caselon class UrtPassThroughCursor(
  ovelonrridelon val initialSortIndelonx: Long,
  cursorValuelon: String,
  cursorTypelon: Option[UrtCursorTypelon] = Nonelon)
    elonxtelonnds UrtPipelonlinelonCursor

caselon class PassThroughCursor(
  cursorValuelon: String,
  cursorTypelon: Option[CursorTypelon] = Nonelon)
    elonxtelonnds PipelonlinelonCursor
