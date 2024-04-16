package com.tw ter.search.earlyb rd_root. rgers;

 mport java.ut l.ArrayL st;
 mport java.ut l.Collect on;
 mport java.ut l.Collect ons;
 mport java.ut l.L st;
 mport java.ut l.Map;

 mport javax.annotat on.Nonnull;
 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Maps;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.ut l.earlyb rd.FacetsResultsUt ls;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ft togramSett ngs;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftTermRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftTermResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftTermStat st csResults;

/**
 * Takes mult ple successful Earlyb rdResponses and  rges t m.
 */
publ c class Thr ftTermResults rger {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Thr ftTermResults rger.class);

  pr vate stat c f nal SearchCounter B N_ D_GAP_COUNTER =
      SearchCounter.export("thr ft_term_results_ rger_found_gap_ n_b n_ ds");
  pr vate stat c f nal SearchCounter M N_COMPLETE_B N_ D_ADJUSTED_NULL =
      SearchCounter.export("thr ft_term_results_ rger_m n_complete_b n_ d_adjusted_null");
  pr vate stat c f nal SearchCounter M N_COMPLETE_B N_ D_NULL_W THOUT_B NS =
      SearchCounter.export("thr ft_term_results_ rger_m n_complete_b n_ d_null_w hout_b ns");
  pr vate stat c f nal SearchCounter M N_COMPLETE_B N_ D_OUT_OF_RANGE =
      SearchCounter.export("thr ft_term_results_ rger_m n_complete_b n_ d_out_of_range");
  pr vate stat c f nal SearchCounter RESPONSE_W THOUT_DR V NG_QUERY_H T =
      SearchCounter.export("response_w hout_dr v ng_query_h ");

  pr vate stat c f nal Thr ftTermRequest GLOBAL_COUNT_REQUEST =
      new Thr ftTermRequest().setF eldNa ("").setTerm("");

  /**
   * Sorted l st of t  most recent (and cont guous) numB ns b n ds across all responses.
   * Expected to be an empty l st  f t  request d d not ask for  tograms, or  f  
   * d d ask for  tograms for 0 numB ns.
   */
  @Nonnull
  pr vate f nal L st< nteger> mostRecentB n ds;
  /**
   * T  f rst b n d  n t  {@l nk #mostRecentB n ds} l st. T  value  s not  ant to be used  n
   * case mostRecentB n ds  s an empty l st.
   */
  pr vate f nal  nt f rstB n d;

  /**
   * For each un que Thr ftTermRequest, stores an array of t  total counts for all t  b n ds
   * that   w ll return, sum d up across all earlyb rd responses.
   *
   * T  values  n each totalCounts array correspond to t  b n ds  n t 
   * {@l nk #mostRecentB n ds} l st.
   *
   * Key: thr ft term request.
   * Value: array of t  total counts sum d up across all earlyb rd responses for t  key's
   * term request, correspond ng to t  b n ds  n {@l nk #mostRecentB n ds}.
   */
  pr vate f nal Map<Thr ftTermRequest,  nt[]>  rgedTermRequestTotalCounts = Maps.newHashMap();
  /**
   * T  set of all un que b n ds that   are  rg ng.
   */
  pr vate f nal Map<Thr ftTermRequest, Thr ftTermResults> termResultsMap = Maps.newHashMap();
  pr vate f nal Thr ft togramSett ngs  togramSett ngs;

  /**
   * Only relevant for  rg ng responses w h  togram sett ngs.
   * T  w ll be null e  r  f (1) t  request  s not ask ng for  tograms at all, or  f
   * (2) numB ns was set to 0 (and no b n can be cons dered complete).
   *  f not null, t  m nCompleteB n d w ll be computed as t  max over all  rged responses'
   * m nCompleteB n d's.
   */
  @Nullable
  pr vate f nal  nteger m nCompleteB n d;

  /**
   * Create  rger w h collect ons of results to  rge
   */
  publ c Thr ftTermResults rger(Collect on<Earlyb rdResponse> termStatsResults,
                                 Thr ft togramSett ngs  togramSett ngs) {
    t . togramSett ngs =  togramSett ngs;

    Collect on<Earlyb rdResponse> f lteredTermStatsResults =
        f lterOutEmptyEarlyb rdResponses(termStatsResults);

    t .mostRecentB n ds = f ndMostRecentB n ds( togramSett ngs, f lteredTermStatsResults);
    t .f rstB n d = mostRecentB n ds. sEmpty()
        ?  nteger.MAX_VALUE // Should not be used  f mostRecentB n ds  s empty.
        : mostRecentB n ds.get(0);

    L st< nteger> m nCompleteB n ds =
        L sts.newArrayL stW hCapac y(f lteredTermStatsResults.s ze());
    for (Earlyb rdResponse response : f lteredTermStatsResults) {
      Precond  ons.c ckState(response.getResponseCode() == Earlyb rdResponseCode.SUCCESS,
          "Unsuccessful responses should not be g ven to Thr ftTermResults rger.");
      Precond  ons.c ckState(response.getTermStat st csResults() != null,
          "Response g ven to Thr ftTermResults rger has no termStat st csResults.");

      Thr ftTermStat st csResults termStat st csResults = response.getTermStat st csResults();
      L st< nteger> b n ds = termStat st csResults.getB n ds();

      for (Map.Entry<Thr ftTermRequest, Thr ftTermResults> entry
          : termStat st csResults.getTermResults().entrySet()) {
        Thr ftTermRequest termRequest = entry.getKey();
        Thr ftTermResults termResults = entry.getValue();

        adjustTotalCount(termResults, b n ds);
        addTotalCountData(termRequest, termResults);

         f ( togramSett ngs != null) {
          Precond  ons.c ckState(termStat st csResults. sSetB n ds());
          add togramData(termRequest, termResults, termStat st csResults.getB n ds());
        }
      }

       f ( togramSett ngs != null) {
        addM nCompleteB n d(m nCompleteB n ds, response);
      }
    }

    m nCompleteB n d = m nCompleteB n ds. sEmpty() ? null : Collect ons.max(m nCompleteB n ds);
  }

  /**
   * Take out any earlyb rd responses that   know d d not match anyth ng relevant to t  query,
   * and may have erroneous b n ds.
   */
  pr vate Collect on<Earlyb rdResponse> f lterOutEmptyEarlyb rdResponses(
      Collect on<Earlyb rdResponse> termStatsResults) {
    L st<Earlyb rdResponse> emptyResponses = L sts.newArrayL st();
    L st<Earlyb rdResponse> nonEmptyResponses = L sts.newArrayL st();
    for (Earlyb rdResponse response : termStatsResults) {
      // Guard aga nst erroneously  rg ng and return ng 0 counts w n   actually have data to
      // return from ot r part  ons.
      // W n a query doesn't match anyth ng at all on an earlyb rd, t  b n ds that are returned
      // do not correspond at all to t  actual query, and are just based on t  data range on t 
      // earlyb rd  self.
      //   can  dent fy t se responses as (1) be ng non-early term nated, and (2) hav ng 0
      // h s processed.
       f ( sTermStatResponseEmpty(response)) {
        emptyResponses.add(response);
      } else {
        nonEmptyResponses.add(response);
      }
    }

    //  f all responses  re "empty",   w ll just use those to  rge  nto a new set of empty
    // responses, us ng t  b n ds prov ded.
    return nonEmptyResponses. sEmpty() ? emptyResponses : nonEmptyResponses;
  }

  pr vate boolean  sTermStatResponseEmpty(Earlyb rdResponse response) {
    return response. sSetSearchResults()
        && (response.getSearchResults().getNumH sProcessed() == 0
            || dr v ngQueryHasNoH s(response))
        && response. sSetEarlyTerm nat on nfo()
        && !response.getEarlyTerm nat on nfo(). sEarlyTerm nated();
  }

  /**
   *  f t  global count b ns are all 0, t n   know t  dr v ng query has no h s.
   * T  c ck  s added as a short term solut on for SEARCH-5476. T  short term f x requ res
   * t  cl ent to set t   ncludeGlobalCounts to k ck  n.
   */
  pr vate boolean dr v ngQueryHasNoH s(Earlyb rdResponse response) {
    Thr ftTermStat st csResults termStat st csResults = response.getTermStat st csResults();
     f (termStat st csResults == null || termStat st csResults.getTermResults() == null) {
      //  f t re's no term stats response, be conservat ve and return false.
      return false;
    } else {
      Thr ftTermResults globalCounts =
          termStat st csResults.getTermResults().get(GLOBAL_COUNT_REQUEST);
       f (globalCounts == null) {
        //   cannot tell  f dr v ng query has no h s, be conservat ve and return false.
        return false;
      } else {
        for ( nteger   : globalCounts.get togramB ns()) {
           f (  > 0) {
            return false;
          }
        }
        RESPONSE_W THOUT_DR V NG_QUERY_H T. ncre nt();
        return true;
      }
    }
  }

  pr vate stat c L st< nteger> f ndMostRecentB n ds(
      Thr ft togramSett ngs  togramSett ngs,
      Collect on<Earlyb rdResponse> f lteredTermStatsResults) {
     nteger largestF rstB n d = null;
    L st< nteger> b n dsToUse = null;

     f ( togramSett ngs != null) {
       nt numB ns =  togramSett ngs.getNumB ns();
      for (Earlyb rdResponse response : f lteredTermStatsResults) {
        Thr ftTermStat st csResults termStat st csResults = response.getTermStat st csResults();
        Precond  ons.c ckState(termStat st csResults.getB n ds().s ze() == numB ns,
            "expected all results to have t  sa  numB ns. "
                + "request numB ns: %s, response numB ns: %s",
            numB ns, termStat st csResults.getB n ds().s ze());

         f (termStat st csResults.getB n ds().s ze() > 0) {
           nteger f rstB n d = termStat st csResults.getB n ds().get(0);
           f (largestF rstB n d == null
              || largestF rstB n d. ntValue() < f rstB n d. ntValue()) {
            largestF rstB n d = f rstB n d;
            b n dsToUse = termStat st csResults.getB n ds();
          }
        }
      }
    }
    return b n dsToUse == null
        ? Collect ons.< nteger>emptyL st()
        // Just  n case, make a copy of t  b n ds so that   don't reuse t  sa  l st from one
        // of t  responses  're  rg ng.
        : L sts.newArrayL st(b n dsToUse);
  }

  pr vate vo d addM nCompleteB n d(L st< nteger> m nCompleteB n ds,
                                   Earlyb rdResponse response) {
    Precond  ons.c ckNotNull( togramSett ngs);
    Thr ftTermStat st csResults termStat st csResults = response.getTermStat st csResults();

     f (termStat st csResults. sSetM nCompleteB n d()) {
      // T   s t  base case. Early term nated or not, t   s t  proper m nCompleteB n d
      // that  're told to use for t  response.
      m nCompleteB n ds.add(termStat st csResults.getM nCompleteB n d());
    } else  f (termStat st csResults.getB n ds().s ze() > 0) {
      // T   s t  case w re no b ns  re complete. For t  purposes of  rg ng,   need to
      // mark all t  b n ds  n t  response as non-complete by mark ng t  "max(b n d)+1" as t 
      // last complete b n.
      // W n return ng t   rged response,   st ll have a guard for t  result ng
      // m nCompleteB n d be ng outs de of t  b n ds range, and w ll set t  returned
      // m nCompleteB n d value to null,  f t  response's b n ds end up be ng used as t  most
      // recent ones, and   need to s gn fy that none of t  b ns are complete.
       nt b nS ze = termStat st csResults.getB n ds().s ze();
       nteger maxB n d = termStat st csResults.getB n ds().get(b nS ze - 1);
      m nCompleteB n ds.add(maxB n d + 1);

      LOG.debug("Adjust ng null m nCompleteB n d for response: {},  togramSett ngs {}",
          response,  togramSett ngs);
      M N_COMPLETE_B N_ D_ADJUSTED_NULL. ncre nt();
    } else {
      // T  should only happen  n t  case w re numB ns  s set to 0.
      Precond  ons.c ckState( togramSett ngs.getNumB ns() == 0,
          "Expected numB ns set to 0. response: %s", response);
      Precond  ons.c ckState(m nCompleteB n ds. sEmpty(),
          "m nCompleteB n ds: %s", m nCompleteB n ds);

      LOG.debug("Got null m nCompleteB n d w h no b ns for response: {},  togramSett ngs {}",
          response,  togramSett ngs);
      M N_COMPLETE_B N_ D_NULL_W THOUT_B NS. ncre nt();
    }
  }

  pr vate vo d addTotalCountData(Thr ftTermRequest request, Thr ftTermResults results) {
    Thr ftTermResults termResults = termResultsMap.get(request);
     f (termResults == null) {
      termResultsMap.put(request, results);
    } else {
      termResults.setTotalCount(termResults.getTotalCount() + results.getTotalCount());
       f (termResults. sSet tadata()) {
        termResults.set tadata(
            FacetsResultsUt ls. rgeFacet tadata(termResults.get tadata(),
                results.get tadata(), null));
      }
    }
  }

  /**
   * Set results.totalCount to t  sum of h s  n only t  b ns that w ll be returned  n
   * t   rged response.
   */
  pr vate vo d adjustTotalCount(Thr ftTermResults results, L st< nteger> b n ds) {
     nt adjustedTotalCount = 0;
    L st< nteger>  togramB ns = results.get togramB ns();
     f ((b n ds != null) && ( togramB ns != null)) {
      Precond  ons.c ckState(
           togramB ns.s ze() == b n ds.s ze(),
          "Expected Thr ftTermResults to have t  sa  number of  togramB ns as b n ds set  n "
          + " Thr ftTermStat st csResults. Thr ftTermResults. togramB ns: %s, "
          + " Thr ftTermStat st csResults.b n ds: %s.",
           togramB ns, b n ds);
      for ( nt   = 0;   < b n ds.s ze(); ++ ) {
         f (b n ds.get( ) >= f rstB n d) {
          adjustedTotalCount +=  togramB ns.get( );
        }
      }
    }

    results.setTotalCount(adjustedTotalCount);
  }

  pr vate vo d add togramData(Thr ftTermRequest request,
                                Thr ftTermResults results,
                                L st< nteger> b n ds) {

     nt[] requestTotalCounts =  rgedTermRequestTotalCounts.get(request);
     f (requestTotalCounts == null) {
      requestTotalCounts = new  nt[mostRecentB n ds.s ze()];
       rgedTermRequestTotalCounts.put(request, requestTotalCounts);
    }

    // Only cons der t se results  f t y fall  nto t  mostRecentB n ds range.
    //
    // T  l st of returned b n ds  s expected to be both sorted ( n ascend ng order), and
    // cont guous, wh ch allows us to use f rstB n d to c ck  f   overlaps w h t 
    // mostRecentB n ds range.
     f (b n ds.s ze() > 0 && b n ds.get(b n ds.s ze() - 1) >= f rstB n d) {
       nt f rstB n ndex;
       f (b n ds.get(0) == f rstB n d) {
        // T  should be t  common case w n all part  ons have t  sa  b n ds,
        // no need to do a b nary search.
        f rstB n ndex = 0;
      } else {
        // T  f rstB n d must be  n t  b n ds range.   can f nd   us ng b nary search s nce
        // b n ds are sorted.
        f rstB n ndex = Collect ons.b narySearch(b n ds, f rstB n d);
        Precond  ons.c ckState(f rstB n ndex >= 0,
            "Expected to f nd f rstB n d (%s)  n t  result b n ds: %s, "
                + " togramSett ngs: %s, termRequest: %s",
            f rstB n d, b n ds,  togramSett ngs, request);
      }

      // Sk p b n ds that are before t  smallest b n d that   w ll use  n t   rged results.
      for ( nt   = f rstB n ndex;   < b n ds.s ze();  ++) {
        f nal  nteger currentB nValue = results.get togramB ns().get( );
        requestTotalCounts[  - f rstB n ndex] += currentB nValue. ntValue();
      }
    }
  }

  /**
   * Return a new Thr ftTermStat st csResults w h t  total counts  rged, and  f enabled,
   *  togram b ns  rged.
   */
  publ c Thr ftTermStat st csResults  rge() {
    Thr ftTermStat st csResults results = new Thr ftTermStat st csResults(termResultsMap);

     f ( togramSett ngs != null) {
       rge togramB ns(results);
    }

    return results;
  }


  /**
   * Takes mult ple  togram results and  rges t m so:
   * 1) Counts for t  sa  b n d (represents t  t  ) and term are sum d
   * 2) All results are re- ndexed to use t  most recent b ns found from t  un on of all b ns
   */
  pr vate vo d  rge togramB ns(Thr ftTermStat st csResults  rgedResults) {

     rgedResults.setB n ds(mostRecentB n ds);
     rgedResults.set togramSett ngs( togramSett ngs);

    setM nCompleteB n d( rgedResults);

    useMostRecentB nsForEachThr ftTermResults();
  }

  pr vate vo d setM nCompleteB n d(Thr ftTermStat st csResults  rgedResults) {
     f (mostRecentB n ds. sEmpty()) {
      Precond  ons.c ckState(m nCompleteB n d == null);
      // T   s t  case w re t  requested numB ns  s set to 0.   don't have any b n ds,
      // and t  m nCompleteB n d has to be unset.
      LOG.debug("Empty b n ds returned for  rgedResults: {}",  rgedResults);
    } else {
      Precond  ons.c ckNotNull(m nCompleteB n d);

       nteger maxB n d = mostRecentB n ds.get(mostRecentB n ds.s ze() - 1);
       f (m nCompleteB n d <= maxB n d) {
         rgedResults.setM nCompleteB n d(m nCompleteB n d);
      } else {
        // Leav ng t  m nCompleteB n d unset as    s outs de t  range of t  returned b n ds.
        LOG.debug("Computed m nCompleteB n d: {}  s out of maxB n d: {} for  rgedResults: {}",
            m nCompleteB n d,  rgedResults);
        M N_COMPLETE_B N_ D_OUT_OF_RANGE. ncre nt();
      }
    }
  }

  /**
   * C ck that t  b n ds   are us ng are cont guous.  ncre nt t  prov ded stat  f   f nd
   * a gap, as   don't expect to f nd any.
   * See: SEARCH-4362
   *
   * @param sortedB n ds most recent numB ns sorted b n ds.
   * @param b n dGapCounter stat to  ncre nt  f   see a gap  n t  b n d range.
   */
  @V s bleForTest ng
  stat c vo d c ckForB n dGaps(L st< nteger> sortedB n ds, SearchCounter b n dGapCounter) {
    for ( nt   = sortedB n ds.s ze() - 1;   > 0;  --) {
      f nal  nteger currentB n d = sortedB n ds.get( );
      f nal  nteger prev ousB n d = sortedB n ds.get(  - 1);

       f (prev ousB n d < currentB n d - 1) {
        b n dGapCounter. ncre nt();
        break;
      }
    }
  }

  /**
   * Returns a v ew conta n ng only t  last N  ems from t  l st
   */
  pr vate stat c <E> L st<E> takeLastN(L st<E> lst,  nt n) {
    Precond  ons.c ckArgu nt(n <= lst.s ze(),
        "Attempt ng to take more ele nts than t  l st has. L st s ze: %s, n: %s", lst.s ze(), n);
    return lst.subL st(lst.s ze() - n, lst.s ze());
  }

  pr vate vo d useMostRecentB nsForEachThr ftTermResults() {
    for (Map.Entry<Thr ftTermRequest, Thr ftTermResults> entry : termResultsMap.entrySet()) {
      Thr ftTermRequest request = entry.getKey();
      Thr ftTermResults results = entry.getValue();

      L st< nteger>  togramB ns = L sts.newArrayL st();
      results.set togramB ns( togramB ns);

       nt[] requestTotalCounts =  rgedTermRequestTotalCounts.get(request);
      Precond  ons.c ckNotNull(requestTotalCounts);

      for ( nt totalCount : requestTotalCounts) {
         togramB ns.add(totalCount);
      }
    }
  }

  /**
   *  rges search stats from several earlyb rd responses and puts t m  n
   * {@l nk Thr ftSearchResults} structure.
   *
   * @param responses earlyb rd responses to  rge t  search stats from
   * @return  rged search stats  ns de of {@l nk Thr ftSearchResults} structure
   */
  publ c stat c Thr ftSearchResults  rgeSearchStats(Collect on<Earlyb rdResponse> responses) {
     nt numH sProcessed = 0;
     nt numPart  onsEarlyTerm nated = 0;

    for (Earlyb rdResponse response : responses) {
      Thr ftSearchResults searchResults = response.getSearchResults();

       f (searchResults != null) {
        numH sProcessed += searchResults.getNumH sProcessed();
        numPart  onsEarlyTerm nated += searchResults.getNumPart  onsEarlyTerm nated();
      }
    }

    Thr ftSearchResults searchResults = new Thr ftSearchResults(new ArrayL st<>());
    searchResults.setNumH sProcessed(numH sProcessed);
    searchResults.setNumPart  onsEarlyTerm nated(numPart  onsEarlyTerm nated);
    return searchResults;
  }
}
