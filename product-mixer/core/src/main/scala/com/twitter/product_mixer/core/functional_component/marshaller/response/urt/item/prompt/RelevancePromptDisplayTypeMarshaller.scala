package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.prompt

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.prompt._
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject.S ngleton

@S ngleton
class RelevancePromptD splayTypeMarshaller {

  def apply(
    relevancePromptD splayType: RelevancePromptD splayType
  ): urt.RelevancePromptD splayType = relevancePromptD splayType match {
    case Normal => urt.RelevancePromptD splayType.Normal
    case Compact => urt.RelevancePromptD splayType.Compact
    case Large => urt.RelevancePromptD splayType.Large
    case ThumbsUpAndDown => urt.RelevancePromptD splayType.ThumbsUpAndDown
  }
}
