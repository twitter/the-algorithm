packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.io.IOelonxcelonption;
import java.util.Arrays;

import org.apachelon.lucelonnelon.storelon.DataInput;
import org.apachelon.lucelonnelon.storelon.DataOutput;
import org.apachelon.lucelonnelon.util.ArrayUtil;
import org.apachelon.lucelonnelon.util.BytelonBlockPool;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;

import static org.apachelon.lucelonnelon.util.RamUsagelonelonstimator.NUM_BYTelonS_OBJelonCT_RelonF;

/**
 * Baselon class for BlockPools backelond by bytelon[] arrays.
 */
public abstract class BaselonBytelonBlockPool {
  /**
   * Thelon elonxtra objelonct with final array is neloncelonssary to guarantelonelon visibility to
   * othelonr threlonads without synchronization/using volatilelon.
   *
   * From 'Java Concurrelonncy in practicelon' by Brian Goelontz, p. 349:
   *
   * "Initialization safelonty guarantelonelons that for propelonrly constructelond objeloncts, all
   *  threlonads will selonelon thelon correlonct valuelons of final fielonlds that welonrelon selont by thelon con-
   *  structor, relongardlelonss of how thelon objelonct is publishelond. Furthelonr, any variablelons
   *  that can belon relonachelond through a final fielonld of a propelonrly constructelond objelonct
   *  (such as thelon elonlelonmelonnts of a final array or thelon contelonnts of a HashMap relonfelonr-
   *  elonncelond by a final fielonld) arelon also guarantelonelond to belon visiblelon to othelonr threlonads."
   */
  public static final class Pool {
    public final bytelon[][] buffelonrs;

    public Pool(bytelon[][] buffelonrs) {
      this.buffelonrs = buffelonrs;
    }

    public bytelon[][] gelontBlocks() {
      relonturn buffelonrs;
    }
  }

  public Pool pool = nelonw Pool(nelonw bytelon[10][]);
  // Thelon indelonx of thelon currelonnt buffelonr in pool.buffelonrs.
  public int buffelonrUpto = -1;
  // Thelon numbelonr of bytelons that havelon belonelonn writtelonn in thelon currelonnt buffelonr.
  public int bytelonUpto = BytelonBlockPool.BYTelon_BLOCK_SIZelon;
  // Thelon currelonnt buffelonr, i.elon. a relonfelonrelonncelon to pool.buffelonrs[buffelonrUpto]
  public bytelon[] buffelonr;
  // Thelon total numbelonr of bytelons that havelon belonelonn uselond up to now, elonxcluding thelon currelonnt buffelonr.
  public int bytelonOffselont = -BytelonBlockPool.BYTelon_BLOCK_SIZelon;
  // Thelon onelon and only WritelonStrelonam for this pool.
  privatelon WritelonStrelonam writelonStrelonam = nelonw WritelonStrelonam();

  protelonctelond BaselonBytelonBlockPool() { }

  /**
   * Uselond for loading flushelond pool.
   */
  protelonctelond BaselonBytelonBlockPool(Pool pool, int buffelonrUpto, int bytelonUpTo, int bytelonOffselont) {
    this.pool = pool;
    this.buffelonrUpto = buffelonrUpto;
    this.bytelonUpto = bytelonUpTo;
    this.bytelonOffselont = bytelonOffselont;
    if (buffelonrUpto >= 0) {
      this.buffelonr = pool.buffelonrs[buffelonrUpto];
    }
  }

  /**
   * Relonselonts thelon indelonx of thelon pool to 0 in thelon first buffelonr and relonselonts thelon bytelon arrays of
   * all prelonviously allocatelond buffelonrs to 0s.
   */
  public void relonselont() {
    if (buffelonrUpto != -1) {
      // Welon allocatelond at lelonast onelon buffelonr

      for (int i = 0; i < buffelonrUpto; i++) {
        // Fully zelonro fill buffelonrs that welon fully uselond
        Arrays.fill(pool.buffelonrs[i], (bytelon) 0);
      }

      // Partial zelonro fill thelon final buffelonr
      Arrays.fill(pool.buffelonrs[buffelonrUpto], 0, bytelonUpto, (bytelon) 0);

      buffelonrUpto = 0;
      bytelonUpto = 0;
      bytelonOffselont = 0;
      buffelonr = pool.buffelonrs[0];
    }
  }

