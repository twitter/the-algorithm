package com.twitter.search.earlybird.search.relevance;

import java.util.Arrays;
import java.util.Map;

import com.google.common.annotations.VisibleForTesting;

import com.twitter.search.common.constants.SearchCardType;
import com.twitter.search.common.constants.thriftjava.ThriftLanguage;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.ranking.thriftjava.ThriftAgeDecayRankingParams;
import com.twitter.search.common.ranking.thriftjava.ThriftCardRankingParams;
import com.twitter.search.common.ranking.thriftjava.ThriftRankingParams;
import com.twitter.search.common.util.lang.ThriftLanguageUtil;
import com.twitter.search.earlybird.thrift.ThriftSearchQuery;
import com.twitter.search.earlybird.thrift.ThriftSocialFilterType;

/*
 * The class for all query specific parameters, including the parameters from the relevanceOptions and
 * values that are etwittertracted from the request itself.
 */
public class LinearScoringParams {

  public static final double DEFAULT_FEATURE_WEIGHT = 0;
  public static final double DEFAULT_FEATURE_MIN_VAL = 0;
  public static final double DEFAULT_NO_BOOST = 1.0;
  @VisibleForTesting
  static final SearchCounter NULL_USER_LANGS_KEY =
      SearchCounter.etwitterport("linear_scoring_params_null_user_langs_key");

  public final double luceneWeight;
  public final double tetwittertScoreWeight;
  public final double tetwittertScoreMinVal;
  public final double retweetWeight;
  public final double retweetMinVal;
  public final double favWeight;
  public final double favMinVal;
  public final double replyWeight;
  public final double multipleReplyWeight;
  public final double multipleReplyMinVal;
  public final double isReplyWeight;
  public final double parusWeight;
  public final double embedsImpressionWeight;
  public final double embedsUrlWeight;
  public final double videoViewWeight;
  public final double quotedCountWeight;

  public final double[] rankingOfflineEtwitterpWeights =
      new double[LinearScoringData.MAtwitter_OFFLINE_EtwitterPERIMENTAL_FIELDS];

  public final boolean applyBoosts;

  // Storing ranking params for cards, avoid using maps for faster lookup
  public final double[] hasCardBoosts = new double[SearchCardType.values().length];
  public final double[] cardDomainMatchBoosts = new double[SearchCardType.values().length];
  public final double[] cardAuthorMatchBoosts = new double[SearchCardType.values().length];
  public final double[] cardTitleMatchBoosts = new double[SearchCardType.values().length];
  public final double[] cardDescriptionMatchBoosts = new double[SearchCardType.values().length];

  public final double urlWeight;
  public final double reputationWeight;
  public final double reputationMinVal;
  public final double followRetweetWeight;
  public final double trustedRetweetWeight;

  // Adjustments for specific tweets (tweetId -> score)
  public final Map<Long, Double> querySpecificScoreAdjustments;

  // Adjustments for tweets posted by specific authors (userId -> score)
  public final Map<Long, Double> authorSpecificScoreAdjustments;

  public final double offensiveDamping;
  public final double spamUserDamping;
  public final double nsfwUserDamping;
  public final double botUserDamping;
  public final double trustedCircleBoost;
  public final double directFollowBoost;
  public final double minScore;

  public final boolean applyFiltersAlways;

  public final boolean useLuceneScoreAsBoost;
  public final double matwitterLuceneScoreBoost;

  public final double langEnglishTweetDemote;
  public final double langEnglishUIDemote;
  public final double langDefaultDemote;
  public final boolean useUserLanguageInfo;
  public final double unknownLanguageBoost;

  public final double outOfNetworkReplyPenalty;

  public final boolean useAgeDecay;
  public final double ageDecayHalflife;
  public final double ageDecayBase;
  public final double ageDecaySlope;

