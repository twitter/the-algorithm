package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.promoted

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.SkAdNetworkData
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}

@S ngleton
class SkAdNetworkDataMarshaller @ nject() () {

  def apply(skAdNetworkData: SkAdNetworkData): urt.SkAdNetworkData =
    urt.SkAdNetworkData(
      vers on = skAdNetworkData.vers on,
      srcApp d = skAdNetworkData.srcApp d,
      dstApp d = skAdNetworkData.dstApp d,
      adNetwork d = skAdNetworkData.adNetwork d,
      campa gn d = skAdNetworkData.campa gn d,
       mpress onT   nM ll s = skAdNetworkData. mpress onT   nM ll s,
      nonce = skAdNetworkData.nonce,
      s gnature = skAdNetworkData.s gnature,
      f del yType = skAdNetworkData.f del yType
    )
}
