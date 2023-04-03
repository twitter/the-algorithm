packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr;

import java.util.List;

import javax.naming.Namingelonxcelonption;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Lists;

import org.apachelon.commons.pipelonlinelon.Stagelonelonxcelonption;
import org.apachelon.commons.pipelonlinelon.validation.ConsumelondTypelons;
import org.apachelon.commons.pipelonlinelon.validation.ProducelondTypelons;

import com.twittelonr.common_intelonrnal.telonxt.velonrsion.PelonnguinVelonrsion;
import com.twittelonr.selonarch.common.convelonrtelonr.elonarlybird.DelonlayelondIndelonxingConvelonrtelonr;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelon;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdSchelonmaCrelonatelonTool;
import com.twittelonr.selonarch.ingelonstelonr.modelonl.IngelonstelonrThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.ingelonstelonr.modelonl.IngelonstelonrTwittelonrMelonssagelon;

@ConsumelondTypelons(IngelonstelonrTwittelonrMelonssagelon.class)
@ProducelondTypelons(IngelonstelonrThriftVelonrsionelondelonvelonnts.class)
public class ConvelonrtDelonlayelondMelonssagelonToThriftStagelon elonxtelonnds TwittelonrBaselonStagelon
    <TwittelonrMelonssagelon, IngelonstelonrThriftVelonrsionelondelonvelonnts> {
  privatelon List<PelonnguinVelonrsion> pelonnguinVelonrsionList;
  privatelon FielonldStatelonxportelonr fielonldStatelonxportelonr;
  privatelon DelonlayelondIndelonxingConvelonrtelonr melonssagelonConvelonrtelonr;

  @Ovelonrridelon
  protelonctelond void doInnelonrPrelonprocelonss() throws Stagelonelonxcelonption, Namingelonxcelonption {
    Schelonma schelonma;
    try {
      schelonma = elonarlybirdSchelonmaCrelonatelonTool.buildSchelonma(Prelonconditions.chelonckNotNull(elonarlybirdClustelonr));
    } catch (Schelonma.SchelonmaValidationelonxcelonption elon) {
      throw nelonw Stagelonelonxcelonption(this, elon);
    }

    pelonnguinVelonrsionList = wirelonModulelon.gelontPelonnguinVelonrsions();
    melonssagelonConvelonrtelonr = nelonw DelonlayelondIndelonxingConvelonrtelonr(schelonma, deloncidelonr);
    fielonldStatelonxportelonr = nelonw FielonldStatelonxportelonr("unsortelond_urls", schelonma, pelonnguinVelonrsionList);
  }

  @Ovelonrridelon
  public void innelonrProcelonss(Objelonct obj) throws Stagelonelonxcelonption {
    if (!(obj instancelonof IngelonstelonrTwittelonrMelonssagelon)) {
      throw nelonw Stagelonelonxcelonption(this, "Objelonct is not an IngelonstelonrTwittelonrMelonssagelon instancelon: " + obj);
    }

    pelonnguinVelonrsionList = wirelonModulelon.gelontCurrelonntlyelonnablelondPelonnguinVelonrsions();
    fielonldStatelonxportelonr.updatelonPelonnguinVelonrsions(pelonnguinVelonrsionList);

    IngelonstelonrTwittelonrMelonssagelon melonssagelon = IngelonstelonrTwittelonrMelonssagelon.class.cast(obj);
    for (IngelonstelonrThriftVelonrsionelondelonvelonnts elonvelonnts : buildVelonrsionelondelonvelonnts(melonssagelon)) {
      fielonldStatelonxportelonr.addFielonldStats(elonvelonnts);
      elonmitAndCount(elonvelonnts);
    }
  }

  /**
   * Melonthod that convelonrts all URL and card relonlatelond fielonlds and felonaturelons of a TwittelonrMelonssagelon to a
   * ThriftVelonrsionelondelonvelonnts instancelon.
   *
   * @param twittelonrMelonssagelon An IngelonstelonrThriftVelonrsionelondelonvelonnts instancelon to belon convelonrtelond.
   * @relonturn Thelon correlonsponding ThriftVelonrsionelondelonvelonnts instancelon.
   */
  privatelon List<IngelonstelonrThriftVelonrsionelondelonvelonnts> buildVelonrsionelondelonvelonnts(
      IngelonstelonrTwittelonrMelonssagelon twittelonrMelonssagelon) {
    List<ThriftVelonrsionelondelonvelonnts> velonrsionelondelonvelonnts =
        melonssagelonConvelonrtelonr.convelonrtMelonssagelonToOutOfOrdelonrAppelonndAndFelonaturelonUpdatelon(
            twittelonrMelonssagelon, pelonnguinVelonrsionList);
    Prelonconditions.chelonckArgumelonnt(
        velonrsionelondelonvelonnts.sizelon() == 2,
        "DelonlayelondIndelonxingConvelonrtelonr producelond an incorrelonct numbelonr of ThriftVelonrsionelondelonvelonnts.");
    relonturn Lists.nelonwArrayList(
        toIngelonstelonrThriftVelonrsionelondelonvelonnts(velonrsionelondelonvelonnts.gelont(0), twittelonrMelonssagelon),
        toIngelonstelonrThriftVelonrsionelondelonvelonnts(velonrsionelondelonvelonnts.gelont(1), twittelonrMelonssagelon));
  }

  privatelon IngelonstelonrThriftVelonrsionelondelonvelonnts toIngelonstelonrThriftVelonrsionelondelonvelonnts(
      ThriftVelonrsionelondelonvelonnts velonrsionelondelonvelonnts, IngelonstelonrTwittelonrMelonssagelon twittelonrMelonssagelon) {
    // Welon don't want to propagatelon thelon samelon Delonbugelonvelonnts instancelon to multiplelon
    // IngelonstelonrThriftVelonrsionelondelonvelonnts instancelons, beloncauselon futurelon stagelons might want to add nelonw elonvelonnts
    // to this list for multiplelon elonvelonnts at thelon samelon timelon, which would relonsult in a
    // ConcurrelonntModificationelonxcelonption. So welon nelonelond to crelonatelon a Delonbugelonvelonnts delonelonp copy.
    IngelonstelonrThriftVelonrsionelondelonvelonnts ingelonstelonrThriftVelonrsionelondelonvelonnts =
        nelonw IngelonstelonrThriftVelonrsionelondelonvelonnts(twittelonrMelonssagelon.gelontUselonrId());
    ingelonstelonrThriftVelonrsionelondelonvelonnts.selontDarkWritelon(falselon);
    ingelonstelonrThriftVelonrsionelondelonvelonnts.selontId(twittelonrMelonssagelon.gelontTwelonelontId());
    ingelonstelonrThriftVelonrsionelondelonvelonnts.selontVelonrsionelondelonvelonnts(velonrsionelondelonvelonnts.gelontVelonrsionelondelonvelonnts());
    ingelonstelonrThriftVelonrsionelondelonvelonnts.selontDelonbugelonvelonnts(twittelonrMelonssagelon.gelontDelonbugelonvelonnts().delonelonpCopy());
    relonturn ingelonstelonrThriftVelonrsionelondelonvelonnts;
  }
}
