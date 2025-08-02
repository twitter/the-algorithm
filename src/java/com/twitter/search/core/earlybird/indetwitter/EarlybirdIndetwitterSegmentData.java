package com.twitter.search.core.earlybird.indetwitter;

import java.io.IOEtwitterception;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.base.Preconditions;

import org.apache.lucene.indetwitter.DirectoryReader;
import org.apache.lucene.indetwitter.IndetwitterWriterConfig;
import org.apache.lucene.indetwitter.LeafReader;
import org.apache.lucene.indetwitter.LeafReaderContetwittert;
import org.apache.lucene.store.Directory;

import com.twitter.common.collections.Pair;
import com.twitter.search.common.schema.base.EarlybirdFieldType;
import com.twitter.search.common.schema.base.Schema;
import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.DataSerializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.common.util.io.flushable.Flushable;
import com.twitter.search.core.earlybird.facets.AbstractFacetCountingArray;
import com.twitter.search.core.earlybird.facets.FacetCountingArrayWriter;
import com.twitter.search.core.earlybird.facets.FacetIDMap;
import com.twitter.search.core.earlybird.facets.FacetLabelProvider;
import com.twitter.search.core.earlybird.indetwitter.column.ColumnStrideByteIndetwitter;
import com.twitter.search.core.earlybird.indetwitter.column.DocValuesManager;
import com.twitter.search.core.earlybird.indetwitter.etwittertensions.EarlybirdIndetwitterEtwittertensionsData;
import com.twitter.search.core.earlybird.indetwitter.etwittertensions.EarlybirdIndetwitterEtwittertensionsFactory;
import com.twitter.search.core.earlybird.indetwitter.inverted.DeletedDocs;
import com.twitter.search.core.earlybird.indetwitter.inverted.InvertedIndetwitter;
import com.twitter.search.core.earlybird.indetwitter.inverted.InvertedRealtimeIndetwitter;
import com.twitter.search.core.earlybird.indetwitter.inverted.OptimizedMemoryIndetwitter;
import com.twitter.search.core.earlybird.indetwitter.inverted.TermPointerEncoding;

/**
 * Base class that references data structures belonging to an Earlybird segment.
 */
public abstract class EarlybirdIndetwitterSegmentData implements Flushable {
  /**
   * This class has a map which contains a snapshot of matwitter published pointers, to distinguish the
   * documents in the skip lists that are fully indetwittered, and safe to return to searchers and those
   * that are in progress and should not be returned to searchers. See
   * "Earlybird Indetwittering Latency Design Document"
   * for rationale and design.
   *
   * It also has the smallestDocID, which determines the smallest assigned doc ID in the tweet ID
   * mapper that is safe to traverse.
   *
   * The pointer map and smallestDocID need to be updated atomically. See SEARCH-27650.
   */
  public static class SyncData {
    private final Map<InvertedIndetwitter, Integer> indetwitterPointers;
    private final int smallestDocID;

    public SyncData(Map<InvertedIndetwitter, Integer> indetwitterPointers, int smallestDocID) {
      this.indetwitterPointers = indetwitterPointers;
      this.smallestDocID = smallestDocID;
    }

    public Map<InvertedIndetwitter, Integer> getIndetwitterPointers() {
      return indetwitterPointers;
    }

    public int getSmallestDocID() {
      return smallestDocID;
    }
  }

  private volatile SyncData syncData;

  private final int matwitterSegmentSize;
  private final long timeSliceID;

  private final ConcurrentHashMap<String, QueryCacheResultForSegment> queryCacheMap =
      new ConcurrentHashMap<>();
  private final AbstractFacetCountingArray facetCountingArray;
  private final boolean isOptimized;
  private final ConcurrentHashMap<String, InvertedIndetwitter> perFieldMap;
  private final ConcurrentHashMap<String, ColumnStrideByteIndetwitter> normsMap;

  private final Map<String, FacetLabelProvider> facetLabelProviders;
  private final FacetIDMap facetIDMap;

  private final Schema schema;
  private final DocValuesManager docValuesManager;

  private final DeletedDocs deletedDocs;

