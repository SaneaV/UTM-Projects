ORG 100h

MOV AL, VAR1                ; Check var1, locating it into AL
LEA BX, VAR1                ; Put address of var1 into BX
MOV BYTE PTR[BX], 44h       ; Change var1 to 44h
MOV AL, VAR1                ; Check var1, locating it into AL

RET
    
VAR2 DB 22h    
VAR1 DB 50h

END