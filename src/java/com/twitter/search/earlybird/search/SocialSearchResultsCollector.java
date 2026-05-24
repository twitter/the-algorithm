package com.twitter.search.earlybird.search;

import java.io.IOException;

import com.twitter.common.util.Clock;
import com.twitter.search.common.schema.base.ImmutableSchemaInterface;
import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.earlybird.common.userupdates.UserTable;
import com.twitter.search.earlybird.stats.EarlybirdSearcherStats;

/**
 * Created with IntelliJ IDEA.
 * Date: 6/20/12
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class SocialSearchResultsCollector extends SearchResultsCollector {

  private final SocialFilter socialFilter;

  public SocialSearchResultsCollector(
      ImmutableSchemaInterface schema,
      SearchRequestInfo searchRequestInfo,
      SocialFilter socialFilter,
      EarlybirdSearcherStats searcherStats,
      EarlybirdCluster cluster,
      UserTable userTable,
      int requestDebugMode) {
    super(schema, searchRequestInfo, Clock.SYSTEM_CLOCK, searcherStats, cluster, userTable,
        requestDebugMode);
    this.socialFilter = socialFilter;
  }

  @Override
  public final void doCollect(long tweetID) throws IOException {
    if (socialFilter == null || socialFilter.accept(curDocId)) {
      results.add(new Hit(currTimeSliceID, tweetID));
    }
  }

  @Override
  public void startSegment() throws IOException {
    if (socialFilter != null) {
      socialFilter.startSegment(currTwitterReader);
    }
  }
}
