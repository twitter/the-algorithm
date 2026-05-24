package com.twitter.follow_recommendations.models

import com.twitter.follow_recommendations.{thriftscala => t}
import com.twitter.timelines.configapi._

object FeatureValue {
  def fromThrift(thriftFeatureValue: t.FeatureValue): FeatureValue = thriftFeatureValue match {
    case t.FeatureValue.PrimitiveValue(t.PrimitiveFeatureValue.BoolValue(bool)) =>
      BooleanFeatureValue(bool)
    case t.FeatureValue.PrimitiveValue(t.PrimitiveFeatureValue.StrValue(string)) =>
      StringFeatureValue(string)
    case t.FeatureValue.PrimitiveValue(t.PrimitiveFeatureValue.IntValue(int)) =>
      NumberFeatureValue(int)
    case t.FeatureValue.PrimitiveValue(t.PrimitiveFeatureValue.LongValue(long)) =>
      NumberFeatureValue(long)
    case t.FeatureValue.PrimitiveValue(t.PrimitiveFeatureValue.UnknownUnionField(field)) =>
      throw new UnknownFeatureValueException(s"Primitive: ${field.field.name}")
    case t.FeatureValue.UnknownUnionField(field) =>
      throw new UnknownFeatureValueException(field.field.name)
  }
}

class UnknownFeatureValueException(fieldName: String)
    extends Exception(s"Unknown FeatureValue name in thrift: ${fieldName}")
