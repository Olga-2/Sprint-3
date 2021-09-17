//package kotlin.ru.sber.qa

import io.mockk.*
import io.mockk.impl.annotations.SpyK
import org.junit.After
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.sber.qa.CertificateRequest
import ru.sber.qa.ScanTimeoutException
import kotlin.random.Random
import kotlin.test.assertEquals


class ScannerTest {
    private lateinit var certificateRequest: CertificateRequest
    private val scanData = Random.nextBytes(100)

    @BeforeEach
    fun beforeTest() {
        mockkObject(Random)
        mockkObject(Scanner)
        every {
            Random.nextBytes(100)
        } returns scanData

    }

    @After
    fun afterTests() {
        unmockkAll()
    }

    @SpyK
    var scanner = Scanner

    @Test
    fun `проверка exception при превышении длительности сканирования`() {

        every {
            Random.nextLong(5000L, 15000L)
        } returns 10_001L

        assertThrows(ScanTimeoutException::class.java) {
            scanner.getScanData()
        }

     }

    @Test
    fun `проверка результата сканирования`() {

        every {
            Random.nextLong(5000L, 15000L)
        } returns 10_000

        assertEquals(scanData, scanner.getScanData(), "Неверный результат сканирования")
    }

    // todo нужен еще метод, проверяющий что вызван sleep с конкретным параметром - не нашла как замокировать Thread
    //
}