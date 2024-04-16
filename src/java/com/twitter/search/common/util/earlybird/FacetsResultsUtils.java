package com.tw ter.search.common.ut l.earlyb rd;

 mport java.ut l.ArrayL st;
 mport java.ut l.Collect on;
 mport java.ut l.Collect ons;
 mport java.ut l.Comparator;
 mport java.ut l.HashMap;
 mport java.ut l. erator;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Set;

 mport com.google.common.collect.L sts;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.constants.thr ftjava.Thr ftLanguage;
 mport com.tw ter.search.common.logg ng.Debug ssageBu lder;
 mport com.tw ter.search.common.rank ng.thr ftjava.Thr ftFacetF nalSortOrder;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetCount;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetCount tadata;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetF eldRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetF eldResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetRank ngMode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftTermResults;

/**
 * A ut l y class to prov de so  funct ons for facets results process ng.
 */
publ c f nal class FacetsResultsUt ls {

  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(FacetsResultsUt ls.class);

  pr vate FacetsResultsUt ls() {
  }

  publ c stat c class FacetF eld nfo {
    publ c Thr ftFacetF eldRequest f eldRequest;
    publ c  nt totalCounts;
    publ c Map<Str ng, Thr ftFacetCount> topFacets;
    publ c L st<Map.Entry<Thr ftLanguage, Double>> language togramEntr es = L sts.newL nkedL st();
  }

  // Only return top languages  n t  language  togram wh ch sum up to at least t  much
  // rat o,  re   get f rst 80 percent les.
  publ c stat c f nal double M N_PERCENTAGE_SUM_REQU RED = 0.8;
  //  f a language rat o  s over t  number,   already return.
  publ c stat c f nal double M N_PERCENTAGE = 0.01;

  /**
   * Prepare facet f elds w h empty entr es and c ck  f   need termStats for f lter ng.
   * Returns true  f termStats f lter ng  s needed (thus t  termStats serv e call).
   * @param facetRequest T  related facet request.
   * @param facetF eld nfoMap T  facet f eld  nfo map to f ll, a map from facet type to t  facet
   * f els results  nfo.
   * @return {@code true}  f termstats request  s needed afterwards.
   */
  publ c stat c boolean prepareF eld nfoMap(
      Thr ftFacetRequest facetRequest,
      f nal Map<Str ng, FacetsResultsUt ls.FacetF eld nfo> facetF eld nfoMap) {
    boolean termStatsF lter ngMode = false;

    for (Thr ftFacetF eldRequest f eldRequest : facetRequest.getFacetF elds()) {
      FacetsResultsUt ls.FacetF eld nfo  nfo = new FacetsResultsUt ls.FacetF eld nfo();
       nfo.f eldRequest = f eldRequest;
      facetF eld nfoMap.put(f eldRequest.getF eldNa (),  nfo);
       f (f eldRequest.getRank ngMode() == Thr ftFacetRank ngMode.F LTER_W TH_TERM_STAT ST CS) {
        termStatsF lter ngMode = true;
      }
    }

    return termStatsF lter ngMode;
  }

  /**
   * Extract  nformat on from one Thr ftFacetResults  nto facetF eld nfoMap and user DWh el st.
   * @param facetResults Related facets results.
   * @param facetF eld nfoMap T  facets f eld  nfo map to f ll, a map from facet type to t  facet
   * f els results  nfo.
   * @param user DWh el st T  user wh el st to f ll.
   */
  publ c stat c vo d f llFacetF eld nfo(
      f nal Thr ftFacetResults facetResults,
      f nal Map<Str ng, FacetsResultsUt ls.FacetF eld nfo> facetF eld nfoMap,
      f nal Set<Long> user DWh el st) {

    for (Str ng facetF eld : facetResults.getFacetF elds().keySet()) {
      FacetsResultsUt ls.FacetF eld nfo  nfo = facetF eld nfoMap.get(facetF eld);
       f ( nfo.topFacets == null) {
         nfo.topFacets = new HashMap<>();
      }

      Thr ftFacetF eldResults results = facetResults.getFacetF elds().get(facetF eld);
       f (results. sSetLanguage togram()) {
         nfo.language togramEntr es.addAll(results.getLanguage togram().entrySet());
      }
      for (Thr ftFacetCount newCount : results.getTopFacets()) {
        Thr ftFacetCount resultCount =  nfo.topFacets.get(newCount.facetLabel);
         f (resultCount == null) {
           nfo.topFacets.put(newCount.facetLabel, new Thr ftFacetCount(newCount));
        } else {
          resultCount.setFacetCount(resultCount.facetCount + newCount.facetCount);
          resultCount.setS mpleCount(resultCount.s mpleCount + newCount.s mpleCount);
          resultCount.set  ghtedCount(resultCount.  ghtedCount + newCount.  ghtedCount);
          resultCount.setPenaltyCount(resultCount.penaltyCount + newCount.penaltyCount);
          //  t  could pass t  old  tadata object back or a new  rged one.
          resultCount.set tadata(
                   rgeFacet tadata(resultCount.get tadata(), newCount.get tadata(),
                                     user DWh el st));
        }
      }
       nfo.totalCounts += results.totalCount;
    }
  }

  /**
   *  rge a  tadata  nto an ex st ng one.
   * @param base tadata t   tadata to  rge  nto.
   * @param  tadataUpdate t  new  tadata to  rge.
   * @param user DWh el st user  d wh el st to f lter user  d w h.
   * @return T  updated  tadata.
   */
  publ c stat c Thr ftFacetCount tadata  rgeFacet tadata(
          f nal Thr ftFacetCount tadata base tadata,
          f nal Thr ftFacetCount tadata  tadataUpdate,
          f nal Set<Long> user DWh el st) {
    Thr ftFacetCount tadata  rged tadata = base tadata;
     f ( tadataUpdate != null) {
      Str ng  rgedExplanat on = null;
       f ( rged tadata != null) {
         f ( rged tadata.maxT epCred <  tadataUpdate.maxT epCred) {
           rged tadata.setMaxT epCred( tadataUpdate.maxT epCred);
        }

         f ( rged tadata. sSetExplanat on()) {
           rgedExplanat on =  rged tadata.getExplanat on();
           f ( tadataUpdate. sSetExplanat on()) {
             rgedExplanat on += "\n" +  tadataUpdate.getExplanat on();
          }
        } else  f ( tadataUpdate. sSetExplanat on()) {
           rgedExplanat on =  tadataUpdate.getExplanat on();
        }

         f ( rged tadata.getStatus d() == -1) {
           f (LOG. sDebugEnabled()) {
            LOG.debug("status  d  n facet count  tadata  s -1: " +  rged tadata);
          }
           rged tadata =  tadataUpdate;
        } else  f ( tadataUpdate.getStatus d() != -1
            &&  tadataUpdate.getStatus d() <  rged tadata.getStatus d()) {
          // keep t  oldest t et,  e. t  lo st status  D
           rged tadata =  tadataUpdate;
        } else  f ( tadataUpdate.getStatus d() ==  rged tadata.getStatus d()) {
           f ( rged tadata.getTw terUser d() == -1) {
            //  n t  case   d dn't f nd t  user  n a prev ous part  on yet
            // only update t  user  f t  status  d matc s
             rged tadata.setTw terUser d( tadataUpdate.getTw terUser d());
             rged tadata.setDontF lterUser( tadataUpdate. sDontF lterUser());
          }
           f (! rged tadata. sSetStatusLanguage()) {
             rged tadata.setStatusLanguage( tadataUpdate.getStatusLanguage());
          }
        }
         f (! rged tadata. sSetNat vePhotoUrl() &&  tadataUpdate. sSetNat vePhotoUrl()) {
           rged tadata.setNat vePhotoUrl( tadataUpdate.getNat vePhotoUrl());
        }
      } else {
         rged tadata =  tadataUpdate;
      }

      // t  w ll not set an explanat on  f ne  r old tadata nor  tadataUpdate
      // had an explanat on
       f ( rgedExplanat on != null) {
         rged tadata.setExplanat on( rgedExplanat on);
      }

       f (user DWh el st != null) {
        // result must not be null now because of t   f above
         f ( rged tadata.getTw terUser d() != -1 && ! rged tadata. sDontF lterUser()) {
           rged tadata.setDontF lterUser(
              user DWh el st.conta ns( rged tadata.getTw terUser d()));
        }
      }
    }

    return  rged tadata;
  }

  /**
   * Appends all tw mg results to t   mage results. Opt onally resorts t   mage results  f
   * a comparator  s passed  n.
   * Also computes t  sums of totalCount, totalScore, totalPenalty.
   */
  publ c stat c vo d  rgeTw mgResults(Thr ftFacetResults facetResults,
                                       Comparator<Thr ftFacetCount> opt onalSortComparator) {
     f (facetResults == null || !facetResults. sSetFacetF elds()) {
      return;
    }

    Thr ftFacetF eldResults  mageResults =
        facetResults.getFacetF elds().get(Earlyb rdF eldConstant. MAGES_FACET);
    Thr ftFacetF eldResults tw mgResults =
        facetResults.getFacetF elds().remove(Earlyb rdF eldConstant.TW MG_FACET);
     f ( mageResults == null) {
       f (tw mgResults != null) {
        facetResults.getFacetF elds().put(Earlyb rdF eldConstant. MAGES_FACET, tw mgResults);
      }
      return;
    }

     f (tw mgResults != null) {
       mageResults.setTotalCount( mageResults.getTotalCount() + tw mgResults.getTotalCount());
       mageResults.setTotalPenalty( mageResults.getTotalPenalty() + tw mgResults.getTotalPenalty());
       mageResults.setTotalScore( mageResults.getTotalScore() + tw mgResults.getTotalScore());
      for (Thr ftFacetCount count : tw mgResults.getTopFacets()) {
         mageResults.addToTopFacets(count);
      }
       f (opt onalSortComparator != null) {
        Collect ons.sort( mageResults.topFacets, opt onalSortComparator);
      }
    }
  }

  /**
   * Dedup tw mg facets.
   *
   * Tw mg facet uses t  status  D as t  facet label,  nstead of t  tw mg URL, a.k.a.
   * nat ve photo URL.    s poss ble to have t  sa  tw mg URL appear ng  n two d fferent
   * facet label (RT style ret et? copy & paste t  tw mg URL?). T refore, to dedup tw mg
   * facet correctly,   need to look at Thr ftFacetCount. tadata.nat vePhotoUrl
   *
   * @param dedupSet A set hold ng t  nat ve URLs from t  tw mg facetF eldResults. By hav ng
   *                 t  caller pass ng  n t  set,   allows t  caller to dedup t  facet
   *                 across d fferent Thr ftFacetF eldResults.
   * @param facetF eldResults T  tw mg facet f eld results to be debupped
   * @param debug ssageBu lder
   */
  publ c stat c vo d dedupTw mgFacet(Set<Str ng> dedupSet,
                                     Thr ftFacetF eldResults facetF eldResults,
                                     Debug ssageBu lder debug ssageBu lder) {
     f (facetF eldResults == null || facetF eldResults.getTopFacets() == null) {
      return;
    }

     erator<Thr ftFacetCount>  erator = facetF eldResults.getTopFacets erator();

    wh le ( erator.hasNext()) {
      Thr ftFacetCount count =  erator.next();
       f (count. sSet tadata() && count.get tadata(). sSetNat vePhotoUrl()) {
        Str ng nat veUrl = count.get tadata().getNat vePhotoUrl();

         f (dedupSet.conta ns(nat veUrl)) {
           erator.remove();
          debug ssageBu lder.deta led("dedupTw mgFacet removed %s", nat veUrl);
        } else {
          dedupSet.add(nat veUrl);
        }
      }
    }


  }

  pr vate stat c f nal class LanguageCount {
    pr vate f nal Thr ftLanguage lang;
    pr vate f nal double count;
    pr vate LanguageCount(Thr ftLanguage lang, double count) {
      t .lang = lang;
      t .count = count;
    }
  }

  /**
   * Calculate t  top languages and store t m  n t  results.
   */
  publ c stat c vo d f llTopLanguages(FacetsResultsUt ls.FacetF eld nfo  nfo,
                                      f nal Thr ftFacetF eldResults results) {
    double sumForLanguage = 0.0;
    double[] sums = new double[Thr ftLanguage.values().length];
    for (Map.Entry<Thr ftLanguage, Double> entry :  nfo.language togramEntr es) {
      sumForLanguage += entry.getValue();
       f (entry.getKey() == null) {
        // EB m ght be sett ng null key for unknown language. SEARCH-1294
        cont nue;
      }
      sums[entry.getKey().getValue()] += entry.getValue();
    }
     f (sumForLanguage == 0.0) {
      return;
    }
    L st<LanguageCount> langCounts = new ArrayL st<>(Thr ftLanguage.values().length);
    for ( nt   = 0;   < sums.length;  ++) {
       f (sums[ ] > 0.0) {
        // Thr ftLanguage.f ndByValue() m ght return null, wh ch should fall back to UNKNOWN.
        Thr ftLanguage lang = Thr ftLanguage.f ndByValue( );
        lang = lang == null ? Thr ftLanguage.UNKNOWN : lang;
        langCounts.add(new LanguageCount(lang, sums[ ]));
      }
    }
    Collect ons.sort(langCounts, (left, r ght) -> Double.compare(r ght.count, left.count));
    double percentageSum = 0.0;
    Map<Thr ftLanguage, Double> language togramMap =
        new HashMap<>(langCounts.s ze());
     nt numAdded = 0;
    for (LanguageCount langCount : langCounts) {
       f (langCount.count == 0.0) {
        break;
      }
      double percentage = langCount.count / sumForLanguage;
       f (percentageSum > M N_PERCENTAGE_SUM_REQU RED
          && percentage < M N_PERCENTAGE && numAdded >= 3) {
        break;
      }
      language togramMap.put(langCount.lang, percentage);
      percentageSum += percentage;
      numAdded++;
    }
    results.setLanguage togram(language togramMap);
  }

  /**
   * Replace "p.tw mg.com/" part of t  nat ve photo (tw mg) URL w h "pbs.tw mg.com/ d a/".
   *   need to do t  because of blobstore and  's suppose to be a temporary  asure. T 
   * code should be removed once   ver f ed that all nat ve photo URL be ng sent to Search
   * are pref xed w h "pbs.tw mg.com/ d a/" and no nat ve photo URL  n    ndex conta ns
   * "p.tw mg.com/"
   *
   * Please see SEARCH-783 and EVENTS-539 for more deta ls.
   *
   * @param response response conta n ng t  facet results
   */
  publ c stat c vo d f xNat vePhotoUrl(Earlyb rdResponse response) {
     f (response == null
        || !response. sSetFacetResults()
        || !response.getFacetResults(). sSetFacetF elds()) {
      return;
    }

    for (Map.Entry<Str ng, Thr ftFacetF eldResults> facetMapEntry
        : response.getFacetResults().getFacetF elds().entrySet()) {
      f nal Str ng facetResultF eld = facetMapEntry.getKey();

       f (Earlyb rdF eldConstant.TW MG_FACET.equals(facetResultF eld)
          || Earlyb rdF eldConstant. MAGES_FACET.equals(facetResultF eld)) {
        Thr ftFacetF eldResults facetF eldResults = facetMapEntry.getValue();
        for (Thr ftFacetCount facetCount : facetF eldResults.getTopFacets()) {
          replacePhotoUrl(facetCount.get tadata());
        }
      }
    }
  }

  /**
   * Replace "p.tw mg.com/" part of t  nat ve photo (tw mg) URL w h "pbs.tw mg.com/ d a/".
   *   need to do t  because of blobstore and  's suppose to be a temporary  asure. T 
   * code should be removed once   ver f ed that all nat ve photo URL be ng sent to Search
   * are pref xed w h "pbs.tw mg.com/ d a/" and no nat ve photo URL  n    ndex conta ns
   * "p.tw mg.com/"
   *
   * Please see SEARCH-783 and EVENTS-539 for more deta ls.
   *
   * @param termResultsCollect on collect on of Thr ftTermResults conta n ng t  nat ve photo URL
   */
  publ c stat c vo d f xNat vePhotoUrl(Collect on<Thr ftTermResults> termResultsCollect on) {
     f (termResultsCollect on == null) {
      return;
    }

    for (Thr ftTermResults termResults : termResultsCollect on) {
       f (!termResults. sSet tadata()) {
        cont nue;
      }
      replacePhotoUrl(termResults.get tadata());
    }
  }

  /**
   *  lper funct on for f xNat vePhotoUrl()
   */
  pr vate stat c vo d replacePhotoUrl(Thr ftFacetCount tadata  tadata) {
     f ( tadata != null
        &&  tadata. sSetNat vePhotoUrl()) {
      Str ng nat vePhotoUrl =  tadata.getNat vePhotoUrl();
      nat vePhotoUrl = nat vePhotoUrl.replace("://p.tw mg.com/", "://pbs.tw mg.com/ d a/");
       tadata.setNat vePhotoUrl(nat vePhotoUrl);
    }
  }

  /**
   * Deepcopy of an Earlyb rdResponse w hout explanat on
   */
  publ c stat c Earlyb rdResponse deepCopyW houtExplanat on(Earlyb rdResponse facetsResponse) {
     f (facetsResponse == null) {
      return null;
    } else  f (!facetsResponse. sSetFacetResults()
        || facetsResponse.getFacetResults().getFacetF eldsS ze() == 0) {
      return facetsResponse.deepCopy();
    }
    Earlyb rdResponse copy = facetsResponse.deepCopy();
    for (Map.Entry<Str ng, Thr ftFacetF eldResults> entry
        : copy.getFacetResults().getFacetF elds().entrySet()) {
       f (entry.getValue().getTopFacetsS ze() > 0) {
        for (Thr ftFacetCount fc : entry.getValue().getTopFacets()) {
          fc.get tadata().unsetExplanat on();
        }
      }
    }
    return copy;
  }

  /**
   * Returns a comparator used to compare facet counts by call ng
   * getFacetCountComparator(Thr ftFacetF nalSortOrder).  T  sort order  s determ ned by
   * t  facetRank ngOpt ons on t  facet request.
   */
  publ c stat c Comparator<Thr ftFacetCount> getFacetCountComparator(
      Thr ftFacetRequest facetRequest) {

    Thr ftFacetF nalSortOrder sortOrder = Thr ftFacetF nalSortOrder.SCORE;

     f (facetRequest. sSetFacetRank ngOpt ons()
        && facetRequest.getFacetRank ngOpt ons(). sSetF nalSortOrder()) {
      sortOrder = facetRequest.getFacetRank ngOpt ons().getF nalSortOrder();
    }

    return getFacetCountComparator(sortOrder);
  }

  /**
   * Returns a comparator us ng t  spec f ed order.
   */
  publ c stat c Comparator<Thr ftFacetCount> getFacetCountComparator(
      Thr ftFacetF nalSortOrder sortOrder) {

    sw ch (sortOrder) {
      case S MPLE_COUNT:   return S MPLE_COUNT_COMPARATOR;
      case SCORE:          return SCORE_COMPARATOR;
      case CREATED_AT:     return CREATED_AT_COMPARATOR;
      case WE GHTED_COUNT: return WE GHTED_COUNT_COMPARATOR;
      default:             return SCORE_COMPARATOR;
    }
  }

  pr vate stat c f nal Comparator<Thr ftFacetCount> S MPLE_COUNT_COMPARATOR =
      (count1, count2) -> {
         f (count1.s mpleCount > count2.s mpleCount) {
          return 1;
        } else  f (count1.s mpleCount < count2.s mpleCount) {
          return -1;
        }

        return count1.facetLabel.compareTo(count2.facetLabel);
      };

  pr vate stat c f nal Comparator<Thr ftFacetCount> WE GHTED_COUNT_COMPARATOR =
      (count1, count2) -> {
         f (count1.  ghtedCount > count2.  ghtedCount) {
          return 1;
        } else  f (count1.  ghtedCount < count2.  ghtedCount) {
          return -1;
        }

        return S MPLE_COUNT_COMPARATOR.compare(count1, count2);
      };

  pr vate stat c f nal Comparator<Thr ftFacetCount> SCORE_COMPARATOR =
      (count1, count2) -> {
         f (count1.score > count2.score) {
          return 1;
        } else  f (count1.score < count2.score) {
          return -1;
        }
        return S MPLE_COUNT_COMPARATOR.compare(count1, count2);
      };

  pr vate stat c f nal Comparator<Thr ftFacetCount> CREATED_AT_COMPARATOR =
      (count1, count2) -> {
         f (count1. sSet tadata() && count1.get tadata(). sSetCreated_at()
            && count2. sSet tadata() && count2.get tadata(). sSetCreated_at()) {
          // more recent  ems have h g r created_at values
           f (count1.get tadata().getCreated_at() > count2.get tadata().getCreated_at()) {
            return 1;
          } else  f (count1.get tadata().getCreated_at() < count2.get tadata().getCreated_at()) {
            return -1;
          }
        }

        return SCORE_COMPARATOR.compare(count1, count2);
      };
}