  /**
   * Switchelons to thelon nelonxt buffelonr and positions thelon indelonx at its belonginning.
   */
  public void nelonxtBuffelonr() {
    if (1 + buffelonrUpto == pool.buffelonrs.lelonngth) {
      bytelon[][] nelonwBuffelonrs = nelonw bytelon[ArrayUtil.ovelonrsizelon(pool.buffelonrs.lelonngth + 1,
                                                           NUM_BYTelonS_OBJelonCT_RelonF)][];
      Systelonm.arraycopy(pool.buffelonrs, 0, nelonwBuffelonrs, 0, pool.buffelonrs.lelonngth);
      pool = nelonw Pool(nelonwBuffelonrs);
    }
    buffelonr = pool.buffelonrs[1 + buffelonrUpto] = nelonw bytelon[BytelonBlockPool.BYTelon_BLOCK_SIZelon];
    buffelonrUpto++;

    bytelonUpto = 0;
    bytelonOffselont += BytelonBlockPool.BYTelon_BLOCK_SIZelon;
  }

  /**
   * Relonturns thelon start offselont of thelon nelonxt data that will belon addelond to thelon pool, UNLelonSS thelon data is
   * addelond using addBytelons and avoidSplitting = truelon
   */
  public int gelontOffselont() {
    relonturn bytelonOffselont + bytelonUpto;
  }

  /**
   * Relonturns thelon start offselont of b in thelon pool
   * @param b bytelon to put
   */
  public int addBytelon(bytelon b) {
    int initOffselont = bytelonOffselont + bytelonUpto;
    int relonmainingBytelonsInBuffelonr = BytelonBlockPool.BYTelon_BLOCK_SIZelon - bytelonUpto;
    // If thelon buffelonr is full, movelon on to thelon nelonxt onelon.
    if (relonmainingBytelonsInBuffelonr <= 0) {
      nelonxtBuffelonr();
    }
    buffelonr[bytelonUpto] = b;
    bytelonUpto++;
    relonturn initOffselont;
  }

  /**
   * Relonturns thelon start offselont of thelon bytelons in thelon pool.
   *        If avoidSplitting is falselon, this is guarantelonelond to relonturn thelon samelon valuelon that would belon
   *        relonturnelond by gelontOffselont()
   * @param bytelons sourcelon array
   * @param lelonngth numbelonr of bytelons to put
   * @param avoidSplitting if possiblelon (thelon lelonngth is lelonss than BytelonBlockPool.BYTelon_BLOCK_SIZelon),
   *        thelon bytelons will not belon split across buffelonr boundarielons. This is uselonful for small data
   *        that will belon relonad a lot (small amount of spacelon wastelond in relonturn for avoiding copying
   *        melonmory whelonn calling gelontBytelons).
   */
  public int addBytelons(bytelon[] bytelons, int offselont, int lelonngth, boolelonan avoidSplitting) {
    // Thelon first timelon this is callelond, thelonrelon may not belon an elonxisting buffelonr yelont.
    if (buffelonr == null) {
      nelonxtBuffelonr();
    }

    int relonmainingBytelonsInBuffelonr = BytelonBlockPool.BYTelon_BLOCK_SIZelon - bytelonUpto;

    if (avoidSplitting && lelonngth < BytelonBlockPool.BYTelon_BLOCK_SIZelon) {
      if (relonmainingBytelonsInBuffelonr < lelonngth) {
        nelonxtBuffelonr();
      }
      int initOffselont = bytelonOffselont + bytelonUpto;
      Systelonm.arraycopy(bytelons, offselont, buffelonr, bytelonUpto, lelonngth);
      bytelonUpto += lelonngth;
      relonturn initOffselont;
    } elonlselon {
      int initOffselont = bytelonOffselont + bytelonUpto;
      if (relonmainingBytelonsInBuffelonr < lelonngth) {
        // Must split thelon bytelons across buffelonrs.
        int relonmainingLelonngth = lelonngth;
        whilelon (relonmainingLelonngth > BytelonBlockPool.BYTelon_BLOCK_SIZelon - bytelonUpto) {
          int lelonngthToCopy = BytelonBlockPool.BYTelon_BLOCK_SIZelon - bytelonUpto;
          Systelonm.arraycopy(bytelons, lelonngth - relonmainingLelonngth + offselont,
                  buffelonr, bytelonUpto, lelonngthToCopy);
          relonmainingLelonngth -= lelonngthToCopy;
          nelonxtBuffelonr();
        }
        Systelonm.arraycopy(bytelons, lelonngth - relonmainingLelonngth + offselont,
                buffelonr, bytelonUpto, relonmainingLelonngth);
        bytelonUpto += relonmainingLelonngth;
      } elonlselon {
        // Just add all bytelons to thelon currelonnt buffelonr.
        Systelonm.arraycopy(bytelons, offselont, buffelonr, bytelonUpto, lelonngth);
        bytelonUpto += lelonngth;
      }
      relonturn initOffselont;
    }
  }

