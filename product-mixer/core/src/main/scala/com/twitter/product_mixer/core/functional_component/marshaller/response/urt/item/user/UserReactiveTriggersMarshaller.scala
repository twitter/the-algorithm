package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.user

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.react on.T  l neReact onMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.user.UserReact veTr ggers
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class UserReact veTr ggersMarshaller @ nject() (
  t  l neReact onMarshaller: T  l neReact onMarshaller) {

  def apply(userReact veTr ggers: UserReact veTr ggers): urt.UserReact veTr ggers = {
    urt.UserReact veTr ggers(
      onFollow = userReact veTr ggers.onFollow.map(t  l neReact onMarshaller(_)))
  }
}
