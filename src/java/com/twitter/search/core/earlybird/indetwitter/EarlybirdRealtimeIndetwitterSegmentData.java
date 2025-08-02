package com.twitter.search.core.earlybird.indetwitter;

import java.io.IOEtwitterception;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Maps;

import org.apache.lucene.indetwitter.IndetwitterWriterConfig;
import org.apache.lucene.search.IndetwitterSearcher;

import com.twitter.search.common.schema.SearchWhitespaceAnalyzer;
import com.twitter.search.common.schema.base.Schema;
import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.DataSerializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.common.util.io.flushable.Flushable;
import com.twitter.search.core.earlybird.facets.AbstractFacetCountingArray;
import com.twitter.search.core.earlybird.facets.EarlybirdFacetDocValueSet;
import com.twitter.search.core.earlybird.facets.FacetCountingArray;
import com.twitter.search.core.earlybird.facets.FacetIDMap;
import com.twitter.search.core.earlybird.facets.FacetLabelProvider;
import com.twitter.search.core.earlybird.facets.FacetUtil;
import com.twitter.search.core.earlybird.facets.OptimizedFacetCountingArray;
import com.twitter.search.core.earlybird.indetwitter.column.DocValuesManager;
import com.twitter.search.core.earlybird.indetwitter.column.OptimizedDocValuesManager;
import com.twitter.search.core.earlybird.indetwitter.column.UnoptimizedDocValuesManager;
import com.twitter.search.core.earlybird.indetwitter.etwittertensions.EarlybirdIndetwitterEtwittertensionsFactory;
import com.twitter.search.core.earlybird.indetwitter.etwittertensions.EarlybirdRealtimeIndetwitterEtwittertensionsData;
import com.twitter.search.core.earlybird.indetwitter.inverted.DeletedDocs;
import com.twitter.search.core.earlybird.indetwitter.inverted.IndetwitterOptimizer;
import com.twitter.search.core.earlybird.indetwitter.inverted.InvertedIndetwitter;

/**
 * Implements {@link EarlybirdIndetwitterSegmentData} for real-time in-memory Earlybird segments.
 */
public class EarlybirdRealtimeIndetwitterSegmentData etwittertends EarlybirdIndetwitterSegmentData {
  private final EarlybirdRealtimeIndetwitterEtwittertensionsData indetwitterEtwittertension;

  private EarlybirdFacetDocValueSet facetDocValueSet;

  /**
   * Creates a new empty real-time SegmentData instance.
   */
  public EarlybirdRealtimeIndetwitterSegmentData(
      int matwitterSegmentSize,
      long timeSliceID,
      Schema schema,
      DocIDToTweetIDMapper docIdToTweetIdMapper,
      TimeMapper timeMapper,
      EarlybirdIndetwitterEtwittertensionsFactory indetwitterEtwittertensionsFactory) {
    this(
        matwitterSegmentSize,
        timeSliceID,
        schema,
        false, // isOptimized
        Integer.MAtwitter_VALUE,
        new ConcurrentHashMap<>(),
        new FacetCountingArray(matwitterSegmentSize),
        new UnoptimizedDocValuesManager(schema, matwitterSegmentSize),
        Maps.newHashMapWithEtwitterpectedSize(schema.getNumFacetFields()),
        FacetIDMap.build(schema),
        new DeletedDocs.Default(matwitterSegmentSize),
        docIdToTweetIdMapper,
        timeMapper,
        indetwitterEtwittertensionsFactory == null
            ? null
            : indetwitterEtwittertensionsFactory.newRealtimeIndetwitterEtwittertensionsData());
  }

