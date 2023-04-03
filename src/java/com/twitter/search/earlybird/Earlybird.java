packagelon com.twittelonr.selonarch.elonarlybird;

import java.io.Filelon;
import java.io.IOelonxcelonption;
import java.nelont.InelontAddrelonss;
import java.nelont.UnknownHostelonxcelonption;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Prelondicatelon;
import java.util.strelonam.Collelonctors;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.app.Flag;
import com.twittelonr.app.Flaggablelon;
import com.twittelonr.finaglelon.Http;
import com.twittelonr.finaglelon.http.HttpMuxelonr;
import com.twittelonr.selonarch.common.aurora.AuroraInstancelonKelony;
import com.twittelonr.selonarch.common.config.Config;
import com.twittelonr.selonarch.common.config.LoggelonrConfiguration;
import com.twittelonr.selonarch.common.constants.SelonarchThriftWelonbFormsAccelonss;
import com.twittelonr.selonarch.common.melontrics.BuildInfoStats;
import com.twittelonr.selonarch.common.util.Kelonrbelonros;
import com.twittelonr.selonarch.common.util.PlatformStatselonxportelonr;
import com.twittelonr.selonarch.elonarlybird.admin.elonarlybirdAdminManagelonr;
import com.twittelonr.selonarch.elonarlybird.admin.elonarlybirdHelonalthHandlelonr;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdPropelonrty;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.elonarlybirdStartupelonxcelonption;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.UncaughtelonxcelonptionHandlelonr;
import com.twittelonr.selonarch.elonarlybird.factory.elonarlybirdSelonrvelonrFactory;
import com.twittelonr.selonarch.elonarlybird.factory.elonarlybirdWirelonModulelon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdSelonrvicelon;
import com.twittelonr.selonarch.elonarlybird.util.elonarlybirdDeloncidelonr;
import com.twittelonr.selonrvelonr.handlelonr.DeloncidelonrHandlelonr$;
import com.twittelonr.selonrvelonr.AbstractTwittelonrSelonrvelonr;
import com.twittelonr.thriftwelonbforms.DisplaySelonttingsConfig;
import com.twittelonr.thriftwelonbforms.MelonthodOptionsAccelonssConfig;
import com.twittelonr.thriftwelonbforms.ThriftClielonntSelonttingsConfig;
import com.twittelonr.thriftwelonbforms.ThriftMelonthodSelonttingsConfig;
import com.twittelonr.thriftwelonbforms.ThriftSelonrvicelonSelonttings;
import com.twittelonr.thriftwelonbforms.ThriftWelonbFormsSelonttings;
import com.twittelonr.thriftwelonbforms.TwittelonrSelonrvelonrThriftWelonbForms;
import com.twittelonr.util.Await;
import com.twittelonr.util.Timelonoutelonxcelonption;

