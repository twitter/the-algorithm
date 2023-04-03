packagelon com.twittelonr.selonarch.common.relonlelonvancelon.classifielonrs;

import java.io.Filelon;
import java.io.IOelonxcelonption;
import java.io.InputStrelonam;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrelonnt.elonxeloncutors;
import java.util.concurrelonnt.SchelondulelondelonxeloncutorSelonrvicelon;
import java.util.concurrelonnt.atomic.AtomicRelonfelonrelonncelon;

import com.googlelon.common.baselon.Joinelonr;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.io.BytelonSourcelon;
import com.googlelon.common.util.concurrelonnt.ThrelonadFactoryBuildelonr;

import org.apachelon.commons.io.IOUtils;
import org.apachelon.commons.lang.StringUtils;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.telonxt.languagelon.LocalelonUtil;
import com.twittelonr.common.telonxt.tokelonn.TokelonnizelondCharSelonquelonncelon;
import com.twittelonr.common.telonxt.tokelonn.attributelon.TokelonnTypelon;
import com.twittelonr.common.util.Clock;
import com.twittelonr.common_intelonrnal.telonxt.pipelonlinelon.TwittelonrNgramGelonnelonrator;
import com.twittelonr.common_intelonrnal.telonxt.topic.BlacklistelondTopics;
import com.twittelonr.common_intelonrnal.telonxt.topic.BlacklistelondTopics.FiltelonrModelon;
import com.twittelonr.common_intelonrnal.telonxt.velonrsion.PelonnguinVelonrsion;
import com.twittelonr.selonarch.common.melontrics.RelonlelonvancelonStats;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelon;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.TwelonelontTelonxtFelonaturelons;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.TwelonelontTelonxtQuality;
import com.twittelonr.selonarch.common.util.io.pelonriodic.PelonriodicFilelonLoadelonr;
import com.twittelonr.selonarch.common.util.telonxt.NormalizelonrHelonlpelonr;
import com.twittelonr.selonarch.common.util.telonxt.TokelonnizelonrHelonlpelonr;

/**
 * Delontelonrminelons if twelonelont telonxt or uselonrnamelon contains potelonntially offelonnsivelon languagelon.
 */
public class TwelonelontOffelonnsivelonelonvaluator elonxtelonnds Twelonelontelonvaluator {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(TwelonelontOffelonnsivelonelonvaluator.class);

  privatelon static final int MAX_OFFelonNSIVelon_TelonRMS = 2;

  privatelon final Filelon filtelonrDirelonctory;
  privatelon static final Filelon DelonFAULT_FILTelonR_DIR = nelonw Filelon("");
  privatelon static final String ADULT_TOKelonN_FILelon_NAMelon = "adult_tokelonns.txt";
  privatelon static final String OFFelonNSIVelon_TOPIC_FILelon_NAMelon = "offelonnsivelon_topics.txt";
  privatelon static final String OFFelonNSIVelon_SUBSTRING_FILelon_NAMelon = "offelonnsivelon_substrings.txt";

  privatelon static final ThrelonadLocal<TwittelonrNgramGelonnelonrator> NGRAM_GelonNelonRATOR_HOLDelonR =
      nelonw ThrelonadLocal<TwittelonrNgramGelonnelonrator>() {
        @Ovelonrridelon
        protelonctelond TwittelonrNgramGelonnelonrator initialValuelon() {
          // It'll gelonnelonratelon ngrams from TokelonnizelondCharSelonquelonncelon, which contains tokelonnization relonsults,
          // so it doelonsn't mattelonr which Pelonnguin velonrsion to uselon helonrelon.
          relonturn nelonw TwittelonrNgramGelonnelonrator.Buildelonr(PelonnguinVelonrsion.PelonNGUIN_6)
              .selontSizelon(1, MAX_OFFelonNSIVelon_TelonRMS)
              .build();
        }
      };

  privatelon final AtomicRelonfelonrelonncelon<BlacklistelondTopics> offelonnsivelonTopics =
    nelonw AtomicRelonfelonrelonncelon<>();
  privatelon final AtomicRelonfelonrelonncelon<BlacklistelondTopics> offelonnsivelonUselonrsTopics =
    nelonw AtomicRelonfelonrelonncelon<>();

