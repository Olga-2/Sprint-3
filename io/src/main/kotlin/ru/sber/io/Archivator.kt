package ru.sber.io

import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

/**
 * Реализовать методы архивации и разархивации файла.
 * Для реализиации использовать ZipInputStream и ZipOutputStream.
 */
class Archivator {

    private val fileNameIn = "logfile.log"
    private val fileNameZip = "logfile.zip"
    private val fileNameUnZip= "unzippedLogfile.log"

    /**
     * Метод, который архивирует файл logfile.log в архив logfile.zip.
     * Архив должен располагаться в том же каталоге, что и исходной файл.
     */
    fun zipLogfile() {
        val inputStream = FileInputStream(fileNameIn)
        val zipStream = ZipOutputStream(BufferedOutputStream(FileOutputStream(fileNameZip)))

        val data = ByteArray(1024)
        zipStream.use{ zip ->
            val entry = ZipEntry(fileNameIn);
            zip.putNextEntry(entry)
            inputStream.use {
                var buffer = it.read(data)
                while (buffer != -1) {
                    zip.write(data, 0, buffer)
                    buffer = it.read(data)
                }
            }
            zip.closeEntry()
        }

    }

    /**
     * Метод, который извлекает файл из архива.
     * Извлечь из архива logfile.zip файл и сохарнить его в том же каталоге с именем unzippedLogfile.log
     */
    fun unzipLogfile() {
        val inputStream = ZipInputStream(FileInputStream(fileNameZip))
        val outputStream = BufferedOutputStream(FileOutputStream(fileNameUnZip))
        val data = ByteArray(1024)
        outputStream.use{ stream ->
            inputStream.use {
                try {
                    val entry = inputStream.getNextEntry();
                }catch (e :IOException){
                    println("Ошибка получения данных из архива: $e")
                }
                var buffer = it.read(data)
                while (buffer != -1) {
                    stream.write(data, 0, buffer)
                    buffer = it.read(data)
                }
                it.closeEntry()
            }
        }
    }
}