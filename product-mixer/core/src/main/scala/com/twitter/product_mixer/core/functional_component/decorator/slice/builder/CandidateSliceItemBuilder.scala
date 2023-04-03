packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.slicelon.buildelonr

import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.SlicelonItelonm
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

trait CandidatelonSlicelonItelonmBuildelonr[
  -Quelonry <: PipelonlinelonQuelonry,
  -BuildelonrInput <: UnivelonrsalNoun[Any],
  BuildelonrOutput <: SlicelonItelonm] {

  delonf apply(quelonry: Quelonry, candidatelon: BuildelonrInput, felonaturelonMap: FelonaturelonMap): BuildelonrOutput
}
