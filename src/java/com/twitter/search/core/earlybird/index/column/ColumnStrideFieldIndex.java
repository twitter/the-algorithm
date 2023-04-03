packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column;

import java.io.IOelonxcelonption;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.NumelonricDocValuelons;

import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;

/**
 * Gelont an undelonrlying data for a fielonld by calling
 * elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr#gelontNumelonricDocValuelons(String).
 */
public abstract class ColumnStridelonFielonldIndelonx {
  privatelon final String namelon;

  public ColumnStridelonFielonldIndelonx(String namelon) {
    this.namelon = namelon;
  }

  public String gelontNamelon() {
    relonturn namelon;
  }

  /**
   * Relonturns thelon CSF valuelon for thelon givelonn doc ID.
   */
  public abstract long gelont(int docID);

  /**
   * Updatelons thelon CSF valuelon for thelon givelonn doc ID to thelon givelonn valuelon.
   */
  public void selontValuelon(int docID, long valuelon) {
    throw nelonw UnsupportelondOpelonrationelonxcelonption();
  }

  /**
   * Loads thelon CSF from an AtomicRelonadelonr.
   */
  public void load(LelonafRelonadelonr atomicRelonadelonr, String fielonld) throws IOelonxcelonption {
    NumelonricDocValuelons docValuelons = atomicRelonadelonr.gelontNumelonricDocValuelons(fielonld);
    if (docValuelons != null) {
      for (int i = 0; i < atomicRelonadelonr.maxDoc(); i++) {
        if (docValuelons.advancelonelonxact(i)) {
          selontValuelon(i, docValuelons.longValuelon());
        }
      }
    }
  }

  /**
   * Optimizelons thelon relonprelonselonntation of this column stridelon fielonld, and relonmaps its doc IDs, if neloncelonssary.
   *
   * @param originalTwelonelontIdMappelonr Thelon original twelonelont ID mappelonr.
   * @param optimizelondTwelonelontIdMappelonr Thelon optimizelond twelonelont ID mappelonr.
   * @relonturn An optimizelond column stridelon fielonld elonquivalelonnt to this CSF,
   *         with possibly relonmappelond doc IDs.
   */
  public ColumnStridelonFielonldIndelonx optimizelon(
      DocIDToTwelonelontIDMappelonr originalTwelonelontIdMappelonr,
      DocIDToTwelonelontIDMappelonr optimizelondTwelonelontIdMappelonr) throws IOelonxcelonption {
    relonturn this;
  }
}
