package ru.sber.nio

import io.mockk.InternalPlatformDsl.toArray
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.useLines
import kotlin.streams.toList

/**
 * Реализовать простой аналог утилиты grep с использованием калссов из пакета java.nio.
 */
class Grep {
    /**
     * Метод должен выполнить поиск подстроки subString во всех файлах каталога logs.
     * Каталог logs размещен в данном проекте (io/logs) и внутри содержит другие каталоги.
     * Результатом работы метода должен быть файл в каталоге io(на том же уровне, что и каталог logs), с названием result.txt.
     * Формат содержимого файла result.txt следующий:
     * имя файла, в котором найдена подстрока : номер строки в файле : содержимое найденной строки
     * Результирующий файл должен содержать данные о найденной подстроке во всех файлах.
     * Пример для подстроки "22/Jan/2001:14:27:46":
     * 22-01-2001-1.log : 3 : 192.168.1.1 - - [22/Jan/2001:14:27:46 +0000] "POST /files HTTP/1.1" 200 - "-"
     */
    fun find(subString: String) {
        val path = Paths.get("io", "logs").toAbsolutePath()

        val outFile = Files.createFile( Paths.get("io", "result.txt"))
        var fileList = Files.find(path, 10, { p, _ -> p.toString().endsWith(".log") })

        Files.newBufferedWriter(outFile).use { out ->
            for (filePath in fileList) {
                var lines = Files.lines(filePath).toList()
                for (i in 0..lines.size - 1) {
                    var line = lines.get(i)
                    if (line.contains(subString))
                        out.write("${filePath.fileName} : ${i + 1} : ${line} \n")
                }
            }
        }
    }
}