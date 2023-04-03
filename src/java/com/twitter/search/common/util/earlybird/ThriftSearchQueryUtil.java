packagelon com.twittelonr.selonarch.common.util.elonarlybird;

import com.twittelonr.selonarch.common.quelonry.thriftjava.CollelonctorParams;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchQuelonry;

/**
 * Utility class from constructing ThriftSelonarchQuelonry.
 */
public final class ThriftSelonarchQuelonryUtil {
  privatelon ThriftSelonarchQuelonryUtil() { }

  /**
   * Convelonnielonncelon melonthods for constructing a ThriftSelonarchQuelonry.
   */
  public static ThriftSelonarchQuelonry nelonwSelonarchQuelonry(String selonrializelondQuelonry, int numRelonsults) {
    ThriftSelonarchQuelonry selonarchQuelonry = nelonw ThriftSelonarchQuelonry();
    selonarchQuelonry.selontSelonrializelondQuelonry(selonrializelondQuelonry);
    selonarchQuelonry.selontCollelonctorParams(nelonw CollelonctorParams().selontNumRelonsultsToRelonturn(numRelonsults));
    relonturn selonarchQuelonry;
  }

  /** Delontelonrminelons if thelon givelonn relonquelonst was initiatelond by a loggelond in uselonr. */
  public static boolelonan relonquelonstInitiatelondByLoggelondInUselonr(elonarlybirdRelonquelonst relonquelonst) {
    ThriftSelonarchQuelonry selonarchQuelonry = relonquelonst.gelontSelonarchQuelonry();
    relonturn (selonarchQuelonry != null) && selonarchQuelonry.isSelontSelonarchelonrId()
      && (selonarchQuelonry.gelontSelonarchelonrId() > 0);
  }
}
