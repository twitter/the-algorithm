package com.tw ter.search.core.earlyb rd.facets;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene.facet.FacetResult;
 mport org.apac .lucene.facet.LabelAndValue;
 mport org.apac .lucene.ut l.BytesRef;
 mport org.apac .lucene.ut l.Pr or yQueue;

 mport com.tw ter.search.common.facets.FacetSearchParam;
 mport com.tw ter.search.core.earlyb rd.facets.FacetLabelProv der.FacetLabelAccessor;

 mport  .un m .ds .fastut l. nts. nt2 ntMap.Entry;
 mport  .un m .ds .fastut l. nts. nt2 ntMap.FastEntrySet;
 mport  .un m .ds .fastut l. nts. nt2 ntOpenHashMap;

publ c class Perf eldFacetCountAggregator {

  pr vate f nal  nt2 ntOpenHashMap countMap;
  pr vate f nal FacetLabelAccessor facetLabelAccessor;
  pr vate f nal Str ng na ;

  /**
   * Creates a new per-f eld facet aggregator.
   */
  publ c Perf eldFacetCountAggregator(Str ng na , FacetLabelProv der facetLabelProv der) {
    t .na  = na ;
    t .countMap = new  nt2 ntOpenHashMap();
    t .countMap.defaultReturnValue(0);
    t .facetLabelAccessor = facetLabelProv der.getLabelAccessor();
  }

  publ c vo d collect( nt term d) {
    countMap.put(term d, countMap.get(term d) + 1);
  }

  /**
   * Returns t  top facets.
   */
  publ c FacetResult getTop(FacetSearchParam facetSearchParam) {
    Precond  ons.c ckArgu nt(
        facetSearchParam != null
        && facetSearchParam.getFacetF eldRequest().getF eld().equals(na )
        && (facetSearchParam.getFacetF eldRequest().getPath() == null
            || facetSearchParam.getFacetF eldRequest().getPath(). sEmpty()));

    Pr or yQueue<Entry> pq = new Pr or yQueue<Entry>(
        facetSearchParam.getFacetF eldRequest().getNumResults()) {

      pr vate BytesRef buffer = new BytesRef();

      @Overr de
      protected boolean lessThan(Entry a, Entry b) {
        // f rst by count desc
         nt r =  nteger.compare(a.get ntValue(), b.get ntValue());
         f (r != 0) {
          return r < 0;
        }

        // and t n by label asc
        BytesRef label1 = facetLabelAccessor.getTermRef(a.get ntKey());
        buffer.bytes = label1.bytes;
        buffer.offset = label1.offset;
        buffer.length = label1.length;

        return buffer.compareTo(facetLabelAccessor.getTermRef(b.get ntKey())) > 0;
      }

    };

    f nal FastEntrySet entrySet = countMap. nt2 ntEntrySet();

     nt numVal d = 0;
    for (Entry entry : entrySet) {
      long val = entry.get ntValue();
       f (val > 0) {
        numVal d++;
        pq. nsertW hOverflow(entry);
      }
    }

     nt numVals = pq.s ze();
    LabelAndValue[] labelValues = new LabelAndValue[numVals];

    // Pr or y queue pops out "least" ele nt f rst (that  s t  root).
    // Least  n   def n  on regardless of how   def ne what that  s should be t  last ele nt.
    for ( nt   = labelValues.length - 1;   >= 0;  --) {
      Entry entry = pq.pop();
      labelValues[ ] = new LabelAndValue(
          facetLabelAccessor.getTermText(entry.get ntKey()),
          entry.getValue());
    }

    return new FacetResult(na , null, 0, labelValues, numVal d);
  }
}
