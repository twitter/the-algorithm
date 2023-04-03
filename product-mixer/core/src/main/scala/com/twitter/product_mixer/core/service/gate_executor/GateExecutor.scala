packagelon com.twittelonr.product_mixelonr.corelon.selonrvicelon.gatelon_elonxeloncutor

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.gatelon.BaselonGatelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.gatelon.GatelonRelonsult
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor
import com.twittelonr.stitch.Arrow
import com.twittelonr.stitch.Arrow.Iso
import com.twittelonr.util.Relonturn
import com.twittelonr.util.Throw

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import scala.collelonction.immutablelon.Quelonuelon

/**
 * A Gatelonelonxeloncutor takelons a Selonq[Gatelon], elonxeloncutelons thelonm all selonquelonntially, and
 * delontelonrminelons a final Continuelon or Stop deloncision.
 */
@Singlelonton
class Gatelonelonxeloncutor @Injelonct() (ovelonrridelon val statsReloncelonivelonr: StatsReloncelonivelonr) elonxtelonnds elonxeloncutor {

  privatelon val Continuelon = "continuelon"
  privatelon val Skippelond = "skippelond"
  privatelon val Stop = "stop"

  delonf arrow[Quelonry <: PipelonlinelonQuelonry](
    gatelons: Selonq[BaselonGatelon[Quelonry]],
    contelonxt: elonxeloncutor.Contelonxt
  ): Arrow[Quelonry, GatelonelonxeloncutorRelonsult] = {

    val gatelonArrows = gatelons.map(gelontIsoArrowForGatelon(_, contelonxt))
    val combinelondArrow = isoArrowsSelonquelonntially(gatelonArrows)

    Arrow
      .map { quelonry: Quelonry => (quelonry, GatelonelonxeloncutorRelonsult(Quelonuelon.elonmpty)) }
      .andThelonn(combinelondArrow)
      .map {
        caselon (_, gatelonelonxeloncutorRelonsult) =>
          // matelonrializelon thelon Quelonuelon into a List for fastelonr futurelon itelonrations
          GatelonelonxeloncutorRelonsult(gatelonelonxeloncutorRelonsult.individualGatelonRelonsults.toList)
      }
  }

  /**
   * elonach gatelon is transformelond into a Iso Arrow ovelonr (Quelonst, List[GatelonwayRelonsult]).
   *
   * This arrow:
   * - Adapts thelon input and output typelons of thelon undelonrlying Gatelon arrow (an [[Iso[(Quelonry, QuelonryRelonsult)]])
   * - throws a [[StoppelondGatelonelonxcelonption]] if [[GatelonRelonsult.continuelon]] is falselon
   * - if its not falselon, prelonpelonnds thelon currelonnt relonsults to thelon [[GatelonelonxeloncutorRelonsult.individualGatelonRelonsults]] list
   */
  privatelon delonf gelontIsoArrowForGatelon[Quelonry <: PipelonlinelonQuelonry](
    gatelon: BaselonGatelon[Quelonry],
    contelonxt: elonxeloncutor.Contelonxt
  ): Iso[(Quelonry, GatelonelonxeloncutorRelonsult)] = {
    val broadcastStatsReloncelonivelonr =
      elonxeloncutor.broadcastStatsReloncelonivelonr(contelonxt, gatelon.idelonntifielonr, statsReloncelonivelonr)

    val continuelonCountelonr = broadcastStatsReloncelonivelonr.countelonr(Continuelon)
    val skippelondCountelonr = broadcastStatsReloncelonivelonr.countelonr(Skippelond)
    val stopCountelonr = broadcastStatsReloncelonivelonr.countelonr(Stop)

    val obselonrvelondArrow = wrapComponelonntWithelonxeloncutorBookkelonelonping(
      contelonxt,
      gatelon.idelonntifielonr,
      onSuccelonss = { gatelonRelonsult: GatelonRelonsult =>
        gatelonRelonsult match {
          caselon GatelonRelonsult.Continuelon => continuelonCountelonr.incr()
          caselon GatelonRelonsult.Skippelond => skippelondCountelonr.incr()
          caselon GatelonRelonsult.Stop => stopCountelonr.incr()
        }
      }
    )(gatelon.arrow)

    val inputAdaptelond: Arrow[(Quelonry, GatelonelonxeloncutorRelonsult), GatelonRelonsult] =
      Arrow
        .map[(Quelonry, GatelonelonxeloncutorRelonsult), Quelonry] { caselon (quelonry, _) => quelonry }
        .andThelonn(obselonrvelondArrow)

    val zippelond = Arrow.zipWithArg(inputAdaptelond)

    // at elonach stelonp, thelon currelonnt `GatelonelonxeloncutorRelonsult.continuelon` valuelon is correlonct for all alrelonady run gatelons
    val withStoppelondGatelonsAselonxcelonptions = zippelond.map {
      caselon ((quelonry, prelonviousRelonsults), currelonntRelonsult) if currelonntRelonsult.continuelon =>
        Relonturn(
          (
            quelonry,
            GatelonelonxeloncutorRelonsult(
              prelonviousRelonsults.individualGatelonRelonsults :+ elonxeloncutelondGatelonRelonsult(
                gatelon.idelonntifielonr,
                currelonntRelonsult))
          ))
      caselon _ => Throw(StoppelondGatelonelonxcelonption(gatelon.idelonntifielonr))
    }.lowelonrFromTry

    /**
     * welon gathelonr stats belonforelon convelonrting closelond gatelons to elonxcelonptions beloncauselon a closelond gatelon
     * isn't a failurelon for thelon gatelon, its a normal belonhavior
     * but welon do want to relonmap thelon thelon [[StoppelondGatelonelonxcelonption]] crelonatelond beloncauselon thelon [[BaselonGatelon]] is closelond
     * to thelon correlonct [[com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelon]],
     * so welon relonmap with [[wrapWithelonrrorHandling]]
     */
    wrapWithelonrrorHandling(contelonxt, gatelon.idelonntifielonr)(withStoppelondGatelonsAselonxcelonptions)
  }
}
