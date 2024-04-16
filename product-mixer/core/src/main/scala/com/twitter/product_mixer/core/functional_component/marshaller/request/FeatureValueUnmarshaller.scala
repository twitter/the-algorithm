package com.tw ter.product_m xer.core.funct onal_component.marshaller.request

 mport com.tw ter.product_m xer.core.{thr ftscala => t}
 mport com.tw ter.t  l nes.conf gap .BooleanFeatureValue
 mport com.tw ter.t  l nes.conf gap .FeatureValue
 mport com.tw ter.t  l nes.conf gap .NumberFeatureValue
 mport com.tw ter.t  l nes.conf gap .Str ngFeatureValue
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class FeatureValueUnmarshaller @ nject() () {

  def apply(featureValue: t.FeatureValue): FeatureValue = featureValue match {
    case t.FeatureValue.Pr m  veValue(t.Pr m  veFeatureValue.BoolValue(bool)) =>
      BooleanFeatureValue(bool)
    case t.FeatureValue.Pr m  veValue(t.Pr m  veFeatureValue.StrValue(str ng)) =>
      Str ngFeatureValue(str ng)
    case t.FeatureValue.Pr m  veValue(t.Pr m  veFeatureValue. ntValue( nt)) =>
      NumberFeatureValue( nt)
    case t.FeatureValue.Pr m  veValue(t.Pr m  veFeatureValue.LongValue(long)) =>
      NumberFeatureValue(long)
    case t.FeatureValue.Pr m  veValue(t.Pr m  veFeatureValue.DoubleValue(double)) =>
      NumberFeatureValue(double)
    case t.FeatureValue.Pr m  veValue(t.Pr m  veFeatureValue.UnknownUn onF eld(f eld)) =>
      throw new UnsupportedOperat onExcept on(
        s"Unknown feature value pr m  ve: ${f eld.f eld.na }")
    case t.FeatureValue.UnknownUn onF eld(f eld) =>
      throw new UnsupportedOperat onExcept on(s"Unknown feature value: ${f eld.f eld.na }")
  }
}
