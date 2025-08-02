package com.twitter.search.earlybird.search.relevance.scoring;

import java.io.IOEtwitterception;
import java.util.List;

import com.google.common.collect.Lists;

import org.apache.lucene.search.Etwitterplanation;

import com.twitter.search.common.relevance.features.MutableFeatureNormalizers;
import com.twitter.search.common.schema.base.ImmutableSchemaInterface;
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant;
import com.twitter.search.earlybird.common.userupdates.UserTable;
import com.twitter.search.earlybird.search.AntiGamingFilter;
import com.twitter.search.earlybird.search.relevance.LinearScoringData;
import com.twitter.search.earlybird.search.relevance.LinearScoringParams;
import com.twitter.search.earlybird.thrift.ThriftSearchQuery;
import com.twitter.search.earlybird.thrift.ThriftSearchResultType;

/**
 * Scoring function that uses the weights and boosts provided in the scoring parameters from the
 * request.
 */
public class LinearScoringFunction etwittertends FeatureBasedScoringFunction {
  private static final double BASE_SCORE = 0.0001;

  public LinearScoringFunction(
      ImmutableSchemaInterface schema,
      ThriftSearchQuery searchQuery,
      AntiGamingFilter antiGamingFilter,
      ThriftSearchResultType searchResultType,
      UserTable userTable) throws IOEtwitterception {
    super("LinearScoringFunction", schema, searchQuery, antiGamingFilter, searchResultType,
        userTable);
  }

  @Override
  protected double computeScore(LinearScoringData data, boolean forEtwitterplanation) throws IOEtwitterception {
    double score = BASE_SCORE;

    data.luceneContrib = params.useLuceneScoreAsBoost
        ? 0.0 : params.luceneWeight * data.luceneScore;

    data.reputationContrib = params.reputationWeight * data.userRep;
    data.tetwittertScoreContrib = params.tetwittertScoreWeight * data.tetwittertScore;
    data.parusContrib = params.parusWeight * data.parusScore;

    // contributions from engagement counters. Note that we have "true" argument for all getters,
    // which means all values will get scaled down for scoring, they were unbounded in raw form.
    data.retweetContrib = params.retweetWeight * data.retweetCountPostLog2;
    data.favContrib = params.favWeight * data.favCountPostLog2;
    data.replyContrib = params.replyWeight * data.replyCountPostLog2;
    data.embedsImpressionContrib =
        params.embedsImpressionWeight * data.getEmbedsImpressionCount(true);
    data.embedsUrlContrib =
        params.embedsUrlWeight * data.getEmbedsUrlCount(true);
    data.videoViewContrib =
        params.videoViewWeight * data.getVideoViewCount(true);
    data.quotedContrib =
        params.quotedCountWeight * data.quotedCount;

    for (int i = 0; i < LinearScoringData.MAtwitter_OFFLINE_EtwitterPERIMENTAL_FIELDS; i++) {
      data.offlineEtwitterpFeatureContributions[i] =
          params.rankingOfflineEtwitterpWeights[i] * data.offlineEtwitterpFeatureValues[i];
    }

    data.hasUrlContrib = params.urlWeight * (data.hasUrl ? 1.0 : 0.0);
    data.isReplyContrib = params.isReplyWeight * (data.isReply ? 1.0 : 0.0);
    data.isFollowRetweetContrib =
        params.followRetweetWeight * (data.isRetweet && data.isFollow ? 1.0 : 0.0);
    data.isTrustedRetweetContrib =
        params.trustedRetweetWeight * (data.isRetweet && data.isTrusted ? 1.0 : 0.0);
    double replyCountOriginal = getUnscaledReplyCountFeatureValue();
    data.multipleReplyContrib = params.multipleReplyWeight
        * (replyCountOriginal < params.multipleReplyMinVal ? 0.0 : replyCountOriginal);

    // We directly the query specific score as the contribution below as it doesn't need a weight
    // for contribution computation.
    score += data.luceneContrib
        + data.reputationContrib
        + data.tetwittertScoreContrib
        + data.replyContrib
        + data.multipleReplyContrib
        + data.retweetContrib
        + data.favContrib
        + data.parusContrib
        + data.embedsImpressionContrib
        + data.embedsUrlContrib
        + data.videoViewContrib
        + data.quotedContrib
        + data.hasUrlContrib
        + data.isReplyContrib
        + data.isFollowRetweetContrib
        + data.isTrustedRetweetContrib
        + data.querySpecificScore
        + data.authorSpecificScore;

    for (int i = 0; i < LinearScoringData.MAtwitter_OFFLINE_EtwitterPERIMENTAL_FIELDS; i++) {
      score += data.offlineEtwitterpFeatureContributions[i];
    }

    return score;
  }

