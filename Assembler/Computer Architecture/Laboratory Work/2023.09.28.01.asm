include 'emu8086.inc'

org 100h

.model small
.data
mas1 db 1, 2, 3, 4, 5, 6, 7, 8, 9, 0

i equ 3  ; Replace with the desired value of i
k equ 7  ; Replace with the desired value of k (decremented by 1)
n equ 10

mas2_size equ i + (n - k + 1)
mas2 db mas2_size dup(0)  ; Array mas2 initialized with zeros

mas3_size equ k - i - 1
mas3 db mas3_size dup(0)   ; Array mas3 initialized with zeros

msg1 db "mas1: $"
msg2 db "mas2: $"
msg3 db "mas3: $"

.code
mov ax, @data
mov ds, ax

; a) Combine sequences from 1 to i and from k to n into mas2
mov cx, i
mov si, 0  ; Index for mas1
mov di, 0  ; Index for mas2

copy_part1:
mov al, mas1[si]
mov mas2[di], al
inc si
inc di
loop copy_part1

mov si, k
mov di, i
mov cx, k
copy_part2:
mov al, mas1[si]
mov mas2[di], al
inc si
inc di
loop copy_part2

; b) Enter the sequence from i to k into mas3
mov si, i
mov di, 0  ; Index for mas3

copy_part3:
mov al, mas1[si]
mov mas3[di], al
inc si
inc di
cmp si, k
jae done_copy_part3
jmp copy_part3

done_copy_part3:

; Print mas1
print 'mas1: '
lea si, mas1
mov cx, n
call print_array

printn

; Print mas2
print 'mas2: '
lea si, mas2
mov cx, i + (n - k)
call print_array

printn

; Print mas3
print 'mas3: '
lea si, mas3
mov cx, k - i
call print_array

printn

; Exit the program
mov ah, 4Ch
int 21h

; Helper procedure to print an array
print_array proc
    loop_start:
    mov dl, [si]
    add dl, 30h  ; Convert digit to char
    mov ah, 2h
    int 21h
    
    mov dl, 20h  ; Print space
    mov ah, 2h
    int 21h
    
    inc si
    loop loop_start
    ret
print_array endp

end
