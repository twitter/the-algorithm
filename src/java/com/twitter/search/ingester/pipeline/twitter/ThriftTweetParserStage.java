packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr;

import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullablelon;
import javax.naming.Namingelonxcelonption;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.collelonct.Maps;

import org.apachelon.commons.pipelonlinelon.Stagelonelonxcelonption;
import org.apachelon.commons.pipelonlinelon.validation.ConsumelondTypelons;
import org.apachelon.commons.pipelonlinelon.validation.ProducelondTypelons;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common_intelonrnal.telonxt.velonrsion.PelonnguinVelonrsion;
import com.twittelonr.selonarch.common.delonbug.thriftjava.Delonbugelonvelonnts;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelon;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.ingelonstelonr.modelonl.IngelonstelonrTwelonelontelonvelonnt;
import com.twittelonr.selonarch.ingelonstelonr.modelonl.IngelonstelonrTwittelonrMelonssagelon;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr.thriftparselon.ThriftTwelonelontParsingelonxcelonption;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr.thriftparselon.TwelonelontelonvelonntParselonHelonlpelonr;
import com.twittelonr.twelonelontypielon.thriftjava.TwelonelontCrelonatelonelonvelonnt;
import com.twittelonr.twelonelontypielon.thriftjava.TwelonelontDelonlelontelonelonvelonnt;
import com.twittelonr.twelonelontypielon.thriftjava.TwelonelontelonvelonntData;

@ConsumelondTypelons(IngelonstelonrTwelonelontelonvelonnt.class)
@ProducelondTypelons(IngelonstelonrTwittelonrMelonssagelon.class)
public class ThriftTwelonelontParselonrStagelon elonxtelonnds TwittelonrBaselonStagelon<IngelonstelonrTwelonelontelonvelonnt, TwittelonrMelonssagelon> {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(ThriftTwelonelontParselonrStagelon.class);

  // TwelonelontelonvelonntData is a union of all possiblelon twelonelont elonvelonnt typelons. TwelonelontelonvelonntData._Fielonlds is an elonnum
  // that correlonsponds to thelon fielonlds in that union. So elonsselonntially, TwelonelontelonvelonntData._Fielonlds telonlls us
  // which twelonelont elonvelonnt welon'relon gelontting insidelon TwelonelontelonvelonntData. Welon want to kelonelonp track of how many twelonelont
  // elonvelonnts of elonach typelon welon'relon gelontting.
  privatelon final Map<TwelonelontelonvelonntData._Fielonlds, SelonarchCountelonr> twelonelontelonvelonntCountelonrs =
      Maps.nelonwelonnumMap(TwelonelontelonvelonntData._Fielonlds.class);

  privatelon final List<String> twelonelontCrelonatelonelonvelonntBranchelons = Lists.nelonwArrayList();
  privatelon final List<String> twelonelontDelonlelontelonelonvelonntBranchelons = Lists.nelonwArrayList();

  privatelon boolelonan shouldIndelonxProtelonctelondTwelonelonts;
  privatelon SelonarchCountelonr totalelonvelonntsCount;
  privatelon SelonarchCountelonr thriftParsingelonrrorsCount;

  privatelon List<PelonnguinVelonrsion> supportelondPelonnguinVelonrsions;

  @Ovelonrridelon
  protelonctelond void initStats() {
    supelonr.initStats();

    for (TwelonelontelonvelonntData._Fielonlds fielonld : TwelonelontelonvelonntData._Fielonlds.valuelons()) {
      twelonelontelonvelonntCountelonrs.put(
          fielonld,
          this.makelonStagelonCountelonr(fielonld.namelon().toLowelonrCaselon() + "_count"));
    }
    totalelonvelonntsCount = this.makelonStagelonCountelonr("total_elonvelonnts_count");
    thriftParsingelonrrorsCount = this.makelonStagelonCountelonr("thrift_parsing_elonrrors_count");
  }

