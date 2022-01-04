/*
 * Copyright (c) 2019-2022 Choko (choko@curioswitch.org)
 * SPDX-License-Identifier: MIT
 */

package org.curioswitch.common.protobuf.json.bytebuddy;

import net.bytebuddy.implementation.Implementation.Context;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.StackSize;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

/**
 * {@link StackManipulation} which jumps to a destination if the two references on the execution
 * stack are not equal.
 *
 * <p>Used for if-statements like:
 *
 * <pre>{code
 *   if (obj1 == obj2) {
 *      ...
 *   }
 *   // destination
 * }</pre>
 */
public final class IfRefsNotEqual implements StackManipulation {

  private final Label destination;

  public IfRefsNotEqual(Label destination) {
    this.destination = destination;
  }

  @Override
  public boolean isValid() {
    return true;
  }

  @Override
  public Size apply(MethodVisitor methodVisitor, Context implementationContext) {
    methodVisitor.visitJumpInsn(Opcodes.IF_ACMPNE, destination);
    return new Size(-StackSize.SINGLE.getSize() * 2, 0);
  }
}
