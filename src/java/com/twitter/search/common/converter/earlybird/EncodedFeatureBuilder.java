package com.twitter.search.common.converter.earlybird;

import java.io.IOEtwitterception;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regetwitter.Matcher;
import java.util.regetwitter.Pattern;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.tetwittert.token.TokenizedCharSequence;
import com.twitter.common.tetwittert.token.TokenizedCharSequenceStream;
import com.twitter.common.tetwittert.util.TokenStreamSerializer;
import com.twitter.common_internal.tetwittert.version.PenguinVersion;
import com.twitter.search.common.indetwittering.thriftjava.Place;
import com.twitter.search.common.indetwittering.thriftjava.PotentialLocation;
import com.twitter.search.common.indetwittering.thriftjava.ProfileGeoEnrichment;
import com.twitter.search.common.indetwittering.thriftjava.ThriftEtwitterpandedUrl;
import com.twitter.search.common.indetwittering.thriftjava.VersionedTweetFeatures;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.relevance.entities.PotentialLocationObject;
import com.twitter.search.common.relevance.entities.TwitterMessage;
import com.twitter.search.common.relevance.features.FeatureSink;
import com.twitter.search.common.relevance.features.MutableFeatureNormalizers;
import com.twitter.search.common.relevance.features.RelevanceSignalConstants;
import com.twitter.search.common.relevance.features.TweetTetwittertFeatures;
import com.twitter.search.common.relevance.features.TweetTetwittertQuality;
import com.twitter.search.common.relevance.features.TweetUserFeatures;
import com.twitter.search.common.schema.base.FeatureConfiguration;
import com.twitter.search.common.schema.base.ImmutableSchemaInterface;
import com.twitter.search.common.schema.earlybird.EarlybirdEncodedFeatures;
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant;
import com.twitter.search.common.util.lang.ThriftLanguageUtil;
import com.twitter.search.common.util.tetwittert.LanguageIdentifierHelper;
import com.twitter.search.common.util.tetwittert.NormalizerHelper;
import com.twitter.search.common.util.tetwittert.SourceNormalizer;
import com.twitter.search.common.util.tetwittert.TokenizerHelper;
import com.twitter.search.common.util.tetwittert.TokenizerResult;
import com.twitter.search.common.util.tetwittert.TweetTokenStreamSerializer;
import com.twitter.search.common.util.url.LinkVisibilityUtils;
import com.twitter.search.common.util.url.NativeVideoClassificationUtils;
import com.twitter.search.ingester.model.VisibleTokenRatioUtil;

/**
 * EncodedFeatureBuilder helps to build encoded features for TwitterMessage.
 *
 * This is stateful so should only be used one tweet at a time
 */
public class EncodedFeatureBuilder {
  private static final Logger LOG = LoggerFactory.getLogger(EncodedFeatureBuilder.class);

  private static final SearchCounter NUM_TWEETS_WITH_INVALID_TWEET_ID_IN_PHOTO_URL =
      SearchCounter.etwitterport("tweets_with_invalid_tweet_id_in_photo_url");

  // TwitterTokenStream for converting TokenizedCharSequence into a stream for serialization
  // This is stateful so should only be used one tweet at a time
  private final TokenizedCharSequenceStream tokenSeqStream = new TokenizedCharSequenceStream();

  // SUPPRESS CHECKSTYLE:OFF LineLength
  private static final Pattern TWITTER_PHOTO_PERMA_LINK_PATTERN =
          Pattern.compile("(?i:^(?:(?:https?\\:\\/\\/)?(?:www\\.)?)?twitter\\.com\\/(?:\\?[^#]+)?(?:#!?\\/?)?\\w{1,20}\\/status\\/(\\d+)\\/photo\\/\\d*$)");

  private static final Pattern TWITTER_PHOTO_COPY_PASTE_LINK_PATTERN =
          Pattern.compile("(?i:^(?:(?:https?\\:\\/\\/)?(?:www\\.)?)?twitter\\.com\\/(?:#!?\\/)?\\w{1,20}\\/status\\/(\\d+)\\/photo\\/\\d*$)");
  // SUPPRESS CHECKSTYLE:ON LineLength

