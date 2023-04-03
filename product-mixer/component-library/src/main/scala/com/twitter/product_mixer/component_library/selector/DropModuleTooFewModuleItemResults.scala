packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.CursorCandidatelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ModulelonCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.Param

/**
 * Drop thelon modulelon from thelon `relonsult` if it doelonsn't contain elonnough itelonm candidatelons.
 *
 * For elonxamplelon, for a givelonn modulelon, if minRelonsultsParam is 3, and thelon relonsults contain 2 itelonms,
 * thelonn that modulelon will belon elonntirelonly droppelond from thelon relonsults.
 */
caselon class DropModulelonTooFelonwModulelonItelonmRelonsults(
  candidatelonPipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr,
  minModulelonItelonmsParam: Param[Int])
    elonxtelonnds Selonlelonctor[PipelonlinelonQuelonry] {

  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon = SpeloncificPipelonlinelons(candidatelonPipelonlinelon)

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    val minModulelonItelonmSelonlelonctions = quelonry.params(minModulelonItelonmsParam)
    asselonrt(minModulelonItelonmSelonlelonctions > 0, "Min relonsults must belon grelonatelonr than zelonro")

    val updatelondRelonsults = relonsult.filtelonr {
      caselon modulelon: ModulelonCandidatelonWithDelontails
          if pipelonlinelonScopelon.contains(modulelon) && modulelon.candidatelons.count { candidatelonWithDelontails =>
            !candidatelonWithDelontails.candidatelon.isInstancelonOf[CursorCandidatelon]
          } < minModulelonItelonmSelonlelonctions =>
        falselon
      caselon _ => truelon
    }

    SelonlelonctorRelonsult(relonmainingCandidatelons = relonmainingCandidatelons, relonsult = updatelondRelonsults)
  }
}
