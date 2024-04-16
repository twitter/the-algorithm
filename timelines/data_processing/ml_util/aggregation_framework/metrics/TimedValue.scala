package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs

 mport com.tw ter.ut l.T  

/**
 * Case class wrapp ng a (value, t  stamp) tuple.
 * All aggregate  tr cs must operate over t  class
 * to ensure   can  mple nt decay and half l ves for t m.
 * T   s translated to an algeb rd DecayedValue under t  hood.
 *
 * @param value Value be ng wrapped
 * @param t  stamp T   after epoch at wh ch value  s be ng  asured
 */
case class T  dValue[T](value: T, t  stamp: T  )
