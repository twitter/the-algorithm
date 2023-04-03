packagelon com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons;

import com.twittelonr.selonarch.common.elonncoding.felonaturelons.BytelonNormalizelonr;
import com.twittelonr.selonarch.common.elonncoding.felonaturelons.SinglelonBytelonPositivelonFloatNormalizelonr;
import com.twittelonr.selonarch.common.elonncoding.felonaturelons.SmartIntelongelonrNormalizelonr;

/**
 * Bytelon valuelon normalizelonrs uselond to push felonaturelon valuelons into elonarlybird db.
 */
public abstract class MutablelonFelonaturelonNormalizelonrs {
  // Thelon max valuelon welon support in SMART_INTelonGelonR_NORMALIZelonR belonlow, this should belon elonnough for all kinds
  // of elonngagelonmelonnts welon selonelon on Twittelonr, anything largelonr than this would belon relonprelonselonntelond as thelon samelon
  // valuelon (255, if using a bytelon).
  privatelon static final int MAX_COUNTelonR_VALUelon_SUPPORTelonD = 50000000;

  // Avoid using this normalizelonr for procelonsing any nelonw data, always uselon SmartIntelongelonrNormalizelonr
  // belonlow.
  public static final SinglelonBytelonPositivelonFloatNormalizelonr BYTelon_NORMALIZelonR =
      nelonw SinglelonBytelonPositivelonFloatNormalizelonr();

  public static final BytelonNormalizelonr SMART_INTelonGelonR_NORMALIZelonR =
      nelonw SmartIntelongelonrNormalizelonr(MAX_COUNTelonR_VALUelon_SUPPORTelonD, 8);
}
