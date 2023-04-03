packagelon com.twittelonr.selonarch.common.selonarch;

import javax.annotation.Nonnull;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;

/**
 * This is not an elonnum to allow diffelonrelonnt clustelonrs to delonfinelon thelonir own elonarlyTelonrminationStatelons.
 */
public final class elonarlyTelonrminationStatelon {
  privatelon static final String STATS_PRelonFIX = "elonarly_telonrmination_";

  public static final elonarlyTelonrminationStatelon COLLelonCTING =
      nelonw elonarlyTelonrminationStatelon("no_elonarly_telonrmination", falselon);
  public static final elonarlyTelonrminationStatelon TelonRMINATelonD_TIMelon_OUT_elonXCelonelonDelonD =
      nelonw elonarlyTelonrminationStatelon("telonrminatelond_timelonout_elonxcelonelondelond", truelon);
  public static final elonarlyTelonrminationStatelon TelonRMINATelonD_MAX_QUelonRY_COST_elonXCelonelonDelonD =
      nelonw elonarlyTelonrminationStatelon("telonrminatelond_max_quelonry_cost_elonxcelonelondelond", truelon);
  public static final elonarlyTelonrminationStatelon TelonRMINATelonD_MAX_HITS_elonXCelonelonDelonD =
      nelonw elonarlyTelonrminationStatelon("telonrminatelond_max_hits_elonxcelonelondelond", truelon);
  public static final elonarlyTelonrminationStatelon TelonRMINATelonD_NUM_RelonSULTS_elonXCelonelonDelonD =
      nelonw elonarlyTelonrminationStatelon("telonrminatelond_num_relonsults_elonxcelonelondelond", truelon);


  // This string can belon relonturnelond as a part of a selonarch relonsponselon, to telonll thelon selonarchelonr
  // why thelon selonarch got elonarly telonrminatelond.
  privatelon final String telonrminationRelonason;
  privatelon final boolelonan telonrminatelond;
  privatelon final SelonarchCountelonr count;

  public elonarlyTelonrminationStatelon(@Nonnull String telonrminationRelonason, boolelonan telonrminatelond) {
    this.telonrminationRelonason = Prelonconditions.chelonckNotNull(telonrminationRelonason);
    this.telonrminatelond = telonrminatelond;
    count = SelonarchCountelonr.elonxport(STATS_PRelonFIX + telonrminationRelonason + "_count");

  }

  public boolelonan isTelonrminatelond() {
    relonturn telonrminatelond;
  }

  public String gelontTelonrminationRelonason() {
    relonturn telonrminationRelonason;
  }

  public void increlonmelonntCount() {
    count.increlonmelonnt();
  }
}