  private static final VisibleTokenRatioUtil VISIBLE_TOKEN_RATIO = new VisibleTokenRatioUtil();

  private static final Map<PenguinVersion, SearchCounter> SERIALIZE_FAILURE_COUNTERS_MAP =
      Maps.newEnumMap(PenguinVersion.class);
  static {
    for (PenguinVersion penguinVersion : PenguinVersion.values()) {
      SERIALIZE_FAILURE_COUNTERS_MAP.put(
          penguinVersion,
          SearchCounter.etwitterport(
              "tokenstream_serialization_failure_" + penguinVersion.name().toLowerCase()));
    }
  }

  public static class TweetFeatureWithEncodeFeatures {
    public final VersionedTweetFeatures versionedFeatures;
    public final EarlybirdEncodedFeatures encodedFeatures;
    public final EarlybirdEncodedFeatures etwittertendedEncodedFeatures;

    public TweetFeatureWithEncodeFeatures(
        VersionedTweetFeatures versionedFeatures,
        EarlybirdEncodedFeatures encodedFeatures,
        EarlybirdEncodedFeatures etwittertendedEncodedFeatures) {
      this.versionedFeatures = versionedFeatures;
      this.encodedFeatures = encodedFeatures;
      this.etwittertendedEncodedFeatures = etwittertendedEncodedFeatures;
    }
  }

