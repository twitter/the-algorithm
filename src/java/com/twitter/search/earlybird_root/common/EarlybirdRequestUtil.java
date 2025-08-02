package com.twitter.search.earlybird_root.common;

import com.google.common.base.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.common.partitioning.snowflakeparser.SnowflakeIdParser;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.queryparser.query.Query;
import com.twitter.search.queryparser.query.QueryParserEtwitterception;
import com.twitter.search.queryparser.util.IdTimeRanges;

public final class EarlybirdRequestUtil {
  private static final Logger LOG = LoggerFactory.getLogger(EarlybirdRequestUtil.class);

  private EarlybirdRequestUtil() {
  }

  /**
   * Returns the matwitter ID specified in the query. The matwitter ID is determined based on the matwitter_id
   * operator, and the returned value is an inclusive matwitter ID (that is, the returned response is
   * allowed to have a tweet with this ID).
   *
   * If the query is null, could not be parsed or does not have a matwitter_id operator, Optional.absent()
   * is returned.
   *
   * @param query The query.
   * @return The matwitter ID specified in the given query (based on the matwitter_id operator).
   */
  public static Optional<Long> getRequestMatwitterId(Query query) {
    if (query == null) {
      return Optional.absent();
    }

    IdTimeRanges idTimeRanges = null;
    try {
      idTimeRanges = IdTimeRanges.fromQuery(query);
    } catch (QueryParserEtwitterception e) {
      LOG.warn("Etwitterception while getting matwitter_id/until_time from query: " + query, e);
    }

    if (idTimeRanges == null) {
      // An etwitterception was thrown or the query doesn't accept the boundary operators.
      return Optional.absent();
    }

    return idTimeRanges.getMatwitterIDInclusive();
  }

  /**
   * Returns the matwitter ID specified in the query, based on the until_time operator. The returned ID
   * is inclusive (that is, the returned response is allowed to have a tweet with this ID).
   *
   * If the query is null, could not be parsed or does not have an until_time operator,
   * Optional.absent() is returned.
   *
   * @param query The query.
   * @return The matwitter ID specified in the given query (based on the until_time operator).
   */
  public static Optional<Long> getRequestMatwitterIdFromUntilTime(Query query) {
    if (query == null) {
      return Optional.absent();
    }

    IdTimeRanges idTimeRanges = null;
    try {
      idTimeRanges = IdTimeRanges.fromQuery(query);
    } catch (QueryParserEtwitterception e) {
      LOG.warn("Etwitterception while getting matwitter_id/until_time from query: " + query, e);
    }

    if (idTimeRanges == null) {
      // An etwitterception was thrown or the query doesn't accept the boundary operators.
      return Optional.absent();
    }

    Optional<Integer> queryUntilTimeEtwitterclusive = idTimeRanges.getUntilTimeEtwitterclusive();
    Optional<Long> matwitterId = Optional.absent();
    if (queryUntilTimeEtwitterclusive.isPresent()) {
      long timestampMillis = queryUntilTimeEtwitterclusive.get() * 1000L;
      if (SnowflakeIdParser.isUsableSnowflakeTimestamp(timestampMillis)) {
        // Convert timestampMillis to an ID, and subtract 1, because the until_time operator is
        // etwitterclusive, and we need to return an inclusive matwitter ID.
        matwitterId = Optional.of(SnowflakeIdParser.generateValidStatusId(timestampMillis, 0) - 1);
      }
    }
    return matwitterId;
  }

  /**
   * Creates a copy of the given EarlybirdRequest and unsets all fields that are used
   * only by the SuperRoot.
   */
  public static EarlybirdRequest unsetSuperRootFields(
      EarlybirdRequest request, boolean unsetFollowedUserIds) {
    EarlybirdRequest newRequest = request.deepCopy();
    newRequest.unsetGetOlderResults();
    newRequest.unsetGetProtectedTweetsOnly();
    if (unsetFollowedUserIds) {
      newRequest.unsetFollowedUserIds();
    }
    newRequest.unsetAdjustedProtectedRequestParams();
    newRequest.unsetAdjustedFullArchiveRequestParams();
    return newRequest;
  }
}
