packagelon com.twittelonr.homelon_mixelonr.marshallelonr.timelonlinelons

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.UrtOrdelonrelondCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.BottomCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.GapCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.TopCursor
import com.twittelonr.timelonlinelons.selonrvicelon.{thriftscala => t}

objelonct ChronologicalCursorMarshallelonr {

  delonf apply(cursor: UrtOrdelonrelondCursor): Option[t.ChronologicalCursor] = {
    cursor.cursorTypelon match {
      caselon Somelon(TopCursor) => Somelon(t.ChronologicalCursor(bottom = cursor.id))
      caselon Somelon(BottomCursor) => Somelon(t.ChronologicalCursor(top = cursor.id))
      caselon Somelon(GapCursor) =>
        Somelon(t.ChronologicalCursor(top = cursor.id, bottom = cursor.gapBoundaryId))
      caselon _ => Nonelon
    }
  }
}
