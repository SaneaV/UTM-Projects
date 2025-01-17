a DB "hello"                ; Create variable 'a' with array of bytes 'hello'

MOV AL, a[3]                ; Copy value from a[3] to 

MOV SI, 3                   ; Copy decimal value 3 to SI

MOV AL, A[SI]               ; Copy a[SI=3] to AL

b DB 5 DUP(9)               ; Create variable 'b' with 5 repetitions of value 9
c DB 9,9,9,9,9              ; Create variable 'c' with 5 repetitions of value 9 (alternative for line 09)
d DB 5 DUP(1,2)             ; 1,2,1,2,1,2,1,2,1,2

e DW 256                    ; We can't use DB with number greater than 255

RET