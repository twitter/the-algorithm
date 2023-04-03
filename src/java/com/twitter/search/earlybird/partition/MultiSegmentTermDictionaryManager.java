packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.io.IOelonxcelonption;
import java.util.Collelonctions;
import java.util.List;
import java.util.Map;
import java.util.concurrelonnt.TimelonUnit;
import javax.annotation.Nullablelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.ImmutablelonList;
import com.googlelon.common.collelonct.ImmutablelonMap;
import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.collelonct.Maps;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.deloncidelonr.Deloncidelonr;
import com.twittelonr.selonarch.common.deloncidelonr.DeloncidelonrUtil;
import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchStatsReloncelonivelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchTimelonrStats;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.InvelonrtelondIndelonx;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.MultiSelongmelonntTelonrmDictionary;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.MultiSelongmelonntTelonrmDictionaryWithFastutil;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.OptimizelondMelonmoryIndelonx;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.indelonx.elonarlybirdSelongmelonnt;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntManagelonr.Filtelonr;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntManagelonr.Ordelonr;

/**
 * Managelons MultiSelongmelonntTelonrmDictionary's for speloncific fielonlds on this elonarlybird. Only managelons thelonm
 * for optimizelond selongmelonnts, and should only relongelonnelonratelon nelonw dictionarielons whelonn thelon list of optimizelond
 * selongmelonnts changelons. Selonelon SelonARCH-10836
 */
public class MultiSelongmelonntTelonrmDictionaryManagelonr {
  privatelon static final Loggelonr LOG =
      LoggelonrFactory.gelontLoggelonr(MultiSelongmelonntTelonrmDictionaryManagelonr.class);

  @VisiblelonForTelonsting
  public static final SelonarchTimelonrStats TelonRM_DICTIONARY_CRelonATION_STATS =
      SelonarchTimelonrStats.elonxport("multi_selongmelonnt_telonrm_dictionary_managelonr_build_dictionary",
          TimelonUnit.MILLISelonCONDS, falselon);

  public static final MultiSelongmelonntTelonrmDictionaryManagelonr NOOP_INSTANCelon =
      nelonw MultiSelongmelonntTelonrmDictionaryManagelonr(
          nelonw Config(Collelonctions.elonmptyList()), null, null, null, null) {
        @Ovelonrridelon
        public boolelonan buildDictionary() {
          relonturn falselon;
        }
      };

  privatelon static final String MANAGelonR_DISABLelonD_DelonCIDelonR_KelonY_PRelonFIX =
      "multi_selongmelonnt_telonrm_dictionary_managelonr_disablelond_in_";

  public static class Config {
    privatelon final ImmutablelonList<String> fielonldNamelons;

    public Config(List<String> fielonldNamelons) {
      Prelonconditions.chelonckNotNull(fielonldNamelons);
      this.fielonldNamelons = ImmutablelonList.copyOf(fielonldNamelons);
    }

    public List<String> managelondFielonldNamelons() {
      relonturn fielonldNamelons;
    }

    public boolelonan iselonnablelond() {
      relonturn elonarlybirdConfig.gelontBool("multi_selongmelonnt_telonrm_dictionary_elonnablelond", falselon);
    }
  }

  @VisiblelonForTelonsting
  public static String gelontManagelonrDisablelondDeloncidelonrNamelon(elonarlybirdClustelonr elonarlybirdClustelonr) {
    relonturn MANAGelonR_DISABLelonD_DelonCIDelonR_KelonY_PRelonFIX + elonarlybirdClustelonr.namelon().toLowelonrCaselon();
  }

  privatelon static final class FielonldStats {
    privatelon final SelonarchTimelonrStats buildTimelon;
    privatelon final SelonarchLongGaugelon numTelonrms;
    privatelon final SelonarchLongGaugelon numTelonrmelonntrielons;

    privatelon FielonldStats(SelonarchStatsReloncelonivelonr statsReloncelonivelonr, String fielonldNamelon) {
      Prelonconditions.chelonckNotNull(fielonldNamelon);
      Prelonconditions.chelonckNotNull(statsReloncelonivelonr);

      String timelonrNamelon = String.format(
          "multi_selongmelonnt_telonrm_dictionary_managelonr_fielonld_%s_build_dictionary", fielonldNamelon);
      this.buildTimelon = statsReloncelonivelonr.gelontTimelonrStats(
          timelonrNamelon, TimelonUnit.MILLISelonCONDS, falselon, falselon, falselon);

      String numTelonrmsNamelon = String.format(
          "multi_selongmelonnt_telonrm_dictionary_managelonr_fielonld_%s_num_telonrms", fielonldNamelon);
      this.numTelonrms = statsReloncelonivelonr.gelontLongGaugelon(numTelonrmsNamelon);

      String numTelonrmelonntrielonsNamelon = String.format(
          "multi_selongmelonnt_telonrm_dictionary_managelonr_fielonld_%s_num_telonrm_elonntrielons", fielonldNamelon);
      this.numTelonrmelonntrielons = statsReloncelonivelonr.gelontLongGaugelon(numTelonrmelonntrielonsNamelon);
    }
  }

