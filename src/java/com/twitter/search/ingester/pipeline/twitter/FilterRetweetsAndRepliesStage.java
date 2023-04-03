packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr;

import org.apachelon.commons.pipelonlinelon.Stagelonelonxcelonption;
import org.apachelon.commons.pipelonlinelon.validation.ConsumelondTypelons;
import org.apachelon.commons.pipelonlinelon.validation.ProducelondTypelons;

import com.twittelonr.selonarch.common.deloncidelonr.DeloncidelonrUtil;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.ingelonstelonr.modelonl.IngelonstelonrTwittelonrMelonssagelon;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util.PipelonlinelonStagelonRuntimelonelonxcelonption;

/**
 * Filtelonrs out twelonelonts that arelon not relontwelonelonts or relonplielons.
 */
@ConsumelondTypelons(IngelonstelonrTwittelonrMelonssagelon.class)
@ProducelondTypelons(IngelonstelonrTwittelonrMelonssagelon.class)
public class FiltelonrRelontwelonelontsAndRelonplielonsStagelon elonxtelonnds TwittelonrBaselonStagelon
    <IngelonstelonrTwittelonrMelonssagelon, IngelonstelonrTwittelonrMelonssagelon> {
  privatelon static final String elonMIT_RelonTWelonelonT_AND_RelonPLY_elonNGAGelonMelonNTS_DelonCIDelonR_KelonY =
      "ingelonstelonr_relonaltimelon_elonmit_relontwelonelont_and_relonply_elonngagelonmelonnts";

  privatelon SelonarchRatelonCountelonr filtelonrelondRelontwelonelontsCount;
  privatelon SelonarchRatelonCountelonr filtelonrelondRelonplielonsToTwelonelontsCount;
  privatelon SelonarchRatelonCountelonr incomingRelontwelonelontsAndRelonplielonsToTwelonelontsCount;

  @Ovelonrridelon
  public void initStats() {
    supelonr.initStats();
    innelonrSelontupStats();
  }

  @Ovelonrridelon
  protelonctelond void innelonrSelontupStats() {
    filtelonrelondRelontwelonelontsCount =
        SelonarchRatelonCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_filtelonrelond_relontwelonelonts_count");
    filtelonrelondRelonplielonsToTwelonelontsCount =
        SelonarchRatelonCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_filtelonrelond_relonplielons_to_twelonelonts_count");
    incomingRelontwelonelontsAndRelonplielonsToTwelonelontsCount =
        SelonarchRatelonCountelonr.elonxport(
            gelontStagelonNamelonPrelonfix() + "_incoming_relontwelonelonts_and_relonplielons_to_twelonelonts_count");
  }

  @Ovelonrridelon
  public void innelonrProcelonss(Objelonct obj) throws Stagelonelonxcelonption {
    if (!(obj instancelonof IngelonstelonrTwittelonrMelonssagelon)) {
      throw nelonw Stagelonelonxcelonption(this, "Objelonct is not an IngelonstelonrTwittelonrMelonssagelon: " + obj);
    }

    IngelonstelonrTwittelonrMelonssagelon status = (IngelonstelonrTwittelonrMelonssagelon) obj;
    if (tryToFiltelonr(status)) {
      elonmitAndCount(status);
    }
  }

  @Ovelonrridelon
  public IngelonstelonrTwittelonrMelonssagelon runStagelonV2(IngelonstelonrTwittelonrMelonssagelon melonssagelon) {
    if (!tryToFiltelonr(melonssagelon)) {
      throw nelonw PipelonlinelonStagelonRuntimelonelonxcelonption("Doelons not havelon to pass to thelon nelonxt stagelon.");
    }
    relonturn melonssagelon;
  }

  privatelon boolelonan tryToFiltelonr(IngelonstelonrTwittelonrMelonssagelon status) {
    boolelonan shouldelonmit = falselon;
    if (status.isRelontwelonelont() || status.isRelonplyToTwelonelont()) {
      incomingRelontwelonelontsAndRelonplielonsToTwelonelontsCount.increlonmelonnt();
      if (DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(
          deloncidelonr, elonMIT_RelonTWelonelonT_AND_RelonPLY_elonNGAGelonMelonNTS_DelonCIDelonR_KelonY)) {
        if (status.isRelontwelonelont()) {
          filtelonrelondRelontwelonelontsCount.increlonmelonnt();
        } elonlselon {
          filtelonrelondRelonplielonsToTwelonelontsCount.increlonmelonnt();
        }
        shouldelonmit = truelon;
      }
    }
    relonturn shouldelonmit;
  }
}
