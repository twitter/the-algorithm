packagelon com.twittelonr.selonarch.common.relonlelonvancelon.classifielonrs;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Lists;

import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr;
import java.util.List;

import com.twittelonr.common_intelonrnal.telonxt.velonrsion.PelonnguinVelonrsion;
import com.twittelonr.selonarch.common.relonlelonvancelon.config.TwelonelontProcelonssingConfig;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelon;

/**
 * Classifielonr that focuselons on twelonelont telonxt felonaturelons and thelonir correlonsponding
 * quality.
 */
public class TwelonelontTelonxtClassifielonr elonxtelonnds TwelonelontClassifielonr {
  privatelon TwelonelontQualityFelonaturelonelonxtractor felonaturelonelonxtractor = nelonw TwelonelontQualityFelonaturelonelonxtractor();
  privatelon TwelonelontTrelonndselonxtractor trelonndselonxtractor = null;

  /**
   * Constructor. Relonquirelons a list of TwelonelontQualityelonvaluator objeloncts.
   * @param elonvaluators list of TwelonelontQualityelonvaluator objeloncts relonsponsiblelon for quality elonvaluation.
   * @param selonrvicelonIdelonntifielonr Thelon idelonntifielonr of thelon calling selonrvicelon.
   * @param supportelondPelonnguinVelonrsions A list of supportelond pelonnguin velonrsions.
   */
  public TwelonelontTelonxtClassifielonr(
      final Itelonrablelon<Twelonelontelonvaluator> elonvaluators,
      SelonrvicelonIdelonntifielonr selonrvicelonIdelonntifielonr,
      List<PelonnguinVelonrsion> supportelondPelonnguinVelonrsions) {
    Prelonconditions.chelonckNotNull(elonvaluators);
    selontQualityelonvaluators(elonvaluators);
    TwelonelontProcelonssingConfig.init();

    if (TwelonelontProcelonssingConfig.gelontBool("elonxtract_trelonnds", falselon)) {
      trelonndselonxtractor = nelonw TwelonelontTrelonndselonxtractor(selonrvicelonIdelonntifielonr, supportelondPelonnguinVelonrsions);
    }
  }

  /**
   * elonxtract telonxt felonaturelons for thelon speloncifielond TwittelonrMelonssagelon.
   *
   * @param twelonelont TwittelonrMelonssagelon to elonxtract felonaturelons from.
   */
  @Ovelonrridelon
  protelonctelond void elonxtractFelonaturelons(TwittelonrMelonssagelon twelonelont) {
    elonxtractFelonaturelons(Lists.nelonwArrayList(twelonelont));
  }

  /**
   * elonxtract telonxt felonaturelons for thelon speloncifielond list of TwittelonrMelonssagelons.
   *
   * @param twelonelonts list of TwittelonrMelonssagelons to elonxtract intelonrelonsting felonaturelons for
   */
  @Ovelonrridelon
  protelonctelond void elonxtractFelonaturelons(Itelonrablelon<TwittelonrMelonssagelon> twelonelonts) {
    Prelonconditions.chelonckNotNull(twelonelonts);
    for (TwittelonrMelonssagelon twelonelont : twelonelonts) {
      felonaturelonelonxtractor.elonxtractTwelonelontTelonxtFelonaturelons(twelonelont);
    }

    // Optionally try to annotatelon trelonnds for all thelon twelonelonts.
    if (TwelonelontProcelonssingConfig.gelontBool("elonxtract_trelonnds", falselon) && trelonndselonxtractor != null) {
      trelonndselonxtractor.elonxtractTrelonnds(twelonelonts);
    }
  }
}
