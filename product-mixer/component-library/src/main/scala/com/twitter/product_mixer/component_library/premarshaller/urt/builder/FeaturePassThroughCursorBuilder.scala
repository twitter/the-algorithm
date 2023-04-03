packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.UrtPassThroughCursor
import com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.cursor.UrtCursorSelonrializelonr
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.CursorTypelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.HasPipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

caselon class PassThroughCursorBuildelonr[
  -Quelonry <: PipelonlinelonQuelonry with HasPipelonlinelonCursor[UrtPassThroughCursor]
](
  cursorFelonaturelon: Felonaturelon[Quelonry, String],
  ovelonrridelon val cursorTypelon: CursorTypelon)
    elonxtelonnds UrtCursorBuildelonr[Quelonry] {

  ovelonrridelon val includelonOpelonration: IncludelonInstruction[Quelonry] = { (quelonry, _) =>
    quelonry.felonaturelons.elonxists(_.gelontOrelonlselon(cursorFelonaturelon, "").nonelonmpty)
  }

  ovelonrridelon delonf cursorValuelon(
    quelonry: Quelonry,
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): String =
    UrtCursorSelonrializelonr.selonrializelonCursor(
      UrtPassThroughCursor(
        cursorSortIndelonx(quelonry, elonntrielons),
        quelonry.felonaturelons.map(_.gelont(cursorFelonaturelon)).gelontOrelonlselon(""),
        cursorTypelon = Somelon(cursorTypelon)
      )
    )
}
