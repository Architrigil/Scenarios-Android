package com.rigil.scenarios.nearby

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object SerializationHelper {
    @Throws(IOException::class)
    fun serialize(`object`: Any): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
        objectOutputStream.writeObject(`object`)
        objectOutputStream.flush()
        objectOutputStream.close()
        return byteArrayOutputStream.toByteArray()
    }
    @Throws(IOException::class, ClassNotFoundException::class)
    fun deserialize(bytes: ByteArray?): Any {
        val byteArrayInputStream = ByteArrayInputStream(bytes)
        val objectInputStream = ObjectInputStream(byteArrayInputStream)
        return objectInputStream.readObject()
    }
}
