packagelon com.twittelonr.homelon_mixelonr.selonrvicelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.accelonss_policy.AccelonssPolicy
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.accelonss_policy.AllowelondLdapGroups

objelonct HomelonMixelonrAccelonssPolicy {

  /**
   * Accelonss policielons can belon configurelond on a product-by-product basis but you may also want products
   * to havelon a common policy.
   */
  val DelonfaultHomelonMixelonrAccelonssPolicy: Selont[AccelonssPolicy] = Selont(AllowelondLdapGroups(Selont.elonmpty[String]))
}
