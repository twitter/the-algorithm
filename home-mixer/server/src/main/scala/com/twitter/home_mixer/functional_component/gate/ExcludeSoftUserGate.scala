package com.tw ter.ho _m xer.funct onal_component.gate

 mport com.tw ter.g zmoduck.{thr ftscala => t}
 mport com.tw ter.ho _m xer.model.Ho Features.UserTypeFeature
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.model.common. dent f er.Gate dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * A Soft User  s a user who  s  n t  gradual onboard ng state. T  gate can be
 * used to turn off certa n funct onal y l ke ads for t se users.
 */
object ExcludeSoftUserGate extends Gate[P pel neQuery] {

  overr de val  dent f er: Gate dent f er = Gate dent f er("ExcludeSoftUser")

  overr de def shouldCont nue(query: P pel neQuery): St ch[Boolean] = {
    val softUser = query.features
      .ex sts(_.getOrElse(UserTypeFeature, None).ex sts(_ == t.UserType.Soft))
    St ch.value(!softUser)
  }
}
