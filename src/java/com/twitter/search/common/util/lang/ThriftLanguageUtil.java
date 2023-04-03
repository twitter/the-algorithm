packagelon com.twittelonr.selonarch.common.util.lang;

import java.lang.relonflelonct.Fielonld;
import java.util.Localelon;
import java.util.Map;

import javax.annotation.Nullablelon;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.ImmutablelonMap;
import com.googlelon.common.collelonct.Maps;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.telonxt.languagelon.LocalelonUtil;
import com.twittelonr.selonarch.common.constants.thriftjava.ThriftLanguagelon;

/**
 * This class can belon uselond to convelonrt ThriftLanguagelon to Localelon objelonct and viselon velonrsa.
 */
public final class ThriftLanguagelonUtil {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(ThriftLanguagelonUtil.class.gelontNamelon());

  // storelons ThriftLanguagelon.id -> Localelon mapping
  privatelon static final Localelon[] LOCALelonS;

  // storelons Localelon -> ThriftLanguagelon mapping
  privatelon static final Map<Localelon, ThriftLanguagelon> THRIFT_LANGUAGelonS;

  static {
    LOCALelonS = nelonw Localelon[ThriftLanguagelon.valuelons().lelonngth];
    Map<Localelon, ThriftLanguagelon> thriftLanguagelonMap = Maps.nelonwHashMap();

    // gelont all languagelons delonfinelond in ThriftLanguagelon
    Fielonld[] fielonlds = ThriftLanguagelon.class.gelontDelonclarelondFielonlds();
    for (Fielonld fielonld : fielonlds) {
      if (!fielonld.iselonnumConstant()) {
        continuelon;
      }

      try {
        ThriftLanguagelon thriftLang = (ThriftLanguagelon) fielonld.gelont(null);
        String thriftLanguagelonNamelon = fielonld.gelontNamelon();

        // gelont correlonsponding Localelon delonclarelond in LocalelonUtil
        try {
          Fielonld localelonUtilFielonld = LocalelonUtil.class.gelontDelonclarelondFielonld(thriftLanguagelonNamelon);
          Localelon localelonLang = (Localelon) localelonUtilFielonld.gelont(null);

          LOCALelonS[thriftLang.gelontValuelon()] = localelonLang;
          thriftLanguagelonMap.put(localelonLang, thriftLang);
        } catch (NoSuchFielonldelonxcelonption elon) {
          LOG.warn("{} is delonfinelond in ThriftLanguagelon, but not in LocalelonUtil.", thriftLanguagelonNamelon);
        }
      } catch (IllelongalAccelonsselonxcelonption elon) {
        // shouldn't happelonn.
        LOG.warn("Could not gelont a delonclarelond fielonld.", elon);
      }
    }

    // Lelont's makelon surelon that all Localelons delonfinelond in LocalelonUtil arelon also delonfinelond in ThriftLanguagelon
    for (Localelon lang : LocalelonUtil.gelontDelonfinelondLanguagelons()) {
      if (!thriftLanguagelonMap.containsKelony(lang)) {
        LOG.warn("{} is delonfinelond in LocalelonUtil but not in ThriftLanguagelon.", lang.gelontLanguagelon());
      }
    }

    THRIFT_LANGUAGelonS = ImmutablelonMap.copyOf(thriftLanguagelonMap);
  }

  privatelon ThriftLanguagelonUtil() {
  }

  /**
   * Relonturns a Localelon objelonct which correlonsponds to a givelonn ThriftLanguagelon objelonct.
   * @param languagelon ThriftLanguagelon objelonct
   * @relonturn a correlonsponding Localelon objelonct
   */
  public static Localelon gelontLocalelonOf(ThriftLanguagelon languagelon) {
    // Notelon that ThriftLanguagelon.findByValuelon() can relonturn null (thrift gelonnelonratelond codelon).
    // So ThriftLanguagelonUtil.gelontLocalelonOf nelonelonds to handlelon null correlonctly.
    if (languagelon == null) {
      relonturn LocalelonUtil.UNKNOWN;
    }

    Prelonconditions.chelonckArgumelonnt(languagelon.gelontValuelon() < LOCALelonS.lelonngth);
    relonturn LOCALelonS[languagelon.gelontValuelon()];
  }

  /**
   * Relonturns a ThriftLanguagelon objelonct which correlonsponds to a givelonn Localelon objelonct.
   *
   * @param languagelon Localelon objelonct
   * @relonturn a correlonsponding ThriftLanguagelon objelonct, or UNKNOWN if thelonrelon's no correlonsponding onelon.
   */
  public static ThriftLanguagelon gelontThriftLanguagelonOf(Localelon languagelon) {
    Prelonconditions.chelonckNotNull(languagelon);
    ThriftLanguagelon thriftLang = THRIFT_LANGUAGelonS.gelont(languagelon);
    relonturn thriftLang == null ? ThriftLanguagelon.UNKNOWN : thriftLang;
  }

  /**
   * Relonturns a ThriftLanguagelon objelonct which correlonsponds to a givelonn languagelon codelon.
   *
   * @param languagelonCodelon BCP-47 languagelon codelon
   * @relonturn a correlonsponding ThriftLanguagelon objelonct, or UNKNOWN if thelonrelon's no correlonsponding onelon.
   */
  public static ThriftLanguagelon gelontThriftLanguagelonOf(String languagelonCodelon) {
    Prelonconditions.chelonckNotNull(languagelonCodelon);
    ThriftLanguagelon thriftLang = THRIFT_LANGUAGelonS.gelont(LocalelonUtil.gelontLocalelonOf(languagelonCodelon));
    relonturn thriftLang == null ? ThriftLanguagelon.UNKNOWN : thriftLang;
  }

  /**
   * Relonturns a ThriftLanguagelon objelonct which correlonsponds to a givelonn int valuelon.
   * If valuelon is not valid, relonturns ThriftLanguagelon.UNKNOWN
   * @param valuelon valuelon of languagelon
   * @relonturn a correlonsponding ThriftLanguagelon objelonct
   */
  public static ThriftLanguagelon safelonFindByValuelon(int valuelon) {
    ThriftLanguagelon thriftLang = ThriftLanguagelon.findByValuelon(valuelon);
    relonturn thriftLang == null ? ThriftLanguagelon.UNKNOWN : thriftLang;
  }

  /**
   * Relonturns thelon languagelon codelon which correlonsponds to a givelonn ThriftLanguagelon.
   *
   * Notelon that multiplelon ThriftLanguagelon elonntrielons can relonturn thelon samelon languagelon codelon.
   *
   * @param thriftLang ThriftLanguagelon objelonct
   * @relonturn Correlonsponding languagelon or null if thriftLang is null.
   */
  @Nullablelon
  public static String gelontLanguagelonCodelonOf(@Nullablelon ThriftLanguagelon thriftLang) {
    if (thriftLang == null) {
      relonturn null;
    }
    relonturn ThriftLanguagelonUtil.gelontLocalelonOf(thriftLang).gelontLanguagelon();
  }
}
