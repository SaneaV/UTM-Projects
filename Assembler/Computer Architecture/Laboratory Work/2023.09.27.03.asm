INCLUDE 'EMU8086.INC' ; Include the Emu8086 assembly library

.MODEL SMALL
.STACK 100h

.DATA
    input_size DB 10                    ; Define the size of the input array
    line_size_index DB 0                ; Initialize the line size index
    number_of_lines_index DB 0          ; Initialize the line number index
    ARR DB 50 DUP(?)                    ; Declare an array with 50 bytes, initially filled with null values

.CODE
MAIN PROC
    MOV AX, @DATA                       ; Load the address of the data segment into AX
    MOV DS, AX                          ; Initialize the data segment DS with the value in AX
    XOR BX, BX                          ; Clear BX register (used as a counter)
    XOR CX, CX                          ; Clear CX register (used as a counter)

    MOV AH, 1                           ; AH=1 indicates DOS function for reading a character

    MOV CL, input_size                  ; Copy the numeric value to CX
    MOV BL, input_size                  ; Copy the numeric value to BX
    MOV SI, 0                           ; Initialize SI register (used as an index)

    PRINT "Enter values (without pressing Enter or Space): "

    ; Read input characters
    INPUT:
        INT 21H                         ; Read a character from input
        MOV ARR[SI], AL                 ; Store the character in the ARR array at the current index SI
        INC SI                          ; Increment the index SI
        LOOP INPUT                      ; Repeat until CX (the number of values to store) becomes zero

    PRINTN                              ; Print a new line
    PRINT "OUTPUT: "    
    PRINTN                              ; Print a new line
    
    OUTPUT_SECTION:
    PRINTN
    MOV  AL, number_of_lines_index
    CBW
    ADD  AX, 1
    CALL PRINT_NUM
    PRINT '. '
    
    MOV line_size_index, 0
    
    ; Start displaying characters
    START_LOOP:
    MOV CX, BX                          ; Copy the number of values to display from BX to CX
    MOV SI, 0                           ; Reset the index SI
    MOV AH, 2                           ; AH=2 indicates DOS function for displaying a character

    OUTPUT:
        MOV DL, ARR[SI]                 ; Load a character from the ARR array at the current index SI
        INT 21H                         ; Call DOS to display the character
        INC SI                          ; Increment the index SI
        INC line_size_index
        CMP line_size_index, 75
        JE END_LOOP
        LOOP OUTPUT                     ; Repeat until CX (the number of values to display) becomes zero
        
    CMP line_size_index, 75
    JNE START_LOOP     
    
    END_LOOP:
    
    INC number_of_lines_index
    CMP number_of_lines_index, 20 
    JAE END_PROGRAM                     ; Exit if the line count exceeds the specified number of lines
     
    JMP OUTPUT_SECTION                  ; Jump back to read the next line of input  
       
    END_PROGRAM:
    ; Exit the program
    MOV AH, 4CH
    INT 21H
    RET
    
    MAIN ENDP                           ; End of the main procedure

DEFINE_PRINT_NUM_UNS
DEFINE_PRINT_NUM

END