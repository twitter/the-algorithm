package com.twitter.timelineranker.model

import com.twitter.servo.util.Gate
import com.twitter.timelines.model.candidate.CandidateTweetSourceId
import com.twitter.timelineranker.{thriftscala => thrift}
import com.twitter.timelines.common.model._
import com.twitter.timelines.earlybird.common.options.EarlybirdOptions
import com.twitter.timelines.earlybird.common.utils.SearchOperator
import com.twitter.timelines.configapi.{
  DependencyProvider => ConfigApiDependencyProvider,
  FutureDependencyProvider => ConfigApiFutureDependencyProvider,
  _
}
import com.twitter.timelines.model.TweetId
import com.twitter.timelines.model.UserId
import com.twitter.timelineservice.DeviceContetwittert

object RecapQuery {

  val EngagedTweetsSupportedTweetKindOption: TweetKindOption.ValueSet = TweetKindOption(
    includeReplies = false,
    includeRetweets = false,
    includeEtwittertendedReplies = false,
    includeOriginalTweetsAndQuotes = true
  )

  val DefaultSearchOperator: SearchOperator.Value = SearchOperator.Etwitterclude
  def fromThrift(query: thrift.RecapQuery): RecapQuery = {

    RecapQuery(
      userId = query.userId,
      matwitterCount = query.matwitterCount,
      range = query.range.map(TimelineRange.fromThrift),
      options = query.options
        .map(options => TweetKindOption.fromThrift(options.to[Set]))
        .getOrElse(TweetKindOption.None),
      searchOperator = query.searchOperator
        .map(SearchOperator.fromThrift)
        .getOrElse(DefaultSearchOperator),
      earlybirdOptions = query.earlybirdOptions.map(EarlybirdOptions.fromThrift),
      deviceContetwittert = query.deviceContetwittert.map(DeviceContetwittert.fromThrift),
      authorIds = query.authorIds,
      etwittercludedTweetIds = query.etwittercludedTweetIds,
      searchClientSubId = query.searchClientSubId,
      candidateTweetSourceId =
        query.candidateTweetSourceId.flatMap(CandidateTweetSourceId.fromThrift),
      hydratesContentFeatures = query.hydratesContentFeatures
    )
  }

  def fromThrift(query: thrift.RecapHydrationQuery): RecapQuery = {
    require(query.tweetIds.nonEmpty, "tweetIds must be non-empty")

    RecapQuery(
      userId = query.userId,
      tweetIds = Some(query.tweetIds),
      searchOperator = DefaultSearchOperator,
      earlybirdOptions = query.earlybirdOptions.map(EarlybirdOptions.fromThrift),
      deviceContetwittert = query.deviceContetwittert.map(DeviceContetwittert.fromThrift),
      candidateTweetSourceId =
        query.candidateTweetSourceId.flatMap(CandidateTweetSourceId.fromThrift),
      hydratesContentFeatures = query.hydratesContentFeatures
    )
  }

  def fromThrift(query: thrift.EngagedTweetsQuery): RecapQuery = {
    val options = query.tweetKindOptions
      .map(tweetKindOptions => TweetKindOption.fromThrift(tweetKindOptions.to[Set]))
      .getOrElse(TweetKindOption.None)

    if (!(options.isEmpty ||
        (options == EngagedTweetsSupportedTweetKindOption))) {
      throw new IllegalArgumentEtwitterception(s"Unsupported TweetKindOption value: $options")
    }

    RecapQuery(
      userId = query.userId,
      matwitterCount = query.matwitterCount,
      range = query.range.map(TimelineRange.fromThrift),
      options = options,
      searchOperator = DefaultSearchOperator,
      earlybirdOptions = query.earlybirdOptions.map(EarlybirdOptions.fromThrift),
      deviceContetwittert = query.deviceContetwittert.map(DeviceContetwittert.fromThrift),
      authorIds = query.userIds,
      etwittercludedTweetIds = query.etwittercludedTweetIds,
    )
  }

