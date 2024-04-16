package com.tw ter.product_m xer.core.funct onal_component.common.access_pol cy

 mport com.fasterxml.jackson.annotat on.JsonSubTypes
 mport com.fasterxml.jackson.annotat on.JsonType nfo

/**
 * T  Access Pol cy to set for gat ng query ng  n t  turntable tool.
 *
 * @note  mple ntat ons must be s mple case classes w h un que structures for ser al zat on
 */
@JsonType nfo(use = JsonType nfo. d.NAME,  nclude = JsonType nfo.As.PROPERTY)
@JsonSubTypes(
  Array(
    new JsonSubTypes.Type(value = classOf[Allo dLdapGroups], na  = "Allo dLdapGroups"),
    new JsonSubTypes.Type(value = classOf[BlockEveryth ng], na  = "BlockEveryth ng")
  )
)
sealed tra  AccessPol cy

/**
 * Users must be  n *at least* one of t  prov ded LDAP groups  n order to make a query.
 *
 * @param groups LDAP groups allo d to access t  product
 */
case class Allo dLdapGroups(groups: Set[Str ng]) extends AccessPol cy

object Allo dLdapGroups {
  def apply(group: Str ng): Allo dLdapGroups = Allo dLdapGroups(Set(group))
}

/**
 * Block all requests to a product.
 *
 * @note t  needs to be a case class rat r than an object because classOf doesn't work on objects
 *       and JsonSubTypes requ res t  annotat on argu nt to be a constant (rul ng out .getClass).
 *       T   ssue may be resolved  n Scala 2.13: https://g hub.com/scala/scala/pull/9279
 */
case class BlockEveryth ng() extends AccessPol cy
