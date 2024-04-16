package com.tw ter.search. ngester.p pel ne.ut l;

 mport java.ut l.ArrayL st;
 mport java.ut l.Collect on;
 mport java.ut l. erator;
 mport java.ut l.L st;
 mport java.ut l.Opt onal;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftGeoLocat onS ce;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftGeoPo nt;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftGeocodeRecord;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.relevance.ent  es.GeoObject;
 mport com.tw ter.search.common.ut l.geocod ng.ManhattanGeocodeRecordStore;
 mport com.tw ter.search. ngester.model. ngesterTw ter ssage;
 mport com.tw ter.st ch.St ch;
 mport com.tw ter.storage.cl ent.manhattan.kv.JavaManhattanKVEndpo nt;
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanValue;
 mport com.tw ter.ut l.Funct on;
 mport com.tw ter.ut l.Future;


publ c f nal class ManhattanCodedLocat onProv der {

  pr vate f nal ManhattanGeocodeRecordStore store;
  pr vate f nal SearchCounter locat onsCounter;

  pr vate stat c f nal Str ng LOCAT ONS_POPULATED_STAT_NAME = "_locat ons_populated_count";

  publ c stat c ManhattanCodedLocat onProv der createW hEndpo nt(
      JavaManhattanKVEndpo nt endpo nt, Str ng  tr csPref x, Str ng datasetNa ) {
    return new ManhattanCodedLocat onProv der(
        ManhattanGeocodeRecordStore.create(endpo nt, datasetNa ),  tr csPref x);
  }

  pr vate ManhattanCodedLocat onProv der(ManhattanGeocodeRecordStore store, Str ng  tr cPref x) {
    t .locat onsCounter = SearchCounter.export( tr cPref x + LOCAT ONS_POPULATED_STAT_NAME);
    t .store = store;
  }

  /**
   *  erates through all g ven  ssages, and for each  ssage that has a locat on set, retr eves
   * t  coord nates of that locat on from Manhattan and sets t m back on that  ssage.
   */
  publ c Future<Collect on< ngesterTw ter ssage>> populateCodedLatLon(
      Collect on< ngesterTw ter ssage>  ssages) {
     f ( ssages. sEmpty()) {
      return Future.value( ssages);
    }

    // Batch read requests
    L st<St ch<Opt onal<ManhattanValue<Thr ftGeocodeRecord>>>> readRequests =
        new ArrayL st<>( ssages.s ze());
    for ( ngesterTw ter ssage  ssage :  ssages) {
      readRequests.add(store.asyncReadFromManhattan( ssage.getLocat on()));
    }
    Future<L st<Opt onal<ManhattanValue<Thr ftGeocodeRecord>>>> batc dRequest =
        St ch.run(St ch.collect(readRequests));

    return batc dRequest.map(Funct on.func(optGeoLocat ons -> {
      //  erate over  ssages and responses s multaneously
      Precond  ons.c ckState( ssages.s ze() == optGeoLocat ons.s ze());
       erator< ngesterTw ter ssage>  ssage erator =  ssages. erator();
       erator<Opt onal<ManhattanValue<Thr ftGeocodeRecord>>> optGeoLocat on erator =
          optGeoLocat ons. erator();
      wh le ( ssage erator.hasNext() && optGeoLocat on erator.hasNext()) {
         ngesterTw ter ssage  ssage =  ssage erator.next();
        Opt onal<ManhattanValue<Thr ftGeocodeRecord>> optGeoLocat on =
            optGeoLocat on erator.next();
         f (setGeoLocat onFor ssage( ssage, optGeoLocat on)) {
          locat onsCounter. ncre nt();
        }
      }
      return  ssages;
    }));
  }

  /**
   * Returns w t r a val d geolocat on was successfully found and saved  n t   ssage.
   */
  pr vate boolean setGeoLocat onFor ssage(
       ngesterTw ter ssage  ssage,
      Opt onal<ManhattanValue<Thr ftGeocodeRecord>> optGeoLocat on) {
     f (optGeoLocat on. sPresent()) {
      Thr ftGeocodeRecord geoLocat on = optGeoLocat on.get().contents();
      Thr ftGeoPo nt geoTags = geoLocat on.getGeoPo nt();

       f ((geoTags.getLat ude() == GeoObject.DOUBLE_F ELD_NOT_PRESENT)
          && (geoTags.getLong ude() == GeoObject.DOUBLE_F ELD_NOT_PRESENT)) {
        // T  case  nd cates that   have "negat ve cac "  n coded_locat ons table, so
        // don't try to geocode aga n.
         ssage.setUncodeableLocat on();
        return false;
      } else {
        GeoObject code = new GeoObject(
            geoTags.getLat ude(),
            geoTags.getLong ude(),
            geoTags.getAccuracy(),
            Thr ftGeoLocat onS ce.USER_PROF LE);
         ssage.setGeoLocat on(code);
        return true;
      }
    } else {
       ssage.setGeocodeRequ red();
      return false;
    }
  }
}
