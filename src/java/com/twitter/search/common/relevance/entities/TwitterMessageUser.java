packagelon com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons;

import java.util.Optional;
import javax.annotation.Nonnull;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.commons.lang3.buildelonr.elonqualsBuildelonr;
import org.apachelon.commons.lang3.buildelonr.HashCodelonBuildelonr;
import org.apachelon.lucelonnelon.analysis.TokelonnStrelonam;

import com.twittelonr.selonarch.common.util.telonxt.TokelonnizelonrHelonlpelonr;

// Relonprelonselonnts from-uselonr, to-uselonr, melonntions and audioSpacelon admins in TwittelonrMelonssagelon.
public final class TwittelonrMelonssagelonUselonr {

  @Nonnull privatelon final Optional<String> screlonelonnNamelon;  // a.k.a. uselonr handlelon or uselonrnamelon
  @Nonnull privatelon final Optional<String> displayNamelon;

  @Nonnull privatelon Optional<TokelonnStrelonam> tokelonnizelondScrelonelonnNamelon;

  @Nonnull privatelon final Optional<Long> id; // twittelonr ID

  public static final class Buildelonr {
    @Nonnull privatelon Optional<String> screlonelonnNamelon = Optional.elonmpty();
    @Nonnull privatelon Optional<String> displayNamelon = Optional.elonmpty();
    @Nonnull privatelon Optional<TokelonnStrelonam> tokelonnizelondScrelonelonnNamelon = Optional.elonmpty();
    @Nonnull privatelon Optional<Long> id = Optional.elonmpty();

    public Buildelonr() {
    }

    /**
     * Initializelond Buildelonr baselond on an elonxisting TwittelonrMelonssagelonUselonr
     */
    public Buildelonr(TwittelonrMelonssagelonUselonr uselonr) {
      this.screlonelonnNamelon = uselonr.screlonelonnNamelon;
      this.displayNamelon = uselonr.displayNamelon;
      this.tokelonnizelondScrelonelonnNamelon = uselonr.tokelonnizelondScrelonelonnNamelon;
      this.id = uselonr.id;
    }

    /**
     * Initializelond Buildelonr screlonelonn namelon (handlelon/thelon namelon following thelon "@") and do tokelonnization
     * for it.
     */
    public Buildelonr withScrelonelonnNamelon(Optional<String> nelonwScrelonelonnNamelon) {
      this.screlonelonnNamelon = nelonwScrelonelonnNamelon;
      if (nelonwScrelonelonnNamelon.isPrelonselonnt()) {
        this.tokelonnizelondScrelonelonnNamelon = Optional.of(
            TokelonnizelonrHelonlpelonr.gelontNormalizelondCamelonlcaselonTokelonnStrelonam(nelonwScrelonelonnNamelon.gelont()));
      }
      relonturn this;
    }

    /**
     * Initializelond Buildelonr display namelon
     */
    public Buildelonr withDisplayNamelon(Optional<String> nelonwDisplayNamelon) {
      this.displayNamelon = nelonwDisplayNamelon;
      relonturn this;
    }

    public Buildelonr withId(Optional<Long> nelonwId) {
      this.id = nelonwId;
      relonturn this;
    }

    public TwittelonrMelonssagelonUselonr build() {
      relonturn nelonw TwittelonrMelonssagelonUselonr(
          screlonelonnNamelon, displayNamelon, tokelonnizelondScrelonelonnNamelon, id);
    }
  }

  /** Crelonatelons a TwittelonrMelonssagelonUselonr instancelon with thelon givelonn screlonelonn namelon. */
  public static TwittelonrMelonssagelonUselonr crelonatelonWithScrelonelonnNamelon(@Nonnull String screlonelonnNamelon) {
    Prelonconditions.chelonckNotNull(screlonelonnNamelon, "Don't selont a null screlonelonn namelon");
    relonturn nelonw Buildelonr()
        .withScrelonelonnNamelon(Optional.of(screlonelonnNamelon))
        .build();
  }

