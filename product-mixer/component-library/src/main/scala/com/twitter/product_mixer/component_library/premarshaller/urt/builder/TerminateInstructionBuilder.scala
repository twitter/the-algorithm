package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.BottomTerm nat on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.Term nateT  l ne nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neTerm nat onD rect on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.TopAndBottomTerm nat on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.TopTerm nat on
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

sealed tra  Term nate nstruct onBu lder[Query <: P pel neQuery]
    extends Urt nstruct onBu lder[Query, Term nateT  l ne nstruct on] {

  def d rect on: T  l neTerm nat onD rect on

  overr de def bu ld(
    query: Query,
    entr es: Seq[T  l neEntry]
  ): Seq[Term nateT  l ne nstruct on] =
     f ( nclude nstruct on(query, entr es))
      Seq(Term nateT  l ne nstruct on(term nateT  l neD rect on = d rect on))
    else Seq.empty
}

case class Term nateTop nstruct onBu lder[Query <: P pel neQuery](
  overr de val  nclude nstruct on:  nclude nstruct on[Query] = Always nclude)
    extends Term nate nstruct onBu lder[Query] {

  overr de val d rect on = TopTerm nat on
}

case class Term nateBottom nstruct onBu lder[Query <: P pel neQuery](
  overr de val  nclude nstruct on:  nclude nstruct on[Query] = Always nclude)
    extends Term nate nstruct onBu lder[Query] {

  overr de val d rect on = BottomTerm nat on
}

case class Term nateTopAndBottom nstruct onBu lder[Query <: P pel neQuery](
  overr de val  nclude nstruct on:  nclude nstruct on[Query] = Always nclude)
    extends Term nate nstruct onBu lder[Query] {

  overr de val d rect on = TopAndBottomTerm nat on
}
