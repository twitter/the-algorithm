package com.tw ter.product_m xer.component_l brary.premarshaller.sl ce.bu lder

 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.Sl ce em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

tra  Should nclude[-Query <: P pel neQuery] {
  def apply(query: Query,  ems: Seq[Sl ce em]): Boolean
}

object Always nclude extends Should nclude[P pel neQuery] {
  overr de def apply(query: P pel neQuery, entr es: Seq[Sl ce em]): Boolean = true
}

object  ncludeOnNonEmpty extends Should nclude[P pel neQuery] {
  overr de def apply(query: P pel neQuery, entr es: Seq[Sl ce em]): Boolean = entr es.nonEmpty
}
