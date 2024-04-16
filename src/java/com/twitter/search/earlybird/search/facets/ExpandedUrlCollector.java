package com.tw ter.search.earlyb rd.search.facets;

 mport java.ut l.L nkedHashMap;
 mport java.ut l.L st;
 mport java.ut l.Map;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.collect. mmutableL st;
 mport com.google.common.collect. mmutableSet;

 mport org.apac .lucene.ut l.BytesRef;

 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.core.earlyb rd.facets.FacetLabelProv der;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultUrl;
 mport com.tw ter.serv ce.sp derduck.gen. d aTypes;

/**
 * A collector for collect ng expanded urls from facets. Note that t  only th ng connect ng t 
 * collector w h expanded URLs  s t  fact that   only store t  expanded url  n t  facet f elds.
 */
publ c class ExpandedUrlCollector extends AbstractFacetTermCollector {
  pr vate stat c f nal  mmutableSet<Str ng> FACET_CONTA NS_URL =  mmutableSet.of(
      Earlyb rdF eldConstant.V DEOS_FACET,
      Earlyb rdF eldConstant. MAGES_FACET,
      Earlyb rdF eldConstant.NEWS_FACET,
      Earlyb rdF eldConstant.L NKS_FACET,
      Earlyb rdF eldConstant.TW MG_FACET);

  pr vate f nal Map<Str ng, Thr ftSearchResultUrl> dedupedUrls = new L nkedHashMap<>();


  @Overr de
  protected Str ng getTermFromProv der(
      Str ng facetNa ,
      long term D,
      FacetLabelProv der prov der) {
    Str ng url = null;
     f (Earlyb rdF eldConstant.TW MG_FACET.equals(facetNa )) {
      // Spec al case extract on of  d a url for tw mg.
      FacetLabelProv der.FacetLabelAccessor photoAccessor = prov der.getLabelAccessor();
      BytesRef termPayload = photoAccessor.getTermPayload(term D);
       f (termPayload != null) {
        url = termPayload.utf8ToStr ng();
      }
    } else {
      url = prov der.getLabelAccessor().getTermText(term D);
    }
    return url;
  }

  @Overr de
  publ c boolean collect( nt doc D, long term D,  nt f eld D) {

    Str ng url = getTermFromFacet(term D, f eld D, FACET_CONTA NS_URL);
     f (url == null || url. sEmpty()) {
      return false;
    }

    Thr ftSearchResultUrl resultUrl = new Thr ftSearchResultUrl();
    resultUrl.setOr g nalUrl(url);
     d aTypes  d aType = get d aType(f ndFacetNa (f eld D));
    resultUrl.set d aType( d aType);

    //  d a l nks w ll show up tw ce:
    //   - once  n  mage/nat ve_ mage/v deo/news facets
    //   - anot r t    n t  l nks facet
    //
    // For those urls,   only want to return t   d a vers on.  f    s non- d a vers on, only
    // wr e to map  f doesn't ex st already,  f  d a vers on, overwr e any prev ous entr es.
     f ( d aType ==  d aTypes.UNKNOWN) {
       f (!dedupedUrls.conta nsKey(url)) {
        dedupedUrls.put(url, resultUrl);
      }
    } else {
      dedupedUrls.put(url, resultUrl);
    }

    return true;
  }

  @Overr de
  publ c vo d f llResultAndClear(Thr ftSearchResult result) {
    result.get tadata().setT etUrls(getExpandedUrls());
    dedupedUrls.clear();
  }

  @V s bleForTest ng
  L st<Thr ftSearchResultUrl> getExpandedUrls() {
    return  mmutableL st.copyOf(dedupedUrls.values());
  }

  /**
   * Gets t  Sp derduck  d a type for a g ven facet na .
   *
   * @param facetNa  A g ven facet na .
   * @return {@code  d aTypes} enum correspond ng to t  facet na .
   */
  pr vate stat c  d aTypes get d aType(Str ng facetNa ) {
     f (facetNa  == null) {
      return  d aTypes.UNKNOWN;
    }

    sw ch (facetNa ) {
      case Earlyb rdF eldConstant.TW MG_FACET:
        return  d aTypes.NAT VE_ MAGE;
      case Earlyb rdF eldConstant. MAGES_FACET:
        return  d aTypes. MAGE;
      case Earlyb rdF eldConstant.V DEOS_FACET:
        return  d aTypes.V DEO;
      case Earlyb rdF eldConstant.NEWS_FACET:
        return  d aTypes.NEWS;
      default:
        return  d aTypes.UNKNOWN;
    }
  }
}
