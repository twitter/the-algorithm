package com.twitter.search.core.earlybird.indetwitter.inverted;

/**
 * Pre-computed shifts, mask, and start int indices used by
 * {@link IntBlockPoolPackedLongsReader} to decode packed values from
 * {@link IntBlockPool}.
 *
 * The purpose of this class is for decoding efficiency and speed. This class is thread-safe since
 * all its usages are read-only.
 *
 * Packed ints are stored from LOWEST bits for HIGHEST bits in an int.
 *
 * Here are 3 different situations when a packed value spans 1, 2, and 3 ints:
 *
 * - A packed value spans 1 int:
 *            [High Bits ................................. Low Bits]
 *   int[n] = [possible_other_data|packed_value|possible_other_data]
 *
 *   To decode, 1 shift right and 1 mask are needed:
 *     * shift - {@link #allLowBitsRightShift}
 *     * mask - dynamically computed based on bitsPerValue (in decoded slice).
 *
 * - A packed value spans 2 ints:
 *   The data is stored as:
 *              [High Bits .................. Low Bits]
 *   int[n]   = [low_bits_of_packed_value | other_data]
 *   int[n+1] = [other_data| high_bits_of_packed_value]
 *
 *   To decode, 1 shift right, 1 shift left, and 2 masks are needed:
 *     * 1 shift right {@link #allLowBitsRightShift} and 1 mask (computed on the fly) to compute
 *       low_bits_of_packed_value
 *     * 1 mask {@link #allMiddleBitsMask} and 1 shift left {@link #allMiddleBitsLeftShift} to
 *       compute high_bits_of_packed_value
 *     * 1 OR to combine `high_bits_of_packed_value | low_bits_of_packed_value`
 *
 * - A packed value spans 3 ints:
 *   The data is stored as:
 *              [High Bits .................. Low Bits]
 *   int[n]   = [low_bits_of_packed_value | other_data]
 *   int[n+1] = [ ... middle_bits_of_packed_value ... ]
 *   int[n+2] = [other_data| high_bits_of_packed_value]
 *
 *   To decode, 1 shift right, 2 shift left, and 3 masks are needed:
 *     * 1 shift right {@link #allLowBitsRightShift} and 1 mask (computed on the fly) to compute
 *       low_bits_of_packed_value
 *     * 1 shift left {@link #allMiddleBitsLeftShift} and 1 mask {@link #allMiddleBitsMask} to
 *       compute middle_bits_of_data
 *     * 1 shift left {@link #allHighBitsLeftShift} and 1 mask {@link #allHighBitsMask} to compute
 *       high_bits_of_data
 *     * 1 OR to combine `low_bits_of_data | middle_bits_of_data | high_bits_of_data`
 *
 * Etwitterample usage:
 * @see HighDFPackedIntsDocsEnum
 * @see HighDFPackedIntsDocsAndPositionsEnum
 */
public final class PackedLongsReaderPreComputedValues {
  private final int[][] allLowBitsRightShift;
  private final int[][] allMiddleBitsLeftShift;
  private final int[][] allMiddleBitsMask;
  private final int[][] allHighBitsLeftShift;
  private final int[][] allHighBitsMask;

  /**
   * 2D int arrays containing pre-computed start int indices; the 2 dimensions are
   * int[numBitsPerPackedValue][packedValueIndetwitter].
   *
   * For a given number bits per packed value and a given packed value indetwitter, this is the first
   * int in the subsequent of ints that contains the packed value with the given packed value indetwitter.
   */
  private final int[][] allStartIntIndices;

  /**
   * Sole constructor.
   *
   * @param matwitterBitsPerValue matwitter possible number of bits of packed values that will be decoded
   * @param matwitterNumValues matwitter number of values are encoded back to back
   * @param matwitterNumInts matwitter number of ints are used to store packed values
   * @param needStartIntIndetwitter for optimization: whether start int indices are needed
   */
  PackedLongsReaderPreComputedValues(
      int matwitterBitsPerValue,
      int matwitterNumValues,
      int matwitterNumInts,
      boolean needStartIntIndetwitter) {
    assert matwitterBitsPerValue <= Long.SIZE;

    if (needStartIntIndetwitter) {
      this.allStartIntIndices = new int[matwitterBitsPerValue + 1][matwitterNumValues];
    } else {
      this.allStartIntIndices = null;
    }

    this.allLowBitsRightShift = new int[matwitterBitsPerValue + 1][matwitterNumValues];
    this.allMiddleBitsLeftShift = new int[matwitterBitsPerValue + 1][matwitterNumValues];
    this.allMiddleBitsMask = new int[matwitterBitsPerValue + 1][matwitterNumValues];

    // Packed value could use up 2 ints.
    if (matwitterBitsPerValue > Integer.SIZE) {
      this.allHighBitsLeftShift = new int[matwitterBitsPerValue + 1][matwitterNumValues];
      this.allHighBitsMask = new int[matwitterBitsPerValue + 1][matwitterNumValues];
    } else {
      this.allHighBitsLeftShift = null;
      this.allHighBitsMask = null;
    }

    compute(matwitterBitsPerValue, matwitterNumValues, matwitterNumInts);
  }

