package com.tw ter.search. ngester.p pel ne.tw ter;

 mport java.ut l.Set;
 mport java.ut l.regex.Matc r;
 mport java.ut l.regex.Pattern;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.collect.Sets;

 mport org.apac .commons.lang.Str ngUt ls;
 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;
 mport org.apac .commons.p pel ne.val dat on.ProducesConsu d;

 mport com.tw ter.search.common.dec der.Dec derUt l;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftExpandedUrl;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage;

@Consu dTypes(Tw ter ssage.class)
@ProducesConsu d
publ c class Retr eveSpace dsStage extends Tw terBaseStage
    <Tw ter ssage, Tw ter ssage> {

  @V s bleForTest ng
  protected stat c f nal Pattern SPACES_URL_REGEX =
      Pattern.comp le("^https://tw ter\\.com/ /spaces/([a-zA-Z0-9]+)\\S*$");

  @V s bleForTest ng
  protected stat c f nal Str ng PARSE_SPACE_ D_DEC DER_KEY = " ngester_all_parse_space_ d_from_url";

  pr vate stat c SearchRateCounter numT etsW hSpace ds;
  pr vate stat c SearchRateCounter numT etsW hMult pleSpace ds;

  @Overr de
  protected vo d  n Stats() {
    super. n Stats();
     nnerSetupStats();
  }

  @Overr de
  protected vo d  nnerSetupStats() {
    numT etsW hSpace ds = SearchRateCounter.export(
        getStageNa Pref x() + "_t ets_w h_space_ ds");
    numT etsW hMult pleSpace ds = SearchRateCounter.export(
        getStageNa Pref x() + "_t ets_w h_mult ple_space_ ds");
  }

  @Overr de
  publ c vo d  nnerProcess(Object obj) throws StageExcept on {
    Tw ter ssage  ssage = (Tw ter ssage) obj;
    tryToRetr eveSpace d( ssage);
    em AndCount( ssage);
  }

  pr vate vo d tryToRetr eveSpace d(Tw ter ssage  ssage) {
     f (Dec derUt l. sAva lableForRandomRec p ent(dec der, PARSE_SPACE_ D_DEC DER_KEY)) {
      Set<Str ng> space ds = parseSpace dsFrom ssage( ssage);
       nt space dCount = space ds.s ze();
       f (space dCount > 0) {
        numT etsW hSpace ds. ncre nt();
         f (space dCount > 1) {
          numT etsW hMult pleSpace ds. ncre nt();
        }
         ssage.setSpace ds(space ds);
      }
    }
  }

  @Overr de
  protected Tw ter ssage  nnerRunStageV2(Tw ter ssage  ssage) {
    tryToRetr eveSpace d( ssage);
    return  ssage;
  }

  pr vate Str ng parseSpace dsFromUrl(Str ng url) {
    Str ng space d = null;

     f (Str ngUt ls. sNotEmpty(url)) {
      Matc r matc r = SPACES_URL_REGEX.matc r(url);
       f (matc r.matc s()) {
        space d = matc r.group(1);
      }
    }
    return space d;
  }

  pr vate Set<Str ng> parseSpace dsFrom ssage(Tw ter ssage  ssage) {
    Set<Str ng> space ds = Sets.newHashSet();

    for (Thr ftExpandedUrl expandedUrl :  ssage.getExpandedUrls()) {
      Str ng space d = parseSpace dsFromUrl(expandedUrl.getExpandedUrl());
       f (Str ngUt ls. sNotEmpty(space d)) {
        space ds.add(space d);
      }
    }
    return space ds;
  }
}
