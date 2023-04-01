#include "utils.h"

#include <stdio.h>
#include <string.h>
#include <assert.h>

void split_fields(char *line, ...)
{
	va_list args;

	va_start(args, line);
	char* c = line;
	for (char** field = va_arg(args, char**); field != NULL; 
			    field = va_arg(args, char**)) {
		*field = c;
		
		for (; *c != '\t' && *c != '\n'; c++) {
			assert(*c != '\0');
		}

		*c = '\0';
		c += 1;
	}
	va_end(args);
}

int url_decode(char* buffer)
{
	char *c = buffer, *o = buffer;
	int size = 0;

	for (;*c != '\0';) {
		if (*c == '%') {
			int count = sscanf(c + 1, "%2hhx", o);
			if (count != 1) {
				size = -1;
				break;
			}
			
			c += 3;
		} else {
			*o = *c;
			c += 1;
		}

		o += 1;
		size += 1;
	}

	if (size > 0)
		*o = '\0';

	return size;
}

int parse_query_param(char** query, char** key, char** value)
{
	if (*query == NULL) {
		return -1;
	}

	*key = *query;
	*value = NULL;

	bool string_ended = false;
	int value_length = -1;
	char *c;

	for(c = *query; *c != '&'; c++) {
		if (*c == '\0') {
			string_ended = true;
			break;
		} else if (*value == NULL && *c == '=') {
			*c = '\0';
			*value = c + 1;
		} 
		
		if (*value != NULL) {
			value_length += 1;
		}
	}

	*c = '\0';

	if (!string_ended) {
		*query = c + 1;
	} else {
		*query = NULL;
	}

	return value_length;
}

