/*
* The contents of this file are subject to the Common Public Attribution
* License Version 1.0. (the "License"); you may not use this file except in
* compliance with the License. You may obtain a copy of the License at
* http://code.reddit.com/LICENSE. The License is based on the Mozilla Public
* License Version 1.1, but Sections 14 and 15 have been added to cover use of
* software over a computer network and provide for limited attribution for the
* Original Developer. In addition, Exhibit A has been modified to be consistent
* with Exhibit B.
*
* Software distributed under the License is distributed on an "AS IS" basis,
* WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
* the specific language governing rights and limitations under the License.
*
* The Original Code is reddit.
*
* The Original Developer is the Initial Developer.  The Initial Developer of
* the Original Code is reddit Inc.
*
* All portions of the code written by reddit are Copyright (c) 2006-2015 reddit
* Inc. All Rights Reserved.
******************************************************************************/

#include <Python.h>
#include <stdio.h>
#include <string.h>


PyObject *unicode_arg(PyObject *args) {
  PyObject * com;
  if (!PyArg_ParseTuple(args, "O", &com))
    return NULL;
  if (!PyUnicode_Check(com)) {
    PyErr_SetObject(PyExc_TypeError, Py_None);
    return NULL;
  }
  return com;
}



static PyObject *
filters_uwebsafe(PyObject * self, PyObject *args) 
{
  PyObject * com;
  Py_UNICODE * input_buffer;
  Py_UNICODE *buffer;
  PyObject * res;
  int ic=0, ib=0;
  int len;
  Py_UNICODE c;
  if (!(com = unicode_arg(args))) return NULL;
  input_buffer = PyUnicode_AS_UNICODE(com);
  len = PyUnicode_GetSize(com);

  buffer = (Py_UNICODE*)malloc(6*len*sizeof(Py_UNICODE));
  if (buffer == NULL) {
    return PyErr_NoMemory();
  }

  for(ic = 0, ib = 0; ic < len; ic++, ib++) {
    c = input_buffer[ic];
    if (c == '&') {
      buffer[ib++] = (Py_UNICODE)'&';
      buffer[ib++] = (Py_UNICODE)'a';
      buffer[ib++] = (Py_UNICODE)'m';
      buffer[ib++] = (Py_UNICODE)'p';
      buffer[ib]   = (Py_UNICODE)';';
    }
    else if(c == (Py_UNICODE)'<') {
      buffer[ib++] = (Py_UNICODE)'&';
      buffer[ib++] = (Py_UNICODE)'l';
      buffer[ib++] = (Py_UNICODE)'t';
      buffer[ib]   = (Py_UNICODE)';';
    }
    else if(c == (Py_UNICODE)'>') {
      buffer[ib++] = (Py_UNICODE)'&';
      buffer[ib++] = (Py_UNICODE)'g';
      buffer[ib++] = (Py_UNICODE)'t';
      buffer[ib]   = (Py_UNICODE)';';
    }
    else if(c == (Py_UNICODE)'"') {
      buffer[ib++] = (Py_UNICODE)'&';
      buffer[ib++] = (Py_UNICODE)'q';
      buffer[ib++] = (Py_UNICODE)'u';
      buffer[ib++] = (Py_UNICODE)'o';
      buffer[ib++] = (Py_UNICODE)'t';
      buffer[ib]   = (Py_UNICODE)';';      
    }
    else {
      buffer[ib] = input_buffer[ic];
    }
  }
  res = PyUnicode_FromUnicode(buffer, ib);
  free(buffer);
  return res;

}

