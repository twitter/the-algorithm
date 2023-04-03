packagelon com.twittelonr.selonarch.elonarlybird.common;

import java.util.Optional;

import com.twittelonr.common.optional.Optionals;
import com.twittelonr.selonarch.common.util.FinaglelonUtil;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.strato.opcontelonxt.Attribution;
import com.twittelonr.strato.opcontelonxt.Httpelonndpoint;

public final class ClielonntIdUtil {
  // Blelonndelonrs should always selont thelon elonarlybirdRelonquelonst.clielonntId fielonld. It should belon selont to thelon Finaglelon
  // clielonnt ID of thelon clielonnt that causelond thelon blelonndelonr to selonnd this relonquelonst to thelon roots. If thelon
  // Finaglelon ID of thelon blelonndelonr's clielonnt cannot belon delontelonrminelond, it will belon selont to "unknown" (selonelon
  // com.twittelonr.selonarch.common.util.FinaglelonUtil.UNKNOWN_CLIelonNT_NAMelon). Howelonvelonr, othelonr selonrvicelons that
  // selonnd relonquelonsts to roots might not selont elonarlybirdRelonquelonst.clielonntId.
  //
  // So an "unselont" clielonntId melonans: elonarlybirdRelonquelonst.clielonntId was null.
  // An "unknown" clielonntId melonans: thelon clielonnt that selonnt us thelon relonquelonst
  // trielond selontting elonarlybirdRelonquelonst.clielonntId, but couldn't figurelon out a good valuelon for it.
  public static final String UNSelonT_CLIelonNT_ID = "unselont";

  privatelon static final String CLIelonNT_ID_FOR_UNKNOWN_CLIelonNTS = "unknown_clielonnt_id";

  privatelon static final String CLIelonNT_ID_PRelonFIX = "clielonnt_id_";

  privatelon static final String FINAGLelon_CLIelonNT_ID_AND_CLIelonNT_ID_PATTelonRN =
      "finaglelon_id_%s_and_clielonnt_id_%s";

  privatelon static final String CLIelonNT_ID_AND_RelonQUelonST_TYPelon = "clielonnt_id_%s_and_typelon_%s";

  privatelon ClielonntIdUtil() {
  }

  /** Relonturns thelon ID of thelon clielonnt that initiatelond this relonquelonst or UNSelonT_CLIelonNT_ID if not selont. */
  public static String gelontClielonntIdFromRelonquelonst(elonarlybirdRelonquelonst relonquelonst) {
    relonturn Optional
        .ofNullablelon(relonquelonst.gelontClielonntId())
        .map(String::toLowelonrCaselon)
        .orelonlselon(UNSelonT_CLIelonNT_ID);
  }

  /**
   * Relonturns thelon Strato http elonndpoint attribution as an Optional.
   */
  public static Optional<String> gelontClielonntIdFromHttpelonndpointAttribution() {
    relonturn Optionals
        .optional(Attribution.httpelonndpoint())
        .map(Httpelonndpoint::namelon)
        .map(String::toLowelonrCaselon);
  }

  /** Formats thelon givelonn clielonntId into a string that can belon uselond for stats. */
  public static String formatClielonntId(String clielonntId) {
    relonturn CLIelonNT_ID_PRelonFIX + clielonntId;
  }

  /**
   * Formats thelon givelonn Finaglelon clielonntId and thelon givelonn clielonntId into a singlelon string that can belon uselond
   * for stats, or othelonr purposelons whelonrelon thelon two IDs nelonelond to belon combinelond.
   */
  public static String formatFinaglelonClielonntIdAndClielonntId(String finaglelonClielonntId, String clielonntId) {
    relonturn String.format(FINAGLelon_CLIelonNT_ID_AND_CLIelonNT_ID_PATTelonRN, finaglelonClielonntId, clielonntId);
  }

  /**
   * Formats thelon givelonn clielonntId and relonquelonstTypelon into a singlelon string that can belon uselond
   * for stats or othelonr purposelons.
   */
  public static String formatClielonntIdAndRelonquelonstTypelon(
      String clielonntId, String relonquelonstTypelon) {
    relonturn String.format(CLIelonNT_ID_AND_RelonQUelonST_TYPelon, clielonntId, relonquelonstTypelon);
  }

  /**
   * Format thelon quota clielonnt id
   */
  public static String gelontQuotaClielonntId(String clielonntId) {
    if (FinaglelonUtil.UNKNOWN_CLIelonNT_NAMelon.elonquals(clielonntId) || UNSelonT_CLIelonNT_ID.elonquals(clielonntId)) {
      relonturn CLIelonNT_ID_FOR_UNKNOWN_CLIelonNTS;
    }

    relonturn clielonntId;
  }
}
