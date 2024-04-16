package com.tw ter.search. ngester.p pel ne.tw ter;

 mport java.net.Malfor dURLExcept on;
 mport java.ut l.ArrayL st;
 mport java.ut l.Collect ons;
 mport java.ut l.HashMap;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Set;
 mport javax.nam ng.Nam ngExcept on;

 mport com.google.common.collect.Maps;

 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .commons.p pel ne.stage.StageT  r;
 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;
 mport org.apac .commons.p pel ne.val dat on.ProducesConsu d;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.text.language.LocaleUt l;
 mport com.tw ter.expandodo.thr ftjava.Card2;
 mport com.tw ter. d aserv ces.commons.t et d a.thr ft_java. d a nfo;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftExpandedUrl;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search. ngester.model. ngesterTw ter ssage;
 mport com.tw ter.search. ngester.p pel ne.ut l.Batch ngCl ent;
 mport com.tw ter.search. ngester.p pel ne.ut l.CardF eldUt l;
 mport com.tw ter.search. ngester.p pel ne.ut l. ngesterStageT  r;
 mport com.tw ter.search. ngester.p pel ne.ut l.ResponseNotReturnedExcept on;
 mport com.tw ter.sp derduck.common.URLUt ls;
 mport com.tw ter.t etyp e.thr ftjava.GetT etOpt ons;
 mport com.tw ter.t etyp e.thr ftjava.GetT etResult;
 mport com.tw ter.t etyp e.thr ftjava.GetT etsRequest;
 mport com.tw ter.t etyp e.thr ftjava. d aEnt y;
 mport com.tw ter.t etyp e.thr ftjava.StatusState;
 mport com.tw ter.t etyp e.thr ftjava.T et;
 mport com.tw ter.t etyp e.thr ftjava.T etServ ce;
 mport com.tw ter.ut l.Funct on;
 mport com.tw ter.ut l.Future;

