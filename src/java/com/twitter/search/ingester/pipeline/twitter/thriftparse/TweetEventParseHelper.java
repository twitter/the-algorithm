package com.twitter.search.ingester.pipeline.twitter.thriftparse;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common_internal.text.version.PenguinVersion;
import com.twitter.dataproducts.enrichments.thriftjava.GeoEntity;
import com.twitter.dataproducts.enrichments.thriftjava.PotentialLocation;
import com.twitter.dataproducts.enrichments.thriftjava.ProfileGeoEnrichment;
import com.twitter.escherbird.thriftjava.TweetEntityAnnotation;
import com.twitter.expandodo.thriftjava.Card2;
import com.twitter.gizmoduck.thriftjava.User;
import com.twitter.mediaservices.commons.tweetmedia.thrift_java.MediaInfo;
import com.twitter.search.common.debug.thriftjava.DebugEvents;
import com.twitter.search.common.metrics.Percentile;
import com.twitter.search.common.metrics.PercentileUtil;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.partitioning.snowflakeparser.SnowflakeIdParser;
import com.twitter.search.common.relevance.entities.GeoObject;
import com.twitter.search.common.relevance.entities.PotentialLocationObject;
import com.twitter.search.common.relevance.entities.TwitterMessage;
import com.twitter.search.common.relevance.entities.TwitterMessage.EscherbirdAnnotation;
import com.twitter.search.common.relevance.entities.TwitterMessageUser;
import com.twitter.search.common.relevance.entities.TwitterMessageUtil;
import com.twitter.search.common.relevance.entities.TwitterQuotedMessage;
import com.twitter.search.common.relevance.entities.TwitterRetweetMessage;
import com.twitter.search.ingester.model.IngesterTwitterMessage;
import com.twitter.search.ingester.pipeline.util.CardFieldUtil;
import com.twitter.service.spiderduck.gen.MediaTypes;
import com.twitter.tweetypie.thriftjava.DeviceSource;
import com.twitter.tweetypie.thriftjava.DirectedAtUser;
import com.twitter.tweetypie.thriftjava.EscherbirdEntityAnnotations;
import com.twitter.tweetypie.thriftjava.ExclusiveTweetControl;
import com.twitter.tweetypie.thriftjava.GeoCoordinates;
import com.twitter.tweetypie.thriftjava.HashtagEntity;
import com.twitter.tweetypie.thriftjava.MediaEntity;
import com.twitter.tweetypie.thriftjava.MentionEntity;
import com.twitter.tweetypie.thriftjava.Place;
import com.twitter.tweetypie.thriftjava.QuotedTweet;
import com.twitter.tweetypie.thriftjava.Reply;
import com.twitter.tweetypie.thriftjava.Tweet;
import com.twitter.tweetypie.thriftjava.TweetCoreData;
import com.twitter.tweetypie.thriftjava.TweetCreateEvent;
import com.twitter.tweetypie.thriftjava.TweetDeleteEvent;
import com.twitter.tweetypie.thriftjava.UrlEntity;
import com.twitter.tweetypie.tweettext.PartialHtmlEncoding;

/**
 * This is an utility class for converting Thrift TweetEvent messages sent by TweetyPie
 * into ingester internal representation, IngesterTwitterMessage.
 */
public final class TweetEventParseHelper {
  private static final Logger LOG = LoggerFactory.getLogger(TweetEventParseHelper.class);

  @VisibleForTesting
  static final SearchCounter NUM_TWEETS_WITH_NULL_TEXT =
      SearchCounter.export("tweets_with_null_text_from_thrift_cnt");

  @VisibleForTesting
  static final SearchCounter TWEET_SIZE = SearchCounter.export("tweet_size_from_thrift");

  @VisibleForTesting
  static final Percentile<Long> TWEET_SIZE_PERCENTILES =
      PercentileUtil.createPercentile("tweet_size_from_thrift");