  /**
   * Create tweet tetwittert features and the encoded features.
   *
   * @param message the tweet message
   * @param penguinVersion the based penguin version to create the features
   * @param schemaSnapshot the schema associated with the features
   * @return the tetwittert features and the encoded features
   */
  public TweetFeatureWithEncodeFeatures createTweetFeaturesFromTwitterMessage(
      TwitterMessage message,
      PenguinVersion penguinVersion,
      ImmutableSchemaInterface schemaSnapshot) {
    VersionedTweetFeatures versionedTweetFeatures = new VersionedTweetFeatures();

    // Write etwittertendedPackedFeatures.
    EarlybirdEncodedFeatures etwittertendedEncodedFeatures =
        createEtwittertendedEncodedFeaturesFromTwitterMessage(message, penguinVersion, schemaSnapshot);
    if (etwittertendedEncodedFeatures != null) {
      etwittertendedEncodedFeatures
          .writeEtwittertendedFeaturesToVersionedTweetFeatures(versionedTweetFeatures);
    }

    setSourceAndNormalizedSource(
        message.getStrippedSource(), versionedTweetFeatures, penguinVersion);

    TweetTetwittertFeatures tetwittertFeatures = message.getTweetTetwittertFeatures(penguinVersion);

    ///////////////////////////////
    // Add hashtags and mentions
    tetwittertFeatures.getHashtags().forEach(versionedTweetFeatures::addToHashtags);
    tetwittertFeatures.getMentions().forEach(versionedTweetFeatures::addToMentions);

    ///////////////////////////////
    // Etwittertract some etwittertra information from the message tetwittert.
    // Indetwitter stock symbols with $ prepended
    tetwittertFeatures.getStocks().stream()
        .filter(stock -> stock != null)
        .forEach(stock -> versionedTweetFeatures.addToStocks(stock.toLowerCase()));

    // Question marks
    versionedTweetFeatures.setHasQuestionMark(tetwittertFeatures.hasQuestionMark());
    // Smileys
    versionedTweetFeatures.setHasPositiveSmiley(tetwittertFeatures.hasPositiveSmiley());
    versionedTweetFeatures.setHasNegativeSmiley(tetwittertFeatures.hasNegativeSmiley());

    TokenStreamSerializer streamSerializer =
        TweetTokenStreamSerializer.getTweetTokenStreamSerializer();
    TokenizedCharSequence tokenSeq = tetwittertFeatures.getTokenSequence();
    tokenSeqStream.reset(tokenSeq);
    int tokenPercent = VISIBLE_TOKEN_RATIO.etwittertractAndNormalizeTokenPercentage(tokenSeqStream);
    tokenSeqStream.reset(tokenSeq);

    // Write packedFeatures.
    EarlybirdEncodedFeatures encodedFeatures = createEncodedFeaturesFromTwitterMessage(
        message, penguinVersion, schemaSnapshot, tokenPercent);
    encodedFeatures.writeFeaturesToVersionedTweetFeatures(versionedTweetFeatures);

    try {
      versionedTweetFeatures.setTweetTokenStream(streamSerializer.serialize(tokenSeqStream));
      versionedTweetFeatures.setTweetTokenStreamTetwittert(tokenSeq.toString());
    } catch (IOEtwitterception e) {
      LOG.error("TwitterTokenStream serialization error! Could not serialize: "
          + tokenSeq.toString());
      SERIALIZE_FAILURE_COUNTERS_MAP.get(penguinVersion).increment();
      versionedTweetFeatures.unsetTweetTokenStream();
      versionedTweetFeatures.unsetTweetTokenStreamTetwittert();
    }

    // User name features
    if (message.getFromUserDisplayName().isPresent()) {
      Locale locale = LanguageIdentifierHelper
          .identifyLanguage(message.getFromUserDisplayName().get());
      String normalizedDisplayName = NormalizerHelper.normalize(
          message.getFromUserDisplayName().get(), locale, penguinVersion);
      TokenizerResult result = TokenizerHelper
          .tokenizeTweet(normalizedDisplayName, locale, penguinVersion);
      tokenSeqStream.reset(result.tokenSequence);
      try {
        versionedTweetFeatures.setUserDisplayNameTokenStream(
            streamSerializer.serialize(tokenSeqStream));
        versionedTweetFeatures.setUserDisplayNameTokenStreamTetwittert(result.tokenSequence.toString());
      } catch (IOEtwitterception e) {
        LOG.error("TwitterTokenStream serialization error! Could not serialize: "
            + message.getFromUserDisplayName().get());
        SERIALIZE_FAILURE_COUNTERS_MAP.get(penguinVersion).increment();
        versionedTweetFeatures.unsetUserDisplayNameTokenStream();
        versionedTweetFeatures.unsetUserDisplayNameTokenStreamTetwittert();
      }
    }

    String resolvedUrlsTetwittert = Joiner.on(" ").skipNulls().join(tetwittertFeatures.getResolvedUrlTokens());
    versionedTweetFeatures.setNormalizedResolvedUrlTetwittert(resolvedUrlsTetwittert);

    addPlace(message, versionedTweetFeatures, penguinVersion);
    addProfileGeoEnrichment(message, versionedTweetFeatures, penguinVersion);

    versionedTweetFeatures.setTweetSignature(message.getTweetSignature(penguinVersion));

    return new TweetFeatureWithEncodeFeatures(
        versionedTweetFeatures, encodedFeatures, etwittertendedEncodedFeatures);
  }


  protected static void setSourceAndNormalizedSource(
      String strippedSource,
      VersionedTweetFeatures versionedTweetFeatures,
      PenguinVersion penguinVersion) {

    if (strippedSource != null && !strippedSource.isEmpty()) {
      // normalize source for searchable field - replaces whitespace with underscores (???).
      versionedTweetFeatures.setNormalizedSource(
          SourceNormalizer.normalize(strippedSource, penguinVersion));

      // source facet has simpler normalization.
      Locale locale = LanguageIdentifierHelper.identifyLanguage(strippedSource);
      versionedTweetFeatures.setSource(NormalizerHelper.normalizeKeepCase(
          strippedSource, locale, penguinVersion));
    }
  }

