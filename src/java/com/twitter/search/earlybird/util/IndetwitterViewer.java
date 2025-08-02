package com.twitter.search.earlybird.util;

import java.io.IOEtwitterception;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingEtwitterception;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import org.apache.lucene.indetwitter.IndetwitterOptions;
import org.apache.lucene.indetwitter.NumericDocValues;
import org.apache.lucene.indetwitter.PostingsEnum;
import org.apache.lucene.indetwitter.Terms;
import org.apache.lucene.indetwitter.TermsEnum;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.util.BytesRef;

import com.twitter.search.common.constants.thriftjava.ThriftLanguage;
import com.twitter.search.common.schema.base.Schema;
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant;
import com.twitter.search.common.schema.thriftjava.ThriftCSFType;
import com.twitter.search.common.util.analysis.IntTermAttributeImpl;
import com.twitter.search.common.util.analysis.LongTermAttributeImpl;
import com.twitter.search.common.util.analysis.SortableLongTermAttributeImpl;
import com.twitter.search.common.util.spatial.GeoUtil;
import com.twitter.search.core.earlybird.indetwitter.DocIDToTweetIDMapper;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentAtomicReader;
import com.twitter.search.core.earlybird.indetwitter.inverted.MPHTermDictionary;
import com.twitter.search.core.earlybird.indetwitter.inverted.RealtimeIndetwitterTerms;
import com.twitter.search.earlybird.indetwitter.EarlybirdSingleSegmentSearcher;

import geo.google.datamodel.GeoCoordinate;

public class IndetwitterViewer {
  /**
   * Fields whose terms are indetwittered using
   * {@link com.twitter.search.common.util.analysis.IntTermAttribute}
   */
  private static final Set<String> INT_TERM_ATTRIBUTE_FIELDS = ImmutableSet.of(
      EarlybirdFieldConstant.CREATED_AT_FIELD.getFieldName(),
      EarlybirdFieldConstant.LINK_CATEGORY_FIELD.getFieldName(),
      EarlybirdFieldConstant
          .NORMALIZED_FAVORITE_COUNT_GREATER_THAN_OR_EQUAL_TO_FIELD.getFieldName(),
      EarlybirdFieldConstant
          .NORMALIZED_REPLY_COUNT_GREATER_THAN_OR_EQUAL_TO_FIELD.getFieldName(),
      EarlybirdFieldConstant
          .NORMALIZED_RETWEET_COUNT_GREATER_THAN_OR_EQUAL_TO_FIELD.getFieldName(),
      EarlybirdFieldConstant.COMPOSER_SOURCE.getFieldName());

  /**
   * Fields whose terms are indetwittered using
   * {@link com.twitter.search.common.util.analysis.LongTermAttribute}
   */
  private static final Set<String> LONG_TERM_ATTRIBUTE_FIELDS = ImmutableSet.of(
      EarlybirdFieldConstant.CONVERSATION_ID_FIELD.getFieldName(),
      EarlybirdFieldConstant.LIKED_BY_USER_ID_FIELD.getFieldName(),
      EarlybirdFieldConstant.QUOTED_TWEET_ID_FIELD.getFieldName(),
      EarlybirdFieldConstant.QUOTED_USER_ID_FIELD.getFieldName(),
      EarlybirdFieldConstant.REPLIED_TO_BY_USER_ID.getFieldName(),
      EarlybirdFieldConstant.RETWEETED_BY_USER_ID.getFieldName(),
      EarlybirdFieldConstant.DIRECTED_AT_USER_ID_FIELD.getFieldName(),
      EarlybirdFieldConstant.FROM_USER_ID_FIELD.getFieldName(),
      EarlybirdFieldConstant.IN_REPLY_TO_TWEET_ID_FIELD.getFieldName(),
      EarlybirdFieldConstant.IN_REPLY_TO_USER_ID_FIELD.getFieldName(),
      EarlybirdFieldConstant.RETWEET_SOURCE_TWEET_ID_FIELD.getFieldName(),
      EarlybirdFieldConstant.RETWEET_SOURCE_USER_ID_FIELD.getFieldName());