  private final DocIDToTweetIDMapper docIdToTweetIdMapper;
  private final TimeMapper timeMapper;

  static LeafReader getLeafReaderFromOptimizedDirectory(Directory directory) throws IOEtwitterception {
    List<LeafReaderContetwittert> leaves = DirectoryReader.open(directory).getContetwittert().leaves();
    int leavesSize = leaves.size();
    Preconditions.checkState(1 == leavesSize,
        "Etwitterpected one leaf reader in directory %s, but found %s", directory, leavesSize);
    return leaves.get(0).reader();
  }

  /**
   * Creates a new SegmentData instance using the provided data.
   */
  public EarlybirdIndetwitterSegmentData(
      int matwitterSegmentSize,
      long timeSliceID,
      Schema schema,
      boolean isOptimized,
      int smallestDocID,
      ConcurrentHashMap<String, InvertedIndetwitter> perFieldMap,
      ConcurrentHashMap<String, ColumnStrideByteIndetwitter> normsMap,
      AbstractFacetCountingArray facetCountingArray,
      DocValuesManager docValuesManager,
      Map<String, FacetLabelProvider> facetLabelProviders,
      FacetIDMap facetIDMap,
      DeletedDocs deletedDocs,
      DocIDToTweetIDMapper docIdToTweetIdMapper,
      TimeMapper timeMapper) {
    this.matwitterSegmentSize = matwitterSegmentSize;
    this.timeSliceID = timeSliceID;
    this.schema = schema;
    this.isOptimized = isOptimized;
    this.facetCountingArray = facetCountingArray;
    this.perFieldMap = perFieldMap;
    this.syncData = new SyncData(buildIndetwitterPointers(), smallestDocID);
    this.normsMap = normsMap;
    this.docValuesManager = docValuesManager;
    this.facetLabelProviders = facetLabelProviders;
    this.facetIDMap = facetIDMap;
    this.deletedDocs = deletedDocs;
    this.docIdToTweetIdMapper = docIdToTweetIdMapper;
    this.timeMapper = timeMapper;

    Preconditions.checkNotNull(schema);
  }

  public final Schema getSchema() {
    return schema;
  }

  /**
   * Returns all {@link EarlybirdIndetwitterEtwittertensionsData} instances contained in this segment.
   * Since indetwitter etwittertensions are optional, the returned map might be null or empty.
   */
  public abstract <S etwittertends EarlybirdIndetwitterEtwittertensionsData> S getIndetwitterEtwittertensionsData();

  public DocIDToTweetIDMapper getDocIDToTweetIDMapper() {
    return docIdToTweetIdMapper;
  }

  public TimeMapper getTimeMapper() {
    return timeMapper;
  }

  public final DocValuesManager getDocValuesManager() {
    return docValuesManager;
  }

  public Map<String, FacetLabelProvider> getFacetLabelProviders() {
    return facetLabelProviders;
  }

  public FacetIDMap getFacetIDMap() {
    return facetIDMap;
  }

  /**
   * Returns the QueryCacheResult for the given filter for this segment.
   */
  public QueryCacheResultForSegment getQueryCacheResult(String queryCacheFilterName) {
    return queryCacheMap.get(queryCacheFilterName);
  }

  public long getQueryCachesCardinality() {
    return queryCacheMap.values().stream().mapToLong(q -> q.getCardinality()).sum();
  }

  /**
   * Get cache cardinality for each query cache.
   * @return
   */
  public List<Pair<String, Long>> getPerQueryCacheCardinality() {
    ArrayList<Pair<String, Long>> result = new ArrayList<>();

    queryCacheMap.forEach((cacheName, queryCacheResult) -> {
      result.add(Pair.of(cacheName, queryCacheResult.getCardinality()));
    });
    return result;
  }

  /**
   * Updates the QueryCacheResult stored for the given filter for this segment
   */
  public QueryCacheResultForSegment updateQueryCacheResult(
      String queryCacheFilterName, QueryCacheResultForSegment queryCacheResultForSegment) {
    return queryCacheMap.put(queryCacheFilterName, queryCacheResultForSegment);
  }

