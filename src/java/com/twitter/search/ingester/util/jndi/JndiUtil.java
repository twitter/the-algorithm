package com.tw ter.search. ngester.ut l.jnd ;

 mport java.ut l.Hashtable;
 mport javax.nam ng.Context;
 mport javax.nam ng. n  alContext;
 mport javax.nam ng.Na NotFoundExcept on;

 mport org.apac .nam ng.conf g.XmlConf gurator;

publ c abstract class Jnd Ut l {
  // T   s d fferent from t  search repo---tw ter-nam ng-devtest.xml  s
  // c cked  n as a res ce  n src/res ces/com/tw ter/search/ ngester.
  publ c stat c f nal Str ng DEFAULT_JND _XML =
      System.getProperty("jnd Xml", "/com/tw ter/search/ ngester/tw ter-nam ng-devtest.xml");
  protected stat c Str ng jnd Xml = DEFAULT_JND _XML;
  protected stat c boolean test ngMode = false;

  stat c {
    System.setProperty("javax.xml.parsers.SAXParserFactory",
        "org.apac .xerces.jaxp.SAXParserFactory mpl");
    System.setProperty("javax.xml.parsers.Docu ntBu lderFactory",
        "com.sun.org.apac .xerces. nternal.jaxp.Docu ntBu lderFactory mpl");
  }

  publ c stat c vo d loadJND () {
    loadJND (jnd Xml);
  }

  protected stat c vo d loadJND (Str ng jnd XmlF le) {
    try {
      Hashtable<Str ng, Str ng> props = new Hashtable<>();
      props.put(Context. N T AL_CONTEXT_FACTORY, "org.apac .nam ng.java.javaURLContextFactory");
      Context jnd Context = new  n  alContext(props);
      try {
        jnd Context.lookup("java:comp");
        setTest ngModeFromJnd Context(jnd Context);
      } catch (Na NotFoundExcept on e) {
        // No context.
        XmlConf gurator.loadConf gurat on(Jnd Ut l.class.getRes ceAsStream(jnd XmlF le));
      }
    } catch (Except on e) {
      throw new Runt  Except on(Str ng.format("Fa led to load JND  conf gurat on f le=%s %s",
          jnd XmlF le, e.get ssage()), e);
    }
  }

  publ c stat c vo d setJnd Xml(Str ng jnd Xml) {
    Jnd Ut l.jnd Xml = jnd Xml;
  }

  publ c stat c Str ng getJnd Xml() {
    return jnd Xml;
  }

  publ c stat c vo d setTest ngMode(Boolean test ngMode) {
     Jnd Ut l.test ngMode = test ngMode;
  }

  publ c stat c boolean  sTest ngMode() {
    return test ngMode;
  }

  pr vate stat c vo d setTest ngModeFromJnd Context(Context jnd Context) {
    try {
      setTest ngMode((Boolean) jnd Context.lookup("java:comp/env/test ngMode"));
    } catch (Except on e) {
      setTest ngMode(false);
    }
  }
}
