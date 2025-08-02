package com.twitter.timelines.prediction.common.aggregates

import com.twitter.conversions.DurationOps._
import com.twitter.ml.api.constant.SharedFeatures.AUTHOR_ID
import com.twitter.ml.api.constant.SharedFeatures.USER_ID
import com.twitter.timelines.data_processing.ml_util.aggregation_framework._
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.metrics._
import com.twitter.timelines.data_processing.ml_util.transforms.DownsampleTransform
import com.twitter.timelines.data_processing.ml_util.transforms.RichRemoveAuthorIdZero
import com.twitter.timelines.data_processing.ml_util.transforms.RichRemoveUserIdZero
import com.twitter.timelines.prediction.features.common.TimelinesSharedFeatures
import com.twitter.timelines.prediction.features.engagement_features.EngagementDataRecordFeatures
import com.twitter.timelines.prediction.features.engagement_features.EngagementDataRecordFeatures.RichUnifyPublicEngagersTransform
import com.twitter.timelines.prediction.features.list_features.ListFeatures
import com.twitter.timelines.prediction.features.recap.RecapFeatures
import com.twitter.timelines.prediction.features.request_contetwittert.RequestContetwittertFeatures
import com.twitter.timelines.prediction.features.semantic_core_features.SemanticCoreFeatures
import com.twitter.timelines.prediction.transform.filter.FilterInNetworkTransform
import com.twitter.timelines.prediction.transform.filter.FilterImageTweetTransform
import com.twitter.timelines.prediction.transform.filter.FilterVideoTweetTransform
import com.twitter.timelines.prediction.transform.filter.FilterOutImageVideoTweetTransform
import com.twitter.util.Duration

trait TimelinesAggregationConfigDetails etwittertends Serializable {

  import TimelinesAggregationSources._

  def outputHdfsPath: String

  /**
   * Converts the given logical store to a physical store. The reason we do not specify the
   * physical store directly with the [[AggregateGroup]] is because of a cyclic dependency when
   * create physical stores that are DalDataset with PersonalDataType annotations derived from
   * the [[AggregateGroup]].
   *
   */
  def mkPhysicalStore(store: AggregateStore): AggregateStore

  def defaultMatwitterKvSourceFailures: Int = 100

  val timelinesOfflineAggregateSink = new OfflineStoreCommonConfig {
    override def apply(startDate: String) = OfflineAggregateStoreCommonConfig(
      outputHdfsPathPrefitwitter = outputHdfsPath,
      dummyAppId = "timelines_aggregates_v2_ro",
      dummyDatasetPrefitwitter = "timelines_aggregates_v2_ro",
      startDate = startDate
    )
  }

  val UserAggregateStore = "user_aggregates"
  val UserAuthorAggregateStore = "user_author_aggregates"
  val UserOriginalAuthorAggregateStore = "user_original_author_aggregates"
  val OriginalAuthorAggregateStore = "original_author_aggregates"
  val UserEngagerAggregateStore = "user_engager_aggregates"
  val UserMentionAggregateStore = "user_mention_aggregates"
  val TwitterWideUserAggregateStore = "twitter_wide_user_aggregates"
  val TwitterWideUserAuthorAggregateStore = "twitter_wide_user_author_aggregates"
  val UserRequestHourAggregateStore = "user_request_hour_aggregates"
  val UserRequestDowAggregateStore = "user_request_dow_aggregates"
  val UserListAggregateStore = "user_list_aggregates"
  val AuthorTopicAggregateStore = "author_topic_aggregates"
  val UserTopicAggregateStore = "user_topic_aggregates"
  val UserInferredTopicAggregateStore = "user_inferred_topic_aggregates"
  val UserMediaUnderstandingAnnotationAggregateStore =
    "user_media_understanding_annotation_aggregates"
  val AuthorCountryCodeAggregateStore = "author_country_code_aggregates"
  val OriginalAuthorCountryCodeAggregateStore = "original_author_country_code_aggregates"

