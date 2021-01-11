	.text
	.global	func1
func1:
	push	{r3, lr}
	ldr	r1, [sp, #12]
	ldr	r0, [sp, #8]
	bl	f
	adds	r0, r0, #1
	pop	{r3, pc}
	.size	func1, .-func1
	.ident	"GCC: (GNU) 7.2.1 20180116"
	.section	.note.GNU-stack,"",%progbits
