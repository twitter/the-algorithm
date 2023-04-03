packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon

import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.HasComponelonntIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.PipelonlinelonStelonpIdelonntifielonr

trait PipelonlinelonConfig elonxtelonnds HasComponelonntIdelonntifielonr

trait PipelonlinelonConfigCompanion {

  /** uselond to gelonnelonratelon `AsyncFelonaturelonsFor` [[PipelonlinelonStelonpIdelonntifielonr]]s for thelon intelonrnal Async Felonaturelons Stelonp */
  privatelon[corelon] delonf asyncFelonaturelonsStelonp(
    stelonpToHydratelonFor: PipelonlinelonStelonpIdelonntifielonr
  ): PipelonlinelonStelonpIdelonntifielonr =
    PipelonlinelonStelonpIdelonntifielonr("AsyncFelonaturelonsFor" + stelonpToHydratelonFor.namelon)

  /** All thelon Stelonps which arelon elonxeloncutelond by a [[Pipelonlinelon]] in thelon ordelonr in which thelony arelon run */
  val stelonpsInOrdelonr: Selonq[PipelonlinelonStelonpIdelonntifielonr]

  val stelonpsAsyncFelonaturelonHydrationCanBelonComplelontelondBy: Selont[PipelonlinelonStelonpIdelonntifielonr] = Selont.elonmpty
}
