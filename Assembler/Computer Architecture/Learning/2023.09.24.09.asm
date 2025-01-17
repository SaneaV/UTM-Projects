ORG 100h

MOV AX, 5               ; Insert 5 into AX
MOV BX, 2               ; Insert 2 into BX

JMP calc                ; Jump into calc label

back: JMP stop          ; Jump into stop label 

calc:
ADD AX, BX              ; Add AX to BX
JMP back                ; Jump to back

stop:
RET
END