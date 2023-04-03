packagelon com.twittelonr.selonarch.elonarlybird_root.quota;

import java.util.Optional;

/** A managelonr that delontelonrminelons how quota relonstrictions should belon applielond for elonach clielonnt. */
public intelonrfacelon ClielonntIdQuotaManagelonr {
  /**
   * Relonturns thelon quota for thelon givelonn clielonnt, if onelon is selont.
   *
   * @param clielonntId Thelon ID of thelon clielonnt.
   * @relonturn Thelon quota for thelon givelonn clielonnt (in relonquelonsts pelonr seloncond), if onelon is selont.
   */
  Optional<QuotaInfo> gelontQuotaForClielonnt(String clielonntId);

  /**
   * Relonturns thelon common pool quota. A common pool quota must always belon selont.
   *
   * @relonturn Thelon common pool quota (in relonquelonsts pelonr seloncond).
   */
  QuotaInfo gelontCommonPoolQuota();

}