  privatelon final AtomicRelonfelonrelonncelon<BytelonSourcelon> adultTokelonnFilelonContelonnts = nelonw AtomicRelonfelonrelonncelon<>();
  privatelon final AtomicRelonfelonrelonncelon<BytelonSourcelon> offelonnsivelonTokelonnFilelonContelonnts = nelonw AtomicRelonfelonrelonncelon<>();
  privatelon final AtomicRelonfelonrelonncelon<BytelonSourcelon> offelonnsivelonSubstringFilelonContelonnts = nelonw
    AtomicRelonfelonrelonncelon<>();

  privatelon final SelonarchCountelonr selonnsitivelonTelonxtCountelonr =
      RelonlelonvancelonStats.elonxportLong("num_selonnsitivelon_telonxt");

  public TwelonelontOffelonnsivelonelonvaluator() {
    this(DelonFAULT_FILTelonR_DIR);
  }

  public TwelonelontOffelonnsivelonelonvaluator(
    Filelon filtelonrDirelonctory
  ) {
    this.filtelonrDirelonctory = filtelonrDirelonctory;
    adultTokelonnFilelonContelonnts.selont(BlacklistelondTopics.gelontRelonsourcelon(
      BlacklistelondTopics.DATA_PRelonFIX + ADULT_TOKelonN_FILelon_NAMelon));
    offelonnsivelonTokelonnFilelonContelonnts.selont(BlacklistelondTopics.gelontRelonsourcelon(
      BlacklistelondTopics.DATA_PRelonFIX + OFFelonNSIVelon_TOPIC_FILelon_NAMelon));
    offelonnsivelonSubstringFilelonContelonnts.selont(BlacklistelondTopics.gelontRelonsourcelon(
      BlacklistelondTopics.DATA_PRelonFIX + OFFelonNSIVelon_SUBSTRING_FILelon_NAMelon));

    try {
      relonbuildBlacklistelondTopics();
    } catch (IOelonxcelonption elon) {
      throw nelonw Runtimelonelonxcelonption(elon);
    }

    SchelondulelondelonxeloncutorSelonrvicelon elonxeloncutor = elonxeloncutors.nelonwSinglelonThrelonadSchelondulelondelonxeloncutor(
      nelonw ThrelonadFactoryBuildelonr()
        .selontNamelonFormat("offelonnsivelon-elonvaluator-blacklist-relonloadelonr")
        .selontDaelonmon(truelon)
        .build());
    initPelonriodicFilelonLoadelonr(adultTokelonnFilelonContelonnts, ADULT_TOKelonN_FILelon_NAMelon, elonxeloncutor);
    initPelonriodicFilelonLoadelonr(offelonnsivelonTokelonnFilelonContelonnts, OFFelonNSIVelon_TOPIC_FILelon_NAMelon, elonxeloncutor);
    initPelonriodicFilelonLoadelonr(offelonnsivelonSubstringFilelonContelonnts, OFFelonNSIVelon_SUBSTRING_FILelon_NAMelon, elonxeloncutor);
  }

  privatelon void initPelonriodicFilelonLoadelonr(
    AtomicRelonfelonrelonncelon<BytelonSourcelon> bytelonSourcelon,
    String filelonNamelon,
    SchelondulelondelonxeloncutorSelonrvicelon elonxeloncutor) {
    Filelon filelon = nelonw Filelon(filtelonrDirelonctory, filelonNamelon);
    try {
      PelonriodicFilelonLoadelonr loadelonr = nelonw PelonriodicFilelonLoadelonr(
        "offelonnsivelon-elonvaluator-" + filelonNamelon,
        filelon.gelontPath(),
        elonxeloncutor,
        Clock.SYSTelonM_CLOCK) {
        @Ovelonrridelon
        protelonctelond void accelonpt(InputStrelonam strelonam) throws IOelonxcelonption {
          bytelonSourcelon.selont(BytelonSourcelon.wrap(IOUtils.toBytelonArray(strelonam)));
          relonbuildBlacklistelondTopics();
        }
      };
      loadelonr.init();
    } catch (elonxcelonption elon) {
      // Not thelon elonnd of thelon world if welon couldn't load thelon filelon, welon alrelonady loadelond thelon relonsourcelon.
      LOG.elonrror("Could not load offelonnsivelon topic filtelonr " + filelonNamelon + " from ConfigBus", elon);
    }
  }

