package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.top c

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.Top cFollowPrompt em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Top cFollowPrompt emMarshaller @ nject() (
  d splayTypeMarshaller: Top cFollowPromptD splayTypeMarshaller) {

  def apply(top cFollowPrompt em: Top cFollowPrompt em): urt.T  l ne emContent = {
    urt.T  l ne emContent.Top cFollowPrompt(
      urt.Top cFollowPrompt(
        top c d = top cFollowPrompt em. d.toStr ng,
        d splayType = d splayTypeMarshaller(top cFollowPrompt em.top cFollowPromptD splayType),
        follow ncent veT le = top cFollowPrompt em.follow ncent veT le,
        follow ncent veText = top cFollowPrompt em.follow ncent veText
      )
    )
  }
}
