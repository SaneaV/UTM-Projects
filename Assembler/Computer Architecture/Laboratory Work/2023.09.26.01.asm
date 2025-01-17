; z=(2+1/a)/(3+1/(b*b))-1/(c*c)

org 100h

a dw 2              ; set a = 2
b dw 3              ; set b = 3
c dw 4              ; set c = 4
result dw 0         ; result

main proc
    
    ; (2 + 1 / a)
    mov ax, 1       ; ax = 1
    mov bx, a       ; bx = a
    div bx          ; ax = 1 / a = 1 / 2
    add ax, 2       ; ax = (2 + 1 / a)
    
    call update_result
    
    ; (3 + 1 / (b * b))
    mov ax, b       ; ax = b = 3
    mul ax          ; ax = b * b = 9
    mov cx, ax      ; ax -> cx
    mov ax, 1       ; ax = 1
    call clear_dx
    div cx          ; ax = 1 / (b * b)
    add ax, 3       ; ax = (3 + 1 / (b * b))
    
    ; (2+1/a)/(3+1/(b*b))
    mov cx, ax      ; cx = (3 + 1 / (b * b))
    mov ax, result  ; ax = (2 + 1 / a)
    call clear_dx
    div cx          ; ax = (2+1/a)/(3+1/(b*b))
    
    call update_result
    
    ; (1 / (c * c))
    mov ax, c       ; ax = c
    mul ax          ; ax = c * c
    mov cx, ax      ; ax -> cx
    mov ax, 1       ; ax = 1
    call clear_dx
    div cx          ; ax = 1 / (c * c)
    
    ; z=(2+1/a)/(3+1/(b*b))-1/(c*c)
    sub result, ax  ; ax = (2+1/a)/(3+1/(b*b))-1/(c*c)
    mov ax, result 
       

    ; Display the result (quotient)
    mov ah, 0Eh     ; AH = 0Eh (function for displaying a character)
    mov bh, 0       ; BH = 0 (video page number)
    mov bl, 7       ; BL = 7 (character color)
    add al, '0'     ; Convert the quotient to ASCII
    int 10h         ; Call BIOS video interrupt

    ; Terminate the program
    mov ah, 4Ch     ; Exit with a return code of 0
    int 21h 
    
    
main endp

clear_dx proc
        xor dx, dx      ; Clear DX (used for the high word of the dividend)
        ret
clear_dx endp

update_result proc
        mov result, ax  ; move ax to result
        ret
update_result endp

ret
end