  /**
   * Fields whose terms indetwitter using SORTED
   * {@link com.twitter.search.common.util.analysis.LongTermAttribute}
   */
  private static final Set<String> SORTED_LONG_TERM_ATTRIBUTE_FIELDS =
      ImmutableSet.of(EarlybirdFieldConstant.ID_FIELD.getFieldName());

  private final EarlybirdSingleSegmentSearcher searcher;
  private final EarlybirdIndetwitterSegmentAtomicReader twitterReader;

  public long getTimeSliceId() {
    return searcher.getTimeSliceID();
  }

  public static class Options {
    private boolean dumpHetwitterTerms = false;
    private String charset;
    private double[] histogramBuckets;
    private boolean termLengthHistogram;

    public Options setDumpHetwitterTerms(boolean dumpHetwitterTermsParam) {
      this.dumpHetwitterTerms = dumpHetwitterTermsParam;
      return this;
    }

    public Options setCharset(String charsetParam) {
      this.charset = charsetParam;
      return this;
    }

    public Options setHistogramBuckets(double[] histogramBucketsParam) {
      this.histogramBuckets = histogramBucketsParam;
      return this;
    }

    public Options setTermLengthHistogram(boolean termLengthHistogramParam) {
      this.termLengthHistogram = termLengthHistogramParam;
      return this;
    }
  }

  /**
   * Data Transfer Object for Terms, encapsulates the "json" serialization
   * while maintaining streaming mode
   */
  private static class TermDto {

    private final String field;
    private final String term;
    private final String docFreq;
    private final String percent;
    private final PostingsEnum docsEnum;
    private final TermsEnum termsEnum;
    private final Integer matwitterDocs;

    public TermDto(String field, String term, String docFreq, String percent,
                   PostingsEnum docsEnum, TermsEnum termsEnum, Integer matwitterDocs) {
      this.field = field;
      this.term = term;
      this.docFreq = docFreq;
      this.percent = percent;
      this.docsEnum = docsEnum;
      this.termsEnum = termsEnum;
      this.matwitterDocs = matwitterDocs;
    }

    public void write(ViewerWriter writer,
                      EarlybirdIndetwitterSegmentAtomicReader twitterReader) throws IOEtwitterception {
      writer.beginObject();
      writer.name("field").value(field);
      writer.name("term").value(term);
      writer.name("docFreq").value(docFreq);
      writer.name("percent").value(percent);
      if (docsEnum != null) {
        appendFrequencyAndPositions(writer, field, docsEnum, twitterReader);
      }
      if (matwitterDocs != null) {
        appendDocs(writer, termsEnum, matwitterDocs, twitterReader);
      }
      writer.endObject();
    }
  }

  /**
   * Data Transfer Object for Terms, encapsulates the "json" serialization
   * while maintaining streaming mode
   */
  private static class StatsDto {

    private final String field;
    private final String numTerms;
    private final String terms;


    public StatsDto(String field, String numTerms, String terms) {
      this.field = field;
      this.numTerms = numTerms;
      this.terms = terms;
    }

    public void write(ViewerWriter writer) throws IOEtwitterception {
      writer.beginObject();

      writer.name("field").value(field);
      writer.name("numTerms").value(numTerms);
      writer.name("terms").value(terms);

      writer.endObject();
    }
  }

  public IndetwitterViewer(EarlybirdSingleSegmentSearcher searcher) {
    this.searcher = searcher;
    this.twitterReader = searcher.getTwitterIndetwitterReader();
  }

  private boolean shouldSeekEtwitteract(Terms terms, TermsEnum termsEnum) {
    return terms instanceof RealtimeIndetwitterTerms
           || termsEnum instanceof MPHTermDictionary.MPHTermsEnum;
  }

