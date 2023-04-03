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
 * Updatelons an [[OrdelonrelondCursor]] in thelon Prelonvious position
 *
 * @param idSelonlelonctor Speloncifielons thelon elonntry from which to delonrivelon thelon `id` fielonld
 * @param includelonOpelonration Speloncifielons whelonthelonr to includelon thelon buildelonr opelonration in thelon relonsponselon
 * @param selonrializelonr Convelonrts thelon cursor to an elonncodelond string
 */
caselon class OrdelonrelondPrelonviousCursorUpdatelonr[
  Quelonry <: PipelonlinelonQuelonry with HasPipelonlinelonCursor[OrdelonrelondCursor]
](
  idSelonlelonctor: PartialFunction[SlicelonItelonm, Long],
  ovelonrridelon val includelonOpelonration: ShouldIncludelon[Quelonry] = AlwaysIncludelon,
  selonrializelonr: PipelonlinelonCursorSelonrializelonr[OrdelonrelondCursor] = CursorSelonrializelonr)
    elonxtelonnds SlicelonCursorUpdatelonrFromUndelonrlyingBuildelonr[Quelonry] {
  ovelonrridelon val cursorTypelon: CursorTypelon = PrelonviousCursor

  ovelonrridelon val undelonrlying: OrdelonrelondPrelonviousCursorBuildelonr[Quelonry] =
    OrdelonrelondPrelonviousCursorBuildelonr(idSelonlelonctor, includelonOpelonration, selonrializelonr)
}
