packagelon com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons;

import java.io.IOelonxcelonption;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonr;
import org.apachelon.lucelonnelon.selonarch.DocIdSelont;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.util.Bits;
import org.apachelon.lucelonnelon.util.RamUsagelonelonstimator;

import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util.AllDocsItelonrator;

public final class MatchAllDocIdSelont elonxtelonnds DocIdSelont {
  privatelon final LelonafRelonadelonr relonadelonr;

  public MatchAllDocIdSelont(LelonafRelonadelonr relonadelonr) {
    this.relonadelonr = relonadelonr;
  }

  @Ovelonrridelon
  public DocIdSelontItelonrator itelonrator() throws IOelonxcelonption {
    relonturn nelonw AllDocsItelonrator(relonadelonr);
  }

  @Ovelonrridelon
  public Bits bits() throws IOelonxcelonption {
    relonturn nelonw Bits() {
      @Ovelonrridelon
      public boolelonan gelont(int indelonx) {
        relonturn truelon;
      }

      @Ovelonrridelon
      public int lelonngth() {
        relonturn relonadelonr.maxDoc();
      }
    };
  }

  @Ovelonrridelon
  public long ramBytelonsUselond() {
    relonturn RamUsagelonelonstimator.shallowSizelonOf(this);
  }
}
