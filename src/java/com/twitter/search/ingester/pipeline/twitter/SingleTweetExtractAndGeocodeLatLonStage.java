package com.tw ter.search. ngester.p pel ne.tw ter;

 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;
 mport org.apac .commons.p pel ne.val dat on.ProducesConsu d;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftGeoLocat onS ce;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common.relevance.ent  es.GeoObject;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage;
 mport com.tw ter.search.common.relevance.text.Locat onUt ls;
 mport com.tw ter.search. ngester.model. ngesterTw ter ssage;
 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neStageRunt  Except on;

/**
 * Read-only stage to extract lat/lon pa rs from t  t et text and populate
 * t  geoLocat on f eld.
 * <p>
 *  f t  t et  s geotagged by mob le dev ces, t  geo coord nates extracted from t  JSON
 *  s used.
 */
@Consu dTypes( ngesterTw ter ssage.class)
@ProducesConsu d
publ c class S ngleT etExtractAndGeocodeLatLonStage extends Tw terBaseStage
    <Tw ter ssage,  ngesterTw ter ssage> {
  pr vate stat c f nal Logger LOG =
      LoggerFactory.getLogger(S ngleT etExtractAndGeocodeLatLonStage.class);

  pr vate SearchRateCounter extractedLatLons;
  pr vate SearchRateCounter badLatLons;

  @Overr de
  publ c vo d  n Stats() {
    super. n Stats();
     nnerSetupStats();
  }

  @Overr de
  protected vo d  nnerSetupStats() {
    extractedLatLons = SearchRateCounter.export(getStageNa Pref x() + "_extracted_lat_lons");
    badLatLons = SearchRateCounter.export(getStageNa Pref x() + "_ nval d_lat_lons");
  }

  @Overr de
  publ c vo d  nnerProcess(Object obj) throws StageExcept on {
     f (!(obj  nstanceof  ngesterTw ter ssage)) {
      throw new StageExcept on(t , "Object  s not  ngesterTw ter ssage object: " + obj);
    }

     ngesterTw ter ssage  ssage =  ngesterTw ter ssage.class.cast(obj);
    tryToSetGeoLocat on( ssage);
    em AndCount( ssage);
  }

  @Overr de
  protected  ngesterTw ter ssage  nnerRunStageV2(Tw ter ssage  ssage) {
    // Prev ous stage takes  n a Tw ter ssage and returns a Tw ter ssage.   th nk   was
    // done to s mpl fy test ng. From t  stage onwards,   only count t   ssage that are of type
    //  ngesterTw ter ssage.
     f (!( ssage  nstanceof  ngesterTw ter ssage)) {
      throw new P pel neStageRunt  Except on(" ssage needs to be of type  ngesterTw ter ssage");
    }

     ngesterTw ter ssage  ngesterTw ter ssage =  ngesterTw ter ssage.class.cast( ssage);
    tryToSetGeoLocat on( ngesterTw ter ssage);
    return  ngesterTw ter ssage;
  }

  pr vate vo d tryToSetGeoLocat on( ngesterTw ter ssage  ssage) {
     f ( ssage.getGeoTaggedLocat on() != null) {
       ssage.setGeoLocat on( ssage.getGeoTaggedLocat on());
    } else  f ( ssage.hasGeoLocat on()) {
      LOG.warn(" ssage {} already conta ns geoLocat on",  ssage.get d());
    } else {
      try {
        GeoObject extracted = extractLatLon( ssage);
         f (extracted != null) {
           ssage.setGeoLocat on(extracted);
          extractedLatLons. ncre nt();
        }
      } catch (NumberFormatExcept on e) {
        LOG.debug(" ssage conta ns bad lat ude and long ude: " +  ssage.getOr gLocat on(), e);
        badLatLons. ncre nt();
      } catch (Except on e) {
        LOG.error("Fa led to extract geo locat on from " +  ssage.getOr gLocat on() + " for t et "
            +  ssage.get d(), e);
      }
    }
  }

  pr vate GeoObject extractLatLon( ngesterTw ter ssage  ssage) throws NumberFormatExcept on {
    double[] latlon = Locat onUt ls.extractLatLon( ssage);
    return latlon == null
        ? null
        : new GeoObject(latlon[0], latlon[1], Thr ftGeoLocat onS ce.TWEET_TEXT);
  }
}
