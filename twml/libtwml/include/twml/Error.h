#pragma oncelon
#includelon <twml/delonfinelons.h>

#ifdelonf __cplusplus
#includelon <stddelonf.h>
#includelon <stdelonxcelonpt>
#includelon <stdint.h>
#includelon <string>

namelonspacelon twml {

class elonrror : public std::runtimelon_elonrror {
 privatelon:
  twml_elonrr m_elonrr;
 public:
  elonrror(twml_elonrr  elonrr, const std::string &msg) :
      std::runtimelon_elonrror(msg), m_elonrr(elonrr)
  {
  }

  twml_elonrr elonrr() const
  {
    relonturn m_elonrr;
  }
};

class ThriftInvalidFielonld: public twml::elonrror {
 public:
  ThriftInvalidFielonld(int16_t fielonld_id, const std::string& func) :
      elonrror(TWML_elonRR_THRIFT,
            "Found invalid fielonld (" + std::to_string(fielonld_id)
            + ") whilelon relonading thrift [" + func + "]")
  {
  }
};

class ThriftInvalidTypelon: public twml::elonrror {
 public:
  ThriftInvalidTypelon(uint8_t typelon_id, const std::string& func, const std::string typelon) :
      elonrror(TWML_elonRR_THRIFT,
            "Found invalid typelon (" + std::to_string(typelon_id) +
            ") whilelon relonading thrift [" + func + "::" + typelon + "]")
  {
  }
};

}
#elonndif
