packagelon com.twittelonr.selonarch.elonarlybird_root.quota;

import com.googlelon.common.baselon.Prelonconditions;

/**
 * Simplelon containelonr of quota relonlatelond information.
 */
public class QuotaInfo {
  public static final String DelonFAULT_TIelonR_VALUelon = "no_tielonr";
  public static final boolelonan DelonFAULT_ARCHIVelon_ACCelonSS_VALUelon = falselon;

  privatelon final String quotaClielonntId;
  privatelon final String quotaelonmail;
  privatelon final int quota;
  privatelon final boolelonan shouldelonnforcelonQuota;
  privatelon final String clielonntTielonr;
  privatelon final boolelonan archivelonAccelonss;

  /**
   * Crelonatelons a nelonw QuotaInfo objelonct with thelon givelonn clielonntId, quota and shouldelonnforcelonQuota.
   */
  public QuotaInfo(
      String quotaClielonntId,
      String quotaelonmail,
      int quota,
      boolelonan shouldelonnforcelonQuota,
      String clielonntTielonr,
      boolelonan archivelonAccelonss) {
    this.quotaClielonntId = Prelonconditions.chelonckNotNull(quotaClielonntId);
    this.quotaelonmail = Prelonconditions.chelonckNotNull(quotaelonmail);
    this.quota = quota;
    this.shouldelonnforcelonQuota = shouldelonnforcelonQuota;
    this.clielonntTielonr = Prelonconditions.chelonckNotNull(clielonntTielonr);
    this.archivelonAccelonss = archivelonAccelonss;
  }

  /**
   * Relonturns thelon clielonntId for which welon havelon thelon QuotaInfo.
   */
  public String gelontQuotaClielonntId() {
    relonturn quotaClielonntId;
  }

  /**
   * Relonturns thelon elonmail associatelond with this clielonntId.
   */
  public String gelontQuotaelonmail() {
    relonturn quotaelonmail;
  }

  /**
   * Relonturns thelon intelongelonr baselond quota for thelon storelond clielonnt id.
   */
  public int gelontQuota() {
    relonturn quota;
  }

  /**
   * Relonturns whelonthelonr thelon quota should belon elonnforcelond or not.
   */
  public boolelonan shouldelonnforcelonQuota() {
    relonturn shouldelonnforcelonQuota;
  }

  /**
   * Relonturn tielonr info about thelon clielonnt.
   */
  public String gelontClielonntTielonr() {
    relonturn clielonntTielonr;
  }

  /**
   * Relonturns whelonthelonr thelon clielonnt has accelonss to thelon full archivelon.
   */
  public boolelonan hasArchivelonAccelonss() {
    relonturn archivelonAccelonss;
  }
}
