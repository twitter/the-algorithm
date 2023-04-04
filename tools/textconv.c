#include <ctype.h>
#include <errno.h>
#include <stdarg.h>
#include <stdint.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "hashtable.h"
#include "utf8.h"

#define ARRAY_COUNT(arr) (sizeof(arr) / sizeof(arr[0]))

struct CharmapEntry
{
    uint32_t unicode[3];
    int length; // length of the unicode array. TODO: use dynamic memory allocation
    int bytesCount;
    uint8_t bytes[2]; // bytes to convert unicode array to, (e.g. 'A' = 0x0A)
};

static struct HashTable *charmap;

static void fatal_error(const char *msgfmt, ...)
{
    va_list args;

    fputs("error: ", stderr);

    va_start(args, msgfmt);
    vfprintf(stderr, msgfmt, args);
    va_end(args);

    fputc('\n', stderr);

    exit(1);
}

static void parse_error(const char *filename, int lineNum, const char *msgfmt, ...)
{
    va_list args;

    fprintf(stderr, "%s: line %i: ", filename, lineNum);

    va_start(args, msgfmt);
    vfprintf(stderr, msgfmt, args);
    va_end(args);

    fputc('\n', stderr);

    exit(1);
}

// Reads the whole file and returns a null-terminated buffer with its contents
void *read_text_file(const char *filename)
{
    if (strcmp(filename, "-") != 0)
    {
        FILE *file = fopen(filename, "rb");
        uint8_t *buffer;
        size_t size;

        if (file == NULL)
            fatal_error("failed to open file '%s' for reading: %s", filename, strerror(errno));

        // get size
        fseek(file, 0, SEEK_END);
        size = ftell(file);

        // allocate buffer
        buffer = malloc(size + 1);
        if (buffer == NULL)
            fatal_error("could not allocate buffer of size %u", (uint32_t)(size + 1));

        // read file
        fseek(file, 0, SEEK_SET);
        if (fread(buffer, size, 1, file) != 1)
            fatal_error("error reading from file '%s': %s", filename, strerror(errno));

        // null-terminate the buffer
        buffer[size] = 0;

        fclose(file);

        return buffer;
    }
    else
    {
        size_t size = 0;
        size_t capacity = 1024;
        uint8_t *buffer = malloc(capacity + 1);

        if (buffer == NULL)
            fatal_error("could not allocate buffer of size %u", (uint32_t)(capacity + 1));

        for (;;)
        {
            size += fread(buffer + size, 1, capacity - size, stdin);
            if (size == capacity)
            {
                capacity *= 2;
                buffer = realloc(buffer, capacity + 1);
                if (buffer == NULL)
                    fatal_error("could not allocate buffer of size %u", (uint32_t)(capacity + 1));
            }
            else if (feof(stdin))
            {
                break;
            }
            else
            {
                fatal_error("error reading from stdin: %s", strerror(errno));
            }
        }

        // null-terminate the buffer
        buffer[size] = 0;
        return buffer;
    }
}

static char *skip_whitespace(char *str)
{
    while (isspace(*str))
        str++;
    return str;
}

// null terminates the current line and returns a pointer to the next line
static char *line_split(char *str)
{
    while (*str != '\n')
    {
        if (*str == 0)
            return str;  // end of string
        str++;
    }
    *str = 0;  // terminate line
    return str + 1;
}

static char *parse_number(const char *str, unsigned int *num)
{
    char *endptr;
    unsigned int n = strtol(str, &endptr, 0);

    *num = n;
    if (endptr > str)
        return endptr;
    else
        return NULL;
}

static int is_identifier_char(char c)
{
    return isalnum(c) || c == '_';
}

static int get_escape_char(int c)
{
    const uint8_t escapeTable[] =
    {
        ['a'] = '\a',
        ['b'] = '\b',
        ['f'] = '\f',
        ['n'] = '\n',
        ['r'] = '\r',
        ['t'] = '\t',
        ['v'] = '\v',
        ['\\'] = '\\',
        ['\''] = '\'',
        ['"'] = '"',
    };

    if ((unsigned int)c < ARRAY_COUNT(escapeTable) && escapeTable[c] != 0)
        return escapeTable[c];
    else
        return 0;
}

