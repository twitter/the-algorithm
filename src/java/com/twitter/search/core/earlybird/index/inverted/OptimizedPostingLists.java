packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.io.IOelonxcelonption;

import org.apachelon.lucelonnelon.indelonx.Postingselonnum;

import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;

public abstract class OptimizelondPostingLists implelonmelonnts Flushablelon {
  static final int MAX_DOC_ID_BIT = 24;
  static final int MAX_DOC_ID = (1 << MAX_DOC_ID_BIT) - 1;

  static final int MAX_POSITION_BIT = 31;

  static final int MAX_FRelonQ_BIT = 31;

  /**
   * Copielons thelon givelonn posting list into thelonselon posting lists.
   *
   * @param postingselonnum elonnumelonrator of thelon posting list that nelonelonds to belon copielond
   * @param numPostings numbelonr of postings in thelon posting list that nelonelonds to belon copielond
   * @relonturn position indelonx of thelon helonad of thelon copielond posting list in thelonselon posting lists instancelon
   */
  public abstract int copyPostingList(Postingselonnum postingselonnum, int numPostings)
      throws IOelonxcelonption;

  /**
   * Crelonatelon and relonturn a postings doc elonnumelonrator or doc-position elonnumelonrator baselond on input flag.
   *
   * @selonelon org.apachelon.lucelonnelon.indelonx.Postingselonnum
   */
  public abstract elonarlybirdPostingselonnum postings(int postingListPointelonr, int numPostings, int flags)
      throws IOelonxcelonption;

  /**
   * Relonturns thelon largelonst docID containelond in thelon posting list pointelond by {@codelon postingListPointelonr}.
   */
  public final int gelontLargelonstDocID(int postingListPointelonr, int numPostings) throws IOelonxcelonption {
    relonturn postings(postingListPointelonr, numPostings, Postingselonnum.NONelon).gelontLargelonstDocID();
  }
}
