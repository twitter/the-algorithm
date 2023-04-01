#include <stdio.h>
#include <assert.h>
#include <stdbool.h>
#include <string.h>
#include <ctype.h>

#include <openssl/bio.h>
#include <openssl/evp.h>

#include "utils.h"

#define MAX_LINE 2048
#define KEY_SIZE 16

enum InputField {
    FIELD_USER=0,
    FIELD_SRPATH,
    FIELD_LANG,
    FIELD_CNAME,

    FIELD_COUNT
};

int main(int argc, char** argv)
{
    const char* secret;
    secret = getenv("TRACKING_SECRET");
    if (!secret) {
        fprintf(stderr, "TRACKING_SECRET not set\n");
        return 1;
    }
    
    char input_line[MAX_LINE];
    char plaintext[MAX_LINE];
    const EVP_CIPHER* cipher = EVP_aes_128_cbc();

    while (fgets(input_line, MAX_LINE, stdin) != NULL) {
        /* split the line into unique_id and query */
        char *unique_id, *query;
        split_fields(input_line, &unique_id, &query, NO_MORE_FIELDS);

        /* parse the query string to get the value we need */
        char *blob = NULL;

        char *key, *value;
        while (parse_query_param(&query, &key, &value) >= 0) {
            if (strcmp(key, "v") == 0) {
                blob = value;
                break;
            }
        }

        if (blob == NULL)
            continue;

        /* undo url encoding on the query string */
        int b64_size = url_decode(blob);
        if (b64_size < 0) 
            continue;

        /* split off the initialization vector from the actual ciphertext */
        char *initialization_vector, *ciphertext;

        initialization_vector = blob;
        initialization_vector[KEY_SIZE] = '\0';
        ciphertext = blob + 32;
        b64_size -= 32;

        /* base 64 decode and decrypt the ciphertext */
        BIO* bio = BIO_new_mem_buf(ciphertext, b64_size);
        if (bio == NULL) {
            fprintf(stderr, "Failed to allocate buffer for b64 ciphertext\n");
            return 1;
        }

        BIO* b64 = BIO_new(BIO_f_base64());
        if (b64 == NULL) {
            fprintf(stderr, "Failed to allocate base64 filter\n");
            return 1;
        }
        BIO_set_flags(b64, BIO_FLAGS_BASE64_NO_NL);
        bio = BIO_push(b64, bio);

        BIO* aes = BIO_new(BIO_f_cipher());
        if (aes == NULL) {
            fprintf(stderr, "Failed to allocate AES cipher\n");
            return 1;
        }
        BIO_set_cipher(
            aes,
            cipher,
            (unsigned char*)secret,
            (unsigned char*)initialization_vector,
            0 /* decryption */
        );
        bio = BIO_push(aes, bio);

        /* stream the output through the filters */
        int plaintext_length = BIO_read(bio, plaintext, b64_size);
        plaintext[plaintext_length] = '\0';

        if (!BIO_get_cipher_status(bio)) {
            BIO_free_all(bio);
            continue;
        }

        /* clean up */
        BIO_free_all(bio);

        /* check that the plaintext isn't garbage; if there are
         * non-ascii characters in it, it's likely bad */
        bool non_ascii_junk = false;
        for (unsigned char* c = (unsigned char*)plaintext; *c != '\0'; c++) {
            if (*c > 0x7F) {
                non_ascii_junk = true;
                break;
            }
        }

        if (non_ascii_junk) {
            continue;
        }

        /* write out the unique id since we don't need it for ourselves */
        fputs(unique_id, stdout);

        /* split the fields out of the plaintext */
        char* current_string = plaintext;
        int field_index = 0;

        for (int i = 0; i < plaintext_length; i++) {
            char *c = plaintext + i;
            if (*c != '|')
                continue;
            
            *c = '\0';

            switch (field_index) {
            case FIELD_USER:
                /* we don't use the user id; skip it */
                break;
            case FIELD_SRPATH:
                fputc('\t', stdout);
                fputs(current_string, stdout);

                fputc('\t', stdout);
                for (char* c2=current_string; *c2 != '\0'; c2++) {
                    if (*c2 == '-') {
                        *c2 = '\0';
                        break;
                    }
                }
                fputs(current_string, stdout);
                break;
            case FIELD_LANG:
                fputc('\t', stdout);
                for (char* c2=current_string; *c2 != '\0'; c2++) {
                    *c2 = tolower(*c2);
                }
                fputs(current_string, stdout);
                break;
            case FIELD_CNAME:
                assert(0!=1);
            }

            current_string = c + 1;
            field_index += 1;
        }

        if (field_index < FIELD_COUNT) {
            fputc('\t', stdout);
            fputs(current_string, stdout);
            field_index += 1;
        }

        for (; field_index < FIELD_COUNT; field_index++) 
            fputc('\t', stdout);

        /* all done! */
        fputc('\n', stdout);
    }

    return 0;
}

