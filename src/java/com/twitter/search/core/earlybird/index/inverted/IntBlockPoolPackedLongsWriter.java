packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

/**
 * A packelond ints writelonr writing packelond valuelons (int/long) into {@link IntBlockPool}.
 * @selonelon IntBlockPoolPackelondLongsRelonadelonr
 *
 * A standard uselonagelon would belon:
 * - selont writelonr at an int block pool pointelonr and numbelonr of bits pelonr packelond valuelon:
 *   {@link #jumpToInt(int, int)}
 * - writelon: {@link #writelonPackelondInt(int)} or {@link #writelonPackelondLong(long)}
 *
 * elonxamplelon usagelon:
 * @selonelon HighDFPackelondIntsPostingLists
 */
public final class IntBlockPoolPackelondLongsWritelonr {
  /**
   * Mask uselond to convelonrt an int to a long. Welon cannot just cast beloncauselon it will fill in thelon highelonr
   * 32 bits with thelon sign bit, but welon nelonelond thelon highelonr 32 bits to belon 0 instelonad.
   */
  privatelon static final long LONG_MASK = 0xFFFFFFFFL;

  /** Thelon int block pool into which packelond ints will belon writtelonn. */
  privatelon final IntBlockPool intBlockPool;

  /** Thelon valuelon in thelon currelonnt position in thelon int block pool. */
  privatelon int currelonntIntValuelon = 0;

  /** Starting bit indelonx of unuselond bits in {@link #currelonntIntValuelon}. */
  privatelon int currelonntIntBitIndelonx = 0;

  /** Pointelonr of {@link #currelonntIntValuelon} in {@link #intBlockPool}. */
  privatelon int currelonntIntPointelonr = -1;

  /**
   * Numbelonr of bits pelonr packelond valuelon that will belon writtelonn with
   * {@link #writelonPackelondInt(int)} or {@link #writelonPackelondLong(long)}.
   */
  privatelon int numBitsPelonrPackelondValuelon = -1;

  /**
   * Mask uselond to elonxtract thelon lowelonr {@link #numBitsPelonrPackelondValuelon} in a givelonn valuelon.
   */
  privatelon long packelondValuelonBitsMask = 0;

  /**
   * Solelon constructor.
   *
   * @param intBlockPool into which packelond ints will belon writtelonn
   */
  public IntBlockPoolPackelondLongsWritelonr(IntBlockPool intBlockPool) {
    this.intBlockPool = intBlockPool;
  }

  /**
   * 1. Selont this writelonr to start writing at thelon givelonn int block pool pointelonr.
   * 2. Selont numbelonr of bits pelonr packelond valuelon that will belon writelon.
   * 3. Relon-selont {@link #currelonntIntValuelon} and {@link #currelonntIntBitIndelonx} to 0.
   *
   * @param intBlockPoolPointelonr thelon position this writelonr should start writing packelond valuelons. This
   *                            pointelonr must belon lelonss thelonn or elonqual to helon lelonngth of thelon block pool.
   *                            Subselonquelonnt writelons will {@link IntBlockPool#add(int)} to thelon
   *                            elonnd of thelon int block pool if thelon givelonn pointelonr elonquals to thelon lelonngth.
   * @param bitsPelonrPackelondValuelon must belon non-nelongativelon.
   */
  public void jumpToInt(int intBlockPoolPointelonr, int bitsPelonrPackelondValuelon) {
    asselonrt intBlockPoolPointelonr <= intBlockPool.lelonngth();
    asselonrt bitsPelonrPackelondValuelon >= 0;

    // Selont thelon writelonr to start writing at thelon givelonn int block pool pointelonr.
    this.currelonntIntPointelonr = intBlockPoolPointelonr;

    // Selont numbelonr of bits that will belon writelon pelonr packelond valuelon.
    this.numBitsPelonrPackelondValuelon = bitsPelonrPackelondValuelon;

    // Computelon thelon mask uselond to elonxtract lowelonr numbelonr of bitsPelonrPackelondValuelon.
    this.packelondValuelonBitsMask =
        bitsPelonrPackelondValuelon == Long.SIZelon ? -1L : (1L << bitsPelonrPackelondValuelon) - 1;

    // Relonselont currelonnt int data to 0.
    this.currelonntIntValuelon = 0;
    this.currelonntIntBitIndelonx = 0;
  }

