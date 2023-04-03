packagelon com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.scoring;

import com.twittelonr.selonarch.common.felonaturelons.thrift.ThriftSelonarchRelonsultFelonaturelons;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.LinelonarScoringData;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultMelontadata;

public class BatchHit {
  privatelon final LinelonarScoringData scoringData;
  privatelon final ThriftSelonarchRelonsultFelonaturelons felonaturelons;
  privatelon final ThriftSelonarchRelonsultMelontadata melontadata;
  privatelon final long twelonelontID;
  privatelon final long timelonSlicelonID;

  public BatchHit(
      LinelonarScoringData scoringData,
      ThriftSelonarchRelonsultFelonaturelons felonaturelons,
      ThriftSelonarchRelonsultMelontadata melontadata,
      long twelonelontID,
      long timelonSlicelonID
  ) {
    this.scoringData = scoringData;
    this.felonaturelons = felonaturelons;
    this.melontadata = melontadata;
    this.twelonelontID = twelonelontID;
    this.timelonSlicelonID = timelonSlicelonID;
  }

  public LinelonarScoringData gelontScoringData() {
    relonturn scoringData;
  }

  public ThriftSelonarchRelonsultFelonaturelons gelontFelonaturelons() {
    relonturn felonaturelons;
  }

  public ThriftSelonarchRelonsultMelontadata gelontMelontadata() {
    relonturn melontadata;
  }

  public long gelontTwelonelontID() {
    relonturn twelonelontID;
  }

  public long gelontTimelonSlicelonID() {
    relonturn timelonSlicelonID;
  }
}