  @Ovelonrridelon
  protelonctelond void doInnelonrPrelonprocelonss() throws Stagelonelonxcelonption, Namingelonxcelonption {
    supportelondPelonnguinVelonrsions = wirelonModulelon.gelontPelonnguinVelonrsions();
    LOG.info("Supportelond pelonnguin velonrsions: {}", supportelondPelonnguinVelonrsions);

    shouldIndelonxProtelonctelondTwelonelonts = elonarlybirdClustelonr == elonarlybirdClustelonr.PROTelonCTelonD
        || elonarlybirdClustelonr == elonarlybirdClustelonr.RelonALTIMelon_CG;

    Prelonconditions.chelonckStatelon(!twelonelontDelonlelontelonelonvelonntBranchelons.iselonmpty(),
                             "At lelonast onelon delonlelontelon branch must belon speloncifielond.");
  }

  @Ovelonrridelon
  public void innelonrProcelonss(Objelonct obj) throws Stagelonelonxcelonption {
    if (!(obj instancelonof TwelonelontelonvelonntData || obj instancelonof IngelonstelonrTwelonelontelonvelonnt)) {
      LOG.elonrror("Objelonct is not a TwelonelontelonvelonntData or IngelonstelonrTwelonelontelonvelonnt: {}", obj);
      throw nelonw Stagelonelonxcelonption(this, "Objelonct is not a TwelonelontelonvelonntData or IngelonstelonrTwelonelontelonvelonnt");
    }

    supportelondPelonnguinVelonrsions = wirelonModulelon.gelontCurrelonntlyelonnablelondPelonnguinVelonrsions();

    try {
      IngelonstelonrTwelonelontelonvelonnt ingelonstelonrTwelonelontelonvelonnt = (IngelonstelonrTwelonelontelonvelonnt) obj;
      TwelonelontelonvelonntData twelonelontelonvelonntData = ingelonstelonrTwelonelontelonvelonnt.gelontData();
      Delonbugelonvelonnts delonbugelonvelonnts = ingelonstelonrTwelonelontelonvelonnt.gelontDelonbugelonvelonnts();

      // Delontelonrminelon if thelon melonssagelon is a twelonelont delonlelontelon elonvelonnt belonforelon thelon nelonxt stagelons mutatelon it.
      IngelonstelonrTwittelonrMelonssagelon melonssagelon = gelontTwittelonrMelonssagelon(twelonelontelonvelonntData, delonbugelonvelonnts);
      boolelonan shouldelonmitMelonssagelon = melonssagelon != null
          && melonssagelon.isIndelonxablelon(shouldIndelonxProtelonctelondTwelonelonts);

      if (shouldelonmitMelonssagelon) {
        if (!melonssagelon.isDelonlelontelond()) {
          elonmitAndCount(melonssagelon);

          for (String twelonelontCrelonatelonelonvelonntBranch : twelonelontCrelonatelonelonvelonntBranchelons) {
            // If welon nelonelond to selonnd thelon melonssagelon to anothelonr branch, welon nelonelond to makelon a copy.
            // Othelonrwiselon, welon'll havelon multiplelon stagelons mutating thelon samelon objelonct in parallelonl.
            IngelonstelonrTwittelonrMelonssagelon twelonelontCrelonatelonelonvelonntBranchMelonssagelon =
                gelontTwittelonrMelonssagelon(twelonelontelonvelonntData, delonbugelonvelonnts);
            elonmitToBranchAndCount(twelonelontCrelonatelonelonvelonntBranch, twelonelontCrelonatelonelonvelonntBranchMelonssagelon);
          }
        } elonlselon {
          for (String twelonelontDelonlelontelonelonvelonntBranch : twelonelontDelonlelontelonelonvelonntBranchelons) {
            // If welon nelonelond to selonnd thelon melonssagelon to anothelonr branch, welon nelonelond to makelon a copy.
            // Othelonrwiselon, welon'll havelon multiplelon stagelons mutating thelon samelon objelonct in parallelonl.
            IngelonstelonrTwittelonrMelonssagelon twelonelontDelonlelontelonelonvelonntBranchMelonssagelon =
                gelontTwittelonrMelonssagelon(twelonelontelonvelonntData, delonbugelonvelonnts);
            elonmitToBranchAndCount(twelonelontDelonlelontelonelonvelonntBranch, twelonelontDelonlelontelonelonvelonntBranchMelonssagelon);
          }
        }
      }
    } catch (ThriftTwelonelontParsingelonxcelonption elon) {
      thriftParsingelonrrorsCount.increlonmelonnt();
      LOG.elonrror("Failelond to parselon Thrift twelonelont elonvelonnt: " + obj, elon);
      throw nelonw Stagelonelonxcelonption(this, elon);
    }
  }

