package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module

/**
 *  T  tra   s used for Module  D generat on. Cl ents are safe to  gnore t  code unless t y
 *  have a spec f c use case that requ res hard-coded, spec f c, module  ds.   n that scenar o,
 *  t y can use t  [[ManualModule d]] case class.
 */
sealed tra  Module dGenerat on {
  val module d: Long
}

object Module dGenerat on {
  def apply(module d: Long): Module dGenerat on = module d match {
    case module d  f Automat cUn queModule d. sAutomat cUn queModule d(module d) =>
      Automat cUn queModule d(module d)
    case module d => ManualModule d(module d)
  }
}

/**
 * Generate un que  ds for each module, wh ch results  n un que URT entry ds
 * for each module even  f t y share t  sa  entryNa space.
 * T   s t  default and recom nded use case.
 * Note that t  module  d value  s just a placeholder
 */
case class Automat cUn queModule d pr vate (module d: Long = 0L) extends Module dGenerat on {
  def w hOffset(offset: Long): Automat cUn queModule d = copy(
    Automat cUn queModule d. dRange.m n + offset)
}

object Automat cUn queModule d {
  //   use a spec f c nu r c range to track w t r  Ds should be automat cally generated.
  val  dRange: Range = Range(-10000, -1000)

  def apply(): Automat cUn queModule d = Automat cUn queModule d( dRange.m n)

  def  sAutomat cUn queModule d(module d: Long): Boolean =  dRange.conta ns(module d)
}

/**
 * ManualModule d should normally not be requ red, but  s  lpful  f t 
 * entry d of t  module must be controlled. A scenar o w re t  may be
 * requ red  s  f a s ngle cand date s ce returns mult ple modules, and
 * each module has t  sa  presentat on (e.g.  ader, Footer). By sett ng
 * d fferent  Ds,   s gnal to t  platform that each module should be separate
 * by us ng a d fferent manual  d.
 */
case class ManualModule d(overr de val module d: Long) extends Module dGenerat on {
  // Negat ve module  Ds are reserved for  nternal usage
   f (module d < 0) throw new  llegalArgu ntExcept on("module d must be a pos  ve number")
}
