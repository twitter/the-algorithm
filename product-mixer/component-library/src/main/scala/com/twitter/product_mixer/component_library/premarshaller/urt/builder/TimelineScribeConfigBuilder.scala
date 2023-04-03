packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonScribelonConfig
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Trait for our buildelonr which givelonn a quelonry and elonntrielons will relonturn an `Option[TimelonlinelonScribelonConfig]`
 *
 * @tparam Quelonry
 */
trait TimelonlinelonScribelonConfigBuildelonr[-Quelonry <: PipelonlinelonQuelonry] {

  delonf build(
    quelonry: Quelonry,
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): Option[TimelonlinelonScribelonConfig]
}
