include 'emu8086.inc'

ORG 100h

; unconditional jump forward:
; skip the next two bytes
JMP $2
a DB 3                  ; 1 BYTE
b DB 4                  ; 1 BYTE

; Print my_string while 9-- is not equal to 0

MOV BL, 9
LEA SI, my_string

my_loop:
DEC BL
CMP BL, 0
JZ done
CALL print_string
JMP my_loop

done:

; Infinitive loop

MOV BL, 9 
DEC BL                  ; 2 BYTE
CMP BL, 0               ; 3 BYTE
JNE $-7                 ; If not equals jump on 7 bytes back

RET   

my_string db 'Hello, World!', 13, 10, 0

DEFINE_PRINT_STRING  

END