package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.TransportMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.ReplaceEntryT  l ne nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ReplaceEntry nstruct onMarshaller @ nject() (
  t  l neEntryMarshaller: T  l neEntryMarshaller) {

  def apply( nstruct on: ReplaceEntryT  l ne nstruct on): urt.ReplaceEntry = {
    val  nstruct onEntry =  nstruct on.entry
    urt.ReplaceEntry(
      entry dToReplace =  nstruct onEntry.entry dToReplace
        .getOrElse(throw new M ss ngEntryToReplaceExcept on( nstruct onEntry)),
      entry = t  l neEntryMarshaller( nstruct onEntry)
    )
  }
}

class M ss ngEntryToReplaceExcept on(entry: T  l neEntry)
    extends  llegalArgu ntExcept on(
      s"M ss ng entry  D to replace ${TransportMarshaller.getS mpleNa (entry.getClass)}")
