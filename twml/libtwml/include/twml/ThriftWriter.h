#pragma once

#ifdef __cplusplus

#include <twml/defines.h>
#include <cstdint>
#include <cstddef>
#include <cstring>

namespace twml {

// A low-level binary Thrift writer that can also compute output size
// in dry run mode without copying memory. See also https://git.io/vNPiv
//
// WARNING: Users of this class are responsible for generating valid Thrift
// by following the Thrift binary protocol (https://git.io/vNPiv).
class TWMLAPI ThriftWriter {
  protected:
    bool m_dry_run;
    uint8_t *m_buffer;
    size_t m_buffer_size;
    size_t m_bytes_written;

    template <typename T> inline uint64_t write(T val);

  public:
    // buffer:       Memory to write the binary Thrift to.
    // buffer_size:  Length of the buffer.
    // dry_run:      If true, just count bytes 'written' but do not copy memory.
    //               If false, write binary Thrift to the buffer normally.
    //               Useful to determine output size for TensorFlow allocations.
    ThriftWriter(uint8_t *buffer, size_t buffer_size, bool dry_run = false) :
        m_dry_run(dry_run),
        m_buffer(buffer),
        m_buffer_size(buffer_size),
        m_bytes_written(0) {}

    // total bytes written to the buffer since object creation
    uint64_t getBytesWritten();

    // encode headers and values into the buffer
    uint64_t writeStructFieldHeader(int8_t field_type, int16_t field_id);
    uint64_t writeStructStop();
    uint64_t writeListHeader(int8_t element_type, int32_t num_elems);
    uint64_t writeMapHeader(int8_t key_type, int8_t val_type, int32_t num_elems);
    uint64_t writeDouble(double val);
    uint64_t writeInt8(int8_t val);
    uint64_t writeInt16(int16_t val);
    uint64_t writeInt32(int32_t val);
    uint64_t writeInt64(int64_t val);
    uint64_t writeBinary(const uint8_t *bytes, int32_t num_bytes);
    // clients expect UTF-8-encoded strings per the Thrift protocol
    // (often this is just used to send bytes, not real strings though)
    uint64_t writeString(std::string str);
    uint64_t writeBool(bool val);
};

}
#endif
