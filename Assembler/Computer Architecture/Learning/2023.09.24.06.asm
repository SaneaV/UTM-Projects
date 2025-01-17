#MAKE_COM#              ; Make COM file

ORG 100h                ; Required directive for COM file

; We are using non-changeable register AH after completion, so we can use it once 

MOV AH, 0Eh             ; Choose subfunction

; INT 10h / 0Eh - accept ASCII symbol code, which will be inserted into AL register

MOV AL, 'H'
INT 10h                 ; Call print function
MOV AL, 'e'
INT 10h
MOV AL, 'l'
INT 10h
MOV AL, 'l'
INT 10h
MOV AL, 'o'
INT 10h
MOV AL, '!'
INT 10h

RET                     ; Go back to OS