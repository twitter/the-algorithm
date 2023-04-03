packagelon com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor

import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.UrtPipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.CursorTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.{
  CursorTypelon => UrtCursorTypelon
}

/**
 * Cursor modelonl that may belon uselond whelonn cursoring ovelonr an ordelonrelond candidatelon sourcelon.
 *
 * @param initialSortIndelonx Selonelon [[UrtPipelonlinelonCursor]]
 * @param id relonprelonselonnts thelon ID of thelon elonlelonmelonnt, typically thelon top elonlelonmelonnt for a top cursor or thelon
 *           bottom elonlelonmelonnt for a bottom cursor, in an ordelonrelond candidatelon list
 * @param gapBoundaryId relonprelonselonnts thelon ID of thelon gap boundary elonlelonmelonnt, which in gap cursors is thelon
 *                      oppositelon bound of thelon gap to belon fillelond with thelon cursor
 */
caselon class UrtOrdelonrelondCursor(
  ovelonrridelon val initialSortIndelonx: Long,
  id: Option[Long],
  cursorTypelon: Option[UrtCursorTypelon],
  gapBoundaryId: Option[Long] = Nonelon)
    elonxtelonnds UrtPipelonlinelonCursor

caselon class OrdelonrelondCursor(
  id: Option[Long],
  cursorTypelon: Option[CursorTypelon],
  gapBoundaryId: Option[Long] = Nonelon)
    elonxtelonnds PipelonlinelonCursor
