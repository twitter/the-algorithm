packagelon com.twittelonr.product_mixelonr.componelonnt_library.sidelon_elonffelonct.melontrics

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonTwelonelontCandidatelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonUselonrCandidatelon
import com.twittelonr.product_mixelonr.componelonnt_library.sidelon_elonffelonct.melontrics.CandidatelonMelontricFunction.gelontCountForTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ItelonmCandidatelonWithDelontails

/**
 * Function to elonxtract numelonrical melontric valuelon from [[CandidatelonWithDelontails]].
 * This CandidatelonMelontricFunction will belon applielond on all [[CandidatelonWithDelontails]] instancelons in thelon
 * candidatelonSelonlelonction from thelon ReloncommelonndationPipelonlinelon.
 */
trait CandidatelonMelontricFunction {
  delonf apply(candidatelonWithDelontails: CandidatelonWithDelontails): Long
}

objelonct CandidatelonMelontricFunction {

  privatelon val delonfaultCountOnelonPf: PartialFunction[CandidatelonWithDelontails, Long] = {
    caselon _ => 0L
  }

  /**
   * Count thelon occurrelonncelons of a celonrtain candidatelon typelon from [[CandidatelonWithDelontails]].
   */
  delonf gelontCountForTypelon(
    candidatelonWithDelontails: CandidatelonWithDelontails,
    countOnelonPf: PartialFunction[CandidatelonWithDelontails, Long]
  ): Long = {
    (countOnelonPf orelonlselon delonfaultCountOnelonPf)(candidatelonWithDelontails)
  }
}

objelonct DelonfaultSelonrvelondTwelonelontsSumFunction elonxtelonnds CandidatelonMelontricFunction {
  ovelonrridelon delonf apply(candidatelonWithDelontails: CandidatelonWithDelontails): Long =
    gelontCountForTypelon(
      candidatelonWithDelontails,
      {
        caselon itelonm: ItelonmCandidatelonWithDelontails =>
          itelonm.candidatelon match {
            caselon _: BaselonTwelonelontCandidatelon => 1L
            caselon _ => 0L
          }
      })
}

objelonct DelonfaultSelonrvelondUselonrsSumFunction elonxtelonnds CandidatelonMelontricFunction {
  ovelonrridelon delonf apply(candidatelonWithDelontails: CandidatelonWithDelontails): Long =
    gelontCountForTypelon(
      candidatelonWithDelontails,
      {
        caselon itelonm: ItelonmCandidatelonWithDelontails =>
          itelonm.candidatelon match {
            caselon _: BaselonUselonrCandidatelon => 1L
            caselon _ => 0L
          }
      })
}