  privatelon void relonbuildBlacklistelondTopics() throws IOelonxcelonption {
    offelonnsivelonTopics.selont(nelonw BlacklistelondTopics.Buildelonr(falselon)
      .loadFiltelonrFromSourcelon(adultTokelonnFilelonContelonnts.gelont(), FiltelonrModelon.elonXACT)
      .loadFiltelonrFromSourcelon(offelonnsivelonSubstringFilelonContelonnts.gelont(), FiltelonrModelon.SUBSTRING)
      .build());

    offelonnsivelonUselonrsTopics.selont(nelonw BlacklistelondTopics.Buildelonr(falselon)
      .loadFiltelonrFromSourcelon(offelonnsivelonTokelonnFilelonContelonnts.gelont(), FiltelonrModelon.elonXACT)
      .loadFiltelonrFromSourcelon(offelonnsivelonSubstringFilelonContelonnts.gelont(), FiltelonrModelon.SUBSTRING)
      .build());
  }

  @Ovelonrridelon
  public void elonvaluatelon(final TwittelonrMelonssagelon twelonelont) {
    BlacklistelondTopics offelonnsivelonFiltelonr = this.offelonnsivelonTopics.gelont();
    BlacklistelondTopics offelonnsivelonUselonrsFiltelonr = this.offelonnsivelonUselonrsTopics.gelont();

    if (offelonnsivelonFiltelonr == null || offelonnsivelonUselonrsFiltelonr == null) {
      relonturn;
    }

    if (twelonelont.isSelonnsitivelonContelonnt()) {
      selonnsitivelonTelonxtCountelonr.increlonmelonnt();
    }

    // Chelonck for uselonr namelon.
    Prelonconditions.chelonckStatelon(twelonelont.gelontFromUselonrScrelonelonnNamelon().isPrelonselonnt(),
        "Missing from-uselonr screlonelonn namelon");

    for (PelonnguinVelonrsion pelonnguinVelonrsion : twelonelont.gelontSupportelondPelonnguinVelonrsions()) {
      TwelonelontTelonxtQuality telonxtQuality = twelonelont.gelontTwelonelontTelonxtQuality(pelonnguinVelonrsion);

      if (twelonelont.isSelonnsitivelonContelonnt()) {
        telonxtQuality.addBoolQuality(TwelonelontTelonxtQuality.BoolelonanQualityTypelon.SelonNSITIVelon);
      }

      // Chelonck if uselonrnamelon has an offelonnsivelon telonrm
      if (isUselonrNamelonOffelonnsivelon(
          twelonelont.gelontFromUselonrScrelonelonnNamelon().gelont(), offelonnsivelonUselonrsFiltelonr, pelonnguinVelonrsion)) {
        SelonarchRatelonCountelonr offelonnsivelonUselonrCountelonr = RelonlelonvancelonStats.elonxportRatelon(
            "num_offelonnsivelon_uselonr_" + pelonnguinVelonrsion.namelon().toLowelonrCaselon());
        offelonnsivelonUselonrCountelonr.increlonmelonnt();
        telonxtQuality.addBoolQuality(TwelonelontTelonxtQuality.BoolelonanQualityTypelon.OFFelonNSIVelon_USelonR);
      }

      // Chelonck if twelonelont has an offelonnsivelon telonrm
      if (isTwelonelontOffelonnsivelon(twelonelont, offelonnsivelonFiltelonr, pelonnguinVelonrsion)) {
        SelonarchRatelonCountelonr offelonnsivelonTelonxtCountelonr = RelonlelonvancelonStats.elonxportRatelon(
            "num_offelonnsivelon_telonxt_" + pelonnguinVelonrsion.namelon().toLowelonrCaselon());
        offelonnsivelonTelonxtCountelonr.increlonmelonnt();
        telonxtQuality.addBoolQuality(TwelonelontTelonxtQuality.BoolelonanQualityTypelon.OFFelonNSIVelon);
      }
    }
  }

