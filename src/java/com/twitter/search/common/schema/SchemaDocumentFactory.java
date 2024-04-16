package com.tw ter.search.common.sc ma;

 mport java. o. OExcept on;
 mport java. o.Str ngReader;
 mport java.ut l.Collect ons;
 mport java.ut l.L st;
 mport java.ut l.Set;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. mmutableL st;
 mport com.google.common.collect.Sets;

 mport org.apac .lucene.analys s.Analyzer;
 mport org.apac .lucene.analys s.TokenStream;
 mport org.apac .lucene.analys s.tokenattr butes.CharTermAttr bute;
 mport org.apac .lucene.analys s.tokenattr butes.TermToBytesRefAttr bute;
 mport org.apac .lucene.docu nt.Docu nt;
 mport org.apac .lucene.docu nt.F eld;
 mport org.apac .lucene.facet.sortedset.SortedSetDocValuesFacetF eld;
 mport org.apac .lucene.ut l.BytesRef;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.text.token.Tw terTokenStream;
 mport com.tw ter.search.common.sc ma.base.Earlyb rdF eldType;
 mport com.tw ter.search.common.sc ma.base. ndexedNu r cF eldSett ngs;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftDocu nt;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftF eld;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftF eldData;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftGeoCoord nate;
 mport com.tw ter.search.common.ut l.analys s. ntTermAttr bute;
 mport com.tw ter.search.common.ut l.analys s.LongTermAttr bute;
 mport com.tw ter.search.common.ut l.analys s.SortableLongTermAttr bute;
 mport com.tw ter.search.common.ut l.spat al.GeoUt l;
 mport com.tw ter.search.common.ut l.text.H ghFrequencyTermPa rs;
 mport com.tw ter.search.common.ut l.text.Om NormTextF eld;
 mport com.tw ter.search.common.ut l.text.S ngleTokenStream;

/**
 * A docu nt factory that converts {@l nk Thr ftDocu nt}  nto Lucene {@l nk Docu nt}s
 * us ng t  prov ded {@l nk com.tw ter.search.common.sc ma.base.Sc ma}.
 */
