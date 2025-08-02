package com.twitter.search.core.earlybird.indetwitter.inverted;

import java.io.IOEtwitterception;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.base.Preconditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.lucene.indetwitter.PostingsEnum;
import org.apache.lucene.indetwitter.TermsEnum;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.util.BytesRef;

import com.twitter.search.common.schema.base.EarlybirdFieldType;
import com.twitter.search.common.schema.base.Schema;
import com.twitter.search.core.earlybird.facets.AbstractFacetCountingArray;
import com.twitter.search.core.earlybird.facets.FacetLabelProvider;
import com.twitter.search.core.earlybird.facets.FacetUtil;
import com.twitter.search.core.earlybird.indetwitter.DocIDToTweetIDMapper;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdRealtimeIndetwitterSegmentData;
import com.twitter.search.core.earlybird.indetwitter.TimeMapper;
import com.twitter.search.core.earlybird.indetwitter.column.DocValuesManager;

public final class IndetwitterOptimizer {
  private static final Logger LOG = LoggerFactory.getLogger(IndetwitterOptimizer.class);

  private IndetwitterOptimizer() {
  }

  /**
   * Optimizes this in-memory indetwitter segment.
   */
  public static EarlybirdRealtimeIndetwitterSegmentData optimize(
      EarlybirdRealtimeIndetwitterSegmentData source) throws IOEtwitterception {
    LOG.info("Starting indetwitter optimizing.");

    ConcurrentHashMap<String, InvertedIndetwitter> targetMap = new ConcurrentHashMap<>();
    LOG.info(String.format(
        "Source PerFieldMap size is %d", source.getPerFieldMap().size()));

    LOG.info("Optimize doc id mapper.");
    // Optimize the doc ID mapper first.
    DocIDToTweetIDMapper originalTweetIdMapper = source.getDocIDToTweetIDMapper();
    DocIDToTweetIDMapper optimizedTweetIdMapper = originalTweetIdMapper.optimize();

    TimeMapper optimizedTimeMapper =
        source.getTimeMapper() != null
        ? source.getTimeMapper().optimize(originalTweetIdMapper, optimizedTweetIdMapper)
        : null;

    // Some fields have their terms rewritten to support the minimal perfect hash function we use
    // (note that it's a minimal perfect hash function, not a minimal perfect hash _table_).
    // The FacetCountingArray stores term IDs. This is a map from the facet field ID to a map from
    // original term ID to the new, MPH term IDs.
    Map<Integer, int[]> termIDMapper = new HashMap<>();

    LOG.info("Optimize inverted indetwitteres.");
    optimizeInvertedIndetwitteres(
        source, targetMap, originalTweetIdMapper, optimizedTweetIdMapper, termIDMapper);

    LOG.info("Rewrite and map ids in facet counting array.");
    AbstractFacetCountingArray facetCountingArray = source.getFacetCountingArray().rewriteAndMapIDs(
        termIDMapper, originalTweetIdMapper, optimizedTweetIdMapper);

    Map<String, FacetLabelProvider> facetLabelProviders =
        FacetUtil.getFacetLabelProviders(source.getSchema(), targetMap);

    LOG.info("Optimize doc values manager.");
    DocValuesManager optimizedDocValuesManager =
        source.getDocValuesManager().optimize(originalTweetIdMapper, optimizedTweetIdMapper);

    LOG.info("Optimize deleted docs.");
    DeletedDocs optimizedDeletedDocs =
        source.getDeletedDocs().optimize(originalTweetIdMapper, optimizedTweetIdMapper);

    final boolean isOptimized = true;
    return new EarlybirdRealtimeIndetwitterSegmentData(
        source.getMatwitterSegmentSize(),
        source.getTimeSliceID(),
        source.getSchema(),
        isOptimized,
        optimizedTweetIdMapper.getNetwittertDocID(Integer.MIN_VALUE),
        targetMap,
        facetCountingArray,
        optimizedDocValuesManager,
        facetLabelProviders,
        source.getFacetIDMap(),
        optimizedDeletedDocs,
        optimizedTweetIdMapper,
        optimizedTimeMapper,
        source.getIndetwitterEtwittertensionsData());
  }

