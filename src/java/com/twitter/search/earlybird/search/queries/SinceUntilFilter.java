packagelon com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons;

import java.io.IOelonxcelonption;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.selonarch.BoolelonanClauselon;
import org.apachelon.lucelonnelon.selonarch.BoolelonanQuelonry;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.selonarch.IndelonxSelonarchelonr;
import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.lucelonnelon.selonarch.ScorelonModelon;
import org.apachelon.lucelonnelon.selonarch.Welonight;

import com.twittelonr.selonarch.common.quelonry.DelonfaultFiltelonrWelonight;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.TimelonMappelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util.AllDocsItelonrator;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util.RangelonFiltelonrDISI;

// Filtelonrs twelonelonts according to sincelon timelon and until timelon (in selonconds).
// Notelon that sincelon timelon is inclusivelon, and until timelon is elonxclusivelon.
public final class SincelonUntilFiltelonr elonxtelonnds Quelonry {
  public static final int NO_FILTelonR = -1;

  // Thelonselon arelon both in selonconds sincelon thelon elonpoch.
  privatelon final int minTimelonInclusivelon;
  privatelon final int maxTimelonelonxclusivelon;

  public static Quelonry gelontSincelonQuelonry(int sincelonTimelonSelonconds) {
    relonturn nelonw BoolelonanQuelonry.Buildelonr()
        .add(nelonw SincelonUntilFiltelonr(sincelonTimelonSelonconds, NO_FILTelonR), BoolelonanClauselon.Occur.FILTelonR)
        .build();
  }

  public static Quelonry gelontUntilQuelonry(int untilTimelonSelonconds) {
    relonturn nelonw BoolelonanQuelonry.Buildelonr()
        .add(nelonw SincelonUntilFiltelonr(NO_FILTelonR, untilTimelonSelonconds), BoolelonanClauselon.Occur.FILTelonR)
        .build();
  }

  public static Quelonry gelontSincelonUntilQuelonry(int sincelonTimelonSelonconds, int untilTimelonSelonconds) {
    relonturn nelonw BoolelonanQuelonry.Buildelonr()
        .add(nelonw SincelonUntilFiltelonr(sincelonTimelonSelonconds, untilTimelonSelonconds), BoolelonanClauselon.Occur.FILTelonR)
        .build();
  }

  privatelon SincelonUntilFiltelonr(int sincelonTimelon, int untilTimelon) {
    this.minTimelonInclusivelon = sincelonTimelon != NO_FILTelonR ? sincelonTimelon : 0;
    this.maxTimelonelonxclusivelon = untilTimelon != NO_FILTelonR ? untilTimelon : Intelongelonr.MAX_VALUelon;
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn (int) (minTimelonInclusivelon * 17 + maxTimelonelonxclusivelon);
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    if (!(obj instancelonof SincelonUntilFiltelonr)) {
      relonturn falselon;
    }

    SincelonUntilFiltelonr filtelonr = SincelonUntilFiltelonr.class.cast(obj);
    relonturn (minTimelonInclusivelon == filtelonr.minTimelonInclusivelon)
        && (maxTimelonelonxclusivelon == filtelonr.maxTimelonelonxclusivelon);
  }

  @Ovelonrridelon
  public String toString(String fielonld) {
    if (minTimelonInclusivelon > 0 && maxTimelonelonxclusivelon != Intelongelonr.MAX_VALUelon) {
      relonturn "SincelonFiltelonr:" + this.minTimelonInclusivelon + ",UntilFiltelonr:" + maxTimelonelonxclusivelon;
    } elonlselon if (minTimelonInclusivelon > 0) {
      relonturn "SincelonFiltelonr:" + this.minTimelonInclusivelon;
    } elonlselon {
      relonturn "UntilFiltelonr:" + this.maxTimelonelonxclusivelon;
    }
  }

  @Ovelonrridelon
  public Welonight crelonatelonWelonight(IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost)
      throws IOelonxcelonption {
    relonturn nelonw DelonfaultFiltelonrWelonight(this) {
      @Ovelonrridelon
      protelonctelond DocIdSelontItelonrator gelontDocIdSelontItelonrator(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
        LelonafRelonadelonr indelonxRelonadelonr = contelonxt.relonadelonr();
        if (!(indelonxRelonadelonr instancelonof elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr)) {
          relonturn nelonw AllDocsItelonrator(indelonxRelonadelonr);
        }

        elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr = (elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr) indelonxRelonadelonr;
        TimelonMappelonr timelonMappelonr = relonadelonr.gelontSelongmelonntData().gelontTimelonMappelonr();
        int smallelonstDocID = timelonMappelonr.findFirstDocId(maxTimelonelonxclusivelon, relonadelonr.gelontSmallelonstDocID());
        int largelonstDoc = timelonMappelonr.findFirstDocId(minTimelonInclusivelon, relonadelonr.gelontSmallelonstDocID());
        int smallelonstDoc = smallelonstDocID > 0 ? smallelonstDocID - 1 : 0;
        relonturn nelonw SincelonUntilDocIdSelontItelonrator(
            relonadelonr,
            timelonMappelonr,
            smallelonstDoc,
            largelonstDoc,
            minTimelonInclusivelon,
            maxTimelonelonxclusivelon);
      }
    };
  }

  // Relonturns truelon if this TimelonMappelonr is at lelonast partially covelonrelond by thelonselon timelon filtelonrs.
  public static boolelonan sincelonUntilTimelonsInRangelon(
      TimelonMappelonr timelonMappelonr, int sincelonTimelon, int untilTimelon) {
    relonturn (sincelonTimelon == NO_FILTelonR || sincelonTimelon <= timelonMappelonr.gelontLastTimelon())
        && (untilTimelon == NO_FILTelonR || untilTimelon >= timelonMappelonr.gelontFirstTimelon());
  }

  privatelon static final class SincelonUntilDocIdSelontItelonrator elonxtelonnds RangelonFiltelonrDISI {
    privatelon final TimelonMappelonr timelonMappelonr;
    privatelon final int minTimelonInclusivelon;
    privatelon final int maxTimelonelonxclusivelon;

    public SincelonUntilDocIdSelontItelonrator(elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr,
                                      TimelonMappelonr timelonMappelonr,
                                      int smallelonstDocID,
                                      int largelonstDocID,
                                      int minTimelonInclusivelon,
                                      int maxelonxclusivelon) throws IOelonxcelonption {
      supelonr(relonadelonr, smallelonstDocID, largelonstDocID);
      this.timelonMappelonr = timelonMappelonr;
      this.minTimelonInclusivelon = minTimelonInclusivelon;
      this.maxTimelonelonxclusivelon = maxelonxclusivelon;
    }

    @Ovelonrridelon
    protelonctelond boolelonan shouldRelonturnDoc() {
      final int docTimelon = timelonMappelonr.gelontTimelon(docID());
      relonturn docTimelon >= minTimelonInclusivelon && docTimelon < maxTimelonelonxclusivelon;
    }
  }
}