  /**
   * Dumps all terms for a given tweet id.
   * @param writer writer being used
   * @param tweetId the tweet id to use
   */
  public void dumpTweetDataByTweetId(ViewerWriter writer, long tweetId, Options options)
      throws IOEtwitterception {
    int docId = twitterReader.getSegmentData().getDocIDToTweetIDMapper().getDocID(tweetId);
    dumpTweetDataByDocId(writer, docId, options);
  }

  /**
   * Dumps all terms for a given doc id.
   * @param writer writer being used
   * @param docId the document id to use.
   */
  public void dumpTweetDataByDocId(ViewerWriter writer, int docId, Options options)
      throws IOEtwitterception {
    writer.beginObject();

    printHeader(writer);
    long tweetID = twitterReader.getSegmentData().getDocIDToTweetIDMapper().getTweetID(docId);
    if (docId < twitterReader.matwitterDoc() && tweetID >= 0) {
      writer.name("docId").value(Integer.toString(docId));
      writer.name("tweetId").value(Long.toString(tweetID));
      dumpIndetwitteredFields(writer, docId, options);
      dumpCsfFields(writer, docId);
    }
    writer.endObject();
  }

  /**
   * Dumps all tweet IDs in the current segment to the given file.
   */
  public void dumpTweetIds(ViewerWriter writer, String logFile, PrintWriter logWriter)
      throws IOEtwitterception {
    writeTweetIdsToLogFile(logWriter);

    writer.beginObject();
    writer.name(Long.toString(searcher.getTimeSliceID())).value(logFile);
    writer.endObject();
  }

  private void writeTweetIdsToLogFile(PrintWriter logWriter) {
    DocIDToTweetIDMapper mapper = twitterReader.getSegmentData().getDocIDToTweetIDMapper();
    int docId = Integer.MIN_VALUE;
    while ((docId = mapper.getNetwittertDocID(docId)) != DocIDToTweetIDMapper.ID_NOT_FOUND) {
      long tweetId = mapper.getTweetID(docId);

      // Ensure tweet ID is valid and non-deleted
      if ((tweetId > 0) && !twitterReader.getDeletesView().isDeleted(docId)) {
        logWriter.println(tweetId);
      }
    }
  }

  private void dumpIndetwitteredFields(ViewerWriter writer, int docId,
                                 Options options) throws IOEtwitterception {
    writer.name("indetwitteredFields");
    writer.beginArray();
    writer.newline();
    for (String field : sortedFields()) {
      dumpTweetData(writer, field, docId, options);
    }
    writer.endArray();
    writer.newline();
  }

  private void dumpCsfFields(ViewerWriter writer, int docId) throws IOEtwitterception {
    writer.name("csfFields");
    writer.beginArray();
    writer.newline();
    dumpCSFData(writer, docId);

    writer.endArray();
  }

  /**
   * Dumps all CSF values for a given doc id.
   * @param writer writer being used
   * @param docId the document id to use.
   */
  private void dumpCSFData(ViewerWriter writer, int docId) throws IOEtwitterception {
    Schema tweetSchema = twitterReader.getSchema();

    // Sort the FieldInfo objects to generate fitwittered order to make testing easier
    List<Schema.FieldInfo> sortedFieldInfos = new ArrayList<>(tweetSchema.getFieldInfos());
    sortedFieldInfos.sort(Comparator.comparing(Schema.FieldInfo::getFieldId));

    for (Schema.FieldInfo fieldInfo: sortedFieldInfos) {
      String csfFieldInfoName = fieldInfo.getName();
      ThriftCSFType csfType = tweetSchema.getCSFFieldType(csfFieldInfoName);
      NumericDocValues csfDocValues = twitterReader.getNumericDocValues(csfFieldInfoName);
      // If twitterReader.getNumericDocValues(value.getName()) == null,
      // means no NumericDocValue was indetwittered for the field so ignore
      if (csfType != null && csfDocValues != null && csfDocValues.advanceEtwitteract(docId)) {
        long csfValue = csfDocValues.longValue();
        writer.beginObject();
        writer.name("field").value(formatField(csfFieldInfoName));
        writer.name("value");
        if (csfFieldInfoName.equals(EarlybirdFieldConstant.LAT_LON_CSF_FIELD.getFieldName())) {
          writer.value(latlongDecode(csfValue));
        } else if (csfFieldInfoName.equals(EarlybirdFieldConstant.LANGUAGE.getFieldName())) {
          writer.value(languageDecode(csfValue));
        } else if (csfFieldInfoName.equals(EarlybirdFieldConstant.CARD_LANG_CSF.getFieldName())) {
          writer.value(languageDecode(csfValue));
        } else {
          writer.value(Long.toString(csfValue));
        }
        writer.endObject();
        writer.newline();
      }
    }
  }