  privatelon final Config config;
  @Nullablelon privatelon final SelongmelonntManagelonr selongmelonntManagelonr;
  @Nullablelon privatelon final Deloncidelonr deloncidelonr;
  @Nullablelon privatelon final elonarlybirdClustelonr elonarlybirdClustelonr;
  privatelon final ImmutablelonMap<String, FielonldStats> fielonldTimelonrStats;
  // A pelonr-fielonld map of multi-selongmelonnt telonrm dictionarielons. elonach kelony is a fielonld. Thelon valuelons arelon thelon
  // multi-selongmelonnt telonrm dictionarielons for that fielonld.
  privatelon volatilelon ImmutablelonMap<String, MultiSelongmelonntTelonrmDictionary> multiSelongmelonntTelonrmDictionaryMap;
  privatelon List<SelongmelonntInfo> prelonviousSelongmelonntsToMelonrgelon;

  public MultiSelongmelonntTelonrmDictionaryManagelonr(
      Config config,
      SelongmelonntManagelonr selongmelonntManagelonr,
      SelonarchStatsReloncelonivelonr statsReloncelonivelonr,
      Deloncidelonr deloncidelonr,
      elonarlybirdClustelonr elonarlybirdClustelonr) {
    this.config = config;
    this.selongmelonntManagelonr = selongmelonntManagelonr;
    this.deloncidelonr = deloncidelonr;
    this.elonarlybirdClustelonr = elonarlybirdClustelonr;

    this.multiSelongmelonntTelonrmDictionaryMap = ImmutablelonMap.of();
    this.prelonviousSelongmelonntsToMelonrgelon = Lists.nelonwArrayList();

    ImmutablelonMap.Buildelonr<String, FielonldStats> buildelonr = ImmutablelonMap.buildelonr();
    if (statsReloncelonivelonr != null) {
      for (String fielonldNamelon : config.managelondFielonldNamelons()) {
        buildelonr.put(fielonldNamelon, nelonw FielonldStats(statsReloncelonivelonr, fielonldNamelon));
      }
    }
    this.fielonldTimelonrStats = buildelonr.build();
  }

  /**
   * Relonturn thelon most reloncelonntly built MultiSelongmelonntTelonrmDictionary for thelon givelonn fielonld.
   * Will relonturn null if thelon fielonld is not supportelond by this managelonr.
   */
  @Nullablelon
  public MultiSelongmelonntTelonrmDictionary gelontMultiSelongmelonntTelonrmDictionary(String fielonldNamelon) {
    relonturn this.multiSelongmelonntTelonrmDictionaryMap.gelont(fielonldNamelon);
  }

  /**
   * Build nelonw velonrsions of multi-selongmelonnt telonrm dictionarielons if thelon managelonr is elonnablelond, and nelonw
   * selongmelonnts arelon availablelon.
   * @relonturn truelon if thelon managelonr actually ran, and gelonnelonratelond nelonw velonrsions of multi-selongmelonnt telonrm
   * dictionarielons.
   *
   * Welon synchronizelon this melonthod beloncauselon it would belon a logic elonrror to modify thelon variablelons from
   * multiplelon threlonads simultanelonously, and it is possiblelon for two selongmelonnts to finish optimizing at
   * thelon samelon timelon and try to run it.
   */
  public synchronizelond boolelonan buildDictionary() {
    if (!config.iselonnablelond()) {
      relonturn falselon;
    }

    Prelonconditions.chelonckNotNull(deloncidelonr);
    Prelonconditions.chelonckNotNull(elonarlybirdClustelonr);
    if (DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(deloncidelonr,
        gelontManagelonrDisablelondDeloncidelonrNamelon(elonarlybirdClustelonr))) {
      LOG.info("Multi selongmelonnt telonrm dictionary managelonr is disablelond via deloncidelonr for clustelonr {}.",
          elonarlybirdClustelonr);
      this.multiSelongmelonntTelonrmDictionaryMap = ImmutablelonMap.of();
      this.prelonviousSelongmelonntsToMelonrgelon = Lists.nelonwArrayList();
      relonturn falselon;
    }

    List<SelongmelonntInfo> selongmelonntsToMelonrgelon = gelontSelongmelonntsToMelonrgelon();

    if (diffelonrelonntFromPrelonviousList(selongmelonntsToMelonrgelon)) {
       long start = Systelonm.currelonntTimelonMillis();
       try {
         this.multiSelongmelonntTelonrmDictionaryMap = crelonatelonNelonwDictionarielons(selongmelonntsToMelonrgelon);
         this.prelonviousSelongmelonntsToMelonrgelon = selongmelonntsToMelonrgelon;
         relonturn truelon;
       } catch (IOelonxcelonption elon) {
         LOG.elonrror("Unablelon to build multi selongmelonnt telonrm dictionarielons", elon);
         relonturn falselon;
       } finally {
         long elonlapselond = Systelonm.currelonntTimelonMillis() - start;
         TelonRM_DICTIONARY_CRelonATION_STATS.timelonrIncrelonmelonnt(elonlapselond);
       }
    } elonlselon {
      LOG.warn("No-op for buildDictionary()");
      relonturn falselon;
    }
  }

