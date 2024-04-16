package com.tw ter.product_m xer.core.gate

 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.model.common. dent f er.Gate dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .Param

case class ParamGate(na : Str ng, param: Param[Boolean])( mpl c  f le: s cecode.F le)
    extends Gate[P pel neQuery] {

  // From a custo r-perspect ve,  's more useful to see t  f le that created t  ParamGate
  overr de val  dent f er: Gate dent f er = Gate dent f er(na )(f le)

  overr de def shouldCont nue(query: P pel neQuery): St ch[Boolean] =
    St ch.value(query.params(param))
}

object ParamGate {
  val EnabledGateSuff x = "Enabled"
  val SupportedCl entGateSuff x = "SupportedCl ent"
}