  /**
   * Adds the given photo url to the thrift status if it is a twitter photo permalink.
   * Returns true, if this was indeed a twitter photo, false otherwise.
   */
  public static boolean addPhotoUrl(TwitterMessage message, String photoPermalink) {
    Matcher matcher = TWITTER_PHOTO_COPY_PASTE_LINK_PATTERN.matcher(photoPermalink);
    if (!matcher.matches() || matcher.groupCount() < 1) {
      matcher = TWITTER_PHOTO_PERMA_LINK_PATTERN.matcher(photoPermalink);
    }

    if (matcher.matches() && matcher.groupCount() == 1) {
      // this is a native photo url which we need to store in a separate field
      String idStr = matcher.group(1);
      if (idStr != null) {
        // idStr should be a valid tweet ID (and therefore, should fit into a Long), but we have
        // tweets for which idStr is a long sequence of digits that does not fit into a Long.
        try {
          long photoStatusId = Long.parseLong(idStr);
          message.addPhotoUrl(photoStatusId, null);
        } catch (NumberFormatEtwitterception e) {
          LOG.warn("Found a tweet with a photo URL with an invalid tweet ID: " + message);
          NUM_TWEETS_WITH_INVALID_TWEET_ID_IN_PHOTO_URL.increment();
        }
      }
      return true;
    }
    return false;
  }

  private void addPlace(TwitterMessage message,
                        VersionedTweetFeatures versionedTweetFeatures,
                        PenguinVersion penguinVersion) {
    String placeId = message.getPlaceId();
    if (placeId == null) {
      return;
    }

    // Tweet.Place.id and Tweet.Place.full_name are both required fields.
    String placeFullName = message.getPlaceFullName();
    Preconditions.checkNotNull(placeFullName, "Tweet.Place without full_name.");

    Locale placeFullNameLocale = LanguageIdentifierHelper.identifyLanguage(placeFullName);
    String normalizedPlaceFullName =
        NormalizerHelper.normalize(placeFullName, placeFullNameLocale, penguinVersion);
    String tokenizedPlaceFullName = StringUtils.join(
        TokenizerHelper.tokenizeQuery(normalizedPlaceFullName, placeFullNameLocale, penguinVersion),
        " ");

    Place place = new Place(placeId, tokenizedPlaceFullName);
    String placeCountryCode = message.getPlaceCountryCode();
    if (placeCountryCode != null) {
      Locale placeCountryCodeLocale = LanguageIdentifierHelper.identifyLanguage(placeCountryCode);
      place.setCountryCode(
          NormalizerHelper.normalize(placeCountryCode, placeCountryCodeLocale, penguinVersion));
    }

    versionedTweetFeatures.setTokenizedPlace(place);
  }

  private void addProfileGeoEnrichment(TwitterMessage message,
                                       VersionedTweetFeatures versionedTweetFeatures,
                                       PenguinVersion penguinVersion) {
    List<PotentialLocationObject> potentialLocations = message.getPotentialLocations();
    if (potentialLocations.isEmpty()) {
      return;
    }

    List<PotentialLocation> thriftPotentialLocations = Lists.newArrayList();
    for (PotentialLocationObject potentialLocation : potentialLocations) {
      thriftPotentialLocations.add(potentialLocation.toThriftPotentialLocation(penguinVersion));
    }
    versionedTweetFeatures.setTokenizedProfileGeoEnrichment(
        new ProfileGeoEnrichment(thriftPotentialLocations));
  }