  /**
   * Step 3: Configure all aggregates to compute.
   * Note that different subsets of aggregates in this list
   * can be launched by different summingbird job instances.
   * Any given job can be responsible for a set of AggregateGroup
   * configs whose outputStores share the same etwitteract startDate.
   * AggregateGroups that do not share the same inputSource,
   * outputStore or startDate MUST be launched using different
   * summingbird jobs and passed in a different --start-time argument
   * See science/scalding/mesos/timelines/prod.yaml for an etwitterample
   * of how to configure your own job.
   */
  val negativeDownsampleTransform =
    DownsampleTransform(
      negativeSamplingRate = 0.03,
      keepLabels = RecapUserFeatureAggregation.LabelsV2)
  val negativeRecTweetDownsampleTransform = DownsampleTransform(
    negativeSamplingRate = 0.03,
    keepLabels = RectweetUserFeatureAggregation.RectweetLabelsForAggregation
  )

  val userAggregatesV2: AggregateGroup =
    AggregateGroup(
      inputSource = timelinesDailyRecapMinimalSource,
      aggregatePrefitwitter = "user_aggregate_v2",
      preTransforms = Seq(RichRemoveUserIdZero), /* Eliminates reducer skew */
      keys = Set(USER_ID),
      features = RecapUserFeatureAggregation.UserFeaturesV2,
      labels = RecapUserFeatureAggregation.LabelsV2,
      metrics = Set(CountMetric, SumMetric),
      halfLives = Set(50.days),
      outputStore = mkPhysicalStore(
        OfflineAggregateDataRecordStore(
          name = UserAggregateStore,
          startDate = "2016-07-15 00:00",
          commonConfig = timelinesOfflineAggregateSink,
          matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
        ))
    )

  val userAuthorAggregatesV2: Set[AggregateGroup] = {

    /**
     * NOTE: We need to remove records from out-of-network authors from the recap input
     * records (which now include out-of-network records as well after merging recap and
     * rectweet models) that are used to compute user-author aggregates. This is necessary
     * to limit the growth rate of user-author aggregates.
     */
    val allFeatureAggregates = Set(
      AggregateGroup(
        inputSource = timelinesDailyRecapMinimalSource,
        aggregatePrefitwitter = "user_author_aggregate_v2",
        preTransforms = Seq(FilterInNetworkTransform, RichRemoveUserIdZero),
        keys = Set(USER_ID, AUTHOR_ID),
        features = RecapUserFeatureAggregation.UserAuthorFeaturesV2,
        labels = RecapUserFeatureAggregation.LabelsV2,
        metrics = Set(SumMetric),
        halfLives = Set(50.days),
        outputStore = mkPhysicalStore(
          OfflineAggregateDataRecordStore(
            name = UserAuthorAggregateStore,
            startDate = "2016-07-15 00:00",
            commonConfig = timelinesOfflineAggregateSink,
            matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
          ))
      )
    )

    val countAggregates: Set[AggregateGroup] = Set(
      AggregateGroup(
        inputSource = timelinesDailyRecapMinimalSource,
        aggregatePrefitwitter = "user_author_aggregate_v2",
        preTransforms = Seq(FilterInNetworkTransform, RichRemoveUserIdZero),
        keys = Set(USER_ID, AUTHOR_ID),
        features = RecapUserFeatureAggregation.UserAuthorFeaturesV2Count,
        labels = RecapUserFeatureAggregation.LabelsV2,
        metrics = Set(CountMetric),
        halfLives = Set(50.days),
        outputStore = mkPhysicalStore(
          OfflineAggregateDataRecordStore(
            name = UserAuthorAggregateStore,
            startDate = "2016-07-15 00:00",
            commonConfig = timelinesOfflineAggregateSink,
            matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
          ))
      )
    )

    allFeatureAggregates ++ countAggregates
  }

  val userAggregatesV5Continuous: AggregateGroup =
    AggregateGroup(
      inputSource = timelinesDailyRecapMinimalSource,
      aggregatePrefitwitter = "user_aggregate_v5.continuous",
      preTransforms = Seq(RichRemoveUserIdZero),
      keys = Set(USER_ID),
      features = RecapUserFeatureAggregation.UserFeaturesV5Continuous,
      labels = RecapUserFeatureAggregation.LabelsV2,
      metrics = Set(CountMetric, SumMetric, SumSqMetric),
      halfLives = Set(50.days),
      outputStore = mkPhysicalStore(
        OfflineAggregateDataRecordStore(
          name = UserAggregateStore,
          startDate = "2016-07-15 00:00",
          commonConfig = timelinesOfflineAggregateSink,
          matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
        ))
    )

