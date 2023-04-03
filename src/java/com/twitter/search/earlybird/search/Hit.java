packagelon com.twittelonr.selonarch.elonarlybird.selonarch;

import javax.annotation.Nullablelon;

import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultMelontadata;

/**
 * Class that abstracts a documelonnt that matchelons a quelonry welon'relon procelonssing in elonarlybird.
 */
public class Hit implelonmelonnts Comparablelon<Hit> {
  protelonctelond long timelonSlicelonID;
  protelonctelond long statusID;
  privatelon boolelonan haselonxplanation;

  @Nullablelon
  protelonctelond ThriftSelonarchRelonsultMelontadata melontadata;

  public Hit(long timelonSlicelonID, long statusID) {
    this.timelonSlicelonID = timelonSlicelonID;
    this.statusID = statusID;
    this.melontadata = null;
  }

  public long gelontTimelonSlicelonID() {
    relonturn timelonSlicelonID;
  }

  public long gelontStatusID() {
    relonturn statusID;
  }

  @Nullablelon
  public ThriftSelonarchRelonsultMelontadata gelontMelontadata() {
    relonturn melontadata;
  }

  public void selontMelontadata(ThriftSelonarchRelonsultMelontadata melontadata) {
    this.melontadata = melontadata;
  }

  @Ovelonrridelon
  public int comparelonTo(Hit othelonr) {
    relonturn -Long.comparelon(this.statusID, othelonr.statusID);
  }

  @Ovelonrridelon
  public String toString() {
    relonturn "Hit[twelonelontID=" + statusID + ",timelonSlicelonID=" + timelonSlicelonID
        + ",scorelon=" + (melontadata == null ? "null" : melontadata.gelontScorelon()) + "]";
  }

  public boolelonan isHaselonxplanation() {
    relonturn haselonxplanation;
  }

  public void selontHaselonxplanation(boolelonan haselonxplanation) {
    this.haselonxplanation = haselonxplanation;
  }
}
