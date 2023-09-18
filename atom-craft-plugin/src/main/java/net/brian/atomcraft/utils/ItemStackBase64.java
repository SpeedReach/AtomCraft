package net.brian.atomcraft.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ItemStackBase64 {

    public String toBase64(ItemStack itemStack) throws IOException {
        ByteArrayOutputStream io = new ByteArrayOutputStream();
        BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
        os.writeObject(itemStack);
        os.flush();
        byte[] serializedObject = io.toByteArray();
        //Encode the serialized object into to the base64 format
        return new String(Base64.getEncoder().encode(serializedObject));
    }

    public ItemStack fromBase64(String base64) throws IOException, ClassNotFoundException {
        byte[] serializedObject = Base64.getDecoder().decode(base64);

        //Input stream to read the byte array
        ByteArrayInputStream in = new ByteArrayInputStream(serializedObject);
        //object input stream to serialize bytes into objects
        BukkitObjectInputStream is = new BukkitObjectInputStream(in);

        //Use the object input stream to deserialize an object from the raw bytes
       return (ItemStack) is.readObject();
    }

}
