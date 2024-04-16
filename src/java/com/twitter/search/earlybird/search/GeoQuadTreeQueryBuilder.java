package com.tw ter.search.earlyb rd.search;

 mport java. o. OExcept on;
 mport java.ut l.L nkedHashSet;
 mport java.ut l.Set;

 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene. ndex.Nu r cDocValues;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.spat al.pref x.tree.Cell;
 mport org.apac .lucene.spat al.pref x.tree.Cell erator;
 mport org.apac .lucene.ut l.BytesRef;
 mport org.locat ontech.spat al4j.shape.Rectangle;

 mport com.tw ter.search.common.query.Mult TermD sjunct onQuery;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.search.GeoQuadTreeQueryBu lderUt l;
 mport com.tw ter.search.common.search.Term nat onTracker;
 mport com.tw ter.search.common.ut l.spat al.Bound ngBox;
 mport com.tw ter.search.common.ut l.spat al.GeoUt l;
 mport com.tw ter.search.common.ut l.spat al.GeohashChunk mpl;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.earlyb rd.search.quer es.GeoTwoPhaseQuery;
 mport com.tw ter.search.earlyb rd.search.quer es.GeoTwoPhaseQuery.SecondPhaseDocAccepter;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.search.queryparser.ut l.GeoCode;

 mport geo.google.datamodel.GeoCoord nate;

/**
 * A class that bu lds quer es to query t  quadtree.
 */
