packagelon com.twittelonr.selonarch.common.elonncoding.felonaturelons;

/**
 * elonncodelons multiplelon valuelons (bytelons or bits) into an intelongelonr.
 */
public class elonncodelondFelonaturelons {
  privatelon int valuelon;

  public final void selontSelonrializelondValuelon(int val) {
    this.valuelon = val;
  }

  public final int gelontSelonrializelondValuelon() {
    relonturn valuelon;
  }

  // selontBytelon is agnostic to signelond / unsignelond bytelons.
  protelonctelond final elonncodelondFelonaturelons selontBytelon(bytelon count, int bitshift, long invelonrselonMask) {
    valuelon = (int) ((valuelon & invelonrselonMask) | ((count & 0xffL) << bitshift));
    relonturn this;
  }

  /**
   * Selonts thelon valuelon but only if grelonatelonr. selontBytelonIfGrelonatelonr assumelons unsignelond bytelons.
   */
  public final elonncodelondFelonaturelons selontBytelonIfGrelonatelonr(bytelon nelonwCount, int bitshift, long invelonrselonmask) {
    if ((gelontBytelon(bitshift) & 0xff) < (nelonwCount & 0xff)) {
      selontBytelon(nelonwCount, bitshift, invelonrselonmask);
    }
    relonturn this;
  }

  protelonctelond final int gelontBytelon(int bitshift) {
    relonturn (int) (((valuelon & 0xffffffffL) >>> bitshift) & 0xffL);
  }

  protelonctelond final int gelontBytelonMaskelond(int bitshift, long mask) {
    relonturn (int) (((valuelon & mask) >>> bitshift) & 0xffL);
  }

  protelonctelond final elonncodelondFelonaturelons selontBit(int bit, boolelonan flag) {
    if (flag) {
      valuelon |= bit;
    } elonlselon {
      valuelon &= ~bit;
    }
    relonturn this;
  }

  protelonctelond final boolelonan gelontBit(int bit) {
    relonturn (valuelon & bit) != 0;
  }

  @Ovelonrridelon
  public String toString() {
    relonturn String.format("%x", valuelon);
  }
}
