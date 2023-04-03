packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator.offlinelon_aggrelongatelons

import com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator.offlinelon_aggrelongatelons.elondgelonAggrelongatelonFelonaturelons._
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class Phaselon1elondgelonAggrelongatelonFelonaturelonHydrator @Injelonct() elonxtelonnds BaselonelondgelonAggrelongatelonFelonaturelonHydrator {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr =
    FelonaturelonHydratorIdelonntifielonr("Phaselon1elondgelonAggrelongatelon")

  ovelonrridelon val aggrelongatelonFelonaturelons: Selont[BaselonelondgelonAggrelongatelonFelonaturelon] =
    Selont(
      UselonrAuthorAggrelongatelonFelonaturelon,
      UselonrOriginalAuthorAggrelongatelonFelonaturelon,
      UselonrMelonntionAggrelongatelonFelonaturelon
    )
}
