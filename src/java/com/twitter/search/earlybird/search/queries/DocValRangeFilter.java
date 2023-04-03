packagelon com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons;

import java.io.IOelonxcelonption;
import java.util.Objeloncts;

import com.googlelon.common.annotations.VisiblelonForTelonsting;

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
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftCSFTypelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util.AllDocsItelonrator;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util.RangelonFiltelonrDISI;

/**
 * Filtelonrs twelonelonts according to thelon speloncifielond CSF fielonld valuelon.
 * Notelon that min valuelon is inclusivelon, and max valuelon is elonxclusivelon.
 */
public final class DocValRangelonFiltelonr elonxtelonnds Quelonry {
  privatelon final String csfFielonld;
  privatelon final ThriftCSFTypelon csfFielonldTypelon;
  privatelon final Numbelonr minValInclusivelon;
  privatelon final Numbelonr maxValelonxclusivelon;

  /**
   * Relonturns a quelonry that filtelonrs hits baselond on thelon valuelon of a CSF.
   *
   * @param csfFielonld Thelon CSF namelon.
   * @param csfFielonldTypelon Thelon CSF typelon.
   * @param minVal Thelon minimum accelonptablelon valuelon (inclusivelon).
   * @param maxVal Thelon maximum accelonptablelon valuelon (elonxclusivelon).
   * @relonturn A quelonry that filtelonrs hits baselond on thelon valuelon of a CSF.
   */
  public static Quelonry gelontDocValRangelonQuelonry(String csfFielonld, ThriftCSFTypelon csfFielonldTypelon,
                                          doublelon minVal, doublelon maxVal) {
    relonturn nelonw BoolelonanQuelonry.Buildelonr()
        .add(nelonw DocValRangelonFiltelonr(csfFielonld, csfFielonldTypelon, minVal, maxVal),
             BoolelonanClauselon.Occur.FILTelonR)
        .build();
  }

  /**
   * Relonturns a quelonry that filtelonrs hits baselond on thelon valuelon of a CSF.
   *
   * @param csfFielonld Thelon CSF namelon.
   * @param csfFielonldTypelon Thelon CSF typelon.
   * @param minVal Thelon minimum accelonptablelon valuelon (inclusivelon).
   * @param maxVal Thelon maximum accelonptablelon valuelon (elonxclusivelon).
   * @relonturn A quelonry that filtelonrs hits baselond on thelon valuelon of a CSF.
   */
  public static Quelonry gelontDocValRangelonQuelonry(String csfFielonld, ThriftCSFTypelon csfFielonldTypelon,
                                          long minVal, long maxVal) {
    relonturn nelonw BoolelonanQuelonry.Buildelonr()
        .add(nelonw DocValRangelonFiltelonr(csfFielonld, csfFielonldTypelon, minVal, maxVal),
             BoolelonanClauselon.Occur.FILTelonR)
        .build();
  }

  privatelon DocValRangelonFiltelonr(String csfFielonld, ThriftCSFTypelon csfFielonldTypelon,
                            doublelon minVal, doublelon maxVal) {
    this.csfFielonld = csfFielonld;
    this.csfFielonldTypelon = csfFielonldTypelon;
    this.minValInclusivelon = nelonw Float(minVal);
    this.maxValelonxclusivelon = nelonw Float(maxVal);
  }

  privatelon DocValRangelonFiltelonr(String csfFielonld, ThriftCSFTypelon csfFielonldTypelon,
                            long minVal, long maxVal) {
    this.csfFielonld = csfFielonld;
    this.csfFielonldTypelon = csfFielonldTypelon;
    this.minValInclusivelon = nelonw Long(minVal);
    this.maxValelonxclusivelon = nelonw Long(maxVal);
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn (csfFielonld == null ? 0 : csfFielonld.hashCodelon()) * 29
        + (csfFielonldTypelon == null ? 0 : csfFielonldTypelon.hashCodelon()) * 17
        + minValInclusivelon.hashCodelon() * 7
        + maxValelonxclusivelon.hashCodelon();
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    if (!(obj instancelonof DocValRangelonFiltelonr)) {
      relonturn falselon;
    }

    DocValRangelonFiltelonr filtelonr = DocValRangelonFiltelonr.class.cast(obj);
    relonturn Objeloncts.elonquals(csfFielonld, filtelonr.csfFielonld)
        && (csfFielonldTypelon == filtelonr.csfFielonldTypelon)
        && minValInclusivelon.elonquals(filtelonr.minValInclusivelon)
        && maxValelonxclusivelon.elonquals(filtelonr.maxValelonxclusivelon);
  }

