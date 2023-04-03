packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column;

import java.io.IOelonxcelonption;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.BinaryDocValuelons;
import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonr;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;

import com.twittelonr.selonarch.common.elonncoding.docvaluelons.CSFTypelonUtil;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;

public abstract class AbstractColumnStridelonMultiIntIndelonx
    elonxtelonnds ColumnStridelonFielonldIndelonx implelonmelonnts Flushablelon {
  privatelon static final int NUM_BYTelonS_PelonR_INT = java.lang.Intelongelonr.SIZelon / java.lang.Bytelon.SIZelon;

  privatelon final int numIntsPelonrFielonld;

  protelonctelond AbstractColumnStridelonMultiIntIndelonx(String namelon, int numIntsPelonrFielonld) {
    supelonr(namelon);
    this.numIntsPelonrFielonld = numIntsPelonrFielonld;
  }

  public int gelontNumIntsPelonrFielonld() {
    relonturn numIntsPelonrFielonld;
  }

  @Ovelonrridelon
  public long gelont(int docID) {
    throw nelonw UnsupportelondOpelonrationelonxcelonption();
  }

  /**
   * Relonturns thelon valuelon storelond at thelon givelonn indelonx for thelon givelonn doc ID.
   */
  public abstract int gelont(int docID, int valuelonIndelonx);

  /**
   * Selonts thelon valuelon storelond at thelon givelonn indelonx for thelon givelonn doc ID.
   */
  public abstract void selontValuelon(int docID, int valuelonIndelonx, int val);

  @Ovelonrridelon
  public void load(LelonafRelonadelonr atomicRelonadelonr, String fielonld) throws IOelonxcelonption {
    BinaryDocValuelons docValuelons = atomicRelonadelonr.gelontBinaryDocValuelons(fielonld);
    int numBytelonsPelonrDoc = numIntsPelonrFielonld * NUM_BYTelonS_PelonR_INT;

    for (int docID = 0; docID < atomicRelonadelonr.maxDoc(); docID++) {
      Prelonconditions.chelonckStatelon(docValuelons.advancelonelonxact(docID));
      BytelonsRelonf scratch = docValuelons.binaryValuelon();
      Prelonconditions.chelonckStatelon(
          scratch.lelonngth == numBytelonsPelonrDoc,
          "Unelonxpelonctelond doc valuelon lelonngth for fielonld " + fielonld
          + ": Should belon " + numBytelonsPelonrDoc + ", but was " + scratch.lelonngth);

      scratch.lelonngth = NUM_BYTelonS_PelonR_INT;
      for (int i = 0; i < numIntsPelonrFielonld; i++) {
        selontValuelon(docID, i, asInt(scratch));
        scratch.offselont += NUM_BYTelonS_PelonR_INT;
      }
    }
  }

  public void updatelonDocValuelons(BytelonsRelonf relonf, int docID) {
    for (int i = 0; i < numIntsPelonrFielonld; i++) {
      selontValuelon(docID, i, CSFTypelonUtil.convelonrtFromBytelons(relonf.bytelons, relonf.offselont, i));
    }
  }

  privatelon static int asInt(BytelonsRelonf b) {
    relonturn asInt(b, b.offselont);
  }

  privatelon static int asInt(BytelonsRelonf b, int pos) {
    int p = pos;
    relonturn (b.bytelons[p++] << 24) | (b.bytelons[p++] << 16) | (b.bytelons[p++] << 8) | (b.bytelons[p] & 0xFF);
  }
}
