package com.twitter.search.common.schema.earlybird;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.TokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.collections.Pair;
import com.twitter.common.text.util.TokenStreamSerializer;
import com.twitter.cuad.ner.plain.thriftjava.NamedEntity;
import com.twitter.cuad.ner.plain.thriftjava.NamedEntityContext;
import com.twitter.cuad.ner.plain.thriftjava.NamedEntityInputSourceType;
import com.twitter.cuad.ner.thriftjava.WholeEntityType;
import com.twitter.search.common.constants.SearchCardType;
import com.twitter.search.common.indexing.thriftjava.ThriftExpandedUrl;
import com.twitter.search.common.indexing.thriftjava.ThriftGeoLocationSource;
import com.twitter.search.common.indexing.thriftjava.TwitterPhotoUrl;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.schema.ThriftDocumentBuilder;
import com.twitter.search.common.schema.base.FieldNameToIdMapping;
import com.twitter.search.common.schema.base.Schema;
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant;
import com.twitter.search.common.util.analysis.CharTermAttributeSerializer;
import com.twitter.search.common.util.analysis.IntTermAttributeSerializer;
import com.twitter.search.common.util.analysis.TermPayloadAttributeSerializer;
import com.twitter.search.common.util.analysis.TwitterPhotoTokenStream;
import com.twitter.search.common.util.spatial.GeoUtil;
import com.twitter.search.common.util.text.TokenizerHelper;
import com.twitter.search.common.util.text.TweetTokenStreamSerializer;
import com.twitter.search.common.util.text.regex.Regex;
import com.twitter.search.common.util.url.LinkVisibilityUtils;
import com.twitter.search.common.util.url.URLUtils;

import geo.google.datamodel.GeoAddressAccuracy;
import com.twitter.search.common.schema.thriftjava.ThriftDocument;

/**
 * Builder class for building a {@link ThriftDocument}.
 */
public final class EarlybirdThriftDocumentBuilder extends ThriftDocumentBuilder {
  private static final Logger LOG = LoggerFactory.getLogger(EarlybirdThriftDocumentBuilder.class);

  private static final SearchCounter SERIALIZE_FAILURE_COUNT_NONPENGUIN_DEPENDENT =
      SearchCounter.export("tokenstream_serialization_failure_non_penguin_dependent");

  private static final String HASHTAG_SYMBOL = "#";
  private static final String CASHTAG_SYMBOL = "$";
  private static final String MENTION_SYMBOL = "@";

  private static final SearchCounter BCP47_LANGUAGE_TAG_COUNTER =
      SearchCounter.export("bcp47_language_tag");

  /**
   * Used to check if a card is video card.
   *
   * @see #withSearchCard
   */
  private static final String AMPLIFY_CARD_NAME = "amplify";
  private static final String PLAYER_CARD_NAME = "player";

  // Extra term indexed for native retweets, to ensure that the "-rt" query excludes them.
  public static final String RETWEET_TERM = "rt";
  public static final String QUESTION_MARK = "?";

  private static final Set<NamedEntityInputSourceType> NAMED_ENTITY_URL_SOURCE_TYPES =
      ImmutableSet.of(
          NamedEntityInputSourceType.URL_TITLE, NamedEntityInputSourceType.URL_DESCRIPTION);

  private final TokenStreamSerializer intTermAttributeSerializer =
      new TokenStreamSerializer(ImmutableList.of(
          new IntTermAttributeSerializer()));
  private final TokenStreamSerializer photoUrlSerializer =
      new TokenStreamSerializer(ImmutableList
          .<TokenStreamSerializer.AttributeSerializer>of(
              new CharTermAttributeSerializer(), new TermPayloadAttributeSerializer()));
  private final Schema schema;

  private boolean isSetLatLonCSF = false;
  private boolean addLatLonCSF = true;
  private boolean addEncodedTweetFeatures = true;

  @Nonnull
  private final EarlybirdEncodedFeatures encodedTweetFeatures;
  @Nullable
  private final EarlybirdEncodedFeatures extendedEncodedTweetFeatures;

  /**
   * Default constructor
   */
  public EarlybirdThriftDocumentBuilder(
      @Nonnull EarlybirdEncodedFeatures encodedTweetFeatures,
      @Nullable EarlybirdEncodedFeatures extendedEncodedTweetFeatures,
      FieldNameToIdMapping idMapping,
      Schema schema) {
    super(idMapping);
    this.schema = schema;
    this.encodedTweetFeatures = Preconditions.checkNotNull(encodedTweetFeatures);

    this.extendedEncodedTweetFeatures = extendedEncodedTweetFeatures;
  }

