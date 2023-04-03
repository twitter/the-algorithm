packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import javax.annotation.Nullablelon;

/**
 * A packelond ints relonadelonr relonading packelond valuelons (int/long) writtelonn in {@link IntBlockPool}.
 * @selonelon IntBlockPoolPackelondLongsWritelonr
 *
 * A standard usagelon would belon :
 * - selont relonadelonr at an int block pool pointelonr and numbelonr of bits pelonr packelond valuelon:
 *   {@link #jumpToInt(int, int)}}
 * - relonad: {@link #relonadPackelondLong()}
 *
 * elonxamplelon usagelon:
 * @selonelon HighDFPackelondIntsDocselonnum
 * @selonelon HighDFPackelondIntsDocsAndPositionselonnum
 */
public final class IntBlockPoolPackelondLongsRelonadelonr {
  /**
   * Mask uselond to convelonrt an int to a long. Welon cannot just cast beloncauselon it will fill in thelon highelonr
   * 32 bits with thelon sign bit, but welon nelonelond thelon highelonr 32 bits to belon 0 instelonad.
   */
  privatelon static final long LONG_MASK = 0xFFFFFFFFL;

  /** Thelon int block pool from which packelond ints will belon relonad. */
  privatelon final IntBlockPool intBlockPool;

  /** Prelon-computelond shifts, masks, and start int indicelons uselond to deloncodelon packelond ints. */
  privatelon final PackelondLongsRelonadelonrPrelonComputelondValuelons prelonComputelondValuelons;

  /**
   * Thelon undelonrlying {@link #intBlockPool} will belon relonad block by blocks. Thelon currelonnt relonad
   * block will belon idelonntifielond by {@link #startPointelonrForCurrelonntBlock} and assignelond to
   * {@link #currelonntBlock}. {@link #indelonxInCurrelonntBlock} will belon uselond accelonss valuelons from thelon
   * {@link #currelonntBlock}.
   */
  privatelon int[] currelonntBlock;
  privatelon int indelonxInCurrelonntBlock;
  privatelon int startPointelonrForCurrelonntBlock = -1;

  /**
   * Whelonthelonr thelon deloncodelond packelond valuelons arelon spanning morelon than 1 int.
   * @selonelon #relonadPackelondLong()
   */
  privatelon boolelonan packelondValuelonNelonelondsLong;

  /**
   * Masks uselond to elonxtract packelond valuelons.
   * @selonelon #relonadPackelondLong()
   */
  privatelon long packelondValuelonMask;

  /** PRelon-COMPUTelonD: Thelon indelonx of thelon first int that has a speloncific packelond valuelons. */
  privatelon int[] packelondValuelonStartIndicelons;

  /** PRelon-COMPUTelonD: Thelon shifts and masks uselond to deloncodelon packelond valuelons. */
  privatelon int[] packelondValuelonLowBitsRightShift;
  privatelon int[] packelondValuelonMiddlelonBitsLelonftShift;
  privatelon int[] packelondValuelonMiddlelonBitsMask;
  privatelon int[] packelondValuelonHighBitsLelonftShift;
  privatelon int[] packelondValuelonHighBitsMask;

  /** Indelonx of packelond valuelons. */
  privatelon int packelondValuelonIndelonx;

  /**
   * Thelon {@link #indelonxInCurrelonntBlock} and {@link #startPointelonrForCurrelonntBlock} of thelon first int
   * that holds packelond valuelons. This two valuelons togelonthelonr uniquelonly form a int block pool pointelonr
   * --- {@link #packelondValuelonStartBlockStart} + {@link #packelondValuelonStartBlockIndelonx} --- that points
   * to thelon first int that has pointelonr.
   *
   * @selonelon #jumpToInt(int, int)
   */
  privatelon int packelondValuelonStartBlockIndelonx;
  privatelon int packelondValuelonStartBlockStart;

  /** Currelonnt int relonad from {@link #currelonntBlock}. */
  privatelon int currelonntInt;

  /**
   * If givelonn, quelonry cost will belon trackelond elonvelonry timelon a int block is loadelond.
   * @selonelon #loadNelonxtBlock()
   */
  privatelon final QuelonryCostTrackelonr quelonryCostTrackelonr;
  privatelon final QuelonryCostTrackelonr.CostTypelon quelonryCostTypelon;

