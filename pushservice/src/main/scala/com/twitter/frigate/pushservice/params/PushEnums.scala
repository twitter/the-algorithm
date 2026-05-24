package com.twitter.frigate.pushservice.params

/**
 * Enum for naming scores we will scribe for non-personalized high quality candidate generation
 */
object HighQualityScribingScores extends Enumeration {
  type Name = Value
  val HeavyRankingScore = Value
  val NonPersonalizedQualityScoreUsingCnn = Value
  val BqmlNsfwScore = Value
  val BqmlReportScore = Value
}

/**
 * Enum for quality upranking transform
 */
object MrQualityUprankingTransformTypeEnum extends Enumeration {
  val Linear = Value
  val Sigmoid = Value
}

/**
 * Enum for quality partial upranking transform
 */
object MrQualityUprankingPartialTypeEnum extends Enumeration {
  val All = Value
  val Oon = Value
}

/**
 * Enum for bucket membership in DDG 10220 Mr Bold Title Favorite Retweet Notification experiment
 */
object MRBoldTitleFavoriteAndRetweetExperimentEnum extends Enumeration {
  val ShortTitle = Value
}

/**
 * Enum for ML filtering predicates
 */
object QualityPredicateEnum extends Enumeration {
  val WeightedOpenOrNtabClick = Value
  val ExplicitOpenOrNtabClickFilter = Value
  val AlwaysTrue = Value // Disable ML filtering
}

/**
 * Enum to specify normalization used in BigFiltering experiments
 */
object BigFilteringNormalizationEnum extends Enumeration {
  val NormalizationDisabled = Value
  val NormalizeByNotSendingScore = Value
}

/**
 * Enum for inline actions
 */
object InlineActionsEnum extends Enumeration {
  val Favorite = Value
  val Follow = Value
  val Reply = Value
  val Retweet = Value
}

/**
 * Enum for template format
 */
object IbisTemplateFormatEnum extends Enumeration {
  val template1 = Value
}

/**
 * Enum for Store name for Top Tweets By Geo
 */
object TopTweetsForGeoCombination extends Enumeration {
  val Default = Value
  val AccountsTweetFavAsBackfill = Value
  val AccountsTweetFavIntermixed = Value
}

/**
 * Enum for scoring function for Top Tweets By Geo
 */
object TopTweetsForGeoRankingFunction extends Enumeration {
  val Score = Value
  val GeohashLengthAndThenScore = Value
}

/**
 * Enum for which version of popgeo tweets to be using
 */
object PopGeoTweetVersion extends Enumeration {
  val Prod = Value
}

/**
 * Enum for Subtext in Android header
 */
object SubtextForAndroidPushHeader extends Enumeration {
  val None = Value
  val TargetHandler = Value
  val TargetTagHandler = Value
  val TargetName = Value
  val AuthorTagHandler = Value
  val AuthorName = Value
}

object NsfwTextDetectionModel extends Enumeration {
  val ProdModel = Value
  val RetrainedModel = Value
}

object HighQualityCandidateGroupEnum extends Enumeration {
  val AgeBucket = Value
  val Language = Value
  val Topic = Value
  val Country = Value
  val Admin0 = Value
  val Admin1 = Value
}

object CrtGroupEnum extends Enumeration {
  val Twistly = Value
  val Frs = Value
  val F1 = Value
  val Topic = Value
  val Trip = Value
  val GeoPop = Value
  val Other = Value
  val None = Value
}

object SportGameEnum extends Enumeration {
  val Soccer = Value
  val Nfl = Value
}
