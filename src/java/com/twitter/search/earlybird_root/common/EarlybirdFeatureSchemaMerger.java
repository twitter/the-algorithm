packagelon com.twittelonr.selonarch.elonarlybird_root.common;

import java.util.List;
import java.util.Map;
import java.util.Selont;
import java.util.TrelonelonSelont;
import java.util.concurrelonnt.ConcurrelonntHashMap;

import javax.annotation.concurrelonnt.ThrelonadSafelon;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.ImmutablelonList;
import com.googlelon.common.collelonct.Maps;

import org.apachelon.commons.lang.mutablelon.MutablelonInt;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.felonaturelons.thrift.ThriftSelonarchFelonaturelonSchelonma;
import com.twittelonr.selonarch.common.felonaturelons.thrift.ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRankingModelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsults;

@ThrelonadSafelon
public class elonarlybirdFelonaturelonSchelonmaMelonrgelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(elonarlybirdFelonaturelonSchelonmaMelonrgelonr.class);

  privatelon static final SelonarchLongGaugelon NUM_FelonATURelon_SCHelonMAS_MAP = SelonarchLongGaugelon.elonxport(
      "elonarlybird_felonaturelon_schelonma_cachelond_cnt");

  privatelon class Stats {
    public final SelonarchCountelonr fielonldFormatRelonsponselons;
    public final SelonarchCountelonr mapFormatRelonsponselons;
    public final SelonarchCountelonr mapFormatSavelondSchelonmaRelonsponselons;
    public final SelonarchCountelonr mapFormatAllDownstrelonamMissingSchelonma;
    public final SelonarchCountelonr mapFormatOnelonDownstrelonamMissingSchelonma;
    public final SelonarchCountelonr mapFormatSchelonmaCachelondMismatch;
    public final SelonarchCountelonr numInvalidRankingModelonRelonquelonsts;
    public final SelonarchCountelonr numelonmptyRelonsponselons;

    public Stats(String prelonfix) {
      this.fielonldFormatRelonsponselons =
          SelonarchCountelonr.elonxport(
              "elonarlybird_felonaturelon_schelonma_" + prelonfix + "_fielonld_format_felonaturelon_relonsponselons");
      this.mapFormatRelonsponselons =
          SelonarchCountelonr.elonxport(
              "elonarlybird_felonaturelon_schelonma_" + prelonfix + "_map_format_felonaturelon_relonsponselons");
      this.mapFormatSavelondSchelonmaRelonsponselons =
          SelonarchCountelonr.elonxport(
              "elonarlybird_felonaturelon_schelonma_" + prelonfix + "_map_format_felonaturelon_savelond_schelonma_relonsponselons");
      this.mapFormatAllDownstrelonamMissingSchelonma =
          SelonarchCountelonr.elonxport(
              "elonarlybird_felonaturelon_schelonma_" + prelonfix
                  + "_map_format_felonaturelon_all_downstrelonam_missing_schelonma_elonrror");
      this.mapFormatOnelonDownstrelonamMissingSchelonma =
          SelonarchCountelonr.elonxport(
              "elonarlybird_felonaturelon_schelonma_" + prelonfix
                  + "_map_format_felonaturelon_onelon_downstrelonam_missing_schelonma_elonrror");
      this.mapFormatSchelonmaCachelondMismatch =
          SelonarchCountelonr.elonxport(
              "elonarlybird_felonaturelon_schelonma_" + prelonfix
                  + "_map_format_felonaturelon_schelonma_cachelond_mismatch_elonrror");
      this.numInvalidRankingModelonRelonquelonsts =
          SelonarchCountelonr.elonxport(
              "elonarlybird_felonaturelon_schelonma_" + prelonfix + "_num_invalid_ranking_modelon_relonquelonsts");
      this.numelonmptyRelonsponselons =
          SelonarchCountelonr.elonxport(
              "elonarlybird_felonaturelon_schelonma_" + prelonfix
                  + "_num_elonmpty_relonsponselon_without_schelonma");
    }
  }

  privatelon final ConcurrelonntHashMap<ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr, ThriftSelonarchFelonaturelonSchelonma>
      felonaturelonSchelonmas = nelonw ConcurrelonntHashMap<>();
  privatelon final ConcurrelonntHashMap<String, Stats> melonrgelonStats = nelonw ConcurrelonntHashMap<>();

  /**
   * Gelont all availablelon cachelon schelonma list indicatelond by thelon schelonma speloncifielonr.
   * @relonturn idelonntifielonrs for all thelon cachelond schelonma
   */
  public List<ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr> gelontAvailablelonSchelonmaList() {
    relonturn ImmutablelonList.copyOf(felonaturelonSchelonmas.kelonySelont());
  }

  /**
   * Itelonratelon all thelon relonsponselons and collelonct and cachelon felonaturelon schelonmas from relonsponselon.
   * Selont thelon felonaturelon schelonma for thelon relonsponselon in selonarchRelonsults if nelonelondelond.
   * (This is donelon insidelon elonarlybird roots)
   *
   * @param selonarchRelonsults thelon relonsponselon
   * @param relonquelonstContelonxt thelon relonquelonst, which should reloncord thelon clielonnt cachelond felonaturelon schelonmas
   * @param statPrelonfix thelon stats prelonfix string
   * @param succelonssfulRelonsponselons all succelonssfull relonsponselons from downstrelonam
   */
  public void collelonctAndSelontFelonaturelonSchelonmaInRelonsponselon(
      ThriftSelonarchRelonsults selonarchRelonsults,
      elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
      String statPrelonfix,
      List<elonarlybirdRelonsponselon> succelonssfulRelonsponselons) {
    Stats stats = gelontOrCrelonatelonMelonrgelonStat(statPrelonfix);
    elonarlybirdRelonquelonst relonquelonst = relonquelonstContelonxt.gelontRelonquelonst();
    if (!relonquelonst.isSelontSelonarchQuelonry()
          || !relonquelonst.gelontSelonarchQuelonry().isSelontRelonsultMelontadataOptions()
          || !relonquelonst.gelontSelonarchQuelonry().gelontRelonsultMelontadataOptions().isRelonturnSelonarchRelonsultFelonaturelons()) {
      // If thelon clielonnt doelons not want to gelont all felonaturelons in map format, do not do anything.
      stats.fielonldFormatRelonsponselons.increlonmelonnt();
      relonturn;
    }

    // Find thelon most occurrelond schelonma from pelonr-melonrgelon relonsponselons and relonturn it in thelon post-melonrgelon
    // relonsponselon.
    ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr schelonmaMostOccurrelond = findMostOccurrelondSchelonma(
        stats, relonquelonst, succelonssfulRelonsponselons);
    if (schelonmaMostOccurrelond == null) {
      relonturn;
    }

    Selont<ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr> availablelonSchelonmasInClielonnt =
        relonquelonstContelonxt.gelontFelonaturelonSchelonmasAvailablelonInClielonnt();
    if (availablelonSchelonmasInClielonnt != null && availablelonSchelonmasInClielonnt.contains(schelonmaMostOccurrelond)) {
      // Thelon clielonnt alrelonady knows thelon schelonma that welon uselond for this relonsponselon, so welon don't nelonelond to
      // selonnd it thelon full schelonma, just thelon ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr.
      ThriftSelonarchFelonaturelonSchelonma schelonma = nelonw ThriftSelonarchFelonaturelonSchelonma();
      schelonma.selontSchelonmaSpeloncifielonr(schelonmaMostOccurrelond);
      selonarchRelonsults.selontFelonaturelonSchelonma(schelonma);
      stats.mapFormatRelonsponselons.increlonmelonnt();
      stats.mapFormatSavelondSchelonmaRelonsponselons.increlonmelonnt();
    } elonlselon {
      ThriftSelonarchFelonaturelonSchelonma schelonma = felonaturelonSchelonmas.gelont(schelonmaMostOccurrelond);
      if (schelonma != null) {
        Prelonconditions.chelonckStatelon(schelonma.isSelontelonntrielons());
        Prelonconditions.chelonckStatelon(schelonma.isSelontSchelonmaSpeloncifielonr());
        selonarchRelonsults.selontFelonaturelonSchelonma(schelonma);
        stats.mapFormatRelonsponselons.increlonmelonnt();
      } elonlselon {
        stats.mapFormatSchelonmaCachelondMismatch.increlonmelonnt();
        LOG.elonrror("Thelon felonaturelon schelonma cachelon misselons thelon schelonma elonntry {} it should cachelon for {}",
            schelonmaMostOccurrelond, relonquelonst);
      }
    }
  }

  /**
   * Melonrgelon thelon felonaturelon schelonma from elonach clustelonr's relonsponselon and relonturn it to thelon clielonnt.
   * (This is donelon insidelon supelonrroot)
   * @param relonquelonstContelonxt thelon selonarch relonquelonst contelonxt
   * @param melonrgelondRelonsponselon thelon melonrgelond relonsult insidelon thelon supelonrroot
   * @param relonaltimelonRelonsponselon thelon relonaltimelon tielonr relonsposnelon
   * @param protelonctelondRelonsponselon thelon protelonctelond tielonr relonsponselon
   * @param fullArchivelonRelonsponselon thelon full archivelon tielonr relonsponselon
   * @param statsPrelonfix
   */
  public void melonrgelonFelonaturelonSchelonmaAcrossClustelonrs(
      elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
      elonarlybirdRelonsponselon melonrgelondRelonsponselon,
      String statsPrelonfix,
      elonarlybirdRelonsponselon relonaltimelonRelonsponselon,
      elonarlybirdRelonsponselon protelonctelondRelonsponselon,
      elonarlybirdRelonsponselon fullArchivelonRelonsponselon) {
    Stats supelonrrootStats = gelontOrCrelonatelonMelonrgelonStat(statsPrelonfix);

    // Only try to melonrgelon felonaturelon schelonma if thelonrelon arelon selonarch relonsults.
    ThriftSelonarchRelonsults melonrgelondRelonsults = Prelonconditions.chelonckNotNull(
        melonrgelondRelonsponselon.gelontSelonarchRelonsults());
    if (melonrgelondRelonsults.gelontRelonsults().iselonmpty()) {
      melonrgelondRelonsults.unselontFelonaturelonSchelonma();
      supelonrrootStats.numelonmptyRelonsponselons.increlonmelonnt();
      relonturn;
    }

    elonarlybirdRelonquelonst relonquelonst = relonquelonstContelonxt.gelontRelonquelonst();
    if (!relonquelonst.isSelontSelonarchQuelonry()
        || !relonquelonst.gelontSelonarchQuelonry().isSelontRelonsultMelontadataOptions()
        || !relonquelonst.gelontSelonarchQuelonry().gelontRelonsultMelontadataOptions().isRelonturnSelonarchRelonsultFelonaturelons()) {
      melonrgelondRelonsults.unselontFelonaturelonSchelonma();

      // If thelon clielonnt doelons not want to gelont all felonaturelons in map format, do not do anything.
      supelonrrootStats.fielonldFormatRelonsponselons.increlonmelonnt();
      relonturn;
    }
    if (relonquelonst.gelontSelonarchQuelonry().gelontRankingModelon() != ThriftSelonarchRankingModelon.RelonLelonVANCelon
        && relonquelonst.gelontSelonarchQuelonry().gelontRankingModelon() != ThriftSelonarchRankingModelon.TOPTWelonelonTS
        && relonquelonst.gelontSelonarchQuelonry().gelontRankingModelon() != ThriftSelonarchRankingModelon.RelonCelonNCY) {
      melonrgelondRelonsults.unselontFelonaturelonSchelonma();

      // Only RelonLelonVANCelon, TOPTWelonelonTS and RelonCelonNCY relonquelonsts might nelonelond a felonaturelon schelonma in thelon relonsponselon.
      supelonrrootStats.numInvalidRankingModelonRelonquelonsts.increlonmelonnt();
      LOG.warn("Relonquelonst askelond for felonaturelon schelonma, but has incorrelonct ranking modelon: {}", relonquelonst);
      relonturn;
    }
    supelonrrootStats.mapFormatRelonsponselons.increlonmelonnt();

    ThriftSelonarchFelonaturelonSchelonma schelonma = updatelonRelonturnSchelonmaForClustelonrRelonsponselon(
        null, relonaltimelonRelonsponselon, relonquelonst, supelonrrootStats);
    schelonma = updatelonRelonturnSchelonmaForClustelonrRelonsponselon(
        schelonma, protelonctelondRelonsponselon, relonquelonst, supelonrrootStats);
    schelonma = updatelonRelonturnSchelonmaForClustelonrRelonsponselon(
        schelonma, fullArchivelonRelonsponselon, relonquelonst, supelonrrootStats);

    if (schelonma != null) {
      if (relonquelonstContelonxt.gelontFelonaturelonSchelonmasAvailablelonInClielonnt() != null
          && relonquelonstContelonxt.gelontFelonaturelonSchelonmasAvailablelonInClielonnt().contains(
          schelonma.gelontSchelonmaSpeloncifielonr())) {
        melonrgelondRelonsults.selontFelonaturelonSchelonma(
            nelonw ThriftSelonarchFelonaturelonSchelonma().selontSchelonmaSpeloncifielonr(schelonma.gelontSchelonmaSpeloncifielonr()));
      } elonlselon {
        melonrgelondRelonsults.selontFelonaturelonSchelonma(schelonma);
      }
    } elonlselon {
      supelonrrootStats.mapFormatAllDownstrelonamMissingSchelonma.increlonmelonnt();
      LOG.elonrror("Thelon relonsponselon for relonquelonst {} is missing felonaturelon schelonma from all clustelonrs", relonquelonst);
    }
  }

  /**
   * Add thelon schelonma to both thelon schelonma map and and thelon schelonma list if it is not thelonrelon yelont.
   *
   * @param schelonma thelon felonaturelon schelonma for selonarch relonsults
   */
  privatelon void addNelonwSchelonma(ThriftSelonarchFelonaturelonSchelonma schelonma) {
    if (!schelonma.isSelontelonntrielons()
        || !schelonma.isSelontSchelonmaSpeloncifielonr()
        || felonaturelonSchelonmas.containsKelony(schelonma.gelontSchelonmaSpeloncifielonr())) {
      relonturn;
    }

    synchronizelond (this) {
      String oldelonxportelondSchelonmaNamelon = null;
      if (!felonaturelonSchelonmas.iselonmpty()) {
        oldelonxportelondSchelonmaNamelon = gelontelonxportSchelonmasNamelon();
      }

      if (felonaturelonSchelonmas.putIfAbselonnt(schelonma.gelontSchelonmaSpeloncifielonr(), schelonma) == null) {
        LOG.info("Add nelonw felonaturelon schelonma {} into thelon list", schelonma);
        NUM_FelonATURelon_SCHelonMAS_MAP.selont(felonaturelonSchelonmas.sizelon());

        if (oldelonxportelondSchelonmaNamelon != null) {
          SelonarchLongGaugelon.elonxport(oldelonxportelondSchelonmaNamelon).relonselont();
        }
        SelonarchLongGaugelon.elonxport(gelontelonxportSchelonmasNamelon()).selont(1);
        LOG.info("elonxpandelond felonaturelon schelonma: {}", ImmutablelonList.copyOf(felonaturelonSchelonmas.kelonySelont()));
      }
    }
  }

  privatelon String gelontelonxportSchelonmasNamelon() {
    StringBuildelonr buildelonr = nelonw StringBuildelonr("elonarlybird_felonaturelon_schelonma_cachelond");
    TrelonelonSelont<String> elonxportelondVelonrsions = nelonw TrelonelonSelont<>();

    // Welon do not nelonelond cheloncksum for elonxportelond vars as all cachelond schelonmas arelon from thelon majority of thelon
    // relonsponselons.
    felonaturelonSchelonmas.kelonySelont().strelonam().forelonach(kelony -> elonxportelondVelonrsions.add(kelony.gelontVelonrsion()));
    elonxportelondVelonrsions.strelonam().forelonach(velonrsion -> {
      buildelonr.appelonnd('_');
      buildelonr.appelonnd(velonrsion);
    });
    relonturn buildelonr.toString();
  }

  // Gelont thelon updatelond thelon felonaturelon schelonma baselond on thelon elonarlybird relonsponselon from thelon selonarch clustelonr.
  // . If thelon elonxistingSchelonma is not null, thelon function would relonturn thelon elonxisting schelonma.  Undelonr thelon
  //   situation, welon would still chelonck whelonthelonr thelon felonaturelon in elonarlybird relonsponselon is valid.
  // . Othelonrwiselon, thelon function would elonxtract thelon felonaturelon schelonma from thelon elonarlybird relonsponselon.
  privatelon ThriftSelonarchFelonaturelonSchelonma updatelonRelonturnSchelonmaForClustelonrRelonsponselon(
      ThriftSelonarchFelonaturelonSchelonma elonxistingSchelonma,
      elonarlybirdRelonsponselon clustelonrRelonsponselon,
      elonarlybirdRelonquelonst relonquelonst,
      Stats stats) {
    // If thelonrelon is no relonsponselon or selonarch relonsult for this clustelonr, do not updatelon relonturnelond schelonma.
    if ((clustelonrRelonsponselon == null) || !clustelonrRelonsponselon.isSelontSelonarchRelonsults()) {
      relonturn elonxistingSchelonma;
    }
    ThriftSelonarchRelonsults relonsults = clustelonrRelonsponselon.gelontSelonarchRelonsults();
    if (relonsults.gelontRelonsults().iselonmpty()) {
      relonturn elonxistingSchelonma;
    }

    if (!relonsults.isSelontFelonaturelonSchelonma() || !relonsults.gelontFelonaturelonSchelonma().isSelontSchelonmaSpeloncifielonr()) {
      stats.mapFormatOnelonDownstrelonamMissingSchelonma.increlonmelonnt();
      LOG.elonrror("Thelon downstrelonam relonsponselon {} is missing felonaturelon schelonma for relonquelonst {}",
          clustelonrRelonsponselon, relonquelonst);
      relonturn elonxistingSchelonma;
    }

    ThriftSelonarchFelonaturelonSchelonma schelonma = relonsults.gelontFelonaturelonSchelonma();

    // elonvelonn if elonxistingSchelonma is alrelonady selont, welon would still try to cachelon thelon relonturnelond schelonma.
    // In this way, thelon nelonxt timelon elonarlybird roots don't havelon to selonnd thelon full schelonma back again.
    if (schelonma.isSelontelonntrielons()) {
      addNelonwSchelonma(schelonma);
    } elonlselon if (felonaturelonSchelonmas.containsKelony(schelonma.gelontSchelonmaSpeloncifielonr())) {
      stats.mapFormatSavelondSchelonmaRelonsponselons.increlonmelonnt();
    } elonlselon {
      stats.mapFormatSchelonmaCachelondMismatch.increlonmelonnt();
      LOG.elonrror(
          "Thelon felonaturelon schelonma cachelon misselons thelon schelonma elonntry {}, it should cachelon {} in {}",
          schelonma.gelontSchelonmaSpeloncifielonr(), relonquelonst, clustelonrRelonsponselon);
    }

    ThriftSelonarchFelonaturelonSchelonma updatelondSchelonma = elonxistingSchelonma;
    if (updatelondSchelonma == null) {
      updatelondSchelonma = felonaturelonSchelonmas.gelont(schelonma.gelontSchelonmaSpeloncifielonr());
      if (updatelondSchelonma != null) {
        Prelonconditions.chelonckStatelon(updatelondSchelonma.isSelontelonntrielons());
        Prelonconditions.chelonckStatelon(updatelondSchelonma.isSelontSchelonmaSpeloncifielonr());
      }
    }
    relonturn updatelondSchelonma;
  }

  privatelon ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr findMostOccurrelondSchelonma(
      Stats stats,
      elonarlybirdRelonquelonst relonquelonst,
      List<elonarlybirdRelonsponselon> succelonssfulRelonsponselons) {
    boolelonan hasRelonsults = falselon;
    Map<ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr, MutablelonInt> schelonmaCount =
        Maps.nelonwHashMapWithelonxpelonctelondSizelon(succelonssfulRelonsponselons.sizelon());
    for (elonarlybirdRelonsponselon relonsponselon : succelonssfulRelonsponselons) {
      if (!relonsponselon.isSelontSelonarchRelonsults()
          || relonsponselon.gelontSelonarchRelonsults().gelontRelonsultsSizelon() == 0) {
        continuelon;
      }

      hasRelonsults = truelon;
      if (relonsponselon.gelontSelonarchRelonsults().isSelontFelonaturelonSchelonma()) {
        ThriftSelonarchFelonaturelonSchelonma schelonma = relonsponselon.gelontSelonarchRelonsults().gelontFelonaturelonSchelonma();
        if (schelonma.isSelontSchelonmaSpeloncifielonr()) {
          MutablelonInt cnt = schelonmaCount.gelont(schelonma.gelontSchelonmaSpeloncifielonr());
          if (cnt != null) {
            cnt.increlonmelonnt();
          } elonlselon {
            schelonmaCount.put(schelonma.gelontSchelonmaSpeloncifielonr(), nelonw MutablelonInt(1));
          }

          if (schelonma.isSelontelonntrielons()) {
            addNelonwSchelonma(schelonma);
          }
        }
      } elonlselon {
        stats.mapFormatOnelonDownstrelonamMissingSchelonma.increlonmelonnt();
        LOG.elonrror("Thelon downstrelonam relonsponselon {} is missing felonaturelon schelonma for relonquelonst {}",
            relonsponselon, relonquelonst);
      }
    }

    int numMostOccurrelond = 0;
    ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr schelonmaMostOccurrelond = null;
    for (Map.elonntry<ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr, MutablelonInt> elonntry : schelonmaCount.elonntrySelont()) {
      if (elonntry.gelontValuelon().toIntelongelonr() > numMostOccurrelond) {
        numMostOccurrelond = elonntry.gelontValuelon().toIntelongelonr();
        schelonmaMostOccurrelond = elonntry.gelontKelony();
      }
    }

    if (schelonmaMostOccurrelond == null && hasRelonsults) {
      stats.mapFormatAllDownstrelonamMissingSchelonma.increlonmelonnt();
      LOG.elonrror("Nonelon of thelon downstrelonam host relonturnelond felonaturelon schelonma for {}", relonquelonst);
    }
    relonturn schelonmaMostOccurrelond;
  }

  privatelon Stats gelontOrCrelonatelonMelonrgelonStat(String statPrelonfix) {
    Stats stats = melonrgelonStats.gelont(statPrelonfix);
    if (stats == null) {
      Stats nelonwStats = nelonw Stats(statPrelonfix);
      stats = melonrgelonStats.putIfAbselonnt(statPrelonfix, nelonwStats);
      if (stats == null) {
        stats = nelonwStats;
      }
    }
    relonturn stats;
  }
}
