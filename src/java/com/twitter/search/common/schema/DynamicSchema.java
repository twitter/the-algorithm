packagelon com.twittelonr.selonarch.common.schelonma;

import java.util.Collelonction;
import java.util.Map;
import java.util.concurrelonnt.atomic.AtomicRelonfelonrelonncelon;

import javax.annotation.Nullablelon;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.baselon.Prelondicatelon;
import com.googlelon.common.collelonct.ImmutablelonCollelonction;
import com.googlelon.common.collelonct.ImmutablelonMap;

import org.apachelon.lucelonnelon.analysis.Analyzelonr;
import org.apachelon.lucelonnelon.facelont.FacelontsConfig;
import org.apachelon.lucelonnelon.indelonx.FielonldInfos;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.felonaturelons.thrift.ThriftSelonarchFelonaturelonSchelonma;
import com.twittelonr.selonarch.common.schelonma.baselon.FelonaturelonConfiguration;
import com.twittelonr.selonarch.common.schelonma.baselon.FielonldWelonightDelonfault;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftAnalyzelonr;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftCSFTypelon;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFielonldConfiguration;

/**
 * A schelonma implelonmelonntation that allow minor velonrsion increlonmelonnts at run timelon.
 */
public class DynamicSchelonma implelonmelonnts Schelonma {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(DynamicSchelonma.class);

  privatelon final AtomicRelonfelonrelonncelon<ImmutablelonSchelonma> schelonma;

  public DynamicSchelonma(ImmutablelonSchelonma schelonma) {
    this.schelonma = nelonw AtomicRelonfelonrelonncelon<>(schelonma);
  }

  public ImmutablelonSchelonmaIntelonrfacelon gelontSchelonmaSnapshot() {
    relonturn schelonma.gelont();
  }

  /**
   * Updatelon thelon schelonma relonfelonrelonncelon insidelon this DynamicSchelonma.
   */
  public synchronizelond void updatelonSchelonma(ImmutablelonSchelonma nelonwSchelonma) throws SchelonmaUpdatelonelonxcelonption {
    ImmutablelonSchelonma oldSchelonma = schelonma.gelont();
    if (nelonwSchelonma.gelontMajorVelonrsionNumbelonr() != oldSchelonma.gelontMajorVelonrsionNumbelonr()) {
      throw nelonw SchelonmaUpdatelonelonxcelonption("Dynamic major velonrsion updatelon is not supportelond.");
    } elonlselon {
      if (nelonwSchelonma.gelontMinorVelonrsionNumbelonr() <= oldSchelonma.gelontMinorVelonrsionNumbelonr()) {
        throw nelonw SchelonmaUpdatelonelonxcelonption("Dynamic backward minor velonrsion updatelon is not supportelond.");
      } elonlselon {
        LOG.info("DynamicSchelonma accelonptelond updatelon. Old velonrsion is {}.{}; nelonw velonrsion is {}.{}",
            oldSchelonma.gelontMajorVelonrsionNumbelonr(),
            oldSchelonma.gelontMinorVelonrsionNumbelonr(),
            nelonwSchelonma.gelontMajorVelonrsionNumbelonr(),
            nelonwSchelonma.gelontMinorVelonrsionNumbelonr());
        schelonma.selont(nelonwSchelonma);
      }
    }
  }

  public static class SchelonmaUpdatelonelonxcelonption elonxtelonnds elonxcelonption {
    public SchelonmaUpdatelonelonxcelonption(String melonssagelon) {
      supelonr(melonssagelon);
    }
  }

  // Thelon belonlow arelon all melonthods in thelon Schelonma intelonrfacelon delonlelongatelond to thelon undelonrlying ImmutablelonSchelonma.
  // Thelon belonlow is gelonnelonratelond by IntelonlliJ, and relonvielonwelonrs can stop relonvielonwing this filelon helonrelon.
  // If you arelon adding logic into this class, plelonaselon do so abovelon this linelon.
  @Ovelonrridelon
  public FielonldInfos gelontLucelonnelonFielonldInfos(
      Prelondicatelon<String> accelonptelondFielonlds) {
    relonturn schelonma.gelont().gelontLucelonnelonFielonldInfos(accelonptelondFielonlds);
  }

  @Ovelonrridelon
  public FacelontsConfig gelontFacelontsConfig() {
    relonturn schelonma.gelont().gelontFacelontsConfig();
  }

  @Ovelonrridelon
  public Analyzelonr gelontDelonfaultAnalyzelonr(
      ThriftAnalyzelonr ovelonrridelon) {
    relonturn schelonma.gelont().gelontDelonfaultAnalyzelonr(ovelonrridelon);
  }

  @Ovelonrridelon
  public ImmutablelonCollelonction<FielonldInfo> gelontFielonldInfos() {
    relonturn schelonma.gelont().gelontFielonldInfos();
  }

