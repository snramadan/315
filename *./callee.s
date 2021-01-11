	.arch armv7-a
	.eabi_attribute 28, 1
	.eabi_attribute 20, 1
	.eabi_attribute 21, 1
	.eabi_attribute 23, 3
	.eabi_attribute 24, 1
	.eabi_attribute 25, 1
	.eabi_attribute 26, 2
	.eabi_attribute 30, 1
	.eabi_attribute 34, 1
	.eabi_attribute 18, 4
	.file	"callercallee.c"
	.text
	.align	2
	.global	funk
	.syntax unified
	.arm
	.fpu vfpv3-d16
	.type	funk, %function
funk:
	@ args = 4, pretend = 0, frame = 0
	@ frame_needed = 0, uses_anonymous_args = 0
	push	{r4, r5, r6, lr}
	mov	r4, r0
	mov	r5, r1
	mov	r6, r2
	ldr	r1, [sp, #16]
	mov	r0, r3
	bl	fum(PLT)
	mov	r1, r0
	mov	r0, r6
	bl	foe(PLT)
	mov	r1, r0
	mov	r0, r5
	bl	fi(PLT)
	mov	r1, r0
	mov	r0, r4
	bl	fee(PLT)
	pop	{r4, r5, r6, pc}
	.size	funk, .-funk
	.ident	"GCC: (GNU) 7.2.1 20180116"
	.section	.note.GNU-stack,"",%progbits
