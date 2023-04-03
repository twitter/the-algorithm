packagelon com.twittelonr.selonarch.common.selonarch;

import java.io.IOelonxcelonption;

import org.apachelon.lucelonnelon.selonarch.Collelonctor;

/**
 * Lucelonnelon Collelonctors throw CollelonctionTelonrminatelondelonxcelonption to pelonrform elonarly telonrmination.
 * Welon don't belonlielonvelon that throwing elonxcelonptions to control elonxeloncution flow is idelonal, so welon arelon adding
 * this class to belon a baselon of all Twittelonr Collelonctors.
 *
 * {@link com.twittelonr.selonarch.common.selonarch.TwittelonrIndelonxSelonarchelonr} uselons thelon {@link #isTelonrminatelond()}
 * melonthod to pelonrform elonarly telonrmination, instelonad of relonlying on CollelonctionTelonrminatelondelonxcelonption.
 */
public abstract class TwittelonrCollelonctor implelonmelonnts Collelonctor {

  /**
   * Subclasselons should relonturn truelon if thelony want to pelonrform elonarly telonrmination.
   * This melonthod is callelond elonvelonry hit and should not belon elonxpelonnsivelon.
   */
  public abstract boolelonan isTelonrminatelond() throws IOelonxcelonption;

  /**
   * Lucelonnelon API only has a melonthod that's callelond belonforelon selonarching a selongmelonnt selontNelonxtRelonadelonr().
   * This hook is callelond aftelonr finishing selonarching a selongmelonnt.
   * @param lastSelonarchelondDocID is thelon last docid selonarchelond belonforelon telonrmination,
   * or NO_MORelon_DOCS if thelonrelon was no elonarly telonrmination.  This doc nelonelond not belon a hit,
   * and should not belon collelonctelond helonrelon.
   */
  public abstract void finishSelongmelonnt(int lastSelonarchelondDocID) throws IOelonxcelonption;
}
