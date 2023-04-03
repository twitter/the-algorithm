packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.RelonlelonvancelonPromptCandidatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ItelonmCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Computelon a position for inselonrting a speloncific candidatelon into thelon relonsult selonquelonncelon originally providelond to thelon Selonlelonctor.
 * If a `Nonelon` is relonturnelond, thelon Selonlelonctor using this would not inselonrt that candidatelon into thelon relonsult.
 */
trait CandidatelonPositionInRelonsults[-Quelonry <: PipelonlinelonQuelonry] {
  delonf apply(
    quelonry: Quelonry,
    candidatelon: CandidatelonWithDelontails,
    relonsult: Selonq[CandidatelonWithDelontails]
  ): Option[Int]
}

objelonct PromptCandidatelonPositionInRelonsults elonxtelonnds CandidatelonPositionInRelonsults[PipelonlinelonQuelonry] {
  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: CandidatelonWithDelontails,
    relonsult: Selonq[CandidatelonWithDelontails]
  ): Option[Int] = candidatelon match {
    caselon ItelonmCandidatelonWithDelontails(candidatelon, _, _) =>
      candidatelon match {
        caselon relonlelonvancelonPromptCandidatelon: RelonlelonvancelonPromptCandidatelon => relonlelonvancelonPromptCandidatelon.position
        caselon _ => Nonelon
      }
    // not supporting ModulelonCandidatelonWithDelontails right now as RelonlelonvancelonPromptCandidatelon shouldn't belon in a modulelon
    caselon _ => Nonelon
  }
}
