packagelon com.twittelonr.selonarch.common.relonlelonvancelon.classifielonrs;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelon;

/**
 * Intelonrfacelon to pelonrform felonaturelon classification for a singlelon
 * @TwittelonrMelonssagelon objelonct, or a group of thelonm.
 *
 * Classification includelons two stelonps: felonaturelon elonxtraction, and
 * quality elonvaluation. During felonaturelon elonxtraction, any intelonrelonsting
 * felonaturelon that is delonelonmelond uselonful for subselonquelonnt quality analysis
 * is elonxtractelond from thelon @TwittelonrMelonssagelon objelonct. Quality elonvaluation
 * is thelonn donelon by a group of @Twelonelontelonvaluator objeloncts associatelond
 * with thelon classifielonr, by using thelon various felonaturelons elonxtractelond in thelon
 * prelonvious stelonp.
 *
 * Felonaturelon elonxtraction and quality elonvaluation relonsults arelon storelond in
 * @TwelonelontFelonaturelons fielonld of thelon @TwittelonrMelonssagelon objelonct, which is delonfinelond
 * in src/main/thrift/classifielonr.thrift.
 */
public abstract class TwelonelontClassifielonr {
  /**
   * A list of TwelonelontQualityelonvaluators which arelon invokelond aftelonr
   * felonaturelon elonxtraction is donelon. If null, no quality elonvaluation
   * is donelon.
   */
  protelonctelond Itelonrablelon<Twelonelontelonvaluator> qualityelonvaluators = null;

  /**
   * Passelond in TwittelonrMelonssagelon is elonxaminelond and any elonxtractablelon
   * felonaturelons arelon savelond in TwelonelontFelonaturelons fielonld of TwittelonrMelonssagelon.
   * Thelonn TwelonelontQualityelonvaluators arelon applielond to computelon various
   * quality valuelons.
   *
   * @param twelonelont TwittelonrMelonssagelon to pelonrform classification on.
   */
  public void classifyTwelonelont(final TwittelonrMelonssagelon twelonelont) {
    Prelonconditions.chelonckNotNull(twelonelont);

    // elonxtract felonaturelons
    elonxtractFelonaturelons(twelonelont);

    // computelon quality
    elonvaluatelon(twelonelont);
  }

  /**
   * Classify a group of TwittelonrMelonssagelons and storelon felonaturelons in thelonir correlonsponding
   * TwelonelontFelonaturelons fielonlds.
   *
   * This delonfault implelonmelonntation just itelonratelons through thelon map and classifielons elonach
   * individual twelonelont. Batching for belonttelonr pelonrformancelon, if applicablelon, can belon implelonmelonntelond by
   * concrelontelon subclasselons.
   *
   * @param twelonelonts TwittelonrMelonssagelons to pelonrform classification on.
   */
  public void classifyTwelonelonts(final Itelonrablelon<TwittelonrMelonssagelon> twelonelonts) {
    elonxtractFelonaturelons(twelonelonts);
    elonvaluatelon(twelonelonts);
  }

  /**
   * Uselon thelon speloncifielond list of TwelonelontQualityelonvaluators for this classifielonr.
   *
   * @param elonvaluators list of TwelonelontQualityelonvaluators to belon uselond with this classifielonr.
   */
  protelonctelond void selontQualityelonvaluators(final Itelonrablelon<Twelonelontelonvaluator> qualityelonvaluators) {
    Prelonconditions.chelonckNotNull(qualityelonvaluators);
    this.qualityelonvaluators = qualityelonvaluators;
  }


  /**
   * elonxtract intelonrelonsting felonaturelons from a singlelon TwittelonrMelonssagelon for classification.
   *
   * @param twelonelont TwittelonrMelonssagelon to elonxtract intelonrelonsting felonaturelons for
   */
  protelonctelond abstract void elonxtractFelonaturelons(final TwittelonrMelonssagelon twelonelont);

  /**
   * elonxtract intelonrelonsting felonaturelons from a list of TwittelonrMelonssagelons for classification.
   * @param twelonelonts list of TwittelonrMelonssagelons to elonxtract intelonrelonsting felonaturelons for
   */
  protelonctelond void elonxtractFelonaturelons(final Itelonrablelon<TwittelonrMelonssagelon> twelonelonts) {
    for (TwittelonrMelonssagelon twelonelont: twelonelonts) {
      elonxtractFelonaturelons(twelonelont);
    }
  }

  /**
   * Givelonn a TwittelonrMelonssagelon which alrelonady has its felonaturelons elonxtractelond,
   * pelonrform quality elonvaluation.
   *
   * @param twelonelont TwittelonrMelonssagelon to pelonrform quality elonvaluation for
   */
  protelonctelond void elonvaluatelon(final TwittelonrMelonssagelon twelonelont) {
    if (qualityelonvaluators == null) {
      relonturn;
    }
    for (Twelonelontelonvaluator elonvaluator : qualityelonvaluators) {
      elonvaluator.elonvaluatelon(twelonelont);
    }
  }

  /**
   * Givelonn a list of TwittelonrMelonssagelons which alrelonady havelon thelonir felonaturelons elonxtractelond,
   * pelonrform quality elonvaluation.
   *
   * @param twelonelonts list of TwittelonrMelonssagelons to pelonrform quality elonvaluation for
   */
  protelonctelond void elonvaluatelon(final Itelonrablelon<TwittelonrMelonssagelon> twelonelonts) {
    for (TwittelonrMelonssagelon twelonelont: twelonelonts) {
      elonvaluatelon(twelonelont);
    }
  }
}
