package com.twitter.product_mixer.core.functional_component.configapi.registry

import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil.DefinedFeatureName
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil.EnumParamWithFeatureName
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil.EnumSeqParamWithFeatureName
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil.ValueFeatureName
import com.twitter.timelines.configapi.decider.HasDecider
import com.twitter.timelines.configapi.Bounded
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.timelines.configapi.OptionalOverride
import com.twitter.timelines.configapi.Param
import com.twitter.util.Duration

/** ParamConfig is used to configure overrides for [[Param]]s of various types */
trait ParamConfig {

  def booleanDeciderOverrides: Seq[Param[Boolean] with HasDecider] = Seq.empty

  def booleanFSOverrides: Seq[Param[Boolean] with FSName] = Seq.empty

  def optionalBooleanOverrides: Seq[
    (Param[Option[Boolean]], DefinedFeatureName, ValueFeatureName)
  ] = Seq.empty

  def enumFSOverrides: Seq[EnumParamWithFeatureName[_ <: Enumeration]] = Seq.empty

  def enumSeqFSOverrides: Seq[EnumSeqParamWithFeatureName[_ <: Enumeration]] = Seq.empty

  /**
   * Support for non-Duration supplied FS overrides (e.g. `timeFromStringFSOverrides`,
   * `timeFromNumberFSOverrides`, `getBoundedOptionalDurationFromMillisOverrides`) is not provided
   * as Duration is preferred
   */
  def boundedDurationFSOverrides: Seq[
    Param[Duration] with Bounded[Duration] with FSName with HasDurationConversion
  ] = Seq.empty

  /** Support for unbounded numeric FS overrides is not provided as bounded is preferred */
  def boundedIntFSOverrides: Seq[Param[Int] with Bounded[Int] with FSName] = Seq.empty

  def boundedOptionalIntOverrides: Seq[
    (Param[Option[Int]] with Bounded[Option[Int]], DefinedFeatureName, ValueFeatureName)
  ] = Seq.empty

  def intSeqFSOverrides: Seq[Param[Seq[Int]] with FSName] = Seq.empty

  def boundedLongFSOverrides: Seq[Param[Long] with Bounded[Long] with FSName] = Seq.empty

  def boundedOptionalLongOverrides: Seq[
    (Param[Option[Long]] with Bounded[Option[Long]], DefinedFeatureName, ValueFeatureName)
  ] = Seq.empty

  def longSeqFSOverrides: Seq[Param[Seq[Long]] with FSName] = Seq.empty

  def longSetFSOverrides: Seq[Param[Set[Long]] with FSName] = Seq.empty

  def boundedDoubleFSOverrides: Seq[Param[Double] with Bounded[Double] with FSName] = Seq.empty

  def boundedOptionalDoubleOverrides: Seq[
    (Param[Option[Double]] with Bounded[Option[Double]], DefinedFeatureName, ValueFeatureName)
  ] = Seq.empty

  def doubleSeqFSOverrides: Seq[Param[Seq[Double]] with FSName] = Seq.empty

  def stringFSOverrides: Seq[Param[String] with FSName] = Seq.empty

  def stringSeqFSOverrides: Seq[Param[Seq[String]] with FSName] = Seq.empty

  def optionalStringOverrides: Seq[(Param[Option[String]], DefinedFeatureName, ValueFeatureName)] =
    Seq.empty

  def gatedOverrides: Map[String, Seq[OptionalOverride[_]]] = Map.empty
}
