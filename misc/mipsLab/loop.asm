# Asks for a high integer and a low integer from the user.
# Then, it prints the numbers within the range in an interval 
# of 25
# @author Aarav Borthakur
# @version 11/1/23

.text 0x00400000
.globl main

main:
    # Get low number
    # Store it in t0
    li $v0, 4
    la $a0, msg1
    syscall
    li $v0, 5
    syscall
    move $t0, $v0

    # Getting high number
    # Store it in t1
    li $v0, 4
    la $a0, msg2
    syscall
        li $v0, 5
        syscall
        move $t1, $v0

        looptest:
        bgt $t0, $t1, endloop
        
        # Print the product
        li $v0, 1
        move $a0, $t0
        syscall
        
        # Print the newline
        li $v0, 4
        la $a0, newline
        syscall
        
        # add 25 to current number
        addu $t0, $t0, 25
        
        # repeat loop
        j looptest
        
    endloop: 
        
        
        # clean up
        li $v0, 10
        syscall

.data
    msg1:	 .asciiz "First number: "
    msg2:	 .asciiz "Second number: "
    newline: .asciiz "\n"
