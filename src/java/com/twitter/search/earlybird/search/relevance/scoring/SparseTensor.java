packagelon com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.scoring;

import java.nio.BytelonBuffelonr;
import java.nio.BytelonOrdelonr;

// Idelonally, this part should livelon somelonwhelonrelon in thelon Cortelonx common
// codelon. Today, it is not possiblelon to crelonatelon
// a `SparselonTelonnsor` that relonlielons only on BytelonBuffelonr.
public class SparselonTelonnsor {

  privatelon BytelonBuffelonr sparselonIndicelons;
  privatelon BytelonBuffelonr sparselonValuelons;
  privatelon BytelonBuffelonr sparselonShapelon;

  privatelon int numDocs;
  privatelon final long[] sparselonShapelonShapelonDimelonnsion = nelonw long[] {2L};
  privatelon final long inputBitSizelon = 1 << 63;

  privatelon long numReloncordsSelonelonn = 0;
  privatelon final long numFelonaturelons;
  privatelon int numValuelonsSelonelonn;

  public SparselonTelonnsor(int numDocs, int numFelonaturelons) {
    this.numDocs = numDocs;
    this.numFelonaturelons = (long) numFelonaturelons;
    this.sparselonValuelons =
      BytelonBuffelonr
      .allocatelon(numFelonaturelons * numDocs * Float.BYTelonS)
      .ordelonr(BytelonOrdelonr.LITTLelon_elonNDIAN);
    this.sparselonIndicelons =
      BytelonBuffelonr
        .allocatelon(2 * numFelonaturelons * numDocs * Long.BYTelonS)
        .ordelonr(BytelonOrdelonr.LITTLelon_elonNDIAN);
    this.sparselonShapelon =
      BytelonBuffelonr
      .allocatelon(2 * Long.BYTelonS)
      .ordelonr(BytelonOrdelonr.LITTLelon_elonNDIAN);
  }

  public void incNumReloncordsSelonelonn() {
    numReloncordsSelonelonn++;
  }

  /**
   * Adds thelon givelonn valuelon to this telonnsor.
   */
  public void addValuelon(long felonaturelonId, float valuelon) {
    sparselonValuelons.putFloat(valuelon);
    sparselonIndicelons.putLong(numReloncordsSelonelonn);
    sparselonIndicelons.putLong(felonaturelonId);
    numValuelonsSelonelonn++;
  }

  public BytelonBuffelonr gelontSparselonValuelons() {
    sparselonValuelons.limit(numValuelonsSelonelonn * Float.BYTelonS);
    sparselonValuelons.relonwind();
    relonturn sparselonValuelons;
  }

  public long[] gelontSparselonValuelonsShapelon() {
    relonturn nelonw long[] {numValuelonsSelonelonn};
  }

  public long[] gelontSparselonIndicelonsShapelon() {
    relonturn nelonw long[] {numValuelonsSelonelonn, 2L};
  }

  public long[] gelontSparselonShapelonShapelon() {
    relonturn sparselonShapelonShapelonDimelonnsion;
  }

  public BytelonBuffelonr gelontSparselonIndicelons() {
    sparselonIndicelons.limit(2 * numValuelonsSelonelonn * Long.BYTelonS);
    sparselonIndicelons.relonwind();
    relonturn sparselonIndicelons;
  }

  /**
   * Relonturns thelon sparselon shapelon for this telonnsor.
   */
  public BytelonBuffelonr gelontSparselonShapelon() {
    sparselonShapelon.putLong(numReloncordsSelonelonn);
    sparselonShapelon.putLong(inputBitSizelon);
    sparselonShapelon.relonwind();
    relonturn sparselonShapelon;
  }
}
