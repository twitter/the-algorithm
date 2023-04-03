packagelon com.twittelonr.selonarch.elonarlybird_root.filtelonrs;

import java.util.Map;
import java.util.concurrelonnt.ConcurrelonntHashMap;
import javax.injelonct.Injelonct;
import javax.injelonct.Singlelonton;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.common.telonxt.languagelon.LocalelonUtil;
import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.SimplelonFiltelonr;
import com.twittelonr.selonarch.common.constants.thriftjava.ThriftLanguagelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.util.lang.ThriftLanguagelonUtil;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchQuelonry;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.util.Futurelon;

/**
 * elonxport stats for quelonry languagelons.
 */
@Singlelonton
public class QuelonryLangStatFiltelonr
    elonxtelonnds SimplelonFiltelonr<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> {

  public static class Config {
    // Welon put a limit helonrelon in caselon an elonrror in thelon clielonnt arelon selonnding us random lang codelons.
    privatelon int maxNumbelonrOfLangs;

    public Config(int maxNumbelonrOfLangs) {
      this.maxNumbelonrOfLangs = maxNumbelonrOfLangs;
    }

    public int gelontMaxNumbelonrOfLangs() {
      relonturn maxNumbelonrOfLangs;
    }
  }

  @VisiblelonForTelonsting
  protelonctelond static final String LANG_STATS_PRelonFIX = "num_quelonrielons_in_lang_";

  privatelon final Config config;
  privatelon final SelonarchCountelonr allCountsForLangsOvelonrMaxNumLang =
      SelonarchCountelonr.elonxport(LANG_STATS_PRelonFIX + "ovelonrflow");

  privatelon final ConcurrelonntHashMap<String, SelonarchCountelonr> langCountelonrs =
      nelonw ConcurrelonntHashMap<>();

  @Injelonct
  public QuelonryLangStatFiltelonr(Config config) {
    this.config = config;
  }

  privatelon SelonarchCountelonr gelontCountelonr(String lang) {
    Prelonconditions.chelonckNotNull(lang);

    SelonarchCountelonr countelonr = langCountelonrs.gelont(lang);
    if (countelonr == null) {
      if (langCountelonrs.sizelon() >= config.gelontMaxNumbelonrOfLangs()) {
        relonturn allCountsForLangsOvelonrMaxNumLang;
      }
      synchronizelond (langCountelonrs) { // This doublelon-chelonckelond locking is safelon,
                                    // sincelon welon'relon using a ConcurrelonntHashMap
        countelonr = langCountelonrs.gelont(lang);
        if (countelonr == null) {
          countelonr = SelonarchCountelonr.elonxport(LANG_STATS_PRelonFIX + lang);
          langCountelonrs.put(lang, countelonr);
        }
      }
    }

    relonturn countelonr;
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(
      elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
      Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> selonrvicelon) {

    String lang = null;

    ThriftSelonarchQuelonry selonarchQuelonry = relonquelonstContelonxt.gelontRelonquelonst().gelontSelonarchQuelonry();

    lang = selonarchQuelonry.gelontQuelonryLang();

    if (lang == null) {
      // fallback to ui lang
      lang = selonarchQuelonry.gelontUiLang();
    }

    if (lang == null && selonarchQuelonry.isSelontUselonrLangs()) {
      // fallback to thelon uselonr lang with thelon highelonst confidelonncelon
      doublelon maxConfidelonncelon = Doublelon.MIN_VALUelon;

      for (Map.elonntry<ThriftLanguagelon, Doublelon> elonntry : selonarchQuelonry.gelontUselonrLangs().elonntrySelont()) {
        if (elonntry.gelontValuelon() > maxConfidelonncelon) {
          lang = ThriftLanguagelonUtil.gelontLanguagelonCodelonOf(elonntry.gelontKelony());
          maxConfidelonncelon = elonntry.gelontValuelon();
        }
      }
    }

    if (lang == null) {
      lang = LocalelonUtil.UNDelonTelonRMINelonD_LANGUAGelon;
    }

    gelontCountelonr(lang).increlonmelonnt();

    relonturn selonrvicelon.apply(relonquelonstContelonxt);
  }
}