  @VisibleForTesting
  static final SearchCounter NUM_TWEETS_WITH_CONVERSATION_ID =
      SearchCounter.export("tweets_with_conversation_id_from_thrift_cnt");

  @VisibleForTesting
  static final SearchCounter NUM_TWEETS_WITH_QUOTE =
      SearchCounter.export("tweets_with_quote_from_thrift_cnt");

  @VisibleForTesting
  static final SearchCounter NUM_TWEETS_WITH_ANNOTATIONS =
      SearchCounter.export("tweets_with_annotation_from_thrift_cnt");

  @VisibleForTesting
  static final SearchCounter NUM_ANNOTATIONS_ADDED =
      SearchCounter.export("num_annotations_from_thrift_cnt");

  @VisibleForTesting
  static final SearchCounter NUM_TWEETS_WITH_COORDINATE_FIELD =
      SearchCounter.export("tweets_with_coordinate_field_from_thrift_cnt");

  @VisibleForTesting
  static final SearchCounter NUM_PLACE_ADDED =
      SearchCounter.export("num_places_from_thrift_cnt");

  @VisibleForTesting
  static final SearchCounter NUM_TWEETS_WITH_PLACE_FIELD =
      SearchCounter.export("tweets_with_place_field_from_thrift_cnt");

  @VisibleForTesting
  static final SearchCounter NUM_TWEETS_WITH_PLACE_COUNTRY_CODE =
      SearchCounter.export("tweets_with_place_country_code_from_thrift_cnt");

  @VisibleForTesting
  static final SearchCounter NUM_TWEETS_USE_PLACE_FIELD =
      SearchCounter.export("tweets_use_place_field_from_thrift_cnt");

  @VisibleForTesting
  static final SearchCounter NUM_TWEETS_CANNOT_PARSE_PLACE_FIELD =
      SearchCounter.export("tweets_cannot_parse_place_field_from_thrift_cnt");

  @VisibleForTesting
  static final SearchCounter NUM_TWEETS_WITH_PROFILE_GEO_ENRICHMENT =
      SearchCounter.export("tweets_with_profile_geo_enrichment_from_thrift_cnt");

  @VisibleForTesting
  static final SearchCounter NUM_TWEETS_WITH_MENTIONS =
      SearchCounter.export("tweets_with_mentions_from_thrift_cnt");

  @VisibleForTesting
  static final SearchCounter NUM_MENTIONS_ADDED =
      SearchCounter.export("num_mentions_from_thrift_cnt");

  @VisibleForTesting
  static final SearchCounter NUM_TWEETS_WITH_HASHTAGS =
      SearchCounter.export("tweets_with_hashtags_from_thrift_cnt");

  @VisibleForTesting
  static final SearchCounter NUM_HASHTAGS_ADDED =
      SearchCounter.export("num_hashtags_from_thrift_cnt");

  @VisibleForTesting
  static final SearchCounter NUM_TWEETS_WITH_MEDIA_URL =
      SearchCounter.export("tweets_with_media_url_from_thrift_cnt");

  @VisibleForTesting
  static final SearchCounter NUM_MEDIA_URLS_ADDED =
      SearchCounter.export("num_media_urls_from_thrift_cnt");

  @VisibleForTesting
  static final SearchCounter NUM_TWEETS_WITH_PHOTO_MEDIA_URL =
      SearchCounter.export("tweets_with_photo_media_url_from_thrift_cnt");

  @VisibleForTesting
  static final SearchCounter NUM_TWEETS_WITH_VIDEO_MEDIA_URL =
      SearchCounter.export("tweets_with_video_media_url_from_thrift_cnt");

  @VisibleForTesting
  static final SearchCounter NUM_TWEETS_WITH_NON_MEDIA_URL =
      SearchCounter.export("tweets_with_non_media_url_from_thrift_cnt");

