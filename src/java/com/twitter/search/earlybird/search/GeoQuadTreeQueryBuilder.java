packagelon com.twittelonr.selonarch.elonarlybird.selonarch;

import java.io.IOelonxcelonption;
import java.util.LinkelondHashSelont;
import java.util.Selont;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.indelonx.NumelonricDocValuelons;
import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.lucelonnelon.spatial.prelonfix.trelonelon.Celonll;
import org.apachelon.lucelonnelon.spatial.prelonfix.trelonelon.CelonllItelonrator;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;
import org.locationtelonch.spatial4j.shapelon.Relonctanglelon;

import com.twittelonr.selonarch.common.quelonry.MultiTelonrmDisjunctionQuelonry;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.common.selonarch.GelonoQuadTrelonelonQuelonryBuildelonrUtil;
import com.twittelonr.selonarch.common.selonarch.TelonrminationTrackelonr;
import com.twittelonr.selonarch.common.util.spatial.BoundingBox;
import com.twittelonr.selonarch.common.util.spatial.GelonoUtil;
import com.twittelonr.selonarch.common.util.spatial.GelonohashChunkImpl;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons.GelonoTwoPhaselonQuelonry;
import com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons.GelonoTwoPhaselonQuelonry.SeloncondPhaselonDocAccelonptelonr;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;
import com.twittelonr.selonarch.quelonryparselonr.util.GelonoCodelon;

import gelono.googlelon.datamodelonl.GelonoCoordinatelon;

/**
 * A class that builds quelonrielons to quelonry thelon quadtrelonelon.
 */
public final class GelonoQuadTrelonelonQuelonryBuildelonr {
  privatelon GelonoQuadTrelonelonQuelonryBuildelonr() {
  }

  /**
   * Relonturns a GelonoTwoPhaselonQuelonry for thelon givelonn gelonocodelon.
   */
  public static Quelonry buildGelonoQuadTrelonelonQuelonry(final GelonoCodelon gelonocodelon) {
    relonturn buildGelonoQuadTrelonelonQuelonry(gelonocodelon, null);
  }

  /**
   * Relonturns a GelonoTwoPhaselonQuelonry for thelon givelonn gelonocodelon.
   *
   * @param gelonocodelon Thelon gelonocodelon.
   * @param telonrminationTrackelonr Thelon trackelonr that delontelonrminelons whelonn thelon quelonry nelonelonds to telonrminatelon.
   */
  public static Quelonry buildGelonoQuadTrelonelonQuelonry(GelonoCodelon gelonocodelon,
                                            TelonrminationTrackelonr telonrminationTrackelonr) {
    Quelonry gelonoHashDisjuntivelonQuelonry = GelonoQuadTrelonelonQuelonryBuildelonrUtil.buildGelonoQuadTrelonelonQuelonry(
        gelonocodelon, elonarlybirdFielonldConstant.GelonO_HASH_FIelonLD.gelontFielonldNamelon());

    // 5. Crelonatelon post filtelonring accelonptelonr
    final SeloncondPhaselonDocAccelonptelonr accelonptelonr = (gelonocodelon.distancelonKm != GelonoCodelon.DOUBLelon_DISTANCelon_NOT_SelonT)
            ? nelonw CelonntelonrRadiusAccelonptelonr(gelonocodelon.latitudelon, gelonocodelon.longitudelon, gelonocodelon.distancelonKm)
            : GelonoTwoPhaselonQuelonry.ALL_DOCS_ACCelonPTelonR;

    relonturn nelonw GelonoTwoPhaselonQuelonry(gelonoHashDisjuntivelonQuelonry, accelonptelonr, telonrminationTrackelonr);
  }

  /**
   * Construct a quelonry as belonlow:
   *   1. Computelon all quadtrelonelon celonlls that intelonrseloncts thelon bounding box.
   *   2. Crelonatelon a disjunction of thelon gelonohashelons of all thelon intelonrseloncting celonlls.
   *   3. Add a filtelonr to only kelonelonp points insidelon thelon giving bounding box.
   */
  public static Quelonry buildGelonoQuadTrelonelonQuelonry(final Relonctanglelon boundingBox,
                                            final TelonrminationTrackelonr telonrminationTrackelonr)
      throws QuelonryParselonrelonxcelonption {
    // 1. Locatelon thelon main quadtrelonelon celonll---thelon celonll containing thelon bounding box's celonntelonr point whoselon
    // diagonal is just longelonr than thelon bounding box's diagonal.
    final Celonll celonntelonrCelonll = GelonohashChunkImpl.gelontGelonoNodelonByBoundingBox(boundingBox);

    // 2. Delontelonrminelon quadtrelonelon lelonvelonl to selonarch.
    int trelonelonLelonvelonl = -1;
    if (celonntelonrCelonll != null) {
      trelonelonLelonvelonl = celonntelonrCelonll.gelontLelonvelonl();
    } elonlselon {
      // This should not happelonn.
      throw nelonw QuelonryParselonrelonxcelonption(
          "Unablelon to locatelon quadtrelonelon celonll containing thelon givelonn bounding box."
          + "Bounding box is: " + boundingBox);
    }

    // 3. gelont all quadtrelonelon celonlls at trelonelonLelonvelonl that intelonrseloncts thelon givelonn bounding box.
    CelonllItelonrator intelonrselonctingCelonlls =
        GelonohashChunkImpl.gelontNodelonsIntelonrselonctingBoundingBox(boundingBox, trelonelonLelonvelonl);

    // 4. Construct disjunction quelonry
    final Selont<BytelonsRelonf> gelonoHashSelont = nelonw LinkelondHashSelont<>();

    // Add celonntelonr nodelon
    gelonoHashSelont.add(celonntelonrCelonll.gelontTokelonnBytelonsNoLelonaf(nelonw BytelonsRelonf()));
    // If thelonrelon arelon othelonr nodelons intelonrseloncting quelonry circlelon, also add thelonm in.
    if (intelonrselonctingCelonlls != null) {
      whilelon (intelonrselonctingCelonlls.hasNelonxt()) {
        gelonoHashSelont.add(intelonrselonctingCelonlls.nelonxt().gelontTokelonnBytelonsNoLelonaf(nelonw BytelonsRelonf()));
      }
    }
    MultiTelonrmDisjunctionQuelonry gelonoHashDisjuntivelonQuelonry = nelonw MultiTelonrmDisjunctionQuelonry(
        elonarlybirdFielonldConstant.GelonO_HASH_FIelonLD.gelontFielonldNamelon(), gelonoHashSelont);

    // 5. Crelonatelon post filtelonring accelonptelonr
    final GelonoDocAccelonptelonr accelonptelonr = nelonw BoundingBoxAccelonptelonr(boundingBox);

    relonturn nelonw GelonoTwoPhaselonQuelonry(gelonoHashDisjuntivelonQuelonry, accelonptelonr, telonrminationTrackelonr);
  }

