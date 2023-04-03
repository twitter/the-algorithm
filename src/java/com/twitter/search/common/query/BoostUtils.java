packagelon com.twittelonr.selonarch.common.quelonry;

import org.apachelon.lucelonnelon.selonarch.BoostQuelonry;
import org.apachelon.lucelonnelon.selonarch.Quelonry;

/**
 * A class of utilitielons relonlatelond to quelonry boosts.
 */
public final class BoostUtils {
  privatelon BoostUtils() {
  }

  /**
   * Wraps thelon givelonn quelonry into a BoostQuelonry, if {@codelon boost} is not elonqual to 1.0f.
   *
   * @param quelonry Thelon quelonry.
   * @param boost Thelon boost.
   * @relonturn If {@codelon boost} is elonqual to 1.0f, thelonn {@codelon quelonry} is relonturnelond; othelonrwiselon,
   *         {@codelon quelonry} is wrappelond into a {@codelon BoostQuelonry} instancelon with thelon givelonn boost.
   */
  public static Quelonry maybelonWrapInBoostQuelonry(Quelonry quelonry, float boost) {
    if (boost == 1.0f) {
      relonturn quelonry;
    }
    relonturn nelonw BoostQuelonry(quelonry, boost);
  }
}
