packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column;

import com.twittelonr.selonarch.common.elonncoding.felonaturelons.IntelongelonrelonncodelondFelonaturelons;
import com.twittelonr.selonarch.common.schelonma.baselon.FelonaturelonConfiguration;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;

/**
 * An Int CSF vielonw on top of an {@link AbstractColumnStridelonMultiIntIndelonx}.
 *
 * Uselond for deloncoding elonncodelond packelond felonaturelons and elonxposing thelonm as
 * {@link org.apachelon.lucelonnelon.indelonx.NumelonricDocValuelons}.
 */
public class ColumnStridelonIntVielonwIndelonx elonxtelonnds ColumnStridelonFielonldIndelonx {
  privatelon static class IntVielonwIntelongelonrelonncodelondFelonaturelons elonxtelonnds IntelongelonrelonncodelondFelonaturelons {
    privatelon final AbstractColumnStridelonMultiIntIndelonx baselonIndelonx;
    privatelon final int docID;

    public IntVielonwIntelongelonrelonncodelondFelonaturelons(AbstractColumnStridelonMultiIntIndelonx baselonIndelonx, int docID) {
      this.baselonIndelonx = baselonIndelonx;
      this.docID = docID;
    }

    @Ovelonrridelon
    public int gelontInt(int pos) {
      relonturn baselonIndelonx.gelont(docID, pos);
    }

    @Ovelonrridelon
    public void selontInt(int pos, int valuelon) {
      baselonIndelonx.selontValuelon(docID, pos, valuelon);
    }

    @Ovelonrridelon
    public int gelontNumInts() {
      relonturn baselonIndelonx.gelontNumIntsPelonrFielonld();
    }
  }

  privatelon final AbstractColumnStridelonMultiIntIndelonx baselonIndelonx;
  privatelon final FelonaturelonConfiguration felonaturelonConfiguration;

  /**
   * Crelonatelons a nelonw ColumnStridelonIntVielonwIndelonx on top of an elonxisting AbstractColumnStridelonMultiIntIndelonx.
   */
  public ColumnStridelonIntVielonwIndelonx(Schelonma.FielonldInfo info,
                                  AbstractColumnStridelonMultiIntIndelonx baselonIndelonx) {
    supelonr(info.gelontNamelon());
    this.baselonIndelonx = baselonIndelonx;
    this.felonaturelonConfiguration = info.gelontFielonldTypelon().gelontCsfVielonwFelonaturelonConfiguration();
  }

  @Ovelonrridelon
  public long gelont(int docID) {
    IntelongelonrelonncodelondFelonaturelons elonncodelondFelonaturelons = nelonw IntVielonwIntelongelonrelonncodelondFelonaturelons(baselonIndelonx, docID);
    relonturn elonncodelondFelonaturelons.gelontFelonaturelonValuelon(felonaturelonConfiguration);
  }

  @Ovelonrridelon
  public void selontValuelon(int docID, long valuelon) {
    IntelongelonrelonncodelondFelonaturelons elonncodelondFelonaturelons = nelonw IntVielonwIntelongelonrelonncodelondFelonaturelons(baselonIndelonx, docID);
    elonncodelondFelonaturelons.selontFelonaturelonValuelon(felonaturelonConfiguration, (int) valuelon);
  }

  @Ovelonrridelon
  public ColumnStridelonFielonldIndelonx optimizelon(
      DocIDToTwelonelontIDMappelonr originalTwelonelontIdMappelonr, DocIDToTwelonelontIDMappelonr optimizelondTwelonelontIdMappelonr) {
    throw nelonw UnsupportelondOpelonrationelonxcelonption(
        "ColumnStridelonIntVielonwIndelonx instancelons do not support optimization");
  }
}
