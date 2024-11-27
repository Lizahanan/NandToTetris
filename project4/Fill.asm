// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/4/Fill.asm

// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, 
// the screen should be cleared.

//// Replace this comment with your code.

//main loop which listens to the keyboard
// Main loop: Listens to keyboard input
(LOOP)
    @KBD          // Address of the keyboard
    D=M           // Store the keypress value in D
    @BLACK
    D;JNE         // If D != 0 (key pressed), jump to BLACK
    @WHITE
    0;JMP         // Otherwise, jump to WHITE

// Blacken the screen
(BLACK)
    @SCREEN       // Start of screen memory
    D=A           // Store the start address in D
    @R0           // Use R0 as a pointer
    M=D           // Initialize R0 to point to the screen memory
(BLACKLOOP)
    @R0
    A=M           // Load the current screen address
    M=-1          // Paint it black (-1)
    @R0
    M=M+1         // Increment the pointer
    @24576        // End of screen memory
    D=A           // Load the end address into D
    @R0
    A=M           // Load the current pointer value
    D=D-A         // Subtract current pointer from end address
    @BLACKLOOP
    D;JGT         // If pointer is less than 24576, continue BLACKLOOP
    @LOOP         // Otherwise, return to main loop
    0;JMP         // Jump back to listen for keypress

// Clear the screen
(WHITE)
    @SCREEN       // Start of screen memory
    D=A           // Store the start address in D
    @R0           // Use R0 as a pointer
    M=D           // Initialize R0 to point to the screen memory
(WHITELOOP)
    @R0
    A=M           // Load the current screen address
    M=0           // Paint it white (0)
    @R0
    M=M+1         // Increment the pointer
    @24576        // End of screen memory
    D=A           // Load the end address into D
    @R0
    A=M           // Load the current pointer value
    D=D-A         // Subtract current pointer from end address
    @WHITELOOP
    D;JGT         // If pointer is less than 24576, continue WHITELOOP
    @LOOP         // Otherwise, return to main loop
    0;JMP         // Jump back to listen for keypress

