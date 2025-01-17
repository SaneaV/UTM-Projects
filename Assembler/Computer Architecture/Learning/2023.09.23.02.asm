#MAKE_COM#              ; Make COM file

ORG 100h                ; Required directive for COM program

MOV AL, var1            ; Copy value from var1 into AL
MOV BX, var2            ; Copy value from var2 into BX

RET                     ; Move back to OS system

VAR1 db 7               ; Initialize VAR1 (Define Byte) with value 7 (case insensitive)
var2 DW 1234h           ; Initialize var2 (Define Word) with word 1234h