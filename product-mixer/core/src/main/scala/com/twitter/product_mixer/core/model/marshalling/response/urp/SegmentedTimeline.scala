package com.tw ter.product_m xer.core.model.marshall ng.response.urp

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neScr beConf g

case class Seg ntedT  l ne(
   d: Str ng,
  labelText: Str ng,
  t  l ne: T  l neKey,
  scr beConf g: Opt on[T  l neScr beConf g] = None,
  refresh ntervalSec: Opt on[Long] = None)
