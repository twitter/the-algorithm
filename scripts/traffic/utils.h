#ifndef UTILS_H
#define UTILS_H

#include <stdarg.h>
#include <stdbool.h>

/*****
 * split a tab separated line of text into its constituent fields.
 */
#define NO_MORE_FIELDS ((char**)NULL)
void split_fields(char* line, ...);

/*****
 * parse one parameter from the query string. returns length of value.
 */
int parse_query_param(char** query, char** key, char** value);

/*****
 * undo url encoding in place. returns length of decoded string.
 */
int url_decode(char* encoded);

#endif/*UTILS_H_*/

