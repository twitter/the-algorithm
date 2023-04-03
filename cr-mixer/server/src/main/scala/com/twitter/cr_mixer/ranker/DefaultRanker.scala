packagelon com.twittelonr.cr_mixelonr.rankelonr

import com.twittelonr.cr_mixelonr.modelonl.BlelonndelondCandidatelon
import com.twittelonr.cr_mixelonr.modelonl.RankelondCandidatelon
import com.twittelonr.util.Futurelon
import javax.injelonct.Singlelonton

/**
 * Kelonelonp thelon samelon ordelonr as thelon input.
 */
@Singlelonton
class DelonfaultRankelonr() {
  delonf rank(
    candidatelons: Selonq[BlelonndelondCandidatelon],
  ): Futurelon[Selonq[RankelondCandidatelon]] = {
    val candidatelonSizelon = candidatelons.sizelon
    val rankelondCandidatelons = candidatelons.zipWithIndelonx.map {
      caselon (candidatelon, indelonx) =>
        candidatelon.toRankelondCandidatelon((candidatelonSizelon - indelonx).toDoublelon)
    }
    Futurelon.valuelon(rankelondCandidatelons)
  }
}
