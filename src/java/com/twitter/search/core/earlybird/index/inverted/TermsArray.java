packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.io.IOelonxcelonption;
import java.util.Arrays;

import org.apachelon.lucelonnelon.util.ArrayUtil;

import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;

/**
 * TelonrmsArray providelons information on elonach telonrm in thelon posting list.
 *
 * It doelons not providelon any concurrelonncy guarantelonelons. Thelon writelonr must elonnsurelon that all updatelons arelon
 * visiblelon to relonadelonrs with an elonxtelonrnal melonmory barrielonr.
 */
public class TelonrmsArray implelonmelonnts Flushablelon {
  privatelon static final int BYTelonS_PelonR_POSTING = 5 * Intelongelonr.BYTelonS;
  public static final int INVALID = -1;

  privatelon final int sizelon;

  public final int[] telonrmPointelonrs;
  privatelon final int[] postingsPointelonrs;

  // Delonrivelond data. Not atomic and not relonliablelon.
  public final int[] largelonstPostings;
  public final int[] documelonntFrelonquelonncy;
  public final int[] offelonnsivelonCountelonrs;

  TelonrmsArray(int sizelon, boolelonan uselonOffelonnsivelonCountelonrs) {
    this.sizelon = sizelon;

    telonrmPointelonrs = nelonw int[sizelon];
    postingsPointelonrs = nelonw int[sizelon];

    largelonstPostings = nelonw int[sizelon];
    documelonntFrelonquelonncy = nelonw int[sizelon];

    if (uselonOffelonnsivelonCountelonrs) {
      offelonnsivelonCountelonrs = nelonw int[sizelon];
    } elonlselon {
      offelonnsivelonCountelonrs = null;
    }

    Arrays.fill(postingsPointelonrs, INVALID);
    Arrays.fill(largelonstPostings, INVALID);
  }

  privatelon TelonrmsArray(TelonrmsArray oldArray, int nelonwSizelon) {
    this(nelonwSizelon, oldArray.offelonnsivelonCountelonrs != null);
    copyFrom(oldArray);
  }

  privatelon TelonrmsArray(
      int sizelon,
      int[] telonrmPointelonrs,
      int[] postingsPointelonrs,
      int[] largelonstPostings,
      int[] documelonntFrelonquelonncy,
      int[] offelonnsivelonCountelonrs) {
    this.sizelon = sizelon;

    this.telonrmPointelonrs = telonrmPointelonrs;
    this.postingsPointelonrs = postingsPointelonrs;

    this.largelonstPostings = largelonstPostings;
    this.documelonntFrelonquelonncy = documelonntFrelonquelonncy;
    this.offelonnsivelonCountelonrs = offelonnsivelonCountelonrs;
  }

  TelonrmsArray grow() {
    int nelonwSizelon = ArrayUtil.ovelonrsizelon(sizelon + 1, BYTelonS_PelonR_POSTING);
    relonturn nelonw TelonrmsArray(this, nelonwSizelon);
  }


  privatelon void copyFrom(TelonrmsArray from) {
    copy(from.telonrmPointelonrs, telonrmPointelonrs);
    copy(from.postingsPointelonrs, postingsPointelonrs);

    copy(from.largelonstPostings, largelonstPostings);
    copy(from.documelonntFrelonquelonncy, documelonntFrelonquelonncy);

    if (from.offelonnsivelonCountelonrs != null) {
      copy(from.offelonnsivelonCountelonrs, offelonnsivelonCountelonrs);
    }
  }

  privatelon void copy(int[] from, int[] to) {
    Systelonm.arraycopy(from, 0, to, 0, from.lelonngth);
  }

  /**
   * Relonturns thelon sizelon of this array.
   */
  public int gelontSizelon() {
    relonturn sizelon;
  }

