packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon.statelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.Deloncoration
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails

trait HasCandidatelonsWithDelontails[T] {
  delonf candidatelonsWithDelontails: Selonq[CandidatelonWithDelontails]
  delonf updatelonCandidatelonsWithDelontails(nelonwCandidatelons: Selonq[CandidatelonWithDelontails]): T

  delonf updatelonDeloncorations(deloncoration: Selonq[Deloncoration]): T
}