  @VisibleForTesting
  static final SearchCounter NUM_NON_MEDIA_URLS_ADDED =
      SearchCounter.export("num_non_media_urls_from_thrift_cnt");

  @VisibleForTesting
  static final SearchCounter NUM_TWEETS_MISSING_QUOTE_URLS =
      SearchCounter.export("num_tweets_missing_quote_urls_cnt");

  // Utility class, disallow instantiation.
  private TweetEventParseHelper() {
  }

  /** Builds an IngesterTwitterMessage instance from a TweetCreateEvent. */
  @Nonnull
  public static IngesterTwitterMessage getTwitterMessageFromCreationEvent(
      @Nonnull TweetCreateEvent createEvent,
      @Nonnull List<PenguinVersion> supportedPenguinVersions,
      @Nullable DebugEvents debugEvents) throws ThriftTweetParsingException {

    Tweet tweet = createEvent.getTweet();
    if (tweet == null) {
      throw new ThriftTweetParsingException("No tweet field in TweetCreateEvent");
    }

    TweetCoreData coreData = tweet.getCore_data();
    if (coreData == null) {
      throw new ThriftTweetParsingException("No core_data field in Tweet in TweetCreateEvent");
    }

    User user = createEvent.getUser();
    if (user == null) {
      throw new ThriftTweetParsingException("No user field in TweetCreateEvent");
    }
    if (!user.isSetProfile()) {
      throw new ThriftTweetParsingException("No profile field in User in TweetCreateEvent");
    }
    if (!user.isSetSafety()) {
      throw new ThriftTweetParsingException("No safety field in User in TweetCreateEvent");
    }

    long twitterId = tweet.getId();
    IngesterTwitterMessage message = new IngesterTwitterMessage(
        twitterId,
        supportedPenguinVersions,
        debugEvents);

    // Set the creation time based on the tweet ID, because it has millisecond granularity,
    // and coreData.created_at_secs has only second granularity.
    message.setDate(new Date(SnowflakeIdParser.getTimestampFromTweetId(twitterId)));

    boolean isNsfw = coreData.isNsfw_admin() || coreData.isNsfw_user();
    boolean hasMediaOrUrlsOrCards =
        tweet.getMediaSize() > 0
            || tweet.getUrlsSize() > 0
            || tweet.getCardsSize() > 0
            || tweet.isSetCard2();

    message.setIsSensitiveContent(isNsfw && hasMediaOrUrlsOrCards);

    message.setFromUser(getFromUser(user));
    if (user.isSetCounts()) {
      message.setFollowersCount((int) user.getCounts().getFollowers());
    }
    message.setUserProtected(user.getSafety().isIs_protected());
    message.setUserVerified(user.getSafety().isVerified());
    message.setUserBlueVerified(user.getSafety().isIs_blue_verified());

    if (tweet.isSetLanguage()) {
      message.setLanguage(tweet.getLanguage().getLanguage()); // language ID like "en"
    }

    if (tweet.isSetSelf_thread_metadata()) {
      message.setSelfThread(true);
    }

    ExclusiveTweetControl exclusiveTweetControl = tweet.getExclusive_tweet_control();
    if (exclusiveTweetControl != null) {
      if (exclusiveTweetControl.isSetConversation_author_id()) {
        message.setExclusiveConversationAuthorId(
            exclusiveTweetControl.getConversation_author_id());
      }
    }

    setDirectedAtUser(message, coreData);
    addMentionsToMessage(message, tweet);
    addHashtagsToMessage(message, tweet);
    addMediaEntitiesToMessage(message, tweet.getId(), tweet.getMedia());
    addUrlsToMessage(message, tweet.getUrls());
    addEscherbirdAnnotationsToMessage(message, tweet);
    message.setNullcast(coreData.isNullcast());

    if (coreData.isSetConversation_id()) {
      message.setConversationId(coreData.getConversation_id());
      NUM_TWEETS_WITH_CONVERSATION_ID.increment();
    }

    // quotes
    if (tweet.isSetQuoted_tweet()) {
      QuotedTweet quotedTweet = tweet.getQuoted_tweet();
      if (quotedTweet.getTweet_id() > 0 &&  quotedTweet.getUser_id() > 0) {
        if (quotedTweet.isSetPermalink()) {
          String quotedURL = quotedTweet.getPermalink().getLong_url();
          UrlEntity quotedURLEntity = new UrlEntity();
          quotedURLEntity.setExpanded(quotedURL);
          quotedURLEntity.setUrl(quotedTweet.getPermalink().getShort_url());
          quotedURLEntity.setDisplay(quotedTweet.getPermalink().getDisplay_text());
          addUrlsToMessage(message, Lists.newArrayList(quotedURLEntity));
        } else {
          LOG.warn("Tweet {} has quoted tweet, but is missing quoted tweet URL: {}",
                   tweet.getId(), quotedTweet);
          NUM_TWEETS_MISSING_QUOTE_URLS.increment();
        }
        TwitterQuotedMessage quotedMessage =
            new TwitterQuotedMessage(
                quotedTweet.getTweet_id(),
                quotedTweet.getUser_id());
        message.setQuotedMessage(quotedMessage);
        NUM_TWEETS_WITH_QUOTE.increment();
      }
    }

    // card fields
    if (createEvent.getTweet().isSetCard2()) {
      Card2 card = createEvent.getTweet().getCard2();
      message.setCardName(card.getName());
      message.setCardTitle(
          CardFieldUtil.extractBindingValue(CardFieldUtil.TITLE_BINDING_KEY, card));
      message.setCardDescription(
          CardFieldUtil.extractBindingValue(CardFieldUtil.DESCRIPTION_BINDING_KEY, card));
      CardFieldUtil.deriveCardLang(message);
      message.setCardUrl(card.getUrl());
    }

    // Some fields should be set based on the "original" tweet. So if this tweet is a retweet,
    // we want to extract those fields from the retweeted tweet.
    Tweet retweetOrTweet = tweet;
    TweetCoreData retweetOrTweetCoreData = coreData;
    User retweetOrTweetUser = user;

    // retweets
    boolean isRetweet = coreData.isSetShare();
    if (isRetweet) {
      retweetOrTweet = createEvent.getSource_tweet();
      retweetOrTweetCoreData = retweetOrTweet.getCore_data();
      retweetOrTweetUser = createEvent.getSource_user();

      TwitterRetweetMessage retweetMessage = new TwitterRetweetMessage();
      retweetMessage.setRetweetId(twitterId);

      if (retweetOrTweetUser != null) {
        if (retweetOrTweetUser.isSetProfile()) {
          retweetMessage.setSharedUserDisplayName(retweetOrTweetUser.getProfile().getName());
        }
        retweetMessage.setSharedUserTwitterId(retweetOrTweetUser.getId());
      }

      retweetMessage.setSharedDate(new Date(retweetOrTweetCoreData.getCreated_at_secs() * 1000));
      retweetMessage.setSharedId(retweetOrTweet.getId());

      addMediaEntitiesToMessage(message, retweetOrTweet.getId(), retweetOrTweet.getMedia());
      addUrlsToMessage(message, retweetOrTweet.getUrls());

      // If a tweet's text is longer than 140 characters, the text for any retweet of that tweet
      // will be truncated. And if the original tweet has hashtags or mentions after character 140,
      // the Tweetypie event for the retweet will not include those hashtags/mentions, which will
      // make the retweet unsearchable by those hashtags/mentions. So in order to avoid this
      // problem, we add to the retweet all hashtags/mentions set on the original tweet.
      addMentionsToMessage(message, retweetOrTweet);
      addHashtagsToMessage(message, retweetOrTweet);

      message.setRetweetMessage(retweetMessage);
    }

    // Some fields should be set based on the "original" tweet.
    // Only set geo fields if this is not a retweet
    if (!isRetweet) {
      setGeoFields(message, retweetOrTweetCoreData, retweetOrTweetUser);
      setPlacesFields(message, retweetOrTweet);
    }
    setText(message, retweetOrTweetCoreData);
    setInReplyTo(message, retweetOrTweetCoreData, isRetweet);
    setDeviceSourceField(message, retweetOrTweet);

    // Profile geo enrichment fields should be set based on this tweet, even if it's a retweet.
    setProfileGeoEnrichmentFields(message, tweet);

    // The composer used to create this tweet: standard tweet creator or the camera flow.
    setComposerSource(message, tweet);

    return message;
  }

