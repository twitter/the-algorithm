package com.tw ter.product_m xer.core.model.common.presentat on.urt

/**
 * W t r an  em  s cons dered d spensable w h n a module.
 * D spensable module  ems should never be left as t  f nal rema n ng
 *  ems w h n a module. W never a module would be left w h only
 * d spensable contents (through removal or d sm ssal of ot r  ems) t 
 * ent re module should be d scarded as  f conta ned 0  ems.
 *
 * @see http://go/urtD spensableModule ems
 */
tra   sD spensable { self: BaseUrt emPresentat on =>
  def d spensable: Boolean
}
