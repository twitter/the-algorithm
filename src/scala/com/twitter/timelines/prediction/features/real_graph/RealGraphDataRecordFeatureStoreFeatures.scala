package com.twitter.timelines.prediction.features.real_graph

import com.twitter.ml.featurestore.catalog.entities.core.UserAuthor
import com.twitter.ml.featurestore.catalog.features.timelines.RealGraph
import com.twitter.ml.featurestore.lib.EdgeEntityId
import com.twitter.ml.featurestore.lib.UserId
import com.twitter.ml.featurestore.lib.feature.BoundFeatureSet
import com.twitter.ml.featurestore.lib.feature.Feature
import com.twitter.ml.featurestore.lib.feature.FeatureSet

object RealGraphDataRecordFeatureStoreFeatures {
  val boundUserAuthorfeatureSet: BoundFeatureSet = FeatureSet(
    RealGraph.DestId,
    RealGraph.AddressBookEmail.DaysSinceLast,
    RealGraph.AddressBookEmail.ElapsedDays,
    RealGraph.AddressBookEmail.Ewma,
    RealGraph.AddressBookEmail.IsMissing,
    RealGraph.AddressBookEmail.Mean,
    RealGraph.AddressBookEmail.NonZeroDays,
    RealGraph.AddressBookEmail.Variance,
    RealGraph.AddressBookInBoth.DaysSinceLast,
    RealGraph.AddressBookInBoth.ElapsedDays,
    RealGraph.AddressBookInBoth.Ewma,
    RealGraph.AddressBookInBoth.IsMissing,
    RealGraph.AddressBookInBoth.Mean,
    RealGraph.AddressBookInBoth.NonZeroDays,
    RealGraph.AddressBookInBoth.Variance,
    RealGraph.AddressBookMutualEdgeEmail.DaysSinceLast,
    RealGraph.AddressBookMutualEdgeEmail.ElapsedDays,
    RealGraph.AddressBookMutualEdgeEmail.Ewma,
    RealGraph.AddressBookMutualEdgeEmail.IsMissing,
    RealGraph.AddressBookMutualEdgeEmail.Mean,
    RealGraph.AddressBookMutualEdgeEmail.NonZeroDays,
    RealGraph.AddressBookMutualEdgeEmail.Variance,
    RealGraph.AddressBookMutualEdgeInBoth.DaysSinceLast,
    RealGraph.AddressBookMutualEdgeInBoth.ElapsedDays,
    RealGraph.AddressBookMutualEdgeInBoth.Ewma,
    RealGraph.AddressBookMutualEdgeInBoth.IsMissing,
    RealGraph.AddressBookMutualEdgeInBoth.Mean,
    RealGraph.AddressBookMutualEdgeInBoth.NonZeroDays,
    RealGraph.AddressBookMutualEdgeInBoth.Variance,
    RealGraph.AddressBookMutualEdgePhone.DaysSinceLast,
    RealGraph.AddressBookMutualEdgePhone.ElapsedDays,
    RealGraph.AddressBookMutualEdgePhone.Ewma,
    RealGraph.AddressBookMutualEdgePhone.IsMissing,
    RealGraph.AddressBookMutualEdgePhone.Mean,
    RealGraph.AddressBookMutualEdgePhone.NonZeroDays,
    RealGraph.AddressBookMutualEdgePhone.Variance,
    RealGraph.AddressBookPhone.DaysSinceLast,
    RealGraph.AddressBookPhone.ElapsedDays,
    RealGraph.AddressBookPhone.Ewma,
    RealGraph.AddressBookPhone.IsMissing,
    RealGraph.AddressBookPhone.Mean,
    RealGraph.AddressBookPhone.NonZeroDays,
    RealGraph.AddressBookPhone.Variance,
    RealGraph.DirectMessages.DaysSinceLast,
    RealGraph.DirectMessages.ElapsedDays,
    RealGraph.DirectMessages.Ewma,
    RealGraph.DirectMessages.IsMissing,
    RealGraph.DirectMessages.Mean,
    RealGraph.DirectMessages.NonZeroDays,
    RealGraph.DirectMessages.Variance,
    RealGraph.DwellTime.DaysSinceLast,
    RealGraph.DwellTime.ElapsedDays,
    RealGraph.DwellTime.Ewma,
    RealGraph.DwellTime.IsMissing,
    RealGraph.DwellTime.Mean,
    RealGraph.DwellTime.NonZeroDays,
    RealGraph.DwellTime.Variance,
    RealGraph.Follow.DaysSinceLast,
    RealGraph.Follow.ElapsedDays,
    RealGraph.Follow.Ewma,
    RealGraph.Follow.IsMissing,
    RealGraph.Follow.Mean,
    RealGraph.Follow.NonZeroDays,
    RealGraph.Follow.Variance,
    RealGraph.InspectedStatuses.DaysSinceLast,
    RealGraph.InspectedStatuses.ElapsedDays,
    RealGraph.InspectedStatuses.Ewma,
    RealGraph.InspectedStatuses.IsMissing,
    RealGraph.InspectedStatuses.Mean,
    RealGraph.InspectedStatuses.NonZeroDays,
    RealGraph.InspectedStatuses.Variance,
    RealGraph.Likes.DaysSinceLast,
    RealGraph.Likes.ElapsedDays,
    RealGraph.Likes.Ewma,
    RealGraph.Likes.IsMissing,
    RealGraph.Likes.Mean,
    RealGraph.Likes.NonZeroDays,
    RealGraph.Likes.Variance,
    RealGraph.LinkClicks.DaysSinceLast,
    RealGraph.LinkClicks.ElapsedDays,
    RealGraph.LinkClicks.Ewma,
    RealGraph.LinkClicks.IsMissing,
    RealGraph.LinkClicks.Mean,
    RealGraph.LinkClicks.NonZeroDays,
    RealGraph.LinkClicks.Variance,
    RealGraph.Mentions.DaysSinceLast,
    RealGraph.Mentions.ElapsedDays,
    RealGraph.Mentions.Ewma,
    RealGraph.Mentions.IsMissing,
    RealGraph.Mentions.Mean,
    RealGraph.Mentions.NonZeroDays,
    RealGraph.Mentions.Variance,
    RealGraph.MutualFollow.DaysSinceLast,
    RealGraph.MutualFollow.ElapsedDays,
    RealGraph.MutualFollow.Ewma,
    RealGraph.MutualFollow.IsMissing,
    RealGraph.MutualFollow.Mean,
    RealGraph.MutualFollow.NonZeroDays,
    RealGraph.MutualFollow.Variance,
    RealGraph.NumTweetQuotes.DaysSinceLast,
    RealGraph.NumTweetQuotes.ElapsedDays,
    RealGraph.NumTweetQuotes.Ewma,
    RealGraph.NumTweetQuotes.IsMissing,
    RealGraph.NumTweetQuotes.Mean,
    RealGraph.NumTweetQuotes.NonZeroDays,
    RealGraph.NumTweetQuotes.Variance,
    RealGraph.PhotoTags.DaysSinceLast,
    RealGraph.PhotoTags.ElapsedDays,
    RealGraph.PhotoTags.Ewma,
    RealGraph.PhotoTags.IsMissing,
    RealGraph.PhotoTags.Mean,
    RealGraph.PhotoTags.NonZeroDays,
    RealGraph.PhotoTags.Variance,
    RealGraph.ProfileViews.DaysSinceLast,
    RealGraph.ProfileViews.ElapsedDays,
    RealGraph.ProfileViews.Ewma,
    RealGraph.ProfileViews.IsMissing,
    RealGraph.ProfileViews.Mean,
    RealGraph.ProfileViews.NonZeroDays,
    RealGraph.ProfileViews.Variance,
    RealGraph.Retweets.DaysSinceLast,
    RealGraph.Retweets.ElapsedDays,
    RealGraph.Retweets.Ewma,
    RealGraph.Retweets.IsMissing,
    RealGraph.Retweets.Mean,
    RealGraph.Retweets.NonZeroDays,
    RealGraph.Retweets.Variance,
    RealGraph.SmsFollow.DaysSinceLast,
    RealGraph.SmsFollow.ElapsedDays,
    RealGraph.SmsFollow.Ewma,
    RealGraph.SmsFollow.IsMissing,
    RealGraph.SmsFollow.Mean,
    RealGraph.SmsFollow.NonZeroDays,
    RealGraph.SmsFollow.Variance,
    RealGraph.TweetClicks.DaysSinceLast,
    RealGraph.TweetClicks.ElapsedDays,
    RealGraph.TweetClicks.Ewma,
    RealGraph.TweetClicks.IsMissing,
    RealGraph.TweetClicks.Mean,
    RealGraph.TweetClicks.NonZeroDays,
    RealGraph.TweetClicks.Variance,
    RealGraph.Weight
  ).bind(UserAuthor)