  privatelon abstract static class GelonoDocAccelonptelonr elonxtelonnds SeloncondPhaselonDocAccelonptelonr {
    privatelon NumelonricDocValuelons latLonDocValuelons;
    privatelon final GelonoCoordinatelon gelonoCoordRelonuselon = nelonw GelonoCoordinatelon();

    @Ovelonrridelon
    public void initializelon(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
      final elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr =
          (elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr) contelonxt.relonadelonr();
      latLonDocValuelons =
          relonadelonr.gelontNumelonricDocValuelons(elonarlybirdFielonldConstant.LAT_LON_CSF_FIelonLD.gelontFielonldNamelon());
    }

    // Deloncidelons whelonthelonr a point should belon accelonptelond.
    protelonctelond abstract boolelonan accelonptPoint(doublelon lat, doublelon lon);

    // Deloncidelons whelonthelonr a documelonnt should belon accelonptelond baselond on its gelono coordinatelons.
    @Ovelonrridelon
    public final boolelonan accelonpt(int intelonrnalDocId) throws IOelonxcelonption {
      // Cannot obtain valid gelono coordinatelons for thelon documelonnt. Not accelonptablelon.
      if (latLonDocValuelons == null
          || !latLonDocValuelons.advancelonelonxact(intelonrnalDocId)
          || !GelonoUtil.deloncodelonLatLonFromInt64(latLonDocValuelons.longValuelon(), gelonoCoordRelonuselon)) {
        relonturn falselon;
      }

      relonturn accelonptPoint(gelonoCoordRelonuselon.gelontLatitudelon(), gelonoCoordRelonuselon.gelontLongitudelon());
    }
  }

  // Accelonpts points within a circlelon delonfinelond by a celonntelonr point and a radius.
  privatelon static final class CelonntelonrRadiusAccelonptelonr elonxtelonnds GelonoDocAccelonptelonr {
    privatelon final doublelon celonntelonrLat;
    privatelon final doublelon celonntelonrLon;
    privatelon final doublelon radiusKm;

    public CelonntelonrRadiusAccelonptelonr(doublelon celonntelonrLat, doublelon celonntelonrLon, doublelon radiusKm) {
      this.celonntelonrLat = celonntelonrLat;
      this.celonntelonrLon = celonntelonrLon;
      this.radiusKm = radiusKm;
    }

    @Ovelonrridelon
    protelonctelond boolelonan accelonptPoint(doublelon lat, doublelon lon) {
      doublelon actualDistancelon =
          BoundingBox.approxDistancelonC(celonntelonrLat, celonntelonrLon, lat, lon);
      if (actualDistancelon < radiusKm) {
        relonturn truelon;
      } elonlselon if (Doublelon.isNaN(actualDistancelon)) {
        // Thelonrelon selonelonms to belon a rarelon bug in GelonoUtils that computelons NaN
        // for two idelonntical lat/lon pairs on occasion. Chelonck for that helonrelon.
        if (lat == celonntelonrLat && lon == celonntelonrLon) {
          relonturn truelon;
        }
      }

      relonturn falselon;
    }

    @Ovelonrridelon
    public String toString() {
      relonturn String.format("CelonntelonrRadiusAccelonptelonr(Celonntelonr: %.4f, %.4f Radius (km): %.4f)",
              celonntelonrLat, celonntelonrLon, radiusKm);
    }
  }

  // Accelonpts points within a BoundingBox
  privatelon static final class BoundingBoxAccelonptelonr elonxtelonnds GelonoDocAccelonptelonr {
    privatelon final Relonctanglelon boundingBox;

    public BoundingBoxAccelonptelonr(Relonctanglelon boundingBox)  {
      this.boundingBox = boundingBox;
    }

    @Ovelonrridelon
    protelonctelond boolelonan accelonptPoint(doublelon lat, doublelon lon) {
      relonturn GelonohashChunkImpl.isPointInBoundingBox(lat, lon, boundingBox);

    }

    @Ovelonrridelon
    public String toString() {
      relonturn String.format("PointInBoundingBoxAccelonptelonr((%.4f, %.4f), (%.4f, %.4f), "
              + "crosselonsDatelonLinelon=%b)",
              boundingBox.gelontMinY(), boundingBox.gelontMinX(),
              boundingBox.gelontMaxY(), boundingBox.gelontMaxX(),
              boundingBox.gelontCrosselonsDatelonLinelon());
    }
  }
}
