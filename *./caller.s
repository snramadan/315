	.arch armv7-a
	.eabi_attribute 28, 1
	.eabi_attribute 20, 1
	.eabi_attribute 21, 1
	.eabi_attribute 23, 3
	.eabi_attribute 24, 1
	.eabi_attribute 25, 1
	.eabi_attribute 26, 2
	.eabi_attribute 30, 6
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
	@ args = 4, pretend = 0, frame = 16
	@ frame_needed = 1, uses_anonymous_args = 0
	push	{fp, lr}
	add	fp, sp, #4
	sub	sp, sp, #16
	str	r0, [fp, #-8]
	str	r1, [fp, #-12]
	str	r2, [fp, #-16]
	str	r3, [fp, #-20]
	ldr	r1, [fp, #4]
	ldr	r0, [fp, #-20]
	bl	fum(PLT)
	mov	r3, r0
	mov	r1, r3
	ldr	r0, [fp, #-16]
	bl	foe(PLT)
	mov	r3, r0
	mov	r1, r3
	ldr	r0, [fp, #-12]
	bl	fi(PLT)
	mov	r3, r0
	mov	r1, r3
	ldr	r0, [fp, #-8]
	bl	fee(PLT)
	mov	r3, r0
	mov	r0, r3
	sub	sp, fp, #4
	@ sp needed
	pop	{fp, pc}
	.size	funk, .-funk
	.ident	"GCC: (GNU) 7.2.1 20180116"
	.section	.note.GNU-stack,"",%progbits
