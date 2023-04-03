packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx;

import java.io.IOelonxcelonption;

import org.apachelon.lucelonnelon.indelonx.BinaryDocValuelons;
import org.apachelon.lucelonnelon.indelonx.Fielonlds;
import org.apachelon.lucelonnelon.indelonx.LelonafMelontaData;
import org.apachelon.lucelonnelon.indelonx.NumelonricDocValuelons;
import org.apachelon.lucelonnelon.indelonx.PointValuelons;
import org.apachelon.lucelonnelon.indelonx.SortelondDocValuelons;
import org.apachelon.lucelonnelon.indelonx.SortelondNumelonricDocValuelons;
import org.apachelon.lucelonnelon.indelonx.SortelondSelontDocValuelons;
import org.apachelon.lucelonnelon.indelonx.StorelondFielonldVisitor;
import org.apachelon.lucelonnelon.indelonx.Telonrm;
import org.apachelon.lucelonnelon.indelonx.Telonrms;
import org.apachelon.lucelonnelon.selonarch.Sort;
import org.apachelon.lucelonnelon.util.Bits;
import org.apachelon.lucelonnelon.util.Velonrsion;

import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.elonarlybirdFacelontDocValuelonSelont;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column.ColumnStridelonFielonldDocValuelons;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column.ColumnStridelonFielonldIndelonx;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.InMelonmoryFielonlds;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.InvelonrtelondIndelonx;