  /**
   * Generates the etwitterplanation for the linear score.
   */
  @Override
  protected void generateEtwitterplanationForScoring(
      LinearScoringData scoringData, boolean isHit, List<Etwitterplanation> details) throws IOEtwitterception {
    // 1. Linear components
    final List<Etwitterplanation> linearDetails = Lists.newArrayList();
    addLinearElementEtwitterplanation(
        linearDetails, "[LuceneQueryScore]",
        params.luceneWeight, scoringData.luceneScore, scoringData.luceneContrib);
    if (scoringData.hasCard) {
      if (scoringData.cardAuthorMatchBoostApplied) {
        linearDetails.add(Etwitterplanation.match(
            (float) params.cardAuthorMatchBoosts[scoringData.cardType],
            "[twitter] card author match boost"));
      }
      if (scoringData.cardDescriptionMatchBoostApplied) {
        linearDetails.add(Etwitterplanation.match(
            (float) params.cardDescriptionMatchBoosts[scoringData.cardType],
            "[twitter] card description match boost"));
      }
      if (scoringData.cardDomainMatchBoostApplied) {
        linearDetails.add(Etwitterplanation.match(
            (float) params.cardDomainMatchBoosts[scoringData.cardType],
            "[twitter] card domain match boost"));
      }
      if (scoringData.cardTitleMatchBoostApplied) {
        linearDetails.add(Etwitterplanation.match(
            (float) params.cardTitleMatchBoosts[scoringData.cardType],
            "[twitter] card title match boost"));
      }
    }
    addLinearElementEtwitterplanation(
        linearDetails, "reputation",
        params.reputationWeight, scoringData.userRep, scoringData.reputationContrib);
    addLinearElementEtwitterplanation(
        linearDetails, "tetwittert score",
        params.tetwittertScoreWeight, scoringData.tetwittertScore, scoringData.tetwittertScoreContrib);
    addLinearElementEtwitterplanation(
        linearDetails, "reply count (log2)",
        params.replyWeight, scoringData.replyCountPostLog2, scoringData.replyContrib);
    addLinearElementEtwitterplanation(
        linearDetails, "multi reply",
        params.multipleReplyWeight,
        getUnscaledReplyCountFeatureValue() > params.multipleReplyMinVal ? 1 : 0,
        scoringData.multipleReplyContrib);
    addLinearElementEtwitterplanation(
        linearDetails, "retweet count (log2)",
        params.retweetWeight, scoringData.retweetCountPostLog2, scoringData.retweetContrib);
    addLinearElementEtwitterplanation(
        linearDetails, "fav count (log2)",
        params.favWeight, scoringData.favCountPostLog2, scoringData.favContrib);
    addLinearElementEtwitterplanation(
        linearDetails, "parus score",
        params.parusWeight, scoringData.parusScore, scoringData.parusContrib);
    for (int i = 0; i < LinearScoringData.MAtwitter_OFFLINE_EtwitterPERIMENTAL_FIELDS; i++) {
      if (params.rankingOfflineEtwitterpWeights[i] != LinearScoringParams.DEFAULT_FEATURE_WEIGHT) {
        addLinearElementEtwitterplanation(linearDetails,
            "ranking etwitterp score offline etwitterperimental #" + i,
            params.rankingOfflineEtwitterpWeights[i], scoringData.offlineEtwitterpFeatureValues[i],
            scoringData.offlineEtwitterpFeatureContributions[i]);
      }
    }
    addLinearElementEtwitterplanation(linearDetails,
        "embedded tweet impression count",
        params.embedsImpressionWeight, scoringData.getEmbedsImpressionCount(false),
        scoringData.embedsImpressionContrib);
    addLinearElementEtwitterplanation(linearDetails,
        "embedded tweet url count",
        params.embedsUrlWeight, scoringData.getEmbedsUrlCount(false),
        scoringData.embedsUrlContrib);
    addLinearElementEtwitterplanation(linearDetails,
        "video view count",
        params.videoViewWeight, scoringData.getVideoViewCount(false),
        scoringData.videoViewContrib);
    addLinearElementEtwitterplanation(linearDetails,
        "quoted count",
        params.quotedCountWeight, scoringData.quotedCount, scoringData.quotedContrib);

    addLinearElementEtwitterplanation(
        linearDetails, "has url", params.urlWeight, scoringData.hasUrl ? 1.0 : 0.0,
        scoringData.hasUrlContrib);

    addLinearElementEtwitterplanation(
        linearDetails, "is reply", params.isReplyWeight,
        scoringData.isReply ? 1.0 : 0.0, scoringData.isReplyContrib);
    addLinearElementEtwitterplanation(
        linearDetails, "is follow retweet", params.followRetweetWeight,
        scoringData.isRetweet && scoringData.isFollow ? 1.0 : 0.0,
        scoringData.isFollowRetweetContrib);
    addLinearElementEtwitterplanation(
        linearDetails, "is trusted retweet", params.trustedRetweetWeight,
        scoringData.isRetweet && scoringData.isTrusted ? 1.0 : 0.0,
        scoringData.isTrustedRetweetContrib);

    if (scoringData.querySpecificScore != 0.0) {
      linearDetails.add(Etwitterplanation.match((float) scoringData.querySpecificScore,
          "[+] query specific score adjustment"));
    }
    if (scoringData.authorSpecificScore != 0.0) {
      linearDetails.add(Etwitterplanation.match((float) scoringData.authorSpecificScore,
          "[+] author specific score adjustment"));
    }


    Etwitterplanation linearCombo = isHit
        ? Etwitterplanation.match((float) scoringData.scoreBeforeBoost,
          "(MATCH) Linear components, sum of:", linearDetails)
        : Etwitterplanation.noMatch("Linear components, sum of:", linearDetails);


    details.add(linearCombo);
  }

  private void addLinearElementEtwitterplanation(List<Etwitterplanation> etwitterplanation,
                                           String name,
                                           double weight,
                                           double componentValue,
                                           double contrib) {
    if (contrib == 0.0) {
      return;
    }
    etwitterplanation.add(
        Etwitterplanation.match((float) contrib,
            String.format("[+] %s=%.3f weight=%.3f", name, componentValue, weight)));
  }

  private double getUnscaledReplyCountFeatureValue() throws IOEtwitterception {
    byte featureValue = (byte) documentFeatures.getFeatureValue(EarlybirdFieldConstant.REPLY_COUNT);
    return MutableFeatureNormalizers.BYTE_NORMALIZER.unnormLowerBound(featureValue);
  }
}
