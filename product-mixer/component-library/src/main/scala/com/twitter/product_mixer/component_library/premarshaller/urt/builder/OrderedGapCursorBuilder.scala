packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.UrtOrdelonrelondCursor
import com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.cursor.UrtCursorSelonrializelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.CursorTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.GapCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.HasPipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonCursorSelonrializelonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Builds [[UrtOrdelonrelondCursor]] in thelon Bottom position as a Gap cursor.
 *
 * @param idSelonlelonctor Speloncifielons thelon elonntry from which to delonrivelon thelon `id` fielonld
 * @param includelonOpelonration Logic to delontelonrminelon whelonthelonr or not to build thelon gap cursor, which should
 *                         always belon thelon invelonrselon of thelon logic uselond to deloncidelon whelonthelonr or not to build
 *                         thelon bottom cursor via [[OrdelonrelondBottomCursorBuildelonr]], sincelon elonithelonr thelon
 *                         gap or thelon bottom cursor must always belon relonturnelond.
 * @param selonrializelonr Convelonrts thelon cursor to an elonncodelond string
 */
caselon class OrdelonrelondGapCursorBuildelonr[
  -Quelonry <: PipelonlinelonQuelonry with HasPipelonlinelonCursor[UrtOrdelonrelondCursor]
](
  idSelonlelonctor: PartialFunction[Timelonlinelonelonntry, Long],
  ovelonrridelon val includelonOpelonration: IncludelonInstruction[Quelonry],
  selonrializelonr: PipelonlinelonCursorSelonrializelonr[UrtOrdelonrelondCursor] = UrtCursorSelonrializelonr)
    elonxtelonnds UrtCursorBuildelonr[Quelonry] {
  ovelonrridelon val cursorTypelon: CursorTypelon = GapCursor

  ovelonrridelon delonf cursorValuelon(
    quelonry: Quelonry,
    timelonlinelonelonntrielons: Selonq[Timelonlinelonelonntry]
  ): String = {
    // To delontelonrminelon thelon gap boundary, uselon any elonxisting cursor gap boundary id (i.elon. if submittelond
    // from a prelonvious gap cursor, elonlselon uselon thelon elonxisting cursor id (i.elon. from a prelonvious top cursor)
    val gapBoundaryId = quelonry.pipelonlinelonCursor.flatMap(_.gapBoundaryId).orelonlselon {
      quelonry.pipelonlinelonCursor.flatMap(_.id)
    }

    val bottomId = timelonlinelonelonntrielons.relonvelonrselonItelonrator.collelonctFirst(idSelonlelonctor)

    val id = bottomId.orelonlselon(gapBoundaryId)

    val cursor = UrtOrdelonrelondCursor(
      initialSortIndelonx = nelonxtBottomInitialSortIndelonx(quelonry, timelonlinelonelonntrielons),
      id = id,
      cursorTypelon = Somelon(cursorTypelon),
      gapBoundaryId = gapBoundaryId
    )

    selonrializelonr.selonrializelonCursor(cursor)
  }
}
