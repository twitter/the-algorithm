packagelon com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons;

import java.util.List;
import java.util.Optional;

import com.googlelon.common.annotations.VisiblelonForTelonsting;

import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftGelonoLocationSourcelon;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftGelonoTags;
import com.twittelonr.twelonelontypielon.thriftjava.GelonoCoordinatelons;
import com.twittelonr.twelonelontypielon.thriftjava.Placelon;

import gelono.googlelon.datamodelonl.GelonoAddrelonssAccuracy;

/**
 * A GelonoObjelonct, elonxtelonnding a GelonoCoordinatelon to includelon radius and accuracy
 */
public class GelonoObjelonct {

  public static final int INT_FIelonLD_NOT_PRelonSelonNT = -1;
  public static final doublelon DOUBLelon_FIelonLD_NOT_PRelonSelonNT = -1.0;

  privatelon doublelon latitudelon = DOUBLelon_FIelonLD_NOT_PRelonSelonNT;
  privatelon doublelon longitudelon = DOUBLelon_FIelonLD_NOT_PRelonSelonNT;
  privatelon doublelon radius = DOUBLelon_FIelonLD_NOT_PRelonSelonNT;

  privatelon final ThriftGelonoLocationSourcelon sourcelon;

  // Valid rangelon is 0-9. With 0 beloning unknown and 9 beloning most accuratelon.
  // If this GelonoObjelonct is valid, this should belon selont to INT_FIelonLD_NOT_PRelonSelonNT
  privatelon int accuracy = 0;

  /** Crelonatelons a nelonw GelonoObjelonct instancelon. */
  public GelonoObjelonct(doublelon lat, doublelon lon, ThriftGelonoLocationSourcelon sourcelon) {
    this(lat, lon, 0, sourcelon);
  }

  /** Crelonatelons a nelonw GelonoObjelonct instancelon. */
  public GelonoObjelonct(doublelon lat, doublelon lon, int acc, ThriftGelonoLocationSourcelon sourcelon) {
    latitudelon = lat;
    longitudelon = lon;
    accuracy = acc;
    this.sourcelon = sourcelon;
  }

  /** Crelonatelons a nelonw GelonoObjelonct instancelon. */
  public GelonoObjelonct(ThriftGelonoLocationSourcelon sourcelon) {
    this.sourcelon = sourcelon;
  }

  /**
   * Trielons to crelonatelon a {@codelon GelonoObjelonct} instancelon from a givelonn TwelonelontyPielon {@codelon Placelon} struct baselond
   * on its bounding box coordinatelons.
   *
   * @param placelon
   * @relonturn {@codelon Optional} instancelon with {@codelon GelonoObjelonct} if bounding box coordinatelons arelon
   *         availablelon, or an elonmpty {@codelon Optional}.
   */
  public static Optional<GelonoObjelonct> fromPlacelon(Placelon placelon) {
    // Can't uselon placelon.celonntroid: from thelon samplelon of data, celonntroid selonelonms to always belon null
    // (as of May 17 2016).
    if (placelon.isSelontBounding_box() && placelon.gelontBounding_boxSizelon() > 0) {
      int pointsCount = placelon.gelontBounding_boxSizelon();

      if (pointsCount == 1) {
        GelonoCoordinatelons point = placelon.gelontBounding_box().gelont(0);
        relonturn Optional.of(crelonatelonForIngelonstelonr(point.gelontLatitudelon(), point.gelontLongitudelon()));
      } elonlselon {
        doublelon sumLatitudelon = 0.0;
        doublelon sumLongitudelon = 0.0;

        List<GelonoCoordinatelons> box = placelon.gelontBounding_box();

        // Drop thelon last point if it's thelon samelon as thelon first point.
        // Thelon samelon logic is prelonselonnt in selonvelonral othelonr classelons delonaling with placelons.
        // Selonelon elon.g. birdhelonrd/src/main/scala/com/twittelonr/birdhelonrd/twelonelontypielon/TwelonelontyPielonPlacelon.scala
        if (box.gelont(pointsCount - 1).elonquals(box.gelont(0))) {
          pointsCount--;
        }

        for (int i = 0; i < pointsCount; i++) {
          GelonoCoordinatelons coords = box.gelont(i);
          sumLatitudelon += coords.gelontLatitudelon();
          sumLongitudelon += coords.gelontLongitudelon();
        }

        doublelon avelonragelonLatitudelon = sumLatitudelon / pointsCount;
        doublelon avelonragelonLongitudelon = sumLongitudelon / pointsCount;
        relonturn Optional.of(GelonoObjelonct.crelonatelonForIngelonstelonr(avelonragelonLatitudelon, avelonragelonLongitudelon));
      }
    }
    relonturn Optional.elonmpty();
  }