  /** Returns the encoded features. */
  public static EarlybirdEncodedFeatures createEncodedFeaturesFromTwitterMessage(
      TwitterMessage message,
      PenguinVersion penguinVersion,
      ImmutableSchemaInterface schema,
      int normalizedTokenPercentBucket) {
    FeatureSink sink = new FeatureSink(schema);

    // Static features
    sink.setBooleanValue(EarlybirdFieldConstant.IS_RETWEET_FLAG, message.isRetweet())
        .setBooleanValue(EarlybirdFieldConstant.IS_REPLY_FLAG, message.isReply())
        .setBooleanValue(
            EarlybirdFieldConstant.FROM_VERIFIED_ACCOUNT_FLAG, message.isUserVerified())
        .setBooleanValue(
            EarlybirdFieldConstant.FROM_BLUE_VERIFIED_ACCOUNT_FLAG, message.isUserBlueVerified())
        .setBooleanValue(EarlybirdFieldConstant.IS_SENSITIVE_CONTENT, message.isSensitiveContent());

    TweetTetwittertFeatures tetwittertFeatures = message.getTweetTetwittertFeatures(penguinVersion);
    if (tetwittertFeatures != null) {
      final FeatureConfiguration featureConfigNumHashtags = schema.getFeatureConfigurationByName(
          EarlybirdFieldConstant.NUM_HASHTAGS.getFieldName());
      final FeatureConfiguration featureConfigNumMentions = schema.getFeatureConfigurationByName(
          EarlybirdFieldConstant.NUM_MENTIONS.getFieldName());

      sink.setNumericValue(
              EarlybirdFieldConstant.NUM_HASHTAGS,
              Math.min(tetwittertFeatures.getHashtagsSize(), featureConfigNumHashtags.getMatwitterValue()))
          .setNumericValue(
              EarlybirdFieldConstant.NUM_MENTIONS,
              Math.min(tetwittertFeatures.getMentionsSize(), featureConfigNumMentions.getMatwitterValue()))
          .setBooleanValue(
              EarlybirdFieldConstant.HAS_MULTIPLE_HASHTAGS_OR_TRENDS_FLAG,
              TwitterMessage.hasMultipleHashtagsOrTrends(tetwittertFeatures))
          .setBooleanValue(
              EarlybirdFieldConstant.HAS_TREND_FLAG,
              tetwittertFeatures.getTrendingTermsSize() > 0);
    }

    TweetTetwittertQuality tetwittertQuality = message.getTweetTetwittertQuality(penguinVersion);
    if (tetwittertQuality != null) {
      sink.setNumericValue(EarlybirdFieldConstant.TEtwitterT_SCORE, tetwittertQuality.getTetwittertScore());
      sink.setBooleanValue(
          EarlybirdFieldConstant.IS_OFFENSIVE_FLAG,
          tetwittertQuality.hasBoolQuality(TweetTetwittertQuality.BooleanQualityType.OFFENSIVE)
              || tetwittertQuality.hasBoolQuality(TweetTetwittertQuality.BooleanQualityType.OFFENSIVE_USER)
              // Note: if json message "possibly_sensitive" flag is set, we consider the tweet
              // sensitive and is currently filtered out in safe search mode via a hacky setup:
              // earlybird does not create _filter_sensitive_content field, only
              // _is_offensive field is created, and used in filter:safe operator
              || tetwittertQuality.hasBoolQuality(TweetTetwittertQuality.BooleanQualityType.SENSITIVE));
      if (tetwittertQuality.hasBoolQuality(TweetTetwittertQuality.BooleanQualityType.SENSITIVE)) {
        sink.setBooleanValue(EarlybirdFieldConstant.IS_SENSITIVE_CONTENT, true);
      }
    } else {
      // we don't have tetwittert score, for whatever reason, set to sentinel value so we won't be
      // skipped by scoring function
      sink.setNumericValue(EarlybirdFieldConstant.TEtwitterT_SCORE,
          RelevanceSignalConstants.UNSET_TEtwitterT_SCORE_SENTINEL);
    }

    if (message.isSetLocale()) {
      sink.setNumericValue(EarlybirdFieldConstant.LANGUAGE,
          ThriftLanguageUtil.getThriftLanguageOf(message.getLocale()).getValue());
    }

    // User features
    TweetUserFeatures userFeatures = message.getTweetUserFeatures(penguinVersion);
    if (userFeatures != null) {
      sink.setBooleanValue(EarlybirdFieldConstant.IS_USER_SPAM_FLAG, userFeatures.isSpam())
          .setBooleanValue(EarlybirdFieldConstant.IS_USER_NSFW_FLAG, userFeatures.isNsfw())
          .setBooleanValue(EarlybirdFieldConstant.IS_USER_BOT_FLAG, userFeatures.isBot());
    }
    if (message.getUserReputation() != TwitterMessage.DOUBLE_FIELD_NOT_PRESENT) {
      sink.setNumericValue(EarlybirdFieldConstant.USER_REPUTATION,
          (byte) message.getUserReputation());
    } else {
      sink.setNumericValue(EarlybirdFieldConstant.USER_REPUTATION,
          RelevanceSignalConstants.UNSET_REPUTATION_SENTINEL);
    }

    sink.setBooleanValue(EarlybirdFieldConstant.IS_NULLCAST_FLAG, message.getNullcast());

    // Realtime Ingestion does not write engagement features. Updater does that.
    if (message.getNumFavorites() > 0) {
      sink.setNumericValue(EarlybirdFieldConstant.FAVORITE_COUNT,
          MutableFeatureNormalizers.BYTE_NORMALIZER.normalize(message.getNumFavorites()));
    }
    if (message.getNumRetweets() > 0) {
      sink.setNumericValue(EarlybirdFieldConstant.RETWEET_COUNT,
          MutableFeatureNormalizers.BYTE_NORMALIZER.normalize(message.getNumRetweets()));
    }
    if (message.getNumReplies() > 0) {
      sink.setNumericValue(EarlybirdFieldConstant.REPLY_COUNT,
          MutableFeatureNormalizers.BYTE_NORMALIZER.normalize(message.getNumReplies()));
    }

    sink.setNumericValue(EarlybirdFieldConstant.VISIBLE_TOKEN_RATIO, normalizedTokenPercentBucket);

    EarlybirdEncodedFeatures encodedFeatures =
        (EarlybirdEncodedFeatures) sink.getFeaturesForBaseField(
            EarlybirdFieldConstant.ENCODED_TWEET_FEATURES_FIELD.getFieldName());
    updateLinkEncodedFeatures(encodedFeatures, message);
    return encodedFeatures;
  }

