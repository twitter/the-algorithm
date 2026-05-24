package com.twitter.search.earlybird.search.relevance.scoring;

import com.twitter.search.common.util.ml.prediction_engine.BaseLegacyScoreAccumulator;
import com.twitter.search.common.util.ml.prediction_engine.LightweightLinearModel;
import com.twitter.search.earlybird.search.relevance.LinearScoringData;
import com.twitter.search.modeling.tweet_ranking.TweetScoringFeatures;

/**
 * Legacy score accumulator in Earlybird with specific features added.
 * This class is created to avoid adding LinearScoringData as a dependency to search's common ML
 * library.
 *
 * @deprecated This class is retired and we suggest to switch to SchemaBasedScoreAccumulator.
 */
@Deprecated
public class LegacyScoreAccumulator extends BaseLegacyScoreAccumulator<LinearScoringData> {
  /**
   * Constructs with a model and LinearScoringData
   */
  LegacyScoreAccumulator(LightweightLinearModel model) {
    super(model);
  }

  /**
   * Update the accumulator score with features, after this function the score should already
   * be computed.
   *
   * @deprecated This function is retired and we suggest to switch to updateScoresWithFeatures in
   * SchemaBasedScoreAccumulator.
   */
  @Override
  @Deprecated
  protected void updateScoreWithFeatures(LinearScoringData data) {
    addContinuousFeature(TweetScoringFeatures.LUCENE_SCORE, data.luceneScore);
    addContinuousFeature(TweetScoringFeatures.TEXT_SCORE, data.textScore);
    addContinuousFeature(TweetScoringFeatures.TWEET_AGE_IN_SECONDS, data.tweetAgeInSeconds);
    addContinuousFeature(TweetScoringFeatures.REPLY_COUNT, data.replyCountPostLog2);
    addContinuousFeature(TweetScoringFeatures.RETWEET_COUNT, data.retweetCountPostLog2);
    addContinuousFeature(TweetScoringFeatures.FAV_COUNT, data.favCountPostLog2);
    addContinuousFeature(TweetScoringFeatures.REPLY_COUNT_V2, data.replyCountV2);
    addContinuousFeature(TweetScoringFeatures.RETWEET_COUNT_V2, data.retweetCountV2);
    addContinuousFeature(TweetScoringFeatures.FAV_COUNT_V2, data.favCountV2);
    addContinuousFeature(TweetScoringFeatures.EMBEDS_IMPRESSION_COUNT,
        data.getEmbedsImpressionCount(false));
    addContinuousFeature(TweetScoringFeatures.EMBEDS_URL_COUNT, data.getEmbedsUrlCount(false));
    addContinuousFeature(TweetScoringFeatures.VIDEO_VIEW_COUNT, data.getVideoViewCount(false));
    addContinuousFeature(TweetScoringFeatures.QUOTED_COUNT, data.quotedCount);
    addContinuousFeature(TweetScoringFeatures.WEIGHTED_RETWEET_COUNT, data.weightedRetweetCount);
    addContinuousFeature(TweetScoringFeatures.WEIGHTED_REPLY_COUNT, data.weightedReplyCount);
    addContinuousFeature(TweetScoringFeatures.WEIGHTED_FAV_COUNT, data.weightedFavCount);
    addContinuousFeature(TweetScoringFeatures.WEIGHTED_QUOTE_COUNT, data.weightedQuoteCount);
    addBinaryFeature(TweetScoringFeatures.HAS_URL, data.hasUrl);
    addBinaryFeature(TweetScoringFeatures.HAS_CARD, data.hasCard);
    addBinaryFeature(TweetScoringFeatures.HAS_VINE, data.hasVine);
    addBinaryFeature(TweetScoringFeatures.HAS_PERISCOPE, data.hasPeriscope);
    addBinaryFeature(TweetScoringFeatures.HAS_NATIVE_IMAGE, data.hasNativeImage);
    addBinaryFeature(TweetScoringFeatures.HAS_IMAGE_URL, data.hasImageUrl);
    addBinaryFeature(TweetScoringFeatures.HAS_NEWS_URL, data.hasNewsUrl);
    addBinaryFeature(TweetScoringFeatures.HAS_VIDEO_URL, data.hasVideoUrl);
    addBinaryFeature(TweetScoringFeatures.HAS_CONSUMER_VIDEO, data.hasConsumerVideo);
    addBinaryFeature(TweetScoringFeatures.HAS_PRO_VIDEO, data.hasProVideo);
    addBinaryFeature(TweetScoringFeatures.HAS_QUOTE, data.hasQuote);
    addBinaryFeature(TweetScoringFeatures.HAS_TREND, data.hasTrend);
    addBinaryFeature(TweetScoringFeatures.HAS_MULTIPLE_HASHTAGS_OR_TRENDS,
        data.hasMultipleHashtagsOrTrends);
    addBinaryFeature(TweetScoringFeatures.IS_OFFENSIVE, data.isOffensive);
    addBinaryFeature(TweetScoringFeatures.IS_REPLY, data.isReply);
    addBinaryFeature(TweetScoringFeatures.IS_RETWEET, data.isRetweet);
    addBinaryFeature(TweetScoringFeatures.IS_SELF_TWEET, data.isSelfTweet);
    addBinaryFeature(TweetScoringFeatures.IS_FOLLOW_RETWEET, data.isRetweet & data.isFollow);
    addBinaryFeature(TweetScoringFeatures.IS_TRUSTED_RETWEET, data.isRetweet & data.isTrusted);
    addContinuousFeature(TweetScoringFeatures.QUERY_SPECIFIC_SCORE, data.querySpecificScore);
    addContinuousFeature(TweetScoringFeatures.AUTHOR_SPECIFIC_SCORE, data.authorSpecificScore);
    addBinaryFeature(TweetScoringFeatures.AUTHOR_IS_FOLLOW, data.isFollow);
    addBinaryFeature(TweetScoringFeatures.AUTHOR_IS_TRUSTED, data.isTrusted);
    addBinaryFeature(TweetScoringFeatures.AUTHOR_IS_VERIFIED, data.isFromVerifiedAccount);
    addBinaryFeature(TweetScoringFeatures.AUTHOR_IS_NSFW, data.isUserNSFW);
    addBinaryFeature(TweetScoringFeatures.AUTHOR_IS_SPAM, data.isUserSpam);
    addBinaryFeature(TweetScoringFeatures.AUTHOR_IS_BOT, data.isUserBot);
    addBinaryFeature(TweetScoringFeatures.AUTHOR_IS_ANTISOCIAL, data.isUserAntiSocial);
    addContinuousFeature(TweetScoringFeatures.AUTHOR_REPUTATION, data.userRep);
    addContinuousFeature(TweetScoringFeatures.SEARCHER_LANG_SCORE, data.userLangMult);
    addBinaryFeature(TweetScoringFeatures.HAS_DIFFERENT_LANG, data.hasDifferentLang);
    addBinaryFeature(TweetScoringFeatures.HAS_ENGLISH_TWEET_AND_DIFFERENT_UI_LANG,
        data.hasEnglishTweetAndDifferentUILang);
    addBinaryFeature(TweetScoringFeatures.HAS_ENGLISH_UI_AND_DIFFERENT_TWEET_LANG,
        data.hasEnglishUIAndDifferentTweetLang);
    addBinaryFeature(TweetScoringFeatures.IS_SENSITIVE_CONTENT, data.isSensitiveContent);
    addBinaryFeature(TweetScoringFeatures.HAS_MULTIPLE_MEDIA, data.hasMultipleMediaFlag);
    addBinaryFeature(TweetScoringFeatures.AUTHOR_IS_PROFILE_EGG, data.profileIsEggFlag);
    addBinaryFeature(TweetScoringFeatures.AUTHOR_IS_NEW, data.isUserNewFlag);
    addContinuousFeature(TweetScoringFeatures.MENTIONS_COUNT, data.numMentions);
    addContinuousFeature(TweetScoringFeatures.HASHTAGS_COUNT, data.numHashtags);
    addContinuousFeature(TweetScoringFeatures.LINK_LANGUAGE_ID, data.linkLanguage);
    addContinuousFeature(TweetScoringFeatures.LANGUAGE_ID, data.tweetLangId);
    addBinaryFeature(TweetScoringFeatures.HAS_VISIBLE_LINK, data.hasVisibleLink);
  }
}
