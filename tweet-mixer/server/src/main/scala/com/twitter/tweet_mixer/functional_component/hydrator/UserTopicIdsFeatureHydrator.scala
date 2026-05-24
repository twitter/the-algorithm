package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.tweet_mixer.feature.UserTopicIdsFeature
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.interests.thriftscala.InterestId
import com.twitter.interests.thriftscala.InterestRelationship
import com.twitter.interests.thriftscala.InterestedInInterestModel
import com.twitter.interests.thriftscala.UserInterest
import com.twitter.interests.thriftscala.UserInterestData
import com.twitter.interests.thriftscala.UserInterestsResponse
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.stitch.Stitch
import com.twitter.strato.catalog.Fetch
import com.twitter.strato.generated.client.interests.InterestedInInterestsClientColumn
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableUserTopicIdsHydrator
import com.twitter.util.logging.Logging

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserTopicIdsFeatureHydrator @Inject() (
  interestedInInterestsClientColumn: InterestedInInterestsClientColumn,
  statsReceiver: StatsReceiver)
    extends QueryFeatureHydrator[PipelineQuery]
    with Conditionally[PipelineQuery]
    with Logging {

  private val scopedStats = statsReceiver.scope(getClass.getSimpleName)
  private val numTopicsFetched = scopedStats.counter("numTopicsFetched")
  private val numTopicsStats = scopedStats.stat("numTopicsStat")
  private val emptyTopics = scopedStats.counter("emptyStats")

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("UserTopicIds")

  private val GenericTopics = Set(
    <removed_id>, // Animation and Comics
    <removed_id>, // Arts and Culture
    <removed_id>, // Business and Finance
    <removed_id>, // Careers
    <removed_id>, // Digital Assets and Crypto
    <removed_id>, // Entertainment
    <removed_id>, // Events
    <removed_id>, // Entertainment Industry
    <removed_id>, // Fashion and Beauty
    <removed_id>, // Fitness
    <removed_id>, // Food
    <removed_id>, // Gaming
    <removed_id>, // Music
    <removed_id>, // News
    <removed_id>, // Outdoors
    <removed_id>, // Politics
    <removed_id>, // Science
    <removed_id>, // Sports
    <removed_id>, // Technology
    <removed_id>, // Travel
  )

  override def onlyIf(query: PipelineQuery): Boolean =
    query.params(EnableUserTopicIdsHydrator)

  override val features: Set[Feature[_, _]] = Set(UserTopicIdsFeature)

  private val EmptyFeatureMap = FeatureMap(UserTopicIdsFeature, Seq.empty)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    query.getOptionalUserId match {
      case Some(userId) =>
        fetchInterestRelationships(userId)
          .map(_.toSeq.flatten)
          .map(extractAndSortTopics).map { topicIds =>
            numTopicsFetched.incr(topicIds.length)
            numTopicsStats.add(topicIds.length)
            FeatureMap(UserTopicIdsFeature, topicIds)
          }
      case _ =>
        emptyTopics.incr()
        Stitch.value(EmptyFeatureMap)
    }
  }

  private def fetchInterestRelationships(
    userId: Long
  ): Stitch[Option[Seq[InterestRelationship]]] = {
    val response: Stitch[Fetch.Result[UserInterestsResponse]] =
      interestedInInterestsClientColumn.fetcher
        .fetch(InterestedInInterestsClientColumn.Key(userId = userId, labels = None, None), Unit)

    response.map(_.v).map {
      case Some(value) =>
        value.interests.interests.map { interests =>
          interests.collect {
            case UserInterest(_, Some(interestData)) => getInterestRelationship(interestData)
          }.flatten
        }
      case _ => None
    }
  }

  private def getInterestRelationship(
    interestData: UserInterestData
  ): Seq[InterestRelationship] = {
    interestData match {
      case UserInterestData.InterestedIn(interestModels) =>
        interestModels.collect {
          case InterestedInInterestModel.ExplicitModel(model) => model
        }
      case _ => Nil
    }
  }

  private def extractAndSortTopics(
    interestRelationships: Seq[InterestRelationship]
  ): Seq[Long] = {
    val topicsWithTimestamps = interestRelationships.flatMap {
      case InterestRelationship.V1(relationshipV1) =>
        relationshipV1.interestId match {
          case InterestId.SemanticCore(semanticCoreInterest) =>
            Some((semanticCoreInterest.id, relationshipV1.timestampMs))
          case _ => None
        }
      case _ => None
    }

    val (genericTopics, subtopics) = topicsWithTimestamps
      .sortBy { case (id, timestamp) => -timestamp }
      .map { case (id, timestamp) => id }
      .partition(GenericTopics.contains)

    subtopics ++ genericTopics
  }
}