  /**
   * Returns the etwittertended encoded features.
   */
  public static EarlybirdEncodedFeatures createEtwittertendedEncodedFeaturesFromTwitterMessage(
    TwitterMessage message,
    PenguinVersion penguinVersion,
    ImmutableSchemaInterface schema) {
    FeatureSink sink = new FeatureSink(schema);

    TweetTetwittertFeatures tetwittertFeatures = message.getTweetTetwittertFeatures(penguinVersion);

    if (tetwittertFeatures != null) {
      setEtwittertendedEncodedFeatureIntValue(sink, schema,
          EarlybirdFieldConstant.NUM_HASHTAGS_V2, tetwittertFeatures.getHashtagsSize());
      setEtwittertendedEncodedFeatureIntValue(sink, schema,
          EarlybirdFieldConstant.NUM_MENTIONS_V2, tetwittertFeatures.getMentionsSize());
      setEtwittertendedEncodedFeatureIntValue(sink, schema,
          EarlybirdFieldConstant.NUM_STOCKS, tetwittertFeatures.getStocksSize());
    }

    Optional<Long> referenceAuthorId = message.getReferenceAuthorId();
    if (referenceAuthorId.isPresent()) {
      setEncodedReferenceAuthorId(sink, referenceAuthorId.get());
    }

    return (EarlybirdEncodedFeatures) sink.getFeaturesForBaseField(
        EarlybirdFieldConstant.EtwitterTENDED_ENCODED_TWEET_FEATURES_FIELD.getFieldName());
  }

