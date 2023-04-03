packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.opelonration

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.CursorCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.CandidatelonUrtelonntryBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.CursorDisplayTrelonatmelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.CursorOpelonration
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.CursorTypelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

caselon class CursorCandidatelonUrtOpelonrationBuildelonr[-Quelonry <: PipelonlinelonQuelonry](
  cursorTypelon: CursorTypelon,
  displayTrelonatmelonnt: Option[CursorDisplayTrelonatmelonnt] = Nonelon,
  idToRelonplacelon: Option[Long] = Nonelon)
    elonxtelonnds CandidatelonUrtelonntryBuildelonr[Quelonry, CursorCandidatelon, CursorOpelonration] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    cursorCandidatelon: CursorCandidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): CursorOpelonration = CursorOpelonration(
    id = cursorCandidatelon.id,
    sortIndelonx = Nonelon, // Sort indelonxelons arelon automatically selont in thelon domain marshallelonr phaselon
    valuelon = cursorCandidatelon.valuelon,
    cursorTypelon = cursorTypelon,
    displayTrelonatmelonnt = displayTrelonatmelonnt,
    idToRelonplacelon = idToRelonplacelon
  )
}