static void read_charmap(const char *filename)
{
    char *filedata = read_text_file(filename);
    char *line = filedata;
    int lineNum = 1;

    while (line[0] != 0)
    {
        char *nextLine = line_split(line);

        struct CharmapEntry entry;

        line = skip_whitespace(line);
        if (line[0] != 0 && line[0] != '#')  // ignore empty lines and comments
        {
            int len = 0;
            /* Read Character */

            // opening quote
            if (*line != '\'')
                parse_error(filename, lineNum, "expected '");
            line++;

            // perform analysis of charmap entry, we are in the quote
            while(1)
            {
                if(*line == '\'')
                {
                    line++;
                    break;
                }
                else if(len == ARRAY_COUNT(entry.unicode))
                {
                    // TODO: Use dynamic memory allocation so this is unnecessary.
                    parse_error(filename, lineNum, "string limit exceeded");
                }
                else if (*line == '\\')
                {
                    line++; // advance to get the character being escaped
                    if (*line == '\r')
                        line++;
                    if (*line == '\n')
                    {
                        // Backslash at end of line is ignored
                        continue;
                    }
                    entry.unicode[len] = get_escape_char(*line);
                    if (entry.unicode[len] == 0)
                        parse_error(filename, lineNum, "unknown escape sequence \\%c", *line);
                    line++; // increment again to get past the escape sequence.
                }
                else
                {
                    line = utf8_decode(line, &entry.unicode[len]);
                    if (line == NULL)
                        parse_error(filename, lineNum, "invalid UTF8");
                }
                len++;
            }
            entry.length = len;

            // equals sign
            line = skip_whitespace(line);
            if (*line != '=')
                parse_error(filename, lineNum, "expected = after character \\%c", *line);
            line++;

            entry.bytesCount = 0;

            // value
            while (1)
            {
                uint32_t value;

                if (entry.bytesCount >= 2)
                    parse_error(filename, lineNum, "more than 2 values specified");

                line = skip_whitespace(line);

                line = parse_number(line, &value);
                if (line == NULL)
                    parse_error(filename, lineNum, "expected number after =");
                if (value > 0xFF)
                    parse_error(filename, lineNum, "0x%X is larger than 1 byte", value);

                entry.bytes[entry.bytesCount] = value;
                entry.bytesCount++;

                line = skip_whitespace(line);
                if (*line == 0)
                    break;
                if (*line != ',')
                    parse_error(filename, lineNum, "junk at end of line");
                line++;
            }

            if (hashtable_query(charmap, &entry) != NULL)
                parse_error(filename, lineNum, "entry for character already exists");
            hashtable_insert(charmap, &entry);
        }

        line = nextLine;
        lineNum++;
    }

    free(filedata);
}

static int count_line_num(const char *start, const char *pos)
{
    const char *c;
    int lineNum = 1;

    for (c = start; c < pos; c++)
    {
        if (*c == '\n')
            lineNum++;
    }
    return lineNum;
}

static char *convert_string(char *pos, FILE *fout, const char *inputFileName, char *start, int uncompressed)
{
    int hasString = 0;

    while (1)
    {
        pos = skip_whitespace(pos);
        if (*pos == ')')
        {
            if (hasString)
                break;
            else
                parse_error(inputFileName, count_line_num(start, pos), "expected quoted string after '_('");
        }
        else if (*pos != '"')
            parse_error(inputFileName, count_line_num(start, pos), "unexpected character '%c'", *pos);
        pos++;

        hasString = 1;

        // convert quoted string
        while (*pos != '"')
        {
            struct CharmapEntry input;
            struct CharmapEntry *last_valid_entry = NULL;
            struct CharmapEntry *entry;
            int i, c;
            int length = 0;
            char* last_valid_pos = NULL;

            // safely erase the unicode area before use
            memset(input.unicode, 0, sizeof (input.unicode));
            input.length = 0;

            // Find a charmap entry of longest length possible starting from this position
            while (*pos != '"')
            {
                if ((uncompressed && length == 1) || length == ARRAY_COUNT(entry->unicode))
                {
                    // Stop searching after length 3; we only support strings of lengths up
                    // to that right now. Unless uncompressed is set, in which we ignore multi
                    // texts by discarding entries longer than 1.
                    break;
                }

                if (*pos == 0)
                    parse_error(inputFileName, count_line_num(start, pos), "EOF in string literal");
                if (*pos == '\\')
                {
                    pos++;
                    c = get_escape_char(*pos);
                    if (c == 0)
                        parse_error(inputFileName, count_line_num(start, pos), "unknown escape sequence \\%c", *pos);
                    input.unicode[length] = c;
                    pos++;
                }
                else
                {
                    pos = utf8_decode(pos, &input.unicode[length]);
                    if (pos == NULL)
                        parse_error(inputFileName, count_line_num(start, pos), "invalid unicode encountered in file");
                }
                length++;
                input.length = length;

                entry = hashtable_query(charmap, &input);
                if (entry != NULL)
                {
                    last_valid_entry = entry;
                    last_valid_pos = pos;
                }
            }

            entry = last_valid_entry;
            pos = last_valid_pos;
            if (entry == NULL)
                parse_error(inputFileName, count_line_num(start, pos), "no charmap entry for U+%X", input.unicode[0]);
            for (i = 0; i < entry->bytesCount; i++)
                fprintf(fout, "0x%02X,", entry->bytes[i]);
        }
        pos++;  // skip over closing '"'
    }
    pos++;  // skip over closing ')'
    fputs("0xFF", fout);
    return pos;
}

