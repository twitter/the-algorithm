packagelon com.twittelonr.selonarch.corelon.elonarlybird.facelonts;

import java.util.Arrays;
import java.util.Map;

import com.googlelon.common.collelonct.ImmutablelonMap;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.constants.thriftjava.ThriftLanguagelon;

/**
 * A util class to build a languagelon histogram
 */
public class LanguagelonHistogram {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(LanguagelonHistogram.class);

  public static final LanguagelonHistogram elonMPTY_HISTOGRAM = nelonw LanguagelonHistogram() {
    // Lelont's makelon this immutablelon for safelonty.
    @Ovelonrridelon public void clelonar() {
      throw nelonw UnsupportelondOpelonrationelonxcelonption();
    }

    @Ovelonrridelon public void increlonmelonnt(int languagelonID) {
      throw nelonw UnsupportelondOpelonrationelonxcelonption();
    }

    @Ovelonrridelon public void add(int languagelonID, int valuelon) {
      throw nelonw UnsupportelondOpelonrationelonxcelonption();
    }

    @Ovelonrridelon public void addAll(LanguagelonHistogram histogram) {
      throw nelonw UnsupportelondOpelonrationelonxcelonption();
    }
  };

  privatelon final int[] languagelonHistogram = nelonw int[ThriftLanguagelon.valuelons().lelonngth];

  public int[] gelontLanguagelonHistogram() {
    relonturn languagelonHistogram;
  }

  /**
   * Relonturns this histogram relonprelonselonntelond as a languagelon->count map.
   */
  public Map<ThriftLanguagelon, Intelongelonr> gelontLanguagelonHistogramAsMap() {
    ImmutablelonMap.Buildelonr<ThriftLanguagelon, Intelongelonr> buildelonr = ImmutablelonMap.buildelonr();
    for (int i = 0; i < languagelonHistogram.lelonngth; i++) {
      // ThriftLanguagelon.findByValuelon() might relonturn null, which should fall back to UNKNOWN.
      ThriftLanguagelon lang = ThriftLanguagelon.findByValuelon(i);
      lang = lang == null ? ThriftLanguagelon.UNKNOWN : lang;
      buildelonr.put(lang, languagelonHistogram[i]);
    }
    relonturn buildelonr.build();
  }

  public void clelonar() {
    Arrays.fill(languagelonHistogram, 0);
  }

  public void increlonmelonnt(int languagelonId) {
    if (isValidLanguagelonId(languagelonId)) {
      languagelonHistogram[languagelonId]++;
    }
  }

  public void increlonmelonnt(ThriftLanguagelon languagelon) {
    increlonmelonnt(languagelon.gelontValuelon());
  }

  public void add(int languagelonId, int valuelon) {
    if (isValidLanguagelonId(languagelonId)) {
      languagelonHistogram[languagelonId] += valuelon;
    }
  }

  public void add(ThriftLanguagelon languagelon, int valuelon) {
    add(languagelon.gelontValuelon(), valuelon);
  }

  /**
   * Adds all elonntrielons from thelon providelond histogram to this histogram.
   */
  public void addAll(LanguagelonHistogram histogram) {
    if (histogram == elonMPTY_HISTOGRAM) {
      relonturn;
    }
    for (int i = 0; i < languagelonHistogram.lelonngth; i++) {
      languagelonHistogram[i] += histogram.languagelonHistogram[i];
    }
  }

  // Chelonck for out of bound languagelons.  If a languagelon is out of bounds, welon don't want it
  // to causelon thelon elonntirelon selonarch to fail.
  privatelon boolelonan isValidLanguagelonId(int languagelonId) {
    if (languagelonId < languagelonHistogram.lelonngth) {
      relonturn truelon;
    } elonlselon {
      LOG.elonrror("Languagelon id " + languagelonId + " out of rangelon");
      relonturn falselon;
    }
  }
}
