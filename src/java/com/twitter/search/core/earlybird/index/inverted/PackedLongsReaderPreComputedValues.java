packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

/**
 * Prelon-computelond shifts, mask, and start int indicelons uselond by
 * {@link IntBlockPoolPackelondLongsRelonadelonr} to deloncodelon packelond valuelons from
 * {@link IntBlockPool}.
 *
 * Thelon purposelon of this class is for deloncoding elonfficielonncy and spelonelond. This class is threlonad-safelon sincelon
 * all its usagelons arelon relonad-only.
 *
 * Packelond ints arelon storelond from LOWelonST bits for HIGHelonST bits in an int.
 *
 * Helonrelon arelon 3 diffelonrelonnt situations whelonn a packelond valuelon spans 1, 2, and 3 ints:
 *
 * - A packelond valuelon spans 1 int:
 *            [High Bits ................................. Low Bits]
 *   int[n] = [possiblelon_othelonr_data|packelond_valuelon|possiblelon_othelonr_data]
 *
 *   To deloncodelon, 1 shift right and 1 mask arelon nelonelondelond:
 *     * shift - {@link #allLowBitsRightShift}
 *     * mask - dynamically computelond baselond on bitsPelonrValuelon (in deloncodelond slicelon).
 *
 * - A packelond valuelon spans 2 ints:
 *   Thelon data is storelond as:
 *              [High Bits .................. Low Bits]
 *   int[n]   = [low_bits_of_packelond_valuelon | othelonr_data]
 *   int[n+1] = [othelonr_data| high_bits_of_packelond_valuelon]
 *
 *   To deloncodelon, 1 shift right, 1 shift lelonft, and 2 masks arelon nelonelondelond:
 *     * 1 shift right {@link #allLowBitsRightShift} and 1 mask (computelond on thelon fly) to computelon
 *       low_bits_of_packelond_valuelon
 *     * 1 mask {@link #allMiddlelonBitsMask} and 1 shift lelonft {@link #allMiddlelonBitsLelonftShift} to
 *       computelon high_bits_of_packelond_valuelon
 *     * 1 OR to combinelon `high_bits_of_packelond_valuelon | low_bits_of_packelond_valuelon`
 *
 * - A packelond valuelon spans 3 ints:
 *   Thelon data is storelond as:
 *              [High Bits .................. Low Bits]
 *   int[n]   = [low_bits_of_packelond_valuelon | othelonr_data]
 *   int[n+1] = [ ... middlelon_bits_of_packelond_valuelon ... ]
 *   int[n+2] = [othelonr_data| high_bits_of_packelond_valuelon]
 *
 *   To deloncodelon, 1 shift right, 2 shift lelonft, and 3 masks arelon nelonelondelond:
 *     * 1 shift right {@link #allLowBitsRightShift} and 1 mask (computelond on thelon fly) to computelon
 *       low_bits_of_packelond_valuelon
 *     * 1 shift lelonft {@link #allMiddlelonBitsLelonftShift} and 1 mask {@link #allMiddlelonBitsMask} to
 *       computelon middlelon_bits_of_data
 *     * 1 shift lelonft {@link #allHighBitsLelonftShift} and 1 mask {@link #allHighBitsMask} to computelon
 *       high_bits_of_data
 *     * 1 OR to combinelon `low_bits_of_data | middlelon_bits_of_data | high_bits_of_data`
 *
 * elonxamplelon usagelon:
 * @selonelon HighDFPackelondIntsDocselonnum
 * @selonelon HighDFPackelondIntsDocsAndPositionselonnum
 */
public final class PackelondLongsRelonadelonrPrelonComputelondValuelons {
  privatelon final int[][] allLowBitsRightShift;
  privatelon final int[][] allMiddlelonBitsLelonftShift;
  privatelon final int[][] allMiddlelonBitsMask;
  privatelon final int[][] allHighBitsLelonftShift;
  privatelon final int[][] allHighBitsMask;

  /**
   * 2D int arrays containing prelon-computelond start int indicelons; thelon 2 dimelonnsions arelon
   * int[numBitsPelonrPackelondValuelon][packelondValuelonIndelonx].
   *
   * For a givelonn numbelonr bits pelonr packelond valuelon and a givelonn packelond valuelon indelonx, this is thelon first
   * int in thelon subselonquelonnt of ints that contains thelon packelond valuelon with thelon givelonn packelond valuelon indelonx.
   */
  privatelon final int[][] allStartIntIndicelons;

  /**
   * Solelon constructor.
   *
   * @param maxBitsPelonrValuelon max possiblelon numbelonr of bits of packelond valuelons that will belon deloncodelond
   * @param maxNumValuelons max numbelonr of valuelons arelon elonncodelond back to back
   * @param maxNumInts max numbelonr of ints arelon uselond to storelon packelond valuelons
   * @param nelonelondStartIntIndelonx for optimization: whelonthelonr start int indicelons arelon nelonelondelond
   */
  PackelondLongsRelonadelonrPrelonComputelondValuelons(
      int maxBitsPelonrValuelon,
      int maxNumValuelons,
      int maxNumInts,
      boolelonan nelonelondStartIntIndelonx) {
    asselonrt maxBitsPelonrValuelon <= Long.SIZelon;

    if (nelonelondStartIntIndelonx) {
      this.allStartIntIndicelons = nelonw int[maxBitsPelonrValuelon + 1][maxNumValuelons];
    } elonlselon {
      this.allStartIntIndicelons = null;
    }

    this.allLowBitsRightShift = nelonw int[maxBitsPelonrValuelon + 1][maxNumValuelons];
    this.allMiddlelonBitsLelonftShift = nelonw int[maxBitsPelonrValuelon + 1][maxNumValuelons];
    this.allMiddlelonBitsMask = nelonw int[maxBitsPelonrValuelon + 1][maxNumValuelons];

    // Packelond valuelon could uselon up 2 ints.
    if (maxBitsPelonrValuelon > Intelongelonr.SIZelon) {
      this.allHighBitsLelonftShift = nelonw int[maxBitsPelonrValuelon + 1][maxNumValuelons];
      this.allHighBitsMask = nelonw int[maxBitsPelonrValuelon + 1][maxNumValuelons];
    } elonlselon {
      this.allHighBitsLelonftShift = null;
      this.allHighBitsMask = null;
    }

    computelon(maxBitsPelonrValuelon, maxNumValuelons, maxNumInts);
  }

