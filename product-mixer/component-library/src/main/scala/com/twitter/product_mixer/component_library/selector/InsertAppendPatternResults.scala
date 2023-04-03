packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import scala.collelonction.mutablelon

/**
 * Selonlelonct candidatelons and add thelonm according to thelon `pattelonrn`.
 * Thelon pattelonrn is relonpelonatelond until all candidatelons containelond in thelon pattelonrn arelon addelond to thelon `relonsult`.
 * If thelon candidatelons for a speloncific [[Buckelont]] in thelon pattelonrn arelon elonxhaustelond, that [[Buckelont]] will belon
 * skippelond on subselonquelonnt itelonrations.
 * If a candidatelon has a [[Buckelont]] that isn't in thelon pattelonrn it is addelond to thelon elonnd of thelon `relonsult`.
 * Thelon elonnd relonsult is all candidatelons from all [[candidatelonPipelonlinelons]]s providelond will elonnd up in thelon relonsult.
 *
 * @elonxamplelon If thelonrelon arelon no morelon candidatelons from a givelonn `CandidatelonPipelonlinelon` thelonn it is skippelond, so
 *          with thelon pattelonrn `Selonq(A, A, B, C)`, if thelonrelon arelon no morelon candidatelons from `B` thelonn it is
 *          elonffelonctivelonly thelon samelon as `Selonq(A, A, C)`. Thelon `relonsult` will contain all candidatelons from all
 *          `CandidatelonPipelonlinelon`s who's `Buckelont` is in thelon `pattelonrn`.
 *
 * @elonxamplelon If thelon pattelonrn is `Selonq(A, A, B, C)` and thelon relonmaining candidatelons
 *          from thelon providelond `candidatelonPipelonlinelons` arelon:
 *          - 5 `A`s
 *          - 2 `B`s
 *          - 1 `C`
 *          - 1 `D`s
 *
 *          thelonn thelon relonsulting output for elonach itelonration ovelonr thelon pattelonrn is
 *          - `Selonq(A, A, B, C)`
 *          - `Selonq(A, A, B)` sincelon thelonrelon's no morelon `C`s
 *          - `Selonq(A)` sincelon thelonrelon arelon no morelon `B`s or `C`s
 *          - `Selonq(D)` sincelon it wasn't in thelon pattelonrn but is from onelon of thelon providelond
 *            `candidatelonPipelonlinelons`, it's appelonndelond at thelon elonnd
 *
 *          so thelon `relonsult` that's relonturnelond would belon `Selonq(A, A, B, C, A, A, B, A, D)`
 */
caselon class InselonrtAppelonndPattelonrnRelonsults[-Quelonry <: PipelonlinelonQuelonry, Buckelont](
  candidatelonPipelonlinelons: Selont[CandidatelonPipelonlinelonIdelonntifielonr],
  buckelontelonr: Buckelontelonr[Buckelont],
  pattelonrn: Selonq[Buckelont])
    elonxtelonnds Selonlelonctor[Quelonry] {

  relonquirelon(pattelonrn.nonelonmpty, "`pattelonrn` must belon non-elonmpty")

  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon = SpeloncificPipelonlinelons(candidatelonPipelonlinelons)

  privatelon selonalelond trait PattelonrnRelonsult
  privatelon caselon objelonct NotASelonlelonctelondCandidatelonPipelonlinelon elonxtelonnds PattelonrnRelonsult
  privatelon caselon objelonct NotABuckelontInThelonPattelonrn elonxtelonnds PattelonrnRelonsult
  privatelon caselon class Buckelontelond(buckelont: Buckelont) elonxtelonnds PattelonrnRelonsult

  privatelon val allBuckelontsInPattelonrn = pattelonrn.toSelont

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    val groupelondCandidatelons: Map[PattelonrnRelonsult, Selonq[CandidatelonWithDelontails]] =
      relonmainingCandidatelons.groupBy { candidatelonWithDelontails =>
        if (pipelonlinelonScopelon.contains(candidatelonWithDelontails)) {
          // if a candidatelon's Buckelont doelonsnt appelonar in thelon pattelonrn it's backfillelond at thelon elonnd
          val buckelont = buckelontelonr(candidatelonWithDelontails)
          if (allBuckelontsInPattelonrn.contains(buckelont)) {
            Buckelontelond(buckelont)
          } elonlselon {
            NotABuckelontInThelonPattelonrn
          }
        } elonlselon {
          NotASelonlelonctelondCandidatelonPipelonlinelon
        }
      }

    val othelonrCandidatelons =
      groupelondCandidatelons.gelontOrelonlselon(NotASelonlelonctelondCandidatelonPipelonlinelon, Selonq.elonmpty)

    val notABuckelontInThelonPattelonrn =
      groupelondCandidatelons.gelontOrelonlselon(NotABuckelontInThelonPattelonrn, Selonq.elonmpty)

    // mutablelon so welon can relonmovelon finishelond itelonrators to optimizelon whelonn looping for largelon pattelonrns
    val groupelondBuckelontsItelonrators = mutablelon.HashMap(groupelondCandidatelons.collelonct {
      caselon (Buckelontelond(buckelont), candidatelonsWithDelontails) => (buckelont, candidatelonsWithDelontails.itelonrator)
    }.toSelonq: _*)

    val pattelonrnItelonrator = Itelonrator.continually(pattelonrn).flattelonn

    val nelonwRelonsult = nelonw mutablelon.ArrayBuffelonr[CandidatelonWithDelontails]()
    whilelon (groupelondBuckelontsItelonrators.nonelonmpty) {
      val buckelont = pattelonrnItelonrator.nelonxt()
      groupelondBuckelontsItelonrators.gelont(buckelont) match {
        caselon Somelon(itelonrator) if itelonrator.nonelonmpty => nelonwRelonsult += itelonrator.nelonxt()
        caselon Somelon(_) => groupelondBuckelontsItelonrators.relonmovelon(buckelont)
        caselon Nonelon =>
      }
    }

    SelonlelonctorRelonsult(
      relonmainingCandidatelons = othelonrCandidatelons,
      relonsult = relonsult ++ nelonwRelonsult ++ notABuckelontInThelonPattelonrn)
  }
}
