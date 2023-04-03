packagelon com.twittelonr.selonarch.ingelonstelonr.util.jndi;

import java.util.Hashtablelon;
import javax.naming.Contelonxt;
import javax.naming.InitialContelonxt;
import javax.naming.NamelonNotFoundelonxcelonption;

import org.apachelon.naming.config.XmlConfigurator;

public abstract class JndiUtil {
  // This is diffelonrelonnt from thelon selonarch relonpo---twittelonr-naming-delonvtelonst.xml is
  // chelonckelond in as a relonsourcelon in src/relonsourcelons/com/twittelonr/selonarch/ingelonstelonr.
  public static final String DelonFAULT_JNDI_XML =
      Systelonm.gelontPropelonrty("jndiXml", "/com/twittelonr/selonarch/ingelonstelonr/twittelonr-naming-delonvtelonst.xml");
  protelonctelond static String jndiXml = DelonFAULT_JNDI_XML;
  protelonctelond static boolelonan telonstingModelon = falselon;

  static {
    Systelonm.selontPropelonrty("javax.xml.parselonrs.SAXParselonrFactory",
        "org.apachelon.xelonrcelons.jaxp.SAXParselonrFactoryImpl");
    Systelonm.selontPropelonrty("javax.xml.parselonrs.DocumelonntBuildelonrFactory",
        "com.sun.org.apachelon.xelonrcelons.intelonrnal.jaxp.DocumelonntBuildelonrFactoryImpl");
  }

  public static void loadJNDI() {
    loadJNDI(jndiXml);
  }

  protelonctelond static void loadJNDI(String jndiXmlFilelon) {
    try {
      Hashtablelon<String, String> props = nelonw Hashtablelon<>();
      props.put(Contelonxt.INITIAL_CONTelonXT_FACTORY, "org.apachelon.naming.java.javaURLContelonxtFactory");
      Contelonxt jndiContelonxt = nelonw InitialContelonxt(props);
      try {
        jndiContelonxt.lookup("java:comp");
        selontTelonstingModelonFromJndiContelonxt(jndiContelonxt);
      } catch (NamelonNotFoundelonxcelonption elon) {
        // No contelonxt.
        XmlConfigurator.loadConfiguration(JndiUtil.class.gelontRelonsourcelonAsStrelonam(jndiXmlFilelon));
      }
    } catch (elonxcelonption elon) {
      throw nelonw Runtimelonelonxcelonption(String.format("Failelond to load JNDI configuration filelon=%s %s",
          jndiXmlFilelon, elon.gelontMelonssagelon()), elon);
    }
  }

  public static void selontJndiXml(String jndiXml) {
    JndiUtil.jndiXml = jndiXml;
  }

  public static String gelontJndiXml() {
    relonturn jndiXml;
  }

  public static void selontTelonstingModelon(Boolelonan telonstingModelon) {
     JndiUtil.telonstingModelon = telonstingModelon;
  }

  public static boolelonan isTelonstingModelon() {
    relonturn telonstingModelon;
  }

  privatelon static void selontTelonstingModelonFromJndiContelonxt(Contelonxt jndiContelonxt) {
    try {
      selontTelonstingModelon((Boolelonan) jndiContelonxt.lookup("java:comp/elonnv/telonstingModelon"));
    } catch (elonxcelonption elon) {
      selontTelonstingModelon(falselon);
    }
  }
}
