package com.X.search.earlybird.queryparser;

import java.util.Map;

import com.google.common.annotations.VisibleForTesting;

import org.apache.lucene.search.Query;

import com.X.decider.Decider;
import com.X.search.common.query.MappableField;
import com.X.search.common.schema.base.FieldWeightDefault;
import com.X.search.common.schema.base.ImmutableSchemaInterface;
import com.X.search.common.schema.earlybird.EarlybirdCluster;
import com.X.search.common.search.TerminationTracker;
import com.X.search.common.search.termination.QueryTimeout;
import com.X.search.earlybird.common.userupdates.UserScrubGeoMap;
import com.X.search.earlybird.common.userupdates.UserTable;
import com.X.search.earlybird.partition.MultiSegmentTermDictionaryManager;
import com.X.search.earlybird.querycache.QueryCacheManager;
import com.X.search.queryparser.query.search.SearchOperator;

public class LuceneRelevanceQueryVisitor extends EarlybirdLuceneQueryVisitor {
  public LuceneRelevanceQueryVisitor(
      ImmutableSchemaInterface schema,
      QueryCacheManager queryCacheManager,
      UserTable userTable,
      UserScrubGeoMap userScrubGeoMap,
      TerminationTracker terminationTracker,
      Map<String, FieldWeightDefault> fieldWeightMap,
      Map<MappableField, String> mappableFieldMap,
      MultiSegmentTermDictionaryManager multiSegmentTermDictionaryManager,
      Decider decider,
      EarlybirdCluster earlybirdCluster,
      QueryTimeout queryTimeout) {
    super(
        schema,
        queryCacheManager,
        userTable,
        userScrubGeoMap,
        terminationTracker,
        fieldWeightMap,
        mappableFieldMap,
        multiSegmentTermDictionaryManager,
        decider,
        earlybirdCluster,
        queryTimeout);
  }

  @VisibleForTesting
  protected LuceneRelevanceQueryVisitor(
      ImmutableSchemaInterface schema,
      QueryCacheManager queryCacheManager,
      UserTable userTable,
      UserScrubGeoMap userScrubGeoMap,
      EarlybirdCluster earlybirdCluster) {
    super(schema,
          queryCacheManager,
          userTable,
          userScrubGeoMap,
          earlybirdCluster,
          queryCacheManager.getDecider());
  }

  @Override
  protected Query visitSinceIDOperator(SearchOperator op) {
    // since_id is handled by the blender for relevance queries, so don't filter on it.
    return null;
  }
}