  /**
   * Decipher long value gotten, put into format (lat, lon)
   * Decode the stored long value by creating a geocode
   */
  private String latlongDecode(long csfValue) {
    StringBuilder sb = new StringBuilder();
    GeoCoordinate geoCoordinate = new GeoCoordinate();
    if (GeoUtil.decodeLatLonFromInt64(csfValue, geoCoordinate)) {
      sb.append(geoCoordinate.getLatitude()).append(", ").append(geoCoordinate.getLongitude());
    } else {
      sb.append(csfValue).append(" (Value Unset or Invalid Coordinate)");
    }
    return sb.toString();
  }

  /**
   * Decipher long value gotten into string of tweet's language
   */
  private String languageDecode(long csfValue) {
    StringBuilder sb = new StringBuilder();
    ThriftLanguage languageType = ThriftLanguage.findByValue((int) csfValue);
    sb.append(csfValue).append(" (").append(languageType).append(")");
    return sb.toString();
  }

  private void dumpTweetData(ViewerWriter writer,
                             String field,
                             int docId,
                             Options options) throws IOEtwitterception {

    Terms terms = twitterReader.terms(field);
    if (terms != null) {
      TermsEnum termsEnum = terms.iterator();
      if (shouldSeekEtwitteract(terms, termsEnum)) {
        long numTerms = terms.size();
        for (int i = 0; i < numTerms; i++) {
          termsEnum.seekEtwitteract(i);
          dumpTweetDataTerm(writer, field, termsEnum, docId, options);
        }
      } else {
        while (termsEnum.netwittert() != null) {
          dumpTweetDataTerm(writer, field, termsEnum, docId, options);
        }
      }
    }
  }

  private void dumpTweetDataTerm(ViewerWriter writer, String field, TermsEnum termsEnum,
                                 int docId, Options options) throws IOEtwitterception {
    PostingsEnum docsAndPositionsEnum = termsEnum.postings(null, PostingsEnum.ALL);
    if (docsAndPositionsEnum != null && docsAndPositionsEnum.advance(docId) == docId) {
      printTerm(writer, field, termsEnum, docsAndPositionsEnum, null, options);
    }
  }

  /**
   * Prints the histogram for the currently viewed indetwitter.
   * @param writer current viewerWriter
   * @param field if null, will use all fields
   * @param options options for dumping out tetwittert
   */
  public void dumpHistogram(ViewerWriter writer, String field, Options options) throws IOEtwitterception {
    writer.beginObject();
    printHeader(writer);
    writer.name("histogram");
    writer.beginArray();
    writer.newline();
    if (field == null) {
      for (String field2 : sortedFields()) {
        dumpFieldHistogram(writer, field2, options);
      }
    } else {
      dumpFieldHistogram(writer, field, options);
    }
    writer.endArray();
    writer.endObject();
  }

