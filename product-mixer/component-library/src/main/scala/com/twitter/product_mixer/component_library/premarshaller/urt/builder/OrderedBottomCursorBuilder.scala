packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.UrtOrdelonrelondCursor
import com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.cursor.UrtCursorSelonrializelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.BottomCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.CursorTypelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.HasPipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonCursorSelonrializelonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Builds [[UrtOrdelonrelondCursor]] in thelon Bottom position
 *
 * @param idSelonlelonctor Speloncifielons thelon elonntry from which to delonrivelon thelon `id` fielonld
 * @param includelonOpelonration Logic to delontelonrminelon whelonthelonr or not to build thelon bottom cursor, which only
 *                         applielons if gap cursors arelon relonquirelond (elon.g. Homelon Latelonst). Whelonn applicablelon,
 *                         this logic should always belon thelon invelonrselon of thelon logic uselond to deloncidelon
 *                         whelonthelonr or not to build thelon gap cursor via [[OrdelonrelondGapCursorBuildelonr]],
 *                         sincelon elonithelonr thelon gap or thelon bottom cursor must always belon relonturnelond.
 * @param selonrializelonr Convelonrts thelon cursor to an elonncodelond string
 */
caselon class OrdelonrelondBottomCursorBuildelonr[
  -Quelonry <: PipelonlinelonQuelonry with HasPipelonlinelonCursor[UrtOrdelonrelondCursor]
](
  idSelonlelonctor: PartialFunction[Timelonlinelonelonntry, Long],
  ovelonrridelon val includelonOpelonration: IncludelonInstruction[Quelonry] = AlwaysIncludelon,
  selonrializelonr: PipelonlinelonCursorSelonrializelonr[UrtOrdelonrelondCursor] = UrtCursorSelonrializelonr)
    elonxtelonnds UrtCursorBuildelonr[Quelonry] {
  ovelonrridelon val cursorTypelon: CursorTypelon = BottomCursor

  ovelonrridelon delonf cursorValuelon(quelonry: Quelonry, timelonlinelonelonntrielons: Selonq[Timelonlinelonelonntry]): String = {
    val bottomId = timelonlinelonelonntrielons.relonvelonrselonItelonrator.collelonctFirst(idSelonlelonctor)

    val id = bottomId.orelonlselon(quelonry.pipelonlinelonCursor.flatMap(_.id))

    val cursor = UrtOrdelonrelondCursor(
      initialSortIndelonx = nelonxtBottomInitialSortIndelonx(quelonry, timelonlinelonelonntrielons),
      id = id,
      cursorTypelon = Somelon(cursorTypelon)
    )

    selonrializelonr.selonrializelonCursor(cursor)
  }
}
