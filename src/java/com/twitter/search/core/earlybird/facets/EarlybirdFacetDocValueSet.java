package com.tw ter.search.core.earlyb rd.facets;

 mport java.ut l.Map;
 mport java.ut l.Map.Entry;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene.facet.FacetsConf g;
 mport org.apac .lucene. ndex.ReaderUt l;
 mport org.apac .lucene. ndex.SortedSetDocValues;
 mport org.apac .lucene.ut l.BytesRef;
 mport org.apac .lucene.ut l.BytesRefBu lder;

 mport com.tw ter.search.core.earlyb rd. ndex. nverted. nverted ndex;

publ c class Earlyb rdFacetDocValueSet extends SortedSetDocValues {
  pr vate f nal AbstractFacetCount ngArray count ngArray;
  pr vate f nal  nverted ndex[] labelProv ders;
  pr vate f nal Str ng[] f eldNa s;
  pr vate f nal  nt[] starts;
  pr vate f nal BytesRefBu lder ordCac ;
  pr vate  nt totalTerms;
  pr vate  nt doc D = -1;
  pr vate  nt currentFacet = FacetCount ngArray.UNASS GNED;
  pr vate  nt po nter = -1;
  pr vate boolean hasMoreOrds = false;

  publ c stat c f nal Str ng F ELD_NAME = FacetsConf g.DEFAULT_ NDEX_F ELD_NAME;

  /**
   * Creates a new Earlyb rdFacetDocValueSet from t  prov ded FacetCount ngArray.
   */
  publ c Earlyb rdFacetDocValueSet(AbstractFacetCount ngArray count ngArray,
                                   Map<Str ng, FacetLabelProv der> labelProv derMap,
                                   Facet DMap facet dMap) {
    t .count ngArray = count ngArray;
    labelProv ders = new  nverted ndex[facet dMap.getNumberOfFacetF elds()];
    f eldNa s = new Str ng[facet dMap.getNumberOfFacetF elds()];
    for (Entry<Str ng, FacetLabelProv der> entry : labelProv derMap.entrySet()) {
      FacetLabelProv der labelProv der = entry.getValue();
       f (labelProv der  nstanceof  nverted ndex) {
        Facet DMap.FacetF eld facetF eld = facet dMap.getFacetF eldByFacetNa (entry.getKey());
         f (facetF eld != null) {
          labelProv ders[facetF eld.getFacet d()] = ( nverted ndex) labelProv der;
          f eldNa s[facetF eld.getFacet d()] = entry.getKey();
        }
      }
    }

    starts = new  nt[labelProv ders.length + 1];    // bu ld starts array
    ordCac  = new BytesRefBu lder();
    totalTerms = 0;

    for ( nt   = 0;   < labelProv ders.length; ++ ) {
       f (labelProv ders[ ] != null) {
        starts[ ] = totalTerms;
         nt termCount = labelProv ders[ ].getNumTerms();
        totalTerms += termCount;
      }
    }

    // added to so that mapp ng from ord to  ndex works v a ReaderUt l.sub ndex
    starts[labelProv ders.length] = totalTerms;
  }

  pr vate long encodeOrd( nt f eld d,  nt term d) {
    assert starts[f eld d] + term d < starts[f eld d + 1];
    return starts[f eld d] + term d;
  }

  @Overr de
  publ c long nextOrd() {
     f (!hasMoreOrds || currentFacet == FacetCount ngArray.UNASS GNED) {
      return SortedSetDocValues.NO_MORE_ORDS;
    }

    // only 1 facet val
     f (!FacetCount ngArray. sPo nter(currentFacet)) {
       nt term d = FacetCount ngArray.decodeTerm D(currentFacet);
       nt f eld d = FacetCount ngArray.decodeF eld D(currentFacet);
      hasMoreOrds = false;
      return encodeOrd(f eld d, term d);
    }

    // mult ple facets, follow t  po nter to f nd all facets  n t  facetsPool.
     f (po nter == -1) {
      po nter = FacetCount ngArray.decodePo nter(currentFacet);
    }
     nt facet D = count ngArray.getFacetsPool().get(po nter);
     nt term d = FacetCount ngArray.decodeTerm D(facet D);
     nt f eld d = FacetCount ngArray.decodeF eld D(facet D);

    hasMoreOrds = FacetCount ngArray. sPo nter(facet D);
    po nter++;
    return encodeOrd(f eld d, term d);
  }

  @Overr de
  publ c BytesRef lookupOrd(long ord) {
     nt  dx = ReaderUt l.sub ndex(( nt) ord, t .starts);
     f (labelProv ders[ dx] != null) {
       nt term D = ( nt) ord - starts[ dx];
      BytesRef term = new BytesRef();
      labelProv ders[ dx].getTerm(term D, term);
      Str ng na  = f eldNa s[ dx];
      Str ng val = FacetsConf g.pathToStr ng(new Str ng[] {na , term.utf8ToStr ng()});
      ordCac .copyChars(val);
    } else {
      ordCac .copyChars("");
    }
    return ordCac .get();
  }

  @Overr de
  publ c long lookupTerm(BytesRef key) {
    throw new UnsupportedOperat onExcept on();
  }

  @Overr de
  publ c long getValueCount() {
    return totalTerms;
  }

  @Overr de
  publ c  nt doc D() {
    return doc D;
  }

  @Overr de
  publ c  nt nextDoc() {
    return ++doc D;
  }

  @Overr de
  publ c  nt advance( nt target) {
    Precond  ons.c ckState(target >= doc D);
    doc D = target;
    currentFacet = count ngArray.getFacet(doc D);
    po nter = -1;
    hasMoreOrds = true;
    return doc D;
  }

  @Overr de
  publ c boolean advanceExact( nt target) {
    return advance(target) != FacetCount ngArray.UNASS GNED;
  }

  @Overr de
  publ c long cost() {
    return totalTerms;
  }
}