  /**
   * Get internal {@link EarlybirdEncodedFeatures}
   */
  public EarlybirdEncodedFeatures getEncodedTweetFeatures() {
    return encodedTweetFeatures;
  }

  /**
   * Add skip list entry for the given field.
   * This adds a term __has_fieldName in the INTERNAL field.
   */
  public EarlybirdThriftDocumentBuilder addFacetSkipList(String fieldName) {
    withStringField(EarlybirdFieldConstant.INTERNAL_FIELD.getFieldName(),
        EarlybirdFieldConstant.getFacetSkipFieldName(fieldName));
    return this;
  }

  /**
   * Add a filter term in the INTERNAL field.
   */
  public EarlybirdThriftDocumentBuilder addFilterInternalFieldTerm(String filterName) {
    withStringField(EarlybirdFieldConstant.INTERNAL_FIELD.getFieldName(),
        EarlybirdThriftDocumentUtil.formatFilter(filterName));
    return this;
  }

  /**
   * Add id field and id csf field.
   */
  public EarlybirdThriftDocumentBuilder withID(long id) {
    withLongField(EarlybirdFieldConstant.ID_FIELD.getFieldName(), id);
    withLongField(EarlybirdFieldConstant.ID_CSF_FIELD.getFieldName(), id);
    return this;
  }

  /**
   * Add created at field and created at csf field.
   */
  public EarlybirdThriftDocumentBuilder withCreatedAt(int createdAt) {
    withIntField(EarlybirdFieldConstant.CREATED_AT_FIELD.getFieldName(), createdAt);
    withIntField(EarlybirdFieldConstant.CREATED_AT_CSF_FIELD.getFieldName(), createdAt);
    return this;
  }

  /**
   * Add tweet text field.
   */
  public EarlybirdThriftDocumentBuilder withTweetText(
      String text, byte[] textTokenStream) throws IOException {
    withTokenStreamField(EarlybirdFieldConstants.EarlybirdFieldConstant.TEXT_FIELD.getFieldName(),
        text, textTokenStream);
    return this;
  }

  public EarlybirdThriftDocumentBuilder withTweetText(String text) throws IOException {
    withTweetText(text, null);
    return this;
  }

  /**
   * Add a list of cashTags. Like $TWTR.
   */
  public EarlybirdThriftDocumentBuilder withStocksFields(List<String> cashTags) {
    if (isNotEmpty(cashTags)) {
      addFacetSkipList(EarlybirdFieldConstant.STOCKS_FIELD.getFieldName());
      for (String cashTag : cashTags) {
        withStringField(
            EarlybirdFieldConstant.STOCKS_FIELD.getFieldName(), CASHTAG_SYMBOL + cashTag);
      }
    }
    return this;
  }

  /**
   * Add a list of hashtags.
   */
  public EarlybirdThriftDocumentBuilder withHashtagsField(List<String> hashtags) {
    if (isNotEmpty(hashtags)) {
      int numHashtags = Math.min(
          hashtags.size(),
          schema.getFeatureConfigurationById(
              EarlybirdFieldConstant.NUM_HASHTAGS.getFieldId()).getMaxValue());
      encodedTweetFeatures.setFeatureValue(EarlybirdFieldConstant.NUM_HASHTAGS, numHashtags);
      addFacetSkipList(EarlybirdFieldConstant.HASHTAGS_FIELD.getFieldName());
      for (String hashtag : hashtags) {
        withStringField(
            EarlybirdFieldConstant.HASHTAGS_FIELD.getFieldName(), HASHTAG_SYMBOL + hashtag);
      }
    }
    return this;
  }

  /**
   * Added a list of mentions.
   */
  public EarlybirdThriftDocumentBuilder withMentionsField(List<String> mentions) {
    if (isNotEmpty(mentions)) {
      int numMentions = Math.min(
          mentions.size(),
          schema.getFeatureConfigurationById(
              EarlybirdFieldConstant.NUM_HASHTAGS.getFieldId()).getMaxValue());
      encodedTweetFeatures.setFeatureValue(EarlybirdFieldConstant.NUM_MENTIONS, numMentions);
      addFacetSkipList(EarlybirdFieldConstant.MENTIONS_FIELD.getFieldName());
      for (String mention : mentions) {
        withStringField(
            EarlybirdFieldConstant.MENTIONS_FIELD.getFieldName(), MENTION_SYMBOL + mention);
      }
    }
    return this;
  }

