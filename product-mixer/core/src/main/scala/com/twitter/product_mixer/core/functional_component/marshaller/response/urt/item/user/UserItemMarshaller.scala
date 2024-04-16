package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.user

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Soc alContextMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.promoted.Promoted tadataMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.user.User em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class User emMarshaller @ nject() (
  userD splayTypeMarshaller: UserD splayTypeMarshaller,
  promoted tadataMarshaller: Promoted tadataMarshaller,
  soc alContextMarshaller: Soc alContextMarshaller,
  userReact veTr ggersMarshaller: UserReact veTr ggersMarshaller) {

  def apply(user em: User em): urt.T  l ne emContent =
    urt.T  l ne emContent.User(
      urt.User(
         d = user em. d,
        d splayType = userD splayTypeMarshaller(user em.d splayType),
        promoted tadata = user em.promoted tadata.map(promoted tadataMarshaller(_)),
        soc alContext = user em.soc alContext.map(soc alContextMarshaller(_)),
        enableReact veBlend ng = user em.enableReact veBlend ng,
        react veTr ggers = user em.react veTr ggers.map(userReact veTr ggersMarshaller(_))
      )
    )
}
