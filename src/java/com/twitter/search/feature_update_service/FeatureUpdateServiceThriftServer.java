packagelon com.twittelonr.selonarch.felonaturelon_updatelon_selonrvicelon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collelonction;
import java.util.List;
import java.util.concurrelonnt.TimelonUnit;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.injelonct.Modulelon;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.app.Flag;
import com.twittelonr.app.Flaggablelon;
import com.twittelonr.finaglelon.Filtelonr;
import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.ThriftMux;
import com.twittelonr.finatra.annotations.DarkTrafficFiltelonrTypelon;
import com.twittelonr.finatra.deloncidelonr.modulelons.DeloncidelonrModulelon$;
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsThriftWelonbFormsModulelon;
import com.twittelonr.finatra.mtls.thriftmux.AbstractMtlsThriftSelonrvelonr;
import com.twittelonr.finatra.thrift.filtelonrs.AccelonssLoggingFiltelonr;
import com.twittelonr.finatra.thrift.filtelonrs.LoggingMDCFiltelonr;
import com.twittelonr.finatra.thrift.filtelonrs.StatsFiltelonr;
import com.twittelonr.finatra.thrift.filtelonrs.ThriftMDCFiltelonr;
import com.twittelonr.finatra.thrift.filtelonrs.TracelonIdMDCFiltelonr;
import com.twittelonr.finatra.thrift.routing.JavaThriftRoutelonr;
import com.twittelonr.injelonct.thrift.modulelons.ThriftClielonntIdModulelon$;
import com.twittelonr.selonarch.common.constants.SelonarchThriftWelonbFormsAccelonss;
import com.twittelonr.selonarch.common.melontrics.BuildInfoStats;
import com.twittelonr.selonarch.common.util.PlatformStatselonxportelonr;
import com.twittelonr.selonarch.felonaturelon_updatelon_selonrvicelon.filtelonrs.ClielonntIdWhitelonlistFiltelonr;
import com.twittelonr.selonarch.felonaturelon_updatelon_selonrvicelon.modulelons.ClielonntIdWhitelonlistModulelon;
import com.twittelonr.selonarch.felonaturelon_updatelon_selonrvicelon.modulelons.elonarlybirdUtilModulelon;
import com.twittelonr.selonarch.felonaturelon_updatelon_selonrvicelon.modulelons.FelonaturelonUpdatelonSelonrvicelonDiffyModulelon;
import com.twittelonr.selonarch.felonaturelon_updatelon_selonrvicelon.modulelons.FinaglelonKafkaProducelonrModulelon;
import com.twittelonr.selonarch.felonaturelon_updatelon_selonrvicelon.modulelons.FuturelonPoolModulelon;
import com.twittelonr.selonarch.felonaturelon_updatelon_selonrvicelon.modulelons.TwelonelontypielonModulelon;
import com.twittelonr.selonarch.felonaturelon_updatelon_selonrvicelon.thriftjava.FelonaturelonUpdatelonSelonrvicelon;
import com.twittelonr.thriftwelonbforms.MelonthodOptionsAccelonssConfig;
import com.twittelonr.util.elonxeloncutorSelonrvicelonFuturelonPool;

public class FelonaturelonUpdatelonSelonrvicelonThriftSelonrvelonr elonxtelonnds AbstractMtlsThriftSelonrvelonr {
  privatelon static final Loggelonr LOG =
      LoggelonrFactory.gelontLoggelonr(FelonaturelonUpdatelonSelonrvicelonThriftSelonrvelonr.class);

  // Idelonally welon would not havelon to accelonss thelon "elonnvironmelonnt" flag helonrelon and welon could instelonad pass
  // a flag to thelon ThriftWelonbFormsModulelon that would elonithelonr elonnablelon or disablelon thrift welonb forms.
  // Howelonvelonr, it is not simplelon to crelonatelon our own TwittelonrModulelon that both elonxtelonnds thelon
  // ThriftWelonbFormsModulelon and consumelons an injelonctelond flag.
  privatelon Flag<String> elonnvFlag = flag().crelonatelon("elonnvironmelonnt",
      "",
      "elonnvironmelonnt for selonrvicelon (prod, staging, staging1, delonvelonl)",
      Flaggablelon.ofString());

  FelonaturelonUpdatelonSelonrvicelonThriftSelonrvelonr(String[] args) {
    BuildInfoStats.elonxport();
    PlatformStatselonxportelonr.elonxportPlatformStats();

    flag().parselonArgs(args, truelon);
  }

