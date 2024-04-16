package com.tw ter.t etyp e.cach ng

 mport com.tw ter. o.Buf
 mport com.tw ter.ut l.T  

/**
 * How to store values of type V  n cac . T   ncludes w t r a
 * g ven value  s cac able, how to ser al ze  , w n   should
 * exp re from cac , and how to  nterpret byte patterns from cac .
 */
tra  ValueSer al zer[V] {

  /**
   * Prepare t  value for storage  n cac . W n a [[So ]]  s
   * returned, t  [[Buf]] should be a val d  nput to [[deser al ze]]
   * and t  [[T  ]] w ll be used as t  exp ry  n t   mcac d
   * command.  W n [[None]]  s returned,    nd cates that t  value
   * cannot or should not be wr ten back to cac .
   *
   * T  most common use case for return ng None  s cach ng Try
   * values, w re certa n except onal values encode a cac able state
   * of a value.  n part cular, Throw(NotFound)  s commonly used to
   * encode a m ss ng value, and   usually want to cac  those
   * negat ve lookups, but   don't want to cac  e.g. a t  out
   * except on.
   *
   * @return a pa r of exp ry t   for t  cac  entry and t  bytes
   *   to store  n cac .  f   do not want t  value to expl c ly
   *   exp re, use T  .Top as t  exp ry.
   */
  def ser al ze(value: V): Opt on[(T  , Buf)]

  /**
   * Deser al ze a value found  n cac . T  funct on converts t 
   * bytes found  n  mcac  to a [[Cac Result]].  n general,  
   * probably want to return [[Cac Result.Fresh]] or
   * [[Cac Result.Stale]], but   are free to return any of t 
   * range of [[Cac Result]]s, depend ng on t  behav or that  
   * want.
   *
   * T   s a total funct on because  n t  common use case, t 
   * bytes stored  n cac  w ll be appropr ate for t 
   * ser al zer. T   thod  s free to throw any except on  f t 
   * bytes are not val d.
   */
  def deser al ze(ser al zedValue: Buf): Cac Result[V]
}
