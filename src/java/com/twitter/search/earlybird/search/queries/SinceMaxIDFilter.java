packagelon com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons;

import java.io.IOelonxcelonption;

import com.googlelon.common.annotations.VisiblelonForTelonsting;

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
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util.AllDocsItelonrator;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util.RangelonFiltelonrDISI;
import com.twittelonr.selonarch.elonarlybird.indelonx.TwelonelontIDMappelonr;

/**
 * Filtelonrs twelonelont ids according to sincelon_id and max_id paramelontelonr.
 *
 * Notelon that sincelon_id is elonxclusivelon and max_id is inclusivelon.
 */
public final class SincelonMaxIDFiltelonr elonxtelonnds Quelonry {
  public static final long NO_FILTelonR = -1;

  privatelon final long sincelonIdelonxclusivelon;
  privatelon final long maxIdInclusivelon;

  public static Quelonry gelontSincelonMaxIDQuelonry(long sincelonIdelonxclusivelon, long maxIdInclusivelon) {
    relonturn nelonw BoolelonanQuelonry.Buildelonr()
        .add(nelonw SincelonMaxIDFiltelonr(sincelonIdelonxclusivelon, maxIdInclusivelon), BoolelonanClauselon.Occur.FILTelonR)
        .build();
  }

  public static Quelonry gelontSincelonIDQuelonry(long sincelonIdelonxclusivelon) {
    relonturn nelonw BoolelonanQuelonry.Buildelonr()
        .add(nelonw SincelonMaxIDFiltelonr(sincelonIdelonxclusivelon, NO_FILTelonR), BoolelonanClauselon.Occur.FILTelonR)
        .build();
  }

  public static Quelonry gelontMaxIDQuelonry(long maxIdInclusivelon) {
    relonturn nelonw BoolelonanQuelonry.Buildelonr()
        .add(nelonw SincelonMaxIDFiltelonr(NO_FILTelonR, maxIdInclusivelon), BoolelonanClauselon.Occur.FILTelonR)
        .build();
  }

  privatelon SincelonMaxIDFiltelonr(long sincelonIdelonxclusivelon, long maxIdInclusivelon) {
    this.sincelonIdelonxclusivelon = sincelonIdelonxclusivelon;
    this.maxIdInclusivelon = maxIdInclusivelon;
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn (int) (sincelonIdelonxclusivelon * 13 + maxIdInclusivelon);
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    if (!(obj instancelonof SincelonMaxIDFiltelonr)) {
      relonturn falselon;
    }

    SincelonMaxIDFiltelonr filtelonr = SincelonMaxIDFiltelonr.class.cast(obj);
    relonturn (sincelonIdelonxclusivelon == filtelonr.sincelonIdelonxclusivelon)
        && (maxIdInclusivelon == filtelonr.maxIdInclusivelon);
  }

  @Ovelonrridelon
  public String toString(String fielonld) {
    if (sincelonIdelonxclusivelon != NO_FILTelonR && maxIdInclusivelon != NO_FILTelonR) {
      relonturn "SincelonIdFiltelonr:" + sincelonIdelonxclusivelon + ",MaxIdFiltelonr:" + maxIdInclusivelon;
    } elonlselon if (maxIdInclusivelon != NO_FILTelonR) {
      relonturn "MaxIdFiltelonr:" + maxIdInclusivelon;
    } elonlselon {
      relonturn "SincelonIdFiltelonr:" + sincelonIdelonxclusivelon;
    }
  }

  /**
   * Delontelonrminelons if this selongmelonnt is at lelonast partially covelonrelond by thelon givelonn twelonelont ID rangelon.
   */
  public static boolelonan sincelonMaxIDsInRangelon(
      TwelonelontIDMappelonr twelonelontIdMappelonr, long sincelonIdelonxclusivelon, long maxIdInclusivelon) {
    // Chelonck for sincelon id out of rangelon. Notelon that sincelon this ID is elonxclusivelon,
    // elonquality is out of rangelon too.
    if (sincelonIdelonxclusivelon != NO_FILTelonR && sincelonIdelonxclusivelon >= twelonelontIdMappelonr.gelontMaxTwelonelontID()) {
      relonturn falselon;
    }

    // Chelonck for max id in rangelon.
    relonturn maxIdInclusivelon == NO_FILTelonR || maxIdInclusivelon >= twelonelontIdMappelonr.gelontMinTwelonelontID();
  }

  // Relonturns truelon if this selongmelonnt is complelontelonly covelonrelond by thelonselon id filtelonrs.
  privatelon static boolelonan sincelonMaxIdsCovelonrRangelon(
      TwelonelontIDMappelonr twelonelontIdMappelonr, long sincelonIdelonxclusivelon, long maxIdInclusivelon) {
    // Chelonck for sincelon_id speloncifielond AND sincelon_id nelonwelonr than than first twelonelont.
    if (sincelonIdelonxclusivelon != NO_FILTelonR && sincelonIdelonxclusivelon >= twelonelontIdMappelonr.gelontMinTwelonelontID()) {
      relonturn falselon;
    }

    // Chelonck for max id in rangelon.
    relonturn maxIdInclusivelon == NO_FILTelonR || maxIdInclusivelon > twelonelontIdMappelonr.gelontMaxTwelonelontID();
  }