@Consu dTypes( ngesterTw ter ssage.class)
@ProducesConsu d
publ c class Retr eveCardBatc dStage extends Tw terBaseStage
    < ngesterTw ter ssage,  ngesterTw ter ssage> {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Retr eveCardBatc dStage.class);

  pr vate stat c f nal Str ng CARDS_PLATFORM_KEY = " Phone-13";
  pr vate  nt batchS ze = 10;

  pr vate SearchRateCounter totalT ets;
  pr vate SearchRateCounter t etsW hCards;
  pr vate SearchRateCounter t etsW houtCards;
  pr vate SearchRateCounter t etsW hAn matedG f d a nfo;
  pr vate SearchRateCounter cardsW hNa ;
  pr vate SearchRateCounter cardsW hDoma n;
  pr vate SearchRateCounter cardsW hT les;
  pr vate SearchRateCounter cardsW hDescr pt ons;
  pr vate SearchRateCounter cardsW hUnknownLanguage;
  pr vate SearchRateCounter t etsNotFound;
  pr vate SearchRateCounter malfor dUrls;
  pr vate SearchRateCounter urlM smatc s;
  pr vate SearchRateCounter cardExcept ons;
  pr vate SearchRateCounter cardExcept onT ets;
  pr vate StageT  r retr eveCardsT  r;

  pr vate Str ng cardNa Pref x;
  // S nce t re  s only one thread execut ng t  stage (although that could potent ally be
  // changed  n t  p pel ne conf g), no need to be thread safe.
  pr vate stat c f nal Map<Str ng, SearchRateCounter> CARD_NAME_STATS = new HashMap<>();

  pr vate stat c T etServ ce.Serv ceToCl ent t etyP eServ ce;
  pr vate Batch ngCl ent<Long, Card2> cardsCl ent;

  pr vate Str ng t etyp eCl ent d = null;

  // Can be overr dden  n t  correspond ng p pel ne- ngester.*.xml conf g.
  // By default protected t ets are f ltered out.
  // Only  n t  protected  ngester p pel ne  s t  set to false.
  pr vate boolean f lterProtected = true;

  @Overr de
  publ c vo d  n Stats() {
    super. n Stats();
    cardNa Pref x = getStageNa Pref x() + "_card_na _";
    totalT ets = SearchRateCounter.export(getStageNa Pref x() + "_total_t ets");
    t etsW hCards = SearchRateCounter.export(getStageNa Pref x() + "_t ets_w h_cards");
    t etsW houtCards = SearchRateCounter.export(getStageNa Pref x() + "_t ets_w hout_cards");
    t etsW hAn matedG f d a nfo =
        SearchRateCounter.export(getStageNa Pref x() + "_t ets_w h_an mated_g f_ d a_ nfo");
    cardsW hNa  = SearchRateCounter.export(getStageNa Pref x() + "_t ets_w h_card_na ");
    cardsW hDoma n = SearchRateCounter.export(getStageNa Pref x() + "_t ets_w h_card_doma n");
    cardsW hT les = SearchRateCounter.export(getStageNa Pref x() + "_t ets_w h_card_t les");
    cardsW hDescr pt ons =
        SearchRateCounter.export(getStageNa Pref x() + "_t ets_w h_card_descr pt ons");
    cardsW hUnknownLanguage =
        SearchRateCounter.export(getStageNa Pref x() + "_t ets_w h_unknown_card_lanuage");
    t etsNotFound = SearchRateCounter.export(getStageNa Pref x() + "_t ets_not_found");
    malfor dUrls = SearchRateCounter.export(getStageNa Pref x() + "_malfor d_urls");
    urlM smatc s = SearchRateCounter.export(getStageNa Pref x() + "_url_m smatc s");
    cardExcept ons = SearchRateCounter.export(getStageNa Pref x() + "_card_except ons");
    cardExcept onT ets =
        SearchRateCounter.export(getStageNa Pref x() + "_card_except on_t ets");
    retr eveCardsT  r = new  ngesterStageT  r(getStageNa Pref x() + "_request_t  r");
  }

  @Overr de
  protected vo d do nnerPreprocess() throws StageExcept on, Nam ngExcept on {
    super.do nnerPreprocess();
    t etyP eServ ce = w reModule.getT etyP eCl ent(t etyp eCl ent d);
    cardsCl ent = new Batch ngCl ent<>(t ::batchRetr eveURLs, batchS ze);
  }

  @Overr de
  publ c vo d  nnerProcess(Object obj) throws StageExcept on {
     f (!(obj  nstanceof  ngesterTw ter ssage)) {
      throw new StageExcept on(t ,
          "Rece ved object of  ncorrect type: " + obj.getClass().getNa ());
    }

     ngesterTw ter ssage  ssage = ( ngesterTw ter ssage) obj;

    cardsCl ent.call( ssage.getT et d())
        .onSuccess(Funct on.cons(card -> {
          update ssage( ssage, card);
          em AndCount( ssage);
        }))
        .onFa lure(Funct on.cons(except on -> {
           f (!(except on  nstanceof ResponseNotReturnedExcept on)) {
            cardExcept onT ets. ncre nt();
          }

          em AndCount( ssage);
        }));
  }

  pr vate Future<Map<Long, Card2>> batchRetr eveURLs(Set<Long> keys) {
    retr eveCardsT  r.start();
    totalT ets. ncre nt(keys.s ze());

    GetT etOpt ons opt ons = new GetT etOpt ons()
        .set nclude_cards(true)
        .setCards_platform_key(CARDS_PLATFORM_KEY)
        .setBypass_v s b l y_f lter ng(!f lterProtected);

    GetT etsRequest request = new GetT etsRequest()
        .setOpt ons(opt ons)
        .setT et_ ds(new ArrayL st<>(keys));

    return t etyP eServ ce.get_t ets(request)
        .onFa lure(throwable -> {
          cardExcept ons. ncre nt();
          LOG.error("T etyP e server threw an except on wh le request ng t et ds: "
              + request.getT et_ ds(), throwable);
          return null;
        })
        .map(t ::create dToCardMap);
  }

  pr vate vo d update ssage( ngesterTw ter ssage  ssage, Card2 card) {
    t etsW hCards. ncre nt();

    Str ng cardNa  = card.getNa ().toLo rCase();
    addCardNa ToStats(cardNa );
     ssage.setCardNa (cardNa );
    cardsW hNa . ncre nt();
     ssage.setCardUrl(card.getUrl());

    Str ng url = getLastHop( ssage, card.getUrl());
     f (url != null) {
      try {
        Str ng doma n = URLUt ls.getDoma nFromURL(url);
         ssage.setCardDoma n(doma n.toLo rCase());
        cardsW hDoma n. ncre nt();
      } catch (Malfor dURLExcept on e) {
        malfor dUrls. ncre nt();
         f (LOG. sDebugEnabled()) {
          LOG.debug("T et  D {} has a malfor d card last hop URL: {}",  ssage.get d(), url);
        }
      }
    } else {
      // T  happens w h ret et. Bas cally w n retr eve card for a ret et,  
      // get a card assoc ated w h t  or g nal t et, so t  tco won't match.
      // As of Sep 2014, t  seems to be t   ntended behav or and has been runn ng
      // l ke t  for over a year.
      urlM smatc s. ncre nt();
    }

     ssage.setCardT le(
        CardF eldUt l.extractB nd ngValue(CardF eldUt l.T TLE_B ND NG_KEY, card));
     f ( ssage.getCardT le() != null) {
      cardsW hT les. ncre nt();
    }
     ssage.setCardDescr pt on(
        CardF eldUt l.extractB nd ngValue(CardF eldUt l.DESCR PT ON_B ND NG_KEY, card));
     f ( ssage.getCardDescr pt on() != null) {
      cardsW hDescr pt ons. ncre nt();
    }
    CardF eldUt l.der veCardLang( ssage);
     f (LocaleUt l.UNKNOWN.getLanguage().equals( ssage.getCardLang())) {
      cardsW hUnknownLanguage. ncre nt();
    }
  }

  pr vate Map<Long, Card2> create dToCardMap(L st<GetT etResult> l stResult) {
    Map<Long, Card2> responseMap = Maps.newHashMap();
    for (GetT etResult entry : l stResult) {
       f (entry. sSetT et()
          && entry. sSetT et_state()
          && (entry.getT et_state() == StatusState.FOUND)) {
        long  d = entry.getT et_ d();
         f (entry.getT et(). sSetCard2()) {
          responseMap.put( d, entry.getT et().getCard2());
        } else {
          // Short-term f x for removal of an mated G F cards --
          //  f t  t et conta ns an an mated G F, create a card based on  d a ent y data
          Card2 card = createCardForAn matedG f(entry.getT et());
           f (card != null) {
            responseMap.put( d, card);
            t etsW hAn matedG f d a nfo. ncre nt();
          } else {
            t etsW houtCards. ncre nt();
          }
        }
      } else {
        t etsNotFound. ncre nt();
      }
    }
    return responseMap;
  }

  pr vate Card2 createCardForAn matedG f(T et t et) {
     f (t et.get d aS ze() > 0) {
      for ( d aEnt y  d aEnt y : t et.get d a()) {
         d a nfo  d a nfo =  d aEnt y.get d a_ nfo();
         f ( d a nfo != null &&  d a nfo.getSetF eld() ==  d a nfo._F elds.AN MATED_G F_ NFO) {
          Card2 card = new Card2();
          card.setNa ("an mated_g f");
          // Use t  or g nal compressed URL for t   d a ent y to match ex st ng card URLs
          card.setUrl( d aEnt y.getUrl());
          card.setB nd ng_values(Collect ons.emptyL st());

          return card;
        }
      }
    }
    return null;
  }

  // Unfortunately t  url returned  n t  card data  s not t  last hop
  pr vate Str ng getLastHop( ngesterTw ter ssage  ssage, Str ng url) {
     f ( ssage.getExpandedUrlMap() != null) {
      Thr ftExpandedUrl expanded =  ssage.getExpandedUrlMap().get(url);
       f ((expanded != null) && expanded. sSetCanon calLastHopUrl()) {
        return expanded.getCanon calLastHopUrl();
      }
    }
    return null;
  }

  // Used by commons-p pel ne and set v a t  xml conf g
  publ c vo d setF lterProtected(boolean f lterProtected) {
    LOG. nfo("F lter ng protected t ets: {}", f lterProtected);
    t .f lterProtected = f lterProtected;
  }

  publ c vo d setT etyp eCl ent d(Str ng t etyp eCl ent d) {
    LOG. nfo("Us ng t etyp eCl ent d: {}", t etyp eCl ent d);
    t .t etyp eCl ent d = t etyp eCl ent d;
  }

  publ c vo d set nternalBatchS ze( nt  nternalBatchS ze) {
    t .batchS ze =  nternalBatchS ze;
  }

  /**
   * For each card na ,   add a rate counter to observe what k nds of card  're actually
   *  ndex ng, and w h what rate.
   */
  pr vate vo d addCardNa ToStats(Str ng cardNa ) {
    SearchRateCounter cardNa Counter = CARD_NAME_STATS.get(cardNa );
     f (cardNa Counter == null) {
      cardNa Counter = SearchRateCounter.export(cardNa Pref x + cardNa );
      CARD_NAME_STATS.put(cardNa , cardNa Counter);
    }
    cardNa Counter. ncre nt();
  }
}
