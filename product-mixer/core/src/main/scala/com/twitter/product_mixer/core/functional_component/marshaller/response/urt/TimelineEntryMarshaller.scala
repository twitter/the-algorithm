package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T  l neEntryMarshaller @ nject() (
  t  l neEntryContentMarshaller: T  l neEntryContentMarshaller) {

  def apply(entry: T  l neEntry): urt.T  l neEntry =
    urt.T  l neEntry(
      entry d = entry.entry dent f er,
      sort ndex = entry.sort ndex.getOrElse(throw new T  l neEntryM ss ngSort ndexExcept on),
      content = t  l neEntryContentMarshaller(entry),
      exp ryT   = entry.exp rat onT   nM ll s
    )
}

class T  l neEntryM ss ngSort ndexExcept on
    extends UnsupportedOperat onExcept on("T  l ne entry m ss ng sort  ndex")
