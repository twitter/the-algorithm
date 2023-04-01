#pragma once
#include <twml/defines.h>

#ifdef __cplusplus
#include <stddef.h>
#include <stdexcept>
#include <stdint.h>
#include <string>

namespace twml {

class Error : public std::runtime_error {
 private:
  twml_err m_err;
 public:
  Error(twml_err  err, const std::string &msg) :
      std::runtime_error(msg), m_err(err)
  {
  }

  twml_err err() const
  {
    return m_err;
  }
};

class ThriftInvalidField: public twml::Error {
 public:
  ThriftInvalidField(int16_t field_id, const std::string& func) :
      Error(TWML_ERR_THRIFT,
            "Found invalid field (" + std::to_string(field_id)
            + ") while reading thrift [" + func + "]")
  {
  }
};

class ThriftInvalidType: public twml::Error {
 public:
  ThriftInvalidType(uint8_t type_id, const std::string& func, const std::string type) :
      Error(TWML_ERR_THRIFT,
            "Found invalid type (" + std::to_string(type_id) +
            ") while reading thrift [" + func + "::" + type + "]")
  {
  }
};

}
#endif
