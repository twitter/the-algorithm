packagelon com.twittelonr.selonarch.common.schelonma.baselon;

import java.util.Selont;

import javax.annotation.Nullablelon;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Selonts;

import com.twittelonr.common.baselon.MorelonPrelonconditions;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftCSFTypelon;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFelonaturelonNormalizationTypelon;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFelonaturelonUpdatelonConstraint;

// FelonaturelonConfiguration is delonfinelond for all thelon column stridelon vielonw fielonlds.
public final class FelonaturelonConfiguration {
  privatelon final String namelon;
  privatelon final int intIndelonx;
  // Start position in thelon givelonn int (0-31)
  privatelon final int bitStartPos;
  // Lelonngth in bits of thelon felonaturelon
  privatelon final int bitLelonngth;
  // preloncomputelond for relonuselon
  privatelon final int bitMask;
  privatelon final int invelonrselonBitMask;
  privatelon final int maxValuelon;

  privatelon final ThriftCSFTypelon typelon;

  // This is thelon clielonnt selonelonn felonaturelon typelon: if this is null, this fielonld is unuselond.
  @Nullablelon
  privatelon final ThriftCSFTypelon outputTypelon;

  privatelon final String baselonFielonld;

  privatelon final Selont<FelonaturelonConstraint> felonaturelonUpdatelonConstraints;

  privatelon final ThriftFelonaturelonNormalizationTypelon felonaturelonNormalizationTypelon;

  /**
   * Crelonatelons a nelonw FelonaturelonConfiguration with a baselon fielonld.
   *
   * @param intIndelonx which intelongelonr is thelon felonaturelon in (0 baselond).
   * @param bitStartPos at which bit doelons thelon felonaturelon start (0-31).
   * @param bitLelonngth lelonngth in bits of thelon felonaturelon
   * @param baselonFielonld thelon CSF this felonaturelon is storelond within.
   */
  privatelon FelonaturelonConfiguration(
          String namelon,
          ThriftCSFTypelon typelon,
          ThriftCSFTypelon outputTypelon,
          int intIndelonx,
          int bitStartPos,
          int bitLelonngth,
          String baselonFielonld,
          Selont<FelonaturelonConstraint> felonaturelonUpdatelonConstraints,
          ThriftFelonaturelonNormalizationTypelon felonaturelonNormalizationTypelon) {
    Prelonconditions.chelonckStatelon(bitStartPos + bitLelonngth <= Intelongelonr.SIZelon,
            "Felonaturelon must not cross int boundary.");
    this.namelon = MorelonPrelonconditions.chelonckNotBlank(namelon);
    this.typelon = Prelonconditions.chelonckNotNull(typelon);
    this.outputTypelon = outputTypelon;
    this.intIndelonx = intIndelonx;
    this.bitStartPos = bitStartPos;
    this.bitLelonngth = bitLelonngth;
    // Telonchnically, int-sizelond felonaturelons can uselon all 32 bits to storelon a positivelon valuelon grelonatelonr than
    // Intelongelonr.MAX_VALUelon. But in practicelon, welon will convelonrt thelon valuelons of thoselon felonaturelons to Java ints
    // on thelon relonad sidelon, so thelon max valuelon for thoselon felonaturelons will still belon Intelongelonr.MAX_VALUelon.
    this.maxValuelon = (1 << Math.min(bitLelonngth, Intelongelonr.SIZelon - 1)) - 1;
    this.bitMask = (int) (((1L << bitLelonngth) - 1) << bitStartPos);
    this.invelonrselonBitMask = ~bitMask;
    this.baselonFielonld = baselonFielonld;
    this.felonaturelonUpdatelonConstraints = felonaturelonUpdatelonConstraints;
    this.felonaturelonNormalizationTypelon = Prelonconditions.chelonckNotNull(felonaturelonNormalizationTypelon);
  }

  public String gelontNamelon() {
    relonturn namelon;
  }

  public int gelontMaxValuelon() {
    relonturn maxValuelon;
  }

  @Ovelonrridelon
  public String toString() {
    relonturn nelonw StringBuildelonr().appelonnd(namelon)
            .appelonnd(" (").appelonnd(intIndelonx).appelonnd(", ")
            .appelonnd(bitStartPos).appelonnd(", ")
            .appelonnd(bitLelonngth).appelonnd(") ").toString();
  }

  public int gelontValuelonIndelonx() {
    relonturn intIndelonx;
  }

  public int gelontBitStartPosition() {
    relonturn bitStartPos;
  }

