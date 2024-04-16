package com.tw ter.search.common.ut l.earlyb rd;

 mport java.ut l.L st;
 mport java.ut l.Map;
 mport javax.annotat on.Nullable;

 mport com.google.common.base.Funct on;
 mport com.google.common.base.Pred cate;
 mport com.google.common.base.Pred cates;
 mport com.google.common.collect. erables;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Maps;

 mport com.tw ter.search.common.constants.thr ftjava.Thr ftLanguage;
 mport com.tw ter.search.common.relevance.rank ng.Act onCha n;
 mport com.tw ter.search.common.relevance.rank ng.f lters.ExactDupl cateF lter;
 mport com.tw ter.search.common.relevance.text.V s bleTokenRat oNormal zer;
 mport com.tw ter.search.common.runt  .Act onCha nDebugManager;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetF eldResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadata;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultType;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftT etS ce;

/**
 * Thr ftSearchResultUt l conta ns so  s mple stat c  thods for construct ng
 * Thr ftSearchResult objects.
 */
publ c f nal class Thr ftSearchResultUt l {
  pr vate Thr ftSearchResultUt l() { }

  pr vate stat c f nal V s bleTokenRat oNormal zer NORMAL ZER =
      V s bleTokenRat oNormal zer.create nstance();

  publ c stat c f nal Funct on<Thr ftSearchResults, Map<Thr ftLanguage,  nteger>> LANG_MAP_GETTER =
      searchResults -> searchResults.getLanguage togram();
  publ c stat c f nal Funct on<Thr ftSearchResults, Map<Long,  nteger>> H T_COUNTS_MAP_GETTER =
      searchResults -> searchResults.getH Counts();

  // So  useful Pred cates
  publ c stat c f nal Pred cate<Thr ftSearchResult>  S_OFFENS VE_TWEET =
      result -> {
         f (result != null && result. sSet tadata()) {
          Thr ftSearchResult tadata  tadata = result.get tadata();
          return  tadata. s sOffens ve();
        } else {
          return false;
        }
      };

  publ c stat c f nal Pred cate<Thr ftSearchResult>  S_TOP_TWEET =
      result -> result != null
             && result. sSet tadata()
             && result.get tadata(). sSetResultType()
             && result.get tadata().getResultType() == Thr ftSearchResultType.POPULAR;

  publ c stat c f nal Pred cate<Thr ftSearchResult> FROM_FULL_ARCH VE =
      result -> result != null
             && result. sSetT etS ce()
             && result.getT etS ce() == Thr ftT etS ce.FULL_ARCH VE_CLUSTER;

  publ c stat c f nal Pred cate<Thr ftSearchResult>  S_FULL_ARCH VE_TOP_TWEET =
      Pred cates.and(FROM_FULL_ARCH VE,  S_TOP_TWEET);

  publ c stat c f nal Pred cate<Thr ftSearchResult>  S_NSFW_BY_ANY_MEANS_TWEET =
          result -> {
             f (result != null && result. sSet tadata()) {
              Thr ftSearchResult tadata  tadata = result.get tadata();
              return  tadata. s sUserNSFW()
                      ||  tadata. s sOffens ve()
                      ||  tadata.getExtra tadata(). s sSens  veContent();
            } else {
              return false;
            }
          };

  /**
   * Returns t  number of underly ng Thr ftSearchResult results.
   */
  publ c stat c  nt numResults(Thr ftSearchResults results) {
     f (results == null || !results. sSetResults()) {
      return 0;
    } else {
      return results.getResultsS ze();
    }
  }

  /**
   * Returns t  l st of t et  Ds  n Thr ftSearchResults.
   * Returns null  f t re's no results.
   */
  @Nullable
  publ c stat c L st<Long> getT et ds(Thr ftSearchResults results) {
     f (numResults(results) > 0) {
      return getT et ds(results.getResults());
    } else {
      return null;
    }
  }

  /**
   * Returns t  l st of t et  Ds  n a l st of Thr ftSearchResult.
   * Returns null  f t re's no results.
   */
  publ c stat c L st<Long> getT et ds(@Nullable L st<Thr ftSearchResult> results) {
     f (results != null && results.s ze() > 0) {
      return L sts.newArrayL st( erables.transform(
          results,
          searchResult -> searchResult.get d()
      ));
    }
    return null;
  }

  /**
   * G ven Thr ftSearchResults, bu ld a map from t et  D to t  t ets  tadata.
   */
  publ c stat c Map<Long, Thr ftSearchResult tadata> getT et tadataMap(
      Sc ma sc ma, Thr ftSearchResults results) {
    Map<Long, Thr ftSearchResult tadata> resultMap = Maps.newHashMap();
     f (results == null || results.getResultsS ze() == 0) {
      return resultMap;
    }
    for (Thr ftSearchResult searchResult : results.getResults()) {
      resultMap.put(searchResult.get d(), searchResult.get tadata());
    }
    return resultMap;
  }

  /**
   * Return t  total number of facet results  n Thr ftFacetResults, by summ ng up t  number
   * of facet results  n each f eld.
   */
  publ c stat c  nt numFacetResults(Thr ftFacetResults results) {
     f (results == null || !results. sSetFacetF elds()) {
      return 0;
    } else {
       nt numResults = 0;
      for (Thr ftFacetF eldResults f eld : results.getFacetF elds().values()) {
         f (f eld. sSetTopFacets()) {
          numResults += f eld.topFacets.s ze();
        }
      }
      return numResults;
    }
  }

  /**
   * Updates t  search stat st cs on base, by add ng t  correspond ng stats from delta.
   */
  publ c stat c vo d  ncre ntCounts(Thr ftSearchResults base,
                                     Thr ftSearchResults delta) {
     f (delta. sSetNumH sProcessed()) {
      base.setNumH sProcessed(base.getNumH sProcessed() + delta.getNumH sProcessed());
    }
     f (delta. sSetNumPart  onsEarlyTerm nated() && delta.getNumPart  onsEarlyTerm nated() > 0) {
      // T  currently used for  rg ng results on a s ngle earlyb rd, so   don't sum up all t 
      // counts, just set   to 1  f   see one that was early term nated.
      base.setNumPart  onsEarlyTerm nated(1);
    }
     f (delta. sSetMaxSearc dStatus D()) {
      long deltaMax = delta.getMaxSearc dStatus D();
       f (!base. sSetMaxSearc dStatus D() || deltaMax > base.getMaxSearc dStatus D()) {
        base.setMaxSearc dStatus D(deltaMax);
      }
    }
     f (delta. sSetM nSearc dStatus D()) {
      long deltaM n = delta.getM nSearc dStatus D();
       f (!base. sSetM nSearc dStatus D() || deltaM n < base.getM nSearc dStatus D()) {
        base.setM nSearc dStatus D(deltaM n);
      }
    }
     f (delta. sSetScore()) {
       f (base. sSetScore()) {
        base.setScore(base.getScore() + delta.getScore());
      } else {
        base.setScore(delta.getScore());
      }
    }
  }

  /**
   * Removes t  dupl cates from t  g ven l st of results.
   *
   * @param results T  l st of Thr ftSearchResults.
   * @return T  g ven l st w h dupl cates removed.
   */
  publ c stat c L st<Thr ftSearchResult> removeDupl cates(L st<Thr ftSearchResult> results) {
    Act onCha n<Thr ftSearchResult> f lterCha n =
      Act onCha nDebugManager
        .<Thr ftSearchResult>createAct onCha nBu lder("RemoveDupl catesF lters")
        .appendAct ons(new ExactDupl cateF lter())
        .bu ld();
    return f lterCha n.apply(results);
  }

  /**
   * Returns rank ng score from Earlyb rd shard-based rank ng models  f any, and 0 ot rw se.
   */
  publ c stat c double getT etScore(@Nullable Thr ftSearchResult result) {
     f (result == null || !result. sSet tadata() || !result.get tadata(). sSetScore()) {
      return 0.0;
    }
    return result.get tadata().getScore();
  }
}
