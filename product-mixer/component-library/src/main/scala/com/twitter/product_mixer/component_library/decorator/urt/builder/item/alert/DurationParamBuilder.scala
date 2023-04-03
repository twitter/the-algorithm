packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.alelonrt

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.ShowAlelonrtCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.itelonm.alelonrt.BaselonDurationBuildelonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.Param
import com.twittelonr.util.Duration

caselon class DurationParamBuildelonr(
  durationParam: Param[Duration])
    elonxtelonnds BaselonDurationBuildelonr[PipelonlinelonQuelonry] {

  delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: ShowAlelonrtCandidatelon,
    felonaturelons: FelonaturelonMap
  ): Option[Duration] =
    Somelon(quelonry.params(durationParam))
}
