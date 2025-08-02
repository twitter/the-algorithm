package com.twitter.search.core.earlybird.indetwitter;

import java.io.IOEtwitterception;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.lucene.indetwitter.IndetwitterWriterConfig;
import org.apache.lucene.indetwitter.LeafReader;
import org.apache.lucene.store.Directory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.common.schema.base.Schema;
import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.DataSerializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.common.util.io.flushable.Flushable;
import com.twitter.search.core.earlybird.facets.AbstractFacetCountingArray;
import com.twitter.search.core.earlybird.facets.FacetCountingArrayWriter;
import com.twitter.search.core.earlybird.indetwitter.column.ColumnStrideFieldIndetwitter;
import com.twitter.search.core.earlybird.indetwitter.column.DocValuesManager;
import com.twitter.search.core.earlybird.indetwitter.column.OptimizedDocValuesManager;
import com.twitter.search.core.earlybird.indetwitter.etwittertensions.EarlybirdIndetwitterEtwittertensionsData;
import com.twitter.search.core.earlybird.indetwitter.etwittertensions.EarlybirdIndetwitterEtwittertensionsFactory;
import com.twitter.search.core.earlybird.indetwitter.inverted.DeletedDocs;
import com.twitter.search.core.earlybird.indetwitter.inverted.InvertedIndetwitter;

/**
 * Implements {@link EarlybirdIndetwitterSegmentData} for Lucene-based on-disk Earlybird segments.
 */
public final class EarlybirdLuceneIndetwitterSegmentData etwittertends EarlybirdIndetwitterSegmentData {
  private static final Logger LOG = LoggerFactory.getLogger(EarlybirdLuceneIndetwitterSegmentData.class);

  private final Directory directory;
  private final EarlybirdIndetwitterEtwittertensionsData indetwitterEtwittertension;

  /**
   * Creates a new Lucene-based SegmentData instance from a lucene directory.
   */
  public EarlybirdLuceneIndetwitterSegmentData(
      Directory directory,
      int matwitterSegmentSize,
      long timeSliceID,
      Schema schema,
      DocIDToTweetIDMapper docIdToTweetIdMapper,
      TimeMapper timeMapper,
      EarlybirdIndetwitterEtwittertensionsFactory indetwitterEtwittertensionsFactory) {
    this(
        directory,
        matwitterSegmentSize,
        timeSliceID,
        schema,
        false, // isOptimized
        0, // smallestDocId
        new ConcurrentHashMap<>(),
        AbstractFacetCountingArray.EMPTY_ARRAY,
        new OptimizedDocValuesManager(schema, matwitterSegmentSize),
        docIdToTweetIdMapper,
        timeMapper,
        indetwitterEtwittertensionsFactory == null
            ? null : indetwitterEtwittertensionsFactory.newLuceneIndetwitterEtwittertensionsData());
  }

  public EarlybirdLuceneIndetwitterSegmentData(
      Directory directory,
      int matwitterSegmentSize,
      long timeSliceID,
      Schema schema,
      boolean isOptimized,
      int smallestDocID,
      ConcurrentHashMap<String, InvertedIndetwitter> perFieldMap,
      AbstractFacetCountingArray facetCountingArray,
      DocValuesManager docValuesManager,
      DocIDToTweetIDMapper docIdToTweetIdMapper,
      TimeMapper timeMapper,
      EarlybirdIndetwitterEtwittertensionsData indetwitterEtwittertension) {
    super(matwitterSegmentSize,
          timeSliceID,
          schema,
          isOptimized,
          smallestDocID,
          perFieldMap,
          new ConcurrentHashMap<>(),
          facetCountingArray,
          docValuesManager,
          null, // facetLabelProviders
          null, // facetIDMap
          DeletedDocs.NO_DELETES,
          docIdToTweetIdMapper,
          timeMapper);
    this.directory = directory;
    this.indetwitterEtwittertension = indetwitterEtwittertension;
  }

  public Directory getLuceneDirectory() {
    return directory;
  }

  @Override
  public EarlybirdIndetwitterEtwittertensionsData getIndetwitterEtwittertensionsData() {
    return indetwitterEtwittertension;
  }

  @Override
  public FacetCountingArrayWriter createFacetCountingArrayWriter() {
    return null;
  }

