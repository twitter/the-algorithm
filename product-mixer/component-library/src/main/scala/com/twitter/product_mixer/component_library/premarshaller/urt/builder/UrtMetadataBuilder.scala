package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne tadata
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.str ngcenter.cl ent.Str ngCenter
 mport com.tw ter.str ngcenter.cl ent.core.ExternalStr ng

tra  BaseUrt tadataBu lder[-Query <: P pel neQuery] {
  def bu ld(
    query: Query,
    entr es: Seq[T  l neEntry]
  ): T  l ne tadata
}

case class Urt tadataBu lder(
  t le: Opt on[Str ng] = None,
  scr beConf gBu lder: Opt on[T  l neScr beConf gBu lder[P pel neQuery]])
    extends BaseUrt tadataBu lder[P pel neQuery] {

  overr de def bu ld(
    query: P pel neQuery,
    entr es: Seq[T  l neEntry]
  ): T  l ne tadata = T  l ne tadata(
    t le = t le,
    scr beConf g = scr beConf gBu lder.flatMap(_.bu ld(query, entr es))
  )
}

case class Urt tadataStr ngCenterBu lder(
  t leKey: ExternalStr ng,
  scr beConf gBu lder: Opt on[T  l neScr beConf gBu lder[P pel neQuery]],
  str ngCenter: Str ngCenter)
    extends BaseUrt tadataBu lder[P pel neQuery] {

  overr de def bu ld(
    query: P pel neQuery,
    entr es: Seq[T  l neEntry]
  ): T  l ne tadata = T  l ne tadata(
    t le = So (str ngCenter.prepare(t leKey)),
    scr beConf g = scr beConf gBu lder.flatMap(_.bu ld(query, entr es))
  )
}