  /**
   * Add a list of Twitter Photo URLs (twimg URLs). These are different from regular URLs, because
   * we use the TwitterPhotoTokenStream to index them, and we also include the status ID as payload.
   */
  public EarlybirdThriftDocumentBuilder withTwimgURLs(
      List<TwitterPhotoUrl> urls) throws IOException {
    if (isNotEmpty(urls)) {
      for (TwitterPhotoUrl photoUrl : urls) {
        TokenStream ts = new TwitterPhotoTokenStream(photoUrl.getPhotoStatusId(),
            photoUrl.getMediaUrl());
        byte[] serializedTs = photoUrlSerializer.serialize(ts);
        withTokenStreamField(EarlybirdFieldConstant.TWIMG_LINKS_FIELD.getFieldName(),
            Long.toString(photoUrl.getPhotoStatusId()), serializedTs);
        addFacetSkipList(EarlybirdFieldConstant.TWIMG_LINKS_FIELD.getFieldName());
      }
      encodedTweetFeatures.setFlag(EarlybirdFieldConstant.HAS_IMAGE_URL_FLAG);
      encodedTweetFeatures.setFlag(EarlybirdFieldConstant.HAS_NATIVE_IMAGE_FLAG);
    }
    return this;
  }

  /**
   * Add a list of URLs. This also add facet skip list terms for news / images / videos if needed.
   */
  public EarlybirdThriftDocumentBuilder withURLs(List<ThriftExpandedUrl> urls) {
    if (isNotEmpty(urls)) {
      Set<String> dedupedLinks = Sets.newHashSet();

      for (ThriftExpandedUrl expandedUrl : urls) {
        if (expandedUrl.isSetOriginalUrl()) {
          String normalizedOriginalUrl = URLUtils.normalizePath(expandedUrl.getOriginalUrl());
          dedupedLinks.add(normalizedOriginalUrl);
        }
        if (expandedUrl.isSetExpandedUrl()) {
          dedupedLinks.add(URLUtils.normalizePath(expandedUrl.getExpandedUrl()));
        }

        if (expandedUrl.isSetCanonicalLastHopUrl()) {
          String url = URLUtils.normalizePath(expandedUrl.getCanonicalLastHopUrl());
          dedupedLinks.add(url);

          String facetUrl = URLUtils.normalizeFacetURL(url);

          if (expandedUrl.isSetMediaType()) {
            switch (expandedUrl.getMediaType()) {
              case NEWS:
                withStringField(EarlybirdFieldConstant.NEWS_LINKS_FIELD.getFieldName(), url);
                addFacetSkipList(EarlybirdFieldConstant.NEWS_LINKS_FIELD.getFieldName());
                encodedTweetFeatures.setFlag(EarlybirdFieldConstant.HAS_NEWS_URL_FLAG);
                break;
              case VIDEO:
                withStringField(EarlybirdFieldConstant.VIDEO_LINKS_FIELD.getFieldName(), facetUrl);
                addFacetSkipList(EarlybirdFieldConstant.VIDEO_LINKS_FIELD.getFieldName());
                encodedTweetFeatures.setFlag(EarlybirdFieldConstant.HAS_VIDEO_URL_FLAG);
                break;
              case IMAGE:
                withStringField(EarlybirdFieldConstant.IMAGE_LINKS_FIELD.getFieldName(), facetUrl);
                addFacetSkipList(EarlybirdFieldConstant.IMAGE_LINKS_FIELD.getFieldName());
                encodedTweetFeatures.setFlag(EarlybirdFieldConstant.HAS_IMAGE_URL_FLAG);
                break;
              case NATIVE_IMAGE:
                // Nothing done here. Native images are handled separately.
                // They are in PhotoUrls instead of expandedUrls.
                break;
              case UNKNOWN:
                break;
              default:
                throw new RuntimeException("Unknown Media Type: " + expandedUrl.getMediaType());
            }
          }

          if (expandedUrl.isSetLinkCategory()) {
            withIntField(EarlybirdFieldConstant.LINK_CATEGORY_FIELD.getFieldName(),
                expandedUrl.getLinkCategory().getValue());
          }
        }
      }

      if (!dedupedLinks.isEmpty()) {
        encodedTweetFeatures.setFlag(EarlybirdFieldConstant.HAS_LINK_FLAG);

        addFacetSkipList(EarlybirdFieldConstant.LINKS_FIELD.getFieldName());

        for (String linkUrl : dedupedLinks) {
          withStringField(EarlybirdFieldConstant.LINKS_FIELD.getFieldName(), linkUrl);
        }
      }

      encodedTweetFeatures.setFlagValue(
          EarlybirdFieldConstant.HAS_VISIBLE_LINK_FLAG,
          LinkVisibilityUtils.hasVisibleLink(urls));
    }

    return this;
  }

