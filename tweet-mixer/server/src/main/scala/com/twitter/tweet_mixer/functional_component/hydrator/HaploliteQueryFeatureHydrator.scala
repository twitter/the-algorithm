package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.haplolite.{thriftscala => t}
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.stitch.Stitch
import com.twitter.timelines.util.ByteBufferBuilder
import com.twitter.timelines.util.SnowflakeSortIndexHelper
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.HaploliteTweetsBasedEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.MaxHaploliteTweetsParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.MaxTweetAgeHoursParam
import com.twitter.util.Future
import com.twitter.util.Time
import java.nio.ByteBuffer
import javax.inject.Inject
import javax.inject.Singleton

object HaploliteFeature extends Feature[PipelineQuery, Seq[ByteBuffer]]
object HaploMissFeature extends Feature[PipelineQuery, Boolean]

@Singleton
case class HaploliteQueryFeatureHydrator @Inject() (
  haploliteClient: t.Haplolite.MethodPerEndpoint)
    extends QueryFeatureHydrator[PipelineQuery]
    with Conditionally[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("Haplolite")

  override val features: Set[Feature[_, _]] = Set(HaploliteFeature, HaploMissFeature)

  private def idToBuf(id: Long) = {
    ByteBufferBuilder(8) { _.putLong(id) }
  }

  override def onlyIf(query: PipelineQuery): Boolean = query.params(HaploliteTweetsBasedEnabled)

  private val defaultFeatureMap = FeatureMap(HaploliteFeature, Seq.empty, HaploMissFeature, false)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val haploliteRequest = createHaploliteRequest(query)
    val haploliteResultFut = haploliteClient.get(Seq(haploliteRequest))
    val haploliteEntriesFut =
      haploliteResultFut
        .map { haploliteResults =>
          haploliteResults.list.flatMap { getResult =>
            getResult.timeline.map(_.entries).getOrElse(Seq.empty)
          }
        }
    val haploliteMissFut = haploliteResultFut
      .map { haploliteResults =>
        haploliteResults.list.exists { getResult =>
          getResult.timeline.map(_.state == t.ResultState.Miss).getOrElse(false)
        }
      }
    Stitch
      .callFuture {
        Future.join(haploliteEntriesFut, haploliteMissFut).map {
          case (haploliteEntries, haploliteMiss) =>
            FeatureMap(HaploliteFeature, haploliteEntries, HaploMissFeature, haploliteMiss)
        }
      }.handle {
        case _ => defaultFeatureMap
      }
  }

  private def createHaploliteRequest(query: PipelineQuery): t.GetRequest = {
    val userId = query.getRequiredUserId

    val duration = query.params(MaxTweetAgeHoursParam)
    val sinceTime: Time = duration.ago
    val fromTweetIdExclusive = SnowflakeSortIndexHelper.timestampToFakeId(sinceTime)

    val bulkKeys = t.BulkKeys(t.KeyNamespace.Home.value, Seq(userId))
    val haploliteCursor =
      t.Cursor(comparableEntry = t.ComparableEntry(idToBuf(fromTweetIdExclusive)), isForward = true)
    t.GetRequest(
      bulkKeys = Seq(bulkKeys),
      timelineRequest = Some(
        t.TimelineRequest(
          dedupeSecondary = true,
          maxCount = query.params(MaxHaploliteTweetsParam),
          cursor = Some(haploliteCursor)
        )
      )
    )
  }
}
