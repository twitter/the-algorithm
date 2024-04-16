package com.tw ter.product_m xer.core.funct onal_component.common.access_pol cy

/**
 * Controls how access pol c es are appl ed to allow/reject a request
 */
object AccessPol cyEvaluator {
  def evaluate(productAccessPol c es: Set[AccessPol cy], userLdapGroups: Set[Str ng]): Boolean =
    productAccessPol c es.ex sts {
      case Allo dLdapGroups(allo dGroups) => allo dGroups.ex sts(userLdapGroups.conta ns)
      case _: BlockEveryth ng => false
    }
}
