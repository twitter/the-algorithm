package com.tw ter.follow_recom ndat ons.products.common

abstract class ProductExcept on( ssage: Str ng) extends Except on( ssage)

class M ss ngF eldExcept on(productRequest: ProductRequest, f eldNa : Str ng)
    extends ProductExcept on(
      s"M ss ng ${f eldNa } f eld for ${productRequest.recom ndat onRequest.d splayLocat on} request")
