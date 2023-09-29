include 'emu8086.inc'

CALL m1

MOV AX, 2

RET                     ; Go back to OS

m1 PROC
    MOV BX,5
    RET                 ; Go to the line were this procedure was called
m1 ENDP                                                                             

END