package com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure

/**
 * Fa lures are grouped  nto categor es based on wh ch party  s 'respons ble' for t   ssue. T 
 *  s  mportant for generat ng accurate SLOs and ensur ng that t  correct team  s alerted.
 */
sealed tra  P pel neFa lureCategory {
  val categoryNa : Str ng
  val fa lureNa : Str ng
}

/**
 * Cl ent Fa lures are fa lures w re t  cl ent  s dee d respons ble for t   ssue. Such as by
 *  ssu ng an  nval d request or not hav ng t  r ght perm ss ons.
 *
 * A fa lure m ght belong  n t  category  f   relates to behav   on t  cl ent and  s not
 * act onable by t  team wh ch owns t  product.
 */
tra  Cl entFa lure extends P pel neFa lureCategory {
  overr de val categoryNa : Str ng = "Cl entFa lure"
}

/**
 * T  requested product  s d sabled so t  request cannot be served.
 */
case object ProductD sabled extends Cl entFa lure {
  overr de val fa lureNa : Str ng = "ProductD sabled"
}

/**
 * T  request was dee d  nval d by t  or a back ng serv ce.
 */
case object BadRequest extends Cl entFa lure {
  overr de val fa lureNa : Str ng = "BadRequest"
}

/**
 * Credent als prov ng t   dent y of t  caller  re m ss ng, not trusted, or exp red.
 * For example, an auth cook e m ght be exp red and  n need of refresh ng.
 *
 * Do not confuse t  w h Author zat on, w re t  credent als are bel eved but not allo d to perform t  operat on.
 */
case object Aut nt cat on extends Cl entFa lure {
  overr de val fa lureNa : Str ng = "Aut nt cat on"
}

/**
 * T  operat on was forb dden (often, but not always, by a Strato access control pol cy).
 *
 * Do not confuse t  w h Aut nt cat on, w re t  g ven credent als  re m ss ng, not trusted, or exp red.
 */
case object Unauthor zed extends Cl entFa lure {
  overr de val fa lureNa : Str ng = "Unauthor zed"
}

/**
 * T  operat on returned a Not Found response.
 */
case object NotFound extends Cl entFa lure {
  overr de val fa lureNa : Str ng = "NotFound"
}

/**
 * An  nval d  nput  s  ncluded  n a cursor f eld.
 */
case object Malfor dCursor extends Cl entFa lure {
  overr de val fa lureNa : Str ng = "Malfor dCursor"
}

/**
 * T  operat on d d not succeed due to a closed gate
 */
case object ClosedGate extends Cl entFa lure {
  overr de val fa lureNa : Str ng = "ClosedGate"
}

/**
 * Server Fa lures are fa lures for wh ch t  owner of t  product  s respons ble. Typ cally t 
 *  ans t  request was val d but an  ssue w h n Product M xer or a dependent serv ce prevented
 *   from succeed ng.
 *
 * Server Fa lures contr bute to t  success rate SLO for t  product.
 */
tra  ServerFa lure extends P pel neFa lureCategory {
  overr de val categoryNa : Str ng = "ServerFa lure"
}

/**
 * Unclass f ed fa lures occur w n product code throws an except on that Product M xer does not
 * know how to class fy.
 *
 * T y can be used  n fa lOpen pol c es, etc - but  's always preferred to  nstead add add  onal
 * class f cat on log c and to keep Unclass f ed fa lures at 0.
 */
case object Uncategor zedServerFa lure extends ServerFa lure {
  overr de val fa lureNa : Str ng = "Uncategor zedServerFa lure"
}

/**
 * A hydrator or transfor r returned a m sconf gured feature map, t   nd cates a custo r
 * conf gurat on error. T  owner of t  component should make sure t  hydrator always returns a
 * [[FeatureMap]] w h t  all features def ned  n t  hydrator also set  n t  map,   should not have
 * any unreg stered features nor should reg stered features be absent.
 */
case object M sconf guredFeatureMapFa lure extends ServerFa lure {
  overr de val fa lureNa : Str ng = "M sconf guredFeatureMapFa lure"
}

/**
 * A P pel neSelector returned an  nval d Component dent f er.
 *
 * A p pel ne selector should choose t   dent f er of a p pel ne that  s conta ned by t  'p pel nes'
 * sequence of t  ProductP pel neConf g.
 */
case object  nval dP pel neSelected extends ServerFa lure {
  overr de val fa lureNa : Str ng = " nval dP pel neSelected"
}

/**
 * Fa lures that occur w n product code reac s an unexpected or ot rw se  llegal state.
 */
case object  llegalStateFa lure extends ServerFa lure {
  overr de val fa lureNa : Str ng = " llegalStateFa lure"
}

/**
 * An unexpected cand date was returned  n a cand date s ce that was unable to be transfor d.
 */
case object UnexpectedCand dateResult extends ServerFa lure {
  overr de val fa lureNa : Str ng = "UnexpectedCand dateResult"
}

/**
 * An unexpected Cand date was returned  n a marshaller
 */
case object UnexpectedCand date nMarshaller extends ServerFa lure {
  overr de val fa lureNa : Str ng = "UnexpectedCand date nMarshaller"
}

/**
 * P pel ne execut on fa led due to an  ncorrectly conf gured qual y factor (e.g, access ng
 * qual y factor state for a p pel ne that does not have qual y factor conf gured)
 */
case object M sconf guredQual yFactor extends ServerFa lure {
  overr de val fa lureNa : Str ng = "M sconf guredQual yFactor"
}

/**
 * P pel ne execut on fa led due to an  ncorrectly conf gured decorator (e.g, decorator
 * returned t  wrong type or tr ed to decorate an already decorated cand date)
 */
case object M sconf guredDecorator extends ServerFa lure {
  overr de val fa lureNa : Str ng = "M sconf guredDecorator"
}

/**
 * Cand date S ce P pel ne execut on fa led due to a t  out.
 */
case object Cand dateS ceT  out extends ServerFa lure {
  overr de val fa lureNa : Str ng = "Cand dateS ceT  out"
}

/**
 * Platform Fa lures are  ssues  n t  core Product M xer log c  self wh ch prevent a p pel ne from
 * properly execut ng. T se fa lures are t  respons b l y of t  Product M xer team.
 */
tra  PlatformFa lure extends P pel neFa lureCategory {
  overr de val categoryNa : Str ng = "PlatformFa lure"
}

/**
 * P pel ne execut on fa led due to an unexpected error  n Product M xer.
 *
 * Execut onFa led  nd cates a bug w h t  core Product M xer execut on log c rat r than w h a
 * spec f c product. For example, a bug  n P pel neBu lder lead ng to us return ng a
 * ProductP pel neResult that ne  r succeeded nor fa led.
 */
case object Execut onFa led extends PlatformFa lure {
  overr de val fa lureNa : Str ng = "Execut onFa led"
}

/**
 * P pel ne execut on fa led due to a feature hydrat on fa lure.
 *
 * FeatureHydrat onFa led  nd cates that t  underly ng hydrat on for a feature def ned  n a hydrator
 * fa led (e.g, typ cally from a RPC call fa l ng).
 */
case object FeatureHydrat onFa led extends PlatformFa lure {
  overr de val fa lureNa : Str ng = "FeatureHydrat onFa led"
}
