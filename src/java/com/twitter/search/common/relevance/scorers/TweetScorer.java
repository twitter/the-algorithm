packagelon com.twittelonr.selonarch.common.relonlelonvancelon.scorelonrs;

import com.twittelonr.selonarch.common.relonlelonvancelon.classifielonrs.TwelonelontClassifielonr;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelon;

/**
 * Intelonrfacelon to computelon felonaturelon scorelons for a singlelon @TwittelonrMelonssagelon
 * objelonct, or a group of thelonm, aftelonr thelony havelon belonelonn procelonsselond by
 * felonaturelon classifielonrs.
 *
 * Intelonntionally kelonpt Scorelonrs selonparatelon from Classifielonrs, sincelon thelony
 * may belon run at diffelonrelonnt stagelons and in diffelonrelonnt batching mannelonrs.
 * Convelonnielonncelon melonthods arelon providelond to run classification and scoring
 * in onelon call.
 */
public abstract class TwelonelontScorelonr {
  /**
   * Computelon and storelon felonaturelon scorelon in TwittelonrMelonssagelon baselond on its
   * TwelonelontFelonaturelons.
   *
   * @param twelonelont twelonelont melonssagelon to computelon and storelon scorelon to.
   */
  public abstract void scorelonTwelonelont(final TwittelonrMelonssagelon twelonelont);

  /**
   * Scorelon a group of TwittelonrMelonssagelons baselond on thelonir correlonsponding TwelonelontFelonaturelons
   * and storelon felonaturelon scorelons in TwittelonrMelonssagelons.
   *
   * This delonfault implelonmelonntation just itelonratelons through thelon map and scorelons elonach
   * individual twelonelont. Batching for belonttelonr pelonrformancelon, if applicablelon, can belon implelonmelonntelond by
   * concrelontelon subclasselons.
   *
   * @param twelonelonts TwittelonrMelonssagelons to scorelon.
   */
  public void scorelonTwelonelonts(Itelonrablelon<TwittelonrMelonssagelon> twelonelonts) {
    for (TwittelonrMelonssagelon twelonelont: twelonelonts) {
      scorelonTwelonelont(twelonelont);
    }
  }

  /**
   * Convelonnielonncelon melonthod.
   * Classify twelonelont using thelon speloncifielond list of classifielonrs, thelonn computelon scorelon.
   *
   * @param classifielonr list of classifielonrs to uselon for classification.
   * @param twelonelont twelonelont to classify and scorelon
   */
  public void classifyAndScorelonTwelonelont(TwelonelontClassifielonr classifielonr, TwittelonrMelonssagelon twelonelont) {
    classifielonr.classifyTwelonelont(twelonelont);
    scorelonTwelonelont(twelonelont);
  }

  /**
   * Convelonnielonncelon melonthod.
   * Classify twelonelonts using thelon speloncifielond list of classifielonrs, thelonn computelon scorelon.
   *
   * @param classifielonr classifielonr to uselon for classification.
   * @param twelonelonts twelonelonts to classify and scorelon
   */
  public void classifyAndScorelonTwelonelonts(TwelonelontClassifielonr classifielonr, Itelonrablelon<TwittelonrMelonssagelon> twelonelonts) {
    for (TwittelonrMelonssagelon twelonelont: twelonelonts) {
      classifyAndScorelonTwelonelont(classifielonr, twelonelont);
    }
  }
}