  private[this] val edgeFeatures: Seq[RealGraph.EdgeFeature] = Seq(
    RealGraph.AddressBookEmail,
    RealGraph.AddressBookInBoth,
    RealGraph.AddressBookMutualEdgeEmail,
    RealGraph.AddressBookMutualEdgeInBoth,
    RealGraph.AddressBookMutualEdgePhone,
    RealGraph.AddressBookPhone,
    RealGraph.DirectMessages,
    RealGraph.DwellTime,
    RealGraph.Follow,
    RealGraph.InspectedStatuses,
    RealGraph.Likes,
    RealGraph.LinkClicks,
    RealGraph.Mentions,
    RealGraph.MutualFollow,
    RealGraph.PhotoTags,
    RealGraph.ProfileViews,
    RealGraph.Retweets,
    RealGraph.SmsFollow,
    RealGraph.TweetClicks
  )

  val htlDoubleFeatures: Set[Feature[EdgeEntityId[UserId, UserId], Double]] = {
    val features = edgeFeatures.flatMap { ef =>
      Seq(ef.Ewma, ef.Mean, ef.Variance)
    } ++ Seq(RealGraph.Weight)
    features.toSet
  }

  val htlLongFeatures: Set[Feature[EdgeEntityId[UserId, UserId], Long]] = {
    val features = edgeFeatures.flatMap { ef =>
      Seq(ef.DaysSinceLast, ef.ElapsedDays, ef.NonZeroDays)
    }
    features.toSet
  }

