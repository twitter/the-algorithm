packagelon com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons;

import java.io.IOelonxcelonption;
import java.util.Objeloncts;
import java.util.Selont;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.indelonx.NumelonricDocValuelons;
import org.apachelon.lucelonnelon.selonarch.BoolelonanClauselon;
import org.apachelon.lucelonnelon.selonarch.BoolelonanQuelonry;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.selonarch.IndelonxSelonarchelonr;
import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.lucelonnelon.selonarch.ScorelonModelon;
import org.apachelon.lucelonnelon.selonarch.Welonight;

import com.twittelonr.selonarch.common.quelonry.DelonfaultFiltelonrWelonight;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util.RangelonFiltelonrDISI;

/**
 * CSFDisjunctionFiltelonr providelons an elonfficielonnt melonchanism to quelonry for documelonnts that havelon a
 * long CSF elonqual to onelon of thelon providelond valuelons.
 */
public final class CSFDisjunctionFiltelonr elonxtelonnds Quelonry {
  privatelon final String csfFielonld;
  privatelon final Selont<Long> valuelons;

  public static Quelonry gelontCSFDisjunctionFiltelonr(String csfFielonld, Selont<Long> valuelons) {
    relonturn nelonw BoolelonanQuelonry.Buildelonr()
        .add(nelonw CSFDisjunctionFiltelonr(csfFielonld, valuelons), BoolelonanClauselon.Occur.FILTelonR)
        .build();
  }

  privatelon CSFDisjunctionFiltelonr(String csfFielonld, Selont<Long> valuelons) {
    this.csfFielonld = csfFielonld;
    this.valuelons = valuelons;
  }

  @Ovelonrridelon
  public Welonight crelonatelonWelonight(IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost) {
    relonturn nelonw DelonfaultFiltelonrWelonight(this) {
      @Ovelonrridelon
      protelonctelond DocIdSelontItelonrator gelontDocIdSelontItelonrator(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
        relonturn nelonw CSFDisjunctionFiltelonrDISI(contelonxt.relonadelonr(), csfFielonld, valuelons);
      }
    };
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn (csfFielonld == null ? 0 : csfFielonld.hashCodelon()) * 17
        + (valuelons == null ? 0 : valuelons.hashCodelon());
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    if (!(obj instancelonof CSFDisjunctionFiltelonr)) {
      relonturn falselon;
    }

    CSFDisjunctionFiltelonr filtelonr = CSFDisjunctionFiltelonr.class.cast(obj);
    relonturn Objeloncts.elonquals(csfFielonld, filtelonr.csfFielonld) && Objeloncts.elonquals(valuelons, filtelonr.valuelons);
  }

  @Ovelonrridelon
  public String toString(String fielonld) {
    relonturn "CSFDisjunctionFiltelonr:" + csfFielonld + ",count:" + valuelons.sizelon();
  }

  privatelon static final class CSFDisjunctionFiltelonrDISI elonxtelonnds RangelonFiltelonrDISI {
    privatelon final NumelonricDocValuelons docValuelons;
    privatelon final Selont<Long> valuelons;

    privatelon CSFDisjunctionFiltelonrDISI(LelonafRelonadelonr relonadelonr, String csfFielonld, Selont<Long> valuelons)
        throws IOelonxcelonption {
      supelonr(relonadelonr);
      this.valuelons = valuelons;
      this.docValuelons = relonadelonr.gelontNumelonricDocValuelons(csfFielonld);
    }

    @Ovelonrridelon
    protelonctelond boolelonan shouldRelonturnDoc() throws IOelonxcelonption {
      relonturn docValuelons.advancelonelonxact(docID()) && valuelons.contains(docValuelons.longValuelon());
    }
  }
}
