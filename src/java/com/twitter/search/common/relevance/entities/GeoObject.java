package com.tw ter.search.common.relevance.ent  es;

 mport java.ut l.L st;
 mport java.ut l.Opt onal;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftGeoLocat onS ce;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftGeoTags;
 mport com.tw ter.t etyp e.thr ftjava.GeoCoord nates;
 mport com.tw ter.t etyp e.thr ftjava.Place;

 mport geo.google.datamodel.GeoAddressAccuracy;

/**
 * A GeoObject, extend ng a GeoCoord nate to  nclude rad us and accuracy
 */
publ c class GeoObject {

  publ c stat c f nal  nt  NT_F ELD_NOT_PRESENT = -1;
  publ c stat c f nal double DOUBLE_F ELD_NOT_PRESENT = -1.0;

  pr vate double lat ude = DOUBLE_F ELD_NOT_PRESENT;
  pr vate double long ude = DOUBLE_F ELD_NOT_PRESENT;
  pr vate double rad us = DOUBLE_F ELD_NOT_PRESENT;

  pr vate f nal Thr ftGeoLocat onS ce s ce;

  // Val d range  s 0-9. W h 0 be ng unknown and 9 be ng most accurate.
  //  f t  GeoObject  s val d, t  should be set to  NT_F ELD_NOT_PRESENT
  pr vate  nt accuracy = 0;

  /** Creates a new GeoObject  nstance. */
  publ c GeoObject(double lat, double lon, Thr ftGeoLocat onS ce s ce) {
    t (lat, lon, 0, s ce);
  }

  /** Creates a new GeoObject  nstance. */
  publ c GeoObject(double lat, double lon,  nt acc, Thr ftGeoLocat onS ce s ce) {
    lat ude = lat;
    long ude = lon;
    accuracy = acc;
    t .s ce = s ce;
  }

  /** Creates a new GeoObject  nstance. */
  publ c GeoObject(Thr ftGeoLocat onS ce s ce) {
    t .s ce = s ce;
  }

  /**
   * Tr es to create a {@code GeoObject}  nstance from a g ven T etyP e {@code Place} struct based
   * on  s bound ng box coord nates.
   *
   * @param place
   * @return {@code Opt onal}  nstance w h {@code GeoObject}  f bound ng box coord nates are
   *         ava lable, or an empty {@code Opt onal}.
   */
  publ c stat c Opt onal<GeoObject> fromPlace(Place place) {
    // Can't use place.centro d: from t  sample of data, centro d seems to always be null
    // (as of May 17 2016).
     f (place. sSetBound ng_box() && place.getBound ng_boxS ze() > 0) {
       nt po ntsCount = place.getBound ng_boxS ze();

       f (po ntsCount == 1) {
        GeoCoord nates po nt = place.getBound ng_box().get(0);
        return Opt onal.of(createFor ngester(po nt.getLat ude(), po nt.getLong ude()));
      } else {
        double sumLat ude = 0.0;
        double sumLong ude = 0.0;

        L st<GeoCoord nates> box = place.getBound ng_box();

        // Drop t  last po nt  f  's t  sa  as t  f rst po nt.
        // T  sa  log c  s present  n several ot r classes deal ng w h places.
        // See e.g. b rd rd/src/ma n/scala/com/tw ter/b rd rd/t etyp e/T etyP ePlace.scala
         f (box.get(po ntsCount - 1).equals(box.get(0))) {
          po ntsCount--;
        }

        for ( nt   = 0;   < po ntsCount;  ++) {
          GeoCoord nates coords = box.get( );
          sumLat ude += coords.getLat ude();
          sumLong ude += coords.getLong ude();
        }

        double averageLat ude = sumLat ude / po ntsCount;
        double averageLong ude = sumLong ude / po ntsCount;
        return Opt onal.of(GeoObject.createFor ngester(averageLat ude, averageLong ude));
      }
    }
    return Opt onal.empty();
  }

  publ c vo d setRad us(double rad us) {
    t .rad us = rad us;
  }

  publ c Double getRad us() {
    return rad us;
  }

  publ c vo d setLat ude(double lat ude) {
    t .lat ude = lat ude;
  }

  publ c Double getLat ude() {
    return lat ude;
  }

  publ c vo d setLong ude(double long ude) {
    t .long ude = long ude;
  }

  publ c Double getLong ude() {
    return long ude;
  }

  publ c  nt getAccuracy() {
    return accuracy;
  }

  publ c vo d setAccuracy( nt accuracy) {
    t .accuracy = accuracy;
  }

  publ c Thr ftGeoLocat onS ce getS ce() {
    return s ce;
  }

  /** Convers t  GeoObject  nstance to a Thr ftGeoTags  nstance. */
  publ c Thr ftGeoTags toThr ftGeoTags(long tw ter ssage d) {
    Thr ftGeoTags geoTags = new Thr ftGeoTags();
    geoTags.setStatus d(tw ter ssage d);
    geoTags.setLat ude(getLat ude());
    geoTags.setLong ude(getLong ude());
    geoTags.setAccuracy(accuracy);
    geoTags.setGeoLocat onS ce(s ce);
    return geoTags;
  }

  pr vate stat c f nal double COORDS_EQUAL TY_THRESHOLD = 1e-7;

  /**
   * Performs an approx mate compar son bet en t  two GeoObject  nstances.
   *
   * @deprecated T  code  s not performant and should not be used  n
   * product on code. Use only for tests. See SEARCH-5148.
   */
  @Deprecated
  @V s bleForTest ng
  publ c stat c boolean approxEquals(GeoObject a, GeoObject b) {
     f (a == null && b == null) {
      return true;
    }
     f ((a == null && b != null) || (a != null && b == null)) {
      return false;
    }

     f (a.accuracy != b.accuracy) {
      return false;
    }
     f (Math.abs(a.lat ude - b.lat ude) > COORDS_EQUAL TY_THRESHOLD) {
      return false;
    }
     f (Math.abs(a.long ude - b.long ude) > COORDS_EQUAL TY_THRESHOLD) {
      return false;
    }
     f (Double.compare(a.rad us, b.rad us) != 0) {
      return false;
    }
     f (a.s ce != b.s ce) {
      return false;
    }

    return true;
  }

  @Overr de
  publ c Str ng toStr ng() {
    return "GeoObject{"
        + "lat ude=" + lat ude
        + ", long ude=" + long ude
        + ", rad us=" + rad us
        + ", s ce=" + s ce
        + ", accuracy=" + accuracy
        + '}';
  }

  /**
   * Conven ence factory  thod for  ngester purposes.
   */
  publ c stat c GeoObject createFor ngester(double lat ude, double long ude) {
    return new GeoObject(
        lat ude,
        long ude,
        // store w h h g st level of accuracy: PO NT_LEVEL
        GeoAddressAccuracy.PO NT_LEVEL.getCode(),
        Thr ftGeoLocat onS ce.GEOTAG);
  }
}
