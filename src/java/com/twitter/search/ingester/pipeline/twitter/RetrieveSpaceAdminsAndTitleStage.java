package com.tw ter.search. ngester.p pel ne.tw ter;

 mport java.ut l.L st;
 mport java.ut l.Opt onal;
 mport java.ut l.Set;
 mport java.ut l.concurrent.CompletableFuture;

 mport scala.Opt on;
 mport scala.Tuple2;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.collect.L sts;

 mport org.apac .commons.lang.Str ngUt ls;
 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;
 mport org.apac .commons.p pel ne.val dat on.ProducesConsu d;

 mport com.tw ter.search.common.dec der.Dec derUt l;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssageUser;
 mport com.tw ter.search. ngester.model. ngesterTw ter ssage;
 mport com.tw ter.search. ngester.p pel ne.strato_fetc rs.Aud oSpaceCoreFetc r;
 mport com.tw ter.search. ngester.p pel ne.strato_fetc rs.Aud oSpacePart c pantsFetc r;
 mport com.tw ter.strato.catalog.Fetch;
 mport com.tw ter.ubs.thr ftjava.Aud oSpace;
 mport com.tw ter.ubs.thr ftjava.Part c pantUser;
 mport com.tw ter.ubs.thr ftjava.Part c pants;
 mport com.tw ter.ut l.Funct on;
 mport com.tw ter.ut l.Future;
 mport com.tw ter.ut l.Futures;
 mport com.tw ter.ut l.Try;

