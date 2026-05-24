package com.twitter.home_mixer.functional_component.gate

import com.twitter.home_mixer.model.HomeFeatures.PersistenceEntriesFeature
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelinemixer.injection.store.persistence.TimelinePersistenceUtils
import com.twitter.timelines.configapi.Param
import com.twitter.timelineservice.model.rich.EntityIdType
import com.twitter.timelineservice.model.TweetScoreV1
import com.twitter.util.Duration
import com.twitter.util.Time
import com.twitter.home_mixer.{thriftscala => hmt}

/**
 * Gate used to reduce the frequency of injections based on specific served types.
 * This gate checks if any tweets in the persistence store have a served type equal to the specified targetServedType.
 * Note that the actual interval between injections may be less than the specified minInjectionIntervalParam 
 * if data is unavailable or missing. For example, being deleted by the persistence store via a TTL or similar mechanism.
 *
 * @param minInjectionIntervalParam the desired minimum interval between injections
 * @param targetServedType the served type to check for in persisted tweets
 */
case class RecentlyServedByServedTypeGate(
  minInjectionIntervalParam: Param[Duration],
  targetServedType: hmt.ServedType)
    extends Gate[PipelineQuery]
    with TimelinePersistenceUtils {

  override val identifier: GateIdentifier = GateIdentifier("RecentlyServedByServedType")

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] =
    Stitch(
      query.queryTime.since(getLastInjectionTime(query)) > query.params(minInjectionIntervalParam))

  private def getLastInjectionTime(query: PipelineQuery) = query.features
    .flatMap { featureMap =>
      val timelineResponses = featureMap.getOrElse(PersistenceEntriesFeature, Seq.empty)
      val latestResponseWithTargetServedTypeEntry =
        timelineResponses.find { response =>
          response.entries.exists { entry =>
            entry.entityIdType == EntityIdType.Tweet && 
            entry.itemIds.exists { itemIds =>
              itemIds.exists { itemId =>
                itemId.tweetScore.exists {
                  case tweetScore: TweetScoreV1 =>
                    tweetScore.servedType.contains(targetServedType.originalName)
                  case _ => false
                }
              }
            }
          }
        }

      latestResponseWithTargetServedTypeEntry.map(_.servedTime)
    }.getOrElse(Time.Bottom)
}