  def fromThrift(query: thrift.EntityTweetsQuery): RecapQuery = {
    require(
      query.semanticCoreIds.isDefined,
      "entities(semanticCoreIds) can't be None"
    )
    val options = query.tweetKindOptions
      .map(tweetKindOptions => TweetKindOption.fromThrift(tweetKindOptions.to[Set]))
      .getOrElse(TweetKindOption.None)

    RecapQuery(
      userId = query.userId,
      matwitterCount = query.matwitterCount,
      range = query.range.map(TimelineRange.fromThrift),
      options = options,
      searchOperator = DefaultSearchOperator,
      earlybirdOptions = query.earlybirdOptions.map(EarlybirdOptions.fromThrift),
      deviceContetwittert = query.deviceContetwittert.map(DeviceContetwittert.fromThrift),
      etwittercludedTweetIds = query.etwittercludedTweetIds,
      semanticCoreIds = query.semanticCoreIds.map(_.map(SemanticCoreAnnotation.fromThrift).toSet),
      hashtags = query.hashtags.map(_.toSet),
      languages = query.languages.map(_.map(Language.fromThrift).toSet),
      candidateTweetSourceId =
        query.candidateTweetSourceId.flatMap(CandidateTweetSourceId.fromThrift),
      includeNullcastTweets = query.includeNullcastTweets,
      includeTweetsFromArchiveIndetwitter = query.includeTweetsFromArchiveIndetwitter,
      authorIds = query.authorIds,
      hydratesContentFeatures = query.hydratesContentFeatures
    )
  }

  def fromThrift(query: thrift.UtegLikedByTweetsQuery): RecapQuery = {
    val options = query.tweetKindOptions
      .map(tweetKindOptions => TweetKindOption.fromThrift(tweetKindOptions.to[Set]))
      .getOrElse(TweetKindOption.None)

    RecapQuery(
      userId = query.userId,
      matwitterCount = query.matwitterCount,
      range = query.range.map(TimelineRange.fromThrift),
      options = options,
      earlybirdOptions = query.earlybirdOptions.map(EarlybirdOptions.fromThrift),
      deviceContetwittert = query.deviceContetwittert.map(DeviceContetwittert.fromThrift),
      etwittercludedTweetIds = query.etwittercludedTweetIds,
      utegLikedByTweetsOptions = for {
        utegCount <- query.utegCount
        weightedFollowings <- query.weightedFollowings.map(_.toMap)
      } yield {
        UtegLikedByTweetsOptions(
          utegCount = utegCount,
          isInNetwork = query.isInNetwork,
          weightedFollowings = weightedFollowings
        )
      },
      candidateTweetSourceId =
        query.candidateTweetSourceId.flatMap(CandidateTweetSourceId.fromThrift),
      hydratesContentFeatures = query.hydratesContentFeatures
    )
  }

  val paramGate: (Param[Boolean] => Gate[RecapQuery]) = HasParams.paramGate

  type DependencyProvider[+T] = ConfigApiDependencyProvider[RecapQuery, T]
  object DependencyProvider etwittertends DependencyProviderFunctions[RecapQuery]

  type FutureDependencyProvider[+T] = ConfigApiFutureDependencyProvider[RecapQuery, T]
  object FutureDependencyProvider etwittertends FutureDependencyProviderFunctions[RecapQuery]
}

/**
 * Model object corresponding to RecapQuery thrift struct.
 */
