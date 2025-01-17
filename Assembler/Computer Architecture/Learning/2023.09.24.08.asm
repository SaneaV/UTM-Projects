include 'emu8086.inc'

ORG 100h

LEA SI, msg1                        ; Request to input a number
CALL print_string                   ; Procedure to print string
CALL scan_num                       ; Procedure to scan number   

MOV AX, CX                          ; Put CX input number into AX

CALL pthis                          ; Same like print_string, but address is taken from stake
DB 13,15, 'You have entered: ', 0

CALL print_num                      ; Procedure to number from AX

;CALL CLEAR_SCREEN                   ; Clear screen

RET                                 ; Go back to OS system

msg1 DB 'Enter the number: ', 0

DEFINE_CLEAR_SCREEN
DEFINE_SCAN_NUM
DEFINE_PRINT_STRING
DEFINE_PRINT_NUM
DEFINE_PRINT_NUM_UNS                ; Required for print_num
DEFINE_PTHIS

END