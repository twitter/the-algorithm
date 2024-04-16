package com.tw ter.search.earlyb rd.search.facets;

 mport java. o. OExcept on;
 mport java.ut l.ArrayL st;
 mport java.ut l.HashMap;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Pr or yQueue;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.constants.thr ftjava.Thr ftLanguage;
 mport com.tw ter.search.common.rank ng.thr ftjava.Thr ftFacetEarlyb rdSort ngMode;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.core.earlyb rd.facets.Dum FacetAccumulator;
 mport com.tw ter.search.core.earlyb rd.facets.FacetAccumulator;
 mport com.tw ter.search.core.earlyb rd.facets.FacetCount erator;
 mport com.tw ter.search.core.earlyb rd.facets.Facet DMap;
 mport com.tw ter.search.core.earlyb rd.facets.Facet DMap.FacetF eld;
 mport com.tw ter.search.core.earlyb rd.facets.FacetLabelProv der;
 mport com.tw ter.search.core.earlyb rd.facets.Language togram;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.earlyb rd.search.AbstractResultsCollector;
 mport com.tw ter.search.earlyb rd.search.Ant Gam ngF lter;
 mport com.tw ter.search.earlyb rd.search.Earlyb rdLuceneSearc r.FacetSearchResults;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetCount;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetF eldResults;