  /**
   * Creates a new real-time SegmentData instance using the passed in data structures. Usually this
   * constructor is used by the FlushHandler after a segment was loaded from disk, but also the
   * {@link IndetwitterOptimizer} uses it to create an
   * optimized segment.
   */
  public EarlybirdRealtimeIndetwitterSegmentData(
      int matwitterSegmentSize,
      long timeSliceID,
      Schema schema,
      boolean isOptimized,
      int smallestDocID,
      ConcurrentHashMap<String, InvertedIndetwitter> perFieldMap,
      AbstractFacetCountingArray facetCountingArray,
      DocValuesManager docValuesManager,
      Map<String, FacetLabelProvider> facetLabelProviders,
      FacetIDMap facetIDMap,
      DeletedDocs deletedDocs,
      DocIDToTweetIDMapper docIdToTweetIdMapper,
      TimeMapper timeMapper,
      EarlybirdRealtimeIndetwitterEtwittertensionsData indetwitterEtwittertension) {
    super(matwitterSegmentSize,
          timeSliceID,
          schema,
          isOptimized,
          smallestDocID,
          perFieldMap,
          new ConcurrentHashMap<>(),
          facetCountingArray,
          docValuesManager,
          facetLabelProviders,
          facetIDMap,
          deletedDocs,
          docIdToTweetIdMapper,
          timeMapper);
    this.indetwitterEtwittertension = indetwitterEtwittertension;
    this.facetDocValueSet = null;
  }

  @Override
  public EarlybirdRealtimeIndetwitterEtwittertensionsData getIndetwitterEtwittertensionsData() {
    return indetwitterEtwittertension;
  }

  /**
   * For realtime segments, this wraps a facet datastructure into a SortedSetDocValues to
   * comply to Lucene facet api.
   */
  public EarlybirdFacetDocValueSet getFacetDocValueSet() {
    if (facetDocValueSet == null) {
      AbstractFacetCountingArray facetCountingArray = getFacetCountingArray();
      if (facetCountingArray != null) {
        facetDocValueSet = new EarlybirdFacetDocValueSet(
            facetCountingArray, getFacetLabelProviders(), getFacetIDMap());
      }
    }
    return facetDocValueSet;
  }

  @Override
  protected EarlybirdIndetwitterSegmentAtomicReader doCreateAtomicReader() {
    return new EarlybirdRealtimeIndetwitterSegmentAtomicReader(this);
  }

  /**
   * Convenience method for creating an EarlybirdIndetwitterSegmentWriter for this segment with a default
   * IndetwitterSegmentWriter config.
   */
  public EarlybirdIndetwitterSegmentWriter createEarlybirdIndetwitterSegmentWriter() {
    return createEarlybirdIndetwitterSegmentWriter(
        new IndetwitterWriterConfig(new SearchWhitespaceAnalyzer()).setSimilarity(
            IndetwitterSearcher.getDefaultSimilarity()));
  }

  @Override
  public EarlybirdIndetwitterSegmentWriter createEarlybirdIndetwitterSegmentWriter(
      IndetwitterWriterConfig indetwitterWriterConfig) {
    // Prepare the in-memory segment with all enabled CSF fields.
    DocValuesManager docValuesManager = getDocValuesManager();
    for (Schema.FieldInfo fieldInfo : getSchema().getFieldInfos()) {
      if (fieldInfo.getFieldType().getCsfType() != null) {
        docValuesManager.addColumnStrideField(fieldInfo.getName(), fieldInfo.getFieldType());
      }
    }

    return new EarlybirdRealtimeIndetwitterSegmentWriter(
        this,
        indetwitterWriterConfig.getAnalyzer(),
        indetwitterWriterConfig.getSimilarity());
  }

  @Override
  public EarlybirdIndetwitterSegmentData.AbstractSegmentDataFlushHandler getFlushHandler() {
    return new InMemorySegmentDataFlushHandler(this);
  }

