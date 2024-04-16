package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.art cle

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Soc alContextMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.art cle.Art cle em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Art cle emMarshaller @ nject() (
  art cleD splayTypeMarshaller: Art cleD splayTypeMarshaller,
  soc alContextMarshaller: Soc alContextMarshaller,
  art cleSeedTypeMarshaller: Art cleSeedTypeMarshaller) {
  def apply(art cle em: Art cle em): urt.T  l ne emContent =
    urt.T  l ne emContent.Art cle(
      urt.Art cle(
         d = art cle em. d,
        d splayType = art cle em.d splayType.map(art cleD splayTypeMarshaller(_)),
        soc alContext = art cle em.soc alContext.map(soc alContextMarshaller(_)),
        art cleSeedType = So (art cleSeedTypeMarshaller(art cle em.art cleSeedType))
      )
    )
}
