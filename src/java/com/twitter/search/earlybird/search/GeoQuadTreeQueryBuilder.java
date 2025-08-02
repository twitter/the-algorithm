package com.twitter.search.earlybird.search;

import java.io.IOEtwitterception;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.lucene.indetwitter.LeafReaderContetwittert;
import org.apache.lucene.indetwitter.NumericDocValues;
import org.apache.lucene.search.Query;
import org.apache.lucene.spatial.prefitwitter.tree.Cell;
import org.apache.lucene.spatial.prefitwitter.tree.CellIterator;
import org.apache.lucene.util.BytesRef;
import org.locationtech.spatial4j.shape.Rectangle;

import com.twitter.search.common.query.MultiTermDisjunctionQuery;
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant;
import com.twitter.search.common.search.GeoQuadTreeQueryBuilderUtil;
import com.twitter.search.common.search.TerminationTracker;
import com.twitter.search.common.util.spatial.BoundingBotwitter;
import com.twitter.search.common.util.spatial.GeoUtil;
import com.twitter.search.common.util.spatial.GeohashChunkImpl;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentAtomicReader;
import com.twitter.search.earlybird.search.queries.GeoTwoPhaseQuery;
import com.twitter.search.earlybird.search.queries.GeoTwoPhaseQuery.SecondPhaseDocAccepter;
import com.twitter.search.queryparser.query.QueryParserEtwitterception;
import com.twitter.search.queryparser.util.GeoCode;

import geo.google.datamodel.GeoCoordinate;

/**
 * A class that builds queries to query the quadtree.
 */
public final class GeoQuadTreeQueryBuilder {
  private GeoQuadTreeQueryBuilder() {
  }

  /**
   * Returns a GeoTwoPhaseQuery for the given geocode.
   */
  public static Query buildGeoQuadTreeQuery(final GeoCode geocode) {
    return buildGeoQuadTreeQuery(geocode, null);
  }

  /**
   * Returns a GeoTwoPhaseQuery for the given geocode.
   *
   * @param geocode The geocode.
   * @param terminationTracker The tracker that determines when the query needs to terminate.
   */
  public static Query buildGeoQuadTreeQuery(GeoCode geocode,
                                            TerminationTracker terminationTracker) {
    Query geoHashDisjuntiveQuery = GeoQuadTreeQueryBuilderUtil.buildGeoQuadTreeQuery(
        geocode, EarlybirdFieldConstant.GEO_HASH_FIELD.getFieldName());

    // 5. Create post filtering accepter
    final SecondPhaseDocAccepter accepter = (geocode.distanceKm != GeoCode.DOUBLE_DISTANCE_NOT_SET)
            ? new CenterRadiusAccepter(geocode.latitude, geocode.longitude, geocode.distanceKm)
            : GeoTwoPhaseQuery.ALL_DOCS_ACCEPTER;

    return new GeoTwoPhaseQuery(geoHashDisjuntiveQuery, accepter, terminationTracker);
  }

  /**
   * Construct a query as below:
   *   1. Compute all quadtree cells that intersects the bounding botwitter.
   *   2. Create a disjunction of the geohashes of all the intersecting cells.
   *   3. Add a filter to only keep points inside the giving bounding botwitter.
   */
  public static Query buildGeoQuadTreeQuery(final Rectangle boundingBotwitter,
                                            final TerminationTracker terminationTracker)
      throws QueryParserEtwitterception {
    // 1. Locate the main quadtree cell---the cell containing the bounding botwitter's center point whose
    // diagonal is just longer than the bounding botwitter's diagonal.
    final Cell centerCell = GeohashChunkImpl.getGeoNodeByBoundingBotwitter(boundingBotwitter);

    // 2. Determine quadtree level to search.
    int treeLevel = -1;
    if (centerCell != null) {
      treeLevel = centerCell.getLevel();
    } else {
      // This should not happen.
      throw new QueryParserEtwitterception(
          "Unable to locate quadtree cell containing the given bounding botwitter."
          + "Bounding botwitter is: " + boundingBotwitter);
    }

    // 3. get all quadtree cells at treeLevel that intersects the given bounding botwitter.
    CellIterator intersectingCells =
        GeohashChunkImpl.getNodesIntersectingBoundingBotwitter(boundingBotwitter, treeLevel);

    // 4. Construct disjunction query
    final Set<BytesRef> geoHashSet = new LinkedHashSet<>();

    // Add center node
    geoHashSet.add(centerCell.getTokenBytesNoLeaf(new BytesRef()));
    // If there are other nodes intersecting query circle, also add them in.
    if (intersectingCells != null) {
      while (intersectingCells.hasNetwittert()) {
        geoHashSet.add(intersectingCells.netwittert().getTokenBytesNoLeaf(new BytesRef()));
      }
    }
    MultiTermDisjunctionQuery geoHashDisjuntiveQuery = new MultiTermDisjunctionQuery(
        EarlybirdFieldConstant.GEO_HASH_FIELD.getFieldName(), geoHashSet);

    // 5. Create post filtering accepter
    final GeoDocAccepter accepter = new BoundingBotwitterAccepter(boundingBotwitter);

    return new GeoTwoPhaseQuery(geoHashDisjuntiveQuery, accepter, terminationTracker);
  }

