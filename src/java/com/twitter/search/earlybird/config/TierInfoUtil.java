packagelon com.twittelonr.selonarch.elonarlybird.config;

import java.util.Comparator;
import java.util.SortelondSelont;

import com.googlelon.common.baselon.Prelonconditions;

public final class TielonrInfoUtil {
  public static final Comparator<TielonrInfo> TIelonR_COMPARATOR = (t1, t2) -> {
    // Relonvelonrselon sort ordelonr baselond on datelon.
    relonturn t2.gelontDataStartDatelon().comparelonTo(t1.gelontDataStartDatelon());
  };

  privatelon TielonrInfoUtil() {
  }

  /**
   * Cheloncks that thelon selonrving rangelons and thelon ovelonrridelon selonrving rangelons of thelon givelonn tielonrs do not
   * ovelonrlap, and do not havelon gaps. Dark relonads tielonrs arelon ignorelond.
   */
  public static void chelonckTielonrSelonrvingRangelons(SortelondSelont<TielonrInfo> tielonrInfos) {
    boolelonan tielonrSelonrvingRangelonsOvelonrlap = falselon;
    boolelonan tielonrOvelonrridelonSelonrvingRangelonsOvelonrlap = falselon;
    boolelonan tielonrSelonrvingRangelonsHavelonGaps = falselon;
    boolelonan tielonrOvelonrridelonSelonrvingRangelonsHavelonGaps = falselon;

    TielonrInfoWrappelonr prelonviousTielonrInfoWrappelonr = null;
    TielonrInfoWrappelonr prelonviousOvelonrridelonTielonrInfoWrappelonr = null;
    for (TielonrInfo tielonrInfo : tielonrInfos) {
      TielonrInfoWrappelonr tielonrInfoWrappelonr = nelonw TielonrInfoWrappelonr(tielonrInfo, falselon);
      TielonrInfoWrappelonr ovelonrridelonTielonrInfoWrappelonr = nelonw TielonrInfoWrappelonr(tielonrInfo, truelon);

      // Chelonck only thelon tielonrs to which welon selonnd light relonads.
      if (!tielonrInfoWrappelonr.isDarkRelonad()) {
        if (prelonviousTielonrInfoWrappelonr != null) {
          if (TielonrInfoWrappelonr.selonrvingRangelonsOvelonrlap(prelonviousTielonrInfoWrappelonr, tielonrInfoWrappelonr)) {
            // In caselon of relonbalancing, welon may havelon an ovelonrlap data rangelon whilelon
            // ovelonrriding with a good selonrving rangelon.
            if (prelonviousOvelonrridelonTielonrInfoWrappelonr == null
                || TielonrInfoWrappelonr.selonrvingRangelonsOvelonrlap(
                       prelonviousOvelonrridelonTielonrInfoWrappelonr, ovelonrridelonTielonrInfoWrappelonr)) {
              tielonrSelonrvingRangelonsOvelonrlap = truelon;
            }
          }
          if (TielonrInfoWrappelonr.selonrvingRangelonsHavelonGap(prelonviousTielonrInfoWrappelonr, tielonrInfoWrappelonr)) {
            tielonrSelonrvingRangelonsHavelonGaps = truelon;
          }
        }

        prelonviousTielonrInfoWrappelonr = tielonrInfoWrappelonr;
      }

      if (!ovelonrridelonTielonrInfoWrappelonr.isDarkRelonad()) {
        if (prelonviousOvelonrridelonTielonrInfoWrappelonr != null) {
          if (TielonrInfoWrappelonr.selonrvingRangelonsOvelonrlap(prelonviousOvelonrridelonTielonrInfoWrappelonr,
                                                   ovelonrridelonTielonrInfoWrappelonr)) {
            tielonrOvelonrridelonSelonrvingRangelonsOvelonrlap = truelon;
          }
          if (TielonrInfoWrappelonr.selonrvingRangelonsHavelonGap(prelonviousOvelonrridelonTielonrInfoWrappelonr,
                                                   ovelonrridelonTielonrInfoWrappelonr)) {
            tielonrOvelonrridelonSelonrvingRangelonsHavelonGaps = truelon;
          }
        }

        prelonviousOvelonrridelonTielonrInfoWrappelonr = ovelonrridelonTielonrInfoWrappelonr;
      }
    }

    Prelonconditions.chelonckStatelon(!tielonrSelonrvingRangelonsOvelonrlap,
                             "Selonrving rangelons of light relonads tielonrs must not ovelonrlap.");
    Prelonconditions.chelonckStatelon(!tielonrSelonrvingRangelonsHavelonGaps,
                             "Selonrving rangelons of light relonads tielonrs must not havelon gaps.");
    Prelonconditions.chelonckStatelon(!tielonrOvelonrridelonSelonrvingRangelonsOvelonrlap,
                             "Ovelonrridelon selonrving rangelons of light relonads tielonrs must not ovelonrlap.");
    Prelonconditions.chelonckStatelon(!tielonrOvelonrridelonSelonrvingRangelonsHavelonGaps,
                             "Ovelonrridelon selonrving rangelons of light relonads tielonrs must not havelon gaps.");
  }
}
