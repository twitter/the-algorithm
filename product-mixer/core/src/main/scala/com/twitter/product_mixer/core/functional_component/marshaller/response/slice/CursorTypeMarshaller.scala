packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.slicelon

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import com.twittelonr.product_mixelonr.componelonnt_library.{thriftscala => t}
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.NelonxtCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.PrelonviousCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.CursorTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.GapCursor

@Singlelonton
class CursorTypelonMarshallelonr @Injelonct() () {

  delonf apply(cursorTypelon: CursorTypelon): t.CursorTypelon = cursorTypelon match {
    caselon NelonxtCursor => t.CursorTypelon.Nelonxt
    caselon PrelonviousCursor => t.CursorTypelon.Prelonvious
    caselon GapCursor => t.CursorTypelon.Gap
  }

  delonf unmarshall(cursorTypelon: t.CursorTypelon): CursorTypelon = cursorTypelon match {
    caselon t.CursorTypelon.Nelonxt => NelonxtCursor
    caselon t.CursorTypelon.Prelonvious => PrelonviousCursor
    caselon t.CursorTypelon.Gap => GapCursor
    caselon t.CursorTypelon.elonnumUnknownCursorTypelon(id) =>
      throw nelonw UnsupportelondOpelonrationelonxcelonption(
        s"Attelonmptelond to unmarshall unreloncognizelond cursor typelon: $id")
  }

}
