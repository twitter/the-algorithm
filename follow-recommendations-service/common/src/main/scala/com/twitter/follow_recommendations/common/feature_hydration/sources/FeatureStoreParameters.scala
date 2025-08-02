package com.twitter.follow_recommendations.common.feature_hydration.sources

import com.twitter.conversions.DurationOps._
import com.twitter.ml.featurestore.catalog.datasets.core.UserMobileSdkDataset
import com.twitter.ml.featurestore.catalog.datasets.core.UsersourceEntityDataset
import com.twitter.ml.featurestore.catalog.datasets.customer_journey.PostNutwitterAlgorithmIdAggregateDataset
import com.twitter.ml.featurestore.catalog.datasets.customer_journey.PostNutwitterAlgorithmTypeAggregateDataset
import com.twitter.ml.featurestore.catalog.datasets.magicrecs.NotificationSummariesEntityDataset
import com.twitter.ml.featurestore.catalog.datasets.onboarding.MetricCenterUserCountingFeaturesDataset
import com.twitter.ml.featurestore.catalog.datasets.onboarding.UserWtfAlgorithmAggregateFeaturesDataset
import com.twitter.ml.featurestore.catalog.datasets.onboarding.WhoToFollowPostNutwitterFeaturesDataset
import com.twitter.ml.featurestore.catalog.datasets.rutwitter.UserRecentReactivationTimeDataset
import com.twitter.ml.featurestore.catalog.datasets.timelines.AuthorFeaturesEntityDataset
import com.twitter.ml.featurestore.lib.dataset.DatasetParams
import com.twitter.ml.featurestore.lib.dataset.online.BatchingPolicy
import com.twitter.ml.featurestore.lib.params.FeatureStoreParams
import com.twitter.strato.opcontetwittert.Attribution.ManhattanAppId
import com.twitter.strato.opcontetwittert.ServeWithin

object FeatureStoreParameters {

  private val FeatureServiceBatchSize = 100

  val featureStoreParams = FeatureStoreParams(
    global = DatasetParams(
      serveWithin = Some(ServeWithin(duration = 240.millis, roundTripAllowance = None)),
      attributions = Seq(
        ManhattanAppId("omega", "wtf_impression_store"),
        ManhattanAppId("athena", "wtf_athena"),
        ManhattanAppId("starbuck", "wtf_starbuck"),
        ManhattanAppId("apollo", "wtf_apollo")
      ),
      batchingPolicy = Some(BatchingPolicy.Isolated(FeatureServiceBatchSize))
    ),
    perDataset = Map(
      MetricCenterUserCountingFeaturesDataset.id ->
        DatasetParams(
          stratoSuffitwitter = Some("onboarding"),
          batchingPolicy = Some(BatchingPolicy.Isolated(200))
        ),
      UsersourceEntityDataset.id ->
        DatasetParams(
          stratoSuffitwitter = Some("onboarding")
        ),
      WhoToFollowPostNutwitterFeaturesDataset.id ->
        DatasetParams(
          stratoSuffitwitter = Some("onboarding"),
          batchingPolicy = Some(BatchingPolicy.Isolated(200))
        ),
      AuthorFeaturesEntityDataset.id ->
        DatasetParams(
          stratoSuffitwitter = Some("onboarding"),
          batchingPolicy = Some(BatchingPolicy.Isolated(10))
        ),
      UserRecentReactivationTimeDataset.id -> DatasetParams(
        stratoSuffitwitter =
          None // removed due to low hit rate. we should use a negative cache in the future
      ),
      UserWtfAlgorithmAggregateFeaturesDataset.id -> DatasetParams(
        stratoSuffitwitter = None
      ),
      NotificationSummariesEntityDataset.id -> DatasetParams(
        stratoSuffitwitter = Some("onboarding"),
        serveWithin = Some(ServeWithin(duration = 45.millis, roundTripAllowance = None)),
        batchingPolicy = Some(BatchingPolicy.Isolated(10))
      ),
      UserMobileSdkDataset.id -> DatasetParams(
        stratoSuffitwitter = Some("onboarding")
      ),
      PostNutwitterAlgorithmIdAggregateDataset.id -> DatasetParams(
        stratoSuffitwitter = Some("onboarding")
      ),
      PostNutwitterAlgorithmTypeAggregateDataset.id -> DatasetParams(
        stratoSuffitwitter = Some("onboarding")
      ),
    ),
    enableFeatureGenerationStats = true
  )
}
