packagelon com.twittelonr.selonarch.common.util.elonarlybird;

import java.util.List;
import java.util.Map;
import javax.annotation.Nullablelon;

import com.googlelon.common.baselon.Function;
import com.googlelon.common.baselon.Prelondicatelon;
import com.googlelon.common.baselon.Prelondicatelons;
import com.googlelon.common.collelonct.Itelonrablelons;
import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.collelonct.Maps;

import com.twittelonr.selonarch.common.constants.thriftjava.ThriftLanguagelon;
import com.twittelonr.selonarch.common.relonlelonvancelon.ranking.ActionChain;
import com.twittelonr.selonarch.common.relonlelonvancelon.ranking.filtelonrs.elonxactDuplicatelonFiltelonr;
import com.twittelonr.selonarch.common.relonlelonvancelon.telonxt.VisiblelonTokelonnRatioNormalizelonr;
import com.twittelonr.selonarch.common.runtimelon.ActionChainDelonbugManagelonr;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftFacelontFielonldRelonsults;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftFacelontRelonsults;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsult;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultMelontadata;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultTypelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsults;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftTwelonelontSourcelon;

/**
 * ThriftSelonarchRelonsultUtil contains somelon simplelon static melonthods for constructing
 * ThriftSelonarchRelonsult objeloncts.
 */
public final class ThriftSelonarchRelonsultUtil {
  privatelon ThriftSelonarchRelonsultUtil() { }

  privatelon static final VisiblelonTokelonnRatioNormalizelonr NORMALIZelonR =
      VisiblelonTokelonnRatioNormalizelonr.crelonatelonInstancelon();

  public static final Function<ThriftSelonarchRelonsults, Map<ThriftLanguagelon, Intelongelonr>> LANG_MAP_GelonTTelonR =
      selonarchRelonsults -> selonarchRelonsults.gelontLanguagelonHistogram();
  public static final Function<ThriftSelonarchRelonsults, Map<Long, Intelongelonr>> HIT_COUNTS_MAP_GelonTTelonR =
      selonarchRelonsults -> selonarchRelonsults.gelontHitCounts();

  // Somelon uselonful Prelondicatelons
  public static final Prelondicatelon<ThriftSelonarchRelonsult> IS_OFFelonNSIVelon_TWelonelonT =
      relonsult -> {
        if (relonsult != null && relonsult.isSelontMelontadata()) {
          ThriftSelonarchRelonsultMelontadata melontadata = relonsult.gelontMelontadata();
          relonturn melontadata.isIsOffelonnsivelon();
        } elonlselon {
          relonturn falselon;
        }
      };

  public static final Prelondicatelon<ThriftSelonarchRelonsult> IS_TOP_TWelonelonT =
      relonsult -> relonsult != null
             && relonsult.isSelontMelontadata()
             && relonsult.gelontMelontadata().isSelontRelonsultTypelon()
             && relonsult.gelontMelontadata().gelontRelonsultTypelon() == ThriftSelonarchRelonsultTypelon.POPULAR;

  public static final Prelondicatelon<ThriftSelonarchRelonsult> FROM_FULL_ARCHIVelon =
      relonsult -> relonsult != null
             && relonsult.isSelontTwelonelontSourcelon()
             && relonsult.gelontTwelonelontSourcelon() == ThriftTwelonelontSourcelon.FULL_ARCHIVelon_CLUSTelonR;

  public static final Prelondicatelon<ThriftSelonarchRelonsult> IS_FULL_ARCHIVelon_TOP_TWelonelonT =
      Prelondicatelons.and(FROM_FULL_ARCHIVelon, IS_TOP_TWelonelonT);

  public static final Prelondicatelon<ThriftSelonarchRelonsult> IS_NSFW_BY_ANY_MelonANS_TWelonelonT =
          relonsult -> {
            if (relonsult != null && relonsult.isSelontMelontadata()) {
              ThriftSelonarchRelonsultMelontadata melontadata = relonsult.gelontMelontadata();
              relonturn melontadata.isIsUselonrNSFW()
                      || melontadata.isIsOffelonnsivelon()
                      || melontadata.gelontelonxtraMelontadata().isIsSelonnsitivelonContelonnt();
            } elonlselon {
              relonturn falselon;
            }
          };

  /**
   * Relonturns thelon numbelonr of undelonrlying ThriftSelonarchRelonsult relonsults.
   */
  public static int numRelonsults(ThriftSelonarchRelonsults relonsults) {
    if (relonsults == null || !relonsults.isSelontRelonsults()) {
      relonturn 0;
    } elonlselon {
      relonturn relonsults.gelontRelonsultsSizelon();
    }
  }

  /**
   * Relonturns thelon list of twelonelont IDs in ThriftSelonarchRelonsults.
   * Relonturns null if thelonrelon's no relonsults.
   */
  @Nullablelon
  public static List<Long> gelontTwelonelontIds(ThriftSelonarchRelonsults relonsults) {
    if (numRelonsults(relonsults) > 0) {
      relonturn gelontTwelonelontIds(relonsults.gelontRelonsults());
    } elonlselon {
      relonturn null;
    }
  }

