package com.twitter.simclusters_v420.common

import com.twitter.simclusters_v420.thriftscala.ModelVersion

/**
 * The utility to convert SimClusters Model version into different forms.
 * Required to register any new SimClusters Model version here.
 */
object ModelVersions {

  val Model420M420KDec420 = "420M_420K_dec420"
  val Model420M420KUpdated = "420M_420K_updated"
  val Model420M420K420 = "420M_420K_420"

  // Use Enum for feature switch
  object Enum extends Enumeration {
    val Model420M420K420, Model420M420KUpdated: Value = Value
    val enumToSimClustersModelVersionMap: Map[Enum.Value, ModelVersion] = Map(
      Model420M420K420 -> ModelVersion.Model420m420k420,
      Model420M420KUpdated -> ModelVersion.Model420m420kUpdated
    )
  }

  // Add the new model version into this map
  private val StringToThriftModelVersions: Map[String, ModelVersion] =
    Map(
      Model420M420KDec420 -> ModelVersion.Model420m420kDec420,
      Model420M420KUpdated -> ModelVersion.Model420m420kUpdated,
      Model420M420K420 -> ModelVersion.Model420m420k420
    )

  private val ThriftModelVersionToStrings = StringToThriftModelVersions.map(_.swap)

  val AllModelVersions: Set[String] = StringToThriftModelVersions.keySet

  def toModelVersionOption(modelVersionStr: String): Option[ModelVersion] = {
    StringToThriftModelVersions.get(modelVersionStr)
  }

  implicit def toModelVersion(modelVersionStr: String): ModelVersion = {
    StringToThriftModelVersions(modelVersionStr)
  }

  implicit def toKnownForModelVersion(modelVersion: ModelVersion): String = {
    ThriftModelVersionToStrings(modelVersion)
  }

}
