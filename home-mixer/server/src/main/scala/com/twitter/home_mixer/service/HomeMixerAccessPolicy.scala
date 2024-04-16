package com.tw ter.ho _m xer.serv ce

 mport com.tw ter.product_m xer.core.funct onal_component.common.access_pol cy.AccessPol cy
 mport com.tw ter.product_m xer.core.funct onal_component.common.access_pol cy.Allo dLdapGroups

object Ho M xerAccessPol cy {

  /**
   * Access pol c es can be conf gured on a product-by-product bas s but   may also want products
   * to have a common pol cy.
   */
  val DefaultHo M xerAccessPol cy: Set[AccessPol cy] = Set(Allo dLdapGroups(Set.empty[Str ng]))
}
