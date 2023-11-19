# Plays a random number guessing game (the highest possible
# number is 10 and the lowest number is 0)
# @author Aarav Borthakur
# @version 11/1/23

.text 0x00400000
.globl main

main:
    li $a1, 10  # Set the max bound to 10
    li $v0, 42  # Generate the random number
    syscall
    move $t0, $a0
    	
ask:
	# Print prompt
    li $v0, 4
	la $a0, prompt
	syscall
	
	# Read integer
    li $v0, 5
	syscall
	move $t1, $v0
	
	# if too less
	blt $t0, $t1, tooHigh
	
	# if too high
	bgt $t0, $t1, tooLow
	
	# else
		# congratulate
    	li $v0, 4
		la $a0, success
		syscall
	 
	 	# clean up
    	li $v0, 10
    	syscall
	
tooLow:
	# Print too low
    li $v0, 4
	la $a0, tooLowMsg
	syscall
	j ask

tooHigh:
	# Print too low
    li $v0, 4
	la $a0, tooHighMsg
	syscall
	j ask

.data
	prompt:		.asciiz "Guess:"
	tooLowMsg:	.asciiz "Too low"
	tooHighMsg:	.asciiz "Too high"
	success:	.asciiz "Congrats!"
