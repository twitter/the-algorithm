package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module

 mport com.tw ter.product_m xer.core.funct onal_component.conf gap .Stat cParam
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseModuleD splayTypeBu lder
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Carousel
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.CompactCarousel
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Conversat onTree
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Gr dCarousel
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.ModuleD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Vert cal
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Vert calConversat on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Vert calGr d
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Vert calW hContextL ne
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .Param

object WhoToFollowModuleD splayType extends Enu rat on {
  type ModuleD splayType = Value

  val Carousel = Value
  val CompactCarousel = Value
  val Conversat onTree = Value
  val Gr dCarousel = Value
  val Vert cal = Value
  val Vert calConversat on = Value
  val Vert calGr d = Value
  val Vert calW hContextL ne = Value
}

case class ParamWhoToFollowModuleD splayTypeBu lder(
  d splayTypeParam: Param[WhoToFollowModuleD splayType.Value] =
    Stat cParam(WhoToFollowModuleD splayType.Vert cal))
    extends BaseModuleD splayTypeBu lder[P pel neQuery, Un versalNoun[Any]] {

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[Un versalNoun[Any]]]
  ): ModuleD splayType = {
    val d splayType = query.params(d splayTypeParam)
    d splayType match {
      case WhoToFollowModuleD splayType.Carousel => Carousel
      case WhoToFollowModuleD splayType.CompactCarousel => CompactCarousel
      case WhoToFollowModuleD splayType.Conversat onTree => Conversat onTree
      case WhoToFollowModuleD splayType.Gr dCarousel => Gr dCarousel
      case WhoToFollowModuleD splayType.Vert cal => Vert cal
      case WhoToFollowModuleD splayType.Vert calConversat on => Vert calConversat on
      case WhoToFollowModuleD splayType.Vert calGr d => Vert calGr d
      case WhoToFollowModuleD splayType.Vert calW hContextL ne => Vert calW hContextL ne
    }
  }
}
