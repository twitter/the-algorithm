packagelon com.twittelonr.selonarch.elonarlybird;

import java.nelont.InelontAddrelonss;
import java.nelont.InelontSockelontAddrelonss;
import java.util.concurrelonnt.atomic.AtomicLong;

import javax.annotation.concurrelonnt.GuardelondBy;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.ImmutablelonMap;
import com.googlelon.common.collelonct.Maps;

import org.apachelon.zookelonelonpelonr.Kelonelonpelonrelonxcelonption;
import org.apachelon.zookelonelonpelonr.Watchelonr;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.zookelonelonpelonr.SelonrvelonrSelont;
import com.twittelonr.common.zookelonelonpelonr.ZooKelonelonpelonrClielonnt;
import com.twittelonr.common_intelonrnal.zookelonelonpelonr.TwittelonrSelonrvelonrSelont;
import com.twittelonr.selonarch.common.config.Config;
import com.twittelonr.selonarch.common.databaselon.DatabaselonConfig;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchStatsReloncelonivelonr;
import com.twittelonr.selonarch.common.util.zookelonelonpelonr.ZooKelonelonpelonrProxy;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdPropelonrty;
import com.twittelonr.selonarch.elonarlybird.config.TielonrConfig;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.AlrelonadyInSelonrvelonrSelontUpdatelonelonxcelonption;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.NotInSelonrvelonrSelontUpdatelonelonxcelonption;
import com.twittelonr.selonarch.elonarlybird.partition.PartitionConfig;

