package com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata

tra  HasCl entEvent nfo {
  def cl entEvent nfo: Opt on[Cl entEvent nfo]
}

/**
 *  nformat on used to bu ld Cl ent Events
 * @see [[http://go/cl ent-events]]
 */
case class Cl entEvent nfo(
  component: Opt on[Str ng],
  ele nt: Opt on[Str ng],
  deta ls: Opt on[Cl entEventDeta ls],
  act on: Opt on[Str ng],
  ent yToken: Opt on[Str ng])

/**
 * Add  onal cl ent events f elds
 *
 * @note  f a f eld from [[http://go/cl ent_app.thr ft]]  s needed but  s not  re
 *       contact t  `#product-m xer` team to have   added.
 */
case class Cl entEventDeta ls(
  conversat onDeta ls: Opt on[Conversat onDeta ls],
  t  l nesDeta ls: Opt on[T  l nesDeta ls],
  art cleDeta ls: Opt on[Art cleDeta ls],
  l veEventDeta ls: Opt on[L veEventDeta ls],
  com rceDeta ls: Opt on[Com rceDeta ls])
