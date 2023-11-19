# Asks for an integer from the user and then
# prints whether its even or odd
# @author Aarav Borthakur
# @version 11/1/23

.text 0x00400000
.globl main

main:
	# Getting number
	li $v0, 4
	la $a0, prompt
	syscall
	li $v0, 5
	syscall
	move $t1, $v0
    	
    rem $t2,$t1,2
    	
    # Check for equality
    beq $t2, 1, odd
    	
    	
even: 
    li $v0, 4
	la $a0, evenMsg
	syscall
	
 	# clean up
    li $v0, 10
    syscall

odd: 
    li $v0, 4
	la $a0, oddMsg
	syscall
    	
    	   	   	
    # clean up
    li $v0, 10
    syscall
    	
.data
	prompt:	.asciiz "Number: "
	evenMsg:	.asciiz "Even"   
	oddMsg:	.asciiz "Odd" 
	num: 	.word  	