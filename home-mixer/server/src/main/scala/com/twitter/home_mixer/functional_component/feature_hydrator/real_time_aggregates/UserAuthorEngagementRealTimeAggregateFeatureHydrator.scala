package com.twitter.home_mixer.functional_component.feature_hydrator.real_time_aggregates

import com.google.inject.name.Named
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.module.RealtimeAggregateFeatureRepositoryModule.authorIdFeature
import com.twitter.home_mixer.module.RealtimeAggregateFeatureRepositoryModule.keyTransformD2
import com.twitter.home_mixer.module.RealtimeAggregateFeatureRepositoryModule.keyTransformD2AggregationKey
import com.twitter.home_mixer.module.RealtimeAggregateFeatureRepositoryModule.userIdFeature
import com.twitter.home_mixer.param.HomeGlobalParams.EnableUserAuthorRTAMhFallbackParam
import com.twitter.home_mixer.param.HomeGlobalParams.EnableUserAuthorRTAMhOnlyParam
import com.twitter.home_mixer.param.HomeMixerInjectionNames.RTAManhattanStore
import com.twitter.home_mixer.param.HomeMixerInjectionNames.UserAuthorEngagementCache
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.home_mixer_features.{thriftjava => t}
import com.twitter.ml.api.DataRecord
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.servo.cache.ReadCache
import com.twitter.stitch.Stitch
import com.twitter.storehaus.ReadableStore
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregateGroup
import com.twitter.timelines.prediction.common.aggregates.real_time.TimelinesOnlineAggregationFeaturesOnlyConfig._
import com.twitter.timelines.realtime_aggregates.{thriftscala => thrift}
import com.twitter.util.Future
import com.twitter.util.Try
import javax.inject.Inject
import javax.inject.Singleton

object UserAuthorEngagementRealTimeAggregateFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class UserAuthorEngagementRealTimeAggregateFeatureHydrator @Inject() (
  override val homeMixerFeatureService: t.HomeMixerFeatures.ServiceToClient,
  @Named(UserAuthorEngagementCache) override val client: ReadCache[(Long, Long), DataRecord],
  @Named(RTAManhattanStore) mhClient: Option[
    ReadableStore[thrift.AggregationKey, DataRecord]
  ],
  override val statsReceiver: StatsReceiver)
    extends FlagBasedRealTimeAggregateBulkCandidateFeatureHydrator[(Long, Long)] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("UserAuthorEngagementRealTimeAggregate")

  override val outputFeature: DataRecordInAFeature[TweetCandidate] =
    UserAuthorEngagementRealTimeAggregateFeature

  override val aggregateGroups: Seq[AggregateGroup] = Seq(
    userAuthorEngagementRealTimeAggregatesProd,
    userAuthorShareEngagementsRealTimeAggregates
  )

  override val aggregateGroupToPrefix: Map[AggregateGroup, String] = Map(
    userAuthorEngagementRealTimeAggregatesProd -> "user-author.timelines.user_author_engagement_real_time_aggregates.",
    userAuthorShareEngagementsRealTimeAggregates -> "user-author.timelines.user_author_share_engagements_real_time_aggregates."
  )

  def serializeKey(key: (Long, Long)): String = {
    keyTransformD2(userIdFeature, authorIdFeature)(key)
  }

  override def keysFromQueryAndCandidates(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Seq[Option[(Long, Long)]] = {
    val userId = query.getRequiredUserId
    candidates.map { candidate =>
      CandidatesUtil
        .getOriginalAuthorId(candidate.features)
        .map((userId, _))
    }
  }

  def convert(key: (Long, Long)): thrift.AggregationKey = {
    val ak = keyTransformD2AggregationKey(userIdFeature, authorIdFeature)((key._1, key._2))
    thrift.AggregationKey(
      ak.discreteFeaturesById,
      ak.textFeaturesById
    )
  }

  def fetchAndConstructDataRecordFromMh(
    possiblyKeys: Seq[Option[(Long, Long)]]
  ): Future[Seq[Try[DataRecord]]] = {
    Future
      .collect {
        possiblyKeys.flatten
          .map {
            convert(_)
          }
          .grouped(64).map { keyGroup =>
            val results = mhClient.get.multiGet(keyGroup.toSet)
            Future.collect(keyGroup.flatMap(results.get)).map { drSeq =>
              drSeq.map { drOpt =>
                if (drOpt.isEmpty) statsReceiver.scope("mhUserAuthorRTA").counter("empty").incr()
                else statsReceiver.scope("mhUserAuthorRTA").counter("non_empty").incr()
                Try(drOpt.map(postTransformer).getOrElse(EmptyDataRecord))
              }
            }
          }.toSeq
      }.map(_.flatten)
  }

  def getFetchFunc(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Future[Seq[Try[DataRecord]]] = {
    val fetchFromMhOnly = query.params.getBoolean(EnableUserAuthorRTAMhOnlyParam)
    val fetchFromMhAsFallBack = query.params.getBoolean(EnableUserAuthorRTAMhFallbackParam)
    val possiblyKeys = keysFromQueryAndCandidates(query, candidates)
    val stats = statsReceiver.scope("user_author_real_time_rta")
    if (fetchFromMhOnly) {
      fetchAndConstructDataRecordFromMh(possiblyKeys)
    } else if (fetchFromMhAsFallBack) {
      fetchAndConstructDataRecordsWithFallback(
        possiblyKeys,
        stats,
        fetchAndConstructDataRecords,
        fetchAndConstructDataRecordFromMh,
      )
    } else {
      fetchAndConstructDataRecords(possiblyKeys)
    }
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    getFetchFunc(query, candidates).map { dataRecords =>
      val featureMaps = dataRecords.map { dataRecord =>
        FeatureMapBuilder().add(outputFeature, dataRecord).build()
      }
      featureMaps
    }
  }
}