  val userAuthorAggregatesV5: AggregateGroup =
    AggregateGroup(
      inputSource = timelinesDailyRecapMinimalSource,
      aggregatePrefitwitter = "user_author_aggregate_v5",
      preTransforms = Seq(FilterInNetworkTransform, RichRemoveUserIdZero),
      keys = Set(USER_ID, AUTHOR_ID),
      features = RecapUserFeatureAggregation.UserAuthorFeaturesV5,
      labels = RecapUserFeatureAggregation.LabelsV2,
      metrics = Set(CountMetric),
      halfLives = Set(50.days),
      outputStore = mkPhysicalStore(
        OfflineAggregateDataRecordStore(
          name = UserAuthorAggregateStore,
          startDate = "2016-07-15 00:00",
          commonConfig = timelinesOfflineAggregateSink,
          matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
        ))
    )

  val tweetSourceUserAuthorAggregatesV1: AggregateGroup =
    AggregateGroup(
      inputSource = timelinesDailyRecapMinimalSource,
      aggregatePrefitwitter = "user_author_aggregate_tweetsource_v1",
      preTransforms = Seq(FilterInNetworkTransform, RichRemoveUserIdZero),
      keys = Set(USER_ID, AUTHOR_ID),
      features = RecapUserFeatureAggregation.UserAuthorTweetSourceFeaturesV1,
      labels = RecapUserFeatureAggregation.LabelsV2,
      metrics = Set(CountMetric, SumMetric),
      halfLives = Set(50.days),
      outputStore = mkPhysicalStore(
        OfflineAggregateDataRecordStore(
          name = UserAuthorAggregateStore,
          startDate = "2016-07-15 00:00",
          commonConfig = timelinesOfflineAggregateSink,
          matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
        ))
    )

  val userEngagerAggregates = AggregateGroup(
    inputSource = timelinesDailyRecapMinimalSource,
    aggregatePrefitwitter = "user_engager_aggregate",
    keys = Set(USER_ID, EngagementDataRecordFeatures.PublicEngagementUserIds),
    features = Set.empty,
    labels = RecapUserFeatureAggregation.LabelsV2,
    metrics = Set(CountMetric),
    halfLives = Set(50.days),
    outputStore = mkPhysicalStore(
      OfflineAggregateDataRecordStore(
        name = UserEngagerAggregateStore,
        startDate = "2016-09-02 00:00",
        commonConfig = timelinesOfflineAggregateSink,
        matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
      )),
    preTransforms = Seq(
      RichRemoveUserIdZero,
      RichUnifyPublicEngagersTransform
    )
  )

  val userMentionAggregates = AggregateGroup(
    inputSource = timelinesDailyRecapMinimalSource,
    preTransforms = Seq(RichRemoveUserIdZero), /* Eliminates reducer skew */
    aggregatePrefitwitter = "user_mention_aggregate",
    keys = Set(USER_ID, RecapFeatures.MENTIONED_SCREEN_NAMES),
    features = Set.empty,
    labels = RecapUserFeatureAggregation.LabelsV2,
    metrics = Set(CountMetric),
    halfLives = Set(50.days),
    outputStore = mkPhysicalStore(
      OfflineAggregateDataRecordStore(
        name = UserMentionAggregateStore,
        startDate = "2017-03-01 00:00",
        commonConfig = timelinesOfflineAggregateSink,
        matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
      )),
    includeAnyLabel = false
  )

  val twitterWideUserAggregates = AggregateGroup(
    inputSource = timelinesDailyTwitterWideSource,
    preTransforms = Seq(RichRemoveUserIdZero), /* Eliminates reducer skew */
    aggregatePrefitwitter = "twitter_wide_user_aggregate",
    keys = Set(USER_ID),
    features = RecapUserFeatureAggregation.TwitterWideFeatures,
    labels = RecapUserFeatureAggregation.TwitterWideLabels,
    metrics = Set(CountMetric, SumMetric),
    halfLives = Set(50.days),
    outputStore = mkPhysicalStore(
      OfflineAggregateDataRecordStore(
        name = TwitterWideUserAggregateStore,
        startDate = "2016-12-28 00:00",
        commonConfig = timelinesOfflineAggregateSink,
        matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
      ))
  )