  @Override
  protected EarlybirdIndetwitterSegmentAtomicReader doCreateAtomicReader() throws IOEtwitterception {
    // EarlybirdSegment creates one single EarlybirdIndetwitterSegmentAtomicReader instance per segment
    // and caches it, and the cached instance is recreated only when the segment's data changes.
    // This is why this is a good place to reload all CSFs that should be loaded in RAM. Also, it's
    // easier and less error-prone to do it here, than trying to track down all places that mutate
    // the segment data and do it there.
    LeafReader reader = getLeafReaderFromOptimizedDirectory(directory);
    for (Schema.FieldInfo fieldInfo : getSchema().getFieldInfos()) {
      // Load CSF into RAM based on configurations in the schema.
      if (fieldInfo.getFieldType().getCsfType() != null
          && fieldInfo.getFieldType().isCsfLoadIntoRam()) {
        if (reader.getNumericDocValues(fieldInfo.getName()) != null) {
          ColumnStrideFieldIndetwitter indetwitter = getDocValuesManager().addColumnStrideField(
              fieldInfo.getName(), fieldInfo.getFieldType());
          indetwitter.load(reader, fieldInfo.getName());
        } else {
          LOG.warn("Field {} does not have NumericDocValues.", fieldInfo.getName());
        }
      }
    }

    return new EarlybirdLuceneIndetwitterSegmentAtomicReader(this, directory);
  }

  @Override
  public EarlybirdIndetwitterSegmentWriter createEarlybirdIndetwitterSegmentWriter(
      IndetwitterWriterConfig indetwitterWriterConfig) throws IOEtwitterception {
    return new EarlybirdLuceneIndetwitterSegmentWriter(this, indetwitterWriterConfig);
  }

  @Override
  public EarlybirdIndetwitterSegmentData.AbstractSegmentDataFlushHandler getFlushHandler() {
    return new OnDiskSegmentDataFlushHandler(this);
  }

  public static class OnDiskSegmentDataFlushHandler
      etwittertends AbstractSegmentDataFlushHandler<EarlybirdIndetwitterEtwittertensionsData> {
    private final Directory directory;

    public OnDiskSegmentDataFlushHandler(EarlybirdLuceneIndetwitterSegmentData objectToFlush) {
      super(objectToFlush);
      this.directory = objectToFlush.directory;
    }

    public OnDiskSegmentDataFlushHandler(
        Schema schema,
        Directory directory,
        EarlybirdIndetwitterEtwittertensionsFactory indetwitterEtwittertensionsFactory,
        Flushable.Handler<? etwittertends DocIDToTweetIDMapper> docIdMapperFlushHandler,
        Flushable.Handler<? etwittertends TimeMapper> timeMapperFlushHandler) {
      super(schema, indetwitterEtwittertensionsFactory, docIdMapperFlushHandler, timeMapperFlushHandler);
      this.directory = directory;
    }

    @Override
    protected EarlybirdIndetwitterEtwittertensionsData newIndetwitterEtwittertension() {
      return indetwitterEtwittertensionsFactory.newLuceneIndetwitterEtwittertensionsData();
    }

    @Override
    protected void flushAdditionalDataStructures(
        FlushInfo flushInfo, DataSerializer out, EarlybirdIndetwitterSegmentData toFlush) {
    }

    @Override
    protected EarlybirdIndetwitterSegmentData constructSegmentData(
        FlushInfo flushInfo,
        ConcurrentHashMap<String, InvertedIndetwitter> perFieldMap,
        int matwitterSegmentSize,
        EarlybirdIndetwitterEtwittertensionsData indetwitterEtwittertension,
        DocIDToTweetIDMapper docIdToTweetIdMapper,
        TimeMapper timeMapper,
        DataDeserializer in) {
      return new EarlybirdLuceneIndetwitterSegmentData(
          directory,
          matwitterSegmentSize,
          flushInfo.getLongProperty(TIME_SLICE_ID_PROP_NAME),
          schema,
          flushInfo.getBooleanProperty(IS_OPTIMIZED_PROP_NAME),
          flushInfo.getIntProperty(SMALLEST_DOCID_PROP_NAME),
          perFieldMap,
          AbstractFacetCountingArray.EMPTY_ARRAY,
          new OptimizedDocValuesManager(schema, matwitterSegmentSize),
          docIdToTweetIdMapper,
          timeMapper,
          indetwitterEtwittertension);
    }
  }
}
