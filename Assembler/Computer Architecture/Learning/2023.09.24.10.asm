include 'emu8086.inc'

ORG 100h

MOV Al, 10
MOV BL, 10

CMP AL, BL                  ; Compare AL and BL

JE equal                    ; If AL equal to BL go to equal

PUTC 'N'                    ; If not equal (AL<>BL) print N
JMP stop                    ; Go to stop

equal:                      ; If program is here then AL is equal to BL 
PUTC 'Y'

stop:                       ; Go here anyway
RET 
END
