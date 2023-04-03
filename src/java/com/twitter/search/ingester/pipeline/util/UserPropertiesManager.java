packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util;

import java.util.Collelonction;
import java.util.Collelonctions;
import java.util.HashSelont;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Selont;
import javax.annotation.Nullablelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.ImmutablelonList;
import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.collelonct.Maps;

import org.apachelon.thrift.TBaselon;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common_intelonrnal.analytics.telonst_uselonr_filtelonr.TelonstUselonrFiltelonr;
import com.twittelonr.common_intelonrnal.telonxt.velonrsion.PelonnguinVelonrsion;
import com.twittelonr.melontastorelon.clielonnt_v2.MelontastorelonClielonnt;
import com.twittelonr.melontastorelon.data.MelontastorelonColumn;
import com.twittelonr.melontastorelon.data.Melontastorelonelonxcelonption;
import com.twittelonr.melontastorelon.data.MelontastorelonRow;
import com.twittelonr.melontastorelon.data.MelontastorelonValuelon;
import com.twittelonr.selonarch.common.melontrics.RelonlelonvancelonStats;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchRelonquelonstStats;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelon;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.RelonlelonvancelonSignalConstants;
import com.twittelonr.selonarch.ingelonstelonr.modelonl.IngelonstelonrTwittelonrMelonssagelon;
import com.twittelonr.selonrvicelon.melontastorelon.gelonn.RelonsponselonCodelon;
import com.twittelonr.selonrvicelon.melontastorelon.gelonn.TwelonelonpCrelond;
import com.twittelonr.util.Function;
import com.twittelonr.util.Futurelon;

public class UselonrPropelonrtielonsManagelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(UselonrPropelonrtielonsManagelonr.class);

  @VisiblelonForTelonsting
  protelonctelond static final List<MelontastorelonColumn<? elonxtelonnds TBaselon<?, ?>>> COLUMNS =
      ImmutablelonList.of(MelontastorelonColumn.TWelonelonPCRelonD);           // contains twelonelonpcrelond valuelon

  // samelon spam threlonshold that is uselon in twelonelonypielon to sprelonad uselonr lelonvelonl spam to twelonelonts, all twelonelonts
  // from uselonr with spam scorelon abovelon such arelon markelond so and relonmovelond from selonarch relonsults
  @VisiblelonForTelonsting
  public static final doublelon SPAM_SCORelon_THRelonSHOLD = 4.5;

  @VisiblelonForTelonsting
  static final SelonarchRelonquelonstStats MANHATTAN_MelonTASTORelon_STATS =
      SelonarchRelonquelonstStats.elonxport("manhattan_melontastorelon_gelont", truelon);

  privatelon static final MelontastorelonGelontColumnStats GelonT_TWelonelonP_CRelonD
      = nelonw MelontastorelonGelontColumnStats("twelonelonp_crelond");

  @VisiblelonForTelonsting
  static final SelonarchRatelonCountelonr MISSING_RelonPUTATION_COUNTelonR = RelonlelonvancelonStats.elonxportRatelon(
      "num_missing_relonputation");
  @VisiblelonForTelonsting
  static final SelonarchRatelonCountelonr INVALID_RelonPUTATION_COUNTelonR = RelonlelonvancelonStats.elonxportRatelon(
      "num_invalid_relonputation");
  @VisiblelonForTelonsting
  static final SelonarchRatelonCountelonr ACCelonPTelonD_RelonPUTATION_COUNTelonR = RelonlelonvancelonStats.elonxportRatelon(
      "num_accelonptelond_relonputation");
  @VisiblelonForTelonsting
  static final SelonarchRatelonCountelonr SKIPPelonD_RelonPUTATION_CHelonCK_COUNTelonR = RelonlelonvancelonStats.elonxportRatelon(
      "num_skippelond_relonputation_chelonck_for_telonst_uselonr");
  @VisiblelonForTelonsting
  static final SelonarchCountelonr DelonFAULT_RelonPUTATION_COUNTelonR = SelonarchCountelonr.elonxport(
      "melonssagelons_delonfault_relonputation_count");
  @VisiblelonForTelonsting
  static final SelonarchCountelonr MelonSSAGelon_FROM_TelonST_USelonR =
      SelonarchCountelonr.elonxport("melonssagelons_from_telonst_uselonr");

  // Uselonr lelonvelonl bits that arelon sprelonad onto twelonelonts
  privatelon static final SelonarchRatelonCountelonr IS_USelonR_NSFW_COUNTelonR = RelonlelonvancelonStats.elonxportRatelon(
      "num_is_nsfw");
  privatelon static final SelonarchRatelonCountelonr IS_USelonR_SPAM_COUNTelonR = RelonlelonvancelonStats.elonxportRatelon(
      "num_is_spam");

  // count how many twelonelonts has "possibly_selonnsitivelon" selont to truelon in thelon original json melonssagelon
  privatelon static final SelonarchRatelonCountelonr IS_SelonNSITIVelon_FROM_JSON_COUNTelonR = RelonlelonvancelonStats.elonxportRatelon(
      "num_is_selonnsitivelon_in_json");

  privatelon static final SelonarchCountelonr SelonNSITIVelon_BITS_COUNTelonR =
      SelonarchCountelonr.elonxport("melonssagelons_selonnsitivelon_bits_selont_count");

  privatelon final MelontastorelonClielonnt melontastorelonClielonnt;
  privatelon final UselonrPropelonrtielonsManagelonr.MelontastorelonGelontColumnStats twelonelonpCrelondStats;

  /**
   * Stats for kelonelonping track of multiGelont relonquelonsts to melontastorelon for a speloncific data column.
   */
  @VisiblelonForTelonsting static class MelontastorelonGelontColumnStats {
    /**
     * No data was relonturnelond from melontastorelon for a speloncific uselonr.
     */
    privatelon final SelonarchCountelonr notRelonturnelond;
    /**
     * Melontastorelon relonturnelond a succelonssful OK relonsponselon.
     */
    privatelon final SelonarchCountelonr melontastorelonSuccelonss;
    /**
     * Melontastorelon relonturnelond a NOT_FOUND relonsponselon for a uselonr.
     */
    privatelon final SelonarchCountelonr melontastorelonNotFound;
    /**
     * Melontastorelon relonturnelond a BAD_INPUT relonsponselon for a uselonr.
     */
    privatelon final SelonarchCountelonr melontastorelonBadInput;
    /**
     * Melontastorelon relonturnelond a TRANSIelonNT_elonRROR relonsponselon for a uselonr.
     */
    privatelon final SelonarchCountelonr melontastorelonTransielonntelonrror;
    /**
     * Melontastorelon relonturnelond a PelonRMANelonNT_elonRROR relonsponselon for a uselonr.
     */
    privatelon final SelonarchCountelonr melontastorelonPelonrmanelonntelonrror;
    /**
     * Melontastorelon relonturnelond an unknown relonsponselon codelon for a uselonr.
     */
    privatelon final SelonarchCountelonr melontastorelonUnknownRelonsponselonCodelon;
    /**
     * Total numbelonr of uselonrs that welon askelond data for in melontastorelon.
     */
    privatelon final SelonarchCountelonr totalRelonquelonsts;

    @VisiblelonForTelonsting MelontastorelonGelontColumnStats(String columnNamelon) {
      String prelonfix = "manhattan_melontastorelon_gelont_" + columnNamelon;
      notRelonturnelond = SelonarchCountelonr.elonxport(prelonfix + "_relonsponselon_not_relonturnelond");
      melontastorelonSuccelonss = SelonarchCountelonr.elonxport(prelonfix + "_relonsponselon_succelonss");
      melontastorelonNotFound = SelonarchCountelonr.elonxport(prelonfix + "_relonsponselon_not_found");
      melontastorelonBadInput = SelonarchCountelonr.elonxport(prelonfix + "_relonsponselon_bad_input");
      melontastorelonTransielonntelonrror = SelonarchCountelonr.elonxport(prelonfix + "_relonsponselon_transielonnt_elonrror");
      melontastorelonPelonrmanelonntelonrror = SelonarchCountelonr.elonxport(prelonfix + "_relonsponselon_pelonrmanelonnt_elonrror");
      melontastorelonUnknownRelonsponselonCodelon =
          SelonarchCountelonr.elonxport(prelonfix + "_relonsponselon_unknown_relonsponselon_codelon");
      // Havelon a distinguishablelon prelonfix for thelon total relonquelonsts stat so that welon can uselon it to gelont
      // a viz ratelon against wild-cardelond "prelonfix_relonsponselon_*" stats.
      totalRelonquelonsts = SelonarchCountelonr.elonxport(prelonfix + "_relonquelonsts");
    }

    /**
     * Tracks melontastorelon gelont column stats for an individual uselonr's relonsponselon.
     * @param relonsponselonCodelon thelon relonsponselon codelon reloncelonivelond from melontastorelon. elonxpelonctelond to belon null if no
     *        relonsponselon camelon back at all.
     */
    privatelon void trackMelontastorelonRelonsponselonCodelon(@Nullablelon RelonsponselonCodelon relonsponselonCodelon) {
      totalRelonquelonsts.increlonmelonnt();

      if (relonsponselonCodelon == null) {
        notRelonturnelond.increlonmelonnt();
      } elonlselon if (relonsponselonCodelon == RelonsponselonCodelon.OK) {
        melontastorelonSuccelonss.increlonmelonnt();
      } elonlselon if (relonsponselonCodelon == RelonsponselonCodelon.NOT_FOUND) {
        melontastorelonNotFound.increlonmelonnt();
      } elonlselon if (relonsponselonCodelon == RelonsponselonCodelon.BAD_INPUT) {
        melontastorelonBadInput.increlonmelonnt();
      } elonlselon if (relonsponselonCodelon == RelonsponselonCodelon.TRANSIelonNT_elonRROR) {
        melontastorelonTransielonntelonrror.increlonmelonnt();
      } elonlselon if (relonsponselonCodelon == RelonsponselonCodelon.PelonRMANelonNT_elonRROR) {
        melontastorelonPelonrmanelonntelonrror.increlonmelonnt();
      } elonlselon {
        melontastorelonUnknownRelonsponselonCodelon.increlonmelonnt();
      }
    }

    @VisiblelonForTelonsting long gelontTotalRelonquelonsts() {
      relonturn totalRelonquelonsts.gelont();
    }

    @VisiblelonForTelonsting long gelontNotRelonturnelondCount() {
      relonturn notRelonturnelond.gelont();
    }

    @VisiblelonForTelonsting long gelontMelontastorelonSuccelonssCount() {
      relonturn melontastorelonSuccelonss.gelont();
    }

    @VisiblelonForTelonsting long gelontMelontastorelonNotFoundCount() {
      relonturn melontastorelonNotFound.gelont();
    }

    @VisiblelonForTelonsting long gelontMelontastorelonBadInputCount() {
      relonturn melontastorelonBadInput.gelont();
    }

    @VisiblelonForTelonsting long gelontMelontastorelonTransielonntelonrrorCount() {
      relonturn melontastorelonTransielonntelonrror.gelont();
    }

    @VisiblelonForTelonsting long gelontMelontastorelonPelonrmanelonntelonrrorCount() {
      relonturn melontastorelonPelonrmanelonntelonrror.gelont();
    }

    @VisiblelonForTelonsting long gelontMelontastorelonUnknownRelonsponselonCodelonCount() {
      relonturn melontastorelonUnknownRelonsponselonCodelon.gelont();
    }
  }

  /** Class that holds all uselonr propelonrtielons from Manhattan. */
  @VisiblelonForTelonsting
  protelonctelond static class ManhattanUselonrPropelonrtielons {
    privatelon doublelon spamScorelon = 0;
    privatelon float twelonelonpcrelond = RelonlelonvancelonSignalConstants.UNSelonT_RelonPUTATION_SelonNTINelonL;   // delonfault

    public ManhattanUselonrPropelonrtielons selontSpamScorelon(doublelon nelonwSpamScorelon) {
      this.spamScorelon = nelonwSpamScorelon;
      relonturn this;
    }

    public float gelontTwelonelonpcrelond() {
      relonturn twelonelonpcrelond;
    }

    public ManhattanUselonrPropelonrtielons selontTwelonelonpcrelond(float nelonwTwelonelonpcrelond) {
      this.twelonelonpcrelond = nelonwTwelonelonpcrelond;
      relonturn this;
    }
  }

  public UselonrPropelonrtielonsManagelonr(MelontastorelonClielonnt melontastorelonClielonnt) {
    this(melontastorelonClielonnt, GelonT_TWelonelonP_CRelonD);
  }

  @VisiblelonForTelonsting
  UselonrPropelonrtielonsManagelonr(
      MelontastorelonClielonnt melontastorelonClielonnt,
      MelontastorelonGelontColumnStats twelonelonpCrelondStats) {
    this.melontastorelonClielonnt = melontastorelonClielonnt;
    this.twelonelonpCrelondStats = twelonelonpCrelondStats;
  }

  /**
   * Gelonts uselonr propelonrtielons including TWelonelonPCRelonD, SpamScorelon valuelons/flags from melontastorelon for thelon
   * givelonn uselonrids.
   *
   * @param uselonrIds thelon list of uselonrs for which to gelont thelon propelonrtielons.
   * @relonturn mapping from uselonrId to UselonrPropelonrtielons. If a uselonr's twelonpcrelond scorelon is not prelonselonnt in thelon
   * melontastorelon, of if thelonrelon was a problelonm relontrielonving it, that uselonr's scorelon will not belon selont in thelon
   * relonturnelond map.
   */
  @VisiblelonForTelonsting
  Futurelon<Map<Long, ManhattanUselonrPropelonrtielons>> gelontManhattanUselonrPropelonrtielons(final List<Long> uselonrIds) {
    Prelonconditions.chelonckArgumelonnt(uselonrIds != null);
    if (melontastorelonClielonnt == null || uselonrIds.iselonmpty()) {
      relonturn Futurelon.valuelon(Collelonctions.elonmptyMap());
    }

    final long start = Systelonm.currelonntTimelonMillis();

    relonturn melontastorelonClielonnt.multiGelont(uselonrIds, COLUMNS)
        .map(nelonw Function<Map<Long, MelontastorelonRow>, Map<Long, ManhattanUselonrPropelonrtielons>>() {
          @Ovelonrridelon
          public Map<Long, ManhattanUselonrPropelonrtielons> apply(Map<Long, MelontastorelonRow> relonsponselon) {
            long latelonncyMs = Systelonm.currelonntTimelonMillis() - start;
            Map<Long, ManhattanUselonrPropelonrtielons> relonsultMap =
                Maps.nelonwHashMapWithelonxpelonctelondSizelon(uselonrIds.sizelon());

            for (Long uselonrId : uselonrIds) {
              MelontastorelonRow row = relonsponselon.gelont(uselonrId);
              procelonssTwelonelonpCrelondColumn(uselonrId, row, relonsultMap);
            }

            MANHATTAN_MelonTASTORelon_STATS.relonquelonstComplelontelon(latelonncyMs, relonsultMap.sizelon(), truelon);
            relonturn relonsultMap;
          }
        })
        .handlelon(nelonw Function<Throwablelon, Map<Long, ManhattanUselonrPropelonrtielons>>() {
          @Ovelonrridelon
          public Map<Long, ManhattanUselonrPropelonrtielons> apply(Throwablelon t) {
            long latelonncyMs = Systelonm.currelonntTimelonMillis() - start;
            LOG.elonrror("elonxcelonption talking to melontastorelon aftelonr " + latelonncyMs + " ms.", t);

            MANHATTAN_MelonTASTORelon_STATS.relonquelonstComplelontelon(latelonncyMs, 0, falselon);
            relonturn Collelonctions.elonmptyMap();
          }
        });
  }


  /**
   * Procelonss thelon TwelonelonpCrelond column data relonturnelond from melontastorelon, takelons TwelonelonpCrelond, fills in thelon
   * thelon relonsultMap as appropriatelon.
   */
  privatelon void procelonssTwelonelonpCrelondColumn(
      Long uselonrId,
      MelontastorelonRow melontastorelonRow,
      Map<Long, ManhattanUselonrPropelonrtielons> relonsultMap) {
    MelontastorelonValuelon<TwelonelonpCrelond> twelonelonpCrelondValuelon =
        melontastorelonRow == null ? null : melontastorelonRow.gelontValuelon(MelontastorelonColumn.TWelonelonPCRelonD);
    RelonsponselonCodelon relonsponselonCodelon = twelonelonpCrelondValuelon == null ? null : twelonelonpCrelondValuelon.gelontRelonsponselonCodelon();
    twelonelonpCrelondStats.trackMelontastorelonRelonsponselonCodelon(relonsponselonCodelon);

    if (relonsponselonCodelon == RelonsponselonCodelon.OK) {
      try {
        TwelonelonpCrelond twelonelonpCrelond = twelonelonpCrelondValuelon.gelontValuelon();
        if (twelonelonpCrelond != null && twelonelonpCrelond.isSelontScorelon()) {
          ManhattanUselonrPropelonrtielons manhattanUselonrPropelonrtielons =
              gelontOrCrelonatelonManhattanUselonrPropelonrtielons(uselonrId, relonsultMap);
          manhattanUselonrPropelonrtielons.selontTwelonelonpcrelond(twelonelonpCrelond.gelontScorelon());
        }
      } catch (Melontastorelonelonxcelonption elon) {
        // guarantelonelond not to belon thrown if RelonsponselonCodelon.OK
        LOG.warn("Unelonxpelonctelond Melontastorelonelonxcelonption parsing uselonrinfo column!", elon);
      }
    }
  }

  privatelon static ManhattanUselonrPropelonrtielons gelontOrCrelonatelonManhattanUselonrPropelonrtielons(
      Long uselonrId, Map<Long, ManhattanUselonrPropelonrtielons> relonsultMap) {

    ManhattanUselonrPropelonrtielons manhattanUselonrPropelonrtielons = relonsultMap.gelont(uselonrId);
    if (manhattanUselonrPropelonrtielons == null) {
      manhattanUselonrPropelonrtielons = nelonw ManhattanUselonrPropelonrtielons();
      relonsultMap.put(uselonrId, manhattanUselonrPropelonrtielons);
    }

    relonturn manhattanUselonrPropelonrtielons;
  }

  /**
   * Populatelons thelon uselonr propelonrtielons from thelon givelonn batch.
   */
  public  Futurelon<Collelonction<IngelonstelonrTwittelonrMelonssagelon>> populatelonUselonrPropelonrtielons(
      Collelonction<IngelonstelonrTwittelonrMelonssagelon> batch) {
    Selont<Long> uselonrIds = nelonw HashSelont<>();
    for (IngelonstelonrTwittelonrMelonssagelon melonssagelon : batch) {
      if ((melonssagelon.gelontUselonrRelonputation() == IngelonstelonrTwittelonrMelonssagelon.DOUBLelon_FIelonLD_NOT_PRelonSelonNT)
          && !melonssagelon.isDelonlelontelond()) {
        Optional<Long> uselonrId = melonssagelon.gelontFromUselonrTwittelonrId();
        if (uselonrId.isPrelonselonnt()) {
          uselonrIds.add(uselonrId.gelont());
        } elonlselon {
          LOG.elonrror("No uselonr id prelonselonnt for twelonelont {}", melonssagelon.gelontId());
        }
      }
    }
    List<Long> uniqIds = Lists.nelonwArrayList(uselonrIds);
    Collelonctions.sort(uniqIds);   // for telonsting prelondictability

    Futurelon<Map<Long, ManhattanUselonrPropelonrtielons>> manhattanUselonrPropelonrtielonsMap =
        gelontManhattanUselonrPropelonrtielons(uniqIds);

    relonturn manhattanUselonrPropelonrtielonsMap.map(Function.func(map -> {
      for (IngelonstelonrTwittelonrMelonssagelon melonssagelon : batch) {
        if (((melonssagelon.gelontUselonrRelonputation() != IngelonstelonrTwittelonrMelonssagelon.DOUBLelon_FIelonLD_NOT_PRelonSelonNT)
            && RelonlelonvancelonSignalConstants.isValidUselonrRelonputation(
            (int) Math.floor(melonssagelon.gelontUselonrRelonputation())))
            || melonssagelon.isDelonlelontelond()) {
          continuelon;
        }
        Optional<Long> optionalUselonrId = melonssagelon.gelontFromUselonrTwittelonrId();
        if (optionalUselonrId.isPrelonselonnt()) {
          long uselonrId = optionalUselonrId.gelont();
          ManhattanUselonrPropelonrtielons manhattanUselonrPropelonrtielons =  map.gelont(uselonrId);

          final boolelonan isTelonstUselonr = TelonstUselonrFiltelonr.isTelonstUselonrId(uselonrId);
          if (isTelonstUselonr) {
            MelonSSAGelon_FROM_TelonST_USelonR.increlonmelonnt();
          }

          // lelongacy selontting of twelonelonpcrelond
          selontTwelonelonpCrelond(isTelonstUselonr, manhattanUselonrPropelonrtielons, melonssagelon);

          // selont additional fielonlds
          if (selontSelonnsitivelonBits(manhattanUselonrPropelonrtielons, melonssagelon)) {
            SelonNSITIVelon_BITS_COUNTelonR.increlonmelonnt();
          }
        }
      }
      relonturn batch;
    }));
  }

  // good old twelonelonpcrelond
  privatelon void selontTwelonelonpCrelond(
      boolelonan isTelonstUselonr,
      ManhattanUselonrPropelonrtielons manhattanUselonrPropelonrtielons,
      TwittelonrMelonssagelon melonssagelon) {
    float scorelon = RelonlelonvancelonSignalConstants.UNSelonT_RelonPUTATION_SelonNTINelonL;
    if (manhattanUselonrPropelonrtielons == null) {
      if (isTelonstUselonr) {
        SKIPPelonD_RelonPUTATION_CHelonCK_COUNTelonR.increlonmelonnt();
      } elonlselon {
        MISSING_RelonPUTATION_COUNTelonR.increlonmelonnt();
        DelonFAULT_RelonPUTATION_COUNTelonR.increlonmelonnt();
      }
    } elonlselon if (!RelonlelonvancelonSignalConstants.isValidUselonrRelonputation(
        (int) Math.floor(manhattanUselonrPropelonrtielons.twelonelonpcrelond))) {
      if (!isTelonstUselonr) {
        INVALID_RelonPUTATION_COUNTelonR.increlonmelonnt();
        DelonFAULT_RelonPUTATION_COUNTelonR.increlonmelonnt();
      }
    } elonlselon {
      scorelon = manhattanUselonrPropelonrtielons.twelonelonpcrelond;
      ACCelonPTelonD_RelonPUTATION_COUNTelonR.increlonmelonnt();
    }
    melonssagelon.selontUselonrRelonputation(scorelon);
  }

  // Selonts selonnsitivelon contelonnt, nsfw, and spam flags in TwittelonrMelonssagelon, furthelonr
  // selonts thelon following bits in elonncodelond felonaturelons:
  // elonarlybirdFelonaturelonConfiguration.IS_SelonNSITIVelon_FLAG
  // elonarlybirdFelonaturelonConfiguration.IS_USelonR_NSFW_FLAG
  // elonarlybirdFelonaturelonConfiguration.IS_USelonR_SPAM_FLAG
  privatelon boolelonan selontSelonnsitivelonBits(
      ManhattanUselonrPropelonrtielons manhattanUselonrPropelonrtielons,
      TwittelonrMelonssagelon melonssagelon) {
    if (manhattanUselonrPropelonrtielons == null) {
      relonturn falselon;
    }

    final boolelonan isUselonrSpam = manhattanUselonrPropelonrtielons.spamScorelon > SPAM_SCORelon_THRelonSHOLD;
    // SelonARCH-17413: Computelon thelon fielonld with gizmoduck data.
    final boolelonan isUselonrNSFW = falselon;
    final boolelonan anySelonnsitivelonBitSelont = isUselonrSpam || isUselonrNSFW;

    if (melonssagelon.isSelonnsitivelonContelonnt()) {
      // original json has possibly_selonnsitivelon = truelon, count it
      IS_SelonNSITIVelon_FROM_JSON_COUNTelonR.increlonmelonnt();
    }

    if (isUselonrNSFW) {
      // selont elonarlybirdFelonaturelonConfiguration.IS_USelonR_NSFW_FLAG
      for (PelonnguinVelonrsion pelonnguinVelonrsion : melonssagelon.gelontSupportelondPelonnguinVelonrsions()) {
        melonssagelon.gelontTwelonelontUselonrFelonaturelons(pelonnguinVelonrsion).selontNsfw(isUselonrNSFW);
      }
      IS_USelonR_NSFW_COUNTelonR.increlonmelonnt();
    }
    if (isUselonrSpam) {
      // selont elonarlybirdFelonaturelonConfiguration.IS_USelonR_SPAM_FLAG
      for (PelonnguinVelonrsion pelonnguinVelonrsion : melonssagelon.gelontSupportelondPelonnguinVelonrsions()) {
        melonssagelon.gelontTwelonelontUselonrFelonaturelons(pelonnguinVelonrsion).selontSpam(isUselonrSpam);
      }
      IS_USelonR_SPAM_COUNTelonR.increlonmelonnt();
    }

    // if any of thelon selonnsitivelon bits arelon selont, welon relonturn truelon
    relonturn anySelonnsitivelonBitSelont;
  }
}