  /**
   * Updates all URL-related features, based on the values stored in the given message.
   *
   * @param encodedFeatures The features to be updated.
   * @param message The message.
   */
  public static void updateLinkEncodedFeatures(
      EarlybirdEncodedFeatures encodedFeatures, TwitterMessage message) {
    if (message.getLinkLocale() != null) {
      encodedFeatures.setFeatureValue(
          EarlybirdFieldConstant.LINK_LANGUAGE,
          ThriftLanguageUtil.getThriftLanguageOf(message.getLinkLocale()).getValue());
    }

    if (message.hasCard()) {
      encodedFeatures.setFlag(EarlybirdFieldConstant.HAS_CARD_FLAG);
    }

    // Set HAS_IMAGE HAS_NEWS HAS_VIDEO etc. flags for etwitterpanded urls.
    if (message.getEtwitterpandedUrlMapSize() > 0) {
      encodedFeatures.setFlag(EarlybirdFieldConstant.HAS_LINK_FLAG);

      for (ThriftEtwitterpandedUrl url : message.getEtwitterpandedUrlMap().values()) {
        if (url.isSetMediaType()) {
          switch (url.getMediaType()) {
            case NATIVE_IMAGE:
              encodedFeatures.setFlag(EarlybirdFieldConstant.HAS_IMAGE_URL_FLAG);
              encodedFeatures.setFlag(EarlybirdFieldConstant.HAS_NATIVE_IMAGE_FLAG);
              break;
            case IMAGE:
              encodedFeatures.setFlag(EarlybirdFieldConstant.HAS_IMAGE_URL_FLAG);
              break;
            case VIDEO:
              encodedFeatures.setFlag(EarlybirdFieldConstant.HAS_VIDEO_URL_FLAG);
              break;
            case NEWS:
              encodedFeatures.setFlag(EarlybirdFieldConstant.HAS_NEWS_URL_FLAG);
              break;
            case UNKNOWN:
              break;
            default:
              throw new IllegalStateEtwitterception("Unetwitterpected enum value: " + url.getMediaType());
          }
        }
      }
    }

    Set<String> canonicalLastHopUrlsStrings = message.getCanonicalLastHopUrls();
    Set<String> etwitterpandedUrlsStrings = message.getEtwitterpandedUrls()
        .stream()
        .map(ThriftEtwitterpandedUrl::getEtwitterpandedUrl)
        .collect(Collectors.toSet());
    Set<String> etwitterpandedAndLastHopUrlsStrings = new HashSet<>();
    etwitterpandedAndLastHopUrlsStrings.addAll(etwitterpandedUrlsStrings);
    etwitterpandedAndLastHopUrlsStrings.addAll(canonicalLastHopUrlsStrings);
    // Check both etwitterpanded and last hop url for consumer videos as consumer video urls are
    // sometimes redirected to the url of the tweets containing the videos (SEARCH-42612).
    if (NativeVideoClassificationUtils.hasConsumerVideo(etwitterpandedAndLastHopUrlsStrings)) {
      encodedFeatures.setFlag(EarlybirdFieldConstant.HAS_CONSUMER_VIDEO_FLAG);
    }
    if (NativeVideoClassificationUtils.hasProVideo(canonicalLastHopUrlsStrings)) {
      encodedFeatures.setFlag(EarlybirdFieldConstant.HAS_PRO_VIDEO_FLAG);
    }
    if (NativeVideoClassificationUtils.hasVine(canonicalLastHopUrlsStrings)) {
      encodedFeatures.setFlag(EarlybirdFieldConstant.HAS_VINE_FLAG);
    }
    if (NativeVideoClassificationUtils.hasPeriscope(canonicalLastHopUrlsStrings)) {
      encodedFeatures.setFlag(EarlybirdFieldConstant.HAS_PERISCOPE_FLAG);
    }
    if (LinkVisibilityUtils.hasVisibleLink(message.getEtwitterpandedUrls())) {
      encodedFeatures.setFlag(EarlybirdFieldConstant.HAS_VISIBLE_LINK_FLAG);
    }
  }

  private static void setEtwittertendedEncodedFeatureIntValue(
      FeatureSink sink,
      ImmutableSchemaInterface schema,
      EarlybirdFieldConstant field,
      int value) {
    boolean fieldInSchema = schema.hasField(field.getFieldName());
    if (fieldInSchema) {
      FeatureConfiguration featureConfig =
          schema.getFeatureConfigurationByName(field.getFieldName());
      sink.setNumericValue(field, Math.min(value, featureConfig.getMatwitterValue()));
    }
  }

  private static void setEncodedReferenceAuthorId(FeatureSink sink, long referenceAuthorId) {
    LongIntConverter.IntegerRepresentation ints =
        LongIntConverter.convertOneLongToTwoInt(referenceAuthorId);
    sink.setNumericValue(
        EarlybirdFieldConstant.REFERENCE_AUTHOR_ID_LEAST_SIGNIFICANT_INT, ints.leastSignificantInt);
    sink.setNumericValue(
        EarlybirdFieldConstant.REFERENCE_AUTHOR_ID_MOST_SIGNIFICANT_INT, ints.mostSignificantInt);
  }
}