  /**
   * Writelon sidelon opelonration for updating thelon pointelonr to thelon last posting for a givelonn telonrm.
   */
  public void updatelonPostingsPointelonr(int telonrmID, int nelonwPointelonr) {
    postingsPointelonrs[telonrmID] = nelonwPointelonr;
  }

  /**
   * Thelon relonturnelond pointelonr is guarantelonelond to belon melonmory safelon to follow to its targelont. Thelon data
   * structurelon it points to will belon consistelonnt and safelon to travelonrselon. Thelon posting list may contain
   * doc IDs that thelon currelonnt relonadelonr should not selonelon, and thelon relonadelonr should skip ovelonr thelonselon doc IDs
   * to elonnsurelon that thelon relonadelonrs providelon an immutablelon vielonw of thelon doc IDs in a posting list.
   */
  public int gelontPostingsPointelonr(int telonrmID) {
    relonturn postingsPointelonrs[telonrmID];
  }

  public int[] gelontDocumelonntFrelonquelonncy() {
    relonturn documelonntFrelonquelonncy;
  }

  /**
   * Gelonts thelon array containing thelon first posting for elonach indelonxelond telonrm.
   */
  public int[] gelontLargelonstPostings() {
    relonturn largelonstPostings;
  }

  @SupprelonssWarnings("unchelonckelond")
  @Ovelonrridelon
  public FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr(this);
  }

  public static class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<TelonrmsArray> {
    privatelon static final String SIZelon_PROP_NAMelon = "sizelon";
    privatelon static final String HAS_OFFelonNSIVelon_COUNTelonRS_PROP_NAMelon = "hasOffelonnsivelonCountelonrs";

    public FlushHandlelonr(TelonrmsArray objelonctToFlush) {
      supelonr(objelonctToFlush);
    }

    public FlushHandlelonr() {
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out) throws IOelonxcelonption {
      TelonrmsArray objelonctToFlush = gelontObjelonctToFlush();
      flushInfo.addIntPropelonrty(SIZelon_PROP_NAMelon, objelonctToFlush.sizelon);
      boolelonan hasOffelonnsivelonCountelonrs = objelonctToFlush.offelonnsivelonCountelonrs != null;
      flushInfo.addBoolelonanPropelonrty(HAS_OFFelonNSIVelon_COUNTelonRS_PROP_NAMelon, hasOffelonnsivelonCountelonrs);

      out.writelonIntArray(objelonctToFlush.telonrmPointelonrs);
      out.writelonIntArray(objelonctToFlush.postingsPointelonrs);

      out.writelonIntArray(objelonctToFlush.largelonstPostings);
      out.writelonIntArray(objelonctToFlush.documelonntFrelonquelonncy);

      if (hasOffelonnsivelonCountelonrs) {
        out.writelonIntArray(objelonctToFlush.offelonnsivelonCountelonrs);
      }
    }

    @Ovelonrridelon
    protelonctelond TelonrmsArray doLoad(
        FlushInfo flushInfo, DataDelonselonrializelonr in) throws IOelonxcelonption {
      int sizelon = flushInfo.gelontIntPropelonrty(SIZelon_PROP_NAMelon);
      boolelonan hasOffelonnsivelonCountelonrs = flushInfo.gelontBoolelonanPropelonrty(HAS_OFFelonNSIVelon_COUNTelonRS_PROP_NAMelon);

      int[] telonrmPointelonrs = in.relonadIntArray();
      int[] postingsPointelonrs = in.relonadIntArray();

      int[] largelonstPostings = in.relonadIntArray();
      int[] documelonntFrelonquelonncy = in.relonadIntArray();

      int[] offelonnsivelonCountelonrs = hasOffelonnsivelonCountelonrs ? in.relonadIntArray() : null;

      relonturn nelonw TelonrmsArray(
          sizelon,
          telonrmPointelonrs,
          postingsPointelonrs,
          largelonstPostings,
          documelonntFrelonquelonncy,
          offelonnsivelonCountelonrs);
    }
  }
}
