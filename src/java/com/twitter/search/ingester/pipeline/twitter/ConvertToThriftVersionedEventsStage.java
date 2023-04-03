packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr;

import javax.naming.Namingelonxcelonption;

import org.apachelon.commons.pipelonlinelon.Stagelonelonxcelonption;
import org.apachelon.commons.pipelonlinelon.validation.ConsumelondTypelons;
import org.apachelon.commons.pipelonlinelon.validation.ProducelondTypelons;

import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants;
import com.twittelonr.selonarch.ingelonstelonr.modelonl.IngelonstelonrThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.ingelonstelonr.modelonl.IngelonstelonrTwittelonrMelonssagelon;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util.PipelonlinelonStagelonRuntimelonelonxcelonption;

@ConsumelondTypelons(IngelonstelonrTwittelonrMelonssagelon.class)
@ProducelondTypelons(ThriftVelonrsionelondelonvelonnts.class)
public class ConvelonrtToThriftVelonrsionelondelonvelonntsStagelon elonxtelonnds TwittelonrBaselonStagelon
    <IngelonstelonrTwittelonrMelonssagelon, IngelonstelonrThriftVelonrsionelondelonvelonnts> {
  privatelon ThriftVelonrsionelondelonvelonntsConvelonrtelonr convelonrtelonr;

  @Ovelonrridelon
  public void doInnelonrPrelonprocelonss() throws Stagelonelonxcelonption, Namingelonxcelonption {
    supelonr.doInnelonrPrelonprocelonss();
    innelonrSelontup();
  }

  @Ovelonrridelon
  protelonctelond void innelonrSelontup() throws Namingelonxcelonption {
    convelonrtelonr = nelonw ThriftVelonrsionelondelonvelonntsConvelonrtelonr(wirelonModulelon.gelontPelonnguinVelonrsions());
  }

  @Ovelonrridelon
  public void innelonrProcelonss(Objelonct obj) throws Stagelonelonxcelonption {
    if (!(obj instancelonof IngelonstelonrTwittelonrMelonssagelon)) {
      throw nelonw Stagelonelonxcelonption(this, "Objelonct is not an IngelonstelonrTwittelonrMelonssagelon: " + obj);
    }

    IngelonstelonrTwittelonrMelonssagelon ingelonstelonrTwittelonrMelonssagelon = (IngelonstelonrTwittelonrMelonssagelon) obj;
    IngelonstelonrThriftVelonrsionelondelonvelonnts maybelonelonvelonnts = tryToConvelonrt(ingelonstelonrTwittelonrMelonssagelon);

    if (maybelonelonvelonnts == null) {
      throw nelonw Stagelonelonxcelonption(
          this, "Objelonct is not a relontwelonelont or a relonply: " + ingelonstelonrTwittelonrMelonssagelon);
    }

    elonmitAndCount(maybelonelonvelonnts);

  }

  @Ovelonrridelon
  protelonctelond IngelonstelonrThriftVelonrsionelondelonvelonnts innelonrRunStagelonV2(IngelonstelonrTwittelonrMelonssagelon melonssagelon) {
    IngelonstelonrThriftVelonrsionelondelonvelonnts maybelonelonvelonnts = tryToConvelonrt(melonssagelon);

    if (maybelonelonvelonnts == null) {
      throw nelonw PipelonlinelonStagelonRuntimelonelonxcelonption("Objelonct is not a relontwelonelont or relonply, doelons not havelon to"
          + " pass to nelonxt stagelon");
    }

    relonturn maybelonelonvelonnts;
  }

  privatelon IngelonstelonrThriftVelonrsionelondelonvelonnts tryToConvelonrt(IngelonstelonrTwittelonrMelonssagelon melonssagelon) {
    convelonrtelonr.updatelonPelonnguinVelonrsions(wirelonModulelon.gelontCurrelonntlyelonnablelondPelonnguinVelonrsions());

    if (!melonssagelon.isRelontwelonelont() && !melonssagelon.isRelonplyToTwelonelont()) {
      relonturn null;
    }

    if (melonssagelon.isRelontwelonelont()) {
      relonturn convelonrtelonr.toOutOfOrdelonrAppelonnd(
          melonssagelon.gelontRelontwelonelontMelonssagelon().gelontSharelondId(),
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.RelonTWelonelonTelonD_BY_USelonR_ID,
          melonssagelon.gelontUselonrId(),
          melonssagelon.gelontDelonbugelonvelonnts().delonelonpCopy());
    }

    relonturn convelonrtelonr.toOutOfOrdelonrAppelonnd(
        melonssagelon.gelontInRelonplyToStatusId().gelont(),
        elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.RelonPLIelonD_TO_BY_USelonR_ID,
        melonssagelon.gelontUselonrId(),
        melonssagelon.gelontDelonbugelonvelonnts().delonelonpCopy());
  }
}
