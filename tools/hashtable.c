#include <stdint.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

#include "hashtable.h"

struct HashNode
{
    struct HashNode *next;
    uint8_t value[];
};

struct HashTable
{
    HashFunc func;
    HashValueCmpFunc cmp;
    int size;
    int elemSize;
    struct HashNode *table[];
};

struct HashTable *hashtable_new(HashFunc func, HashValueCmpFunc cmp, int size,
    int elemSize)
{
    struct HashTable *ht = malloc(sizeof(*ht) + size * sizeof(ht->table[0]));

    ht->func = func;
    ht->cmp = cmp;
    ht->size = size;
    ht->elemSize = elemSize;
    memset(ht->table, 0, ht->size * sizeof(ht->table[0]));
    return ht;
}

void hashtable_free(struct HashTable *ht)
{
    int i;

    for (i = 0; i < ht->size; i++)
    {
        struct HashNode *node = ht->table[i];

        while (node != NULL)
        {
            struct HashNode *next = node->next;

            free(node);
            node = next;
        }
    }
    free(ht);
}

void hashtable_insert(struct HashTable *ht, const void *value)
{
    unsigned int key = ht->func(value) % ht->size;
    struct HashNode *node = malloc(sizeof(*node) + ht->elemSize);

    node->next = NULL;
    memcpy(node->value, value, ht->elemSize);

    if (ht->table[key] == NULL)
    {
        ht->table[key] = node;
    }
    else
    {
        struct HashNode *parent = ht->table[key];

        while (parent->next != NULL)
            parent = parent->next;
        parent->next = node;
    }
}

void *hashtable_query(struct HashTable *ht, const void *value)
{
    unsigned int key = ht->func(value) % ht->size;
    struct HashNode *node = ht->table[key];

    while (node != NULL)
    {
        if (ht->cmp(node->value, value))
            return node->value;
        node = node->next;
    }
    return NULL;
}
