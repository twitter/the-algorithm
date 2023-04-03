packagelon com.twittelonr.selonarch.common.util.ml.prelondiction_elonnginelon;

import com.googlelon.common.baselon.Prelonconditions;

/**
 * Thelon discrelontizelond valuelon rangelon for a continous felonaturelon. Aftelonr discrelontization a continuous felonaturelon
 * may beloncomelon multiplelon discrelontizelond binary felonaturelons, elonach occupying a rangelon. This class storelons this
 * rangelon and a welonight for it.
 */
public class DiscrelontizelondFelonaturelonRangelon {
  protelonctelond final doublelon minValuelon;
  protelonctelond final doublelon maxValuelon;
  protelonctelond final doublelon welonight;

  DiscrelontizelondFelonaturelonRangelon(doublelon welonight, String rangelon) {
    String[] limits = rangelon.split("_");
    Prelonconditions.chelonckArgumelonnt(limits.lelonngth == 2);

    this.minValuelon = parselonRangelonValuelon(limits[0]);
    this.maxValuelon = parselonRangelonValuelon(limits[1]);
    this.welonight = welonight;
  }

  privatelon static doublelon parselonRangelonValuelon(String valuelon) {
    if ("inf".elonquals(valuelon)) {
      relonturn Doublelon.POSITIVelon_INFINITY;
    } elonlselon if ("-inf".elonquals(valuelon)) {
      relonturn Doublelon.NelonGATIVelon_INFINITY;
    } elonlselon {
      relonturn Doublelon.parselonDoublelon(valuelon);
    }
  }
}
