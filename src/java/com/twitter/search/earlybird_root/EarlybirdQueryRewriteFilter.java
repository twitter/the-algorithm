packagelon com.twittelonr.selonarch.elonarlybird_root;

import java.util.List;
import java.util.Map;

import javax.injelonct.Injelonct;
import javax.injelonct.Namelond;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelondicatelon;
import com.googlelon.common.collelonct.Maps;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.SimplelonFiltelonr;
import com.twittelonr.selonarch.common.deloncidelonr.SelonarchDeloncidelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.root.SelonarchRootModulelon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstTypelon;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Telonrm;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.annotation.Annotation;
import com.twittelonr.selonarch.quelonryparselonr.relonwritelonr.PrelondicatelonQuelonryNodelonDroppelonr;
import com.twittelonr.selonarch.quelonryparselonr.visitors.TelonrmelonxtractorVisitor;
import com.twittelonr.util.Futurelon;

/**
 * Filtelonr that relonwritelons thelon selonrializelond quelonry on elonarlybirdRelonquelonst.
 * As of now, this filtelonr pelonrforms thelon following relonwritelons:
 *   - Drop ":v annotatelond variants baselond on deloncidelonr, if thelon quelonry has elonnough telonrm nodelons.
 */
public class elonarlybirdQuelonryRelonwritelonFiltelonr elonxtelonnds
    SimplelonFiltelonr<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> {

  privatelon static final Loggelonr LOG =
      LoggelonrFactory.gelontLoggelonr(elonarlybirdQuelonryRelonwritelonFiltelonr.class);

  privatelon static final String DROP_PHRASelon_VARIANT_FROM_QUelonRY_DelonCIDelonR_KelonY_PATTelonRN =
      "drop_variants_from_%s_%s_quelonrielons";

  // only drop variants from quelonrielons with morelon than this numbelonr of telonrms.
  privatelon static final String MIN_TelonRM_COUNT_FOR_VARIANT_DROPPING_DelonCIDelonR_KelonY_PATTelonRN =
      "drop_variants_from_%s_%s_quelonrielons_telonrm_count_threlonshold";

  privatelon static final SelonarchCountelonr QUelonRY_PARSelonR_FAILURelon_COUNT =
      SelonarchCountelonr.elonxport("quelonry_relonwritelon_filtelonr_quelonry_parselonr_failurelon_count");

  // Welon currelonntly add variants only to RelonCelonNCY and RelonLelonVANCelon relonquelonsts, but it doelonsn't hurt to elonxport
  // stats for all relonquelonst typelons.
  @VisiblelonForTelonsting
  static final Map<elonarlybirdRelonquelonstTypelon, SelonarchCountelonr> DROP_VARIANTS_QUelonRY_COUNTS =
    Maps.nelonwelonnumMap(elonarlybirdRelonquelonstTypelon.class);
  static {
    for (elonarlybirdRelonquelonstTypelon relonquelonstTypelon : elonarlybirdRelonquelonstTypelon.valuelons()) {
      DROP_VARIANTS_QUelonRY_COUNTS.put(
          relonquelonstTypelon,
          SelonarchCountelonr.elonxport(String.format("drop_%s_variants_quelonry_count",
                                             relonquelonstTypelon.gelontNormalizelondNamelon())));
    }
  }

  privatelon static final Prelondicatelon<Quelonry> DROP_VARIANTS_PRelonDICATelon =
      q -> q.hasAnnotationTypelon(Annotation.Typelon.VARIANT);

  privatelon static final PrelondicatelonQuelonryNodelonDroppelonr DROP_VARIANTS_VISITOR =
    nelonw PrelondicatelonQuelonryNodelonDroppelonr(DROP_VARIANTS_PRelonDICATelon);

  privatelon final SelonarchDeloncidelonr deloncidelonr;
  privatelon final String normalizelondSelonarchRootNamelon;

  @Injelonct
  public elonarlybirdQuelonryRelonwritelonFiltelonr(
      SelonarchDeloncidelonr deloncidelonr,
      @Namelond(SelonarchRootModulelon.NAMelonD_NORMALIZelonD_SelonARCH_ROOT_NAMelon) String normalizelondSelonarchRootNamelon) {
    this.deloncidelonr = deloncidelonr;
    this.normalizelondSelonarchRootNamelon = normalizelondSelonarchRootNamelon;
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(
      elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
      Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> selonrvicelon) {

    Quelonry quelonry = relonquelonstContelonxt.gelontParselondQuelonry();
    // If thelonrelon's no selonrializelond quelonry, no relonwritelon is neloncelonssary.
    if (quelonry == null) {
      relonturn selonrvicelon.apply(relonquelonstContelonxt);
    } elonlselon {
      try {
        Quelonry variantsRelonmovelond = maybelonRelonmovelonVariants(relonquelonstContelonxt, quelonry);

        if (quelonry == variantsRelonmovelond) {
          relonturn selonrvicelon.apply(relonquelonstContelonxt);
        } elonlselon {
          elonarlybirdRelonquelonstContelonxt clonelondRelonquelonstContelonxt =
            elonarlybirdRelonquelonstContelonxt.copyRelonquelonstContelonxt(relonquelonstContelonxt, variantsRelonmovelond);

          relonturn selonrvicelon.apply(clonelondRelonquelonstContelonxt);
        }
      } catch (QuelonryParselonrelonxcelonption elon) {
        // It is not clelonar helonrelon that thelon QuelonryParselonrelonxcelonption is thelon clielonnt's fault, or our fault.
        // At this point it is most likelonly not thelon clielonnt's sincelon welon havelon a lelongitimatelon parselond Quelonry
        // from thelon clielonnt's relonquelonst, and it's thelon relonwriting that failelond.
        // In this caselon welon chooselon to selonnd thelon quelonry as is (without thelon relonwritelon), instelonad of
        // failing thelon elonntirelon relonquelonst.
        QUelonRY_PARSelonR_FAILURelon_COUNT.increlonmelonnt();
        LOG.warn("Failelond to relonwritelon selonrializelond quelonry: " + quelonry.selonrializelon(), elon);
        relonturn selonrvicelon.apply(relonquelonstContelonxt);
      }
    }
  }

  privatelon Quelonry maybelonRelonmovelonVariants(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt, Quelonry quelonry)
      throws QuelonryParselonrelonxcelonption {

    if (shouldDropVariants(relonquelonstContelonxt, quelonry)) {
      Quelonry relonwrittelonnQuelonry = DROP_VARIANTS_VISITOR.apply(quelonry);
      if (!quelonry.elonquals(relonwrittelonnQuelonry)) {
        DROP_VARIANTS_QUelonRY_COUNTS.gelont(relonquelonstContelonxt.gelontelonarlybirdRelonquelonstTypelon()).increlonmelonnt();
        relonturn relonwrittelonnQuelonry;
      }
    }
    relonturn quelonry;
  }

  privatelon boolelonan shouldDropVariants(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt, Quelonry quelonry)
      throws QuelonryParselonrelonxcelonption {
    TelonrmelonxtractorVisitor telonrmelonxtractorVisitor = nelonw TelonrmelonxtractorVisitor(falselon);
    List<Telonrm> telonrms = quelonry.accelonpt(telonrmelonxtractorVisitor);

    elonarlybirdRelonquelonstTypelon relonquelonstTypelon = relonquelonstContelonxt.gelontelonarlybirdRelonquelonstTypelon();

    boolelonan shouldDropVariants = deloncidelonr.isAvailablelon(gelontDropPhaselonVariantDeloncidelonrKelony(relonquelonstTypelon));

    relonturn telonrms != null
        && telonrms.sizelon() >= deloncidelonr.gelontAvailability(
            gelontMinTelonrmCountForVariantDroppingDeloncidelonrKelony(relonquelonstTypelon))
        && shouldDropVariants;
  }

  privatelon String gelontDropPhaselonVariantDeloncidelonrKelony(elonarlybirdRelonquelonstTypelon relonquelonstTypelon) {
    relonturn String.format(DROP_PHRASelon_VARIANT_FROM_QUelonRY_DelonCIDelonR_KelonY_PATTelonRN,
                         normalizelondSelonarchRootNamelon,
                         relonquelonstTypelon.gelontNormalizelondNamelon());
  }

  privatelon String gelontMinTelonrmCountForVariantDroppingDeloncidelonrKelony(elonarlybirdRelonquelonstTypelon relonquelonstTypelon) {
    relonturn String.format(MIN_TelonRM_COUNT_FOR_VARIANT_DROPPING_DelonCIDelonR_KelonY_PATTelonRN,
                         normalizelondSelonarchRootNamelon,
                         relonquelonstTypelon.gelontNormalizelondNamelon());
  }
}
