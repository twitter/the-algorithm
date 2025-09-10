package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.servo.cache.ExpiringLruInProcessCache
import com.twitter.servo.cache.InProcessCache
import com.twitter.simclusters_v2.thriftscala.ClusterDetails
import com.twitter.stitch.Stitch
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future
import javax.inject.Inject
import javax.inject.Singleton
import scala.util.Random

object SimclustersFavBasedTopAuthors extends Feature[PipelineQuery, Seq[(Long, Double)]]
object SimclustersFollowBasedTopAuthors extends Feature[PipelineQuery, Seq[(Long, Double)]]

object SimclusterBasedTopAuthorsQueryFeatureHydrator {
  private val BaseTTL = 60 * 24
  private val TTL = (BaseTTL + Random.nextInt(60)).minutes

  val cache: InProcessCache[String, Seq[(Long, Double)]] =
    new ExpiringLruInProcessCache(ttl = TTL, maximumSize = 150 * 1000)
}

@Singleton
class SimclusterBasedTopAuthorsQueryFeatureHydrator @Inject() (
  store: ReadableStore[String, ClusterDetails])
    extends QueryFeatureHydrator[PipelineQuery] {

  import SimclusterBasedTopAuthorsQueryFeatureHydrator._

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("SimclusterBasedTopAuthors")

  override val features: Set[Feature[_, _]] =
    Set(SimclustersFavBasedTopAuthors, SimclustersFollowBasedTopAuthors)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val favBasedEmbeddings =
      query.features
        .flatMap(_.getOrElse(SimClustersUserLogFavSparseEmbeddingsDataRecordFeature, None))
        .getOrElse(Map.empty[String, Double])
    val followBasedEmbeddings =
      query.features
        .flatMap(_.getOrElse(SimClustersUserFollowSparseEmbeddingsDataRecordFeature, None))
        .getOrElse(Map.empty[String, Double])

    val favBasedTopAuthorsStitch = Stitch.callFuture(getTopAuthorsWithScores(favBasedEmbeddings))
    val followBasedTopAuthorsStitch =
      Stitch.callFuture(getTopAuthorsWithScores(followBasedEmbeddings))
    Stitch.join(favBasedTopAuthorsStitch, followBasedTopAuthorsStitch).map {
      case (favBasedTopAuthors, followBasedTopAuthors) =>
        FeatureMap(
          SimclustersFavBasedTopAuthors,
          favBasedTopAuthors,
          SimclustersFollowBasedTopAuthors,
          followBasedTopAuthors
        )
    }
  }

  private def getTopAuthorsWithScores(
    embeddings: Map[String, Double]
  ): Future[Seq[(Long, Double)]] = {
    val flattenedAuthorsWithScoresFut = Future
      .collect {
        embeddings.map {
          case (clusterId, seedScore) =>
            getTopAuthorsWithScoresForCluster(clusterId).map { topAuthors =>
              topAuthors.map {
                case (author, score) => (author, score * seedScore)
              }
            }
        }.toSeq
      }.map(_.flatten)
    flattenedAuthorsWithScoresFut.map { flattenedAuthorsWithScores =>
      val authorsWithScores =
        flattenedAuthorsWithScores.groupBy(_._1).mapValues(_.map(_._2).sum).toSeq
      authorsWithScores.sortBy(-_._2)
    }
  }

  private def getTopAuthorsWithScoresForCluster(
    clusterId: String
  ): Future[Seq[(Long, Double)]] = {
    cache
      .get(clusterId)
      .map(Future.value(_))
      .getOrElse {
        store
          .get(clusterId).map { clusterDetailsOpt =>
            val authorsWithScores = clusterDetailsOpt
              .flatMap { clusterDetails =>
                clusterDetails.knownForUsersAndScores.map { knownForUsersAndScores =>
                  knownForUsersAndScores.map { userAndScore =>
                    (userAndScore.userId, userAndScore.score)
                  }
                }
              }.getOrElse(Seq.empty)
            cache.set(clusterId, authorsWithScores)
            authorsWithScores
          }.handle { case _ => Seq.empty }
      }
  }

}