  /**
   * Compute masks, shifts and start indices.
   */
  private void compute(int matwitterBitsPerValue, int matwitterNumValues, int matwitterNumInts) {
    // For each possible bits per packed value.
    for (int bitsPerPackedValue = 0; bitsPerPackedValue <= matwitterBitsPerValue; bitsPerPackedValue++) {
      int[] startIntIndices =
          allStartIntIndices != null ? allStartIntIndices[bitsPerPackedValue] : null;
      int[] lowBitsRightShift =
          allLowBitsRightShift[bitsPerPackedValue];
      int[] middleBitsLeftShift =
          allMiddleBitsLeftShift[bitsPerPackedValue];
      int[] middleBitsMask =
          allMiddleBitsMask[bitsPerPackedValue];
      int[] highBitsLeftShift =
          allHighBitsLeftShift != null ? allHighBitsLeftShift[bitsPerPackedValue] : null;
      int[] highBitsMask =
          allHighBitsMask != null ? allHighBitsMask[bitsPerPackedValue] : null;

      int shift = 0;
      int currentIntIndetwitter = 0;
      int bitsRead;
      int bitsRemaining;

      // For each packed value.
      for (int packedValueIndetwitter = 0; packedValueIndetwitter < matwitterNumValues; packedValueIndetwitter++) {
        if (startIntIndices != null) {
          startIntIndices[packedValueIndetwitter] = currentIntIndetwitter;
        }
        // Packed value spans to the 1st int.
        lowBitsRightShift[packedValueIndetwitter] = shift;
        bitsRead = Integer.SIZE - shift;
        bitsRemaining = bitsPerPackedValue - bitsRead;

        if (bitsRemaining >= 0) {
          // Packed value spans to the 2nd int.
          currentIntIndetwitter++;
          if (currentIntIndetwitter == matwitterNumInts) {
            break;
          }
          middleBitsLeftShift[packedValueIndetwitter] = bitsRead;
          middleBitsMask[packedValueIndetwitter] =
              bitsRemaining >= Integer.SIZE ? 0twitterFFFFFFFF : (1 << bitsRemaining) - 1;

          // Packed value spans to the 3rd int.
          bitsRead += Integer.SIZE;
          bitsRemaining -= Integer.SIZE;
          if (bitsRemaining >= 0) {
            currentIntIndetwitter++;
            if (currentIntIndetwitter == matwitterNumInts) {
              break;
            }
            assert highBitsLeftShift != null;
            assert highBitsMask != null;
            highBitsLeftShift[packedValueIndetwitter] = bitsRead;
            highBitsMask[packedValueIndetwitter] =
                bitsRemaining >= Integer.SIZE ? 0twitterFFFFFFFF : (1 << bitsRemaining) - 1;
          }
        }

        shift += bitsPerPackedValue;
        shift = shift % Integer.SIZE;
      }
    }
  }

  /********************************************************************
   * Getters of Pre-computed Values: returns should NEVER be modified *
   ********************************************************************/

  int[] getStartIntIndices(int numBitsPerValue) {
    return allStartIntIndices == null ? null : allStartIntIndices[numBitsPerValue];
  }

  int[] getLowBitsRightShift(int numBitsPerValue) {
    return allLowBitsRightShift[numBitsPerValue];
  }

  int[] getMiddleBitsLeftShift(int numBitsPerValue) {
    return allMiddleBitsLeftShift[numBitsPerValue];
  }

  int[] getMiddleBitsMask(int numBitsPerValue) {
    return allMiddleBitsMask[numBitsPerValue];
  }

  int[] getHighBitsLeftShift(int numBitsPerValue) {
    return allHighBitsLeftShift == null ? null : allHighBitsLeftShift[numBitsPerValue];
  }

  int[] getHighBitsMask(int numBitsPerValue) {
    return allHighBitsMask == null ? null : allHighBitsMask[numBitsPerValue];
  }
}
