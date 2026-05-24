package com.twitter.ann.common

import com.twitter.bijection.{Bijection, Injection}

// Class providing commonly used injections that can be used directly with ANN apis.
// Injection  prefixed with `J` can be used in java directly with ANN apis.
object AnnInjections {
  val LongInjection: Injection[Long, Array[Byte]] = Injection.long2BigEndian

  def StringInjection: Injection[String, Array[Byte]] = Injection.utf8

  def IntInjection: Injection[Int, Array[Byte]] = Injection.int2BigEndian

  val JLongInjection: Injection[java.lang.Long, Array[Byte]] =
    Bijection.long2Boxed
      .asInstanceOf[Bijection[Long, java.lang.Long]]
      .inverse
      .andThen(LongInjection)

  val JStringInjection: Injection[java.lang.String, Array[Byte]] =
    StringInjection

  val JIntInjection: Injection[java.lang.Integer, Array[Byte]] =
    Bijection.int2Boxed
      .asInstanceOf[Bijection[Int, java.lang.Integer]]
      .inverse
      .andThen(IntInjection)
}
