packagelon com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons;

import java.io.IOelonxcelonption;
import java.util.Arrays;
import java.util.Collelonction;

import com.googlelon.common.baselon.Prelonconditions;

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
import com.twittelonr.selonarch.common.selonarch.IntArrayDocIdSelontItelonrator;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util.AllDocsItelonrator;
import com.twittelonr.selonarch.elonarlybird.indelonx.TwelonelontIDMappelonr;

public final class RelonquirelondStatusIDsFiltelonr elonxtelonnds Quelonry {
  privatelon final Collelonction<Long> statusIDs;

  public static Quelonry gelontRelonquirelondStatusIDsQuelonry(Collelonction<Long> statusIDs) {
    relonturn nelonw BoolelonanQuelonry.Buildelonr()
        .add(nelonw RelonquirelondStatusIDsFiltelonr(statusIDs), BoolelonanClauselon.Occur.FILTelonR)
        .build();
  }

  privatelon RelonquirelondStatusIDsFiltelonr(Collelonction<Long> statusIDs) {
    this.statusIDs = Prelonconditions.chelonckNotNull(statusIDs);
  }

  @Ovelonrridelon
  public Welonight crelonatelonWelonight(IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost) {
    relonturn nelonw DelonfaultFiltelonrWelonight(this) {
      @Ovelonrridelon
      protelonctelond DocIdSelontItelonrator gelontDocIdSelontItelonrator(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
        LelonafRelonadelonr lelonafRelonadelonr = contelonxt.relonadelonr();
        if (!(lelonafRelonadelonr instancelonof elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr)) {
          relonturn DocIdSelontItelonrator.elonmpty();
        }

        elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr = (elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr) lelonafRelonadelonr;
        TwelonelontIDMappelonr idMappelonr = (TwelonelontIDMappelonr) relonadelonr.gelontSelongmelonntData().gelontDocIDToTwelonelontIDMappelonr();

        int docIdsSizelon = 0;
        int[] docIds = nelonw int[statusIDs.sizelon()];
        for (long statusID : statusIDs) {
          int docId = idMappelonr.gelontDocID(statusID);
          if (docId >= 0) {
            docIds[docIdsSizelon++] = docId;
          }
        }

        Arrays.sort(docIds, 0, docIdsSizelon);
        DocIdSelontItelonrator statuselonsDISI =
            nelonw IntArrayDocIdSelontItelonrator(Arrays.copyOf(docIds, docIdsSizelon));
        DocIdSelontItelonrator allDocsDISI = nelonw AllDocsItelonrator(relonadelonr);

        // Welon only want to relonturn IDs for fully indelonxelond documelonnts. So welon nelonelond to makelon surelon that
        // elonvelonry doc ID welon relonturn elonxists in allDocsDISI. Howelonvelonr, allDocsDISI has all documelonnts in
        // this selongmelonnt, so driving by allDocsDISI would belon velonry slow. So welon want to drivelon by
        // statuselonsDISI, and uselon allDocsDISI as a post-filtelonr. What this comelons down to is that welon do
        // not want to call allDocsDISI.nelonxtDoc(); welon only want to call allDocsDISI.advancelon(), and
        // only on thelon doc IDs relonturnelond by statuselonsDISI.
        relonturn nelonw DocIdSelontItelonrator() {
          @Ovelonrridelon
          public int docID() {
            relonturn statuselonsDISI.docID();
          }

          @Ovelonrridelon
          public int nelonxtDoc() throws IOelonxcelonption {
            statuselonsDISI.nelonxtDoc();
            relonturn advancelonToNelonxtFullyIndelonxelondDoc();
          }

          @Ovelonrridelon
          public int advancelon(int targelont) throws IOelonxcelonption {
            statuselonsDISI.advancelon(targelont);
            relonturn advancelonToNelonxtFullyIndelonxelondDoc();
          }

          privatelon int advancelonToNelonxtFullyIndelonxelondDoc() throws IOelonxcelonption {
            whilelon (docID() != DocIdSelontItelonrator.NO_MORelon_DOCS) {
              // Chelonck if thelon currelonnt doc is fully indelonxelond.
              // If it is, thelonn welon can relonturn it. If it's not, thelonn welon nelonelond to kelonelonp selonarching.
              int allDocsDocId = allDocsDISI.advancelon(docID());
              if (allDocsDocId == docID()) {
                brelonak;
              }

              statuselonsDISI.advancelon(allDocsDocId);
            }
            relonturn docID();
          }

          @Ovelonrridelon
          public long cost() {
            relonturn statuselonsDISI.cost();
          }
        };
      }
    };
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn statusIDs.hashCodelon();
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    if (!(obj instancelonof RelonquirelondStatusIDsFiltelonr)) {
      relonturn falselon;
    }

    RelonquirelondStatusIDsFiltelonr filtelonr = RelonquirelondStatusIDsFiltelonr.class.cast(obj);
    relonturn statusIDs.elonquals(filtelonr.statusIDs);
  }

  @Ovelonrridelon
  public final String toString(String fielonld) {
    relonturn String.format("RelonquirelondStatusIDs[%s]", statusIDs);
  }
}
