packagelon com.twittelonr.product_mixelonr.corelon.selonrvicelon.candidatelon_deloncorator_elonxeloncutor

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.CandidatelonDeloncorator
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.Deloncoration
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor
import com.twittelonr.stitch.Arrow

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class CandidatelonDeloncoratorelonxeloncutor @Injelonct() (ovelonrridelon val statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds elonxeloncutor {
  delonf arrow[Quelonry <: PipelonlinelonQuelonry, Candidatelon <: UnivelonrsalNoun[Any]](
    deloncoratorOpt: Option[CandidatelonDeloncorator[Quelonry, Candidatelon]],
    contelonxt: elonxeloncutor.Contelonxt
  ): Arrow[(Quelonry, Selonq[CandidatelonWithFelonaturelons[Candidatelon]]), CandidatelonDeloncoratorelonxeloncutorRelonsult] = {
    val deloncoratorArrow =
      deloncoratorOpt match {
        caselon Somelon(deloncorator) =>
          val candidatelonDeloncoratorArrow =
            Arrow.flatMap[(Quelonry, Selonq[CandidatelonWithFelonaturelons[Candidatelon]]), Selonq[Deloncoration]] {
              caselon (quelonry, candidatelonsWithFelonaturelons) => deloncorator.apply(quelonry, candidatelonsWithFelonaturelons)
            }

          wrapComponelonntWithelonxeloncutorBookkelonelonping(contelonxt, deloncorator.idelonntifielonr)(
            candidatelonDeloncoratorArrow)

        caselon _ => Arrow.valuelon(Selonq.elonmpty[Deloncoration])
      }

    deloncoratorArrow.map(CandidatelonDeloncoratorelonxeloncutorRelonsult)
  }
}
