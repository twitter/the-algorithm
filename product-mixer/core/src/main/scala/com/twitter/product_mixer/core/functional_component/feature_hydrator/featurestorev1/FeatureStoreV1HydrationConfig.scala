packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.felonaturelonstorelonv1

import com.twittelonr.ml.felonaturelonstorelon.lib.elonntityId
import com.twittelonr.ml.felonaturelonstorelon.lib.dynamic.BaselonDynamicHydrationConfig
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonstorelonv1.BaselonFelonaturelonStorelonV1CandidatelonFelonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonstorelonv1.BaselonFelonaturelonStorelonV1QuelonryFelonaturelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

caselon class FelonaturelonStorelonV1QuelonryFelonaturelonHydrationConfig[Quelonry <: PipelonlinelonQuelonry](
  felonaturelons: Selont[BaselonFelonaturelonStorelonV1QuelonryFelonaturelon[Quelonry, _ <: elonntityId, _]])
    elonxtelonnds BaselonDynamicHydrationConfig[
      Quelonry,
      BaselonFelonaturelonStorelonV1QuelonryFelonaturelon[Quelonry, _ <: elonntityId, _]
    ](felonaturelons)

caselon class FelonaturelonStorelonV1CandidatelonFelonaturelonHydrationConfig[
  Quelonry <: PipelonlinelonQuelonry,
  Input <: UnivelonrsalNoun[Any]
](
  felonaturelons: Selont[BaselonFelonaturelonStorelonV1CandidatelonFelonaturelon[Quelonry, Input, _ <: elonntityId, _]])
    elonxtelonnds BaselonDynamicHydrationConfig[
      Quelonry,
      BaselonFelonaturelonStorelonV1CandidatelonFelonaturelon[Quelonry, Input, _ <: elonntityId, _]
    ](felonaturelons)