  private static void optimizeInvertedIndetwitteres(
      EarlybirdRealtimeIndetwitterSegmentData source,
      ConcurrentHashMap<String, InvertedIndetwitter> targetMap,
      DocIDToTweetIDMapper originalTweetIdMapper,
      DocIDToTweetIDMapper optimizedTweetIdMapper,
      Map<Integer, int[]> termIDMapper
  ) throws IOEtwitterception {
    for (Map.Entry<String, InvertedIndetwitter> entry : source.getPerFieldMap().entrySet()) {
      String fieldName = entry.getKey();
      Preconditions.checkState(entry.getValue() instanceof InvertedRealtimeIndetwitter);
      InvertedRealtimeIndetwitter sourceIndetwitter = (InvertedRealtimeIndetwitter) entry.getValue();
      EarlybirdFieldType fieldType = source.getSchema().getFieldInfo(fieldName).getFieldType();

      InvertedIndetwitter newIndetwitter;
      if (fieldType.becomesImmutable() && sourceIndetwitter.getNumTerms() > 0) {
        Schema.FieldInfo facetField = source.getSchema().getFacetFieldByFieldName(fieldName);

        newIndetwitter = new OptimizedMemoryIndetwitter(
            fieldType,
            fieldName,
            sourceIndetwitter,
            termIDMapper,
            source.getFacetIDMap().getFacetField(facetField),
            originalTweetIdMapper,
            optimizedTweetIdMapper);
      } else {
        newIndetwitter = optimizeMutableIndetwitter(
            fieldType,
            fieldName,
            sourceIndetwitter,
            originalTweetIdMapper,
            optimizedTweetIdMapper);
      }

      targetMap.put(fieldName, newIndetwitter);
    }
  }

  /**
   * Optimize a mutable indetwitter.
   */
  private static InvertedIndetwitter optimizeMutableIndetwitter(
      EarlybirdFieldType fieldType,
      String fieldName,
      InvertedRealtimeIndetwitter originalIndetwitter,
      DocIDToTweetIDMapper originalMapper,
      DocIDToTweetIDMapper optimizedMapper
  ) throws IOEtwitterception {
    Preconditions.checkState(!fieldType.isStorePerPositionPayloads());
    TermsEnum allTerms = originalIndetwitter.createTermsEnum(originalIndetwitter.getMatwitterPublishedPointer());

    int numTerms = originalIndetwitter.getNumTerms();

    InvertedRealtimeIndetwitter indetwitter = new InvertedRealtimeIndetwitter(
        fieldType,
        TermPointerEncoding.DEFAULT_ENCODING,
        fieldName);
    indetwitter.setNumDocs(originalIndetwitter.getNumDocs());

    for (int termID = 0; termID < numTerms; termID++) {
      allTerms.seekEtwitteract(termID);
      PostingsEnum postingsEnum = new OptimizingPostingsEnumWrapper(
          allTerms.postings(null), originalMapper, optimizedMapper);

      BytesRef termPayload = originalIndetwitter.getLabelAccessor().getTermPayload(termID);
      copyPostingList(indetwitter, postingsEnum, termID, allTerms.term(), termPayload);
    }
    return indetwitter;
  }


  /**
   * Copies the given posting list into these posting lists.
   *
   * @param postingsEnum enumerator of the posting list that needs to be copied
   */
  private static void copyPostingList(
      InvertedRealtimeIndetwitter indetwitter,
      PostingsEnum postingsEnum,
      int termID,
      BytesRef term,
      BytesRef termPayload
  ) throws IOEtwitterception {
    int docId;
    while ((docId = postingsEnum.netwittertDoc()) != DocIdSetIterator.NO_MORE_DOCS) {
      indetwitter.incrementSumTermDocFreq();
      for (int i = 0; i < postingsEnum.freq(); i++) {
        indetwitter.incrementSumTotalTermFreq();
        int position = postingsEnum.netwittertPosition();
        int newTermID = InvertedRealtimeIndetwitterWriter.indetwitterTerm(
            indetwitter,
            term,
            docId,
            position,
            termPayload,
            null, // We know that fields that remain mutable never have a posting payload.
            TermPointerEncoding.DEFAULT_ENCODING);

        // Our term lookups are very slow, so we cache term dictionaries for some fields across many
        // segments, so we must keep the term IDs the same while remapping.
        Preconditions.checkState(newTermID == termID);
      }
    }
  }
}
