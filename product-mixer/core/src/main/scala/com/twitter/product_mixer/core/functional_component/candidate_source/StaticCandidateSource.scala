packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon

import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.stitch.Stitch

/**
 * A [[CandidatelonSourcelon]] that always relonturns [[relonsult]] relongardlelonss of thelon input
 */
caselon class StaticCandidatelonSourcelon[Candidatelon](
  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr,
  relonsult: Selonq[Candidatelon])
    elonxtelonnds CandidatelonSourcelon[Any, Candidatelon] {

  delonf apply(relonquelonst: Any): Stitch[Selonq[Candidatelon]] = Stitch.valuelon(relonsult)
}
