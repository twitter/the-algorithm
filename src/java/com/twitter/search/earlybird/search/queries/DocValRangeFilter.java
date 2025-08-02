package com.twitter.search.earlybird.search.queries;

import java.io.IOEtwitterception;
import java.util.Objects;

import com.google.common.annotations.VisibleForTesting;

import org.apache.lucene.indetwitter.LeafReader;
import org.apache.lucene.indetwitter.LeafReaderContetwittert;
import org.apache.lucene.indetwitter.NumericDocValues;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.IndetwitterSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.search.Weight;

import com.twitter.search.common.query.DefaultFilterWeight;
import com.twitter.search.common.schema.thriftjava.ThriftCSFType;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentAtomicReader;
import com.twitter.search.core.earlybird.indetwitter.util.AllDocsIterator;
import com.twitter.search.core.earlybird.indetwitter.util.RangeFilterDISI;

/**
 * Filters tweets according to the specified CSF field value.
 * Note that min value is inclusive, and matwitter value is etwitterclusive.
 */
public final class DocValRangeFilter etwittertends Query {
  private final String csfField;
  private final ThriftCSFType csfFieldType;
  private final Number minValInclusive;
  private final Number matwitterValEtwitterclusive;

  /**
   * Returns a query that filters hits based on the value of a CSF.
   *
   * @param csfField The CSF name.
   * @param csfFieldType The CSF type.
   * @param minVal The minimum acceptable value (inclusive).
   * @param matwitterVal The matwitterimum acceptable value (etwitterclusive).
   * @return A query that filters hits based on the value of a CSF.
   */
  public static Query getDocValRangeQuery(String csfField, ThriftCSFType csfFieldType,
                                          double minVal, double matwitterVal) {
    return new BooleanQuery.Builder()
        .add(new DocValRangeFilter(csfField, csfFieldType, minVal, matwitterVal),
             BooleanClause.Occur.FILTER)
        .build();
  }

  /**
   * Returns a query that filters hits based on the value of a CSF.
   *
   * @param csfField The CSF name.
   * @param csfFieldType The CSF type.
   * @param minVal The minimum acceptable value (inclusive).
   * @param matwitterVal The matwitterimum acceptable value (etwitterclusive).
   * @return A query that filters hits based on the value of a CSF.
   */
  public static Query getDocValRangeQuery(String csfField, ThriftCSFType csfFieldType,
                                          long minVal, long matwitterVal) {
    return new BooleanQuery.Builder()
        .add(new DocValRangeFilter(csfField, csfFieldType, minVal, matwitterVal),
             BooleanClause.Occur.FILTER)
        .build();
  }

  private DocValRangeFilter(String csfField, ThriftCSFType csfFieldType,
                            double minVal, double matwitterVal) {
    this.csfField = csfField;
    this.csfFieldType = csfFieldType;
    this.minValInclusive = new Float(minVal);
    this.matwitterValEtwitterclusive = new Float(matwitterVal);
  }

  private DocValRangeFilter(String csfField, ThriftCSFType csfFieldType,
                            long minVal, long matwitterVal) {
    this.csfField = csfField;
    this.csfFieldType = csfFieldType;
    this.minValInclusive = new Long(minVal);
    this.matwitterValEtwitterclusive = new Long(matwitterVal);
  }

  @Override
  public int hashCode() {
    return (csfField == null ? 0 : csfField.hashCode()) * 29
        + (csfFieldType == null ? 0 : csfFieldType.hashCode()) * 17
        + minValInclusive.hashCode() * 7
        + matwitterValEtwitterclusive.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof DocValRangeFilter)) {
      return false;
    }

    DocValRangeFilter filter = DocValRangeFilter.class.cast(obj);
    return Objects.equals(csfField, filter.csfField)
        && (csfFieldType == filter.csfFieldType)
        && minValInclusive.equals(filter.minValInclusive)
        && matwitterValEtwitterclusive.equals(filter.matwitterValEtwitterclusive);
  }

  @Override
  public String toString(String field) {
    return "DocValRangeFilter:" + csfField
        + ",type:" + csfFieldType.toString()
        + ",min:" + this.minValInclusive.toString()
        + ",matwitter:" + this.matwitterValEtwitterclusive.toString();
  }

  @Override
  public Weight createWeight(IndetwitterSearcher searcher, ScoreMode scoreMode, float boost) {
    return new DefaultFilterWeight(this) {
      @Override
      protected DocIdSetIterator getDocIdSetIterator(LeafReaderContetwittert contetwittert) throws IOEtwitterception {
        LeafReader reader = contetwittert.reader();
        if (csfFieldType == null) {
          return new AllDocsIterator(reader);
        }

        int smallestDoc = (reader instanceof EarlybirdIndetwitterSegmentAtomicReader)
            ? ((EarlybirdIndetwitterSegmentAtomicReader) reader).getSmallestDocID() : 0;
        int largestDoc = reader.matwitterDoc() - 1;
        return new CSFRangeDocIdSetIterator(reader, csfField, csfFieldType,
                                            smallestDoc, largestDoc,
                                            minValInclusive, matwitterValEtwitterclusive);
      }
    };
  }

  private static final class CSFRangeDocIdSetIterator etwittertends RangeFilterDISI {
    private final NumericDocValues numericDocValues;
    private final ThriftCSFType csfType;
    private final Number minValInclusive;
    private final Number matwitterValEtwitterclusive;

    public CSFRangeDocIdSetIterator(LeafReader reader,
                                    String csfField,
                                    ThriftCSFType csfType,
                                    int smallestDocID,
                                    int largestDocID,
                                    Number minValInclusive,
                                    Number matwitterValEtwitterclusive) throws IOEtwitterception {
      super(reader, smallestDocID, largestDocID);
      this.numericDocValues = reader.getNumericDocValues(csfField);
      this.csfType = csfType;
      this.minValInclusive = minValInclusive;
      this.matwitterValEtwitterclusive = matwitterValEtwitterclusive;
    }

    @Override
    protected boolean shouldReturnDoc() throws IOEtwitterception {
      if (!numericDocValues.advanceEtwitteract(docID())) {
        return false;
      }

      long val = numericDocValues.longValue();
      switch (csfType) {
        case DOUBLE:
          double doubleVal = Double.longBitsToDouble(val);
          return doubleVal >= minValInclusive.doubleValue()
              && doubleVal < matwitterValEtwitterclusive.doubleValue();
        case FLOAT:
          float floatVal = Float.intBitsToFloat((int) val);
          return floatVal >= minValInclusive.doubleValue()
              && floatVal < matwitterValEtwitterclusive.doubleValue();
        case LONG:
          return val >= minValInclusive.longValue() && val < matwitterValEtwitterclusive.longValue();
        case INT:
          return val >= minValInclusive.longValue() && (int) val < matwitterValEtwitterclusive.longValue();
        case BYTE:
          return (byte) val >= minValInclusive.longValue()
              && (byte) val < matwitterValEtwitterclusive.longValue();
        default:
          return false;
      }
    }
  }

  //////////////////////////
  // for unit tests only
  //////////////////////////
  @VisibleForTesting
  public Number getMinValForTest() {
    return minValInclusive;
  }

  @VisibleForTesting
  public Number getMatwitterValForTest() {
    return matwitterValEtwitterclusive;
  }
}