  private static void setGeoFields(
      TwitterMessage message, TweetCoreData coreData, User user) {

    if (coreData.isSetCoordinates()) {
      NUM_TWEETS_WITH_COORDINATE_FIELD.increment();
      GeoCoordinates coords = coreData.getCoordinates();
      message.setGeoTaggedLocation(
          GeoObject.createForIngester(coords.getLatitude(), coords.getLongitude()));

      String location =
          String.format("GeoAPI:%.4f,%.4f", coords.getLatitude(), coords.getLongitude());
      TwitterMessageUtil.setAndTruncateLocationOnMessage(message, location);
    }

    // If the location was not set from the coordinates.
    if ((message.getOrigLocation() == null) && (user != null) && user.isSetProfile()) {
      TwitterMessageUtil.setAndTruncateLocationOnMessage(message, user.getProfile().getLocation());
    }
  }

  private static void setPlacesFields(TwitterMessage message, Tweet tweet) {
    if (!tweet.isSetPlace()) {
      return;
    }

    Place place = tweet.getPlace();

    if (place.isSetContainers() && place.getContainersSize() > 0) {
      NUM_TWEETS_WITH_PLACE_FIELD.increment();
      NUM_PLACE_ADDED.add(place.getContainersSize());

      for (String placeId : place.getContainers()) {
        message.addPlace(placeId);
      }
    }

    Preconditions.checkArgument(place.isSetId(), "Tweet.Place without id.");
    message.setPlaceId(place.getId());
    Preconditions.checkArgument(place.isSetFull_name(), "Tweet.Place without full_name.");
    message.setPlaceFullName(place.getFull_name());
    if (place.isSetCountry_code()) {
      message.setPlaceCountryCode(place.getCountry_code());
      NUM_TWEETS_WITH_PLACE_COUNTRY_CODE.increment();
    }

    if (message.getGeoTaggedLocation() == null) {
      Optional<GeoObject> location = GeoObject.fromPlace(place);

      if (location.isPresent()) {
        NUM_TWEETS_USE_PLACE_FIELD.increment();
        message.setGeoTaggedLocation(location.get());
      } else {
        NUM_TWEETS_CANNOT_PARSE_PLACE_FIELD.increment();
      }
    }
  }

