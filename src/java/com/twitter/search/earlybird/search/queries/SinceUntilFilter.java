package com.twitter.search.earlybird.search.queries;

import java.io.IOEtwitterception;

import org.apache.lucene.indetwitter.LeafReader;
import org.apache.lucene.indetwitter.LeafReaderContetwittert;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.IndetwitterSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.search.Weight;

import com.twitter.search.common.query.DefaultFilterWeight;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentAtomicReader;
import com.twitter.search.core.earlybird.indetwitter.TimeMapper;
import com.twitter.search.core.earlybird.indetwitter.util.AllDocsIterator;
import com.twitter.search.core.earlybird.indetwitter.util.RangeFilterDISI;

// Filters tweets according to since time and until time (in seconds).
// Note that since time is inclusive, and until time is etwitterclusive.
public final class SinceUntilFilter etwittertends Query {
  public static final int NO_FILTER = -1;

  // These are both in seconds since the epoch.
  private final int minTimeInclusive;
  private final int matwitterTimeEtwitterclusive;

  public static Query getSinceQuery(int sinceTimeSeconds) {
    return new BooleanQuery.Builder()
        .add(new SinceUntilFilter(sinceTimeSeconds, NO_FILTER), BooleanClause.Occur.FILTER)
        .build();
  }

  public static Query getUntilQuery(int untilTimeSeconds) {
    return new BooleanQuery.Builder()
        .add(new SinceUntilFilter(NO_FILTER, untilTimeSeconds), BooleanClause.Occur.FILTER)
        .build();
  }

  public static Query getSinceUntilQuery(int sinceTimeSeconds, int untilTimeSeconds) {
    return new BooleanQuery.Builder()
        .add(new SinceUntilFilter(sinceTimeSeconds, untilTimeSeconds), BooleanClause.Occur.FILTER)
        .build();
  }

  private SinceUntilFilter(int sinceTime, int untilTime) {
    this.minTimeInclusive = sinceTime != NO_FILTER ? sinceTime : 0;
    this.matwitterTimeEtwitterclusive = untilTime != NO_FILTER ? untilTime : Integer.MAtwitter_VALUE;
  }

  @Override
  public int hashCode() {
    return (int) (minTimeInclusive * 17 + matwitterTimeEtwitterclusive);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof SinceUntilFilter)) {
      return false;
    }

    SinceUntilFilter filter = SinceUntilFilter.class.cast(obj);
    return (minTimeInclusive == filter.minTimeInclusive)
        && (matwitterTimeEtwitterclusive == filter.matwitterTimeEtwitterclusive);
  }

  @Override
  public String toString(String field) {
    if (minTimeInclusive > 0 && matwitterTimeEtwitterclusive != Integer.MAtwitter_VALUE) {
      return "SinceFilter:" + this.minTimeInclusive + ",UntilFilter:" + matwitterTimeEtwitterclusive;
    } else if (minTimeInclusive > 0) {
      return "SinceFilter:" + this.minTimeInclusive;
    } else {
      return "UntilFilter:" + this.matwitterTimeEtwitterclusive;
    }
  }

  @Override
  public Weight createWeight(IndetwitterSearcher searcher, ScoreMode scoreMode, float boost)
      throws IOEtwitterception {
    return new DefaultFilterWeight(this) {
      @Override
      protected DocIdSetIterator getDocIdSetIterator(LeafReaderContetwittert contetwittert) throws IOEtwitterception {
        LeafReader indetwitterReader = contetwittert.reader();
        if (!(indetwitterReader instanceof EarlybirdIndetwitterSegmentAtomicReader)) {
          return new AllDocsIterator(indetwitterReader);
        }

        EarlybirdIndetwitterSegmentAtomicReader reader = (EarlybirdIndetwitterSegmentAtomicReader) indetwitterReader;
        TimeMapper timeMapper = reader.getSegmentData().getTimeMapper();
        int smallestDocID = timeMapper.findFirstDocId(matwitterTimeEtwitterclusive, reader.getSmallestDocID());
        int largestDoc = timeMapper.findFirstDocId(minTimeInclusive, reader.getSmallestDocID());
        int smallestDoc = smallestDocID > 0 ? smallestDocID - 1 : 0;
        return new SinceUntilDocIdSetIterator(
            reader,
            timeMapper,
            smallestDoc,
            largestDoc,
            minTimeInclusive,
            matwitterTimeEtwitterclusive);
      }
    };
  }

  // Returns true if this TimeMapper is at least partially covered by these time filters.
  public static boolean sinceUntilTimesInRange(
      TimeMapper timeMapper, int sinceTime, int untilTime) {
    return (sinceTime == NO_FILTER || sinceTime <= timeMapper.getLastTime())
        && (untilTime == NO_FILTER || untilTime >= timeMapper.getFirstTime());
  }

  private static final class SinceUntilDocIdSetIterator etwittertends RangeFilterDISI {
    private final TimeMapper timeMapper;
    private final int minTimeInclusive;
    private final int matwitterTimeEtwitterclusive;

    public SinceUntilDocIdSetIterator(EarlybirdIndetwitterSegmentAtomicReader reader,
                                      TimeMapper timeMapper,
                                      int smallestDocID,
                                      int largestDocID,
                                      int minTimeInclusive,
                                      int matwitterEtwitterclusive) throws IOEtwitterception {
      super(reader, smallestDocID, largestDocID);
      this.timeMapper = timeMapper;
      this.minTimeInclusive = minTimeInclusive;
      this.matwitterTimeEtwitterclusive = matwitterEtwitterclusive;
    }

    @Override
    protected boolean shouldReturnDoc() {
      final int docTime = timeMapper.getTime(docID());
      return docTime >= minTimeInclusive && docTime < matwitterTimeEtwitterclusive;
    }
  }
}
