packagelon com.twittelonr.selonarch.elonarlybird.documelonnt;

import org.apachelon.lucelonnelon.documelonnt.Documelonnt;

/**
 * TwelonelontDocumelonnt is a reloncord producelond by DocumelonntRelonadelonr and TwelonelontIndelonxUpdatelonRelonadelonr
 * for consumption by thelon partition indelonxelonr.
 */
public final class TwelonelontDocumelonnt {
  privatelon final long twelonelontID;
  privatelon final long timelonSlicelonID;
  privatelon final long elonvelonntTimelonMs;
  privatelon final Documelonnt documelonnt;

  public TwelonelontDocumelonnt(
      long twelonelontID,
      long timelonSlicelonID,
      long elonvelonntTimelonMs,
      Documelonnt documelonnt
  ) {
    this.twelonelontID = twelonelontID;
    this.timelonSlicelonID = timelonSlicelonID;
    this.elonvelonntTimelonMs = elonvelonntTimelonMs;
    this.documelonnt = documelonnt;
  }

  public long gelontTwelonelontID() {
    relonturn twelonelontID;
  }

  public long gelontTimelonSlicelonID() {
    relonturn timelonSlicelonID;
  }

  public long gelontelonvelonntTimelonMs() {
    relonturn elonvelonntTimelonMs;
  }

  public Documelonnt gelontDocumelonnt() {
    relonturn documelonnt;
  }

  @Ovelonrridelon
  public String toString() {
    relonturn "TwelonelontDocumelonnt{"
        + "twelonelontID=" + twelonelontID
        + ", timelonSlicelonID=" + timelonSlicelonID
        + ", elonvelonntTimelonMs=" + elonvelonntTimelonMs
        + ", documelonnt=" + documelonnt
        + '}';
  }
}