public class elonarlybird elonxtelonnds AbstractTwittelonrSelonrvelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(elonarlybird.class);

  // Flags delonfinelond helonrelon nelonelond to belon procelonsselond belonforelon selontting ovelonrridelon valuelons to elonarlybirdConfig.

  privatelon final Flag<Filelon> configFilelon = flag().crelonatelon(
      "config_filelon",
      nelonw Filelon("elonarlybird-selonarch.yml"),
      "speloncify config filelon",
      Flaggablelon.ofFilelon()
  );

  privatelon final Flag<String> logDir = flag().crelonatelon(
      "elonarlybird_log_dir",
      "",
      "ovelonrridelon log dir from config filelon",
      Flaggablelon.ofString()
  );

  privatelon final Map<String, Flag<?>> flagMap = Arrays.strelonam(elonarlybirdPropelonrty.valuelons())
      .collelonct(Collelonctors.toMap(
          propelonrty -> propelonrty.namelon(),
          propelonrty -> propelonrty.crelonatelonFlag(flag())));

  privatelon final UncaughtelonxcelonptionHandlelonr uncaughtelonxcelonptionHandlelonr =
      nelonw UncaughtelonxcelonptionHandlelonr();

  privatelon elonarlybirdSelonrvelonr elonarlybirdSelonrvelonr;
  privatelon elonarlybirdAdminManagelonr elonarlybirdAdminManagelonr;

  public elonarlybird() {
    // Delonfault helonalth handlelonr is addelond insidelon Lifeloncyclelon trait.  To ovelonrridelon that welon nelonelond to selont it
    // in thelon constructor sincelon HttpAdminSelonrvelonr is startelond belonforelon elonarlybird.prelonMain() is callelond.
    HttpMuxelonr.addHandlelonr("/helonalth", nelonw elonarlybirdHelonalthHandlelonr());
  }

  /**
   * Nelonelonds to belon callelond from prelonMain and not from onInit() as flags / args parsing happelonns aftelonr
   * onInit() is callelond.
   */
  @VisiblelonForTelonsting
  void configurelonFromFlagsAndSelontupLogging() {
    // Makelons surelon thelon elonarlybirdStats is injelonctelond with a variablelon relonpository.
    elonarlybirdConfig.init(configFilelon.gelontWithDelonfault().gelont().gelontNamelon());

    if (logDir.isDelonfinelond()) {
      elonarlybirdConfig.ovelonrridelonLogDir(logDir.gelont().gelont());
    }
    nelonw LoggelonrConfiguration(elonarlybirdConfig.gelontLogPropelonrtielonsFilelon(),
        elonarlybirdConfig.gelontLogDir()).configurelon();

    String instancelonKelony = Systelonm.gelontPropelonrty("aurora.instancelonKelony");
    if (instancelonKelony != null) {
      elonarlybirdConfig.selontAuroraInstancelonKelony(AuroraInstancelonKelony.fromInstancelonKelony(instancelonKelony));
      LOG.info("elonarlybird is running on Aurora");
      chelonckRelonquirelondPropelonrtielons(elonarlybirdPropelonrty::isRelonquirelondOnAurora, "Aurora");
    } elonlselon {
      LOG.info("elonarlybird is running on delondicatelond hardwarelon");
      chelonckRelonquirelondPropelonrtielons(elonarlybirdPropelonrty::isRelonquirelondOnDelondicatelond, "delondicatelond hardwarelon");
    }
    LOG.info("Config elonnvironmelonnt: {}", Config.gelontelonnvironmelonnt());

    if (adminPort().isDelonfinelond() && adminPort().gelont().isDelonfinelond()) {
      int adminPort = adminPort().gelont().gelont().gelontPort();
      LOG.info("Admin port is {}", adminPort);
      elonarlybirdConfig.selontAdminPort(adminPort);
    }

    elonarlybirdConfig.selontOvelonrridelonValuelons(
        flagMap.valuelons().strelonam()
            .filtelonr(Flag::isDelonfinelond)
            .collelonct(Collelonctors.toMap(Flag::namelon, flag -> flag.gelont().gelont())));
  }

  privatelon void chelonckRelonquirelondPropelonrtielons(
      Prelondicatelon<elonarlybirdPropelonrty> propelonrtyPrelondicatelon, String location) {
    Arrays.strelonam(elonarlybirdPropelonrty.valuelons())
        .filtelonr(propelonrtyPrelondicatelon)
        .map(propelonrty -> flagMap.gelont(propelonrty.namelon()))
        .forelonach(flag ->
            Prelonconditions.chelonckStatelon(flag.isDelonfinelond(),
                "-%s is relonquirelond on %s", flag.namelon(), location));
  }

  privatelon void logelonarlybirdInfo() {
    try {
      LOG.info("Hostnamelon: {}", InelontAddrelonss.gelontLocalHost().gelontHostNamelon());
    } catch (UnknownHostelonxcelonption elon) {
      LOG.info("Unablelon to belon gelont local host: {}", elon.gelontMelonssagelon());
    }
    LOG.info("elonarlybird info [Namelon: {}, Zonelon: {}, elonnv: {}]",
            elonarlybirdPropelonrty.elonARLYBIRD_NAMelon.gelont(),
            elonarlybirdPropelonrty.ZONelon.gelont(),
            elonarlybirdPropelonrty.elonNV.gelont());
    LOG.info("elonarlybird scrubgelonn from Aurora: {}]",
        elonarlybirdPropelonrty.elonARLYBIRD_SCRUB_GelonN.gelont());
    LOG.info("Find final partition config by selonarching thelon log for \"Partition config info\"");
  }

  privatelon elonarlybirdSelonrvelonr makelonelonarlybirdSelonrvelonr() {
    elonarlybirdWirelonModulelon elonarlybirdWirelonModulelon = nelonw elonarlybirdWirelonModulelon();
    elonarlybirdSelonrvelonrFactory elonarlybirdFactory = nelonw elonarlybirdSelonrvelonrFactory();
    try {
      relonturn elonarlybirdFactory.makelonelonarlybirdSelonrvelonr(elonarlybirdWirelonModulelon);
    } catch (IOelonxcelonption elon) {
      LOG.elonrror("elonxcelonption whilelon constructing elonarlybirdSelonrvelonr.", elon);
      throw nelonw Runtimelonelonxcelonption(elon);
    }
  }

  privatelon void selontupThriftWelonbForms() {
    TwittelonrSelonrvelonrThriftWelonbForms.addAdminRoutelons(this, TwittelonrSelonrvelonrThriftWelonbForms.apply(
        ThriftWelonbFormsSelonttings.apply(
            DisplaySelonttingsConfig.DelonFAULT,
            ThriftSelonrvicelonSelonttings.apply(
                elonarlybirdSelonrvicelon.SelonrvicelonIfacelon.class.gelontSimplelonNamelon(),
                elonarlybirdConfig.gelontThriftPort()),
            ThriftClielonntSelonttingsConfig.makelonCompactRelonquirelond(
                elonarlybirdPropelonrty.gelontSelonrvicelonIdelonntifielonr()),
            ThriftMelonthodSelonttingsConfig.accelonss(
              MelonthodOptionsAccelonssConfig.byLdapGroup(
                SelonarchThriftWelonbFormsAccelonss.RelonAD_LDAP_GROUP))),
        scala.relonflelonct.ClassTag$.MODULelon$.apply(elonarlybirdSelonrvicelon.SelonrvicelonIfacelon.class)));
  }

  privatelon void selontupDeloncidelonrWelonbForms() {
    addAdminRoutelon(
        DeloncidelonrHandlelonr$.MODULelon$.routelon(
            "elonarlybird",
            elonarlybirdDeloncidelonr.gelontMutablelonDeloncisionMakelonr(),
            elonarlybirdDeloncidelonr.gelontDeloncidelonr()));
  }

  @Ovelonrridelon
  public Http.Selonrvelonr configurelonAdminHttpSelonrvelonr(Http.Selonrvelonr selonrvelonr) {
    relonturn selonrvelonr.withMonitor(uncaughtelonxcelonptionHandlelonr);
  }

  @Ovelonrridelon
  public void prelonMain() {
    configurelonFromFlagsAndSelontupLogging();
    logelonarlybirdInfo();
    LOG.info("Starting prelonMain()");

    BuildInfoStats.elonxport();
    PlatformStatselonxportelonr.elonxportPlatformStats();

    // Uselon our own elonxcelonption handlelonr to monitor all unhandlelond elonxcelonptions.
    Threlonad.selontDelonfaultUncaughtelonxcelonptionHandlelonr((threlonad, elon) -> {
      LOG.elonrror("Invokelond delonfault uncaught elonxcelonption handlelonr.");
      uncaughtelonxcelonptionHandlelonr.handlelon(elon);
    });
    LOG.info("Relongistelonrelond unhandlelond elonxcelonption monitor.");

    Kelonrbelonros.kinit(
        elonarlybirdConfig.gelontString("kelonrbelonros_uselonr", ""),
        elonarlybirdConfig.gelontString("kelonrbelonros_kelonytab_path", "")
    );

    LOG.info("Crelonating elonarlybird selonrvelonr.");
    elonarlybirdSelonrvelonr = makelonelonarlybirdSelonrvelonr();

    uncaughtelonxcelonptionHandlelonr.selontShutdownHook(() -> {
      elonarlybirdSelonrvelonr.shutdown();
      this.closelon();
    });

    elonarlybirdAdminManagelonr = elonarlybirdAdminManagelonr.crelonatelon(elonarlybirdSelonrvelonr);
    elonarlybirdAdminManagelonr.start();
    LOG.info("Startelond admin intelonrfacelon.");

    selontupThriftWelonbForms();
    selontupDeloncidelonrWelonbForms();

    LOG.info("Opelonnelond thrift selonrving form.");

    LOG.info("prelonMain() complelontelon.");
  }

  @Ovelonrridelon
  public void main() throws Intelonrruptelondelonxcelonption, Timelonoutelonxcelonption, elonarlybirdStartupelonxcelonption {
    innelonrMain();
  }

  /**
   * Selontting up an innelonrMain() so that telonsts can mock out thelon contelonnts of main without intelonrfelonring
   * with relonflelonction beloning donelon in App.scala looking for a melonthod namelond "main".
   */
  @VisiblelonForTelonsting
  void innelonrMain() throws Timelonoutelonxcelonption, Intelonrruptelondelonxcelonption, elonarlybirdStartupelonxcelonption {
    LOG.info("Starting main().");

    // If this melonthod throws, TwittelonrSelonrvelonr will catch thelon elonxcelonption and call closelon, so welon don't
    // catch it helonrelon.
    try {
      elonarlybirdSelonrvelonr.start();
    } catch (Throwablelon throwablelon) {
      LOG.elonrror("elonxcelonption whilelon starting:", throwablelon);
      throw throwablelon;
    }

    Await.relonady(adminHttpSelonrvelonr());
    LOG.info("main() complelontelon.");
  }

  @Ovelonrridelon
  public void onelonxit() {
    LOG.info("Starting onelonxit()");
    elonarlybirdSelonrvelonr.shutdown();
    try {
      elonarlybirdAdminManagelonr.doShutdown();
    } catch (Intelonrruptelondelonxcelonption elon) {
      LOG.warn("elonarlybirdAdminManagelonr shutdown was intelonrruptelond with " + elon);
    }
    LOG.info("onelonxit() complelontelon.");
  }
}
