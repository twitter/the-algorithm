#ifndef LIBMIO0_H_
#define LIBMIO0_H_

// defines

#define MIO0_HEADER_LENGTH 16

// typedefs

typedef struct
{
   unsigned int dest_size;
   unsigned int comp_offset;
   unsigned int uncomp_offset;
} mio0_header_t;

// function prototypes

// decode MIO0 header
// returns 1 if valid header, 0 otherwise
int mio0_decode_header(const unsigned char *buf, mio0_header_t *head);

// encode MIO0 header from struct
void mio0_encode_header(unsigned char *buf, const mio0_header_t *head);

// decode MIO0 data in memory
// in: buffer containing MIO0 data
// out: buffer for output data
// end: output offset of the last byte decoded from in (set to NULL if unwanted)
// returns bytes extracted to 'out' or negative value on failure
int mio0_decode(const unsigned char *in, unsigned char *out, unsigned int *end);

// encode MIO0 data in memory
// in: buffer containing raw data
// out: buffer for MIO0 data
// returns size of compressed data in 'out' including MIO0 header
int mio0_encode(const unsigned char *in, unsigned int length, unsigned char *out);

// decode an entire MIO0 block at an offset from file to output file
// in_file: input filename
// offset: offset to start decoding from in_file
// out_file: output filename
int mio0_decode_file(const char *in_file, unsigned long offset, const char *out_file);

// encode an entire file
// in_file: input filename containing raw data to be encoded
// out_file: output filename to write MIO0 compressed data to
int mio0_encode_file(const char *in_file, const char *out_file);

#endif // LIBMIO0_H_
