packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata

import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackActionInfo
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

trait BaselonFelonelondbackActionInfoBuildelonr[-Quelonry <: PipelonlinelonQuelonry, -Candidatelon <: UnivelonrsalNoun[Any]] {

  delonf apply(
    quelonry: Quelonry,
    candidatelon: Candidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[FelonelondbackActionInfo]
}