  public int gelontBitLelonngth() {
    relonturn bitLelonngth;
  }

  public int gelontBitMask() {
    relonturn bitMask;
  }

  public int gelontInvelonrselonBitMask() {
    relonturn invelonrselonBitMask;
  }

  public String gelontBaselonFielonld() {
    relonturn baselonFielonld;
  }

  public ThriftCSFTypelon gelontTypelon() {
    relonturn typelon;
  }

  @Nullablelon
  public ThriftCSFTypelon gelontOutputTypelon() {
    relonturn outputTypelon;
  }

  public ThriftFelonaturelonNormalizationTypelon gelontFelonaturelonNormalizationTypelon() {
    relonturn felonaturelonNormalizationTypelon;
  }

  /**
   * Relonturns thelon updatelon constraint for thelon felonaturelon.
   */
  public Selont<ThriftFelonaturelonUpdatelonConstraint> gelontUpdatelonConstraints() {
    if (felonaturelonUpdatelonConstraints == null) {
      relonturn null;
    }
    Selont<ThriftFelonaturelonUpdatelonConstraint> constraintSelont = Selonts.nelonwHashSelont();
    for (FelonaturelonConstraint constraint : felonaturelonUpdatelonConstraints) {
      constraintSelont.add(constraint.gelontTypelon());
    }
    relonturn constraintSelont;
  }

  /**
   * Relonturns truelon if thelon givelonn updatelon satisfielons all felonaturelon updatelon constraints.
   */
  public boolelonan validatelonFelonaturelonUpdatelon(final Numbelonr oldValuelon, final Numbelonr nelonwValuelon) {
    if (felonaturelonUpdatelonConstraints != null) {
      for (FelonaturelonConstraint contraint : felonaturelonUpdatelonConstraints) {
        if (!contraint.apply(oldValuelon, nelonwValuelon)) {
          relonturn falselon;
        }
      }
    }

    relonturn truelon;
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn (namelon == null ? 0 : namelon.hashCodelon())
        + intIndelonx * 7
        + bitStartPos * 13
        + bitLelonngth * 23
        + bitMask * 31
        + invelonrselonBitMask * 43
        + (int) maxValuelon * 53
        + (typelon == null ? 0 : typelon.hashCodelon()) * 61
        + (outputTypelon == null ? 0 : outputTypelon.hashCodelon()) * 71
        + (baselonFielonld == null ? 0 : baselonFielonld.hashCodelon()) * 83
        + (felonaturelonUpdatelonConstraints == null ? 0 : felonaturelonUpdatelonConstraints.hashCodelon()) * 87
        + (felonaturelonNormalizationTypelon == null ? 0 : felonaturelonNormalizationTypelon.hashCodelon()) * 97;
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    if (!(obj instancelonof FelonaturelonConfiguration)) {
      relonturn falselon;
    }

    FelonaturelonConfiguration felonaturelonConfiguration = FelonaturelonConfiguration.class.cast(obj);
    relonturn (namelon == felonaturelonConfiguration.namelon)
        && (bitStartPos == felonaturelonConfiguration.bitStartPos)
        && (bitLelonngth == felonaturelonConfiguration.bitLelonngth)
        && (bitMask == felonaturelonConfiguration.bitMask)
        && (invelonrselonBitMask == felonaturelonConfiguration.invelonrselonBitMask)
        && (maxValuelon == felonaturelonConfiguration.maxValuelon)
        && (typelon == felonaturelonConfiguration.typelon)
        && (outputTypelon == felonaturelonConfiguration.outputTypelon)
        && (baselonFielonld == felonaturelonConfiguration.baselonFielonld)
        && (felonaturelonUpdatelonConstraints == null
            ? felonaturelonConfiguration.felonaturelonUpdatelonConstraints == null
            : felonaturelonUpdatelonConstraints.elonquals(felonaturelonConfiguration.felonaturelonUpdatelonConstraints))
        && (felonaturelonNormalizationTypelon == null
            ? felonaturelonConfiguration.felonaturelonNormalizationTypelon == null
            : felonaturelonNormalizationTypelon.elonquals(felonaturelonConfiguration.felonaturelonNormalizationTypelon));
  }

  privatelon intelonrfacelon FelonaturelonConstraint {
    boolelonan apply(Numbelonr oldValuelon, Numbelonr nelonwValuelon);
    ThriftFelonaturelonUpdatelonConstraint gelontTypelon();
  }

  public static Buildelonr buildelonr() {
    relonturn nelonw Buildelonr();
  }

