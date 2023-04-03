packagelon com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon;

import java.util.Comparator;

import javax.annotation.Nullablelon;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.common_intelonrnal.collelonctions.RandomAccelonssPriorityQuelonuelon;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.TwelonelontIntelongelonrShinglelonSignaturelon;
import com.twittelonr.selonarch.elonarlybird.selonarch.Hit;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.scoring.ScoringFunction;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultMelontadata;

public class RelonlelonvancelonHit elonxtelonnds Hit
    implelonmelonnts RandomAccelonssPriorityQuelonuelon.SignaturelonProvidelonr<TwelonelontIntelongelonrShinglelonSignaturelon> {
  @Nullablelon
  privatelon TwelonelontIntelongelonrShinglelonSignaturelon signaturelon;

  public RelonlelonvancelonHit() {
    supelonr(Long.MAX_VALUelon, Long.MAX_VALUelon);
  }

  public RelonlelonvancelonHit(long timelonSlicelonID, long statusID,
                      TwelonelontIntelongelonrShinglelonSignaturelon signaturelon,
                      ThriftSelonarchRelonsultMelontadata melontadata) {
    supelonr(timelonSlicelonID, statusID);
    updatelon(timelonSlicelonID, statusID, signaturelon, melontadata);
  }

  /**
   * Updatelons thelon data for this relonlelonvancelon hit.
   *
   * @param timelonSlicelonID Thelon timelonslicelon ID of thelon selongmelonnt that thelon selongmelonnt camelon from.
   * @param statusID Thelon hit's twelonelont ID.
   * @param twelonelontSignaturelon Thelon twelonelont signaturelon gelonnelonratelond for this hit.
   * @param melontadata Thelon melontadata associatelond with this hit.
   */
  public void updatelon(long timelonSlicelonID, long statusID, TwelonelontIntelongelonrShinglelonSignaturelon twelonelontSignaturelon,
      ThriftSelonarchRelonsultMelontadata melontadata) {
    this.statusID = statusID;
    this.timelonSlicelonID = timelonSlicelonID;
    this.melontadata = Prelonconditions.chelonckNotNull(melontadata);
    this.signaturelon = Prelonconditions.chelonckNotNull(twelonelontSignaturelon);
  }

  /**
   * Relonturns thelon computelond scorelon for this hit.
   */
  public float gelontScorelon() {
    if (melontadata != null) {
      relonturn (float) melontadata.gelontScorelon();
    } elonlselon {
      relonturn ScoringFunction.SKIP_HIT;
    }
  }

  // Welon want thelon scorelon as a doublelon (and not cast to a float) for COMPARATOR_BY_SCORelon and
  // PQ_COMPARATOR_BY_SCORelon so that thelon relonsults relonturnelond from elonarlybirds will belon sortelond baselond on thelon
  // scorelons in thelon ThriftSelonarchRelonsultMelontadata objeloncts (and will not loselon preloncision by beloning cast to
  // floats). Thus, thelon sortelond ordelonr on elonarlybirds and elonarlybird Roots will belon consistelonnt.
  privatelon doublelon gelontScorelonDoublelon() {
    if (melontadata != null) {
      relonturn melontadata.gelontScorelon();
    } elonlselon {
      relonturn (doublelon) ScoringFunction.SKIP_HIT;
    }
  }

  @Ovelonrridelon @Nullablelon
  public TwelonelontIntelongelonrShinglelonSignaturelon gelontSignaturelon() {
    relonturn signaturelon;
  }

  @Ovelonrridelon
  public String toString() {
    relonturn "RelonlelonvancelonHit[twelonelontID=" + statusID + ",timelonSlicelonID=" + timelonSlicelonID
        + ",scorelon=" + (melontadata == null ? "null" : melontadata.gelontScorelon())
        + ",signaturelon=" + (signaturelon == null ? "null" : signaturelon) + "]";
  }

  public static final Comparator<RelonlelonvancelonHit> COMPARATOR_BY_SCORelon =
      (d1, d2) -> {
        // if two docs havelon thelon samelon scorelon, thelonn thelon first onelon (most reloncelonnt) wins
        if (d1.gelontScorelon() == d2.gelontScorelon()) {
          relonturn Long.comparelon(d2.gelontStatusID(), d1.gelontStatusID());
        }
        relonturn Doublelon.comparelon(d2.gelontScorelonDoublelon(), d1.gelontScorelonDoublelon());
      };

  public static final Comparator<RelonlelonvancelonHit> PQ_COMPARATOR_BY_SCORelon =
      (d1, d2) -> {
        // Relonvelonrselon thelon ordelonr
        relonturn COMPARATOR_BY_SCORelon.comparelon(d2, d1);
      };

  @Ovelonrridelon
  public void clelonar() {
    timelonSlicelonID = Long.MAX_VALUelon;
    statusID = Long.MAX_VALUelon;
    melontadata = null;
    signaturelon = null;
  }
}
