packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.opelonration

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.CursorOpelonration
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class CursorOpelonrationMarshallelonr @Injelonct() (
  cursorTypelonMarshallelonr: CursorTypelonMarshallelonr,
  cursorDisplayTrelonatmelonntMarshallelonr: CursorDisplayTrelonatmelonntMarshallelonr) {

  delonf apply(cursorOpelonration: CursorOpelonration): urt.TimelonlinelonOpelonration.Cursor =
    urt.TimelonlinelonOpelonration.Cursor(
      urt.TimelonlinelonCursor(
        valuelon = cursorOpelonration.valuelon,
        cursorTypelon = cursorTypelonMarshallelonr(cursorOpelonration.cursorTypelon),
        displayTrelonatmelonnt = cursorOpelonration.displayTrelonatmelonnt.map(cursorDisplayTrelonatmelonntMarshallelonr(_))
      )
    )
}