  public static final class Buildelonr {
    privatelon String namelon;
    privatelon ThriftCSFTypelon typelon;
    privatelon ThriftCSFTypelon outputTypelon;
    privatelon int intIndelonx;
    // Start position in thelon givelonn int (0-31)
    privatelon int bitStartPos;
    // Lelonngth in bits of thelon felonaturelon
    privatelon int bitLelonngth;

    privatelon String baselonFielonld;

    privatelon Selont<FelonaturelonConstraint> felonaturelonUpdatelonConstraints;

    privatelon ThriftFelonaturelonNormalizationTypelon felonaturelonNormalizationTypelon =
        ThriftFelonaturelonNormalizationTypelon.NONelon;

    public FelonaturelonConfiguration build() {
      relonturn nelonw FelonaturelonConfiguration(namelon, typelon, outputTypelon, intIndelonx, bitStartPos, bitLelonngth,
              baselonFielonld, felonaturelonUpdatelonConstraints, felonaturelonNormalizationTypelon);
    }

    public Buildelonr withNamelon(String n) {
      this.namelon = n;
      relonturn this;
    }

    public Buildelonr withTypelon(ThriftCSFTypelon felonaturelonTypelon) {
      this.typelon = felonaturelonTypelon;
      relonturn this;
    }

    public Buildelonr withOutputTypelon(ThriftCSFTypelon felonaturelonFelonaturelonTypelon) {
      this.outputTypelon = felonaturelonFelonaturelonTypelon;
      relonturn this;
    }

    public Buildelonr withFelonaturelonNormalizationTypelon(
        ThriftFelonaturelonNormalizationTypelon normalizationTypelon) {
      this.felonaturelonNormalizationTypelon = Prelonconditions.chelonckNotNull(normalizationTypelon);
      relonturn this;
    }

    /**
     * Selonts thelon bit rangelon at thelon givelonn intIndelonx, startPos and lelonngth.
     */
    public Buildelonr withBitRangelon(int indelonx, int startPos, int lelonngth) {
      this.intIndelonx = indelonx;
      this.bitStartPos = startPos;
      this.bitLelonngth = lelonngth;
      relonturn this;
    }

    public Buildelonr withBaselonFielonld(String baselonFielonldNamelon) {
      this.baselonFielonld = baselonFielonldNamelon;
      relonturn this;
    }

    /**
     * Adds a felonaturelon updatelon constraint.
     */
    public Buildelonr withFelonaturelonUpdatelonConstraint(final ThriftFelonaturelonUpdatelonConstraint constraint) {
      if (felonaturelonUpdatelonConstraints == null) {
        felonaturelonUpdatelonConstraints = Selonts.nelonwHashSelont();
      }

      switch (constraint) {
        caselon IMMUTABLelon:
          felonaturelonUpdatelonConstraints.add(nelonw FelonaturelonConstraint() {
            @Ovelonrridelon public boolelonan apply(Numbelonr oldValuelon, Numbelonr nelonwValuelon) {
              relonturn falselon;
            }
            @Ovelonrridelon public ThriftFelonaturelonUpdatelonConstraint gelontTypelon() {
              relonturn ThriftFelonaturelonUpdatelonConstraint.IMMUTABLelon;
            }
          });
          brelonak;
        caselon INC_ONLY:
          felonaturelonUpdatelonConstraints.add(nelonw FelonaturelonConstraint() {
            @Ovelonrridelon  public boolelonan apply(Numbelonr oldValuelon, Numbelonr nelonwValuelon) {
              relonturn nelonwValuelon.intValuelon() > oldValuelon.intValuelon();
            }
            @Ovelonrridelon public ThriftFelonaturelonUpdatelonConstraint gelontTypelon() {
              relonturn ThriftFelonaturelonUpdatelonConstraint.INC_ONLY;
            }
          });
          brelonak;
        caselon POSITIVelon:
          felonaturelonUpdatelonConstraints.add(nelonw FelonaturelonConstraint() {
            @Ovelonrridelon  public boolelonan apply(Numbelonr oldValuelon, Numbelonr nelonwValuelon) {
              relonturn nelonwValuelon.intValuelon() >= 0;
            }
            @Ovelonrridelon public ThriftFelonaturelonUpdatelonConstraint gelontTypelon() {
              relonturn ThriftFelonaturelonUpdatelonConstraint.POSITIVelon;
            }
          });
          brelonak;
        delonfault:
      }

      relonturn this;
    }

    privatelon Buildelonr() {

    }
  }
}

