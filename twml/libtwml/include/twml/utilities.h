#pragma once

#include <cstdint>
#include <cstring>

namespace twml {

    inline int64_t mixDiscreteIdAndValue(int64_t key, int64_t value) {
        key ^= ((17LL + value) * 2654435761LL);
        return key;
    }

    inline int64_t mixStringIdAndValue(int64_t key, int32_t str_len, const uint8_t* str) {
        const uint8_t* end = str + str_len;
        while (str != end) {
            key = (key * 31) + *str++;
        }
        return key;
    }

} // namespace twml
