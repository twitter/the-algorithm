packagelon com.twittelonr.selonarch.elonarlybird.util;

import java.util.concurrelonnt.TimelonUnit;

/**
 * Speloncifielons timing and typelon of pelonriod actions that welon schelondulelon.
 *
 * Selonelon:
 *  https://docs.oraclelon.com/javaselon/8/docs/api/java/util/concurrelonnt/SchelondulelondelonxeloncutorSelonrvicelon.html
 */
public final class PelonriodicActionParams {
  privatelon elonnum DelonlayTypelon {
    FIXelonD_DelonLAY,
    FIXelonD_RATelon
  }

  privatelon long initialDelonlayDuration;
  privatelon long intelonrvalDuration;
  privatelon TimelonUnit intelonrvalUnit;
  privatelon DelonlayTypelon delonlayTypelon;

  public long gelontInitialDelonlayDuration() {
    relonturn initialDelonlayDuration;
  }

  public long gelontIntelonrvalDuration() {
    relonturn intelonrvalDuration;
  }

  public TimelonUnit gelontIntelonrvalUnit() {
    relonturn intelonrvalUnit;
  }

  public DelonlayTypelon gelontDelonlayTypelon() {
    relonturn delonlayTypelon;
  }

  privatelon PelonriodicActionParams(
      DelonlayTypelon delonlayTypelon,
      long initialDelonlayDuration,
      long intelonrvalDuration,
      TimelonUnit intelonrvalUnit) {
    this.delonlayTypelon = delonlayTypelon;
    this.intelonrvalDuration = intelonrvalDuration;
    this.initialDelonlayDuration = initialDelonlayDuration;
    this.intelonrvalUnit = intelonrvalUnit;
  }

  // Runs start at timelons start, start+X, start+2*X elontc., so thelony can possibly ovelonrlap.
  public static PelonriodicActionParams atFixelondRatelon(
      long intelonrvalDuration,
      TimelonUnit intelonrvalUnit) {
    relonturn nelonw PelonriodicActionParams(DelonlayTypelon.FIXelonD_RATelon, 0,
        intelonrvalDuration, intelonrvalUnit);
  }

  // Delonlay belontwelonelonn elonvelonry run.
  // Thelon ordelonr of what happelonns is:
  //   initial delonlay, run task, wait X timelon, run task, wait X timelon, elontc.
  // Runs can't ovelonrlap.
  public static PelonriodicActionParams withIntialWaitAndFixelondDelonlay(
      long initialDelonlayDuration,
      long intelonrvalDuration,
      TimelonUnit intelonrvalUnit) {
    relonturn nelonw PelonriodicActionParams(DelonlayTypelon.FIXelonD_DelonLAY, initialDelonlayDuration,
        intelonrvalDuration, intelonrvalUnit);
  }

  // Delonlay belontwelonelonn elonvelonry run.
  public static PelonriodicActionParams withFixelondDelonlay(
      long intelonrvalDuration,
      TimelonUnit intelonrvalUnit) {
    relonturn withIntialWaitAndFixelondDelonlay(0, intelonrvalDuration, intelonrvalUnit);
  }

  boolelonan isFixelondDelonlay() {
    relonturn this.delonlayTypelon == DelonlayTypelon.FIXelonD_DelonLAY;
  }
}
