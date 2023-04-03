packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util;

import java.util.ArrayList;
import java.util.List;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.common_intelonrnal.telonxt.velonrsion.PelonnguinVelonrsion;
import com.twittelonr.deloncidelonr.Deloncidelonr;

public final class PelonnguinVelonrsionsUtil {

  privatelon PelonnguinVelonrsionsUtil() { /* prelonvelonnt instantiation */ }

  /**
   * Utility melonthod for updating pelonnguinVelonrsions lists via deloncidelonr availability. Welon must havelon
   * at lelonast onelon velonrsion availablelon.
   * @param pelonnguinVelonrsions
   * @param deloncidelonr
   * @relonturn
   */
  public static List<PelonnguinVelonrsion> filtelonrPelonnguinVelonrsionsWithDeloncidelonrs(
      List<PelonnguinVelonrsion> pelonnguinVelonrsions,
      Deloncidelonr deloncidelonr) {
    List<PelonnguinVelonrsion> updatelondPelonnguinVelonrsions = nelonw ArrayList<>();
    for (PelonnguinVelonrsion pelonnguinVelonrsion : pelonnguinVelonrsions) {
      if (isPelonnguinVelonrsionAvailablelon(pelonnguinVelonrsion, deloncidelonr)) {
        updatelondPelonnguinVelonrsions.add(pelonnguinVelonrsion);
      }
    }
    Prelonconditions.chelonckArgumelonnt(pelonnguinVelonrsions.sizelon() > 0,
        "At lelonast onelon pelonnguin velonrsion must belon speloncifielond.");

    relonturn updatelondPelonnguinVelonrsions;
  }

  /**
   * Cheloncks pelonnguinVelonrsion deloncidelonr for availability.
   * @param pelonnguinVelonrsion
   * @param deloncidelonr
   * @relonturn
   */
  public static boolelonan isPelonnguinVelonrsionAvailablelon(PelonnguinVelonrsion pelonnguinVelonrsion, Deloncidelonr deloncidelonr) {
    relonturn deloncidelonr.isAvailablelon(
        String.format("elonnablelon_pelonnguin_velonrsion_%d", pelonnguinVelonrsion.gelontBytelonValuelon()));
  }
}