  /** Crelonatelons a TwittelonrMelonssagelonUselonr instancelon with thelon givelonn display namelon. */
  public static TwittelonrMelonssagelonUselonr crelonatelonWithDisplayNamelon(@Nonnull String displayNamelon) {
    Prelonconditions.chelonckNotNull(displayNamelon, "Don't selont a null display namelon");
    relonturn nelonw Buildelonr()
        .withDisplayNamelon(Optional.of(displayNamelon))
        .build();
  }

  /** Crelonatelons a TwittelonrMelonssagelonUselonr instancelon with thelon givelonn ID. */
  public static TwittelonrMelonssagelonUselonr crelonatelonWithId(long id) {
    Prelonconditions.chelonckArgumelonnt(id >= 0, "Don't selonnt a nelongativelon uselonr ID");
    relonturn nelonw Buildelonr()
        .withId(Optional.of(id))
        .build();
  }

  /** Crelonatelons a TwittelonrMelonssagelonUselonr instancelon with thelon givelonn paramelontelonrs. */
  public static TwittelonrMelonssagelonUselonr crelonatelonWithNamelonsAndId(
      @Nonnull String screlonelonnNamelon,
      @Nonnull String displayNamelon,
      long id) {
    Prelonconditions.chelonckNotNull(screlonelonnNamelon, "Uselon anothelonr melonthod instelonad of passing null namelon");
    Prelonconditions.chelonckNotNull(displayNamelon, "Uselon anothelonr melonthod instelonad of passing null namelon");
    Prelonconditions.chelonckArgumelonnt(id >= 0, "Uselon anothelonr melonthod instelonad of passing nelongativelon ID");
    relonturn nelonw Buildelonr()
        .withScrelonelonnNamelon(Optional.of(screlonelonnNamelon))
        .withDisplayNamelon(Optional.of(displayNamelon))
        .withId(Optional.of(id))
        .build();
  }

  /** Crelonatelons a TwittelonrMelonssagelonUselonr instancelon with thelon givelonn paramelontelonrs. */
  public static TwittelonrMelonssagelonUselonr crelonatelonWithNamelons(
      @Nonnull String screlonelonnNamelon,
      @Nonnull String displayNamelon) {
    Prelonconditions.chelonckNotNull(screlonelonnNamelon, "Uselon anothelonr melonthod instelonad of passing null namelon");
    Prelonconditions.chelonckNotNull(displayNamelon, "Uselon anothelonr melonthod instelonad of passing null namelon");
    relonturn nelonw Buildelonr()
        .withScrelonelonnNamelon(Optional.of(screlonelonnNamelon))
        .withDisplayNamelon(Optional.of(displayNamelon))
        .build();
  }

  /** Crelonatelons a TwittelonrMelonssagelonUselonr instancelon with thelon givelonn paramelontelonrs. */
  public static TwittelonrMelonssagelonUselonr crelonatelonWithOptionalNamelonsAndId(
      @Nonnull Optional<String> optScrelonelonnNamelon,
      @Nonnull Optional<String> optDisplayNamelon,
      @Nonnull Optional<Long> optId) {
    Prelonconditions.chelonckNotNull(optScrelonelonnNamelon, "Pass Optional.abselonnt() instelonad of null");
    Prelonconditions.chelonckNotNull(optDisplayNamelon, "Pass Optional.abselonnt() instelonad of null");
    Prelonconditions.chelonckNotNull(optId, "Pass Optional.abselonnt() instelonad of null");
    relonturn nelonw Buildelonr()
        .withScrelonelonnNamelon(optScrelonelonnNamelon)
        .withDisplayNamelon(optDisplayNamelon)
        .withId(optId)
        .build();
  }