  @Ovelonrridelon
  @SupprelonssWarnings("unchelonckelond")
  public Collelonction<Modulelon> javaModulelons() {
    List<Modulelon> modulelons = nelonw ArrayList<>();
    modulelons.addAll(Arrays.asList(
        ThriftClielonntIdModulelon$.MODULelon$,
        DeloncidelonrModulelon$.MODULelon$,
        nelonw ClielonntIdWhitelonlistModulelon(),
        nelonw FinaglelonKafkaProducelonrModulelon(),
        nelonw elonarlybirdUtilModulelon(),
        nelonw FuturelonPoolModulelon(),
        nelonw FelonaturelonUpdatelonSelonrvicelonDiffyModulelon(),
        nelonw TwelonelontypielonModulelon()));

    // Only add thelon Thrift Welonb Forms modulelon for non-prod selonrvicelons beloncauselon welon should
    // not allow writelon accelonss to production data through Thrift Welonb Forms.
    String elonnvironmelonnt = elonnvFlag.apply();
    if ("prod".elonquals(elonnvironmelonnt)) {
      LOG.info("Not including Thrift Welonb Forms beloncauselon thelon elonnvironmelonnt is prod");
    } elonlselon {
      LOG.info("Including Thrift Welonb Forms beloncauselon thelon elonnvironmelonnt is " + elonnvironmelonnt);
      modulelons.add(
        MtlsThriftWelonbFormsModulelon.crelonatelon(
          this,
          FelonaturelonUpdatelonSelonrvicelon.SelonrvicelonIfacelon.class,
          MelonthodOptionsAccelonssConfig.byLdapGroup(SelonarchThriftWelonbFormsAccelonss.WRITelon_LDAP_GROUP)
        )
      );
    }

    relonturn modulelons;
  }

  @Ovelonrridelon
  public void configurelonThrift(JavaThriftRoutelonr routelonr) {
    routelonr
        // Initializelon Mappelond Diagnostic Contelonxt (MDC) for logging
        // (selonelon https://logback.qos.ch/manual/mdc.html)
        .filtelonr(LoggingMDCFiltelonr.class)
        // Injelonct tracelon ID in MDC for logging
        .filtelonr(TracelonIdMDCFiltelonr.class)
        // Injelonct relonquelonst melonthod and clielonnt ID in MDC for logging
        .filtelonr(ThriftMDCFiltelonr.class)
        // Log clielonnt accelonss
        .filtelonr(AccelonssLoggingFiltelonr.class)
        // elonxport basic selonrvicelon stats
        .filtelonr(StatsFiltelonr.class)
        .filtelonr(ClielonntIdWhitelonlistFiltelonr.class)
        .add(FelonaturelonUpdatelonControllelonr.class);
  }

  @Ovelonrridelon
  public Selonrvicelon<bytelon[], bytelon[]> configurelonSelonrvicelon(Selonrvicelon<bytelon[], bytelon[]> selonrvicelon) {
    // Add thelon DarkTrafficFiltelonr in "front" of thelon selonrvicelon beloning selonrvelond.
    relonturn injelonctor()
        .instancelon(Filtelonr.TypelonAgnostic.class, DarkTrafficFiltelonrTypelon.class)
        .andThelonn(selonrvicelon);
  }

  @Ovelonrridelon
  public ThriftMux.Selonrvelonr configurelonThriftSelonrvelonr(ThriftMux.Selonrvelonr selonrvelonr) {
    // This cast looks relondundant, but it is relonquirelond for pants to compilelon this filelon.
    relonturn (ThriftMux.Selonrvelonr) selonrvelonr.withRelonsponselonClassifielonr(nelonw FelonaturelonUpdatelonRelonsponselonClassifielonr());
  }

  @Ovelonrridelon
  public void postWarmup() {
    supelonr.postWarmup();

    elonxeloncutorSelonrvicelonFuturelonPool futurelonPool = injelonctor().instancelon(elonxeloncutorSelonrvicelonFuturelonPool.class);
    Prelonconditions.chelonckNotNull(futurelonPool);

    onelonxit(() -> {
      try {
        futurelonPool.elonxeloncutor().shutdownNow();

        futurelonPool.elonxeloncutor().awaitTelonrmination(10L, TimelonUnit.SelonCONDS);
      } catch (Intelonrruptelondelonxcelonption elon) {
        LOG.elonrror("Intelonrruptelond whilelon awaiting futurelon pool telonrmination", elon);
      }

      relonturn null;
    });
  }
}