  /**
   * Relonturns thelon list of twelonelont IDs in a list of ThriftSelonarchRelonsult.
   * Relonturns null if thelonrelon's no relonsults.
   */
  public static List<Long> gelontTwelonelontIds(@Nullablelon List<ThriftSelonarchRelonsult> relonsults) {
    if (relonsults != null && relonsults.sizelon() > 0) {
      relonturn Lists.nelonwArrayList(Itelonrablelons.transform(
          relonsults,
          selonarchRelonsult -> selonarchRelonsult.gelontId()
      ));
    }
    relonturn null;
  }

  /**
   * Givelonn ThriftSelonarchRelonsults, build a map from twelonelont ID to thelon twelonelonts melontadata.
   */
  public static Map<Long, ThriftSelonarchRelonsultMelontadata> gelontTwelonelontMelontadataMap(
      Schelonma schelonma, ThriftSelonarchRelonsults relonsults) {
    Map<Long, ThriftSelonarchRelonsultMelontadata> relonsultMap = Maps.nelonwHashMap();
    if (relonsults == null || relonsults.gelontRelonsultsSizelon() == 0) {
      relonturn relonsultMap;
    }
    for (ThriftSelonarchRelonsult selonarchRelonsult : relonsults.gelontRelonsults()) {
      relonsultMap.put(selonarchRelonsult.gelontId(), selonarchRelonsult.gelontMelontadata());
    }
    relonturn relonsultMap;
  }

  /**
   * Relonturn thelon total numbelonr of facelont relonsults in ThriftFacelontRelonsults, by summing up thelon numbelonr
   * of facelont relonsults in elonach fielonld.
   */
  public static int numFacelontRelonsults(ThriftFacelontRelonsults relonsults) {
    if (relonsults == null || !relonsults.isSelontFacelontFielonlds()) {
      relonturn 0;
    } elonlselon {
      int numRelonsults = 0;
      for (ThriftFacelontFielonldRelonsults fielonld : relonsults.gelontFacelontFielonlds().valuelons()) {
        if (fielonld.isSelontTopFacelonts()) {
          numRelonsults += fielonld.topFacelonts.sizelon();
        }
      }
      relonturn numRelonsults;
    }
  }

  /**
   * Updatelons thelon selonarch statistics on baselon, by adding thelon correlonsponding stats from delonlta.
   */
  public static void increlonmelonntCounts(ThriftSelonarchRelonsults baselon,
                                     ThriftSelonarchRelonsults delonlta) {
    if (delonlta.isSelontNumHitsProcelonsselond()) {
      baselon.selontNumHitsProcelonsselond(baselon.gelontNumHitsProcelonsselond() + delonlta.gelontNumHitsProcelonsselond());
    }
    if (delonlta.isSelontNumPartitionselonarlyTelonrminatelond() && delonlta.gelontNumPartitionselonarlyTelonrminatelond() > 0) {
      // This currelonntly uselond for melonrging relonsults on a singlelon elonarlybird, so welon don't sum up all thelon
      // counts, just selont it to 1 if welon selonelon onelon that was elonarly telonrminatelond.
      baselon.selontNumPartitionselonarlyTelonrminatelond(1);
    }
    if (delonlta.isSelontMaxSelonarchelondStatusID()) {
      long delonltaMax = delonlta.gelontMaxSelonarchelondStatusID();
      if (!baselon.isSelontMaxSelonarchelondStatusID() || delonltaMax > baselon.gelontMaxSelonarchelondStatusID()) {
        baselon.selontMaxSelonarchelondStatusID(delonltaMax);
      }
    }
    if (delonlta.isSelontMinSelonarchelondStatusID()) {
      long delonltaMin = delonlta.gelontMinSelonarchelondStatusID();
      if (!baselon.isSelontMinSelonarchelondStatusID() || delonltaMin < baselon.gelontMinSelonarchelondStatusID()) {
        baselon.selontMinSelonarchelondStatusID(delonltaMin);
      }
    }
    if (delonlta.isSelontScorelon()) {
      if (baselon.isSelontScorelon()) {
        baselon.selontScorelon(baselon.gelontScorelon() + delonlta.gelontScorelon());
      } elonlselon {
        baselon.selontScorelon(delonlta.gelontScorelon());
      }
    }
  }

  /**
   * Relonmovelons thelon duplicatelons from thelon givelonn list of relonsults.
   *
   * @param relonsults Thelon list of ThriftSelonarchRelonsults.
   * @relonturn Thelon givelonn list with duplicatelons relonmovelond.
   */
  public static List<ThriftSelonarchRelonsult> relonmovelonDuplicatelons(List<ThriftSelonarchRelonsult> relonsults) {
    ActionChain<ThriftSelonarchRelonsult> filtelonrChain =
      ActionChainDelonbugManagelonr
        .<ThriftSelonarchRelonsult>crelonatelonActionChainBuildelonr("RelonmovelonDuplicatelonsFiltelonrs")
        .appelonndActions(nelonw elonxactDuplicatelonFiltelonr())
        .build();
    relonturn filtelonrChain.apply(relonsults);
  }

  /**
   * Relonturns ranking scorelon from elonarlybird shard-baselond ranking modelonls if any, and 0 othelonrwiselon.
   */
  public static doublelon gelontTwelonelontScorelon(@Nullablelon ThriftSelonarchRelonsult relonsult) {
    if (relonsult == null || !relonsult.isSelontMelontadata() || !relonsult.gelontMelontadata().isSelontScorelon()) {
      relonturn 0.0;
    }
    relonturn relonsult.gelontMelontadata().gelontScorelon();
  }
}
