include 'emu8086.inc'

org 100h

x dw 0
y dw 0   

main proc
     
    ; Input value x from keyboard     
    lea si, x_message
    call print_string
    call scan_num
    
    mov x, cx
    
    ; Input value y from keyboard     
    lea si, Y_message
    call print_string
    call scan_num
    
    mov y, cx
    
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

x_message db 'Enter the number for x: ', 0
x_g_2y_message db 13, 10, 'Result for x > 2y is 2 * (x - y) + 81 = ', 0
x_be_2y_message db 13, 10, 'Result for x <= 2y is 2 * Y + X / 4 = ', 0
y_message db 13, 10,'Enter the number for y: ', 0
no_case db 13, 10, 'There is a case not equal to x > 2y or x <= 2y', 0  

DEFINE_PTHIS
DEFINE_PRINT_STRING
DEFINE_SCAN_NUM
DEFINE_PRINT_NUM
DEFINE_PRINT_NUM_UNS 

end