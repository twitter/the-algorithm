packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.opelonration

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.CursorItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class CursorItelonmMarshallelonr @Injelonct() (
  cursorTypelonMarshallelonr: CursorTypelonMarshallelonr,
  cursorDisplayTrelonatmelonntMarshallelonr: CursorDisplayTrelonatmelonntMarshallelonr) {

  delonf apply(cursorItelonm: CursorItelonm): urt.TimelonlinelonItelonmContelonnt.TimelonlinelonCursor =
    urt.TimelonlinelonItelonmContelonnt.TimelonlinelonCursor(
      urt.TimelonlinelonCursor(
        valuelon = cursorItelonm.valuelon,
        cursorTypelon = cursorTypelonMarshallelonr(cursorItelonm.cursorTypelon),
        displayTrelonatmelonnt = cursorItelonm.displayTrelonatmelonnt.map(cursorDisplayTrelonatmelonntMarshallelonr(_))
      )
    )
}