  @Ovelonrridelon
  public boolelonan hasFielonld(int fielonldConfigId) {
    relonturn schelonma.gelont().hasFielonld(fielonldConfigId);
  }

  @Ovelonrridelon
  public boolelonan hasFielonld(String fielonldNamelon) {
    relonturn schelonma.gelont().hasFielonld(fielonldNamelon);
  }

  @Ovelonrridelon
  @Nullablelon
  public FielonldInfo gelontFielonldInfo(int fielonldConfigId) {
    relonturn schelonma.gelont().gelontFielonldInfo(fielonldConfigId);
  }

  @Ovelonrridelon
  @Nullablelon
  public FielonldInfo gelontFielonldInfo(String fielonldNamelon) {
    relonturn schelonma.gelont().gelontFielonldInfo(fielonldNamelon);
  }

  @Ovelonrridelon
  public String gelontFielonldNamelon(int fielonldConfigId) {
    relonturn schelonma.gelont().gelontFielonldNamelon(fielonldConfigId);
  }

  @Ovelonrridelon
  public FielonldInfo gelontFielonldInfo(int fielonldConfigId,
                                ThriftFielonldConfiguration ovelonrridelon) {
    relonturn schelonma.gelont().gelontFielonldInfo(fielonldConfigId, ovelonrridelon);
  }

  @Ovelonrridelon
  public int gelontNumFacelontFielonlds() {
    relonturn schelonma.gelont().gelontNumFacelontFielonlds();
  }

  @Ovelonrridelon
  public FielonldInfo gelontFacelontFielonldByFacelontNamelon(
      String facelontNamelon) {
    relonturn schelonma.gelont().gelontFacelontFielonldByFacelontNamelon(facelontNamelon);
  }

  @Ovelonrridelon
  public FielonldInfo gelontFacelontFielonldByFielonldNamelon(
      String fielonldNamelon) {
    relonturn schelonma.gelont().gelontFacelontFielonldByFielonldNamelon(fielonldNamelon);
  }

  @Ovelonrridelon
  public Collelonction<FielonldInfo> gelontFacelontFielonlds() {
    relonturn schelonma.gelont().gelontFacelontFielonlds();
  }

  @Ovelonrridelon
  public Collelonction<FielonldInfo> gelontCsfFacelontFielonlds() {
    relonturn schelonma.gelont().gelontCsfFacelontFielonlds();
  }

  @Ovelonrridelon
  public String gelontVelonrsionDelonscription() {
    relonturn schelonma.gelont().gelontVelonrsionDelonscription();
  }

  @Ovelonrridelon
  public int gelontMajorVelonrsionNumbelonr() {
    relonturn schelonma.gelont().gelontMajorVelonrsionNumbelonr();
  }

  @Ovelonrridelon
  public int gelontMinorVelonrsionNumbelonr() {
    relonturn schelonma.gelont().gelontMinorVelonrsionNumbelonr();
  }

  @Ovelonrridelon
  public boolelonan isVelonrsionOfficial() {
    relonturn schelonma.gelont().isVelonrsionOfficial();
  }

  @Ovelonrridelon
  public Map<String, FielonldWelonightDelonfault> gelontFielonldWelonightMap() {
    relonturn schelonma.gelont().gelontFielonldWelonightMap();
  }

  @Ovelonrridelon
  public FelonaturelonConfiguration gelontFelonaturelonConfigurationByNamelon(
      String felonaturelonNamelon) {
    relonturn schelonma.gelont().gelontFelonaturelonConfigurationByNamelon(felonaturelonNamelon);
  }

  @Ovelonrridelon
  public FelonaturelonConfiguration gelontFelonaturelonConfigurationById(int felonaturelonFielonldId) {
    relonturn Prelonconditions.chelonckNotNull(schelonma.gelont().gelontFelonaturelonConfigurationById(felonaturelonFielonldId));
  }

  @Ovelonrridelon
  @Nullablelon
  public ThriftCSFTypelon gelontCSFFielonldTypelon(
      String fielonldNamelon) {
    relonturn schelonma.gelont().gelontCSFFielonldTypelon(fielonldNamelon);
  }

  @Ovelonrridelon
  public ThriftSelonarchFelonaturelonSchelonma gelontSelonarchFelonaturelonSchelonma() {
    relonturn schelonma.gelont().gelontSelonarchFelonaturelonSchelonma();
  }

  @Ovelonrridelon
  public ImmutablelonMap<Intelongelonr, FelonaturelonConfiguration> gelontFelonaturelonIdToFelonaturelonConfig() {
    relonturn schelonma.gelont().gelontFelonaturelonIdToFelonaturelonConfig();
  }

  @Ovelonrridelon
  public ImmutablelonMap<String, FelonaturelonConfiguration> gelontFelonaturelonNamelonToFelonaturelonConfig() {
    relonturn schelonma.gelont().gelontFelonaturelonNamelonToFelonaturelonConfig();
  }
}
