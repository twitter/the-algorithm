package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.t  l ne_module

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Carousel
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.CompactCarousel
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Conversat onTree
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Gr dCarousel
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.ModuleD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Vert cal
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Vert calConversat on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Vert calW hContextL ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Vert calGr d
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}

@S ngleton
class ModuleD splayTypeMarshaller @ nject() () {

  def apply(d splayType: ModuleD splayType): urt.ModuleD splayType = d splayType match {
    case Vert cal => urt.ModuleD splayType.Vert cal
    case Carousel => urt.ModuleD splayType.Carousel
    case Vert calW hContextL ne => urt.ModuleD splayType.Vert calW hContextL ne
    case Vert calConversat on => urt.ModuleD splayType.Vert calConversat on
    case Conversat onTree => urt.ModuleD splayType.Conversat onTree
    case Gr dCarousel => urt.ModuleD splayType.Gr dCarousel
    case CompactCarousel => urt.ModuleD splayType.CompactCarousel
    case Vert calGr d => urt.ModuleD splayType.Vert calGr d
  }
}
