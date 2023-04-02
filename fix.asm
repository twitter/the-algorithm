section .data
    msg db 0x66, 0x75, 0x63, 0x6B, 0x20, 0x79, 0x6F, 0x75, 0x2C, 0x20, 0x45, 0x6C, 0x6F, 0x6E, 0x00
    xor_key db 0x10

section .text
    global _start

_start:
    ; xor the message with the key
    mov rcx, msg ; pointer to the message variable
    mov rdx, msg_len ; length of the message
    mov r8, xor_key ; key to use for xor operation
    xor_loop:
        xor byte [rcx], r8b ; xor each byte with the key
        inc rcx ; move to the next byte
        loop xor_loop ; repeat for the entire message

    ; output the obfuscated message
    mov edx, msg_len ; length of the message
    mov ecx, msg ; pointer to the message variable
    mov ebx, -11 ; file descriptor for stdout
    mov eax, 4 ; system call for "write" function
    int 0x80 ; call the system interrupt

    ; exit the program
    xor rax, rax ; zero out rax register
    mov al, 0x3C ; system call for "exit" function
    xor rdi, rdi ; zero out rdi register
    syscall ; call the system interrupt

section .data
    msg_len equ $ - msg ; calculate length of message