static PyObject *
filters_uwebsafe_json(PyObject * self, PyObject *args) 
{
  PyObject * com;
  Py_UNICODE * input_buffer;
  Py_UNICODE *buffer;
  PyObject * res;
  int ic=0, ib=0;
  int len;
  Py_UNICODE c;
  if (!(com = unicode_arg(args))) return NULL;
  input_buffer = PyUnicode_AS_UNICODE(com);
  len = PyUnicode_GetSize(com);

  buffer = (Py_UNICODE*)malloc(6*len*sizeof(Py_UNICODE));
  if (buffer == NULL) {
    return PyErr_NoMemory();
  }

  for(ic = 0, ib = 0; ic < len; ic++, ib++) {
    c = input_buffer[ic];
    if (c == '&') {
      buffer[ib++] = (Py_UNICODE)'&';
      buffer[ib++] = (Py_UNICODE)'a';
      buffer[ib++] = (Py_UNICODE)'m';
      buffer[ib++] = (Py_UNICODE)'p';
      buffer[ib]   = (Py_UNICODE)';';
    }
    else if(c == (Py_UNICODE)'<') {
      buffer[ib++] = (Py_UNICODE)'&';
      buffer[ib++] = (Py_UNICODE)'l';
      buffer[ib++] = (Py_UNICODE)'t';
      buffer[ib]   = (Py_UNICODE)';';
    }
    else if(c == (Py_UNICODE)'>') {
      buffer[ib++] = (Py_UNICODE)'&';
      buffer[ib++] = (Py_UNICODE)'g';
      buffer[ib++] = (Py_UNICODE)'t';
      buffer[ib]   = (Py_UNICODE)';';
    }
    else {
      buffer[ib] = input_buffer[ic];
    }
  }
  res = PyUnicode_FromUnicode(buffer, ib);
  free(buffer);
  return res;

}


static PyObject *
filters_websafe(PyObject * self, PyObject *args) 
{
  const char * input_buffer;
  char *buffer;
  PyObject * res;
  int ic=0, ib=0;
  int len;
  char c;
  if (!PyArg_ParseTuple(args, "s", &input_buffer))
    return NULL;
  len = strlen(input_buffer);
  buffer = (char*)malloc(6*len);
  if (buffer == NULL) {
    return PyErr_NoMemory();
  }

  for(ic = 0, ib = 0; ic <= len; ic++, ib++) {
    c = input_buffer[ic];
    if (c == '&') {
      buffer[ib++] = '&';
      buffer[ib++] = 'a';
      buffer[ib++] = 'm';
      buffer[ib++] = 'p';
      buffer[ib]   = ';';
    }
    else if(c == '<') {
      buffer[ib++] = '&';
      buffer[ib++] = 'l';
      buffer[ib++] = 't';
      buffer[ib]   = ';';
    }
    else if(c == '>') {
      buffer[ib++] = '&';
      buffer[ib++] = 'g';
      buffer[ib++] = 't';
      buffer[ib]   = ';';
    }
    else if(c == '"') {
      buffer[ib++] = '&';
      buffer[ib++] = 'q';
      buffer[ib++] = 'u';
      buffer[ib++] = 'o';
      buffer[ib++] = 't';
      buffer[ib]   = ';';
    }
    else {
      buffer[ib] = input_buffer[ic];
    }
  }
  res =  Py_BuildValue("s", buffer);
  free(buffer);
  return res;
}

void print_unicode(Py_UNICODE *c, int len) {
  int i;
  for(i = 0; i < len; i++) {
    printf("%d", (int)c[i]);
    if(i + 1 != len) printf(":");
  }
  printf("\n");
}

const char *SC_OFF = "<!-- SC_OFF -->";
const char *SC_ON  = "<!-- SC_ON -->";
const Py_UNICODE *SC_OFF_U;
const Py_UNICODE *SC_ON_U;
int SC_OFF_LEN = 0;
int SC_ON_LEN = 0;



int whitespace(char c) {
  return (c == '\n' || c == '\r' || c == '\t' || c == ' ');
}


