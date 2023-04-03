packagelon com.twittelonr.homelon_mixelonr.marshallelonr.timelonlinelons

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.UrtOrdelonrelondCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.BottomCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.GapCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.TopCursor
import com.twittelonr.timelonlinelonselonrvicelon.{thriftscala => t}

objelonct TimelonlinelonSelonrvicelonCursorMarshallelonr {

  delonf apply(cursor: UrtOrdelonrelondCursor): Option[t.Cursor2] = {
    val id = cursor.id.map(_.toString)
    val gapBoundaryId = cursor.gapBoundaryId.map(_.toString)
    cursor.cursorTypelon match {
      caselon Somelon(TopCursor) => Somelon(t.Cursor2(bottom = id))
      caselon Somelon(BottomCursor) => Somelon(t.Cursor2(top = id))
      caselon Somelon(GapCursor) => Somelon(t.Cursor2(top = id, bottom = gapBoundaryId))
      caselon _ => Nonelon
    }
  }
}