  private static void setText(TwitterMessage message, TweetCoreData coreData) {
    /**
     * TweetyPie doesn't do a full HTML escaping of the text, only a partial escaping
     * so we use their code to unescape it first, then we do
     * a second unescaping because when the tweet text itself has HTML escape
     * sequences, we want to index the unescaped version, not the escape sequence itself.
     * --
     * Yes, we *double* unescape html. About 1-2 tweets per second are double escaped,
     * and we probably want to index the real text and not things like '&#9733;'.
     * Unescaping already unescaped text seems safe in practice.
     * --
     *
     * This may seem wrong, because one thinks we should index whatever the user posts,
     * but given punctuation stripping this creates odd behavior:
     *
     * If someone tweets &amp; they won't be able to find it by searching for '&amp;' because
     * the tweet will be indexed as 'amp'
     *
     * It would also prevent some tweets from surfacing for certain searches, for example:
     *
     * User Tweets: John Mayer &amp; Dave Chappelle
     * We Unescape To: John Mayer & Dave Chappelle
     * We Strip/Normalize To: john mayer dave chappelle
     *
     * A user searching for 'John Mayer Dave Chappelle' would get the above tweet.
     *
     * If we didn't double unescape
     *
     * User Tweets: John Mayer &amp; Dave Chappelle
     * We Strip/Normalize To: john mayer amp dave chappelle
     *
     * A user searching for 'John Mayer Dave Chappelle' would miss the above tweet.
     *
     * Second example
     *
     * User Tweets: L'Humanit&eacute;
     * We Unescape To: L'Humanité
     * We Strip/Normalize To: l humanite
     *
     * If we didn't double escape
     *
     * User Tweets: L'Humanit&eacute;
     * We Strip/Normalize To: l humanit eacute
     *
     */

    String text = coreData.isSetText()
        ? StringEscapeUtils.unescapeHtml(PartialHtmlEncoding.decode(coreData.getText()))
        : coreData.getText();
    message.setText(text);
    if (text != null) {
      long tweetLength = text.length();
      TWEET_SIZE.add(tweetLength);
      TWEET_SIZE_PERCENTILES.record(tweetLength);
    } else {
      NUM_TWEETS_WITH_NULL_TEXT.increment();
    }
  }