  private val edgeFeatureToLegacyName = Map(
    RealGraph.AddressBookEmail -> "num_address_book_email",
    RealGraph.AddressBookInBoth -> "num_address_book_in_both",
    RealGraph.AddressBookMutualEdgeEmail -> "num_address_book_mutual_edge_email",
    RealGraph.AddressBookMutualEdgeInBoth -> "num_address_book_mutual_edge_in_both",
    RealGraph.AddressBookMutualEdgePhone -> "num_address_book_mutual_edge_phone",
    RealGraph.AddressBookPhone -> "num_address_book_phone",
    RealGraph.DirectMessages -> "direct_messages",
    RealGraph.DwellTime -> "total_dwell_time",
    RealGraph.Follow -> "num_follow",
    RealGraph.InspectedStatuses -> "num_inspected_tweets",
    RealGraph.Likes -> "num_favorites",
    RealGraph.LinkClicks -> "num_link_clicks",
    RealGraph.Mentions -> "num_mentions",
    RealGraph.MutualFollow -> "num_mutual_follow",
    RealGraph.PhotoTags -> "num_photo_tags",
    RealGraph.ProfileViews -> "num_profile_views",
    RealGraph.Retweets -> "num_retweets",
    RealGraph.SmsFollow -> "num_sms_follow",
    RealGraph.TweetClicks -> "num_tweet_clicks",
  )

  def convertFeatureToLegacyName(
    prefix: String,
    variance: String = "variance"
  ): Map[Feature[EdgeEntityId[UserId, UserId], _ >: Long with Double <: AnyVal], String] =
    edgeFeatureToLegacyName.flatMap {
      case (k, v) =>
        Seq(
          k.NonZeroDays -> s"${prefix}.${v}.non_zero_days",
          k.DaysSinceLast -> s"${prefix}.${v}.days_since_last",
          k.ElapsedDays -> s"${prefix}.${v}.elapsed_days",
          k.Ewma -> s"${prefix}.${v}.ewma",
          k.Mean -> s"${prefix}.${v}.mean",
          k.Variance -> s"${prefix}.${v}.${variance}",
        )
    } ++ Map(
      RealGraph.Weight -> (prefix + ".weight")
    )
}
