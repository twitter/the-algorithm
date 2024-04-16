package com.tw ter.follow_recom ndat ons.models

 mport com.tw ter.follow_recom ndat ons.{thr ftscala => t}
 mport com.tw ter.t  l nes.conf gap ._

object FeatureValue {
  def fromThr ft(thr ftFeatureValue: t.FeatureValue): FeatureValue = thr ftFeatureValue match {
    case t.FeatureValue.Pr m  veValue(t.Pr m  veFeatureValue.BoolValue(bool)) =>
      BooleanFeatureValue(bool)
    case t.FeatureValue.Pr m  veValue(t.Pr m  veFeatureValue.StrValue(str ng)) =>
      Str ngFeatureValue(str ng)
    case t.FeatureValue.Pr m  veValue(t.Pr m  veFeatureValue. ntValue( nt)) =>
      NumberFeatureValue( nt)
    case t.FeatureValue.Pr m  veValue(t.Pr m  veFeatureValue.LongValue(long)) =>
      NumberFeatureValue(long)
    case t.FeatureValue.Pr m  veValue(t.Pr m  veFeatureValue.UnknownUn onF eld(f eld)) =>
      throw new UnknownFeatureValueExcept on(s"Pr m  ve: ${f eld.f eld.na }")
    case t.FeatureValue.UnknownUn onF eld(f eld) =>
      throw new UnknownFeatureValueExcept on(f eld.f eld.na )
  }
}

class UnknownFeatureValueExcept on(f eldNa : Str ng)
    extends Except on(s"Unknown FeatureValue na   n thr ft: ${f eldNa }")
