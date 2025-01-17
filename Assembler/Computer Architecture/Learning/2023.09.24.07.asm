include emu8086.inc             ; Include emu8086.inc file with required macroses

ORG 100h                        ; Required parameter for COM files

PRINT 'Hello, World!'           ; PRINT macros from emu8086.inc to print 'Hello, World!'
GOTOXY 10, 5                    ; Go to column 10 and row 5

PUTC 'A'                        ; Print ASCII symbol 'A'
PUTC 66                         ; Print ASCII code for symbol 'B'

RET                             ; Go back to OS system
END                             ; End compilation