packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.itelonm.alelonrt

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.ShowAlelonrtCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.util.Duration

trait BaselonDurationBuildelonr[-Quelonry <: PipelonlinelonQuelonry] {

  delonf apply(quelonry: Quelonry, candidatelon: ShowAlelonrtCandidatelon, felonaturelons: FelonaturelonMap): Option[Duration]
}