static PyObject *
filters_uspace_compress(PyObject * self, PyObject *args) {
  PyObject * com;
  PyObject * res;
  Py_ssize_t len;
  Py_UNICODE *input_buffer;
  Py_UNICODE *buffer;
  Py_UNICODE c;
  int ic, ib;
  int gobble = 1;
  com = unicode_arg(args);
  if(!com) {
    return NULL;
  }
  input_buffer = PyUnicode_AS_UNICODE(com);
  len = PyUnicode_GetSize(com);
  buffer = (Py_UNICODE*)malloc(len * sizeof(Py_UNICODE));
  if (buffer == NULL) {
    return PyErr_NoMemory();
  }

  /* ic -> input buffer index, ib -> output buffer */
  for(ic = 0, ib = 0; ic <= len; ic++) {
    c = input_buffer[ic];
    /* gobble -> we are space compressing */
    if(gobble) {
      /* remove spaces if encountered */
      if(Py_UNICODE_ISSPACE(c)) {
        /* after this loop, c will be a non-space */
        while(Py_UNICODE_ISSPACE(c)) { c = input_buffer[++ic]; }
        /* unless next char is a <, add a single space to account for
           the multiple spaces that have been removed */
        if(c != (Py_UNICODE)('<')) {
          buffer[ib++] = (Py_UNICODE)(' ');
        }
      }
      /* gobble all space after '>' */
      if(c == (Py_UNICODE)('>')) {
        buffer[ib++] = c;
	c = input_buffer[++ic];
        while(Py_UNICODE_ISSPACE(c)) { c = input_buffer[++ic]; }
      }
      /* does the next part of the string match the SC_OFF label */
      if (len - ic >= SC_OFF_LEN &&
          memcmp(&input_buffer[ic], SC_OFF_U, 
                 sizeof(Py_UNICODE)*SC_OFF_LEN) == 0) {
        /* disable gobbling, and bypass that part of the string */
        gobble = 0;
        ic += SC_OFF_LEN;
        c = input_buffer[ic];
      }
    }
    /* not gobbling, but find the SC_ON tag */
    else if (len - ic >= SC_ON_LEN &&
          memcmp(&input_buffer[ic], SC_ON_U, 
                 sizeof(Py_UNICODE)*SC_ON_LEN) == 0) {
        gobble = 1;
        ic += SC_ON_LEN;
        c = input_buffer[ic];
    }
    if(c) {
      buffer[ib++] = c;
    }
  }  

  res = PyUnicode_FromUnicode(buffer, ib);
  free(buffer);
  return res;
}

static PyMethodDef FilterMethods[] = {
  {"websafe",  filters_websafe, METH_VARARGS,
   "make string web safe."},
  {"uwebsafe",  filters_uwebsafe, METH_VARARGS,
   "make string web safe."},
  {"uwebsafe_json",  filters_uwebsafe_json, METH_VARARGS,
   "make string web safe, no &quot;."},
  {"uspace_compress",  filters_uspace_compress, METH_VARARGS,
   "removes spaces around angle brackets. Can be disabled with the use of SC_OFF and SC_ON comments from r2.lib.filters."},
  {NULL, NULL, 0, NULL}        /* Sentinel */
};

Py_UNICODE *to_unicode(const char *c, int len) {
  Py_UNICODE *x = (Py_UNICODE *)malloc((len+1) * sizeof(Py_UNICODE));
  if (x == NULL) {
    PyErr_NoMemory();
    return NULL;
  }

  int i;
  for(i = 0; i < len; i++) {
    x[i] = (Py_UNICODE)c[i];
  }
  x[len] = (Py_UNICODE)(0);
  return x;
}


PyMODINIT_FUNC
initCfilters(void)
{
  SC_OFF_LEN = strlen(SC_OFF);
  SC_OFF_U = to_unicode(SC_OFF, SC_OFF_LEN);
  SC_ON_LEN = strlen(SC_ON);
  SC_ON_U = to_unicode(SC_ON, SC_ON_LEN);

  (void) Py_InitModule("Cfilters", FilterMethods);
}

