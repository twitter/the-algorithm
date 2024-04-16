package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.BottomTerm nat on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.Term nateT  l ne nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.TopTerm nat on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.TopAndBottomTerm nat on
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Term nateT  l ne nstruct onMarshaller @ nject() () {

  def apply( nstruct on: Term nateT  l ne nstruct on): urt.Term nateT  l ne =
    urt.Term nateT  l ne(
      d rect on =  nstruct on.term nateT  l neD rect on match {
        case TopTerm nat on => urt.T  l neTerm nat onD rect on.Top
        case BottomTerm nat on => urt.T  l neTerm nat onD rect on.Bottom
        case TopAndBottomTerm nat on => urt.T  l neTerm nat onD rect on.TopAndBottom
      }
    )
}
