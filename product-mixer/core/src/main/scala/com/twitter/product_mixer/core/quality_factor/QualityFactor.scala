package com.tw ter.product_m xer.core.qual y_factor

/**
 * [[Qual yFactor]]  s an abstract number that enables a feedback loop to control operat on costs and ult mately
 * ma nta n t  operat on success rate. Abstractly,  f operat ons/calls are too expens ve (such as h gh
 * latenc es), t  qual y factor should go down, wh ch  lps future calls to ease t  r demand/load (such as
 * reduc ng request w dth);  f ops/calls are fast, t  qual y factor should go up, so   can  ncur more load.
 *
 * @note to avo d over ad t  underly ng state may so t  s not be synchron zed.
 *        f a part of an appl cat on  s un althy,   w ll l kely be un althy for all threads,
 *         w ll eventually result  n a close-enough qual y factor value for all thread's v ew of t  state.
 *
 *        n extre ly low volu  scenar os such as manual test ng  n a develop nt env ron nt,
 *        's poss ble that d fferent threads w ll have vastly d fferent v ews of t  underl ng state,
 *       but  n pract ce,  n product on systems, t y w ll be close-enough.
 */
tra  Qual yFactor[ nput] { self =>

  /** get t  current [[Qual yFactor]]'s value */
  def currentValue: Double

  def conf g: Qual yFactorConf g

  /** update of t  current `factor` value */
  def update( nput:  nput): Un 

  /** a [[Qual yFactorObserver]] for t  [[Qual yFactor]] */
  def bu ldObserver(): Qual yFactorObserver

  overr de def toStr ng: Str ng = {
    self.getClass.getS mpleNa .str pSuff x("$")
  }
}
