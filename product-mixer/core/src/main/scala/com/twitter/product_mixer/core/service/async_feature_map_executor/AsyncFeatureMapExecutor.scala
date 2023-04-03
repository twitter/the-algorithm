packagelon com.twittelonr.product_mixelonr.corelon.selonrvicelon.async_felonaturelon_map_elonxeloncutor

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.asyncfelonaturelonmap.AsyncFelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.PipelonlinelonStelonpIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor._
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutorRelonsult
import com.twittelonr.stitch.Arrow
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class AsyncFelonaturelonMapelonxeloncutor @Injelonct() (
  ovelonrridelon val statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds elonxeloncutor {

  /**
   * Forcelons an [[AsyncFelonaturelonMap]] to hydratelon and relonsolvelon into a [[FelonaturelonMap]]
   * containing all [[com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon]]s that arelon
   * supposelond to belon hydratelond belonforelon `stelonpToHydratelonBelonforelon`.
   */
  delonf arrow(
    stelonpToHydratelonFor: PipelonlinelonStelonpIdelonntifielonr,
    currelonntStelonp: PipelonlinelonStelonpIdelonntifielonr,
    contelonxt: Contelonxt
  ): Arrow[AsyncFelonaturelonMap, AsyncFelonaturelonMapelonxeloncutorRelonsults] = {
    Arrow
      .map[AsyncFelonaturelonMap, Option[Stitch[FelonaturelonMap]]](_.hydratelon(stelonpToHydratelonFor))
      .andThelonn(
        Arrow.chooselon(
          Arrow.Choicelon.ifDelonfinelondAt(
            { caselon Somelon(stitchOfFelonaturelonMap) => stitchOfFelonaturelonMap },
            // only stat if thelonrelon's somelonthing to hydratelon
            wrapComponelonntWithelonxeloncutorBookkelonelonping(contelonxt, currelonntStelonp)(
              Arrow
                .flatMap[Stitch[FelonaturelonMap], FelonaturelonMap](idelonntity)
                .map(felonaturelonMap =>
                  AsyncFelonaturelonMapelonxeloncutorRelonsults(Map(stelonpToHydratelonFor -> felonaturelonMap)))
            )
          ),
          Arrow.Choicelon.othelonrwiselon(Arrow.valuelon(AsyncFelonaturelonMapelonxeloncutorRelonsults(Map.elonmpty)))
        )
      )
  }
}

caselon class AsyncFelonaturelonMapelonxeloncutorRelonsults(
  felonaturelonMapsByStelonp: Map[PipelonlinelonStelonpIdelonntifielonr, FelonaturelonMap])
    elonxtelonnds elonxeloncutorRelonsult {
  delonf ++(
    asyncFelonaturelonMapelonxeloncutorRelonsults: AsyncFelonaturelonMapelonxeloncutorRelonsults
  ): AsyncFelonaturelonMapelonxeloncutorRelonsults =
    AsyncFelonaturelonMapelonxeloncutorRelonsults(
      felonaturelonMapsByStelonp ++ asyncFelonaturelonMapelonxeloncutorRelonsults.felonaturelonMapsByStelonp)
}