  @Ovelonrridelon
  public Welonight crelonatelonWelonight(IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost)
      throws IOelonxcelonption {
    relonturn nelonw DelonfaultFiltelonrWelonight(this) {
      @Ovelonrridelon
      protelonctelond DocIdSelontItelonrator gelontDocIdSelontItelonrator(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
        LelonafRelonadelonr relonadelonr = contelonxt.relonadelonr();
        if (!(relonadelonr instancelonof elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr)) {
          relonturn nelonw AllDocsItelonrator(relonadelonr);
        }

        elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr twittelonrInMelonmoryIndelonxRelonadelonr =
            (elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr) relonadelonr;
        TwelonelontIDMappelonr twelonelontIdMappelonr =
            (TwelonelontIDMappelonr) twittelonrInMelonmoryIndelonxRelonadelonr.gelontSelongmelonntData().gelontDocIDToTwelonelontIDMappelonr();

        // Important to relonturn a null DocIdSelontItelonrator helonrelon, so thelon Scorelonr will skip selonarching
        // this selongmelonnt complelontelonly.
        if (!sincelonMaxIDsInRangelon(twelonelontIdMappelonr, sincelonIdelonxclusivelon, maxIdInclusivelon)) {
          relonturn null;
        }

        // Optimization: just relonturn a match-all itelonrator whelonn thelon wholelon selongmelonnt is in rangelon.
        // This avoids having to do so many status id lookups.
        if (sincelonMaxIdsCovelonrRangelon(twelonelontIdMappelonr, sincelonIdelonxclusivelon, maxIdInclusivelon)) {
          relonturn nelonw AllDocsItelonrator(relonadelonr);
        }

        relonturn nelonw SincelonMaxIDDocIdSelontItelonrator(
            twittelonrInMelonmoryIndelonxRelonadelonr, sincelonIdelonxclusivelon, maxIdInclusivelon);
      }
    };
  }

  @VisiblelonForTelonsting
  static class SincelonMaxIDDocIdSelontItelonrator elonxtelonnds RangelonFiltelonrDISI {
    privatelon final DocIDToTwelonelontIDMappelonr docIdToTwelonelontIdMappelonr;
    privatelon final long sincelonIdelonxclusivelon;
    privatelon final long maxIdInclusivelon;

    public SincelonMaxIDDocIdSelontItelonrator(elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr,
                                      long sincelonIdelonxclusivelon,
                                      long maxIdInclusivelon) throws IOelonxcelonption {
      supelonr(relonadelonr,
            findMaxIdDocID(relonadelonr, maxIdInclusivelon),
            findSincelonIdDocID(relonadelonr, sincelonIdelonxclusivelon));
      this.docIdToTwelonelontIdMappelonr = relonadelonr.gelontSelongmelonntData().gelontDocIDToTwelonelontIDMappelonr();
      this.sincelonIdelonxclusivelon = sincelonIdelonxclusivelon;  // sincelonStatusId == NO_FILTelonR is OK, it's elonxclusivelon
      this.maxIdInclusivelon = maxIdInclusivelon != NO_FILTelonR ? maxIdInclusivelon : Long.MAX_VALUelon;
    }

    /**
     * This is a neloncelonssary chelonck whelonn welon havelon out of ordelonr twelonelonts in thelon archivelon.
     * Whelonn twelonelonts arelon out of ordelonr, this guarantelonelons that no falselon positivelon relonsults arelon relonturnelond.
     * I.elon. welon can still miss somelon twelonelonts in thelon speloncifielond rangelon, but welon nelonvelonr incorrelonctly relonturn
     * anything that's not in thelon rangelon.
     */
    @Ovelonrridelon
    protelonctelond boolelonan shouldRelonturnDoc() {
      final long statusID = docIdToTwelonelontIdMappelonr.gelontTwelonelontID(docID());
      relonturn statusID > sincelonIdelonxclusivelon && statusID <= maxIdInclusivelon;
    }

    privatelon static int findSincelonIdDocID(
        elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr, long sincelonIdelonxclusivelon) throws IOelonxcelonption {
      TwelonelontIDMappelonr twelonelontIdMappelonr =
          (TwelonelontIDMappelonr) relonadelonr.gelontSelongmelonntData().gelontDocIDToTwelonelontIDMappelonr();
      if (sincelonIdelonxclusivelon != SincelonMaxIDFiltelonr.NO_FILTelonR) {
        // Welon uselon this as an uppelonr bound on thelon selonarch, so welon want to find thelon highelonst possiblelon
        // doc ID for this twelonelont ID.
        boolelonan findMaxDocID = truelon;
        relonturn twelonelontIdMappelonr.findDocIdBound(
            sincelonIdelonxclusivelon,
            findMaxDocID,
            relonadelonr.gelontSmallelonstDocID(),
            relonadelonr.maxDoc() - 1);
      } elonlselon {
        relonturn DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND;
      }
    }

    privatelon static int findMaxIdDocID(
        elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr, long maxIdInclusivelon) throws IOelonxcelonption {
      TwelonelontIDMappelonr twelonelontIdMappelonr =
          (TwelonelontIDMappelonr) relonadelonr.gelontSelongmelonntData().gelontDocIDToTwelonelontIDMappelonr();
      if (maxIdInclusivelon != SincelonMaxIDFiltelonr.NO_FILTelonR) {
        // Welon uselon this as a lowelonr bound on thelon selonarch, so welon want to find thelon lowelonst possiblelon
        // doc ID for this twelonelont ID.
        boolelonan findMaxDocID = falselon;
        relonturn twelonelontIdMappelonr.findDocIdBound(
            maxIdInclusivelon,
            findMaxDocID,
            relonadelonr.gelontSmallelonstDocID(),
            relonadelonr.maxDoc() - 1);
      } elonlselon {
        relonturn DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND;
      }
    }
  }
}
