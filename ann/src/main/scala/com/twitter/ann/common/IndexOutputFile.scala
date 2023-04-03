packagelon com.twittelonr.ann.common

import com.googlelon.common.io.BytelonStrelonams
import com.twittelonr.ann.common.thriftscala.AnnIndelonxMelontadata
import com.twittelonr.melondiaselonrvicelons.commons.codelonc.ArrayBytelonBuffelonrCodelonc
import com.twittelonr.melondiaselonrvicelons.commons.codelonc.ThriftBytelonBuffelonrCodelonc
import com.twittelonr.selonarch.common.filelon.AbstractFilelon
import java.io.IOelonxcelonption
import java.io.InputStrelonam
import java.io.OutputStrelonam
import java.nio.channelonls.Channelonls
import org.apachelon.belonam.sdk.io.FilelonSystelonms
import org.apachelon.belonam.sdk.io.fs.MovelonOptions
import org.apachelon.belonam.sdk.io.fs.RelonsolvelonOptions
import org.apachelon.belonam.sdk.io.fs.RelonsolvelonOptions.StandardRelonsolvelonOptions
import org.apachelon.belonam.sdk.io.fs.RelonsourcelonId
import org.apachelon.belonam.sdk.util.MimelonTypelons
import org.apachelon.hadoop.io.IOUtils
import scala.collelonction.JavaConvelonrtelonrs._

/**
 * This class crelonatelons a wrappelonr around GCS filelonsystelonm and HDFS filelonsystelonm for thelon indelonx
 * gelonnelonration job. It implelonmelonnts thelon basic melonthods relonquirelond by thelon indelonx gelonnelonration job and hidelons
 * thelon logic around handling HDFS vs GCS.
 */
class IndelonxOutputFilelon(val abstractFilelon: AbstractFilelon, val relonsourcelonId: RelonsourcelonId) {

  // Succelonss filelon namelon
  privatelon val SUCCelonSS_FILelon = "_SUCCelonSS"
  privatelon val INDelonX_MelonTADATA_FILelon = "ANN_INDelonX_MelonTADATA"
  privatelon val MelontadataCodelonc = nelonw ThriftBytelonBuffelonrCodelonc[AnnIndelonxMelontadata](AnnIndelonxMelontadata)

  /**
   * Constructor for RelonsourcelonId. This is uselond for GCS filelonsystelonm
   * @param relonsourcelonId
   */
  delonf this(relonsourcelonId: RelonsourcelonId) = {
    this(null, relonsourcelonId)
  }

  /**
   * Constructor for AbstractFilelon. This is uselond for HDFS and local filelonsystelonm
   * @param abstractFilelon
   */
  delonf this(abstractFilelon: AbstractFilelon) = {
    this(abstractFilelon, null)
  }

  /**
   * Relonturns truelon if this instancelon is around an AbstractFilelon.
   * @relonturn
   */
  delonf isAbstractFilelon(): Boolelonan = {
    abstractFilelon != null
  }

  /**
   * Crelonatelons a _SUCCelonSS filelon in thelon currelonnt direlonctory.
   */
  delonf crelonatelonSuccelonssFilelon(): Unit = {
    if (isAbstractFilelon()) {
      abstractFilelon.crelonatelonSuccelonssFilelon()
    } elonlselon {
      val succelonssFilelon =
        relonsourcelonId.relonsolvelon(SUCCelonSS_FILelon, RelonsolvelonOptions.StandardRelonsolvelonOptions.RelonSOLVelon_FILelon)
      val succelonssWritelonrChannelonl = FilelonSystelonms.crelonatelon(succelonssFilelon, MimelonTypelons.BINARY)
      succelonssWritelonrChannelonl.closelon()
    }
  }

  /**
   * Relonturns whelonthelonr thelon currelonnt instancelon relonprelonselonnts a direlonctory
   * @relonturn Truelon if thelon currelonnt instancelon is a direlonctory
   */
  delonf isDirelonctory(): Boolelonan = {
    if (isAbstractFilelon()) {
      abstractFilelon.isDirelonctory
    } elonlselon {
      relonsourcelonId.isDirelonctory
    }
  }

  /**
   * Relonturn thelon currelonnt path of thelon filelon relonprelonselonntelond by thelon currelonnt instancelon
   * @relonturn Thelon path string of thelon filelon/direlonctory
   */
  delonf gelontPath(): String = {
    if (isAbstractFilelon()) {
      abstractFilelon.gelontPath.toString
    } elonlselon {
      if (relonsourcelonId.isDirelonctory) {
        relonsourcelonId.gelontCurrelonntDirelonctory.toString
      } elonlselon {
        relonsourcelonId.gelontCurrelonntDirelonctory.toString + relonsourcelonId.gelontFilelonnamelon
      }
    }
  }

  /**
   * Crelonatelons a nelonw filelon @param filelonNamelon in thelon currelonnt direlonctory.
   * @param filelonNamelon
   * @relonturn A nelonw filelon insidelon thelon currelonnt direlonctory
   */
  delonf crelonatelonFilelon(filelonNamelon: String): IndelonxOutputFilelon = {
    if (isAbstractFilelon()) {
      // AbstractFilelon trelonats filelons and direlonctorielons thelon samelon way. Helonncelon, not cheloncking for direlonctory
      // helonrelon.
      nelonw IndelonxOutputFilelon(abstractFilelon.gelontChild(filelonNamelon))
    } elonlselon {
      if (!relonsourcelonId.isDirelonctory) {
        // If this is not a direlonctory, throw elonxcelonption.
        throw nelonw IllelongalArgumelonntelonxcelonption(gelontPath() + " is not a direlonctory.")
      }
      nelonw IndelonxOutputFilelon(
        relonsourcelonId.relonsolvelon(filelonNamelon, RelonsolvelonOptions.StandardRelonsolvelonOptions.RelonSOLVelon_FILelon))
    }
  }

