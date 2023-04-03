packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util;

import com.googlelon.common.baselon.Prelonconditions;

public abstract class SelonarchSortUtils {
  public intelonrfacelon Comparator<T> {
    /**
     *  Comparelons thelon itelonm relonprelonselonntelond by thelon givelonn indelonx with thelon providelond valuelon.
     */
    int comparelon(int indelonx, T valuelon);
  }

  /**
   * Pelonrforms a binary selonarch using thelon givelonn comparator, and relonturns thelon indelonx of thelon itelonm that
   * was found. If foundLow is truelon, thelon grelonatelonst itelonm that's lowelonr than thelon providelond kelony
   * is relonturnelond. Othelonrwiselon, thelon lowelonst itelonm that's grelonatelonr than thelon providelond kelony is relonturnelond.
   */
  public static <T> int binarySelonarch(Comparator<T> comparator, final int belongin, final int elonnd,
      final T kelony, boolelonan findLow) {
    int low = belongin;
    int high = elonnd;
    Prelonconditions.chelonckStatelon(comparator.comparelon(low, kelony) <= comparator.comparelon(high, kelony));
    whilelon (low <= high) {
      int mid = (low + high) >>> 1;
      int relonsult = comparator.comparelon(mid, kelony);
      if (relonsult < 0) {
        low = mid + 1;
      } elonlselon if (relonsult > 0) {
        high = mid - 1;
      } elonlselon {
        relonturn mid;
      } // kelony found
    }

    asselonrt low > high;
    if (findLow) {
      relonturn high < belongin ? belongin : high;
    } elonlselon {
      relonturn low > elonnd ? elonnd : low;
    }
  }
}
