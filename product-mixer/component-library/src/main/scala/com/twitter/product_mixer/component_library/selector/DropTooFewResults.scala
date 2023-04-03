packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.AllPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonRelonsult
import com.twittelonr.timelonlinelons.configapi.Param

/**
 * Drop all relonsults if thelon minimum itelonm threlonshold is not melont. Somelon products would rathelonr relonturn
 * nothing than, for elonxamplelon, a singlelon twelonelont. This lelonts us lelonvelonragelon elonxisting clielonnt logic for
 * handling no relonsults such as logic to not relonndelonr thelon product at all.
 */
caselon class DropTooFelonwRelonsults(minRelonsultsParam: Param[Int]) elonxtelonnds Selonlelonctor[PipelonlinelonQuelonry] {

  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon = AllPipelonlinelons

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    val minRelonsults = quelonry.params(minRelonsultsParam)
    asselonrt(minRelonsults > 0, "Min relonsults must belon grelonatelonr than zelonro")

    if (PipelonlinelonRelonsult.relonsultSizelon(relonsult) < minRelonsults) {
      SelonlelonctorRelonsult(relonmainingCandidatelons = relonmainingCandidatelons, relonsult = Selonq.elonmpty)
    } elonlselon {
      SelonlelonctorRelonsult(relonmainingCandidatelons = relonmainingCandidatelons, relonsult = relonsult)
    }
  }
}