  val twitterWideUserAuthorAggregates = AggregateGroup(
    inputSource = timelinesDailyTwitterWideSource,
    preTransforms = Seq(RichRemoveUserIdZero), /* Eliminates reducer skew */
    aggregatePrefitwitter = "twitter_wide_user_author_aggregate",
    keys = Set(USER_ID, AUTHOR_ID),
    features = RecapUserFeatureAggregation.TwitterWideFeatures,
    labels = RecapUserFeatureAggregation.TwitterWideLabels,
    metrics = Set(CountMetric),
    halfLives = Set(50.days),
    outputStore = mkPhysicalStore(
      OfflineAggregateDataRecordStore(
        name = TwitterWideUserAuthorAggregateStore,
        startDate = "2016-12-28 00:00",
        commonConfig = timelinesOfflineAggregateSink,
        matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
      )),
    includeAnyLabel = false
  )

  /**
   * User-HourOfDay and User-DayOfWeek aggregations, both for recap and rectweet
   */
  val userRequestHourAggregates = AggregateGroup(
    inputSource = timelinesDailyRecapMinimalSource,
    aggregatePrefitwitter = "user_request_contetwittert_aggregate.hour",
    preTransforms = Seq(RichRemoveUserIdZero, negativeDownsampleTransform),
    keys = Set(USER_ID, RequestContetwittertFeatures.TIMESTAMP_GMT_HOUR),
    features = Set.empty,
    labels = RecapUserFeatureAggregation.LabelsV2,
    metrics = Set(CountMetric),
    halfLives = Set(50.days),
    outputStore = mkPhysicalStore(
      OfflineAggregateDataRecordStore(
        name = UserRequestHourAggregateStore,
        startDate = "2017-08-01 00:00",
        commonConfig = timelinesOfflineAggregateSink,
        matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
      ))
  )

  val userRequestDowAggregates = AggregateGroup(
    inputSource = timelinesDailyRecapMinimalSource,
    aggregatePrefitwitter = "user_request_contetwittert_aggregate.dow",
    preTransforms = Seq(RichRemoveUserIdZero, negativeDownsampleTransform),
    keys = Set(USER_ID, RequestContetwittertFeatures.TIMESTAMP_GMT_DOW),
    features = Set.empty,
    labels = RecapUserFeatureAggregation.LabelsV2,
    metrics = Set(CountMetric),
    halfLives = Set(50.days),
    outputStore = mkPhysicalStore(
      OfflineAggregateDataRecordStore(
        name = UserRequestDowAggregateStore,
        startDate = "2017-08-01 00:00",
        commonConfig = timelinesOfflineAggregateSink,
        matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
      ))
  )

  val authorTopicAggregates = AggregateGroup(
    inputSource = timelinesDailyRecapMinimalSource,
    aggregatePrefitwitter = "author_topic_aggregate",
    preTransforms = Seq(RichRemoveUserIdZero),
    keys = Set(AUTHOR_ID, TimelinesSharedFeatures.TOPIC_ID),
    features = Set.empty,
    labels = RecapUserFeatureAggregation.LabelsV2,
    metrics = Set(CountMetric),
    halfLives = Set(50.days),
    outputStore = mkPhysicalStore(
      OfflineAggregateDataRecordStore(
        name = AuthorTopicAggregateStore,
        startDate = "2020-05-19 00:00",
        commonConfig = timelinesOfflineAggregateSink,
        matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
      ))
  )

  val userTopicAggregates = AggregateGroup(
    inputSource = timelinesDailyRecapMinimalSource,
    aggregatePrefitwitter = "user_topic_aggregate",
    preTransforms = Seq(RichRemoveUserIdZero),
    keys = Set(USER_ID, TimelinesSharedFeatures.TOPIC_ID),
    features = Set.empty,
    labels = RecapUserFeatureAggregation.LabelsV2,
    metrics = Set(CountMetric),
    halfLives = Set(50.days),
    outputStore = mkPhysicalStore(
      OfflineAggregateDataRecordStore(
        name = UserTopicAggregateStore,
        startDate = "2020-05-23 00:00",
        commonConfig = timelinesOfflineAggregateSink,
        matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
      ))
  )

  val userTopicAggregatesV2 = AggregateGroup(
    inputSource = timelinesDailyRecapMinimalSource,
    aggregatePrefitwitter = "user_topic_aggregate_v2",
    preTransforms = Seq(RichRemoveUserIdZero),
    keys = Set(USER_ID, TimelinesSharedFeatures.TOPIC_ID),
    features = RecapUserFeatureAggregation.UserTopicFeaturesV2Count,
    labels = RecapUserFeatureAggregation.LabelsV2,
    includeAnyFeature = false,
    includeAnyLabel = false,
    metrics = Set(CountMetric),
    halfLives = Set(50.days),
    outputStore = mkPhysicalStore(
      OfflineAggregateDataRecordStore(
        name = UserTopicAggregateStore,
        startDate = "2020-05-23 00:00",
        commonConfig = timelinesOfflineAggregateSink,
        matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
      ))
  )

