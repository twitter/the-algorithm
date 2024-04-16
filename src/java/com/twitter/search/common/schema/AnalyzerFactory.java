package com.tw ter.search.common.sc ma;

 mport java. o.Reader;
 mport java.text.ParseExcept on;
 mport java.ut l.Map;

 mport com.google.common.base.Spl ter;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Sets;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport org.apac .lucene.analys s.Analyzer;
 mport org.apac .lucene.analys s.CharArraySet;
 mport org.apac .lucene.analys s.CharF lter;
 mport org.apac .lucene.analys s.TokenStream;
 mport org.apac .lucene.analys s.Token zer;
 mport org.apac .lucene.analys s.charf lter.HTMLStr pCharF lter;
 mport org.apac .lucene.analys s.core.Wh espaceAnalyzer;
 mport org.apac .lucene.analys s.fa.Pers anCharF lter;
 mport org.apac .lucene.analys s.standard.StandardAnalyzer;
 mport org.apac .lucene.ut l.Vers on;

 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftAnalyzer;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftClass nstant ater;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftCustomAnalyzer;

publ c class AnalyzerFactory {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(AnalyzerFactory.class);

  pr vate stat c f nal Str ng MATCH_VERS ON_ARG_NAME = "matchVers on";
  pr vate stat c f nal Str ng STANDARD_ANALYZER = "StandardAnalyzer";
  pr vate stat c f nal Str ng WH TESPACE_ANALYZER = "Wh espaceAnalyzer";
  pr vate stat c f nal Str ng SEARCH_WH TESPACE_ANALYZER = "SearchWh espaceAnalyzer";
  pr vate stat c f nal Str ng HTML_STR P_CHAR_F LTER = "HTMLStr pCharF lter";
  pr vate stat c f nal Str ng PERS AN_CHAR_F LTER = "Pers anCharF lter";

  /**
   * Return a Lucene Analyzer based on t  g ven Thr ftAnalyzer.
   */
  publ c Analyzer getAnalyzer(Thr ftAnalyzer analyzer) {
     f (analyzer. sSetAnalyzer()) {
      return resolveAnalyzerClass(analyzer.getAnalyzer());
    } else  f (analyzer. sSetCustomAnalyzer()) {
      return bu ldCustomAnalyzer(analyzer.getCustomAnalyzer());
    }
    return new SearchWh espaceAnalyzer();
  }

  pr vate Analyzer resolveAnalyzerClass(Thr ftClass nstant ater classDef) {
    Map<Str ng, Str ng> params = classDef.getParams();
    Vers on matchVers on = Vers on.LUCENE_8_5_2;

    Str ng matchVers onNa  = getArg(params, MATCH_VERS ON_ARG_NAME);
     f (matchVers onNa  != null) {
      try {
        matchVers on = Vers on.parse(matchVers onNa );
      } catch (ParseExcept on e) {
        //  gnore and use default vers on
        LOG.warn("Unable to parse match vers on: " + matchVers onNa 
                + ". W ll use default vers on of 8.5.2.");
      }
    }

     f (classDef.getClassNa ().equals(STANDARD_ANALYZER)) {
      Str ng stopwords = getArg(params, "stopwords");
       f (stopwords != null) {

        CharArraySet stopwordSet = new CharArraySet(
                L sts.newL nkedL st(Spl ter.on(",").spl (stopwords)),
                false);
        return new StandardAnalyzer(stopwordSet);
      } else {
        return new StandardAnalyzer();
      }
    } else  f (classDef.getClassNa ().equals(WH TESPACE_ANALYZER)) {
      return new Wh espaceAnalyzer();
    } else  f (classDef.getClassNa ().equals(SEARCH_WH TESPACE_ANALYZER)) {
      return new SearchWh espaceAnalyzer();
    }

    return null;
  }

  pr vate Analyzer bu ldCustomAnalyzer(f nal Thr ftCustomAnalyzer customAnalyzer) {
    return new Analyzer() {
      @Overr de
      protected TokenStreamComponents createComponents(Str ng f eldNa ) {
        f nal Token zer token zer = resolveToken zerClass(customAnalyzer.getToken zer());

        TokenStream f lter = token zer;

         f (customAnalyzer. sSetF lters()) {
          for (Thr ftClass nstant ater f lterClass : customAnalyzer.getF lters()) {
            f lter = resolveTokenF lterClass(f lterClass, f lter);
          }
        }

        return new TokenStreamComponents(token zer, f lter);
      }
    };
  }

  pr vate Token zer resolveToken zerClass(Thr ftClass nstant ater classDef) {
    return null;
  }

  pr vate TokenStream resolveTokenF lterClass(Thr ftClass nstant ater classDef, TokenStream  nput) {
    return null;
  }

  pr vate CharF lter resolveCharF lterClass(Thr ftClass nstant ater classDef, Reader  nput) {
     f (classDef.getClassNa ().equals(HTML_STR P_CHAR_F LTER)) {
      Str ng escapedTags = getArg(classDef.getParams(), "excapedTags");
       f (escapedTags != null) {
        return new HTMLStr pCharF lter( nput, Sets.newHashSet(Spl ter.on(",").spl (escapedTags)));
      } else {
        return new HTMLStr pCharF lter( nput);
      }
    } else  f (classDef.getClassNa ().equals(PERS AN_CHAR_F LTER)) {
      return new Pers anCharF lter( nput);
    }


    throw new ClassNotSupportedExcept on("CharF lter", classDef);
  }

  pr vate Str ng getArg(Map<Str ng, Str ng> args, Str ng arg) {
     f (args == null) {
      return null;
    }

    return args.get(arg);
  }

  publ c f nal class ClassNotSupportedExcept on extends Runt  Except on {
    pr vate ClassNotSupportedExcept on(Str ng type, Thr ftClass nstant ater classDef) {
      super(type + " class w h na  " + classDef.getClassNa () + " currently not supported.");
    }
  }
}
