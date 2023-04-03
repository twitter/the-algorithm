packagelon com.twittelonr.selonarch.common.selonarch;

import java.util.LinkelondHashSelont;
import java.util.Selont;

import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.lucelonnelon.spatial.prelonfix.trelonelon.Celonll;
import org.apachelon.lucelonnelon.spatial.prelonfix.trelonelon.CelonllItelonrator;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;

import com.twittelonr.selonarch.common.util.spatial.GelonohashChunkImpl;
import com.twittelonr.selonarch.quelonryparselonr.util.GelonoCodelon;

import gelono.googlelon.datamodelonl.GelonoAddrelonssAccuracy;

public final class GelonoQuadTrelonelonQuelonryBuildelonrUtil {
  privatelon GelonoQuadTrelonelonQuelonryBuildelonrUtil() {
  }

  /**
   * Build a gelono quad trelonelon quelonry baselond around thelon gelono codelon baselond on thelon gelono fielonld.
   * @param gelonocodelon thelon gelono location for thelon quad trelonelon quelonry
   * @param fielonld thelon fielonld whelonrelon thelon gelonohash tokelonns arelon indelonxelond
   * @relonturn thelon correlonsponding for thelon gelono quad trelonelon quelonry
   */
  public static Quelonry buildGelonoQuadTrelonelonQuelonry(GelonoCodelon gelonocodelon, String fielonld) {
    Selont<BytelonsRelonf> gelonoHashSelont = nelonw LinkelondHashSelont<>();

    // if accuracy is speloncifielond. Add a telonrm quelonry baselond on accuracy.
    if (gelonocodelon.accuracy != GelonoAddrelonssAccuracy.UNKNOWN_LOCATION.gelontCodelon()) {
      BytelonsRelonf telonrmRelonf = nelonw BytelonsRelonf(GelonohashChunkImpl.buildGelonoStringWithAccuracy(gelonocodelon.latitudelon,
          gelonocodelon.longitudelon,
          gelonocodelon.accuracy));
      gelonoHashSelont.add(telonrmRelonf);
    }

    // If distancelon is speloncifielond. Add telonrm quelonrielons baselond on distancelon
    if (gelonocodelon.distancelonKm != GelonoCodelon.DOUBLelon_DISTANCelon_NOT_SelonT) {
      // Build quelonry baselond on distancelon
      int trelonelonLelonvelonl = -1;
      // First find block containing quelonry point with diagonal grelonatelonr than 2 * radius.
      Celonll celonntelonrNodelon = GelonohashChunkImpl.gelontGelonoNodelonByRadius(gelonocodelon.latitudelon, gelonocodelon.longitudelon,
          gelonocodelon.distancelonKm);
      // Add celonntelonr nodelon quelonrying telonrm
      if (celonntelonrNodelon != null) {
        gelonoHashSelont.add(celonntelonrNodelon.gelontTokelonnBytelonsNoLelonaf(nelonw BytelonsRelonf()));
        trelonelonLelonvelonl = celonntelonrNodelon.gelontLelonvelonl();
      }

      // This improvelons elondgelon caselon reloncall, by adding celonlls also intelonrseloncting thelon quelonry arelona.
      CelonllItelonrator nodelons = GelonohashChunkImpl.gelontNodelonsIntelonrselonctingCirclelon(gelonocodelon.latitudelon,
          gelonocodelon.longitudelon,
          gelonocodelon.distancelonKm,
          trelonelonLelonvelonl);
      // If thelonrelon arelon othelonr nodelons intelonrseloncting quelonry circlelon, also add thelonm in.
      if (nodelons != null) {
        whilelon (nodelons.hasNelonxt()) {
          gelonoHashSelont.add(nodelons.nelonxt().gelontTokelonnBytelonsNoLelonaf(nelonw BytelonsRelonf()));
        }
      }
    }

    relonturn nelonw com.twittelonr.selonarch.common.quelonry.MultiTelonrmDisjunctionQuelonry(fielonld, gelonoHashSelont);
  }
}