  /**
   * Add a list of places. The place are U64 encoded place IDs.
   */
  public EarlybirdThriftDocumentBuilder withPlacesField(List<String> places) {
    if (isNotEmpty(places)) {
      for (String place : places) {
        withStringField(EarlybirdFieldConstant.PLACE_FIELD.getFieldName(), place);
      }
    }
    return this;
  }

  /**
   * Add tweet text signature field.
   */
  public EarlybirdThriftDocumentBuilder withTweetSignature(int signature) {
    encodedTweetFeatures.setFeatureValue(EarlybirdFieldConstant.TWEET_SIGNATURE, signature);
    return this;
  }

  /**
   * Add geo hash field and internal filter field.
   */
  public EarlybirdThriftDocumentBuilder withGeoHash(double lat, double lon, int accuracy) {
    if (GeoUtil.validateGeoCoordinates(lat, lon)) {
      withGeoField(
          EarlybirdFieldConstant.GEO_HASH_FIELD.getFieldName(),
          lat, lon, accuracy);
      withLatLonCSF(lat, lon);
    }
    return this;
  }

  public EarlybirdThriftDocumentBuilder withGeoHash(double lat, double lon) {
    withGeoHash(lat, lon, GeoAddressAccuracy.UNKNOWN_LOCATION.getCode());
    return this;
  }

  /**
   * Add geo location source to the internal field with ThriftGeoLocationSource object.
   */
  public EarlybirdThriftDocumentBuilder withGeoLocationSource(
      ThriftGeoLocationSource geoLocationSource) {
    if (geoLocationSource != null) {
      withGeoLocationSource(EarlybirdFieldConstants.formatGeoType(geoLocationSource));
    }
    return this;
  }

  /**
   * Add geo location source to the internal field.
   */
  public EarlybirdThriftDocumentBuilder withGeoLocationSource(String geoLocationSource) {
    withStringField(EarlybirdFieldConstant.INTERNAL_FIELD.getFieldName(), geoLocationSource);
    return this;
  }

  /**
   * Add encoded lat and lon to LatLonCSF field.
   */
  public EarlybirdThriftDocumentBuilder withLatLonCSF(double lat, double lon) {
    isSetLatLonCSF = true;
    long encodedLatLon = GeoUtil.encodeLatLonIntoInt64((float) lat, (float) lon);
    withLongField(EarlybirdFieldConstant.LAT_LON_CSF_FIELD.getFieldName(), encodedLatLon);
    return this;
  }

  /**
   * Add from verified account flag to internal field.
   */
  public EarlybirdThriftDocumentBuilder withFromVerifiedAccountFlag() {
    encodedTweetFeatures.setFlag(EarlybirdFieldConstant.FROM_VERIFIED_ACCOUNT_FLAG);
    addFilterInternalFieldTerm(EarlybirdFieldConstant.VERIFIED_FILTER_TERM);
    return this;
  }

  /**
   * Add from blue-verified account flag to internal field.
   */
  public EarlybirdThriftDocumentBuilder withFromBlueVerifiedAccountFlag() {
    encodedTweetFeatures.setFlag(EarlybirdFieldConstant.FROM_BLUE_VERIFIED_ACCOUNT_FLAG);
    addFilterInternalFieldTerm(EarlybirdFieldConstant.BLUE_VERIFIED_FILTER_TERM);
    return this;
  }

  /**
   * Add offensive flag to internal field.
   */
  public EarlybirdThriftDocumentBuilder withOffensiveFlag() {
    encodedTweetFeatures.setFlag(EarlybirdFieldConstant.IS_OFFENSIVE_FLAG);
    withStringField(
        EarlybirdFieldConstant.INTERNAL_FIELD.getFieldName(),
        EarlybirdFieldConstant.IS_OFFENSIVE);
    return this;
  }

  /**
   * Add user reputation value to encoded feature.
   */
  public EarlybirdThriftDocumentBuilder withUserReputation(byte score) {
    encodedTweetFeatures.setFeatureValue(EarlybirdFieldConstant.USER_REPUTATION, score);
    return this;
  }