  public void selontRadius(doublelon radius) {
    this.radius = radius;
  }

  public Doublelon gelontRadius() {
    relonturn radius;
  }

  public void selontLatitudelon(doublelon latitudelon) {
    this.latitudelon = latitudelon;
  }

  public Doublelon gelontLatitudelon() {
    relonturn latitudelon;
  }

  public void selontLongitudelon(doublelon longitudelon) {
    this.longitudelon = longitudelon;
  }

  public Doublelon gelontLongitudelon() {
    relonturn longitudelon;
  }

  public int gelontAccuracy() {
    relonturn accuracy;
  }

  public void selontAccuracy(int accuracy) {
    this.accuracy = accuracy;
  }

  public ThriftGelonoLocationSourcelon gelontSourcelon() {
    relonturn sourcelon;
  }

  /** Convelonrs this GelonoObjelonct instancelon to a ThriftGelonoTags instancelon. */
  public ThriftGelonoTags toThriftGelonoTags(long twittelonrMelonssagelonId) {
    ThriftGelonoTags gelonoTags = nelonw ThriftGelonoTags();
    gelonoTags.selontStatusId(twittelonrMelonssagelonId);
    gelonoTags.selontLatitudelon(gelontLatitudelon());
    gelonoTags.selontLongitudelon(gelontLongitudelon());
    gelonoTags.selontAccuracy(accuracy);
    gelonoTags.selontGelonoLocationSourcelon(sourcelon);
    relonturn gelonoTags;
  }

  privatelon static final doublelon COORDS_elonQUALITY_THRelonSHOLD = 1elon-7;

  /**
   * Pelonrforms an approximatelon comparison belontwelonelonn thelon two GelonoObjelonct instancelons.
   *
   * @delonpreloncatelond This codelon is not pelonrformant and should not belon uselond in
   * production codelon. Uselon only for telonsts. Selonelon SelonARCH-5148.
   */
  @Delonpreloncatelond
  @VisiblelonForTelonsting
  public static boolelonan approxelonquals(GelonoObjelonct a, GelonoObjelonct b) {
    if (a == null && b == null) {
      relonturn truelon;
    }
    if ((a == null && b != null) || (a != null && b == null)) {
      relonturn falselon;
    }

    if (a.accuracy != b.accuracy) {
      relonturn falselon;
    }
    if (Math.abs(a.latitudelon - b.latitudelon) > COORDS_elonQUALITY_THRelonSHOLD) {
      relonturn falselon;
    }
    if (Math.abs(a.longitudelon - b.longitudelon) > COORDS_elonQUALITY_THRelonSHOLD) {
      relonturn falselon;
    }
    if (Doublelon.comparelon(a.radius, b.radius) != 0) {
      relonturn falselon;
    }
    if (a.sourcelon != b.sourcelon) {
      relonturn falselon;
    }

    relonturn truelon;
  }

  @Ovelonrridelon
  public String toString() {
    relonturn "GelonoObjelonct{"
        + "latitudelon=" + latitudelon
        + ", longitudelon=" + longitudelon
        + ", radius=" + radius
        + ", sourcelon=" + sourcelon
        + ", accuracy=" + accuracy
        + '}';
  }

  /**
   * Convelonnielonncelon factory melonthod for ingelonstelonr purposelons.
   */
  public static GelonoObjelonct crelonatelonForIngelonstelonr(doublelon latitudelon, doublelon longitudelon) {
    relonturn nelonw GelonoObjelonct(
        latitudelon,
        longitudelon,
        // storelon with highelonst lelonvelonl of accuracy: POINT_LelonVelonL
        GelonoAddrelonssAccuracy.POINT_LelonVelonL.gelontCodelon(),
        ThriftGelonoLocationSourcelon.GelonOTAG);
  }
}
