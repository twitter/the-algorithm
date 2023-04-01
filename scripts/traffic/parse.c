#include <stdio.h>
#include <string.h>
#include <assert.h>
#include <stdint.h>
#include <inttypes.h>

#include <arpa/inet.h>

#include <pcre.h>
#include <zlib.h>

#define MAX_LINE 2048 

/******************************
 * this regular expression has the following capture groups in it (in order):
 *  - ip
 *  - path
 *  - query
 *  - user agent
 ******************************/
#define RE  "(?:[0-9.]+,\\ )*([0-9.]+)"\
            "[^\"]+"\
            "\"GET\\s([^\\s?]+)\\?([^\\s]+)\\s[^\"]+\""\
            "\\s([^\\s]+)[^\"]+"\
            "\"[^\"]+\""\
            "[^\"]+"\
            "\"([^\"]+)\""

#define GROUP_IP    1
#define GROUP_PATH  2
#define GROUP_QUERY 3
#define GROUP_CODE  4
#define GROUP_UA    5

int main(int argc, char** argv) 
{
    /* compile the pattern */
    const char* error;
    int error_offset;
    pcre *re = pcre_compile(RE, 0, &error, &error_offset, NULL);
    if (re == NULL) {
        fprintf(
            stderr, 
            "character %d: failed to compile regex: %s\n", 
            error_offset, 
            error
        );

        return 1;
    }

    /* study it to speed it up */
    pcre_extra *extra = pcre_study(re, 0, &error);

    /* allocate enough space for the capturing groups */
    int group_count;
    pcre_fullinfo(re, extra, PCRE_INFO_CAPTURECOUNT, &group_count);
    int match_vector_size = (group_count + 1) * 3;
    int *matches = malloc(sizeof(int) * match_vector_size);
    if (matches == NULL) {
        fprintf(stderr, "Couldn't allocate memory for regex groups!\n");
        return 1;
    }

    /* iterate through the input */
    char input_line[MAX_LINE];
    while (fgets(input_line, MAX_LINE, stdin)) {
        int length = strlen(input_line);

        /* run the regular expression against the line */
        int match_result = pcre_exec(
            re, 
            extra, 
            input_line, 
            length, 
            0, 
            0, 
            matches, 
            match_vector_size
        );

        /* bail out if the line didn't match */
        if (match_result < 0) {
            continue;
        }

        /* iterate through the groups */
        /* NOTE: the crc function uses int32_t instead of uint32_t
         * and has the funky (2^31 - crc) bit of math for backwards
         * compatibility with the old python code. fix this when 
         * such compatibility is no longer necessary. */
        uint32_t address = 0;
        int32_t crc;
        uint64_t unique_id;

        for (int i = 1; i < match_result; i++) {
            int start_position = matches[i * 2];
            int end_position = matches[i * 2 + 1];
            int substr_length = end_position - start_position;

            switch (i) {
            case GROUP_UA:
                crc = crc32(0L, Z_NULL, 0);
                crc = crc32(crc, (unsigned char*)input_line + start_position, 
                            substr_length);
                unique_id = (((uint64_t)address << 32) & 0xffffffff00000000) | 
                            (2147483648 - crc);
                fprintf(stdout, "%" PRIu64, unique_id);
                break;
            case GROUP_IP:
                /* parse and store the ip so we can use it in GROUP_UA
                 * to calculate the unique id */
                input_line[end_position] = 0;
                address = inet_addr(input_line + start_position);

                /* fall through so it gets written out as well */
            case GROUP_PATH:
            case GROUP_CODE:
            case GROUP_QUERY:
                /* write them out verbatim */
                (void)fwrite(
                    input_line + start_position,
                    sizeof(char),
                    substr_length,
                    stdout
                );
                break;
            }

            /* tab-delimit the data */
            fputc('\t', stdout);
        }

        fputc('\n', stdout);
    }

    return 0;
}