@Consu dTypes( ngesterTw ter ssage.class)
@ProducesConsu d
publ c class Retr eveSpaceAdm nsAndT leStage extends Tw terBaseStage
    < ngesterTw ter ssage, CompletableFuture< ngesterTw ter ssage>> {

  @V s bleForTest ng
  protected stat c f nal Str ng RETR EVE_SPACE_ADM NS_AND_T TLE_DEC DER_KEY =
      " ngester_all_retr eve_space_adm ns_and_t le";

  pr vate Aud oSpaceCoreFetc r coreFetc r;
  pr vate Aud oSpacePart c pantsFetc r part c pantsFetc r;

  pr vate SearchRateCounter t etsW hSpaceAdm ns;
  pr vate SearchRateCounter t etsW hSpaceT le;
  pr vate SearchRateCounter coreFetchSuccess;
  pr vate SearchRateCounter coreFetchFa lure;
  pr vate SearchRateCounter part c pantsFetchSuccess;
  pr vate SearchRateCounter part c pantsFetchFa lure;
  pr vate SearchRateCounter emptyCore;
  pr vate SearchRateCounter emptyPart c pants;
  pr vate SearchRateCounter emptySpaceT le;
  pr vate SearchRateCounter emptySpaceAdm ns;
  pr vate SearchRateCounter parallelFetchAttempts;
  pr vate SearchRateCounter parallelFetchFa lure;


  @Overr de
  protected vo d do nnerPreprocess() {
     nnerSetup();
  }

  @Overr de
  protected vo d  nnerSetup() {
    coreFetc r = w reModule.getAud oSpaceCoreFetc r();
    part c pantsFetc r = w reModule.getAud oSpacePart c pantsFetc r();

    t etsW hSpaceAdm ns = getStageStat("t ets_w h_aud o_space_adm ns");
    t etsW hSpaceT le = getStageStat("t ets_w h_aud o_space_t le");
    coreFetchSuccess = getStageStat("core_fetch_success");
    coreFetchFa lure = getStageStat("core_fetch_fa lure");
    part c pantsFetchSuccess = getStageStat("part c pants_fetch_success");
    part c pantsFetchFa lure = getStageStat("part c pants_fetch_fa lure");
    emptyCore = getStageStat("empty_core");
    emptyPart c pants = getStageStat("empty_part c pants");
    emptySpaceT le = getStageStat("empty_space_t le");
    emptySpaceAdm ns = getStageStat("empty_space_adm ns");
    parallelFetchAttempts = getStageStat("parallel_fetch_attempts");
    parallelFetchFa lure = getStageStat("parallel_fetch_fa lure");
  }

  pr vate SearchRateCounter getStageStat(Str ng statSuff x) {
    return SearchRateCounter.export(getStageNa Pref x() + "_" + statSuff x);
  }

  pr vate Future<Tuple2<Try<Fetch.Result<Aud oSpace>>, Try<Fetch.Result<Part c pants>>>>
  tryRetr eveSpaceAdm nAndT le( ngesterTw ter ssage tw ter ssage) {
    Set<Str ng> space ds = tw ter ssage.getSpace ds();

     f (space ds. sEmpty()) {
      return null;
    }

     f (!(Dec derUt l. sAva lableForRandomRec p ent(dec der,
        RETR EVE_SPACE_ADM NS_AND_T TLE_DEC DER_KEY))) {
      return null;
    }

    Str ng space d = space ds. erator().next();

    // Query both columns  n parallel.
    parallelFetchAttempts. ncre nt();
    Future<Fetch.Result<Aud oSpace>> core = coreFetc r.fetch(space d);
    Future<Fetch.Result<Part c pants>> part c pants = part c pantsFetc r.fetch(space d);

    return Futures.jo n(core.l ftToTry(), part c pants.l ftToTry());
  }

  @Overr de
  protected CompletableFuture< ngesterTw ter ssage>  nnerRunStageV2( ngesterTw ter ssage
                                                                            tw ter ssage) {
    Future<Tuple2<Try<Fetch.Result<Aud oSpace>>, Try<Fetch.Result<Part c pants>>>>
        tryRetr eveSpaceAdm nAndT le = tryRetr eveSpaceAdm nAndT le(tw ter ssage);

    CompletableFuture< ngesterTw ter ssage> cf = new CompletableFuture<>();

     f (tryRetr eveSpaceAdm nAndT le == null) {
      cf.complete(tw ter ssage);
    } else {
      tryRetr eveSpaceAdm nAndT le.onSuccess(Funct on.cons(tr es -> {
        handleFutureOnSuccess(tr es, tw ter ssage);
        cf.complete(tw ter ssage);
      })).onFa lure(Funct on.cons(throwable -> {
        handleFutureOnFa lure();
        cf.complete(tw ter ssage);
      }));
    }

    return cf;
  }

  @Overr de
  publ c vo d  nnerProcess(Object obj) throws StageExcept on {
     f (!(obj  nstanceof  ngesterTw ter ssage)) {
      throw new StageExcept on(t , "Object  s not a  ngesterTw ter ssage object: " + obj);
    }
     ngesterTw ter ssage tw ter ssage = ( ngesterTw ter ssage) obj;
    Future<Tuple2<Try<Fetch.Result<Aud oSpace>>, Try<Fetch.Result<Part c pants>>>>
        tryRetr eveSpaceAdm nAndT le = tryRetr eveSpaceAdm nAndT le(tw ter ssage);

     f (tryRetr eveSpaceAdm nAndT le == null) {
      em AndCount(tw ter ssage);
      return;
    }

    tryRetr eveSpaceAdm nAndT le.onSuccess(Funct on.cons(tr es -> {
            handleFutureOnSuccess(tr es, tw ter ssage);
            em AndCount(tw ter ssage);
          })).onFa lure(Funct on.cons(throwable -> {
            handleFutureOnFa lure();
            em AndCount(tw ter ssage);
          }));
  }

  pr vate vo d handleFutureOnSuccess(Tuple2<Try<Fetch.Result<Aud oSpace>>,
      Try<Fetch.Result<Part c pants>>> tr es,  ngesterTw ter ssage tw ter ssage) {
    handleCoreFetchTry(tr es._1(), tw ter ssage);
    handlePart c pantsFetchTry(tr es._2(), tw ter ssage);
  }

  pr vate vo d handleFutureOnFa lure() {
    parallelFetchFa lure. ncre nt();
  }

  pr vate vo d handleCoreFetchTry(
      Try<Fetch.Result<Aud oSpace>> fetchTry,
       ngesterTw ter ssage tw ter ssage) {

     f (fetchTry. sReturn()) {
      coreFetchSuccess. ncre nt();
      addSpaceT leTo ssage(tw ter ssage, fetchTry.get().v());
    } else {
      coreFetchFa lure. ncre nt();
    }
  }

  pr vate vo d handlePart c pantsFetchTry(
      Try<Fetch.Result<Part c pants>> fetchTry,
       ngesterTw ter ssage tw ter ssage) {

     f (fetchTry. sReturn()) {
      part c pantsFetchSuccess. ncre nt();
      addSpaceAdm nsTo ssage(tw ter ssage, fetchTry.get().v());
    } else {
      part c pantsFetchFa lure. ncre nt();
    }
  }

  pr vate vo d addSpaceT leTo ssage(
       ngesterTw ter ssage tw ter ssage,
      Opt on<Aud oSpace> aud oSpace) {

     f (aud oSpace. sDef ned()) {
      Str ng aud oSpaceT le = aud oSpace.get().getT le();
       f (Str ngUt ls. sNotEmpty(aud oSpaceT le)) {
        tw ter ssage.setSpaceT le(aud oSpaceT le);
        t etsW hSpaceT le. ncre nt();
      } else {
        emptySpaceT le. ncre nt();
      }
    } else {
      emptyCore. ncre nt();
    }
  }

  pr vate vo d addSpaceAdm nsTo ssage(
       ngesterTw ter ssage tw ter ssage,
      Opt on<Part c pants> part c pants) {

     f (part c pants. sDef ned()) {
      L st<Part c pantUser> adm ns = getAdm nsFromPart c pants(part c pants.get());
       f (!adm ns. sEmpty()) {
        for (Part c pantUser adm n : adm ns) {
          addSpaceAdm nTo ssage(tw ter ssage, adm n);
        }
        t etsW hSpaceAdm ns. ncre nt();
      } else {
        emptySpaceAdm ns. ncre nt();
      }
    } else {
      emptyPart c pants. ncre nt();
    }
  }

  pr vate L st<Part c pantUser> getAdm nsFromPart c pants(Part c pants part c pants) {
     f (!part c pants. sSetAdm ns()) {
      return L sts.newArrayL st();
    }
    return part c pants.getAdm ns();
  }

  pr vate vo d addSpaceAdm nTo ssage( ngesterTw ter ssage tw ter ssage,
                                      Part c pantUser adm n) {
    Tw ter ssageUser.Bu lder userBu lder = new Tw ter ssageUser.Bu lder();
     f (adm n. sSetTw ter_screen_na ()
        && Str ngUt ls. sNotEmpty(adm n.getTw ter_screen_na ())) {
      userBu lder.w hScreenNa (Opt onal.of(adm n.getTw ter_screen_na ()));
    }
     f (adm n. sSetD splay_na () && Str ngUt ls. sNotEmpty(adm n.getD splay_na ())) {
      userBu lder.w hD splayNa (Opt onal.of(adm n.getD splay_na ()));
    }
    tw ter ssage.addSpaceAdm n(userBu lder.bu ld());
  }
}
