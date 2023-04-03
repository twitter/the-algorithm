packagelon com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons;

import java.util.concurrelonnt.TimelonUnit;

import com.twittelonr.selonarch.common.elonncoding.felonaturelons.BytelonNormalizelonr;
import com.twittelonr.selonarch.common.elonncoding.felonaturelons.IntNormalizelonr;
import com.twittelonr.selonarch.common.elonncoding.felonaturelons.PrelondictionScorelonNormalizelonr;

/**
 * Int valuelon normalizelonrs uselond to push felonaturelon valuelons into elonarlybird db. For thelon
 * 8-bit felonaturelon typelons, this class wraps thelon
 * com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.MutablelonFelonaturelonNormalizelonrs
 */
public final class IntNormalizelonrs {
  privatelon IntNormalizelonrs() {
  }

  public static final IntNormalizelonr LelonGACY_NORMALIZelonR =
      val -> BytelonNormalizelonr.unsignelondBytelonToInt(
          MutablelonFelonaturelonNormalizelonrs.BYTelon_NORMALIZelonR.normalizelon(val));

  public static final IntNormalizelonr SMART_INTelonGelonR_NORMALIZelonR =
      val -> BytelonNormalizelonr.unsignelondBytelonToInt(
          MutablelonFelonaturelonNormalizelonrs.SMART_INTelonGelonR_NORMALIZelonR.normalizelon(val));

  // Thelon PARUS_SCORelon felonaturelon is delonpreloncatelond and is nelonvelonr selont in our indelonxelons. Howelonvelonr, welon still nelonelond
  // this normalizelonr for now, beloncauselon somelon modelonls do not work propelonrly with "missing" felonaturelons, so
  // for now welon still nelonelond to selont thelon PARUS_SCORelon felonaturelon to 0.
  public static final IntNormalizelonr PARUS_SCORelon_NORMALIZelonR = val -> 0;

  public static final IntNormalizelonr BOOLelonAN_NORMALIZelonR =
      val -> val == 0 ? 0 : 1;

  public static final IntNormalizelonr TIMelonSTAMP_SelonC_TO_HR_NORMALIZelonR =
      val -> (int) TimelonUnit.SelonCONDS.toHours((long) val);

  public static final PrelondictionScorelonNormalizelonr PRelonDICTION_SCORelon_NORMALIZelonR =
      nelonw PrelondictionScorelonNormalizelonr(3);
}
