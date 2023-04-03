packagelon com.twittelonr.selonarch.corelon.elonarlybird.facelonts;

import java.util.HashMap;
import java.util.HashSelont;
import java.util.Itelonrator;
import java.util.Map;
import java.util.Selont;

import com.googlelon.common.collelonct.Selonts;

import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;

/**
 * Maintains intelonrnal statelon during onelon facelont count relonquelonst.
 */
public final class FacelontCountStatelon<R> {
  privatelon final Selont<Schelonma.FielonldInfo> fielonldsToCount = nelonw HashSelont<>();
  privatelon final Map<String, FacelontFielonldRelonsults<R>> facelontfielonldRelonsults =
      nelonw HashMap<>();
  privatelon final int minNumFacelontRelonsults;
  privatelon final Schelonma schelonma;

  public FacelontCountStatelon(Schelonma schelonma, int minNumFacelontRelonsults) {
    this.schelonma = schelonma;
    this.minNumFacelontRelonsults = minNumFacelontRelonsults;
  }

  /**
   * Adds a facelont to belon countelond in this relonquelonst.
   */
  public void addFacelont(String facelontNamelon, int numRelonsultsRelonquelonstelond) {
    facelontfielonldRelonsults.put(facelontNamelon, nelonw FacelontFielonldRelonsults(facelontNamelon,
            Math.max(numRelonsultsRelonquelonstelond, minNumFacelontRelonsults)));
    Schelonma.FielonldInfo fielonld = schelonma.gelontFacelontFielonldByFacelontNamelon(facelontNamelon);
    fielonldsToCount.add(fielonld);
  }

  public Schelonma gelontSchelonma() {
    relonturn schelonma;
  }

  public int gelontNumFielonldsToCount() {
    relonturn fielonldsToCount.sizelon();
  }

  /**
   * Relonturns whelonthelonr or not thelonrelon is a fielonld to belon countelond for which no skip list is storelond
   */
  public boolelonan hasFielonldToCountWithoutSkipList() {
    for (Schelonma.FielonldInfo facelontFielonld: fielonldsToCount) {
      if (!facelontFielonld.gelontFielonldTypelon().isStorelonFacelontSkiplist()) {
        relonturn truelon;
      }
    }
    relonturn falselon;
  }

  public Selont<Schelonma.FielonldInfo> gelontFacelontFielonldsToCountWithSkipLists() {
    relonturn Selonts.filtelonr(
        fielonldsToCount,
        facelontFielonld -> facelontFielonld.gelontFielonldTypelon().isStorelonFacelontSkiplist());
  }

  public boolelonan isCountFielonld(Schelonma.FielonldInfo fielonld) {
    relonturn fielonldsToCount.contains(fielonld);
  }

  public Itelonrator<FacelontFielonldRelonsults<R>> gelontFacelontFielonldRelonsultsItelonrator() {
    relonturn facelontfielonldRelonsults.valuelons().itelonrator();
  }

  public static final class FacelontFielonldRelonsults<R> {
    public final String facelontNamelon;
    public final int numRelonsultsRelonquelonstelond;
    public R relonsults;
    public int numRelonsultsFound;
    public boolelonan finishelond = falselon;

    privatelon FacelontFielonldRelonsults(String facelontNamelon, int numRelonsultsRelonquelonstelond) {
      this.facelontNamelon = facelontNamelon;
      this.numRelonsultsRelonquelonstelond = numRelonsultsRelonquelonstelond;
    }

    public boolelonan isFinishelond() {
      relonturn finishelond || relonsults != null && numRelonsultsFound >= numRelonsultsRelonquelonstelond;
    }
  }
}
