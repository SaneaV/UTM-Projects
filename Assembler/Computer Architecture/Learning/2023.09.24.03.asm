ORG 100h

MOV    AL, VAR1              ; Check VAR1, locating it into AL
MOV    BX, OFFSET VAR1       ; Move address of VAR1 into BX
MOV    BYTE PTR [BX], 44h    ; Change VAR1 variable value to 44h
MOV    AL, VAR1              ; Check VAR1, locating it into AL

RET

VAR1   DB  22h

END