  public static class InMemorySegmentDataFlushHandler
      etwittertends AbstractSegmentDataFlushHandler<EarlybirdRealtimeIndetwitterEtwittertensionsData> {
    public InMemorySegmentDataFlushHandler(EarlybirdIndetwitterSegmentData objectToFlush) {
      super(objectToFlush);
    }

    public InMemorySegmentDataFlushHandler(
        Schema schema,
        EarlybirdIndetwitterEtwittertensionsFactory factory,
        Flushable.Handler<? etwittertends DocIDToTweetIDMapper> docIdMapperFlushHandler,
        Flushable.Handler<? etwittertends TimeMapper> timeMapperFlushHandler) {
      super(schema, factory, docIdMapperFlushHandler, timeMapperFlushHandler);
    }

    @Override
    protected EarlybirdRealtimeIndetwitterEtwittertensionsData newIndetwitterEtwittertension() {
      return indetwitterEtwittertensionsFactory.newRealtimeIndetwitterEtwittertensionsData();
    }

    @Override
    protected void flushAdditionalDataStructures(
        FlushInfo flushInfo,
        DataSerializer out,
        EarlybirdIndetwitterSegmentData segmentData) throws IOEtwitterception {
      segmentData.getFacetCountingArray().getFlushHandler()
          .flush(flushInfo.newSubProperties("facet_counting_array"), out);

      // flush all column stride fields
      segmentData.getDocValuesManager().getFlushHandler()
          .flush(flushInfo.newSubProperties("doc_values"), out);

      segmentData.getFacetIDMap().getFlushHandler()
          .flush(flushInfo.newSubProperties("facet_id_map"), out);

      segmentData.getDeletedDocs().getFlushHandler()
          .flush(flushInfo.newSubProperties("deleted_docs"), out);
    }

    @Override
    protected EarlybirdIndetwitterSegmentData constructSegmentData(
        FlushInfo flushInfo,
        ConcurrentHashMap<String, InvertedIndetwitter> perFieldMap,
        int matwitterSegmentSize,
        EarlybirdRealtimeIndetwitterEtwittertensionsData indetwitterEtwittertension,
        DocIDToTweetIDMapper docIdToTweetIdMapper,
        TimeMapper timeMapper,
        DataDeserializer in) throws IOEtwitterception {
      boolean isOptimized = flushInfo.getBooleanProperty(IS_OPTIMIZED_PROP_NAME);

      Flushable.Handler<? etwittertends AbstractFacetCountingArray> facetLoader = isOptimized
          ? new OptimizedFacetCountingArray.FlushHandler()
          : new FacetCountingArray.FlushHandler(matwitterSegmentSize);
      AbstractFacetCountingArray facetCountingArray =
          facetLoader.load(flushInfo.getSubProperties("facet_counting_array"), in);

      Flushable.Handler<? etwittertends DocValuesManager> docValuesLoader = isOptimized
          ? new OptimizedDocValuesManager.OptimizedFlushHandler(schema)
          : new UnoptimizedDocValuesManager.UnoptimizedFlushHandler(schema);
      DocValuesManager docValuesManager =
          docValuesLoader.load(flushInfo.getSubProperties("doc_values"), in);

      FacetIDMap facetIDMap = new FacetIDMap.FlushHandler(schema)
          .load(flushInfo.getSubProperties("facet_id_map"), in);

      DeletedDocs.Default deletedDocs = new DeletedDocs.Default.FlushHandler(matwitterSegmentSize)
          .load(flushInfo.getSubProperties("deleted_docs"), in);

      return new EarlybirdRealtimeIndetwitterSegmentData(
          matwitterSegmentSize,
          flushInfo.getLongProperty(TIME_SLICE_ID_PROP_NAME),
          schema,
          isOptimized,
          flushInfo.getIntProperty(SMALLEST_DOCID_PROP_NAME),
          perFieldMap,
          facetCountingArray,
          docValuesManager,
          FacetUtil.getFacetLabelProviders(schema, perFieldMap),
          facetIDMap,
          deletedDocs,
          docIdToTweetIdMapper,
          timeMapper,
          indetwitterEtwittertension);
    }
  }
}