static void convert_file(const char *infilename, const char *outfilename)
{
    char *in = read_text_file(infilename);
    FILE *fout = strcmp(outfilename, "-") != 0 ? fopen(outfilename, "wb") : stdout;

    if (fout == NULL)
        fatal_error("failed to open file '%s' for writing: %s", strerror(errno));

    char *start = in;
    char *end = in;
    char *pos = in;

    while (1)
    {
        if (*pos == 0)  // end of file
            goto eof;

        // check for comment
        if (*pos == '/')
        {
            pos++;
            // skip over // comment
            if (*pos == '/')
            {
                pos++;
                // skip over next newline
                while (*pos != '\n')
                {
                    if (*pos == 0)
                        goto eof;
                    pos++;
                }
                pos++;
            }
            // skip over /* */ comment
            else if (*pos == '*')
            {
                pos++;
                while (*pos != '*' && pos[1] != '/')
                {
                    if (*pos == 0)
                        goto eof;
                    pos++;
                }
                pos += 2;
            }
        }
        // skip over normal string literal
        else if (*pos == '"')
        {
            pos++;
            while (*pos != '"')
            {
                if (*pos == 0)
                    goto eof;
                if (*pos == '\\')
                    pos++;
                pos++;
            }
            pos++;
        }
        // check for _( sequence
        else if ((*pos == '_') && (pos == in || !is_identifier_char(pos[-1])))
        {
            int uncompressed = 0;
            end = pos;
            pos++;
            if (*pos == '_') // an extra _ signifies uncompressed strings. Enable uncompressed flag
            {
                pos++;
                uncompressed = 1;
            }
            if (*pos == '(')
            {
                pos++;
                fwrite(start, end - start, 1, fout);
                pos = convert_string(pos, fout, infilename, in, uncompressed);
                start = pos;
            }
        }
        else
        {
            pos++;
        }
    }

  eof:
    fwrite(start, pos - start, 1, fout);
    if (strcmp(outfilename, "-") != 0)
        fclose(fout);
    free(in);
}

static unsigned int charmap_hash(const void *value)
{
    const struct CharmapEntry* entry = value;
    unsigned int ret = 0;
    for (int i = 0; i < entry->length; i++)
        ret = ret * 17 + entry->unicode[i];
    return ret;
}

static int charmap_cmp(const void *a, const void *b)
{
    const struct CharmapEntry *ea = a;
    const struct CharmapEntry *eb = b;
    if (ea->length != eb->length)
        return 0;
    for(int i = 0; i < ea->length; i++)
        if(ea->unicode[i] != eb->unicode[i])
            return 0;
    return 1;
}

static void usage(const char *execName)
{
    fprintf(stderr, "Usage: %s CHARMAP INPUT OUTPUT\n", execName);
}

int main(int argc, char **argv)
{
    if (argc != 4)
    {
        usage(argv[0]);
        return 1;
    }

    charmap = hashtable_new(charmap_hash, charmap_cmp, 256, sizeof(struct CharmapEntry));

    read_charmap(argv[1]);
    convert_file(argv[2], argv[3]);

    hashtable_free(charmap);

    return 0;
}
