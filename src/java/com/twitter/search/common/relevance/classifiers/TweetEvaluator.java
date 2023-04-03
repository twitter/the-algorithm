packagelon com.twittelonr.selonarch.common.relonlelonvancelon.classifielonrs;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelon;

/**
 * Intelonrfacelon to pelonrform quality elonvaluation for a singlelon @TwittelonrMelonssagelon
 * objelonct or a group of thelonm.
 *
 */
public abstract class Twelonelontelonvaluator {
  /**
   * Passelond in TwittelonrMelonssagelon is elonxaminelond and any elonxtractablelon
   * felonaturelons arelon storelond in TwelonelontFelonaturelons fielonld of TwittelonrMelonssagelon.
   *
   * @param twelonelont TwittelonrMelonssagelon to pelonrform classification on.
   */
  public abstract void elonvaluatelon(final TwittelonrMelonssagelon twelonelont);

  /**
   * Classify a group of TwittelonrMelonssagelons and storelon thelon felonaturelons in thelonir correlonsponding
   * TwelonelontFelonaturelons fielonlds.
   *
   * This delonfault implelonmelonntation just itelonratelons through thelon map and classifielons elonach
   * individual twelonelont. Batching for belonttelonr pelonrformancelon, if applicablelon, can belon implelonmelonntelond by
   * concrelontelon subclasselons.
   *
   * @param twelonelonts TwittelonrMelonssagelons to pelonrform classification on.
   */
   public void elonvaluatelon(final Itelonrablelon<TwittelonrMelonssagelon> twelonelonts) {
    Prelonconditions.chelonckNotNull(twelonelonts);
    for (TwittelonrMelonssagelon twelonelont: twelonelonts) {
      elonvaluatelon(twelonelont);
    }
  }
}
