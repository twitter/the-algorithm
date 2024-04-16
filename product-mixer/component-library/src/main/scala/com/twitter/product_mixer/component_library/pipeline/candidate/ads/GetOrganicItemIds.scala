package com.tw ter.product_m xer.component_l brary.p pel ne.cand date.ads

 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls

/**
 * Get organ c  em cand dates from t  set of prev ous cand dates
 */
tra  GetOrgan c em ds {

  def apply(prev ousCand dates: Seq[Cand dateW hDeta ls]): Opt on[Seq[Long]]
}

/**
 * Get organ c  ems from spec f ed p pel nes
 */
case class P pel neScopedOrgan c em ds(p pel nes: Cand dateScope) extends GetOrgan c em ds {

  def apply(prev ousCand dates: Seq[Cand dateW hDeta ls]): Opt on[Seq[Long]] =
    So (prev ousCand dates.f lter(p pel nes.conta ns).map(_.cand date dLong))
}

/**
 * Get an empty l st of organ c  em cand dates
 */
case object EmptyOrgan c em ds extends GetOrgan c em ds {

  def apply(prev ousCand dates: Seq[Cand dateW hDeta ls]): Opt on[Seq[Long]] = None
}
