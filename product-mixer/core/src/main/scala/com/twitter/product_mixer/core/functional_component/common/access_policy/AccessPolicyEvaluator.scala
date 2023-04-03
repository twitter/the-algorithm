packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.accelonss_policy

/**
 * Controls how accelonss policielons arelon applielond to allow/relonjelonct a relonquelonst
 */
objelonct AccelonssPolicyelonvaluator {
  delonf elonvaluatelon(productAccelonssPolicielons: Selont[AccelonssPolicy], uselonrLdapGroups: Selont[String]): Boolelonan =
    productAccelonssPolicielons.elonxists {
      caselon AllowelondLdapGroups(allowelondGroups) => allowelondGroups.elonxists(uselonrLdapGroups.contains)
      caselon _: Blockelonvelonrything => falselon
    }
}