  private abstract static class GeoDocAccepter etwittertends SecondPhaseDocAccepter {
    private NumericDocValues latLonDocValues;
    private final GeoCoordinate geoCoordReuse = new GeoCoordinate();

    @Override
    public void initialize(LeafReaderContetwittert contetwittert) throws IOEtwitterception {
      final EarlybirdIndetwitterSegmentAtomicReader reader =
          (EarlybirdIndetwitterSegmentAtomicReader) contetwittert.reader();
      latLonDocValues =
          reader.getNumericDocValues(EarlybirdFieldConstant.LAT_LON_CSF_FIELD.getFieldName());
    }

    // Decides whether a point should be accepted.
    protected abstract boolean acceptPoint(double lat, double lon);

    // Decides whether a document should be accepted based on its geo coordinates.
    @Override
    public final boolean accept(int internalDocId) throws IOEtwitterception {
      // Cannot obtain valid geo coordinates for the document. Not acceptable.
      if (latLonDocValues == null
          || !latLonDocValues.advanceEtwitteract(internalDocId)
          || !GeoUtil.decodeLatLonFromInt64(latLonDocValues.longValue(), geoCoordReuse)) {
        return false;
      }

      return acceptPoint(geoCoordReuse.getLatitude(), geoCoordReuse.getLongitude());
    }
  }

  // Accepts points within a circle defined by a center point and a radius.
  private static final class CenterRadiusAccepter etwittertends GeoDocAccepter {
    private final double centerLat;
    private final double centerLon;
    private final double radiusKm;

    public CenterRadiusAccepter(double centerLat, double centerLon, double radiusKm) {
      this.centerLat = centerLat;
      this.centerLon = centerLon;
      this.radiusKm = radiusKm;
    }

    @Override
    protected boolean acceptPoint(double lat, double lon) {
      double actualDistance =
          BoundingBotwitter.approtwitterDistanceC(centerLat, centerLon, lat, lon);
      if (actualDistance < radiusKm) {
        return true;
      } else if (Double.isNaN(actualDistance)) {
        // There seems to be a rare bug in GeoUtils that computes NaN
        // for two identical lat/lon pairs on occasion. Check for that here.
        if (lat == centerLat && lon == centerLon) {
          return true;
        }
      }

      return false;
    }

    @Override
    public String toString() {
      return String.format("CenterRadiusAccepter(Center: %.4f, %.4f Radius (km): %.4f)",
              centerLat, centerLon, radiusKm);
    }
  }

  // Accepts points within a BoundingBotwitter
  private static final class BoundingBotwitterAccepter etwittertends GeoDocAccepter {
    private final Rectangle boundingBotwitter;

    public BoundingBotwitterAccepter(Rectangle boundingBotwitter)  {
      this.boundingBotwitter = boundingBotwitter;
    }

    @Override
    protected boolean acceptPoint(double lat, double lon) {
      return GeohashChunkImpl.isPointInBoundingBotwitter(lat, lon, boundingBotwitter);

    }

    @Override
    public String toString() {
      return String.format("PointInBoundingBotwitterAccepter((%.4f, %.4f), (%.4f, %.4f), "
              + "crossesDateLine=%b)",
              boundingBotwitter.getMinY(), boundingBotwitter.getMintwitter(),
              boundingBotwitter.getMatwitterY(), boundingBotwitter.getMatwittertwitter(),
              boundingBotwitter.getCrossesDateLine());
    }
  }
}