  private static void setInReplyTo(
      TwitterMessage message, TweetCoreData coreData, boolean isRetweet) {
    Reply reply = coreData.getReply();
    if (!isRetweet && reply != null) {
      String inReplyToScreenName = reply.getIn_reply_to_screen_name();
      long inReplyToUserId = reply.getIn_reply_to_user_id();
      message.replaceToUserWithInReplyToUserIfNeeded(inReplyToScreenName, inReplyToUserId);
    }

    if ((reply != null) && reply.isSetIn_reply_to_status_id()) {
      message.setInReplyToStatusId(reply.getIn_reply_to_status_id());
    }
  }

  private static void setProfileGeoEnrichmentFields(TwitterMessage message, Tweet tweet) {
    if (!tweet.isSetProfile_geo_enrichment()) {
      return;
    }

    ProfileGeoEnrichment profileGeoEnrichment = tweet.getProfile_geo_enrichment();
    List<PotentialLocation> thriftPotentialLocations =
        profileGeoEnrichment.getPotential_locations();
    if (!thriftPotentialLocations.isEmpty()) {
      NUM_TWEETS_WITH_PROFILE_GEO_ENRICHMENT.increment();
      List<PotentialLocationObject> potentialLocations = Lists.newArrayList();
      for (PotentialLocation potentialLocation : thriftPotentialLocations) {
        GeoEntity geoEntity = potentialLocation.getGeo_entity();
        potentialLocations.add(new PotentialLocationObject(geoEntity.getCountry_code(),
                                                           geoEntity.getRegion(),
                                                           geoEntity.getLocality()));
      }

      message.setPotentialLocations(potentialLocations);
    }
  }

  private static void setDeviceSourceField(TwitterMessage message, Tweet tweet) {
    DeviceSource deviceSource = tweet.getDevice_source();
    TwitterMessageUtil.setSourceOnMessage(message, modifyDeviceSourceWithNofollow(deviceSource));
  }

  /** Builds an IngesterTwitterMessage instance from a TweetDeleteEvent. */
  @Nonnull
  public static IngesterTwitterMessage getTwitterMessageFromDeletionEvent(
      @Nonnull TweetDeleteEvent deleteEvent,
      @Nonnull List<PenguinVersion> supportedPenguinVersions,
      @Nullable DebugEvents debugEvents) throws ThriftTweetParsingException {

    Tweet tweet = deleteEvent.getTweet();
    if (tweet == null) {
      throw new ThriftTweetParsingException("No tweet field in TweetDeleteEvent");
    }
    long tweetId = tweet.getId();

    TweetCoreData coreData = tweet.getCore_data();
    if (coreData == null) {
      throw new ThriftTweetParsingException("No TweetCoreData in TweetDeleteEvent");
    }
    long userId = coreData.getUser_id();

    IngesterTwitterMessage message = new IngesterTwitterMessage(
        tweetId,
        supportedPenguinVersions,
        debugEvents);
    message.setDeleted(true);
    message.setText("delete");
    message.setFromUser(TwitterMessageUser.createWithNamesAndId("delete", "delete", userId));

    return message;
  }