  private void dumpFieldHistogram(ViewerWriter writer, String field, Options options)
      throws IOEtwitterception {
    Histogram histo = new Histogram(options.histogramBuckets);

    Terms terms = twitterReader.terms(field);
    if (terms != null) {
      TermsEnum termsEnum = terms.iterator();
      if (shouldSeekEtwitteract(terms, termsEnum)) {
        long numTerms = terms.size();
        for (int i = 0; i < numTerms; i++) {
          termsEnum.seekEtwitteract(i);
          countHistogram(options, histo, termsEnum);
        }
      } else {
        while (termsEnum.netwittert() != null) {
          countHistogram(options, histo, termsEnum);
        }
      }
      printHistogram(writer, field, options, histo);
    }
  }

  private void printHistogram(ViewerWriter writer, String field, Options options,
                              Histogram histo) throws IOEtwitterception {

    String bucket = options.termLengthHistogram ? "termLength" : "df";
    for (Histogram.Entry histEntry : histo.entries()) {
      String format =
          String.format(Locale.US,
              "field: %s %sBucket: %11s count: %10d "
                  + "percent: %6.2f%% cumulative: %6.2f%% totalCount: %10d"
                  + " sum: %15d percent: %6.2f%% cumulative: %6.2f%% totalSum: %15d",
              formatField(field),
              bucket,
              histEntry.getBucketName(),
              histEntry.getCount(),
              histEntry.getCountPercent() * 100.0,
              histEntry.getCountCumulative() * 100.0,
              histo.getTotalCount(),
              histEntry.getSum(),
              histEntry.getSumPercent() * 100.0,
              histEntry.getSumCumulative() * 100.0,
              histo.getTotalSum()
          );
      writer.value(format);
      writer.newline();
    }
  }

  private void countHistogram(Options options, Histogram histo, TermsEnum termsEnum)
          throws IOEtwitterception {
    if (options.termLengthHistogram) {
      final BytesRef bytesRef = termsEnum.term();
      histo.addItem(bytesRef.length);
    } else {
      histo.addItem(termsEnum.docFreq());
    }
  }


  /**
   * Prints terms and optionally documents for the currently viewed indetwitter.
   * @param writer writer being used
   * @param field if null, will use all fields
   * @param term if null will use all terms
   * @param matwitterTerms will print at most this many terms per field. If null will print 0 terms.
   * @param matwitterDocs will print at most this many documents, If null, will not print docs.
   * @param options options for dumping out tetwittert
   */
  public void dumpData(ViewerWriter writer, String field, String term, Integer matwitterTerms,
        Integer matwitterDocs, Options options, boolean shouldSeekToTerm) throws IOEtwitterception {

    writer.beginObject();
    printHeader(writer);

    writer.name("terms");
    writer.beginArray();
    writer.newline();
    dumpDataInternal(writer, field, term, matwitterTerms, matwitterDocs, options, shouldSeekToTerm);
    writer.endArray();
    writer.endObject();
  }

  private void dumpDataInternal(ViewerWriter writer, String field, String term, Integer matwitterTerms,
      Integer matwitterDocs, Options options, boolean shouldSeekToTerm) throws IOEtwitterception {

    if (field == null) {
      dumpDataForAllFields(writer, term, matwitterTerms, matwitterDocs, options);
      return;
    }
    if (term == null) {
      dumpDataForAllTerms(writer, field, matwitterTerms, matwitterDocs, options);
      return;
    }
    Terms terms = twitterReader.terms(field);
    if (terms != null) {
      TermsEnum termsEnum = terms.iterator();
      TermsEnum.SeekStatus status = termsEnum.seekCeil(new BytesRef(term));
      if (status == TermsEnum.SeekStatus.FOUND) {
        printTerm(writer, field, termsEnum, null, matwitterDocs, options);
      }
      if (shouldSeekToTerm) {
        dumpTermsAfterSeek(writer, field, terms, matwitterTerms, matwitterDocs, options, termsEnum, status);
      }
    }
  }

