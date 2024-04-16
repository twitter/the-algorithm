package com.tw ter.search.common.search;

 mport java.ut l.L nkedHashSet;
 mport java.ut l.Set;

 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.spat al.pref x.tree.Cell;
 mport org.apac .lucene.spat al.pref x.tree.Cell erator;
 mport org.apac .lucene.ut l.BytesRef;

 mport com.tw ter.search.common.ut l.spat al.GeohashChunk mpl;
 mport com.tw ter.search.queryparser.ut l.GeoCode;

 mport geo.google.datamodel.GeoAddressAccuracy;

publ c f nal class GeoQuadTreeQueryBu lderUt l {
  pr vate GeoQuadTreeQueryBu lderUt l() {
  }

  /**
   * Bu ld a geo quad tree query based around t  geo code based on t  geo f eld.
   * @param geocode t  geo locat on for t  quad tree query
   * @param f eld t  f eld w re t  geohash tokens are  ndexed
   * @return t  correspond ng for t  geo quad tree query
   */
  publ c stat c Query bu ldGeoQuadTreeQuery(GeoCode geocode, Str ng f eld) {
    Set<BytesRef> geoHashSet = new L nkedHashSet<>();

    //  f accuracy  s spec f ed. Add a term query based on accuracy.
     f (geocode.accuracy != GeoAddressAccuracy.UNKNOWN_LOCAT ON.getCode()) {
      BytesRef termRef = new BytesRef(GeohashChunk mpl.bu ldGeoStr ngW hAccuracy(geocode.lat ude,
          geocode.long ude,
          geocode.accuracy));
      geoHashSet.add(termRef);
    }

    //  f d stance  s spec f ed. Add term quer es based on d stance
     f (geocode.d stanceKm != GeoCode.DOUBLE_D STANCE_NOT_SET) {
      // Bu ld query based on d stance
       nt treeLevel = -1;
      // F rst f nd block conta n ng query po nt w h d agonal greater than 2 * rad us.
      Cell centerNode = GeohashChunk mpl.getGeoNodeByRad us(geocode.lat ude, geocode.long ude,
          geocode.d stanceKm);
      // Add center node query ng term
       f (centerNode != null) {
        geoHashSet.add(centerNode.getTokenBytesNoLeaf(new BytesRef()));
        treeLevel = centerNode.getLevel();
      }

      // T   mproves edge case recall, by add ng cells also  ntersect ng t  query area.
      Cell erator nodes = GeohashChunk mpl.getNodes ntersect ngC rcle(geocode.lat ude,
          geocode.long ude,
          geocode.d stanceKm,
          treeLevel);
      //  f t re are ot r nodes  ntersect ng query c rcle, also add t m  n.
       f (nodes != null) {
        wh le (nodes.hasNext()) {
          geoHashSet.add(nodes.next().getTokenBytesNoLeaf(new BytesRef()));
        }
      }
    }

    return new com.tw ter.search.common.query.Mult TermD sjunct onQuery(f eld, geoHashSet);
  }
}