  private static TwitterMessageUser getFromUser(User user) {
    String screenName = user.getProfile().getScreen_name();
    long id = user.getId();
    String displayName = user.getProfile().getName();
    return TwitterMessageUser.createWithNamesAndId(screenName, displayName, id);
  }

  private static void addMentionsToMessage(IngesterTwitterMessage message, Tweet tweet) {
    List<MentionEntity> mentions = tweet.getMentions();
    if (mentions != null) {
      NUM_TWEETS_WITH_MENTIONS.increment();
      NUM_MENTIONS_ADDED.add(mentions.size());
      for (MentionEntity mention : mentions) {
        addMention(message, mention);
      }
    }
  }

  private static void addMention(IngesterTwitterMessage message, MentionEntity mention) {
    // Default values. They are weird, but are consistent with JSON parsing behavior.
    Optional<Long> id = Optional.of(-1L);
    Optional<String> screenName = Optional.of("");
    Optional<String> displayName = Optional.of("");

    if (mention.isSetUser_id()) {
      id = Optional.of(mention.getUser_id());
    }

    if (mention.isSetScreen_name()) {
      screenName = Optional.of(mention.getScreen_name());
    }

    if (mention.isSetName()) {
      displayName = Optional.of(mention.getName());
    }

    TwitterMessageUser mentionedUser = TwitterMessageUser
        .createWithOptionalNamesAndId(screenName, displayName, id);

    if (isToUser(mention, message.getToUserObject())) {
      message.setToUserObject(mentionedUser);
    }
    message.addUserToMentions(mentionedUser);
  }

  private static boolean isToUser(
      MentionEntity mention, Optional<TwitterMessageUser> optionalToUser) {
    if (mention.getFrom_index() == 0) {
      return true;
    }
    if (optionalToUser.isPresent()) {
      TwitterMessageUser toUser = optionalToUser.get();
      if (toUser.getId().isPresent()) {
        long toUserId = toUser.getId().get();
        return mention.getUser_id() == toUserId;
      }
    }
    return false;
  }

  private static void addHashtagsToMessage(IngesterTwitterMessage message, Tweet tweet) {
    List<HashtagEntity> hashtags = tweet.getHashtags();
    if (hashtags != null) {
      NUM_TWEETS_WITH_HASHTAGS.increment();
      NUM_HASHTAGS_ADDED.add(hashtags.size());
      for (HashtagEntity hashtag : hashtags) {
        addHashtag(message, hashtag);
      }
    }
  }

  private static void addHashtag(IngesterTwitterMessage message, HashtagEntity hashtag) {
    String hashtagString = hashtag.getText();
    message.addHashtag(hashtagString);
  }