  /**
   * This method creates the fields related to document language.
   * For most languages, their isoLanguageCode and bcp47LanguageTag are the same.
   * For some languages with variants, these two fields are different.
   * E.g. for simplified Chinese, their isoLanguageCode is zh, but their bcp47LanguageTag is zh-cn.
   * <p>
   * This method adds fields for both the isoLanguageCode and bcp47LanguageTag.
   */
  public EarlybirdThriftDocumentBuilder withLanguageCodes(
      String isoLanguageCode, String bcp47LanguageTag) {
    if (isoLanguageCode != null) {
      withISOLanguage(isoLanguageCode);
    }
    if (bcp47LanguageTag != null && !bcp47LanguageTag.equals(isoLanguageCode)) {
      BCP47_LANGUAGE_TAG_COUNTER.increment();
      withISOLanguage(bcp47LanguageTag);
    }
    return this;
  }

  /**
   * Adds a String field into the ISO_LANGUAGE_FIELD.
   */
  public EarlybirdThriftDocumentBuilder withISOLanguage(String languageString) {
    withStringField(
        EarlybirdFieldConstant.ISO_LANGUAGE_FIELD.getFieldName(), languageString.toLowerCase());
    return this;
  }

  /**
   * Add from user ID fields.
   */
  public EarlybirdThriftDocumentBuilder withFromUserID(long fromUserId) {
    withLongField(EarlybirdFieldConstant.FROM_USER_ID_FIELD.getFieldName(), fromUserId);
    withLongField(EarlybirdFieldConstant.FROM_USER_ID_CSF.getFieldName(), fromUserId);
    return this;
  }

  /**
   * Add from user information fields.
   */
  public EarlybirdThriftDocumentBuilder withFromUser(
      long fromUserId, String fromUser) {
    withFromUser(fromUserId, fromUser, null);
    return this;
  }

  /**
   * Add from user information fields.
   */
  public EarlybirdThriftDocumentBuilder withFromUser(String fromUser) {
    withFromUser(fromUser, null);
    return this;
  }

  /**
   * Add from user information fields.
   */
  public EarlybirdThriftDocumentBuilder withFromUser(
      String fromUser, String tokenizedFromUser) {
    withStringField(EarlybirdFieldConstant.FROM_USER_FIELD.getFieldName(), fromUser);
    withStringField(EarlybirdFieldConstant.TOKENIZED_FROM_USER_FIELD.getFieldName(),
        isNotBlank(tokenizedFromUser) ? tokenizedFromUser : fromUser);
    return this;
  }

  /**
   * Add from user information fields.
   */
  public EarlybirdThriftDocumentBuilder withFromUser(
      long fromUserId, String fromUser, String tokenizedFromUser) {
    withFromUserID(fromUserId);
    withFromUser(fromUser, tokenizedFromUser);
    return this;
  }

  /**
   * Add to user field.
   */
  public EarlybirdThriftDocumentBuilder withToUser(
      String toUser) {
    withStringField(EarlybirdFieldConstant.TO_USER_FIELD.getFieldName(), toUser);
    return this;
  }

  /**
   * Add escherbird annotation fields.
   */
  public EarlybirdThriftDocumentBuilder withAnnotationEntities(List<String> entities) {
    if (isNotEmpty(entities)) {
      for (String entity : entities) {
        withStringField(EarlybirdFieldConstant.ENTITY_ID_FIELD.getFieldName(), entity);
      }
    }
    return this;
  }

  /**
   * Add replies to internal field and set is reply flag.
   */
  public EarlybirdThriftDocumentBuilder withReplyFlag() {
    encodedTweetFeatures.setFlag(EarlybirdFieldConstant.IS_REPLY_FLAG);
    addFilterInternalFieldTerm(EarlybirdFieldConstant.REPLIES_FILTER_TERM);
    return this;
  }

  public EarlybirdThriftDocumentBuilder withCameraComposerSourceFlag() {
    encodedTweetFeatures.setFlag(EarlybirdFieldConstant.COMPOSER_SOURCE_IS_CAMERA_FLAG);
    return this;
  }

    /**
     * Add in reply to user id.
     * <p>
     * Notice {@link #withReplyFlag} is not automatically called since retweet a tweet that is
     * a reply to some other tweet is not considered a reply.
     * The caller should call {@link #withReplyFlag} separately if this tweet is really a reply tweet.
     */
  public EarlybirdThriftDocumentBuilder withInReplyToUserID(long inReplyToUserID) {
    withLongField(EarlybirdFieldConstant.IN_REPLY_TO_USER_ID_FIELD.getFieldName(), inReplyToUserID);
    return this;
  }

  /**
   * Add reference tweet author id.
   */
  public EarlybirdThriftDocumentBuilder withReferenceAuthorID(long referenceAuthorID) {
    withLongField(EarlybirdFieldConstant.REFERENCE_AUTHOR_ID_CSF.getFieldName(), referenceAuthorID);
    return this;
  }

