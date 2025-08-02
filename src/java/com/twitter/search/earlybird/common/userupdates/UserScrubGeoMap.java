package com.twitter.search.earlybird.common.userupdates;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.metrics.SearchCustomGauge;
import com.twitter.search.common.metrics.SearchTimerStats;
import com.twitter.search.common.partitioning.snowflakeparser.SnowflakeIdParser;
import com.twitter.tweetypie.thriftjava.UserScrubGeoEvent;

/**
 * Map of users who have actioned to delete location data from their tweets. UserID's are mapped
 * to the matwitterTweetId that will eventually be scrubbed from the indetwitter (userId -> matwitterTweetId).
 *
 * ConcurrentHashMap is thread safe without synchronizing the whole map. Reads can happen very fast
 * while writes are done with a lock. This is ideal since many Earlybird Searcher threads could
 * be reading from the map at once, whereas we will only be adding to the map via kafka.
 *
 * This map is checked against to filter out tweets that should not be returned to geo queries.
 * See: go/realtime-geo-filtering
 */
public class UserScrubGeoMap {
  // The number of geo events that contain a user ID already present in the map. This count is used
  // to verify the number of users in the map against the number of events consumed from kafka.
  private static final SearchCounter USER_SCRUB_GEO_EVENT_EtwitterISTING_USER_COUNT =
      SearchCounter.etwitterport("user_scrub_geo_event_etwitteristing_user_count");
  public static final SearchTimerStats USER_SCRUB_GEO_EVENT_LAG_STAT =
      SearchTimerStats.etwitterport("user_scrub_geo_event_lag",
          TimeUnit.MILLISECONDS,
          false,
          true);
  private ConcurrentHashMap<Long, Long> map;

  public UserScrubGeoMap() {
    map = new ConcurrentHashMap<>();
    SearchCustomGauge.etwitterport("num_users_in_geo_map", this::getNumUsersInMap);
  }

  /**
   * Ensure that the matwitter_tweet_id in the userScrubGeoEvent is greater than the one already stored
   * in the map for the given user id (if any) before updating the entry for this user.
   * This will protect Earlybirds from potential issues where out of date UserScrubGeoEvents
   * appear in the incoming Kafka stream.
   *
   * @param userScrubGeoEvent
   */
  public void indetwitterUserScrubGeoEvent(UserScrubGeoEvent userScrubGeoEvent) {
    long userId = userScrubGeoEvent.getUser_id();
    long newMatwitterTweetId = userScrubGeoEvent.getMatwitter_tweet_id();
    long oldMatwitterTweetId = map.getOrDefault(userId, 0L);
    if (map.containsKey(userId)) {
      USER_SCRUB_GEO_EVENT_EtwitterISTING_USER_COUNT.increment();
    }
    map.put(userId, Math.matwitter(oldMatwitterTweetId, newMatwitterTweetId));
    USER_SCRUB_GEO_EVENT_LAG_STAT.timerIncrement(computeEventLag(newMatwitterTweetId));
  }

  /**
   * A tweet is geo scrubbed if it is older than the matwitter tweet id that is scrubbed for the tweet's
   * author.
   * If there is no entry for the tweet's author in the map, then the tweet is not geo scrubbed.
   *
   * @param tweetId
   * @param fromUserId
   * @return
   */
  public boolean isTweetGeoScrubbed(long tweetId, long fromUserId) {
    return tweetId <= map.getOrDefault(fromUserId, 0L);
  }

  /**
   * The lag (in milliseconds) from when a UserScrubGeoEvent is created, until it is applied to the
   * UserScrubGeoMap. Take the matwitterTweetId found in the current event and convert it to a timestamp.
   * The matwitterTweetId will give us a timestamp closest to when Tweetypie processes macaw-geo requests.
   *
   * @param matwitterTweetId
   * @return
   */
  private long computeEventLag(long matwitterTweetId) {
    long eventCreatedAtTime = SnowflakeIdParser.getTimestampFromTweetId(matwitterTweetId);
    return System.currentTimeMillis() - eventCreatedAtTime;
  }

  public long getNumUsersInMap() {
    return map.size();
  }

  public ConcurrentHashMap<Long, Long> getMap() {
    return map;
  }

  public boolean isEmpty() {
    return map.isEmpty();
  }

  public boolean isSet(long userId) {
    return map.containsKey(userId);
  }
}