  /**
   * Subclasses are allowed to return null here to disable writing to a FacetCountingArray.
   */
  public FacetCountingArrayWriter createFacetCountingArrayWriter() {
    return getFacetCountingArray() != null
        ? new FacetCountingArrayWriter(getFacetCountingArray()) : null;
  }

  public int getMatwitterSegmentSize() {
    return matwitterSegmentSize;
  }

  public long getTimeSliceID() {
    return timeSliceID;
  }

  public void updateSmallestDocID(int smallestDocID) {
    // Atomic swap
    syncData = new SyncData(Collections.unmodifiableMap(buildIndetwitterPointers()), smallestDocID);
  }

  private Map<InvertedIndetwitter, Integer> buildIndetwitterPointers() {
    Map<InvertedIndetwitter, Integer> newIndetwitterPointers = new HashMap<>();
    for (InvertedIndetwitter indetwitter : perFieldMap.values()) {
      if (indetwitter.hasMatwitterPublishedPointer()) {
        newIndetwitterPointers.put(indetwitter, indetwitter.getMatwitterPublishedPointer());
      }
    }

    return newIndetwitterPointers;
  }

  public SyncData getSyncData() {
    return syncData;
  }

  public AbstractFacetCountingArray getFacetCountingArray() {
    return facetCountingArray;
  }

  public void addField(String fieldName, InvertedIndetwitter field) {
    perFieldMap.put(fieldName, field);
  }

  public Map<String, InvertedIndetwitter> getPerFieldMap() {
    return Collections.unmodifiableMap(perFieldMap);
  }

  public InvertedIndetwitter getFieldIndetwitter(String fieldName) {
    return perFieldMap.get(fieldName);
  }

  public Map<String, ColumnStrideByteIndetwitter> getNormsMap() {
    return Collections.unmodifiableMap(normsMap);
  }

  public DeletedDocs getDeletedDocs() {
    return deletedDocs;
  }

  /**
   * Returns the norms indetwitter for the given field name.
   */
  public ColumnStrideByteIndetwitter getNormIndetwitter(String fieldName) {
    return normsMap == null ? null : normsMap.get(fieldName);
  }

  /**
   * Returns the norms indetwitter for the given field name, add if not etwitterist.
   */
  public ColumnStrideByteIndetwitter createNormIndetwitter(String fieldName) {
    if (normsMap == null) {
      return null;
    }
    ColumnStrideByteIndetwitter csf = normsMap.get(fieldName);
    if (csf == null) {
      csf = new ColumnStrideByteIndetwitter(fieldName, matwitterSegmentSize);
      normsMap.put(fieldName, csf);
    }
    return csf;
  }

  /**
   * Flushes this segment to disk.
   */
  public void flushSegment(FlushInfo flushInfo, DataSerializer out) throws IOEtwitterception {
    getFlushHandler().flush(flushInfo, out);
  }

  public final boolean isOptimized() {
    return this.isOptimized;
  }

  /**
   * Returns a new atomic reader for this segment.
   */
  public EarlybirdIndetwitterSegmentAtomicReader createAtomicReader() throws IOEtwitterception {
    EarlybirdIndetwitterSegmentAtomicReader reader = doCreateAtomicReader();
    EarlybirdIndetwitterEtwittertensionsData indetwitterEtwittertension = getIndetwitterEtwittertensionsData();
    if (indetwitterEtwittertension != null) {
      indetwitterEtwittertension.setupEtwittertensions(reader);
    }
    return reader;
  }

  /**
   * Creates a new atomic reader for this segment.
   */
  protected abstract EarlybirdIndetwitterSegmentAtomicReader doCreateAtomicReader() throws IOEtwitterception;

  /**
   * Creates a new segment writer for this segment.
   */
  public abstract EarlybirdIndetwitterSegmentWriter createEarlybirdIndetwitterSegmentWriter(
      IndetwitterWriterConfig indetwitterWriterConfig) throws IOEtwitterception;

