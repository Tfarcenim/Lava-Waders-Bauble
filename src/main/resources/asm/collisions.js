
function initializeCoreMod() {
  return {
    "Collisions": {
      "target": {
        "type": "METHOD", "class": "net.minecraft.block.Block",
        "methodName": "func_220071_b", //getCollisionShape
         "methodDesc": "(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/shapes/ISelectionContext;)Lnet/minecraft/util/math/shapes/VoxelShape;"
      },
      "transformer": function(method) {
              print('[LavaWadersBauble]: Patching Minecraft\' Block#getCollisionShapes');

                var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
                var Opcodes = Java.type('org.objectweb.asm.Opcodes');
                var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
                var InsnList = Java.type('org.objectweb.asm.tree.InsnList');
                var InsnNode = Java.type('org.objectweb.asm.tree.InsnNode');

                var newInstructions = new InsnList();

                newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
                newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 2));
                newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 3));
                newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 4));

                newInstructions.add(ASM.buildMethodCall(
                    "com/tfar/lavawaderbauble/AsmHooks",
                    "getShape",
                    "(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/shapes/ISelectionContext;)Lnet/minecraft/util/math/shapes/VoxelShape;",
                    ASM.MethodType.STATIC
                ));

                newInstructions.add(new InsnNode(Opcodes.ARETURN));

                method.instructions.insertBefore(method.instructions.getFirst(), newInstructions);

                return method;
            }
    }
  };
}