  /**
   * if term (cursor) is found for an indetwittered segment - dump the netwittert termsLeft words
   * starting from the current position in the enum.  For an indetwittered segment,
   * seekCeil will place the enum at the word or the netwittert "ceiling" term.  For
   * a realtime indetwitter, if the word is not found we do not paginate anything
   * We also only paginate if the TermsEnum is not at the end.
   */
  private void dumpTermsAfterSeek(ViewerWriter writer, String field, Terms terms, Integer matwitterTerms,
      Integer matwitterDocs, Options options, TermsEnum termsEnum, TermsEnum.SeekStatus status)
      throws IOEtwitterception {
    if (status != TermsEnum.SeekStatus.END) {
      // for realtime, to not repeat the found word
      if (shouldSeekEtwitteract(terms, termsEnum)) {
        termsEnum.netwittert();
      }
      if (status != TermsEnum.SeekStatus.FOUND) {
        // if not found, print out curr term before calling netwittert()
        printTerm(writer, field, termsEnum, null, matwitterDocs, options);
      }
      for (int termsLeft = matwitterTerms - 1; termsLeft > 0 && termsEnum.netwittert() != null; termsLeft--) {
        printTerm(writer, field, termsEnum, null, matwitterDocs, options);
      }
    }
  }

  private void dumpDataForAllFields(ViewerWriter writer, String term, Integer matwitterTerms,
                                    Integer matwitterDocs, Options options) throws IOEtwitterception {
    for (String field : sortedFields()) {
      dumpDataInternal(writer, field, term, matwitterTerms, matwitterDocs, options, false);
    }
  }

  private List<String> sortedFields() {
    // Tweet facets are added to a special $facets field, which is not part of the schema.
    // We include it here, because seeing the facets for a tweet is generally useful.
    List<String> fields = Lists.newArrayList("$facets");
    for (Schema.FieldInfo fieldInfo : twitterReader.getSchema().getFieldInfos()) {
      if (fieldInfo.getFieldType().indetwitterOptions() != IndetwitterOptions.NONE) {
        fields.add(fieldInfo.getName());
      }
    }
    Collections.sort(fields);
    return fields;
  }

  private void dumpDataForAllTerms(ViewerWriter writer,
                                   String field,
                                   Integer matwitterTerms,
                                   Integer matwitterDocs,
                                   Options options) throws IOEtwitterception {
    Terms terms = twitterReader.terms(field);
    if (terms != null) {
      TermsEnum termsEnum = terms.iterator();
      if (shouldSeekEtwitteract(terms, termsEnum)) {
        long numTerms = terms.size();
        long termToDump = matwitterTerms == null ? 0 : Math.min(numTerms, matwitterTerms);
        for (int i = 0; i < termToDump; i++) {
          termsEnum.seekEtwitteract(i);
          printTerm(writer, field, termsEnum, null, matwitterDocs, options);
        }
      } else {
        int matwitter = matwitterTerms == null ? 0 : matwitterTerms;
        while (matwitter > 0 && termsEnum.netwittert() != null) {
          printTerm(writer, field, termsEnum, null, matwitterDocs, options);
          matwitter--;
        }
      }
    }
  }

  private String termToString(String field, BytesRef bytesTerm, Options options)
      throws UnsupportedEncodingEtwitterception {
    if (INT_TERM_ATTRIBUTE_FIELDS.contains(field)) {
      return Integer.toString(IntTermAttributeImpl.copyBytesRefToInt(bytesTerm));
    } else if (LONG_TERM_ATTRIBUTE_FIELDS.contains(field)) {
      return Long.toString(LongTermAttributeImpl.copyBytesRefToLong(bytesTerm));
    } else if (SORTED_LONG_TERM_ATTRIBUTE_FIELDS.contains(field)) {
      return Long.toString(SortableLongTermAttributeImpl.copyBytesRefToLong(bytesTerm));
    } else {
      if (options != null && options.charset != null && !options.charset.isEmpty()) {
        return new String(bytesTerm.bytes, bytesTerm.offset, bytesTerm.length, options.charset);
      } else {
        return bytesTerm.utf8ToString();
      }
    }
  }

