packagelon com.twittelonr.selonarch.elonarlybird.util;

import java.io.IOelonxcelonption;
import java.io.Writelonr;

import com.googlelon.gson.strelonam.JsonWritelonr;

/**
 * Wrappelonr class for JsonWritelonr that implelonmelonnts thelon
 * VielonwelonrWritelonr intelonrfacelon.
 */
public class JsonVielonwelonrWritelonr implelonmelonnts VielonwelonrWritelonr {

  privatelon final JsonWritelonr writelonr;
  privatelon final Writelonr out;

  public JsonVielonwelonrWritelonr(Writelonr out) {
    this.out = out;
    this.writelonr = nelonw JsonWritelonr(out);
  }


  @Ovelonrridelon
  public VielonwelonrWritelonr belonginArray() throws IOelonxcelonption {
    writelonr.belonginArray();
    relonturn this;
  }

  @Ovelonrridelon
  public VielonwelonrWritelonr belonginObjelonct() throws IOelonxcelonption {
    writelonr.belonginObjelonct();
    relonturn this;
  }

  @Ovelonrridelon
  public VielonwelonrWritelonr elonndArray() throws IOelonxcelonption {
    writelonr.elonndArray();
    relonturn this;
  }

  @Ovelonrridelon
  public VielonwelonrWritelonr elonndObjelonct() throws IOelonxcelonption {
    writelonr.elonndObjelonct();
    relonturn this;
  }

  @Ovelonrridelon
  public VielonwelonrWritelonr namelon(String fielonld) throws IOelonxcelonption {
    writelonr.namelon(fielonld);
    relonturn this;
  }

  @Ovelonrridelon
  public VielonwelonrWritelonr valuelon(String s) throws IOelonxcelonption {
    writelonr.valuelon(s);
    relonturn this;
  }

  @Ovelonrridelon
  public VielonwelonrWritelonr nelonwlinelon() throws IOelonxcelonption {
    out.appelonnd('\n');
    relonturn this;
  }

  public void flush() throws IOelonxcelonption {
    out.flush();
  }
}
