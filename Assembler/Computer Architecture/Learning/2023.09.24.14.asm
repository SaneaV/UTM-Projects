ORG 100h

LEA SI, msg                 ; Put msg into SI register

CALL print_me               ; Call print_me procedure

RET                         ; Return to OS

print_me PROC
    next_char:
    CMP b.[SI], 0           ; Check register SI, in case
    JE stop                 ; it is 0, go to stop
    
    MOV AL, [SI]            ; Else get ASCII symbol
    
    MOV AH, 0Eh             ; Function number to print symbol
    INT 10h                 ; use interrupts to print symbol from AH
    
    ADD SI, 1               ; Icrease index of string array
    
    JMP next_char           ; Go back and print another symbol

    stop:
    RET

print_me ENDP

msg DB 'Hello World!', 0    ; String with the zero end

END