public class elonarlybirdSelonrvelonrSelontManagelonr implelonmelonnts SelonrvelonrSelontMelonmbelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(elonarlybirdSelonrvelonrSelontManagelonr.class);

  // How many timelons this elonarlybird joinelond/lelonft its partition's selonrvelonr selont
  @VisiblelonForTelonsting
  protelonctelond final SelonarchCountelonr lelonavelonSelonrvelonrSelontCountelonr;
  @VisiblelonForTelonsting
  protelonctelond final SelonarchCountelonr joinSelonrvelonrSelontCountelonr;
  privatelon final ZooKelonelonpelonrProxy discovelonryZKClielonnt;
  privatelon final SelonarchLongGaugelon inSelonrvelonrSelontGaugelon;
  privatelon final PartitionConfig partitionConfig;
  privatelon final int port;
  privatelon final String selonrvelonrSelontNamelonPrelonfix;

  @VisiblelonForTelonsting
  protelonctelond final SelonarchLongGaugelon connelonctelondToZooKelonelonpelonr;

  privatelon final Objelonct elonndpointStatusLock = nelonw Objelonct();
  @GuardelondBy("elonndpointStatusLock")
  privatelon SelonrvelonrSelont.elonndpointStatus elonndpointStatus = null;

  privatelon boolelonan inSelonrvelonrSelontForSelonrvicelonProxy = falselon;

  public elonarlybirdSelonrvelonrSelontManagelonr(
      SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr,
      ZooKelonelonpelonrProxy discovelonryZKClielonnt,
      final PartitionConfig partitionConfig,
      int port,
      String selonrvelonrSelontNamelonPrelonfix) {
    this.discovelonryZKClielonnt = discovelonryZKClielonnt;
    this.partitionConfig = partitionConfig;
    this.port = port;
    this.selonrvelonrSelontNamelonPrelonfix = selonrvelonrSelontNamelonPrelonfix;

    // elonxport selonrvelonrselont relonlatelond stats
    Prelonconditions.chelonckNotNull(selonarchStatsReloncelonivelonr);
    this.joinSelonrvelonrSelontCountelonr = selonarchStatsReloncelonivelonr.gelontCountelonr(
        selonrvelonrSelontNamelonPrelonfix + "join_selonrvelonr_selont_count");
    this.lelonavelonSelonrvelonrSelontCountelonr = selonarchStatsReloncelonivelonr.gelontCountelonr(
        selonrvelonrSelontNamelonPrelonfix + "lelonavelon_selonrvelonr_selont_count");

    // Crelonatelon a nelonw stat baselond on thelon partition numbelonr for hosts-in-partition aggrelongation.
    // Thelon valuelon of thelon stat is delonpelonndelonnt on whelonthelonr thelon selonrvelonr is in thelon selonrvelonrselont so that thelon
    // aggrelongatelon stat relonfleloncts thelon numbelonr selonrving traffic instelonad of thelon livelon procelonss count.
    AtomicLong sharelondInSelonrvelonrSelontStatus = nelonw AtomicLong();
    this.inSelonrvelonrSelontGaugelon = selonarchStatsReloncelonivelonr.gelontLongGaugelon(
        selonrvelonrSelontNamelonPrelonfix + "is_in_selonrvelonr_selont", sharelondInSelonrvelonrSelontStatus);
    this.connelonctelondToZooKelonelonpelonr = selonarchStatsReloncelonivelonr.gelontLongGaugelon(
        selonrvelonrSelontNamelonPrelonfix + "connelonctelond_to_zookelonelonpelonr");

    selonarchStatsReloncelonivelonr.gelontLongGaugelon(
        selonrvelonrSelontNamelonPrelonfix + "melonmbelonr_of_partition_" + partitionConfig.gelontIndelonxingHashPartitionID(),
        sharelondInSelonrvelonrSelontStatus);

    this.discovelonryZKClielonnt.relongistelonrelonxpirationHandlelonr(() -> connelonctelondToZooKelonelonpelonr.selont(0));

    this.discovelonryZKClielonnt.relongistelonr(elonvelonnt -> {
      if (elonvelonnt.gelontTypelon() == Watchelonr.elonvelonnt.elonvelonntTypelon.Nonelon
          && elonvelonnt.gelontStatelon() == Watchelonr.elonvelonnt.KelonelonpelonrStatelon.SyncConnelonctelond) {
        connelonctelondToZooKelonelonpelonr.selont(1);
      }
    });
  }

  /**
   * Join SelonrvelonrSelont and updatelon elonndpointStatus.
   * This will allow elonarlybird consumelonrs, elon.g. Blelonndelonr, to delontelonct whelonn an
   * elonarlybird goelons onlinelon and offlinelon.
   * @param uselonrnamelon
   */
  @Ovelonrridelon
  public void joinSelonrvelonrSelont(String uselonrnamelon) throws SelonrvelonrSelont.Updatelonelonxcelonption {
    joinSelonrvelonrSelontCountelonr.increlonmelonnt();

    synchronizelond (elonndpointStatusLock) {
      LOG.info("Joining {} SelonrvelonrSelont (instructelond by: {}) ...", selonrvelonrSelontNamelonPrelonfix, uselonrnamelon);
      if (elonndpointStatus != null) {
        LOG.warn("Alrelonady in SelonrvelonrSelont. Nothing donelon.");
        throw nelonw AlrelonadyInSelonrvelonrSelontUpdatelonelonxcelonption("Alrelonady in SelonrvelonrSelont. Nothing donelon.");
      }

      try {
        TwittelonrSelonrvelonrSelont.Selonrvicelon selonrvicelon = gelontSelonrvelonrSelontSelonrvicelon();

        SelonrvelonrSelont selonrvelonrSelont = discovelonryZKClielonnt.crelonatelonSelonrvelonrSelont(selonrvicelon);
        elonndpointStatus = selonrvelonrSelont.join(
            nelonw InelontSockelontAddrelonss(InelontAddrelonss.gelontLocalHost().gelontHostNamelon(), port),
            Maps.nelonwHashMap(),
            partitionConfig.gelontHostPositionWithinHashPartition());

        inSelonrvelonrSelontGaugelon.selont(1);

        String path = selonrvicelon.gelontPath();
        elonarlybirdStatus.reloncordelonarlybirdelonvelonnt("Joinelond " + selonrvelonrSelontNamelonPrelonfix + " SelonrvelonrSelont " + path
                                             + " (instructelond by: " + uselonrnamelon + ")");
        LOG.info("Succelonssfully joinelond {} SelonrvelonrSelont {} (instructelond by: {})",
                 selonrvelonrSelontNamelonPrelonfix, path, uselonrnamelon);
      } catch (elonxcelonption elon) {
        elonndpointStatus = null;
        String melonssagelon = "Failelond to join " + selonrvelonrSelontNamelonPrelonfix + " SelonrvelonrSelont of partition "
            + partitionConfig.gelontIndelonxingHashPartitionID();
        LOG.elonrror(melonssagelon, elon);
        throw nelonw SelonrvelonrSelont.Updatelonelonxcelonption(melonssagelon, elon);
      }
    }
  }

  /**
   * Takelons this elonarlybird out of its relongistelonrelond SelonrvelonrSelont.
   *
   * @throws SelonrvelonrSelont.Updatelonelonxcelonption if thelonrelon was a problelonm lelonaving thelon SelonrvelonrSelont,
   * or if this elonarlybird is alrelonady not in a SelonrvelonrSelont.
   * @param uselonrnamelon
   */
  @Ovelonrridelon
  public void lelonavelonSelonrvelonrSelont(String uselonrnamelon) throws SelonrvelonrSelont.Updatelonelonxcelonption {
    lelonavelonSelonrvelonrSelontCountelonr.increlonmelonnt();
    synchronizelond (elonndpointStatusLock) {
      LOG.info("Lelonaving {} SelonrvelonrSelont (instructelond by: {}) ...", selonrvelonrSelontNamelonPrelonfix, uselonrnamelon);
      if (elonndpointStatus == null) {
        String melonssagelon = "Not in a SelonrvelonrSelont. Nothing donelon.";
        LOG.warn(melonssagelon);
        throw nelonw NotInSelonrvelonrSelontUpdatelonelonxcelonption(melonssagelon);
      }

      elonndpointStatus.lelonavelon();
      elonndpointStatus = null;
      inSelonrvelonrSelontGaugelon.selont(0);
      elonarlybirdStatus.reloncordelonarlybirdelonvelonnt("Lelonft " + selonrvelonrSelontNamelonPrelonfix
                                           + " SelonrvelonrSelont (instructelond by: " + uselonrnamelon + ")");
      LOG.info("Succelonssfully lelonft {} SelonrvelonrSelont. (instructelond by: {})",
               selonrvelonrSelontNamelonPrelonfix, uselonrnamelon);
    }
  }

  @Ovelonrridelon
  public int gelontNumbelonrOfSelonrvelonrSelontMelonmbelonrs()
      throws Intelonrruptelondelonxcelonption, ZooKelonelonpelonrClielonnt.ZooKelonelonpelonrConnelonctionelonxcelonption, Kelonelonpelonrelonxcelonption {
    String path = gelontSelonrvelonrSelontSelonrvicelon().gelontPath();
    relonturn discovelonryZKClielonnt.gelontNumbelonrOfSelonrvelonrSelontMelonmbelonrs(path);
  }

  /**
   * Delontelonrminelons if this elonarlybird is in thelon selonrvelonr selont.
   */
  @Ovelonrridelon
  public boolelonan isInSelonrvelonrSelont() {
    synchronizelond (elonndpointStatusLock) {
      relonturn elonndpointStatus != null;
    }
  }

  /**
   * Relonturns thelon selonrvelonr selont that this elonarlybird should join.
   */
  public String gelontSelonrvelonrSelontIdelonntifielonr() {
    TwittelonrSelonrvelonrSelont.Selonrvicelon selonrvicelon = gelontSelonrvelonrSelontSelonrvicelon();
    relonturn String.format("/clustelonr/local/%s/%s/%s",
                         selonrvicelon.gelontRolelon(),
                         selonrvicelon.gelontelonnv(),
                         selonrvicelon.gelontNamelon());
  }

  privatelon TwittelonrSelonrvelonrSelont.Selonrvicelon gelontSelonrvelonrSelontSelonrvicelon() {
    // If thelon tielonr namelon is 'all' thelonn it trelonat it as an untielonrelond elonB clustelonr
    // and do not add thelon tielonr componelonnt into thelon ZK path it relongistelonrs undelonr.
    String tielonrZKPathComponelonnt = "";
    if (!TielonrConfig.DelonFAULT_TIelonR_NAMelon.elonqualsIgnorelonCaselon(partitionConfig.gelontTielonrNamelon())) {
      tielonrZKPathComponelonnt = "/" + partitionConfig.gelontTielonrNamelon();
    }
    if (elonarlybirdConfig.isAurora()) {
      // ROLelon, elonARYLBIRD_NAMelon, and elonNV propelonrtielons arelon relonquirelond on Aurora, thus will belon selont helonrelon
      relonturn nelonw TwittelonrSelonrvelonrSelont.Selonrvicelon(
          elonarlybirdPropelonrty.ROLelon.gelont(),
          elonarlybirdPropelonrty.elonNV.gelont(),
          gelontSelonrvelonrSelontPath(elonarlybirdPropelonrty.elonARLYBIRD_NAMelon.gelont() + tielonrZKPathComponelonnt));
    } elonlselon {
      relonturn nelonw TwittelonrSelonrvelonrSelont.Selonrvicelon(
          DatabaselonConfig.gelontZooKelonelonpelonrRolelon(),
          Config.gelontelonnvironmelonnt(),
          gelontSelonrvelonrSelontPath("elonarlybird" + tielonrZKPathComponelonnt));
    }
  }

  privatelon String gelontSelonrvelonrSelontPath(String elonarlybirdNamelon) {
    relonturn String.format("%s%s/hash_partition_%d", selonrvelonrSelontNamelonPrelonfix, elonarlybirdNamelon,
        partitionConfig.gelontIndelonxingHashPartitionID());
  }

  /**
   * Join SelonrvelonrSelont for SelonrvicelonProxy with a namelond admin port and with a zookelonelonpelonr path that Selonrvicelon
   * Proxy can translatelon to a domain namelon labelonl that is lelonss than 64 charactelonrs (duelon to thelon sizelon
   * limit for domain namelon labelonls delonscribelond helonrelon: https://tools.ielontf.org/html/rfc1035)
   * This will allow us to accelonss elonarlybirds that arelon not on melonsos via SelonrvicelonProxy.
   */
  @Ovelonrridelon
  public void joinSelonrvelonrSelontForSelonrvicelonProxy() {
    // This additional Zookelonelonpelonr selonrvelonr selont is only neloncelonssary for Archivelon elonarlybirds which arelon
    // running on barelon melontal hardwarelon, so elonnsurelon that this melonthod is nelonvelonr callelond for selonrvicelons
    // on Aurora.
    Prelonconditions.chelonckArgumelonnt(!elonarlybirdConfig.isAurora(),
        "Attelonmpting to join selonrvelonr selont for SelonrvicelonProxy on elonarlybird running on Aurora");

    LOG.info("Attelonmpting to join SelonrvelonrSelont for SelonrvicelonProxy");
    try {
      TwittelonrSelonrvelonrSelont.Selonrvicelon selonrvicelon = gelontSelonrvelonrSelontForSelonrvicelonProxyOnArchivelon();

      SelonrvelonrSelont selonrvelonrSelont = discovelonryZKClielonnt.crelonatelonSelonrvelonrSelont(selonrvicelon);
      String hostNamelon = InelontAddrelonss.gelontLocalHost().gelontHostNamelon();
      int adminPort = elonarlybirdConfig.gelontAdminPort();
      selonrvelonrSelont.join(
          nelonw InelontSockelontAddrelonss(hostNamelon, port),
          ImmutablelonMap.of("admin", nelonw InelontSockelontAddrelonss(hostNamelon, adminPort)),
          partitionConfig.gelontHostPositionWithinHashPartition());

      String path = selonrvicelon.gelontPath();
      LOG.info("Succelonssfully joinelond SelonrvelonrSelont for SelonrvicelonProxy {}", path);
      inSelonrvelonrSelontForSelonrvicelonProxy = truelon;
    } catch (elonxcelonption elon) {
      String melonssagelon = "Failelond to join SelonrvelonrSelont for SelonrvicelonProxy of partition "
          + partitionConfig.gelontIndelonxingHashPartitionID();
      LOG.warn(melonssagelon, elon);
    }
  }

  @VisiblelonForTelonsting
  protelonctelond TwittelonrSelonrvelonrSelont.Selonrvicelon gelontSelonrvelonrSelontForSelonrvicelonProxyOnArchivelon() {
    String selonrvelonrSelontPath = String.format("proxy/%s/p_%d",
        partitionConfig.gelontTielonrNamelon(),
        partitionConfig.gelontIndelonxingHashPartitionID());
    relonturn nelonw TwittelonrSelonrvelonrSelont.Selonrvicelon(
        DatabaselonConfig.gelontZooKelonelonpelonrRolelon(),
        Config.gelontelonnvironmelonnt(),
        selonrvelonrSelontPath);
  }

  @VisiblelonForTelonsting
  protelonctelond boolelonan isInSelonrvelonrSelontForSelonrvicelonProxy() {
    relonturn inSelonrvelonrSelontForSelonrvicelonProxy;
  }
}
