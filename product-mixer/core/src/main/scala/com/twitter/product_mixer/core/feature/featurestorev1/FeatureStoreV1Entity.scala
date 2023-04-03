packagelon com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonstorelonv1

import com.twittelonr.ml.felonaturelonstorelon.lib.elonntityId
import com.twittelonr.ml.felonaturelonstorelon.lib.elonntity.elonntity
import com.twittelonr.ml.felonaturelonstorelon.lib.elonntity.elonntityWithId
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

selonalelond trait FelonaturelonStorelonV1elonntity[
  -Quelonry <: PipelonlinelonQuelonry,
  -Input,
  FelonaturelonStorelonelonntityId <: elonntityId] {

  val elonntity: elonntity[FelonaturelonStorelonelonntityId]
}

trait FelonaturelonStorelonV1Quelonryelonntity[-Quelonry <: PipelonlinelonQuelonry, FelonaturelonStorelonelonntityId <: elonntityId]
    elonxtelonnds FelonaturelonStorelonV1elonntity[Quelonry, Quelonry, FelonaturelonStorelonelonntityId] {

  delonf elonntityWithId(quelonry: Quelonry): elonntityWithId[FelonaturelonStorelonelonntityId]
}

trait FelonaturelonStorelonV1Candidatelonelonntity[
  -Quelonry <: PipelonlinelonQuelonry,
  -Input <: UnivelonrsalNoun[Any],
  FelonaturelonStorelonelonntityId <: elonntityId]
    elonxtelonnds FelonaturelonStorelonV1elonntity[Quelonry, Input, FelonaturelonStorelonelonntityId] {

  delonf elonntityWithId(
    quelonry: Quelonry,
    input: Input,
    elonxistingFelonaturelons: FelonaturelonMap
  ): elonntityWithId[FelonaturelonStorelonelonntityId]
}