  /**
   * Add all native retweet related fields/label
   */
  @VisibleForTesting
  public EarlybirdThriftDocumentBuilder withNativeRetweet(final long retweetUserID,
                                                          final long sharedStatusID) {
    withLongField(EarlybirdFieldConstant.SHARED_STATUS_ID_CSF.getFieldName(), sharedStatusID);

    withLongField(EarlybirdFieldConstant.RETWEET_SOURCE_TWEET_ID_FIELD.getFieldName(),
                  sharedStatusID);
    withLongField(EarlybirdFieldConstant.RETWEET_SOURCE_USER_ID_FIELD.getFieldName(),
                  retweetUserID);
    withLongField(EarlybirdFieldConstant.REFERENCE_AUTHOR_ID_CSF.getFieldName(), retweetUserID);

    encodedTweetFeatures.setFlag(EarlybirdFieldConstant.IS_RETWEET_FLAG);

    // Add native retweet label to the internal field.
    addFilterInternalFieldTerm(EarlybirdFieldConstant.NATIVE_RETWEETS_FILTER_TERM);
    withStringField(EarlybirdFieldConstant.TEXT_FIELD.getFieldName(), RETWEET_TERM);
    return this;
  }

  /**
   * Add quoted tweet id and user id.
   */
  @VisibleForTesting
  public EarlybirdThriftDocumentBuilder withQuote(
      final long quotedStatusId, final long quotedUserId) {
    withLongField(EarlybirdFieldConstant.QUOTED_TWEET_ID_FIELD.getFieldName(), quotedStatusId);
    withLongField(EarlybirdFieldConstant.QUOTED_USER_ID_FIELD.getFieldName(), quotedUserId);

    withLongField(EarlybirdFieldConstant.QUOTED_TWEET_ID_CSF.getFieldName(), quotedStatusId);
    withLongField(EarlybirdFieldConstant.QUOTED_USER_ID_CSF.getFieldName(), quotedUserId);

    encodedTweetFeatures.setFlag(EarlybirdFieldConstant.HAS_QUOTE_FLAG);

    // Add quote label to the internal field.
    addFilterInternalFieldTerm(EarlybirdFieldConstant.QUOTE_FILTER_TERM);
    return this;
  }

  /**
   * Add resolved links text field.
   */
  public EarlybirdThriftDocumentBuilder withResolvedLinksText(String linksText) {
    withStringField(EarlybirdFieldConstant.RESOLVED_LINKS_TEXT_FIELD.getFieldName(), linksText);
    return this;
  }

  /**
   * Add source field.
   */
  public EarlybirdThriftDocumentBuilder withSource(String source) {
    withStringField(EarlybirdFieldConstant.SOURCE_FIELD.getFieldName(), source);
    return this;
  }

  /**
   * Add normalized source field.
   */
  public EarlybirdThriftDocumentBuilder withNormalizedSource(String normalizedSource) {
    withStringField(
        EarlybirdFieldConstant.NORMALIZED_SOURCE_FIELD.getFieldName(), normalizedSource);
    return this;
  }

  /**
   * Add positive smiley to internal field.
   */
  public EarlybirdThriftDocumentBuilder withPositiveSmiley() {
    withStringField(
        EarlybirdFieldConstant.INTERNAL_FIELD.getFieldName(),
        EarlybirdFieldConstant.HAS_POSITIVE_SMILEY);
    return this;
  }

  /**
   * Add negative smiley to internal field.
   */
  public EarlybirdThriftDocumentBuilder withNegativeSmiley() {
    withStringField(
        EarlybirdFieldConstant.INTERNAL_FIELD.getFieldName(),
        EarlybirdFieldConstant.HAS_NEGATIVE_SMILEY);
    return this;
  }

  /**
   * Add question mark label to a text field.
   */
  public EarlybirdThriftDocumentBuilder withQuestionMark() {
    withStringField(EarlybirdFieldConstant.TEXT_FIELD.getFieldName(), QUESTION_MARK);
    return this;
  }