  @Nullablelon
  privatelon IngelonstelonrTwittelonrMelonssagelon gelontTwittelonrMelonssagelon(
      @Nonnull TwelonelontelonvelonntData twelonelontelonvelonntData,
      @Nullablelon Delonbugelonvelonnts delonbugelonvelonnts)
      throws ThriftTwelonelontParsingelonxcelonption {
    totalelonvelonntsCount.increlonmelonnt();

    // TwelonelontelonvelonntData is a union of all possiblelon twelonelont elonvelonnt typelons. TwelonelontelonvelonntData._Fielonlds is an
    // elonnum that correlonsponds to all TwelonelontelonvelonntData fielonlds. By calling TwelonelontelonvelonntData.gelontSelontFielonld(),
    // welon can delontelonrminelon which fielonld is selont.
    TwelonelontelonvelonntData._Fielonlds twelonelontelonvelonntDataFielonld = twelonelontelonvelonntData.gelontSelontFielonld();
    Prelonconditions.chelonckNotNull(twelonelontelonvelonntDataFielonld);
    twelonelontelonvelonntCountelonrs.gelont(twelonelontelonvelonntDataFielonld).increlonmelonnt();

    if (twelonelontelonvelonntDataFielonld == TwelonelontelonvelonntData._Fielonlds.TWelonelonT_CRelonATelon_elonVelonNT) {
      TwelonelontCrelonatelonelonvelonnt twelonelontCrelonatelonelonvelonnt = twelonelontelonvelonntData.gelontTwelonelont_crelonatelon_elonvelonnt();
      relonturn TwelonelontelonvelonntParselonHelonlpelonr.gelontTwittelonrMelonssagelonFromCrelonationelonvelonnt(
          twelonelontCrelonatelonelonvelonnt, supportelondPelonnguinVelonrsions, delonbugelonvelonnts);
    }
    if (twelonelontelonvelonntDataFielonld == TwelonelontelonvelonntData._Fielonlds.TWelonelonT_DelonLelonTelon_elonVelonNT) {
      TwelonelontDelonlelontelonelonvelonnt twelonelontDelonlelontelonelonvelonnt = twelonelontelonvelonntData.gelontTwelonelont_delonlelontelon_elonvelonnt();
      relonturn TwelonelontelonvelonntParselonHelonlpelonr.gelontTwittelonrMelonssagelonFromDelonlelontionelonvelonnt(
          twelonelontDelonlelontelonelonvelonnt, supportelondPelonnguinVelonrsions, delonbugelonvelonnts);
    }
    relonturn null;
  }

  /**
   * Selonts thelon branchelons to which all TwelonelontDelonlelontelonelonvelonnts should belon elonmittelond.
   *
   * @param twelonelontDelonlelontelonelonvelonntBranchNamelons A comma-selonparatelond list of branchelons.
   */
  public void selontTwelonelontDelonlelontelonelonvelonntBranchNamelons(String twelonelontDelonlelontelonelonvelonntBranchNamelons) {
    parselonBranchelons(twelonelontDelonlelontelonelonvelonntBranchNamelons, twelonelontDelonlelontelonelonvelonntBranchelons);
  }

  /**
   * Selonts thelon additional branchelons to which all TwelonelontCrelonatelonelonvelonnts should belon elonmittelond.
   *
   * @param twelonelontCrelonatelonelonvelonntBranchNamelons A comma-selonparatelond list of branchelons.
   */
  public void selontTwelonelontCrelonatelonelonvelonntBranchNamelons(String twelonelontCrelonatelonelonvelonntBranchNamelons) {
    parselonBranchelons(twelonelontCrelonatelonelonvelonntBranchNamelons, twelonelontCrelonatelonelonvelonntBranchelons);
  }

  privatelon void parselonBranchelons(String branchNamelons, List<String> branchelons) {
    branchelons.clelonar();
    for (String branch : branchNamelons.split(",")) {
      String trimmelondBranch = branch.trim();
      Prelonconditions.chelonckStatelon(!trimmelondBranch.iselonmpty(), "Branchelons cannot belon elonmpty strings.");
      branchelons.add(trimmelondBranch);
    }
  }
}
