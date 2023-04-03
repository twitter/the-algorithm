packagelon com.twittelonr.product_mixelonr.corelon.selonrvicelon.pipelonlinelon_relonsult_sidelon_elonffelonct_elonxeloncutor

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.sidelon_elonffelonct.elonxeloncutelonSynchronously
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.sidelon_elonffelonct.FailOpelonn
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.sidelon_elonffelonct.PipelonlinelonRelonsultSidelonelonffelonct
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.sidelon_elonffelonct.PipelonlinelonRelonsultSidelonelonffelonct.Inputs
import com.twittelonr.product_mixelonr.corelon.modelonl.common.Conditionally
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.SidelonelonffelonctIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.HasMarshalling
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutorRelonsult
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.pipelonlinelon_relonsult_sidelon_elonffelonct_elonxeloncutor.PipelonlinelonRelonsultSidelonelonffelonctelonxeloncutor._
import com.twittelonr.stitch.Arrow
import com.twittelonr.util.Relonturn
import com.twittelonr.util.Try
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class PipelonlinelonRelonsultSidelonelonffelonctelonxeloncutor @Injelonct() (ovelonrridelon val statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds elonxeloncutor {
  delonf arrow[Quelonry <: PipelonlinelonQuelonry, MixelonrDomainRelonsultTypelon <: HasMarshalling](
    sidelonelonffeloncts: Selonq[PipelonlinelonRelonsultSidelonelonffelonct[Quelonry, MixelonrDomainRelonsultTypelon]],
    contelonxt: elonxeloncutor.Contelonxt
  ): Arrow[Inputs[Quelonry, MixelonrDomainRelonsultTypelon], PipelonlinelonRelonsultSidelonelonffelonctelonxeloncutor.Relonsult] = {

    val individualArrows: Selonq[
      Arrow[Inputs[Quelonry, MixelonrDomainRelonsultTypelon], (SidelonelonffelonctIdelonntifielonr, SidelonelonffelonctRelonsultTypelon)]
    ] = sidelonelonffeloncts.map {
      caselon synchronousSidelonelonffelonct: elonxeloncutelonSynchronously =>
        val failsRelonquelonstIfThrows = {
          wrapComponelonntWithelonxeloncutorBookkelonelonping(contelonxt, synchronousSidelonelonffelonct.idelonntifielonr)(
            Arrow.flatMap(synchronousSidelonelonffelonct.apply))
        }
        synchronousSidelonelonffelonct match {
          caselon failOpelonn: FailOpelonn =>
            // lift thelon failurelon
            failsRelonquelonstIfThrows.liftToTry.map(t =>
              (failOpelonn.idelonntifielonr, SynchronousSidelonelonffelonctRelonsult(t)))
          caselon _ =>
            // don't elonncapsulatelon thelon failurelon
            failsRelonquelonstIfThrows.map(_ =>
              (synchronousSidelonelonffelonct.idelonntifielonr, SynchronousSidelonelonffelonctRelonsult(Relonturn.Unit)))
        }

      caselon sidelonelonffelonct =>
        Arrow
          .async(
            wrapComponelonntWithelonxeloncutorBookkelonelonping(contelonxt, sidelonelonffelonct.idelonntifielonr)(
              Arrow.flatMap(sidelonelonffelonct.apply)))
          .andThelonn(Arrow.valuelon((sidelonelonffelonct.idelonntifielonr, SidelonelonffelonctRelonsult)))
    }

    val conditionallyRunArrows = sidelonelonffeloncts.zip(individualArrows).map {
      caselon (
            sidelonelonffelonct: Conditionally[
              PipelonlinelonRelonsultSidelonelonffelonct.Inputs[Quelonry, MixelonrDomainRelonsultTypelon] @unchelonckelond
            ],
            arrow) =>
        Arrow.ifelonlselon[
          Inputs[Quelonry, MixelonrDomainRelonsultTypelon],
          (SidelonelonffelonctIdelonntifielonr, SidelonelonffelonctRelonsultTypelon)](
          input => sidelonelonffelonct.onlyIf(input),
          arrow,
          Arrow.valuelon((sidelonelonffelonct.idelonntifielonr, TurnelondOffByConditionally)))
      caselon (_, arrow) => arrow
    }

    Arrow
      .collelonct(conditionallyRunArrows)
      .map(relonsults => Relonsult(relonsults))
  }
}

objelonct PipelonlinelonRelonsultSidelonelonffelonctelonxeloncutor {
  caselon class Relonsult(sidelonelonffeloncts: Selonq[(SidelonelonffelonctIdelonntifielonr, SidelonelonffelonctRelonsultTypelon)])
      elonxtelonnds elonxeloncutorRelonsult

  selonalelond trait SidelonelonffelonctRelonsultTypelon

  /** Thelon [[PipelonlinelonRelonsultSidelonelonffelonct]] was elonxeloncutelond asynchronously in a firelon-and-forgelont way so no relonsult will belon availablelon */
  caselon objelonct SidelonelonffelonctRelonsult elonxtelonnds SidelonelonffelonctRelonsultTypelon

  /** Thelon relonsult of thelon [[PipelonlinelonRelonsultSidelonelonffelonct]] that was elonxeloncutelond with [[elonxeloncutelonSynchronously]] */
  caselon class SynchronousSidelonelonffelonctRelonsult(relonsult: Try[Unit]) elonxtelonnds SidelonelonffelonctRelonsultTypelon

  /** Thelon relonsult for whelonn a [[PipelonlinelonRelonsultSidelonelonffelonct]] is turnelond off by [[Conditionally]]'s [[Conditionally.onlyIf]] */
  caselon objelonct TurnelondOffByConditionally elonxtelonnds SidelonelonffelonctRelonsultTypelon
}
