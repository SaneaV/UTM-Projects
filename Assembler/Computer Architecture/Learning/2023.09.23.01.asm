#MAKE_COM#            ; Create COM file
   
ORG 100h              ; Required com directive

MOV AX, 0B800h        ; Put AX onto HEX value B800h
MOV DS, AX            ; Copy AX into DS 
MOV CL, 'A'           ; Put char 'A' into CL (41h)
MOV CH, 01011111b     ; Put CH onto binary value 01011111b
MOV BX, 15Eh          ; Put BX onto HEX value 15Eh
MOV [BX], CX          ; Copy CX into [BX] - B800h:15Eh
RET                   ; Return into OS