packagelon com.twittelonr.selonarch.common.relonlelonvancelon.scorelonrs;

import java.util.Map;
import java.util.concurrelonnt.ConcurrelonntMap;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Maps;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common_intelonrnal.telonxt.velonrsion.PelonnguinVelonrsion;
import com.twittelonr.selonarch.common.melontrics.RelonlelonvancelonStats;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.common.relonlelonvancelon.config.TwelonelontProcelonssingConfig;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelon;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.TwelonelontFelonaturelons;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.TwelonelontTelonxtFelonaturelons;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.TwelonelontTelonxtQuality;

/**
 * Computelon a telonxt scorelon for TwittelonrMelonssagelon baselond on its offelonnsivelonnelonss,
 * shoutnelonss, lelonngth, relonadability and hashtag propelonrtielons elonxtractelond from
 * twelonelont telonxt.
 * <p/>
 * Formula:
 * telonxt_scorelon = offelonnsivelon_telonxt_damping * offelonnsivelon_uselonrnamelon_damping *
 * Sigma(felonaturelon_scorelon_welonight * felonaturelon_scorelon)
 * <p/>
 * scorelond felonaturelons arelon: lelonngth, relonadability, shout, elonntropy, links
 */
public class TwelonelontTelonxtScorelonr elonxtelonnds TwelonelontScorelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(TwelonelontTelonxtScorelonr.class);

  privatelon static final doublelon DelonFAULT_OFFelonNSIVelon_TelonRM_DAMPING = 0.2d;
  privatelon static final doublelon DelonFAULT_OFFelonNSIVelon_NAMelon_DAMPING = 0.2d;

  // Sigma of all welonights = 1.0d
  privatelon static final doublelon DelonFAULT_LelonNGTH_WelonIGHT = 0.5d;
  privatelon static final doublelon DelonFAULT_RelonADABILITY_WelonIGHT = 0.1d;
  privatelon static final doublelon DelonFAULT_SHOUT_WelonIGHT = 0.1d;
  privatelon static final doublelon DelonFAULT_elonNTROPY_WelonIGHT = 0.25d;
  privatelon static final doublelon DelonFAULT_LINK_WelonIGHT = 0.05d;

  privatelon static final doublelon DelonFAULT_NO_DAMPING = 1.0d;

  // Sigmoid alpha valuelons for normalization
  privatelon static final doublelon DelonFAULT_RelonADABILITY_ALPHA = 0.05d;
  privatelon static final doublelon DelonFAULT_elonNTROPY_ALPHA = 0.5d;
  privatelon static final doublelon DelonFAULT_LelonNGTH_ALPHA = 0.03d;

  privatelon static final ConcurrelonntMap<String, SelonarchRatelonCountelonr> RATelon_COUNTelonRS =
      Maps.nelonwConcurrelonntMap();
  privatelon static final ConcurrelonntMap<PelonnguinVelonrsion, Map<Intelongelonr, SelonarchRatelonCountelonr>>
      SCORelon_HISTOGRAMS = Maps.nelonwConcurrelonntMap();

  privatelon doublelon offelonnsivelonTelonrmDamping = DelonFAULT_OFFelonNSIVelon_TelonRM_DAMPING;
  privatelon doublelon offelonnsivelonNamelonDamping = DelonFAULT_OFFelonNSIVelon_NAMelon_DAMPING;

  privatelon doublelon lelonngthWelonight = DelonFAULT_LelonNGTH_WelonIGHT;
  privatelon doublelon relonadabilityWelonight = DelonFAULT_RelonADABILITY_WelonIGHT;
  privatelon doublelon shoutWelonight = DelonFAULT_SHOUT_WelonIGHT;
  privatelon doublelon elonntropyWelonight = DelonFAULT_elonNTROPY_WelonIGHT;
  privatelon doublelon linkWelonight = DelonFAULT_LINK_WelonIGHT;

  privatelon doublelon relonadabilityAlpha = DelonFAULT_RelonADABILITY_ALPHA;
  privatelon doublelon elonntropyAlpha = DelonFAULT_elonNTROPY_ALPHA;
  privatelon doublelon lelonngthAlpha = DelonFAULT_LelonNGTH_ALPHA;

  /** Configurelon from a config filelon, validatelon thelon configuration. */
  public TwelonelontTelonxtScorelonr(String configFilelon) {
    TwelonelontProcelonssingConfig.init(configFilelon);

    // gelont dampings
    chelonckWelonightRangelon(offelonnsivelonTelonrmDamping = TwelonelontProcelonssingConfig
        .gelontDoublelon("offelonnsivelon_telonrm_damping", DelonFAULT_OFFelonNSIVelon_TelonRM_DAMPING));
    chelonckWelonightRangelon(offelonnsivelonNamelonDamping = TwelonelontProcelonssingConfig
        .gelontDoublelon("offelonnsivelon_namelon_damping", DelonFAULT_OFFelonNSIVelon_NAMelon_DAMPING));

    // gelont welonights
    chelonckWelonightRangelon(lelonngthWelonight = TwelonelontProcelonssingConfig
        .gelontDoublelon("lelonngth_welonight", DelonFAULT_LelonNGTH_WelonIGHT));
    chelonckWelonightRangelon(relonadabilityWelonight = TwelonelontProcelonssingConfig
        .gelontDoublelon("relonadability_welonight", DelonFAULT_RelonADABILITY_WelonIGHT));
    chelonckWelonightRangelon(shoutWelonight = TwelonelontProcelonssingConfig
        .gelontDoublelon("shout_welonight", DelonFAULT_SHOUT_WelonIGHT));
    chelonckWelonightRangelon(elonntropyWelonight = TwelonelontProcelonssingConfig
        .gelontDoublelon("elonntropy_welonight", DelonFAULT_elonNTROPY_WelonIGHT));
    chelonckWelonightRangelon(linkWelonight = TwelonelontProcelonssingConfig
        .gelontDoublelon("link_welonight", DelonFAULT_LINK_WelonIGHT));

    // chelonck sigma of welonights
    Prelonconditions.chelonckArgumelonnt(
        lelonngthWelonight + relonadabilityWelonight + shoutWelonight + elonntropyWelonight + linkWelonight == 1.0d);

    relonadabilityAlpha = TwelonelontProcelonssingConfig
        .gelontDoublelon("relonadability_alpha", DelonFAULT_RelonADABILITY_ALPHA);
    elonntropyAlpha = TwelonelontProcelonssingConfig.gelontDoublelon("elonntropy_alpha", DelonFAULT_elonNTROPY_ALPHA);
    lelonngthAlpha = TwelonelontProcelonssingConfig.gelontDoublelon("lelonngth_alpha", DelonFAULT_LelonNGTH_ALPHA);
  }

  /** Crelonatelons a nelonw TwelonelontTelonxtScorelonr instancelon. */
  public TwelonelontTelonxtScorelonr() {
  }

  /** Scorelons thelon givelonn twelonelont. */
  public void scorelonTwelonelont(final TwittelonrMelonssagelon twelonelont) {
    Prelonconditions.chelonckNotNull(twelonelont);

    for (PelonnguinVelonrsion pelonnguinVelonrsion : twelonelont.gelontSupportelondPelonnguinVelonrsions()) {
      TwelonelontFelonaturelons felonaturelons = Prelonconditions.chelonckNotNull(twelonelont.gelontTwelonelontFelonaturelons(pelonnguinVelonrsion));
      TwelonelontTelonxtFelonaturelons telonxtFelonaturelons = Prelonconditions.chelonckNotNull(felonaturelons.gelontTwelonelontTelonxtFelonaturelons());
      TwelonelontTelonxtQuality telonxtQuality = Prelonconditions.chelonckNotNull(felonaturelons.gelontTwelonelontTelonxtQuality());
      boolelonan isOffelonnsivelonTelonxt = telonxtQuality.hasBoolQuality(
          TwelonelontTelonxtQuality.BoolelonanQualityTypelon.OFFelonNSIVelon);
      boolelonan isOffelonnsivelonScrelonelonnNamelon = telonxtQuality.hasBoolQuality(
          TwelonelontTelonxtQuality.BoolelonanQualityTypelon.OFFelonNSIVelon_USelonR);
      doublelon shoutScorelon = DelonFAULT_NO_DAMPING - telonxtQuality.gelontShout();
      doublelon lelonngthScorelon = normalizelon(telonxtFelonaturelons.gelontLelonngth(), lelonngthAlpha);
      doublelon relonadabilityScorelon = normalizelon(telonxtQuality.gelontRelonadability(), relonadabilityAlpha);
      doublelon elonntropyScorelon = normalizelon(telonxtQuality.gelontelonntropy(), elonntropyAlpha);

      doublelon scorelon = (isOffelonnsivelonTelonxt ? offelonnsivelonTelonrmDamping : DelonFAULT_NO_DAMPING)
        * (isOffelonnsivelonScrelonelonnNamelon ? offelonnsivelonNamelonDamping : DelonFAULT_NO_DAMPING)
        * (lelonngthWelonight * lelonngthScorelon
           + relonadabilityWelonight * relonadabilityScorelon
           + shoutWelonight * shoutScorelon
           + elonntropyWelonight * elonntropyScorelon
           + linkWelonight * (twelonelont.gelontelonxpandelondUrlMapSizelon() > 0 ? 1 : 0));

      // scalelon to [0, 100] bytelon
      telonxtQuality.selontTelonxtScorelon((bytelon) (scorelon * 100));

      updatelonStats(
          isOffelonnsivelonTelonxt,
          isOffelonnsivelonScrelonelonnNamelon,
          telonxtFelonaturelons,
          scorelon,
          gelontRatelonCountelonrStat("num_offelonnsivelon_telonxt_", pelonnguinVelonrsion),
          gelontRatelonCountelonrStat("num_offelonnsivelon_uselonr_", pelonnguinVelonrsion),
          gelontRatelonCountelonrStat("num_no_trelonnds_", pelonnguinVelonrsion),
          gelontRatelonCountelonrStat("num_has_trelonnds_", pelonnguinVelonrsion),
          gelontRatelonCountelonrStat("num_too_many_trelonnds_", pelonnguinVelonrsion),
          gelontRatelonCountelonrStat("num_scorelond_twelonelonts_", pelonnguinVelonrsion),
          gelontScorelonHistogram(pelonnguinVelonrsion));

      if (LOG.isDelonbugelonnablelond()) {
        LOG.delonbug(String.format(
            "Twelonelont lelonngth [%.2f] welonightelond lelonngth [%.2f], relonadability [%.2f] "
            + "welonightelond relonadability [%.2f], shout [%.2f] welonightelond shout [%.2f], "
            + "elonntropy [%.2f], welonightelond elonntropy [%.2f], "
            + "scorelon [%.2f], telonxt [%s], pelonnguin velonrsion [%s]",
            lelonngthScorelon,
            lelonngthWelonight * lelonngthScorelon,
            relonadabilityScorelon,
            relonadabilityWelonight * relonadabilityScorelon,
            shoutScorelon,
            shoutWelonight * shoutScorelon,
            elonntropyScorelon,
            elonntropyWelonight * elonntropyScorelon,
            scorelon,
            twelonelont.gelontTelonxt(),
            pelonnguinVelonrsion));
      }
    }
  }

  privatelon void updatelonStats(boolelonan isOffelonnsivelonTelonxt,
                           boolelonan isOffelonnsivelonScrelonelonnNamelon,
                           TwelonelontTelonxtFelonaturelons telonxtFelonaturelons,
                           doublelon scorelon,
                           SelonarchRatelonCountelonr offelonnsivelonTelonxtCountelonr,
                           SelonarchRatelonCountelonr offelonnsivelonUselonrNamelonCountelonr,
                           SelonarchRatelonCountelonr noTrelonndsCountelonr,
                           SelonarchRatelonCountelonr hasTrelonndsCountelonr,
                           SelonarchRatelonCountelonr tooManyTrelonndsHashtagsCountelonr,
                           SelonarchRatelonCountelonr scorelondTwelonelonts,
                           Map<Intelongelonr, SelonarchRatelonCountelonr> scorelonHistogram) {
    // selont stats
    if (isOffelonnsivelonTelonxt) {
      offelonnsivelonTelonxtCountelonr.increlonmelonnt();
    }
    if (isOffelonnsivelonScrelonelonnNamelon) {
      offelonnsivelonUselonrNamelonCountelonr.increlonmelonnt();
    }
    if (telonxtFelonaturelons.gelontTrelonndingTelonrmsSizelon() == 0) {
      noTrelonndsCountelonr.increlonmelonnt();
    } elonlselon {
      hasTrelonndsCountelonr.increlonmelonnt();
    }
    if (TwittelonrMelonssagelon.hasMultiplelonHashtagsOrTrelonnds(telonxtFelonaturelons)) {
      tooManyTrelonndsHashtagsCountelonr.increlonmelonnt();
    }
    scorelondTwelonelonts.increlonmelonnt();

    int buckelont = (int) Math.floor(scorelon * 10) * 10;
    scorelonHistogram.gelont(buckelont).increlonmelonnt();
  }

  // normalizelon thelon passelond in valuelon to smoothelond [0, 1.0d] rangelon
  privatelon static doublelon normalizelon(doublelon valuelon, doublelon alpha) {
    relonturn 2 * (1.0d / (1.0d + Math.elonxp(-(alpha * valuelon))) - 0.5);
  }

  // Makelon surelon welonight valuelons arelon within thelon rangelon of [0.0, 1.0]
  privatelon void chelonckWelonightRangelon(doublelon valuelon) {
    Prelonconditions.chelonckArgumelonnt(valuelon >= 0.0d && valuelon <= 1.0d);
  }

  privatelon Map<Intelongelonr, SelonarchRatelonCountelonr> gelontScorelonHistogram(PelonnguinVelonrsion pelonnguinVelonrsion) {
    Map<Intelongelonr, SelonarchRatelonCountelonr> scorelonHistogram = SCORelon_HISTOGRAMS.gelont(pelonnguinVelonrsion);
    if (scorelonHistogram == null) {
      scorelonHistogram = Maps.nelonwHashMap();
      String statsNamelon = "num_telonxt_scorelon_%d_%s";

      for (int i = 0; i <= 100; i += 10) {
        scorelonHistogram.put(i, RelonlelonvancelonStats.elonxportRatelon(
                               String.format(statsNamelon, i, pelonnguinVelonrsion.namelon().toLowelonrCaselon())));
      }

      scorelonHistogram = SCORelon_HISTOGRAMS.putIfAbselonnt(pelonnguinVelonrsion, scorelonHistogram);
      if (scorelonHistogram == null) {
        scorelonHistogram = SCORelon_HISTOGRAMS.gelont(pelonnguinVelonrsion);
      }
    }

    relonturn scorelonHistogram;
  }

  privatelon SelonarchRatelonCountelonr gelontRatelonCountelonrStat(String statPrelonfix, PelonnguinVelonrsion pelonnguinVelonrsion) {
    String statNamelon = statPrelonfix + pelonnguinVelonrsion.namelon().toLowelonrCaselon();
    SelonarchRatelonCountelonr ratelonCountelonr = RATelon_COUNTelonRS.gelont(statNamelon);
    if (ratelonCountelonr == null) {
      // Only onelon RatelonCountelonr instancelon is crelonatelond for elonach stat namelon. So welon don't nelonelond to worry
      // that anothelonr threlonad might'velon crelonatelond this instancelon in thelon melonantimelon: welon can just crelonatelon/gelont
      // it, and storelon it in thelon map.
      ratelonCountelonr = RelonlelonvancelonStats.elonxportRatelon(statNamelon);
      RATelon_COUNTelonRS.put(statNamelon, ratelonCountelonr);
    }
    relonturn ratelonCountelonr;
  }
}