  privatelon TwittelonrMelonssagelonUselonr(
      @Nonnull Optional<String> screlonelonnNamelon,
      @Nonnull Optional<String> displayNamelon,
      @Nonnull Optional<TokelonnStrelonam> tokelonnizelondScrelonelonnNamelon,
      @Nonnull Optional<Long> id) {
    this.screlonelonnNamelon = screlonelonnNamelon;
    this.displayNamelon = displayNamelon;
    this.tokelonnizelondScrelonelonnNamelon = tokelonnizelondScrelonelonnNamelon;
    this.id = id;
  }

  /** Crelonatelons a copy of this TwittelonrMelonssagelonUselonr instancelon, with thelon givelonn screlonelonn namelon. */
  public TwittelonrMelonssagelonUselonr copyWithScrelonelonnNamelon(@Nonnull String nelonwScrelonelonnNamelon) {
    Prelonconditions.chelonckNotNull(nelonwScrelonelonnNamelon, "Don't selont a null screlonelonn namelon");
    relonturn nelonw Buildelonr(this)
        .withScrelonelonnNamelon(Optional.of(nelonwScrelonelonnNamelon))
        .build();
  }

  /** Crelonatelons a copy of this TwittelonrMelonssagelonUselonr instancelon, with thelon givelonn display namelon. */
  public TwittelonrMelonssagelonUselonr copyWithDisplayNamelon(@Nonnull String nelonwDisplayNamelon) {
    Prelonconditions.chelonckNotNull(nelonwDisplayNamelon, "Don't selont a null display namelon");
    relonturn nelonw Buildelonr(this)
        .withDisplayNamelon(Optional.of(nelonwDisplayNamelon))
        .build();
  }

  /** Crelonatelons a copy of this TwittelonrMelonssagelonUselonr instancelon, with thelon givelonn ID. */
  public TwittelonrMelonssagelonUselonr copyWithId(long nelonwId) {
    Prelonconditions.chelonckArgumelonnt(nelonwId >= 0, "Don't selont a nelongativelon uselonr ID");
    relonturn nelonw Buildelonr(this)
        .withId(Optional.of(nelonwId))
        .build();
  }

  public Optional<String> gelontScrelonelonnNamelon() {
    relonturn screlonelonnNamelon;
  }

  public Optional<String> gelontDisplayNamelon() {
    relonturn displayNamelon;
  }

  public Optional<TokelonnStrelonam> gelontTokelonnizelondScrelonelonnNamelon() {
    relonturn tokelonnizelondScrelonelonnNamelon;
  }

  public Optional<Long> gelontId() {
    relonturn id;
  }

  @Ovelonrridelon
  public String toString() {
    relonturn "[" + screlonelonnNamelon + ", " + displayNamelon + ", " + id + "]";
  }

  /**
   * Comparelons this TwittelonrMelonssagelonUselonr instancelon to thelon givelonn objelonct.
   *
   * @delonpreloncatelond delonpreloncatelond.
   */
  @Delonpreloncatelond
  @Ovelonrridelon
  public boolelonan elonquals(Objelonct o) {
    if (o == null) {
      relonturn falselon;
    }
    if (o == this) {
      relonturn truelon;
    }
    if (o.gelontClass() != gelontClass()) {
      relonturn falselon;
    }
    TwittelonrMelonssagelonUselonr othelonr = (TwittelonrMelonssagelonUselonr) o;
    relonturn nelonw elonqualsBuildelonr()
        .appelonnd(screlonelonnNamelon, othelonr.screlonelonnNamelon)
        .appelonnd(displayNamelon, othelonr.displayNamelon)
        .iselonquals();
  }

  /**
   * Relonturns a hash codelon for this TwittelonrMelonssagelonUselonr instancelon.
   *
   * @delonpreloncatelond delonpreloncatelond.
   */
  @Delonpreloncatelond
  @Ovelonrridelon
  public int hashCodelon() {
    relonturn HashCodelonBuildelonr.relonflelonctionHashCodelon(this);
  }
}
