include 'emu8086.inc'

org 100h

.model small
.data

x dw 0
y dw 0 

x_message db 'Enter the number for x: ', 0
x_g_2y_message db 13, 10, 'Result for x > 2y is 2 * (x - y) + 81 = ', 0
x_be_2y_message db 13, 10, 'Result for x <= 2y is 2 * Y + X / 4 = ', 0
y_message db 13, 10,'Enter the number for y: ', 0
no_case db 13, 10, 'There is a case not equal to x > 2y or x <= 2y', 0  

.code
main proc 
    
    call srandsystime   ; Seed PRNG with system time, call once only 

    call rand           ; Get a random number in AX
    call rand2num1to10  ; Convert AX to num between 1 and 10
    mov x, ax
    call rand           ; Get another random number in AX
    call rand2num1to10  ; Convert AX to num between 1 and 10
    mov y, ax
    
    ; check x > 2y
    mov ax, y
    mov bx, 2
    mul bx
    
    cmp x, ax
    jg x_g_2y
    
    ; check x <= 2y
    mov ax, y
    mov bx, 2
    mul bx
    
    cmp x, ax
    jbe x_be_2y
    
    ; if not x > 2y or x <= 2y
    
    
    ; 2 * (X - Y) + 81
    x_g_2y:
    mov ax, x           ; ax = x
    sub ax, y           ; ax = ax - y = x - y
    mov bx, 2           ; bx = 2
    mul bx              ; ax = 2 * ax = 2 * (x - y)
    add ax, 81          ; ax = ax + 81 = 2 * (x - y) + 81
    
    ; print result and go to the end of the program
    lea si, x_g_2y_message
    call print_string
    call print_num
    jmp stop
    
    ; 2 * Y + X / 4
    x_be_2y:
    mov bx, 2           ; bx = 2
    mov ax, y           ; ax = y
    mul bx              ; ax = 2 * ax
    mov cx, ax          ; cx = ax
    mov bx, 4           ; bx = x
    mov ax, x           ; ax = 4
    div bx              ; ax = ax / 4
    add ax, cx          ; ax = ax + cx = 2 * y + x / 4
    
    ; print result and go to the end of the program
    lea si, x_be_2y_message
    call print_string
    call print_num
    
    stop:
    
main endp  

ret

; Return number between 1 and 10
;    
; Inputs:   AX = value to convert
; Return:   (AX) value between 1 and 10

rand2num1to10:
    push dx
    push bx
    xor dx,dx           ; Compute randval(DX) mod 10 to get num
    mov bx,10           ;     between 0 and 9
    div bx
    inc dx              ; DX = modulo from division
                        ;     Add 1 to give us # between 1 and 10 (not 0 to 9)
    mov ax,dx
    pop bx
    pop dx
    ret

; Set LCG PRNG seed to system timer ticks
;
; Inputs:   AX = seed
; Modifies: AX 
; Return:   nothing 

srandsystime:
    push cx
    push dx
    xor ax, ax          ; Int 1Ah/AH=0 to get system timer in CX:DX 
    int 1ah
    mov [seed], dx      ; seed = 16-bit value from DX
    pop dx
    pop cx
    ret

; Updates seed for next iteration
;     seed = (multiplier * seed + increment) mod 65536
;     multiplier = 25173, increment = 13849
;
; Inputs: none
; Return: (AX) random value

rand:
    push dx
    mov ax, 25173       ; LCG Multiplier
    mul word ptr [seed] ; DX:AX = LCG multiplier * seed
    add ax, 13849       ; Add LCG increment value
    mov [seed], ax      ; Update seed
    ; AX = (multiplier * seed + increment) mod 65536
    pop dx
    ret
        
seed dw 11             ; Default initial seed of 11 

DEFINE_PTHIS
DEFINE_PRINT_STRING
DEFINE_SCAN_NUM
DEFINE_PRINT_NUM
DEFINE_PRINT_NUM_UNS 

end