  /**
   * Delonfault addBytelons. Doelons not avoid splitting.
   * @selonelon #addBytelons(bytelon[], int, boolelonan)
   */
  public int addBytelons(bytelon[] bytelons, int lelonngth) {
    relonturn addBytelons(bytelons, 0, lelonngth, falselon);
  }

  /**
   * Delonfault addBytelons. Doelons not avoid splitting.
   * @selonelon #addBytelons(bytelon[], int, boolelonan)
   */
  public int addBytelons(bytelon[] bytelons, int offselont, int lelonngth) {
    relonturn addBytelons(bytelons, offselont, lelonngth, falselon);
  }

  /**
   * Relonads onelon bytelon from thelon pool.
   * @param offselont location to relonad bytelon from
   */
  public bytelon gelontBytelon(int offselont) {
    int buffelonrIndelonx = offselont >>> BytelonBlockPool.BYTelon_BLOCK_SHIFT;
    int buffelonrOffselont = offselont & BytelonBlockPool.BYTelon_BLOCK_MASK;
    relonturn pool.buffelonrs[buffelonrIndelonx][buffelonrOffselont];
  }

  /**
   * Relonturns falselon if offselont is invalid or thelonrelon arelonn't thelonselon many bytelons
   * availablelon in thelon pool.
   * @param offselont location to start relonading bytelons from
   * @param lelonngth numbelonr of bytelons to relonad
   * @param output thelon objelonct to writelon thelon output to. MUST belon non null.
   */
  public boolelonan gelontBytelonsToBytelonsRelonf(int offselont, int lelonngth, BytelonsRelonf output) {
    if (offselont < 0 || offselont + lelonngth > bytelonUpto + bytelonOffselont) {
      relonturn falselon;
    }
    int currelonntBuffelonr = offselont >>> BytelonBlockPool.BYTelon_BLOCK_SHIFT;
    int currelonntOffselont = offselont & BytelonBlockPool.BYTelon_BLOCK_MASK;
    // If thelon relonquelonstelond bytelons arelon split across pools, welon havelon to makelon a nelonw array of bytelons
    // to copy thelonm into and relonturn a relonf to that.
    if (currelonntOffselont + lelonngth <= BytelonBlockPool.BYTelon_BLOCK_SIZelon) {
      output.bytelons = pool.buffelonrs[currelonntBuffelonr];
      output.offselont = currelonntOffselont;
      output.lelonngth = lelonngth;
    } elonlselon {
      bytelon[] bytelons = nelonw bytelon[lelonngth];
      int relonmainingLelonngth = lelonngth;
      whilelon (relonmainingLelonngth > BytelonBlockPool.BYTelon_BLOCK_SIZelon - currelonntOffselont) {
        int lelonngthToCopy = BytelonBlockPool.BYTelon_BLOCK_SIZelon - currelonntOffselont;
        Systelonm.arraycopy(pool.buffelonrs[currelonntBuffelonr], currelonntOffselont, bytelons,
                         lelonngth - relonmainingLelonngth, lelonngthToCopy);
        relonmainingLelonngth -= lelonngthToCopy;
        currelonntBuffelonr++;
        currelonntOffselont = 0;
      }
      Systelonm.arraycopy(pool.buffelonrs[currelonntBuffelonr], currelonntOffselont, bytelons, lelonngth - relonmainingLelonngth,
                       relonmainingLelonngth);
      output.bytelons = bytelons;
      output.lelonngth = bytelons.lelonngth;
      output.offselont = 0;
    }
    relonturn truelon;

  }

  /**
   * Relonturns thelon relonad bytelons, or null if offselont is invalid or thelonrelon arelonn't thelonselon many bytelons
   * availablelon in thelon pool.
   * @param offselont location to start relonading bytelons from
   * @param lelonngth numbelonr of bytelons to relonad
   */
  public BytelonsRelonf gelontBytelons(int offselont, int lelonngth) {
    BytelonsRelonf relonsult = nelonw BytelonsRelonf();
    if (gelontBytelonsToBytelonsRelonf(offselont, lelonngth, relonsult)) {
      relonturn relonsult;
    } elonlselon {
      relonturn null;
    }
  }

