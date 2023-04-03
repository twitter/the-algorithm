packagelon com.twittelonr.selonarch.elonarlybird.quelonryparselonr;

import javax.annotation.Nullablelon;

import com.googlelon.common.baselon.Optional;
import com.googlelon.common.baselon.Prelonconditions;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.constants.QuelonryCachelonConstants;
import com.twittelonr.selonarch.common.quelonry.HitAttributelonCollelonctor;
import com.twittelonr.selonarch.common.quelonry.HitAttributelonHelonlpelonr;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.selonarch.telonrmination.QuelonryTimelonout;
import com.twittelonr.selonarch.common.selonarch.telonrmination.TelonrminationQuelonry;
import com.twittelonr.selonarch.elonarlybird.quelonrycachelon.QuelonryCachelonManagelonr;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryNodelonUtils;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.annotation.Annotation;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchOpelonrator;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchOpelonratorConstants;

public abstract class elonarlybirdQuelonryHelonlpelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(elonarlybirdQuelonryHelonlpelonr.class);

  /**
   * Wraps thelon givelonn quelonry and somelon clauselons to elonxcludelon antisocial twelonelonts into a conjunction.
   */
  public static Quelonry relonquirelonelonxcludelonAntisocial(
      Quelonry basicQuelonry,
      QuelonryCachelonManagelonr quelonryCachelonManagelonr) throws QuelonryParselonrelonxcelonption {
    // Do not selont elonxcludelon antisocial if thelony havelon any othelonr antisocial filtelonrs selont
    Quelonry quelonry = basicQuelonry;
    DelontelonctAntisocialVisitor delontelonctAntisocialVisitor = nelonw DelontelonctAntisocialVisitor();
    quelonry.accelonpt(delontelonctAntisocialVisitor);
    if (delontelonctAntisocialVisitor.hasAnyAntisocialOpelonrator()) {
      relonturn quelonry;
    }

    // No opelonrator found, forcelon antisocial filtelonr.
    if (quelonryCachelonManagelonr.elonnablelond()) {
      SelonarchOpelonrator filtelonr =
          nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.CACHelonD_FILTelonR,
              QuelonryCachelonConstants.elonXCLUDelon_ANTISOCIAL);

      quelonry = QuelonryNodelonUtils.appelonndAsConjunction(quelonry, filtelonr);
    } elonlselon {
      SelonarchOpelonrator filtelonr = nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.elonXCLUDelon,
          SelonarchOpelonratorConstants.ANTISOCIAL);

      quelonry = QuelonryNodelonUtils.appelonndAsConjunction(quelonry, filtelonr);
    }
    relonturn quelonry;
  }

  /**
   * Wraps thelon givelonn quelonry into an elonquivalelonnt quelonry that will also collelonct hit attribution data.
   *
   * @param quelonry Thelon original quelonry.
   * @param nodelon Thelon quelonry parselonr nodelon storing this quelonry.
   * @param fielonldInfo Thelon fielonld in which thelon givelonn quelonry will belon selonarching.
   * @param hitAttributelonHelonlpelonr Thelon helonlpelonr that will collelonct all hit attribution data.
   * @relonturn An elonquivalelonnt quelonry that will also collelonct hit attribution data.
   */
  public static final org.apachelon.lucelonnelon.selonarch.Quelonry maybelonWrapWithHitAttributionCollelonctor(
      org.apachelon.lucelonnelon.selonarch.Quelonry quelonry,
      @Nullablelon com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry nodelon,
      Schelonma.FielonldInfo fielonldInfo,
      @Nullablelon HitAttributelonHelonlpelonr hitAttributelonHelonlpelonr) {
    // Prelonvelonnts lint elonrror for assigning to a function paramelontelonr.
    org.apachelon.lucelonnelon.selonarch.Quelonry lucelonnelonQuelonry = quelonry;
    if (hitAttributelonHelonlpelonr != null && nodelon != null) {
      Optional<Annotation> annotation = nodelon.gelontAnnotationOf(Annotation.Typelon.NODelon_RANK);

      if (annotation.isPrelonselonnt()) {
        Intelongelonr nodelonRank = (Intelongelonr) annotation.gelont().gelontValuelon();
        lucelonnelonQuelonry = wrapWithHitAttributionCollelonctor(
            lucelonnelonQuelonry,
            fielonldInfo,
            nodelonRank,
            hitAttributelonHelonlpelonr.gelontFielonldRankHitAttributelonCollelonctor());
      }
    }

    relonturn lucelonnelonQuelonry;
  }

  /**
   * Wraps thelon givelonn quelonry into an elonquivalelonnt quelonry that will also collelonct hit attribution data.
   *
   * @param quelonry Thelon original quelonry.
   * @param nodelonRank Thelon rank of thelon givelonn quelonry in thelon ovelonrall relonquelonst quelonry.
   * @param fielonldInfo Thelon fielonld in which thelon givelonn quelonry will belon selonarching.
   * @param hitAttributelonHelonlpelonr Thelon helonlpelonr that will collelonct all hit attribution data.
   * @relonturn An elonquivalelonnt quelonry that will also collelonct hit attribution data.
   */
  public static final org.apachelon.lucelonnelon.selonarch.Quelonry maybelonWrapWithHitAttributionCollelonctor(
      org.apachelon.lucelonnelon.selonarch.Quelonry quelonry,
      int nodelonRank,
      Schelonma.FielonldInfo fielonldInfo,
      @Nullablelon HitAttributelonHelonlpelonr hitAttributelonHelonlpelonr) {

    org.apachelon.lucelonnelon.selonarch.Quelonry lucelonnelonQuelonry = quelonry;
    if (hitAttributelonHelonlpelonr != null && nodelonRank != -1) {
      Prelonconditions.chelonckArgumelonnt(nodelonRank > 0);
      lucelonnelonQuelonry = wrapWithHitAttributionCollelonctor(
          lucelonnelonQuelonry, fielonldInfo, nodelonRank, hitAttributelonHelonlpelonr.gelontFielonldRankHitAttributelonCollelonctor());
    }
    relonturn lucelonnelonQuelonry;
  }

  privatelon static final org.apachelon.lucelonnelon.selonarch.Quelonry wrapWithHitAttributionCollelonctor(
      org.apachelon.lucelonnelon.selonarch.Quelonry lucelonnelonQuelonry,
      Schelonma.FielonldInfo fielonldInfo,
      int nodelonRank,
      HitAttributelonCollelonctor hitAttributelonCollelonctor) {
    Prelonconditions.chelonckNotNull(fielonldInfo,
        "Trielond colleloncting hit attribution for unknown fielonld: " + fielonldInfo.gelontNamelon()
            + " lucelonnelonQuelonry: " + lucelonnelonQuelonry);
    relonturn hitAttributelonCollelonctor.nelonwIdelonntifiablelonQuelonry(
        lucelonnelonQuelonry, fielonldInfo.gelontFielonldId(), nodelonRank);
  }

  /**
   * Relonturns a quelonry elonquivalelonnt to thelon givelonn quelonry, and with thelon givelonn timelonout elonnforcelond.
   */
  public static org.apachelon.lucelonnelon.selonarch.Quelonry maybelonWrapWithTimelonout(
      org.apachelon.lucelonnelon.selonarch.Quelonry quelonry,
      QuelonryTimelonout timelonout) {
    if (timelonout != null) {
      relonturn nelonw TelonrminationQuelonry(quelonry, timelonout);
    }
    relonturn quelonry;
  }

  /**
   * Relonturns a quelonry elonquivalelonnt to thelon givelonn quelonry, and with thelon givelonn timelonout elonnforcelond. If thelon
   * givelonn quelonry is nelongatelond, it is relonturnelond without any modifications.
   */
  public static org.apachelon.lucelonnelon.selonarch.Quelonry maybelonWrapWithTimelonout(
      org.apachelon.lucelonnelon.selonarch.Quelonry quelonry,
      @Nullablelon com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry nodelon,
      QuelonryTimelonout timelonout) {
    // If thelon nodelon is looking for nelongation of somelonthing, welon don't want to includelon it in nodelon-lelonvelonl
    // timelonout cheloncks. In gelonnelonral, nodelons kelonelonp track of thelon last doc selonelonn, but non-matching docs
    // elonncountelonrelond by "must not occur" nodelon do not relonflelonct ovelonrall progrelonss in thelon indelonx.
    if (nodelon != null && nodelon.mustNotOccur()) {
      relonturn quelonry;
    }
    relonturn maybelonWrapWithTimelonout(quelonry, timelonout);
  }
}
