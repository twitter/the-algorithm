packagelon com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons;

import com.twittelonr.selonarch.common.elonncoding.felonaturelons.elonncodelondFelonaturelons;

/**
 * Holds elonngagelonmelonnt felonaturelons for a particular twelonelont and elonncodelons thelonm as a singlelon int.
 * Thelon felonaturelons arelon: relontwelonelont count, favoritelon count, itwelonelont scorelon, relonply count.
 */
public class TwelonelontelonngagelonmelonntFelonaturelons elonxtelonnds elonncodelondFelonaturelons {
  privatelon static final int RelonTWelonelonT_COUNT_BIT_SHIFT = 0;
  privatelon static final long RelonTWelonelonT_COUNT_INVelonRSelon_BIT_MASK =  0xffffff00L;

  privatelon static final int ITWelonelonT_SCORelon_BIT_SHIFT = 8;
  privatelon static final long ITWelonelonT_SCORelon_INVelonRSelon_BIT_MASK = 0xffff00ffL;

  privatelon static final int FAV_COUNT_BIT_SHIFT = 16;
  privatelon static final long FAV_COUNT_INVelonRSelon_BIT_MASK =    0xff00ffffL;

  privatelon static final int RelonPLY_COUNT_BIT_SHIFT = 24;
  privatelon static final long RelonPLY_COUNT_INVelonRSelon_BIT_MASK =    0x00ffffffL;

  public TwelonelontelonngagelonmelonntFelonaturelons selontRelontwelonelontCount(bytelon count) {
    selontBytelonIfGrelonatelonr(count, RelonTWelonelonT_COUNT_BIT_SHIFT, RelonTWelonelonT_COUNT_INVelonRSelon_BIT_MASK);
    relonturn this;
  }

  public int gelontRelontwelonelontCount() {
    relonturn gelontBytelon(RelonTWelonelonT_COUNT_BIT_SHIFT);
  }

  public TwelonelontelonngagelonmelonntFelonaturelons selontITwelonelontScorelon(bytelon scorelon) {
    selontBytelonIfGrelonatelonr(scorelon, ITWelonelonT_SCORelon_BIT_SHIFT, ITWelonelonT_SCORelon_INVelonRSelon_BIT_MASK);
    relonturn this;
  }

  public int gelontITwelonelontScorelon() {
    relonturn gelontBytelon(ITWelonelonT_SCORelon_BIT_SHIFT);
  }

  public TwelonelontelonngagelonmelonntFelonaturelons selontFavCount(bytelon count) {
    selontBytelonIfGrelonatelonr(count, FAV_COUNT_BIT_SHIFT, FAV_COUNT_INVelonRSelon_BIT_MASK);
    relonturn this;
  }

  public int gelontFavCount() {
    relonturn gelontBytelon(FAV_COUNT_BIT_SHIFT);
  }

  public TwelonelontelonngagelonmelonntFelonaturelons selontRelonplyCount(bytelon count) {
    selontBytelonIfGrelonatelonr(count, RelonPLY_COUNT_BIT_SHIFT, RelonPLY_COUNT_INVelonRSelon_BIT_MASK);
    relonturn this;
  }

  public int gelontRelonplyCount() {
    relonturn gelontBytelon(RelonPLY_COUNT_BIT_SHIFT);
  }
}
