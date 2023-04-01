CCOPTS=-Wall -O2 -std=c99

all: parse_hour decrypt_userinfo verify

decrypt_userinfo: decrypt_userinfo.o utils.o
	gcc ${CCOPTS} -o $@ $< utils.o -lcrypto

verify: verify.o utils.o
	gcc ${CCOPTS} -o $@ $< utils.o -lcrypto

parse_hour: parse.o
	gcc ${CCOPTS} -o $@ $< -lpcre -lz

%.o: %.c
	gcc ${CCOPTS} -c -o $@ $<

.PHONY: clean realclean

clean:
	-rm parse.o utils.o verify.o decrypt_userinfo.o 
	
realclean: clean
	-rm parse_hour decrypt_userinfo verify
