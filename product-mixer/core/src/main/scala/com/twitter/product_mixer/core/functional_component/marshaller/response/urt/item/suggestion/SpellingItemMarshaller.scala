package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.suggest on

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.suggest on.Spell ng em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Spell ng emMarshaller @ nject() (
  textResultMarshaller: TextResultMarshaller,
  spell ngAct onTypeMarshaller: Spell ngAct onTypeMarshaller) {

  def apply(spell ng em: Spell ng em): urt.T  l ne emContent = {
    urt.T  l ne emContent.Spell ng(
      urt.Spell ng(
        spell ngResult = textResultMarshaller(spell ng em.textResult),
        spell ngAct on = spell ng em.spell ngAct onType.map(spell ngAct onTypeMarshaller(_)),
        or g nalQuery = spell ng em.or g nalQuery
      )
    )
  }
}
