package com.tw ter.search.earlyb rd. ndex.facets;

 mport java. o. OExcept on;
 mport java.ut l.HashSet;
 mport java.ut l. erator;
 mport java.ut l.Set;

 mport org.apac .lucene.analys s.TokenStream;
 mport org.apac .lucene.analys s.tokenattr butes.CharTermAttr bute;
 mport org.apac .lucene. ndex.Term;
 mport org.apac .lucene.search.BooleanClause;
 mport org.apac .lucene.search.BooleanQuery;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.TermQuery;

 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.core.earlyb rd.facets.FacetCountState;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftTermRequest;

publ c abstract class FacetSk pL st {
  publ c stat c class Sk pTokenStream extends TokenStream {
    pr vate CharTermAttr bute termAtt = addAttr bute(CharTermAttr bute.class);

    pr vate  erator<Sc ma.F eld nfo>  erator;
    pr vate Set<Sc ma.F eld nfo> facetF elds = new HashSet<>();

    publ c vo d add(Sc ma.F eld nfo f eld) {
      t .facetF elds.add(f eld);
    }

    @Overr de
    publ c f nal boolean  ncre ntToken() throws  OExcept on {
       f ( erator == null) {
         erator = facetF elds. erator();
      }

      wh le ( erator.hasNext()) {
        Sc ma.F eld nfo f eld =  erator.next();
         f (f eld.getF eldType(). sStoreFacetSk pl st()) {
          termAtt.setEmpty();
          termAtt.append(Earlyb rdF eldConstant.getFacetSk pF eldNa (f eld.getNa ()));

          return true;
        }
      }

      return false;
    }
  }

  /**
   * Returns a Term query to search  n t  g ven facet f eld.
   */
  publ c stat c Term getSk pL stTerm(Sc ma.F eld nfo facetF eld) {
     f (facetF eld.getF eldType(). sStoreFacetSk pl st()) {
      return new Term(Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa (),
                      Earlyb rdF eldConstant.getFacetSk pF eldNa (facetF eld.getNa ()));
    }
    return null;
  }

  /**
   * Returns a d sjunct on query that searc s  n all facet f elds  n t  g ven facet count state.
   */
  publ c stat c Query getSk pL stQuery(FacetCountState facetCountState) {
    Set<Sc ma.F eld nfo> f eldsW hSk pL sts =
        facetCountState.getFacetF eldsToCountW hSk pL sts();

     f (f eldsW hSk pL sts == null || f eldsW hSk pL sts. sEmpty()) {
      return null;
    }

    Query sk pL sts;

     f (f eldsW hSk pL sts.s ze() == 1) {
      sk pL sts = new TermQuery(getSk pL stTerm(f eldsW hSk pL sts. erator().next()));
    } else {
      BooleanQuery.Bu lder d sjunct onBu lder = new BooleanQuery.Bu lder();
      for (Sc ma.F eld nfo facetF eld : f eldsW hSk pL sts) {
        d sjunct onBu lder.add(
            new TermQuery(new Term(
                Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa (),
                Earlyb rdF eldConstant.getFacetSk pF eldNa (facetF eld.getNa ()))),
            BooleanClause.Occur.SHOULD);
      }
      sk pL sts = d sjunct onBu lder.bu ld();
    }

    return sk pL sts;
  }

  /**
   * Returns a term request that can be used to get term stat st cs for t  sk p l st term
   * assoc ated w h t  prov ded facet. Returns null,  f t  FacetF eld  s conf gured to not
   * store a sk pl st.
   */
  publ c stat c Thr ftTermRequest getSk pL stTermRequest(Sc ma sc ma, Str ng facetNa ) {
    return getSk pL stTermRequest(sc ma.getFacetF eldByFacetNa (facetNa ));
  }

  /**
   * Returns a term request that can be used to get term stat st cs for t  sk p l st term
   * assoc ated w h t  prov ded facet. Returns null,  f t  FacetF eld  s conf gured to not
   * store a sk pl st.
   */
  publ c stat c Thr ftTermRequest getSk pL stTermRequest(Sc ma.F eld nfo facetF eld) {
    return facetF eld != null && facetF eld.getF eldType(). sStoreFacetSk pl st()
           ? new Thr ftTermRequest(
                Earlyb rdF eldConstant.getFacetSk pF eldNa (facetF eld.getNa ()))
             .setF eldNa (Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa ())
           : null;
  }

  /**
   * Returns a term request us ng t  spec f ed f eldNa . T   s only a temporary solut on unt l
   * Blender can access t  Sc ma to pass t  Facet DMap  nto t   thod above.
   *
   * @deprecated Temporary solut on unt l Blender
   */
  @Deprecated
  publ c stat c Thr ftTermRequest getSk pL stTermRequest(Str ng f eldNa ) {
    return new Thr ftTermRequest(Earlyb rdF eldConstant.getFacetSk pF eldNa (f eldNa ))
        .setF eldNa (Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa ());
  }
}
