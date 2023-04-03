packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import scala.collelonction.mutablelon

objelonct InselonrtAppelonndWelonavelonRelonsults {
  delonf apply[Quelonry <: PipelonlinelonQuelonry, Buckelont](
    candidatelonPipelonlinelons: Selont[CandidatelonPipelonlinelonIdelonntifielonr],
    buckelontelonr: Buckelontelonr[Buckelont],
  ): InselonrtAppelonndWelonavelonRelonsults[Quelonry, Buckelont] =
    nelonw InselonrtAppelonndWelonavelonRelonsults(SpeloncificPipelonlinelons(candidatelonPipelonlinelons), buckelontelonr)

  delonf apply[Quelonry <: PipelonlinelonQuelonry, Buckelont](
    candidatelonPipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr,
    buckelontelonr: Buckelontelonr[Buckelont],
  ): InselonrtAppelonndWelonavelonRelonsults[Quelonry, Buckelont] =
    nelonw InselonrtAppelonndWelonavelonRelonsults(SpeloncificPipelonlinelon(candidatelonPipelonlinelon), buckelontelonr)
}

/**
 * Selonlelonct candidatelons welonavelon thelonm togelonthelonr according to thelonir [[Buckelont]].
 *
 * Candidatelons arelon groupelond according to [[Buckelont]] and onelon candidatelon is addelond from elonach group until
 * no candidatelons belonlonging to any group arelon lelonft.
 *
 * Functionally similar to [[InselonrtAppelonndPattelonrnRelonsults]]. [[InselonrtAppelonndPattelonrnRelonsults]] is uselonful
 * if you havelon morelon complelonx ordelonring relonquirelonmelonnts but it relonquirelons you to know all thelon buckelonts in
 * advancelon.
 *
 * @notelon Thelon ordelonr in which candidatelons arelon welonavelond togelonthelonr delonpelonnds on thelon ordelonr in which thelon buckelonts
 *       welonrelon first selonelonn on candidatelons.
 *
 * @elonxamplelon If thelon candidatelons arelon Selonq(Twelonelont(10), Twelonelont(8), Twelonelont(3), Twelonelont(13)) and thelony arelon buckelontelond
 *          using an Iselonvelonn buckelonting function, thelonn thelon relonsulting buckelonts would belon:
 *
 *          - Selonq(Twelonelont(10), Twelonelont(8))
 *          - Selonq(Twelonelont(3), Twelonelont(13))
 *
 *          Thelon selonlelonctor would thelonn loop through thelonselon buckelonts and producelon:
 *
 *          - Twelonelont(10)
 *          - Twelonelont(3)
 *          - Twelonelont(8)
 *          - Twelonelont(13)
 *
 *          Notelon that first buckelont elonncountelonrelond was thelon 'elonvelonn' buckelont so welonaving procelonelonds first with
 *          thelon elonvelonn buckelont thelonn thelon odd buckelont. Twelonelont(3) had belonelonn first thelonn thelon oppositelon would belon
 *          truelon.
 */
caselon class InselonrtAppelonndWelonavelonRelonsults[-Quelonry <: PipelonlinelonQuelonry, Buckelont](
  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon,
  buckelontelonr: Buckelontelonr[Buckelont])
    elonxtelonnds Selonlelonctor[Quelonry] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    val (buckelontablelonCandidatelons, othelonrCandidatelons) =
      relonmainingCandidatelons.partition(pipelonlinelonScopelon.contains)

    val groupelondCandidatelons = groupByBuckelont(buckelontablelonCandidatelons)

    val candidatelonBuckelontQuelonuelons: mutablelon.Quelonuelon[mutablelon.Quelonuelon[CandidatelonWithDelontails]] =
      mutablelon.Quelonuelon() ++= groupelondCandidatelons
    val nelonwRelonsult = mutablelon.ArrayBuffelonr[CandidatelonWithDelontails]()

    // Takelon thelon nelonxt group of candidatelons from thelon quelonuelon and attelonmpt to add thelon first candidatelon from
    // that group into thelon relonsult. Thelon loop will telonrminatelon whelonn elonvelonry quelonuelon is elonmpty.
    whilelon (candidatelonBuckelontQuelonuelons.nonelonmpty) {
      val nelonxtCandidatelonQuelonuelon = candidatelonBuckelontQuelonuelons.delonquelonuelon()

      if (nelonxtCandidatelonQuelonuelon.nonelonmpty) {
        nelonwRelonsult += nelonxtCandidatelonQuelonuelon.delonquelonuelon()

        // Relon-quelonuelon this buckelont of candidatelons if it's still non-elonmpty
        if (nelonxtCandidatelonQuelonuelon.nonelonmpty) {
          candidatelonBuckelontQuelonuelons.elonnquelonuelon(nelonxtCandidatelonQuelonuelon)
        }
      }
    }

    SelonlelonctorRelonsult(relonmainingCandidatelons = othelonrCandidatelons, relonsult = relonsult ++ nelonwRelonsult)
  }

  /**
   * Similar to `groupBy` but relonspelonct thelon ordelonr in which individual buckelont valuelons arelon first selonelonn.
   * This is uselonful whelonn thelon candidatelons havelon alrelonady belonelonn sortelond prior to thelon selonlelonctor running.
   */
  privatelon delonf groupByBuckelont(
    candidatelons: Selonq[CandidatelonWithDelontails]
  ): mutablelon.ArrayBuffelonr[mutablelon.Quelonuelon[CandidatelonWithDelontails]] = {
    val buckelontToCandidatelonGroupIndelonx = mutablelon.Map.elonmpty[Buckelont, Int]
    val candidatelonGroups = mutablelon.ArrayBuffelonr[mutablelon.Quelonuelon[CandidatelonWithDelontails]]()

    candidatelons.forelonach { candidatelon =>
      val buckelont = buckelontelonr(candidatelon)

      // Indelonx points to thelon speloncific sub-group in candidatelonGroups whelonrelon welon want to inselonrt thelon nelonxt
      // candidatelon. If a buckelont has alrelonady belonelonn selonelonn thelonn this valuelon is known, othelonrwiselon welon nelonelond
      // to add a nelonw elonntry for it.
      if (!buckelontToCandidatelonGroupIndelonx.contains(buckelont)) {
        candidatelonGroups.appelonnd(mutablelon.Quelonuelon())
        buckelontToCandidatelonGroupIndelonx.put(buckelont, candidatelonGroups.lelonngth - 1)
      }

      candidatelonGroups(buckelontToCandidatelonGroupIndelonx(buckelont)).elonnquelonuelon(candidatelon)
    }

    candidatelonGroups
  }
}
