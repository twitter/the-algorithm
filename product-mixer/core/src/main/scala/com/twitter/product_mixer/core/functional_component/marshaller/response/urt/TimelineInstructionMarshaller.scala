package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.AddEntr esT  l ne nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.AddToModuleT  l ne nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.ClearCac T  l ne nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.MarkEntr esUnread nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.P nEntryT  l ne nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.ReplaceEntryT  l ne nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.ShowAlert nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.ShowCover nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.Term nateT  l ne nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne nstruct on
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T  l ne nstruct onMarshaller @ nject() (
  addEntr es nstruct onMarshaller: AddEntr es nstruct onMarshaller,
  addToModule nstruct onMarshaller: AddToModule nstruct onMarshaller,
  markEntr esUnread nstruct onMarshaller: MarkEntr esUnread nstruct onMarshaller,
  p nEntry nstruct onMarshaller: P nEntry nstruct onMarshaller,
  replaceEntry nstruct onMarshaller: ReplaceEntry nstruct onMarshaller,
  showAlert nstruct onMarshaller: ShowAlert nstruct onMarshaller,
  term nateT  l ne nstruct onMarshaller: Term nateT  l ne nstruct onMarshaller,
  coverMarshaller: CoverMarshaller) {

  def apply( nstruct on: T  l ne nstruct on): urt.T  l ne nstruct on =  nstruct on match {
    case  nstruct on: AddEntr esT  l ne nstruct on =>
      urt.T  l ne nstruct on.AddEntr es(addEntr es nstruct onMarshaller( nstruct on))
    case  nstruct on: AddToModuleT  l ne nstruct on =>
      urt.T  l ne nstruct on.AddToModule(addToModule nstruct onMarshaller( nstruct on))
    case _: ClearCac T  l ne nstruct on =>
      urt.T  l ne nstruct on.ClearCac (urt.ClearCac ())
    case  nstruct on: MarkEntr esUnread nstruct on =>
      urt.T  l ne nstruct on.MarkEntr esUnread(
        markEntr esUnread nstruct onMarshaller( nstruct on)
      )
    case  nstruct on: P nEntryT  l ne nstruct on =>
      urt.T  l ne nstruct on.P nEntry(p nEntry nstruct onMarshaller( nstruct on))
    case  nstruct on: ReplaceEntryT  l ne nstruct on =>
      urt.T  l ne nstruct on.ReplaceEntry(replaceEntry nstruct onMarshaller( nstruct on))
    case  nstruct on: ShowCover nstruct on =>
      urt.T  l ne nstruct on.ShowCover(coverMarshaller( nstruct on.cover))
    case  nstruct on: ShowAlert nstruct on =>
      urt.T  l ne nstruct on.ShowAlert(showAlert nstruct onMarshaller( nstruct on))
    case  nstruct on: Term nateT  l ne nstruct on =>
      urt.T  l ne nstruct on.Term nateT  l ne(term nateT  l ne nstruct onMarshaller( nstruct on))
  }
}
