ORG 100h

MOV AL, 1
MOV BL, 2

CALL m2
CALL m2
CALL m2
CALL m2

m2 PROC
    MUL BL        
    RET
m2 ENDP

END