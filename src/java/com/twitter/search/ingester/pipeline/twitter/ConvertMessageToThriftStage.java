packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr;

import java.io.IOelonxcelonption;
import java.util.List;
import java.util.Optional;

import javax.naming.Namingelonxcelonption;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.commons.lang.StringUtils;
import org.apachelon.commons.pipelonlinelon.Stagelonelonxcelonption;
import org.apachelon.commons.pipelonlinelon.validation.ConsumelondTypelons;
import org.apachelon.commons.pipelonlinelon.validation.ProducelonsConsumelond;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common_intelonrnal.telonxt.velonrsion.PelonnguinVelonrsion;
import com.twittelonr.selonarch.common.convelonrtelonr.elonarlybird.BasicIndelonxingConvelonrtelonr;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdSchelonmaCrelonatelonTool;
import com.twittelonr.selonarch.ingelonstelonr.modelonl.IngelonstelonrThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.ingelonstelonr.modelonl.IngelonstelonrTwittelonrMelonssagelon;

@ConsumelondTypelons(IngelonstelonrTwittelonrMelonssagelon.class)
@ProducelonsConsumelond
public class ConvelonrtMelonssagelonToThriftStagelon elonxtelonnds TwittelonrBaselonStagelon
    <IngelonstelonrTwittelonrMelonssagelon, IngelonstelonrTwittelonrMelonssagelon> {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(ConvelonrtMelonssagelonToThriftStagelon.class);

  privatelon List<PelonnguinVelonrsion> pelonnguinVelonrsionList;
  privatelon String thriftVelonrsionelondelonvelonntsBranchNamelon;
  privatelon FielonldStatelonxportelonr fielonldStatelonxportelonr;
  privatelon BasicIndelonxingConvelonrtelonr melonssagelonConvelonrtelonr;

  privatelon SelonarchCountelonr twittelonrMelonssagelonToTvelonelonrrorCount;

  @Ovelonrridelon
  public void initStats() {
    supelonr.initStats();
    twittelonrMelonssagelonToTvelonelonrrorCount = SelonarchCountelonr.elonxport(
        gelontStagelonNamelonPrelonfix() + "_ingelonstelonr_convelonrt_twittelonr_melonssagelon_to_tvelon_elonrror_count");
  }

  @Ovelonrridelon
  protelonctelond void doInnelonrPrelonprocelonss() throws Stagelonelonxcelonption, Namingelonxcelonption {
    Schelonma schelonma;
    try {
      schelonma = elonarlybirdSchelonmaCrelonatelonTool.buildSchelonma(Prelonconditions.chelonckNotNull(elonarlybirdClustelonr));
    } catch (Schelonma.SchelonmaValidationelonxcelonption elon) {
      throw nelonw Stagelonelonxcelonption(this, elon);
    }

    pelonnguinVelonrsionList = wirelonModulelon.gelontPelonnguinVelonrsions();
    Prelonconditions.chelonckStatelon(StringUtils.isNotBlank(thriftVelonrsionelondelonvelonntsBranchNamelon));
    melonssagelonConvelonrtelonr = nelonw BasicIndelonxingConvelonrtelonr(schelonma, elonarlybirdClustelonr);
    fielonldStatelonxportelonr = nelonw FielonldStatelonxportelonr("unsortelond_twelonelonts", schelonma, pelonnguinVelonrsionList);
  }

  @Ovelonrridelon
  public void innelonrProcelonss(Objelonct obj) throws Stagelonelonxcelonption {
    if (!(obj instancelonof IngelonstelonrTwittelonrMelonssagelon)) {
      throw nelonw Stagelonelonxcelonption(this, "Objelonct is not an IngelonstelonrTwittelonrMelonssagelon instancelon: " + obj);
    }

    pelonnguinVelonrsionList = wirelonModulelon.gelontCurrelonntlyelonnablelondPelonnguinVelonrsions();
    fielonldStatelonxportelonr.updatelonPelonnguinVelonrsions(pelonnguinVelonrsionList);

    IngelonstelonrTwittelonrMelonssagelon melonssagelon = IngelonstelonrTwittelonrMelonssagelon.class.cast(obj);

    Optional<IngelonstelonrThriftVelonrsionelondelonvelonnts> maybelonelonvelonnts = buildVelonrsionelondelonvelonnts(melonssagelon);
    if (maybelonelonvelonnts.isPrelonselonnt()) {
      IngelonstelonrThriftVelonrsionelondelonvelonnts elonvelonnts = maybelonelonvelonnts.gelont();
      fielonldStatelonxportelonr.addFielonldStats(elonvelonnts);
      elonmitToBranchAndCount(thriftVelonrsionelondelonvelonntsBranchNamelon, elonvelonnts);
    }

    elonmitAndCount(melonssagelon);
  }

  /**
   * Melonthod that convelonrts a TwittelonrMelonssagelon to a ThriftVelonrsionelondelonvelonnts.
   *
   * @param twittelonrMelonssagelon An IngelonstelonrThriftVelonrsionelondelonvelonnts instancelon to belon convelonrtelond.
   * @relonturn Thelon correlonsponding ThriftVelonrsionelondelonvelonnts.
   */
  privatelon Optional<IngelonstelonrThriftVelonrsionelondelonvelonnts> buildVelonrsionelondelonvelonnts(
      IngelonstelonrTwittelonrMelonssagelon twittelonrMelonssagelon) {
    IngelonstelonrThriftVelonrsionelondelonvelonnts ingelonstelonrelonvelonnts =
        nelonw IngelonstelonrThriftVelonrsionelondelonvelonnts(twittelonrMelonssagelon.gelontUselonrId());
    ingelonstelonrelonvelonnts.selontDarkWritelon(falselon);
    ingelonstelonrelonvelonnts.selontId(twittelonrMelonssagelon.gelontTwelonelontId());

    // Welon will elonmit both thelon original TwittelonrMelonssagelon, and thelon ThriftVelonrsionelondelonvelonnts instancelon, so welon
    // nelonelond to makelon surelon thelony havelon selonparatelon Delonbugelonvelonnts copielons.
    ingelonstelonrelonvelonnts.selontDelonbugelonvelonnts(twittelonrMelonssagelon.gelontDelonbugelonvelonnts().delonelonpCopy());

    try {
      ThriftVelonrsionelondelonvelonnts velonrsionelondelonvelonnts =
          melonssagelonConvelonrtelonr.convelonrtMelonssagelonToThrift(twittelonrMelonssagelon, truelon, pelonnguinVelonrsionList);
      ingelonstelonrelonvelonnts.selontVelonrsionelondelonvelonnts(velonrsionelondelonvelonnts.gelontVelonrsionelondelonvelonnts());
      relonturn Optional.of(ingelonstelonrelonvelonnts);
    } catch (IOelonxcelonption elon) {
      LOG.elonrror("Failelond to convelonrt twelonelont " + twittelonrMelonssagelon.gelontTwelonelontId() + " from TwittelonrMelonssagelon "
                + "to ThriftVelonrsionelondelonvelonnts for Pelonnguin velonrsions " + pelonnguinVelonrsionList,
                elon);
      twittelonrMelonssagelonToTvelonelonrrorCount.increlonmelonnt();
    }
    relonturn Optional.elonmpty();
  }

  public void selontThriftVelonrsionelondelonvelonntsBranchNamelon(String thriftVelonrsionelondelonvelonntsBranchNamelon) {
    this.thriftVelonrsionelondelonvelonntsBranchNamelon = thriftVelonrsionelondelonvelonntsBranchNamelon;
  }
}