  /**
   * Add card related fields.
   */
  public EarlybirdThriftDocumentBuilder withSearchCard(
      String name,
      String domain,
      String title, byte[] serializedTitleStream,
      String description, byte[] serializedDescriptionStream,
      String lang) {
    if (isNotBlank(title)) {
      withTokenStreamField(
          EarlybirdFieldConstants.EarlybirdFieldConstant.CARD_TITLE_FIELD.getFieldName(),
          title, serializedTitleStream);
    }

    if (isNotBlank(description)) {
      withTokenStreamField(
          EarlybirdFieldConstants.EarlybirdFieldConstant.CARD_DESCRIPTION_FIELD.getFieldName(),
          description, serializedDescriptionStream);
    }

    if (isNotBlank(lang)) {
      withStringField(EarlybirdFieldConstant.CARD_LANG.getFieldName(), lang);
    }

    if (isNotBlank(domain)) {
      withStringField(
          EarlybirdFieldConstants.EarlybirdFieldConstant.CARD_DOMAIN_FIELD.getFieldName(), domain);
    }

    if (isNotBlank(name)) {
      withStringField(
          EarlybirdFieldConstants.EarlybirdFieldConstant.CARD_NAME_FIELD.getFieldName(), name);
      withIntField(
          EarlybirdFieldConstants.EarlybirdFieldConstant.CARD_TYPE_CSF_FIELD.getFieldName(),
          SearchCardType.cardTypeFromStringName(name).getByteValue());
    }

    if (AMPLIFY_CARD_NAME.equalsIgnoreCase(name)
        || PLAYER_CARD_NAME.equalsIgnoreCase(name)) {
      // Add into "internal" field so that this tweet is returned by filter:videos.
      addFacetSkipList(
          EarlybirdFieldConstants.EarlybirdFieldConstant.VIDEO_LINKS_FIELD.getFieldName());
    }

    return this;
  }

  public EarlybirdThriftDocumentBuilder withNormalizedMinEngagementField(
      String fieldName, int normalizedNumEngagements) throws IOException {
    EarlybirdThriftDocumentUtil.addNormalizedMinEngagementField(doc, fieldName,
        normalizedNumEngagements);
    return this;
  }

  /**
   * Add named entity with given canonical name and type to document.
   */
  public EarlybirdThriftDocumentBuilder withNamedEntity(NamedEntity namedEntity) {
    if (namedEntity.getContexts() == null) {
      // In this unlikely case, we don't have any context for named entity type or source,
      // so we can't properly index it in any of our fields. We'll just skip it in this case.
      return this;
    }

    // Keep track of the fields we've applied in the builder already, to ensure we only index
    // each term (field/value pair) once
    Set<Pair<EarlybirdFieldConstant, String>> fieldsApplied = new HashSet<>();
    for (NamedEntityContext context : namedEntity.getContexts()) {
      if (context.isSetInput_source()
          && NAMED_ENTITY_URL_SOURCE_TYPES.contains(context.getInput_source().getSource_type())) {
        // If the source is one of the URL* types, add the named entity to the "from_url" fields,
        // ensuring we add it only once
        addNamedEntityFields(
            fieldsApplied,
            EarlybirdFieldConstant.NAMED_ENTITY_FROM_URL_FIELD,
            EarlybirdFieldConstant.NAMED_ENTITY_WITH_TYPE_FROM_URL_FIELD,
            namedEntity.getCanonical_name(),
            context);
      } else {
        addNamedEntityFields(
            fieldsApplied,
            EarlybirdFieldConstant.NAMED_ENTITY_FROM_TEXT_FIELD,
            EarlybirdFieldConstant.NAMED_ENTITY_WITH_TYPE_FROM_TEXT_FIELD,
            namedEntity.getCanonical_name(),
            context);
      }
    }

    return this;
  }

  /**
   * Add space id fields.
   */
  public EarlybirdThriftDocumentBuilder withSpaceIdFields(Set<String> spaceIds) {
    if (!spaceIds.isEmpty()) {
      addFacetSkipList(EarlybirdFieldConstant.SPACE_ID_FIELD.getFieldName());
      for (String spaceId : spaceIds) {
        withStringField(EarlybirdFieldConstant.SPACE_ID_FIELD.getFieldName(), spaceId);
      }
    }
    return this;
  }

  /**
   * Add directed at user.
   */
  @VisibleForTesting
  public EarlybirdThriftDocumentBuilder withDirectedAtUser(final long directedAtUserId) {
    withLongField(EarlybirdFieldConstant.DIRECTED_AT_USER_ID_FIELD.getFieldName(),
        directedAtUserId);

    withLongField(EarlybirdFieldConstant.DIRECTED_AT_USER_ID_CSF.getFieldName(), directedAtUserId);

    return this;
  }

  /**
   * Add a white space tokenized screen name field.
   *
   * Example:
   *  screenName - "super_hero"
   *  tokenized version - "super hero"
   */
  public EarlybirdThriftDocumentBuilder withWhiteSpaceTokenizedScreenNameField(
      String fieldName,
      String normalizedScreenName) {
    String whiteSpaceTokenizableScreenName = StringUtils.join(
        normalizedScreenName.split(Regex.HASHTAG_USERNAME_PUNCTUATION_REGEX), " ");
    withStringField(fieldName, whiteSpaceTokenizableScreenName);
    return this;
  }

