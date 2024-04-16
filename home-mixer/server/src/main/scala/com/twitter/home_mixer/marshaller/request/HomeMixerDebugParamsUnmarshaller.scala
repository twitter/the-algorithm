package com.tw ter.ho _m xer.marshaller.request

 mport com.tw ter.ho _m xer.model.request.Ho M xerDebugOpt ons
 mport com.tw ter.ho _m xer.{thr ftscala => t}
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.request.FeatureValueUnmarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.request.DebugParams
 mport com.tw ter.ut l.T  
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Ho M xerDebugParamsUnmarshaller @ nject() (
  featureValueUnmarshaller: FeatureValueUnmarshaller) {

  def apply(debugParams: t.DebugParams): DebugParams = {
    DebugParams(
      featureOverr des = debugParams.featureOverr des.map { map =>
        map.mapValues(featureValueUnmarshaller(_)).toMap
      },
      debugOpt ons = debugParams.debugOpt ons.map { opt ons =>
        Ho M xerDebugOpt ons(
          requestT  Overr de = opt ons.requestT  Overr deM ll s.map(T  .fromM ll seconds)
        )
      }
    )
  }
}