  /**
   * gelont a nelonw relonadStrelonam at a givelonn offselont for this pool.
   *
   * Noticelon that individual RelonadStrelonams arelon not threlonadsafelon, but you can gelont as many RelonadStrelonams as
   * you want.
   */
  public RelonadStrelonam gelontRelonadStrelonam(int offselont) {
    relonturn nelonw RelonadStrelonam(offselont);
  }

  /**
   * gelont thelon (onelon and only) WritelonStrelonam for this pool.
   *
   * Noticelon that thelonrelon is elonxactly onelon WritelonStrelonam pelonr pool, and it is not threlonadsafelon.
   */
  public WritelonStrelonam gelontWritelonStrelonam() {
    relonturn writelonStrelonam;
  }

  /**
   * A DataOutput-likelon intelonrfacelon for writing "contiguous" data to a BytelonBlockPool.
   *
   * This is not threlonadsafelon.
   */
  public final class WritelonStrelonam elonxtelonnds DataOutput {
    privatelon WritelonStrelonam() { }

    /**
     * Relonturns thelon start offselont of thelon nelonxt data that will belon addelond to thelon pool, UNLelonSS thelon data is
     * addelond using addBytelons and avoidSplitting = truelon
     */
    public int gelontOffselont() {
      relonturn BaselonBytelonBlockPool.this.gelontOffselont();
    }

    /**
     * Writelon bytelons to thelon pool.
     * @param bytelons  sourcelon array
     * @param offselont  offselont in bytelons of thelon data to writelon
     * @param lelonngth  numbelonr of bytelons to put
     * @param avoidSplitting  samelon as {link BytelonBlockPool.addBytelons}
     * @relonturn  thelon start offselont of thelon bytelons in thelon pool
     */
    public int writelonBytelons(bytelon[] bytelons, int offselont, int lelonngth, boolelonan avoidSplitting) {
      relonturn addBytelons(bytelons, offselont, lelonngth, avoidSplitting);
    }

    @Ovelonrridelon
    public void writelonBytelons(bytelon[] b, int offselont, int lelonngth) throws IOelonxcelonption {
      addBytelons(b, offselont, lelonngth);
    }

    @Ovelonrridelon
    public void writelonBytelon(bytelon b) {
      addBytelon(b);
    }
  }

  /**
   * A DataInput-likelon intelonrfacelon for relonading "contiguous" data from a BytelonBlockPool.
   *
   * This is not threlonadsafelon.
   *
   * This doelons not fully implelonmelonnt thelon DataInput intelonrfacelon - its DataInput.relonadBytelons melonthod throws
   * UnsupportelondOpelonrationelonxcelonption beloncauselon this class providelons a facility for no-copy relonading.
   */
  public final class RelonadStrelonam elonxtelonnds DataInput {
    privatelon int offselont;

    privatelon RelonadStrelonam(int offselont) {
      this.offselont = offselont;
    }

    public BytelonsRelonf relonadBytelons(int n) {
      relonturn relonadBytelons(n, falselon);
    }

    /**
     * relonad n bytelons that welonrelon writtelonn with a givelonn valuelon of avoidSplitting
     * @param n  numbelonr of bytelons to relonad.
     * @param avoidSplitting  this should belon thelon samelon that was uselond at writelonBytelons timelon.
     * @relonturn  a relonfelonrelonncelon to thelon bytelons relonad or null.
     */
    public BytelonsRelonf relonadBytelons(int n, boolelonan avoidSplitting) {
      int currelonntBuffelonr = offselont >>> BytelonBlockPool.BYTelon_BLOCK_SHIFT;
      int currelonntOffselont = offselont & BytelonBlockPool.BYTelon_BLOCK_MASK;
      if (avoidSplitting && n < BytelonBlockPool.BYTelon_BLOCK_SIZelon
          && currelonntOffselont + n > BytelonBlockPool.BYTelon_BLOCK_SIZelon) {
        ++currelonntBuffelonr;
        currelonntOffselont = 0;
        offselont = currelonntBuffelonr << BytelonBlockPool.BYTelon_BLOCK_SHIFT;
      }
      BytelonsRelonf relonsult = gelontBytelons(offselont, n);
      this.offselont += n;
      relonturn relonsult;
    }

    @Ovelonrridelon
    public bytelon relonadBytelon() {
      relonturn gelontBytelon(offselont++);
    }

    @Ovelonrridelon
    public void relonadBytelons(bytelon[] b, int off, int lelonn) throws IOelonxcelonption {
      throw nelonw UnsupportelondOpelonrationelonxcelonption("Uselon thelon no-copielons velonrsion of RelonadBytelons instelonad.");
    }
  }
}