publ c f nal class GeoQuadTreeQueryBu lder {
  pr vate GeoQuadTreeQueryBu lder() {
  }

  /**
   * Returns a GeoTwoPhaseQuery for t  g ven geocode.
   */
  publ c stat c Query bu ldGeoQuadTreeQuery(f nal GeoCode geocode) {
    return bu ldGeoQuadTreeQuery(geocode, null);
  }

  /**
   * Returns a GeoTwoPhaseQuery for t  g ven geocode.
   *
   * @param geocode T  geocode.
   * @param term nat onTracker T  tracker that determ nes w n t  query needs to term nate.
   */
  publ c stat c Query bu ldGeoQuadTreeQuery(GeoCode geocode,
                                            Term nat onTracker term nat onTracker) {
    Query geoHashD sjunt veQuery = GeoQuadTreeQueryBu lderUt l.bu ldGeoQuadTreeQuery(
        geocode, Earlyb rdF eldConstant.GEO_HASH_F ELD.getF eldNa ());

    // 5. Create post f lter ng accepter
    f nal SecondPhaseDocAccepter accepter = (geocode.d stanceKm != GeoCode.DOUBLE_D STANCE_NOT_SET)
            ? new CenterRad usAccepter(geocode.lat ude, geocode.long ude, geocode.d stanceKm)
            : GeoTwoPhaseQuery.ALL_DOCS_ACCEPTER;

    return new GeoTwoPhaseQuery(geoHashD sjunt veQuery, accepter, term nat onTracker);
  }

  /**
   * Construct a query as below:
   *   1. Compute all quadtree cells that  ntersects t  bound ng box.
   *   2. Create a d sjunct on of t  geohas s of all t   ntersect ng cells.
   *   3. Add a f lter to only keep po nts  ns de t  g v ng bound ng box.
   */
  publ c stat c Query bu ldGeoQuadTreeQuery(f nal Rectangle bound ngBox,
                                            f nal Term nat onTracker term nat onTracker)
      throws QueryParserExcept on {
    // 1. Locate t  ma n quadtree cell---t  cell conta n ng t  bound ng box's center po nt whose
    // d agonal  s just longer than t  bound ng box's d agonal.
    f nal Cell centerCell = GeohashChunk mpl.getGeoNodeByBound ngBox(bound ngBox);

    // 2. Determ ne quadtree level to search.
     nt treeLevel = -1;
     f (centerCell != null) {
      treeLevel = centerCell.getLevel();
    } else {
      // T  should not happen.
      throw new QueryParserExcept on(
          "Unable to locate quadtree cell conta n ng t  g ven bound ng box."
          + "Bound ng box  s: " + bound ngBox);
    }

    // 3. get all quadtree cells at treeLevel that  ntersects t  g ven bound ng box.
    Cell erator  ntersect ngCells =
        GeohashChunk mpl.getNodes ntersect ngBound ngBox(bound ngBox, treeLevel);

    // 4. Construct d sjunct on query
    f nal Set<BytesRef> geoHashSet = new L nkedHashSet<>();

    // Add center node
    geoHashSet.add(centerCell.getTokenBytesNoLeaf(new BytesRef()));
    //  f t re are ot r nodes  ntersect ng query c rcle, also add t m  n.
     f ( ntersect ngCells != null) {
      wh le ( ntersect ngCells.hasNext()) {
        geoHashSet.add( ntersect ngCells.next().getTokenBytesNoLeaf(new BytesRef()));
      }
    }
    Mult TermD sjunct onQuery geoHashD sjunt veQuery = new Mult TermD sjunct onQuery(
        Earlyb rdF eldConstant.GEO_HASH_F ELD.getF eldNa (), geoHashSet);

    // 5. Create post f lter ng accepter
    f nal GeoDocAccepter accepter = new Bound ngBoxAccepter(bound ngBox);

    return new GeoTwoPhaseQuery(geoHashD sjunt veQuery, accepter, term nat onTracker);
  }

  pr vate abstract stat c class GeoDocAccepter extends SecondPhaseDocAccepter {
    pr vate Nu r cDocValues latLonDocValues;
    pr vate f nal GeoCoord nate geoCoordReuse = new GeoCoord nate();

    @Overr de
    publ c vo d  n  al ze(LeafReaderContext context) throws  OExcept on {
      f nal Earlyb rd ndexSeg ntAtom cReader reader =
          (Earlyb rd ndexSeg ntAtom cReader) context.reader();
      latLonDocValues =
          reader.getNu r cDocValues(Earlyb rdF eldConstant.LAT_LON_CSF_F ELD.getF eldNa ());
    }

    // Dec des w t r a po nt should be accepted.
    protected abstract boolean acceptPo nt(double lat, double lon);

    // Dec des w t r a docu nt should be accepted based on  s geo coord nates.
    @Overr de
    publ c f nal boolean accept( nt  nternalDoc d) throws  OExcept on {
      // Cannot obta n val d geo coord nates for t  docu nt. Not acceptable.
       f (latLonDocValues == null
          || !latLonDocValues.advanceExact( nternalDoc d)
          || !GeoUt l.decodeLatLonFrom nt64(latLonDocValues.longValue(), geoCoordReuse)) {
        return false;
      }

      return acceptPo nt(geoCoordReuse.getLat ude(), geoCoordReuse.getLong ude());
    }
  }

  // Accepts po nts w h n a c rcle def ned by a center po nt and a rad us.
  pr vate stat c f nal class CenterRad usAccepter extends GeoDocAccepter {
    pr vate f nal double centerLat;
    pr vate f nal double centerLon;
    pr vate f nal double rad usKm;

    publ c CenterRad usAccepter(double centerLat, double centerLon, double rad usKm) {
      t .centerLat = centerLat;
      t .centerLon = centerLon;
      t .rad usKm = rad usKm;
    }

    @Overr de
    protected boolean acceptPo nt(double lat, double lon) {
      double actualD stance =
          Bound ngBox.approxD stanceC(centerLat, centerLon, lat, lon);
       f (actualD stance < rad usKm) {
        return true;
      } else  f (Double. sNaN(actualD stance)) {
        // T re seems to be a rare bug  n GeoUt ls that computes NaN
        // for two  dent cal lat/lon pa rs on occas on. C ck for that  re.
         f (lat == centerLat && lon == centerLon) {
          return true;
        }
      }

      return false;
    }

    @Overr de
    publ c Str ng toStr ng() {
      return Str ng.format("CenterRad usAccepter(Center: %.4f, %.4f Rad us (km): %.4f)",
              centerLat, centerLon, rad usKm);
    }
  }

  // Accepts po nts w h n a Bound ngBox
  pr vate stat c f nal class Bound ngBoxAccepter extends GeoDocAccepter {
    pr vate f nal Rectangle bound ngBox;

    publ c Bound ngBoxAccepter(Rectangle bound ngBox)  {
      t .bound ngBox = bound ngBox;
    }

    @Overr de
    protected boolean acceptPo nt(double lat, double lon) {
      return GeohashChunk mpl. sPo nt nBound ngBox(lat, lon, bound ngBox);

    }

    @Overr de
    publ c Str ng toStr ng() {
      return Str ng.format("Po nt nBound ngBoxAccepter((%.4f, %.4f), (%.4f, %.4f), "
              + "crossesDateL ne=%b)",
              bound ngBox.getM nY(), bound ngBox.getM nX(),
              bound ngBox.getMaxY(), bound ngBox.getMaxX(),
              bound ngBox.getCrossesDateL ne());
    }
  }
}