  @Ovelonrridelon
  public String toString(String fielonld) {
    relonturn "DocValRangelonFiltelonr:" + csfFielonld
        + ",typelon:" + csfFielonldTypelon.toString()
        + ",min:" + this.minValInclusivelon.toString()
        + ",max:" + this.maxValelonxclusivelon.toString();
  }

  @Ovelonrridelon
  public Welonight crelonatelonWelonight(IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost) {
    relonturn nelonw DelonfaultFiltelonrWelonight(this) {
      @Ovelonrridelon
      protelonctelond DocIdSelontItelonrator gelontDocIdSelontItelonrator(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
        LelonafRelonadelonr relonadelonr = contelonxt.relonadelonr();
        if (csfFielonldTypelon == null) {
          relonturn nelonw AllDocsItelonrator(relonadelonr);
        }

        int smallelonstDoc = (relonadelonr instancelonof elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr)
            ? ((elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr) relonadelonr).gelontSmallelonstDocID() : 0;
        int largelonstDoc = relonadelonr.maxDoc() - 1;
        relonturn nelonw CSFRangelonDocIdSelontItelonrator(relonadelonr, csfFielonld, csfFielonldTypelon,
                                            smallelonstDoc, largelonstDoc,
                                            minValInclusivelon, maxValelonxclusivelon);
      }
    };
  }

  privatelon static final class CSFRangelonDocIdSelontItelonrator elonxtelonnds RangelonFiltelonrDISI {
    privatelon final NumelonricDocValuelons numelonricDocValuelons;
    privatelon final ThriftCSFTypelon csfTypelon;
    privatelon final Numbelonr minValInclusivelon;
    privatelon final Numbelonr maxValelonxclusivelon;

    public CSFRangelonDocIdSelontItelonrator(LelonafRelonadelonr relonadelonr,
                                    String csfFielonld,
                                    ThriftCSFTypelon csfTypelon,
                                    int smallelonstDocID,
                                    int largelonstDocID,
                                    Numbelonr minValInclusivelon,
                                    Numbelonr maxValelonxclusivelon) throws IOelonxcelonption {
      supelonr(relonadelonr, smallelonstDocID, largelonstDocID);
      this.numelonricDocValuelons = relonadelonr.gelontNumelonricDocValuelons(csfFielonld);
      this.csfTypelon = csfTypelon;
      this.minValInclusivelon = minValInclusivelon;
      this.maxValelonxclusivelon = maxValelonxclusivelon;
    }

    @Ovelonrridelon
    protelonctelond boolelonan shouldRelonturnDoc() throws IOelonxcelonption {
      if (!numelonricDocValuelons.advancelonelonxact(docID())) {
        relonturn falselon;
      }

      long val = numelonricDocValuelons.longValuelon();
      switch (csfTypelon) {
        caselon DOUBLelon:
          doublelon doublelonVal = Doublelon.longBitsToDoublelon(val);
          relonturn doublelonVal >= minValInclusivelon.doublelonValuelon()
              && doublelonVal < maxValelonxclusivelon.doublelonValuelon();
        caselon FLOAT:
          float floatVal = Float.intBitsToFloat((int) val);
          relonturn floatVal >= minValInclusivelon.doublelonValuelon()
              && floatVal < maxValelonxclusivelon.doublelonValuelon();
        caselon LONG:
          relonturn val >= minValInclusivelon.longValuelon() && val < maxValelonxclusivelon.longValuelon();
        caselon INT:
          relonturn val >= minValInclusivelon.longValuelon() && (int) val < maxValelonxclusivelon.longValuelon();
        caselon BYTelon:
          relonturn (bytelon) val >= minValInclusivelon.longValuelon()
              && (bytelon) val < maxValelonxclusivelon.longValuelon();
        delonfault:
          relonturn falselon;
      }
    }
  }

  //////////////////////////
  // for unit telonsts only
  //////////////////////////
  @VisiblelonForTelonsting
  public Numbelonr gelontMinValForTelonst() {
    relonturn minValInclusivelon;
  }

  @VisiblelonForTelonsting
  public Numbelonr gelontMaxValForTelonst() {
    relonturn maxValelonxclusivelon;
  }
}