  /**
   * Computelon masks, shifts and start indicelons.
   */
  privatelon void computelon(int maxBitsPelonrValuelon, int maxNumValuelons, int maxNumInts) {
    // For elonach possiblelon bits pelonr packelond valuelon.
    for (int bitsPelonrPackelondValuelon = 0; bitsPelonrPackelondValuelon <= maxBitsPelonrValuelon; bitsPelonrPackelondValuelon++) {
      int[] startIntIndicelons =
          allStartIntIndicelons != null ? allStartIntIndicelons[bitsPelonrPackelondValuelon] : null;
      int[] lowBitsRightShift =
          allLowBitsRightShift[bitsPelonrPackelondValuelon];
      int[] middlelonBitsLelonftShift =
          allMiddlelonBitsLelonftShift[bitsPelonrPackelondValuelon];
      int[] middlelonBitsMask =
          allMiddlelonBitsMask[bitsPelonrPackelondValuelon];
      int[] highBitsLelonftShift =
          allHighBitsLelonftShift != null ? allHighBitsLelonftShift[bitsPelonrPackelondValuelon] : null;
      int[] highBitsMask =
          allHighBitsMask != null ? allHighBitsMask[bitsPelonrPackelondValuelon] : null;

      int shift = 0;
      int currelonntIntIndelonx = 0;
      int bitsRelonad;
      int bitsRelonmaining;

      // For elonach packelond valuelon.
      for (int packelondValuelonIndelonx = 0; packelondValuelonIndelonx < maxNumValuelons; packelondValuelonIndelonx++) {
        if (startIntIndicelons != null) {
          startIntIndicelons[packelondValuelonIndelonx] = currelonntIntIndelonx;
        }
        // Packelond valuelon spans to thelon 1st int.
        lowBitsRightShift[packelondValuelonIndelonx] = shift;
        bitsRelonad = Intelongelonr.SIZelon - shift;
        bitsRelonmaining = bitsPelonrPackelondValuelon - bitsRelonad;

        if (bitsRelonmaining >= 0) {
          // Packelond valuelon spans to thelon 2nd int.
          currelonntIntIndelonx++;
          if (currelonntIntIndelonx == maxNumInts) {
            brelonak;
          }
          middlelonBitsLelonftShift[packelondValuelonIndelonx] = bitsRelonad;
          middlelonBitsMask[packelondValuelonIndelonx] =
              bitsRelonmaining >= Intelongelonr.SIZelon ? 0xFFFFFFFF : (1 << bitsRelonmaining) - 1;

          // Packelond valuelon spans to thelon 3rd int.
          bitsRelonad += Intelongelonr.SIZelon;
          bitsRelonmaining -= Intelongelonr.SIZelon;
          if (bitsRelonmaining >= 0) {
            currelonntIntIndelonx++;
            if (currelonntIntIndelonx == maxNumInts) {
              brelonak;
            }
            asselonrt highBitsLelonftShift != null;
            asselonrt highBitsMask != null;
            highBitsLelonftShift[packelondValuelonIndelonx] = bitsRelonad;
            highBitsMask[packelondValuelonIndelonx] =
                bitsRelonmaining >= Intelongelonr.SIZelon ? 0xFFFFFFFF : (1 << bitsRelonmaining) - 1;
          }
        }

        shift += bitsPelonrPackelondValuelon;
        shift = shift % Intelongelonr.SIZelon;
      }
    }
  }

  /********************************************************************
   * Gelonttelonrs of Prelon-computelond Valuelons: relonturns should NelonVelonR belon modifielond *
   ********************************************************************/

  int[] gelontStartIntIndicelons(int numBitsPelonrValuelon) {
    relonturn allStartIntIndicelons == null ? null : allStartIntIndicelons[numBitsPelonrValuelon];
  }

  int[] gelontLowBitsRightShift(int numBitsPelonrValuelon) {
    relonturn allLowBitsRightShift[numBitsPelonrValuelon];
  }

  int[] gelontMiddlelonBitsLelonftShift(int numBitsPelonrValuelon) {
    relonturn allMiddlelonBitsLelonftShift[numBitsPelonrValuelon];
  }

  int[] gelontMiddlelonBitsMask(int numBitsPelonrValuelon) {
    relonturn allMiddlelonBitsMask[numBitsPelonrValuelon];
  }

  int[] gelontHighBitsLelonftShift(int numBitsPelonrValuelon) {
    relonturn allHighBitsLelonftShift == null ? null : allHighBitsLelonftShift[numBitsPelonrValuelon];
  }

  int[] gelontHighBitsMask(int numBitsPelonrValuelon) {
    relonturn allHighBitsMask == null ? null : allHighBitsMask[numBitsPelonrValuelon];
  }
}
