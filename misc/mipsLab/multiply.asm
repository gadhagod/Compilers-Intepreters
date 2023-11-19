# Asks the user for two numbers and prints the product
# @author Aarav Borthakur
# @version 11/1/23

.text 0x00400000
.globl main

main:
    # Getting first number
    li $v0, 4
    la $a0, msg1
    syscall
    li $v0, 5
    syscall
    move $t1, $v0
    
    # Getting second number
    li $v0, 4
    la $a0, msg2
    syscall
    li $v0, 5
    syscall
    move $t2, $v0
        
    # Multiply first & second numbers
    mult $t1, $t2
    mflo $t3
        
    # Print the product
    li $v0, 1
    move $a0, $t3
    syscall
        
    # clean up
    li $v0, 10
    syscall
        
.data
    msg1:	.asciiz "First number: "
    msg2:	.asciiz "Second number: "    	