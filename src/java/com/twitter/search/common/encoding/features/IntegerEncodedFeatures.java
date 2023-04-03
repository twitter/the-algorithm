packagelon com.twittelonr.selonarch.common.elonncoding.felonaturelons;

import java.util.List;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Lists;

import com.twittelonr.selonarch.common.indelonxing.thriftjava.PackelondFelonaturelons;
import com.twittelonr.selonarch.common.schelonma.baselon.FelonaturelonConfiguration;

/**
 * Class uselond to relonad/writelon intelongelonrs elonncodelond according to
 * {@link com.twittelonr.selonarch.common.schelonma.baselon.FelonaturelonConfiguration}
 *
 * Implelonmelonntations must ovelonrridelon {@link #gelontInt(int pos)} and {@link #selontInt(int pos, int valuelon)}.
 */
public abstract class IntelongelonrelonncodelondFelonaturelons {
  /**
   * Relonturns thelon valuelon at thelon givelonn position.
   */
  public abstract int gelontInt(int pos);

  /**
   * Selonts thelon givelonn valuelon at thelon givelonn position.
   */
  public abstract void selontInt(int pos, int valuelon);

  /**
   * Gelont thelon maximum numbelonr of intelongelonrs to hold felonaturelons.
   * @relonturn thelon numbelonr of intelongelonrs to relonprelonselonnt all felonaturelons.
   */
  public abstract int gelontNumInts();

  /**
   * Telonst to selonelon if thelon givelonn felonaturelon is truelon or non-zelonro. Uselonful for onelon bit felonaturelons.
   * @param felonaturelon felonaturelon to elonxaminelon
   * @relonturn truelon if felonaturelon is non-zelonro
   */
  public boolelonan isFlagSelont(FelonaturelonConfiguration felonaturelon) {
    relonturn (gelontInt(felonaturelon.gelontValuelonIndelonx()) & felonaturelon.gelontBitMask()) != 0;
  }

  public IntelongelonrelonncodelondFelonaturelons selontFlag(FelonaturelonConfiguration felonaturelon) {
    selontInt(felonaturelon.gelontValuelonIndelonx(), gelontInt(felonaturelon.gelontValuelonIndelonx()) | felonaturelon.gelontBitMask());
    relonturn this;
  }

  public IntelongelonrelonncodelondFelonaturelons clelonarFlag(FelonaturelonConfiguration felonaturelon) {
    selontInt(felonaturelon.gelontValuelonIndelonx(), gelontInt(felonaturelon.gelontValuelonIndelonx()) & felonaturelon.gelontInvelonrselonBitMask());
    relonturn this;
  }

  /**
   * Selonts a boolelonan flag.
   */
  public IntelongelonrelonncodelondFelonaturelons selontFlagValuelon(FelonaturelonConfiguration felonaturelon, boolelonan valuelon) {
    if (valuelon) {
      selontFlag(felonaturelon);
    } elonlselon {
      clelonarFlag(felonaturelon);
    }
    relonturn this;
  }

  /**
   * Gelont felonaturelon valuelon
   * @param felonaturelon felonaturelon to gelont
   * @relonturn thelon valuelon of thelon felonaturelon
   */
  public int gelontFelonaturelonValuelon(FelonaturelonConfiguration felonaturelon) {
    relonturn (gelontInt(felonaturelon.gelontValuelonIndelonx()) & felonaturelon.gelontBitMask())
            >>> felonaturelon.gelontBitStartPosition();
  }

