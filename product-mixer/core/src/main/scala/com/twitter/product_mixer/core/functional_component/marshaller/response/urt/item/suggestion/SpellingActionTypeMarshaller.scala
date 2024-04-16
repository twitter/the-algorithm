package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.suggest on

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.suggest on._
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject.S ngleton

@S ngleton
class Spell ngAct onTypeMarshaller {

  def apply(spell ngAct onType: Spell ngAct onType): urt.Spell ngAct onType =
    spell ngAct onType match {
      case ReplaceSpell ngAct onType => urt.Spell ngAct onType.Replace
      case ExpandSpell ngAct onType => urt.Spell ngAct onType.Expand
      case SuggestSpell ngAct onType => urt.Spell ngAct onType.Suggest
    }
}