case class RecapQuery(
  userId: UserId,
  matwitterCount: Option[Int] = None,
  range: Option[TimelineRange] = None,
  options: TweetKindOption.ValueSet = TweetKindOption.None,
  searchOperator: SearchOperator.Value = RecapQuery.DefaultSearchOperator,
  earlybirdOptions: Option[EarlybirdOptions] = None,
  deviceContetwittert: Option[DeviceContetwittert] = None,
  authorIds: Option[Seq[UserId]] = None,
  tweetIds: Option[Seq[TweetId]] = None,
  semanticCoreIds: Option[Set[SemanticCoreAnnotation]] = None,
  hashtags: Option[Set[String]] = None,
  languages: Option[Set[Language]] = None,
  etwittercludedTweetIds: Option[Seq[TweetId]] = None,
  // options used only for yml tweets
  utegLikedByTweetsOptions: Option[UtegLikedByTweetsOptions] = None,
  searchClientSubId: Option[String] = None,
  override val params: Params = Params.Empty,
  candidateTweetSourceId: Option[CandidateTweetSourceId.Value] = None,
  includeNullcastTweets: Option[Boolean] = None,
  includeTweetsFromArchiveIndetwitter: Option[Boolean] = None,
  hydratesContentFeatures: Option[Boolean] = None)
    etwittertends HasParams {

  override def toString: String = {
    s"RecapQuery(userId: $userId, matwitterCount: $matwitterCount, range: $range, options: $options, searchOperator: $searchOperator, " +
      s"earlybirdOptions: $earlybirdOptions, deviceContetwittert: $deviceContetwittert, authorIds: $authorIds, " +
      s"tweetIds: $tweetIds, semanticCoreIds: $semanticCoreIds, hashtags: $hashtags, languages: $languages, etwittercludedTweetIds: $etwittercludedTweetIds, " +
      s"utegLikedByTweetsOptions: $utegLikedByTweetsOptions, searchClientSubId: $searchClientSubId, " +
      s"params: $params, candidateTweetSourceId: $candidateTweetSourceId, includeNullcastTweets: $includeNullcastTweets, " +
      s"includeTweetsFromArchiveIndetwitter: $includeTweetsFromArchiveIndetwitter), hydratesContentFeatures: $hydratesContentFeatures"
  }

  def throwIfInvalid(): Unit = {
    def noDuplicates[T <: Traversable[_]](elements: T) = {
      elements.toSet.size == elements.size
    }

    matwitterCount.foreach { matwitter => require(matwitter > 0, "matwitterCount must be a positive integer") }
    range.foreach(_.throwIfInvalid())
    earlybirdOptions.foreach(_.throwIfInvalid())
    tweetIds.foreach { ids => require(ids.nonEmpty, "tweetIds must be nonEmpty if present") }
    semanticCoreIds.foreach(_.foreach(_.throwIfInvalid()))
    languages.foreach(_.foreach(_.throwIfInvalid()))
    languages.foreach { langs =>
      require(langs.nonEmpty, "languages must be nonEmpty if present")
      require(noDuplicates(langs.map(_.language)), "languages must be unique")
    }
  }

  throwIfInvalid()

  def toThriftRecapQuery: thrift.RecapQuery = {
    val thriftOptions = Some(TweetKindOption.toThrift(options))
    thrift.RecapQuery(
      userId,
      matwitterCount,
      range.map(_.toTimelineRangeThrift),
      deprecatedMinCount = None,
      thriftOptions,
      earlybirdOptions.map(_.toThrift),
      deviceContetwittert.map(_.toThrift),
      authorIds,
      etwittercludedTweetIds,
      Some(SearchOperator.toThrift(searchOperator)),
      searchClientSubId,
      candidateTweetSourceId.flatMap(CandidateTweetSourceId.toThrift)
    )
  }

  def toThriftRecapHydrationQuery: thrift.RecapHydrationQuery = {
    require(tweetIds.isDefined && tweetIds.get.nonEmpty, "tweetIds must be present")
    thrift.RecapHydrationQuery(
      userId,
      tweetIds.get,
      earlybirdOptions.map(_.toThrift),
      deviceContetwittert.map(_.toThrift),
      candidateTweetSourceId.flatMap(CandidateTweetSourceId.toThrift)
    )
  }

  def toThriftEntityTweetsQuery: thrift.EntityTweetsQuery = {
    val thriftTweetKindOptions = Some(TweetKindOption.toThrift(options))
    thrift.EntityTweetsQuery(
      userId = userId,
      matwitterCount = matwitterCount,
      range = range.map(_.toTimelineRangeThrift),
      tweetKindOptions = thriftTweetKindOptions,
      earlybirdOptions = earlybirdOptions.map(_.toThrift),
      deviceContetwittert = deviceContetwittert.map(_.toThrift),
      etwittercludedTweetIds = etwittercludedTweetIds,
      semanticCoreIds = semanticCoreIds.map(_.map(_.toThrift)),
      hashtags = hashtags,
      languages = languages.map(_.map(_.toThrift)),
      candidateTweetSourceId.flatMap(CandidateTweetSourceId.toThrift),
      includeNullcastTweets = includeNullcastTweets,
      includeTweetsFromArchiveIndetwitter = includeTweetsFromArchiveIndetwitter,
      authorIds = authorIds
    )
  }

  def toThriftUtegLikedByTweetsQuery: thrift.UtegLikedByTweetsQuery = {

    val thriftTweetKindOptions = Some(TweetKindOption.toThrift(options))
    thrift.UtegLikedByTweetsQuery(
      userId = userId,
      matwitterCount = matwitterCount,
      utegCount = utegLikedByTweetsOptions.map(_.utegCount),
      range = range.map(_.toTimelineRangeThrift),
      tweetKindOptions = thriftTweetKindOptions,
      earlybirdOptions = earlybirdOptions.map(_.toThrift),
      deviceContetwittert = deviceContetwittert.map(_.toThrift),
      etwittercludedTweetIds = etwittercludedTweetIds,
      isInNetwork = utegLikedByTweetsOptions.map(_.isInNetwork).get,
      weightedFollowings = utegLikedByTweetsOptions.map(_.weightedFollowings),
      candidateTweetSourceId = candidateTweetSourceId.flatMap(CandidateTweetSourceId.toThrift)
    )
  }
}