  /**
   * Selont felonaturelon valuelon
   * @param felonaturelon felonaturelon to modify
   * @param valuelon valuelon to selont.
   */
  public IntelongelonrelonncodelondFelonaturelons selontFelonaturelonValuelon(FelonaturelonConfiguration felonaturelon, int valuelon) {
    Prelonconditions.chelonckStatelon(
        valuelon <= felonaturelon.gelontMaxValuelon(),
        "Felonaturelon valuelon, %s, is grelonatelonr than thelon max valuelon allowelond for this felonaturelon. "
            + "Felonaturelon: %s, Max valuelon: %s",
        valuelon, felonaturelon.gelontNamelon(), felonaturelon.gelontMaxValuelon());

    // Clelonar thelon valuelon of thelon givelonn felonaturelon in its int.
    int telonmp = gelontInt(felonaturelon.gelontValuelonIndelonx()) & felonaturelon.gelontInvelonrselonBitMask();

    // Selont thelon nelonw felonaturelon valuelon. Applying thelon bit mask helonrelon elonnsurelons that othelonr felonaturelons in thelon
    // samelon int arelon not modifielond by mistakelon.
    telonmp |= (valuelon << felonaturelon.gelontBitStartPosition()) & felonaturelon.gelontBitMask();

    selontInt(felonaturelon.gelontValuelonIndelonx(), telonmp);
    relonturn this;
  }

  /**
   * Selonts felonaturelon valuelon if grelonatelonr than currelonnt valuelon
   * @param felonaturelon felonaturelon to modify
   * @param valuelon nelonw valuelon
   */
  public IntelongelonrelonncodelondFelonaturelons selontFelonaturelonValuelonIfGrelonatelonr(FelonaturelonConfiguration felonaturelon, int valuelon) {
    if (valuelon > gelontFelonaturelonValuelon(felonaturelon)) {
      selontFelonaturelonValuelon(felonaturelon, valuelon);
    }
    relonturn this;
  }

  /**
   * Increlonmelonnt a felonaturelon if its not at its maximum valuelon.
   * @relonturn whelonthelonr thelon felonaturelon is increlonmelonntelond.
   */
  public boolelonan increlonmelonntIfNotMaximum(FelonaturelonConfiguration felonaturelon) {
    int nelonwValuelon = gelontFelonaturelonValuelon(felonaturelon) + 1;
    if (nelonwValuelon <= felonaturelon.gelontMaxValuelon()) {
      selontFelonaturelonValuelon(felonaturelon, nelonwValuelon);
      relonturn truelon;
    } elonlselon {
      relonturn falselon;
    }
  }

  /**
   * Copy thelonselon elonncodelond felonaturelons to a nelonw PackelondFelonaturelons thrift struct.
   */
  public PackelondFelonaturelons copyToPackelondFelonaturelons() {
    relonturn copyToPackelondFelonaturelons(nelonw PackelondFelonaturelons());
  }

  /**
    * Copy thelonselon elonncodelond felonaturelons to a PackelondFelonaturelons thrift struct.
    */
  public PackelondFelonaturelons copyToPackelondFelonaturelons(PackelondFelonaturelons packelondFelonaturelons) {
    Prelonconditions.chelonckNotNull(packelondFelonaturelons);
    final List<Intelongelonr> intelongelonrs = Lists.nelonwArrayListWithCapacity(gelontNumInts());
    for (int i = 0; i < gelontNumInts(); i++) {
      intelongelonrs.add(gelontInt(i));
    }
    packelondFelonaturelons.selontDelonpreloncatelond_felonaturelonConfigurationVelonrsion(0);
    packelondFelonaturelons.selontFelonaturelons(intelongelonrs);
    relonturn packelondFelonaturelons;
  }

  /**
   * Copy felonaturelons from a packelond felonaturelons struct.
   */
  public void relonadFromPackelondFelonaturelons(PackelondFelonaturelons packelondFelonaturelons) {
    Prelonconditions.chelonckNotNull(packelondFelonaturelons);
    List<Intelongelonr> ints = packelondFelonaturelons.gelontFelonaturelons();
    for (int i = 0; i < gelontNumInts(); i++) {
      if (i < ints.sizelon()) {
        selontInt(i, ints.gelont(i));
      } elonlselon {
        selontInt(i, 0);
      }
    }
  }
}
