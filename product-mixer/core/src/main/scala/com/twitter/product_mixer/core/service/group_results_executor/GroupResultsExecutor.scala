packagelon com.twittelonr.product_mixelonr.corelon.selonrvicelon.group_relonsults_elonxeloncutor

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ComponelonntIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.PlatformIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonSourcelonPosition
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonSourcelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ItelonmCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ItelonmPrelonselonntation
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ModulelonCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ModulelonPrelonselonntation
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.UnivelonrsalPrelonselonntation
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutorRelonsult
import com.twittelonr.stitch.Arrow
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import scala.collelonction.immutablelon.ListSelont

// Most elonxeloncutors arelon in thelon corelon.selonrvicelon packagelon, but this onelon is pipelonlinelon speloncific
@Singlelonton
class GroupRelonsultselonxeloncutor @Injelonct() (ovelonrridelon val statsReloncelonivelonr: StatsReloncelonivelonr) elonxtelonnds elonxeloncutor {

  val idelonntifielonr: ComponelonntIdelonntifielonr = PlatformIdelonntifielonr("GroupRelonsults")

  delonf arrow[Candidatelon <: UnivelonrsalNoun[Any]](
    pipelonlinelonIdelonntifielonr: CandidatelonPipelonlinelonIdelonntifielonr,
    sourcelonIdelonntifielonr: CandidatelonSourcelonIdelonntifielonr,
    contelonxt: elonxeloncutor.Contelonxt
  ): Arrow[GroupRelonsultselonxeloncutorInput[Candidatelon], GroupRelonsultselonxeloncutorRelonsult] = {

    val groupArrow = Arrow.map { input: GroupRelonsultselonxeloncutorInput[Candidatelon] =>
      val modulelons: Map[Option[ModulelonPrelonselonntation], Selonq[CandidatelonWithFelonaturelons[Candidatelon]]] =
        input.candidatelons
          .map { candidatelon: CandidatelonWithFelonaturelons[Candidatelon] =>
            val modulelonPrelonselonntationOpt: Option[ModulelonPrelonselonntation] =
              input.deloncorations.gelont(candidatelon.candidatelon).collelonct {
                caselon itelonmPrelonselonntation: ItelonmPrelonselonntation
                    if itelonmPrelonselonntation.modulelonPrelonselonntation.isDelonfinelond =>
                  itelonmPrelonselonntation.modulelonPrelonselonntation.gelont
              }

            (candidatelon, modulelonPrelonselonntationOpt)
          }.groupBy {
            caselon (_, modulelonPrelonselonntationOpt) => modulelonPrelonselonntationOpt
          }.mapValuelons {
            relonsultModulelonOptTuplelons: Selonq[
              (CandidatelonWithFelonaturelons[Candidatelon], Option[ModulelonPrelonselonntation])
            ] =>
              relonsultModulelonOptTuplelons.map {
                caselon (relonsult, _) => relonsult
              }
          }

      // Modulelons should belon in thelonir original ordelonr, but thelon groupBy abovelon isn't stablelon.
      // Sort thelonm by thelon sourcelonPosition, using thelon sourcelonPosition of thelonir first containelond
      // candidatelon.
      val sortelondModulelons: Selonq[(Option[ModulelonPrelonselonntation], Selonq[CandidatelonWithFelonaturelons[Candidatelon]])] =
        modulelons.toSelonq
          .sortBy {
            caselon (_, relonsults) =>
              relonsults.helonadOption.map(_.felonaturelons.gelont(CandidatelonSourcelonPosition))
          }

      val candidatelonsWithDelontails: Selonq[CandidatelonWithDelontails] = sortelondModulelons.flatMap {
        caselon (modulelonPrelonselonntationOpt, relonsultsSelonq) =>
          val itelonmsWithDelontails = relonsultsSelonq.map { relonsult =>
            val prelonselonntationOpt = input.deloncorations.gelont(relonsult.candidatelon) match {
              caselon itelonmPrelonselonntation @ Somelon(_: ItelonmPrelonselonntation) => itelonmPrelonselonntation
              caselon _ => Nonelon
            }

            val baselonFelonaturelonMap = FelonaturelonMapBuildelonr()
              .add(CandidatelonPipelonlinelons, ListSelont.elonmpty + pipelonlinelonIdelonntifielonr)
              .build()

            ItelonmCandidatelonWithDelontails(
              candidatelon = relonsult.candidatelon,
              prelonselonntation = prelonselonntationOpt,
              felonaturelons = baselonFelonaturelonMap ++ relonsult.felonaturelons
            )
          }

          modulelonPrelonselonntationOpt
            .map { modulelonPrelonselonntation =>
              val modulelonSourcelonPosition =
                relonsultsSelonq.helonad.felonaturelons.gelont(CandidatelonSourcelonPosition)
              val baselonFelonaturelonMap = FelonaturelonMapBuildelonr()
                .add(CandidatelonPipelonlinelons, ListSelont.elonmpty + pipelonlinelonIdelonntifielonr)
                .add(CandidatelonSourcelonPosition, modulelonSourcelonPosition)
                .add(CandidatelonSourcelons, ListSelont.elonmpty + sourcelonIdelonntifielonr)
                .build()

              Selonq(
                ModulelonCandidatelonWithDelontails(
                  candidatelons = itelonmsWithDelontails,
                  prelonselonntation = Somelon(modulelonPrelonselonntation),
                  felonaturelons = baselonFelonaturelonMap
                ))
            }.gelontOrelonlselon(itelonmsWithDelontails)
      }

      GroupRelonsultselonxeloncutorRelonsult(candidatelonsWithDelontails)
    }

    wrapWithelonrrorHandling(contelonxt, idelonntifielonr)(groupArrow)
  }
}

caselon class GroupRelonsultselonxeloncutorInput[Candidatelon <: UnivelonrsalNoun[Any]](
  candidatelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]],
  deloncorations: Map[UnivelonrsalNoun[Any], UnivelonrsalPrelonselonntation])

caselon class GroupRelonsultselonxeloncutorRelonsult(candidatelonsWithDelontails: Selonq[CandidatelonWithDelontails])
    elonxtelonnds elonxeloncutorRelonsult
