packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.UrtPlacelonholdelonrCursor
import com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.cursor.UrtCursorSelonrializelonr
import com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr.PlacelonholdelonrTopCursorBuildelonr.DelonfaultPlacelonholdelonrCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.CursorTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.TopCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.HasPipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonCursorSelonrializelonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.UrtPipelonlinelonCursor

objelonct PlacelonholdelonrTopCursorBuildelonr {
  val DelonfaultPlacelonholdelonrCursor = UrtPlacelonholdelonrCursor()
}

/**
 * Top cursor buildelonr that can belon uselond whelonn thelon Product doelons not support paging up. Thelon URT spelonc
 * relonquirelons that both bottom and top cursors always belon prelonselonnt on elonach pagelon. Thelonrelonforelon, if thelon
 * product doelons not support paging up, thelonn welon can uselon a cursor valuelon that is not delonselonrializablelon.
 * This way if thelon clielonnt submits a TopCursor, thelon backelonnd will trelonat thelon thelon relonquelonst as if no
 * cursor was submittelond.
 */
caselon class PlacelonholdelonrTopCursorBuildelonr(
  selonrializelonr: PipelonlinelonCursorSelonrializelonr[UrtPipelonlinelonCursor] = UrtCursorSelonrializelonr)
    elonxtelonnds UrtCursorBuildelonr[PipelonlinelonQuelonry with HasPipelonlinelonCursor[UrtPipelonlinelonCursor]] {
  ovelonrridelon val cursorTypelon: CursorTypelon = TopCursor

  ovelonrridelon delonf cursorValuelon(
    quelonry: PipelonlinelonQuelonry with HasPipelonlinelonCursor[UrtPipelonlinelonCursor],
    timelonlinelonelonntrielons: Selonq[Timelonlinelonelonntry]
  ): String = selonrializelonr.selonrializelonCursor(DelonfaultPlacelonholdelonrCursor)
}