publ c class FacetResultsCollector extends
    AbstractResultsCollector<FacetSearchRequest nfo, FacetSearchResults> {

  pr vate f nal FacetScorer facetScorer;
  pr vate f nal Thr ftFacetEarlyb rdSort ngMode sort ngMode;

  stat c class Accumulator {
    protected f nal FacetAccumulator<Thr ftFacetF eldResults>[] accumulators;
    protected f nal FacetCount erator accessor;
    protected f nal Facet DMap facet DMap;

    Accumulator(FacetAccumulator<Thr ftFacetF eldResults>[] accumulators,
                FacetCount erator accessor,
                Facet DMap facet DMap) {
      t .accumulators = accumulators;
      t .accessor = accessor;
      t .facet DMap = facet DMap;
    }

    FacetAccumulator<Thr ftFacetF eldResults> getFacetAccumulator(Str ng facetNa ) {
      FacetF eld facet = facet DMap.getFacetF eldByFacetNa (facetNa );
      return accumulators[facet.getFacet d()];
    }
  }

  pr vate Accumulator currentAccumulator;
  pr vate L st<Accumulator> segAccumulators;
  pr vate f nal Hash ngAndPrun ngFacetAccumulator.FacetComparator facetComparator;

  /**
   * Creates a new FacetResultsCollector for t  g ven facet search request.
   */
  publ c FacetResultsCollector(
       mmutableSc ma nterface sc ma,
      FacetSearchRequest nfo searchRequest nfo,
      Ant Gam ngF lter ant Gam ngF lter,
      Earlyb rdSearc rStats searc rStats,
      Clock clock,
       nt requestDebug nfo) {
    super(sc ma, searchRequest nfo, clock, searc rStats, requestDebug nfo);

     f (searchRequest nfo.rank ngOpt ons != null
        && searchRequest nfo.rank ngOpt ons. sSetSort ngMode()) {
      t .sort ngMode = searchRequest nfo.rank ngOpt ons.getSort ngMode();
    } else {
      t .sort ngMode = Thr ftFacetEarlyb rdSort ngMode.SORT_BY_WE GHTED_COUNT;
    }

    t .facetComparator = Hash ngAndPrun ngFacetAccumulator.getComparator(sort ngMode);
    t .facetScorer = createScorer(ant Gam ngF lter);
    t .segAccumulators = new ArrayL st<>();
  }

  @Overr de
  publ c vo d startSeg nt() {
    currentAccumulator = null;
  }

  @Overr de
  publ c vo d doCollect(long t et D) throws  OExcept on {
     f (currentAccumulator == null) {
      // Laz ly create accumulators.  Most seg nt / query / facet comb nat ons have no h s.
      currentAccumulator = newPerSeg ntAccumulator(currTw terReader);
      segAccumulators.add(currentAccumulator);
      facetScorer.startSeg nt(currTw terReader);
    }
    facetScorer. ncre ntCounts(currentAccumulator, curDoc d);
  }

  @Overr de
  publ c FacetSearchResults doGetResults() {
    return new FacetSearchResults(t );
  }

  /**
   * Returns t  top-k facet results for t  requested facetNa .
   */
  publ c Thr ftFacetF eldResults getFacetResults(Str ng facetNa ,  nt topK) {
     nt totalCount = 0;
    f nal Map<Str ng, Thr ftFacetCount> map = new HashMap<>();

    Language togram language togram = new Language togram();

    for (Accumulator segAccumulator : segAccumulators) {
      FacetAccumulator<Thr ftFacetF eldResults> accumulator =
          segAccumulator.getFacetAccumulator(facetNa );
      Precond  ons.c ckNotNull(accumulator);

      Thr ftFacetF eldResults results = accumulator.getAllFacets();
       f (results == null) {
        cont nue;
      }

      totalCount += results.totalCount;

      //  rge language  tograms from d fferent seg nts
      language togram.addAll(accumulator.getLanguage togram());

      for (Thr ftFacetCount facetCount : results.getTopFacets()) {
        Str ng label = facetCount.getFacetLabel();
        Thr ftFacetCount oldCount = map.get(label);
         f (oldCount != null) {
          oldCount.setS mpleCount(oldCount.getS mpleCount() + facetCount.getS mpleCount());
          oldCount.set  ghtedCount(oldCount.get  ghtedCount() + facetCount.get  ghtedCount());

          oldCount.setFacetCount(oldCount.getFacetCount() + facetCount.getFacetCount());
          oldCount.setPenaltyCount(oldCount.getPenaltyCount() + facetCount.getPenaltyCount());
        } else {
          map.put(label, facetCount);
        }
      }
    }

     f (map.s ze() == 0 || totalCount == 0) {
      // No results.
      return null;
    }

    // sort table wrt percentage
    Pr or yQueue<Thr ftFacetCount> pq =
        new Pr or yQueue<>(map.s ze(), facetComparator.getThr ftComparator(true));
    pq.addAll(map.values());

    Thr ftFacetF eldResults results = new Thr ftFacetF eldResults();
    results.setTopFacets(new ArrayL st<>());
    results.setTotalCount(totalCount);

    // Store  rged language  togram  nto thr ft object
    for (Map.Entry<Thr ftLanguage,  nteger> entry
        : language togram.getLanguage togramAsMap().entrySet()) {
      results.putToLanguage togram(entry.getKey(), entry.getValue());
    }

    // Get top facets.
    for ( nt   = 0;   < topK &&   < map.s ze();  ++) {
      Thr ftFacetCount facetCount = pq.poll();
       f (facetCount != null) {
        results.addToTopFacets(facetCount);
      }
    }
    return results;
  }

  protected FacetScorer createScorer(Ant Gam ngF lter ant Gam ngF lter) {
     f (searchRequest nfo.rank ngOpt ons != null) {
      return new DefaultFacetScorer(searchRequest nfo.getSearchQuery(),
                                    searchRequest nfo.rank ngOpt ons,
                                    ant Gam ngF lter,
                                    sort ngMode);
    } else {
      return new FacetScorer() {
        @Overr de
        protected vo d startSeg nt(Earlyb rd ndexSeg ntAtom cReader reader) {
        }

        @Overr de
        publ c vo d  ncre ntCounts(Accumulator accumulator,  nt  nternalDoc D) throws  OExcept on {
          accumulator.accessor. ncre ntData.accumulators = accumulator.accumulators;
          accumulator.accessor. ncre ntData.  ghtedCount ncre nt = 1;
          accumulator.accessor. ncre ntData.penalty ncre nt = 0;
          accumulator.accessor. ncre ntData.language d = Thr ftLanguage.UNKNOWN.getValue();
          accumulator.accessor.collect( nternalDoc D);
        }

        @Overr de
        publ c FacetAccumulator getFacetAccumulator(FacetLabelProv der labelProv der) {
          return new Hash ngAndPrun ngFacetAccumulator(labelProv der, facetComparator);
        }
      };
    }
  }

  protected Accumulator newPerSeg ntAccumulator(Earlyb rd ndexSeg ntAtom cReader  ndexReader) {
    f nal Facet DMap facet DMap =  ndexReader.getFacet DMap();
    f nal FacetCount erator accessor =
         ndexReader.getFacetCount ngArray().get erator(
             ndexReader,
            getSearchRequest nfo().getFacetCountState(),
            T etSearchFacetCount eratorFactory.FACTORY);

    f nal FacetAccumulator<Thr ftFacetF eldResults>[] accumulators =
        (FacetAccumulator<Thr ftFacetF eldResults>[])
            new FacetAccumulator[facet DMap.getNumberOfFacetF elds()];

    Map<Str ng, FacetLabelProv der> labelProv ders =  ndexReader.getFacetLabelProv ders();
    for (FacetF eld f : facet DMap.getFacetF elds()) {
       nt  d = f.getFacet d();
       f (getSearchRequest nfo().getFacetCountState(). sCountF eld(f.getF eld nfo())) {
        accumulators[ d] = (FacetAccumulator<Thr ftFacetF eldResults>) facetScorer
                .getFacetAccumulator(labelProv ders.get(f.getFacetNa ()));
      } else {
        // Dumm  accumulator does noth ng.
        accumulators[ d] = new Dum FacetAccumulator();
      }
    }

    return new Accumulator(accumulators, accessor, facet DMap);
  }
}
