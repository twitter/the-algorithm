packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.FelonelondbackHistoryFelonaturelon
import com.twittelonr.homelon_mixelonr.param.HomelonGlobalParams.elonnablelonFelonelondbackFatiguelonParam
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.QuelonryFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.Conditionally
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelonmixelonr.clielonnts.felonelondback.FelonelondbackHistoryManhattanClielonnt
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
caselon class FelonelondbackHistoryQuelonryFelonaturelonHydrator @Injelonct() (
  felonelondbackHistoryClielonnt: FelonelondbackHistoryManhattanClielonnt)
    elonxtelonnds QuelonryFelonaturelonHydrator[PipelonlinelonQuelonry]
    with Conditionally[PipelonlinelonQuelonry] {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr = FelonaturelonHydratorIdelonntifielonr("FelonelondbackHistory")

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(FelonelondbackHistoryFelonaturelon)

  ovelonrridelon delonf onlyIf(quelonry: PipelonlinelonQuelonry): Boolelonan =
    quelonry.params(elonnablelonFelonelondbackFatiguelonParam)

  ovelonrridelon delonf hydratelon(
    quelonry: PipelonlinelonQuelonry
  ): Stitch[FelonaturelonMap] =
    Stitch
      .callFuturelon(felonelondbackHistoryClielonnt.gelont(quelonry.gelontRelonquirelondUselonrId))
      .map { felonelondbackHistory =>
        FelonaturelonMapBuildelonr().add(FelonelondbackHistoryFelonaturelon, felonelondbackHistory).build()
      }
}