  private void printTerm(ViewerWriter writer, String field, TermsEnum termsEnum,
                         PostingsEnum docsEnum, Integer matwitterDocs, Options options)
      throws IOEtwitterception {
    final BytesRef bytesRef = termsEnum.term();
    StringBuilder termToString = new StringBuilder();
    termToString.append(termToString(field, bytesRef, options));
    if (options != null && options.dumpHetwitterTerms) {
      termToString.append(" ").append(bytesRef.toString());
    }
    final int df = termsEnum.docFreq();
    double dfPercent = ((double) df / this.twitterReader.numDocs()) * 100.0;
    TermDto termDto = new TermDto(field, termToString.toString(), Integer.toString(df),
                                   String.format(Locale.US, "%.2f%%", dfPercent),
                                   docsEnum, termsEnum, matwitterDocs);
    termDto.write(writer, twitterReader);
    writer.newline();
  }

  private static void appendFrequencyAndPositions(ViewerWriter writer, String field,
      PostingsEnum docsEnum, EarlybirdIndetwitterSegmentAtomicReader twitterReader) throws IOEtwitterception {
    final int frequency = docsEnum.freq();
    writer.name("freq").value(Integer.toString(frequency));

    Schema schema = twitterReader.getSchema();
    Schema.FieldInfo fieldInfo = schema.getFieldInfo(field);

    if (fieldInfo != null
            && (fieldInfo.getFieldType().indetwitterOptions() == IndetwitterOptions.DOCS_AND_FREQS_AND_POSITIONS
            || fieldInfo.getFieldType().indetwitterOptions()
                == IndetwitterOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS)) {
      appendPositions(writer, docsEnum);
    }
  }

  private static void appendPositions(ViewerWriter writer, PostingsEnum docsAndPositionsEnum)
      throws IOEtwitterception {
    writer.name("positions");

    writer.beginArray();
    final int frequency = docsAndPositionsEnum.freq();
    for (int i = 0; i < frequency; i++) {
      int position = docsAndPositionsEnum.netwittertPosition();
      writer.value(Integer.toString(position));
    }
    writer.endArray();
  }

  private static void appendDocs(ViewerWriter writer, TermsEnum termsEnum, int matwitterDocs,
                                 EarlybirdIndetwitterSegmentAtomicReader twitterReader)
      throws IOEtwitterception {
    writer.name("docIds");

    writer.beginArray();

    PostingsEnum docs = termsEnum.postings(null, 0);
    int docsReturned = 0;
    int docId;
    boolean endedEarly = false;
    DocIDToTweetIDMapper mapper = twitterReader.getSegmentData().getDocIDToTweetIDMapper();
    while ((docId = docs.netwittertDoc()) != DocIdSetIterator.NO_MORE_DOCS) {
      if (docsReturned < matwitterDocs) {
        docsReturned++;
        long tweetID = mapper.getTweetID(docId);

        writer.beginObject();
        writer.name("docId").value(Long.toString(docId));
        writer.name("tweetId").value(Long.toString(tweetID));
        writer.endObject();
      } else {
        endedEarly = true;
        break;
      }
    }
    if (endedEarly) {
      writer.beginObject();
      writer.name("status").value("ended early");
      writer.endObject();
    }
    writer.endArray();
  }

  /**
   * Prints generic stats for all fields in the currently viewed indetwitter.
   */
  public void dumpStats(ViewerWriter writer) throws IOEtwitterception {
    writer.beginObject();

    printHeader(writer);
    // stats section
    writer.name("stats");
    writer.beginArray();
    writer.newline();
    for (String field : sortedFields()) {
      Terms terms = twitterReader.terms(field);
      if (terms != null) {
        printStats(writer, field, terms);
      }
    }
    writer.endArray();
    writer.endObject();
  }

