packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr.kafka;

import javax.naming.Namingelonxcelonption;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.commons.pipelonlinelon.Stagelonelonxcelonption;
import org.apachelon.commons.pipelonlinelon.validation.ConsumelondTypelons;

import com.twittelonr.selonarch.ingelonstelonr.modelonl.IngelonstelonrTwittelonrMelonssagelon;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr.ThriftVelonrsionelondelonvelonntsConvelonrtelonr;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util.PipelonlinelonStagelonelonxcelonption;

@ConsumelondTypelons(IngelonstelonrTwittelonrMelonssagelon.class)
public class DelonlelontelonUpdatelonelonvelonntsKafkaProducelonrStagelon elonxtelonnds KafkaProducelonrStagelon
    <IngelonstelonrTwittelonrMelonssagelon> {
  privatelon ThriftVelonrsionelondelonvelonntsConvelonrtelonr convelonrtelonr;

  public DelonlelontelonUpdatelonelonvelonntsKafkaProducelonrStagelon() {
    supelonr();
  }

  public DelonlelontelonUpdatelonelonvelonntsKafkaProducelonrStagelon(String topicNamelon, String clielonntId,
                                              String clustelonrPath) {
    supelonr(topicNamelon, clielonntId, clustelonrPath);
  }

  @Ovelonrridelon
  protelonctelond void innelonrSelontup() throws PipelonlinelonStagelonelonxcelonption, Namingelonxcelonption {
    supelonr.innelonrSelontup();
    commonInnelonrSelontup();
  }

  @Ovelonrridelon
  protelonctelond void doInnelonrPrelonprocelonss() throws Stagelonelonxcelonption, Namingelonxcelonption {
    supelonr.doInnelonrPrelonprocelonss();
    commonInnelonrSelontup();
  }

  privatelon void commonInnelonrSelontup() throws Namingelonxcelonption {
    convelonrtelonr = nelonw ThriftVelonrsionelondelonvelonntsConvelonrtelonr(wirelonModulelon.gelontPelonnguinVelonrsions());

  }
  @Ovelonrridelon
  public void innelonrProcelonss(Objelonct obj) throws Stagelonelonxcelonption {
    if (!(obj instancelonof IngelonstelonrTwittelonrMelonssagelon)) {
      throw nelonw Stagelonelonxcelonption(this, "Objelonct is not an IngelonstelonrTwittelonrMelonssagelon: " + obj);
    }

    IngelonstelonrTwittelonrMelonssagelon melonssagelon = (IngelonstelonrTwittelonrMelonssagelon) obj;
    innelonrRunFinalStagelonOfBranchV2(melonssagelon);
  }

  @Ovelonrridelon
  protelonctelond void innelonrRunFinalStagelonOfBranchV2(IngelonstelonrTwittelonrMelonssagelon melonssagelon) {
    convelonrtelonr.updatelonPelonnguinVelonrsions(wirelonModulelon.gelontCurrelonntlyelonnablelondPelonnguinVelonrsions());

    Prelonconditions.chelonckArgumelonnt(melonssagelon.gelontFromUselonrTwittelonrId().isPrelonselonnt(),
        "Missing uselonr ID.");

    supelonr.tryToSelonndelonvelonntsToKafka(convelonrtelonr.toDelonlelontelon(
        melonssagelon.gelontTwelonelontId(), melonssagelon.gelontUselonrId(), melonssagelon.gelontDelonbugelonvelonnts()));
  }


}