  val userInferredTopicAggregates = AggregateGroup(
    inputSource = timelinesDailyRecapMinimalSource,
    aggregatePrefitwitter = "user_inferred_topic_aggregate",
    preTransforms = Seq(RichRemoveUserIdZero),
    keys = Set(USER_ID, TimelinesSharedFeatures.INFERRED_TOPIC_IDS),
    features = Set.empty,
    labels = RecapUserFeatureAggregation.LabelsV2,
    metrics = Set(CountMetric),
    halfLives = Set(50.days),
    outputStore = mkPhysicalStore(
      OfflineAggregateDataRecordStore(
        name = UserInferredTopicAggregateStore,
        startDate = "2020-09-09 00:00",
        commonConfig = timelinesOfflineAggregateSink,
        matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
      ))
  )

  val userInferredTopicAggregatesV2 = AggregateGroup(
    inputSource = timelinesDailyRecapMinimalSource,
    aggregatePrefitwitter = "user_inferred_topic_aggregate_v2",
    preTransforms = Seq(RichRemoveUserIdZero),
    keys = Set(USER_ID, TimelinesSharedFeatures.INFERRED_TOPIC_IDS),
    features = RecapUserFeatureAggregation.UserTopicFeaturesV2Count,
    labels = RecapUserFeatureAggregation.LabelsV2,
    includeAnyFeature = false,
    includeAnyLabel = false,
    metrics = Set(CountMetric),
    halfLives = Set(50.days),
    outputStore = mkPhysicalStore(
      OfflineAggregateDataRecordStore(
        name = UserInferredTopicAggregateStore,
        startDate = "2020-09-09 00:00",
        commonConfig = timelinesOfflineAggregateSink,
        matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
      ))
  )

  val userReciprocalEngagementAggregates = AggregateGroup(
    inputSource = timelinesDailyRecapMinimalSource,
    aggregatePrefitwitter = "user_aggregate_v6",
    preTransforms = Seq(RichRemoveUserIdZero),
    keys = Set(USER_ID),
    features = Set.empty,
    labels = RecapUserFeatureAggregation.ReciprocalLabels,
    metrics = Set(CountMetric),
    halfLives = Set(50.days),
    outputStore = mkPhysicalStore(
      OfflineAggregateDataRecordStore(
        name = UserAggregateStore,
        startDate = "2016-07-15 00:00",
        commonConfig = timelinesOfflineAggregateSink,
        matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
      )),
    includeAnyLabel = false
  )

  val userOriginalAuthorReciprocalEngagementAggregates = AggregateGroup(
    inputSource = timelinesDailyRecapMinimalSource,
    aggregatePrefitwitter = "user_original_author_aggregate_v1",
    preTransforms = Seq(RichRemoveUserIdZero, RichRemoveAuthorIdZero),
    keys = Set(USER_ID, TimelinesSharedFeatures.ORIGINAL_AUTHOR_ID),
    features = Set.empty,
    labels = RecapUserFeatureAggregation.ReciprocalLabels,
    metrics = Set(CountMetric),
    halfLives = Set(50.days),
    outputStore = mkPhysicalStore(
      OfflineAggregateDataRecordStore(
        name = UserOriginalAuthorAggregateStore,
        startDate = "2018-12-26 00:00",
        commonConfig = timelinesOfflineAggregateSink,
        matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
      )),
    includeAnyLabel = false
  )

  val originalAuthorReciprocalEngagementAggregates = AggregateGroup(
    inputSource = timelinesDailyRecapMinimalSource,
    aggregatePrefitwitter = "original_author_aggregate_v1",
    preTransforms = Seq(RichRemoveUserIdZero, RichRemoveAuthorIdZero),
    keys = Set(TimelinesSharedFeatures.ORIGINAL_AUTHOR_ID),
    features = Set.empty,
    labels = RecapUserFeatureAggregation.ReciprocalLabels,
    metrics = Set(CountMetric),
    halfLives = Set(50.days),
    outputStore = mkPhysicalStore(
      OfflineAggregateDataRecordStore(
        name = OriginalAuthorAggregateStore,
        startDate = "2023-02-25 00:00",
        commonConfig = timelinesOfflineAggregateSink,
        matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
      )),
    includeAnyLabel = false
  )

