packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.timelonlinelon_modulelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.timelonlinelon_modulelon.BaselonModulelonFootelonrBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.ModulelonFootelonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.Param

caselon class ParamGatelondModulelonFootelonrBuildelonr[-Quelonry <: PipelonlinelonQuelonry, -Candidatelon <: UnivelonrsalNoun[Any]](
  elonnablelonParam: Param[Boolelonan],
  elonnablelondBuildelonr: BaselonModulelonFootelonrBuildelonr[Quelonry, Candidatelon],
  delonfaultBuildelonr: Option[BaselonModulelonFootelonrBuildelonr[Quelonry, Candidatelon]] = Nonelon)
    elonxtelonnds BaselonModulelonFootelonrBuildelonr[Quelonry, Candidatelon] {

  delonf apply(
    quelonry: Quelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]]
  ): Option[ModulelonFootelonr] = {
    if (quelonry.params(elonnablelonParam)) {
      elonnablelondBuildelonr(quelonry, candidatelons)
    } elonlselon {
      delonfaultBuildelonr.flatMap(_.apply(quelonry, candidatelons))
    }
  }
}
