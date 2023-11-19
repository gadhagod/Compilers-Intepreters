# Prompts the user for their name then print's Hello user name
# 
# @author Aarav Borthakur, Juliana Li
# @version 11/17/23

.data
    name:	  .space		20					# space to save the name in memory
    prompt:	  .asciiz		"Enter your name: "	# prompt for the user to enter name
    response: .asciiz 	    "Hello "		    # used to display Hello user name
    len: 	  .word 		20					# max characters allowed in name

.text
.globl main

main:

    # Print prompt
    li $v0, 4
    la $a0, prompt
    syscall

    # Read name
    li $v0, 8
    la $a0, name
    la $a1, len
    move $t0, $a0 
    syscall

    # Print "hello"
    li $v0, 4
    la $a0, response
    syscall

    # Print username
    la $a0, name
    move $a0, $t0
    li $v0, 4
    syscall

    # Normal termination
    li $v0, 10
    syscall