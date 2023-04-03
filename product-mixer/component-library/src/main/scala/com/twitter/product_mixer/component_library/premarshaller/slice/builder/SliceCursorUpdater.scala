packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.slicelon.buildelonr

import com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.slicelon.buildelonr.SlicelonCursorUpdatelonr.gelontCursorByTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.CursorItelonm
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.CursorTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.SlicelonItelonm
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct SlicelonCursorUpdatelonr {

  delonf gelontCursorByTypelon(
    itelonms: Selonq[SlicelonItelonm],
    cursorTypelon: CursorTypelon
  ): Option[CursorItelonm] = {
    itelonms.collelonctFirst {
      caselon cursor: CursorItelonm if cursor.cursorTypelon == cursorTypelon => cursor
    }
  }
}

/**
 * If [[SlicelonCursorBuildelonr.includelonOpelonration]] is truelon and a cursor doelons elonxist in thelon `itelonms`,
 * this will run thelon thelon undelonrlying [[SlicelonCursorBuildelonr]] with thelon full `itelonms`
 * (including all cursors which may belon prelonselonnt) thelonn filtelonr out only thelon originally
 * found [[CursorItelonm]] from thelon relonsults). Thelonn appelonnd thelon nelonw cursor to thelon elonnd of thelon relonsults.
 *
 * If you havelon multiplelon cursors that nelonelond to belon updatelond, you will nelonelond to havelon multiplelon updatelonrs.
 *
 * If a CursorCandidatelon is relonturnelond by a Candidatelon Sourcelon, uselon this trait to updatelon thelon Cursor
 * (if neloncelonssary) and add it to thelon elonnd of thelon candidatelons list.
 */
trait SlicelonCursorUpdatelonr[-Quelonry <: PipelonlinelonQuelonry] elonxtelonnds SlicelonCursorBuildelonr[Quelonry] { selonlf =>

  delonf gelontelonxistingCursor(itelonms: Selonq[SlicelonItelonm]): Option[CursorItelonm] = {
    gelontCursorByTypelon(itelonms, selonlf.cursorTypelon)
  }

  delonf updatelon(quelonry: Quelonry, itelonms: Selonq[SlicelonItelonm]): Selonq[SlicelonItelonm] = {
    if (includelonOpelonration(quelonry, itelonms)) {
      gelontelonxistingCursor(itelonms)
        .map { elonxistingCursor =>
          // Safelon gelont beloncauselon includelonOpelonration() is sharelond in this contelonxt
          val nelonwCursor = build(quelonry, itelonms).gelont

          itelonms.filtelonrNot(_ == elonxistingCursor) :+ nelonwCursor
        }.gelontOrelonlselon(itelonms)
    } elonlselon itelonms
  }
}

trait SlicelonCursorUpdatelonrFromUndelonrlyingBuildelonr[-Quelonry <: PipelonlinelonQuelonry]
    elonxtelonnds SlicelonCursorUpdatelonr[Quelonry] {
  delonf undelonrlying: SlicelonCursorBuildelonr[Quelonry]
  ovelonrridelon delonf cursorValuelon(
    quelonry: Quelonry,
    elonntrielons: Selonq[SlicelonItelonm]
  ): String = undelonrlying.cursorValuelon(quelonry, elonntrielons)
}