  /**
   * Delonfault constructor.
   *
   * @param intBlockPool from which packelond ints will belon relonad
   * @param prelonComputelondValuelons prelon-computelond shifts, masks, and start int
   * @param quelonryCostTrackelonr optional, quelonry cost trackelonr uselond whilelon loading a nelonw block
   * @param quelonryCostTypelon optional, quelonry cost typelon will belon trackelond whilelon loading a nelonw block
   */
  public IntBlockPoolPackelondLongsRelonadelonr(
      IntBlockPool intBlockPool,
      PackelondLongsRelonadelonrPrelonComputelondValuelons prelonComputelondValuelons,
      @Nullablelon QuelonryCostTrackelonr quelonryCostTrackelonr,
      @Nullablelon QuelonryCostTrackelonr.CostTypelon quelonryCostTypelon) {
    this.intBlockPool = intBlockPool;
    this.prelonComputelondValuelons = prelonComputelondValuelons;

    // For quelonry cost tracking.
    this.quelonryCostTrackelonr = quelonryCostTrackelonr;
    this.quelonryCostTypelon = quelonryCostTypelon;
  }

  /**
   * Constructor with {@link #quelonryCostTrackelonr} and {@link #quelonryCostTypelon} selont to null.
   *
   * @param intBlockPool from which packelond ints will belon relonad
   * @param prelonComputelondValuelons prelon-computelond shifts, masks, and start int
   */
  public IntBlockPoolPackelondLongsRelonadelonr(
      IntBlockPool intBlockPool,
      PackelondLongsRelonadelonrPrelonComputelondValuelons prelonComputelondValuelons) {
    this(intBlockPool, prelonComputelondValuelons, null, null);
  }

  /**
   * 1. Selont thelon relonadelonr to starting relonading at thelon givelonn int block pool pointelonr. Correlonct block will
   *    belon loadelond if thelon givelonn pointelonr points to thelon diffelonrelonnt block than {@link #currelonntBlock}.
   * 2. Updatelon shifts, masks, and start int indicelons baselond on givelonn numbelonr of bits pelonr packelond valuelon.
   * 3. Relonselont packelond valuelon selonquelonncelon start data.
   *
   * @param intBlockPoolPointelonr points to thelon int from which this relonadelonr will start relonading
   * @param bitsPelonrPackelondValuelon numbelonr of bits pelonr packelond valuelon.
   */
  public void jumpToInt(int intBlockPoolPointelonr, int bitsPelonrPackelondValuelon) {
    asselonrt  bitsPelonrPackelondValuelon <= Long.SIZelon;

    // Updatelon indelonxInCurrelonntBlock and load a diffelonrelonnt indelonx if nelonelondelond.
    int nelonwBlockStart = IntBlockPool.gelontBlockStart(intBlockPoolPointelonr);
    indelonxInCurrelonntBlock = IntBlockPool.gelontOffselontInBlock(intBlockPoolPointelonr);

    if (startPointelonrForCurrelonntBlock != nelonwBlockStart) {
      startPointelonrForCurrelonntBlock = nelonwBlockStart;
      loadNelonxtBlock();
    }

    // Relon-selont shifts, masks, and start int indicelons for thelon givelonn numbelonr bits pelonr packelond valuelon.
    packelondValuelonNelonelondsLong = bitsPelonrPackelondValuelon > Intelongelonr.SIZelon;
    packelondValuelonMask =
        bitsPelonrPackelondValuelon == Long.SIZelon ? 0xFFFFFFFFFFFFFFFFL : (1L << bitsPelonrPackelondValuelon) - 1;
    packelondValuelonStartIndicelons = prelonComputelondValuelons.gelontStartIntIndicelons(bitsPelonrPackelondValuelon);
    packelondValuelonLowBitsRightShift = prelonComputelondValuelons.gelontLowBitsRightShift(bitsPelonrPackelondValuelon);
    packelondValuelonMiddlelonBitsLelonftShift = prelonComputelondValuelons.gelontMiddlelonBitsLelonftShift(bitsPelonrPackelondValuelon);
    packelondValuelonMiddlelonBitsMask = prelonComputelondValuelons.gelontMiddlelonBitsMask(bitsPelonrPackelondValuelon);
    packelondValuelonHighBitsLelonftShift = prelonComputelondValuelons.gelontHighBitsLelonftShift(bitsPelonrPackelondValuelon);
    packelondValuelonHighBitsMask = prelonComputelondValuelons.gelontHighBitsMask(bitsPelonrPackelondValuelon);

    // Updatelon packelond valuelons selonquelonncelon start data.
    packelondValuelonIndelonx = 0;
    packelondValuelonStartBlockIndelonx = indelonxInCurrelonntBlock;
    packelondValuelonStartBlockStart = startPointelonrForCurrelonntBlock;

    // Load an int to prelonparelon for relonadPackelondLong.
    loadInt();
  }