  // hit attribute demotions
  public final boolean enableHitDemotion;
  public final double noTetwittertHitDemotion;
  public final double urlOnlyHitDemotion;
  public final double nameOnlyHitDemotion;
  public final double separateTetwittertAndNameHitDemotion;
  public final double separateTetwittertAndUrlHitDemotion;

  // trends related params
  public final double tweetHasTrendBoost;
  public final double multipleHashtagsOrTrendsDamping;

  public final double tweetFromVerifiedAccountBoost;

  public final double tweetFromBlueVerifiedAccountBoost;

  public final ThriftSocialFilterType socialFilterType;
  public final int uiLangId;
  // Confidences of the understandability of different languages for this user.
  public final double[] userLangs = new double[ThriftLanguage.values().length];

  public final long searcherId;
  public final double selfTweetBoost;

  public final double tweetHasMediaUrlBoost;
  public final double tweetHasNewsUrlBoost;

  // whether we need meta-data for replies what the reply is to.
  public final boolean getInReplyToStatusId;

  // Initialize from a ranking parameter
  public LinearScoringParams(ThriftSearchQuery searchQuery, ThriftRankingParams params) {
    // weights
    luceneWeight = params.isSetLuceneScoreParams()
        ? params.getLuceneScoreParams().getWeight() : DEFAULT_FEATURE_WEIGHT;
    tetwittertScoreWeight = params.isSetTetwittertScoreParams()
        ? params.getTetwittertScoreParams().getWeight() : DEFAULT_FEATURE_WEIGHT;
    retweetWeight = params.isSetRetweetCountParams()
        ? params.getRetweetCountParams().getWeight() : DEFAULT_FEATURE_WEIGHT;
    favWeight = params.isSetFavCountParams()
        ? params.getFavCountParams().getWeight() : DEFAULT_FEATURE_WEIGHT;
    replyWeight = params.isSetReplyCountParams()
        ? params.getReplyCountParams().getWeight() : DEFAULT_FEATURE_WEIGHT;
    multipleReplyWeight = params.isSetMultipleReplyCountParams()
        ? params.getMultipleReplyCountParams().getWeight() : DEFAULT_FEATURE_WEIGHT;
    parusWeight = params.isSetParusScoreParams()
        ? params.getParusScoreParams().getWeight() : DEFAULT_FEATURE_WEIGHT;
    for (int i = 0; i < LinearScoringData.MAtwitter_OFFLINE_EtwitterPERIMENTAL_FIELDS; i++) {
      Byte featureTypeByte = (byte) i;
      // default weight is 0, thus contribution for unset feature value will be 0.
      rankingOfflineEtwitterpWeights[i] = params.getOfflineEtwitterperimentalFeatureRankingParamsSize() > 0
          && params.getOfflineEtwitterperimentalFeatureRankingParams().containsKey(featureTypeByte)
              ? params.getOfflineEtwitterperimentalFeatureRankingParams().get(featureTypeByte).getWeight()
              : DEFAULT_FEATURE_WEIGHT;
    }
    embedsImpressionWeight = params.isSetEmbedsImpressionCountParams()
        ? params.getEmbedsImpressionCountParams().getWeight() : DEFAULT_FEATURE_WEIGHT;
    embedsUrlWeight = params.isSetEmbedsUrlCountParams()
        ? params.getEmbedsUrlCountParams().getWeight() : DEFAULT_FEATURE_WEIGHT;
    videoViewWeight = params.isSetVideoViewCountParams()
        ? params.getVideoViewCountParams().getWeight() : DEFAULT_FEATURE_WEIGHT;
    quotedCountWeight = params.isSetQuotedCountParams()
        ? params.getQuotedCountParams().getWeight() : DEFAULT_FEATURE_WEIGHT;

    applyBoosts = params.isApplyBoosts();

    // configure card values
    Arrays.fill(hasCardBoosts, DEFAULT_NO_BOOST);
    Arrays.fill(cardAuthorMatchBoosts, DEFAULT_NO_BOOST);
    Arrays.fill(cardDomainMatchBoosts, DEFAULT_NO_BOOST);
    Arrays.fill(cardTitleMatchBoosts, DEFAULT_NO_BOOST);
    Arrays.fill(cardDescriptionMatchBoosts, DEFAULT_NO_BOOST);
    if (params.isSetCardRankingParams()) {
      for (SearchCardType cardType : SearchCardType.values()) {
        byte cardTypeIndetwitter = cardType.getByteValue();
        ThriftCardRankingParams rankingParams = params.getCardRankingParams().get(cardTypeIndetwitter);
        if (rankingParams != null) {
          hasCardBoosts[cardTypeIndetwitter] = rankingParams.getHasCardBoost();
          cardAuthorMatchBoosts[cardTypeIndetwitter] = rankingParams.getAuthorMatchBoost();
          cardDomainMatchBoosts[cardTypeIndetwitter] = rankingParams.getDomainMatchBoost();
          cardTitleMatchBoosts[cardTypeIndetwitter] = rankingParams.getTitleMatchBoost();
          cardDescriptionMatchBoosts[cardTypeIndetwitter] = rankingParams.getDescriptionMatchBoost();
        }
      }
    }

    urlWeight = params.isSetUrlParams()
        ? params.getUrlParams().getWeight() : DEFAULT_FEATURE_WEIGHT;
    reputationWeight = params.isSetReputationParams()
        ? params.getReputationParams().getWeight() : DEFAULT_FEATURE_WEIGHT;
    isReplyWeight = params.isSetIsReplyParams()
        ? params.getIsReplyParams().getWeight() : DEFAULT_FEATURE_WEIGHT;
    followRetweetWeight = params.isSetDirectFollowRetweetCountParams()
        ? params.getDirectFollowRetweetCountParams().getWeight() : DEFAULT_FEATURE_WEIGHT;
    trustedRetweetWeight = params.isSetTrustedCircleRetweetCountParams()
        ? params.getTrustedCircleRetweetCountParams().getWeight() : DEFAULT_FEATURE_WEIGHT;

    querySpecificScoreAdjustments = params.getQuerySpecificScoreAdjustments();
    authorSpecificScoreAdjustments = params.getAuthorSpecificScoreAdjustments();

    // min/matwitter filters
    tetwittertScoreMinVal = params.isSetTetwittertScoreParams()
        ? params.getTetwittertScoreParams().getMin() : DEFAULT_FEATURE_MIN_VAL;
    reputationMinVal = params.isSetReputationParams()
        ? params.getReputationParams().getMin() : DEFAULT_FEATURE_MIN_VAL;
    multipleReplyMinVal = params.isSetMultipleReplyCountParams()
        ? params.getMultipleReplyCountParams().getMin() : DEFAULT_FEATURE_MIN_VAL;
    retweetMinVal = params.isSetRetweetCountParams() && params.getRetweetCountParams().isSetMin()
        ? params.getRetweetCountParams().getMin() : DEFAULT_FEATURE_MIN_VAL;
    favMinVal = params.isSetFavCountParams() && params.getFavCountParams().isSetMin()
        ? params.getFavCountParams().getMin() : DEFAULT_FEATURE_MIN_VAL;

    // boosts
    spamUserDamping = params.isSetSpamUserBoost() ? params.getSpamUserBoost() : 1.0;
    nsfwUserDamping = params.isSetNsfwUserBoost() ? params.getNsfwUserBoost() : 1.0;
    botUserDamping = params.isSetBotUserBoost() ? params.getBotUserBoost() : 1.0;
    offensiveDamping = params.getOffensiveBoost();
    trustedCircleBoost = params.getInTrustedCircleBoost();
    directFollowBoost = params.getInDirectFollowBoost();

    // language boosts
    langEnglishTweetDemote = params.getLangEnglishTweetBoost();
    langEnglishUIDemote = params.getLangEnglishUIBoost();
    langDefaultDemote = params.getLangDefaultBoost();
    useUserLanguageInfo = params.isUseUserLanguageInfo();
    unknownLanguageBoost = params.getUnknownLanguageBoost();

    // hit demotions
    enableHitDemotion = params.isEnableHitDemotion();
    noTetwittertHitDemotion = params.getNoTetwittertHitDemotion();
    urlOnlyHitDemotion = params.getUrlOnlyHitDemotion();
    nameOnlyHitDemotion = params.getNameOnlyHitDemotion();
    separateTetwittertAndNameHitDemotion = params.getSeparateTetwittertAndNameHitDemotion();
    separateTetwittertAndUrlHitDemotion = params.getSeparateTetwittertAndUrlHitDemotion();

    outOfNetworkReplyPenalty = params.getOutOfNetworkReplyPenalty();

    if (params.isSetAgeDecayParams()) {
      // new age decay settings
      ThriftAgeDecayRankingParams ageDecayParams = params.getAgeDecayParams();
      ageDecaySlope = ageDecayParams.getSlope();
      ageDecayHalflife = ageDecayParams.getHalflife();
      ageDecayBase = ageDecayParams.getBase();
      useAgeDecay = true;
    } else if (params.isSetDeprecatedAgeDecayBase()
        && params.isSetDeprecatedAgeDecayHalflife()
        && params.isSetDeprecatedAgeDecaySlope()) {
      ageDecaySlope = params.getDeprecatedAgeDecaySlope();
      ageDecayHalflife = params.getDeprecatedAgeDecayHalflife();
      ageDecayBase = params.getDeprecatedAgeDecayBase();
      useAgeDecay = true;
    } else {
      ageDecaySlope = 0.0;
      ageDecayHalflife = 0.0;
      ageDecayBase = 0.0;
      useAgeDecay = false;
    }

    // trends
    tweetHasTrendBoost = params.getTweetHasTrendBoost();
    multipleHashtagsOrTrendsDamping = params.getMultipleHashtagsOrTrendsBoost();

    // verified accounts
    tweetFromVerifiedAccountBoost = params.getTweetFromVerifiedAccountBoost();
    tweetFromBlueVerifiedAccountBoost = params.getTweetFromBlueVerifiedAccountBoost();

    // score filter
    minScore = params.getMinScore();

    applyFiltersAlways = params.isApplyFiltersAlways();

    useLuceneScoreAsBoost = params.isUseLuceneScoreAsBoost();
    matwitterLuceneScoreBoost = params.getMatwitterLuceneScoreBoost();

    searcherId = searchQuery.isSetSearcherId() ? searchQuery.getSearcherId() : -1;
    selfTweetBoost = params.getSelfTweetBoost();

    socialFilterType = searchQuery.getSocialFilterType();

    // the UI language and the confidences of the languages user can understand.
    if (!searchQuery.isSetUiLang() || searchQuery.getUiLang().isEmpty()) {
      uiLangId = ThriftLanguage.UNKNOWN.getValue();
    } else {
      uiLangId = ThriftLanguageUtil.getThriftLanguageOf(searchQuery.getUiLang()).getValue();
    }
    if (searchQuery.getUserLangsSize() > 0) {
      for (Map.Entry<ThriftLanguage, Double> lang : searchQuery.getUserLangs().entrySet()) {
        ThriftLanguage thriftLanguage = lang.getKey();
        // SEARCH-13441
        if (thriftLanguage != null) {
          userLangs[thriftLanguage.getValue()] = lang.getValue();
        } else {
          NULL_USER_LANGS_KEY.increment();
        }
      }
    }

    // For now, we will use the same boost for both image, and video.
    tweetHasMediaUrlBoost = params.getTweetHasImageUrlBoost();
    tweetHasNewsUrlBoost = params.getTweetHasNewsUrlBoost();

    getInReplyToStatusId =
        searchQuery.isSetResultMetadataOptions()
            && searchQuery.getResultMetadataOptions().isSetGetInReplyToStatusId()
            && searchQuery.getResultMetadataOptions().isGetInReplyToStatusId();
  }
}
