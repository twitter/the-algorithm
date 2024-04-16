package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.Module em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Module emMarshaller @ nject() (
  t  l ne emMarshaller: T  l ne emMarshaller,
  module emTreeD splayMarshaller: Module emTreeD splayMarshaller) {

  def apply(module em: Module em, moduleEntry d: Str ng): urt.Module em = urt.Module em(
    /* Module  ems have an  dent f er compr s ng both t  module entry  d and t  module  em  d.
    So  URT cl ents w ll dedupl cate globally across d fferent modules.
    Us ng t  entry  d as a pref x ensures that dedupl cat on only happens w h n a s ngle module,
    wh ch   bel eve better al gns w h eng neers'  ntent ons. */
    entry d = moduleEntry d + "-" + module em. em.entry dent f er,
     em = t  l ne emMarshaller(module em. em),
    d spensable = module em.d spensable,
    treeD splay = module em.treeD splay.map(module emTreeD splayMarshaller.apply)
  )
}
