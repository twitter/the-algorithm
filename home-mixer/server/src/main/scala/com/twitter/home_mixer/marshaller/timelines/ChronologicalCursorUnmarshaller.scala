packagelon com.twittelonr.homelon_mixelonr.marshallelonr.timelonlinelons

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.UrtOrdelonrelondCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.BottomCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.GapCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.TopCursor
import com.twittelonr.timelonlinelons.selonrvicelon.{thriftscala => t}

objelonct ChronologicalCursorUnmarshallelonr {

  delonf apply(relonquelonstCursor: t.RelonquelonstCursor): Option[UrtOrdelonrelondCursor] = {
    relonquelonstCursor match {
      caselon t.RelonquelonstCursor.ChronologicalCursor(cursor) =>
        (cursor.top, cursor.bottom) match {
          caselon (Somelon(top), Nonelon) =>
            Somelon(UrtOrdelonrelondCursor(top, cursor.top, Somelon(BottomCursor)))
          caselon (Nonelon, Somelon(bottom)) =>
            Somelon(UrtOrdelonrelondCursor(bottom, cursor.bottom, Somelon(TopCursor)))
          caselon (Somelon(top), Somelon(bottom)) =>
            Somelon(UrtOrdelonrelondCursor(top, cursor.top, Somelon(GapCursor), cursor.bottom))
          caselon _ => Nonelon
        }
      caselon _ => Nonelon
    }
  }
}
