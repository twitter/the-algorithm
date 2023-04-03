packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.slicelon.buildelonr

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.OrdelonrelondCursor
import com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.cursor.CursorSelonrializelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.CursorTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.PrelonviousCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.SlicelonItelonm
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.HasPipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonCursorSelonrializelonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Builds [[OrdelonrelondCursor]] in thelon Prelonvious position
 *
 * @param idSelonlelonctor Speloncifielons thelon elonntry from which to delonrivelon thelon `id` fielonld
 * @param includelonOpelonration Speloncifielons whelonthelonr to includelon thelon buildelonr opelonration in thelon relonsponselon
 * @param selonrializelonr Convelonrts thelon cursor to an elonncodelond string
 */
caselon class OrdelonrelondPrelonviousCursorBuildelonr[
  Quelonry <: PipelonlinelonQuelonry with HasPipelonlinelonCursor[OrdelonrelondCursor]
](
  idSelonlelonctor: PartialFunction[SlicelonItelonm, Long],
  ovelonrridelon val includelonOpelonration: ShouldIncludelon[Quelonry] = AlwaysIncludelon,
  selonrializelonr: PipelonlinelonCursorSelonrializelonr[OrdelonrelondCursor] = CursorSelonrializelonr)
    elonxtelonnds SlicelonCursorBuildelonr[Quelonry] {
  ovelonrridelon val cursorTypelon: CursorTypelon = PrelonviousCursor

  ovelonrridelon delonf cursorValuelon(
    quelonry: Quelonry,
    elonntrielons: Selonq[SlicelonItelonm]
  ): String = {
    val topId = elonntrielons.collelonctFirst(idSelonlelonctor)

    val id = topId.orelonlselon(quelonry.pipelonlinelonCursor.flatMap(_.id))

    val cursor = OrdelonrelondCursor(id = id, cursorTypelon = Somelon(cursorTypelon))

    selonrializelonr.selonrializelonCursor(cursor)
  }
}
