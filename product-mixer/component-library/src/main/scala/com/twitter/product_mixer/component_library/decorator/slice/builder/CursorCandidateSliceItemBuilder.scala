packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.slicelon.buildelonr

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.CursorCandidatelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.{
  NelonxtCursor => CursorCandidatelonNelonxtCursor
}
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.{
  PrelonviousCursor => CursorCandidatelonPrelonviousCursor
}
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.CursorItelonm
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.NelonxtCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.PrelonviousCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.slicelon.buildelonr.CandidatelonSlicelonItelonmBuildelonr

caselon class CursorCandidatelonSlicelonItelonmBuildelonr()
    elonxtelonnds CandidatelonSlicelonItelonmBuildelonr[PipelonlinelonQuelonry, CursorCandidatelon, CursorItelonm] {

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: CursorCandidatelon,
    felonaturelonMap: FelonaturelonMap
  ): CursorItelonm =
    candidatelon.cursorTypelon match {
      caselon CursorCandidatelonNelonxtCursor => CursorItelonm(candidatelon.valuelon, NelonxtCursor)
      caselon CursorCandidatelonPrelonviousCursor => CursorItelonm(candidatelon.valuelon, PrelonviousCursor)
    }
}