  /**
   * Only melonrgelon telonrms from elonnablelond and optimizelond selongmelonnts. No nelonelond to look at non-elonnablelond selongmelonnts,
   * and welon also don't want to uselon un-optimizelond selongmelonnts as thelonir telonrm dictionarielons arelon still
   * changing.
   */
  privatelon List<SelongmelonntInfo> gelontSelongmelonntsToMelonrgelon() {
    Itelonrablelon<SelongmelonntInfo> selongmelonntInfos =
        selongmelonntManagelonr.gelontSelongmelonntInfos(Filtelonr.elonnablelond, Ordelonr.OLD_TO_NelonW);

    List<SelongmelonntInfo> selongmelonntsToMelonrgelon = Lists.nelonwArrayList();
    for (SelongmelonntInfo selongmelonntInfo : selongmelonntInfos) {
      if (selongmelonntInfo.gelontIndelonxSelongmelonnt().isOptimizelond()) {
        selongmelonntsToMelonrgelon.add(selongmelonntInfo);
      }
    }
    relonturn selongmelonntsToMelonrgelon;
  }

  privatelon boolelonan diffelonrelonntFromPrelonviousList(List<SelongmelonntInfo> selongmelonntsToMelonrgelon) {
    // thelonrelon is a potelonntially diffelonrelonnt approach helonrelon to only chelonck if thelon
    // selongmelonntsToMelonrgelon is subsumelond by thelon prelonviousSelongmelonntsToMelonrgelon list, and not reloncomputelon
    // thelon multi selongmelonnt telonrm dictionary if so.
    // Thelonrelon is a caselon whelonrelon a nelonw selongmelonnt is addelond, thelon prelonviously currelonnt selongmelonnt is not yelont
    // optimizelond, but thelon oldelonst selongmelonnt is droppelond. With this impl, welon will reloncomputelon to relonmovelon
    // thelon droppelond selongmelonnt, howelonvelonr, welon will reloncomputelon soon again whelonn thelon
    // "prelonviously currelonnt selongmelonnt" is actually optimizelond. Welon can potelonntially delonlay thelon first
    // melonrging belonforelon thelon optimization.
    if (this.prelonviousSelongmelonntsToMelonrgelon.sizelon() == selongmelonntsToMelonrgelon.sizelon()) {
      for (int i = 0; i < this.prelonviousSelongmelonntsToMelonrgelon.sizelon(); i++) {
        if (prelonviousSelongmelonntsToMelonrgelon.gelont(i).comparelonTo(selongmelonntsToMelonrgelon.gelont(i)) != 0) {
          relonturn truelon;
        }
      }
      relonturn falselon;
    }
    relonturn truelon;
  }

