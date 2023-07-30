package com.X.product_mixer.core.functional_component.marshaller.request

import com.X.product_mixer.core.{thriftscala => t}
import com.X.timelines.configapi.BooleanFeatureValue
import com.X.timelines.configapi.FeatureValue
import com.X.timelines.configapi.NumberFeatureValue
import com.X.timelines.configapi.StringFeatureValue
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeatureValueUnmarshaller @Inject() () {

  def apply(featureValue: t.FeatureValue): FeatureValue = featureValue match {
    case t.FeatureValue.PrimitiveValue(t.PrimitiveFeatureValue.BoolValue(bool)) =>
      BooleanFeatureValue(bool)
    case t.FeatureValue.PrimitiveValue(t.PrimitiveFeatureValue.StrValue(string)) =>
      StringFeatureValue(string)
    case t.FeatureValue.PrimitiveValue(t.PrimitiveFeatureValue.IntValue(int)) =>
      NumberFeatureValue(int)
    case t.FeatureValue.PrimitiveValue(t.PrimitiveFeatureValue.LongValue(long)) =>
      NumberFeatureValue(long)
    case t.FeatureValue.PrimitiveValue(t.PrimitiveFeatureValue.DoubleValue(double)) =>
      NumberFeatureValue(double)
    case t.FeatureValue.PrimitiveValue(t.PrimitiveFeatureValue.UnknownUnionField(field)) =>
      throw new UnsupportedOperationException(
        s"Unknown feature value primitive: ${field.field.name}")
    case t.FeatureValue.UnknownUnionField(field) =>
      throw new UnsupportedOperationException(s"Unknown feature value: ${field.field.name}")
  }
}
