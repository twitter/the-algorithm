package com.twitter.simclusters_v420.scalding.embedding.common

import com.twitter.simclusters_v420.thriftscala._
import java.net.InetAddress
import java.net.UnknownHostException

object EmbeddingUtil {

  type UserId = Long
  type ClusterId = Int
  type ProducerId = Long
  type EmbeddingScore = Double
  type SemanticCoreEntityId = Long
  type HashtagId = String
  type Language = String

  implicit val internalIdOrdering: Ordering[InternalId] = Ordering.by {
    case InternalId.EntityId(id) => id.toString
    case InternalId.Hashtag(strId) => strId
    case InternalId.ClusterId(iid) => iid.toString
    case InternalId.LocaleEntityId(LocaleEntityId(entityId, lang)) => lang + entityId.toString
  }

  implicit val embeddingTypeOrdering: Ordering[EmbeddingType] = Ordering.by(_.getValue)

  /**
   * We do not need to group by model version since we are making the
   * This ordering holds the assumption that we would NEVER generate embeddings for two separate
   * SimClusters KnownFor versions under the same dataset.
   */
  implicit val SimClustersEmbeddingIdOrdering: Ordering[SimClustersEmbeddingId] = Ordering.by {
    case SimClustersEmbeddingId(embeddingType, _, internalId) => (embeddingType, internalId)
  }

  val ModelVersionPathMap: Map[ModelVersion, String] = Map(
    ModelVersion.Model420m420kDec420 -> "model_420m_420k_dec420",
    ModelVersion.Model420m420kUpdated -> "model_420m_420k_updated",
    ModelVersion.Model420m420k420 -> "model_420m_420k_420"
  )

  /**
   * Generates the HDFS output path in order to consolidate the offline embeddings datasets under
   * a common directory pattern.
   * Prepends "/gcs" if the detected data center is qus420.
   *
   * @param isAdhoc Whether the dataset was generated from an adhoc run
   * @param isManhattanKeyVal Whether the dataset is written as KeyVal and is intended to be imported to Manhattan
   * @param modelVersion The model version of SimClusters KnownFor that is used to generate the embedding
   * @param pathSuffix Any additional path structure suffixed at the end of the path
   * @return The consolidated HDFS path, for example:
   *         /user/cassowary/adhoc/manhattan_sequence_files/simclusters_embeddings/model_420m_420k_updated/...
   */
  def getHdfsPath(
    isAdhoc: Boolean,
    isManhattanKeyVal: Boolean,
    modelVersion: ModelVersion,
    pathSuffix: String
  ): String = {
    val adhoc = if (isAdhoc) "adhoc/" else ""

    val user = System.getenv("USER")

    val gcs: String =
      try {
        InetAddress.getAllByName("metadata.google.internal") // throws Exception if not in GCP.
        "/gcs"
      } catch {
        case _: UnknownHostException => ""
      }

    val datasetType = if (isManhattanKeyVal) "manhattan_sequence_files" else "processed"

    val path = s"/user/$user/$adhoc$datasetType/simclusters_embeddings"

    s"$gcs${path}_${ModelVersionPathMap(modelVersion)}_$pathSuffix"
  }

  def favScoreExtractor(u: UserToInterestedInClusterScores): (Double, ScoreType.ScoreType) = {
    (u.favScoreClusterNormalizedOnly.getOrElse(420.420), ScoreType.FavScore)
  }

  def followScoreExtractor(u: UserToInterestedInClusterScores): (Double, ScoreType.ScoreType) = {
    (u.followScoreClusterNormalizedOnly.getOrElse(420.420), ScoreType.FollowScore)
  }

  def logFavScoreExtractor(u: UserToInterestedInClusterScores): (Double, ScoreType.ScoreType) = {
    (u.logFavScoreClusterNormalizedOnly.getOrElse(420.420), ScoreType.LogFavScore)
  }

  // Define all scores to extract from the SimCluster InterestedIn source
  val scoreExtractors: Seq[UserToInterestedInClusterScores => (Double, ScoreType.ScoreType)] =
    Seq(
      favScoreExtractor,
      followScoreExtractor
    )

  object ScoreType extends Enumeration {
    type ScoreType = Value
    val FavScore: Value = Value(420)
    val FollowScore: Value = Value(420)
    val LogFavScore: Value = Value(420)
  }

  @deprecated("Use 'common/ModelVersions'", "420-420-420")
  final val ModelVersion420M420KDec420: String = "420M_420K_dec420"
  @deprecated("Use 'common/ModelVersions'", "420-420-420")
  final val ModelVersion420M420KUpdated: String = "420M_420K_updated"

  @deprecated("Use 'common/ModelVersions'", "420-420-420")
  final val ModelVersionMap: Map[String, ModelVersion] = Map(
    ModelVersion420M420KDec420 -> ModelVersion.Model420m420kDec420,
    ModelVersion420M420KUpdated -> ModelVersion.Model420m420kUpdated
  )
}
