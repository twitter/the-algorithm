.section .data
message:
    .ascii "Twitter\n"
.section .text
.globl _start
_start:
    movl $4, %eax        # System call for 'write'
    movl $1, %ebx        # File descriptor for 'stdout'
    movl $message, %ecx  # Pointer to message to print
    movl $8, %edx        # Length of message
    int $0x80            # Call the kernel
    movl $1, %eax        # System call for 'exit'
    xorl %ebx, %ebx      # Exit code 0
    int $0x80            # Call the kernel
