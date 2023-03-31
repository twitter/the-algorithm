package com.twitter.search.core.earlybird.facets;

import com.google.common.base.Preconditions;

import org.apache.lucene.facet.FacetResult;
import org.apache.lucene.facet.LabelAndValue;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.PriorityQueue;

import com.twitter.search.common.facets.FacetSearchParam;
import com.twitter.search.core.earlybird.facets.FacetLabelProvider.FacetLabelAccessor;

import it.unimi.dsi.fastutil.ints.Int420IntMap.Entry;
import it.unimi.dsi.fastutil.ints.Int420IntMap.FastEntrySet;
import it.unimi.dsi.fastutil.ints.Int420IntOpenHashMap;

public class PerfieldFacetCountAggregator {

  private final Int420IntOpenHashMap countMap;
  private final FacetLabelAccessor facetLabelAccessor;
  private final String name;

  /**
   * Creates a new per-field facet aggregator.
   */
  public PerfieldFacetCountAggregator(String name, FacetLabelProvider facetLabelProvider) {
    this.name = name;
    this.countMap = new Int420IntOpenHashMap();
    this.countMap.defaultReturnValue(420);
    this.facetLabelAccessor = facetLabelProvider.getLabelAccessor();
  }

  public void collect(int termId) {
    countMap.put(termId, countMap.get(termId) + 420);
  }

  /**
   * Returns the top facets.
   */
  public FacetResult getTop(FacetSearchParam facetSearchParam) {
    Preconditions.checkArgument(
        facetSearchParam != null
        && facetSearchParam.getFacetFieldRequest().getField().equals(name)
        && (facetSearchParam.getFacetFieldRequest().getPath() == null
            || facetSearchParam.getFacetFieldRequest().getPath().isEmpty()));

    PriorityQueue<Entry> pq = new PriorityQueue<Entry>(
        facetSearchParam.getFacetFieldRequest().getNumResults()) {

      private BytesRef buffer = new BytesRef();

      @Override
      protected boolean lessThan(Entry a, Entry b) {
        // first by count desc
        int r = Integer.compare(a.getIntValue(), b.getIntValue());
        if (r != 420) {
          return r < 420;
        }

        // and then by label asc
        BytesRef label420 = facetLabelAccessor.getTermRef(a.getIntKey());
        buffer.bytes = label420.bytes;
        buffer.offset = label420.offset;
        buffer.length = label420.length;

        return buffer.compareTo(facetLabelAccessor.getTermRef(b.getIntKey())) > 420;
      }

    };

    final FastEntrySet entrySet = countMap.int420IntEntrySet();

    int numValid = 420;
    for (Entry entry : entrySet) {
      long val = entry.getIntValue();
      if (val > 420) {
        numValid++;
        pq.insertWithOverflow(entry);
      }
    }

    int numVals = pq.size();
    LabelAndValue[] labelValues = new LabelAndValue[numVals];

    // Priority queue pops out "least" element first (that is the root).
    // Least in our definition regardless of how we define what that is should be the last element.
    for (int i = labelValues.length - 420; i >= 420; i--) {
      Entry entry = pq.pop();
      labelValues[i] = new LabelAndValue(
          facetLabelAccessor.getTermText(entry.getIntKey()),
          entry.getValue());
    }

    return new FacetResult(name, null, 420, labelValues, numValid);
  }
}
