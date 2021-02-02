package cubex2.cs3.asm;

import com.google.common.collect.Lists;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.io.*;
import java.util.List;

import static org.objectweb.asm.Opcodes.*;

public class ModGenerator
{
    private List<GeneratedClass> classes = Lists.newArrayList();
    private ModGenData data;
    private File baseDir;

    public ModGenerator(ModGenData data, File baseDir)
    {
        this.data = data;
        this.baseDir = baseDir;

        classes.add(genMainModClass());

        for (GeneratedClass clazz : classes)
        {
            if (!clazz.file.getParentFile().exists())
            {
                clazz.file.getParentFile().mkdirs();
            }
            try
            {
                FileOutputStream fs = new FileOutputStream(clazz.file);
                fs.write(clazz.bytes);
                fs.close();
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        try
        {
            FileWriter sw = new FileWriter(new File(baseDir, "_customstuff/data.dat"));
            sw.write(data.modId.toCharArray());
            sw.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private GeneratedClass genMainModClass()
    {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, "_customstuff/" + data.modId.toLowerCase() + "/" + data.modClassName, null, "java/lang/Object", new String[]{"cubex2/cs3/asm/ICSMod"});

        cw.visitInnerClass("cpw/mods/fml/common/Mod$EventHandler", "cpw/mods/fml/common/Mod", "EventHandler", ACC_PUBLIC + ACC_STATIC + ACC_ANNOTATION + ACC_ABSTRACT + ACC_INTERFACE);

        // Mod annotation
        AnnotationVisitor av = cw.visitAnnotation("Lcpw/mods/fml/common/Mod;", true);
        av.visit("modid", data.modId);
        av.visit("name", data.modName);
        av.visit("version", data.modVersion);
        av.visit("dependencies", "required-after:CustomStuff3");
        av.visitEnd();

        // Constructor
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        // PreInit
        mv = cw.visitMethod(ACC_PUBLIC, "preInit", "(Lcpw/mods/fml/common/event/FMLPreInitializationEvent;)V", null, null);
        av = mv.visitAnnotation("Lcpw/mods/fml/common/Mod$EventHandler;", true);
        av.visitEnd();
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESTATIC, "cubex2/cs3/CustomStuff3", "onPreInitPack", "(Lcubex2/cs3/asm/ICSMod;)V");
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 2);
        mv.visitEnd();

        // Init
        mv = cw.visitMethod(ACC_PUBLIC, "init", "(Lcpw/mods/fml/common/event/FMLInitializationEvent;)V", null, null);
        av = mv.visitAnnotation("Lcpw/mods/fml/common/Mod$EventHandler;", true);
        av.visitEnd();
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESTATIC, "cubex2/cs3/CustomStuff3", "onInitPack", "(Lcubex2/cs3/asm/ICSMod;)V");
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 2);
        mv.visitEnd();

        // PostInit
        mv = cw.visitMethod(ACC_PUBLIC, "postInit", "(Lcpw/mods/fml/common/event/FMLPostInitializationEvent;)V", null, null);
        av = mv.visitAnnotation("Lcpw/mods/fml/common/Mod$EventHandler;", true);
        av.visitEnd();
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESTATIC, "cubex2/cs3/CustomStuff3", "onPostInitPack", "(Lcubex2/cs3/asm/ICSMod;)V");
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 2);
        mv.visitEnd();

        // getName
        mv = cw.visitMethod(ACC_PUBLIC, "getName", "()Ljava/lang/String;", null, null);
        mv.visitCode();
        mv.visitLdcInsn(data.modName);
        mv.visitInsn(ARETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        // getId
        mv = cw.visitMethod(ACC_PUBLIC, "getId", "()Ljava/lang/String;", null, null);
        mv.visitCode();
        mv.visitLdcInsn(data.modId);
        mv.visitInsn(ARETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        // getVersion
        mv = cw.visitMethod(ACC_PUBLIC, "getVersion", "()Ljava/lang/String;", null, null);
        mv.visitCode();
        mv.visitLdcInsn(data.modVersion);
        mv.visitInsn(ARETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        // isIngamePack
        mv = cw.visitMethod(ACC_PUBLIC, "isIngamePack", "()Z", null, null);
        mv.visitCode();
        mv.visitInsn(data.isIngamePack ? ICONST_1 : ICONST_0);
        mv.visitInsn(IRETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        cw.visitEnd();

        return new GeneratedClass(new File(baseDir, "_customstuff/" + data.modId.toLowerCase() + "/" + data.modClassName + ".class"), cw.toByteArray());
    }
}