  private void printStats(ViewerWriter writer, String field, Terms terms) throws IOEtwitterception {
    StatsDto statsDto = new StatsDto(
        field, String.valueOf(terms.size()), terms.getClass().getCanonicalName());
    statsDto.write(writer);
    writer.newline();
  }

  private void printHeader(ViewerWriter writer) throws IOEtwitterception {
    writer.name("timeSliceId").value(Long.toString(this.searcher.getTimeSliceID()));
    writer.name("matwitterDocNumber").value(Integer.toString(this.twitterReader.matwitterDoc()));
    writer.newline();
  }

  private static String formatField(String field) {
    return String.format("%20s", field);
  }

  /**
   * Dumps out the schema of the current segment.
   * @param writer to be used for printing
   */
  public void dumpSchema(ViewerWriter writer) throws IOEtwitterception {
    writer.beginObject();
    printHeader(writer);
    writer.name("schemaFields");
    writer.beginArray();
    writer.newline();
    Schema schema = this.twitterReader.getSchema();
    // The fields in the schema are not sorted. Sort them so that the output is deterministic
    Set<String> fieldNameSet = new TreeSet<>();
    for (Schema.FieldInfo fieldInfo: schema.getFieldInfos()) {
      fieldNameSet.add(fieldInfo.getName());
    }
    for (String fieldName : fieldNameSet) {
      writer.value(fieldName);
      writer.newline();
    }
    writer.endArray();
    writer.endObject();
  }

  /**
   * Dumps out the indetwittered fields inside the current segment.
   * Mainly used to help the front end populate the fields.
   * @param writer writer to be used for printing
   */
  public void dumpFields(ViewerWriter writer) throws IOEtwitterception {
    writer.beginObject();
    printHeader(writer);
    writer.name("fields");
    writer.beginArray();
    writer.newline();
    for (String field : sortedFields()) {
      writer.value(field);
      writer.newline();
    }
    writer.endArray();
    writer.endObject();
  }

  /**
   * Dumps out the mapping of the tweet/tweetId to
   * a docId as well as segment/timeslide pair.
   * @param writer writer to be used for writing
   * @param tweetId tweetId that is input by user
   */
  public void dumpTweetIdToDocIdMapping(ViewerWriter writer, long tweetId) throws IOEtwitterception {
    writer.beginObject();
    printHeader(writer);
    writer.name("tweetId").value(Long.toString(tweetId));
    int docId = twitterReader.getSegmentData().getDocIDToTweetIDMapper().getDocID(tweetId);

    writer.name("docId").value(Integer.toString(docId));
    writer.endObject();
    writer.newline();
  }

  /**
   * Dumps out the mapping of the docId to
   * tweetId and timeslice/segmentId pairs.
   * @param writer writer to be used for writing
   * @param docid docId that is input by user
   */
  public void dumpDocIdToTweetIdMapping(ViewerWriter writer, int docid) throws IOEtwitterception {
    writer.beginObject();
    printHeader(writer);
    long tweetId = twitterReader.getSegmentData().getDocIDToTweetIDMapper().getTweetID(docid);

    writer.name("tweetId");
    if (tweetId >= 0) {
      writer.value(Long.toString(tweetId));
    } else {
      writer.value("Does not etwitterist in segment");
    }
    writer.name("docid").value(Integer.toString(docid));
    writer.endObject();
  }

  /**
   * Print a response indicating that the given tweet id is not found in the indetwitter.
   *
   * Note that this method does not actually need the underlying indetwitter, and hence is setup as
   * a util function.
   */
  public static void writeTweetDoesNotEtwitteristResponse(ViewerWriter writer, long tweetId)
      throws IOEtwitterception {
    writer.beginObject();
    writer.name("tweetId");
    writer.value(Long.toString(tweetId));
    writer.name("docId");
    writer.value("does not etwitterist on this earlybird.");
    writer.endObject();
  }
}
