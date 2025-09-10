package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.content_understanding.api.PostAnnotationsOnTweetClientColumn
import com.twitter.tweet_mixer.feature._
import com.twitter.tweet_mixer.feature.USSFeatures._
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableUSSGrokCategoryFeatureHydrator
import javax.inject.Inject
import javax.inject.Singleton
import scala.collection.mutable

object USSGrokCategoryFeature
    extends FeatureWithDefaultOnFailure[PipelineQuery, Option[Map[Long, Int]]] {
  override def defaultValue: Option[Map[Long, Int]] = None
}

@Singleton
class USSGrokCategoryFeatureHydratorFactory @Inject() (
  postAnnotationsOnTweetClientColumn: PostAnnotationsOnTweetClientColumn) {

  def build(
    signalFn: PipelineQuery => Seq[Long]
  ): USSGrokCategoryFeatureHydrator = {
    new USSGrokCategoryFeatureHydrator(
      postAnnotationsOnTweetClientColumn,
      signalFn
    )
  }
}

@Singleton
class USSGrokCategoryFeatureHydrator @Inject() (
  postAnnotationsOnTweetClientColumn: PostAnnotationsOnTweetClientColumn,
  signalFn: PipelineQuery => Seq[Long])
    extends QueryFeatureHydrator[PipelineQuery]
    with Conditionally[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("USSGrokCategory")

  override val features: Set[Feature[_, _]] =
    Set(USSGrokCategoryFeature) ++ TweetFeatures

  private val fetcher = postAnnotationsOnTweetClientColumn.fetcher

  private val MaxTweetsPerCategory = 15

  override def onlyIf(query: PipelineQuery): Boolean =
    query.params(EnableUSSGrokCategoryFeatureHydrator)

  private def diversifySignalsByCategory(
    query: PipelineQuery,
    categoryMap: Map[Long, Int],
    maxTweetsPerCategory: Int = MaxTweetsPerCategory
  ): Map[USSFeature[Long], Map[Long, Seq[SignalInfo]]] = {

    val allSignals = TweetFeatures.map { feature =>
      val signals = feature.getValue(query)
      feature -> signals
    }.toMap

    // 1. construct tweet to uss map
    val tweetToFeaturesMap = mutable.Map[Long, mutable.Set[USSFeature[Long]]]()
    allSignals.foreach {
      case (feature, signalMap) =>
        signalMap.keys.foreach { tweetId =>
          if (!tweetToFeaturesMap.contains(tweetId)) {
            tweetToFeaturesMap(tweetId) = mutable.Set[USSFeature[Long]]()
          }
          tweetToFeaturesMap(tweetId) += feature
        }
    }

    //  2. construct category to tweets map
    val categoryToTweetsMap = categoryMap.groupBy(_._2).map {
      case (category, tweets) =>
        category -> tweets.keys.toSeq.take(maxTweetsPerCategory)
    }

    // 3. construct category queues based on the categoryToTweetsMap
    val categoryQueues = mutable.ArrayBuffer[mutable.Queue[Long]]()
    val categoryToQueueIndex = mutable.Map.empty[Int, Int]

    // Create a queue for each category and track its index
    categoryMap.foreach {
      case (tweetId, category) =>
        // only process tweets that exist in at least one feature
        if (tweetToFeaturesMap.contains(tweetId)) {
          if (!categoryToQueueIndex.contains(category)) {
            categoryQueues.append(mutable.Queue[Long]())
            categoryToQueueIndex.put(category, categoryQueues.length - 1)
          }

          // only add tweets that are in the maxTweetsPerCategory limit
          val tweetsInCategory = categoryToTweetsMap.getOrElse(category, Seq.empty)
          if (tweetsInCategory.contains(tweetId)) {
            categoryQueues(categoryToQueueIndex(category)).enqueue(tweetId)
          }
        }
    }

    // 4. count the number of tweets assigned to each feature
    val featureAssignmentCount = mutable.Map[USSFeature[Long], Int]()
    TweetFeatures.foreach(feature => featureAssignmentCount(feature) = 0)

    // 5. maintain a queue of category queues for round-robin processing
    val categoryQueueQueue = mutable.Queue[mutable.Queue[Long]]() ++= categoryQueues

    val selectedTweets = mutable.Map[USSFeature[Long], Set[Long]]()
    TweetFeatures.foreach(feature => selectedTweets(feature) = Set.empty[Long])

    // 6. round-robin assignment of tweets to features, prioritize features with fewer assigned tweets
    while (categoryQueueQueue.nonEmpty) {
      val nextCategoryQueue = categoryQueueQueue.dequeue()

      if (nextCategoryQueue.nonEmpty) {
        val tweetId = nextCategoryQueue.dequeue()

        // get the available features for this tweet
        val availableFeatures =
          tweetToFeaturesMap.getOrElse(tweetId, mutable.Set.empty[USSFeature[Long]])

        if (availableFeatures.nonEmpty) {
          // select the feature with the least assigned tweets
          val selectedFeature = availableFeatures.toSeq.minBy(featureAssignmentCount(_))

          // assign the tweet to the selected feature
          selectedTweets(selectedFeature) += tweetId
          featureAssignmentCount(selectedFeature) += 1
        }

        // if there are still tweets in this category, re-enqueue it
        if (nextCategoryQueue.nonEmpty) {
          categoryQueueQueue.enqueue(nextCategoryQueue)
        }
      }
    }

    // update signals based on diversified tweets
    val diversifiedSignals = allSignals.map {
      case (feature, signalMap) =>
        val filteredMap = signalMap.filter {
          case (tweetId, _) => selectedTweets(feature).contains(tweetId)
        }
        feature -> filteredMap
    }

    diversifiedSignals
  }

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val tweetIds = signalFn(query)

    if (tweetIds.isEmpty) {
      val emptyFeatureMapBuilder = FeatureMapBuilder()
        .add(USSGrokCategoryFeature, None)

      TweetFeatures.foreach { feature =>
        emptyFeatureMapBuilder.add(feature, Map.empty[Long, Seq[SignalInfo]])
      }

      Stitch.value(emptyFeatureMapBuilder.build())
    } else {
      val stitchedResults = tweetIds.map { tweetId =>
        fetcher.fetch(tweetId).map { response =>
          val resultOpt = response.v
          val categoryIdOpt = resultOpt.flatMap { result =>
            val entities = result.annotations.entities.getOrElse(Seq.empty)
            val computedPairs = entities
              .map(category => (category.score.getOrElse(0.0), category.qualifiedId.entityId))
            val sortedPairs = computedPairs.sortBy(-_._1)
            val filteredIds = sortedPairs.map(_._2).filter(_ % 10000 == 0)
            filteredIds.headOption
          }
          tweetId -> categoryIdOpt.map(_.toInt)
        }
      }

      Stitch.collect(stitchedResults).map { pairs =>
        val categoryMap = pairs.collect {
          case (tweetId, Some(categoryId)) =>
            tweetId -> categoryId
        }.toMap

        val featureMapBuilder = FeatureMapBuilder()
          .add(USSGrokCategoryFeature, Some(categoryMap).filter(_.nonEmpty))

        if (categoryMap.nonEmpty) {
          // execute diversification and get the result
          val diversifiedSignals = diversifySignalsByCategory(query, categoryMap)

          // update the original features
          diversifiedSignals.foreach {
            case (feature, signalMap) =>
              featureMapBuilder.add(feature, signalMap)
          }
        } else {
          TweetFeatures.foreach { feature =>
            featureMapBuilder.add(feature, Map.empty[Long, Seq[SignalInfo]])
          }
        }

        featureMapBuilder.build()
      }
    }
  }
}
