package com.twitter.search.core.earlybird.indetwitter.inverted;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.OptionalInt;
import java.util.concurrent.TimeUnit;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.lucene.util.BytesRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.common.metrics.SearchTimerStats;
import com.twitter.search.common.util.LogFormatUtil;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentAtomicReader;

/**
 * A rather simple implementation of a MultiSegmentTermDictionary that just keeps all terms in a
 * java hash map, and all the termIds for a term in a java list.
 *
 * An alternate implementation could have an MPH for the map, and a IntBlockPool for storing
 * the term ids.
 *
 * See UserIdMultiSegmentQuery class comment for more information on how this is used.
 */
public class MultiSegmentTermDictionaryWithMap implements MultiSegmentTermDictionary {
  private static final Logger LOG = LoggerFactory.getLogger(
      MultiSegmentTermDictionaryWithMap.class);

  @VisibleForTesting
  public static final SearchTimerStats TERM_DICTIONARY_CREATION_STATS =
      SearchTimerStats.etwitterport("multi_segment_term_dictionary_with_map_creation",
          TimeUnit.MILLISECONDS, false);

  private final ImmutableList<OptimizedMemoryIndetwitter> indetwitteres;
  private final HashMap<BytesRef, List<IndetwitterTerm>> termsMap;
  private final int numTerms;
  private final int numTermEntries;

  private static class IndetwitterTerm {
    private int indetwitterId;
    private final int termId;

    public IndetwitterTerm(int indetwitterId, int termId) {
      this.indetwitterId = indetwitterId;
      this.termId = termId;
    }
  }

  /**
   * Creates a new multi-segment term dictionary backed by a regular java map.
   */
  public MultiSegmentTermDictionaryWithMap(
      String field,
      List<OptimizedMemoryIndetwitter> indetwitteres) {

    this.indetwitteres = ImmutableList.copyOf(indetwitteres);

    // Pre-size the map with estimate of matwitter number of terms. It should be at least that big.
    OptionalInt optionalMatwitter = indetwitteres.stream().mapToInt(OptimizedMemoryIndetwitter::getNumTerms).matwitter();
    int matwitterNumTerms = optionalMatwitter.orElse(0);
    this.termsMap = Maps.newHashMapWithEtwitterpectedSize(matwitterNumTerms);

    LOG.info("About to merge {} indetwitteres for field {}, estimated {} terms",
        indetwitteres.size(), field, LogFormatUtil.formatInt(matwitterNumTerms));
    long start = System.currentTimeMillis();

    BytesRef termTetwittert = new BytesRef();
    long copiedBytes = 0;
    for (int indetwitterId = 0; indetwitterId < indetwitteres.size(); indetwitterId++) {
      // The inverted indetwitter for this field.
      OptimizedMemoryIndetwitter indetwitter = indetwitteres.get(indetwitterId);

      int indetwitterNumTerms = indetwitter.getNumTerms();
      for (int termId = 0; termId < indetwitterNumTerms; termId++) {
        indetwitter.getTerm(termId, termTetwittert);

        // This copies the underlying array to a new array.
        BytesRef term = BytesRef.deepCopyOf(termTetwittert);
        copiedBytes += term.length;

        List<IndetwitterTerm> indetwitterTerms = termsMap.computeIfAbsent(term, k -> Lists.newArrayList());

        indetwitterTerms.add(new IndetwitterTerm(indetwitterId, termId));
      }
    }

    this.numTerms = termsMap.size();
    this.numTermEntries = indetwitteres.stream().mapToInt(OptimizedMemoryIndetwitter::getNumTerms).sum();

    long elapsed = System.currentTimeMillis() - start;
    TERM_DICTIONARY_CREATION_STATS.timerIncrement(elapsed);
    LOG.info("Done merging {} indetwitteres for field {} in {}ms - "
      + "num terms: {}, num term entries: {}, copied bytes: {}",
        indetwitteres.size(), field, elapsed,
        LogFormatUtil.formatInt(this.numTerms), LogFormatUtil.formatInt(this.numTermEntries),
            LogFormatUtil.formatInt(copiedBytes));
  }

  @Override
  public int[] lookupTermIds(BytesRef term) {
    int[] termIds = new int[indetwitteres.size()];
    Arrays.fill(termIds, EarlybirdIndetwitterSegmentAtomicReader.TERM_NOT_FOUND);

    List<IndetwitterTerm> indetwitterTerms = termsMap.get(term);
    if (indetwitterTerms != null) {
      for (IndetwitterTerm indetwitterTerm : indetwitterTerms) {
        termIds[indetwitterTerm.indetwitterId] = indetwitterTerm.termId;
      }
    }

    return termIds;
  }

  @Override
  public ImmutableList<? etwittertends InvertedIndetwitter> getSegmentIndetwitteres() {
    return indetwitteres;
  }

  @Override
  public int getNumTerms() {
    return this.numTerms;
  }

  @Override
  public int getNumTermEntries() {
    return this.numTermEntries;
  }
}
