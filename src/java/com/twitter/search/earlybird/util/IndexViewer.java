package com.tw ter.search.earlyb rd.ut l;

 mport java. o. OExcept on;
 mport java. o.Pr ntWr er;
 mport java. o.UnsupportedEncod ngExcept on;
 mport java.ut l.ArrayL st;
 mport java.ut l.Collect ons;
 mport java.ut l.Comparator;
 mport java.ut l.L st;
 mport java.ut l.Locale;
 mport java.ut l.Set;
 mport java.ut l.TreeSet;

 mport com.google.common.collect. mmutableSet;
 mport com.google.common.collect.L sts;

 mport org.apac .lucene. ndex. ndexOpt ons;
 mport org.apac .lucene. ndex.Nu r cDocValues;
 mport org.apac .lucene. ndex.Post ngsEnum;
 mport org.apac .lucene. ndex.Terms;
 mport org.apac .lucene. ndex.TermsEnum;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.ut l.BytesRef;

 mport com.tw ter.search.common.constants.thr ftjava.Thr ftLanguage;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftCSFType;
 mport com.tw ter.search.common.ut l.analys s. ntTermAttr bute mpl;
 mport com.tw ter.search.common.ut l.analys s.LongTermAttr bute mpl;
 mport com.tw ter.search.common.ut l.analys s.SortableLongTermAttr bute mpl;
 mport com.tw ter.search.common.ut l.spat al.GeoUt l;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted.MPHTermD ct onary;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted.Realt   ndexTerms;
 mport com.tw ter.search.earlyb rd. ndex.Earlyb rdS ngleSeg ntSearc r;

 mport geo.google.datamodel.GeoCoord nate;