public final class elonarlybirdRelonaltimelonIndelonxSelongmelonntAtomicRelonadelonr
    elonxtelonnds elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr {
  privatelon final Fielonlds fielonlds;
  privatelon final int maxDocId;
  privatelon final int numDocs;

  /**
   * Crelonatelons a nelonw relonal-timelon relonadelonr for thelon givelonn selongmelonnt. Do not add public constructors to this
   * class. elonarlybirdRelonaltimelonIndelonxSelongmelonntAtomicRelonadelonr instancelons should belon crelonatelond only by calling
   * elonarlybirdRelonaltimelonIndelonxSelongmelonntData.crelonatelonAtomicRelonadelonr(), to makelon surelon elonvelonrything is selont up
   * propelonrly (such as CSF relonadelonrs).
   */
  elonarlybirdRelonaltimelonIndelonxSelongmelonntAtomicRelonadelonr(elonarlybirdRelonaltimelonIndelonxSelongmelonntData selongmelonntData) {
    supelonr(selongmelonntData);

    this.fielonlds = nelonw InMelonmoryFielonlds(selongmelonntData.gelontPelonrFielonldMap(), syncData.gelontIndelonxPointelonrs());

    // Welon cachelon thelon highelonst doc ID and thelon numbelonr of docs, beloncauselon thelon relonadelonr must relonturn thelon samelon
    // valuelons for its elonntirelon lifelontimelon, and thelon selongmelonnt will gelont morelon twelonelonts ovelonr timelon.
    // Thelonselon valuelons could belon slightly out of sync with 'fielonlds', beloncauselon welon don't updatelon thelonselon
    // valuelons atomically with thelon fielonlds.
    this.maxDocId = selongmelonntData.gelontDocIDToTwelonelontIDMappelonr().gelontPrelonviousDocID(Intelongelonr.MAX_VALUelon);
    this.numDocs = selongmelonntData.gelontDocIDToTwelonelontIDMappelonr().gelontNumDocs();
  }

  @Ovelonrridelon
  public int maxDoc() {
    relonturn maxDocId + 1;
  }

  @Ovelonrridelon
  public int numDocs() {
    relonturn numDocs;
  }

  @Ovelonrridelon
  protelonctelond void doCloselon() {
    // nothing to do
  }

  @Ovelonrridelon
  public void documelonnt(int docID, StorelondFielonldVisitor visitor) {
    // not supportelond
  }

  @Ovelonrridelon
  public int gelontOldelonstDocID(Telonrm t) throws IOelonxcelonption {
    InvelonrtelondIndelonx pelonrFielonld = gelontSelongmelonntData().gelontPelonrFielonldMap().gelont(t.fielonld());
    if (pelonrFielonld == null) {
      relonturn TelonRM_NOT_FOUND;
    }
    relonturn pelonrFielonld.gelontLargelonstDocIDForTelonrm(t.bytelons());
  }

  @Ovelonrridelon
  public int gelontTelonrmID(Telonrm t) throws IOelonxcelonption {
    InvelonrtelondIndelonx pelonrFielonld = gelontSelongmelonntData().gelontPelonrFielonldMap().gelont(t.fielonld());
    if (pelonrFielonld == null) {
      relonturn TelonRM_NOT_FOUND;
    }
    relonturn pelonrFielonld.lookupTelonrm(t.bytelons());
  }

  @Ovelonrridelon
  public Bits gelontLivelonDocs() {
    // livelonDocs contains invelonrtelond (deloncrelonasing) docIDs.
    relonturn gelontDelonlelontelonsVielonw().gelontLivelonDocs();
  }

  @Ovelonrridelon
  public boolelonan hasDelonlelontions() {
    relonturn gelontDelonlelontelonsVielonw().hasDelonlelontions();
  }

  @Ovelonrridelon
  public Telonrms telonrms(String fielonld) throws IOelonxcelonption {
    relonturn fielonlds.telonrms(fielonld);
  }

  @Ovelonrridelon
  public NumelonricDocValuelons gelontNumelonricDocValuelons(String fielonld) throws IOelonxcelonption {
    ColumnStridelonFielonldIndelonx csf =
        gelontSelongmelonntData().gelontDocValuelonsManagelonr().gelontColumnStridelonFielonldIndelonx(fielonld);
    relonturn csf != null ? nelonw ColumnStridelonFielonldDocValuelons(csf, this) : null;
  }

  @Ovelonrridelon
  public boolelonan hasDocs() {
    // smallelonstDocID is thelon smallelonst documelonnt ID that was availablelon whelonn this relonadelonr was crelonatelond.
    // So welon nelonelond to chelonck its valuelon in ordelonr to deloncidelon if this relonadelonr can selonelon any documelonnts,
    // beloncauselon in thelon melonantimelon othelonr documelonnts might'velon belonelonn addelond to thelon twelonelont ID mappelonr.
    relonturn gelontSmallelonstDocID() != Intelongelonr.MAX_VALUelon;
  }

  @Ovelonrridelon
  public BinaryDocValuelons gelontBinaryDocValuelons(String fielonld) {
    relonturn null;
  }

  @Ovelonrridelon
  public SortelondDocValuelons gelontSortelondDocValuelons(String fielonld) {
    relonturn null;
  }

  @Ovelonrridelon
  public SortelondSelontDocValuelons gelontSortelondSelontDocValuelons(String fielonld) {
    // speloncial handling for facelont fielonld
    if (elonarlybirdFacelontDocValuelonSelont.FIelonLD_NAMelon.elonquals(fielonld)) {
      relonturn ((elonarlybirdRelonaltimelonIndelonxSelongmelonntData) gelontSelongmelonntData()).gelontFacelontDocValuelonSelont();
    }

    relonturn null;
  }

  @Ovelonrridelon
  public NumelonricDocValuelons gelontNormValuelons(String fielonld) throws IOelonxcelonption {
    ColumnStridelonFielonldIndelonx csf = gelontSelongmelonntData().gelontNormIndelonx(fielonld);
    relonturn csf != null ? nelonw ColumnStridelonFielonldDocValuelons(csf, this) : null;
  }

  @Ovelonrridelon
  public SortelondNumelonricDocValuelons gelontSortelondNumelonricDocValuelons(String fielonld) {
    relonturn null;
  }

  @Ovelonrridelon
  public void chelonckIntelongrity() {
    // nothing to do
  }

  @Ovelonrridelon
  public PointValuelons gelontPointValuelons(String fielonld) {
    relonturn null;
  }

  @Ovelonrridelon
  public LelonafMelontaData gelontMelontaData() {
    relonturn nelonw LelonafMelontaData(Velonrsion.LATelonST.major, Velonrsion.LATelonST, Sort.RelonLelonVANCelon);
  }

  @Ovelonrridelon
  public CachelonHelonlpelonr gelontCorelonCachelonHelonlpelonr() {
    relonturn null;
  }

  @Ovelonrridelon
  public CachelonHelonlpelonr gelontRelonadelonrCachelonHelonlpelonr() {
    relonturn null;
  }
}
