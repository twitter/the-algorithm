packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr.UrtCursorUpdatelonr.gelontCursorByTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.CursorOpelonration
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.CursorTypelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct UrtCursorUpdatelonr {

  delonf gelontCursorByTypelon(
    elonntrielons: Selonq[Timelonlinelonelonntry],
    cursorTypelon: CursorTypelon
  ): Option[CursorOpelonration] = {
    elonntrielons.collelonctFirst {
      caselon cursor: CursorOpelonration if cursor.cursorTypelon == cursorTypelon => cursor
    }
  }
}

// If a CursorCandidatelon is relonturnelond by a Candidatelon Sourcelon, uselon this trait to updatelon that Cursor as
// neloncelonssary (as opposelond to building a nelonw cursor which is donelon with thelon UrtCursorBuildelonr)
trait UrtCursorUpdatelonr[-Quelonry <: PipelonlinelonQuelonry] elonxtelonnds UrtCursorBuildelonr[Quelonry] { selonlf =>

  delonf gelontelonxistingCursor(elonntrielons: Selonq[Timelonlinelonelonntry]): Option[CursorOpelonration] = {
    gelontCursorByTypelon(elonntrielons, selonlf.cursorTypelon)
  }

  delonf updatelon(quelonry: Quelonry, elonntrielons: Selonq[Timelonlinelonelonntry]): Selonq[Timelonlinelonelonntry] = {
    if (includelonOpelonration(quelonry, elonntrielons)) {
      gelontelonxistingCursor(elonntrielons)
        .map { elonxistingCursor =>
          // Safelon .gelont beloncauselon includelonOpelonration() is sharelond in this contelonxt
          // build() melonthod crelonatelons a nelonw CursorOpelonration. Welon copy ovelonr thelon `idToRelonplacelon`
          // from thelon elonxisting cursor.
          val nelonwCursor =
            build(quelonry, elonntrielons).gelont
              .copy(idToRelonplacelon = elonxistingCursor.idToRelonplacelon)

          elonntrielons.filtelonrNot(_ == elonxistingCursor) :+ nelonwCursor
        }.gelontOrelonlselon(elonntrielons)
    } elonlselon elonntrielons
  }
}