publ c class  ndexV e r {
  /**
   * F elds whose terms are  ndexed us ng
   * {@l nk com.tw ter.search.common.ut l.analys s. ntTermAttr bute}
   */
  pr vate stat c f nal Set<Str ng>  NT_TERM_ATTR BUTE_F ELDS =  mmutableSet.of(
      Earlyb rdF eldConstant.CREATED_AT_F ELD.getF eldNa (),
      Earlyb rdF eldConstant.L NK_CATEGORY_F ELD.getF eldNa (),
      Earlyb rdF eldConstant
          .NORMAL ZED_FAVOR TE_COUNT_GREATER_THAN_OR_EQUAL_TO_F ELD.getF eldNa (),
      Earlyb rdF eldConstant
          .NORMAL ZED_REPLY_COUNT_GREATER_THAN_OR_EQUAL_TO_F ELD.getF eldNa (),
      Earlyb rdF eldConstant
          .NORMAL ZED_RETWEET_COUNT_GREATER_THAN_OR_EQUAL_TO_F ELD.getF eldNa (),
      Earlyb rdF eldConstant.COMPOSER_SOURCE.getF eldNa ());

  /**
   * F elds whose terms are  ndexed us ng
   * {@l nk com.tw ter.search.common.ut l.analys s.LongTermAttr bute}
   */
  pr vate stat c f nal Set<Str ng> LONG_TERM_ATTR BUTE_F ELDS =  mmutableSet.of(
      Earlyb rdF eldConstant.CONVERSAT ON_ D_F ELD.getF eldNa (),
      Earlyb rdF eldConstant.L KED_BY_USER_ D_F ELD.getF eldNa (),
      Earlyb rdF eldConstant.QUOTED_TWEET_ D_F ELD.getF eldNa (),
      Earlyb rdF eldConstant.QUOTED_USER_ D_F ELD.getF eldNa (),
      Earlyb rdF eldConstant.REPL ED_TO_BY_USER_ D.getF eldNa (),
      Earlyb rdF eldConstant.RETWEETED_BY_USER_ D.getF eldNa (),
      Earlyb rdF eldConstant.D RECTED_AT_USER_ D_F ELD.getF eldNa (),
      Earlyb rdF eldConstant.FROM_USER_ D_F ELD.getF eldNa (),
      Earlyb rdF eldConstant. N_REPLY_TO_TWEET_ D_F ELD.getF eldNa (),
      Earlyb rdF eldConstant. N_REPLY_TO_USER_ D_F ELD.getF eldNa (),
      Earlyb rdF eldConstant.RETWEET_SOURCE_TWEET_ D_F ELD.getF eldNa (),
      Earlyb rdF eldConstant.RETWEET_SOURCE_USER_ D_F ELD.getF eldNa ());

  /**
   * F elds whose terms  ndex us ng SORTED
   * {@l nk com.tw ter.search.common.ut l.analys s.LongTermAttr bute}
   */
  pr vate stat c f nal Set<Str ng> SORTED_LONG_TERM_ATTR BUTE_F ELDS =
       mmutableSet.of(Earlyb rdF eldConstant. D_F ELD.getF eldNa ());

  pr vate f nal Earlyb rdS ngleSeg ntSearc r searc r;
  pr vate f nal Earlyb rd ndexSeg ntAtom cReader tw terReader;

  publ c long getT  Sl ce d() {
    return searc r.getT  Sl ce D();
  }

  publ c stat c class Opt ons {
    pr vate boolean dump xTerms = false;
    pr vate Str ng charset;
    pr vate double[]  togramBuckets;
    pr vate boolean termLength togram;

    publ c Opt ons setDump xTerms(boolean dump xTermsParam) {
      t .dump xTerms = dump xTermsParam;
      return t ;
    }

    publ c Opt ons setCharset(Str ng charsetParam) {
      t .charset = charsetParam;
      return t ;
    }

    publ c Opt ons set togramBuckets(double[]  togramBucketsParam) {
      t . togramBuckets =  togramBucketsParam;
      return t ;
    }

    publ c Opt ons setTermLength togram(boolean termLength togramParam) {
      t .termLength togram = termLength togramParam;
      return t ;
    }
  }

  /**
   * Data Transfer Object for Terms, encapsulates t  "json" ser al zat on
   * wh le ma nta n ng stream ng mode
   */
  pr vate stat c class TermDto {

    pr vate f nal Str ng f eld;
    pr vate f nal Str ng term;
    pr vate f nal Str ng docFreq;
    pr vate f nal Str ng percent;
    pr vate f nal Post ngsEnum docsEnum;
    pr vate f nal TermsEnum termsEnum;
    pr vate f nal  nteger maxDocs;

    publ c TermDto(Str ng f eld, Str ng term, Str ng docFreq, Str ng percent,
                   Post ngsEnum docsEnum, TermsEnum termsEnum,  nteger maxDocs) {
      t .f eld = f eld;
      t .term = term;
      t .docFreq = docFreq;
      t .percent = percent;
      t .docsEnum = docsEnum;
      t .termsEnum = termsEnum;
      t .maxDocs = maxDocs;
    }

    publ c vo d wr e(V e rWr er wr er,
                      Earlyb rd ndexSeg ntAtom cReader tw terReader) throws  OExcept on {
      wr er.beg nObject();
      wr er.na ("f eld").value(f eld);
      wr er.na ("term").value(term);
      wr er.na ("docFreq").value(docFreq);
      wr er.na ("percent").value(percent);
       f (docsEnum != null) {
        appendFrequencyAndPos  ons(wr er, f eld, docsEnum, tw terReader);
      }
       f (maxDocs != null) {
        appendDocs(wr er, termsEnum, maxDocs, tw terReader);
      }
      wr er.endObject();
    }
  }

  /**
   * Data Transfer Object for Terms, encapsulates t  "json" ser al zat on
   * wh le ma nta n ng stream ng mode
   */
  pr vate stat c class StatsDto {

    pr vate f nal Str ng f eld;
    pr vate f nal Str ng numTerms;
    pr vate f nal Str ng terms;


    publ c StatsDto(Str ng f eld, Str ng numTerms, Str ng terms) {
      t .f eld = f eld;
      t .numTerms = numTerms;
      t .terms = terms;
    }

    publ c vo d wr e(V e rWr er wr er) throws  OExcept on {
      wr er.beg nObject();

      wr er.na ("f eld").value(f eld);
      wr er.na ("numTerms").value(numTerms);
      wr er.na ("terms").value(terms);

      wr er.endObject();
    }
  }

  publ c  ndexV e r(Earlyb rdS ngleSeg ntSearc r searc r) {
    t .searc r = searc r;
    t .tw terReader = searc r.getTw ter ndexReader();
  }

  pr vate boolean shouldSeekExact(Terms terms, TermsEnum termsEnum) {
    return terms  nstanceof Realt   ndexTerms
           || termsEnum  nstanceof MPHTermD ct onary.MPHTermsEnum;
  }

  /**
   * Dumps all terms for a g ven t et  d.
   * @param wr er wr er be ng used
   * @param t et d t  t et  d to use
   */
  publ c vo d dumpT etDataByT et d(V e rWr er wr er, long t et d, Opt ons opt ons)
      throws  OExcept on {
     nt doc d = tw terReader.getSeg ntData().getDoc DToT et DMapper().getDoc D(t et d);
    dumpT etDataByDoc d(wr er, doc d, opt ons);
  }

  /**
   * Dumps all terms for a g ven doc  d.
   * @param wr er wr er be ng used
   * @param doc d t  docu nt  d to use.
   */
  publ c vo d dumpT etDataByDoc d(V e rWr er wr er,  nt doc d, Opt ons opt ons)
      throws  OExcept on {
    wr er.beg nObject();

    pr nt ader(wr er);
    long t et D = tw terReader.getSeg ntData().getDoc DToT et DMapper().getT et D(doc d);
     f (doc d < tw terReader.maxDoc() && t et D >= 0) {
      wr er.na ("doc d").value( nteger.toStr ng(doc d));
      wr er.na ("t et d").value(Long.toStr ng(t et D));
      dump ndexedF elds(wr er, doc d, opt ons);
      dumpCsfF elds(wr er, doc d);
    }
    wr er.endObject();
  }

  /**
   * Dumps all t et  Ds  n t  current seg nt to t  g ven f le.
   */
  publ c vo d dumpT et ds(V e rWr er wr er, Str ng logF le, Pr ntWr er logWr er)
      throws  OExcept on {
    wr eT et dsToLogF le(logWr er);

    wr er.beg nObject();
    wr er.na (Long.toStr ng(searc r.getT  Sl ce D())).value(logF le);
    wr er.endObject();
  }

  pr vate vo d wr eT et dsToLogF le(Pr ntWr er logWr er) {
    Doc DToT et DMapper mapper = tw terReader.getSeg ntData().getDoc DToT et DMapper();
     nt doc d =  nteger.M N_VALUE;
    wh le ((doc d = mapper.getNextDoc D(doc d)) != Doc DToT et DMapper. D_NOT_FOUND) {
      long t et d = mapper.getT et D(doc d);

      // Ensure t et  D  s val d and non-deleted
       f ((t et d > 0) && !tw terReader.getDeletesV ew(). sDeleted(doc d)) {
        logWr er.pr ntln(t et d);
      }
    }
  }

  pr vate vo d dump ndexedF elds(V e rWr er wr er,  nt doc d,
                                 Opt ons opt ons) throws  OExcept on {
    wr er.na (" ndexedF elds");
    wr er.beg nArray();
    wr er.newl ne();
    for (Str ng f eld : sortedF elds()) {
      dumpT etData(wr er, f eld, doc d, opt ons);
    }
    wr er.endArray();
    wr er.newl ne();
  }

  pr vate vo d dumpCsfF elds(V e rWr er wr er,  nt doc d) throws  OExcept on {
    wr er.na ("csfF elds");
    wr er.beg nArray();
    wr er.newl ne();
    dumpCSFData(wr er, doc d);

    wr er.endArray();
  }

  /**
   * Dumps all CSF values for a g ven doc  d.
   * @param wr er wr er be ng used
   * @param doc d t  docu nt  d to use.
   */
  pr vate vo d dumpCSFData(V e rWr er wr er,  nt doc d) throws  OExcept on {
    Sc ma t etSc ma = tw terReader.getSc ma();

    // Sort t  F eld nfo objects to generate f xed order to make test ng eas er
    L st<Sc ma.F eld nfo> sortedF eld nfos = new ArrayL st<>(t etSc ma.getF eld nfos());
    sortedF eld nfos.sort(Comparator.compar ng(Sc ma.F eld nfo::getF eld d));

    for (Sc ma.F eld nfo f eld nfo: sortedF eld nfos) {
      Str ng csfF eld nfoNa  = f eld nfo.getNa ();
      Thr ftCSFType csfType = t etSc ma.getCSFF eldType(csfF eld nfoNa );
      Nu r cDocValues csfDocValues = tw terReader.getNu r cDocValues(csfF eld nfoNa );
      //  f tw terReader.getNu r cDocValues(value.getNa ()) == null,
      //  ans no Nu r cDocValue was  ndexed for t  f eld so  gnore
       f (csfType != null && csfDocValues != null && csfDocValues.advanceExact(doc d)) {
        long csfValue = csfDocValues.longValue();
        wr er.beg nObject();
        wr er.na ("f eld").value(formatF eld(csfF eld nfoNa ));
        wr er.na ("value");
         f (csfF eld nfoNa .equals(Earlyb rdF eldConstant.LAT_LON_CSF_F ELD.getF eldNa ())) {
          wr er.value(latlongDecode(csfValue));
        } else  f (csfF eld nfoNa .equals(Earlyb rdF eldConstant.LANGUAGE.getF eldNa ())) {
          wr er.value(languageDecode(csfValue));
        } else  f (csfF eld nfoNa .equals(Earlyb rdF eldConstant.CARD_LANG_CSF.getF eldNa ())) {
          wr er.value(languageDecode(csfValue));
        } else {
          wr er.value(Long.toStr ng(csfValue));
        }
        wr er.endObject();
        wr er.newl ne();
      }
    }
  }

  /**
   * Dec p r long value gotten, put  nto format (lat, lon)
   * Decode t  stored long value by creat ng a geocode
   */
  pr vate Str ng latlongDecode(long csfValue) {
    Str ngBu lder sb = new Str ngBu lder();
    GeoCoord nate geoCoord nate = new GeoCoord nate();
     f (GeoUt l.decodeLatLonFrom nt64(csfValue, geoCoord nate)) {
      sb.append(geoCoord nate.getLat ude()).append(", ").append(geoCoord nate.getLong ude());
    } else {
      sb.append(csfValue).append(" (Value Unset or  nval d Coord nate)");
    }
    return sb.toStr ng();
  }

  /**
   * Dec p r long value gotten  nto str ng of t et's language
   */
  pr vate Str ng languageDecode(long csfValue) {
    Str ngBu lder sb = new Str ngBu lder();
    Thr ftLanguage languageType = Thr ftLanguage.f ndByValue(( nt) csfValue);
    sb.append(csfValue).append(" (").append(languageType).append(")");
    return sb.toStr ng();
  }

  pr vate vo d dumpT etData(V e rWr er wr er,
                             Str ng f eld,
                              nt doc d,
                             Opt ons opt ons) throws  OExcept on {

    Terms terms = tw terReader.terms(f eld);
     f (terms != null) {
      TermsEnum termsEnum = terms. erator();
       f (shouldSeekExact(terms, termsEnum)) {
        long numTerms = terms.s ze();
        for ( nt   = 0;   < numTerms;  ++) {
          termsEnum.seekExact( );
          dumpT etDataTerm(wr er, f eld, termsEnum, doc d, opt ons);
        }
      } else {
        wh le (termsEnum.next() != null) {
          dumpT etDataTerm(wr er, f eld, termsEnum, doc d, opt ons);
        }
      }
    }
  }

  pr vate vo d dumpT etDataTerm(V e rWr er wr er, Str ng f eld, TermsEnum termsEnum,
                                  nt doc d, Opt ons opt ons) throws  OExcept on {
    Post ngsEnum docsAndPos  onsEnum = termsEnum.post ngs(null, Post ngsEnum.ALL);
     f (docsAndPos  onsEnum != null && docsAndPos  onsEnum.advance(doc d) == doc d) {
      pr ntTerm(wr er, f eld, termsEnum, docsAndPos  onsEnum, null, opt ons);
    }
  }

  /**
   * Pr nts t   togram for t  currently v e d  ndex.
   * @param wr er current v e rWr er
   * @param f eld  f null, w ll use all f elds
   * @param opt ons opt ons for dump ng out text
   */
  publ c vo d dump togram(V e rWr er wr er, Str ng f eld, Opt ons opt ons) throws  OExcept on {
    wr er.beg nObject();
    pr nt ader(wr er);
    wr er.na (" togram");
    wr er.beg nArray();
    wr er.newl ne();
     f (f eld == null) {
      for (Str ng f eld2 : sortedF elds()) {
        dumpF eld togram(wr er, f eld2, opt ons);
      }
    } else {
      dumpF eld togram(wr er, f eld, opt ons);
    }
    wr er.endArray();
    wr er.endObject();
  }

  pr vate vo d dumpF eld togram(V e rWr er wr er, Str ng f eld, Opt ons opt ons)
      throws  OExcept on {
     togram  to = new  togram(opt ons. togramBuckets);

    Terms terms = tw terReader.terms(f eld);
     f (terms != null) {
      TermsEnum termsEnum = terms. erator();
       f (shouldSeekExact(terms, termsEnum)) {
        long numTerms = terms.s ze();
        for ( nt   = 0;   < numTerms;  ++) {
          termsEnum.seekExact( );
          count togram(opt ons,  to, termsEnum);
        }
      } else {
        wh le (termsEnum.next() != null) {
          count togram(opt ons,  to, termsEnum);
        }
      }
      pr nt togram(wr er, f eld, opt ons,  to);
    }
  }

  pr vate vo d pr nt togram(V e rWr er wr er, Str ng f eld, Opt ons opt ons,
                               togram  to) throws  OExcept on {

    Str ng bucket = opt ons.termLength togram ? "termLength" : "df";
    for ( togram.Entry  tEntry :  to.entr es()) {
      Str ng format =
          Str ng.format(Locale.US,
              "f eld: %s %sBucket: %11s count: %10d "
                  + "percent: %6.2f%% cumulat ve: %6.2f%% totalCount: %10d"
                  + " sum: %15d percent: %6.2f%% cumulat ve: %6.2f%% totalSum: %15d",
              formatF eld(f eld),
              bucket,
               tEntry.getBucketNa (),
               tEntry.getCount(),
               tEntry.getCountPercent() * 100.0,
               tEntry.getCountCumulat ve() * 100.0,
               to.getTotalCount(),
               tEntry.getSum(),
               tEntry.getSumPercent() * 100.0,
               tEntry.getSumCumulat ve() * 100.0,
               to.getTotalSum()
          );
      wr er.value(format);
      wr er.newl ne();
    }
  }

  pr vate vo d count togram(Opt ons opt ons,  togram  to, TermsEnum termsEnum)
          throws  OExcept on {
     f (opt ons.termLength togram) {
      f nal BytesRef bytesRef = termsEnum.term();
       to.add em(bytesRef.length);
    } else {
       to.add em(termsEnum.docFreq());
    }
  }


  /**
   * Pr nts terms and opt onally docu nts for t  currently v e d  ndex.
   * @param wr er wr er be ng used
   * @param f eld  f null, w ll use all f elds
   * @param term  f null w ll use all terms
   * @param maxTerms w ll pr nt at most t  many terms per f eld.  f null w ll pr nt 0 terms.
   * @param maxDocs w ll pr nt at most t  many docu nts,  f null, w ll not pr nt docs.
   * @param opt ons opt ons for dump ng out text
   */
  publ c vo d dumpData(V e rWr er wr er, Str ng f eld, Str ng term,  nteger maxTerms,
         nteger maxDocs, Opt ons opt ons, boolean shouldSeekToTerm) throws  OExcept on {

    wr er.beg nObject();
    pr nt ader(wr er);

    wr er.na ("terms");
    wr er.beg nArray();
    wr er.newl ne();
    dumpData nternal(wr er, f eld, term, maxTerms, maxDocs, opt ons, shouldSeekToTerm);
    wr er.endArray();
    wr er.endObject();
  }

  pr vate vo d dumpData nternal(V e rWr er wr er, Str ng f eld, Str ng term,  nteger maxTerms,
       nteger maxDocs, Opt ons opt ons, boolean shouldSeekToTerm) throws  OExcept on {

     f (f eld == null) {
      dumpDataForAllF elds(wr er, term, maxTerms, maxDocs, opt ons);
      return;
    }
     f (term == null) {
      dumpDataForAllTerms(wr er, f eld, maxTerms, maxDocs, opt ons);
      return;
    }
    Terms terms = tw terReader.terms(f eld);
     f (terms != null) {
      TermsEnum termsEnum = terms. erator();
      TermsEnum.SeekStatus status = termsEnum.seekCe l(new BytesRef(term));
       f (status == TermsEnum.SeekStatus.FOUND) {
        pr ntTerm(wr er, f eld, termsEnum, null, maxDocs, opt ons);
      }
       f (shouldSeekToTerm) {
        dumpTermsAfterSeek(wr er, f eld, terms, maxTerms, maxDocs, opt ons, termsEnum, status);
      }
    }
  }

  /**
   *  f term (cursor)  s found for an  ndexed seg nt - dump t  next termsLeft words
   * start ng from t  current pos  on  n t  enum.  For an  ndexed seg nt,
   * seekCe l w ll place t  enum at t  word or t  next "ce l ng" term.  For
   * a realt    ndex,  f t  word  s not found   do not pag nate anyth ng
   *   also only pag nate  f t  TermsEnum  s not at t  end.
   */
  pr vate vo d dumpTermsAfterSeek(V e rWr er wr er, Str ng f eld, Terms terms,  nteger maxTerms,
       nteger maxDocs, Opt ons opt ons, TermsEnum termsEnum, TermsEnum.SeekStatus status)
      throws  OExcept on {
     f (status != TermsEnum.SeekStatus.END) {
      // for realt  , to not repeat t  found word
       f (shouldSeekExact(terms, termsEnum)) {
        termsEnum.next();
      }
       f (status != TermsEnum.SeekStatus.FOUND) {
        //  f not found, pr nt out curr term before call ng next()
        pr ntTerm(wr er, f eld, termsEnum, null, maxDocs, opt ons);
      }
      for ( nt termsLeft = maxTerms - 1; termsLeft > 0 && termsEnum.next() != null; termsLeft--) {
        pr ntTerm(wr er, f eld, termsEnum, null, maxDocs, opt ons);
      }
    }
  }

  pr vate vo d dumpDataForAllF elds(V e rWr er wr er, Str ng term,  nteger maxTerms,
                                     nteger maxDocs, Opt ons opt ons) throws  OExcept on {
    for (Str ng f eld : sortedF elds()) {
      dumpData nternal(wr er, f eld, term, maxTerms, maxDocs, opt ons, false);
    }
  }

  pr vate L st<Str ng> sortedF elds() {
    // T et facets are added to a spec al $facets f eld, wh ch  s not part of t  sc ma.
    //    nclude    re, because see ng t  facets for a t et  s generally useful.
    L st<Str ng> f elds = L sts.newArrayL st("$facets");
    for (Sc ma.F eld nfo f eld nfo : tw terReader.getSc ma().getF eld nfos()) {
       f (f eld nfo.getF eldType(). ndexOpt ons() !=  ndexOpt ons.NONE) {
        f elds.add(f eld nfo.getNa ());
      }
    }
    Collect ons.sort(f elds);
    return f elds;
  }

  pr vate vo d dumpDataForAllTerms(V e rWr er wr er,
                                   Str ng f eld,
                                    nteger maxTerms,
                                    nteger maxDocs,
                                   Opt ons opt ons) throws  OExcept on {
    Terms terms = tw terReader.terms(f eld);
     f (terms != null) {
      TermsEnum termsEnum = terms. erator();
       f (shouldSeekExact(terms, termsEnum)) {
        long numTerms = terms.s ze();
        long termToDump = maxTerms == null ? 0 : Math.m n(numTerms, maxTerms);
        for ( nt   = 0;   < termToDump;  ++) {
          termsEnum.seekExact( );
          pr ntTerm(wr er, f eld, termsEnum, null, maxDocs, opt ons);
        }
      } else {
         nt max = maxTerms == null ? 0 : maxTerms;
        wh le (max > 0 && termsEnum.next() != null) {
          pr ntTerm(wr er, f eld, termsEnum, null, maxDocs, opt ons);
          max--;
        }
      }
    }
  }

  pr vate Str ng termToStr ng(Str ng f eld, BytesRef bytesTerm, Opt ons opt ons)
      throws UnsupportedEncod ngExcept on {
     f ( NT_TERM_ATTR BUTE_F ELDS.conta ns(f eld)) {
      return  nteger.toStr ng( ntTermAttr bute mpl.copyBytesRefTo nt(bytesTerm));
    } else  f (LONG_TERM_ATTR BUTE_F ELDS.conta ns(f eld)) {
      return Long.toStr ng(LongTermAttr bute mpl.copyBytesRefToLong(bytesTerm));
    } else  f (SORTED_LONG_TERM_ATTR BUTE_F ELDS.conta ns(f eld)) {
      return Long.toStr ng(SortableLongTermAttr bute mpl.copyBytesRefToLong(bytesTerm));
    } else {
       f (opt ons != null && opt ons.charset != null && !opt ons.charset. sEmpty()) {
        return new Str ng(bytesTerm.bytes, bytesTerm.offset, bytesTerm.length, opt ons.charset);
      } else {
        return bytesTerm.utf8ToStr ng();
      }
    }
  }

  pr vate vo d pr ntTerm(V e rWr er wr er, Str ng f eld, TermsEnum termsEnum,
                         Post ngsEnum docsEnum,  nteger maxDocs, Opt ons opt ons)
      throws  OExcept on {
    f nal BytesRef bytesRef = termsEnum.term();
    Str ngBu lder termToStr ng = new Str ngBu lder();
    termToStr ng.append(termToStr ng(f eld, bytesRef, opt ons));
     f (opt ons != null && opt ons.dump xTerms) {
      termToStr ng.append(" ").append(bytesRef.toStr ng());
    }
    f nal  nt df = termsEnum.docFreq();
    double dfPercent = ((double) df / t .tw terReader.numDocs()) * 100.0;
    TermDto termDto = new TermDto(f eld, termToStr ng.toStr ng(),  nteger.toStr ng(df),
                                   Str ng.format(Locale.US, "%.2f%%", dfPercent),
                                   docsEnum, termsEnum, maxDocs);
    termDto.wr e(wr er, tw terReader);
    wr er.newl ne();
  }

  pr vate stat c vo d appendFrequencyAndPos  ons(V e rWr er wr er, Str ng f eld,
      Post ngsEnum docsEnum, Earlyb rd ndexSeg ntAtom cReader tw terReader) throws  OExcept on {
    f nal  nt frequency = docsEnum.freq();
    wr er.na ("freq").value( nteger.toStr ng(frequency));

    Sc ma sc ma = tw terReader.getSc ma();
    Sc ma.F eld nfo f eld nfo = sc ma.getF eld nfo(f eld);

     f (f eld nfo != null
            && (f eld nfo.getF eldType(). ndexOpt ons() ==  ndexOpt ons.DOCS_AND_FREQS_AND_POS T ONS
            || f eld nfo.getF eldType(). ndexOpt ons()
                ==  ndexOpt ons.DOCS_AND_FREQS_AND_POS T ONS_AND_OFFSETS)) {
      appendPos  ons(wr er, docsEnum);
    }
  }

  pr vate stat c vo d appendPos  ons(V e rWr er wr er, Post ngsEnum docsAndPos  onsEnum)
      throws  OExcept on {
    wr er.na ("pos  ons");

    wr er.beg nArray();
    f nal  nt frequency = docsAndPos  onsEnum.freq();
    for ( nt   = 0;   < frequency;  ++) {
       nt pos  on = docsAndPos  onsEnum.nextPos  on();
      wr er.value( nteger.toStr ng(pos  on));
    }
    wr er.endArray();
  }

  pr vate stat c vo d appendDocs(V e rWr er wr er, TermsEnum termsEnum,  nt maxDocs,
                                 Earlyb rd ndexSeg ntAtom cReader tw terReader)
      throws  OExcept on {
    wr er.na ("doc ds");

    wr er.beg nArray();

    Post ngsEnum docs = termsEnum.post ngs(null, 0);
     nt docsReturned = 0;
     nt doc d;
    boolean endedEarly = false;
    Doc DToT et DMapper mapper = tw terReader.getSeg ntData().getDoc DToT et DMapper();
    wh le ((doc d = docs.nextDoc()) != Doc dSet erator.NO_MORE_DOCS) {
       f (docsReturned < maxDocs) {
        docsReturned++;
        long t et D = mapper.getT et D(doc d);

        wr er.beg nObject();
        wr er.na ("doc d").value(Long.toStr ng(doc d));
        wr er.na ("t et d").value(Long.toStr ng(t et D));
        wr er.endObject();
      } else {
        endedEarly = true;
        break;
      }
    }
     f (endedEarly) {
      wr er.beg nObject();
      wr er.na ("status").value("ended early");
      wr er.endObject();
    }
    wr er.endArray();
  }

  /**
   * Pr nts gener c stats for all f elds  n t  currently v e d  ndex.
   */
  publ c vo d dumpStats(V e rWr er wr er) throws  OExcept on {
    wr er.beg nObject();

    pr nt ader(wr er);
    // stats sect on
    wr er.na ("stats");
    wr er.beg nArray();
    wr er.newl ne();
    for (Str ng f eld : sortedF elds()) {
      Terms terms = tw terReader.terms(f eld);
       f (terms != null) {
        pr ntStats(wr er, f eld, terms);
      }
    }
    wr er.endArray();
    wr er.endObject();
  }

  pr vate vo d pr ntStats(V e rWr er wr er, Str ng f eld, Terms terms) throws  OExcept on {
    StatsDto statsDto = new StatsDto(
        f eld, Str ng.valueOf(terms.s ze()), terms.getClass().getCanon calNa ());
    statsDto.wr e(wr er);
    wr er.newl ne();
  }

  pr vate vo d pr nt ader(V e rWr er wr er) throws  OExcept on {
    wr er.na ("t  Sl ce d").value(Long.toStr ng(t .searc r.getT  Sl ce D()));
    wr er.na ("maxDocNumber").value( nteger.toStr ng(t .tw terReader.maxDoc()));
    wr er.newl ne();
  }

  pr vate stat c Str ng formatF eld(Str ng f eld) {
    return Str ng.format("%20s", f eld);
  }

  /**
   * Dumps out t  sc ma of t  current seg nt.
   * @param wr er to be used for pr nt ng
   */
  publ c vo d dumpSc ma(V e rWr er wr er) throws  OExcept on {
    wr er.beg nObject();
    pr nt ader(wr er);
    wr er.na ("sc maF elds");
    wr er.beg nArray();
    wr er.newl ne();
    Sc ma sc ma = t .tw terReader.getSc ma();
    // T  f elds  n t  sc ma are not sorted. Sort t m so that t  output  s determ n st c
    Set<Str ng> f eldNa Set = new TreeSet<>();
    for (Sc ma.F eld nfo f eld nfo: sc ma.getF eld nfos()) {
      f eldNa Set.add(f eld nfo.getNa ());
    }
    for (Str ng f eldNa  : f eldNa Set) {
      wr er.value(f eldNa );
      wr er.newl ne();
    }
    wr er.endArray();
    wr er.endObject();
  }

  /**
   * Dumps out t   ndexed f elds  ns de t  current seg nt.
   * Ma nly used to  lp t  front end populate t  f elds.
   * @param wr er wr er to be used for pr nt ng
   */
  publ c vo d dumpF elds(V e rWr er wr er) throws  OExcept on {
    wr er.beg nObject();
    pr nt ader(wr er);
    wr er.na ("f elds");
    wr er.beg nArray();
    wr er.newl ne();
    for (Str ng f eld : sortedF elds()) {
      wr er.value(f eld);
      wr er.newl ne();
    }
    wr er.endArray();
    wr er.endObject();
  }

  /**
   * Dumps out t  mapp ng of t  t et/t et d to
   * a doc d as  ll as seg nt/t  sl de pa r.
   * @param wr er wr er to be used for wr  ng
   * @param t et d t et d that  s  nput by user
   */
  publ c vo d dumpT et dToDoc dMapp ng(V e rWr er wr er, long t et d) throws  OExcept on {
    wr er.beg nObject();
    pr nt ader(wr er);
    wr er.na ("t et d").value(Long.toStr ng(t et d));
     nt doc d = tw terReader.getSeg ntData().getDoc DToT et DMapper().getDoc D(t et d);

    wr er.na ("doc d").value( nteger.toStr ng(doc d));
    wr er.endObject();
    wr er.newl ne();
  }

  /**
   * Dumps out t  mapp ng of t  doc d to
   * t et d and t  sl ce/seg nt d pa rs.
   * @param wr er wr er to be used for wr  ng
   * @param doc d doc d that  s  nput by user
   */
  publ c vo d dumpDoc dToT et dMapp ng(V e rWr er wr er,  nt doc d) throws  OExcept on {
    wr er.beg nObject();
    pr nt ader(wr er);
    long t et d = tw terReader.getSeg ntData().getDoc DToT et DMapper().getT et D(doc d);

    wr er.na ("t et d");
     f (t et d >= 0) {
      wr er.value(Long.toStr ng(t et d));
    } else {
      wr er.value("Does not ex st  n seg nt");
    }
    wr er.na ("doc d").value( nteger.toStr ng(doc d));
    wr er.endObject();
  }

  /**
   * Pr nt a response  nd cat ng that t  g ven t et  d  s not found  n t   ndex.
   *
   * Note that t   thod does not actually need t  underly ng  ndex, and  nce  s setup as
   * a ut l funct on.
   */
  publ c stat c vo d wr eT etDoesNotEx stResponse(V e rWr er wr er, long t et d)
      throws  OExcept on {
    wr er.beg nObject();
    wr er.na ("t et d");
    wr er.value(Long.toStr ng(t et d));
    wr er.na ("doc d");
    wr er.value("does not ex st on t  earlyb rd.");
    wr er.endObject();
  }
}
