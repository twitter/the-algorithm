packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urp.buildelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.PagelonHelonadelonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Trait for our buildelonr which givelonn a quelonry and selonlelonctions will relonturn an `Option[PagelonHelonadelonr]`
 *
 * @tparam Quelonry
 */
trait PagelonHelonadelonrBuildelonr[-Quelonry <: PipelonlinelonQuelonry] {

  delonf build(
    quelonry: Quelonry,
    selonlelonctions: Selonq[CandidatelonWithDelontails]
  ): Option[PagelonHelonadelonr]
}
