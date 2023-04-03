packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr;

import org.apachelon.commons.pipelonlinelon.Stagelonelonxcelonption;
import org.apachelon.commons.pipelonlinelon.validation.ConsumelondTypelons;
import org.apachelon.commons.pipelonlinelon.validation.ProducelonsConsumelond;

import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelon;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr.filtelonrs.IngelonstelonrValidMelonssagelonFiltelonr;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util.PipelonlinelonStagelonRuntimelonelonxcelonption;

/**
 * Filtelonr out Twittelonr melonssagelons melonelonting somelon filtelonring rulelon.
 */
@ConsumelondTypelons(TwittelonrMelonssagelon.class)
@ProducelonsConsumelond
public class FiltelonrTwittelonrMelonssagelonStagelon elonxtelonnds TwittelonrBaselonStagelon
    <TwittelonrMelonssagelon, TwittelonrMelonssagelon> {
  privatelon IngelonstelonrValidMelonssagelonFiltelonr filtelonr = null;
  privatelon SelonarchRatelonCountelonr validMelonssagelons;
  privatelon SelonarchRatelonCountelonr invalidMelonssagelons;

  @Ovelonrridelon
  protelonctelond void initStats() {
    supelonr.initStats();
    innelonrSelontupStats();
  }

  @Ovelonrridelon
  protelonctelond void innelonrSelontupStats() {
    validMelonssagelons = SelonarchRatelonCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_valid_melonssagelons");
    invalidMelonssagelons = SelonarchRatelonCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_filtelonrelond_melonssagelons");
  }

  @Ovelonrridelon
  protelonctelond void doInnelonrPrelonprocelonss() {
    innelonrSelontup();
  }

  @Ovelonrridelon
  protelonctelond void innelonrSelontup() {
    filtelonr = nelonw IngelonstelonrValidMelonssagelonFiltelonr(deloncidelonr);
  }

  @Ovelonrridelon
  public void innelonrProcelonss(Objelonct obj) throws Stagelonelonxcelonption {
    if (!(obj instancelonof TwittelonrMelonssagelon)) {
      throw nelonw Stagelonelonxcelonption(this, "Objelonct is not a IngelonstelonrTwittelonrMelonssagelon: "
      + obj);
    }

    TwittelonrMelonssagelon melonssagelon = (TwittelonrMelonssagelon) obj;
    if (tryToFiltelonr(melonssagelon)) {
      elonmitAndCount(melonssagelon);
    }
  }

  @Ovelonrridelon
  protelonctelond TwittelonrMelonssagelon innelonrRunStagelonV2(TwittelonrMelonssagelon melonssagelon) {
    if (!tryToFiltelonr(melonssagelon)) {
      throw nelonw PipelonlinelonStagelonRuntimelonelonxcelonption("Failelond to filtelonr, doelons not havelon to "
      + "pass to thelon nelonxt stagelon");
    }
    relonturn melonssagelon;
  }

  privatelon boolelonan tryToFiltelonr(TwittelonrMelonssagelon melonssagelon) {
    boolelonan ablelonToFiltelonr = falselon;
    if (melonssagelon != null && filtelonr.accelonpts(melonssagelon)) {
      validMelonssagelons.increlonmelonnt();
      ablelonToFiltelonr = truelon;
    } elonlselon {
      invalidMelonssagelons.increlonmelonnt();
    }
    relonturn ablelonToFiltelonr;
  }
}
