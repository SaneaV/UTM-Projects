.MODEL SMALL                   ; Define the model as SMALL.
.DATA                          ; Start the data segment.

X DW ?                         ; Declare X as a word variable (initialized later).
Y DW ?                         ; Declare Y as a word variable (initialized later).
T DW ?                         ; Declare T as a word variable (initialized later).
linecounter dw ?               ; Declare linecounter as a word variable (initialized later).

LastX DW ?                     ; Declare LastX as a word variable (initialized later).
LastY DW ?                     ; Declare LastY as a word variable (initialized later).

PixelClr DB ?                  ; Declare PixelClr as a byte variable (initialized later).

.CODE                          ; Switch to the code segment.
.STARTUP                       ; Start the program execution.

MOV AH, 00                     ; Set AH register to 00.
MOV AL, 13h                    ; Set AL register to 13h (video mode 13h).
INT 10h                        ; Call BIOS interrupt 10h to set the video mode.

MOV X, 85                      ; Initialize X coordinate.
MOV Y, 90                      ; Initialize Y coordinate.
MOV linecounter, 50            ; Initialize linecounter.

FirstLine:                     ; Label for the beginning of the first line drawing loop.

MOV BH, 0                      ; Set BH register to 0 (page number).
MOV CX, X                      ; Load X coordinate into CX register.
MOV DX, Y                      ; Load Y coordinate into DX register.
MOV T, 15                      ; Initialize T to 15 (pixel counter for the first line).
MOV AH, 0Ch                    ; Set AH to 0Ch (plot a pixel).
MOV AL, 0Fh                    ; Set AL to 0Fh (color attribute, white).

DrawFirstLine:                 ; Label for drawing the first line.
INT 10h                        ; Call BIOS interrupt 10h to draw a pixel.

INC CX                         ; Increment CX (move to the next pixel horizontally).
INC DX                         ; Increment DX (move down one pixel).
DEC T                          ; Decrement T (decrease the pixel counter).
CMP T, 0                       ; Compare T to 0.
JNZ DrawFirstLine              ; If T is not zero, jump back to DrawFirstLine.

INC X                          ; Increment X (move to the next starting X coordinate).
DEC Y                          ; Decrement Y (move to the next starting Y coordinate).
DEC linecounter                ; Decrement linecounter.
CMP linecounter, 0             ; Compare linecounter to 0.
JNZ FirstLine                  ; If linecounter is not zero, jump back to FirstLine.

MOV X, 95                      ; Initialize X coordinate for the second line.
MOV Y, 110                     ; Initialize Y coordinate for the second line.
MOV linecounter, 50            ; Initialize linecounter for the second line.

SecondLine:                    ; Label for the beginning of the second line drawing loop.

MOV BH, 0                      ; Set BH register to 0 (page number).
MOV CX, X                      ; Load X coordinate into CX register.
MOV DX, Y                      ; Load Y coordinate into DX register.
MOV T, 15                      ; Initialize T to 15 (pixel counter for the second line).
MOV AH, 0Ch                    ; Set AH to 0Ch (plot a pixel).
MOV AL, 02h                    ; Set AL to 02h (color attribute, green).

DrawSecondLine:                ; Label for drawing the second line.
INT 10h                        ; Call BIOS interrupt 10h to draw a pixel.

INC CX                         ; Increment CX (move to the next pixel horizontally).
INC DX                         ; Increment DX (move down one pixel).
DEC T                          ; Decrement T (decrease the pixel counter).
CMP T, 0                       ; Compare T to 0.
JNZ DrawSecondLine             ; If T is not zero, jump back to DrawSecondLine.

INC X                          ; Increment X (move to the next starting X coordinate).
DEC Y                          ; Decrement Y (move to the next starting Y coordinate).
DEC linecounter                ; Decrement linecounter.
CMP linecounter, 0             ; Compare linecounter to 0.
JNZ SecondLine                 ; If linecounter is not zero, jump back to SecondLine.

MOV X, 105                     ; Initialize X coordinate for the third line.
MOV Y, 130                     ; Initialize Y coordinate for the third line.
MOV linecounter, 50            ; Initialize linecounter for the third line.

ThirdLine:                     ; Label for the beginning of the third line drawing loop.

MOV BH, 0                      ; Set BH register to 0 (page number).
MOV CX, X                      ; Load X coordinate into CX register.
MOV DX, Y                      ; Load Y coordinate into DX register.
MOV T, 15                      ; Initialize T to 15 (pixel counter for the third line).
MOV AH, 0Ch                    ; Set AH to 0Ch (plot a pixel).
MOV AL, 0Ch                    ; Set AL to 0Ch (color attribute, red).

DrawThirdLine:                 ; Label for drawing the third line.
INT 10h                        ; Call BIOS interrupt 10h to draw a pixel.

INC CX                         ; Increment CX (move to the next pixel horizontally).
INC DX                         ; Increment DX (move down one pixel).
DEC T                          ; Decrement T (decrease the pixel counter).
CMP T, 0                       ; Compare T to 0.
JNZ DrawThirdLine              ; If T is not zero, jump back to DrawThirdLine.

INC X                          ; Increment X (move to the next starting X coordinate).
DEC Y                          ; Decrement Y (move to the next starting Y coordinate).
DEC linecounter                ; Decrement linecounter.
CMP linecounter, 0             ; Compare linecounter to 0.
JNZ ThirdLine                  ; If linecounter is not zero, jump back to ThirdLine.

END                            ; End of the program.
