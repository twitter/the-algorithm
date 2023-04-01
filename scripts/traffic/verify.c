#include <stdio.h>
#include <assert.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>

#include <openssl/sha.h>
#include <openssl/hmac.h>

#include "utils.h"

#define MAX_LINE 2048
#define SHA1_HASH_LENGTH 40

int main(int argc, char** argv)
{
    const char* secret;
    secret = getenv("TRACKING_SECRET");
    if (!secret) {
        fprintf(stderr, "TRACKING_SECRET not set\n");
        return 1;
    }

    char input_line[MAX_LINE];
    unsigned int hash_length = SHA_DIGEST_LENGTH;
    unsigned char input_hash[hash_length];
    unsigned char expected_hash[hash_length];
    int secret_length = strlen(secret);

    while (fgets(input_line, MAX_LINE, stdin) != NULL) {
        /* get the fields */
        char *ip, *path, *query, *response_code, *unique_id;

        split_fields(
            input_line, 
            &ip, 
            &path, 
            &query, 
            &response_code, 
            &unique_id, 
            NO_MORE_FIELDS
        );

        /* in the query string, grab the fields we want to verify */
        char *id = NULL;
        char *hash = NULL;
        char *url = NULL;

        char *key, *value;
        while (parse_query_param(&query, &key, &value) >= 0) {
            if (strcmp(key, "id") == 0) {
                id = value;
            } else if (strcmp(key, "hash") == 0) {
                hash = value;
            } else if (strcmp(key, "url") == 0) {
                url = value;
            }
        }

        if (id == NULL || hash == NULL)
            continue;

        /* decode the params */
        int id_length = url_decode(id);
        if (id_length < 0)
            continue;

        if (url_decode(hash) != SHA1_HASH_LENGTH)
            continue;

        int url_length = 0;
        if (url != NULL) {
            url_length = url_decode(url);
            if (url_length < 0)
                continue;
        }

        /* validation:
            * for clicks just check the response code--validation was done in
              the click redirect app
            * for impression pixels check the hash
        */
        if (strcmp("/click", path) == 0) {
            if (strcmp(response_code, "302") != 0) {
                continue;
            }
        } else {
            /* turn the expected hash into bytes */
            bool bad_hash = false;
            for (int i = 0; i < hash_length; i++) {
                int count = sscanf(&hash[i*2], "%2hhx", &input_hash[i]);
                if (count != 1) {
                    bad_hash = true;
                    break;
                }
            }

            if (bad_hash)
                continue;

            /* generate the expected hash */
            HMAC_CTX ctx;

            // NOTE: EMR has openssl <1.0, so these HMAC methods don't return
            // error codes -- see https://www.openssl.org/docs/crypto/hmac.html
            HMAC_Init(&ctx, secret, secret_length, EVP_sha1());
            HMAC_Update(&ctx, id, id_length);
            HMAC_Final(&ctx, expected_hash, &hash_length);

            /* check that the hashes match */
            if (memcmp(input_hash, expected_hash, SHA_DIGEST_LENGTH) != 0)
                continue;
        }

        /* split out the fullname and subreddit if necessary */
        char *fullname = id;
        char *subreddit = NULL;

        for (char *c = id; *c != '\0'; c++) {
            if (*c == '-') {
                subreddit = c + 1;
                *c = '\0';
                break;
            }
        }

        /* output stuff! */
        fputs(unique_id, stdout);
        fputc('\t', stdout);

        fputs(path, stdout);
        fputc('\t', stdout);

        fputs(fullname, stdout);
        fputc('\t', stdout);

        if (subreddit != NULL) {
            fputs(subreddit, stdout);
        }

        fputc('\n', stdout);
    }
}
