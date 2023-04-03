packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urp.buildelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.PagelonBody
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Trait for our buildelonr which givelonn a quelonry and selonlelonctions will relonturn a `PagelonBody`
 *
 * @tparam Quelonry
 */
trait PagelonBodyBuildelonr[-Quelonry <: PipelonlinelonQuelonry] {

  delonf build(
    quelonry: Quelonry,
    selonlelonctions: Selonq[CandidatelonWithDelontails]
  ): PagelonBody
}
