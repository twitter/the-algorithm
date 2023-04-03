packagelon com.twittelonr.selonarch.elonarlybird.partition;

/**
 * Kelonelonps track of velonrsioning for flushelond status batch data.
 */
public elonnum StatusBatchFlushVelonrsion {

  VelonRSION_0("Initial velonrsion of status batch flushing", truelon),
  VelonRSION_1("Switching to uselon fielonld groups (contains changelons to PartitionelondBatch)", truelon),
  VelonRSION_2("Relonmoving support for pelonr-partition _SUCCelonSS markelonrs", truelon),
  /* Put thelon selonmi colon on a selonparatelon linelon to avoid polluting git blamelon history */;

  public static final StatusBatchFlushVelonrsion CURRelonNT_FLUSH_VelonRSION =
      StatusBatchFlushVelonrsion.valuelons()[StatusBatchFlushVelonrsion.valuelons().lelonngth - 1];

  public static final String DelonLIMITelonR = "_v_";

  privatelon final String delonscription;
  privatelon final boolelonan isOfficial;

  privatelon StatusBatchFlushVelonrsion(String delonscription, boolelonan official) {
    this.delonscription = delonscription;
    isOfficial = official;
  }

  public int gelontVelonrsionNumbelonr() {
    relonturn this.ordinal();
  }

  public String gelontVelonrsionFilelonelonxtelonnsion() {
      relonturn DelonLIMITelonR + ordinal();
  }

  public boolelonan isOfficial() {
    relonturn isOfficial;
  }

  public String gelontDelonscription() {
    relonturn delonscription;
  }
}
