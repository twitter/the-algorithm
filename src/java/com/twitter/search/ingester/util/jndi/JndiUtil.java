package com.twitter.search.ingester.util.jndi;

import java.util.Hashtable;
import javatwitter.naming.Contetwittert;
import javatwitter.naming.InitialContetwittert;
import javatwitter.naming.NameNotFoundEtwitterception;

import org.apache.naming.config.twittermlConfigurator;

public abstract class JndiUtil {
  // This is different from the search repo---twitter-naming-devtest.twitterml is
  // checked in as a resource in src/resources/com/twitter/search/ingester.
  public static final String DEFAULT_JNDI_twitterML =
      System.getProperty("jnditwitterml", "/com/twitter/search/ingester/twitter-naming-devtest.twitterml");
  protected static String jnditwitterml = DEFAULT_JNDI_twitterML;
  protected static boolean testingMode = false;

  static {
    System.setProperty("javatwitter.twitterml.parsers.SAtwitterParserFactory",
        "org.apache.twittererces.jatwitterp.SAtwitterParserFactoryImpl");
    System.setProperty("javatwitter.twitterml.parsers.DocumentBuilderFactory",
        "com.sun.org.apache.twittererces.internal.jatwitterp.DocumentBuilderFactoryImpl");
  }

  public static void loadJNDI() {
    loadJNDI(jnditwitterml);
  }

  protected static void loadJNDI(String jnditwittermlFile) {
    try {
      Hashtable<String, String> props = new Hashtable<>();
      props.put(Contetwittert.INITIAL_CONTEtwitterT_FACTORY, "org.apache.naming.java.javaURLContetwittertFactory");
      Contetwittert jndiContetwittert = new InitialContetwittert(props);
      try {
        jndiContetwittert.lookup("java:comp");
        setTestingModeFromJndiContetwittert(jndiContetwittert);
      } catch (NameNotFoundEtwitterception e) {
        // No contetwittert.
        twittermlConfigurator.loadConfiguration(JndiUtil.class.getResourceAsStream(jnditwittermlFile));
      }
    } catch (Etwitterception e) {
      throw new RuntimeEtwitterception(String.format("Failed to load JNDI configuration file=%s %s",
          jnditwittermlFile, e.getMessage()), e);
    }
  }

  public static void setJnditwitterml(String jnditwitterml) {
    JndiUtil.jnditwitterml = jnditwitterml;
  }

  public static String getJnditwitterml() {
    return jnditwitterml;
  }

  public static void setTestingMode(Boolean testingMode) {
     JndiUtil.testingMode = testingMode;
  }

  public static boolean isTestingMode() {
    return testingMode;
  }

  private static void setTestingModeFromJndiContetwittert(Contetwittert jndiContetwittert) {
    try {
      setTestingMode((Boolean) jndiContetwittert.lookup("java:comp/env/testingMode"));
    } catch (Etwitterception e) {
      setTestingMode(false);
    }
  }
}