publ c class Sc maDocu ntFactory {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Sc maDocu ntFactory.class);

  pr vate f nal Sc ma sc ma;
  pr vate f nal  mmutableL st<TokenStreamRewr er> tokenStreamRewr ers;

  /**
   * Creates a Sc maDocu ntFactory w h a sc ma and t  tokenStreamRewr ers.
   *
   * @param tokenStreamRewr ers a l st of token stream rewr ers, wh ch w ll be appl ed  n order.
   */
  publ c Sc maDocu ntFactory(
      Sc ma sc ma,
      L st<TokenStreamRewr er> tokenStreamRewr ers) {
    t .sc ma = sc ma;
    t .tokenStreamRewr ers =  mmutableL st.copyOf(tokenStreamRewr ers);
  }

  /**
   * Creates a Sc maDocu ntFactory w h no tokenStreamRewr ers.
   */
  publ c Sc maDocu ntFactory(Sc ma sc ma) {
    t (sc ma, Collect ons.EMPTY_L ST);
  }

  publ c f nal Docu nt newDocu nt(Thr ftDocu nt docu nt) throws  OExcept on {
    return  nnerNewDocu nt(docu nt);
  }

  /**
   * Create a Lucene docu nt from t  Thr ftDocu nt.
   */
  @V s bleForTest ng
  publ c Docu nt  nnerNewDocu nt(Thr ftDocu nt docu nt) throws  OExcept on {
    Docu nt luceneDocu nt = new Docu nt();
    Set<Str ng> hfTerms = Sets.newHashSet();
    Set<Str ng> hfPhrases = Sets.newHashSet();

    Analyzer defaultAnalyzer = sc ma.getDefaultAnalyzer(docu nt.getDefaultAnalyzerOverr de());

    for (Thr ftF eld f eld : docu nt.getF elds()) {
      boolean successful = false;
      try {
        addLuceneF elds(f eld, defaultAnalyzer, luceneDocu nt, hfTerms, hfPhrases);
        successful = true;
      } f nally {
         f (!successful) {
          LOG.warn("Unexpected except on wh le try ng to add f eld. F eld  D: "
              + f eld.getF eldConf g d() + " F eld Na : "
              + sc ma.getF eldNa (f eld.getF eldConf g d()));
        }
      }
    }

    for (Str ng token : hfTerms) {
      for (Str ng token2 : hfTerms) {
         f (token.compareTo(token2) < 0) {
          luceneDocu nt.add(new F eld( mmutableSc ma.HF_TERM_PA RS_F ELD,
                                          H ghFrequencyTermPa rs.createPa r(token, token2),
                                          Om NormTextF eld.TYPE_NOT_STORED));
        }
      }
    }

    for (Str ng phrase : hfPhrases) {
      // Tokens  n t  phrase set are not terms and have already been processed w h
      // H ghFrequencyTermPa rs.createPhrasePa r.
      luceneDocu nt.add(new F eld( mmutableSc ma.HF_PHRASE_PA RS_F ELD, phrase,
                                      Om NormTextF eld.TYPE_NOT_STORED));
    }

    return sc ma.getFacetsConf g().bu ld(luceneDocu nt);
  }

  pr vate vo d addLuceneF elds(Thr ftF eld f eld, Analyzer analyzer, Docu nt doc,
                               Set<Str ng> hfTerms, Set<Str ng> hfPhrases) throws  OExcept on {
    Sc ma.F eld nfo f eld nfo =
        sc ma.getF eld nfo(f eld.getF eldConf g d(), f eld.getF eldConf gOverr de());

     f (f eld nfo == null) {
      // f eld not def ned  n sc ma - sk p  
      return;
    }

    Thr ftF eldData f eldData = f eld.getF eldData();
     f (f eld nfo.getF eldType().getCsfType() !=  null) {
      addCSFF eld(doc, f eld nfo, f eldData);
      return;
    }

    // C ck ng wh ch data type  s set  s not suff c ent  re.   also need to c ck sc ma to
    // see what t  type t  f eld  s conf gured to be. See SEARCH-5173 for more deta ls.
    // T  problem  s that P g, wh le convert ng Tuples to Thr ft, sets all pr m  ve type
    // f elds to 0. ( .e. t   sSet calls w ll return true).
     ndexedNu r cF eldSett ngs nu r cSett ngs =
        f eld nfo.getF eldType().getNu r cF eldSett ngs();
     f (f eldData. sSetTokenStreamValue()) {
      addTokenF eld(doc, hfTerms, hfPhrases, f eld nfo, f eldData);
    } else  f (f eldData. sSetStr ngValue()) {
      addStr ngF eld(analyzer, doc, hfTerms, hfPhrases, f eld nfo, f eldData);
    } else  f (f eldData. sSetBytesValue()) {
      addBytesF eld(doc, f eld nfo, f eldData);
    } else  f (f eldData. sSetGeoCoord nate()) {
      addGeoF eld(doc, f eld nfo, f eldData);
    } else  f (nu r cSett ngs != null) {
      // handle nu r c f elds.
      sw ch (nu r cSett ngs.getNu r cType()) {
        case  NT:
          Precond  ons.c ckState(f eldData. sSet ntValue(),
              " nt f eld does not have  nt value set. F eld na : %s", f eld nfo.getNa ());
          add ntF eld(doc, f eld nfo, f eldData);
          break;
        case LONG:
          Precond  ons.c ckState(f eldData. sSetLongValue(),
              "Long f eld does not have long value set. F eld na : %s", f eld nfo.getNa ());
          addLongF eld(doc, f eld nfo, f eldData);
          break;
        case FLOAT:
          Precond  ons.c ckState(f eldData. sSetFloatValue(),
              "Float f eld does not have float value set. F eld na : %s ", f eld nfo.getNa ());
          addFloatF eld();
          break;
        case DOUBLE:
          Precond  ons.c ckState(f eldData. sSetDoubleValue(),
              "Double f eld does not have double value set. F eld na : %s", f eld nfo.getNa ());
          addDoubleF eld();
          break;
        default:
          throw new UnsupportedOperat onExcept on("Earlyb rd does not know how to handle f eld "
              + f eld.getF eldConf g d() + " " + f eld);
      }
    } else {
      throw new UnsupportedOperat onExcept on("Earlyb rd does not know how to handle f eld "
          + f eld.getF eldConf g d() + " " + f eld);
    }
  }

  pr vate vo d addCSFF eld(Docu nt doc, Sc ma.F eld nfo f eld nfo, Thr ftF eldData f eldData) {
     f (f eld nfo.getF eldType().getCsfF xedLengthNumValuesPerDoc() > 1) {

      // As an opt m zat on, TB naryProtocol stores a byte array f eld as a part of a larger byte
      // array f eld.  Must call f eldData.getBytesValue().  f eldData.bytesValue.array() w ll
      // return extraneous data. See: SEARCH-3996
      doc.add(new F eld(f eld nfo.getNa (), f eldData.getBytesValue(), f eld nfo.getF eldType()));
    } else {
      doc.add(new CSFF eld(f eld nfo.getNa (), f eld nfo.getF eldType(), f eldData));
    }
  }

  pr vate vo d addTokenF eld(
      Docu nt doc,
      Set<Str ng> hfTerms,
      Set<Str ng> hfPhrases,
      Sc ma.F eld nfo f eld nfo,
      Thr ftF eldData f eldData) throws  OExcept on {
    Tw terTokenStream tw terTokenStream
        = f eld nfo.getF eldType().getTokenStreamSer al zer().deser al ze(
        f eldData.getTokenStreamValue(), f eldData.getStr ngValue());

    try {
      for (TokenStreamRewr er rewr er : tokenStreamRewr ers) {
        tw terTokenStream = rewr er.rewr e(f eld nfo, tw terTokenStream);
      }

      expandStream(doc, f eld nfo, tw terTokenStream, hfTerms, hfPhrases);
      doc.add(new F eld(f eld nfo.getNa (), tw terTokenStream, f eld nfo.getF eldType()));
    } f nally {
      tw terTokenStream.close();
    }
  }

  pr vate vo d addStr ngF eld(Analyzer analyzer, Docu nt doc, Set<Str ng> hfTerms,
                              Set<Str ng> hfPhrases, Sc ma.F eld nfo f eld nfo,
                              Thr ftF eldData f eldData) {
    doc.add(new F eld(f eld nfo.getNa (), f eldData.getStr ngValue(), f eld nfo.getF eldType()));
     f (f eld nfo.getF eldType().token zed()) {
      try {
        TokenStream tokenStream = analyzer.tokenStream(f eld nfo.getNa (),
                new Str ngReader(f eldData.getStr ngValue()));
        try {
          expandStream(
              doc,
              f eld nfo,
              tokenStream,
              hfTerms,
              hfPhrases);
        } f nally {
          tokenStream.close();
        }
      } catch ( OExcept on e) {
        LOG.error(" OExcept on expand ng token stream", e);
      }
    } else {
      addFacetF eld(doc, f eld nfo, f eldData.getStr ngValue());
    }
  }

  pr vate vo d addBytesF eld(Docu nt doc, Sc ma.F eld nfo f eld nfo, Thr ftF eldData f eldData) {
    doc.add(new F eld(f eld nfo.getNa (), f eldData.getBytesValue(), f eld nfo.getF eldType()));
  }

  pr vate vo d add ntF eld(Docu nt doc, Sc ma.F eld nfo f eld nfo,
                           Thr ftF eldData f eldData) {
     nt value = f eldData.get ntValue();
    addFacetF eld(doc, f eld nfo, Str ng.valueOf(value));

     f (f eld nfo.getF eldType().getNu r cF eldSett ngs() == null) {
      // No Nu r cF eldSett ngs. Even though t  data  s nu r c, t  f eld  s not
      // really a nu r cal f eld. Just add as a str ng.
      doc.add(new F eld(f eld nfo.getNa (), Str ng.valueOf(value), f eld nfo.getF eldType()));
    } else  f (f eld nfo.getF eldType().getNu r cF eldSett ngs(). sUseTw terFormat()) {
      add ntTermAttr buteF eld(value, f eld nfo, doc);
    } else {
      // Use lucene style nu r cal f elds
      doc.add(Nu r cF eld.new ntF eld(f eld nfo.getNa (), value));
    }
  }

  pr vate vo d add ntTermAttr buteF eld( nt value,
                                        Sc ma.F eld nfo f eld nfo,
                                        Docu nt doc) {
    S ngleTokenStream s ngleToken = new S ngleTokenStream();
     ntTermAttr bute termAtt = s ngleToken.addAttr bute( ntTermAttr bute.class);
    termAtt.setTerm(value);
    doc.add(new F eld(f eld nfo.getNa (), s ngleToken, f eld nfo.getF eldType()));
  }

  pr vate vo d addLongF eld(Docu nt doc, Sc ma.F eld nfo f eld nfo,
                            Thr ftF eldData f eldData) {
    long value = f eldData.getLongValue();
    addFacetF eld(doc, f eld nfo, Str ng.valueOf(value));

     f (f eld nfo.getF eldType().getNu r cF eldSett ngs() == null) {
      // No Nu r cF eldSett ngs. Even though t  data  s nu r c, t  f eld  s not
      // really a nu r cal f eld. Just add as a str ng.
      doc.add(new F eld(f eld nfo.getNa (), Str ng.valueOf(value), f eld nfo.getF eldType()));
    } else  f (f eld nfo.getF eldType().getNu r cF eldSett ngs(). sUseTw terFormat()) {
      // Tw ter style nu r cal f eld: use LongTermAttr bute
      addLongTermAttr buteF eld(value, f eld nfo, doc);
    } else {
      // Use lucene style nu r cal f elds
      doc.add(Nu r cF eld.newLongF eld(f eld nfo.getNa (), value));
    }
  }

  pr vate vo d addLongTermAttr buteF eld(long value,
                                         Sc ma.F eld nfo f eld nfo,
                                         Docu nt doc) {
    S ngleTokenStream s ngleToken = new S ngleTokenStream();
    boolean useSortableEncod ng =
        f eld nfo.getF eldType().getNu r cF eldSett ngs(). sUseSortableEncod ng();

     f (useSortableEncod ng) {
      SortableLongTermAttr bute termAtt = s ngleToken.addAttr bute(SortableLongTermAttr bute.class);
      termAtt.setTerm(value);
    } else {
      LongTermAttr bute termAtt = s ngleToken.addAttr bute(LongTermAttr bute.class);
      termAtt.setTerm(value);
    }
    doc.add(new F eld(f eld nfo.getNa (), s ngleToken, f eld nfo.getF eldType()));
  }

  pr vate vo d addFloatF eld() {
    throw new UnsupportedOperat onExcept on("Earlyb rd does not support float values yet.");
  }

  pr vate vo d addDoubleF eld() {
    throw new UnsupportedOperat onExcept on("Earlyb rd does not support double values yet.");
  }

  pr vate vo d addGeoF eld(Docu nt doc, Sc ma.F eld nfo f eld nfo, Thr ftF eldData f eldData) {
    Thr ftGeoCoord nate coord = f eldData.getGeoCoord nate();
     f (GeoUt l.val dateGeoCoord nates(coord.getLat(), coord.getLon())) {
      GeoUt l.f llGeoF elds(doc, f eld nfo.getNa (),
          coord.getLat(), coord.getLon(), coord.getAccuracy());
    }
  }

  pr vate vo d addFacetF eld(Docu nt doc, Sc ma.F eld nfo f eld nfo, Str ng value) {
    Precond  ons.c ckArgu nt(doc != null);
    Precond  ons.c ckArgu nt(f eld nfo != null);
    Precond  ons.c ckArgu nt(value != null);

     f (f eld nfo.getF eldType().getFacetNa () != null) {
      doc.add(new SortedSetDocValuesFacetF eld(f eld nfo.getF eldType().getFacetNa (), value));
    }
  }

  pr vate Str ng getTerm(TermToBytesRefAttr bute attr) {
     f (attr  nstanceof CharTermAttr bute) {
      return ((CharTermAttr bute) attr).toStr ng();
    } else  f (attr  nstanceof  ntTermAttr bute) {
      return Str ng.valueOf((( ntTermAttr bute) attr).getTerm());
    } else  f (attr  nstanceof LongTermAttr bute) {
      return Str ng.valueOf(((LongTermAttr bute) attr).getTerm());
    } else {
      return attr.getBytesRef().utf8ToStr ng();
    }
  }

  /**
   * Expand t  Tw terTokenStream and populate h gh-frequency terms, phrases and/or facet category paths.
   */
  pr vate vo d expandStream(
      Docu nt doc,
      Sc ma.F eld nfo f eld nfo,
      TokenStream stream,
      Set<Str ng> hfTerms,
      Set<Str ng> hfPhrases) throws  OExcept on {
    // C ckstyle does not allow ass gn nt to para ters.
    Set<Str ng> facetHfTerms = hfTerms;
    Set<Str ng> facetHfPhrases = hfPhrases;

     f (!(H ghFrequencyTermPa rs. NDEX_HF_TERM_PA RS
        && f eld nfo.getF eldType(). s ndexHFTermPa rs())) {
      // h gh-frequency terms and phrases are not needed
       f (f eld nfo.getF eldType().getFacetNa () == null) {
        // Facets are not needed e  r, s mply return, would do noth ng ot rw se
        return;
      }
      facetHfTerms = null;
      facetHfPhrases = null;
    }

    f nal TermToBytesRefAttr bute attr = stream.getAttr bute(TermToBytesRefAttr bute.class);
    stream.reset();

    Str ng lastHFTerm = null;
    wh le (stream. ncre ntToken()) {
      Str ng term = getTerm(attr);
       f (f eld nfo.getF eldType().getFacetNa () != null) {
        addFacetF eld(doc, f eld nfo, term);
      }
       f (H ghFrequencyTermPa rs.HF_TERM_SET.conta ns(term)) {
         f (facetHfTerms != null) {
          facetHfTerms.add(term);
        }
         f (lastHFTerm != null) {
           f (facetHfPhrases != null) {
            facetHfPhrases.add(H ghFrequencyTermPa rs.createPhrasePa r(lastHFTerm, term));
          }
        }
        lastHFTerm = term;
      } else {
        lastHFTerm = null;
      }
    }
  }

  publ c stat c f nal class CSFF eld extends F eld {
    /**
     * Create a CSFF eld w h t  g ven f eldType, conta n ng t  g ven f eld data.
     */
    publ c CSFF eld(Str ng na , Earlyb rdF eldType f eldType, Thr ftF eldData data) {
      super(na , f eldType);

       f (f eldType. sCsfVar ableLength()) {
        f eldsData = new BytesRef(data.getBytesValue());
      } else {
        sw ch (f eldType.getCsfType()) {
          case BYTE:
            f eldsData = Long.valueOf(data.getByteValue());
            break;
          case  NT:
            f eldsData = Long.valueOf(data.get ntValue());
            break;
          case LONG:
            f eldsData = Long.valueOf(data.getLongValue());
            break;
          case FLOAT:
            f eldsData = Long.valueOf(Float.floatToRaw ntB s((float) data.getFloatValue()));
            break;
          case DOUBLE:
            f eldsData = Long.valueOf(Double.doubleToRawLongB s(data.getDoubleValue()));
            break;
          default:
            throw new  llegalArgu ntExcept on("Unknown csf type: " + f eldType.getCsfType());
        }
      }
    }
  }

  publ c  nterface TokenStreamRewr er {
    /**
     * Rewr e t  token stream.
     */
    Tw terTokenStream rewr e(Sc ma.F eld nfo f eld nfo, Tw terTokenStream stream);
  }
}