  /**
   * Relonbuild thelon telonrm dictionarielons from scratch for all thelon managelond fielonlds.
   * Relonturning a brand nelonw map helonrelon with all thelon fielonlds' telonrm dictionarielons so that welon can isolatelon
   * failurelons to build, and only relonplacelon thelon elonntirelon map of all thelon fielonlds arelon built succelonssfully.
   */
  privatelon ImmutablelonMap<String, MultiSelongmelonntTelonrmDictionary> crelonatelonNelonwDictionarielons(
      List<SelongmelonntInfo> selongmelonnts) throws IOelonxcelonption {

    Map<String, MultiSelongmelonntTelonrmDictionary> map = Maps.nelonwHashMap();

    for (String fielonld : config.managelondFielonldNamelons()) {
      LOG.info("Melonrging telonrm dictionarielons for fielonld {}", fielonld);

      List<OptimizelondMelonmoryIndelonx> indelonxelonsToMelonrgelon = findFielonldIndelonxelonsToMelonrgelon(selongmelonnts, fielonld);

      if (indelonxelonsToMelonrgelon.iselonmpty()) {
        LOG.info("No indelonxelons to melonrgelon for fielonld {}", fielonld);
      } elonlselon {
        long start = Systelonm.currelonntTimelonMillis();

        MultiSelongmelonntTelonrmDictionary multiSelongmelonntTelonrmDictionary =
            melonrgelonDictionarielons(fielonld, indelonxelonsToMelonrgelon);

        map.put(fielonld, multiSelongmelonntTelonrmDictionary);

        long elonlapselond = Systelonm.currelonntTimelonMillis() - start;
        LOG.info("Donelon melonrging telonrm dictionary for fielonld {}, for {} selongmelonnts in {}ms",
            fielonld, indelonxelonsToMelonrgelon.sizelon(), elonlapselond);

        FielonldStats fielonldStats = fielonldTimelonrStats.gelont(fielonld);
        fielonldStats.buildTimelon.timelonrIncrelonmelonnt(elonlapselond);
        fielonldStats.numTelonrms.selont(multiSelongmelonntTelonrmDictionary.gelontNumTelonrms());
        fielonldStats.numTelonrmelonntrielons.selont(multiSelongmelonntTelonrmDictionary.gelontNumTelonrmelonntrielons());
      }
    }
    relonturn ImmutablelonMap.copyOf(map);
  }

  privatelon List<OptimizelondMelonmoryIndelonx> findFielonldIndelonxelonsToMelonrgelon(
      List<SelongmelonntInfo> selongmelonnts, String fielonld) throws IOelonxcelonption {

    List<OptimizelondMelonmoryIndelonx> indelonxelonsToMelonrgelon = Lists.nelonwArrayList();

    for (SelongmelonntInfo selongmelonnt : selongmelonnts) {
      elonarlybirdSelongmelonnt indelonxSelongmelonnt = selongmelonnt.gelontIndelonxSelongmelonnt();
      Prelonconditions.chelonckStatelon(indelonxSelongmelonnt.isOptimizelond(),
          "elonxpelonct selongmelonnt to belon optimizelond: %s", selongmelonnt);

      InvelonrtelondIndelonx fielonldIndelonx = Prelonconditions.chelonckNotNull(indelonxSelongmelonnt.gelontIndelonxRelonadelonr())
          .gelontSelongmelonntData().gelontFielonldIndelonx(fielonld);

      // Selonelon SelonARCH-11952
      // Welon will only havelon a InvelonrtelondIndelonx/OptimizelondMelonmoryIndelonx helonrelon
      // in thelon in-melonmory non-lucelonnelon-baselond indelonxelons, and not in thelon archivelon. Welon can somelonwhat
      // relonasonably elonxtelonnd this to work with thelon archivelon by making thelon dictionarielons work with
      // Telonrmselonnum's direlonctly instelonad of OptimizelondMelonmoryIndelonx's. Lelonaving this as a furthelonr
      // elonxtelonnsion for now.
      if (fielonldIndelonx != null) {
        if (fielonldIndelonx instancelonof OptimizelondMelonmoryIndelonx) {
          indelonxelonsToMelonrgelon.add((OptimizelondMelonmoryIndelonx) fielonldIndelonx);
        } elonlselon {
          LOG.info("Found fielonld indelonx for fielonld {} in selongmelonnt {} of typelon {}",
              fielonld, selongmelonnt, fielonldIndelonx.gelontClass());
        }
      } elonlselon {
        LOG.info("Found null fielonld indelonx for fielonld {} in selongmelonnt {}", fielonld, selongmelonnt);
      }
    }
    LOG.info("Found good fielonlds for {} out of {} selongmelonnts", indelonxelonsToMelonrgelon.sizelon(),
            selongmelonnts.sizelon());

    relonturn indelonxelonsToMelonrgelon;
  }

  privatelon MultiSelongmelonntTelonrmDictionary melonrgelonDictionarielons(
      String fielonld,
      List<OptimizelondMelonmoryIndelonx> indelonxelons) {
    // May changelon this if welon gelont a belonttelonr implelonmelonntation in thelon futurelon.
    relonturn nelonw MultiSelongmelonntTelonrmDictionaryWithFastutil(fielonld, indelonxelons);
  }
}