  val originalAuthorNegativeEngagementAggregates = AggregateGroup(
    inputSource = timelinesDailyRecapMinimalSource,
    aggregatePrefitwitter = "original_author_aggregate_v2",
    preTransforms = Seq(RichRemoveUserIdZero, RichRemoveAuthorIdZero),
    keys = Set(TimelinesSharedFeatures.ORIGINAL_AUTHOR_ID),
    features = Set.empty,
    labels = RecapUserFeatureAggregation.NegativeEngagementLabels,
    metrics = Set(CountMetric),
    halfLives = Set(50.days),
    outputStore = mkPhysicalStore(
      OfflineAggregateDataRecordStore(
        name = OriginalAuthorAggregateStore,
        startDate = "2023-02-25 00:00",
        commonConfig = timelinesOfflineAggregateSink,
        matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
      )),
    includeAnyLabel = false
  )

  val userListAggregates: AggregateGroup =
    AggregateGroup(
      inputSource = timelinesDailyRecapMinimalSource,
      aggregatePrefitwitter = "user_list_aggregate",
      keys = Set(USER_ID, ListFeatures.LIST_ID),
      features = Set.empty,
      labels = RecapUserFeatureAggregation.LabelsV2,
      metrics = Set(CountMetric),
      halfLives = Set(50.days),
      outputStore = mkPhysicalStore(
        OfflineAggregateDataRecordStore(
          name = UserListAggregateStore,
          startDate = "2020-05-28 00:00",
          commonConfig = timelinesOfflineAggregateSink,
          matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
        )),
      preTransforms = Seq(RichRemoveUserIdZero)
    )

  val userMediaUnderstandingAnnotationAggregates: AggregateGroup = AggregateGroup(
    inputSource = timelinesDailyRecapMinimalSource,
    aggregatePrefitwitter = "user_media_annotation_aggregate",
    preTransforms = Seq(RichRemoveUserIdZero),
    keys =
      Set(USER_ID, SemanticCoreFeatures.mediaUnderstandingHighRecallNonSensitiveEntityIdsFeature),
    features = Set.empty,
    labels = RecapUserFeatureAggregation.LabelsV2,
    metrics = Set(CountMetric),
    halfLives = Set(50.days),
    outputStore = mkPhysicalStore(
      OfflineAggregateDataRecordStore(
        name = UserMediaUnderstandingAnnotationAggregateStore,
        startDate = "2021-03-20 00:00",
        commonConfig = timelinesOfflineAggregateSink
      ))
  )

  val userAuthorGoodClickAggregates = AggregateGroup(
    inputSource = timelinesDailyRecapMinimalSource,
    aggregatePrefitwitter = "user_author_good_click_aggregate",
    preTransforms = Seq(FilterInNetworkTransform, RichRemoveUserIdZero),
    keys = Set(USER_ID, AUTHOR_ID),
    features = RecapUserFeatureAggregation.UserAuthorFeaturesV2,
    labels = RecapUserFeatureAggregation.GoodClickLabels,
    metrics = Set(SumMetric),
    halfLives = Set(14.days),
    outputStore = mkPhysicalStore(
      OfflineAggregateDataRecordStore(
        name = UserAuthorAggregateStore,
        startDate = "2016-07-15 00:00",
        commonConfig = timelinesOfflineAggregateSink,
        matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
      ))
  )

  val userEngagerGoodClickAggregates = AggregateGroup(
    inputSource = timelinesDailyRecapMinimalSource,
    aggregatePrefitwitter = "user_engager_good_click_aggregate",
    keys = Set(USER_ID, EngagementDataRecordFeatures.PublicEngagementUserIds),
    features = Set.empty,
    labels = RecapUserFeatureAggregation.GoodClickLabels,
    metrics = Set(CountMetric),
    halfLives = Set(14.days),
    outputStore = mkPhysicalStore(
      OfflineAggregateDataRecordStore(
        name = UserEngagerAggregateStore,
        startDate = "2016-09-02 00:00",
        commonConfig = timelinesOfflineAggregateSink,
        matwitterKvSourceFailures = defaultMatwitterKvSourceFailures
      )),
    preTransforms = Seq(
      RichRemoveUserIdZero,
      RichUnifyPublicEngagersTransform
    )
  )

}
