packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr;

import org.apachelon.commons.pipelonlinelon.Stagelonelonxcelonption;
import org.apachelon.commons.pipelonlinelon.validation.ConsumelondTypelons;
import org.apachelon.commons.pipelonlinelon.validation.ProducelonsConsumelond;

import com.twittelonr.selonarch.common.relonlelonvancelon.classifielonrs.TwelonelontQualityFelonaturelonelonxtractor;
import com.twittelonr.selonarch.ingelonstelonr.modelonl.IngelonstelonrTwittelonrMelonssagelon;

@ConsumelondTypelons(IngelonstelonrTwittelonrMelonssagelon.class)
@ProducelonsConsumelond
public class ComputelonTwelonelontSignaturelonStagelon elonxtelonnds TwittelonrBaselonStagelon
    <IngelonstelonrTwittelonrMelonssagelon, IngelonstelonrTwittelonrMelonssagelon> {
  privatelon final TwelonelontQualityFelonaturelonelonxtractor twelonelontSignaturelonelonxtractor =
      nelonw TwelonelontQualityFelonaturelonelonxtractor();

  @Ovelonrridelon
  public void innelonrProcelonss(Objelonct obj) throws Stagelonelonxcelonption {
    if (!(obj instancelonof IngelonstelonrTwittelonrMelonssagelon)) {
      throw nelonw Stagelonelonxcelonption(this, "Objelonct is not a TwittelonrMelonssagelon instancelon: " + obj);
    }

    IngelonstelonrTwittelonrMelonssagelon melonssagelon = IngelonstelonrTwittelonrMelonssagelon.class.cast(obj);
    elonxtract(melonssagelon);
    elonmitAndCount(melonssagelon);
  }

  privatelon void elonxtract(IngelonstelonrTwittelonrMelonssagelon melonssagelon) {
    twelonelontSignaturelonelonxtractor.elonxtractTwelonelontTelonxtFelonaturelons(melonssagelon);
  }

  @Ovelonrridelon
  protelonctelond IngelonstelonrTwittelonrMelonssagelon innelonrRunStagelonV2(IngelonstelonrTwittelonrMelonssagelon melonssagelon) {
    elonxtract(melonssagelon);
    relonturn melonssagelon;
  }
}