  /**
   * Add a camel case tokenized screen name field.
   */
  public EarlybirdThriftDocumentBuilder withCamelCaseTokenizedScreenNameField(
      String fieldName,
      String screenName,
      String normalizedScreenName,
      TokenStream screenNameTokenStream) {

    // this normalized text is consistent to how the tokenized stream is created from
    // TokenizerHelper.getNormalizedCamelcaseTokenStream - ie. just lowercasing.
    String camelCaseTokenizedScreenNameText =
        TokenizerHelper.getNormalizedCamelcaseTokenStreamText(screenName);
    try {
      // Reset the token stream in case it has been read before.
      screenNameTokenStream.reset();
      byte[] camelCaseTokenizedScreenName =
          TweetTokenStreamSerializer.getTweetTokenStreamSerializer()
              .serialize(screenNameTokenStream);

      withTokenStreamField(
          fieldName,
          camelCaseTokenizedScreenNameText.isEmpty()
              ? normalizedScreenName : camelCaseTokenizedScreenNameText,
          camelCaseTokenizedScreenName);
    } catch (IOException e) {
      LOG.error("TwitterTokenStream serialization error! Could not serialize: " + screenName);
      SERIALIZE_FAILURE_COUNT_NONPENGUIN_DEPENDENT.increment();
    }
    return this;
  }

  private void addNamedEntityFields(
      Set<Pair<EarlybirdFieldConstant, String>> fieldsApplied,
      EarlybirdFieldConstant nameOnlyField,
      EarlybirdFieldConstant nameWithTypeField,
      String name,
      NamedEntityContext context) {
    withOneTimeStringField(fieldsApplied, nameOnlyField, name, false);
    if (context.isSetEntity_type()) {
      withOneTimeStringField(fieldsApplied, nameWithTypeField,
          formatNamedEntityString(name, context.getEntity_type()), true);
    }
  }

  private void withOneTimeStringField(
      Set<Pair<EarlybirdFieldConstant, String>> fieldsApplied, EarlybirdFieldConstant field,
      String value, boolean addToFacets) {
    Pair<EarlybirdFieldConstant, String> fieldValuePair = new Pair<>(field, value);
    if (!fieldsApplied.contains(fieldValuePair)) {
      if (addToFacets) {
        addFacetSkipList(field.getFieldName());
      }
      withStringField(field.getFieldName(), value);
      fieldsApplied.add(fieldValuePair);
    }
  }

  private String formatNamedEntityString(String name, WholeEntityType type) {
    return String.format("%s:%s", name, type).toLowerCase();
  }

  /**
   * Set whether set LAT_LON_CSF_FIELD or not before build
   * if LAT_LON_CSF_FIELD is not set deliberately.
   *
   * @see #prepareToBuild()
   */
  public EarlybirdThriftDocumentBuilder setAddLatLonCSF(boolean isSet) {
    addLatLonCSF = isSet;
    return this;
  }

  /**
   * Set if add encoded tweet feature field in the end.
   *
   * @see #prepareToBuild()
   */
  public EarlybirdThriftDocumentBuilder setAddEncodedTweetFeatures(boolean isSet) {
    addEncodedTweetFeatures = isSet;
    return this;
  }

  @Override
  protected void prepareToBuild() {
    if (!isSetLatLonCSF && addLatLonCSF) {
      // In lucene archives, this CSF is needed regardless of whether geoLocation is set.
      withLatLonCSF(GeoUtil.ILLEGAL_LATLON, GeoUtil.ILLEGAL_LATLON);
    }

    if (addEncodedTweetFeatures) {
      // Add encoded_tweet_features before building the document.
      withBytesField(
          EarlybirdFieldConstant.ENCODED_TWEET_FEATURES_FIELD.getFieldName(),
          EarlybirdEncodedFeaturesUtil.toBytesForThriftDocument(encodedTweetFeatures));
    }

    if (extendedEncodedTweetFeatures != null) {
      // Add extended_encoded_tweet_features before building the document.
      withBytesField(
          EarlybirdFieldConstant.EXTENDED_ENCODED_TWEET_FEATURES_FIELD.getFieldName(),
          EarlybirdEncodedFeaturesUtil.toBytesForThriftDocument(extendedEncodedTweetFeatures));
    }
  }

  private static boolean isNotBlank(String value) {
    return value != null && !value.isEmpty();
  }

  private static boolean isNotEmpty(List<?> value) {
    return value != null && !value.isEmpty();
  }
}
