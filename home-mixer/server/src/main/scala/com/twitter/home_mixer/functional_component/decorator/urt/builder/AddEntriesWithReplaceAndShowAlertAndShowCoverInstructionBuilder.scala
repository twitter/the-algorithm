package com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder

 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.Always nclude
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder. nclude nstruct on
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.Urt nstruct onBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.AddEntr esT  l ne nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.Cover
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.ShowAlert
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class AddEntr esW hReplaceAndShowAlertAndCover nstruct onBu lder[Query <: P pel neQuery](
  overr de val  nclude nstruct on:  nclude nstruct on[Query] = Always nclude)
    extends Urt nstruct onBu lder[Query, AddEntr esT  l ne nstruct on] {

  overr de def bu ld(
    query: Query,
    entr es: Seq[T  l neEntry]
  ): Seq[AddEntr esT  l ne nstruct on] = {
     f ( nclude nstruct on(query, entr es)) {
      val entr esToAdd = entr es
        .f lterNot(_. s nstanceOf[ShowAlert])
        .f lterNot(_. s nstanceOf[Cover])
        .f lter(_.entry dToReplace. sEmpty)
       f (entr esToAdd.nonEmpty) Seq(AddEntr esT  l ne nstruct on(entr esToAdd))
      else Seq.empty
    } else
      Seq.empty
  }
}
