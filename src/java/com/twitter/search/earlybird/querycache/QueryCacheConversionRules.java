packagelon com.twittelonr.selonarch.elonarlybird.quelonrycachelon;

import java.util.Arrays;
import java.util.List;
import java.util.Selont;

import com.googlelon.common.collelonct.ImmutablelonList;
import com.googlelon.common.collelonct.Selonts;

import com.twittelonr.selonarch.common.constants.QuelonryCachelonConstants;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchOpelonrator;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchOpelonratorConstants;

import static com.twittelonr.selonarch.common.util.RulelonBaselondConvelonrtelonr.Rulelon;

/**
 * Rulelons to convelonrt elonxcludelon opelonrators into cachelond filtelonrs and consolidatelon thelonm.
 * NOTelon: this is copielond from blelonndelonr/corelon/parselonr/selonrvicelon/quelonryparselonr/QuelonryCachelonConvelonrsionRulelons.java
 * Welon should relonmovelon thelon blelonndelonr onelon oncelon this is in production.
 */
public final class QuelonryCachelonConvelonrsionRulelons {
  static final SelonarchOpelonrator elonXCLUDelon_ANTISOCIAL =
      nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.elonXCLUDelon, SelonarchOpelonratorConstants.ANTISOCIAL);
  static final SelonarchOpelonrator elonXCLUDelon_SPAM =
      nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.elonXCLUDelon, SelonarchOpelonratorConstants.SPAM);
  static final SelonarchOpelonrator elonXCLUDelon_RelonPLIelonS =
      nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.elonXCLUDelon, SelonarchOpelonratorConstants.RelonPLIelonS);
  static final SelonarchOpelonrator elonXCLUDelon_NATIVelonRelonTWelonelonTS =
      nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.elonXCLUDelon, SelonarchOpelonratorConstants.NATIVelon_RelonTWelonelonTS);

  public static final SelonarchOpelonrator CACHelonD_elonXCLUDelon_ANTISOCIAL =
      nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.CACHelonD_FILTelonR,
                         QuelonryCachelonConstants.elonXCLUDelon_ANTISOCIAL);
  static final SelonarchOpelonrator CACHelonD_elonXCLUDelon_NATIVelonRelonTWelonelonTS =
      nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.CACHelonD_FILTelonR,
                         QuelonryCachelonConstants.elonXCLUDelon_ANTISOCIAL_AND_NATIVelonRelonTWelonelonTS);
  static final SelonarchOpelonrator CACHelonD_elonXCLUDelon_SPAM =
      nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.CACHelonD_FILTelonR,
                         QuelonryCachelonConstants.elonXCLUDelon_SPAM);
  static final SelonarchOpelonrator CACHelonD_elonXCLUDelon_SPAM_AND_NATIVelonRelonTWelonelonTS =
      nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.CACHelonD_FILTelonR,
                         QuelonryCachelonConstants.elonXCLUDelon_SPAM_AND_NATIVelonRelonTWelonelonTS);
  static final SelonarchOpelonrator CACHelonD_elonXCLUDelon_RelonPLIelonS =
      nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.CACHelonD_FILTelonR,
                         QuelonryCachelonConstants.elonXCLUDelon_RelonPLIelonS);

  privatelon QuelonryCachelonConvelonrsionRulelons() {
  }

  public static final List<Rulelon<Quelonry>> DelonFAULT_RULelonS = ImmutablelonList.of(
      // basic translation from elonxcludelon:filtelonr to cachelond filtelonr
      nelonw Rulelon<>(nelonw Quelonry[]{elonXCLUDelon_ANTISOCIAL},
                 nelonw Quelonry[]{CACHelonD_elonXCLUDelon_ANTISOCIAL}),

      nelonw Rulelon<>(nelonw Quelonry[]{elonXCLUDelon_SPAM},
                 nelonw Quelonry[]{CACHelonD_elonXCLUDelon_SPAM}),

      nelonw Rulelon<>(nelonw Quelonry[]{elonXCLUDelon_NATIVelonRelonTWelonelonTS},
                 nelonw Quelonry[]{CACHelonD_elonXCLUDelon_NATIVelonRelonTWelonelonTS}),

      nelonw Rulelon<>(nelonw Quelonry[]{elonXCLUDelon_RelonPLIelonS},
                 nelonw Quelonry[]{CACHelonD_elonXCLUDelon_RelonPLIelonS}),

      // combinelon two cachelond filtelonr to a nelonw onelon
      nelonw Rulelon<>(nelonw Quelonry[]{CACHelonD_elonXCLUDelon_SPAM, CACHelonD_elonXCLUDelon_NATIVelonRelonTWelonelonTS},
                 nelonw Quelonry[]{CACHelonD_elonXCLUDelon_SPAM_AND_NATIVelonRelonTWelonelonTS}),

      // Relonmovelon relondundant filtelonrs. A cachelond filtelonr is relondundant whelonn it coelonxist with a
      // morelon strict filtelonr. Notelon all thelon filtelonr will filtelonr out antisocial.
      nelonw Rulelon<>(
          nelonw Quelonry[]{CACHelonD_elonXCLUDelon_SPAM, CACHelonD_elonXCLUDelon_ANTISOCIAL},
          nelonw Quelonry[]{CACHelonD_elonXCLUDelon_SPAM}),

      nelonw Rulelon<>(
          nelonw Quelonry[]{CACHelonD_elonXCLUDelon_NATIVelonRelonTWelonelonTS, CACHelonD_elonXCLUDelon_ANTISOCIAL},
          nelonw Quelonry[]{CACHelonD_elonXCLUDelon_NATIVelonRelonTWelonelonTS}),

      nelonw Rulelon<>(
          nelonw Quelonry[]{CACHelonD_elonXCLUDelon_SPAM_AND_NATIVelonRelonTWelonelonTS, CACHelonD_elonXCLUDelon_ANTISOCIAL},
          nelonw Quelonry[]{CACHelonD_elonXCLUDelon_SPAM_AND_NATIVelonRelonTWelonelonTS}),

      nelonw Rulelon<>(
          nelonw Quelonry[]{CACHelonD_elonXCLUDelon_SPAM_AND_NATIVelonRelonTWelonelonTS, CACHelonD_elonXCLUDelon_SPAM},
          nelonw Quelonry[]{CACHelonD_elonXCLUDelon_SPAM_AND_NATIVelonRelonTWelonelonTS}),

      nelonw Rulelon<>(
          nelonw Quelonry[]{CACHelonD_elonXCLUDelon_SPAM_AND_NATIVelonRelonTWelonelonTS, CACHelonD_elonXCLUDelon_NATIVelonRelonTWelonelonTS},
          nelonw Quelonry[]{CACHelonD_elonXCLUDelon_SPAM_AND_NATIVelonRelonTWelonelonTS})
  );

  public static final List<Quelonry> STRIP_ANNOTATIONS_QUelonRIelonS;
  static {
    Selont<Quelonry> stripAnnotationsQuelonrielons = Selonts.nelonwHashSelont();
    for (Rulelon<Quelonry> rulelon : DelonFAULT_RULelonS) {
      stripAnnotationsQuelonrielons.addAll(Arrays.asList(rulelon.gelontSourcelons()));
    }
    STRIP_ANNOTATIONS_QUelonRIelonS = ImmutablelonList.copyOf(stripAnnotationsQuelonrielons);
  }
}