  /** Add the given media entities to the given message. */
  public static void addMediaEntitiesToMessage(
      IngesterTwitterMessage message,
      long photoStatusId,
      @Nullable List<MediaEntity> medias) {

    if (medias != null) {
      NUM_TWEETS_WITH_MEDIA_URL.increment();
      NUM_MEDIA_URLS_ADDED.add(medias.size());

      boolean hasPhotoMediaUrl = false;
      boolean hasVideoMediaUrl = false;
      for (MediaEntity media : medias) {
        MediaTypes mediaType = null;
        if (media.isSetMedia_info()) {
          MediaInfo mediaInfo = media.getMedia_info();
          if (mediaInfo != null) {
            if (mediaInfo.isSet(MediaInfo._Fields.IMAGE_INFO)) {
              mediaType = MediaTypes.NATIVE_IMAGE;
              String mediaUrl = media.getMedia_url_https();
              if (mediaUrl != null) {
                hasPhotoMediaUrl = true;
                message.addPhotoUrl(photoStatusId, mediaUrl);
                // Add this link to the expanded URLs too, so that the HAS_NATIVE_IMAGE_FLAG is set
                // correctly too. See EncodedFeatureBuilder.updateLinkEncodedFeatures().
              }
            } else if (mediaInfo.isSet(MediaInfo._Fields.VIDEO_INFO)) {
              mediaType = MediaTypes.VIDEO;
              hasVideoMediaUrl = true;
            }
          }
        }
        String originalUrl = media.getUrl();
        String expandedUrl = media.getExpanded_url();
        message.addExpandedMediaUrl(originalUrl, expandedUrl, mediaType);
      }

      if (hasPhotoMediaUrl) {
        NUM_TWEETS_WITH_PHOTO_MEDIA_URL.increment();
      }
      if (hasVideoMediaUrl) {
        NUM_TWEETS_WITH_VIDEO_MEDIA_URL.increment();
      }
    }
  }

  /** Adds the given urls to the given message. */
  public static void addUrlsToMessage(
      IngesterTwitterMessage message,
      @Nullable List<UrlEntity> urls) {

    if (urls != null) {
      NUM_TWEETS_WITH_NON_MEDIA_URL.increment();
      NUM_NON_MEDIA_URLS_ADDED.add(urls.size());
      for (UrlEntity url : urls) {
        String originalUrl = url.getUrl();
        String expandedUrl = url.getExpanded();
        message.addExpandedNonMediaUrl(originalUrl, expandedUrl);
      }
    }
  }

  private static void addEscherbirdAnnotationsToMessage(
      IngesterTwitterMessage message, Tweet tweet) {
    if (tweet.isSetEscherbird_entity_annotations()) {
      EscherbirdEntityAnnotations entityAnnotations = tweet.getEscherbird_entity_annotations();
      if (entityAnnotations.isSetEntity_annotations()) {
        NUM_TWEETS_WITH_ANNOTATIONS.increment();
        NUM_ANNOTATIONS_ADDED.add(entityAnnotations.getEntity_annotationsSize());
        for (TweetEntityAnnotation entityAnnotation : entityAnnotations.getEntity_annotations()) {
          EscherbirdAnnotation escherbirdAnnotation =
              new EscherbirdAnnotation(entityAnnotation.getGroupId(),
                  entityAnnotation.getDomainId(),
                  entityAnnotation.getEntityId());
          message.addEscherbirdAnnotation(escherbirdAnnotation);
        }
      }
    }
  }

  private static void setComposerSource(IngesterTwitterMessage message, Tweet tweet) {
    if (tweet.isSetComposer_source()) {
      message.setComposerSource(tweet.getComposer_source());
    }
  }

  private static String modifyDeviceSourceWithNofollow(@Nullable DeviceSource deviceSource) {
    if (deviceSource != null) {
      String source = deviceSource.getDisplay();
      int i = source.indexOf("\">");
      if (i == -1) {
        return source;
      } else {
        return source.substring(0, i) + "\" rel=\"nofollow\">" + source.substring(i + 2);
      }
    } else {
      return "<a href=\"http://twitter.com\" rel=\"nofollow\">Twitter</a>";
    }
  }

  private static void setDirectedAtUser(
      IngesterTwitterMessage message,
      TweetCoreData tweetCoreData) {
    if (!tweetCoreData.isSetDirected_at_user()) {
      return;
    }

    DirectedAtUser directedAtUser = tweetCoreData.getDirected_at_user();

    if (!directedAtUser.isSetUser_id()) {
      return;
    }

    message.setDirectedAtUserId(Optional.of(directedAtUser.getUser_id()));
  }
}
