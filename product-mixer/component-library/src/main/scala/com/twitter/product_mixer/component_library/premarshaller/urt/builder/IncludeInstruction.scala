package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.p pel ne.HasP pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

tra   nclude nstruct on[-Query <: P pel neQuery] { self =>
  def apply(query: Query, entr es: Seq[T  l neEntry]): Boolean

  def  nverse():  nclude nstruct on[Query] = new  nclude nstruct on[Query] {
    def apply(query: Query, entr es: Seq[T  l neEntry]): Boolean = !self.apply(query, entr es)
  }
}

object Always nclude extends  nclude nstruct on[P pel neQuery] {
  overr de def apply(query: P pel neQuery, entr es: Seq[T  l neEntry]): Boolean = true
}

object  ncludeOnF rstPage extends  nclude nstruct on[P pel neQuery w h HasP pel neCursor[_]] {
  overr de def apply(
    query: P pel neQuery w h HasP pel neCursor[_],
    entr es: Seq[T  l neEntry]
  ): Boolean = query. sF rstPage
}

object  ncludeAfterF rstPage extends  nclude nstruct on[P pel neQuery w h HasP pel neCursor[_]] {
  overr de def apply(
    query: P pel neQuery w h HasP pel neCursor[_],
    entr es: Seq[T  l neEntry]
  ): Boolean = !query. sF rstPage
}