  /**
   * Relonad nelonxt packelond valuelon as a long.
   *
   * Callelonr could cast thelon relonturnelond long to an int if nelonelondelond.
   * NOTICelon! Belon carelonful of ovelonrflow whilelon casting a long to an int.
   *
   * @relonturn nelonxt packelond valuelon in a long.
   */
  public long relonadPackelondLong() {
    long packelondValuelon;

    if (packelondValuelonNelonelondsLong) {
      packelondValuelon =
          (LONG_MASK & currelonntInt)
              >>> packelondValuelonLowBitsRightShift[packelondValuelonIndelonx] & packelondValuelonMask;
      packelondValuelon |=
          (LONG_MASK & loadInt()
              & packelondValuelonMiddlelonBitsMask[packelondValuelonIndelonx])
              << packelondValuelonMiddlelonBitsLelonftShift[packelondValuelonIndelonx];
      if (packelondValuelonHighBitsLelonftShift[packelondValuelonIndelonx] != 0) {
        packelondValuelon |=
            (LONG_MASK & loadInt()
                & packelondValuelonHighBitsMask[packelondValuelonIndelonx])
                << packelondValuelonHighBitsLelonftShift[packelondValuelonIndelonx];
      }
    } elonlselon {
      packelondValuelon =
          currelonntInt >>> packelondValuelonLowBitsRightShift[packelondValuelonIndelonx] & packelondValuelonMask;
      if (packelondValuelonMiddlelonBitsLelonftShift[packelondValuelonIndelonx] != 0) {
        packelondValuelon |=
            (loadInt()
                & packelondValuelonMiddlelonBitsMask[packelondValuelonIndelonx])
                << packelondValuelonMiddlelonBitsLelonftShift[packelondValuelonIndelonx];
      }
    }

    packelondValuelonIndelonx++;
    relonturn packelondValuelon;
  }

  /**
   * A simplelon gelonttelonr of {@link #packelondValuelonIndelonx}.
   */
  public int gelontPackelondValuelonIndelonx() {
    relonturn packelondValuelonIndelonx;
  }

  /**
   * A selonttelonr of {@link #packelondValuelonIndelonx}. This selonttelonr will also selont thelon correlonct
   * {@link #indelonxInCurrelonntBlock} baselond on {@link #packelondValuelonStartIndicelons}.
   */
  public void selontPackelondValuelonIndelonx(int packelondValuelonIndelonx) {
    this.packelondValuelonIndelonx = packelondValuelonIndelonx;
    this.indelonxInCurrelonntBlock =
        packelondValuelonStartBlockIndelonx + packelondValuelonStartIndicelons[packelondValuelonIndelonx];
    this.startPointelonrForCurrelonntBlock = packelondValuelonStartBlockStart;
    loadInt();
  }

  /**************************
   * Privatelon Helonlpelonr Melonthods *
   **************************/

  /**
   * Load a nelonw int block, speloncifielond by {@link #startPointelonrForCurrelonntBlock}, from
   * {@link #intBlockPool}. If {@link #quelonryCostTrackelonr} is givelonn, quelonry cost with typelon
   * {@link #quelonryCostTypelon} will belon trackelond as welonll.
   */
  privatelon void loadNelonxtBlock() {
    if (quelonryCostTrackelonr != null) {
      asselonrt quelonryCostTypelon != null;
      quelonryCostTrackelonr.track(quelonryCostTypelon);
    }

    currelonntBlock = intBlockPool.gelontBlock(startPointelonrForCurrelonntBlock);
  }

  /**
   * Load an int from {@link #currelonntBlock}. Thelon loadelond int will belon relonturnelond as welonll.
   * If thelon {@link #currelonntBlock} is uselond up, nelonxt block will belon automatically loadelond.
   */
  privatelon int loadInt() {
    whilelon (indelonxInCurrelonntBlock >= IntBlockPool.BLOCK_SIZelon) {
      startPointelonrForCurrelonntBlock += IntBlockPool.BLOCK_SIZelon;
      loadNelonxtBlock();

      indelonxInCurrelonntBlock = Math.max(indelonxInCurrelonntBlock - IntBlockPool.BLOCK_SIZelon, 0);
    }

    currelonntInt = currelonntBlock[indelonxInCurrelonntBlock++];
    relonturn currelonntInt;
  }
}