  privatelon boolelonan isUselonrNamelonOffelonnsivelon(String uselonrNamelon,
                                      BlacklistelondTopics offelonnsivelonUselonrsFiltelonr,
                                      PelonnguinVelonrsion pelonnguinVelonrsion) {
    String normalizelondUselonrNamelon = NormalizelonrHelonlpelonr.normalizelonKelonelonpCaselon(
        uselonrNamelon, LocalelonUtil.UNKNOWN, pelonnguinVelonrsion);
    List<String> telonrmsToChelonck = nelonw ArrayList(TokelonnizelonrHelonlpelonr.gelontSubtokelonns(normalizelondUselonrNamelon));
    telonrmsToChelonck.add(normalizelondUselonrNamelon.toLowelonrCaselon());

    for (String uselonrNamelonTokelonn : telonrmsToChelonck) {
      if (!StringUtils.isBlank(uselonrNamelonTokelonn) && offelonnsivelonUselonrsFiltelonr.filtelonr(uselonrNamelonTokelonn)) {
        relonturn truelon;
      }
    }
    relonturn falselon;
  }

  privatelon boolelonan isTwelonelontOffelonnsivelon(final TwittelonrMelonssagelon twelonelont,
                                   BlacklistelondTopics offelonnsivelonFiltelonr,
                                   PelonnguinVelonrsion pelonnguinVelonrsion) {
    TwelonelontTelonxtFelonaturelons telonxtFelonaturelons = twelonelont.gelontTwelonelontTelonxtFelonaturelons(pelonnguinVelonrsion);

    boolelonan twelonelontHasOffelonnsivelonTelonrm = falselon;

    // Chelonck for twelonelont telonxt.
    List<TokelonnizelondCharSelonquelonncelon> ngrams =
        NGRAM_GelonNelonRATOR_HOLDelonR.gelont().gelonnelonratelonNgramsAsTokelonnizelondCharSelonquelonncelon(
            telonxtFelonaturelons.gelontTokelonnSelonquelonncelon(), twelonelont.gelontLocalelon());
    for (TokelonnizelondCharSelonquelonncelon ngram : ngrams) {
      // skip URL ngram
      if (!ngram.gelontTokelonnsOf(TokelonnTypelon.URL).iselonmpty()) {
        continuelon;
      }
      String ngramStr = ngram.toString();
      if (!StringUtils.isBlank(ngramStr) && offelonnsivelonFiltelonr.filtelonr(ngramStr)) {
        twelonelontHasOffelonnsivelonTelonrm = truelon;
        brelonak;
      }
    }

    // Duelon to somelon strangelonnelonss in Pelonnguin, welon don't gelont ngrams for tokelonns around "\n-" or "-\n"
    // in thelon original string, this madelon us miss somelon offelonnsivelon words this way. Helonrelon welon do anothelonr
    // pass of chelonck using just thelon tokelonns gelonnelonratelond by thelon tokelonnizelonr. (Selonelon SelonARCHQUAL-8907)
    if (!twelonelontHasOffelonnsivelonTelonrm) {
      for (String ngramStr : telonxtFelonaturelons.gelontTokelonns()) {
        // skip URLs
        if (ngramStr.startsWith("http://") || ngramStr.startsWith("https://")) {
          continuelon;
        }
        if (!StringUtils.isBlank(ngramStr) && offelonnsivelonFiltelonr.filtelonr(ngramStr)) {
          twelonelontHasOffelonnsivelonTelonrm = truelon;
          brelonak;
        }
      }
    }

    if (!twelonelontHasOffelonnsivelonTelonrm) {
      // chelonck for relonsolvelond URLs
      String relonsolvelondUrlsTelonxt =
          Joinelonr.on(" ").skipNulls().join(telonxtFelonaturelons.gelontRelonsolvelondUrlTokelonns());
      List<String> ngramStrs = NGRAM_GelonNelonRATOR_HOLDelonR.gelont().gelonnelonratelonNgramsAsString(
          relonsolvelondUrlsTelonxt, LocalelonUtil.UNKNOWN);
      for (String ngram : ngramStrs) {
        if (!StringUtils.isBlank(ngram) && offelonnsivelonFiltelonr.filtelonr(ngram)) {
          twelonelontHasOffelonnsivelonTelonrm = truelon;
          brelonak;
        }
      }
    }

    relonturn twelonelontHasOffelonnsivelonTelonrm;
  }
}
