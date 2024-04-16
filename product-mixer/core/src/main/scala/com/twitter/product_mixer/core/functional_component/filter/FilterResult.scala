package com.tw ter.product_m xer.core.funct onal_component.f lter

/** `Cand date`s  re `kept` and `removed` by a [[F lter]] */
case class F lterResult[+Cand date](kept: Seq[Cand date], removed: Seq[Cand date])