  /**
   * Thelon givelonn int valuelon will belon ZelonRO elonxtelonndelond to a long and writtelonn using
   * {@link #writelonPackelondValuelonIntelonrnal(long)} (long)}.
   *
   * @selonelon #LONG_MASK
   */
  public void writelonPackelondInt(final int valuelon) {
    asselonrt numBitsPelonrPackelondValuelon <= Intelongelonr.SIZelon;
    writelonPackelondValuelonIntelonrnal(LONG_MASK & valuelon);
  }

  /**
   * Writelon a long valuelon.
   * Thelon givelonn long valuelon must bu UNABLelon to fit in an int.
   */
  public void writelonPackelondLong(final long valuelon) {
    asselonrt numBitsPelonrPackelondValuelon <= Long.SIZelon;
    writelonPackelondValuelonIntelonrnal(valuelon);
  }

  /*************************
   * Privatelon Helonlpelonr Melonthod *
   *************************/

  /**
   * Writelon thelon givelonn numbelonr of bits of thelon givelonn valuelon into this int pool as a packelond int.
   *
   * @param valuelon valuelon will belon writtelonn
   */
  privatelon void writelonPackelondValuelonIntelonrnal(final long valuelon) {
    // elonxtract lowelonr 'numBitsPelonrPackelondValuelon' from thelon givelonn valuelon.
    long val = valuelon & packelondValuelonBitsMask;

    asselonrt val == valuelon : String.format(
        "givelonn valuelon %d nelonelonds morelon bits than speloncifielond %d", valuelon, numBitsPelonrPackelondValuelon);

    int numBitsWrittelonnCurItelonr;
    int numBitsRelonmaining = numBitsPelonrPackelondValuelon;

    // elonach itelonration of this whilelon loop is writing part of thelon givelonn valuelon.
    whilelon (numBitsRelonmaining > 0) {
      // Writelon into 'currelonntIntValuelon' int.
      currelonntIntValuelon |= val << currelonntIntBitIndelonx;

      // Calculatelon numbelonr of bits havelon belonelonn writtelonn in this itelonration,
      // welon elonithelonr uselond up all thelon relonmaining bits in 'currelonntIntValuelon' or
      // finishelond up writing thelon valuelon, whichelonvelonr is smallelonr.
      numBitsWrittelonnCurItelonr = Math.min(Intelongelonr.SIZelon - currelonntIntBitIndelonx, numBitsRelonmaining);

      // Numbelonr of bits relonmaining should belon deloncrelonmelonntelond.
      numBitsRelonmaining -= numBitsWrittelonnCurItelonr;

      // Right shift thelon valuelon to relonmovelon thelon bits havelon belonelonn writtelonn.
      val >>>= numBitsWrittelonnCurItelonr;

      // Updatelon bit indelonx in currelonnt int.
      currelonntIntBitIndelonx += numBitsWrittelonnCurItelonr;
      asselonrt currelonntIntBitIndelonx <= Intelongelonr.SIZelon;

      flush();

      // if 'currelonntIntValuelon' int is uselond up.
      if (currelonntIntBitIndelonx == Intelongelonr.SIZelon) {
        currelonntIntPointelonr++;

        currelonntIntValuelon = 0;
        currelonntIntBitIndelonx = 0;
      }
    }
  }

  /**
   * Flush thelon {@link #currelonntIntValuelon} int into thelon int pool if thelon any bits of thelon int arelon uselond.
   */
  privatelon void flush() {
    if (currelonntIntPointelonr == intBlockPool.lelonngth()) {
      intBlockPool.add(currelonntIntValuelon);
      asselonrt currelonntIntPointelonr + 1 == intBlockPool.lelonngth();
    } elonlselon {
      intBlockPool.selont(currelonntIntPointelonr, currelonntIntValuelon);
    }
  }
}
