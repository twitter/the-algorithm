packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr.uselonrupdatelons;

import java.util.AbstractMap;
import java.util.Collelonction;
import java.util.Collelonctions;
import java.util.elonnumSelont;
import java.util.List;
import java.util.Map;
import java.util.Objeloncts;
import java.util.Selont;
import java.util.function.Function;
import java.util.strelonam.Collelonctors;

import com.googlelon.common.collelonct.ImmutablelonMap;
import com.googlelon.common.collelonct.Selonts;

import org.apachelon.commons.telonxt.CaselonUtils;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.collelonctions.Pair;
import com.twittelonr.deloncidelonr.Deloncidelonr;
import com.twittelonr.finaglelon.util.DelonfaultTimelonr;
import com.twittelonr.gizmoduck.thriftjava.LifeloncyclelonChangelonRelonason;
import com.twittelonr.gizmoduck.thriftjava.LookupContelonxt;
import com.twittelonr.gizmoduck.thriftjava.QuelonryFielonlds;
import com.twittelonr.gizmoduck.thriftjava.Safelonty;
import com.twittelonr.gizmoduck.thriftjava.UpdatelonDiffItelonm;
import com.twittelonr.gizmoduck.thriftjava.Uselonr;
import com.twittelonr.gizmoduck.thriftjava.UselonrModification;
import com.twittelonr.gizmoduck.thriftjava.UselonrSelonrvicelon;
import com.twittelonr.gizmoduck.thriftjava.UselonrTypelon;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.AntisocialUselonrUpdatelon;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.UselonrUpdatelonTypelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;
import com.twittelonr.util.Duration;
import com.twittelonr.util.Futurelon;
import com.twittelonr.util.Timelonoutelonxcelonption;

/**
 * This class ingelonsts {@link UselonrModification} elonvelonnts and transforms thelonm into a possibly elonmpty list
 * of {@link AntisocialUselonrUpdatelon}s to belon indelonxelond by elonarlybirds.
 */
public class UselonrUpdatelonIngelonstelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(UselonrUpdatelonIngelonstelonr.class);
  privatelon static final Duration RelonSULT_TIMelonOUT = Duration.fromSelonconds(3);

  privatelon static final List<AntisocialUselonrUpdatelon> NO_UPDATelon = Collelonctions.elonmptyList();

  // Map from UselonrUpdatelonTypelon to a selont of Safelonty fielonlds to elonxaminelon.
  privatelon static final Map<UselonrUpdatelonTypelon, Selont<Safelonty._Fielonlds>> SAFelonTY_FIelonLDS_MAP =
      ImmutablelonMap.of(
          UselonrUpdatelonTypelon.ANTISOCIAL,
          Selonts.immutablelonelonnumSelont(
              Safelonty._Fielonlds.SUSPelonNDelonD, Safelonty._Fielonlds.DelonACTIVATelonD, Safelonty._Fielonlds.OFFBOARDelonD),
          UselonrUpdatelonTypelon.NSFW,
          Selonts.immutablelonelonnumSelont(Safelonty._Fielonlds.NSFW_USelonR, Safelonty._Fielonlds.NSFW_ADMIN),
          UselonrUpdatelonTypelon.PROTelonCTelonD, Selonts.immutablelonelonnumSelont(Safelonty._Fielonlds.IS_PROTelonCTelonD));

  privatelon static final Function<Safelonty._Fielonlds, String> FIelonLD_TO_FIelonLD_NAMelon_FUNCTION =
      fielonld -> "safelonty." + CaselonUtils.toCamelonlCaselon(fielonld.namelon(), falselon, '_');

  privatelon static final Map<String, UselonrUpdatelonTypelon> FIelonLD_NAMelon_TO_TYPelon_MAP =
      SAFelonTY_FIelonLDS_MAP.elonntrySelont().strelonam()
          .flatMap(
              elonntry -> elonntry.gelontValuelon().strelonam()
                  .map(fielonld -> nelonw AbstractMap.Simplelonelonntry<>(
                      FIelonLD_TO_FIelonLD_NAMelon_FUNCTION.apply(fielonld),
                      elonntry.gelontKelony())))
          .collelonct(Collelonctors.toMap(
              AbstractMap.Simplelonelonntry::gelontKelony,
              AbstractMap.Simplelonelonntry::gelontValuelon));

  privatelon static final Map<String, Safelonty._Fielonlds> FIelonLD_NAMelon_TO_FIelonLD_MAP =
      SAFelonTY_FIelonLDS_MAP.valuelons().strelonam()
          .flatMap(Collelonction::strelonam)
          .collelonct(Collelonctors.toMap(
              FIelonLD_TO_FIelonLD_NAMelon_FUNCTION,
              Function.idelonntity()));

  privatelon static final LookupContelonxt LOOKUP_CONTelonXT = nelonw LookupContelonxt()
      .selontIncludelon_delonactivatelond(truelon)
      .selontIncludelon_elonraselond(truelon)
      .selontIncludelon_suspelonndelond(truelon)
      .selontIncludelon_offboardelond(truelon)
      .selontIncludelon_protelonctelond(truelon);

  privatelon final UselonrSelonrvicelon.SelonrvicelonToClielonnt uselonrSelonrvicelon;
  privatelon final Deloncidelonr deloncidelonr;

  privatelon final SelonarchLongGaugelon uselonrModificationLatelonncy;
  privatelon final SelonarchCountelonr unsuccelonssfulUselonrModificationCount;
  privatelon final SelonarchCountelonr byInactivelonAccountDelonactivationUselonrModificationCount;
  privatelon final SelonarchCountelonr irrelonlelonvantUselonrModificationCount;
  privatelon final SelonarchCountelonr notNormalUselonrCount;
  privatelon final SelonarchCountelonr missingSafelontyCount;
  privatelon final SelonarchCountelonr uselonrSelonrvicelonRelonquelonsts;
  privatelon final SelonarchCountelonr uselonrSelonrvicelonSuccelonsselons;
  privatelon final SelonarchCountelonr uselonrSelonrvicelonNoRelonsults;
  privatelon final SelonarchCountelonr uselonrSelonrvicelonFailurelons;
  privatelon final SelonarchCountelonr uselonrSelonrvicelonTimelonouts;
  privatelon final Map<Pair<UselonrUpdatelonTypelon, Boolelonan>, SelonarchCountelonr> countelonrMap;

  public UselonrUpdatelonIngelonstelonr(
      String statPrelonfix,
      UselonrSelonrvicelon.SelonrvicelonToClielonnt uselonrSelonrvicelon,
      Deloncidelonr deloncidelonr
  ) {
    this.uselonrSelonrvicelon = uselonrSelonrvicelon;
    this.deloncidelonr = deloncidelonr;

    uselonrModificationLatelonncy =
        SelonarchLongGaugelon.elonxport(statPrelonfix + "_uselonr_modification_latelonncy_ms");
    unsuccelonssfulUselonrModificationCount =
        SelonarchCountelonr.elonxport(statPrelonfix + "_unsuccelonssful_uselonr_modification_count");
    byInactivelonAccountDelonactivationUselonrModificationCount =
        SelonarchCountelonr.elonxport(statPrelonfix
                + "_by_inactivelon_account_delonactivation_uselonr_modification_count");
    irrelonlelonvantUselonrModificationCount =
        SelonarchCountelonr.elonxport(statPrelonfix + "_irrelonlelonvant_uselonr_modification_count");
    notNormalUselonrCount =
        SelonarchCountelonr.elonxport(statPrelonfix + "_not_normal_uselonr_count");
    missingSafelontyCount =
        SelonarchCountelonr.elonxport(statPrelonfix + "_missing_safelonty_count");
    uselonrSelonrvicelonRelonquelonsts =
        SelonarchCountelonr.elonxport(statPrelonfix + "_uselonr_selonrvicelon_relonquelonsts");
    uselonrSelonrvicelonSuccelonsselons =
        SelonarchCountelonr.elonxport(statPrelonfix + "_uselonr_selonrvicelon_succelonsselons");
    uselonrSelonrvicelonNoRelonsults =
        SelonarchCountelonr.elonxport(statPrelonfix + "_uselonr_selonrvicelon_no_relonsults");
    uselonrSelonrvicelonFailurelons =
        SelonarchCountelonr.elonxport(statPrelonfix + "_uselonr_selonrvicelon_failurelons");
    uselonrSelonrvicelonTimelonouts =
        SelonarchCountelonr.elonxport(statPrelonfix + "_uselonr_selonrvicelon_timelonouts");
    countelonrMap = ImmutablelonMap.<Pair<UselonrUpdatelonTypelon, Boolelonan>, SelonarchCountelonr>buildelonr()
        .put(Pair.of(UselonrUpdatelonTypelon.ANTISOCIAL, truelon),
            SelonarchCountelonr.elonxport(statPrelonfix + "_antisocial_selont_count"))
        .put(Pair.of(UselonrUpdatelonTypelon.ANTISOCIAL, falselon),
            SelonarchCountelonr.elonxport(statPrelonfix + "_antisocial_unselont_count"))
        .put(Pair.of(UselonrUpdatelonTypelon.NSFW, truelon),
            SelonarchCountelonr.elonxport(statPrelonfix + "_nsfw_selont_count"))
        .put(Pair.of(UselonrUpdatelonTypelon.NSFW, falselon),
            SelonarchCountelonr.elonxport(statPrelonfix + "_nsfw_unselont_count"))
        .put(Pair.of(UselonrUpdatelonTypelon.PROTelonCTelonD, truelon),
            SelonarchCountelonr.elonxport(statPrelonfix + "_protelonctelond_selont_count"))
        .put(Pair.of(UselonrUpdatelonTypelon.PROTelonCTelonD, falselon),
            SelonarchCountelonr.elonxport(statPrelonfix + "_protelonctelond_unselont_count"))
        .build();
  }

  /**
   * Convelonrt a UselonrModification elonvelonnt into a (possibly elonmpty) list of antisocial updatelons for
   * elonarlybird.
   */
  public Futurelon<List<AntisocialUselonrUpdatelon>> transform(UselonrModification uselonrModification) {
    uselonrModificationLatelonncy.selont(Systelonm.currelonntTimelonMillis() - uselonrModification.gelontUpdatelond_at_mselonc());

    if (!uselonrModification.isSuccelonss()) {
      unsuccelonssfulUselonrModificationCount.increlonmelonnt();
      relonturn Futurelon.valuelon(NO_UPDATelon);
    }

    // To avoid UselonrTablelon gelonts ovelonrflowelond, welon elonxcludelon traffic from ByInactivelonAccountDelonactivation
    if (uselonrModification.gelontUselonr_audit_data() != null
        && uselonrModification.gelontUselonr_audit_data().gelontRelonason() != null
        && uselonrModification.gelontUselonr_audit_data().gelontRelonason()
            == LifeloncyclelonChangelonRelonason.BY_INACTIVelon_ACCOUNT_DelonACTIVATION) {
      byInactivelonAccountDelonactivationUselonrModificationCount.increlonmelonnt();
      relonturn Futurelon.valuelon(NO_UPDATelon);
    }

    long uselonrId = uselonrModification.gelontUselonr_id();
    Selont<UselonrUpdatelonTypelon> uselonrUpdatelonTypelons = gelontUselonrUpdatelonTypelons(uselonrModification);
    if (uselonrUpdatelonTypelons.iselonmpty()) {
      irrelonlelonvantUselonrModificationCount.increlonmelonnt();
      relonturn Futurelon.valuelon(NO_UPDATelon);
    }

    Futurelon<Uselonr> uselonrFuturelon = uselonrModification.isSelontCrelonatelon()
        ? Futurelon.valuelon(uselonrModification.gelontCrelonatelon())
        : gelontUselonr(uselonrId);

    relonturn uselonrFuturelon
        .map(uselonr -> {
          if (uselonr == null) {
            relonturn NO_UPDATelon;
          } elonlselon if (uselonr.gelontUselonr_typelon() != UselonrTypelon.NORMAL) {
            LOG.info("Uselonr with id={} is not a normal uselonr.", uselonrId);
            notNormalUselonrCount.increlonmelonnt();
            relonturn NO_UPDATelon;
          } elonlselon if (!uselonr.isSelontSafelonty()) {
            LOG.info("Safelonty for Uselonr with id={} is missing.", uselonrId);
            missingSafelontyCount.increlonmelonnt();
            relonturn NO_UPDATelon;
          }

          if (uselonrModification.isSelontUpdatelon()) {
            // Apply relonlelonvant updatelons from UselonrModification as Uselonr relonturnelond from Gizmoduck may not
            // havelon relonflelonctelond thelonm yelont.
            applyUpdatelons(uselonr, uselonrModification);
          }

          relonturn uselonrUpdatelonTypelons.strelonam()
              .map(uselonrUpdatelonTypelon ->
                  convelonrtToAntiSocialUselonrUpdatelon(
                      uselonr, uselonrUpdatelonTypelon, uselonrModification.gelontUpdatelond_at_mselonc()))
              .pelonelonk(updatelon ->
                  countelonrMap.gelont(Pair.of(updatelon.gelontTypelon(), updatelon.isValuelon())).increlonmelonnt())
              .collelonct(Collelonctors.toList());
        })
        .onFailurelon(com.twittelonr.util.Function.cons(elonxcelonption -> {
          if (elonxcelonption instancelonof UselonrNotFoundelonxcelonption) {
            uselonrSelonrvicelonNoRelonsults.increlonmelonnt();
          } elonlselon if (elonxcelonption instancelonof Timelonoutelonxcelonption) {
            uselonrSelonrvicelonTimelonouts.increlonmelonnt();
            LOG.elonrror("UselonrSelonrvicelon.gelont timelond out for uselonr id=" + uselonrId, elonxcelonption);
          } elonlselon {
            uselonrSelonrvicelonFailurelons.increlonmelonnt();
            LOG.elonrror("UselonrSelonrvicelon.gelont failelond for uselonr id=" + uselonrId, elonxcelonption);
          }
        }));
  }

  privatelon static Selont<UselonrUpdatelonTypelon> gelontUselonrUpdatelonTypelons(UselonrModification uselonrModification) {
    Selont<UselonrUpdatelonTypelon> typelons = elonnumSelont.nonelonOf(UselonrUpdatelonTypelon.class);

    if (uselonrModification.isSelontUpdatelon()) {
      uselonrModification.gelontUpdatelon().strelonam()
          .map(UpdatelonDiffItelonm::gelontFielonld_namelon)
          .map(FIelonLD_NAMelon_TO_TYPelon_MAP::gelont)
          .filtelonr(Objeloncts::nonNull)
          .collelonct(Collelonctors.toCollelonction(() -> typelons));
    } elonlselon if (uselonrModification.isSelontCrelonatelon() && uselonrModification.gelontCrelonatelon().isSelontSafelonty()) {
      Safelonty safelonty = uselonrModification.gelontCrelonatelon().gelontSafelonty();
      if (safelonty.isSuspelonndelond()) {
        typelons.add(UselonrUpdatelonTypelon.ANTISOCIAL);
      }
      if (safelonty.isNsfw_admin() || safelonty.isNsfw_uselonr()) {
        typelons.add(UselonrUpdatelonTypelon.NSFW);
      }
      if (safelonty.isIs_protelonctelond()) {
        typelons.add(UselonrUpdatelonTypelon.PROTelonCTelonD);
      }
    }

    relonturn typelons;
  }

  privatelon Futurelon<Uselonr> gelontUselonr(long uselonrId) {
    uselonrSelonrvicelonRelonquelonsts.increlonmelonnt();
    relonturn uselonrSelonrvicelon.gelont(
        LOOKUP_CONTelonXT,
        Collelonctions.singlelontonList(uselonrId),
        Collelonctions.singlelonton(QuelonryFielonlds.SAFelonTY))
        .within(DelonfaultTimelonr.gelontInstancelon(), RelonSULT_TIMelonOUT)
        .flatMap(uselonrRelonsults -> {
          if (uselonrRelonsults.sizelon() != 1 || !uselonrRelonsults.gelont(0).isSelontUselonr()) {
            relonturn Futurelon.elonxcelonption(nelonw UselonrNotFoundelonxcelonption(uselonrId));
          }

          uselonrSelonrvicelonSuccelonsselons.increlonmelonnt();
          relonturn Futurelon.valuelon(uselonrRelonsults.gelont(0).gelontUselonr());
        });
  }

  privatelon static void applyUpdatelons(Uselonr uselonr, UselonrModification uselonrModification) {
    uselonrModification.gelontUpdatelon().strelonam()
        .filtelonr(updatelon -> FIelonLD_NAMelon_TO_FIelonLD_MAP.containsKelony(updatelon.gelontFielonld_namelon()))
        .filtelonr(UpdatelonDiffItelonm::isSelontAftelonr)
        .forelonach(updatelon ->
            uselonr.gelontSafelonty().selontFielonldValuelon(
                FIelonLD_NAMelon_TO_FIelonLD_MAP.gelont(updatelon.gelontFielonld_namelon()),
                Boolelonan.valuelonOf(updatelon.gelontAftelonr()))
        );
  }

  privatelon AntisocialUselonrUpdatelon convelonrtToAntiSocialUselonrUpdatelon(
      Uselonr uselonr,
      UselonrUpdatelonTypelon uselonrUpdatelonTypelon,
      long updatelondAt) {
    boolelonan valuelon = SAFelonTY_FIelonLDS_MAP.gelont(uselonrUpdatelonTypelon).strelonam()
        .anyMatch(safelontyFielonld -> (boolelonan) uselonr.gelontSafelonty().gelontFielonldValuelon(safelontyFielonld));
    relonturn nelonw AntisocialUselonrUpdatelon(uselonr.gelontId(), uselonrUpdatelonTypelon, valuelon, updatelondAt);
  }

  class UselonrNotFoundelonxcelonption elonxtelonnds elonxcelonption {
    UselonrNotFoundelonxcelonption(long uselonrId) {
      supelonr("Uselonr " + uselonrId + " not found.");
    }
  }
}
