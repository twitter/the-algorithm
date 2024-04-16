package com.tw ter.search.earlyb rd.search.facets;

 mport java. o. OExcept on;
 mport java.ut l.ArrayL st;
 mport java.ut l.HashMap;
 mport java.ut l.L st;
 mport java.ut l.Map;

 mport com.google.common.collect.Maps;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.collect ons.Pa r;
 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.core.earlyb rd.facets.Facet DMap;
 mport com.tw ter.search.core.earlyb rd.facets.FacetLabelProv der;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.earlyb rd.search.Ant Gam ngF lter;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetCount;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetCount tadata;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetF eldResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetResults;

publ c class Expla nFacetResultsCollector extends FacetResultsCollector {
  pr vate stat c f nal Logger LOG =
      LoggerFactory.getLogger(Expla nFacetResultsCollector.class.getNa ());

  protected f nal L st<Pa r< nteger, Long>> proofs;
  protected f nal Map<Str ng, Map<Str ng, L st<Long>>> proofAccumulators;

  protected Map<Str ng, FacetLabelProv der> facetLabelProv ders;
  pr vate Facet DMap facet DMap;

  /**
   * Creates a new facet collector w h t  ab l y to prov de explanat ons for t  search results.
   */
  publ c Expla nFacetResultsCollector(
       mmutableSc ma nterface sc ma,
      FacetSearchRequest nfo searchRequest nfo,
      Ant Gam ngF lter ant Gam ngF lter,
      Earlyb rdSearc rStats searc rStats,
      Clock clock,
       nt requestDebugMode) throws  OExcept on {
    super(sc ma, searchRequest nfo, ant Gam ngF lter, searc rStats, clock, requestDebugMode);

    proofs = new ArrayL st<>(128);

    proofAccumulators = Maps.newHashMap();
    for (Sc ma.F eld nfo facetF eld : sc ma.getFacetF elds()) {
      HashMap<Str ng, L st<Long>> f eldLabelToT et dsMap = new HashMap<>();
      proofAccumulators.put(facetF eld.getF eldType().getFacetNa (), f eldLabelToT et dsMap);
    }
  }

  @Overr de
  protected Accumulator newPerSeg ntAccumulator(Earlyb rd ndexSeg ntAtom cReader  ndexReader) {
    Accumulator accumulator = super.newPerSeg ntAccumulator( ndexReader);
    accumulator.accessor.setProofs(proofs);
    facetLabelProv ders =  ndexReader.getFacetLabelProv ders();
    facet DMap =  ndexReader.getFacet DMap();

    return accumulator;
  }

  @Overr de
  publ c vo d doCollect(long t et D) throws  OExcept on {
    proofs.clear();

    // FacetResultsCollector.doCollect() calls FacetScorer. ncre ntCounts(),
    // FacetResultsCollector.doCollect() creates a FacetResultsCollector.Accumulator,  f
    // necessary, wh ch conta ns t  accessor (a Compos eFacet erator) and accumulators
    // (FacetAccumulator of each f eld)
    super.doCollect(t et D);

    for (Pa r< nteger, Long> f eld dTerm dPa r : proofs) {
       nt f eld D = f eld dTerm dPa r.getF rst();
      long term D = f eld dTerm dPa r.getSecond();

      // Convert term  D to t  term text, a.k.a. facet label
      Str ng facetNa  = facet DMap.getFacetF eldByFacet D(f eld D).getFacetNa ();
       f (facetNa  != null) {
        Str ng facetLabel = facetLabelProv ders.get(facetNa )
                .getLabelAccessor().getTermText(term D);

        L st<Long> t et Ds = proofAccumulators.get(facetNa ).get(facetLabel);
         f (t et Ds == null) {
          t et Ds = new ArrayL st<>();
          proofAccumulators.get(facetNa ).put(facetLabel, t et Ds);
        }

        t et Ds.add(t et D);
      }
    }

    // clear   aga n just to be sure
    proofs.clear();
  }

  /**
   * Sets explanat ons for t  facet results.
   */
  publ c vo d setExplanat ons(Thr ftFacetResults facetResults) {
    Str ngBu lder explanat on = new Str ngBu lder();

    for (Map.Entry<Str ng, Thr ftFacetF eldResults> facetF eldResultsEntry
            : facetResults.getFacetF elds().entrySet()) {
      Str ng facetNa  = facetF eldResultsEntry.getKey();
      Thr ftFacetF eldResults facetF eldResults = facetF eldResultsEntry.getValue();

      Map<Str ng, L st<Long>> proofAccumulator = proofAccumulators.get(facetNa );

       f (proofAccumulator == null) {
        // d d not accumulate explanat on for t  facet type? a bug?
        LOG.warn("No explanat on accumulated for facet type " + facetNa );
        cont nue;
      }

      for (Thr ftFacetCount facetCount : facetF eldResults.getTopFacets()) {
        Str ng facetLabel = facetCount.getFacetLabel(); // a.k.a. term text
        Thr ftFacetCount tadata  tadata = facetCount.get tadata();

        L st<Long> t et Ds = proofAccumulator.get(facetLabel);
         f (t et Ds == null) {
          // d d not accumulate explanat on for t  facet label? a bug?
          LOG.warn("No explanat on accumulated for " + facetLabel + " of facet type " + facetNa );
          cont nue;
        }

        explanat on.setLength(0);
        Str ng oldExplanat on = null;
         f ( tadata. sSetExplanat on()) {
          // save t  old explanat on from Tw ter n mory ndexSearc r.f llTerm tadata()
          oldExplanat on =  tadata.getExplanat on();
          // as of 2012/05/29,   have 18 d g s t et  Ds
          explanat on.ensureCapac y(oldExplanat on.length() + (18 + 2) + 10);
        } else {
          // as of 2012/05/29,   have 18 d g s t et  Ds
          explanat on.ensureCapac y(t et Ds.s ze() * (18 + 2) + 10);
        }

        explanat on.append("[");
        for (Long t et D : t et Ds) {
          explanat on.append(t et D)
                  .append(", ");
        }
        explanat on.setLength(explanat on.length() - 2); // remove t  last ", "
        explanat on.append("]\n");
         f (oldExplanat on != null) {
          explanat on.append(oldExplanat on);
        }
         tadata.setExplanat on(explanat on.toStr ng());
      }
    }
  }
}