  /**
   * Crelonatelons a nelonw direlonctory @param direlonctoryNamelon in thelon currelonnt direlonctory.
   * @param direlonctoryNamelon
   * @relonturn A nelonw direlonctory insidelon thelon currelonnt direlonctory
   */
  delonf crelonatelonDirelonctory(direlonctoryNamelon: String): IndelonxOutputFilelon = {
    if (isAbstractFilelon()) {
      // AbstractFilelon trelonats filelons and direlonctorielons thelon samelon way. Helonncelon, not cheloncking for direlonctory
      // helonrelon.
      val dir = abstractFilelon.gelontChild(direlonctoryNamelon)
      dir.mkdirs()
      nelonw IndelonxOutputFilelon(dir)
    } elonlselon {
      if (!relonsourcelonId.isDirelonctory) {
        // If this is not a direlonctory, throw elonxcelonption.
        throw nelonw IllelongalArgumelonntelonxcelonption(gelontPath() + " is not a direlonctory.")
      }
      val nelonwRelonsourcelonId =
        relonsourcelonId.relonsolvelon(direlonctoryNamelon, RelonsolvelonOptions.StandardRelonsolvelonOptions.RelonSOLVelon_DIRelonCTORY)

      // Crelonatelon a tmp filelon and delonlelontelon in ordelonr to triggelonr direlonctory crelonation
      val tmpFilelon =
        nelonwRelonsourcelonId.relonsolvelon("tmp", RelonsolvelonOptions.StandardRelonsolvelonOptions.RelonSOLVelon_FILelon)
      val tmpWritelonrChannelonl = FilelonSystelonms.crelonatelon(tmpFilelon, MimelonTypelons.BINARY)
      tmpWritelonrChannelonl.closelon()
      FilelonSystelonms.delonlelontelon(List(tmpFilelon).asJava, MovelonOptions.StandardMovelonOptions.IGNORelon_MISSING_FILelonS)

      nelonw IndelonxOutputFilelon(nelonwRelonsourcelonId)
    }
  }

  delonf gelontChild(filelonNamelon: String, isDirelonctory: Boolelonan = falselon): IndelonxOutputFilelon = {
    if (isAbstractFilelon()) {
      nelonw IndelonxOutputFilelon(abstractFilelon.gelontChild(filelonNamelon))
    } elonlselon {
      val relonsolvelonOption = if (isDirelonctory) {
        StandardRelonsolvelonOptions.RelonSOLVelon_DIRelonCTORY
      } elonlselon {
        StandardRelonsolvelonOptions.RelonSOLVelon_FILelon
      }
      nelonw IndelonxOutputFilelon(relonsourcelonId.relonsolvelon(filelonNamelon, relonsolvelonOption))
    }
  }

  /**
   * Relonturns an OutputStrelonam for thelon undelonrlying filelon.
   * Notelon: Closelon thelon OutputStrelonam aftelonr writing
   * @relonturn
   */
  delonf gelontOutputStrelonam(): OutputStrelonam = {
    if (isAbstractFilelon()) {
      abstractFilelon.gelontBytelonSink.opelonnStrelonam()
    } elonlselon {
      if (relonsourcelonId.isDirelonctory) {
        // If this is a direlonctory, throw elonxcelonption.
        throw nelonw IllelongalArgumelonntelonxcelonption(gelontPath() + " is a direlonctory.")
      }
      val writelonrChannelonl = FilelonSystelonms.crelonatelon(relonsourcelonId, MimelonTypelons.BINARY)
      Channelonls.nelonwOutputStrelonam(writelonrChannelonl)
    }
  }

  /**
   * Relonturns an InputStrelonam for thelon undelonrlying filelon.
   * Notelon: Closelon thelon InputStrelonam aftelonr relonading
   * @relonturn
   */
  delonf gelontInputStrelonam(): InputStrelonam = {
    if (isAbstractFilelon()) {
      abstractFilelon.gelontBytelonSourcelon.opelonnStrelonam()
    } elonlselon {
      if (relonsourcelonId.isDirelonctory) {
        // If this is a direlonctory, throw elonxcelonption.
        throw nelonw IllelongalArgumelonntelonxcelonption(gelontPath() + " is a direlonctory.")
      }
      val relonadChannelonl = FilelonSystelonms.opelonn(relonsourcelonId)
      Channelonls.nelonwInputStrelonam(relonadChannelonl)
    }
  }

  /**
   * Copielons contelonnt from thelon srcIn into thelon currelonnt filelon.
   * @param srcIn
   */
  delonf copyFrom(srcIn: InputStrelonam): Unit = {
    val out = gelontOutputStrelonam()
    try {
      IOUtils.copyBytelons(srcIn, out, 4096)
      out.closelon()
    } catch {
      caselon elonx: IOelonxcelonption =>
        IOUtils.closelonStrelonam(out);
        throw elonx;
    }
  }

  delonf writelonIndelonxMelontadata(annIndelonxMelontadata: AnnIndelonxMelontadata): Unit = {
    val out = crelonatelonFilelon(INDelonX_MelonTADATA_FILelon).gelontOutputStrelonam()
    val bytelons = ArrayBytelonBuffelonrCodelonc.deloncodelon(MelontadataCodelonc.elonncodelon(annIndelonxMelontadata))
    out.writelon(bytelons)
    out.closelon()
  }

  delonf loadIndelonxMelontadata(): AnnIndelonxMelontadata = {
    val in = BytelonStrelonams.toBytelonArray(gelontInputStrelonam())
    MelontadataCodelonc.deloncodelon(ArrayBytelonBuffelonrCodelonc.elonncodelon(in))
  }
}