  public abstract static class AbstractSegmentDataFlushHandler
      <S etwittertends EarlybirdIndetwitterEtwittertensionsData>
      etwittertends Flushable.Handler<EarlybirdIndetwitterSegmentData> {
    protected static final String MAtwitter_SEGMENT_SIZE_PROP_NAME = "matwitterSegmentSize";
    protected static final String TIME_SLICE_ID_PROP_NAME = "time_slice_id";
    protected static final String SMALLEST_DOCID_PROP_NAME = "smallestDocID";
    protected static final String DOC_ID_MAPPER_SUBPROPS_NAME = "doc_id_mapper";
    protected static final String TIME_MAPPER_SUBPROPS_NAME = "time_mapper";
    public static final String IS_OPTIMIZED_PROP_NAME = "isOptimized";

    // Abstract methods child classes should implement:
    // 1. How to additional data structures
    protected abstract void flushAdditionalDataStructures(
        FlushInfo flushInfo, DataSerializer out, EarlybirdIndetwitterSegmentData toFlush)
            throws IOEtwitterception;

    // 2. Load additional data structures and construct SegmentData.
    // Common data structures should be passed into this method to avoid code duplication.
    // Subclasses should load additional data structures and construct a SegmentData.
    protected abstract EarlybirdIndetwitterSegmentData constructSegmentData(
        FlushInfo flushInfo,
        ConcurrentHashMap<String, InvertedIndetwitter> perFieldMap,
        int matwitterSegmentSize,
        S indetwitterEtwittertension,
        DocIDToTweetIDMapper docIdToTweetIdMapper,
        TimeMapper timeMapper,
        DataDeserializer in) throws IOEtwitterception;

    protected abstract S newIndetwitterEtwittertension();

    protected final Schema schema;
    protected final EarlybirdIndetwitterEtwittertensionsFactory indetwitterEtwittertensionsFactory;
    private final Flushable.Handler<? etwittertends DocIDToTweetIDMapper> docIdMapperFlushHandler;
    private final Flushable.Handler<? etwittertends TimeMapper> timeMapperFlushHandler;

    public AbstractSegmentDataFlushHandler(
        Schema schema,
        EarlybirdIndetwitterEtwittertensionsFactory indetwitterEtwittertensionsFactory,
        Flushable.Handler<? etwittertends DocIDToTweetIDMapper> docIdMapperFlushHandler,
        Flushable.Handler<? etwittertends TimeMapper> timeMapperFlushHandler) {
      super();
      this.schema = schema;
      this.indetwitterEtwittertensionsFactory = indetwitterEtwittertensionsFactory;
      this.docIdMapperFlushHandler = docIdMapperFlushHandler;
      this.timeMapperFlushHandler = timeMapperFlushHandler;
    }

    public AbstractSegmentDataFlushHandler(EarlybirdIndetwitterSegmentData objectToFlush) {
      super(objectToFlush);
      this.schema = objectToFlush.schema;
      this.indetwitterEtwittertensionsFactory = null; // factory only needed for loading SegmentData from disk
      this.docIdMapperFlushHandler = null; // docIdMapperFlushHandler needed only for loading data
      this.timeMapperFlushHandler = null; // timeMapperFlushHandler needed only for loading data
    }

    @Override
    protected void doFlush(FlushInfo flushInfo, DataSerializer out)
        throws IOEtwitterception {
      EarlybirdIndetwitterSegmentData segmentData = getObjectToFlush();

      Preconditions.checkState(segmentData.docIdToTweetIdMapper instanceof Flushable);
      ((Flushable) segmentData.docIdToTweetIdMapper).getFlushHandler().flush(
          flushInfo.newSubProperties(DOC_ID_MAPPER_SUBPROPS_NAME), out);

      if (segmentData.timeMapper != null) {
        segmentData.timeMapper.getFlushHandler()
            .flush(flushInfo.newSubProperties(TIME_MAPPER_SUBPROPS_NAME), out);
      }

      flushInfo.addBooleanProperty(IS_OPTIMIZED_PROP_NAME, segmentData.isOptimized());
      flushInfo.addIntProperty(MAtwitter_SEGMENT_SIZE_PROP_NAME, segmentData.getMatwitterSegmentSize());
      flushInfo.addLongProperty(TIME_SLICE_ID_PROP_NAME, segmentData.getTimeSliceID());
      flushInfo.addIntProperty(SMALLEST_DOCID_PROP_NAME,
                               segmentData.getSyncData().getSmallestDocID());

      flushIndetwitteres(flushInfo, out, segmentData);

      // Flush cluster specific data structures:
      // FacetCountingArray, TweetIDMapper, LatLonMapper, and TimeMapper
      flushAdditionalDataStructures(flushInfo, out, segmentData);
    }

    private void flushIndetwitteres(
        FlushInfo flushInfo,
        DataSerializer out,
        EarlybirdIndetwitterSegmentData segmentData) throws IOEtwitterception {
      Map<String, InvertedIndetwitter> perFieldMap = segmentData.getPerFieldMap();
      FlushInfo fieldProps = flushInfo.newSubProperties("fields");
      long sizeBeforeFlush = out.length();
      for (Map.Entry<String, InvertedIndetwitter> entry : perFieldMap.entrySet()) {
        String fieldName = entry.getKey();
        entry.getValue().getFlushHandler().flush(fieldProps.newSubProperties(fieldName), out);
      }
      fieldProps.setSizeInBytes(out.length() - sizeBeforeFlush);
    }

    @Override
    protected EarlybirdIndetwitterSegmentData doLoad(FlushInfo flushInfo, DataDeserializer in)
        throws IOEtwitterception {
      DocIDToTweetIDMapper docIdToTweetIdMapper = docIdMapperFlushHandler.load(
          flushInfo.getSubProperties(DOC_ID_MAPPER_SUBPROPS_NAME), in);

      FlushInfo timeMapperFlushInfo = flushInfo.getSubProperties(TIME_MAPPER_SUBPROPS_NAME);
      TimeMapper timeMapper =
          timeMapperFlushInfo != null ? timeMapperFlushHandler.load(timeMapperFlushInfo, in) : null;

      final int matwitterSegmentSize = flushInfo.getIntProperty(MAtwitter_SEGMENT_SIZE_PROP_NAME);
      ConcurrentHashMap<String, InvertedIndetwitter> perFieldMap = loadIndetwitteres(flushInfo, in);
      return constructSegmentData(
          flushInfo,
          perFieldMap,
          matwitterSegmentSize,
          newIndetwitterEtwittertension(),
          docIdToTweetIdMapper,
          timeMapper,
          in);
    }

    // Move this method into EarlybirdRealtimeIndetwitterSegmentData (careful,
    // we may need to increment FlushVersion because EarlybirdLuceneIndetwitterSegmentData
    // currently has the 'fields' subproperty in its FlushInfo as well)
    private ConcurrentHashMap<String, InvertedIndetwitter> loadIndetwitteres(
        FlushInfo flushInfo, DataDeserializer in) throws IOEtwitterception {
      ConcurrentHashMap<String, InvertedIndetwitter> perFieldMap = new ConcurrentHashMap<>();

      FlushInfo fieldProps = flushInfo.getSubProperties("fields");
      Iterator<String> fieldIterator = fieldProps.getKeyIterator();
      while (fieldIterator.hasNetwittert()) {
        String fieldName = fieldIterator.netwittert();
        EarlybirdFieldType fieldType = schema.getFieldInfo(fieldName).getFieldType();
        FlushInfo subProp = fieldProps.getSubProperties(fieldName);
        boolean isOptimized = subProp.getBooleanProperty(
            OptimizedMemoryIndetwitter.FlushHandler.IS_OPTIMIZED_PROP_NAME);
        final InvertedIndetwitter invertedIndetwitter;
        if (isOptimized) {
          if (!fieldType.becomesImmutable()) {
            throw new IOEtwitterception("Tried to load an optimized field that is not immutable: "
                + fieldName);
          }
          invertedIndetwitter = (new OptimizedMemoryIndetwitter.FlushHandler(fieldType)).load(subProp, in);
        } else {
          invertedIndetwitter = (new InvertedRealtimeIndetwitter.FlushHandler(
                               fieldType, TermPointerEncoding.DEFAULT_ENCODING))
              .load(subProp, in);
        }
        perFieldMap.put(fieldName, invertedIndetwitter);
      }
      return perFieldMap;
    }
  }

  public int numDocs() {
    return docIdToTweetIdMapper.getNumDocs();
  }
}
