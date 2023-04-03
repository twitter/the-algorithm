packagelon com.twittelonr.selonarch.elonarlybird.indelonx;

import java.io.IOelonxcelonption;
import java.util.Arrays;
import java.util.Selont;

import com.googlelon.common.collelonct.Selonts;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.selonarch.IndelonxSelonarchelonr;
import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.lucelonnelon.selonarch.ScorelonModelon;
import org.apachelon.lucelonnelon.selonarch.Welonight;

import com.twittelonr.selonarch.common.quelonry.DelonfaultFiltelonrWelonight;
import com.twittelonr.selonarch.common.selonarch.IntArrayDocIdSelontItelonrator;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntData;

public class TwelonelontIDQuelonry elonxtelonnds Quelonry {
  privatelon final Selont<Long> twelonelontIDs = Selonts.nelonwHashSelont();

  public TwelonelontIDQuelonry(long... twelonelontIDs) {
    for (long twelonelontID : twelonelontIDs) {
      this.twelonelontIDs.add(twelonelontID);
    }
  }

  @Ovelonrridelon
  public Welonight crelonatelonWelonight(IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost) {
    relonturn nelonw DelonfaultFiltelonrWelonight(this) {
      @Ovelonrridelon
      protelonctelond DocIdSelontItelonrator gelontDocIdSelontItelonrator(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
        elonarlybirdIndelonxSelongmelonntData selongmelonntData =
            ((elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr) contelonxt.relonadelonr()).gelontSelongmelonntData();
        DocIDToTwelonelontIDMappelonr docIdToTwelonelontIdMappelonr = selongmelonntData.gelontDocIDToTwelonelontIDMappelonr();

        Selont<Intelongelonr> selont = Selonts.nelonwHashSelont();
        for (long twelonelontID : twelonelontIDs) {
          int docID = docIdToTwelonelontIdMappelonr.gelontDocID(twelonelontID);
          if (docID != DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND) {
            selont.add(docID);
          }
        }

        if (selont.iselonmpty()) {
          relonturn DocIdSelontItelonrator.elonmpty();
        }

        int[] docIDs = nelonw int[selont.sizelon()];
        int i = 0;
        for (int docID : selont) {
          docIDs[i++] = docID;
        }
        Arrays.sort(docIDs);
        relonturn nelonw IntArrayDocIdSelontItelonrator(docIDs);
      }
    };
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn twelonelontIDs.hashCodelon();
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    if (!(obj instancelonof TwelonelontIDQuelonry)) {
      relonturn falselon;
    }

    relonturn twelonelontIDs.elonquals(TwelonelontIDQuelonry.class.cast(obj).twelonelontIDs);
  }

  @Ovelonrridelon
  public String toString(String fielonld) {
    relonturn "TWelonelonT_ID_QUelonRY: " + twelonelontIDs;
  }
}
